/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "preservice_vessel")
@NamedQueries({
    @NamedQuery(name = "PreserviceVessel.findAll", query = "SELECT p FROM PreserviceVessel p"),
    @NamedQuery(name = "PreserviceVessel.findByBookingCode", query = "SELECT p FROM PreserviceVessel p WHERE p.bookingCode = :bookingCode"),
    @NamedQuery(name = "PreserviceVessel.findByVoyIn", query = "SELECT p FROM PreserviceVessel p WHERE p.voyIn = :voyIn"),
    @NamedQuery(name = "PreserviceVessel.findByVoyOut", query = "SELECT p FROM PreserviceVessel p WHERE p.voyOut = :voyOut"),
    @NamedQuery(name = "PreserviceVessel.findByActivity", query = "SELECT p FROM PreserviceVessel p WHERE p.activity = :activity"),
    @NamedQuery(name = "PreserviceVessel.findByEta", query = "SELECT p FROM PreserviceVessel p WHERE p.eta = :eta"),
    @NamedQuery(name = "PreserviceVessel.findByEtd", query = "SELECT p FROM PreserviceVessel p WHERE p.etd = :etd"),
    @NamedQuery(name = "PreserviceVessel.findByEstDischarge", query = "SELECT p FROM PreserviceVessel p WHERE p.estDischarge = :estDischarge"),
    @NamedQuery(name = "PreserviceVessel.findByEstLoad", query = "SELECT p FROM PreserviceVessel p WHERE p.estLoad = :estLoad"),
    @NamedQuery(name = "PreserviceVessel.findByOpenStack", query = "SELECT p FROM PreserviceVessel p WHERE p.openStack = :openStack"),
    @NamedQuery(name = "PreserviceVessel.findByClosingTime", query = "SELECT p FROM PreserviceVessel p WHERE p.closingTime = :closingTime"),
    @NamedQuery(name = "PreserviceVessel.findByStatus", query = "SELECT p FROM PreserviceVessel p WHERE p.status = :status")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PreserviceVessel.Native.findPreserviceVessels", query = "SELECT v.name, c.name, p.voy_in, p.voy_out, p.eta, p.etd, m.name, m2.name, p.status, p.booking_code FROM preservice_vessel p, m_port m, m_port m2, m_vessel v, m_customer c WHERE p.vessel_code = v.vessel_code AND p.cust_code = c.cust_code AND p.last_port_code = m.port_code AND p.next_port_code = m2.port_code AND p.status='Booking' ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PreserviceVessel.Native.findCanceledPreserviceVessels", query = "SELECT v.name, c.name, p.voy_in, p.voy_out, p.eta, p.etd, m.name, m2.name, p.status, p.booking_code, pv.no_ppkb "
    + "FROM preservice_vessel p "
            + "JOIN m_port m ON (p.last_port_code = m.port_code) "
            + "JOIN m_port m2 ON (p.next_port_code = m2.port_code) "
            + "JOIN m_vessel v ON (p.vessel_code = v.vessel_code) "
            + "JOIN m_customer c ON (p.cust_code = c.cust_code) "
            + "LEFT JOIN planning_vessel pv ON (p.booking_code = pv.booking_code) "
    + "WHERE p.status='Canceled' ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PreserviceVessel.Native.findPreserviceVesselOnlines", query = "SELECT v.name, c.name, p.voy_in, p.voy_out, p.eta, p.etd, m.name, m2.name, p.status, p.booking_code FROM preservice_vessel p, m_port m, m_port m2, m_vessel v, m_customer c WHERE p.vessel_code = v.vessel_code AND p.cust_code = c.cust_code AND p.last_port_code = m.port_code AND p.next_port_code = m2.port_code AND c.cust_code=? ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PreserviceVessel.Native.findPreserviceVesselByBooking", query = "SELECT p.booking_code, p.vessel_name, v.grt, v.dwt, v.brt, v.nrt, v.loa, p.agent, p.voy_in, p.voy_out, p.eta, p.etd, p.est_discharge, p.est_load, p.last_port, p.next_port FROM preservice_vessel p, m_vessel v WHERE p.vessel_name = v.name AND p.booking_code = ?"),
    @NamedNativeQuery(name = "PreserviceVessel.Native.findPreserviceVesselByPort", query = "SELECT v.booking_code, v.last_port_code, v.next_port_code FROM preservice_vessel v WHERE v.last_port_code = ? OR v.next_port_code = ? ORDER BY v.created_date DESC"),
    @NamedNativeQuery(name = "PreserviceVessel.Native.findPreserviceVesselByVessel", query = "SELECT p.booking_code FROM preservice_vessel p WHERE p.vessel_code = ? ORDER BY p.created_date DESC")})
public class PreserviceVessel implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;

    public static final Short DISCHARGE = 1;
    public static final Short LOAD = 2;
    public static final Short LOAD_DISCHARGE = 3;
    @Id
    @Basic(optional = false)
    @Column(name = "booking_code", nullable = false, length = 20)
    private String bookingCode;
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
    private short estDischarge = 0;
    @Basic(optional = false)
    @Column(name = "est_load", nullable = false)
    private short estLoad = 0;
    @Column(name = "open_stack")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openStack;
    @Column(name = "closing_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closingTime;
    @Basic(optional = false)
    @Column(name = "status", nullable = false, length = 15)
    private String status;
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
    @OneToMany(mappedBy = "preserviceVessel")
    private List<Payment> paymentList;
    @JoinColumn(name = "vessel_code", referencedColumnName = "vessel_code")
    @ManyToOne
    private MasterVessel masterVessel;
    @JoinColumn(name = "next_port_code", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort masterPort;
    @JoinColumn(name = "last_port_code", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort masterPort1;
    @JoinColumn(name = "cust_code", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer masterCustomer;
    @OneToOne(mappedBy = "preserviceVessel")
    private PlanningVessel planningVessel;

    public PreserviceVessel() {
        activity = DISCHARGE;
    }

    public PreserviceVessel(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public PreserviceVessel(String bookingCode, String voyIn, String voyOut, Date eta, Date etd, short estDischarge, short estLoad, String status) {
        this.bookingCode = bookingCode;
        this.voyIn = voyIn;
        this.voyOut = voyOut;
        this.eta = eta;
        this.etd = etd;
        this.estDischarge = estDischarge;
        this.estLoad = estLoad;
        this.status = status;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
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

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public MasterVessel getMasterVessel() {
        return masterVessel;
    }

    public void setMasterVessel(MasterVessel masterVessel) {
        this.masterVessel = masterVessel;
    }

    public MasterPort getMasterPort() {
        return masterPort;
    }

    public void setMasterPort(MasterPort masterPort) {
        this.masterPort = masterPort;
    }

    public MasterPort getMasterPort1() {
        return masterPort1;
    }

    public void setMasterPort1(MasterPort masterPort1) {
        this.masterPort1 = masterPort1;
    }

    public MasterCustomer getMasterCustomer() {
        return masterCustomer;
    }

    public void setMasterCustomer(MasterCustomer masterCustomer) {
        this.masterCustomer = masterCustomer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingCode != null ? bookingCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreserviceVessel)) {
            return false;
        }
        PreserviceVessel other = (PreserviceVessel) object;
        if ((this.bookingCode == null && other.bookingCode != null) || (this.bookingCode != null && !this.bookingCode.equals(other.bookingCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PreserviceVessel[bookingCode=" + bookingCode + "]";
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

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public Boolean isLoad() {
        if (activity != null && (activity.equals(LOAD) || activity.equals(LOAD_DISCHARGE)))
            return true;

        return false;
    }

    public Boolean isDischarge() {
        if (activity != null && activity.equals(DISCHARGE))
            return true;

        return false;
    }

    public Boolean isLoadDischarge() {
        if (activity != null && activity.equals(LOAD_DISCHARGE))
            return true;

        return false;
    }
}
