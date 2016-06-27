/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "equipment")
@NamedQueries({
    @NamedQuery(name = "Equipment.findAll", query = "SELECT e FROM Equipment e"),
    @NamedQuery(name = "Equipment.findById", query = "SELECT e FROM Equipment e WHERE e.id = :id"),
    @NamedQuery(name = "Equipment.findByContNo", query = "SELECT e FROM Equipment e WHERE e.contNo = :contNo"),
    @NamedQuery(name = "Equipment.findByBlNo", query = "SELECT e FROM Equipment e WHERE e.blNo = :blNo"),
    @NamedQuery(name = "Equipment.findByStartTime", query = "SELECT e FROM Equipment e WHERE e.startTime = :startTime"),
    @NamedQuery(name = "Equipment.updatePlanningVessel", query = "UPDATE Equipment e SET e.planningVessel = :newValue WHERE e.planningVessel = :oldValue"),
    @NamedQuery(name = "Equipment.updatePlanningVesselByContNo", query = "UPDATE Equipment e SET e.planningVessel = :newValue WHERE e.planningVessel = :oldValue AND e.contNo IN :containers"),
    @NamedQuery(name = "Equipment.updatePlanningVesselReceivingByContNo", query = "UPDATE Equipment e SET e.noPpkbRecv = :newValue WHERE e.noPpkbRecv = :oldValue AND e.contNo IN :containers"),
    @NamedQuery(name = "Equipment.findByEquipmentActCodeAndOperation", query = "SELECT e FROM Equipment e WHERE e.planningVessel.noPpkb = :noPpkb AND e.contNo = :contNo AND e.equipmentActCode = :equipmentActCode AND e.operation = :operation"),
    @NamedQuery(name = "Equipment.findByEquipmentActCodeAndOperationReceiving", query = "SELECT e FROM Equipment e WHERE e.noPpkbRecv = :noPpkb AND e.contNo = :contNo AND e.equipmentActCode = :equipmentActCode AND e.operation = :operation"),
    @NamedQuery(name = "Equipment.findByEndTime", query = "SELECT e FROM Equipment e WHERE e.endTime = :endTime"),
    @NamedQuery(name = "Equipment.findByEquipmentActCode", query = "SELECT e FROM Equipment e WHERE e.equipmentActCode = :equipmentActCode"),
    @NamedQuery(name = "Equipment.findByOperation", query = "SELECT e FROM Equipment e WHERE e.operation = :operation"),
    @NamedQuery(name = "Equipment.updateEndTimeByEquipmentConstraint", query = "UPDATE Equipment e SET e.endTime = :endTime WHERE e.planningVessel.noPpkb = :noPpkb AND e.contNo = :contNo AND e.equipmentActCode = :equipmentActCode AND e.operation = :operation"),
    @NamedQuery(name = "Equipment.findByPpkbBLNoEquipmentActCodeAndOperation", query = "SELECT e FROM Equipment e WHERE e.planningVessel.noPpkb = :noPpkb AND e.blNo = :blNo AND e.equipmentActCode = :equipmentActCode AND e.operation= :operation")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "Equipment.Native.findByPpkb", query = "SELECT id FROM equipment WHERE no_ppkb = ?"),
    @NamedNativeQuery(name = "Equipment.Native.findByIdContainer", query = "SELECT MAX(id) FROM equipment WHERE no_ppkb = ? AND cont_no = ? AND equipment_act_code = ? AND operation= ?"),
    @NamedNativeQuery(name = "Equipment.Native.findByIdContainerReceiving", query = "SELECT MAX(e.id) "
                                                                                    + "FROM equipment e "
                                                                                    + "WHERE e.no_ppkb_recv = ? AND e.cont_no = ? AND e.equipment_act_code = ? AND e.operation= ?"),
    @NamedNativeQuery(name = "Equipment.Native.findByIdContainerReceivingPlug", query = "SELECT id FROM equipment WHERE no_ppkb_plug = ? AND cont_no = ? AND equipment_act_code = ? AND operation= ? limit 1"),
    @NamedNativeQuery(name = "Equipment.Native.findByIdContainerOperation", query = "SELECT id FROM equipment WHERE no_ppkb = ? AND cont_no = ? AND equipment_act_code = ? AND operation = ?"),
    @NamedNativeQuery(name = "Equipment.Native.findByContNoActCode", query = "SELECT * FROM equipment e WHERE e.cont_no = ? AND e.equipment_act_code = ? AND e.operation = ?"),
    @NamedNativeQuery(name = "Equipment.Native.findEquipmentId", query = "Select e.id FROM equipment e where e.no_ppkb=? AND e.start_time=? AND e.end_time=? AND e.equipment_act_code = ? AND e.operation = ? "),
    @NamedNativeQuery(name = "Equipment.Native.findIdEquipmentByAll", query = "Select e.id FROM equipment e where e.equip_code = ? AND e.opt_code = ? AND e.cont_no = ? AND e.no_ppkb = ? AND e.start_time = ? AND e.end_time = ? AND e.equipment_act_code = ? AND e.operation = ? "),
    @NamedNativeQuery(name = "Equipment.Native.findIdEquipmentByAllBlNo", query = "Select e.id FROM equipment e where e.equip_code = ? AND e.opt_code = ? AND e.bl_no = ? AND e.no_ppkb = ? AND e.start_time = ? AND e.end_time = ? AND e.equipment_act_code = ? AND e.operation = ? "),
    @NamedNativeQuery(name = "Equipment.Native.deleteEquipmentById", query = "DELETE FROM equipment WHERE id = ?"),
    @NamedNativeQuery(name = "Equipment.Native.findEquipmentByValidasi", query = "SELECT * FROM equipment r where r.start_time= ? AND r.end_time=? AND r.equipment_act_code=? AND r.operation=?"),
    @NamedNativeQuery(name = "Equipment.Native.findEquipmentHTUc", query = "SELECT id FROM equipment WHERE equip_code = (SELECT equip_code FROM m_equipment WHERE equipment_type_code = '002') AND no_ppkb = ? AND bl_no = ? AND equipment_act_code = ? AND operation = ?"),
    @NamedNativeQuery(name = "Equipment.Native.findStartTimeServiceReceiving", query = "SELECT start_time FROM equipment WHERE equipment_act_code='LIFTON' AND operation='LOAD' AND no_ppkb=? AND cont_no=?"),
    @NamedNativeQuery(name = "Equipment.Native.findIdByBLno", query = "SELECT id FROM equipment WHERE no_ppkb = ? AND bl_no = ? AND equipment_act_code = ? AND operation= ?"),
    @NamedNativeQuery(name = "Equipment.Native.updateEquipmentHTUncontainerized", query = "UPDATE equipment SET end_time = ? WHERE no_ppkb = ? AND bl_no = ? AND equipment_act_code = ? AND operation= ?"),
    @NamedNativeQuery(name = "Equipment.Native.findIdByPPKBnContNo" , query = "SELECT id FROM equipment WHERE no_ppkb=? AND cont_no=?"),
    @NamedNativeQuery(name = "Equipment.Native.findDischargeDestinationByContNo" , query = "SELECT e.no_ppkb, e.cont_no, COALESCE(scd.block, COALESCE(sct.block, COALESCE(sct.block, NULL))) block, "
                                                                                    + "COALESCE(scd.y_slot, COALESCE(sct.y_slot, COALESCE(psd.y_slot, NULL))) y_slot, "
                                                                                    + "COALESCE(scd.y_row, COALESCE(sct.y_row, COALESCE(psd.y_row, NULL))) y_row, "
                                                                                    + "COALESCE(scd.y_tier, COALESCE(sct.y_tier, COALESCE(psd.y_tier, NULL))) y_tier "
                                                                            + "FROM ((equipment e "
                                                                                    + "LEFT JOIN service_cont_discharge scd ON (scd.no_ppkb=e.no_ppkb AND scd.cont_no=e.cont_no)) "
                                                                                    + "LEFT JOIN service_cont_transhipment sct ON (sct.no_ppkb=e.no_ppkb AND sct.cont_no=e.cont_no)) "
                                                                                    + "LEFT JOIN service_shifting sf ON (sf.no_ppkb=e.no_ppkb AND sf.cont_no=e.cont_no) "
                                                                                            + "LEFT JOIN planning_shift_discharge psd ON (psd.no_ppkb=sf.no_ppkb AND psd.cont_no=sf.cont_no) "
                                                                            + "WHERE e.equipment_act_code='H/T' AND e.end_time IS NULL AND e.operation IN ('TRANSDISCHARGE', 'DISCHARGE', 'SHIFTING-CY') AND e.cont_no = ? ")
    })
public class Equipment implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "bl_no")
    private String blNo;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "equipment_act_code", length = 15)
    private String equipmentActCode;
    @Column(name = "operation", length = 50)
    private String operation;
    @Column(name = "no_ppkb_recv", length = 30)
    private String noPpkbRecv;
    @Column(name = "no_ppkb_plug", length = 30)
    private String noPpkbPlug;
    @JoinColumn(name = "opt_code", referencedColumnName = "opt_code")
    @ManyToOne
    private MasterOperator masterOperator;
    @JoinColumn(name = "equip_code", referencedColumnName = "equip_code")
    @ManyToOne
    private MasterEquipment masterEquipment;
    @OneToMany(mappedBy = "equipment")
    private List<ServiceDischargeUc> serviceDischargeUcList;
    @OneToMany(mappedBy = "equipment")
    private List<ServiceLoadUc> serviceLoadUcList;
    @ManyToOne
    @JoinColumns(value = {
        @JoinColumn(name = "cont_no", referencedColumnName = "cont_no", insertable = false, updatable = false),
        @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", insertable = false, updatable = false)
    })
    private ServiceReceiving serviceReceiving;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
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


    public Equipment() {
    }

    public Equipment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEquipmentActCode() {
        return equipmentActCode;
    }

    public void setEquipmentActCode(String equipmentActCode) {
        this.equipmentActCode = equipmentActCode;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the noPpkbRecv
     */
    public String getNoPpkbRecv() {
        return noPpkbRecv;
    }

    /**
     * @param noPpkbRecv the noPpkbRecv to set
     */
    public void setNoPpkbRecv(String noPpkbRecv) {
        this.noPpkbRecv = noPpkbRecv;
    }

    public MasterOperator getMasterOperator() {
        return masterOperator;
    }

    public void setMasterOperator(MasterOperator masterOperator) {
        this.masterOperator = masterOperator;
    }

    public MasterEquipment getMasterEquipment() {
        return masterEquipment;
    }

    public void setMasterEquipment(MasterEquipment masterEquipment) {
        this.masterEquipment = masterEquipment;
    }

    public List<ServiceDischargeUc> getServiceDischargeUcList() {
        return serviceDischargeUcList;
    }

    public void setServiceDischargeUcList(List<ServiceDischargeUc> serviceDischargeUcList) {
        this.serviceDischargeUcList = serviceDischargeUcList;
    }

    /**
     * @return the serviceLoadUcList
     */
    public List<ServiceLoadUc> getServiceLoadUcList() {
        return serviceLoadUcList;
    }

    /**
     * @param serviceLoadUcList the serviceLoadUcList to set
     */
    public void setServiceLoadUcList(List<ServiceLoadUc> serviceLoadUcList) {
        this.serviceLoadUcList = serviceLoadUcList;
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

    public ServiceReceiving getServiceReceiving() {
        return serviceReceiving;
    }

    public void setServiceReceiving(ServiceReceiving serviceReceiving) {
        this.serviceReceiving = serviceReceiving;
    }

    public String getNoPpkbPlug() {
        return noPpkbPlug;
    }

    public void setNoPpkbPlug(String noPpkbPlug) {
        this.noPpkbPlug = noPpkbPlug;
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
        if (!(object instanceof Equipment)) {
            return false;
        }
        Equipment other = (Equipment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.Equipment[id=" + id + "]";
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }
}
