/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.Invoice;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author R Seno Anggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_bank")
@NamedQueries({
    @NamedQuery(name = "MasterBank.findAll", query = "SELECT m FROM MasterBank m"),
    @NamedQuery(name = "MasterBank.findById", query = "SELECT m FROM MasterBank m WHERE m.id = :id"),
    @NamedQuery(name = "MasterBank.findByNama", query = "SELECT m FROM MasterBank m WHERE m.nama = :nama"),
    @NamedQuery(name = "MasterBank.findByRekeningAkuntansi", query = "SELECT m FROM MasterBank m WHERE m.rekeningAkuntansi = :rekeningAkuntansi"),
    @NamedQuery(name = "MasterBank.findByGuid", query = "SELECT m FROM MasterBank m WHERE m.guid = :guid"),
    @NamedQuery(name = "MasterBank.findByRecreateGuidDate", query = "SELECT m FROM MasterBank m WHERE m.recreateGuidDate = :recreateGuidDate"),
    @NamedQuery(name = "MasterBank.findByCreatedBy", query = "SELECT m FROM MasterBank m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MasterBank.findByCreatedDate", query = "SELECT m FROM MasterBank m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MasterBank.findByModifiedBy", query = "SELECT m FROM MasterBank m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MasterBank.findByModifiedDate", query = "SELECT m FROM MasterBank m WHERE m.modifiedDate = :modifiedDate")})
public class MasterBank implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, length = 5)
    private String id;
    @Column(name = "nama", length = 100)
    private String nama;
    @Column(name = "rekening_akuntansi", length = 50)
    private String rekeningAkuntansi;
    @Column(name = "guid", length = 100)
    private String guid;
    @Column(name = "recreate_guid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recreateGuidDate;
    @Basic(optional = false)
    @Column(name = "created_by", nullable = false, length = 2147483647)
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_by", length = 2147483647)
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(mappedBy = "masterBank")
    private List<Invoice> invoiceList;

    public MasterBank() {
        guid = UUID.randomUUID().toString();
    }

    public MasterBank(String id) {
        this.id = id;
    }

    public MasterBank(String id, String createdBy, Date createdDate) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRekeningAkuntansi() {
        return rekeningAkuntansi;
    }

    public void setRekeningAkuntansi(String rekeningAkuntansi) {
        this.rekeningAkuntansi = rekeningAkuntansi;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Date getRecreateGuidDate() {
        return recreateGuidDate;
    }

    public void setRecreateGuidDate(Date recreateGuidDate) {
        this.recreateGuidDate = recreateGuidDate;
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

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
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
        if (!(object instanceof MasterBank)) {
            return false;
        }
        MasterBank other = (MasterBank) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterBank[id=" + id + "]";
    }

    public Boolean isValid(){
        return getId() != null || getNama() != null || getRekeningAkuntansi() != null || getGuid() != null;
    }

}
