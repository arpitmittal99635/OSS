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
package com.sun.enterprise.connectors.jms.system;

import com.sun.enterprise.config.serverbeans.*;
import com.sun.enterprise.transaction.spi.RecoveryResourceHandler;
import com.sun.enterprise.util.LocalStringManagerImpl;
import com.sun.logging.LogDomains;

import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.transaction.xa.XAResource;
//import javax.jms.Session;
//import javax.jms.JMSException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.lang.reflect.Method;


import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.component.Habitat;

/**
 * Recovery Handler for JMS Resources
 *
 */
@Service
public class LegacyJmsRecoveryResourceHandler implements RecoveryResourceHandler {

    @Inject
    private TransactionService txService;

    @Inject
    private Resources resources;

    @Inject
    private Habitat connectorRuntimeHabitat;

    static final String JMS_QUEUE_CONNECTION_FACTORY = "javax.jms.QueueConnectionFactory";
    static final String JMS_TOPIC_CONNECTION_FACTORY = "javax.jms.TopicConnectionFactory";

    private static Logger _logger = LogDomains.getLogger(LegacyJmsRecoveryResourceHandler.class, LogDomains.RSR_LOGGER);
    final private static LocalStringManagerImpl localStrings = new LocalStringManagerImpl(LegacyJmsRecoveryResourceHandler.class);


     public void loadXAResourcesAndItsConnections(List xaresList, List connList)
     {
            try {
            Collection<ExternalJndiResource> jndiResources = resources.getResources(ExternalJndiResource.class);
            InitialContext ic = new InitialContext();
            for (ExternalJndiResource jndiResource : jndiResources) {
                if (isEnabled(jndiResource)) {
                    try {
                        ic.lookup(jndiResource.getJndiName());
                         if (!isJMSConnectionFactory(jndiResource)) {
                            continue;
                        }

                        String jndiName = jndiResource.getJndiName();
                        Object jmsXAConnectionFactory;
                        boolean isQueue;
                        if (jndiResource instanceof ExternalJndiResource) {
                            Object objext = ic.lookup(jndiName);
                            if (!instanceOf(objext, "ConnectionFactory")) {
                                throw new NamingException(localStrings.getLocalString("recovery.unexpected_objtype",
                                "Unexpected object type "+objext.getClass().getName()+" for "+ jndiName,
                                new Object[]{objext.getClass().getName(), jndiName}));
                            }
                            jmsXAConnectionFactory = wrapJMSConnectionFactoryObject(objext);
                            isQueue = instanceOf(objext, "QueueConnectionFactory");
                        }
                        else {
                            jmsXAConnectionFactory = ic.lookup(getXAConnectionFactoryName(jndiName));
                            isQueue = instanceOf(jmsXAConnectionFactory, "JMSXAQueueConnectionFactory");
                        }
                        recoverJMSXAResource(xaresList, connList, jmsXAConnectionFactory, isQueue);
                    //} catch (NamingException ne) {
                        //If you are here then it is most probably an embedded RAR resource
                        //So we need to explicitly load that rar and create the resources

                    } catch (Exception ex) {
                        _logger.log(Level.SEVERE, "error.loading.connector.resources.during.recovery", jndiResource.getJndiName());
                        if (_logger.isLoggable(Level.FINE)) {
                            _logger.log(Level.FINE, ex.toString(), ex);
                        }
                    }
                }
            }
        } catch (NamingException ne) {
            _logger.log(Level.SEVERE, "error.loading.connector.resources.during.recovery", ne.getMessage());
            if (_logger.isLoggable(Level.FINE)) {
                _logger.log(Level.FINE, ne.toString(), ne);
            }
        }
     }
     public void closeConnections(List connList) {
        for (Object obj : connList) {
            try {
                 closeXAConnection(obj);
            } catch (Exception ex) {
                _logger.log(Level.WARNING, "Connector Resource could not be closed", ex);
            }
        }
    }
  private boolean isEnabled(ExternalJndiResource resource) {
        return Boolean.valueOf(resource.getEnabled());
    }

 public boolean isJMSConnectionFactory(ExternalJndiResource resType_) {
        if (resType_ == null) return false;

        return (JMS_QUEUE_CONNECTION_FACTORY.equals(resType_.getResType()) ||
                JMS_TOPIC_CONNECTION_FACTORY.equals(resType_.getResType()));
    }

  private boolean instanceOf(Object obj, String interfaceName)
  {
      if(obj==null) return false;
        //Class jmsXAQueueConnectionFactory = Class.forName("JMSXAQueueConnectionFactory");
        Class[] classes = obj.getClass().getInterfaces();
        for (Class aClass : classes) {
            String aClassName = aClass.getName();
            if (aClassName.indexOf(interfaceName) != -1)
                return true;
        }

      return false;

  }
 //-------------------------

   /* private void recoverJMSXAResources(Context ic,
    Vector xaresList, Vector connList, Set jmsRes) {
        for(Iterator iter = jmsRes.iterator(); iter.hasNext();) {
            J2EEResource next = (J2EEResource)iter.next();
            if (next instanceof ExternalJndiResource) {
                if (!((ExternalJndiResource)next).isJMSConnectionFactory()) {
                    continue;
                }
            }

            String jndiName = next.getName();
            try {
                Object jmsXAConnectionFactory;
                boolean isQueue;
                if (next instanceof ExternalJndiResource) {
                    Object objext = ic.lookup(jndiName);
                    if (!(objext instanceof javax.jms.ConnectionFactory)) {
                        throw new NamingException(localStrings.getLocalString("recovery.unexpected_objtype",
                        "Unexpected object type "+objext.getClass().getName()+" for "+ jndiName,
                        new Object[]{objext.getClass().getName(), jndiName}));
                    }
                    jmsXAConnectionFactory = wrapJMSConnectionFactoryObject(objext);
                    isQueue = (objext instanceof javax.jms.QueueConnectionFactory);
                }
                else {
                    jmsXAConnectionFactory = ic.lookup(getXAConnectionFactoryName(jndiName));
                    isQueue = (jmsXAConnectionFactory instanceof JMSXAQueueConnectionFactory);
                }
                recoverJMSXAResource(xaresList, connList, jmsXAConnectionFactory, isQueue);
            } catch (Exception ex) {
                _logger.log(Level.SEVERE,"datasource.xadatasource_error",jndiName);
                _logger.log(Level.SEVERE,"datasource.xadatasource_error_excp",ex);
            }
        }
    }  */


    private void recoverJMSXAResource(List xaresList, List connList,
    Object jmsXAConnectionFactory, boolean isQueue ) throws Exception {
        if (isQueue) {
           // JMSXAQueueConnectionFactory fac =
            //(JMSXAQueueConnectionFactory) obj;

            Object jmsXAQueueConnection = getXAConnection (jmsXAConnectionFactory, "createXAQueueConnection");
            connList.add(jmsXAQueueConnection);

            XAResource xares = getXAResource(jmsXAQueueConnection, "createXAQueueSession");//con.createXAQueueSession
            //(true, Session.AUTO_ACKNOWLEDGE).getXAResource();
            xaresList.add(xares);
        } else {
            // XATopicConnectionFactory
            //JMSXATopicConnectionFactory fac =
            //(JMSXATopicConnectionFactory) obj;
            Object jmsXATopicConnection = getXAConnection(jmsXAConnectionFactory, "createXATopicConnection");
            connList.add(jmsXATopicConnection);

            XAResource xares = getXAResource(jmsXATopicConnection, "createXATopicSession");
            //XAResource xares = con.createXATopicSession
            //(true, Session.AUTO_ACKNOWLEDGE).getXAResource();
            xaresList.add(xares);
        }
    }
    private Object jmsAdmin = null;
    private Object getJmsAdmin()
    {
        if (jmsAdmin != null)  return jmsAdmin;
        try{
               Class jmsAdminFactoryClass = Class.forName("com.sun.messaging.jmq.admin.jmsspi.JMSAdminFactoryImpl");
               Object jmsAdminFactory = jmsAdminFactoryClass.newInstance();

               Method m = jmsAdminFactoryClass.getMethod("getJMSAdmin", null);
               jmsAdmin = m.invoke(jmsAdminFactory, null);
               return jmsAdmin;
           }catch (Exception ex){
                  throw new RuntimeException ("Unable to create an JmsAdmin object. Cause - " + ex.getMessage(), ex);
           }

    }

    /**
         * Form the name of the internal XA Connection Factory
         * for each javax.jms.ConnectionFactory
         */
      private String getXAConnectionFactoryName(String connectionFactoryName) {
            return "JMSXA" + connectionFactoryName + "__jmsxa";
        }

    /**
     * wrap a JMS standard XAQueue/TopicConnectionFactory or Queue/TopicConnectionFactory
     *
     * This method is used for foreign (non-built-in) JMS provider
     *
     * @return a Object of JMSXAConnectionFactory
     * @throws Exception if syntax error
     */
    private Object wrapJMSConnectionFactoryObject(Object obj)
        throws Exception {

        Method m = getJmsAdmin().getClass().getMethod("wrapJMSConnectionFactoryObject", obj.getClass());
        return  m.invoke(getJmsAdmin(), obj);
    }
   private Object getXAConnection(Object XAconnectionFactory, String methodName)
   {
       try{
         Class connectionFactoryClass = XAconnectionFactory.getClass();
         Method m = connectionFactoryClass.getMethod(methodName, null);
         return m.invoke(XAconnectionFactory, null);
       }catch(Exception e){
           //todo: need to handle this better
       }
       return null;

   }
   private XAResource getXAResource(Object XAConnection, String methodName)
   {
       try{
         Class connectionClass = XAConnection.getClass();
         Class[] paramtypes = {boolean.class, int.class};
         Method m = connectionClass.getMethod(methodName, paramtypes);
         Object jmsXASession = m.invoke(XAConnection, new Object[]{true, 1}); //Session.AUTO_ACKNOWLEDGE});

         Class xaSessionClass =  jmsXASession.getClass();
         Method m1 = xaSessionClass.getMethod("getXAResource", null);
         return (XAResource) m1.invoke(jmsXASession, null);
       }catch(Exception e){
           //todo: need to handle this better
       }
       return null;
   }
    private void closeXAConnection(Object jmsXAConnection){

        try{
        Class jmsXAConnectionClass = jmsXAConnection.getClass();
        Method m = jmsXAConnectionClass.getMethod("close", null);
        m.invoke(jmsXAConnection, null);
        }catch (Exception e){
            //todo: need to handle this better
        }

    }
}
