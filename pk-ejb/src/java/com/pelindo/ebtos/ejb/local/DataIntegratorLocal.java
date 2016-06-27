/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.local;

import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.LockType;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface DataIntegratorLocal {

    @Lock(value = LockType.READ)
    String gerInfo();

    @Lock(value = LockType.READ)
    String getLastProgrammaticTimeout();

    @Lock(value = LockType.READ)
    boolean isAgentRunning(String timerName);

    Boolean isSimpatAvailable();

    void syncMasterCustomer();

    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void createFetchSchedule();
}
