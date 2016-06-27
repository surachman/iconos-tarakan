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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dycode-java
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "recap_penumpukan_load")
@NamedQueries({
    @NamedQuery(name = "RecapPenumpukanLoad.findAll", query = "SELECT r FROM RecapPenumpukanLoad r"),
    @NamedQuery(name = "RecapPenumpukanLoad.findByNoPpkb", query = "SELECT r FROM RecapPenumpukanLoad r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapPenumpukanLoad.findByCharge", query = "SELECT r FROM RecapPenumpukanLoad r WHERE r.charge = :charge"),
    @NamedQuery(name = "RecapPenumpukanLoad.findByCurrCode", query = "SELECT r FROM RecapPenumpukanLoad r WHERE r.currCode = :currCode")})
public class RecapPenumpukanLoad implements Serializable, EntityAuditable {
    @Column(name = "operation", length = 20)
    private String operation;
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
    @Column(name = "no_ppkb", nullable = false, length = 30)
    private String noPpkb;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "curr_code", length = 5)
    private String currCode;

    public RecapPenumpukanLoad() {
    }

    public RecapPenumpukanLoad(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noPpkb != null ? noPpkb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecapPenumpukanLoad)) {
            return false;
        }
        RecapPenumpukanLoad other = (RecapPenumpukanLoad) object;
        if ((this.noPpkb == null && other.noPpkb != null) || (this.noPpkb != null && !this.noPpkb.equals(other.noPpkb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapPenumpukanLoad[noPpkb=" + noPpkb + "]";
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
