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
 * @author Dyware-Dev01
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "perhitungan_nota_manual_detail")
@NamedQueries({
    @NamedQuery(name = "PerhitunganNotaManualDetail.findAll", query = "SELECT p FROM PerhitunganNotaManualDetail p"),
    @NamedQuery(name = "PerhitunganNotaManualDetail.findByNoReg", query = "SELECT p FROM PerhitunganNotaManualDetail p WHERE p.noReg = :noReg"),
    @NamedQuery(name = "PerhitunganNotaManualDetail.findByActivityName", query = "SELECT p FROM PerhitunganNotaManualDetail p WHERE p.activityName = :activityName"),
    @NamedQuery(name = "PerhitunganNotaManualDetail.findByAmount", query = "SELECT p FROM PerhitunganNotaManualDetail p WHERE p.amount = :amount"),
    @NamedQuery(name = "PerhitunganNotaManualDetail.findById", query = "SELECT p FROM PerhitunganNotaManualDetail p WHERE p.id = :id")})
@NamedNativeQueries({
    @NamedNativeQuery(name="PerhitunganNotaManualDetail.Native.findByNoReg", query = "SELECT id, activity_name, amount FROM perhitungan_nota_manual_detail WHERE no_reg = ?")
    })
public class PerhitunganNotaManualDetail implements Serializable, EntityAuditable {

    @Column(name = "no_reg", length = 30)
    private String noReg;
    @Column(name = "activity_name", length = 256)
    private String activityName;
    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amount;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;
    @Column(name = "activity_type")
    private Integer activityType;
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

    public PerhitunganNotaManualDetail() {
    }

    public PerhitunganNotaManualDetail(BigDecimal id) {
        this.id = id;
    }

    public PerhitunganNotaManualDetail(BigDecimal id, String noReg) {
        this.id = id;
        this.noReg = noReg;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerhitunganNotaManualDetail)) {
            return false;
        }
        PerhitunganNotaManualDetail other = (PerhitunganNotaManualDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganNotaManualDetail[id=" + id + "]";
    }
}
