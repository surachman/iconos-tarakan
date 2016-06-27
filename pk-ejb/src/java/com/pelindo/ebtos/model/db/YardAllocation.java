/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dycode-java
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "yard_allocation")
@NamedQueries({
    @NamedQuery(name = "YardAllocation.findAll", query = "SELECT y FROM YardAllocation y"),
    @NamedQuery(name = "YardAllocation.findById", query = "SELECT y FROM YardAllocation y WHERE y.id = :id"),
    @NamedQuery(name = "YardAllocation.findByBlock", query = "SELECT y FROM YardAllocation y WHERE y.block = :block"),
    @NamedQuery(name = "YardAllocation.findByFrSlot", query = "SELECT y FROM YardAllocation y WHERE y.frSlot = :frSlot"),
    @NamedQuery(name = "YardAllocation.findByToSlot", query = "SELECT y FROM YardAllocation y WHERE y.toSlot = :toSlot"),
    @NamedQuery(name = "YardAllocation.findByFrRow", query = "SELECT y FROM YardAllocation y WHERE y.frRow = :frRow"),
    @NamedQuery(name = "YardAllocation.findByToRow", query = "SELECT y FROM YardAllocation y WHERE y.toRow = :toRow"),
    @NamedQuery(name = "YardAllocation.findByYardAllocationFilter", query = "SELECT y FROM YardAllocation y WHERE y.yardAllocationFilter = :yardAllocationFilter")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "YardAllocation.Native.findAllByYardAllocationFilter", query = "SELECT * FROM yard_allocation WHERE yard_allocation_filter = ?"),
    @NamedNativeQuery(name = "YardAllocation.Native.truncate", query = "TRUNCATE table yard_allocation"),
    @NamedNativeQuery(name = "YardAllocation.Native.deleteByIdConstraint", query = "DELETE FROM yard_allocation WHERE yard_allocation_filter = ?"),
    @NamedNativeQuery(name = "YardAllocation.Native.unFinishBayPlanDischargeByPPKB", query = "SELECT DISTINCT id FROM yard_allocation WHERE yard_allocation_filter IN (SELECT id FROM yard_allocation_filter WHERE no_ppkb=?)")
})
public class YardAllocation implements Serializable, EntityAuditable {
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
    private String id;
    @Column(name = "block", length = 5)
    private String block;
    @Column(name = "fr_slot")
    private Short frSlot = 1;
    @Column(name = "to_slot")
    private Short toSlot = 1;
    @Column(name = "fr_row")
    private Short frRow = 1;
    @Column(name = "to_row")
    private Short toRow = 1;
    @Column(name = "count_allocation")
    private Integer countAllocation;
    @Column(name = "yard_allocation_filter")
    private Integer yardAllocationFilter;
    @Column(name = "is_transhipment", nullable=false)
    private String isTranshipment = "FALSE";
    @Column(name = "is_shifting", nullable = false)
    private String isShifting = "FALSE";
    public YardAllocation() {
    }

    public YardAllocation(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public Short getFrSlot() {
        return frSlot;
    }

    public void setFrSlot(Short frSlot) {
        this.frSlot = frSlot;
    }

    public Short getToSlot() {
        return toSlot;
    }

    public void setToSlot(Short toSlot) {
        this.toSlot = toSlot;
    }

    public Short getFrRow() {
        return frRow;
    }

    public void setFrRow(Short frRow) {
        this.frRow = frRow;
    }

    public Short getToRow() {
        return toRow;
    }

    public void setToRow(Short toRow) {
        this.toRow = toRow;
    }

    public Integer getCountAllocation() {
        return countAllocation;
    }

    public void setCountAllocation(Integer countAllocation) {
        this.countAllocation = countAllocation;
    }

    public Integer getYardAllocationFilter() {
        return yardAllocationFilter;
    }

    public void setYardAllocationFilter(Integer yardAllocationFilter) {
        this.yardAllocationFilter = yardAllocationFilter;
    }

    public String getIsTranshipment() {
        return isTranshipment;
    }

    public void setIsTranshipment(String isTranshipment) {
        this.isTranshipment = isTranshipment;
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
        if (!(object instanceof YardAllocation)) {
            return false;
        }
        YardAllocation other = (YardAllocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.YardAllocation[id=" + id + "]";
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

    public String getIsShifting() {
        return isShifting;
    }

    public void setIsShifting(String isShifting) {
        this.isShifting = isShifting;
    }
    
}
