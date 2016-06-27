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
public class DischargeConfirmUcSubmitData implements Serializable, EntityAuditable {
    private String noPpkb;
    private String blNo;
    private String ccCode;
    private String htCode;
    private String startTime;
    private String endTime;
    private String isTruckLoosing;
    private String ccOperatorCode;
    private String htOperatorCode;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;

    public DischargeConfirmUcSubmitData(){}

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getCcCode() {
        return ccCode;
    }

    public void setCcCode(String ccCode) {
        this.ccCode = ccCode;
    }

    public String getCcOperatorCode() {
        return ccOperatorCode;
    }

    public void setCcOperatorCode(String ccOperatorCode) {
        this.ccOperatorCode = ccOperatorCode;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHtCode() {
        return htCode;
    }

    public void setHtCode(String htCode) {
        this.htCode = htCode;
    }

    public String getHtOperatorCode() {
        return htOperatorCode;
    }

    public void setHtOperatorCode(String htOperatorCode) {
        this.htOperatorCode = htOperatorCode;
    }

    public String getIsTruckLoosing() {
        return isTruckLoosing;
    }

    public void setIsTruckLoosing(String isTruckLoosing) {
        this.isTruckLoosing = isTruckLoosing;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
