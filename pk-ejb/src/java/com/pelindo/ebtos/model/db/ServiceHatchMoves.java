/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import java.util.Date;
import javax.persistence.EntityListeners;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "service_hatch_moves")
@NamedQueries({
    @NamedQuery(name = "ServiceHatchMoves.findAll", query = "SELECT s FROM ServiceHatchMoves s"),
    @NamedQuery(name = "ServiceHatchMoves.findById", query = "SELECT s FROM ServiceHatchMoves s WHERE s.id = :id"),
//    @NamedQuery(name = "ServiceHatchMoves.findByNoPpkbAndStatusPaid", query = "SELECT s FROM ServiceHatchMoves s WHERE s.isPaid = 'TRUE' AND s.noPpkb = :noPpkb AND s.operation = 'DISCHARGE'"),
    @NamedQuery(name = "ServiceHatchMoves.findByNoPpkbAndStatusPaid", query = "SELECT s FROM ServiceHatchMoves s WHERE s.isPaid = 'TRUE' AND s.noPpkb = :noPpkb"),
    @NamedQuery(name = "ServiceHatchMoves.findByBay", query = "SELECT s FROM ServiceHatchMoves s WHERE s.bay = :bay"),
    @NamedQuery(name = "ServiceHatchMoves.findByOperationBayAndHatchMoves", query = "SELECT s FROM ServiceHatchMoves s WHERE s.bay = :bay AND s.operation = :operation AND s.hatchMoves = :hatchMoves AND s.noPpkb = :noPpkb"),
    @NamedQuery(name = "ServiceHatchMoves.findByOperation", query = "SELECT s FROM ServiceHatchMoves s WHERE s.operation = :operation"),
    @NamedQuery(name = "ServiceHatchMoves.findByHatchMoves", query = "SELECT s FROM ServiceHatchMoves s WHERE s.hatchMoves = :hatchMoves"),
    @NamedQuery(name = "ServiceHatchMoves.findByCrane", query = "SELECT s FROM ServiceHatchMoves s WHERE s.crane = :crane")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceHatchMoves.Native.findServiceHatchMovesByPpkb", query = "SELECT pcl.bay, pcl.crane, pcl.operation,  pcl.hatch_moves, e.equip_code, e.start_time, e.end_time,pcl.id,e.id FROM service_hatch_moves pcl, equipment e WHERE pcl.id_equipment=e.id AND pcl.no_ppkb=? ORDER BY pcl.id DESC"),
/*    
    @NamedNativeQuery(name = "ServiceHatchMoves.Native.findServiceHatchMovesByPpkbRecap", query = "SELECT pcl.bay, pcl.crane, pcl.operation,  pcl.hatch_moves, e.equip_code, e.start_time, e.end_time,pcl.id,e.id,pcl.is_paid FROM service_hatch_moves pcl, equipment e WHERE pcl.id_equipment=e.id AND pcl.is_paid='true' AND pcl.no_ppkb=? ORDER BY pcl.id DESC"),
*/
    @NamedNativeQuery(name = "ServiceHatchMoves.Native.findServiceHatchMovesByPpkbRecap", query = 
"SELECT pcl.bay, " 
+"  pcl.crane, " 
+"  pcl.operation, " 
+"  pcl.hatch_moves, " 
+"  e.equip_code, " 
+"  e.start_time, " 
+"  e.end_time, " 
+"  pcl.id, " 
+"  e.id, " 
+"  pcl.is_paid " 
+"FROM service_hatch_moves pcl, " 
+"  equipment e " 
+"WHERE pcl.id_equipment=e.id " 
+"AND pcl.is_paid       ='TRUE' " 
+"AND pcl.no_ppkb       =? " 
+"ORDER BY pcl.id DESC"),
    
    @NamedNativeQuery(name = "ServiceHatchMoves.Native.findServiceHatchMovesByPpkbDischarge", query = "SELECT pcl.bay, pcl.crane, pcl.operation,  pcl.hatch_moves, e.equip_code, e.start_time, e.end_time,pcl.id,e.id FROM service_hatch_moves pcl, equipment e WHERE pcl.id_equipment=e.id AND pcl.no_ppkb=? AND pcl.operation='DISCHARGE' ORDER BY pcl.id DESC"),
    
    @NamedNativeQuery(name = "ServiceHatchMoves.Native.findServiceHatchMovesByPpkbLoad", query = "SELECT pcl.bay, pcl.crane, pcl.operation,  pcl.hatch_moves, e.equip_code, e.start_time, e.end_time,pcl.id,e.id FROM service_hatch_moves pcl, equipment e WHERE pcl.id_equipment=e.id AND pcl.no_ppkb=? AND pcl.operation='LOAD' ORDER BY pcl.id DESC"),
/*    
    @NamedNativeQuery(name = "ServiceHatchMoves.Native.findServiceHatchMovesByPpkbOperationDischarge", query = "SELECT pcl.bay, pcl.crane, m.description,pcl.operation, e.equip_code, e.start_time, e.end_time,pcl.id,e.id FROM service_hatch_moves pcl, m_activity m,equipment e WHERE pcl.id_equipment=e.id AND pcl.activity_code=m.activity_code AND pcl.no_ppkb=? AND pcl.operation = ? AND pcl.is_paid='true' ORDER BY pcl.id DESC"),
*/
    @NamedNativeQuery(name = "ServiceHatchMoves.Native.findServiceHatchMovesByPpkbOperationDischarge", query = 
"SELECT pcl.bay, " 
+"  pcl.crane, " 
+"  m.description, " 
+"  pcl.operation, " 
+"  e.equip_code, " 
+"  e.start_time, " 
+"  e.end_time, " 
+"  pcl.id, " 
+"  e.id " 
+"FROM service_hatch_moves pcl, " 
+"  m_activity m, " 
+"  equipment e " 
+"WHERE pcl.id_equipment=e.id " 
+"AND pcl.activity_code =m.activity_code " 
+"AND pcl.no_ppkb       = ? " 
+"AND pcl.operation     = ? " 
+"AND pcl.is_paid       ='TRUE' " 
+"ORDER BY pcl.id DESC"),

/*
    @NamedNativeQuery(name = "ServiceHatchMoves.Native.findServiceHatchMovesByPpkbOperationLoad", query = "SELECT pcl.bay, pcl.crane, m.description,pcl.operation, e.equip_code, e.start_time, e.end_time,pcl.id,e.id FROM service_hatch_moves pcl, m_activity m,equipment e WHERE pcl.id_equipment=e.id AND pcl.activity_code=m.activity_code AND pcl.no_ppkb=? AND pcl.operation = ? AND pcl.is_paid='true' ORDER BY pcl.id DESC"),
*/
    @NamedNativeQuery(name = "ServiceHatchMoves.Native.findServiceHatchMovesByPpkbOperationLoad", query = 
"SELECT pcl.bay, " 
+"  pcl.crane, " 
+"  m.description, " 
+"  pcl.operation, " 
+"  e.equip_code, " 
+"  e.start_time, " 
+"  e.end_time, " 
+"  pcl.id, " 
+"  e.id " 
+"FROM service_hatch_moves pcl, " 
+"  m_activity m, " 
+"  equipment e " 
+"WHERE pcl.id_equipment=e.id " 
+"AND pcl.activity_code =m.activity_code " 
+"AND pcl.no_ppkb       =? " 
+"AND pcl.operation     = ? " 
+"AND pcl.is_paid       ='TRUE' " 
+"ORDER BY pcl.id DESC"),

/*
    @NamedNativeQuery(name = "ServiceHatchMoves.Native.findServiceHatchMovesByPpkbOperation", query = "SELECT pcl.bay, pcl.crane, m.description,pcl.operation, e.equip_code, e.start_time, e.end_time,pcl.id,e.id FROM service_hatch_moves pcl, m_activity m,equipment e WHERE pcl.id_equipment=e.id AND pcl.activity_code=m.activity_code AND pcl.no_ppkb=? AND pcl.is_paid='true' ORDER BY pcl.id DESC")
*/
    @NamedNativeQuery(name = "ServiceHatchMoves.Native.findServiceHatchMovesByPpkbOperation", query = 
"SELECT pcl.bay, " 
+"  pcl.crane, " 
+"  m.description, " 
+"  pcl.operation, " 
+"  e.equip_code, " 
+"  e.start_time, " 
+"  e.end_time, " 
+"  pcl.id, " 
+"  e.id " 
+"FROM service_hatch_moves pcl, " 
+"  m_activity m, " 
+"  equipment e " 
+"WHERE pcl.id_equipment=e.id " 
+"AND pcl.activity_code =m.activity_code " 
+"AND pcl.no_ppkb       = ? " 
+"AND pcl.is_paid       ='TRUE' " 
+"ORDER BY pcl.id DESC")    
    })
public class ServiceHatchMoves implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "bay")
    private Short bay;
    @Column(name = "operation", length = 10)
    private String operation;
    @Column(name = "hatch_moves", length = 10)
    private String hatchMoves;
    @Column(name = "crane", length = 2)
    private String crane;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "is_paid")
    private String isPaid;
    @Column(name = "id_equipment")
    private Integer idEquipment;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;
    @Column(name = "hatch_move_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date hatchMoveDate;
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


    public ServiceHatchMoves() {
    }

    public ServiceHatchMoves(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getBay() {
        return bay;
    }

    public void setBay(Short bay) {
        this.bay = bay;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getHatchMoves() {
        return hatchMoves;
    }

    public void setHatchMoves(String hatchMoves) {
        this.hatchMoves = hatchMoves;
    }

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    /**
     * @return the noPpkb
     */
    public String getNoPpkb() {
        return noPpkb;
    }

    /**
     * @param noPpkb the noPpkb to set
     */
    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    /**
     * @return the isPaid
     */
    public String getIsPaid() {
        return isPaid;
    }

    /**
     * @param isPaid the isPaid to set
     */
    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
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
        if (!(object instanceof ServiceHatchMoves)) {
            return false;
        }
        ServiceHatchMoves other = (ServiceHatchMoves) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceHatchMoves[id=" + id + "]";
    }

    /**
     * @return the idEquipment
     */
    public Integer getIdEquipment() {
        return idEquipment;
    }

    /**
     * @param idEquipment the idEquipment to set
     */
    public void setIdEquipment(Integer idEquipment) {
        this.idEquipment = idEquipment;
    }

    /**
     * @return the masterActivity
     */
    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    /**
     * @param masterActivity the masterActivity to set
     */
    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }

    public Date getHatchMoveDate() {
        return hatchMoveDate;
    }

    public void setHatchMoveDate(Date hatchMoveDate) {
        this.hatchMoveDate = hatchMoveDate;
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
