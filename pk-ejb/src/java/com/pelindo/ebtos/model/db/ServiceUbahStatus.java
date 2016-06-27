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
@Table(name = "service_ubah_status")
@NamedQueries({
    @NamedQuery(name = "ServiceUbahStatus.findAll", query = "SELECT s FROM ServiceUbahStatus s"),
    @NamedQuery(name = "ServiceUbahStatus.findById", query = "SELECT s FROM ServiceUbahStatus s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceUbahStatus.findByContNo", query = "SELECT s FROM ServiceUbahStatus s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceUbahStatus.findByContSize", query = "SELECT s FROM ServiceUbahStatus s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceUbahStatus.findByContStatus", query = "SELECT s FROM ServiceUbahStatus s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceUbahStatus.findByContGross", query = "SELECT s FROM ServiceUbahStatus s WHERE s.contGross = :contGross"),
    @NamedQuery(name = "ServiceUbahStatus.findBySealNo", query = "SELECT s FROM ServiceUbahStatus s WHERE s.sealNo = :sealNo"),
    @NamedQuery(name = "ServiceUbahStatus.findByOverSize", query = "SELECT s FROM ServiceUbahStatus s WHERE s.overSize = :overSize"),
    @NamedQuery(name = "ServiceUbahStatus.findByDg", query = "SELECT s FROM ServiceUbahStatus s WHERE s.dg = :dg"),
    @NamedQuery(name = "ServiceUbahStatus.findByDgLabel", query = "SELECT s FROM ServiceUbahStatus s WHERE s.dgLabel = :dgLabel"),
    @NamedQuery(name = "ServiceUbahStatus.findByNewContStatus", query = "SELECT s FROM ServiceUbahStatus s WHERE s.newContStatus = :newContStatus"),
    @NamedQuery(name = "ServiceUbahStatus.findByTransactionDate", query = "SELECT s FROM ServiceUbahStatus s WHERE s.transactionDate = :transactionDate")})
public class ServiceUbahStatus implements Serializable, EntityAuditable {
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
    @Basic(optional = false)
    @Column(name = "new_cont_status", nullable = false, length = 5)
    private String newContStatus;
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

    public ServiceUbahStatus() {
    }

    public ServiceUbahStatus(Integer id) {
        this.id = id;
    }

    public ServiceUbahStatus(Integer id, String contNo, String newContStatus, Date transactionDate, MasterCustomer mlo) {
        this.id = id;
        this.contNo = contNo;
        this.mlo = mlo;
        this.newContStatus = newContStatus;
        this.transactionDate = transactionDate;
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

    public String getNewContStatus() {
        return newContStatus;
    }

    public void setNewContStatus(String newContStatus) {
        this.newContStatus = newContStatus;
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
        if (!(object instanceof ServiceUbahStatus)) {
            return false;
        }
        ServiceUbahStatus other = (ServiceUbahStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceUbahStatus[id=" + id + "]";
    }

}
