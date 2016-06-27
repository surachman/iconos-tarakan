/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterIdletime;
import com.pelindo.ebtos.model.db.master.MasterIdletimetype;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "service_idle_time")
@NamedQueries({
    @NamedQuery(name = "ServiceIdleTime.findAll", query = "SELECT s FROM ServiceIdleTime s"),
    @NamedQuery(name = "ServiceIdleTime.findById", query = "SELECT s FROM ServiceIdleTime s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceIdleTime.findByDescription", query = "SELECT s FROM ServiceIdleTime s WHERE s.description = :description"),
    @NamedQuery(name = "ServiceIdleTime.findByStartTime", query = "SELECT s FROM ServiceIdleTime s WHERE s.startTime = :startTime"),
    @NamedQuery(name = "ServiceIdleTime.findByEndTime", query = "SELECT s FROM ServiceIdleTime s WHERE s.endTime = :endTime")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceIdleTime.Native.findServiceIdleTimesByPpkb", query = "SELECT m.id_type, m.name, idl.description, idl.start_time,idl.end_time,idl.id,mi.name FROM m_idletimetype m, service_idle_time idl,m_idletime mi,service_vessel sv WHERE m.id_type=mi.id_type AND idl.id_type=m.id_type AND idl.id_type=m.id_type AND idl.no_ppkb=sv.no_ppkb AND mi.idle_time_code=idl.idle_time_code AND idl.no_ppkb=? ORDER BY idl.id DESC")})
public class ServiceIdleTime implements Serializable, EntityAuditable {

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
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "description", length = 256)
    private String description;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @JoinColumn(name = "id_type", referencedColumnName = "id_type")
    @ManyToOne
    private MasterIdletimetype masterIdletimetype;
    @JoinColumn(name = "idle_time_code", referencedColumnName = "idle_time_code")
    @ManyToOne
    private MasterIdletime masterIdletime;
    @Column(name = "equip_code")
    private String equipCode;
    @Column(name = "et_tanggal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date etTanggal;

    public ServiceIdleTime() {
    }

    public ServiceIdleTime(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the noPpkb
     */
    public String getNoPpkb() {
        return noPpkb;
    }

    /**
     * @param noPpkb the noPpkb to set
     */
    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public MasterIdletimetype getMasterIdletimetype() {
        return masterIdletimetype;
    }

    public void setMasterIdletimetype(MasterIdletimetype masterIdletimetype) {
        this.masterIdletimetype = masterIdletimetype;
    }

    public MasterIdletime getMasterIdletime() {
        return masterIdletime;
    }

    public void setMasterIdletime(MasterIdletime masterIdletime) {
        this.masterIdletime = masterIdletime;
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
        if (!(object instanceof ServiceIdleTime)) {
            return false;
        }
        ServiceIdleTime other = (ServiceIdleTime) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceIdleTime[id=" + id + "]";
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

    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    public Date getEtTanggal() {
        return etTanggal;
    }

    public void setEtTanggal(Date etTanggal) {
        this.etTanggal = etTanggal;
    }

}
