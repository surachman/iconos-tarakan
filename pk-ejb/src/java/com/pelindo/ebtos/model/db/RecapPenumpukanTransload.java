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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "recap_penumpukan_transload")
@NamedQueries({
    @NamedQuery(name = "RecapPenumpukanTransload.findAll", query = "SELECT r FROM RecapPenumpukanTransload r"),
    @NamedQuery(name = "RecapPenumpukanTransload.findById", query = "SELECT r FROM RecapPenumpukanTransload r WHERE r.id = :id"),
    @NamedQuery(name = "RecapPenumpukanTransload.findByNoPpkb", query = "SELECT r FROM RecapPenumpukanTransload r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapPenumpukanTransload.findByCharge", query = "SELECT r FROM RecapPenumpukanTransload r WHERE r.charge = :charge"),
    @NamedQuery(name = "RecapPenumpukanTransload.findByCurrCode", query = "SELECT r FROM RecapPenumpukanTransload r WHERE r.currCode = :currCode"),
    @NamedQuery(name = "RecapPenumpukanTransload.findByOperation", query = "SELECT r FROM RecapPenumpukanTransload r WHERE r.operation = :operation")})

    @NamedNativeQueries({
    @NamedNativeQuery(name = "RecapPenumpukanTransload.Native.findRecapPenumpukanTransloadByPpkb", query = "SELECT r.id from recap_penumpukan_transload r WHERE r.operation=? AND r.no_ppkb =?")})
public class RecapPenumpukanTransload implements Serializable, EntityAuditable {
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
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "curr_code", length = 5)
    private String currCode;
    @Column(name = "operation", length = 20)
    private String operation;

    public RecapPenumpukanTransload() {
    }

    public RecapPenumpukanTransload(Integer id) {
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
        if (!(object instanceof RecapPenumpukanTransload)) {
            return false;
        }
        RecapPenumpukanTransload other = (RecapPenumpukanTransload) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapPenumpukanTransload[id=" + id + "]";
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
