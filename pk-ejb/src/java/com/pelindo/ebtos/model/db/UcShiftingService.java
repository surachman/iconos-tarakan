/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

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
@Table(name = "uc_shifting_service")
@NamedQueries({
    @NamedQuery(name = "UcShiftingService.findAll", query = "SELECT u FROM UcShiftingService u"),
    @NamedQuery(name = "UcShiftingService.findById", query = "SELECT u FROM UcShiftingService u WHERE u.id = :id"),
    @NamedQuery(name = "UcShiftingService.findByNoPpkb", query = "SELECT u FROM UcShiftingService u WHERE u.noPpkb = :noPpkb"),
    @NamedQuery(name = "UcShiftingService.findByNoPpkbAndBlNo", query = "SELECT u FROM UcShiftingService u WHERE u.noPpkb = :noPpkb AND u.blNo = :blNo"),
    @NamedQuery(name = "UcShiftingService.deleteByNoPpkbAndBlNo", query = "DELETE FROM UcShiftingService u WHERE u.noPpkb = :noPpkb AND u.blNo = :blNo"),
    @NamedQuery(name = "UcShiftingService.findByActivityCode", query = "SELECT u FROM UcShiftingService u WHERE u.activityCode = :activityCode"),
    @NamedQuery(name = "UcShiftingService.findByCrane", query = "SELECT u FROM UcShiftingService u WHERE u.crane = :crane"),
    @NamedQuery(name = "UcShiftingService.findByBlNo", query = "SELECT u FROM UcShiftingService u WHERE u.blNo = :blNo"),
    @NamedQuery(name = "UcShiftingService.findByShiftTo", query = "SELECT u FROM UcShiftingService u WHERE u.shiftTo = :shiftTo"),
    @NamedQuery(name = "UcShiftingService.findByIsReshipping", query = "SELECT u FROM UcShiftingService u WHERE u.isReshipping = :isReshipping"),
    @NamedQuery(name = "UcShiftingService.findByNoPpkbAndReshippingStatus", query = "SELECT u FROM UcShiftingService u WHERE u.noPpkb = :noPpkb AND u.isReshipping = :isReshipping"),
    @NamedQuery(name = "UcShiftingService.findByOperation", query = "SELECT u FROM UcShiftingService u WHERE u.operation = :operation"),
    @NamedQuery(name = "UcShiftingService.findByIsPaid", query = "SELECT u FROM UcShiftingService u WHERE u.isPaid = :isPaid"),
    @NamedQuery(name = "UcShiftingService.findByIsLanded", query = "SELECT u FROM UcShiftingService u WHERE u.isLanded = :isLanded"),
    @NamedQuery(name = "UcShiftingService.findByIsPlanning", query = "SELECT u FROM UcShiftingService u WHERE u.isPlanning = :isPlanning"),
    @NamedQuery(name = "UcShiftingService.findByIdEquipment", query = "SELECT u FROM UcShiftingService u WHERE u.idEquipment = :idEquipment"),
    @NamedQuery(name = "UcShiftingService.findByIdEquipmentReshipping", query = "SELECT u FROM UcShiftingService u WHERE u.idEquipmentReshipping = :idEquipmentReshipping"),
    @NamedQuery(name = "UcShiftingService.findByNoPpkbStatusPaid", query = "SELECT u FROM UcShiftingService u WHERE u.noPpkb = :noPpkb AND u.isPaid = :isPaid"),
    @NamedQuery(name = "UcShiftingService.updateActivityCodeByNoPpkbStatusPaid", query = "UPDATE UcShiftingService u SET u.activityCode = :activityCode WHERE u.noPpkb = :noPpkb AND u.isPaid = :isPaid")})

@NamedNativeQueries({
/*
    @NamedNativeQuery(name = "UcShiftingService.Native.findUcShiftingServiceWithoutPlan", query = "SELECT c.id,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.id_uc, pu.unit,c.shift_to,c.id_equipment,c.is_reshipping,c.id_equipment_reshipping from uncontainerized_service pu,m_commodity m,uc_shifting_service c where pu.commodity=m.commodity_code AND c.bl_no=pu.bl_no AND c.no_ppkb = ? AND (c.is_reshipping = 'TRUE' OR c.is_landed = 'TRUE') AND c.is_planning = 'FALSE'"),
*/
    @NamedNativeQuery(name = "UcShiftingService.Native.findUcShiftingServiceWithoutPlan", query =     
"SELECT c.id, " 
+"  pu.bl_no, " 
+"  m.name, " 
+"  pu.weight, " 
+"  change(pu.truck_loosing), " 
+"  pu.id_uc, " 
+"  pu.unit, " 
+"  c.shift_to, " 
+"  c.id_equipment, " 
+"  c.is_reshipping, " 
+"  c.id_equipment_reshipping " 
+"FROM uncontainerized_service pu, " 
+"  m_commodity m, " 
+"  uc_shifting_service c " 
+"WHERE pu.commodity                 =m.commodity_code " 
+"AND c.bl_no                        =pu.bl_no " 
+"AND c.no_ppkb                      = ? " 
+"AND (NVL(c.is_reshipping, 'FALSE') = 'TRUE' " 
+"OR NVL(c.is_landed, 'FALSE')       = 'TRUE') " 
+"AND NVL(c.is_planning, 'FALSE')    = 'FALSE'"),    
/*
    @NamedNativeQuery(name = "UcShiftingService.Native.findUcShiftingServiceWithPlan", query = "SELECT c.id,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.id_uc, pu.unit,c.shift_to,c.id_equipment,c.is_reshipping,c.id_equipment_reshipping "
                                                                                                + "FROM uncontainerized_service pu "
                                                                                                        + "JOIN m_commodity m ON (pu.commodity=m.commodity_code) "
                                                                                                        + "JOIN uc_shifting_service c ON (c.bl_no=pu.bl_no) "
                                                                                                + "WHERE c.no_ppkb = ? AND (c.is_reshipping = 'TRUE' OR c.is_landed = 'TRUE') AND c.is_planning = 'TRUE' AND c.shift_to IN ('LANDED', 'NOLANDED')"),
*/
    @NamedNativeQuery(name = "UcShiftingService.Native.findUcShiftingServiceWithPlan", query = 
"SELECT c.id, " 
+"  pu.bl_no, " 
+"  m.name, " 
+"  pu.weight, " 
+"  change(pu.truck_loosing), " 
+"  pu.id_uc, " 
+"  pu.unit, " 
+"  c.shift_to, " 
+"  c.id_equipment, " 
+"  c.is_reshipping, " 
+"  c.id_equipment_reshipping " 
+"FROM uncontainerized_service pu " 
+"JOIN m_commodity m " 
+"ON (pu.commodity=m.commodity_code) " 
+"JOIN uc_shifting_service c " 
+"ON (c.bl_no                        =pu.bl_no) " 
+"WHERE c.no_ppkb                    = ? " 
+"AND (NVL(c.is_reshipping, 'FALSE') = 'TRUE' " 
+"OR NVL(c.is_landed, 'FALSE')       = 'TRUE') " 
+"AND NVL(c.is_planning, 'FALSE')    = 'TRUE' " 
+"AND c.shift_to                    IN ('LANDED', 'NOLANDED')"),

    @NamedNativeQuery(name = "UcShiftingService.Native.findByPpkb", query = "SELECT id, bl_no FROM uc_shifting_service WHERE no_ppkb = ?"),
/*
    @NamedNativeQuery(name = "UcShiftingService.Native.findByPpkbPaid", query = "SELECT id FROM uc_shifting_service WHERE no_ppkb = ? AND is_paid = true"),
*/
    @NamedNativeQuery(name = "UcShiftingService.Native.findByPpkbPaid", query = "SELECT id FROM uc_shifting_service WHERE no_ppkb = ? AND nvl(is_paid, 'FALSE') = 'TRUE'"),
    @NamedNativeQuery(name = "UcShiftingService.Native.findByPpkbBlNo", query = "SELECT id, no_ppkb, bl_no, shift_to, operation FROM uc_shifting_service WHERE no_ppkb = ? AND bl_no = ? AND is_reshipping = ?")
})

public class UcShiftingService implements Serializable, EntityAuditable {
    public static String TYPE_TOCY = "TOCY";
    public static String TYPE_LANDED = "LANDED";
    public static String TYPE_NOLANDED = "NOLANDED";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "activity_code", length = 10)
    private String activityCode;
    @Column(name = "crane", length = 2)
    private String crane;
    @Column(name = "bl_no")
    private String blNo;
    @Column(name = "shift_to", length = 10)
    private String shiftTo;
    @Column(name = "is_reshipping")
    private String isReshipping;
    @Column(name = "operation", length = 10)
    private String operation;
    @Column(name = "is_paid")
    private String isPaid;
    @Column(name = "is_landed")
    private String isLanded;
    @Column(name = "is_planning")
    private String isPlanning;
    @Column(name = "id_equipment")
    private Integer idEquipment;
    @Column(name = "id_equipment_reshipping")
    private Integer idEquipmentReshipping;
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

    public UcShiftingService() {
    }

    public UcShiftingService(Integer id) {
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

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getShiftTo() {
        return shiftTo;
    }

    public void setShiftTo(String shiftTo) {
        this.shiftTo = shiftTo;
    }

    public String getIsReshipping() {
        return isReshipping;
    }

    public void setIsReshipping(String isReshipping) {
        this.isReshipping = isReshipping;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getIsLanded() {
        return isLanded;
    }

    public void setIsLanded(String isLanded) {
        this.isLanded = isLanded;
    }

    public String getIsPlanning() {
        return isPlanning;
    }

    public void setIsPlanning(String isPlanning) {
        this.isPlanning = isPlanning;
    }

    public Integer getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(Integer idEquipment) {
        this.idEquipment = idEquipment;
    }

    public Integer getIdEquipmentReshipping() {
        return idEquipmentReshipping;
    }

    public void setIdEquipmentReshipping(Integer idEquipmentReshipping) {
        this.idEquipmentReshipping = idEquipmentReshipping;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UcShiftingService)) {
            return false;
        }
        UcShiftingService other = (UcShiftingService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.UcShiftingService[id=" + id + "]";
    }
}
