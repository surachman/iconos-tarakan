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
@Table(name = "m_vessel_type")
@NamedQueries({
    @NamedQuery(name = "MasterVesselType.findAll", query = "SELECT m FROM MasterVesselType m"),
    @NamedQuery(name = "MasterVesselType.findByVesselTypeCode", query = "SELECT m FROM MasterVesselType m WHERE m.vesselTypeCode = :vesselTypeCode"),
    @NamedQuery(name = "MasterVesselType.findByName", query = "SELECT m FROM MasterVesselType m WHERE m.name = :name"),
    @NamedQuery(name = "MasterVesselType.findByTonage", query = "SELECT m FROM MasterVesselType m WHERE m.tonage = :tonage")})

    @NamedNativeQueries({
    @NamedNativeQuery(name ="MasterVessel.Native.findMasterVesselType",query="SELECT m.vessel_type_code,m.name,m.tonage FROM m_vessel_type m")})
public class MasterVesselType implements Serializable, EntityAuditable {
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "vessel_type_code", nullable = false)
    private Integer vesselTypeCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic(optional = false)
    @Column(name = "tonage", nullable = false)
    private short tonage;
    @OneToMany(mappedBy = "masterVesselType")
    private List<MasterVessel> masterVesselList;

    public MasterVesselType() {
    }

    public MasterVesselType(Integer vesselTypeCode) {
        this.vesselTypeCode = vesselTypeCode;
    }

    public MasterVesselType(Integer vesselTypeCode, String name, short tonage) {
        this.vesselTypeCode = vesselTypeCode;
        this.name = name;
        this.tonage = tonage;
    }

    public Integer getVesselTypeCode() {
        return vesselTypeCode;
    }

    public void setVesselTypeCode(Integer vesselTypeCode) {
        this.vesselTypeCode = vesselTypeCode;
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

    public List<MasterVessel> getMasterVesselList() {
        return masterVesselList;
    }

    public void setMasterVesselList(List<MasterVessel> masterVesselList) {
        this.masterVesselList = masterVesselList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vesselTypeCode != null ? vesselTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterVesselType)) {
            return false;
        }
        MasterVesselType other = (MasterVesselType) object;
        if ((this.vesselTypeCode == null && other.vesselTypeCode != null) || (this.vesselTypeCode != null && !this.vesselTypeCode.equals(other.vesselTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterVesselType[vesselTypeCode=" + vesselTypeCode + "]";
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
