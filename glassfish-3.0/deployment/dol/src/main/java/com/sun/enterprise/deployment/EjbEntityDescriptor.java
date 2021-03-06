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

package com.sun.enterprise.deployment;

import com.sun.enterprise.util.LocalStringManagerImpl;
import com.sun.enterprise.deployment.util.DOLUtils;

import java.util.Iterator;
import java.util.logging.Logger;

/** 
 * This class contains deployment information for an EntityBean with
 * bean-managed persistence.
 * Subclasses contains additional information for EJB1.1/EJB2.0 CMP EntityBeans.
 *
 * @author Danny Coward
 * @author Sanjeev Krishnan
 * @author Vivek Nagar
 */

public class EjbEntityDescriptor extends EjbDescriptor {

    public static final String TYPE = "Entity";
    public static final String BEAN_PERSISTENCE = "Bean";
    public static final String CONTAINER_PERSISTENCE = "Container";
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    // EntityBean attributes
    protected String persistenceType;
    protected boolean isReentrant = false;
    protected String primaryKeyClassName;

    private static LocalStringManagerImpl localStrings =
	    new LocalStringManagerImpl(EjbEntityDescriptor.class);

               static Logger _logger = DOLUtils.getDefaultLogger();
 

    
    /**
     * The default constructor. 
     */
    public EjbEntityDescriptor() {
    }
    
    /** 
     * The copy constructor.
     */
    public EjbEntityDescriptor(EjbDescriptor other) {
	super(other);
	if (other instanceof EjbEntityDescriptor) {
	    EjbEntityDescriptor entity = (EjbEntityDescriptor) other;
	    this.persistenceType = entity.persistenceType;
	    this.isReentrant = entity.isReentrant;
	    this.primaryKeyClassName = entity.primaryKeyClassName;
	}   
    } 

    /**
     * Replace all pointers to the old EntityDescriptor in this app
     * with myself. Used when user switches from BMP to CMP or vice versa
     * in deploytool wizard.
     */
    public void replaceEntityDescriptor(EjbEntityDescriptor oldEntityDesc) 
    {
	// first replace in bundleDesc
	EjbBundleDescriptor bundle = oldEntityDesc.getEjbBundleDescriptor();
	bundle.replaceEjb(oldEntityDesc, this);

	// now replace in all EjbReferences to the old Ejb in this app
	Iterator refs = oldEntityDesc.getAllEjbReferencers().iterator();
	while ( refs.hasNext() ) {
	    EjbReferenceDescriptor ref = (EjbReferenceDescriptor)refs.next();
	    ref.setEjbDescriptor(this);
	}
    }
 

    /**
     * Gets the container transaction type for this entity bean. Entity 
     * beans always have CONTAINER_TRANSACTION_TYPE transaction type.
     */
    public String getTransactionType() {
	return super.transactionType;
    }
    
    /**
     * Sets the transaction type for this entity bean. 
     * Throws an illegal argument exception if this type is not 
     * CONTAINER_TRANSACTION_TYPE.
     */
    public void setTransactionType(String transactionType) {
	if (!CONTAINER_TRANSACTION_TYPE.equals(transactionType) 
		&& this.isBoundsChecking()) {
	    throw new IllegalArgumentException(localStrings.getLocalString(
	      "enterprise.deployment.exceptionentitybeancanonlyhavecntnrtxtype",
	      "Entity beans can only have Container transaction type. The type was being set to {0}", new Object[] {transactionType}));
	}
	super.transactionType = transactionType;
    }
    
    
    /**
     * Return true if this entity bean is reentrant, false else.
     */
    public boolean isReentrant() {
	return this.isReentrant;
    }
    
    public String getReentrant() {
	if (this.isReentrant()) {
	    return TRUE;
	} else {
	    return FALSE;
	}
    }
    
    public void setReentrant(String reentrantString) {
	if (TRUE.equalsIgnoreCase(reentrantString)) {
	    this.setReentrant(true);
	    return;
	}
	if (FALSE.equalsIgnoreCase(reentrantString)) {
	    this.setReentrant(false);
	    return;
	}
	if (this.isBoundsChecking()) {
	    throw new IllegalArgumentException(localStrings.getLocalString(
									   "enterprise.deployment.exceptionstringnotlegalvalue",
									  "{0} is not a legal value for entity reentrancy", new Object[] {reentrantString}));
	}   
    }
    
    /**
     * Sets the isReentrant flag for this bean.
     */
    public void setReentrant(boolean isReentrant) {
	this.isReentrant = isReentrant;

    }
    
    /**
     * Returns the persistence type for this entity bean. Defaults to BEAN_PERSISTENCE.
     */
    public String getPersistenceType() {
	if (this.persistenceType == null) {
	    this.persistenceType = BEAN_PERSISTENCE;
	}
	return this.persistenceType;
    }
    
    /**
     * Sets the persistence type for this entity bean. Allowable values are BEAN_PERSISTENCE 
     * or CONTAINER_PERSISTENCE, or else an IllegalArgumentException is thrown.
     */
    public void setPersistenceType(String persistenceType) {
	boolean isValidChange = (BEAN_PERSISTENCE.equals(persistenceType) || CONTAINER_PERSISTENCE.equals(persistenceType));
	if (isValidChange || !this.isBoundsChecking()) {
	    this.persistenceType = persistenceType;
	    
	} else {
	        //_logger.log(Level.FINE,"Warning " + persistenceType + " is not an allowed persistence type");
	    throw new IllegalArgumentException(localStrings.getLocalString(
									   "enterprise.deployment.exceptionpersistenceisnotallowedtype",
									   "{0} is not an allowed persistence type", new Object[] {persistenceType}));
	}
    }
    
    /**
     * Return the classname of the primary key for this bean, or the empty 
     * string if none has been set.
     */
    public String getPrimaryKeyClassName() {
	if (this.primaryKeyClassName == null) {
	    this.primaryKeyClassName = Object.class.getName();
	}
	return this.primaryKeyClassName;
    }
    
    /**
     * Set the classname of the primary key used by this bean. 
     */
    public void setPrimaryKeyClassName(String primaryKeyClassName) {
	this.primaryKeyClassName = primaryKeyClassName;

    }
    
    /**
     * Returns the type of this bean. EjbEntityDescriptor.TYPE
     */
    public String getType() {
	return TYPE;
    }
    
    /** 
     * Sets my type String.
     */
    public void setType(String type) {
	throw new IllegalArgumentException(localStrings.getLocalString(
	       "enterprise.deployment.exceptioncannotsettypeonentitybean",
	       "Cannon set type on an entity bean"));
    }
    
    /**
     * Return my formatted string representation.
     */
    public void print(StringBuffer toStringBuffer) {
	super.print(toStringBuffer);
	toStringBuffer.append("\n Entity descriptor");
	toStringBuffer.append("\n isReentrant ").append(isReentrant);
	toStringBuffer.append("\n primaryKeyClassName ").append(primaryKeyClassName);
	toStringBuffer.append("\n persistenceType ").append(persistenceType);
    }
}
