/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "angkut_petikemas")
@NamedQueries({
    @NamedQuery(name = "AngkutPetikemas.findAll", query = "SELECT a FROM AngkutPetikemas a"),
    @NamedQuery(name = "AngkutPetikemas.findById", query = "SELECT a FROM AngkutPetikemas a WHERE a.id = :id"),
    @NamedQuery(name = "AngkutPetikemas.findByNoSpk", query = "SELECT a FROM AngkutPetikemas a WHERE a.noSpk = :noSpk"),
    @NamedQuery(name = "AngkutPetikemas.findByContSize", query = "SELECT a FROM AngkutPetikemas a WHERE a.contSize = :contSize"),
    @NamedQuery(name = "AngkutPetikemas.findByContStatus", query = "SELECT a FROM AngkutPetikemas a WHERE a.contStatus = :contStatus"),
    @NamedQuery(name = "AngkutPetikemas.findByJmlCont", query = "SELECT a FROM AngkutPetikemas a WHERE a.jmlCont = :jmlCont"),
    @NamedQuery(name = "AngkutPetikemas.findByTarifCont", query = "SELECT a FROM AngkutPetikemas a WHERE a.tarifCont = :tarifCont"),
    @NamedQuery(name = "AngkutPetikemas.findByTotalCharge", query = "SELECT a FROM AngkutPetikemas a WHERE a.totalCharge = :totalCharge"),
    @NamedQuery(name = "AngkutPetikemas.findByCreatedBy", query = "SELECT a FROM AngkutPetikemas a WHERE a.createdBy = :createdBy"),
    @NamedQuery(name = "AngkutPetikemas.findByCreatedDate", query = "SELECT a FROM AngkutPetikemas a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "AngkutPetikemas.findByModifiedBy", query = "SELECT a FROM AngkutPetikemas a WHERE a.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "AngkutPetikemas.findByModifiedDate", query = "SELECT a FROM AngkutPetikemas a WHERE a.modifiedDate = :modifiedDate")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "AngkutPetikemas.Native.findAll", query = "SELECT a.id, a.no_spk, c.name, a.cont_size, a.cont_status, a.jml_cont, a.tarif_cont, a.total_charge FROM angkut_petikemas a, m_customer c WHERE a.cust_code = c.cust_code ORDER BY a.created_date DESC")})
public class AngkutPetikemas implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "no_spk", nullable = false, length = 2147483647)
    private String noSpk;
    @Column(name = "cont_size")
    private Integer contSize;
    @Column(name = "cont_status", length = 3)
    private String contStatus;
    @Column(name = "jml_cont")
    private Integer jmlCont;
    @Column(name = "tarif_cont", precision = 19, scale = 2)
    private BigDecimal tarifCont;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
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
    @JoinColumn(name = "cust_code", referencedColumnName = "cust_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCustomer masterCustomer;

    public AngkutPetikemas() {
    }

    public AngkutPetikemas(Integer id) {
        this.id = id;
    }

    public AngkutPetikemas(Integer id, String noSpk, String createdBy, Date createdDate) {
        this.id = id;
        this.noSpk = noSpk;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoSpk() {
        return noSpk;
    }

    public void setNoSpk(String noSpk) {
        this.noSpk = noSpk;
    }

    public Integer getContSize() {
        return contSize;
    }

    public void setContSize(Integer contSize) {
        this.contSize = contSize;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public Integer getJmlCont() {
        return jmlCont;
    }

    public void setJmlCont(Integer jmlCont) {
        this.jmlCont = jmlCont;
    }

    public BigDecimal getTarifCont() {
        return tarifCont;
    }

    public void setTarifCont(BigDecimal tarifCont) {
        this.tarifCont = tarifCont;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
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

    public MasterCustomer getMasterCustomer() {
        return masterCustomer;
    }

    public void setMasterCustomer(MasterCustomer masterCustomer) {
        this.masterCustomer = masterCustomer;
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
        if (!(object instanceof AngkutPetikemas)) {
            return false;
        }
        AngkutPetikemas other = (AngkutPetikemas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.AngkutPetikemas[id=" + id + "]";
    }

}
