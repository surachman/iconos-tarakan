/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningTransDischarge;
import com.pelindo.ebtos.model.db.PlanningTransLoad;
import com.pelindo.ebtos.model.db.PlanningUcReceiving;
import com.pelindo.ebtos.model.db.PlanningUcTrans;
import com.pelindo.ebtos.model.db.PlanningUncontainerized;
import com.pelindo.ebtos.model.db.PlanningucShiftingPlacement;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceDelivery;
import com.pelindo.ebtos.model.db.ServiceDeliveryUc;
import com.pelindo.ebtos.model.db.ServiceDischargeUc;
import com.pelindo.ebtos.model.db.ServiceReceiving;
import com.pelindo.ebtos.model.db.ServiceTransLoad;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Table(name = "m_yard")
@NamedQueries({
    @NamedQuery(name = "MasterYard.findAll", query = "SELECT m FROM MasterYard m order by m.block"),
    @NamedQuery(name = "MasterYard.findByBlock", query = "SELECT m FROM MasterYard m WHERE m.block = :block"),
    @NamedQuery(name = "MasterYard.findByDescription", query = "SELECT m FROM MasterYard m WHERE m.description = :description"),
    @NamedQuery(name = "MasterYard.findBySlot", query = "SELECT m FROM MasterYard m WHERE m.slot = :slot"),
    @NamedQuery(name = "MasterYard.findByRow", query = "SELECT m FROM MasterYard m WHERE m.row = :row"),
    @NamedQuery(name = "MasterYard.findByTier", query = "SELECT m FROM MasterYard m WHERE m.tier = :tier")})
@NamedNativeQueries({
    /*
    //@NamedNativeQuery(name="MasterYard.Native.findMasterYards",query="Select block, description, cast(slot as integer) slot, cast\"ROW\", tier, created_by, created_date, modified_by, modified_date, yard FROM m_yard ORDER BY block"),
*/
    //@NamedNativeQuery(name="MasterYard.Native.findMasterYards",query="select block, description, cast(slot as smallint) slot, cast(\"ROW\" as smallint) \"ROW\", cast(tier as smallint) tier from m_yard order by block"),
    @NamedNativeQuery(name="MasterYard.Native.findMasterYards",query="select block, description, 1 slot, 10 , 6 tier from m_yard order by block"),
/*    
    @NamedNativeQuery(name="MasterYard.Native.findNotPlannedBlockLocationByNoPpkb",query="SELECT DISTINCT my.block FROM m_yard my JOIN planning_cont_load pcl ON (my.block = pcl.block) WHERE pcl.no_ppkb = ? AND pcl.completed = FALSE AND pcl.status_cancel_loading = FALSE AND (pcl.v_bay IS NULL OR pcl.v_row IS NULL OR pcl.v_tier IS NULL) ORDER BY my.block"),
*/
    @NamedNativeQuery(name="MasterYard.Native.findNotPlannedBlockLocationByNoPpkb",query=
"SELECT DISTINCT my.block " 
+"FROM m_yard my " 
+"JOIN planning_cont_load pcl " 
+"ON (my.block                        = pcl.block) " 
+"WHERE pcl.no_ppkb                   = ? " 
+"AND nvln(pcl.completed)             = 0 " 
+"AND nvln(pcl.status_cancel_loading) = 0 " 
+"AND (pcl.v_bay                     IS NULL " 
+"OR pcl.v_row                       IS NULL " 
+"OR pcl.v_tier                      IS NULL) " 
+"ORDER BY my.block"),

/*    
    @NamedNativeQuery(name="MasterYard.Native.findNotPlannedSlotLocationByNoPpkbAndBlock",query="SELECT DISTINCT pcl.y_slot::int FROM planning_cont_load pcl WHERE pcl.no_ppkb = ? AND pcl.block = ? AND pcl.completed = FALSE AND pcl.status_cancel_loading = FALSE AND (pcl.v_bay IS NULL OR pcl.v_row IS NULL OR pcl.v_tier IS NULL) ORDER BY pcl.y_slot::int"),
*/
    @NamedNativeQuery(name="MasterYard.Native.findNotPlannedSlotLocationByNoPpkbAndBlock",query=    
"SELECT DISTINCT CAST (pcl.y_slot AS INTEGER) " 
+"FROM planning_cont_load pcl " 
+"WHERE pcl.no_ppkb                   = ? " 
+"AND pcl.block                       = ? " 
+"AND nvln(pcl.completed)             = 0 " 
+"AND nvln(pcl.status_cancel_loading) = 0 " 
+"AND (pcl.v_bay                     IS NULL " 
+"OR pcl.v_row                       IS NULL " 
+"OR pcl.v_tier                      IS NULL) " 
+"ORDER BY CAST (pcl.y_slot AS INTEGER)"),    
    @NamedNativeQuery(name="MasterYard.Native.findMasterYardByBlock",query="Select slot, \"ROW\", tier, description FROM m_yard WHERE block = ? "),
    @NamedNativeQuery(name="MasterYard.Native.findAllMax",query="Select MAX(slot), MAX(\"ROW\"), MAX(tier) FROM m_yard WHERE block = ?"),
    @NamedNativeQuery(name="MasterYard.Native.findAllBlocks",query="SELECT block FROM m_yard ORDER BY block"),
    @NamedNativeQuery(name="MasterYard.Native.findMasterYardsSimpleByBlock",query="Select block, description, slot, \"ROW\", tier, yard FROM m_yard WHERE block = ? ORDER BY block", resultClass=MasterYard.class),
    @NamedNativeQuery(name="MasterYard.Native.findYardCapacity",query="select sum(my.slot*my.\"ROW\"*my.tier) jumlah from m_yard my")
})
public class MasterYard implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "block", nullable = false, length = 5)
    private String block;
    @Column(name = "description", length = 256)
    private String description;
    @Column(name = "slot")
    private Short slot;
    @Column(name = "\"ROW\"")
    private Short row;
    @Column(name = "tier")
    private Short tier;
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
    @JoinColumn(name = "yard", referencedColumnName = "yard")
    @ManyToOne
    private MasterCy masterCy;
    @OneToMany(mappedBy = "masterYard")
    private List<PlanningUcReceiving> planningUcReceivingList;
    @OneToMany(mappedBy = "masterYard")
    private List<PlanningucShiftingPlacement> planningucShiftingPlacementList;
    @OneToMany(mappedBy = "masterYard")
    private List<ServiceTransLoad> serviceTransLoadList;
    @OneToMany(mappedBy = "masterYard")
    private List<PlanningUcTrans> planningUcTransList;
    @OneToMany(mappedBy = "masterYard")
    private List<ServiceDischargeUc> serviceDischargeUcList;
    @OneToMany(mappedBy = "masterYard")
    private List<ServiceDelivery> serviceDeliveryList;
    @OneToMany(mappedBy = "masterYard")
    private List<ServiceReceiving> serviceReceivingList;
    @OneToMany(mappedBy = "masterYard")
    private List<ServiceContDischarge> serviceContDischargeList;
    @OneToMany(mappedBy = "masterYard")
    private List<ServiceDeliveryUc> serviceDeliveryUcList;
    @OneToMany(mappedBy = "masterYard")
    private List<MasterYardConstraintSize> masterYardConstraintSizeList;
    @OneToMany(mappedBy = "masterYard")
    private List<PlanningUncontainerized> planningUncontainerizedList;
    @OneToMany(mappedBy = "masterYard")
    private List<PlanningContLoad> planningContLoadList;
    @OneToMany(mappedBy = "masterYard")
    private List<PlanningTransLoad> planningTransLoadList;
    @OneToMany(mappedBy = "masterYard")
    private List<PlanningShiftDischarge> planningShiftDischargeList;
    @OneToMany(mappedBy = "masterYard")
    private List<PlanningContDischarge> planningContDischargeList;
    @OneToMany(mappedBy = "masterYard")
    private List<MasterYardConstraintType> masterYardConstraintTypeList; 
    @OneToMany(mappedBy = "masterYard")
    private List<ServiceContLoad> serviceContLoadList;
    @OneToMany(mappedBy = "masterYard")
    private List<PlanningTransDischarge> planningTransDischargeList;
    @OneToMany(mappedBy = "masterYard")
    private List<ServiceContTranshipment> serviceContTranshipmentList;

    public MasterYard() {
    }

    public MasterYard(String block) {
        this.block = block;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getSlot() {
        return slot;
    }

    public void setSlot(Short slot) {
        this.slot = slot;
    }

    public Short  getRow() {
        return row;
    }

    public void setRow(Short row) {
        this.row = row;
    }

    public Short  getTier() {
        return tier;
    }

    public void setTier(Short tier) {
        this.tier = tier;
    } 

    public List<PlanningUcReceiving> getPlanningUcReceivingList() {
        return planningUcReceivingList;
    }

    public void setPlanningUcReceivingList(List<PlanningUcReceiving> planningUcReceivingList) {
        this.planningUcReceivingList = planningUcReceivingList;
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

    public List<MasterYardConstraintSize> getMasterYardConstraintSizeList() {
        return masterYardConstraintSizeList;
    }

    public void setMasterYardConstraintSizeList(List<MasterYardConstraintSize> masterYardConstraintSizeList) {
        this.masterYardConstraintSizeList = masterYardConstraintSizeList;
    }

    public List<PlanningUncontainerized> getPlanningUncontainerizedList() {
        return planningUncontainerizedList;
    }

    public void setPlanningUncontainerizedList(List<PlanningUncontainerized> planningUncontainerizedList) {
        this.planningUncontainerizedList = planningUncontainerizedList;
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

    public List<MasterYardConstraintType> getMasterYardConstraintTypeList() {
        return masterYardConstraintTypeList;
    }

    public void setMasterYardConstraintTypeList(List<MasterYardConstraintType> masterYardConstraintTypeList) {
        this.masterYardConstraintTypeList = masterYardConstraintTypeList;
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
        hash += (block != null ? block.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterYard)) {
            return false;
        }
        MasterYard other = (MasterYard) object;
        if ((this.block == null && other.block != null) || (this.block != null && !this.block.equals(other.block))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterYard[block=" + block + "]";
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

    public MasterCy getMasterCy() {
        return masterCy;
    }

    public void setMasterCy(MasterCy masterCy) {
        this.masterCy = masterCy;
    }

}
