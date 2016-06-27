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
public class ShiftingConfirmInitialData implements Serializable, IMessageable {
    private List<Object[]> vessels;
    private List<Object[]> operators;
    private List<Object> ccCodes;
    private List<Object> statusses;
    private List<Object> sizes;
    private String serverTime;
    private String messageCode;
    private String message;

    public ShiftingConfirmInitialData(){}

    public ShiftingConfirmInitialData(String messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    public List<Object> getCcCodes() {
        return ccCodes;
    }

    public void setCcCodes(List<Object> ccCodes) {
        this.ccCodes = ccCodes;
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

    public List<Object> getSizes() {
        return sizes;
    }

    public void setSizes(List<Object> sizes) {
        this.sizes = sizes;
    }

    public List<Object> getStatusses() {
        return statusses;
    }

    public void setStatusses(List<Object> statusses) {
        this.statusses = statusses;
    }

    public List<Object[]> getVessels() {
        return vessels;
    }

    public void setVessels(List<Object[]> vessels) {
        this.vessels = vessels;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getMessage() {
        return message;
    }
}
