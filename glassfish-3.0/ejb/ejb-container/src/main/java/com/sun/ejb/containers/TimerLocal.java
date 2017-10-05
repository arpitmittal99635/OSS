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

package com.sun.ejb.containers;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.TimerConfig;
import javax.ejb.Local;

/**
 * Local view of the persistent representation of an EJB timer.
 *
 * @author Kenneth Saks
 * @author Marina Vatkina
 */
@Local
public interface TimerLocal {

    /**
     * Cancel timer.
     */
    void cancel(TimerPrimaryKey timerId) throws Exception;

    void cancelTimers(Collection<TimerState> timers);

    TimerState createTimer(String timerId,  
                      long containerId, String ownerId,
                      Object timedObjectPrimaryKey, 
                      Date initialExpiration, long intervalDuration, 
                      TimerSchedule schedule, TimerConfig timerConfig) 
                      throws CreateException;

    TimerState findTimer(TimerPrimaryKey timerId);

    void remove(TimerPrimaryKey timerId);

    void remove(Set<TimerPrimaryKey> timerIds);

    // 
    // Queries returning Timer Ids (TimerPrimaryKey)
    //

    Set findTimerIdsByContainer(long containerId);
    Set findActiveTimerIdsByContainer(long containerId);
    Set findCancelledTimerIdsByContainer(long containerId);    

    Set findTimerIdsOwnedByThisServerByContainer(long containerId);
    Set findActiveTimerIdsOwnedByThisServerByContainer(long containerId);
    Set findCancelledTimerIdsOwnedByThisServerByContainer(long containerId);

    Set findTimerIdsOwnedByThisServer(); 
    Set findActiveTimerIdsOwnedByThisServer(); 
    Set findCancelledTimerIdsOwnedByThisServer();

    Set findTimerIdsOwnedBy(String owner);
    Set findActiveTimerIdsOwnedBy(String owner);
    Set findCancelledTimerIdsOwnedBy(String owner);


    //
    // Queries returning Timer local objects
    //

    Set findTimersByContainer(long containerId);
    Set findActiveTimersByContainer(long containerId);
    Set findCancelledTimersByContainer(long containerId);    

    Set findTimersOwnedByThisServerByContainer(long containerId);
    Set findActiveTimersOwnedByThisServerByContainer(long containerId);
    Set findCancelledTimersOwnedByThisServerByContainer(long containerId);

    Set findTimersOwnedByThisServer(); 
    Set findActiveTimersOwnedByThisServer(); 
    Set findCancelledTimersOwnedByThisServer();

    Set findTimersOwnedBy(String owner);
    Set findActiveTimersOwnedBy(String owner);
    Set findCancelledTimersOwnedBy(String owner);


    //
    // Queries returning counts
    //

    int countTimersByContainer(long containerId);
    int countActiveTimersByContainer(long containerId);
    int countCancelledTimersByContainer(long containerId);    

    int countTimersOwnedByThisServerByContainer(long containerId);
    int countActiveTimersOwnedByThisServerByContainer(long containerId);
    int countCancelledTimersOwnedByThisServerByContainer(long containerId);

    int countTimersOwnedByThisServer(); 
    int countActiveTimersOwnedByThisServer(); 
    int countCancelledTimersOwnedByThisServer();

    int countTimersOwnedBy(String owner);
    int countActiveTimersOwnedBy(String owner);
    int countCancelledTimersOwnedBy(String owner);

    String[] countTimersOwnedByServerIds(String[] serverIds);


    // Perform health check on timer database
    boolean checkStatus(String resourceJndiName, boolean checkDatabase);

    // Migrate timers from one server instance to another via bulk update
    int migrateTimers(String fromOwnerId, String toOwnerId);

    // Called from the TimerWelcomeServlet
    Set findActiveNonPersistentTimersOwnedByThisServer();
}
