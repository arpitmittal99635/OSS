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
 *     Matt MacIvor -  January, 2010
 ******************************************************************************/  
package org.eclipse.persistence.testing.jaxb.typemappinginfo;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.eclipse.persistence.jaxb.TypeMappingInfo;
import org.eclipse.persistence.jaxb.TypeMappingInfo.ElementScope;
import org.w3c.dom.Document;

public class RootLevelByteArrayNullContentTestCases extends TypeMappingInfoWithJSONTestCases {
    protected final static String XML_RESOURCE = "org/eclipse/persistence/testing/jaxb/typemappinginfo/byteArrayMtomNull.xml";
    protected final static String JSON_RESOURCE = "org/eclipse/persistence/testing/jaxb/typemappinginfo/byteArrayMtomNull.json";
    
    protected MyAttachmentMarshaller attachmentMarshaller;
    
    public RootLevelByteArrayNullContentTestCases(String name) throws Exception {
        super(name);
        init();
    }
    
    protected TypeMappingInfo[] getTypeMappingInfos()throws Exception {
        if(typeMappingInfos == null) {
            typeMappingInfos = new TypeMappingInfo[1];
            TypeMappingInfo tpi = new TypeMappingInfo();
            tpi.setXmlTagName(new QName("someUri","testTagname"));      
            tpi.setElementScope(ElementScope.Global);
                
            tpi.setType(byte[].class);         
            typeMappingInfos[0] = tpi;          
        }
        return typeMappingInfos;        
    }
    
    public void init() throws Exception {
        setControlDocument(XML_RESOURCE);
        setControlJSON(JSON_RESOURCE);
        setupParser();
    
        setTypeMappingInfos(getTypeMappingInfos());
        this.attachmentMarshaller = new MyAttachmentMarshaller();
        jaxbUnmarshaller.setAttachmentUnmarshaller(new MyAttachmentUnmarshaller());
        jaxbMarshaller.setAttachmentMarshaller(this.attachmentMarshaller);
        
        byte[] bytes = null;
        MyAttachmentMarshaller.attachments.put(MyAttachmentUnmarshaller.ATTACHMENT_TEST_ID, bytes);
    }   
   
    protected Object getControlObject() {
        
        QName qname = new QName("someUri", "testTagName");
        JAXBElement jaxbElement = new JAXBElement(qname, byte[].class, null);

        return jaxbElement;
    }

    protected String getNoXsiTypeControlResourceName() {
        return XML_RESOURCE;
    }  
    public Map<String, InputStream> getControlSchemaFiles(){                       
        InputStream instream = ClassLoader.getSystemResourceAsStream("org/eclipse/persistence/testing/jaxb/typemappinginfo/byteArray.xsd");
        
        Map<String, InputStream> controlSchema = new HashMap<String, InputStream>();
        controlSchema.put("someUri", instream);
        return controlSchema;
    }
  
}
