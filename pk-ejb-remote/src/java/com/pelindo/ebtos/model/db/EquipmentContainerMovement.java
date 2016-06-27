/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author R Seno Anggoro
 */
@Entity
@Table(name = "equipment_container_movement")
@EntityListeners({AuditTrailEntityListener.class})
@NamedQueries({
    @NamedQuery(name = "EquipmentContainerMovement.findAll", query = "SELECT e FROM EquipmentContainerMovement e"),
    @NamedQuery(name = "EquipmentContainerMovement.findById", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.id = :id"),
    @NamedQuery(name = "EquipmentContainerMovement.findByContNo", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.contNo = :contNo"),
    @NamedQuery(name = "EquipmentContainerMovement.findNotFinishedYetByActivityAndService", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.planningVessel.noPpkb = :noPpkb AND e.contNo = :contNo AND e.activity = :activity AND e.service = :service AND e.endTime IS NULL"),
    @NamedQuery(name = "EquipmentContainerMovement.findByBlNo", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.blNo = :blNo"),
    @NamedQuery(name = "EquipmentContainerMovement.updatePlanningVesselByContNo", query = "UPDATE EquipmentContainerMovement e SET e.planningVessel = :newValue WHERE e.planningVessel = :oldValue AND e.contNo IN :containers"),
    @NamedQuery(name = "EquipmentContainerMovement.findByStartTime", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.startTime = :startTime"),
    @NamedQuery(name = "EquipmentContainerMovement.findByEndTime", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.endTime = :endTime"),
    @NamedQuery(name = "EquipmentContainerMovement.findByActivity", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.activity = :activity"),
    @NamedQuery(name = "EquipmentContainerMovement.findByService", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.service = :service"),
    @NamedQuery(name = "EquipmentContainerMovement.findByCreatedBy", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.createdBy = :createdBy"),
    @NamedQuery(name = "EquipmentContainerMovement.findByCreatedDate", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.createdDate = :createdDate"),
    @NamedQuery(name = "EquipmentContainerMovement.findByModifiedBy", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "EquipmentContainerMovement.findByModifiedDate", query = "SELECT e FROM EquipmentContainerMovement e WHERE e.modifiedDate = :modifiedDate")})
public class EquipmentContainerMovement implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;

    public static final String SERVICE_DISCHARGE = "DISCHARGE";
    public static final String SERVICE_RECEIVING = "RECEIVING";
    public static final String SERVICE_RELOCATION = "RELOCATION";
    public static final String SERVICE_CANCEL_LOADING = "CANCEL_LOADING";
    public static final String SERVICE_TRANSHIPMENT = "TRANSHIPMENT";
    public static final String SERVICE_SHIFTING = "SHIFTING";
    public static final String ACTIVITY_LIFTON = "LIFTON";
    public static final String ACTIVITY_LIFTOFF = "LIFTOFF";
    public static final String ACTIVITY_HT = "H/T";

    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Column(name = "bl_no", length = 100)
    private String blNo;
    @Basic(optional = false)
    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Basic(optional = false)
    @Column(name = "activity", nullable = false, length = 15)
    private String activity;
    @Basic(optional = false)
    @Column(name = "service", nullable = false, length = 30)
    private String service;
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
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", nullable = false)
    @ManyToOne(optional = false)
    private PlanningVessel planningVessel;
    @JoinColumn(name = "opt_code", referencedColumnName = "opt_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterOperator masterOperator;
    @JoinColumn(name = "equip_code", referencedColumnName = "equip_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterEquipment masterEquipment;
    @JoinColumn(name = "movement_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private ContainerMovementHistory containerMovementHistory;

    public EquipmentContainerMovement() {
    }

    public EquipmentContainerMovement(Integer id) {
        this.id = id;
    }

    public EquipmentContainerMovement(Integer id, String contNo, Date startTime, String activity, String service, String createdBy, Date createdDate) {
        this.id = id;
        this.contNo = contNo;
        this.startTime = startTime;
        this.activity = activity;
        this.service = service;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
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

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
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

    public ContainerMovementHistory getContainerMovementHistory() {
        return containerMovementHistory;
    }

    public void setContainerMovementHistory(ContainerMovementHistory containerMovementHistory) {
        this.containerMovementHistory = containerMovementHistory;
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
        if (!(object instanceof EquipmentContainerMovement)) {
            return false;
        }
        EquipmentContainerMovement other = (EquipmentContainerMovement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.EquipmentContainerMovement[id=" + id + "]";
    }
}
