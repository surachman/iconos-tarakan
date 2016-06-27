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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "yard_allocation_filter")
@NamedQueries({
    @NamedQuery(name = "YardAllocationFilter.findAll", query = "SELECT y FROM YardAllocationFilter y"),
    @NamedQuery(name = "YardAllocationFilter.findById", query = "SELECT y FROM YardAllocationFilter y WHERE y.id = :id"),
    @NamedQuery(name = "YardAllocationFilter.findByNoPpkb", query = "SELECT y FROM YardAllocationFilter y WHERE y.noPpkb = :noPpkb"),
    @NamedQuery(name = "YardAllocationFilter.findByContSize", query = "SELECT y FROM YardAllocationFilter y WHERE y.contSize = :contSize"),
    @NamedQuery(name = "YardAllocationFilter.findByContType", query = "SELECT y FROM YardAllocationFilter y WHERE y.contType = :contType"),
    @NamedQuery(name = "YardAllocationFilter.findByContStatus", query = "SELECT y FROM YardAllocationFilter y WHERE y.contStatus = :contStatus"),
    @NamedQuery(name = "YardAllocationFilter.findByGrossClass", query = "SELECT y FROM YardAllocationFilter y WHERE y.grossClass = :grossClass"),
    @NamedQuery(name = "YardAllocationFilter.findByCommodity", query = "SELECT y FROM YardAllocationFilter y WHERE y.commodity = :commodity"),
    @NamedQuery(name = "YardAllocationFilter.findByDg", query = "SELECT y FROM YardAllocationFilter y WHERE y.dg = :dg")})
    @NamedNativeQueries({
    @NamedNativeQuery(name = "YardAllocationFilter.Native.yardAllocationFilterfindByNoPpkb", query = "SELECT c.name, f.cont_size, f.cont_status, g.description, null, change(f.dg) dg, f.count, f.allocated, f.id, null as block, change(f.is_import) is_import, change(f.over_size) over_size, c.type_in_general "
                                                                                                    + "FROM yard_allocation_filter f "
                                                                                                            + "JOIN m_container_type c ON (f.cont_type=c.cont_type) "
                                                                                                            + "JOIN m_gross_class g ON (f.gross_class=g.gross_class) "
                                                                                                    + "WHERE f.no_ppkb = ? AND is_transhipment = ? AND is_shifting = ?"),
    @NamedNativeQuery(name = "YardAllocationFilter.Native.truncate", query = "TRUNCATE TABLE yard_allocation_filter"),
    @NamedNativeQuery(name = "YardAllocationFilter.Native.deleteById", query = "DELETE FROM yard_allocation_filter WHERE id = ?"),
    @NamedNativeQuery(name = "YardAllocationFilter.Native.sumCountByPPKB", query = "SELECT SUM (count) FROM yard_allocation_filter WHERE no_ppkb = ?"),
    @NamedNativeQuery(name = "YardAllocationFilter.Native.findById", query = "SELECT c.name, f.cont_size, f.cont_status, g.description, null, f.dg, f.count, f.allocated, f.is_import, f.over_size "
                                                                            + "FROM yard_allocation_filter f "
                                                                                + "JOIN m_container_type c ON (f.cont_type=c.cont_type) "
                                                                                + "JOIN m_gross_class g ON (f.gross_class=g.gross_class) "
                                                                            + "WHERE f.id=?"),
    @NamedNativeQuery(name = "YardAllocationFilter.Native.unFinishBayPlanDischargeByPPKB", query = "SELECT DISTINCT id FROM yard_allocation_filter WHERE no_ppkb=?")
})
public class YardAllocationFilter implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_type")
    private Integer contType;
    @Column(name = "cont_status", length = 10)
    private String contStatus;
    @Column(name = "gross_class", length = 2)
    private String grossClass;
    @Column(name = "commodity", length = 10)
    private String commodity;
    @Column(name = "dg")
    private String dg;
    @Column(name = "count")
    private Integer count;
    @Column(name = "allocated")
    private Integer allocated;
    @Column(name = "is_transhipment", nullable=false)
    private String isTranshipment = "FALSE";
     @Column(name = "is_shifting", nullable = false)
    private String isShifting = "FALSE";
    @Basic(optional = false)
    @Column(name = "is_import", nullable = false)
    private String isImport = "FALSE";
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
    @Basic(optional = false)
    @Column(name = "over_size", nullable = false)
    private String overSize;

    public YardAllocationFilter() {
    }

    public YardAllocationFilter(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public Short getContSize() {
        return contSize;
    }

    public void setContSize(Short contSize) {
        this.contSize = contSize;
    }

    public Integer getContType() {
        return contType;
    }

    public void setContType(Integer contType) {
        this.contType = contType;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public String getGrossClass() {
        return grossClass;
    }

    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getDg() {
        return dg;
    }

    public void setDg(String dg) {
        this.dg = dg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAllocated() {
        return allocated;
    }

    public void setAllocated(Integer allocated) {
        this.allocated = allocated;
    }

    public String getIsTranshipment() {
        return isTranshipment;
    }

    public void setIsTranshipment(String isTranshipment) {
        this.isTranshipment = isTranshipment;
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

    public String getIsImport() {
        return isImport;
    }

    public void setIsImport(String isImport) {
        this.isImport = isImport;
    }

    public String getOverSize() {
        return overSize;
    }

    public void setOverSize(String overSize) {
        this.overSize = overSize;
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
        if (!(object instanceof YardAllocationFilter)) {
            return false;
        }
        YardAllocationFilter other = (YardAllocationFilter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.YardAllocationFilter[id=" + id + "]";
    }
}
