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
public class ContainerMovementOffConfirmSubmitData implements Serializable, EntityAuditable {
    private String contNo;
    private String service;
    private String yardBlock;
    private String yardSlot;
    private String yardRow;
    private String yardTier;
    private String ttCode;
    private String ttOperatorCode;
    private String startTime;
    private String endTime;
    private String destination;
    private String operation;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;

    public ContainerMovementOffConfirmSubmitData(){}

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

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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
}
