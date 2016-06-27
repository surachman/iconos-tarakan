/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.ServiceIdleTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Table(name = "m_idletime")
@NamedQueries({
    @NamedQuery(name = "MasterIdletime.findAll", query = "SELECT m FROM MasterIdletime m"),
    @NamedQuery(name = "MasterIdletime.findById", query = "SELECT m FROM MasterIdletime m WHERE m.idleTimeCode = :id"),
    @NamedQuery(name = "MasterIdletime.findByName", query = "SELECT m FROM MasterIdletime m WHERE m.name = :name")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterIdletime.Native.findAllMasterIdletime" , query = "SELECT i.idle_time_code, t.name, i.name FROM m_idletime i, m_idletimetype t WHERE i.id_type=t.id_type"),
    @NamedNativeQuery(name ="MasterIdletime.Native.findAllMasterIdletimeByDelete",query="SELECT v.id_type FROM m_idletime v, m_idletimetype p WHERE v.id_type = p.id_type AND v.id_type = ?"),
     @NamedNativeQuery(name ="MasterIdletime.Native.findAllMasterIdletimeById",query="SELECT v.idle_time_code,v.name FROM m_idletime v, m_idletimetype p WHERE v.id_type = p.id_type AND v.id_type = ?")})
public class MasterIdletime implements Serializable, EntityAuditable {
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
    @Column(name = "idle_time_code", nullable = false)
    private Integer idleTimeCode;
    @Column(name = "name", length = 256)
    private String name;
    @JoinColumn(name = "id_type", referencedColumnName = "id_type")
    @ManyToOne
    private MasterIdletimetype masterIdletimetype;
    @OneToMany(mappedBy = "masterIdletime")
    private List<ServiceIdleTime> serviceIdleTimeList;

    public MasterIdletime() {
    }

    public MasterIdletime(Integer idleTimeCode) {
        this.idleTimeCode = idleTimeCode;
    }

    public Integer getIdleTimeCode() {
        return idleTimeCode;
    }

    public void setIdleTimeCode(Integer idleTimeCode) {
        this.idleTimeCode = idleTimeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MasterIdletimetype getMasterIdletimetype() {
        return masterIdletimetype;
    }

    public void setMasterIdletimetype(MasterIdletimetype masterIdletimetype) {
        this.masterIdletimetype = masterIdletimetype;
    }

    public List<ServiceIdleTime> getServiceIdleTimeList() {
        return serviceIdleTimeList;
    }

    public void setServiceIdleTimeList(List<ServiceIdleTime> serviceIdleTimeList) {
        this.serviceIdleTimeList = serviceIdleTimeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idleTimeCode != null ? idleTimeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterIdletime)) {
            return false;
        }
        MasterIdletime other = (MasterIdletime) object;
        if ((this.idleTimeCode == null && other.idleTimeCode != null) || (this.idleTimeCode != null && !this.idleTimeCode.equals(other.idleTimeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterIdletime[id=" + idleTimeCode + "]";
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
