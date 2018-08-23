/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     rbarkhouse - 2.3.3 - initial implementation
 ******************************************************************************/
@XmlSchema(
    elementFormDefault=XmlNsForm.QUALIFIED,
    namespace="http://www.example.com/FOO",
    xmlns={
        @XmlNs(prefix="foo", namespaceURI="http://www.example.com/FOO"),
        @XmlNs(prefix="", namespaceURI="http://www.example.com/BAR")
    }
)
package org.eclipse.persistence.testing.jaxb.xmlschema.defaultns.emptyprefix;
 
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;