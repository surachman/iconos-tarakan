/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
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
 * @author x42jr
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "cancel_document_service")
@NamedQueries({
    @NamedQuery(name = "CancelDocumentService.findAll", query = "SELECT c FROM CancelDocumentService c"),
    @NamedQuery(name = "CancelDocumentService.findByNoReg", query = "SELECT c FROM CancelDocumentService c WHERE c.registration.noReg = :noReg")})
public class CancelDocumentService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobSlip;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Basic(optional = false)
    @Column(name = "cont_size", nullable = false)
    private short contSize;
    @Basic(optional = false)
    @Column(name = "cont_status", nullable = false, length = 10)
    private String contStatus;
    @Basic(optional = false)
    @Column(name = "cont_gross", nullable = false)
    private int contGross = 0;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Basic(optional = false)
    @Column(name = "over_size", nullable = false)
    private boolean overSize = false;
    @Basic(optional = false)
    @Column(name = "dg", nullable = false)
    private boolean dg = false;
    @Basic(optional = false)
    @Column(name = "dg_label", nullable = false)
    private boolean dgLabel = false;
    @Column(name = "bl_no", length = 100)
    private String blNo;
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
    @Basic(optional = false)
    @Column(name = "is_export", nullable = false)
    private boolean isExport = false;
    @Basic(optional = false)
    @Column(name = "total_charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCharge = BigDecimal.ZERO;
    @JoinColumn(name = "reference_no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration referenceReceiving;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration registration;
    @JoinColumn(name = "new_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel newPlanningVessel;
    @JoinColumn(name = "port_of_delivery", referencedColumnName = "port_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterPort portOfDelivery;
    @JoinColumn(name = "load_port_code", referencedColumnName = "port_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterPort loadPort;
    @JoinColumn(name = "disch_port_code", referencedColumnName = "port_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterPort dischargePort;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type", nullable = false)
    @ManyToOne(optional = false)
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCommodity masterCommodity;
    @Basic(optional = false)
    @Column(name = "gross_class", nullable = false, length = 5)
    private String grossClass;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterActivity masterActivity;
    @JoinColumn(name = "perhitungan_handling_discharge_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganHandlingDischarge perhitunganHandlingDischarge;
    @JoinColumn(name = "perhitungan_lift_service_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganLiftService perhitunganLiftService;
    @JoinColumn(name = "perhitungan_pass_gate_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganPassGate perhitunganPassGate;
    @JoinColumn(name = "perhitungan_penumpukan_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganPenumpukan perhitunganPenumpukan;
    @JoinColumn(name = "perhitungan_upah_buruh_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganUpahBuruh perhitunganUpahBuruh;
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;

    public CancelDocumentService() {
    }

    public CancelDocumentService(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public CancelDocumentService(String jobSlip, String contNo, short contSize, String contStatus, int contGross, boolean overSize, boolean dg, boolean dgLabel, BigDecimal charge, String createdBy, Date createdDate, boolean isExport) {
        this.jobSlip = jobSlip;
        this.contNo = contNo;
        this.contSize = contSize;
        this.contStatus = contStatus;
        this.contGross = contGross;
        this.overSize = overSize;
        this.dg = dg;
        this.dgLabel = dgLabel;
        this.charge = charge;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.isExport = isExport;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public int getContGross() {
        return contGross;
    }

    public void setContGross(int contGross) {
        this.contGross = contGross;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public short getContSize() {
        return contSize;
    }

    public void setContSize(short contSize) {
        this.contSize = contSize;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
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

    public boolean isDg() {
        return dg;
    }

    public void setDg(boolean dg) {
        this.dg = dg;
    }

    public boolean isDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(boolean dgLabel) {
        this.dgLabel = dgLabel;
    }

    public MasterPort getDischargePort() {
        return dischargePort;
    }

    public void setDischargePort(MasterPort dischargePort) {
        this.dischargePort = dischargePort;
    }

    public boolean isIsExport() {
        return isExport;
    }

    public void setIsExport(boolean isExport) {
        this.isExport = isExport;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public MasterPort getLoadPort() {
        return loadPort;
    }

    public void setLoadPort(MasterPort loadPort) {
        this.loadPort = loadPort;
    }
    
    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public MasterCustomer getMlo() {
        return mlo;
    }

    public void setMlo(MasterCustomer mlo) {
        this.mlo = mlo;
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

    public PlanningVessel getNewPlanningVessel() {
        return newPlanningVessel;
    }

    public void setNewPlanningVessel(PlanningVessel newPlanningVessel) {
        this.newPlanningVessel = newPlanningVessel;
    }

    public boolean isOverSize() {
        return overSize;
    }

    public void setOverSize(boolean overSize) {
        this.overSize = overSize;
    }

    public MasterPort getPortOfDelivery() {
        return portOfDelivery;
    }

    public void setPortOfDelivery(MasterPort portOfDelivery) {
        this.portOfDelivery = portOfDelivery;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Registration getReferenceReceiving() {
        return referenceReceiving;
    }

    public void setReferenceReceiving(Registration referenceReceiving) {
        this.referenceReceiving = referenceReceiving;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getGrossClass() {
        return grossClass;
    }

    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    public Boolean getIsUnderpaymentRequired() {
        return perhitunganHandlingDischarge != null || perhitunganLiftService != null || perhitunganPassGate != null || perhitunganPenumpukan != null || perhitunganUpahBuruh != null;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }

    public Integer getContTeus() {
         return (int) Math.ceil((int) ((double) contSize / (double) 20));
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }

    public PerhitunganHandlingDischarge getPerhitunganHandlingDischarge() {
        return perhitunganHandlingDischarge;
    }

    public void setPerhitunganHandlingDischarge(PerhitunganHandlingDischarge perhitunganHandlingDischarge) {
        this.perhitunganHandlingDischarge = perhitunganHandlingDischarge;
    }

    public PerhitunganLiftService getPerhitunganLiftService() {
        return perhitunganLiftService;
    }

    public void setPerhitunganLiftService(PerhitunganLiftService perhitunganLiftService) {
        this.perhitunganLiftService = perhitunganLiftService;
    }

    public PerhitunganPassGate getPerhitunganPassGate() {
        return perhitunganPassGate;
    }

    public void setPerhitunganPassGate(PerhitunganPassGate perhitunganPassGate) {
        this.perhitunganPassGate = perhitunganPassGate;
    }

    public PerhitunganPenumpukan getPerhitunganPenumpukan() {
        return perhitunganPenumpukan;
    }

    public void setPerhitunganPenumpukan(PerhitunganPenumpukan perhitunganPenumpukan) {
        this.perhitunganPenumpukan = perhitunganPenumpukan;
    }

    public PerhitunganUpahBuruh getPerhitunganUpahBuruh() {
        return perhitunganUpahBuruh;
    }

    public void setPerhitunganUpahBuruh(PerhitunganUpahBuruh perhitunganUpahBuruh) {
        this.perhitunganUpahBuruh = perhitunganUpahBuruh;
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
        if (!(object instanceof CancelDocumentService)) {
            return false;
        }
        CancelDocumentService other = (CancelDocumentService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.CancelDocumentService[jobSlip=" + jobSlip + "]";
    }
}
