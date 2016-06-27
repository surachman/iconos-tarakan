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
public class ContainerMovementOnConfirmInitialData implements Serializable, IMessageable {
    private String serverTime;
    private List<Object> ttCodes;
    private List<Object> htCodes;
    private List<Object[]> operators;
    private String messageCode;
    private String message;

    public ContainerMovementOnConfirmInitialData(){}

    public ContainerMovementOnConfirmInitialData(String code, String message) {
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

    public List<Object> getHtCodes() {
        return htCodes;
    }

    public void setHtCodes(List<Object> htCodes) {
        this.htCodes = htCodes;
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

    public List<Object> getTtCodes() {
        return ttCodes;
    }

    public void setTtCodes(List<Object> ttCodes) {
        this.ttCodes = ttCodes;
    }
}
