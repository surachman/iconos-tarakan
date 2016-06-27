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
 * @author wulan
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "plugging_only_rekap")
@NamedQueries({
    @NamedQuery(name = "PluggingOnlyRekap.findAll", query = "SELECT p FROM PluggingOnlyRekap p"),
    @NamedQuery(name = "PluggingOnlyRekap.findByJobSlip", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.jobSlip = :jobSlip"),
    @NamedQuery(name = "PluggingOnlyRekap.findByJobPluggReefer", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.jobPluggReefer = :jobPluggReefer"),
    @NamedQuery(name = "PluggingOnlyRekap.findByChargeReceivingLift", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.chargeReceivingLift = :chargeReceivingLift"),
    @NamedQuery(name = "PluggingOnlyRekap.findByChargeDeliveryLift", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.chargeDeliveryLift = :chargeDeliveryLift"),
    @NamedQuery(name = "PluggingOnlyRekap.findByMassa2", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.massa2 = :massa2"),
    @NamedQuery(name = "PluggingOnlyRekap.findByMassa1", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.massa1 = :massa1"),
    @NamedQuery(name = "PluggingOnlyRekap.findByChargeMassa1", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.chargeMassa1 = :chargeMassa1"),
    @NamedQuery(name = "PluggingOnlyRekap.findByChargeMassa2", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.chargeMassa2 = :chargeMassa2"),
    @NamedQuery(name = "PluggingOnlyRekap.findByChargePenumpukan", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.chargePenumpukan = :chargePenumpukan"),
    @NamedQuery(name = "PluggingOnlyRekap.findByActivityLift", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.activityLift = :activityLift"),
    @NamedQuery(name = "PluggingOnlyRekap.findByActivityTumpuk", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.activityTumpuk = :activityTumpuk"),
    @NamedQuery(name = "PluggingOnlyRekap.findByCurrCode", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.currCode = :currCode"),
    @NamedQuery(name = "PluggingOnlyRekap.findByCreatedDate", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "PluggingOnlyRekap.findByCreatedBy", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.createdBy = :createdBy"),
    @NamedQuery(name = "PluggingOnlyRekap.findByModifiedDate", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "PluggingOnlyRekap.findByModifiedBy", query = "SELECT p FROM PluggingOnlyRekap p WHERE p.modifiedBy = :modifiedBy")})
public class PluggingOnlyRekap implements Serializable, EntityAuditable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobSlip;
    @Column(name = "job_plugg_reefer", length = 30)
    private String jobPluggReefer;
    @Column(name = "charge_receiving_lift", precision = 19, scale = 2)
    private BigDecimal chargeReceivingLift;
    @Column(name = "charge_delivery_lift", precision = 19, scale = 2)
    private BigDecimal chargeDeliveryLift;
    @Column(name = "massa2")
    private Short massa2;
    @Column(name = "massa1")
    private Short massa1;
    @Column(name = "charge_massa1", precision = 19, scale = 2)
    private BigDecimal chargeMassa1;
    @Column(name = "charge_massa2", precision = 19, scale = 2)
    private BigDecimal chargeMassa2;
    @Column(name = "charge_penumpukan", precision = 19, scale = 2)
    private BigDecimal chargePenumpukan;
    @Column(name = "activity_lift", length = 2147483647)
    private String activityLift;
    @Column(name = "activity_tumpuk", length = 2147483647)
    private String activityTumpuk;
    @Column(name = "curr_code", length = 2147483647)
    private String currCode;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "created_by", length = 2147483647)
    private String createdBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "modified_by", length = 2147483647)
    private String modifiedBy;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
    @Column(name = "no_reg", length = 30)
    private String noReg;

    public PluggingOnlyRekap() {
    }

    public PluggingOnlyRekap(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getJobPluggReefer() {
        return jobPluggReefer;
    }

    public void setJobPluggReefer(String jobPluggReefer) {
        this.jobPluggReefer = jobPluggReefer;
    }

    public BigDecimal getChargeReceivingLift() {
        return chargeReceivingLift;
    }

    public void setChargeReceivingLift(BigDecimal chargeReceivingLift) {
        this.chargeReceivingLift = chargeReceivingLift;
    }

    public BigDecimal getChargeDeliveryLift() {
        return chargeDeliveryLift;
    }

    public void setChargeDeliveryLift(BigDecimal chargeDeliveryLift) {
        this.chargeDeliveryLift = chargeDeliveryLift;
    }

    public Short getMassa2() {
        return massa2;
    }

    public void setMassa2(Short massa2) {
        this.massa2 = massa2;
    }

    public Short getMassa1() {
        return massa1;
    }

    public void setMassa1(Short massa1) {
        this.massa1 = massa1;
    }

    public BigDecimal getChargeMassa1() {
        return chargeMassa1;
    }

    public void setChargeMassa1(BigDecimal chargeMassa1) {
        this.chargeMassa1 = chargeMassa1;
    }

    public BigDecimal getChargeMassa2() {
        return chargeMassa2;
    }

    public void setChargeMassa2(BigDecimal chargeMassa2) {
        this.chargeMassa2 = chargeMassa2;
    }

    public BigDecimal getChargePenumpukan() {
        return chargePenumpukan;
    }

    public void setChargePenumpukan(BigDecimal chargePenumpukan) {
        this.chargePenumpukan = chargePenumpukan;
    }

    public String getActivityLift() {
        return activityLift;
    }

    public void setActivityLift(String activityLift) {
        this.activityLift = activityLift;
    }

    public String getActivityTumpuk() {
        return activityTumpuk;
    }

    public void setActivityTumpuk(String activityTumpuk) {
        this.activityTumpuk = activityTumpuk;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobSlip != null ? jobSlip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PluggingOnlyRekap)) {
            return false;
        }
        PluggingOnlyRekap other = (PluggingOnlyRekap) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PluggingOnlyRekap[jobSlip=" + jobSlip + "]";
    }
}
