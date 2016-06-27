/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.ServiceGateReceiving;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "m_cont_damage")
@NamedQueries({
    @NamedQuery(name = "MasterContDamage.findAll", query = "SELECT m FROM MasterContDamage m ORDER BY m.id DESC"),
    @NamedQuery(name = "MasterContDamage.findById", query = "SELECT m FROM MasterContDamage m WHERE m.id = :id"),
    @NamedQuery(name = "MasterContDamage.findByName", query = "SELECT m FROM MasterContDamage m WHERE m.name = :name"),
    @NamedQuery(name = "MasterContDamage.findByDescription", query = "SELECT m FROM MasterContDamage m WHERE m.description = :description")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterContDamage.Native.findMasterContDamages", query = "SELECT m.id,m.name from m_cont_damage m ORDER BY m.id DESC")})
public class MasterContDamage implements Serializable, EntityAuditable {
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
    @Column(name = "id", nullable = false, length = 2)
    private String id;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "description", length = 50)
    private String description;
    @OneToMany(mappedBy = "masterContDamage")
    private List<ServiceGateReceiving> serviceGateReceivingList;

    public MasterContDamage() {
    }

    public MasterContDamage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ServiceGateReceiving> getServiceGateReceivingList() {
        return serviceGateReceivingList;
    }

    public void setServiceGateReceivingList(List<ServiceGateReceiving> serviceGateReceivingList) {
        this.serviceGateReceivingList = serviceGateReceivingList;
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
        if (!(object instanceof MasterContDamage)) {
            return false;
        }
        MasterContDamage other = (MasterContDamage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterContDamage[id=" + id + "]";
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
