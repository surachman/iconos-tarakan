/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterYard;
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
@Table(name = "service_discharge_uc")
@NamedQueries({
    @NamedQuery(name = "ServiceDischargeUc.findAll", query = "SELECT s FROM ServiceDischargeUc s"),
    @NamedQuery(name = "ServiceDischargeUc.findById", query = "SELECT s FROM ServiceDischargeUc s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceDischargeUc.findByNoBl", query = "SELECT s FROM ServiceDischargeUc s WHERE s.noBl = :noBl"),
    @NamedQuery(name = "ServiceDischargeUc.findByCrane", query = "SELECT s FROM ServiceDischargeUc s WHERE s.crane = :crane"),
    @NamedQuery(name = "ServiceDischargeUc.findByWeight", query = "SELECT s FROM ServiceDischargeUc s WHERE s.weight = :weight"),
    @NamedQuery(name = "ServiceDischargeUc.findByTruckLossing", query = "SELECT s FROM ServiceDischargeUc s WHERE s.truckLossing = :truckLossing"),
    @NamedQuery(name = "ServiceDischargeUc.findByPlacementDate", query = "SELECT s FROM ServiceDischargeUc s WHERE s.placementDate = :placementDate")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceDischargeUc.Native.findByPpkbAja", query = "SELECT sdu.id FROM service_discharge_uc sdu WHERE sdu.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceDischargeUc.Native.findByPpkb", query = "SELECT sdu.id, sdu.no_bl, m.name, sdu.unit, sdu.weight, sdu.truck_lossing, sdu.block, sdu.id_equipment FROM service_discharge_uc sdu, m_commodity m WHERE sdu.no_ppkb = ? AND sdu.commodity_code = m.commodity_code AND sdu.position = '02' OR (sdu.position = '03' AND sdu.truck_lossing = TRUE)"),
    @NamedNativeQuery(name = "ServiceDischargeUc.Native.findByPpkbPick", query = "SELECT sdu.id, sdu.no_bl, m.name, sdu.unit, sdu.weight, sdu.truck_lossing, sdu.block, sdu.id_equipment FROM service_discharge_uc sdu, m_commodity m WHERE sdu.no_ppkb = ? AND sdu.commodity_code = m.commodity_code AND sdu.position = '01'"),
    @NamedNativeQuery(name = "ServiceDischargeUc.Native.findByBlNo", query = "SELECT s.id, s.no_ppkb, s.no_bl, c.name, s.weight, s.unit, s.truck_lossing FROM service_discharge_uc s, m_commodity as c WHERE s.commodity_code = c.commodity_code AND s.no_ppkb = ? AND s.no_bl = ? AND s.position = ?")
})
public class ServiceDischargeUc implements Serializable, EntityAuditable {
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
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @Column(name = "unit")
    private Short unit;
    @Column(name = "is_delivery")
    private Boolean isDelivery;
    @JoinColumn(name = "block", referencedColumnName = "block")
    @ManyToOne
    private MasterYard masterYard;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private ServiceVessel serviceVessel;
    @JoinColumn(name = "id_equipment", referencedColumnName = "id")
    @ManyToOne
    private Equipment equipment;

    public ServiceDischargeUc() {
    }

    public ServiceDischargeUc(Integer id) {
        this.id = id;
    }

    public ServiceDischargeUc(Integer id, String noBl, Integer weight) {
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

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public Short getUnit() {
        return unit;
    }

    public void setUnit(Short unit) {
        this.unit = unit;
    }

    public Boolean getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Boolean isDelivery) {
        this.isDelivery = isDelivery;
    }

    public MasterYard getMasterYard() {
        return masterYard;
    }

    public void setMasterYard(MasterYard masterYard) {
        this.masterYard = masterYard;
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
        if (!(object instanceof ServiceDischargeUc)) {
            return false;
        }
        ServiceDischargeUc other = (ServiceDischargeUc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceDischargeUc[id=" + id + "]";
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
