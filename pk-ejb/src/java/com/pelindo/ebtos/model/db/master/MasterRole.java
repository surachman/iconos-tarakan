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
import javax.persistence.FetchType;
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
@Table(name = "m_role")
@NamedQueries({
    @NamedQuery(name = "MasterRole.findAll", query = "SELECT m FROM MasterRole m"),
    @NamedQuery(name = "MasterRole.findRolesByView", query = "SELECT m FROM MasterRole m WHERE m.masterView.id = :id"),
    @NamedQuery(name = "MasterRole.findById", query = "SELECT m FROM MasterRole m WHERE m.id = :id")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterRole.Native.findGroupsByView", query = "SELECT mug.group_name FROM m_role mr, m_user_group mug WHERE mr.id_view=? AND mr.id_group = mug.id")})
public class MasterRole implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "id_view", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch=FetchType.LAZY)
    private MasterView masterView;
    @JoinColumn(name = "id_group", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private MasterUserGroup masterUserGroup;
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

    public MasterRole() {
    }

    public MasterRole(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MasterView getMasterView() {
        return masterView;
    }

    public void setMasterView(MasterView masterView) {
        this.masterView = masterView;
    }

    public MasterUserGroup getMasterUserGroup() {
        return masterUserGroup;
    }

    public void setMasterUserGroup(MasterUserGroup masterUserGroup) {
        this.masterUserGroup = masterUserGroup;
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
        if (!(object instanceof MasterRole)) {
            return false;
        }
        MasterRole other = (MasterRole) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterRole[id=" + id + "]";
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
