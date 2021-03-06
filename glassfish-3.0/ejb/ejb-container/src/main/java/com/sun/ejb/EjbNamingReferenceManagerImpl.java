/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.ejb;

import com.sun.ejb.containers.EJBContextImpl;
import com.sun.ejb.containers.EJBTimerServiceWrapper;
import com.sun.ejb.containers.EjbContainerUtil;
import com.sun.enterprise.container.common.spi.EjbNamingReferenceManager;
import com.sun.enterprise.deployment.EjbReferenceDescriptor;
import org.glassfish.api.invocation.ComponentInvocation;
import org.glassfish.api.invocation.InvocationManager;
import org.glassfish.enterprise.iiop.api.GlassFishORBHelper;
import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Habitat;
import javax.naming.Context;
import com.sun.enterprise.util.Utility;

import org.omg.CORBA.ORB;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.InitialContext;
import java.util.logging.Level;

/**
 * @author Mahesh Kannan
 */

@Service
public class EjbNamingReferenceManagerImpl
    implements EjbNamingReferenceManager {

    private static final String CORBANAME = "corbaname:";
    private static final String IIOPURL = "iiop://";

    @Inject
    InvocationManager invMgr;

    @Inject
    Habitat habitat;

    // Be careful with EjbContainerUtil usage.  It should only be used from code running within a
    // server environment.
    private volatile EjbContainerUtil ejbContainerUtil;

    public Object resolveEjbReference(EjbReferenceDescriptor ejbRefDesc, Context context)
        throws NamingException {

        Object jndiObj = null;
        boolean resolved = false;


        if( ejbRefDesc.isLocal() ) {
            // local ejb dependencies if there's a lookup string, use that.
            // Otherwise, the ejb will be resolved by EJBUtils.
            if( ejbRefDesc.hasLookupName()) {
                jndiObj = context.lookup(ejbRefDesc.getLookupName());
                resolved = true;
            }
        } else if( !ejbRefDesc.hasJndiName() && ejbRefDesc.hasLookupName() ) {
            // For a remote reference, only do a context lookup if there is no
            // jndi name. 
            jndiObj = context.lookup(ejbRefDesc.getLookupName());
            resolved = true;
        } else if( ejbRefDesc.hasJndiName() &&
                   ejbRefDesc.getJndiName().startsWith("java:app/") &&
                   !ejbRefDesc.getJndiName().startsWith("java:app/env/")) {

            // This could be an @EJB dependency in an appclient whose target name
            // is a portable java:app ejb name.  Try the global version.  If that
            // doesn't work, the javaURLContext logic should be able to figure it
            // out.
            String remoteJndiName = ejbRefDesc.getJndiName();

            String appName = (String) context.lookup("java:app/AppName");
            String newPrefix = "java:global/" + appName + "/";

            int javaAppLength = "java:app/".length();
            String globalLookup = newPrefix + remoteJndiName.substring(javaAppLength);

            jndiObj = context.lookup(globalLookup);
            resolved = true;

        } else {

            // Get actual jndi-name from ejb module.
            String remoteJndiName = EJBUtils.getRemoteEjbJndiName(ejbRefDesc);

            // We could be resolving an ejb-ref as part of a remote lookup thread.  In that
            // case the context class loader won't be set appropriately on the thread
            // being used to process the remote naming request.   We can't just always
            // set the context class loader to the class loader of the application module
            // that defined the ejb reference.  That would cause ClassCastExceptions
            // when the returned object is assigned within a cross-application intra-server
            // lookup. So, just try to lookup the interface associated with the ejb-ref
            // using the context class loader.  If that doesn't work, explicitly use the
            // defining application's class loader.

            ClassLoader origClassLoader = Utility.getClassLoader();
            boolean setCL = false;

            try {

                try {
                    
                    String refInterface = ejbRefDesc.isEJB30ClientView() ?
                       ejbRefDesc.getEjbInterface() : ejbRefDesc.getHomeClassName();
                    origClassLoader.loadClass(refInterface);

                } catch(ClassNotFoundException e) {

                     ClassLoader referringBundleClassLoader =
                             ejbRefDesc.getReferringBundleDescriptor().getClassLoader();
                     Utility.setContextClassLoader(referringBundleClassLoader);
                     setCL = true;

                }

                /* For remote ejb refs, first lookup the target remote object
                 * and pass it to the next stage of ejb ref resolution.
                 * If the string is a "corbaname:...." URL
                 * the lookup happens thru the corbanameURL context,
                 * else it happens thru the context provided by the NamingManager.
                 *
                 * NOTE : we might need some additional logic to handle cross-server
                 * MEJB resolution for cluster support post V3 FCS.
                 */
                if (remoteJndiName.startsWith(CORBANAME)) {
                    GlassFishORBHelper orbHelper = habitat.getComponent(GlassFishORBHelper.class);

                    ORB orb = orbHelper.getORB();
                    jndiObj = (Object) orb.string_to_object(remoteJndiName);
                } else {
                    jndiObj = context.lookup(remoteJndiName);
                }
                
            } catch(Exception e) {
                // Important to make the real underlying lookup name part of the exception.
                NamingException ne = new NamingException("Exception resolving Ejb for '" +
                    ejbRefDesc + "' .  Actual (possibly internal) Remote JNDI name used for lookup is '" +
                    remoteJndiName + "'");
                ne.initCause(e);
                throw ne;
            } finally {
                if( setCL ) {
                    Utility.setContextClassLoader(origClassLoader);
                }
            }
        }

        return resolved ? jndiObj : EJBUtils.resolveEjbRefObject(ejbRefDesc, jndiObj);
    }

    public boolean isEjbReferenceCacheable(EjbReferenceDescriptor ejbRefDesc) {
        // Ejb-ref is only eligible for caching if it refers to the legacy
        // Home view and it is resolved to an ejb within the same application.
        return ( (!ejbRefDesc.isEJB30ClientView()) &&
                 (ejbRefDesc.getEjbDescriptor() != null) );

        // caching not enabled.
        //return false;
    }


    public Object getEJBContextObject(String contextType) {

        ComponentInvocation currentInv = invMgr.getCurrentInvocation();

        if(currentInv == null) {
            throw new IllegalStateException("no current invocation");
        } else if (currentInv.getInvocationType() !=
                   ComponentInvocation.ComponentInvocationType.EJB_INVOCATION) {
            throw new IllegalStateException
                ("Illegal invocation type for EJB Context : "
                 + currentInv.getInvocationType());
        }

        EjbInvocation ejbInv = (EjbInvocation) currentInv;

        Object returnObject = ejbInv.context;

        if( contextType.equals("javax.ejb.TimerService") ) {
            if (ejbContainerUtil == null) {
                ejbContainerUtil = habitat.getByContract(EjbContainerUtil.class);
            }

            if (ejbContainerUtil == null ) {
                throw new IllegalStateException("EJB Timer Service not " +
                                                "available. And EjbContainerUtil is null");
            }
            if (ejbContainerUtil.getEJBTimerService() == null ) {
                throw new IllegalStateException("EJB Timer Service not " +
                                                "available");
            }
            returnObject = new EJBTimerServiceWrapper
                (ejbContainerUtil.getEJBTimerService(), (EJBContextImpl) ejbInv.context);
        }


        return returnObject;
    }


}
