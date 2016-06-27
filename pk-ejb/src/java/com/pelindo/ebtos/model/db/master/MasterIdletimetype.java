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
@Table(name = "m_idletimetype")
@NamedQueries({
    @NamedQuery(name = "MasterIdletimetype.findAll", query = "SELECT m FROM MasterIdletimetype m"),
    @NamedQuery(name = "MasterIdletimetype.findByIdType", query = "SELECT m FROM MasterIdletimetype m WHERE m.idType = :idType"),
    @NamedQuery(name = "MasterIdletimetype.findByName", query = "SELECT m FROM MasterIdletimetype m WHERE m.name = :name")})
public class MasterIdletimetype implements Serializable, EntityAuditable {
    public static final String IT = "IT";
    public static final String NOT = "NOT";
    public static final String ET = "ET";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_type", nullable = false)
    private Integer idType;
    @Column(name = "name", length = 256)
    private String name;
    @Basic(optional = false)
    @Column(name = "type", nullable = false, length = 5)
    private String type;
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
    @OneToMany(mappedBy = "masterIdletimetype")
    private List<ServiceIdleTime> serviceIdleTimeList;
    @OneToMany(mappedBy = "masterIdletimetype")
    private List<MasterIdletime> masterIdletimeList;

    public MasterIdletimetype() {
        type = IT;
    }

    public MasterIdletimetype(Integer idType) {
        this.idType = idType;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ServiceIdleTime> getServiceIdleTimeList() {
        return serviceIdleTimeList;
    }

    public void setServiceIdleTimeList(List<ServiceIdleTime> serviceIdleTimeList) {
        this.serviceIdleTimeList = serviceIdleTimeList;
    }

    public List<MasterIdletime> getMasterIdletimeList() {
        return masterIdletimeList;
    }

    public void setMasterIdletimeList(List<MasterIdletime> masterIdletimeList) {
        this.masterIdletimeList = masterIdletimeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idType != null ? idType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterIdletimetype)) {
            return false;
        }
        MasterIdletimetype other = (MasterIdletimetype) object;
        if ((this.idType == null && other.idType != null) || (this.idType != null && !this.idType.equals(other.idType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterIdletimetype[idType=" + idType + "]";
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
