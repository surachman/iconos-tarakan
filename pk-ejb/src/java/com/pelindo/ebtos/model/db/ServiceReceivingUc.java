/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import java.io.Serializable;
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
@Table(name = "service_receiving_uc")
@NamedQueries({
    @NamedQuery(name = "ServiceReceivingUc.findAll", query = "SELECT s FROM ServiceReceivingUc s"),
    @NamedQuery(name = "ServiceReceivingUc.findById", query = "SELECT s FROM ServiceReceivingUc s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceReceivingUc.findByBlock", query = "SELECT s FROM ServiceReceivingUc s WHERE s.block = :block"),
    @NamedQuery(name = "ServiceReceivingUc.findByNoBl", query = "SELECT s FROM ServiceReceivingUc s WHERE s.noBl = :noBl"),
    @NamedQuery(name = "ServiceReceivingUc.findByWeight", query = "SELECT s FROM ServiceReceivingUc s WHERE s.weight = :weight"),
    @NamedQuery(name = "ServiceReceivingUc.findByTruckLossing", query = "SELECT s FROM ServiceReceivingUc s WHERE s.truckLossing = :truckLossing"),
    @NamedQuery(name = "ServiceReceivingUc.findByTransactionDate", query = "SELECT s FROM ServiceReceivingUc s WHERE s.transactionDate = :transactionDate")})
public class ServiceReceivingUc implements Serializable, EntityAuditable {
    @Column(name = "masa1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date masa1;
    @Column(name = "masa2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date masa2;
    @Column(name = "end_storage_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endStorageDate;
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
    @Column(name = "block", length = 5)
    private String block;
    @Basic(optional = false)
    @Column(name = "no_bl", nullable = false)
    private String noBl;
    @Basic(optional = false)
    @Column(name = "weight", nullable = false, length = 3)
    private String weight;
    @Column(name = "truck_lossing")
    private String truckLossing;
    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;

    public ServiceReceivingUc() {
    }

    public ServiceReceivingUc(Integer id) {
        this.id = id;
    }

    public ServiceReceivingUc(Integer id, String noBl, String weight) {
        this.id = id;
        this.noBl = noBl;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getNoBl() {
        return noBl;
    }

    public void setNoBl(String noBl) {
        this.noBl = noBl;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTruckLossing() {
        return truckLossing;
    }

    public void setTruckLossing(String truckLossing) {
        this.truckLossing = truckLossing;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
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
        if (!(object instanceof ServiceReceivingUc)) {
            return false;
        }
        ServiceReceivingUc other = (ServiceReceivingUc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceReceivingUc[id=" + id + "]";
    }

    public Date getMasa1() {
        return masa1;
    }

    public void setMasa1(Date masa1) {
        this.masa1 = masa1;
    }

    public Date getMasa2() {
        return masa2;
    }

    public void setMasa2(Date masa2) {
        this.masa2 = masa2;
    }

    public Date getEndStorageDate() {
        return endStorageDate;
    }

    public void setEndStorageDate(Date endStorageDate) {
        this.endStorageDate = endStorageDate;
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
