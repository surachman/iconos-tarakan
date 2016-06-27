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
@Table(name = "delivery_uc_service")
@NamedQueries({
    @NamedQuery(name = "DeliveryUcService.findAll", query = "SELECT d FROM DeliveryUcService d"),
    @NamedQuery(name = "DeliveryUcService.findByJobSlip", query = "SELECT d FROM DeliveryUcService d WHERE d.jobSlip = :jobSlip"),
    @NamedQuery(name = "DeliveryUcService.findByNoReg", query = "SELECT d FROM DeliveryUcService d WHERE d.noReg = :noReg"),
    @NamedQuery(name = "DeliveryUcService.findByNoPpkb", query = "SELECT d FROM DeliveryUcService d WHERE d.noPpkb = :noPpkb"),
    @NamedQuery(name = "DeliveryUcService.findByNoBl", query = "SELECT d FROM DeliveryUcService d WHERE d.noBl = :noBl"),
    @NamedQuery(name = "DeliveryUcService.findByWeight", query = "SELECT d FROM DeliveryUcService d WHERE d.weight = :weight"),
    @NamedQuery(name = "DeliveryUcService.findByTruckLossing", query = "SELECT d FROM DeliveryUcService d WHERE d.truckLossing = :truckLossing"),
    @NamedQuery(name = "DeliveryUcService.findByPlacementDate", query = "SELECT d FROM DeliveryUcService d WHERE d.placementDate = :placementDate"),
    @NamedQuery(name = "DeliveryUcService.findByValidDate", query = "SELECT d FROM DeliveryUcService d WHERE d.validDate = :validDate")})
public class DeliveryUcService implements Serializable, EntityAuditable {
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
    @Column(name = "no_reg", length = 30)
    private String noReg;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "no_bl")
    private String noBl;
    @Column(name = "weight", length = 10)
    private String weight;
    @Column(name = "truck_lossing")
    private String truckLossing;
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;

    public DeliveryUcService() {
    }

    public DeliveryUcService(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
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

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
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
        if (!(object instanceof DeliveryUcService)) {
            return false;
        }
        DeliveryUcService other = (DeliveryUcService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.DeliveryUcService[jobSlip=" + jobSlip + "]";
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
