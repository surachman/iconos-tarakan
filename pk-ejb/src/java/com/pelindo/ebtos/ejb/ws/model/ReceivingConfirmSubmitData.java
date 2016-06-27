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
public class ReceivingConfirmSubmitData implements Serializable, EntityAuditable {
    private String contNo;
    private String ttCode;
    private String startTime;
    private String endTime;
    private String ttOperatorCode;
    private String yardBlock;
    private String yardSlot;
    private String yardRow;
    private String yardTier;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;

    public ReceivingConfirmSubmitData(){}

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTtCode() {
        return ttCode;
    }

    public void setTtCode(String ttCode) {
        this.ttCode = ttCode;
    }

    public String getTtOperatorCode() {
        return ttOperatorCode;
    }

    public void setTtOperatorCode(String ttOperatorCode) {
        this.ttOperatorCode = ttOperatorCode;
    }

    public String getYardBlock() {
        return yardBlock;
    }

    public void setYardBlock(String yardBlock) {
        this.yardBlock = yardBlock;
    }

    public String getYardRow() {
        return yardRow;
    }

    public void setYardRow(String yardRow) {
        this.yardRow = yardRow;
    }

    public String getYardSlot() {
        return yardSlot;
    }

    public void setYardSlot(String yardSlot) {
        this.yardSlot = yardSlot;
    }

    public String getYardTier() {
        return yardTier;
    }

    public void setYardTier(String yardTier) {
        this.yardTier = yardTier;
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
