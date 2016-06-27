/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.remote;

import javax.ejb.Remote;

/**
 *
 * @author R. Seno Anggoro A
 */
@Remote
public interface DischargeConfirmOperationRemote {

    public void handleDischargeConfirmContainer(com.pelindo.ebtos.model.db.ServiceContDischarge serviceContDischarge, com.pelindo.ebtos.model.db.Equipment crane, com.pelindo.ebtos.model.db.Equipment ht) throws com.pelindo.ebtos.exception.TimeSelectionNotValidException;

    public void handleDischargeConfirmCancelLoadingContainer(com.pelindo.ebtos.model.db.CancelLoadingService cancelLoadingService, com.pelindo.ebtos.model.db.Equipment crane, com.pelindo.ebtos.model.db.Equipment ht) throws com.pelindo.ebtos.exception.TimeSelectionNotValidException;

    public void handleDischargeConfirmShiftingContainer(com.pelindo.ebtos.model.db.ServiceShifting serviceShifting, com.pelindo.ebtos.model.db.Equipment crane, com.pelindo.ebtos.model.db.Equipment ht) throws com.pelindo.ebtos.exception.TimeSelectionNotValidException;
    
}
