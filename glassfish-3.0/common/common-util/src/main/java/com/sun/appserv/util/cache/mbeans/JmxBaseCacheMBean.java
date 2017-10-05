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

/**
 * @Version $Id: JmxBaseCacheMBean.java,v 1.4 2005/12/25 04:25:22 tcfujii Exp $
 * Created on May 4, 2005 11:43 AM
 */

package com.sun.appserv.util.cache.mbeans;

/**
 * This interface defines the attributes exposed by the BaseCache MBean
 *
 * @author Krishnamohan Meduri (Krishna.Meduri@Sun.com)
 *
 */
public interface JmxBaseCacheMBean {

    /**
     * Returns a unique identifier for this MBean inside the domain
     */
    public String getName();

    /**
     * Returns maximum possible number of entries
     */
    public Integer getMaxEntries();

    /**
     * Returns threshold. This when reached, an overflow will occur
     */
    public Integer getThreshold();

    /**
     * Returns current number of buckets
     */
    public Integer getTableSize();

    /**
     * Returns current number of Entries
     */
    public Integer getEntryCount();

    /**
     * Return the number of cache hits
     */
    public Integer getHitCount();

    /**
     * Returns the number of cache misses
     */
    public Integer getMissCount();

    /**
     * Returns the number of entries that have been removed
     */
    public Integer getRemovalCount();

    /**
     * Returns the number of values that have been refreshed 
     * (replaced with a new value in an existing extry)
     */
    public Integer getRefreshCount();

    /**
     * Returns the number of times that an overflow has occurred
     */
    public Integer getOverflowCount();

    /**
     * Returns the number of times new entries have been added
     */
    public Integer getAddCount();
}
