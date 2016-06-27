/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.AngsurService;
import com.pelindo.ebtos.model.db.BaplieDischarge;
import com.pelindo.ebtos.model.db.ChangeStatusService;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.DeliveryUcService;
import com.pelindo.ebtos.model.db.PenumpukanSusulanService;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningTransDischarge;
import com.pelindo.ebtos.model.db.PlanningTransLoad;
import com.pelindo.ebtos.model.db.PlanningUcLoad;
import com.pelindo.ebtos.model.db.PlanningUcReceiving;
import com.pelindo.ebtos.model.db.PlanningUcShifting;
import com.pelindo.ebtos.model.db.PlanningUcTrans;
import com.pelindo.ebtos.model.db.PlanningUncontainerized;
import com.pelindo.ebtos.model.db.PlanningucShiftingPlacement;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ReceivingUc;
import com.pelindo.ebtos.model.db.ReeferDischargeService;
import com.pelindo.ebtos.model.db.ReeferLoadService;
import com.pelindo.ebtos.model.db.RekapPenumpukanUc;
import com.pelindo.ebtos.model.db.ServiceCancelLoading;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceDelivery;
import com.pelindo.ebtos.model.db.ServiceDeliveryUc;
import com.pelindo.ebtos.model.db.ServiceDischargeUc;
import com.pelindo.ebtos.model.db.ServiceLoadUc;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.ServiceReceivingBarang;
import com.pelindo.ebtos.model.db.ServiceReceivingUc;
import com.pelindo.ebtos.model.db.ServiceShifting;
import com.pelindo.ebtos.model.db.ServiceStripping;
import com.pelindo.ebtos.model.db.ServiceStuffing;
import com.pelindo.ebtos.model.db.ServiceTransLoad;
import com.pelindo.ebtos.model.db.ServiceUbahStatus;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_commodity")
@NamedQueries({
    @NamedQuery(name = "MasterCommodity.findAll", query = "SELECT m FROM MasterCommodity m"),
    @NamedQuery(name = "MasterCommodity.findByCommodityCode", query = "SELECT m FROM MasterCommodity m WHERE m.commodityCode = :commodityCode"),
    @NamedQuery(name = "MasterCommodity.findByName", query = "SELECT m FROM MasterCommodity m WHERE m.name = :name")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterCommodity.Native.findAll", query = "SELECT m.commodity_code, m.name FROM m_commodity m"),
    @NamedNativeQuery(name ="MasterCommodity.Native.findMasterCommoditys",query="SELECT m.commodity_code,m.name,mm.name FROM m_commodity m,m_commodity_type mm where m.commodity_type_code=mm.commodity_type_code"),
    @NamedNativeQuery(name ="MasterCommodity.Native.findMasterCommodityByCode",query="SELECT m.commodity_code, m.commodity_type_code FROM m_commodity m,m_commodity_type my where m.commodity_type_code = my.commodity_type_code AND m.commodity_type_code = ?"),
    @NamedNativeQuery(name="MasterCommodity.Native.findMasterCommodityByGenerate",query="SELECT MAX(substring(commodity_code,1,3))FROM m_commodity"),
    @NamedNativeQuery(name="MasterCommodity.Native.findMasterCommodityForIdentify",query="SELECT c.commodity_code, c.name FROM m_commodity c WHERE c.commodity_code = ?")
    })
public class MasterCommodity implements Serializable, EntityAuditable {
    @JoinColumn(name = "dangerous_class", referencedColumnName = "dangerous_class")
    @ManyToOne
    private MasterDangerousClass masterDangerousClass;
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
    @Column(name = "commodity_code", nullable = false, length = 5)
    private String commodityCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceCancelLoading> serviceCancelLoadingList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningUcReceiving> planningUcReceivingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterCommodity")
    private List<ServiceShifting> serviceShiftingList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ReceivingService> receivingServiceList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ReeferLoadService> reeferLoadServiceList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceStripping> serviceStrippingList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningucShiftingPlacement> planningucShiftingPlacementList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceTransLoad> serviceTransLoadList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningUcTrans> planningUcTransList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<RekapPenumpukanUc> rekapPenumpukanUcList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceDischargeUc> serviceDischargeUcList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterCommodity")
    private List<ServiceDelivery> serviceDeliveryList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceReceiving> serviceReceivingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterCommodity")
    private List<ServiceContDischarge> serviceContDischargeList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceDeliveryUc> serviceDeliveryUcList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<DeliveryUcService> deliveryUcServiceList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningUncontainerized> planningUncontainerizedList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<AngsurService> angsurServiceList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceStuffing> serviceStuffingList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningContLoad> planningContLoadList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningTransLoad> planningTransLoadList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<DeliveryService> deliveryServiceList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<BaplieDischarge> baplieDischargeList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceReceivingUc> serviceReceivingUcList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceLoadUc> serviceLoadUcList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningShiftDischarge> planningShiftDischargeList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningUcShifting> planningUcShiftingList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningContDischarge> planningContDischargeList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ChangeStatusService> changeStatusServiceList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ReceivingUc> receivingUcList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningUcLoad> planningUcLoadList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PenumpukanSusulanService> penumpukanSusulanServiceList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceReceivingBarang> serviceReceivingBarangList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PluggingReeferService> pluggingReeferServiceList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningContReceiving> planningContReceivingList;
    @JoinColumn(name = "commodity_type_code", referencedColumnName = "commodity_type_code")
    @ManyToOne
    private MasterCommodityType masterCommodityType;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ReeferDischargeService> reeferDischargeServiceList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceUbahStatus> serviceUbahStatusList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<ServiceContLoad> serviceContLoadList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningTransDischarge> planningTransDischargeList;
    @OneToMany(mappedBy = "masterCommodity")
    private List<PlanningReceivingAllocation> planningReceivingAllocationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterCommodity")
    private List<ServiceContTranshipment> serviceContTranshipmentList;

    public MasterCommodity() {
    }

    public MasterCommodity(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public MasterCommodity(String commodityCode, String name) {
        this.commodityCode = commodityCode;
        this.name = name;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceCancelLoading> getServiceCancelLoadingList() {
        return serviceCancelLoadingList;
    }

    public void setServiceCancelLoadingList(List<ServiceCancelLoading> serviceCancelLoadingList) {
        this.serviceCancelLoadingList = serviceCancelLoadingList;
    }

    public List<PlanningUcReceiving> getPlanningUcReceivingList() {
        return planningUcReceivingList;
    }

    public void setPlanningUcReceivingList(List<PlanningUcReceiving> planningUcReceivingList) {
        this.planningUcReceivingList = planningUcReceivingList;
    }

    public List<ServiceShifting> getServiceShiftingList() {
        return serviceShiftingList;
    }

    public void setServiceShiftingList(List<ServiceShifting> serviceShiftingList) {
        this.serviceShiftingList = serviceShiftingList;
    }

    public List<ReceivingService> getReceivingServiceList() {
        return receivingServiceList;
    }

    public void setReceivingServiceList(List<ReceivingService> receivingServiceList) {
        this.receivingServiceList = receivingServiceList;
    }

    public List<ReeferLoadService> getReeferLoadServiceList() {
        return reeferLoadServiceList;
    }

    public void setReeferLoadServiceList(List<ReeferLoadService> reeferLoadServiceList) {
        this.reeferLoadServiceList = reeferLoadServiceList;
    }

    public List<ServiceStripping> getServiceStrippingList() {
        return serviceStrippingList;
    }

    public void setServiceStrippingList(List<ServiceStripping> serviceStrippingList) {
        this.serviceStrippingList = serviceStrippingList;
    }

    public List<PlanningucShiftingPlacement> getPlanningucShiftingPlacementList() {
        return planningucShiftingPlacementList;
    }

    public void setPlanningucShiftingPlacementList(List<PlanningucShiftingPlacement> planningucShiftingPlacementList) {
        this.planningucShiftingPlacementList = planningucShiftingPlacementList;
    }

    public List<ServiceTransLoad> getServiceTransLoadList() {
        return serviceTransLoadList;
    }

    public void setServiceTransLoadList(List<ServiceTransLoad> serviceTransLoadList) {
        this.serviceTransLoadList = serviceTransLoadList;
    }

    public List<PlanningUcTrans> getPlanningUcTransList() {
        return planningUcTransList;
    }

    public void setPlanningUcTransList(List<PlanningUcTrans> planningUcTransList) {
        this.planningUcTransList = planningUcTransList;
    }

    public List<RekapPenumpukanUc> getRekapPenumpukanUcList() {
        return rekapPenumpukanUcList;
    }

    public void setRekapPenumpukanUcList(List<RekapPenumpukanUc> rekapPenumpukanUcList) {
        this.rekapPenumpukanUcList = rekapPenumpukanUcList;
    }

    public List<ServiceDischargeUc> getServiceDischargeUcList() {
        return serviceDischargeUcList;
    }

    public void setServiceDischargeUcList(List<ServiceDischargeUc> serviceDischargeUcList) {
        this.serviceDischargeUcList = serviceDischargeUcList;
    }

    public List<ServiceDelivery> getServiceDeliveryList() {
        return serviceDeliveryList;
    }

    public void setServiceDeliveryList(List<ServiceDelivery> serviceDeliveryList) {
        this.serviceDeliveryList = serviceDeliveryList;
    }

    public List<ServiceReceiving> getServiceReceivingList() {
        return serviceReceivingList;
    }

    public void setServiceReceivingList(List<ServiceReceiving> serviceReceivingList) {
        this.serviceReceivingList = serviceReceivingList;
    }

    public List<ServiceContDischarge> getServiceContDischargeList() {
        return serviceContDischargeList;
    }

    public void setServiceContDischargeList(List<ServiceContDischarge> serviceContDischargeList) {
        this.serviceContDischargeList = serviceContDischargeList;
    }

    public List<ServiceDeliveryUc> getServiceDeliveryUcList() {
        return serviceDeliveryUcList;
    }

    public void setServiceDeliveryUcList(List<ServiceDeliveryUc> serviceDeliveryUcList) {
        this.serviceDeliveryUcList = serviceDeliveryUcList;
    }

    public List<DeliveryUcService> getDeliveryUcServiceList() {
        return deliveryUcServiceList;
    }

    public void setDeliveryUcServiceList(List<DeliveryUcService> deliveryUcServiceList) {
        this.deliveryUcServiceList = deliveryUcServiceList;
    }

    public List<PlanningUncontainerized> getPlanningUncontainerizedList() {
        return planningUncontainerizedList;
    }

    public void setPlanningUncontainerizedList(List<PlanningUncontainerized> planningUncontainerizedList) {
        this.planningUncontainerizedList = planningUncontainerizedList;
    }

    public List<AngsurService> getAngsurServiceList() {
        return angsurServiceList;
    }

    public void setAngsurServiceList(List<AngsurService> angsurServiceList) {
        this.angsurServiceList = angsurServiceList;
    }

    public List<ServiceStuffing> getServiceStuffingList() {
        return serviceStuffingList;
    }

    public void setServiceStuffingList(List<ServiceStuffing> serviceStuffingList) {
        this.serviceStuffingList = serviceStuffingList;
    }

    public List<PlanningContLoad> getPlanningContLoadList() {
        return planningContLoadList;
    }

    public void setPlanningContLoadList(List<PlanningContLoad> planningContLoadList) {
        this.planningContLoadList = planningContLoadList;
    }

    public List<PlanningTransLoad> getPlanningTransLoadList() {
        return planningTransLoadList;
    }

    public void setPlanningTransLoadList(List<PlanningTransLoad> planningTransLoadList) {
        this.planningTransLoadList = planningTransLoadList;
    }

    public List<DeliveryService> getDeliveryServiceList() {
        return deliveryServiceList;
    }

    public void setDeliveryServiceList(List<DeliveryService> deliveryServiceList) {
        this.deliveryServiceList = deliveryServiceList;
    }

    public List<BaplieDischarge> getBaplieDischargeList() {
        return baplieDischargeList;
    }

    public void setBaplieDischargeList(List<BaplieDischarge> baplieDischargeList) {
        this.baplieDischargeList = baplieDischargeList;
    }

    public List<ServiceReceivingUc> getServiceReceivingUcList() {
        return serviceReceivingUcList;
    }

    public void setServiceReceivingUcList(List<ServiceReceivingUc> serviceReceivingUcList) {
        this.serviceReceivingUcList = serviceReceivingUcList;
    }

    public List<ServiceLoadUc> getServiceLoadUcList() {
        return serviceLoadUcList;
    }

    public void setServiceLoadUcList(List<ServiceLoadUc> serviceLoadUcList) {
        this.serviceLoadUcList = serviceLoadUcList;
    }

    public List<PlanningShiftDischarge> getPlanningShiftDischargeList() {
        return planningShiftDischargeList;
    }

    public void setPlanningShiftDischargeList(List<PlanningShiftDischarge> planningShiftDischargeList) {
        this.planningShiftDischargeList = planningShiftDischargeList;
    }

    public List<PlanningUcShifting> getPlanningUcShiftingList() {
        return planningUcShiftingList;
    }

    public void setPlanningUcShiftingList(List<PlanningUcShifting> planningUcShiftingList) {
        this.planningUcShiftingList = planningUcShiftingList;
    }

    public List<PlanningContDischarge> getPlanningContDischargeList() {
        return planningContDischargeList;
    }

    public void setPlanningContDischargeList(List<PlanningContDischarge> planningContDischargeList) {
        this.planningContDischargeList = planningContDischargeList;
    }

    public List<ChangeStatusService> getChangeStatusServiceList() {
        return changeStatusServiceList;
    }

    public void setChangeStatusServiceList(List<ChangeStatusService> changeStatusServiceList) {
        this.changeStatusServiceList = changeStatusServiceList;
    }

    public List<ReceivingUc> getReceivingUcList() {
        return receivingUcList;
    }

    public void setReceivingUcList(List<ReceivingUc> receivingUcList) {
        this.receivingUcList = receivingUcList;
    }

    public List<PlanningUcLoad> getPlanningUcLoadList() {
        return planningUcLoadList;
    }

    public void setPlanningUcLoadList(List<PlanningUcLoad> planningUcLoadList) {
        this.planningUcLoadList = planningUcLoadList;
    }

    public List<PenumpukanSusulanService> getPenumpukanSusulanServiceList() {
        return penumpukanSusulanServiceList;
    }

    public void setPenumpukanSusulanServiceList(List<PenumpukanSusulanService> penumpukanSusulanServiceList) {
        this.penumpukanSusulanServiceList = penumpukanSusulanServiceList;
    }

    public List<ServiceReceivingBarang> getServiceReceivingBarangList() {
        return serviceReceivingBarangList;
    }

    public void setServiceReceivingBarangList(List<ServiceReceivingBarang> serviceReceivingBarangList) {
        this.serviceReceivingBarangList = serviceReceivingBarangList;
    }

    public List<PluggingReeferService> getPluggingReeferServiceList() {
        return pluggingReeferServiceList;
    }

    public void setPluggingReeferServiceList(List<PluggingReeferService> pluggingReeferServiceList) {
        this.pluggingReeferServiceList = pluggingReeferServiceList;
    }

    public List<PlanningContReceiving> getPlanningContReceivingList() {
        return planningContReceivingList;
    }

    public void setPlanningContReceivingList(List<PlanningContReceiving> planningContReceivingList) {
        this.planningContReceivingList = planningContReceivingList;
    }

    public MasterCommodityType getMasterCommodityType() {
        return masterCommodityType;
    }

    public void setMasterCommodityType(MasterCommodityType masterCommodityType) {
        this.masterCommodityType = masterCommodityType;
    }

    public List<ReeferDischargeService> getReeferDischargeServiceList() {
        return reeferDischargeServiceList;
    }

    public void setReeferDischargeServiceList(List<ReeferDischargeService> reeferDischargeServiceList) {
        this.reeferDischargeServiceList = reeferDischargeServiceList;
    }

    public List<ServiceUbahStatus> getServiceUbahStatusList() {
        return serviceUbahStatusList;
    }

    public void setServiceUbahStatusList(List<ServiceUbahStatus> serviceUbahStatusList) {
        this.serviceUbahStatusList = serviceUbahStatusList;
    }

    public List<ServiceContLoad> getServiceContLoadList() {
        return serviceContLoadList;
    }

    public void setServiceContLoadList(List<ServiceContLoad> serviceContLoadList) {
        this.serviceContLoadList = serviceContLoadList;
    }

    public List<PlanningTransDischarge> getPlanningTransDischargeList() {
        return planningTransDischargeList;
    }

    public void setPlanningTransDischargeList(List<PlanningTransDischarge> planningTransDischargeList) {
        this.planningTransDischargeList = planningTransDischargeList;
    }

    public List<PlanningReceivingAllocation> getPlanningReceivingAllocationList() {
        return planningReceivingAllocationList;
    }

    public void setPlanningReceivingAllocationList(List<PlanningReceivingAllocation> planningReceivingAllocationList) {
        this.planningReceivingAllocationList = planningReceivingAllocationList;
    }

    public List<ServiceContTranshipment> getServiceContTranshipmentList() {
        return serviceContTranshipmentList;
    }

    public void setServiceContTranshipmentList(List<ServiceContTranshipment> serviceContTranshipmentList) {
        this.serviceContTranshipmentList = serviceContTranshipmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commodityCode != null ? commodityCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterCommodity)) {
            return false;
        }
        MasterCommodity other = (MasterCommodity) object;
        if ((this.commodityCode == null && other.commodityCode != null) || (this.commodityCode != null && !this.commodityCode.equals(other.commodityCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterCommodity[commodityCode=" + commodityCode + "]";
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

    public MasterDangerousClass getMasterDangerousClass() {
        return masterDangerousClass;
    }

    public void setMasterDangerousClass(MasterDangerousClass masterDangerousClass) {
        this.masterDangerousClass = masterDangerousClass;
    }

}
