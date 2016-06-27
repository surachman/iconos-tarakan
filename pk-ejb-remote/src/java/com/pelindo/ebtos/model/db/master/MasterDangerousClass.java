/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Arrays;
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
 * @author R. Seno Anggoro A
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_dangerous_class")
@NamedQueries({
    @NamedQuery(name = "MasterDangerousClass.findAll", query = "SELECT m FROM MasterDangerousClass m")})
public class MasterDangerousClass implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    public static List<String> affectedToHandling = Arrays.asList("1", "7");

    @Id
    @Basic(optional = false)
    @Column(name = "dangerous_class", nullable = false, length = 10)
    private String dangerousClass;
    @Basic(optional = false)
    @Column(name = "description", nullable = false, length = 2147483647)
    private String description;
    @OneToMany(mappedBy = "masterDangerousClass")
    private List<MasterCommodity> masterCommodityList;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Basic(optional = false)
    @Column(name = "created_by", nullable = false, length = 2147483647)
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_by", length = 2147483647)
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public MasterDangerousClass() {
    }

    public MasterDangerousClass(String dangerousClass) {
        this.dangerousClass = dangerousClass;
    }

    public MasterDangerousClass(String dangerousClass, String description) {
        this.dangerousClass = dangerousClass;
        this.description = description;
    }

    public String getDangerousClass() {
        return dangerousClass;
    }

    public void setDangerousClass(String dangerousClass) {
        this.dangerousClass = dangerousClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MasterCommodity> getMasterCommodityList() {
        return masterCommodityList;
    }

    public void setMasterCommodityList(List<MasterCommodity> masterCommodityList) {
        this.masterCommodityList = masterCommodityList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dangerousClass != null ? dangerousClass.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterDangerousClass)) {
            return false;
        }
        MasterDangerousClass other = (MasterDangerousClass) object;
        if ((this.dangerousClass == null && other.dangerousClass != null) || (this.dangerousClass != null && !this.dangerousClass.equals(other.dangerousClass))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterDangerousClass[dangerousClass=" + dangerousClass + "]";
    }

}
