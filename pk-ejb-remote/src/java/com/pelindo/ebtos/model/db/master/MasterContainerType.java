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
import com.pelindo.ebtos.model.db.PenumpukanSusulanService;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningTransDischarge;
import com.pelindo.ebtos.model.db.PlanningTransLoad;
import com.pelindo.ebtos.model.db.PluggingReeferService;
import com.pelindo.ebtos.model.db.RecapPenumpukan;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ReeferDischargeService;
import com.pelindo.ebtos.model.db.ReeferLoadService;
import com.pelindo.ebtos.model.db.ReeferProductivity;
import com.pelindo.ebtos.model.db.ReeferRecap;
import com.pelindo.ebtos.model.db.ServiceCancelLoading;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceDelivery;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.ServiceReefer;
import com.pelindo.ebtos.model.db.ServiceShifting;
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
@Table(name = "m_container_type")
@NamedQueries({
    @NamedQuery(name = "MasterContainerType.findAll", query = "SELECT m FROM MasterContainerType m"),
    @NamedQuery(name = "MasterContainerType.findByContType", query = "SELECT m FROM MasterContainerType m WHERE m.contType = :contType"),
    @NamedQuery(name = "MasterContainerType.findByName", query = "SELECT m FROM MasterContainerType m WHERE m.name = :name"),
    @NamedQuery(name = "MasterContainerType.findByDescription", query = "SELECT m FROM MasterContainerType m WHERE m.description = :description"),
    @NamedQuery(name = "MasterContainerType.findByIsoCode", query = "SELECT m FROM MasterContainerType m WHERE m.isoCode = :isoCode OR m.newIsoCode = :isoCode")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterContainerType.Native.findAll", query = "SELECT " +
                                                                                    "m.cont_type, m.name, m.description, mp.name, m.type " +
                                                                            "FROM " +
                                                                                    "m_container_type m " +
                                                                                            "JOIN m_container_type_in_general mctig ON (m.type_in_general=mctig.id) " +
                                                                                                    "JOIN m_pattern mp ON (mctig.pattern=mp.name) " +
                                                                            "ORDER BY m.modified_date DESC"),
    @NamedNativeQuery(name = "MasterContainerType.Native.findAllByISO", query = "SELECT " +
                                                                                        "mct.cont_type, mct.name, mct.description, mct.iso_code, mct.new_iso_code, mct.dimension, mct.feet_mark, mp.name " +
                                                                                "FROM " +
                                                                                        "m_container_type mct " +
                                                                                                "LEFT JOIN m_container_type_in_general mctig ON (mct.type_in_general=mctig.id) " +
                                                                                                        "LEFT JOIN m_pattern mp ON (mctig.pattern=mp.name) " +
                                                                                "WHERE mct.iso_code=?"),
    @NamedNativeQuery(name = "MasterContainerType.Native.findReefer", query = "SELECT cont_type, name "
                                                                            + "FROM m_container_type "
                                                                            + "WHERE type_in_general = 'RFR'")
    })
public class MasterContainerType implements Serializable, EntityAuditable {
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
    @Column(name = "cont_type", nullable = false)
    private Integer contType;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Column(name = "description", length = 256)
    private String description;
    @Column(name = "iso_code")
    private String isoCode;
    @Column(name = "new_iso_code")
    private String newIsoCode;
    @Column(name = "dimension")
    private String dimension;
    @Column(name = "feet_mark")
    private Short feetMark;
    @Column(name = "type")
    private String type;
    @JoinColumn(name = "type_in_general", referencedColumnName = "id")
    @ManyToOne
    private MasterContainerTypeInGeneral masterContainerTypeInGeneral;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ServiceCancelLoading> serviceCancelLoadingList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ReeferRecap> reeferRecapList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterContainerType")
    private List<ServiceShifting> serviceShiftingList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ReceivingService> receivingServiceList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ReeferLoadService> reeferLoadServiceList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ServiceTransLoad> serviceTransLoadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterContainerType")
    private List<ServiceDelivery> serviceDeliveryList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ServiceReceiving> serviceReceivingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterContainerType")
    private List<ServiceContDischarge> serviceContDischargeList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<AngsurService> angsurServiceList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<PlanningContLoad> planningContLoadList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<PlanningTransLoad> planningTransLoadList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<DeliveryService> deliveryServiceList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ServiceReefer> serviceReeferList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<BaplieDischarge> baplieDischargeList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<PlanningShiftDischarge> planningShiftDischargeList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<PlanningContDischarge> planningContDischargeList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ChangeStatusService> changeStatusServiceList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<MasterYardConstraintType> masterYardConstraintTypeList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<PenumpukanSusulanService> penumpukanSusulanServiceList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<PluggingReeferService> pluggingReeferServiceList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<PlanningContReceiving> planningContReceivingList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ReeferDischargeService> reeferDischargeServiceList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ServiceUbahStatus> serviceUbahStatusList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<PlanningReceivingAllocation> planningReceivingAllocationList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ReeferProductivity> reeferProductivityList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<ServiceContLoad> serviceContLoadList;
    @OneToMany(mappedBy = "masterContainerType")
    private List<PlanningTransDischarge> planningTransDischargeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterContainerType")
    private List<ServiceContTranshipment> serviceContTranshipmentList;

    public MasterContainerType() {
    }

    public MasterContainerType(Integer contType) {
        this.contType = contType;
    }

    public MasterContainerType(Integer contType, String name) {
        this.contType = contType;
        this.name = name;
    }

    public Integer getContType() {
        return contType;
    }

    public void setContType(Integer contType) {
        this.contType = contType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the isoCode
     */
    public String getIsoCode() {
        return isoCode;
    }

    /**
     * @param isoCode the isoCode to set
     */
    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    /**
     * @return the newIsoCode
     */
    public String getNewIsoCode() {
        return newIsoCode;
    }

    /**
     * @param newIsoCode the newIsoCode to set
     */
    public void setNewIsoCode(String newIsoCode) {
        this.newIsoCode = newIsoCode;
    }

    /**
     * @return the dimension
     */
    public String getDimension() {
        return dimension;
    }

    /**
     * @param dimension the dimension to set
     */
    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    /**
     * @return the feetMark
     */
    public Short getFeetMark() {
        return feetMark;
    }

    /**
     * @param feetMark the feetMark to set
     */
    public void setFeetMark(Short feetMark) {
        this.feetMark = feetMark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ServiceCancelLoading> getServiceCancelLoadingList() {
        return serviceCancelLoadingList;
    }

    public void setServiceCancelLoadingList(List<ServiceCancelLoading> serviceCancelLoadingList) {
        this.serviceCancelLoadingList = serviceCancelLoadingList;
    }

    public List<ReeferRecap> getReeferRecapList() {
        return reeferRecapList;
    }

    public void setReeferRecapList(List<ReeferRecap> reeferRecapList) {
        this.reeferRecapList = reeferRecapList;
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

    public List<ServiceTransLoad> getServiceTransLoadList() {
        return serviceTransLoadList;
    }

    public void setServiceTransLoadList(List<ServiceTransLoad> serviceTransLoadList) {
        this.serviceTransLoadList = serviceTransLoadList;
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

    public List<AngsurService> getAngsurServiceList() {
        return angsurServiceList;
    }

    public void setAngsurServiceList(List<AngsurService> angsurServiceList) {
        this.angsurServiceList = angsurServiceList;
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

    public List<ServiceReefer> getServiceReeferList() {
        return serviceReeferList;
    }

    public void setServiceReeferList(List<ServiceReefer> serviceReeferList) {
        this.serviceReeferList = serviceReeferList;
    }

    public List<BaplieDischarge> getBaplieDischargeList() {
        return baplieDischargeList;
    }

    public void setBaplieDischargeList(List<BaplieDischarge> baplieDischargeList) {
        this.baplieDischargeList = baplieDischargeList;
    }

    public List<PlanningShiftDischarge> getPlanningShiftDischargeList() {
        return planningShiftDischargeList;
    }

    public void setPlanningShiftDischargeList(List<PlanningShiftDischarge> planningShiftDischargeList) {
        this.planningShiftDischargeList = planningShiftDischargeList;
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

    public List<MasterYardConstraintType> getMasterYardConstraintTypeList() {
        return masterYardConstraintTypeList;
    }

    public void setMasterYardConstraintTypeList(List<MasterYardConstraintType> masterYardConstraintTypeList) {
        this.masterYardConstraintTypeList = masterYardConstraintTypeList;
    }

    public List<PenumpukanSusulanService> getPenumpukanSusulanServiceList() {
        return penumpukanSusulanServiceList;
    }

    public void setPenumpukanSusulanServiceList(List<PenumpukanSusulanService> penumpukanSusulanServiceList) {
        this.penumpukanSusulanServiceList = penumpukanSusulanServiceList;
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

    public List<PlanningReceivingAllocation> getPlanningReceivingAllocationList() {
        return planningReceivingAllocationList;
    }

    public void setPlanningReceivingAllocationList(List<PlanningReceivingAllocation> planningReceivingAllocationList) {
        this.planningReceivingAllocationList = planningReceivingAllocationList;
    }

    public List<ReeferProductivity> getReeferProductivityList() {
        return reeferProductivityList;
    }

    public void setReeferProductivityList(List<ReeferProductivity> reeferProductivityList) {
        this.reeferProductivityList = reeferProductivityList;
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

    public List<ServiceContTranshipment> getServiceContTranshipmentList() {
        return serviceContTranshipmentList;
    }

    public void setServiceContTranshipmentList(List<ServiceContTranshipment> serviceContTranshipmentList) {
        this.serviceContTranshipmentList = serviceContTranshipmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contType != null ? contType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterContainerType)) {
            return false;
        }
        MasterContainerType other = (MasterContainerType) object;
        if ((this.contType == null && other.contType != null) || (this.contType != null && !this.contType.equals(other.contType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterContainerType[contType=" + contType + "]";
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

    public MasterContainerTypeInGeneral getMasterContainerTypeInGeneral() {
        return masterContainerTypeInGeneral;
    }

    public void setMasterContainerTypeInGeneral(MasterContainerTypeInGeneral masterContainerTypeInGeneral) {
        this.masterContainerTypeInGeneral = masterContainerTypeInGeneral;
    }
}
