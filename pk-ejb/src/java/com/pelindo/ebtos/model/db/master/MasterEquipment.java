/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PengisianBbm;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_equipment")
@NamedQueries({
    @NamedQuery(name = "MasterEquipment.findAll", query = "SELECT m FROM MasterEquipment m"),
    @NamedQuery(name = "MasterEquipment.findByEquipCode", query = "SELECT m FROM MasterEquipment m WHERE m.masterEquipmentType.equipmentTypeCode = :equipCode"),
    @NamedQuery(name = "MasterEquipment.findByName", query = "SELECT m FROM MasterEquipment m WHERE m.name = :name"),
    @NamedQuery(name = "MasterEquipment.findByCapacity", query = "SELECT m FROM MasterEquipment m WHERE m.capacity = :capacity"),
    @NamedQuery(name = "MasterEquipment.findByOwner", query = "SELECT m FROM MasterEquipment m WHERE m.owner = :owner"),
    @NamedQuery(name = "MasterEquipment.findByMerk", query = "SELECT m FROM MasterEquipment m WHERE m.merk = :merk"),
    @NamedQuery(name = "MasterEquipment.findByMade", query = "SELECT m FROM MasterEquipment m WHERE m.made = :made")})

    @NamedNativeQueries({
    @NamedNativeQuery(name ="MasterEquipment.Native.findAll", query = "SELECT m.equip_code, m.name, m.capacity, m.owner, m.merk, m.made, t.name, m.owner_code FROM m_equipment m, m_equipment_type t WHERE m.equipment_type_code = t.equipment_type_code"),
    @NamedNativeQuery(name = "MasterEquipment.Native.findCrane", query = "SELECT equip_code FROM m_equipment WHERE equipment_type_code = '001'"),
    @NamedNativeQuery(name = "MasterEquipment.Native.findHt", query = "SELECT equip_code FROM m_equipment WHERE equipment_type_code = '002'"),
    @NamedNativeQuery(name = "MasterEquipment.Native.findTt", query = "SELECT equip_code FROM m_equipment WHERE equipment_type_code = '003'"),
    @NamedNativeQuery(name = "MasterEquipment.Native.findCraneForView", query = "SELECT equip_code, name, capacity, merk, made FROM m_equipment WHERE equipment_type_code = '001'"),
    @NamedNativeQuery(name = "MasterEquipment.Native.findHtForView", query = "SELECT equip_code, name, capacity, merk, made FROM m_equipment WHERE equipment_type_code = '002'"),
    @NamedNativeQuery(name = "MasterEquipment.Native.findTangoForView", query = "SELECT equip_code, name, capacity, merk, made FROM m_equipment WHERE equipment_type_code = '003'"),
    @NamedNativeQuery(name = "MasterEquipment.Native.findCraneExcKapal", query = "SELECT equip_code, name FROM m_equipment WHERE equipment_type_code = '001' AND UPPER(name) NOT LIKE UPPER('%kapal%')"),
    @NamedNativeQuery(name = "MasterEquipment.Native.findOwnerReport", query = "select * from m_equipment where owner_code=?"),
    @NamedNativeQuery(name = "MasterEquipment.Native.findEquipmentSewaAlat", query = "select me.equip_code,me.name,me.owner_code from m_equipment me where (me.equip_code='05101' OR me.equip_code='05102' OR me.equip_code='06101' OR me.equip_code='06102' OR me.equip_code='06103')")})
public class MasterEquipment implements Serializable, EntityAuditable {
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
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "masterEquipment")
    private MasterDeviceRegistration masterDeviceRegistration;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "equip_code", nullable = false, length = 10)
    private String equipCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "capacity")
    private Short capacity;
    @Column(name = "owner")
    private String owner;
    @Column(name = "merk", length = 25)
    private String merk;
    @Column(name = "made", length = 25)
    private String made;
    @Column(name = "equipment_group", length = 25)
    private String equipmentGroup;
    @OneToMany(mappedBy = "masterEquipment")
    private List<Equipment> equipmentList;
    @OneToMany(mappedBy = "masterEquipment")
    private List<PengisianBbm> pengisianBbmList;
    @JoinColumn(name = "owner_code", referencedColumnName = "owner_code")
    @ManyToOne
    private MasterOwnerEquipment masterOwnerEquipment;
    @JoinColumn(name = "equipment_type_code", referencedColumnName = "equipment_type_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterEquipmentType masterEquipmentType;

    public MasterEquipment() {
    }

    public MasterEquipment(String equipCode) {
        this.equipCode = equipCode;
    }

    public MasterEquipment(String equipCode, String name) {
        this.equipCode = equipCode;
        this.name = name;
    }

    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getCapacity() {
        return capacity;
    }

    public void setCapacity(Short capacity) {
        this.capacity = capacity;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getMade() {
        return made;
    }

    public void setMade(String made) {
        this.made = made;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<PengisianBbm> getPengisianBbmList() {
        return pengisianBbmList;
    }

    public void setPengisianBbmList(List<PengisianBbm> pengisianBbmList) {
        this.pengisianBbmList = pengisianBbmList;
    }

    /**
     * @return the masterEquipmentType
     */
    public MasterEquipmentType getMasterEquipmentType() {
        return masterEquipmentType;
    }

    /**
     * @param masterEquipmentType the masterEquipmentType to set
     */
    public void setMasterEquipmentType(MasterEquipmentType masterEquipmentType) {
        this.masterEquipmentType = masterEquipmentType;
    }

    public MasterOwnerEquipment getMasterOwnerEquipment() {
        return masterOwnerEquipment;
    }

    public void setMasterOwnerEquipment(MasterOwnerEquipment masterOwnerEquipment) {
        this.masterOwnerEquipment = masterOwnerEquipment;
    }

    public String getEquipmentGroup() {
        return equipmentGroup;
    }

    public void setEquipmentGroup(String equipmentGroup) {
        this.equipmentGroup = equipmentGroup;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (equipCode != null ? equipCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterEquipment)) {
            return false;
        }
        MasterEquipment other = (MasterEquipment) object;
        if ((this.equipCode == null && other.equipCode != null) || (this.equipCode != null && !this.equipCode.equals(other.equipCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterEquipment[equipCode=" + equipCode + "]";
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

    public MasterDeviceRegistration getMasterDeviceRegistration() {
        return masterDeviceRegistration;
    }

    public void setMasterDeviceRegistration(MasterDeviceRegistration masterDeviceRegistration) {
        this.masterDeviceRegistration = masterDeviceRegistration;
    }
}
