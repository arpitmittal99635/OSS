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
 *     Denise Smith  February 9, 2009 - 2.1
 ******************************************************************************/
package org.eclipse.persistence.testing.jaxb.typemappinginfo.simple;

public class Person {
   
	public Person(){
		
	}
	
	public boolean equals(Object obj){
		return obj instanceof Person;			
	}
}
