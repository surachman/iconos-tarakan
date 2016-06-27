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
@Table(name = "service_cfs_stuffing")
@NamedQueries({
    @NamedQuery(name = "ServiceCfsStuffing.findAll", query = "SELECT s FROM ServiceCfsStuffing s"),
    @NamedQuery(name = "ServiceCfsStuffing.findByJobslip", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.jobslip = :jobslip"),
    @NamedQuery(name = "ServiceCfsStuffing.findByNoPpkb", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.noPpkb = :noPpkb"),
    @NamedQuery(name = "ServiceCfsStuffing.findByContNo", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceCfsStuffing.findByBlNo", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.blNo = :blNo"),
    @NamedQuery(name = "ServiceCfsStuffing.findByCommodityCode", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.commodityCode = :commodityCode"),
    @NamedQuery(name = "ServiceCfsStuffing.findByUnit", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.unit = :unit"),
    @NamedQuery(name = "ServiceCfsStuffing.findByWeight", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.weight = :weight"),
    @NamedQuery(name = "ServiceCfsStuffing.findByVolume", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.volume = :volume"),
    @NamedQuery(name = "ServiceCfsStuffing.findByPlacementDate", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.placementDate = :placementDate"),
    @NamedQuery(name = "ServiceCfsStuffing.findByStuffingDate", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.stuffingDate = :stuffingDate"),
    @NamedQuery(name = "ServiceCfsStuffing.findByMasa1", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.masa1 = :masa1"),
    @NamedQuery(name = "ServiceCfsStuffing.findByMasa2", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.masa2 = :masa2"),
    @NamedQuery(name = "ServiceCfsStuffing.findByIsStuffing", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.isStuffing = :isStuffing"),
    @NamedQuery(name = "ServiceCfsStuffing.findByCountBy", query = "SELECT s FROM ServiceCfsStuffing s WHERE s.countBy = :countBy")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceCfsStuffing.Native.findAll", query = "SELECT jobslip, no_ppkb, cont_no, bl_no, commodity_code, unit, weight, volume, placement_date FROM service_cfs_stuffing"),
    @NamedNativeQuery(name = "ServiceCfsStuffing.Native.findServiceCfsStuffingByList", query = "SELECT s.jobslip,m.name,s.cont_no,s.bl_no,s.unit,s.weight,s.volume,s.placement_date,s.count_by,s.no_ppkb,change(s.is_stuffing) FROM service_cfs_stuffing s,m_commodity m where s.commodity_code=m.commodity_code"),
    @NamedNativeQuery(name = "ServiceCfsStuffing.Native.findServiceCfsStuffingByPenumpukan", query = "SELECT s.jobslip, s.cont_no, s.bl_no, s.weight,s.volume FROM service_cfs_stuffing s where s.jobslip=? AND s.stuffing_date IS NULL"),
    @NamedNativeQuery(name = "ServiceCfsStuffing.Native.findServiceCfsStuffingByAutoComplete", query = "SELECT m.jobslip FROM service_cfs_stuffing m WHERE m.jobslip LIKE ('%'|| ? ||'%') AND m.stuffing_date IS NULL ORDER BY substring(m.jobslip,7,5) DESC"),
    @NamedNativeQuery(name = "ServiceCfsStuffing.Native.findServiceCfsStuffingAll", query = "SELECT s.jobslip,m.name,s.cont_no,s.bl_no,s.unit,s.weight,s.volume,s.placement_date,s.count_by,s.no_ppkb,change(s.is_stuffing) FROM service_cfs_stuffing s,m_commodity m where s.commodity_code=m.commodity_code AND s.cont_no NOT IN (select cont_no from perhitungan_penumpukan_barang where no_ppkb=s.no_ppkb)"),
    @NamedNativeQuery(name = "ServiceCfsStuffing.Native.findServiceCfsStuffingAllAndBl", query = "SELECT s.jobslip,m.name,s.cont_no,s.bl_no,s.unit,s.weight,s.volume,s.placement_date,s.count_by,s.no_ppkb,change(s.is_stuffing) FROM service_cfs_stuffing s,m_commodity m where s.commodity_code=m.commodity_code AND s.bl_no=? AND s.cont_no NOT IN (select cont_no from perhitungan_penumpukan_barang where no_ppkb=s.no_ppkb)"),
    @NamedNativeQuery(name = "ServiceCfsStuffing.Native.findByPpkbNCont", query = "SELECT scs.bl_no, c.name, scs.unit, scs.weight, scs.volume, scs.placement_date FROM service_cfs_stuffing scs, m_commodity c WHERE c.commodity_code = scs.commodity_code AND scs.no_ppkb = ? AND scs.cont_no = ?")})
public class ServiceCfsStuffing implements Serializable, EntityAuditable {
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
    private Integer jobslip;
    @Column(name = "no_ppkb" ,length = 30)
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
    @Column(name = "stuffing_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stuffingDate;
    @Column(name = "masa_1")
    private Integer masa1;
    @Column(name = "masa_2")
    private Integer masa2;
    @Column(name = "is_stuffing")
    private String isStuffing;
    @Column(name = "count_by")
    private String countBy;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public ServiceCfsStuffing() {
    }

    public ServiceCfsStuffing(Integer jobslip) {
        this.jobslip = jobslip;
    }

    public Integer getJobslip() {
        return jobslip;
    }

    public void setJobslip(Integer jobslip) {
        this.jobslip = jobslip;
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

    public Date getStuffingDate() {
        return stuffingDate;
    }

    public void setStuffingDate(Date stuffingDate) {
        this.stuffingDate = stuffingDate;
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

    public String getIsStuffing() {
        return isStuffing;
    }

    public void setIsStuffing(String isStuffing) {
        this.isStuffing = isStuffing;
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
        if (!(object instanceof ServiceCfsStuffing)) {
            return false;
        }
        ServiceCfsStuffing other = (ServiceCfsStuffing) object;
        if ((this.jobslip == null && other.jobslip != null) || (this.jobslip != null && !this.jobslip.equals(other.jobslip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceCfsStuffing[jobslip=" + jobslip + "]";
    }
}
