/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "receiving_barang_service")
@NamedQueries({
    @NamedQuery(name = "ReceivingBarangService.findAll", query = "SELECT r FROM ReceivingBarangService r"),
    @NamedQuery(name = "ReceivingBarangService.findByJobslip", query = "SELECT r FROM ReceivingBarangService r WHERE r.jobslip = :jobslip"),
    @NamedQuery(name = "ReceivingBarangService.findByNoReg", query = "SELECT r FROM ReceivingBarangService r WHERE r.noReg = :noReg"),
    @NamedQuery(name = "ReceivingBarangService.findByNoPpkb", query = "SELECT r FROM ReceivingBarangService r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "ReceivingBarangService.findByContNo", query = "SELECT r FROM ReceivingBarangService r WHERE r.contNo = :contNo"),
    @NamedQuery(name = "ReceivingBarangService.findByBlNo", query = "SELECT r FROM ReceivingBarangService r WHERE r.blNo = :blNo"),
    @NamedQuery(name = "ReceivingBarangService.findByCommodityCode", query = "SELECT r FROM ReceivingBarangService r WHERE r.commodityCode = :commodityCode"),
    @NamedQuery(name = "ReceivingBarangService.findByUnit", query = "SELECT r FROM ReceivingBarangService r WHERE r.unit = :unit"),
    @NamedQuery(name = "ReceivingBarangService.findByWeight", query = "SELECT r FROM ReceivingBarangService r WHERE r.weight = :weight"),
    @NamedQuery(name = "ReceivingBarangService.findByVolume", query = "SELECT r FROM ReceivingBarangService r WHERE r.volume = :volume"),
    @NamedQuery(name = "ReceivingBarangService.findByValidDate", query = "SELECT r FROM ReceivingBarangService r WHERE r.validDate = :validDate"),
    @NamedQuery(name = "ReceivingBarangService.findByCountBy", query = "SELECT r FROM ReceivingBarangService r WHERE r.countBy = :countBy")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ReceivingBarangService.Native.findReceivingBarangServices", query = "SELECT s.jobslip, s.cont_no, s.bl_no, m.name,s.weight,s.volume,s.valid_date,s.count_by FROM receiving_barang_service s,registration r,m_commodity m where s.no_reg=r.no_reg AND s.commodity_code=m.commodity_code AND s.no_reg=?"),
    @NamedNativeQuery(name = "ReceivingBarangService.Native.findReceivingBarangServiceByStuffing", query = "SELECT s.jobslip, s.cont_no, s.bl_no, m.name,s.weight,s.volume,s.valid_date FROM receiving_barang_service s,registration r,m_commodity m where s.commodity_code=m.commodity_code AND s.no_reg=r.no_reg AND s.jobslip=? AND s.jobslip NOT IN (select jobslip from service_cfs_stuffing)"),
    @NamedNativeQuery(name = "ReceivingBarangService.Native.findReceivingBarangServiceByAutoComplete", query = "SELECT m.jobslip FROM receiving_barang_service m WHERE m.jobslip LIKE ('%'|| ? ||'%') AND m.jobslip NOT IN (select jobslip from service_cfs_stuffing) ORDER BY substr(m.jobslip,7,5) DESC"),
    @NamedNativeQuery(name = "ReceivingBarangService.Native.findReceivingBarangServiceByGenerate", query = "SELECT MAX(substr(jobslip,7,5))FROM receiving_barang_service WHERE substr(jobslip,3,4)= ?"),
    @NamedNativeQuery(name = "ReceivingBarangService.Native.findReceivingBarangServiceByJobSlip", query = "SELECT ds.jobslip, ds.no_ppkb, ds.cont_no, ds.bl_no, c.name, ds.unit, ds.weight, ds.volume, ds.valid_date FROM receiving_barang_service ds, m_commodity c WHERE ds.commodity_code = c.commodity_code AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "ReceivingBarangService.Native.findReceivingBarangServiceByList", query = "SELECT ds.jobslip, ds.no_ppkb, ds.cont_no, ds.bl_no, c.name, ds.unit, ds.weight, ds.volume, ds.valid_date FROM receiving_barang_service ds, m_commodity c WHERE ds.commodity_code = c.commodity_code AND ds.bl_no=? AND ds.jobslip NOT IN (select jobslip from service_cfs_stuffing) ORDER BY substr(ds.jobslip,7,5) DESC"),
    @NamedNativeQuery(name = "ReceivingBarangService.Native.findReceivingBarangServiceByListAll", query = "SELECT ds.jobslip, ds.no_ppkb, ds.cont_no, ds.bl_no, c.name, ds.unit, ds.weight, ds.volume, ds.valid_date FROM receiving_barang_service ds, m_commodity c WHERE ds.commodity_code = c.commodity_code AND ds.jobslip NOT IN (select jobslip from service_cfs_stuffing) ORDER BY substr(ds.jobslip,7,5) DESC"),
    @NamedNativeQuery(name = "ReceivingBarangService.Native.findByListBatalNotaService", query = "select d.jobslip,d.no_reg,d.no_ppkb,d.cont_no from receiving_barang_service d where d.no_ppkb=? AND d.no_reg=?"),
    @NamedNativeQuery(name = "ReceivingBarangService.Native.findByNoregValidasiPrint", query = "select ds.jobslip from receiving_barang_service ds where ds.counter_print >=1 AND ds.no_reg=?")})
public class ReceivingBarangService implements Serializable, EntityAuditable {
    @Column(name = "masa_1")
    private Integer masa1;
    @Column(name = "masa_2")
    private Integer masa2;
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
    @Column(name = "jobslip", nullable = false, length = 30)
    private String jobslip;
    @Column(name = "no_reg", length = 30)
    private String noReg;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "bl_no")
    private String blNo;
    @Column(name = "commodity_code", length = 5)
    private String commodityCode;
    @Column(name = "unit")
    private Integer unit;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "volume")
    private Integer volume;
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @Column(name = "count_by", length = 10)
    private String countBy;
    @Column(name = "counter_print")
    private Integer counterPrint=0;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public ReceivingBarangService() {
    }

    public ReceivingBarangService(String jobslip) {
        this.jobslip = jobslip;
    }

    public String getJobslip() {
        return jobslip;
    }

    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
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

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getCountBy() {
        return countBy;
    }

    public void setCountBy(String countBy) {
        this.countBy = countBy;
    }

    public Integer getMasa1() {
        return masa1;
    }

    public void setMasa1(Integer masa1) {
        this.masa1 = masa1;
    }

    public Integer getMasa2() {
        return masa2;
    }

    public void setMasa2(Integer masa2) {
        this.masa2 = masa2;
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

    public Integer getCounterPrint() {
        return counterPrint;
    }

    public void setCounterPrint(Integer counterPrint) {
        this.counterPrint = counterPrint;
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
        hash += (jobslip != null ? jobslip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceivingBarangService)) {
            return false;
        }
        ReceivingBarangService other = (ReceivingBarangService) object;
        if ((this.jobslip == null && other.jobslip != null) || (this.jobslip != null && !this.jobslip.equals(other.jobslip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ReceivingBarangService[jobslip=" + jobslip + "]";
    }
    
}
