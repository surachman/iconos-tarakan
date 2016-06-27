/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "m_materai")
@NamedQueries({
    @NamedQuery(name = "MasterMaterai.findAll", query = "SELECT m FROM MasterMaterai m"),
    @NamedQuery(name = "MasterMaterai.findById", query = "SELECT m FROM MasterMaterai m WHERE m.id = :id"),
    @NamedQuery(name = "MasterMaterai.findByNilaiMaterai", query = "SELECT m FROM MasterMaterai m WHERE m.nilaiMaterai = :nilaiMaterai"),
    @NamedQuery(name = "MasterMaterai.findByDescription", query = "SELECT m FROM MasterMaterai m WHERE m.description = :description")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterMaterai.Native.findByCurr", query = "SELECT id, nilai_materai, tarif_minimum FROM m_materai WHERE curr_code = ? ORDER BY tarif_minimum ASC")
})
public class MasterMaterai implements Serializable, EntityAuditable {
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
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nilai_materai")
    private BigDecimal nilaiMaterai;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "curr_code", referencedColumnName = "curr_code")
    @ManyToOne(optional = false)
    private MasterCurrency masterCurrency;
    @Column(name = "tarif_minimum")
    private BigDecimal tarifMinimum;

    public MasterMaterai() {
    }

    public MasterMaterai(Integer id) {
        this.id = id;
    }

    public MasterMaterai(Integer id, BigDecimal nilaiMaterai) {
        this.id = id;
        this.nilaiMaterai = nilaiMaterai;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getNilaiMaterai() {
        return nilaiMaterai;
    }

    public void setNilaiMaterai(BigDecimal nilaiMaterai) {
        this.nilaiMaterai = nilaiMaterai;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public void setMasterCurrency(MasterCurrency masterCurrency) {
        this.masterCurrency = masterCurrency;
    }

    public BigDecimal getTarifMinimum() {
        return tarifMinimum;
    }

    public void setTarifMinimum(BigDecimal tarifMinimum) {
        this.tarifMinimum = tarifMinimum;
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
        if (!(object instanceof MasterMaterai)) {
            return false;
        }
        MasterMaterai other = (MasterMaterai) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterMaterai[id=" + id + "]";
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
