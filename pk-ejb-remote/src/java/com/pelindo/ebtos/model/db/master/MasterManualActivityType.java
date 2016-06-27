/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db.master;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Dyware-Dev01
 */
@Entity
@Table(name = "m_manual_activity_type")
@NamedQueries({
    @NamedQuery(name = "MasterManualActivityType.findAll", query = "SELECT m FROM MasterManualActivityType m"),
    @NamedQuery(name = "MasterManualActivityType.findById", query = "SELECT m FROM MasterManualActivityType m WHERE m.id = :id"),
    @NamedQuery(name = "MasterManualActivityType.findByActivityName", query = "SELECT m FROM MasterManualActivityType m WHERE m.activityName = :activityName"),
    @NamedQuery(name = "MasterManualActivityType.findByDescription", query = "SELECT m FROM MasterManualActivityType m WHERE m.description = :description")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterManualActivityType.Native.findAllNative", query = "SELECT id, activity_name FROM m_manual_activity_type ORDER BY ID")
})
public class MasterManualActivityType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "activity_name", nullable = false, length = 256)
    private String activityName;
    @Column(name = "description", length = 256)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mManualActivityType")
    private List<MasterManualActivity> mManualActivityList;

    public MasterManualActivityType() {
    }

    public MasterManualActivityType(Integer id) {
        this.id = id;
    }

    public MasterManualActivityType(Integer id, String activityName) {
        this.id = id;
        this.activityName = activityName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MasterManualActivity> getMManualActivityList() {
        return mManualActivityList;
    }

    public void setMManualActivityList(List<MasterManualActivity> mManualActivityList) {
        this.mManualActivityList = mManualActivityList;
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
        if (!(object instanceof MasterManualActivityType)) {
            return false;
        }
        MasterManualActivityType other = (MasterManualActivityType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterManualActivityType[id=" + id + "]";
    }
}
