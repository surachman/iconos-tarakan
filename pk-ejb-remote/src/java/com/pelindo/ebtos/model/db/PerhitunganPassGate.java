/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterService;
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
 * @author x42jr
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "perhitungan_pass_gate")
@NamedQueries({
    @NamedQuery(name = "PerhitunganPassGate.findAll", query = "SELECT p FROM PerhitunganPassGate p"),
    @NamedQuery(name = "PerhitunganPassGate.findById", query = "SELECT p FROM PerhitunganPassGate p WHERE p.id = :id"),
    @NamedQuery(name = "PerhitunganPassGate.findByCharge", query = "SELECT p FROM PerhitunganPassGate p WHERE p.charge = :charge"),
    @NamedQuery(name = "PerhitunganPassGate.updatePlanningVessel", query = "UPDATE PerhitunganPassGate p SET p.planningVessel = :newValue WHERE p.planningVessel = :oldValue"),
    @NamedQuery(name = "PerhitunganPassGate.deleteByJobSlip", query = "DELETE FROM PerhitunganPassGate p WHERE p.jobSlip = :jobSlip"),
    @NamedQuery(name = "PerhitunganPassGate.findByJobSlip", query = "SELECT p FROM PerhitunganPassGate p WHERE p.jobSlip = :jobSlip"),
    @NamedQuery(name = "PerhitunganPassGate.deleteByNoReg", query = "DELETE FROM PerhitunganPassGate p WHERE p.registration.noReg = :noReg"),
    @NamedQuery(name = "PerhitunganPassGate.findByJumlah", query = "SELECT p FROM PerhitunganPassGate p WHERE p.jumlah = :jumlah"),
    @NamedQuery(name = "PerhitunganPassGate.findByTotalCharge", query = "SELECT p FROM PerhitunganPassGate p WHERE p.totalCharge = :totalCharge"),
    @NamedQuery(name = "PerhitunganPassGate.findByCreatedBy", query = "SELECT p FROM PerhitunganPassGate p WHERE p.createdBy = :createdBy"),
    @NamedQuery(name = "PerhitunganPassGate.findByModifiedBy", query = "SELECT p FROM PerhitunganPassGate p WHERE p.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "PerhitunganPassGate.findByModifiedDate", query = "SELECT p FROM PerhitunganPassGate p WHERE p.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "PerhitunganPassGate.findByCreatedDate", query = "SELECT p FROM PerhitunganPassGate p WHERE p.createdDate = :createdDate")})
public class PerhitunganPassGate implements Serializable, EntityAuditable {
    @OneToOne(mappedBy = "perhitunganPassGate")
    private CancelDocumentService cancelDocumentService;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal charge = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "jumlah", nullable = false)
    private int jumlah;
    @Basic(optional = false)
    @Column(name = "total_charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCharge = BigDecimal.ZERO;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration registration;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", nullable = false)
    @ManyToOne(optional = false)
    private PlanningVessel planningVessel;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterActivity masterActivity;
    @Basic(optional = false)
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobSlip;
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

    public PerhitunganPassGate() {
    }

    public PerhitunganPassGate(Integer id) {
        this.id = id;
    }

    public PerhitunganPassGate(Integer id, BigDecimal charge, int jumlah, BigDecimal totalCharge, String createdBy, Date createdDate) {
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

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
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
        if (!(object instanceof PerhitunganPassGate)) {
            return false;
        }
        PerhitunganPassGate other = (PerhitunganPassGate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganPassGate[id=" + id + "]";
    }

    public CancelDocumentService getCancelDocumentService() {
        return cancelDocumentService;
    }

    public void setCancelDocumentService(CancelDocumentService cancelDocumentService) {
        this.cancelDocumentService = cancelDocumentService;
    }
}
