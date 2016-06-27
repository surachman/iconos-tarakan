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
@Table(name = "loaduc_productivity_recap")
@NamedQueries({
    @NamedQuery(name = "LoaducProductivityRecap.findAll", query = "SELECT l FROM LoaducProductivityRecap l"),
    @NamedQuery(name = "LoaducProductivityRecap.findById", query = "SELECT l FROM LoaducProductivityRecap l WHERE l.id = :id"),
    @NamedQuery(name = "LoaducProductivityRecap.findByJumlah", query = "SELECT l FROM LoaducProductivityRecap l WHERE l.jumlah = :jumlah"),
    @NamedQuery(name = "LoaducProductivityRecap.findByTarif", query = "SELECT l FROM LoaducProductivityRecap l WHERE l.tarif = :tarif"),
    @NamedQuery(name = "LoaducProductivityRecap.findByCharge", query = "SELECT l FROM LoaducProductivityRecap l WHERE l.charge = :charge"),
    @NamedQuery(name = "LoaducProductivityRecap.findBySubActivity", query = "SELECT l FROM LoaducProductivityRecap l WHERE l.subActivity = :subActivity")})
public class LoaducProductivityRecap implements Serializable, EntityAuditable {
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
    @Column(name = "jumlah")
    private Short jumlah;
    @Column(name = "tarif")
    private BigInteger tarif;
    @Column(name = "charge")
    private BigInteger charge;
    @Column(name = "sub_activity", length = 10)
    private String subActivity;

    public LoaducProductivityRecap() {
    }

    public LoaducProductivityRecap(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getJumlah() {
        return jumlah;
    }

    public void setJumlah(Short jumlah) {
        this.jumlah = jumlah;
    }

    public BigInteger getTarif() {
        return tarif;
    }

    public void setTarif(BigInteger tarif) {
        this.tarif = tarif;
    }

    public BigInteger getCharge() {
        return charge;
    }

    public void setCharge(BigInteger charge) {
        this.charge = charge;
    }

    public String getSubActivity() {
        return subActivity;
    }

    public void setSubActivity(String subActivity) {
        this.subActivity = subActivity;
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
        if (!(object instanceof LoaducProductivityRecap)) {
            return false;
        }
        LoaducProductivityRecap other = (LoaducProductivityRecap) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.LoaducProductivityRecap[id=" + id + "]";
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
