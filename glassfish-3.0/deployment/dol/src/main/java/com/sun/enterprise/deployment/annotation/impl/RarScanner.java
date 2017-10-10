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

package com.sun.enterprise.deployment.annotation.impl;

import com.sun.enterprise.deployment.ConnectorDescriptor;

import java.io.IOException;
import java.io.File;
import java.io.FileFilter;
import java.util.logging.Level;

import org.glassfish.apf.impl.AnnotationUtils;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.annotations.Scoped;
import org.jvnet.hk2.component.PerLookup;


@Service(name="rar")
@Scoped(PerLookup.class)
public class RarScanner extends ModuleScanner<ConnectorDescriptor>{

    public void process(File archiveFile, ConnectorDescriptor desc,
        ClassLoader classLoader) throws IOException {
        if (AnnotationUtils.getLogger().isLoggable(Level.FINE)) {
            AnnotationUtils.getLogger().fine("archiveFile is " + archiveFile);
            AnnotationUtils.getLogger().fine("classLoader is " + classLoader);
        }
        this.archiveFile = archiveFile;
        this.classLoader = classLoader;
        if (archiveFile.isDirectory()) {
            addScanDirectory(archiveFile);

            // add top level jars for scanning
            File[] jarFiles = archiveFile.listFiles(new FileFilter() {
                 public boolean accept(File pathname) {
                     return (pathname.isFile() &&
                            pathname.getAbsolutePath().endsWith(".jar"));
                 }
            });

            if (jarFiles != null && jarFiles.length > 0) {
                for (File jarFile : jarFiles) {
                    addScanJar(jarFile);
                }
            }
        }else{
            AnnotationUtils.getLogger().fine("RARScanner : not a directory : " + archiveFile.getName());
        }
    }
}