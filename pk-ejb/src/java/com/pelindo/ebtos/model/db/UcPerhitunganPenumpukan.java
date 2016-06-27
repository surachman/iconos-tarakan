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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author postgres
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "uc_perhitungan_penumpukan")
@NamedQueries({
    @NamedQuery(name = "UcPerhitunganPenumpukan.findAll", query = "SELECT u FROM UcPerhitunganPenumpukan u"),
    @NamedQuery(name = "UcPerhitunganPenumpukan.findById", query = "SELECT u FROM UcPerhitunganPenumpukan u WHERE u.id = :id"),
    @NamedQuery(name = "UcPerhitunganPenumpukan.findByNoReg", query = "SELECT u FROM UcPerhitunganPenumpukan u WHERE u.registration.noReg = :noReg"),
    @NamedQuery(name = "UcPerhitunganPenumpukan.deleteByNoReg", query = "DELETE FROM UcPerhitunganPenumpukan u WHERE u.registration.noReg = :noReg"),
    @NamedQuery(name = "UcPerhitunganPenumpukan.findByJobslip", query = "SELECT u FROM UcPerhitunganPenumpukan u WHERE u.jobslip = :jobslip"),
    @NamedQuery(name = "UcPerhitunganPenumpukan.deleteByJobslip", query = "DELETE FROM UcPerhitunganPenumpukan u WHERE u.jobslip = :jobslip"),
    @NamedQuery(name = "UcPerhitunganPenumpukan.findByActivityCode", query = "SELECT u FROM UcPerhitunganPenumpukan u WHERE u.activityCode = :activityCode"),
    @NamedQuery(name = "UcPerhitunganPenumpukan.findByCharge", query = "SELECT u FROM UcPerhitunganPenumpukan u WHERE u.charge = :charge"),
    @NamedQuery(name = "UcPerhitunganPenumpukan.findByChargeMasa1", query = "SELECT u FROM UcPerhitunganPenumpukan u WHERE u.chargeMasa1 = :chargeMasa1"),
    @NamedQuery(name = "UcPerhitunganPenumpukan.findByChargeMasa2", query = "SELECT u FROM UcPerhitunganPenumpukan u WHERE u.chargeMasa2 = :chargeMasa2"),
    @NamedQuery(name = "UcPerhitunganPenumpukan.findByOperation", query = "SELECT u FROM UcPerhitunganPenumpukan u WHERE u.operation = :operation"),
    @NamedQuery(name = "UcPerhitunganPenumpukan.deleteByNoPpkbAndTruckLoosingStatus", query = "DELETE FROM UcPerhitunganPenumpukan u WHERE u.ucReceivingService.uncontainerizedService.noPpkb = :noPpkb AND u.ucReceivingService.uncontainerizedService.truckLoosing = :truckLoosing")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "UcPerhitunganPenumpukan.Native.findByJobslip", query = "SELECT id from uc_perhitungan_penumpukan WHERE jobslip=?"),
    @NamedNativeQuery(name = "UcPerhitunganPenumpukan.Native.findByReg", query = "SELECT id from uc_perhitungan_penumpukan WHERE no_reg=?")})
public class UcPerhitunganPenumpukan implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "jobslip", length = 30)
    private String jobslip;
    @Column(name = "activity_code", length = 10)
    private String activityCode;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "charge_masa1", precision = 19, scale = 2)
    private BigDecimal chargeMasa1;
    @Column(name = "charge_masa2", precision = 19, scale = 2)
    private BigDecimal chargeMasa2;
    @Column(name = "operation", length = 20)
    private String operation;
    @Column(name = "curr_code")
    private String currCode;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg")
    @ManyToOne
    private Registration registration;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
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
    @JoinColumn(name = "jobslip", referencedColumnName = "jobslip", insertable = false, updatable = false)
    @OneToOne
    private UcReceivingService ucReceivingService;
    @Column(name = "masa_1")
    private Short masa1;
    @Column(name = "masa_2")
    private Short masa2;

    public UcPerhitunganPenumpukan() {
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public UcPerhitunganPenumpukan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getChargeMasa1() {
        return chargeMasa1;
    }

    public void setChargeMasa1(BigDecimal chargeMasa1) {
        this.chargeMasa1 = chargeMasa1;
    }

    public BigDecimal getChargeMasa2() {
        return chargeMasa2;
    }

    public void setChargeMasa2(BigDecimal chargeMasa2) {
        this.chargeMasa2 = chargeMasa2;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public UcReceivingService getUcReceivingService() {
        return ucReceivingService;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Short getMasa1() {
        return masa1;
    }

    public void setMasa1(Short masa1) {
        this.masa1 = masa1;
    }

    public Short getMasa2() {
        return masa2;
    }

    public void setMasa2(Short masa2) {
        this.masa2 = masa2;
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
        if (!(object instanceof UcPerhitunganPenumpukan)) {
            return false;
        }
        UcPerhitunganPenumpukan other = (UcPerhitunganPenumpukan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.UcPerhitunganPenumpukan[id=" + id + "]";
    }
}
