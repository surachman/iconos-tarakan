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
public interface LiftOffOperationRemote {

    public void handleLiftOffConfirmDischargeContainer(com.pelindo.ebtos.model.db.ServiceContDischarge serviceContDischarge, com.pelindo.ebtos.model.db.Equipment tango) throws com.pelindo.ebtos.exception.LocationNotAvailableException, com.pelindo.ebtos.exception.TimeSelectionNotValidException;

    public void handleLiftOffConfirmCancelLoadingContainer(com.pelindo.ebtos.model.db.CancelLoadingService cancelLoadingService, com.pelindo.ebtos.model.db.Equipment tango) throws com.pelindo.ebtos.exception.LocationNotAvailableException, com.pelindo.ebtos.exception.TimeSelectionNotValidException;

    public void handleLiftOffConfirmShiftingContainer(com.pelindo.ebtos.model.db.ServiceShifting serviceShifting, com.pelindo.ebtos.model.db.PlanningShiftDischarge planningShiftDischarge, com.pelindo.ebtos.model.db.Equipment tango) throws com.pelindo.ebtos.exception.LocationNotAvailableException, com.pelindo.ebtos.exception.TimeSelectionNotValidException;

    public void handleLiftOffConfirmTranshipmentContainer(com.pelindo.ebtos.model.db.ServiceContTranshipment serviceContTranshipment, com.pelindo.ebtos.model.db.Equipment tango) throws com.pelindo.ebtos.exception.LocationNotAvailableException, com.pelindo.ebtos.exception.TimeSelectionNotValidException;

    public void handleCancelLiftOffConfirmShiftingContainer(com.pelindo.ebtos.model.db.ServiceShifting serviceShifting, com.pelindo.ebtos.model.db.PlanningShiftDischarge planningShiftDischarge, com.pelindo.ebtos.model.db.Equipment tango) throws com.pelindo.ebtos.exception.ContainerNotMovableException;

    public void handleCancelLiftOffConfirmCancelLoadingContainer(com.pelindo.ebtos.model.db.CancelLoadingService cancelLoadingService, com.pelindo.ebtos.model.db.Equipment tango) throws com.pelindo.ebtos.exception.ContainerNotMovableException;

    public void handleCancelLiftOffConfirmDischargeContainer(com.pelindo.ebtos.model.db.ServiceContDischarge serviceContDischarge, com.pelindo.ebtos.model.db.Equipment tango) throws com.pelindo.ebtos.exception.ContainerNotMovableException;

    public void handleCancelLiftOffConfirmTranshipmentContainer(com.pelindo.ebtos.model.db.ServiceContTranshipment serviceContTranshipment, com.pelindo.ebtos.model.db.Equipment tango) throws com.pelindo.ebtos.exception.ContainerNotMovableException;
    
}
