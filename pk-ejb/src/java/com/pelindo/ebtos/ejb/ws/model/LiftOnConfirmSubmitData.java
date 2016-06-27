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
public class LiftOnConfirmSubmitData implements Serializable, EntityAuditable {
    private String contNo;
    private String ttCode;
    private String ttOperatorCode;
    private String htCode;
    private String htOperatorCode;
    private String startTime;
    private String endTime;
    private String service;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;

    public LiftOnConfirmSubmitData(){}

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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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
