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
public class DirectInputContainerInitialData implements Serializable, IMessageable {
    private String serverTime;
    private List<Object> ccCodes;
    private List<Object> htCodes;
    private List<Object[]> operators;
    private List<Object[]> contDamage;
    private List<Object[]> vessels;
    private String messageCode;
    private String message;

    public DirectInputContainerInitialData(){}

    public DirectInputContainerInitialData(String code, String message) {
        this.messageCode = code;
        this.message = message;
    }

    public List<Object> getCcCodes() {
        return ccCodes;
    }

    public void setCcCodes(List<Object> ccCodes) {
        this.ccCodes = ccCodes;
    }

    public List<Object[]> getContDamage() {
        return contDamage;
    }

    public void setContDamage(List<Object[]> contDamage) {
        this.contDamage = contDamage;
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

    public List<Object[]> getVessels() {
        return vessels;
    }

    public void setVessels(List<Object[]> vessels) {
        this.vessels = vessels;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
