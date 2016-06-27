/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db.master;

import com.pelindo.ebtos.model.db.PlanningVessel;
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
@Table(name = "m_dock")
@NamedQueries({
    @NamedQuery(name = "MasterDock.findAll", query = "SELECT m FROM MasterDock m"),
    @NamedQuery(name = "MasterDock.findByDockCode", query = "SELECT m FROM MasterDock m WHERE m.dockCode = :dockCode"),
    @NamedQuery(name = "MasterDock.findByName", query = "SELECT m FROM MasterDock m WHERE m.name = :name"),
    @NamedQuery(name = "MasterDock.findByFrMeter", query = "SELECT m FROM MasterDock m WHERE m.frMeter = :frMeter"),
    @NamedQuery(name = "MasterDock.findByToMeter", query = "SELECT m FROM MasterDock m WHERE m.toMeter = :toMeter"),
    @NamedQuery(name = "MasterDock.findByLws", query = "SELECT m FROM MasterDock m WHERE m.lws = :lws"),
    @NamedQuery(name = "MasterDock.findByAllowedDraft", query = "SELECT m FROM MasterDock m WHERE m.allowedDraft = :allowedDraft")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterDock.Native.findDocks", query = "SELECT dock_code FROM m_dock ORDER BY dock_code ASC")
})
public class MasterDock implements Serializable, EntityAuditable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dock_code", nullable = false, length = 10)
    private String dockCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "fr_meter")
    private Short frMeter;
    @Column(name = "to_meter")
    private Short toMeter;
    @Basic(optional = false)
    @Column(name = "lws", nullable = false)
    private short lws;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "allowed_draft", nullable = false)
    private short allowedDraft;
    @OneToMany(mappedBy = "masterDock")
    private List<PlanningVessel> planningVesselList;

    public MasterDock() {
    }

    public MasterDock(String dockCode) {
        this.dockCode = dockCode;
    }

    public MasterDock(String dockCode, String name, short lws, short allowedDraft) {
        this.dockCode = dockCode;
        this.name = name;
        this.lws = lws;
        this.allowedDraft = allowedDraft;
    }

    public String getDockCode() {
        return dockCode;
    }

    public void setDockCode(String dockCode) {
        this.dockCode = dockCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getFrMeter() {
        return frMeter;
    }

    public void setFrMeter(Short frMeter) {
        this.frMeter = frMeter;
    }

    public Short getToMeter() {
        return toMeter;
    }

    public void setToMeter(Short toMeter) {
        this.toMeter = toMeter;
    }

    public short getLws() {
        return lws;
    }

    public void setLws(short lws) {
        this.lws = lws;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public short getAllowedDraft() {
        return allowedDraft;
    }

    public void setAllowedDraft(short allowedDraft) {
        this.allowedDraft = allowedDraft;
    }

    public List<PlanningVessel> getPlanningVesselList() {
        return planningVesselList;
    }

    public void setPlanningVesselList(List<PlanningVessel> planningVesselList) {
        this.planningVesselList = planningVesselList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dockCode != null ? dockCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterDock)) {
            return false;
        }
        MasterDock other = (MasterDock) object;
        if ((this.dockCode == null && other.dockCode != null) || (this.dockCode != null && !this.dockCode.equals(other.dockCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterDock[dockCode=" + dockCode + "]";
    }
}
