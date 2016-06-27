/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author postgres
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "uc_perhitungan_lift")
@NamedQueries({
    @NamedQuery(name = "UcPerhitunganLift.findAll", query = "SELECT u FROM UcPerhitunganLift u"),
    @NamedQuery(name = "UcPerhitunganLift.findById", query = "SELECT u FROM UcPerhitunganLift u WHERE u.id = :id"),
    @NamedQuery(name = "UcPerhitunganLift.findByNoReg", query = "SELECT u FROM UcPerhitunganLift u WHERE u.noReg = :noReg"),
    @NamedQuery(name = "UcPerhitunganLift.deleteByNoReg", query = "DELETE FROM UcPerhitunganLift u WHERE u.noReg = :noReg"),
    @NamedQuery(name = "UcPerhitunganLift.findByJobslip", query = "SELECT u FROM UcPerhitunganLift u WHERE u.jobslip = :jobslip"),
    @NamedQuery(name = "UcPerhitunganLift.deleteByJobslip", query = "DELETE FROM UcPerhitunganLift u WHERE u.jobslip = :jobslip"),
    @NamedQuery(name = "UcPerhitunganLift.findByActivityCode", query = "SELECT u FROM UcPerhitunganLift u WHERE u.activityCode = :activityCode"),
    @NamedQuery(name = "UcPerhitunganLift.findByCharge", query = "SELECT u FROM UcPerhitunganLift u WHERE u.charge = :charge"),
    @NamedQuery(name = "UcPerhitunganLift.findByTotalCharge", query = "SELECT u FROM UcPerhitunganLift u WHERE u.totalCharge = :totalCharge"),
    @NamedQuery(name = "UcPerhitunganLift.findByOperation", query = "SELECT u FROM UcPerhitunganLift u WHERE u.operation = :operation")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "UcPerhitunganLift.Native.findByJobslip", query = "SELECT id from uc_perhitungan_lift WHERE jobslip=?"),
    @NamedNativeQuery(name = "UcPerhitunganLift.Native.findByReg", query = "SELECT id from uc_perhitungan_lift WHERE no_reg=?")})
public class UcPerhitunganLift implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "created_by", nullable = false, length = 256)
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_by", length = 256)
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_reg", length = 30)
    private String noReg;
    @Column(name = "jobslip", length = 30)
    private String jobslip;
    @Column(name = "activity_code", length = 10)
    private String activityCode;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
    @Column(name = "operation", length = 20)
    private String operation;
    @Column(name = "curr_code")
    private String currCode;

    public UcPerhitunganLift() {
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public UcPerhitunganLift(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public String getJobslip() {
        return jobslip;
    }

    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UcPerhitunganLift)) {
            return false;
        }
        UcPerhitunganLift other = (UcPerhitunganLift) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.UcPerhitunganLift[id=" + id + "]";
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
