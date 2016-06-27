/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author arie
 */
@Entity
@Table(name = "m_vehicle_type")
@NamedQueries({
    @NamedQuery(name = "MasterVehicleType.findAll", query = "SELECT m FROM MasterVehicleType m"),
    @NamedQuery(name = "MasterVehicleType.findByVehicleTypeCode", query = "SELECT m FROM MasterVehicleType m WHERE m.vehicleTypeCode = :vehicleTypeCode"),
    @NamedQuery(name = "MasterVehicleType.findByName", query = "SELECT m FROM MasterVehicleType m WHERE m.name = :name"),
    @NamedQuery(name = "MasterVehicleType.findByTonage", query = "SELECT m FROM MasterVehicleType m WHERE m.tonage = :tonage"),
    @NamedQuery(name = "MasterVehicleType.findByCreatedBy", query = "SELECT m FROM MasterVehicleType m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MasterVehicleType.findByCreatedDate", query = "SELECT m FROM MasterVehicleType m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MasterVehicleType.findByModifiedBy", query = "SELECT m FROM MasterVehicleType m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MasterVehicleType.findByModifiedDate", query = "SELECT m FROM MasterVehicleType m WHERE m.modifiedDate = :modifiedDate")})
public class MasterVehicleType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "vehicle_type_code")
    private String vehicleTypeCode;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "tonage")
    private short tonage;
    @Basic(optional = false)
    @Column(name = "created_by")
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterVehicleType")
    private Collection<MasterVehicle> masterVehicleCollection;

    public MasterVehicleType() {
    }

    public MasterVehicleType(String vehicleTypeCode) {
        this.vehicleTypeCode = vehicleTypeCode;
    }

    public MasterVehicleType(String vehicleTypeCode, String name, short tonage, String createdBy, Date createdDate) {
        this.vehicleTypeCode = vehicleTypeCode;
        this.name = name;
        this.tonage = tonage;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public String getVehicleTypeCode() {
        return vehicleTypeCode;
    }

    public void setVehicleTypeCode(String vehicleTypeCode) {
        this.vehicleTypeCode = vehicleTypeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getTonage() {
        return tonage;
    }

    public void setTonage(short tonage) {
        this.tonage = tonage;
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

    public Collection<MasterVehicle> getMasterVehicleCollection() {
        return masterVehicleCollection;
    }

    public void setMasterVehicleCollection(Collection<MasterVehicle> masterVehicleCollection) {
        this.masterVehicleCollection = masterVehicleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vehicleTypeCode != null ? vehicleTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterVehicleType)) {
            return false;
        }
        MasterVehicleType other = (MasterVehicleType) object;
        if ((this.vehicleTypeCode == null && other.vehicleTypeCode != null) || (this.vehicleTypeCode != null && !this.vehicleTypeCode.equals(other.vehicleTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterVehicleType[vehicleTypeCode=" + vehicleTypeCode + "]";
    }

}
