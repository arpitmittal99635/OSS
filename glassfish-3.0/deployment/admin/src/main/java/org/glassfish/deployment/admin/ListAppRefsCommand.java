/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
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
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
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

package org.glassfish.deployment.admin;

import org.glassfish.api.ActionReport;
import org.glassfish.api.admin.AdminCommand;
import org.glassfish.api.admin.AdminCommandContext;
import org.glassfish.api.Param;
import org.jvnet.hk2.annotations.Service;
import com.sun.enterprise.config.serverbeans.ConfigBeansUtilities;
import com.sun.enterprise.config.serverbeans.ApplicationRef;
import com.sun.enterprise.config.serverbeans.Application;
import com.sun.enterprise.config.serverbeans.Engine;
import com.sun.enterprise.config.serverbeans.Module;
import com.sun.enterprise.config.serverbeans.ServerTags;
import org.glassfish.api.admin.config.ApplicationName;
import com.sun.enterprise.util.LocalStringManagerImpl;
import org.jvnet.hk2.annotations.Scoped;
import org.jvnet.hk2.component.PerLookup;
import java.util.List;

@Service(name="list-app-refs")
@Scoped(PerLookup.class)
public class ListAppRefsCommand implements AdminCommand {

    @Param(optional=true)
    String target = "server";

    @Param(optional=true)
    String type = null;

    @Param(optional=true, defaultValue="all")
    String state;

    final private static LocalStringManagerImpl localStrings = new LocalStringManagerImpl(ListAppRefsCommand.class);    

    public void execute(AdminCommandContext context) {
        
        final ActionReport report = context.getActionReport();

        ActionReport.MessagePart part = report.getTopMessagePart();
        part.setMessage(target);
        part.setChildrenType("application");
        List<ApplicationRef> appRefs = 
            ConfigBeansUtilities.getApplicationRefsInServer(target);
        for (ApplicationRef appRef : appRefs) {
            if (state.equals("all") || 
               (state.equals("running") && 
                Boolean.valueOf(appRef.getEnabled())) ||
               (state.equals("non-running") && 
                !Boolean.valueOf(appRef.getEnabled())) ) {
                if (isApplicationOfThisType(appRef.getRef(), type)) {
                    ActionReport.MessagePart childPart = part.addChild();
                    childPart.setMessage(appRef.getRef());
                }
            }
        }
        report.setActionExitCode(ActionReport.ExitCode.SUCCESS);
    }


    private boolean isApplicationOfThisType(String name, String type) {
        if (type == null)  {
            return true;
        }

        ApplicationName ApplicationName = ConfigBeansUtilities.getModule(name);
        Application app = null;
        if (ApplicationName instanceof Application) {
            app = (Application) ApplicationName;
        }
        if (app != null) {
            if (!app.isStandaloneModule()) {
                if (type.equals("ear")) {
                    return true;
                } else {
                    return false;
                }
            }
            for (Module module : app.getModule()) {
                final List<Engine> engineList = module.getEngines();
                for (Engine engine : engineList) {
                    if (engine.getSniffer().equals(type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
