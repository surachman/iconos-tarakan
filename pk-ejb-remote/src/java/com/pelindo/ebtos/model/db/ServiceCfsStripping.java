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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "service_cfs_stripping")
@NamedQueries({
    @NamedQuery(name = "ServiceCfsStripping.findAll", query = "SELECT s FROM ServiceCfsStripping s"),
    @NamedQuery(name = "ServiceCfsStripping.findById", query = "SELECT s FROM ServiceCfsStripping s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceCfsStripping.findByNoPpkb", query = "SELECT s FROM ServiceCfsStripping s WHERE s.noPpkb = :noPpkb"),
    @NamedQuery(name = "ServiceCfsStripping.findByContNo", query = "SELECT s FROM ServiceCfsStripping s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceCfsStripping.findByBlNo", query = "SELECT s FROM ServiceCfsStripping s WHERE s.blNo = :blNo"),
    @NamedQuery(name = "ServiceCfsStripping.findByCommodityCode", query = "SELECT s FROM ServiceCfsStripping s WHERE s.commodityCode = :commodityCode"),
    @NamedQuery(name = "ServiceCfsStripping.findByUnit", query = "SELECT s FROM ServiceCfsStripping s WHERE s.unit = :unit"),
    @NamedQuery(name = "ServiceCfsStripping.findByWeight", query = "SELECT s FROM ServiceCfsStripping s WHERE s.weight = :weight"),
    @NamedQuery(name = "ServiceCfsStripping.findByVolume", query = "SELECT s FROM ServiceCfsStripping s WHERE s.volume = :volume"),
    @NamedQuery(name = "ServiceCfsStripping.findByPlacementDate", query = "SELECT s FROM ServiceCfsStripping s WHERE s.placementDate = :placementDate"),
    @NamedQuery(name = "ServiceCfsStripping.findByDeliveryDate", query = "SELECT s FROM ServiceCfsStripping s WHERE s.deliveryDate = :deliveryDate"),
    @NamedQuery(name = "ServiceCfsStripping.findByIsDelivery", query = "SELECT s FROM ServiceCfsStripping s WHERE s.isDelivery = :isDelivery"),
    @NamedQuery(name = "ServiceCfsStripping.findByJobslip", query = "SELECT s FROM ServiceCfsStripping s WHERE s.jobslip = :jobslip")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceCfsStripping.Native.findAll", query = "SELECT id, no_ppkb, cont_no, bl_no, commodity_code, unit, weight, volume, placement_date FROM service_cfs_stripping"),
    @NamedNativeQuery(name = "ServiceCfsStripping.Native.findForDeliveryGoods", query = "SELECT DISTINCT no_ppkb, cont_no FROM service_cfs_stripping"),
    @NamedNativeQuery(name = "ServiceCfsStripping.Native.findBL", query = "SELECT cfs.id, cfs.no_ppkb, cfs.cont_no, cfs.bl_no, cfs.commodity_code, cfs.unit, cfs.weight, cfs.volume, cfs.placement_date FROM service_cfs_stripping cfs WHERE cfs.cont_no = ? AND cfs.bl_no NOT IN (SELECT bl_no FROM delivery_barang_service WHERE cont_no = cfs.cont_no)"),
    @NamedNativeQuery(name = "ServiceCfsStripping.Native.findDelivery", query = "SELECT id, no_ppkb, cont_no, bl_no, commodity_code, unit, weight, volume, placement_date, jobslip FROM service_cfs_stripping WHERE is_delivery = true"),
    @NamedNativeQuery(name = "ServiceCfsStripping.Native.findId", query = "SELECT id FROM service_cfs_stripping WHERE no_ppkb = ? AND cont_no = ? AND bl_no = ?"),
    @NamedNativeQuery(name = "ServiceCfsStripping.Native.findByPpkbNCont", query = "SELECT scs.bl_no, c.name, scs.unit, scs.weight, scs.volume, scs.placement_date FROM service_cfs_stripping scs, m_commodity c WHERE c.commodity_code = scs.commodity_code AND scs.no_ppkb = ? AND scs.cont_no = ?")
})
public class ServiceCfsStripping implements Serializable, EntityAuditable {
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
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
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
    @Column(name = "delivery_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    @Column(name = "is_delivery")
    private Boolean isDelivery;
    @Column(name = "jobslip", length = 30)
    private String jobslip;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public ServiceCfsStripping() {
    }

    public ServiceCfsStripping(Integer id) {
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

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Boolean getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Boolean isDelivery) {
        this.isDelivery = isDelivery;
    }

    public String getJobslip() {
        return jobslip;
    }

    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
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
        if (!(object instanceof ServiceCfsStripping)) {
            return false;
        }
        ServiceCfsStripping other = (ServiceCfsStripping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceCfsStripping[id=" + id + "]";
    }

}
