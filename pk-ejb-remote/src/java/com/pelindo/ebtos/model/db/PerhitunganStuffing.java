/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "perhitungan_stuffing")
@NamedQueries({
    @NamedQuery(name = "PerhitunganStuffing.findAll", query = "SELECT p FROM PerhitunganStuffing p"),
    @NamedQuery(name = "PerhitunganStuffing.findByContNoAndNoReg", query = "SELECT p FROM PerhitunganStuffing p WHERE p.stuffingService.registration.noReg = :noReg AND p.stuffingService.contNo = :contNo"),
    @NamedQuery(name = "PerhitunganStuffing.deleteByNoReg", query = "DELETE FROM PerhitunganStuffing p WHERE p.stuffingService.registration.noReg = :noReg")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PerhitunganStuffing.Native.findInvoice", query = "SELECT id FROM perhitungan_stuffing WHERE cont_no = ? AND no_reg = ?"),
    @NamedNativeQuery(name = "PerhitunganStuffing.Native.findByCancelInvoice", query = "SELECT id FROM perhitungan_stuffing WHERE no_reg = ?")})
public class PerhitunganStuffing implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "charge", precision = 19, scale = 2)
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
    @Column(name = "total_charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCharge = BigDecimal.ZERO;
    @JoinColumn(name = "perhitungan_upah_buruh_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganUpahBuruh perhitunganUpahBuruh;
    @JoinColumn(name = "perhitungan_sewa_alat_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganSewaAlat perhitunganSewaAlat;
    @JoinColumn(name = "perhitungan_pass_gate_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganPassGate perhitunganPassGate;
    @JoinColumn(name = "perhitungan_lift_service_id", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
    private PerhitunganLiftService perhitunganLiftService;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;
    @OneToOne(mappedBy = "perhitunganStuffing")
    private StuffingService stuffingService;

    public PerhitunganStuffing() {
    }

    public PerhitunganStuffing(Integer id) {
        this.id = id;
    }

    public PerhitunganStuffing(Integer id, String createdBy, Date createdDate, BigDecimal totalCharge) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.totalCharge = totalCharge;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
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

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public PerhitunganUpahBuruh getPerhitunganUpahBuruh() {
        return perhitunganUpahBuruh;
    }

    public void setPerhitunganUpahBuruh(PerhitunganUpahBuruh perhitunganUpahBuruh) {
        this.perhitunganUpahBuruh = perhitunganUpahBuruh;
    }

    public PerhitunganSewaAlat getPerhitunganSewaAlat() {
        return perhitunganSewaAlat;
    }

    public void setPerhitunganSewaAlat(PerhitunganSewaAlat perhitunganSewaAlat) {
        this.perhitunganSewaAlat = perhitunganSewaAlat;
    }

    public PerhitunganPassGate getPerhitunganPassGate() {
        return perhitunganPassGate;
    }

    public void setPerhitunganPassGate(PerhitunganPassGate perhitunganPassGate) {
        this.perhitunganPassGate = perhitunganPassGate;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }

    public StuffingService getStuffingService() {
        return stuffingService;
    }

    public void setStuffingService(StuffingService stuffingService) {
        this.stuffingService = stuffingService;
    }

    public PerhitunganLiftService getPerhitunganLiftService() {
        return perhitunganLiftService;
    }

    public void setPerhitunganLiftService(PerhitunganLiftService perhitunganLiftService) {
        this.perhitunganLiftService = perhitunganLiftService;
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
        if (!(object instanceof PerhitunganStuffing)) {
            return false;
        }
        PerhitunganStuffing other = (PerhitunganStuffing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganStuffing[id=" + id + "]";
    }

}
