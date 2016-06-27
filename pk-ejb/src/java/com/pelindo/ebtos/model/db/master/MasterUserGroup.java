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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "m_user_group", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"group_name"})})
@NamedQueries({
    @NamedQuery(name = "MasterUserGroup.findAll", query = "SELECT m FROM MasterUserGroup m"),
    @NamedQuery(name = "MasterUserGroup.findAllForView", query = "SELECT m FROM MasterUserGroup m WHERE m.id NOT IN (SELECT mr.masterUserGroup.id FROM MasterRole mr WHERE mr.masterView.id = :id)"),
    @NamedQuery(name = "MasterUserGroup.findById", query = "SELECT m FROM MasterUserGroup m WHERE m.id = :id"),
    @NamedQuery(name = "MasterUserGroup.findGroupsByView", query = "SELECT m.masterUserGroup FROM MasterRole m WHERE m.masterView.id = :id"),
    @NamedQuery(name = "MasterUserGroup.findByGroup", query = "SELECT m FROM MasterUserGroup m WHERE m.group = :group")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterUserGroup.Native.findLikeName", query = 
"SELECT p.id " 
+"  p.group_name " 
+"FROM m_user_group p " 
+"WHERE " 
+" UPPER(p.group_name) LIKE ('%' " 
+"  || UPPER(?) " 
+"  || '%') " 
+"AND ROWNUM < 11")})
public class MasterUserGroup implements Serializable, EntityAuditable {
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
    private Integer id;
    @Basic(optional = false)
    @Column(name = "group_name", nullable = false, length = 2147483647)
    private String group;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterUserGroup")
    private List<MasterRole> masterRoleList;
 
    public MasterUserGroup() {
    }

    public MasterUserGroup(Integer id) {
        this.id = id;
    }

    public MasterUserGroup(Integer id, String group) {
        this.id = id;
        this.group = group;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<MasterRole> getMasterRoleList() {
        return masterRoleList;
    }

    public void setMasterRoleList(List<MasterRole> masterRoleList) {
        this.masterRoleList = masterRoleList;
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
        if (!(object instanceof MasterUserGroup)) {
            return false;
        }
        MasterUserGroup other = (MasterUserGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterUserGroup[id=" + id + "]";
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
