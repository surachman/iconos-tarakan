/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterActivity;
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

/**
 *
 * @author R. Seno Anggoro A
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "perhitungan_supervisi")
@NamedQueries({
    @NamedQuery(name = "PerhitunganSupervisi.findAll", query = "SELECT p FROM PerhitunganSupervisi p"),
    @NamedQuery(name = "PerhitunganSupervisi.findById", query = "SELECT p FROM PerhitunganSupervisi p WHERE p.id = :id"),
    @NamedQuery(name = "PerhitunganSupervisi.findByCharge", query = "SELECT p FROM PerhitunganSupervisi p WHERE p.charge = :charge"),
    @NamedQuery(name = "PerhitunganSupervisi.findByJumlah", query = "SELECT p FROM PerhitunganSupervisi p WHERE p.jumlah = :jumlah"),
    @NamedQuery(name = "PerhitunganSupervisi.findByTotalCharge", query = "SELECT p FROM PerhitunganSupervisi p WHERE p.totalCharge = :totalCharge"),
    @NamedQuery(name = "PerhitunganSupervisi.findByCreatedBy", query = "SELECT p FROM PerhitunganSupervisi p WHERE p.createdBy = :createdBy"),
    @NamedQuery(name = "PerhitunganSupervisi.findByModifiedBy", query = "SELECT p FROM PerhitunganSupervisi p WHERE p.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "PerhitunganSupervisi.findByModifiedDate", query = "SELECT p FROM PerhitunganSupervisi p WHERE p.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "PerhitunganSupervisi.findByCreatedDate", query = "SELECT p FROM PerhitunganSupervisi p WHERE p.createdDate = :createdDate")})
public class PerhitunganSupervisi implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal charge = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "jumlah", nullable = false)
    private int jumlah = 0;
    @Basic(optional = false)
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobslip;
    @Basic(optional = false)
    @Column(name = "total_charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCharge = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "created_by", nullable = false, length = 256)
    private String createdBy;
    @Column(name = "modified_by", length = 256)
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Basic(optional = false)
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration registration;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterActivity masterActivity;
    @OneToOne(mappedBy = "perhitunganSupervisi")
    private PerhitunganBehandle perhitunganBehandle;

    public PerhitunganSupervisi() {
    }

    public PerhitunganSupervisi(Integer id) {
        this.id = id;
    }

    public PerhitunganSupervisi(Integer id, BigDecimal charge, int jumlah, BigDecimal totalCharge, String createdBy, Date createdDate) {
        this.id = id;
        this.charge = charge;
        this.jumlah = jumlah;
        this.totalCharge = totalCharge;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }

    public PerhitunganBehandle getPerhitunganBehandle() {
        return perhitunganBehandle;
    }

    public void setPerhitunganBehandle(PerhitunganBehandle perhitunganBehandle) {
        this.perhitunganBehandle = perhitunganBehandle;
    }

    public String getJobslip() {
        return jobslip;
    }

    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
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
        if (!(object instanceof PerhitunganSupervisi)) {
            return false;
        }
        PerhitunganSupervisi other = (PerhitunganSupervisi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganSupervisi[id=" + id + "]";
    }

}
