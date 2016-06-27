/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author R Seno Anggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "perhitungan_upah_buruh", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"jobslip"})})
@NamedQueries({
    @NamedQuery(name = "PerhitunganUpahBuruh.findAll", query = "SELECT p FROM PerhitunganUpahBuruh p"),
    @NamedQuery(name = "PerhitunganUpahBuruh.findById", query = "SELECT p FROM PerhitunganUpahBuruh p WHERE p.id = :id"),
    @NamedQuery(name = "PerhitunganUpahBuruh.findByJobslip", query = "SELECT p FROM PerhitunganUpahBuruh p WHERE p.jobslip = :jobslip"),
    @NamedQuery(name = "PerhitunganUpahBuruh.deleteByJobslip", query = "DELETE FROM PerhitunganUpahBuruh p WHERE p.jobslip = :jobslip"),
    @NamedQuery(name = "PerhitunganUpahBuruh.findByJobslip", query = "SELECT p FROM PerhitunganUpahBuruh p WHERE p.jobslip = :jobslip"),
    @NamedQuery(name = "PerhitunganUpahBuruh.deleteByNoReg", query = "DELETE FROM PerhitunganUpahBuruh p WHERE p.registration.noReg = :noReg"),
    @NamedQuery(name = "PerhitunganUpahBuruh.findByCharge", query = "SELECT p FROM PerhitunganUpahBuruh p WHERE p.charge = :charge"),
    @NamedQuery(name = "PerhitunganUpahBuruh.findByCreatedBy", query = "SELECT p FROM PerhitunganUpahBuruh p WHERE p.createdBy = :createdBy"),
    @NamedQuery(name = "PerhitunganUpahBuruh.findByCreatedDate", query = "SELECT p FROM PerhitunganUpahBuruh p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "PerhitunganUpahBuruh.findByModifiedBy", query = "SELECT p FROM PerhitunganUpahBuruh p WHERE p.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "PerhitunganUpahBuruh.findByModifiedDate", query = "SELECT p FROM PerhitunganUpahBuruh p WHERE p.modifiedDate = :modifiedDate")})
public class PerhitunganUpahBuruh implements Serializable, EntityAuditable {
    @OneToOne(mappedBy = "perhitunganUpahBuruh")
    private CancelDocumentService cancelDocumentService;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "jobslip", nullable = false, length = 30)
    private String jobslip;
    @Basic(optional = false)
    @Column(name = "charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal charge = BigDecimal.ZERO;
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
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration registration;
    @JoinColumn(name = "curr_code", referencedColumnName = "curr_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCurrency masterCurrency;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterActivity masterActivity;

    public PerhitunganUpahBuruh() {
    }

    public PerhitunganUpahBuruh(Integer id) {
        this.id = id;
    }

    public PerhitunganUpahBuruh(Integer id, String jobslip, BigDecimal charge, String createdBy, Date createdDate) {
        this.id = id;
        this.jobslip = jobslip;
        this.charge = charge;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobslip() {
        return jobslip;
    }

    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
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

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public void setMasterCurrency(MasterCurrency masterCurrency) {
        this.masterCurrency = masterCurrency;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
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
        if (!(object instanceof PerhitunganUpahBuruh)) {
            return false;
        }
        PerhitunganUpahBuruh other = (PerhitunganUpahBuruh) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganUpahBuruh[id=" + id + "]";
    }

    public CancelDocumentService getCancelDocumentService() {
        return cancelDocumentService;
    }

    public void setCancelDocumentService(CancelDocumentService cancelDocumentService) {
        this.cancelDocumentService = cancelDocumentService;
    }

}
