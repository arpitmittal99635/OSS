/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the license at
 * https://glassfish.dev.java.net/public/CDDLv1.0.html or
 * glassfish/bootstrap/legal/CDDLv1.0.txt.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at glassfish/bootstrap/legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.enterprise.v3.admin;

import com.sun.enterprise.module.common_impl.Tokenizer;
import com.sun.enterprise.universal.collections.ManifestUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.glassfish.api.ActionReport;
import org.glassfish.api.I18n;
import org.glassfish.api.Param;
import org.glassfish.api.admin.AdminCommand;
import org.glassfish.api.admin.AdminCommandContext;
import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.annotations.Scoped;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Habitat;
import org.jvnet.hk2.component.PerLookup;

/**
 * Create data structures that describe the command.
 *
 * @author Jerome Dochez
 * 
 */
@Service(name="list-descriptors", metadata="mode=debug")
@Scoped(PerLookup.class)
@I18n("list.commands")

public class ListCommandDescriptorsCommand implements AdminCommand {
    @Inject
    Habitat habitat;

    public void execute(AdminCommandContext context) {
        setAdminCommands();
        sort();
        
        for (AdminCommand cmd : adminCmds) {
            cliCmds.add(reflect(cmd));
        }
        ActionReport report = context.getActionReport();
        String s = "ALL COMMANDS: " + EOL;
        for(CLICommand cli : cliCmds) {
            s += cli.toString() + EOL;
        }
        report.setMessage(s);
        report.setActionExitCode(ActionReport.ExitCode.SUCCESS);
    }
    
    private CLICommand reflect(AdminCommand cmd) {
        CLICommand cliCmd = new CLICommand(cmd);

        for (Field f : cmd.getClass().getDeclaredFields()) {
            final Param param = f.getAnnotation(Param.class);
            if (param==null)
                continue;
            
            Option option = new Option(param, f);
            cliCmd.options.add(option);
        }
        return cliCmd;
    }
/*
 private String generateUsageText(AdminCommand command) {
        StringBuffer usageText = new StringBuffer();
        usageText.append("Usage: ");
        usageText.append(command.getClass().getAnnotation(Service.class).name());
        usageText.append(" ");
        StringBuffer operand = new StringBuffer();
            final String paramName = getParamName(param, f);
            final boolean optional = param.optional();
            final Class<?> ftype = f.getType();
            Object fvalue = null;
            String fvalueString = null;
            try {
                f.setAccessible(true);
                fvalue = f.get(command);
                if(fvalue != null)
                    fvalueString = fvalue.toString();
            }
            catch(Exception e) {
                // just leave it as null...
            }
            // this is a param.
            if (param.primary()) {
                if (optional) {
                    operand.append("[").append(paramName).append("] ");
                }
                else {
                    operand.append(paramName).append(" ");
                }
                continue;
            }
            if (optional) { usageText.append("["); }
            usageText.append("--").append(paramName);

            if (ok(param.defaultValue())) {
                usageText.append("=").append(param.defaultValue());
                if(optional) { usageText.append("] "); }
                else { usageText.append(" "); }
            }
            else if (ftype.isAssignableFrom(String.class)) {
                    //check if there is a default value assigned
                if (ok(fvalueString)) {
                    usageText.append("=").append(fvalueString);
                    if (optional) { usageText.append("] "); }
                    else { usageText.append(" "); }
                } else {
                    usageText.append("=").append(paramName);
                    if (optional) { usageText.append("] "); }
                    else { usageText.append(" "); }
                }
            }
            else if (ftype.isAssignableFrom(Boolean.class)) {
                // note: There is no defaultValue for this param.  It might
                // hava  value -- but we don't care -- it isn't an official
                // default value.
                    usageText.append("=").append("true|false");
                    if (optional) { usageText.append("] "); }
                    else { usageText.append(" "); }
            }
            else {
                usageText.append("=").append(paramName);
                if (optional) { usageText.append("] "); }
                else { usageText.append(" "); }
            }
        }//for
        usageText.append(operand);
        return usageText.toString();
    }

  */  
    
    
    
    private void setAdminCommands() {
        adminCmds = new ArrayList<AdminCommand>();
        for (AdminCommand command : habitat.getAllByContract(AdminCommand.class)) {
            adminCmds.add(command);
        }
    }

    private void sort() {
        Collections.sort(adminCmds, new Comparator<AdminCommand>() {
            public int compare(AdminCommand c1, AdminCommand c2) {
                String name1 = c1.getClass().getAnnotation(Service.class).name();
                String name2 = c2.getClass().getAnnotation(Service.class).name();
                return name1.compareTo(name2);
            }
        }
        );
    }
    
    private static boolean ok(String s) {
        return s != null && s.length() > 0 && !s.equals("null");
    }
    
    private List<AdminCommand> adminCmds;
    private List<CLICommand> cliCmds = new LinkedList<CLICommand>();
    private final static String EOL = ManifestUtils.EOL_TOKEN;
    
    private static class CLICommand {
        CLICommand(AdminCommand adminCommand) {
            this.adminCommand = adminCommand;
            name = adminCommand.getClass().getAnnotation(Service.class).name();
        }

        @Override
        public String toString() {
            String s = "CLI Command:" +
                    " name=" + name +
                    " class=" + adminCommand.getClass().getName();
            
            for(Option opt : options) {
                s += opt.toString() + EOL;
            }
            return s;
        }

        AdminCommand adminCommand;
        String name;
        List<Option> options = new LinkedList<Option>();
    }
    private static class Option {
        Option(Param p, Field f) {
            final Class<?> ftype = f.getType();
            name = p.name();
            
            if(!ok(name)) {
                name = f.getName();
            }

            required = !p.optional();
            operand = p.primary();
            defaultValue = p.defaultValue();
            type = ftype;
        }
        @Override
        public String toString() {
            String s = "   Option:" + 
                    " name=" + name + 
                    " required=" + required +
                    " operand=" + operand +
                    " defaultValue=" + defaultValue +
                    " type=" + type.getName();
            return s;
        }

        private boolean required;
        private boolean operand;
        private String name;
        private String defaultValue;
        private Class<?> type;
    }
}
