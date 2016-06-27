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
public class DirectInputContainerSubmitData implements Serializable, EntityAuditable {
    private String noPpkb;
    private String contNo;
    private String contSize;
    private String contStatus;
    private String ccCode;
    private String htCode;
    private String startTime;
    private String endTime;
    private String ccOperatorCode;
    private String htOperatorCode;
    private String contDamage;
    private String vesselBay;
    private String vesselRow;
    private String vesselTier;
    private String isOversize;
    private String isDangerous;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;

    public DirectInputContainerSubmitData(){}

    public String getContSize() {
        return contSize;
    }

    public void setContSize(String contSize) {
        this.contSize = contSize;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public String getVesselBay() {
        return vesselBay;
    }

    public void setVesselBay(String vesselBay) {
        this.vesselBay = vesselBay;
    }

    public String getVesselRow() {
        return vesselRow;
    }

    public void setVesselRow(String vesselRow) {
        this.vesselRow = vesselRow;
    }

    public String getVesselTier() {
        return vesselTier;
    }

    public void setVesselTier(String vesselTier) {
        this.vesselTier = vesselTier;
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

    public String getContDamage() {
        return contDamage;
    }

    public void setContDamage(String contDamage) {
        this.contDamage = contDamage;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
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

    public String getIsDangerous() {
        return isDangerous;
    }

    public void setIsDangerous(String isDangerous) {
        this.isDangerous = isDangerous;
    }

    public String getIsOversize() {
        return isOversize;
    }

    public void setIsOversize(String isOversize) {
        this.isOversize = isOversize;
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
