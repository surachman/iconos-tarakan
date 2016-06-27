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
public class VesselScheduleInitialData implements Serializable, IMessageable {
    private List<Object[]> vessels;
    private Vessel vessel;
    private String messageCode;
    private String message;

    public VesselScheduleInitialData(){}

    public VesselScheduleInitialData(String code, String message) {
        this.messageCode = code;
        this.message = message;
    }

    public List<Object[]> getVessels() {
        return vessels;
    }

    public void setVessels(List<Object[]> vessels) {
        this.vessels = vessels;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
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
