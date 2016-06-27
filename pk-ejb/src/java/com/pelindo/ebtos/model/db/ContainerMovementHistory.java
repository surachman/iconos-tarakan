/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterYard;
import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author R Seno Anggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "container_movement_history")
@NamedQueries({
    @NamedQuery(name = "ContainerMovementHistory.findAll", query = "SELECT c FROM ContainerMovementHistory c"),
    @NamedQuery(name = "ContainerMovementHistory.findById", query = "SELECT c FROM ContainerMovementHistory c WHERE c.id = :id"),
    @NamedQuery(name = "ContainerMovementHistory.findByContNo", query = "SELECT c FROM ContainerMovementHistory c WHERE c.contNo = :contNo"),
    @NamedQuery(name = "ContainerMovementHistory.findMovableOffByContNo", query = "SELECT c FROM ContainerMovementHistory c WHERE c.contNo = :contNo AND c.isHt = 'TRUE'"),
    @NamedQuery(name = "ContainerMovementHistory.findByService", query = "SELECT c FROM ContainerMovementHistory c WHERE c.service = :service"),
    @NamedQuery(name = "ContainerMovementHistory.findByIsCfs", query = "SELECT c FROM ContainerMovementHistory c WHERE c.isCfs = :isCfs"),
    @NamedQuery(name = "ContainerMovementHistory.findByIsInspection", query = "SELECT c FROM ContainerMovementHistory c WHERE c.isInspection = :isInspection"),
    @NamedQuery(name = "ContainerMovementHistory.findByIsHt", query = "SELECT c FROM ContainerMovementHistory c WHERE c.isHt = :isHt"),
    @NamedQuery(name = "ContainerMovementHistory.findByIsBehandle", query = "SELECT c FROM ContainerMovementHistory c WHERE c.isBehandle = :isBehandle"),
    @NamedQuery(name = "ContainerMovementHistory.findByIsLast", query = "SELECT c FROM ContainerMovementHistory c WHERE c.isLast = :isLast"),
    @NamedQuery(name = "ContainerMovementHistory.findLastRecord", query = "SELECT c FROM ContainerMovementHistory c WHERE c.planningVessel.noPpkb = :noPpkb AND c.contNo = :contNo AND c.isLast = 'TRUE'"),
    @NamedQuery(name = "ContainerMovementHistory.findByCreatedBy", query = "SELECT c FROM ContainerMovementHistory c WHERE c.createdBy = :createdBy"),
    @NamedQuery(name = "ContainerMovementHistory.findByCreatedDate", query = "SELECT c FROM ContainerMovementHistory c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "ContainerMovementHistory.findByModifiedBy", query = "SELECT c FROM ContainerMovementHistory c WHERE c.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "ContainerMovementHistory.findByModifiedDate", query = "SELECT c FROM ContainerMovementHistory c WHERE c.modifiedDate = :modifiedDate")})
@NamedNativeQueries({
/* 
* Updated by SRACH
* Tue 28 Oct 2014 11:19:40 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*
    @NamedNativeQuery(name = "ContainerMovementHistory.Native.findMovementHistories", query = "SELECT cmh.no_ppkb, cmh.cont_no, s.cont_size, mct.type_in_general, s.cont_status, s.cont_gross, cmh.block, cmh.yard_slot, cmh.yard_row, cmh.yard_tier, cmh.is_ht, cmh.is_behandle, cmh.is_cfs, cmh.is_inspection, CASE WHEN NOT cmh.is_behandle AND NOT cmh.is_cfs AND NOT cmh.is_inspection THEN TRUE ELSE FALSE END AS is_cy, cmh.is_last, cmh.service "
                                                                                            + "FROM container_movement_history cmh "
                                                                                                    + "JOIN (SELECT cont_no, no_ppkb, cont_size, cont_type, cont_status, cont_gross FROM service_cont_discharge "
                                                                                                            + "UNION ALL "
                                                                                                            + "SELECT cont_no, no_ppkb, cont_size, cont_type, cont_status, cont_gross FROM planning_cont_load WHERE status_cancel_loading <> TRUE "
                                                                                                            + "UNION ALL "
                                                                                                            + "SELECT cont_no, no_ppkb, cont_size, cont_type, cont_status, cont_gross FROM cancel_loading_service) s ON (s.cont_no=cmh.cont_no AND s.no_ppkb=cmh.no_ppkb) "
                                                                                                    + "JOIN m_container_type mct ON (s.cont_type=mct.cont_type) "
                                                                                            + "ORDER BY cmh.id DESC"),
*/
    @NamedNativeQuery(name = "ContainerMovementHistory.Native.findMovementHistories", query = 
"SELECT cmh.no_ppkb, " 
+"  cmh.cont_no, " 
+"  s.cont_size, " 
+"  mct.type_in_general, " 
+"  s.cont_status, " 
+"  s.cont_gross, " 
+"  cmh.block, " 
+"  cmh.yard_slot, " 
+"  cmh.yard_row, " 
+"  cmh.yard_tier, " 
+"  cmh.is_ht, " 
+"  cmh.is_behandle, " 
+"  cmh.is_cfs, " 
+"  cmh.is_inspection, " 
+"  CASE " 
+"    WHEN cmh.is_behandle  <> 'TRUE' " 
+"    AND cmh.is_cfs        <> 'TRUE' " 
+"    AND cmh.is_inspection <> 'TRUE' " 
+"    THEN 'TRUE' " 
+"    ELSE 'FALSE' " 
+"  END AS is_cy, " 
+"  cmh.is_last, " 
+"  cmh.service " 
+"FROM container_movement_history cmh " 
+"JOIN " 
+"  (SELECT cont_no, " 
+"    no_ppkb, " 
+"    cont_size, " 
+"    cont_type, " 
+"    cont_status, " 
+"    cont_gross " 
+"  FROM service_cont_discharge " 
+"  UNION ALL " 
+"  SELECT cont_no, " 
+"    no_ppkb, " 
+"    cont_size, " 
+"    cont_type, " 
+"    cont_status, " 
+"    cont_gross " 
+"  FROM planning_cont_load " 
+"  WHERE status_cancel_loading <> 'TRUE' " 
+"  UNION ALL " 
+"  SELECT cont_no, " 
+"    no_ppkb, " 
+"    cont_size, " 
+"    cont_type, " 
+"    cont_status, " 
+"    cont_gross " 
+"  FROM cancel_loading_service " 
+"  ) s " 
+"ON (s.cont_no=cmh.cont_no " 
+"AND s.no_ppkb=cmh.no_ppkb) " 
+"JOIN m_container_type mct " 
+"ON (s.cont_type=mct.cont_type) " 
+"ORDER BY cmh.id DESC"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 11:19:40 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*                                                                                            
    @NamedNativeQuery(name = "ContainerMovementHistory.Native.findDischargeMovementHistories", query = "SELECT scd.no_ppkb, scd.cont_no, scd.cont_size, mct.type_in_general, scd.cont_status, scd.cont_gross, cmh.block, cmh.yard_slot, cmh.yard_row, cmh.yard_tier, cmh.is_ht, cmh.is_behandle, cmh.is_cfs, cmh.is_inspection, CASE WHEN NOT cmh.is_behandle AND NOT cmh.is_cfs AND NOT cmh.is_inspection THEN TRUE ELSE FALSE END AS is_cy, cmh.is_last "
                                                                                                    + "FROM service_cont_discharge scd "
                                                                                                            + "JOIN container_movement_history cmh ON (scd.cont_no=cmh.cont_no AND scd.no_ppkb=cmh.no_ppkb) "
                                                                                                            + "JOIN m_container_type mct ON (scd.cont_type=mct.cont_type) "
                                                                                                    + "WHERE cmh.service = '" + ContainerMovementHistory.SERVICE_DISCHARGE + "' "
                                                                                                    + "ORDER BY cmh.id DESC"),
*/                                                                                                    
    @NamedNativeQuery(name = "ContainerMovementHistory.Native.findDischargeMovementHistories", query = 
"SELECT scd.no_ppkb, " 
+"  scd.cont_no, " 
+"  scd.cont_size, " 
+"  mct.type_in_general, " 
+"  scd.cont_status, " 
+"  scd.cont_gross, " 
+"  cmh.block, " 
+"  cmh.yard_slot, " 
+"  cmh.yard_row, " 
+"  cmh.yard_tier, " 
+"  cmh.is_ht, " 
+"  cmh.is_behandle, " 
+"  cmh.is_cfs, " 
+"  cmh.is_inspection, " 
+"  --  CASE " 
+"  --    WHEN NOT cmh.is_behandle " 
+"  --    AND NOT cmh.is_cfs " 
+"  --    AND NOT cmh.is_inspection " 
+"  --    THEN TRUE " 
+"  --    ELSE FALSE " 
+"  --  END AS is_cy, " 
+"  CASE " 
+"    WHEN 'TRUE' <> NVL(cmh.is_behandle,'FALSE') " 
+"    AND 'TRUE'  <> NVL(cmh.is_cfs,'FALSE') " 
+"    AND 'TRUE'  <> NVL(cmh.is_inspection,'FALSE') " 
+"    THEN 'TRUE' " 
+"    ELSE 'FALSE' " 
+"  END AS is_cy, " 
+"  cmh.is_last " 
+"FROM service_cont_discharge scd " 
+"JOIN container_movement_history cmh " 
+"ON (scd.cont_no=cmh.cont_no " 
+"AND scd.no_ppkb=cmh.no_ppkb) " 
+"JOIN m_container_type mct " 
+"ON (scd.cont_type =mct.cont_type) " 
+"WHERE cmh.service = '" + ContainerMovementHistory.SERVICE_DISCHARGE + "' " 
+"ORDER BY cmh.id DESC"),                                                                                                    

/* 
* Updated by SRACH
* Tue 28 Oct 2014 11:19:40 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*
    @NamedNativeQuery(name = "ContainerMovementHistory.Native.findReceivingMovementHistories", query = "SELECT sr.no_ppkb, sr.cont_no, sr.cont_size, mct.type_in_general, sr.cont_status, sr.cont_gross, cmh.block, cmh.yard_slot, cmh.yard_row, cmh.yard_tier, cmh.is_ht, cmh.is_behandle, cmh.is_cfs, cmh.is_inspection, CASE WHEN NOT cmh.is_behandle AND NOT cmh.is_cfs AND NOT cmh.is_inspection THEN TRUE ELSE FALSE END AS is_cy, cmh.is_last "
                                                                                                    + "FROM service_receiving sr "
                                                                                                            + "JOIN container_movement_history cmh ON (sr.cont_no=cmh.cont_no AND sr.no_ppkb=cmh.no_ppkb) "
                                                                                                            + "JOIN m_container_type mct ON (sr.cont_type=mct.cont_type) "
                                                                                                    + "WHERE cmh.service = '" + ContainerMovementHistory.SERVICE_RECEIVING + "' "
                                                                                                    + "ORDER BY cmh.id DESC")
*/
    @NamedNativeQuery(name = "ContainerMovementHistory.Native.findReceivingMovementHistories", query = 
"SELECT sr.no_ppkb, " 
+"  sr.cont_no, " 
+"  sr.cont_size, " 
+"  mct.type_in_general, " 
+"  sr.cont_status, " 
+"  sr.cont_gross, " 
+"  cmh.block, " 
+"  cmh.yard_slot, " 
+"  cmh.yard_row, " 
+"  cmh.yard_tier, " 
+"  cmh.is_ht, " 
+"  cmh.is_behandle, " 
+"  cmh.is_cfs, " 
+"  cmh.is_inspection, " 
+"  --  CASE " 
+"  --    WHEN NOT cmh.is_behandle " 
+"  --    AND NOT cmh.is_cfs " 
+"  --    AND NOT cmh.is_inspection " 
+"  --    THEN TRUE " 
+"  --    ELSE FALSE " 
+"  --  END AS is_cy, "
+"  CASE " 
+"    WHEN 'TRUE' <> NVL(cmh.is_behandle,'FALSE') " 
+"    AND 'TRUE'  <> NVL(cmh.is_cfs,'FALSE') " 
+"    AND 'TRUE'  <> NVL(cmh.is_inspection,'FALSE') " 
+"    THEN 'TRUE' " 
+"    ELSE 'FALSE' " 
+"  END AS is_cy, "   
+"  cmh.is_last "
+"FROM service_receiving sr "   
+"JOIN container_movement_history cmh "   
+"ON (sr.cont_no=cmh.cont_no "   
+"AND sr.no_ppkb=cmh.no_ppkb) "   
+"JOIN m_container_type mct "   
+"ON (sr.cont_type  =mct.cont_type) "   
+"WHERE cmh.service = '" + ContainerMovementHistory.SERVICE_RECEIVING + "' "   
+"ORDER BY cmh.id DESC")                                                                                                    
})
public class ContainerMovementHistory implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;

    public static final String SERVICE_DISCHARGE = "DISCHARGE";
    public static final String SERVICE_RECEIVING = "RECEIVING";
    public static final String SERVICE_RELOCATION = "RELOCATION";
    public static final String SERVICE_CANCEL_LOADING = "CANCEL_LOADING";
    public static final String SERVICE_TRANSHIPMENT = "TRANSHIPMENT";
    public static final String SERVICE_SHIFTING = "SHIFTING";

    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name="CONTAINER_MOVEMENT_HISTORY_SEQ", sequenceName="CONTAINER_MOVEMENT_HISTORY_SEQ", initialValue = 40, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTAINER_MOVEMENT_HISTORY_SEQ")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Basic(optional = false)
    @Column(name = "service", nullable = false, length = 30)
    private String service;
    @Basic(optional = false)
    @Column(name = "is_cfs", nullable = false)
    private String isCfs = "FALSE";
    @Basic(optional = false)
    @Column(name = "is_inspection", nullable = false)
    private String isInspection = "FALSE";
    @Basic(optional = false)
    @Column(name = "is_ht", nullable = false)
    private String isHt = "FALSE";
    @Basic(optional = false)
    @Column(name = "is_behandle", nullable = false)
    private String isBehandle = "FALSE";
    @Basic(optional = false)
    @Column(name = "is_last", nullable = false)
    private String isLast = "FALSE";
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
    @JoinColumn(name = "block", referencedColumnName = "block", nullable = false)
    @ManyToOne(optional = false)
    private MasterYard masterYard;
    @Basic(optional = false)
    @Column(name = "yard_slot", nullable = false)
    private short yardSlot;
    @Basic(optional = false)
    @Column(name = "yard_row", nullable = false)
    private short yardRow;
    @Basic(optional = false)
    @Column(name = "yard_tier", nullable = false)
    private short yardTier;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "containerMovementHistory")
    private List<EquipmentContainerMovement> equipmentContainerMovementList;

    public ContainerMovementHistory() {
    }

    public ContainerMovementHistory(Integer id) {
        this.id = id;
    }

    public ContainerMovementHistory(Integer id, String contNo, String service, short ySlot, short yRow, short yTier, String isCfs, String isInspection, String isHt, String isBehandle, String isLast, String createdBy, Date createdDate) {
        this.id = id;
        this.contNo = contNo;
        this.service = service;
        this.yardSlot = ySlot;
        this.yardRow = yRow;
        this.yardTier = yTier;
        this.isCfs = isCfs;
        this.isInspection = isInspection;
        this.isHt = isHt;
        this.isBehandle = isBehandle;
        this.isLast = isLast;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }


    public String getIsCfs() {
        return isCfs;
    }

    public void setIsCfs(String isCfs) {
        this.isCfs = isCfs;
    }

    public String getIsInspection() {
        return isInspection;
    }

    public void setIsInspection(String isInspection) {
        this.isInspection = isInspection;
    }

    public String getIsHt() {
        return isHt;
    }

    public void setIsHt(String isHt) {
        this.isHt = isHt;
    }

    public String getIsBehandle() {
        return isBehandle;
    }

    public void setIsBehandle(String isBehandle) {
        this.isBehandle = isBehandle;
    }

    public String getIsLast() {
        return isLast;
    }

    public void setIsLast(String isLast) {
        this.isLast = isLast;
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

    public MasterYard getMasterYard() {
        return masterYard;
    }

    public void setMasterYard(MasterYard masterYard) {
        this.masterYard = masterYard;
    }

    public short getYardSlot() {
        return yardSlot;
    }

    public void setYardSlot(short yardSlot) {
        this.yardSlot = yardSlot;
    }

    public short getYardRow() {
        return yardRow;
    }

    public void setYardRow(short yardRow) {
        this.yardRow = yardRow;
    }

    public short getYardTier() {
        return yardTier;
    }

    public void setYardTier(short yardTier) {
        this.yardTier = yardTier;
    }

    public List<EquipmentContainerMovement> getEquipmentContainerMovementList() {
        return equipmentContainerMovementList;
    }

    public void setEquipmentContainerMovementList(List<EquipmentContainerMovement> equipmentContainerMovementList) {
        this.equipmentContainerMovementList = equipmentContainerMovementList;
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
        if (!(object instanceof ContainerMovementHistory)) {
            return false;
        }
        ContainerMovementHistory other = (ContainerMovementHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ContainerMovementHistory[id=" + id + "]";
    }
}
