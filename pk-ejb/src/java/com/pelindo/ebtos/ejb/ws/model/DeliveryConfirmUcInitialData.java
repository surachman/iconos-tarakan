/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.ws.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author x42jr
 */
public class DeliveryConfirmUcInitialData implements Serializable, IMessageable {
    private String serverTime;
    private List<Object> equipmentCodes;
    private List<Object[]> operators;
    private String messageCode;
    private String message;

    public DeliveryConfirmUcInitialData(){}

    public DeliveryConfirmUcInitialData(String code, String message) {
        this.messageCode = code;
        this.message = message;
    }

    public List<Object> getEquipmentCodes() {
        return equipmentCodes;
    }

    public void setEquipmentCodes(List<Object> equipmentCodes) {
        this.equipmentCodes = equipmentCodes;
    }

    public List<Object[]> getOperators() {
        return operators;
    }

    public void setOperators(List<Object[]> operators) {
        this.operators = operators;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }
}
