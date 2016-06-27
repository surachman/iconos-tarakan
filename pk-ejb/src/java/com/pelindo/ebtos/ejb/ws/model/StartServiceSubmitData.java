/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.ws.model;

import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author x42jr
 */
public class StartServiceSubmitData implements Serializable, EntityAuditable {
    private String noPpkb;
    private String dockCode;
    private String frMeter;
    private String toMeter;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;

    public StartServiceSubmitData(){}

    public String getDockCode() {
        return dockCode;
    }

    public void setDockCode(String dockCode) {
        this.dockCode = dockCode;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getFrMeter() {
        return frMeter;
    }

    public void setFrMeter(String frMeter) {
        this.frMeter = frMeter;
    }

    public String getToMeter() {
        return toMeter;
    }

    public void setToMeter(String toMeter) {
        this.toMeter = toMeter;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
