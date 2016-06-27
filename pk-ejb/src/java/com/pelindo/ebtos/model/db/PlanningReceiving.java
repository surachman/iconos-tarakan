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
@Table(name = "planning_receiving")
@NamedQueries({
    @NamedQuery(name = "PlanningReceiving.findAll", query = "SELECT p FROM PlanningReceiving p"),
    @NamedQuery(name = "PlanningReceiving.findById", query = "SELECT p FROM PlanningReceiving p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningReceiving.findByBlock", query = "SELECT p FROM PlanningReceiving p WHERE p.block = :block"),
    @NamedQuery(name = "PlanningReceiving.findByReceivingAllocationId", query = "SELECT p FROM PlanningReceiving p WHERE p.receivingAllocationId = :receivingAllocationId"),
    @NamedQuery(name = "PlanningReceiving.findByFrSlot", query = "SELECT p FROM PlanningReceiving p WHERE p.frSlot = :frSlot"),
    @NamedQuery(name = "PlanningReceiving.findByToSlot", query = "SELECT p FROM PlanningReceiving p WHERE p.toSlot = :toSlot"),
    @NamedQuery(name = "PlanningReceiving.findByFrRow", query = "SELECT p FROM PlanningReceiving p WHERE p.frRow = :frRow"),
    @NamedQuery(name = "PlanningReceiving.findByToRow", query = "SELECT p FROM PlanningReceiving p WHERE p.toRow = :toRow"),
    @NamedQuery(name = "PlanningReceiving.findByCountAllocation", query = "SELECT p FROM PlanningReceiving p WHERE p.countAllocation = :countAllocation")})

    @NamedNativeQueries({
    @NamedNativeQuery(name = "PlanningReceiving.Native.findPlanningReceivingList", query="SELECT r.id, r.block,r.fr_slot,r.to_slot,r.fr_row,r.to_row,r.receiving_allocation_id FROM planning_receiving r, planning_receiving_allocation p WHERE r.receiving_allocation_id=p.id AND r.receiving_allocation_id=? ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "PlanningReceiving.Native.findPlanningReceivingAbmilIdContraint", query="SELECT r.id, r.block,r.fr_slot,r.to_slot,r.fr_row,r.to_row,r.receiving_allocation_id FROM planning_receiving r, planning_receiving_allocation p WHERE r.receiving_allocation_id=p.id AND r.receiving_allocation_id=?"),
    @NamedNativeQuery(name = "PlanningReceiving.Native.findAllIdByIdReceivingAllocation", query="SELECT id FROM planning_receiving WHERE receiving_allocation_id=?"),
    @NamedNativeQuery(name = "PlanningReceiving.Native.findAllByIdReceivingAllocation", query="SELECT block, fr_slot, to_slot, fr_row, to_row, count_allocation, id FROM planning_receiving WHERE receiving_allocation_id =? ORDER BY block"),
    @NamedNativeQuery(name = "PlanningReceiving.Native.findLastOfId", query="SELECT MAX(id) FROM planning_receiving"),
/*    
    @NamedNativeQuery(name = "PlanningReceiving.Native.findRecommendedLocation", query="SELECT myc.cont_no, myc.block, myc.slot, myc.row, myc.tier, pya.fr_row, pya.to_row, CASE WHEN myc.tier <= 3 THEN 0 ELSE -1 END AS ordering "
                                                                                    + "FROM "
                                                                                        + "(SELECT pya.id, pya.no_ppkb, py.block, py.fr_slot, py.to_slot, py.fr_row, py.to_row, pya.port_code, pya.cont_type, pya.gross_class, pya.cont_size, pya.cont_status, pya.over_size, pya.dg, pya.is_export, mct.type_in_general "
                                                                                        + "FROM planning_receiving_allocation pya "
                                                                                            + "JOIN planning_receiving py ON (py.receiving_allocation_id=pya.id) "
                                                                                            + "JOIN m_container_type mct ON (pya.cont_type=mct.cont_type) "
                                                                                        + "WHERE pya.no_ppkb = ?) AS pya "
                                                                                                + "JOIN m_yard_coordinat myc ON (myc.block = pya.block AND (myc.slot BETWEEN pya.fr_slot AND pya.to_slot AND myc.row BETWEEN pya.fr_row AND pya.to_row)) "
                                                                                    + "WHERE (char_length(myc.cont_no) > 11) AND pya.port_code = ? AND pya.type_in_general = (SELECT type_in_general FROM m_container_type WHERE cont_type = ?) AND pya.gross_class = ? AND pya.cont_size = ? AND pya.cont_status = ? AND pya.over_size = ? AND pya.dg = ? AND pya.is_export = ? "
                                                                                    + "ORDER BY myc.block ASC, ordering DESC, myc.slot ASC, myc.tier ASC, myc.row ASC "
                                                                                    + "LIMIT 1;")
*/
    @NamedNativeQuery(name = "PlanningReceiving.Native.findRecommendedLocation", query=
"SELECT myc.cont_no, " 
+"  myc.block, " 
+"  myc.slot, " 
+"  myc.\"ROW\", " 
+"  myc.tier, " 
+"  pya.fr_row, " 
+"  pya.to_row, " 
+"  CASE " 
+"    WHEN myc.tier <= 3 " 
+"    THEN 0 " 
+"    ELSE -1 " 
+"  END AS ordering " 
+"FROM " 
+"  (SELECT pya.id, " 
+"    pya.no_ppkb, " 
+"    py.block, " 
+"    py.fr_slot, " 
+"    py.to_slot, " 
+"    py.fr_row, " 
+"    py.to_row, " 
+"    pya.port_code, " 
+"    pya.cont_type, " 
+"    pya.gross_class, " 
+"    pya.cont_size, " 
+"    pya.cont_status, " 
+"    pya.over_size, " 
+"    pya.dg, " 
+"    pya.is_export, " 
+"    mct.type_in_general " 
+"  FROM planning_receiving_allocation pya " 
+"  JOIN planning_receiving py " 
+"  ON (py.receiving_allocation_id=pya.id) " 
+"  JOIN m_container_type mct " 
+"  ON (pya.cont_type =mct.cont_type) " 
+"  WHERE pya.no_ppkb = ? " 
+"  ) pya " 
+"JOIN m_yard_coordinat myc " 
+"ON (myc.block = pya.block " 
+"AND (myc.slot BETWEEN pya.fr_slot AND pya.to_slot " 
+"AND myc.\"ROW\" BETWEEN pya.fr_row AND pya.to_row)) " 
+"WHERE (LENGTH(myc.cont_no) > 11) " 
+"AND pya.port_code          = ? " 
+"AND pya.type_in_general    = " 
+"  (SELECT type_in_general FROM m_container_type WHERE cont_type = ? " 
+"  ) " 
+"AND pya.gross_class = ? " 
+"AND pya.cont_size   = ? " 
+"AND pya.cont_status = ? " 
+"AND pya.over_size   = ? " 
+"AND pya.dg          = ? " 
+"AND pya.is_export   = ? " 
+"AND ROWNUM          < 2 " 
+"ORDER BY myc.block ASC, " 
+"  ordering DESC, " 
+"  myc.slot ASC, " 
+"  myc.tier ASC, " 
+"  myc.\"ROW\" ASC")
    })


public class PlanningReceiving implements Serializable, EntityAuditable {
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
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "block", length = 5)
    private String block;
    @Column(name = "receiving_allocation_id")
    private Integer receivingAllocationId;
    @Column(name = "fr_slot")
    private Short frSlot = 1;
    @Column(name = "to_slot")
    private Short toSlot = 1;
    @Column(name = "fr_row")
    private Short frRow = 1;
    @Column(name = "to_row")
    private Short toRow = 1;
    @Column(name = "count_allocation")
    private Integer countAllocation;

    public PlanningReceiving() {
    }

    public PlanningReceiving(Integer id) {
        this.id = id;
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

    public Integer getReceivingAllocationId() {
        return receivingAllocationId;
    }

    public void setReceivingAllocationId(Integer receivingAllocationId) {
        this.receivingAllocationId = receivingAllocationId;
    }

    public Short getFrSlot() {
        return frSlot;
    }

    public void setFrSlot(Short frSlot) {
        this.frSlot = frSlot;
    }

    public Short getToSlot() {
        return toSlot;
    }

    public void setToSlot(Short toSlot) {
        this.toSlot = toSlot;
    }

    public Short getFrRow() {
        return frRow;
    }

    public void setFrRow(Short frRow) {
        this.frRow = frRow;
    }

    public Short getToRow() {
        return toRow;
    }

    public void setToRow(Short toRow) {
        this.toRow = toRow;
    }

    public Integer getCountAllocation() {
        return countAllocation;
    }

    public void setCountAllocation(Integer countAllocation) {
        this.countAllocation = countAllocation;
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
        if (!(object instanceof PlanningReceiving)) {
            return false;
        }
        PlanningReceiving other = (PlanningReceiving) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningReceiving[id=" + id + "]";
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
