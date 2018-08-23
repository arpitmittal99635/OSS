/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Blaise Doughan - 2.4.1 - initial implementation
 ******************************************************************************/
package org.eclipse.persistence.testing.jaxb.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.TreeSet;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;
import org.eclipse.persistence.testing.jaxb.JAXBWithJSONTestCases;

public class NavigableSetHolderTestCases extends JAXBWithJSONTestCases {

    private final static String XML_RESOURCE = "org/eclipse/persistence/testing/jaxb/collections/containertype2.xml";
    private final static String JSON_RESOURCE = "org/eclipse/persistence/testing/jaxb/collections/containertype2.json";

    public NavigableSetHolderTestCases(String name) throws Exception {
        super(name);
        setControlDocument(XML_RESOURCE);
        setControlJSON(JSON_RESOURCE);
        Class[] classes = new Class[1];
        classes[0] = NavigableSetHolder.class;
        setClasses(classes);
        jaxbMarshaller.setProperty(MarshallerProperties.JSON_ATTRIBUTE_PREFIX, "@");
        jaxbUnmarshaller.setProperty(UnmarshallerProperties.JSON_ATTRIBUTE_PREFIX, "@");
        jaxbMarshaller.setProperty(MarshallerProperties.JSON_MARSHAL_EMPTY_COLLECTIONS, Boolean.FALSE);
    }

    @Override
    protected NavigableSetHolder getControlObject() {
        NavigableSetHolder ch = new NavigableSetHolder();
        ch.setCollection1(newContainer());
        ch.getCollection1().add(10);

        ch.setCollection2(newContainer());
        ch.getCollection2().add("one");

        ch.setCollection3(newContainer());
        ch.getCollection3().add(20);

        ch.setCollection5(newContainer());
        ch.getCollection5().add(new NavigableSetHolder());

        ReferencedObject ref2 = new ReferencedObject();
        ref2.id = "2";
        ch.setCollection7(newContainer());
        ch.getCollection7().add(ref2);
        ch.getReferenced().add(ref2);

        ReferencedObject ref4 = new ReferencedObject();
        ref4.id = "4";
        ch.setCollection8(newContainer());
        ch.getCollection8().add(ref4);
        ch.getReferenced().add(ref4);

        ch.setCollection9(newContainer());
        ch.getCollection9().add(CoinEnum.PENNY);

        ch.setCollection12(newContainer());
        ch.getCollection12().add("abc");

        ch.setCollection13(newContainer());
        ch.getCollection13().add(123);

        ch.collection14 = newContainer();
        ch.collection14.add(123);

        return ch;
    }

    private NavigableSet newContainer() {
        return new TreeSet();
    }

}