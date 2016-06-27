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
@Table(name = "perhitungan_lift_barang")
@NamedQueries({
    @NamedQuery(name = "PerhitunganLiftBarang.findAll", query = "SELECT p FROM PerhitunganLiftBarang p"),
    @NamedQuery(name = "PerhitunganLiftBarang.findById", query = "SELECT p FROM PerhitunganLiftBarang p WHERE p.id = :id"),
    @NamedQuery(name = "PerhitunganLiftBarang.findByNoPpkb", query = "SELECT p FROM PerhitunganLiftBarang p WHERE p.noPpkb = :noPpkb"),
    @NamedQuery(name = "PerhitunganLiftBarang.findByContNo", query = "SELECT p FROM PerhitunganLiftBarang p WHERE p.contNo = :contNo"),
    @NamedQuery(name = "PerhitunganLiftBarang.findByBlNo", query = "SELECT p FROM PerhitunganLiftBarang p WHERE p.blNo = :blNo"),
    @NamedQuery(name = "PerhitunganLiftBarang.findByCommodityCode", query = "SELECT p FROM PerhitunganLiftBarang p WHERE p.commodityCode = :commodityCode"),
    @NamedQuery(name = "PerhitunganLiftBarang.findByJobslip", query = "SELECT p FROM PerhitunganLiftBarang p WHERE p.jobslip = :jobslip"),
    @NamedQuery(name = "PerhitunganLiftBarang.findByActivityCode", query = "SELECT p FROM PerhitunganLiftBarang p WHERE p.activityCode = :activityCode"),
    @NamedQuery(name = "PerhitunganLiftBarang.findByChargeEquipmet", query = "SELECT p FROM PerhitunganLiftBarang p WHERE p.chargeEquipmet = :chargeEquipmet"),
    @NamedQuery(name = "PerhitunganLiftBarang.findByCurrCode", query = "SELECT p FROM PerhitunganLiftBarang p WHERE p.currCode = :currCode"),
    @NamedQuery(name = "PerhitunganLiftBarang.findByOperation", query = "SELECT p FROM PerhitunganLiftBarang p WHERE p.operation = :operation")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PerhitunganLiftBarang.Native.findPerhitunganLiftBarangByEdit", query = "SELECT s.id,s.jobslip FROM perhitungan_lift_barang s where s.jobslip=? AND s.cont_no=? "),
    @NamedNativeQuery(name = "PerhitunganLiftBarang.Native.findPerhitunganLiftBarangByList", query = "SELECT s.cont_no,s.bl_no,m.name,s.jobslip,a.description,s.operation,s.charge_equipmet,s.id,c.symbol,c.language,c.country FROM perhitungan_lift_barang s,m_commodity m,m_activity a,m_currency c where s.commodity_code=m.commodity_code AND s.curr_code=c.curr_code AND s.activity_code=a.activity_code AND s.no_reg=? ORDER By s.id ASC"),
    @NamedNativeQuery(name = "PerhitunganLiftBarang.Native.findCancelInvoice", query = "SELECT id FROM perhitungan_lift_barang WHERE jobslip = ? AND no_ppkb = ? AND no_reg = ?"),
    @NamedNativeQuery(name = "PerhitunganLiftBarang.Native.findByReg", query = "SELECT id FROM perhitungan_lift_barang WHERE no_reg = ?")})

public class PerhitunganLiftBarang implements Serializable, EntityAuditable {
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
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "bl_no")
    private String blNo;
    @Column(name = "commodity_code", length = 5)
    private String commodityCode;
    @Column(name = "jobslip", length = 30)
    private String jobslip;
    @Column(name = "activity_code", length = 10)
    private String activityCode;
    @Column(name = "charge_equipmet", precision = 19, scale = 2)
    private BigDecimal chargeEquipmet;
    @Column(name = "curr_code", length = 5)
    private String currCode;
    @Column(name = "operation", length = 10)
    private String operation;
    @Column(name = "no_reg", length = 30)
    private String noReg;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public PerhitunganLiftBarang() {
    }

    public PerhitunganLiftBarang(Integer id) {
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

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getJobslip() {
        return jobslip;
    }

    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public BigDecimal getChargeEquipmet() {
        return chargeEquipmet;
    }

    public void setChargeEquipmet(BigDecimal chargeEquipmet) {
        this.chargeEquipmet = chargeEquipmet;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the noReg
     */
    public String getNoReg() {
        return noReg;
    }

    /**
     * @param noReg the noReg to set
     */
    public void setNoReg(String noReg) {
        this.noReg = noReg;
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
        if (!(object instanceof PerhitunganLiftBarang)) {
            return false;
        }
        PerhitunganLiftBarang other = (PerhitunganLiftBarang) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganLiftBarang[id=" + id + "]";
    }
}
