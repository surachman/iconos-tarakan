/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
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
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "perhitungan_penumpukan_transhi")
@NamedQueries({
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findAll", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findById", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p WHERE p.id = :id"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findByActivityCode", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p WHERE p.activityCode = :activityCode"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findByContNo", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p WHERE p.contNo = :contNo"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findByBlNo", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p WHERE p.blNo = :blNo"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findByCharge", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p WHERE p.charge = :charge"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findByNoPpkb", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p WHERE p.noPpkb = :noPpkb"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.deleteByNoPpkb", query = "DELETE FROM PerhitunganPenumpukanTranshipment p WHERE p.noPpkb = :noPpkb"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findByChargeM1", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p WHERE p.chargeM1 = :chargeM1"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findByChargeM2", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p WHERE p.chargeM2 = :chargeM2"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findByTotalCharge", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p WHERE p.totalCharge = :totalCharge"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findByJasaDermaga", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p WHERE p.jasaDermaga = :jasaDermaga"),
    @NamedQuery(name = "PerhitunganPenumpukanTranshipment.findByCurrCode", query = "SELECT p FROM PerhitunganPenumpukanTranshipment p WHERE p.currCode = :currCode")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PerhitunganPenumpukanTranshipment.Native.findByPpkbNCont", query = "SELECT id FROM perhitungan_penumpukan_transhipment WHERE cont_no = ? AND no_ppkb = ?")
})
public class PerhitunganPenumpukanTranshipment implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "activity_code", length = 10)
    private String activityCode;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "bl_no")
    private String blNo;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "charge_m1", precision = 19, scale = 2)
    private BigDecimal chargeM1;
    @Column(name = "charge_m2", precision = 19, scale = 2)
    private BigDecimal chargeM2;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
    @Column(name = "jasa_dermaga", precision = 19, scale = 2)
    private BigDecimal jasaDermaga;
    @Column(name = "curr_code", length = 5)
    private String currCode;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Column(name = "masa_1")
    private Short masa1;
    @Column(name = "masa_2")
    private Short masa2;
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
    @Column(name = "total_hari", nullable = false)
    private int totalHari = 0;
    @Basic(optional = false)
    @Column(name = "operation", nullable = false, length = 15)
    private String operation;

    public PerhitunganPenumpukanTranshipment() {
    }

    public PerhitunganPenumpukanTranshipment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
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

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
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

    public BigDecimal getJasaDermaga() {
        return jasaDermaga;
    }

    public void setJasaDermaga(BigDecimal jasaDermaga) {
        this.jasaDermaga = jasaDermaga;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
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

    public Short getMasa1() {
        return masa1;
    }

    public void setMasa1(Short masa1) {
        this.masa1 = masa1;
    }

    public Short getMasa2() {
        return masa2;
    }

    public void setMasa2(Short masa2) {
        this.masa2 = masa2;
    }

    public int getTotalHari() {
        return totalHari;
    }

    public void setTotalHari(int totalHari) {
        this.totalHari = totalHari;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
        if (!(object instanceof PerhitunganPenumpukanTranshipment)) {
            return false;
        }
        PerhitunganPenumpukanTranshipment other = (PerhitunganPenumpukanTranshipment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganPenumpukanTranshipment[id=" + id + "]";
    }
}
