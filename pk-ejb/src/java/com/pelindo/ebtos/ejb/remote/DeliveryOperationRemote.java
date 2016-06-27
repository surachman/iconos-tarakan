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
public interface DeliveryOperationRemote {

    public void handleDeliveryConfirm(com.pelindo.ebtos.model.db.ServiceContDischarge serviceContDischarge, com.pelindo.ebtos.model.db.Equipment equipment) throws com.pelindo.ebtos.exception.TimeSelectionNotValidException, com.pelindo.ebtos.exception.ContainerNotMovableException;

    public void handleDeliveryConfirmCancelLoading(com.pelindo.ebtos.model.db.CancelLoadingService cancelLoadingService, com.pelindo.ebtos.model.db.Equipment equipment) throws com.pelindo.ebtos.exception.ContainerNotMovableException, com.pelindo.ebtos.exception.TimeSelectionNotValidException;

    public void handleCancelDeliveryConfirm(com.pelindo.ebtos.model.db.ServiceContDischarge serviceContDischarge, com.pelindo.ebtos.model.db.Equipment equipment) throws com.pelindo.ebtos.exception.LocationNotAvailableException;

    public void handleCancelDeliveryConfirmCancelLoading(com.pelindo.ebtos.model.db.CancelLoadingService cancelLoadingService, com.pelindo.ebtos.model.db.Equipment equipment) throws com.pelindo.ebtos.exception.LocationNotAvailableException;
    
}
