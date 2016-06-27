/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigInteger;
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
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "perhitungan_reefer")
@NamedQueries({
    @NamedQuery(name = "PerhitunganReefer.findAll", query = "SELECT p FROM PerhitunganReefer p"),
    @NamedQuery(name = "PerhitunganReefer.findById", query = "SELECT p FROM PerhitunganReefer p WHERE p.id = :id"),
    @NamedQuery(name = "PerhitunganReefer.findBySubTotalPlugging", query = "SELECT p FROM PerhitunganReefer p WHERE p.subTotalPlugging = :subTotalPlugging"),
    @NamedQuery(name = "PerhitunganReefer.findBySubTotalMonitoring", query = "SELECT p FROM PerhitunganReefer p WHERE p.subTotalMonitoring = :subTotalMonitoring"),
    @NamedQuery(name = "PerhitunganReefer.findByTotalCharge", query = "SELECT p FROM PerhitunganReefer p WHERE p.totalCharge = :totalCharge"),
    @NamedQuery(name = "PerhitunganReefer.findByOperation", query = "SELECT p FROM PerhitunganReefer p WHERE p.operation = :operation")})
public class PerhitunganReefer implements Serializable, EntityAuditable {
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
    private Integer id;
    @Column(name = "sub_total_plugging")
    private BigInteger subTotalPlugging;
    @Column(name = "sub_total_monitoring")
    private BigInteger subTotalMonitoring;
    @Column(name = "total_charge")
    private BigInteger totalCharge;
    @Column(name = "operation", length = 10)
    private String operation;

    public PerhitunganReefer() {
    }

    public PerhitunganReefer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getSubTotalPlugging() {
        return subTotalPlugging;
    }

    public void setSubTotalPlugging(BigInteger subTotalPlugging) {
        this.subTotalPlugging = subTotalPlugging;
    }

    public BigInteger getSubTotalMonitoring() {
        return subTotalMonitoring;
    }

    public void setSubTotalMonitoring(BigInteger subTotalMonitoring) {
        this.subTotalMonitoring = subTotalMonitoring;
    }

    public BigInteger getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigInteger totalCharge) {
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
        if (!(object instanceof PerhitunganReefer)) {
            return false;
        }
        PerhitunganReefer other = (PerhitunganReefer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganReefer[id=" + id + "]";
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
