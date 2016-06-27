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
public interface ContainerMovementOperationRemote {

    public void handleLiftOnCancelLoadingContainer(com.pelindo.ebtos.model.db.CancelLoadingService cancelLoadingService, com.pelindo.ebtos.model.db.ContainerMovementHistory containerMovementHistory, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentLift, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentHT) throws com.pelindo.ebtos.exception.ContainerNotMovableException, com.pelindo.ebtos.exception.LocationNotAvailableException, com.pelindo.ebtos.exception.TimeSelectionNotValidException;

    public void handleLiftOffCancelLoadingContainer(com.pelindo.ebtos.model.db.CancelLoadingService cancelLoadingService, com.pelindo.ebtos.model.db.ContainerMovementHistory containerMovementHistory, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentLift, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentHT, java.lang.String moveTo) throws com.pelindo.ebtos.exception.LocationNotAvailableException, com.pelindo.ebtos.exception.TimeSelectionNotValidException;

    public void handleLiftOffDischargeContainer(com.pelindo.ebtos.model.db.ServiceContDischarge serviceContDischarge, com.pelindo.ebtos.model.db.ContainerMovementHistory containerMovementHistory, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentLift, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentHT, java.lang.String moveTo) throws com.pelindo.ebtos.exception.TimeSelectionNotValidException, com.pelindo.ebtos.exception.LocationNotAvailableException;

    public void handleLiftOnDischargeContainer(com.pelindo.ebtos.model.db.ServiceContDischarge serviceContDischarge, com.pelindo.ebtos.model.db.ContainerMovementHistory containerMovementHistory, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentLift, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentHT, java.lang.String cfsOperationStatus) throws com.pelindo.ebtos.exception.LocationNotAvailableException, com.pelindo.ebtos.exception.ContainerNotMovableException, com.pelindo.ebtos.exception.TimeSelectionNotValidException;

    public void handleLiftOnLoadContainer(com.pelindo.ebtos.model.db.PlanningContLoad planningContLoad, com.pelindo.ebtos.model.db.ContainerMovementHistory containerMovementHistory, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentLift, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentHT, java.lang.String cfsOperationStatus) throws com.pelindo.ebtos.exception.TimeSelectionNotValidException, com.pelindo.ebtos.exception.LocationNotAvailableException, com.pelindo.ebtos.exception.ContainerNotMovableException;

    public void handleLiftOffLoadContainer(com.pelindo.ebtos.model.db.PlanningContLoad planningContLoad, com.pelindo.ebtos.model.db.ContainerMovementHistory containerMovementHistory, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentLift, com.pelindo.ebtos.model.db.EquipmentContainerMovement equipmentHT, java.lang.String moveTo) throws com.pelindo.ebtos.exception.TimeSelectionNotValidException, com.pelindo.ebtos.exception.LocationNotAvailableException;
    
}
