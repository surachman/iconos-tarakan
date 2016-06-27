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
@Table(name = "delivery_barang_service")
@NamedQueries({
    @NamedQuery(name = "DeliveryBarangService.findAll", query = "SELECT d FROM DeliveryBarangService d"),
    @NamedQuery(name = "DeliveryBarangService.findByJobslip", query = "SELECT d FROM DeliveryBarangService d WHERE d.jobslip = :jobslip"),
    @NamedQuery(name = "DeliveryBarangService.findByNoReg", query = "SELECT d FROM DeliveryBarangService d WHERE d.noReg = :noReg"),
    @NamedQuery(name = "DeliveryBarangService.findByNoPpkb", query = "SELECT d FROM DeliveryBarangService d WHERE d.noPpkb = :noPpkb"),
    @NamedQuery(name = "DeliveryBarangService.findByContNo", query = "SELECT d FROM DeliveryBarangService d WHERE d.contNo = :contNo"),
    @NamedQuery(name = "DeliveryBarangService.findByBlNo", query = "SELECT d FROM DeliveryBarangService d WHERE d.blNo = :blNo"),
    @NamedQuery(name = "DeliveryBarangService.findByCommodityCode", query = "SELECT d FROM DeliveryBarangService d WHERE d.commodityCode = :commodityCode"),
    @NamedQuery(name = "DeliveryBarangService.findByUnit", query = "SELECT d FROM DeliveryBarangService d WHERE d.unit = :unit"),
    @NamedQuery(name = "DeliveryBarangService.findByWeight", query = "SELECT d FROM DeliveryBarangService d WHERE d.weight = :weight"),
    @NamedQuery(name = "DeliveryBarangService.findByVolume", query = "SELECT d FROM DeliveryBarangService d WHERE d.volume = :volume"),
    @NamedQuery(name = "DeliveryBarangService.findByPlacementDate", query = "SELECT d FROM DeliveryBarangService d WHERE d.placementDate = :placementDate"),
    @NamedQuery(name = "DeliveryBarangService.findByMasa1", query = "SELECT d FROM DeliveryBarangService d WHERE d.masa1 = :masa1"),
    @NamedQuery(name = "DeliveryBarangService.findByMasa2", query = "SELECT d FROM DeliveryBarangService d WHERE d.masa2 = :masa2"),
    @NamedQuery(name = "DeliveryBarangService.findByValidDate", query = "SELECT d FROM DeliveryBarangService d WHERE d.validDate = :validDate"),
    @NamedQuery(name = "DeliveryBarangService.findByCountBy", query = "SELECT d FROM DeliveryBarangService d WHERE d.countBy = :countBy")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "DeliveryBarangService.Native.generateId", query = "SELECT MAX(substring(jobslip,7,5))FROM delivery_barang_service WHERE substring(jobslip,3,4) = ?"),
    @NamedNativeQuery(name = "DeliveryBarangService.Native.findInvoice", query = "SELECT ds.jobslip FROM delivery_service ds WHERE ds.cont_no = ? AND ds.no_ppkb = ? AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "DeliveryBarangService.Native.perhitungan", query = "SELECT ds.jobslip, ds.cont_no, ds.bl_no, c.name, ds.unit, ds.weight, ds.volume, ds.placement_date, ds.masa_1, ds.masa_2, pls.charge_equipmet, pp.charge_masa_1, pp.charge_masa_2, pp.jasa_dermaga, curr.symbol, curr.language, curr.country "
    + "FROM delivery_barang_service ds, perhitungan_lift_barang pls, perhitungan_penumpukan_barang pp, m_commodity c, m_currency curr "
    + "WHERE ds.commodity_code = c.commodity_code AND pls.jobslip = ds.jobslip AND pp.jobslip = ds.jobslip AND pls.no_reg = ds.no_reg AND pp.no_reg = ds.no_reg AND pls.curr_code = curr.curr_code AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "DeliveryBarangService.Native.findByReg", query = "SELECT ds.jobslip, ds.no_ppkb, ds.cont_no, ds.bl_no, c.name, ds.unit, ds.weight, ds.volume, ds.placement_date, ds.valid_date FROM delivery_barang_service ds, m_commodity c WHERE ds.commodity_code = c.commodity_code AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "DeliveryBarangService.Native.findJobslip", query = "SELECT jobslip, no_ppkb, cont_no, bl_no FROM delivery_barang_service WHERE jobslip = ?"),
    @NamedNativeQuery(name = "DeliveryBarangService.Native.findJobslipAutoComplete", query = "SELECT jobslip FROM delivery_barang_service WHERE jobslip LIKE ('%'|| ? ||'%') ORDER BY substring(jobslip,7,5) ASC"),
    @NamedNativeQuery(name = "DeliveryBarangService.Native.findDeliveryBarangServiceByList", query = "SELECT ds.jobslip, ds.no_ppkb, ds.cont_no, ds.bl_no, c.name, ds.unit, ds.weight, ds.volume, ds.valid_date FROM delivery_barang_service ds, m_commodity c WHERE ds.commodity_code = c.commodity_code AND ds.bl_no=? AND ds.jobslip NOT IN (select jobslip from service_cfs_stripping where jobslip IS NOT NULL) ORDER BY substring(ds.jobslip,7,5) DESC"),
    @NamedNativeQuery(name = "DeliveryBarangService.Native.findDeliveryBarangServiceByListAll", query = "SELECT ds.jobslip, ds.no_ppkb, ds.cont_no, ds.bl_no, c.name, ds.unit, ds.weight, ds.volume, ds.valid_date FROM delivery_barang_service ds, m_commodity c WHERE ds.commodity_code = c.commodity_code AND ds.jobslip NOT IN (select jobslip from service_cfs_stripping where jobslip IS NOT NULL) ORDER BY substring(ds.jobslip,7,5) DESC"),
    @NamedNativeQuery(name = "DeliveryBarangService.Native.findByNoregValidasiPrint", query = "select ds.jobslip from delivery_barang_service ds where ds.counter_print >=1 AND ds.no_reg=?")})

    public class DeliveryBarangService implements Serializable, EntityAuditable {

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
    @Column(name = "jobslip", length = 30)
    private String jobslip;
    @Column(name = "no_reg", length = 30)
    private String noReg;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "cont_no")
    private String contNo;
    @Column(name = "bl_no")
    private String blNo;
    @Column(name = "commodity_code")
    private String commodityCode;
    @Column(name = "unit")
    private Integer unit;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "volume")
    private Integer volume;
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @Column(name = "masa_1")
    private Integer masa1;
    @Column(name = "masa_2")
    private Integer masa2;
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @Column(name = "count_by")
    private String countBy;
    @Column(name = "counter_print")
    private Integer counterPrint=0;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public DeliveryBarangService() {
    }

    public DeliveryBarangService(String jobslip) {
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

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
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
        if (!(object instanceof DeliveryBarangService)) {
            return false;
        }
        DeliveryBarangService other = (DeliveryBarangService) object;
        if ((this.jobslip == null && other.jobslip != null) || (this.jobslip != null && !this.jobslip.equals(other.jobslip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.DeliveryBarangService[jobslip=" + jobslip + "]";
    }
}
