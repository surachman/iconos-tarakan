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
@Table(name = "productivity_hatch_moves")
@NamedQueries({
    @NamedQuery(name = "ProductivityHatchMoves.findAll", query = "SELECT p FROM ProductivityHatchMoves p"),
    @NamedQuery(name = "ProductivityHatchMoves.findById", query = "SELECT p FROM ProductivityHatchMoves p WHERE p.id = :id"),
    @NamedQuery(name = "ProductivityHatchMoves.findByNoPpkb", query = "SELECT p FROM ProductivityHatchMoves p WHERE p.noPpkb = :noPpkb"),
    @NamedQuery(name = "ProductivityHatchMoves.findByOperation", query = "SELECT p FROM ProductivityHatchMoves p WHERE p.operation = :operation"),
    @NamedQuery(name = "ProductivityHatchMoves.findByTotalHatchMoves", query = "SELECT p FROM ProductivityHatchMoves p WHERE p.totalHatchMoves = :totalHatchMoves"),
    @NamedQuery(name = "ProductivityHatchMoves.findByTotalCharge", query = "SELECT p FROM ProductivityHatchMoves p WHERE p.totalCharge = :totalCharge")})
public class ProductivityHatchMoves implements Serializable, EntityAuditable {
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
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "operation", length = 10)
    private String operation;
    @Column(name = "total_hatch_moves", length = 10)
    private String totalHatchMoves;
    @Column(name = "total_charge", length = 2)
    private String totalCharge;

    public ProductivityHatchMoves() {
    }

    public ProductivityHatchMoves(Integer id) {
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

    public String getTotalHatchMoves() {
        return totalHatchMoves;
    }

    public void setTotalHatchMoves(String totalHatchMoves) {
        this.totalHatchMoves = totalHatchMoves;
    }

    public String getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
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
        if (!(object instanceof ProductivityHatchMoves)) {
            return false;
        }
        ProductivityHatchMoves other = (ProductivityHatchMoves) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ProductivityHatchMoves[id=" + id + "]";
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
