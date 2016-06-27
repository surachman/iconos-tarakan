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
public class DeliveryConfirmUcLookupData implements Serializable, IMessageable {
    private Uncontainerized uncontainerized;
    private List<Object> equipmentCodes;
    private String messageCode;
    private String message;

    public DeliveryConfirmUcLookupData(){}

    public DeliveryConfirmUcLookupData(String code, String message) {
        this.messageCode = code;
        this.message = message;
    }

    public List<Object> getEquipmentCodes() {
        return equipmentCodes;
    }

    public void setEquipmentCodes(List<Object> equipmentCodes) {
        this.equipmentCodes = equipmentCodes;
    }

    public Uncontainerized getUncontainerized() {
        return uncontainerized;
    }

    public void setUncontainerized(Uncontainerized uncontainerized) {
        this.uncontainerized = uncontainerized;
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
