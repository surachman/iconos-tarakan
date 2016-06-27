/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.EntityAuditable;
import com.qtasnim.persistence.AuditTrailEntityListener;
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
@Table(name = "m_activity_sharing")
@EntityListeners({AuditTrailEntityListener.class})
@NamedQueries({
    @NamedQuery(name = "MasterActivitySharing.findAll", query = "SELECT m FROM MasterActivitySharing m"),
    @NamedQuery(name = "MasterActivitySharing.findByActivityCode", query = "SELECT m FROM MasterActivitySharing m WHERE m.activityCode = :activityCode"),
    @NamedQuery(name = "MasterActivitySharing.findByDescription", query = "SELECT m FROM MasterActivitySharing m WHERE m.description = :description"),
    @NamedQuery(name = "MasterActivitySharing.findByCreatedBy", query = "SELECT m FROM MasterActivitySharing m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MasterActivitySharing.findByCreatedDate", query = "SELECT m FROM MasterActivitySharing m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MasterActivitySharing.findByModifiedBy", query = "SELECT m FROM MasterActivitySharing m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MasterActivitySharing.findByModifiedDate", query = "SELECT m FROM MasterActivitySharing m WHERE m.modifiedDate = :modifiedDate")})
public class MasterActivitySharing implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "activity_code", nullable = false, length = 10)
    private String activityCode;
    @Column(name = "description", length = 256)
    private String description;
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
    @OneToMany(mappedBy = "masterActivitySharing")
    private List<MasterTarifSharing> masterTarifSharingList;

    public MasterActivitySharing() {
    }

    public MasterActivitySharing(String activityCode) {
        this.activityCode = activityCode;
    }

    public MasterActivitySharing(String activityCode, String createdBy, Date createdDate) {
        this.activityCode = activityCode;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<MasterTarifSharing> getMasterTarifSharingList() {
        return masterTarifSharingList;
    }

    public void setMasterTarifSharingList(List<MasterTarifSharing> masterTarifSharingList) {
        this.masterTarifSharingList = masterTarifSharingList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (activityCode != null ? activityCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterActivitySharing)) {
            return false;
        }
        MasterActivitySharing other = (MasterActivitySharing) object;
        if ((this.activityCode == null && other.activityCode != null) || (this.activityCode != null && !this.activityCode.equals(other.activityCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterActivitySharing[activityCode=" + activityCode + "]";
    }

}
