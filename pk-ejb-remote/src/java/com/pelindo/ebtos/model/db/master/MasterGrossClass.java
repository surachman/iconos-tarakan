/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
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
@Table(name = "m_gross_class")
@NamedQueries({
    @NamedQuery(name = "MasterGrossClass.findAll", query = "SELECT m FROM MasterGrossClass m"),
    @NamedQuery(name = "MasterGrossClass.findByGrossClass", query = "SELECT m FROM MasterGrossClass m WHERE m.grossClass = :grossClass"),
    @NamedQuery(name = "MasterGrossClass.findByDescription", query = "SELECT m FROM MasterGrossClass m WHERE m.description = :description")})
@NamedNativeQueries({
    @NamedNativeQuery(name="MasterGrossClass.Native.findGrossClasses",query="SELECT * FROM m_gross_class")
})
public class MasterGrossClass implements Serializable, EntityAuditable {
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
    @Column(name = "gross_class", nullable = false, length = 5)
    private String grossClass;
    @Basic(optional = false)
    @Column(name = "description", nullable = false, length = 20)
    private String description;
    @Column(name = "color", nullable = false, length = 10)
    private String color;
    @OneToMany(mappedBy = "masterGrossClass")
    private List<PlanningReceivingAllocation> planningReceivingAllocationList;

    public MasterGrossClass() {
    }

    public MasterGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    public MasterGrossClass(String grossClass, String description) {
        this.grossClass = grossClass;
        this.description = description;
    }

    public String getGrossClass() {
        return grossClass;
    }

    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    public List<PlanningReceivingAllocation> getPlanningReceivingAllocationList() {
        return planningReceivingAllocationList;
    }

    public void setPlanningReceivingAllocationList(List<PlanningReceivingAllocation> planningReceivingAllocationList) {
        this.planningReceivingAllocationList = planningReceivingAllocationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grossClass != null ? grossClass.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterGrossClass)) {
            return false;
        }
        MasterGrossClass other = (MasterGrossClass) object;
        if ((this.grossClass == null && other.grossClass != null) || (this.grossClass != null && !this.grossClass.equals(other.grossClass))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterGrossClass[grossClass=" + grossClass + "]";
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
