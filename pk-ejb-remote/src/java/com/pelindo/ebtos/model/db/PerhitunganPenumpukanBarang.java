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
@Table(name = "perhitungan_penumpukan_barang")
@NamedQueries({
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findAll", query = "SELECT p FROM PerhitunganPenumpukanBarang p"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findById", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.id = :id"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByNoPpkb", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.noPpkb = :noPpkb"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByContNo", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.contNo = :contNo"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByBlNo", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.blNo = :blNo"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByCommodityCode", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.commodityCode = :commodityCode"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByJobslip", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.jobslip = :jobslip"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByActivityCode", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.activityCode = :activityCode"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByChargePenumpukan", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.chargePenumpukan = :chargePenumpukan"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByChargeMasa1", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.chargeMasa1 = :chargeMasa1"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByChargeMasa2", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.chargeMasa2 = :chargeMasa2"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByJasaDermaga", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.jasaDermaga = :jasaDermaga"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByTotalCharge", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.totalCharge = :totalCharge"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByCurrCode", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.currCode = :currCode"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByOperation", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.operation = :operation"),
    @NamedQuery(name = "PerhitunganPenumpukanBarang.findByNoReg", query = "SELECT p FROM PerhitunganPenumpukanBarang p WHERE p.noReg = :noReg")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PerhitunganPenumpukanBarang.Native.findPerhitunganPenumpukanBarangByList", query = "SELECT s.jobslip,s.cont_no,s.bl_no,s.no_ppkb,m.name,a.description,s.charge_penumpukan,s.charge_masa_1,s.charge_masa_2,s.jasa_dermaga,s.total_charge,s.operation, c.symbol FROM perhitungan_penumpukan_barang s,m_commodity m,m_activity a, m_currency c where s.commodity_code=m.commodity_code AND s.activity_code=a.activity_code AND s.curr_code = c.curr_code"),
    @NamedNativeQuery(name = "PerhitunganPenumpukanBarang.Native.findPerhitunganPenumpukanBarangByEdit", query = "SELECT s.id, s.cont_no FROM perhitungan_penumpukan_barang s where s.jobslip=? AND s.cont_no=?"),
    @NamedNativeQuery(name = "PerhitunganPenumpukanBarang.Native.findByReg", query = "SELECT id FROM perhitungan_penumpukan_barang WHERE no_reg = ?")})
public class PerhitunganPenumpukanBarang implements Serializable, EntityAuditable {
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "cont_no")
    private String contNo;
    @Column(name = "bl_no")
    private String blNo;
    @Column(name = "commodity_code")
    private String commodityCode;
    @Column(name = "jobslip", length = 30)
    private String jobslip;
    @Column(name = "activity_code")
    private String activityCode;
    @Column(name = "charge_penumpukan")
    private BigDecimal chargePenumpukan;
    @Column(name = "charge_masa_1")
    private BigDecimal chargeMasa1;
    @Column(name = "charge_masa_2")
    private BigDecimal chargeMasa2;
    @Column(name = "jasa_dermaga")
    private BigDecimal jasaDermaga;
    @Column(name = "total_charge")
    private BigDecimal totalCharge;
    @Column(name = "curr_code")
    private String currCode;
    @Column(name = "operation")
    private String operation;
    @Column(name = "no_reg", length = 30)
    private String noReg;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public PerhitunganPenumpukanBarang() {
    }

    public PerhitunganPenumpukanBarang(Integer id) {
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

    public BigDecimal getChargePenumpukan() {
        return chargePenumpukan;
    }

    public void setChargePenumpukan(BigDecimal chargePenumpukan) {
        this.chargePenumpukan = chargePenumpukan;
    }

    public BigDecimal getChargeMasa1() {
        return chargeMasa1;
    }

    public void setChargeMasa1(BigDecimal chargeMasa1) {
        this.chargeMasa1 = chargeMasa1;
    }

    public BigDecimal getChargeMasa2() {
        return chargeMasa2;
    }

    public void setChargeMasa2(BigDecimal chargeMasa2) {
        this.chargeMasa2 = chargeMasa2;
    }

    public BigDecimal getJasaDermaga() {
        return jasaDermaga;
    }

    public void setJasaDermaga(BigDecimal jasaDermaga) {
        this.jasaDermaga = jasaDermaga;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
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

    public String getNoReg() {
        return noReg;
    }

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
        if (!(object instanceof PerhitunganPenumpukanBarang)) {
            return false;
        }
        PerhitunganPenumpukanBarang other = (PerhitunganPenumpukanBarang) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PerhitunganPenumpukanBarang[id=" + id + "]";
    }
}
