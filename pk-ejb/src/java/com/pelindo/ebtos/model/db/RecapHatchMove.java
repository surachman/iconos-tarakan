/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "recap_hatch_move")
@NamedQueries({
    @NamedQuery(name = "RecapHatchMove.findAll", query = "SELECT r FROM RecapHatchMove r"),
    @NamedQuery(name = "RecapHatchMove.findById", query = "SELECT r FROM RecapHatchMove r WHERE r.id = :id"),
    @NamedQuery(name = "RecapHatchMove.findByNoPpkb", query = "SELECT r FROM RecapHatchMove r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapHatchMove.findByBentuk3Constraint", query = "SELECT r FROM RecapHatchMove r WHERE r.noPpkb = :noPpkb AND r.crane = :crane"),
    @NamedQuery(name = "RecapHatchMove.findByBentuk3ConstraintTarakan", query = "SELECT r FROM RecapHatchMove r WHERE r.noPpkb = :noPpkb AND r.crane = :crane AND r.operation = :operation"),
    
    @NamedQuery(name = "RecapHatchMove.findByOperation", query = "SELECT r FROM RecapHatchMove r WHERE r.operation = :operation"),
    @NamedQuery(name = "RecapHatchMove.findByActivityCode", query = "SELECT r FROM RecapHatchMove r WHERE r.activityCode = :activityCode"),
    @NamedQuery(name = "RecapHatchMove.findByAmount", query = "SELECT r FROM RecapHatchMove r WHERE r.amount = :amount"),
    @NamedQuery(name = "RecapHatchMove.findByCharge", query = "SELECT r FROM RecapHatchMove r WHERE r.charge = :charge"),
    @NamedQuery(name = "RecapHatchMove.findByTotalCharge", query = "SELECT r FROM RecapHatchMove r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapHatchMove.deleteByNoPpkb", query = "DELETE FROM RecapHatchMove r WHERE r.noPpkb = :noPpkb")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "RecapHatchMove.Native.findByPpkb", query = "SELECT id FROM recap_hatch_move WHERE no_ppkb = ?")
})
public class RecapHatchMove implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "operation", length = 10)
    private String operation;
    @Column(name = "activity_code", length = 10)
    private String activityCode;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
    @Column(name = "curr_code", length = 5)
    private String currCode;
    @Column(name = "crane", length = 2)
    private String crane;
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

    public RecapHatchMove() {
    }

    public RecapHatchMove(Integer id) {
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
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

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
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
        if (!(object instanceof RecapHatchMove)) {
            return false;
        }
        RecapHatchMove other = (RecapHatchMove) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapHatchMove[id=" + id + "]";
    }
}
