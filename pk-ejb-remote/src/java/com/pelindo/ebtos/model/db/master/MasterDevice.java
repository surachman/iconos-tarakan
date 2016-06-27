/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
@Table(name = "m_device")
@NamedQueries({
    @NamedQuery(name = "MasterDevice.findAll", query = "SELECT m FROM MasterDevice m"),
    @NamedQuery(name = "MasterDevice.findByDeviceId", query = "SELECT m FROM MasterDevice m WHERE m.deviceId = :deviceId"),
    @NamedQuery(name = "MasterDevice.findByMachineNo", query = "SELECT m FROM MasterDevice m WHERE m.machineNo = :machineNo"),
    @NamedQuery(name = "MasterDevice.findByDescriptions", query = "SELECT m FROM MasterDevice m WHERE m.descriptions = :descriptions"),
    @NamedQuery(name = "MasterDevice.findByMerk", query = "SELECT m FROM MasterDevice m WHERE m.merk = :merk"),
    @NamedQuery(name = "MasterDevice.findBySeri", query = "SELECT m FROM MasterDevice m WHERE m.seri = :seri"),
    @NamedQuery(name = "MasterDevice.findByPurchaseDate", query = "SELECT m FROM MasterDevice m WHERE m.purchaseDate = :purchaseDate")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterDevice.Native.CheckAppDevice", query = "SELECT md.app_id, me.name "
                                                                        + "FROM m_device md "
                                                                                + "LEFT JOIN (m_device_registration mdr JOIN m_equipment me ON (mdr.equip_code=me.equip_code)) ON (md.device_id=mdr.device_id) "
                                                                        + "WHERE md.app_id = ?")
})
public class MasterDevice implements Serializable, EntityAuditable {
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
    @Column(name = "device_id", nullable = false, length = 20)
    private String deviceId;
    @Basic(optional = false)
    @Column(name = "machine_no", nullable = false, length = 50)
    private String machineNo;
    @Basic(optional = false)
    @Column(name = "descriptions", nullable = false, length = 50)
    private String descriptions;
    @Basic(optional = false)
    @Column(name = "merk", nullable = false, length = 50)
    private String merk;
    @Basic(optional = false)
    @Column(name = "seri", nullable = false, length = 50)
    private String seri;
    @Column(name = "app_id")
    private String appId;
    @Basic(optional = false)
    @Column(name = "purchase_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;
    @Column(name = "lo")
    private BigInteger lo;
    @Column(name = "la")
    private BigInteger la;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "masterDevice")
    private MasterDeviceRegistration masterDeviceRegistration;
    @JoinColumn(name = "device_type_code", referencedColumnName = "device_type_code")
    @ManyToOne
    private MasterDeviceType masterDeviceType;

    public MasterDevice() {
    }

    public MasterDevice(String deviceId) {
        this.deviceId = deviceId;
    }

    public MasterDevice(String deviceId, String machineNo, String descriptions, String merk, String seri, Date purchaseDate) {
        this.deviceId = deviceId;
        this.machineNo = machineNo;
        this.descriptions = descriptions;
        this.merk = merk;
        this.seri = seri;
        this.purchaseDate = purchaseDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getSeri() {
        return seri;
    }

    public void setSeri(String seri) {
        this.seri = seri;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigInteger getLo() {
        return lo;
    }

    public void setLo(BigInteger lo) {
        this.lo = lo;
    }

    public BigInteger getLa() {
        return la;
    }

    public void setLa(BigInteger la) {
        this.la = la;
    }

    public MasterDeviceRegistration getMasterDeviceRegistration() {
        return masterDeviceRegistration;
    }

    public void setMasterDeviceRegistration(MasterDeviceRegistration masterDeviceRegistration) {
        this.masterDeviceRegistration = masterDeviceRegistration;
    }

    public MasterDeviceType getMasterDeviceType() {
        return masterDeviceType;
    }

    public void setMasterDeviceType(MasterDeviceType masterDeviceType) {
        this.masterDeviceType = masterDeviceType;
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
        if (!(object instanceof MasterDevice)) {
            return false;
        }
        MasterDevice other = (MasterDevice) object;
        if ((this.deviceId == null && other.deviceId != null) || (this.deviceId != null && !this.deviceId.equals(other.deviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterDevice[deviceId=" + deviceId + "]";
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
