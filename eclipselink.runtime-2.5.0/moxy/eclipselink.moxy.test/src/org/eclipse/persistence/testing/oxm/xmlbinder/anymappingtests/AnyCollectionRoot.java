/*******************************************************************************
 * Copyright (c) 1998, 2013 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 ******************************************************************************/  
package org.eclipse.persistence.testing.oxm.xmlbinder.anymappingtests;

import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Element;

import org.eclipse.persistence.oxm.XMLRoot;
import org.eclipse.persistence.testing.oxm.mappings.XMLMappingTestCases;

/**
 *  @version $Header: Root.java 31-jan-2007.15:08:43 mmacivor Exp $
 *  @author  mmacivor
 *  @since   release specific (what release of product did this appear in)
 */
public class AnyCollectionRoot {
    private Vector any = new Vector();
    private String directMapping;

    public Vector getAny() {
        return any;
    }

    public void setAny(Vector a) {
        any = a;
    }

    public boolean equals(Object object) {
        if (object instanceof AnyCollectionRoot) {
            String directMapping1 = directMapping;
            String directMapping2 = ((AnyCollectionRoot)object).getDirectMapping();
            if ((directMapping1 == null) && (directMapping2 != null)) {
                return false;
            }
            if ((directMapping2 == null) && (directMapping1 != null)) {
                return false;
            }
            if (directMapping1 != null) {
                if (!directMapping1.equals(directMapping2)) {
                    return false;
                }
            }

            Vector collection1 = any;
            Vector collection2 = ((AnyCollectionRoot)object).getAny();
            if ((collection1 == null) && (collection2 == null)) {
                return true;
            } else if ((collection1 == null) && (collection2.size() == 0)) {
                return true;
            } else if ((collection2 == null) && (collection1.size() == 0)) {
                return true;
            } else if ((collection1 == null) && (collection2.size() > 0)) {
                return false;
            } else if ((collection2 == null) && (collection1.size() > 0)) {
                return false;
            } else if (any.size() != ((AnyCollectionRoot)object).getAny().size()) {
                return false;
            } else {
                Iterator values1 = any.iterator();
                Iterator values2 = ((AnyCollectionRoot)object).getAny().iterator();

                while (values1.hasNext()) {
                    Object value1 = values1.next();
                    Object value2 = values2.next();
                    if ((value1 instanceof XMLRoot) && (value2 instanceof XMLRoot)) {
                        XMLMappingTestCases.compareXMLRootObjects((XMLRoot)value1, (XMLRoot)value2);
                    } else if ((value1 instanceof org.w3c.dom.Element) && !(value2 instanceof org.w3c.dom.Element)) {
                        return false;
                    } else if (!(value1 instanceof org.w3c.dom.Element) && (value2 instanceof org.w3c.dom.Element)) {
                        return false;
                    } else if ((value1 instanceof org.w3c.dom.Element) && (value2 instanceof org.w3c.dom.Element)) {
                        Element elem1 = (Element)value1;
                        Element elem2 = (Element)value2;
                        if(!(elem1.getLocalName().equals(elem2.getLocalName()))) {
                            return false;
                        }
                    } else if (!(value1.equals(value2))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String value = "Root:\n ";
        if (any == null) {
            return value;
        }
        for (int i = 0; i < any.size(); i++) {
            value += ("==> " + any.elementAt(i) + "\n");
        }
        return value;
    }

    public void setDirectMapping(String directMapping) {
        this.directMapping = directMapping;
    }

    public String getDirectMapping() {
        return directMapping;
    }
}