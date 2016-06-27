/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "planning_vessel_history")
@NamedQueries({
    @NamedQuery(name = "PlanningVesselHistory.findAll", query = "SELECT p FROM PlanningVesselHistory p"),
    @NamedQuery(name = "PlanningVesselHistory.findById", query = "SELECT p FROM PlanningVesselHistory p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningVesselHistory.findByNoPpkb", query = "SELECT p FROM PlanningVesselHistory p WHERE p.noPpkb = :noPpkb"),
    @NamedQuery(name = "PlanningVesselHistory.findByDockCode", query = "SELECT p FROM PlanningVesselHistory p WHERE p.dockCode = :dockCode"),
    @NamedQuery(name = "PlanningVesselHistory.findByBookingCode", query = "SELECT p FROM PlanningVesselHistory p WHERE p.bookingCode = :bookingCode"),
    @NamedQuery(name = "PlanningVesselHistory.findByVesselCode", query = "SELECT p FROM PlanningVesselHistory p WHERE p.vesselCode = :vesselCode"),
    @NamedQuery(name = "PlanningVesselHistory.findByTipePelayaran", query = "SELECT p FROM PlanningVesselHistory p WHERE p.tipePelayaran = :tipePelayaran"),
    @NamedQuery(name = "PlanningVesselHistory.findByEstDischarge", query = "SELECT p FROM PlanningVesselHistory p WHERE p.estDischarge = :estDischarge"),
    @NamedQuery(name = "PlanningVesselHistory.findByEstLoad", query = "SELECT p FROM PlanningVesselHistory p WHERE p.estLoad = :estLoad"),
    @NamedQuery(name = "PlanningVesselHistory.findByEta", query = "SELECT p FROM PlanningVesselHistory p WHERE p.eta = :eta"),
    @NamedQuery(name = "PlanningVesselHistory.findByEbt", query = "SELECT p FROM PlanningVesselHistory p WHERE p.ebt = :ebt"),
    @NamedQuery(name = "PlanningVesselHistory.findByEtd", query = "SELECT p FROM PlanningVesselHistory p WHERE p.etd = :etd"),
    @NamedQuery(name = "PlanningVesselHistory.findByEstStartWork", query = "SELECT p FROM PlanningVesselHistory p WHERE p.estStartWork = :estStartWork"),
    @NamedQuery(name = "PlanningVesselHistory.findByEstEndWork", query = "SELECT p FROM PlanningVesselHistory p WHERE p.estEndWork = :estEndWork"),
    @NamedQuery(name = "PlanningVesselHistory.findByCloseRec", query = "SELECT p FROM PlanningVesselHistory p WHERE p.closeRec = :closeRec"),
    @NamedQuery(name = "PlanningVesselHistory.findByCloseDocRec", query = "SELECT p FROM PlanningVesselHistory p WHERE p.closeDocRec = :closeDocRec"),
    @NamedQuery(name = "PlanningVesselHistory.findByCloseEmpty", query = "SELECT p FROM PlanningVesselHistory p WHERE p.closeEmpty = :closeEmpty"),
    @NamedQuery(name = "PlanningVesselHistory.findByCloseReefer", query = "SELECT p FROM PlanningVesselHistory p WHERE p.closeReefer = :closeReefer"),
    @NamedQuery(name = "PlanningVesselHistory.findByCloseDocMtyref", query = "SELECT p FROM PlanningVesselHistory p WHERE p.closeDocMtyref = :closeDocMtyref"),
    @NamedQuery(name = "PlanningVesselHistory.findByPaymentType", query = "SELECT p FROM PlanningVesselHistory p WHERE p.paymentType = :paymentType"),
    @NamedQuery(name = "PlanningVesselHistory.findByStatus", query = "SELECT p FROM PlanningVesselHistory p WHERE p.status = :status"),
    @NamedQuery(name = "PlanningVesselHistory.findByFrMeter", query = "SELECT p FROM PlanningVesselHistory p WHERE p.frMeter = :frMeter"),
    @NamedQuery(name = "PlanningVesselHistory.findByToMeter", query = "SELECT p FROM PlanningVesselHistory p WHERE p.toMeter = :toMeter"),
    @NamedQuery(name = "PlanningVesselHistory.findByCheckBaplie", query = "SELECT p FROM PlanningVesselHistory p WHERE p.checkBaplie = :checkBaplie"),
    @NamedQuery(name = "PlanningVesselHistory.findByBaplieDischarge", query = "SELECT p FROM PlanningVesselHistory p WHERE p.baplieDischarge = :baplieDischarge"),
    @NamedQuery(name = "PlanningVesselHistory.findByModifiedBy", query = "SELECT p FROM PlanningVesselHistory p WHERE p.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "PlanningVesselHistory.findByModifiedDate", query = "SELECT p FROM PlanningVesselHistory p WHERE p.modifiedDate = :modifiedDate")})
public class PlanningVesselHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "dock_code", length = 10)
    private String dockCode;
    @Column(name = "booking_code", length = 20)
    private String bookingCode;
    @Column(name = "vessel_code", length = 10)
    private String vesselCode;
    @Column(name = "tipe_pelayaran", length = 15)
    private String tipePelayaran;
    @Column(name = "est_discharge")
    private Short estDischarge;
    @Column(name = "est_load")
    private Short estLoad;
    @Column(name = "eta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eta;
    @Column(name = "ebt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ebt;
    @Column(name = "etd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date etd;
    @Column(name = "est_start_work")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estStartWork;
    @Column(name = "est_end_work")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estEndWork;
    @Column(name = "close_rec")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeRec;
    @Column(name = "close_doc_rec")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDocRec;
    @Column(name = "close_empty")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeEmpty;
    @Column(name = "close_reefer")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeReefer;
    @Column(name = "close_doc_mtyref")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDocMtyref;
    @Column(name = "payment_type", length = 10)
    private String paymentType;
    @Column(name = "status", length = 15)
    private String status;
    @Column(name = "fr_meter")
    private Short frMeter;
    @Column(name = "to_meter")
    private Short toMeter;
    @Column(name = "check_baplie")
    private String checkBaplie;
    @Column(name = "baplie_discharge")
    private String baplieDischarge;
    @Column(name = "modified_by", length = 256)
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public PlanningVesselHistory() {
    }

    public PlanningVesselHistory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getDockCode() {
        return dockCode;
    }

    public void setDockCode(String dockCode) {
        this.dockCode = dockCode;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getTipePelayaran() {
        return tipePelayaran;
    }

    public void setTipePelayaran(String tipePelayaran) {
        this.tipePelayaran = tipePelayaran;
    }

    public Short getEstDischarge() {
        return estDischarge;
    }

    public void setEstDischarge(Short estDischarge) {
        this.estDischarge = estDischarge;
    }

    public Short getEstLoad() {
        return estLoad;
    }

    public void setEstLoad(Short estLoad) {
        this.estLoad = estLoad;
    }

    public Date getEta() {
        return eta;
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    public Date getEbt() {
        return ebt;
    }

    public void setEbt(Date ebt) {
        this.ebt = ebt;
    }

    public Date getEtd() {
        return etd;
    }

    public void setEtd(Date etd) {
        this.etd = etd;
    }

    public Date getEstStartWork() {
        return estStartWork;
    }

    public void setEstStartWork(Date estStartWork) {
        this.estStartWork = estStartWork;
    }

    public Date getEstEndWork() {
        return estEndWork;
    }

    public void setEstEndWork(Date estEndWork) {
        this.estEndWork = estEndWork;
    }

    public Date getCloseRec() {
        return closeRec;
    }

    public void setCloseRec(Date closeRec) {
        this.closeRec = closeRec;
    }

    public Date getCloseDocRec() {
        return closeDocRec;
    }

    public void setCloseDocRec(Date closeDocRec) {
        this.closeDocRec = closeDocRec;
    }

    public Date getCloseEmpty() {
        return closeEmpty;
    }

    public void setCloseEmpty(Date closeEmpty) {
        this.closeEmpty = closeEmpty;
    }

    public Date getCloseReefer() {
        return closeReefer;
    }

    public void setCloseReefer(Date closeReefer) {
        this.closeReefer = closeReefer;
    }

    public Date getCloseDocMtyref() {
        return closeDocMtyref;
    }

    public void setCloseDocMtyref(Date closeDocMtyref) {
        this.closeDocMtyref = closeDocMtyref;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Short getFrMeter() {
        return frMeter;
    }

    public void setFrMeter(Short frMeter) {
        this.frMeter = frMeter;
    }

    public Short getToMeter() {
        return toMeter;
    }

    public void setToMeter(Short toMeter) {
        this.toMeter = toMeter;
    }

    public String getCheckBaplie() {
        return checkBaplie;
    }

    public void setCheckBaplie(String checkBaplie) {
        this.checkBaplie = checkBaplie;
    }

    public String getBaplieDischarge() {
        return baplieDischarge;
    }

    public void setBaplieDischarge(String baplieDischarge) {
        this.baplieDischarge = baplieDischarge;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanningVesselHistory)) {
            return false;
        }
        PlanningVesselHistory other = (PlanningVesselHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningVesselHistory[id=" + id + "]";
    }

}
