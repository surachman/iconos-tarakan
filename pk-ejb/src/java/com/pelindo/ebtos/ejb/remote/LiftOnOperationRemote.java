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
public interface LiftOnOperationRemote {

    public void handleLiftOnConfirmLoadContainer(com.pelindo.ebtos.model.db.ServiceVessel serviceVessel, com.pelindo.ebtos.model.db.PlanningContLoad planningContLoad, com.pelindo.ebtos.model.db.Equipment tango, com.pelindo.ebtos.model.db.Equipment haulageTruck) throws com.pelindo.ebtos.exception.TimeSelectionNotValidException, com.pelindo.ebtos.exception.ContainerNotMovableException;

    public void handleCancelLiftOnConfirmLoadContainer(com.pelindo.ebtos.model.db.PlanningContLoad planningContLoad, com.pelindo.ebtos.model.db.Equipment tango, com.pelindo.ebtos.model.db.Equipment haulageTruck) throws com.pelindo.ebtos.exception.LocationNotAvailableException;

    public void handleLiftOnConfirmShiftingContainer(com.pelindo.ebtos.model.db.PlanningShiftDischarge planningShiftDischarge, com.pelindo.ebtos.model.db.ServiceShifting serviceShifting, com.pelindo.ebtos.model.db.Equipment tango, com.pelindo.ebtos.model.db.Equipment haulageTruck) throws com.pelindo.ebtos.exception.TimeSelectionNotValidException, com.pelindo.ebtos.exception.ContainerNotMovableException;

    public void handleCancelLiftOnConfirmShiftingContainer(com.pelindo.ebtos.model.db.PlanningShiftDischarge planningShiftDischarge, com.pelindo.ebtos.model.db.ServiceShifting serviceShifting, com.pelindo.ebtos.model.db.Equipment tango, com.pelindo.ebtos.model.db.Equipment haulageTruck) throws com.pelindo.ebtos.exception.LocationNotAvailableException;
    
}
