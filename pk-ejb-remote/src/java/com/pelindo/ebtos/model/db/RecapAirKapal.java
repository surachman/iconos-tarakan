/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author R Seno Anggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "recap_air_kapal")
@NamedQueries({
    @NamedQuery(name = "RecapAirKapal.findAll", query = "SELECT r FROM RecapAirKapal r"),
    @NamedQuery(name = "RecapAirKapal.findById", query = "SELECT r FROM RecapAirKapal r WHERE r.id = :id"),
    @NamedQuery(name = "RecapAirKapal.deleteByNoPpkb", query = "DELETE FROM RecapAirKapal r WHERE r.serviceVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapAirKapal.findByAmount", query = "SELECT r FROM RecapAirKapal r WHERE r.amount = :amount"),
    @NamedQuery(name = "RecapAirKapal.findByCharge", query = "SELECT r FROM RecapAirKapal r WHERE r.charge = :charge"),
    @NamedQuery(name = "RecapAirKapal.findByTotalCharge", query = "SELECT r FROM RecapAirKapal r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapAirKapal.findByCreatedBy", query = "SELECT r FROM RecapAirKapal r WHERE r.createdBy = :createdBy"),
    @NamedQuery(name = "RecapAirKapal.findByCreatedDate", query = "SELECT r FROM RecapAirKapal r WHERE r.createdDate = :createdDate"),
    @NamedQuery(name = "RecapAirKapal.findByModifiedBy", query = "SELECT r FROM RecapAirKapal r WHERE r.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "RecapAirKapal.findByModifiedDate", query = "SELECT r FROM RecapAirKapal r WHERE r.modifiedDate = :modifiedDate")})
public class RecapAirKapal implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "total_charge", precision = 19, scale = 2)
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
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private ServiceVessel serviceVessel;
    @JoinColumn(name = "curr_code", referencedColumnName = "curr_code")
    @ManyToOne
    private MasterCurrency masterCurrency;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;

    public RecapAirKapal() {
    }

    public RecapAirKapal(Integer id) {
        this.id = id;
    }

    public RecapAirKapal(Integer id, String createdBy, Date createdDate) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
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

    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public void setMasterCurrency(MasterCurrency masterCurrency) {
        this.masterCurrency = masterCurrency;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
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
        if (!(object instanceof RecapAirKapal)) {
            return false;
        }
        RecapAirKapal other = (RecapAirKapal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapAirKapal[id=" + id + "]";
    }

}
