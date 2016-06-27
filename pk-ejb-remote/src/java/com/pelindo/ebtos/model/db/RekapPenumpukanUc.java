/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "rekap_penumpukan_uc")
@NamedQueries({
    @NamedQuery(name = "RekapPenumpukanUc.findAll", query = "SELECT r FROM RekapPenumpukanUc r"),
    @NamedQuery(name = "RekapPenumpukanUc.findById", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.id = :id"),
    @NamedQuery(name = "RekapPenumpukanUc.findByNoBl", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.noBl = :noBl"),
    @NamedQuery(name = "RekapPenumpukanUc.findByCrane", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.crane = :crane"),
    @NamedQuery(name = "RekapPenumpukanUc.findByWeight", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.weight = :weight"),
    @NamedQuery(name = "RekapPenumpukanUc.findByTruckLossing", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.truckLossing = :truckLossing"),
    @NamedQuery(name = "RekapPenumpukanUc.findByUcTracking", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.ucTracking = :ucTracking"),
    @NamedQuery(name = "RekapPenumpukanUc.findByPlacementDate", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.placementDate = :placementDate"),
    @NamedQuery(name = "RekapPenumpukanUc.findByStartWorkDate", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.startWorkDate = :startWorkDate"),
    @NamedQuery(name = "RekapPenumpukanUc.findByEndWorkDate", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.endWorkDate = :endWorkDate"),
    @NamedQuery(name = "RekapPenumpukanUc.findByTotalDay", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.totalDay = :totalDay"),
    @NamedQuery(name = "RekapPenumpukanUc.findByProdMasa11", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.prodMasa11 = :prodMasa11"),
    @NamedQuery(name = "RekapPenumpukanUc.findByChargeMasa11", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.chargeMasa11 = :chargeMasa11"),
    @NamedQuery(name = "RekapPenumpukanUc.findByProdMasa12", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.prodMasa12 = :prodMasa12"),
    @NamedQuery(name = "RekapPenumpukanUc.findByChargeMasa12", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.chargeMasa12 = :chargeMasa12"),
    @NamedQuery(name = "RekapPenumpukanUc.findByProdMasa2", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.prodMasa2 = :prodMasa2"),
    @NamedQuery(name = "RekapPenumpukanUc.findByChargeMasa2", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.chargeMasa2 = :chargeMasa2"),
    @NamedQuery(name = "RekapPenumpukanUc.findByProdJasaDermaga", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.prodJasaDermaga = :prodJasaDermaga"),
    @NamedQuery(name = "RekapPenumpukanUc.findByChargeJasaDermaga", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.chargeJasaDermaga = :chargeJasaDermaga"),
    @NamedQuery(name = "RekapPenumpukanUc.findByTotalCharge", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RekapPenumpukanUc.findByOperation", query = "SELECT r FROM RekapPenumpukanUc r WHERE r.operation = :operation")})
public class RekapPenumpukanUc implements Serializable, EntityAuditable {
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
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "no_bl", nullable = false)
    private String noBl;
    @Column(name = "crane", length = 2)
    private String crane;
    @Basic(optional = false)
    @Column(name = "weight", nullable = false, length = 3)
    private String weight;
    @Column(name = "truck_lossing")
    private Boolean truckLossing;
    @Column(name = "uc_tracking", length = 10)
    private String ucTracking;
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @Column(name = "start_work_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startWorkDate;
    @Column(name = "end_work_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endWorkDate;
    @Column(name = "total_day")
    private Short totalDay;
    @Column(name = "prod_masa1_1")
    private Short prodMasa11;
    @Column(name = "charge_masa1_1")
    private BigInteger chargeMasa11;
    @Column(name = "prod_masa1_2")
    private Short prodMasa12;
    @Column(name = "charge_masa1_2")
    private BigInteger chargeMasa12;
    @Column(name = "prod_masa2")
    private Short prodMasa2;
    @Column(name = "charge_masa2")
    private BigInteger chargeMasa2;
    @Column(name = "prod_jasa_dermaga")
    private Short prodJasaDermaga;
    @Column(name = "charge_jasa_dermaga")
    private BigInteger chargeJasaDermaga;
    @Column(name = "total_charge")
    private BigInteger totalCharge;
    @Column(name = "operation", length = 10)
    private String operation;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;

    public RekapPenumpukanUc() {
    }

    public RekapPenumpukanUc(Integer id) {
        this.id = id;
    }

    public RekapPenumpukanUc(Integer id, String noBl, String weight) {
        this.id = id;
        this.noBl = noBl;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoBl() {
        return noBl;
    }

    public void setNoBl(String noBl) {
        this.noBl = noBl;
    }

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Boolean getTruckLossing() {
        return truckLossing;
    }

    public void setTruckLossing(Boolean truckLossing) {
        this.truckLossing = truckLossing;
    }

    public String getUcTracking() {
        return ucTracking;
    }

    public void setUcTracking(String ucTracking) {
        this.ucTracking = ucTracking;
    }

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public Date getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(Date startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public Date getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(Date endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public Short getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Short totalDay) {
        this.totalDay = totalDay;
    }

    public Short getProdMasa11() {
        return prodMasa11;
    }

    public void setProdMasa11(Short prodMasa11) {
        this.prodMasa11 = prodMasa11;
    }

    public BigInteger getChargeMasa11() {
        return chargeMasa11;
    }

    public void setChargeMasa11(BigInteger chargeMasa11) {
        this.chargeMasa11 = chargeMasa11;
    }

    public Short getProdMasa12() {
        return prodMasa12;
    }

    public void setProdMasa12(Short prodMasa12) {
        this.prodMasa12 = prodMasa12;
    }

    public BigInteger getChargeMasa12() {
        return chargeMasa12;
    }

    public void setChargeMasa12(BigInteger chargeMasa12) {
        this.chargeMasa12 = chargeMasa12;
    }

    public Short getProdMasa2() {
        return prodMasa2;
    }

    public void setProdMasa2(Short prodMasa2) {
        this.prodMasa2 = prodMasa2;
    }

    public BigInteger getChargeMasa2() {
        return chargeMasa2;
    }

    public void setChargeMasa2(BigInteger chargeMasa2) {
        this.chargeMasa2 = chargeMasa2;
    }

    public Short getProdJasaDermaga() {
        return prodJasaDermaga;
    }

    public void setProdJasaDermaga(Short prodJasaDermaga) {
        this.prodJasaDermaga = prodJasaDermaga;
    }

    public BigInteger getChargeJasaDermaga() {
        return chargeJasaDermaga;
    }

    public void setChargeJasaDermaga(BigInteger chargeJasaDermaga) {
        this.chargeJasaDermaga = chargeJasaDermaga;
    }

    public BigInteger getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigInteger totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RekapPenumpukanUc)) {
            return false;
        }
        RekapPenumpukanUc other = (RekapPenumpukanUc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RekapPenumpukanUc[id=" + id + "]";
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
