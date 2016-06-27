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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Table(name = "discharge_to_load_service")
@EntityListeners({AuditTrailEntityListener.class})
@NamedQueries({
    @NamedQuery(name = "DischargeToLoadService.findAll", query = "SELECT d FROM DischargeToLoadService d"),
    @NamedQuery(name = "DischargeToLoadService.findByNoReg", query = "SELECT d FROM DischargeToLoadService d WHERE d.registration.noReg = :noReg"),
    @NamedQuery(name = "DischargeToLoadService.deleteByNoReg", query = "DELETE FROM DischargeToLoadService d WHERE d.registration.noReg = :noReg")})
public class DischargeToLoadService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
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
    @Basic(optional = false)
    @Column(name = "total_charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCharge = BigDecimal.ZERO;
    @Id
    @Basic(optional = false)
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobSlip;
    @JoinColumn(name = "service_cont_discharge_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private ServiceContDischarge serviceContDischarge;
    @JoinColumn(name = "service_receiving_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false, cascade=CascadeType.ALL, orphanRemoval=true)
    private ServiceReceiving serviceReceiving;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration registration;
    @JoinColumn(name = "load_no_ppkb", referencedColumnName = "no_ppkb", nullable = false)
    @ManyToOne(optional = false)
    private PlanningVessel planningVessel;
    @JoinColumn(name = "perhitungan_penumpukan_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganPenumpukan perhitunganPenumpukan;
    @JoinColumn(name = "perhitungan_discharge_to_load_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganDischargeToLoad perhitunganDischargeToLoad;

    @Column(name = "last_no_ppkb", length = 30)
    private String lastNoPpkb;
    @Column(name = "last_pod", length = 10)
    private String lastPod;

    public DischargeToLoadService() {
    }

    public DischargeToLoadService(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public DischargeToLoadService(String jobSlip, String createdBy, Date createdDate, BigDecimal totalCharge) {
        this.jobSlip = jobSlip;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
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

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public ServiceContDischarge getServiceContDischarge() {
        return serviceContDischarge;
    }

    public void setServiceContDischarge(ServiceContDischarge serviceContDischarge) {
        this.serviceContDischarge = serviceContDischarge;
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

    public PerhitunganPenumpukan getPerhitunganPenumpukan() {
        return perhitunganPenumpukan;
    }

    public void setPerhitunganPenumpukan(PerhitunganPenumpukan perhitunganPenumpukan) {
        this.perhitunganPenumpukan = perhitunganPenumpukan;
    }

    public PerhitunganDischargeToLoad getPerhitunganDischargeToLoad() {
        return perhitunganDischargeToLoad;
    }

    public void setPerhitunganDischargeToLoad(PerhitunganDischargeToLoad perhitunganDischargeToLoad) {
        this.perhitunganDischargeToLoad = perhitunganDischargeToLoad;
    }

    public ServiceReceiving getServiceReceiving() {
        return serviceReceiving;
    }

    public void setServiceReceiving(ServiceReceiving serviceReceiving) {
        this.serviceReceiving = serviceReceiving;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobSlip != null ? jobSlip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DischargeToLoadService)) {
            return false;
        }
        DischargeToLoadService other = (DischargeToLoadService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.DischargeToLoadService[jobSlip=" + jobSlip + "]";
    }

    public String getLastNoPpkb() {
        return lastNoPpkb;
    }

    public void setLastNoPpkb(String lastNoPpkb) {
        this.lastNoPpkb = lastNoPpkb;
    }

    public String getLastPod() {
        return lastPod;
    }

    public void setLastPod(String lastPod) {
        this.lastPod = lastPod;
    }

}
