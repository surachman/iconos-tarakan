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
@Table(name = "preservice_vessel_history")
@NamedQueries({
    @NamedQuery(name = "PreserviceVesselHistory.findAll", query = "SELECT p FROM PreserviceVesselHistory p"),
    @NamedQuery(name = "PreserviceVesselHistory.findById", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.id = :id"),
    @NamedQuery(name = "PreserviceVesselHistory.findByBookingCode", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.bookingCode = :bookingCode"),
    @NamedQuery(name = "PreserviceVesselHistory.findByVesselCode", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.vesselCode = :vesselCode"),
    @NamedQuery(name = "PreserviceVesselHistory.findByCustCode", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.custCode = :custCode"),
    @NamedQuery(name = "PreserviceVesselHistory.findByLastPortCode", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.lastPortCode = :lastPortCode"),
    @NamedQuery(name = "PreserviceVesselHistory.findByNextPortCode", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.nextPortCode = :nextPortCode"),
    @NamedQuery(name = "PreserviceVesselHistory.findByVoyIn", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.voyIn = :voyIn"),
    @NamedQuery(name = "PreserviceVesselHistory.findByVoyOut", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.voyOut = :voyOut"),
    @NamedQuery(name = "PreserviceVesselHistory.findByActivity", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.activity = :activity"),
    @NamedQuery(name = "PreserviceVesselHistory.findByEta", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.eta = :eta"),
    @NamedQuery(name = "PreserviceVesselHistory.findByEtd", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.etd = :etd"),
    @NamedQuery(name = "PreserviceVesselHistory.findByEstDischarge", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.estDischarge = :estDischarge"),
    @NamedQuery(name = "PreserviceVesselHistory.findByEstLoad", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.estLoad = :estLoad"),
    @NamedQuery(name = "PreserviceVesselHistory.findByOpenStack", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.openStack = :openStack"),
    @NamedQuery(name = "PreserviceVesselHistory.findByClosingTime", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.closingTime = :closingTime"),
    @NamedQuery(name = "PreserviceVesselHistory.findByStatus", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.status = :status"),
    @NamedQuery(name = "PreserviceVesselHistory.findByModifiedBy", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "PreserviceVesselHistory.findByModifiedDate", query = "SELECT p FROM PreserviceVesselHistory p WHERE p.modifiedDate = :modifiedDate")})
public class PreserviceVesselHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "booking_code", nullable = false, length = 20)
    private String bookingCode;
    @Column(name = "vessel_code", length = 10)
    private String vesselCode;
    @Column(name = "cust_code", length = 10)
    private String custCode;
    @Column(name = "last_port_code", length = 10)
    private String lastPortCode;
    @Column(name = "next_port_code", length = 10)
    private String nextPortCode;
    @Basic(optional = false)
    @Column(name = "voy_in", nullable = false, length = 10)
    private String voyIn;
    @Basic(optional = false)
    @Column(name = "voy_out", nullable = false, length = 10)
    private String voyOut;
    @Column(name = "activity")
    private Short activity;
    @Basic(optional = false)
    @Column(name = "eta", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eta;
    @Basic(optional = false)
    @Column(name = "etd", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date etd;
    @Basic(optional = false)
    @Column(name = "est_discharge", nullable = false)
    private short estDischarge;
    @Basic(optional = false)
    @Column(name = "est_load", nullable = false)
    private short estLoad;
    @Column(name = "open_stack")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openStack;
    @Column(name = "closing_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closingTime;
    @Basic(optional = false)
    @Column(name = "status", nullable = false, length = 15)
    private String status;
    @Column(name = "modified_by", length = 256)
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public PreserviceVesselHistory() {
    }

    public PreserviceVesselHistory(Integer id) {
        this.id = id;
    }

    public PreserviceVesselHistory(Integer id, String bookingCode, String voyIn, String voyOut, Date eta, Date etd, short estDischarge, short estLoad, String status) {
        this.id = id;
        this.bookingCode = bookingCode;
        this.voyIn = voyIn;
        this.voyOut = voyOut;
        this.eta = eta;
        this.etd = etd;
        this.estDischarge = estDischarge;
        this.estLoad = estLoad;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getLastPortCode() {
        return lastPortCode;
    }

    public void setLastPortCode(String lastPortCode) {
        this.lastPortCode = lastPortCode;
    }

    public String getNextPortCode() {
        return nextPortCode;
    }

    public void setNextPortCode(String nextPortCode) {
        this.nextPortCode = nextPortCode;
    }

    public String getVoyIn() {
        return voyIn;
    }

    public void setVoyIn(String voyIn) {
        this.voyIn = voyIn;
    }

    public String getVoyOut() {
        return voyOut;
    }

    public void setVoyOut(String voyOut) {
        this.voyOut = voyOut;
    }

    public Short getActivity() {
        return activity;
    }

    public void setActivity(Short activity) {
        this.activity = activity;
    }

    public Date getEta() {
        return eta;
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    public Date getEtd() {
        return etd;
    }

    public void setEtd(Date etd) {
        this.etd = etd;
    }

    public short getEstDischarge() {
        return estDischarge;
    }

    public void setEstDischarge(short estDischarge) {
        this.estDischarge = estDischarge;
    }

    public short getEstLoad() {
        return estLoad;
    }

    public void setEstLoad(short estLoad) {
        this.estLoad = estLoad;
    }

    public Date getOpenStack() {
        return openStack;
    }

    public void setOpenStack(Date openStack) {
        this.openStack = openStack;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof PreserviceVesselHistory)) {
            return false;
        }
        PreserviceVesselHistory other = (PreserviceVesselHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PreserviceVesselHistory[id=" + id + "]";
    }

}
