/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.ws.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author R Seno Anggoro
 */
public class ReshippableContainer implements Serializable, IMessageable {
    private Container container;
    private Boolean isAtHeadTruck;
    private String messageCode;
    private String message;

    public ReshippableContainer() {}

    public ReshippableContainer(String code, String message) {
        this.messageCode = code;
        this.message = message;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Boolean getIsAtHeadTruck() {
        return isAtHeadTruck;
    }

    public void setIsAtHeadTruck(Boolean isAtHeadTruck) {
        this.isAtHeadTruck = isAtHeadTruck;
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
