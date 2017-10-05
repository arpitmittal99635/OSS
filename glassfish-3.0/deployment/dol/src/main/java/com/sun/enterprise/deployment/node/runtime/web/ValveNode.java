/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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

package com.sun.enterprise.deployment.node.runtime.web;

import com.sun.enterprise.deployment.node.XMLElement;
import com.sun.enterprise.deployment.runtime.web.Valve;
import com.sun.enterprise.deployment.xml.RuntimeTagNames;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Node representing a valve tag.
 */
public class ValveNode extends WebRuntimeNode {

    public ValveNode() {
        registerElementHandler(new XMLElement(RuntimeTagNames.PROPERTY),
                WebPropertyNode.class, "addWebProperty");
    }

    @Override
    protected boolean setAttributeValue(XMLElement elementName,
                                        XMLElement attributeName,
                                        String value) {
        Valve descriptor = (Valve) getRuntimeDescriptor();
        if (descriptor == null) {
            throw new RuntimeException("Trying to set values on a null descriptor");
        }
        if (attributeName.getQName().equals(RuntimeTagNames.NAME)) {
            descriptor.setAttributeValue(Valve.NAME, value);
            return true;
        } else if (attributeName.getQName().equals(RuntimeTagNames.CLASS_NAME)) {
            descriptor.setAttributeValue(Valve.CLASS_NAME, value);
            return true;
        }
        return false;
    }

    /**
     * Writes the descriptor class to a DOM tree and returns it
     *
     * @param parent node for the DOM tree
     * @param node name
     * @param the descriptor to write
     * @return the DOM tree top node
     */
    public Node writeDescriptor(Node parent, String nodeName,
                                Valve descriptor) {

        Element valve = (Element) super.writeDescriptor(
            parent, nodeName, descriptor);

        WebPropertyNode wpn = new WebPropertyNode();

        // sub-element description?
        appendTextChild(valve, RuntimeTagNames.DESCRIPTION, descriptor.getDescription());

        // sub-element property*
        wpn.writeDescriptor(valve, RuntimeTagNames.PROPERTY,
                            descriptor.getWebProperty());

        // attributes classname and name
        setAttribute(valve, RuntimeTagNames.NAME,
            (String) descriptor.getAttributeValue(Valve.NAME));
        setAttribute(valve, RuntimeTagNames.CLASS_NAME,
            (String) descriptor.getAttributeValue(Valve.CLASS_NAME));

        return valve;
    }

    /**
     * write the descriptor class to a DOM tree and return it
     *
     * @param parent node for the DOM tree
     * @param node name for the descriptor
     * @param the array of descriptors to write
     */
    public void writeDescriptor(Node parent, String nodeName, Valve[] valves) {
        if (valves == null) {
            return;
        }
        for (int i = 0; i < valves.length; i++) {
            writeDescriptor(parent, nodeName, valves[i]);
        }
    }
}
