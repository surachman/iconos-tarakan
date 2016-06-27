/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterOperator;
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
@Table(name = "sewa_alat")
@NamedQueries({
    @NamedQuery(name = "SewaAlat.findAll", query = "SELECT s FROM SewaAlat s"),
    @NamedQuery(name = "SewaAlat.findByNoBooking", query = "SELECT s FROM SewaAlat s WHERE s.noBooking = :noBooking"),
    @NamedQuery(name = "SewaAlat.findByNoSpk", query = "SELECT s FROM SewaAlat s WHERE s.noSpk = :noSpk"),
    @NamedQuery(name = "SewaAlat.findByCustName", query = "SELECT s FROM SewaAlat s WHERE s.custName = :custName"),
    @NamedQuery(name = "SewaAlat.findByLocation", query = "SELECT s FROM SewaAlat s WHERE s.location = :location"),
    @NamedQuery(name = "SewaAlat.findByStartTime", query = "SELECT s FROM SewaAlat s WHERE s.startTime = :startTime"),
    @NamedQuery(name = "SewaAlat.findByEndTime", query = "SELECT s FROM SewaAlat s WHERE s.endTime = :endTime"),
    @NamedQuery(name = "SewaAlat.findByTagihan", query = "SELECT s FROM SewaAlat s WHERE s.tagihan = :tagihan")})
public class SewaAlat implements Serializable, EntityAuditable {
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
    @Column(name = "no_booking", nullable = false, length = 10)
    private String noBooking;
    @Basic(optional = false)
    @Column(name = "no_spk", nullable = false, length = 10)
    private String noSpk;
    @Column(name = "cust_name", length = 50)
    private String custName;
    @Column(name = "location", length = 20)
    private String location;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Basic(optional = false)
    @Column(name = "end_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Basic(optional = false)
    @Column(name = "tagihan", nullable = false)
    private BigInteger tagihan;
    @JoinColumn(name = "opt_code", referencedColumnName = "opt_code")
    @ManyToOne
    private MasterOperator masterOperator;

    public SewaAlat() {
    }

    public SewaAlat(String noBooking) {
        this.noBooking = noBooking;
    }

    public SewaAlat(String noBooking, String noSpk, Date endTime, BigInteger tagihan) {
        this.noBooking = noBooking;
        this.noSpk = noSpk;
        this.endTime = endTime;
        this.tagihan = tagihan;
    }

    public String getNoBooking() {
        return noBooking;
    }

    public void setNoBooking(String noBooking) {
        this.noBooking = noBooking;
    }

    public String getNoSpk() {
        return noSpk;
    }

    public void setNoSpk(String noSpk) {
        this.noSpk = noSpk;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigInteger getTagihan() {
        return tagihan;
    }

    public void setTagihan(BigInteger tagihan) {
        this.tagihan = tagihan;
    }

    public MasterOperator getMasterOperator() {
        return masterOperator;
    }

    public void setMasterOperator(MasterOperator masterOperator) {
        this.masterOperator = masterOperator;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noBooking != null ? noBooking.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SewaAlat)) {
            return false;
        }
        SewaAlat other = (SewaAlat) object;
        if ((this.noBooking == null && other.noBooking != null) || (this.noBooking != null && !this.noBooking.equals(other.noBooking))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.SewaAlat[noBooking=" + noBooking + "]";
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
