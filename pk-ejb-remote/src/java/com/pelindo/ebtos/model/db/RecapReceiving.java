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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dycode-java
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "recap_receiving")
@NamedQueries({
    @NamedQuery(name = "RecapReceiving.findAll", query = "SELECT r FROM RecapReceiving r"),
    @NamedQuery(name = "RecapReceiving.findByNoPpkb", query = "SELECT r FROM RecapReceiving r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapReceiving.findByReceivingCharge", query = "SELECT r FROM RecapReceiving r WHERE r.receivingCharge = :receivingCharge"),
    @NamedQuery(name = "RecapReceiving.findByBooking", query = "SELECT r FROM RecapReceiving r WHERE r.booking = :booking"),
    @NamedQuery(name = "RecapReceiving.findByJobslipAmount", query = "SELECT r FROM RecapReceiving r WHERE r.jobslipAmount = :jobslipAmount"),
    @NamedQuery(name = "RecapReceiving.findByJobslipReal", query = "SELECT r FROM RecapReceiving r WHERE r.jobslipReal = :jobslipReal"),
    @NamedQuery(name = "RecapReceiving.findByCancelCharge", query = "SELECT r FROM RecapReceiving r WHERE r.cancelCharge = :cancelCharge"),
    @NamedQuery(name = "RecapReceiving.findByTotalCharge", query = "SELECT r FROM RecapReceiving r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapReceiving.findByCurrCode", query = "SELECT r FROM RecapReceiving r WHERE r.currCode = :currCode")})
public class RecapReceiving implements Serializable, EntityAuditable {
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
    @Column(name = "no_ppkb", nullable = false, length = 30)
    private String noPpkb;
    @Column(name = "receiving_charge", precision = 19, scale = 2)
    private BigDecimal receivingCharge;
    @Column(name = "booking")
    private Integer booking;
    @Column(name = "jobslip_amount")
    private Integer jobslipAmount;
    @Column(name = "jobslip_real")
    private Integer jobslipReal;
    @Column(name = "cancel_charge", precision = 19, scale = 2)
    private BigDecimal cancelCharge;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
    @Column(name = "curr_code", length = 5)
    private String currCode;

    public RecapReceiving() {
    }

    public RecapReceiving(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public BigDecimal getReceivingCharge() {
        return receivingCharge;
    }

    public void setReceivingCharge(BigDecimal receivingCharge) {
        this.receivingCharge = receivingCharge;
    }

    public Integer getBooking() {
        return booking;
    }

    public void setBooking(Integer booking) {
        this.booking = booking;
    }

    public Integer getJobslipAmount() {
        return jobslipAmount;
    }

    public void setJobslipAmount(Integer jobslipAmount) {
        this.jobslipAmount = jobslipAmount;
    }

    public Integer getJobslipReal() {
        return jobslipReal;
    }

    public void setJobslipReal(Integer jobslipReal) {
        this.jobslipReal = jobslipReal;
    }

    public BigDecimal getCancelCharge() {
        return cancelCharge;
    }

    public void setCancelCharge(BigDecimal cancelCharge) {
        this.cancelCharge = cancelCharge;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noPpkb != null ? noPpkb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecapReceiving)) {
            return false;
        }
        RecapReceiving other = (RecapReceiving) object;
        if ((this.noPpkb == null && other.noPpkb != null) || (this.noPpkb != null && !this.noPpkb.equals(other.noPpkb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapReceiving[noPpkb=" + noPpkb + "]";
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
