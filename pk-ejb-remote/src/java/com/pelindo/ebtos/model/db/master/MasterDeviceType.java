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
import javax.persistence.Id;
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
@Table(name = "m_device_type")
@NamedQueries({
    @NamedQuery(name = "MasterDeviceType.findAll", query = "SELECT m FROM MasterDeviceType m"),
    @NamedQuery(name = "MasterDeviceType.findByDeviceTypeCode", query = "SELECT m FROM MasterDeviceType m WHERE m.deviceTypeCode = :deviceTypeCode"),
    @NamedQuery(name = "MasterDeviceType.findByName", query = "SELECT m FROM MasterDeviceType m WHERE m.name = :name")})
public class MasterDeviceType implements Serializable, EntityAuditable {
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
    @Column(name = "device_type_code", nullable = false)
    private Integer deviceTypeCode;
    @Column(name = "name", length = 50)
    private String name;
    @OneToMany(mappedBy = "masterDeviceType")
    private List<MasterDevice> masterDeviceList;

    public MasterDeviceType() {
    }

    public MasterDeviceType(Integer deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode;
    }

    public Integer getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(Integer deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MasterDevice> getMasterDeviceList() {
        return masterDeviceList;
    }

    public void setMasterDeviceList(List<MasterDevice> masterDeviceList) {
        this.masterDeviceList = masterDeviceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deviceTypeCode != null ? deviceTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterDeviceType)) {
            return false;
        }
        MasterDeviceType other = (MasterDeviceType) object;
        if ((this.deviceTypeCode == null && other.deviceTypeCode != null) || (this.deviceTypeCode != null && !this.deviceTypeCode.equals(other.deviceTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterDeviceType[deviceTypeCode=" + deviceTypeCode + "]";
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
