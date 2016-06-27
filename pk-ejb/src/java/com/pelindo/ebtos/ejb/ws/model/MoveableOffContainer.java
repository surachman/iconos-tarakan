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
public class MoveableOffContainer implements Serializable, IMessageable {
    private Container container;
    private List<String> destinations;
    private List<String> operations;
    private String messageCode;
    private String message;

    public MoveableOffContainer() {}

    public MoveableOffContainer(String code, String message) {
        this.messageCode = code;
        this.message = message;
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

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

    public List<String> getOperations() {
        return operations;
    }

    public void setOperations(List<String> operations) {
        this.operations = operations;
    }
}
