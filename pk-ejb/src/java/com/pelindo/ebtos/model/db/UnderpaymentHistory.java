/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

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
 * @author x42jr
 */
@Entity
@Table(name = "underpayment_history")
@EntityListeners({AuditTrailEntityListener.class})
@NamedQueries({
    @NamedQuery(name = "UnderpaymentHistory.findAll", query = "SELECT u FROM UnderpaymentHistory u"),
    @NamedQuery(name = "UnderpaymentHistory.findByJobSlip", query = "SELECT u FROM UnderpaymentHistory u WHERE u.jobSlip = :jobSlip")})
public class UnderpaymentHistory implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private String twentyOneFeet = "FALSE";
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobSlip;
    @Basic(optional = false)
    @Column(name = "charge_lift", nullable = false, precision = 19, scale = 2)
    private BigDecimal chargeLift = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "charge_handling", nullable = false, precision = 19, scale = 2)
    private BigDecimal chargeHandling = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "charge_penumpukan", nullable = false, precision = 19, scale = 2)
    private BigDecimal chargePenumpukan = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "charge_biaya_handling", nullable = false, precision = 19, scale = 2)
    private BigDecimal chargeBiayaHandling = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "charge_pass_gate", nullable = false, precision = 19, scale = 2)
    private BigDecimal chargePassGate = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "total_charge", nullable = false, precision = 19, scale = 2)
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
    @JoinColumn(name = "no_reg_underpayment", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration underpaymentRegistration;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration registration;
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
    private String overSize = "FALSE";
    @Basic(optional = false)
    @Column(name = "dg", nullable = false)
    private String dg = "FALSE";
    @Basic(optional = false)
    @Column(name = "dg_label", nullable = false)
    private String dgLabel = "FALSE";
    @Column(name = "bl_no", length = 100)
    private String blNo;
    @Basic(optional = false)
    @Column(name = "is_export", nullable = false)
    private String isExport = "FALSE";
    @JoinColumn(name = "port_of_delivery", referencedColumnName = "port_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterPort portOfDelivery;
    @JoinColumn(name = "disch_port_code", referencedColumnName = "port_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterPort dischargePort;
    @JoinColumn(name = "load_port_code", referencedColumnName = "port_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterPort loadPort;
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

    public UnderpaymentHistory() {
    }

    public UnderpaymentHistory(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public UnderpaymentHistory(String jobSlip, BigDecimal chargeLift, BigDecimal chargeHandling, BigDecimal chargePenumpukan, BigDecimal chargeBiayaHandling, BigDecimal chargePassGate, String createdBy, Date createdDate) {
        this.jobSlip = jobSlip;
        this.chargeLift = chargeLift;
        this.chargeHandling = chargeHandling;
        this.chargePenumpukan = chargePenumpukan;
        this.chargeBiayaHandling = chargeBiayaHandling;
        this.chargePassGate = chargePassGate;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public BigDecimal getChargeBiayaHandling() {
        return chargeBiayaHandling;
    }

    public void setChargeBiayaHandling(BigDecimal chargeBiayaHandling) {
        this.chargeBiayaHandling = chargeBiayaHandling;
    }

    public BigDecimal getChargeHandling() {
        return chargeHandling;
    }

    public void setChargeHandling(BigDecimal chargeHandling) {
        this.chargeHandling = chargeHandling;
    }

    public BigDecimal getChargeLift() {
        return chargeLift;
    }

    public void setChargeLift(BigDecimal chargeLift) {
        this.chargeLift = chargeLift;
    }

    public BigDecimal getChargePassGate() {
        return chargePassGate;
    }

    public void setChargePassGate(BigDecimal chargePassGate) {
        this.chargePassGate = chargePassGate;
    }

    public BigDecimal getChargePenumpukan() {
        return chargePenumpukan;
    }

    public void setChargePenumpukan(BigDecimal chargePenumpukan) {
        this.chargePenumpukan = chargePenumpukan;
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

    public String isDg() {
        return dg;
    }

    public void setDg(String dg) {
        this.dg = dg;
    }

    public String isDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(String dgLabel) {
        this.dgLabel = dgLabel;
    }

    public MasterPort getDischargePort() {
        return dischargePort;
    }

    public void setDischargePort(MasterPort dischargePort) {
        this.dischargePort = dischargePort;
    }

    public String isIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
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

    public String isOverSize() {
        return overSize;
    }

    public void setOverSize(String overSize) {
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

    public Registration getUnderpaymentRegistration() {
        return underpaymentRegistration;
    }

    public void setUnderpaymentRegistration(Registration underpaymentRegistration) {
        this.underpaymentRegistration = underpaymentRegistration;
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

    public String getGrossClass() {
        return grossClass;
    }

    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
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
        if (!(object instanceof UnderpaymentHistory)) {
            return false;
        }
        UnderpaymentHistory other = (UnderpaymentHistory) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.UnderpaymentHistory[jobSlip=" + jobSlip + "]";
    }

    public String getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(String twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
