/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_device_registration", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"equip_code"})})
@NamedQueries({
    @NamedQuery(name = "MasterDeviceRegistration.findAll", query = "SELECT m FROM MasterDeviceRegistration m"),
    @NamedQuery(name = "MasterDeviceRegistration.findByDeviceId", query = "SELECT m FROM MasterDeviceRegistration m WHERE m.deviceId = :deviceId")})
public class MasterDeviceRegistration implements Serializable, EntityAuditable {
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
    @Column(name = "device_id", nullable = false, length = 2147483647)
    private String deviceId;
    @JoinColumn(name = "equip_code", referencedColumnName = "equip_code", nullable = false)
    @OneToOne(optional = false)
    private MasterEquipment masterEquipment;
    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private MasterDevice masterDevice;

    public MasterDeviceRegistration() {
    }

    public MasterDeviceRegistration(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public MasterEquipment getMasterEquipment() {
        return masterEquipment;
    }

    public void setMasterEquipment(MasterEquipment masterEquipment) {
        this.masterEquipment = masterEquipment;
    }

    public MasterDevice getMasterDevice() {
        return masterDevice;
    }

    public void setMasterDevice(MasterDevice masterDevice) {
        this.masterDevice = masterDevice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deviceId != null ? deviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterDeviceRegistration)) {
            return false;
        }
        MasterDeviceRegistration other = (MasterDeviceRegistration) object;
        if ((this.deviceId == null && other.deviceId != null) || (this.deviceId != null && !this.deviceId.equals(other.deviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterDeviceRegistration[deviceId=" + deviceId + "]";
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
