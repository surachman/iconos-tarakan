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
public interface ReceivingOperationRemote {

    public void handleReceivingConfirm(com.pelindo.ebtos.model.db.ServiceReceiving serviceReceiving, com.pelindo.ebtos.model.db.PlanningContReceiving planningContReceiving, com.pelindo.ebtos.model.db.Equipment equipment) throws com.pelindo.ebtos.exception.TimeSelectionNotValidException, com.pelindo.ebtos.exception.LocationNotAvailableException;

    public void handleCancelReceivingConfirm(com.pelindo.ebtos.model.db.ServiceReceiving serviceReceiving, com.pelindo.ebtos.model.db.PlanningContReceiving planningContReceiving, com.pelindo.ebtos.model.db.Equipment equipment) throws com.pelindo.ebtos.exception.ContainerNotMovableException;
    
}
