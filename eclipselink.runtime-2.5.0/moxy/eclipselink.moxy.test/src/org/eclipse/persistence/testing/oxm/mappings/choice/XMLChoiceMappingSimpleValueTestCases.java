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
package org.eclipse.persistence.testing.oxm.mappings.choice;

import org.eclipse.persistence.sessions.Project;
import org.eclipse.persistence.testing.oxm.mappings.XMLWithJSONMappingTestCases;

public class XMLChoiceMappingSimpleValueTestCases extends XMLWithJSONMappingTestCases {

  private final static String XML_RESOURCE = "org/eclipse/persistence/testing/oxm/mappings/choice/ChoiceSimpleValue.xml";
  private final static String JSON_RESOURCE = "org/eclipse/persistence/testing/oxm/mappings/choice/ChoiceSimpleValue.json";

  public XMLChoiceMappingSimpleValueTestCases(String name) throws Exception {
    super(name);
    setControlDocument(XML_RESOURCE);
    setControlJSON(JSON_RESOURCE);
    //setSession(SESSION_NAME);
    setProject(new EmployeeProject());
  }

  protected Object getControlObject() {
    Employee employee = new Employee();
    employee.name = "Jane Doe";
    
    employee.choice = "123 Fake Street";
    
    employee.phone = "123-4567"; 
    
    return employee;

  }
  
  public Project getNewProject(Project originalProject, ClassLoader classLoader) {
      Project project = super.getNewProject(originalProject, classLoader);
      //project.getDatasourceLogin().setPlatform(new SAXPlatform());
      
      return project;
  }  
  

}
