/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
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

package com.sun.enterprise.admin.cli;

import java.util.*;

/**
 * The environment variables for CLI commands.  An instance of this class
 * is passed to each command to give it access to environment variables.
 * Command implementations should access environment information from
 * this class rather than using System.getenv.  In multimode, the export
 * command may change environment variables in the instance of this class
 * that is shared by all commands.
 *
 * @author Bill Shannon
 */
public final class Environment {
    // XXX - should Environment just extend HashMap?

    public static final String AS_ADMIN_ENV_PREFIX = "AS_ADMIN_";

    private Map<String, String> env = new HashMap<String, String>();

    /**
     * Initialize the enviroment with all relevant system environment entries.
     */
    public Environment() {
        this(false);
    }

    /**
     * Constructor that ignores the system environment,
     * mostly used to enable repeatable tests.
     */
    public Environment(boolean ignoreEnvironment) {
        if (ignoreEnvironment)
            return;
        // initialize it with all relevant system environment entries
        for (Map.Entry<String, String> e : System.getenv().entrySet()) {
            if (e.getKey().startsWith(AS_ADMIN_ENV_PREFIX)) {
                env.put(e.getKey().toUpperCase(Locale.ENGLISH), e.getValue());
            }
        }
    }

    /**
     * Return the value of the environment entry corresponding
     * to the named option.
     *
     * @param name the option name
     * @return the value of the corresponding environment entry
     */
    public boolean getBooleanOption(String name) {
        return Boolean.parseBoolean(env.get(optionToEnv(name)));
    }

    /**
     * Return the value of the environment entry corresponding
     * to the named option.
     *
     * @param name the option name
     * @return the value of the corresponding environment entry
     */
    public String getStringOption(String name) {
        return env.get(optionToEnv(name));
    }

    /**
     * Is there an environment entry corresponding to the named option?
     *
     * @param name the option name
     * @return true if there's a corresponding environment entry
     */
    public boolean hasOption(String name) {
        return env.containsKey(optionToEnv(name));
    }

    /**
     * Get the named environment entry.
     *
     * @param name the name of the environment entry
     * @return the value of the entry, or null if no such entry
     */
    public String get(String name) {
        return env.get(name);
    }

    /**
     * Set the named environment entry to the specified value.
     *
     * @param name the environment entry name
     * @param value the value
     * @return the previous value of the entry
     */
    public String put(String name, String value) {
        return env.put(name, value);
    }

    /**
     * Remove the name environment entry.
     *
     * @param name the environment entry name
     */
    public void remove(String name) {
        env.remove(name);
    }

    /**
     * Set the environment entry corresponding to the named option
     * to the specified value.
     *
     * @param name the option name
     * @param value the value
     * @return the previous value of the entry
     */
    public String putOption(String name, String value) {
        return env.put(optionToEnv(name), value);
    }

    /**
     * Return a set of all the entries, just like a Map does.
     */
    public Set<Map.Entry<String, String>> entrySet() {
        return env.entrySet();
    }

    /**
     * Convert an option name (e.g., "host")
     * to an environment variable name (e.g., AS_ADMIN_HOST).
     *
     * @param name the option name
     * @return the environment variable name
     */
    private String optionToEnv(String name) {
        return AS_ADMIN_ENV_PREFIX +
            name.replace('-', '_').toUpperCase(Locale.ENGLISH);
    }
}
