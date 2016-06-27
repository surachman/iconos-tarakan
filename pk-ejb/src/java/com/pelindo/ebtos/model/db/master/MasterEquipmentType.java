/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_equipment_type")
@NamedQueries({
    @NamedQuery(name = "MasterEquipmentType.findAll", query = "SELECT m FROM MasterEquipmentType m"),
    @NamedQuery(name = "MasterEquipmentType.findByEquipmentTypeCode", query = "SELECT m FROM MasterEquipmentType m WHERE m.equipmentTypeCode = :equipmentTypeCode"),
    @NamedQuery(name = "MasterEquipmentType.findByName", query = "SELECT m FROM MasterEquipmentType m WHERE m.name = :name")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterEquipmentType.Native.findAll", query = "SELECT * FROM m_equipment_type")
})
public class MasterEquipmentType implements Serializable, EntityAuditable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "equipment_type_code", nullable = false, length = 10)
    private String equipmentTypeCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
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

    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterEquipmentType")
    private List<MasterEquipment> masterEquipmentList;

    public MasterEquipmentType() {
    }

    public MasterEquipmentType(String equipmentTypeCode) {
        this.equipmentTypeCode = equipmentTypeCode;
    }

    public MasterEquipmentType(String equipmentTypeCode, String name) {
        this.equipmentTypeCode = equipmentTypeCode;
        this.name = name;
    }

    public String getEquipmentTypeCode() {
        return equipmentTypeCode;
    }

    public void setEquipmentTypeCode(String equipmentTypeCode) {
        this.equipmentTypeCode = equipmentTypeCode;
    }

    /**
     * @return the masterEquipmentList
     */
    public List<MasterEquipment> getMasterEquipmentList() {
        return masterEquipmentList;
    }

    /**
     * @param masterEquipmentList the masterEquipmentList to set
     */
    public void setMasterEquipmentList(List<MasterEquipment> masterEquipmentList) {
        this.masterEquipmentList = masterEquipmentList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (equipmentTypeCode != null ? equipmentTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterEquipmentType)) {
            return false;
        }
        MasterEquipmentType other = (MasterEquipmentType) object;
        if ((this.equipmentTypeCode == null && other.equipmentTypeCode != null) || (this.equipmentTypeCode != null && !this.equipmentTypeCode.equals(other.equipmentTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterEquipmentType[equipmentTypeCode=" + equipmentTypeCode + "]";
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
