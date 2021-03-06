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
@Table(name = "perhitungan_discharge")
@NamedQueries({
    @NamedQuery(name = "PerhitunganDischarge.findAll", query = "SELECT p FROM PerhitunganDischarge p"),
    @NamedQuery(name = "PerhitunganDischarge.findById", query = "SELECT p FROM PerhitunganDischarge p WHERE p.id = :id"),
    @NamedQuery(name = "PerhitunganDischarge.findBySubTotalDischarge", query = "SELECT p FROM PerhitunganDischarge p WHERE p.subTotalDischarge = :subTotalDischarge"),
    @NamedQuery(name = "PerhitunganDischarge.findBySubTotalTranshipment", query = "SELECT p FROM PerhitunganDischarge p WHERE p.subTotalTranshipment = :subTotalTranshipment"),
    @NamedQuery(name = "PerhitunganDischarge.findBySubTotalShifting", query = "SELECT p FROM PerhitunganDischarge p WHERE p.subTotalShifting = :subTotalShifting"),
    @NamedQuery(name = "PerhitunganDischarge.findBySubTotalHatch", query = "SELECT p FROM PerhitunganDischarge p WHERE p.subTotalHatch = :subTotalHatch"),
    @NamedQuery(name = "PerhitunganDischarge.findByJumlah", query = "SELECT p FROM PerhitunganDischarge p WHERE p.jumlah = :jumlah"),
    @NamedQuery(name = "PerhitunganDischarge.findByTax", query = "SELECT p FROM PerhitunganDischarge p WHERE p.tax = :tax"),
    @NamedQuery(name = "PerhitunganDischarge.findByTotal", query = "SELECT p FROM PerhitunganDischarge p WHERE p.total = :total")})
public class PerhitunganDischarge implements Serializable, EntityAuditable {
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
    @Column(name = "sub_total_discharge")
    private BigInteger subTotalDischarge;
    @Column(name = "sub_total_transhipment")
    private BigInteger subTotalTranshipment;
    @Column(name = "sub_total_shifting")
    private BigInteger subTotalShifting;
    @Column(name = "sub_total_hatch")
    private BigInteger subTotalHatch;
    @Column(name = "jumlah")
    private BigInteger jumlah;
    @Column(name = "tax")
    private BigInteger tax;
    @Column(name = "total")
    private BigInteger total;

    public PerhitunganDischarge() {
    }

    public PerhitunganDischarge(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getSubTotalDischarge() {
        return subTotalDischarge;
    }

    public void setSubTotalDischarge(BigInteger subTotalDischarge) {
        this.subTotalDischarge = subTotalDischarge;
    }

    public BigInteger getSubTotalTranshipment() {
        return subTotalTranshipment;
    }

    public void setSubTotalTranshipment(BigInteger subTotalTranshipment) {
        this.subTotalTranshipment = subTotalTranshipment;
    }

    public BigInteger getSubTotalShifting() {
        return subTotalShifting;
    }

    public void setSubTotalShifting(BigInteger subTotalShifting) {
        this.subTotalShifting = subTotalShifting;
    }

    public BigInteger getSubTotalHatch() {
        return subTotalHatch;
    }

    public void setSubTotalHatch(BigInteger subTotalHatch) {
        this.subTotalHatch = subTotalHatch;
    }

    public BigInteger getJumlah() {
        return jumlah;
    }

    public void setJumlah(BigInteger jumlah) {
        this.jumlah = jumlah;
    }

    public BigInteger getTax() {
        return tax;
    }

    public void setTax(BigInteger tax) {
        this.tax = tax;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
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
        if (!(object instanceof PerhitunganDischarge)) {
            return false;
        }
        PerhitunganDischarge other = (PerhitunganDischarge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganDischarge[id=" + id + "]";
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
