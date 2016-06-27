/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "reefer_productivity")
@NamedQueries({
    @NamedQuery(name = "ReeferProductivity.findAll", query = "SELECT r FROM ReeferProductivity r"),
    @NamedQuery(name = "ReeferProductivity.findById", query = "SELECT r FROM ReeferProductivity r WHERE r.id = :id"),
    @NamedQuery(name = "ReeferProductivity.findByContNo", query = "SELECT r FROM ReeferProductivity r WHERE r.contNo = :contNo"),
    @NamedQuery(name = "ReeferProductivity.findByContSize", query = "SELECT r FROM ReeferProductivity r WHERE r.contSize = :contSize"),
    @NamedQuery(name = "ReeferProductivity.findByContStatus", query = "SELECT r FROM ReeferProductivity r WHERE r.contStatus = :contStatus"),
    @NamedQuery(name = "ReeferProductivity.findByShift", query = "SELECT r FROM ReeferProductivity r WHERE r.shift = :shift"),
    @NamedQuery(name = "ReeferProductivity.findByCharge", query = "SELECT r FROM ReeferProductivity r WHERE r.charge = :charge"),
    @NamedQuery(name = "ReeferProductivity.findByOperation", query = "SELECT r FROM ReeferProductivity r WHERE r.operation = :operation"),
    @NamedQuery(name = "ReeferProductivity.findByChargeType", query = "SELECT r FROM ReeferProductivity r WHERE r.chargeType = :chargeType")})
public class ReeferProductivity implements Serializable, EntityAuditable {
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
    @Column(name = "bl_no")
    private String blNo;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id; 
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_status", length = 5)
    private String contStatus;
    @Basic(optional = false)
    @Column(name = "shift", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date shift;
    @Column(name = "charge")
    private BigInteger charge;
    @Column(name = "operation", length = 10)
    private String operation;
    @Column(name = "charge_type", length = 10)
    private String chargeType;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public ReeferProductivity() {
    }

    public ReeferProductivity(Integer id) {
        this.id = id;
    }

    public ReeferProductivity(Integer id, String contNo, Date shift, MasterCustomer mlo) {
        this.id = id;
        this.contNo = contNo;
        this.shift = shift;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public Short getContSize() {
        return contSize;
    }

    public void setContSize(Short contSize) {
        this.contSize = contSize;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public Date getShift() {
        return shift;
    }

    public void setShift(Date shift) {
        this.shift = shift;
    }

    public BigInteger getCharge() {
        return charge;
    }

    public void setCharge(BigInteger charge) {
        this.charge = charge;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
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

    /**
     * @return the mlo
     */
    public MasterCustomer getMlo() {
        return mlo;
    }

    /**
     * @param mlo the mlo to set
     */
    public void setMlo(MasterCustomer mlo) {
        this.mlo = mlo;
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
        if (!(object instanceof ReeferProductivity)) {
            return false;
        }
        ReeferProductivity other = (ReeferProductivity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ReeferProductivity[id=" + id + "]";
    }

}
