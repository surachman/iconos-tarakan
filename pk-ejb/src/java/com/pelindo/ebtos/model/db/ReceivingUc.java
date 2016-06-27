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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
@Table(name = "receiving_uc")
@NamedQueries({
    @NamedQuery(name = "ReceivingUc.findAll", query = "SELECT r FROM ReceivingUc r"),
    @NamedQuery(name = "ReceivingUc.findByJobSlip", query = "SELECT r FROM ReceivingUc r WHERE r.jobSlip = :jobSlip"),
    @NamedQuery(name = "ReceivingUc.findByNoBl", query = "SELECT r FROM ReceivingUc r WHERE r.noBl = :noBl"),
    @NamedQuery(name = "ReceivingUc.findByWeight", query = "SELECT r FROM ReceivingUc r WHERE r.weight = :weight"),
    @NamedQuery(name = "ReceivingUc.findByTruckLossing", query = "SELECT r FROM ReceivingUc r WHERE r.truckLossing = :truckLossing"),
    @NamedQuery(name = "ReceivingUc.sfindByValidDate", query = "SELECT r FROM ReceivingUc r WHERE r.validDate = :validDate")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ReceivingUc.Native.findByPpkbNReg", query = "SELECT r.job_slip, c.name, r.no_bl, r.truck_lossing, r.weight, r.unit, r.valid_date FROM receiving_uc r, m_commodity c WHERE r.commodity_code = c.commodity_code AND r.no_ppkb = ? AND r.no_reg = ?"),
/*  
    @NamedNativeQuery(name="ReceivingUc.Native.generateId", query = "SELECT MAX(substring(job_slip,7,4))FROM receiving_uc WHERE substring(job_slip,5,2) = ?")
*/
    @NamedNativeQuery(name="ReceivingUc.Native.generateId", query = 
"SELECT MAX(SUBSTR(job_slip,7,4)) " 
+"FROM receiving_uc " 
+"WHERE SUBSTR(job_slip,5,2) = ?")
    
})
public class ReceivingUc implements Serializable, EntityAuditable {
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
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobSlip;
    @Column(name = "no_bl")
    private String noBl;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "truck_lossing")
    private String truckLossing;
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @Column(name = "unit")
    private Short unit;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg")
    @ManyToOne
    private Registration registration;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;

    public ReceivingUc() {
    }

    public ReceivingUc(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getNoBl() {
        return noBl;
    }

    public void setNoBl(String noBl) {
        this.noBl = noBl;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getTruckLossing() {
        return truckLossing;
    }

    public void setTruckLossing(String truckLossing) {
        this.truckLossing = truckLossing;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Short getUnit() {
        return unit;
    }

    public void setUnit(Short unit) {
        this.unit = unit;
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

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
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
        if (!(object instanceof ReceivingUc)) {
            return false;
        }
        ReceivingUc other = (ReceivingUc) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ReceivingUc[jobSlip=" + jobSlip + "]";
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
