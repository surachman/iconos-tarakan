/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
@Table(name = "perhitungan_penumpukan_susulan")
@NamedQueries({
    @NamedQuery(name = "PerhitunganPenumpukanSusulan.findAll", query = "SELECT p FROM PerhitunganPenumpukanSusulan p"),
    @NamedQuery(name = "PerhitunganPenumpukanSusulan.findById", query = "SELECT p FROM PerhitunganPenumpukanSusulan p WHERE p.id = :id"),
    @NamedQuery(name = "PerhitunganPenumpukanSusulan.findByContNo", query = "SELECT p FROM PerhitunganPenumpukanSusulan p WHERE p.contNo = :contNo"),
    @NamedQuery(name = "PerhitunganPenumpukanSusulan.findByBlNo", query = "SELECT p FROM PerhitunganPenumpukanSusulan p WHERE p.blNo = :blNo"),
    @NamedQuery(name = "PerhitunganPenumpukanSusulan.findByCharge", query = "SELECT p FROM PerhitunganPenumpukanSusulan p WHERE p.charge = :charge")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PerhitunganPenumpukanSusulan.Native.findInvoice", query = "SELECT id FROM perhitungan_penumpukan_susulan WHERE cont_no = ? AND no_ppkb = ? AND no_reg = ?"),
    @NamedNativeQuery(name = "PerhitunganPenumpukanSusulan.Native.findInvoiceUC", query = "SELECT id FROM perhitungan_penumpukan_susulan WHERE bl_no = ? AND no_ppkb = ? AND no_reg = ?"),
    @NamedNativeQuery(name = "PerhitunganPenumpukanSusulan.Native.findByReg", query = "SELECT id FROM perhitungan_penumpukan_susulan WHERE no_reg = ?")})
public class PerhitunganPenumpukanSusulan implements Serializable, EntityAuditable {
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "bl_no")
    private String blNo;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "charge_m1", precision = 19, scale = 2)
    private BigDecimal chargeM1;
    @Column(name = "charge_m2", precision = 19, scale = 2)
    private BigDecimal chargeM2;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "curr_code", length = 5)
    private String currCode;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg")
    @ManyToOne
    private Registration registration;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public PerhitunganPenumpukanSusulan() {
    }

    public PerhitunganPenumpukanSusulan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
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

    public BigDecimal getChargeM1() {
        return chargeM1;
    }

    public void setChargeM1(BigDecimal chargeM1) {
        this.chargeM1 = chargeM1;
    }

    public BigDecimal getChargeM2() {
        return chargeM2;
    }

    public void setChargeM2(BigDecimal chargeM2) {
        this.chargeM2 = chargeM2;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
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

    /**
     * @return the mlo
     */
    public MasterCustomer getMlo() {
        return mlo;
    }

    /**
     * @param mlo the mlo to set
     */
    public void setMlo(MasterCustomer mlo) {
        this.mlo = mlo;
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
        if (!(object instanceof PerhitunganPenumpukanSusulan)) {
            return false;
        }
        PerhitunganPenumpukanSusulan other = (PerhitunganPenumpukanSusulan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganPenumpukanSusulan[id=" + id + "]";
    }

}
