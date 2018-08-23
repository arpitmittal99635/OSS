/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Blaise Doughan - 2.3.3 - initial implementation
 ******************************************************************************/
package org.eclipse.persistence.testing.jaxb.defaultvalue;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DefaultValueTestSuite extends TestSuite {

	public static Test suite() {
        TestSuite suite = new TestSuite("Default Value Test Suite");
        suite.addTestSuite(DefaultValueTestCases.class);
        // suite.addTestSuite(DefaultValueXmlValueTestCases.class);
        // suite.addTestSuite(DefaultValueXmlValueEnumTestCases.class);
        return suite;
    }

}