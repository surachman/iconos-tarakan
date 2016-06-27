/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "booking_online")
@NamedQueries({
    @NamedQuery(name = "BookingOnline.findAll", query = "SELECT b FROM BookingOnline b"),
    @NamedQuery(name = "BookingOnline.findById", query = "SELECT b FROM BookingOnline b WHERE b.id = :id"),
    @NamedQuery(name = "BookingOnline.findByVesselName", query = "SELECT b FROM BookingOnline b WHERE b.vesselName = :vesselName"),
    @NamedQuery(name = "BookingOnline.findByCallSign", query = "SELECT b FROM BookingOnline b WHERE b.callSign = :callSign"),
    @NamedQuery(name = "BookingOnline.findByFlag", query = "SELECT b FROM BookingOnline b WHERE b.flag = :flag"),
    @NamedQuery(name = "BookingOnline.findByGrt", query = "SELECT b FROM BookingOnline b WHERE b.grt = :grt"),
    @NamedQuery(name = "BookingOnline.findByDwt", query = "SELECT b FROM BookingOnline b WHERE b.dwt = :dwt"),
    @NamedQuery(name = "BookingOnline.findByBrt", query = "SELECT b FROM BookingOnline b WHERE b.brt = :brt"),
    @NamedQuery(name = "BookingOnline.findByNrt", query = "SELECT b FROM BookingOnline b WHERE b.nrt = :nrt"),
    @NamedQuery(name = "BookingOnline.findByLoa", query = "SELECT b FROM BookingOnline b WHERE b.loa = :loa"),
    @NamedQuery(name = "BookingOnline.findByCustCode", query = "SELECT b FROM BookingOnline b WHERE b.custCode = :custCode"),
    @NamedQuery(name = "BookingOnline.findByLastPortCode", query = "SELECT b FROM BookingOnline b WHERE b.lastPortCode = :lastPortCode"),
    @NamedQuery(name = "BookingOnline.findByNextPortCode", query = "SELECT b FROM BookingOnline b WHERE b.nextPortCode = :nextPortCode"),
    @NamedQuery(name = "BookingOnline.findByVoyIn", query = "SELECT b FROM BookingOnline b WHERE b.voyIn = :voyIn"),
    @NamedQuery(name = "BookingOnline.findByVoyOut", query = "SELECT b FROM BookingOnline b WHERE b.voyOut = :voyOut"),
    @NamedQuery(name = "BookingOnline.findByActivity", query = "SELECT b FROM BookingOnline b WHERE b.activity = :activity"),
    @NamedQuery(name = "BookingOnline.findByEta", query = "SELECT b FROM BookingOnline b WHERE b.eta = :eta"),
    @NamedQuery(name = "BookingOnline.findByEtd", query = "SELECT b FROM BookingOnline b WHERE b.etd = :etd"),
    @NamedQuery(name = "BookingOnline.findByEstDischarge", query = "SELECT b FROM BookingOnline b WHERE b.estDischarge = :estDischarge"),
    @NamedQuery(name = "BookingOnline.findByEstLoad", query = "SELECT b FROM BookingOnline b WHERE b.estLoad = :estLoad"),
    @NamedQuery(name = "BookingOnline.findByStatus", query = "SELECT b FROM BookingOnline b WHERE b.estLoad = :estLoad"),
    @NamedQuery(name = "BookingOnline.findByBookingCode", query = "SELECT b FROM BookingOnline b WHERE b.bookingCode = :bookingCode")})
@NamedNativeQueries({
    @NamedNativeQuery(name ="BookingOnline.Native.findBooking",query="SELECT bo.id, bo.vessel_name, c.name, bo.voy_in, bo.voy_out, bo.eta, bo.etd, bo.est_discharge, bo.est_load, bo.status FROM booking_online bo LEFT JOIN m_customer c ON bo.cust_code = c.cust_code WHERE bo.status = 'Waiting'"),
    @NamedNativeQuery(name ="BookingOnline.Native.findBookingList",query="SELECT p.vessel_name, p.voy_in, p.voy_out, p.eta, p.etd, m.name, m2.name, served(p.status) FROM booking_online p, m_port m, m_port m2, m_customer c WHERE p.cust_code = c.cust_code AND p.last_port_code = m.port_code AND p.next_port_code = m2.port_code AND c.cust_code=? ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name ="BookingOnline.Native.findByBookingCode",query="SELECT bo.id FROM booking_online bo WHERE bo.booking_code = ?")
})
public class BookingOnline implements Serializable, EntityAuditable {
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "vessel_name")
    private String vesselName;
    @Column(name = "call_sign")
    private String callSign;
    @Column(name = "flag")
    private String flag;
    @Column(name = "grt")
    private Integer grt;
    @Column(name = "dwt")
    private Integer dwt;
    @Column(name = "brt")
    private Integer brt;
    @Column(name = "nrt")
    private Integer nrt;
    @Column(name = "loa")
    private Integer loa;
    @Column(name = "cust_code")
    private String custCode;
    @Column(name = "last_port_code")
    private String lastPortCode;
    @Column(name = "next_port_code")
    private String nextPortCode;
    @Basic(optional = false)
    @Column(name = "voy_in")
    private String voyIn;
    @Basic(optional = false)
    @Column(name = "voy_out")
    private String voyOut;
    @Column(name = "activity")
    private Short activity;
    @Basic(optional = false)
    @Column(name = "eta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eta;
    @Basic(optional = false)
    @Column(name = "etd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date etd;
    @Basic(optional = false)
    @Column(name = "est_discharge")
    private short estDischarge;
    @Basic(optional = false)
    @Column(name = "est_load")
    private short estLoad;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "booking_code", length = 20)
    private String bookingCode;

    public BookingOnline() {
    }

    public BookingOnline(Integer id) {
        this.id = id;
    }

    public BookingOnline(Integer id, String voyIn, String voyOut, Date eta, Date etd, short estDischarge, short estLoad, String status) {
        this.id = id;
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

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getGrt() {
        return grt;
    }

    public void setGrt(Integer grt) {
        this.grt = grt;
    }

    public Integer getDwt() {
        return dwt;
    }

    public void setDwt(Integer dwt) {
        this.dwt = dwt;
    }

    public Integer getBrt() {
        return brt;
    }

    public void setBrt(Integer brt) {
        this.brt = brt;
    }

    public Integer getNrt() {
        return nrt;
    }

    public void setNrt(Integer nrt) {
        this.nrt = nrt;
    }

    public Integer getLoa() {
        return loa;
    }

    public void setLoa(Integer loa) {
        this.loa = loa;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
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
        if (!(object instanceof BookingOnline)) {
            return false;
        }
        BookingOnline other = (BookingOnline) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.BookingOnline[id=" + id + "]";
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
