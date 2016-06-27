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
public class ReshippingConfirmSubmitData implements Serializable, EntityAuditable {
    private String noPpkb;
    private String contNo;
    private String vesselBay;
    private String vesselRow;
    private String vesselTier;
    private String ccCode;
    private String ccOperatorCode;
    private String isUseSling;
    private String isUseHt;
    private String htCode;
    private String htOperatorCode;
    private String service;
    private String startTime;
    private String endTime;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;

    public ReshippingConfirmSubmitData(){}

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

    public String getIsUseHt() {
        return isUseHt;
    }

    public void setIsUseHt(String isUseHt) {
        this.isUseHt = isUseHt;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getIsUseSling() {
        return isUseSling;
    }

    public void setIsUseSling(String isUseSling) {
        this.isUseSling = isUseSling;
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
