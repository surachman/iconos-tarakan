/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import java.io.Serializable;
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
@Table(name = "service_load_uc")
@NamedQueries({
    @NamedQuery(name = "ServiceLoadUc.findAll", query = "SELECT s FROM ServiceLoadUc s"),
    @NamedQuery(name = "ServiceLoadUc.findById", query = "SELECT s FROM ServiceLoadUc s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceLoadUc.findByBlock", query = "SELECT s FROM ServiceLoadUc s WHERE s.block = :block"),
    @NamedQuery(name = "ServiceLoadUc.findByNoBl", query = "SELECT s FROM ServiceLoadUc s WHERE s.noBl = :noBl"),
    @NamedQuery(name = "ServiceLoadUc.findByCrane", query = "SELECT s FROM ServiceLoadUc s WHERE s.crane = :crane"),
    @NamedQuery(name = "ServiceLoadUc.findByWeight", query = "SELECT s FROM ServiceLoadUc s WHERE s.weight = :weight"),
    @NamedQuery(name = "ServiceLoadUc.findByTruckLossing", query = "SELECT s FROM ServiceLoadUc s WHERE s.truckLossing = :truckLossing")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceLoadUc.Native.findIdByPPKBnBLno", query = "SELECT id FROM service_load_uc WHERE no_ppkb = ? AND no_bl = ?"),
    @NamedNativeQuery(name = "ServiceLoadUc.Native.findByPpkb", query = "SELECT slu.id, slu.no_bl, m.name, slu.unit, slu.weight, slu.truck_lossing, slu.block, slu.id_equipment FROM service_load_uc slu, m_commodity m WHERE slu.no_ppkb = ? AND slu.commodity_code = m.commodity_code AND slu.position = '01'"),
    @NamedNativeQuery(name = "ServiceLoadUc.Native.findByPpkbPick", query = "SELECT slu.id, slu.no_bl, m.name, slu.unit, slu.weight, slu.truck_lossing, slu.block, slu.id_equipment FROM service_load_uc slu, m_commodity m WHERE slu.no_ppkb = ? AND slu.commodity_code = m.commodity_code AND (slu.position = '02' OR slu.position = '03')"),
    @NamedNativeQuery(name = "ServiceLoadUc.Native.findByBlNo", query = "SELECT s.id, s.no_ppkb, s.no_bl, c.name, s.weight, s.unit, s.truck_lossing FROM service_load_uc s, m_commodity as c WHERE s.commodity_code = c.commodity_code AND s.no_ppkb = ? AND s.no_bl = ? AND s.position = ?")
})
public class ServiceLoadUc implements Serializable, EntityAuditable {
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
    @Column(name = "block", length = 5)
    private String block;
    @Basic(optional = false)
    @Column(name = "no_bl", nullable = false)
    private String noBl;
    @Column(name = "crane", length = 2)
    private String crane;
    @Basic(optional = false)
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "truck_lossing")
    private Boolean truckLossing;
    @Column(name = "position", length = 2)
    private String position;
    @Column(name = "unit")
    private Short unit;
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private ServiceVessel serviceVessel;
    @JoinColumn(name = "id_equipment", referencedColumnName = "id")
    @ManyToOne
    private Equipment equipment;

    public ServiceLoadUc() {
    }

    public ServiceLoadUc(Integer id) {
        this.id = id;
    }

    public ServiceLoadUc(Integer id, String noBl, Integer weight) {
        this.id = id;
        this.noBl = noBl;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getNoBl() {
        return noBl;
    }

    public void setNoBl(String noBl) {
        this.noBl = noBl;
    }

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getTruckLossing() {
        return truckLossing;
    }

    public void setTruckLossing(Boolean truckLossing) {
        this.truckLossing = truckLossing;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Short getUnit() {
        return unit;
    }

    public void setUnit(Short unit) {
        this.unit = unit;
    }

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
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
        if (!(object instanceof ServiceLoadUc)) {
            return false;
        }
        ServiceLoadUc other = (ServiceLoadUc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceLoadUc[id=" + id + "]";
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

}
