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
public class StartServiceInitialData implements Serializable, IMessageable {
    private List<String> masterDocks;
    private List<Object[]> vessels;
    private Vessel inititalVesselData;
    private String messageCode;
    private String message;

    public StartServiceInitialData(){}

    public StartServiceInitialData(String messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    public List<String> getMasterDocks() {
        return masterDocks;
    }

    public void setMasterDocks(List<String> masterDocks) {
        this.masterDocks = masterDocks;
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

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public Vessel getInititalVesselData() {
        return inititalVesselData;
    }

    public void setInititalVesselData(Vessel inititalVesselData) {
        this.inititalVesselData = inititalVesselData;
    }
}
