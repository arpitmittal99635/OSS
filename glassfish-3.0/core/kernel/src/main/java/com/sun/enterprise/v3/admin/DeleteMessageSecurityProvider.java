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
package com.sun.enterprise.v3.admin;

import org.glassfish.api.admin.AdminCommand;
import org.glassfish.api.admin.AdminCommandContext;
import org.glassfish.api.I18n;
import org.glassfish.api.Param;
import org.glassfish.api.ActionReport;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.annotations.Scoped;
import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.component.PerLookup;
import org.jvnet.hk2.config.ConfigSupport;
import org.jvnet.hk2.config.SingleConfigCode;
import org.jvnet.hk2.config.TransactionFailure;
import com.sun.enterprise.config.serverbeans.SecurityService;
import com.sun.enterprise.config.serverbeans.MessageSecurityConfig;
import com.sun.enterprise.config.serverbeans.ProviderConfig;
import com.sun.enterprise.util.LocalStringManagerImpl;

import java.beans.PropertyVetoException;
import java.util.Iterator;
import java.util.List;

/**
 * Delete Message Security Provider Command
 * 
 * Usage: delete-message-security-provider --layer message_layer [--terse=false] 
 *        [--echo=false] [--interactive=true] [--host localhost] [--port 4848|4849] 
 *        [--secure | -s] [--user admin_user] [--passwordfile file_name] 
 *        [--target target(Defaultserver)] provider_name
 *
 * @author Nandini Ektare
 */
@Service(name="delete-message-security-provider")
@Scoped(PerLookup.class)
@I18n("delete.message.security.provider")
public class DeleteMessageSecurityProvider implements AdminCommand {
    
    final private static LocalStringManagerImpl localStrings = 
        new LocalStringManagerImpl(DeleteMessageSecurityProvider.class);

    @Param(name="providername", primary=true)
    String providerId;
 
    // auth-layer can only be SOAP | HttpServlet
    @Param(name="layer",defaultValue="SOAP")
    String authLayer;
    
    @Param(optional=true)
    String target;

    @Inject
    SecurityService securityService;   

    ProviderConfig thePC = null;
    MessageSecurityConfig msgSecCfg = null;
    
    /**
     * Executes the command with the command parameters passed as Properties
     * where the keys are the paramter names and the values the parameter values
     *
     * @param context information
     */
    public void execute(AdminCommandContext context) {
        
        ActionReport report = context.getActionReport();        
        List<MessageSecurityConfig> mscs = securityService.getMessageSecurityConfig();        
        
        for (MessageSecurityConfig  msc : mscs) {
            if (msc.getAuthLayer().equals(authLayer)) {
                msgSecCfg = msc;
            }
        }
        
        if (msgSecCfg == null) {           
            report.setMessage(localStrings.getLocalString(
                "delete.message.security.provider.confignotfound", 
                "A Message security config does not exist for the layer {0}", 
                authLayer));
            report.setActionExitCode(ActionReport.ExitCode.FAILURE);
            return;            
        }
        
        List<ProviderConfig> pcs = msgSecCfg.getProviderConfig();
        for (ProviderConfig pc : pcs) {
            if (pc.getProviderId().equals(providerId)) { 
                thePC = pc;
                try {
                    ConfigSupport.apply(
                        new SingleConfigCode<MessageSecurityConfig>() {
                        
                        public Object run(MessageSecurityConfig param) 
                        throws PropertyVetoException, TransactionFailure {

                            if ((param.getDefaultProvider() != null) &&
                                param.getDefaultProvider().equals(
                                    thePC.getProviderId())) {
                                param.setDefaultProvider(null);
                            }
                                
                            if ((param.getDefaultClientProvider() != null) &&
                                 param.getDefaultClientProvider().equals(
                                    thePC.getProviderId())) {
                                param.setDefaultClientProvider(null);
                            }
                            
                            param.getProviderConfig().remove(thePC);                                                                                       
                            return null;
                        }
                    }, msgSecCfg);
                } catch(TransactionFailure e) {
                    e.printStackTrace();
                    report.setMessage(localStrings.getLocalString(
                        "delete.message.security.provider.fail", 
                        "Deletion of message security provider named {0} failed", 
                        providerId));
                    report.setActionExitCode(ActionReport.ExitCode.FAILURE);
                    report.setFailureCause(e);
                    return;
                }
                report.setMessage(localStrings.getLocalString(
                    "delete.message.security.provider.success", 
                    "Deletion of message security provider {0} completed " +
                    "successfully", providerId));
                report.setActionExitCode(ActionReport.ExitCode.SUCCESS);
                return;                
            }
        }
    }
}