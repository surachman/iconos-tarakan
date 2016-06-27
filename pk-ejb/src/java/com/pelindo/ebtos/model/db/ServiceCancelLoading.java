/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
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
@Table(name = "service_cancel_loading")
@NamedQueries({
    @NamedQuery(name = "ServiceCancelLoading.findAll", query = "SELECT s FROM ServiceCancelLoading s"),
    @NamedQuery(name = "ServiceCancelLoading.findById", query = "SELECT s FROM ServiceCancelLoading s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceCancelLoading.findByContNo", query = "SELECT s FROM ServiceCancelLoading s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceCancelLoading.findByContSize", query = "SELECT s FROM ServiceCancelLoading s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceCancelLoading.findByContStatus", query = "SELECT s FROM ServiceCancelLoading s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceCancelLoading.findByContGross", query = "SELECT s FROM ServiceCancelLoading s WHERE s.contGross = :contGross"),
    @NamedQuery(name = "ServiceCancelLoading.findBySealNo", query = "SELECT s FROM ServiceCancelLoading s WHERE s.sealNo = :sealNo"),
    @NamedQuery(name = "ServiceCancelLoading.findByOverSize", query = "SELECT s FROM ServiceCancelLoading s WHERE s.overSize = :overSize"),
    @NamedQuery(name = "ServiceCancelLoading.findByDg", query = "SELECT s FROM ServiceCancelLoading s WHERE s.dg = :dg"),
    @NamedQuery(name = "ServiceCancelLoading.findByDgLabel", query = "SELECT s FROM ServiceCancelLoading s WHERE s.dgLabel = :dgLabel"),
    @NamedQuery(name = "ServiceCancelLoading.findByStatusLoad", query = "SELECT s FROM ServiceCancelLoading s WHERE s.statusLoad = :statusLoad"),
    @NamedQuery(name = "ServiceCancelLoading.findByCategory", query = "SELECT s FROM ServiceCancelLoading s WHERE s.category = :category"),
    @NamedQuery(name = "ServiceCancelLoading.findByTransactionDate", query = "SELECT s FROM ServiceCancelLoading s WHERE s.transactionDate = :transactionDate")})
public class ServiceCancelLoading implements Serializable, EntityAuditable {
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
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "over_size")
    private String overSize;
    @Column(name = "dg")
    private String dg;
    @Column(name = "dg_label")
    private String dgLabel;
    @Column(name = "status_load", length = 10)
    private String statusLoad;
    @Basic(optional = false)
    @Column(name = "category", nullable = false, length = 2)
    private String category;
    @Basic(optional = false)
    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public ServiceCancelLoading() {
    }

    public ServiceCancelLoading(Integer id) {
        this.id = id;
    }

    public ServiceCancelLoading(Integer id, String contNo, String category, Date transactionDate, MasterCustomer mlo) {
        this.id = id;
        this.contNo = contNo;
        this.category = category;
        this.transactionDate = transactionDate;
        this.mlo = mlo;
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

    public Integer getContGross() {
        return contGross;
    }

    public void setContGross(Integer contGross) {
        this.contGross = contGross;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getOverSize() {
        return overSize;
    }

    public void setOverSize(String overSize) {
        this.overSize = overSize;
    }

    public String getDg() {
        return dg;
    }

    public void setDg(String dg) {
        this.dg = dg;
    }

    public String getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(String dgLabel) {
        this.dgLabel = dgLabel;
    }

    public String getStatusLoad() {
        return statusLoad;
    }

    public void setStatusLoad(String statusLoad) {
        this.statusLoad = statusLoad;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
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
        if (!(object instanceof ServiceCancelLoading)) {
            return false;
        }
        ServiceCancelLoading other = (ServiceCancelLoading) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceCancelLoading[id=" + id + "]";
    }
}
