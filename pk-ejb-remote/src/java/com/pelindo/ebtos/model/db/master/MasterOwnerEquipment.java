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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author RSenoAnggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_owner_equipment")
@NamedQueries({
    @NamedQuery(name = "MasterOwnerEquipment.findAll", query = "SELECT m FROM MasterOwnerEquipment m"),
    @NamedQuery(name = "MasterOwnerEquipment.findByOwnerCode", query = "SELECT m FROM MasterOwnerEquipment m WHERE m.ownerCode = :ownerCode"),
    @NamedQuery(name = "MasterOwnerEquipment.findByOwnerName", query = "SELECT m FROM MasterOwnerEquipment m WHERE m.ownerName = :ownerName")})
public class MasterOwnerEquipment implements Serializable, EntityAuditable {
    @OneToMany(mappedBy = "masterOwnerEquipment")
    private List<MasterTarifSharing> masterTarifSharingList;
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
    @Column(name = "owner_code", nullable = false, length = 5)
    private String ownerCode;
    @Basic(optional = false)
    @Column(name = "owner_name", nullable = false, length = 50)
    private String ownerName;
    @Basic(optional = false)
    @Column(name = "profitpelindo", precision = 5, scale = 2)
    private BigDecimal profitpelindo;
    @Basic(optional = false)
    @Column(name = "profitowner", precision = 5, scale = 2)
    private BigDecimal profitowner;
    @OneToMany(mappedBy = "masterOwnerEquipment")
    private List<MasterEquipment> masterEquipmentList;

    public MasterOwnerEquipment() {
    }

    public MasterOwnerEquipment(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public MasterOwnerEquipment(String ownerCode, String ownerName) {
        this.ownerCode = ownerCode;
        this.ownerName = ownerName;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public BigDecimal getProfitowner() {
        return profitowner;
    }

    public void setProfitowner(BigDecimal profitowner) {
        this.profitowner = profitowner;
    }

    public BigDecimal getProfitpelindo() {
        return profitpelindo;
    }

    public void setProfitpelindo(BigDecimal profitpelindo) {
        this.profitpelindo = profitpelindo;
    }  

    public List<MasterEquipment> getMasterEquipmentList() {
        return masterEquipmentList;
    }

    public void setMasterEquipmentList(List<MasterEquipment> masterEquipmentList) {
        this.masterEquipmentList = masterEquipmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ownerCode != null ? ownerCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterOwnerEquipment)) {
            return false;
        }
        MasterOwnerEquipment other = (MasterOwnerEquipment) object;
        if ((this.ownerCode == null && other.ownerCode != null) || (this.ownerCode != null && !this.ownerCode.equals(other.ownerCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterOwnerEquipment[ownerCode=" + ownerCode + "]";
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

    public List<MasterTarifSharing> getMasterTarifSharingList() {
        return masterTarifSharingList;
    }

    public void setMasterTarifSharingList(List<MasterTarifSharing> masterTarifSharingList) {
        this.masterTarifSharingList = masterTarifSharingList;
    }
}
