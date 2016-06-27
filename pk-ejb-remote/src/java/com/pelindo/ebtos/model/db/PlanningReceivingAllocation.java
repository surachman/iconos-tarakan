/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterGrossClass;
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
@Table(name = "planning_receiving_allocation")
@NamedQueries({
    @NamedQuery(name = "PlanningReceivingAllocation.findAll", query = "SELECT p FROM PlanningReceivingAllocation p"),
    @NamedQuery(name = "PlanningReceivingAllocation.findById", query = "SELECT p FROM PlanningReceivingAllocation p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningReceivingAllocation.findByPortCode", query = "SELECT p FROM PlanningReceivingAllocation p WHERE p.portCode = :portCode"),
    @NamedQuery(name = "PlanningReceivingAllocation.findByAllocationConstraint", query = "SELECT p FROM PlanningReceivingAllocation p WHERE p.planningVessel.noPpkb = :noPpkb AND p.contSize = :contSize and p.masterContainerType.contType = :contType and p.contStatus = :contStatus and p.overSize = :overSize and p.dg = :dg and p.masterGrossClass.grossClass = :grossClass AND p.portCode = :portCode AND p.isExport = :isExport"),
    @NamedQuery(name = "PlanningReceivingAllocation.findByContSize", query = "SELECT p FROM PlanningReceivingAllocation p WHERE p.contSize = :contSize"),
    @NamedQuery(name = "PlanningReceivingAllocation.findByContStatus", query = "SELECT p FROM PlanningReceivingAllocation p WHERE p.contStatus = :contStatus"),
    @NamedQuery(name = "PlanningReceivingAllocation.updatePlanningVessel", query = "UPDATE PlanningReceivingAllocation p SET p.planningVessel = :newValue WHERE p.planningVessel = :oldValue"),
    @NamedQuery(name = "PlanningReceivingAllocation.findByOverSize", query = "SELECT p FROM PlanningReceivingAllocation p WHERE p.overSize = :overSize"),
    @NamedQuery(name = "PlanningReceivingAllocation.findByDg", query = "SELECT p FROM PlanningReceivingAllocation p WHERE p.dg = :dg"),
    @NamedQuery(name = "PlanningReceivingAllocation.findByTotalBooking", query = "SELECT p FROM PlanningReceivingAllocation p WHERE p.totalBooking = :totalBooking"),
    @NamedQuery(name = "PlanningReceivingAllocation.findByRealisasi", query = "SELECT p FROM PlanningReceivingAllocation p WHERE p.realisasi = :realisasi"),
    @NamedQuery(name = "PlanningReceivingAllocation.findBySisa", query = "SELECT p FROM PlanningReceivingAllocation p WHERE p.sisa = :sisa")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findPlanningReceivingAllocationsByBooking", query = "SELECT r.cont_size, c.name, "
    + "r.cont_status, r.gross_class, r.over_size, r.dg, r.block, r.fr_slot, r.to_slot, r.fr_row, r.to_row, r.id FROM planning_receiving_allocation as r, m_container_type as c"
    + " WHERE c.cont_type=r.cont_type AND r.no_ppkb = ? ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findPlanningReceivingAllocationsByMapping", query = "SELECT r.cont_size, c.name,r.cont_status, r.gross_class, r.over_size, r.dg, pp.block, pp.fr_slot, pp.to_slot, pp.fr_row, pp.to_row, r.id FROM planning_receiving_allocation as r, m_container_type as c ,planning_receiving pp WHERE c.cont_type=r.cont_type AND r.id=pp.receiving_allocation_id AND r.no_ppkb = ? ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findPlanningReceivingAllocationsByBlock", query = "SELECT r.id, r.block FROM planning_receiving_allocation as r WHERE r.block = ?"),
    //@NamedNativeQuery(name="PlanningReceivingAllocation.Native.findPlanningReceiving",query="SELECT * FROM planning_receiving_allocation r,receiving_service p where r.no_ppkb = p.no_ppkb and r.cont_size=p.cont_size and r.cont_type=p.cont_type and r.cont_status=p.cont_status and r.over_size=p.over_size and r.dg=p.dg AND r.no_ppkb=?"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findPlanningReceiving", query = "SELECT * FROM planning_receiving_allocation r where r.no_ppkb= ? AND r.cont_size=? and r.cont_type=? and r.cont_status=? and r.over_size=? and r.dg=? "),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findPlanningReceivingByValidasi", query = "SELECT r.id, r.no_ppkb, r.cont_type, r.gross_class, r.port_code, r.cont_size, r.cont_status, "
                                                                                                               + "r.over_size, r.dg, r.total_booking, r.realisasi, r.sisa, r.comodity, r.count_baplie, "
                                                                                                               + "r.allocated, r.count_baplie2, r.total_booking2, r.is_export "
                                                                                                           + "FROM planning_receiving_allocation r where r.no_ppkb= ? AND r.cont_size=? and r.cont_type=? and r.cont_status=? and r.over_size=? and r.dg=? "),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findPlanningReceivingByQueryCopy", query = "SELECT r.id, r.no_ppkb, r.cont_type, r.gross_class, r.port_code, r.cont_size, r.cont_status, "
                                                                                                               + "r.over_size, r.dg, r.total_booking, r.realisasi, r.sisa, r.comodity, r.count_baplie, "
                                                                                                               + "r.allocated, r.count_baplie2, r.total_booking2, r.is_export "
                                                                                                           + "FROM planning_receiving_allocation r "
                                                                                                           + "WHERE r.no_ppkb= ? AND r.cont_size=? and r.cont_type=? and r.cont_status=? and r.over_size=? and r.dg=? and r.gross_class=? AND r.port_code=? AND r.is_export=?"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findPlanningReceivingById", query = "SELECT id FROM planning_receiving_allocation r where r.no_ppkb= ? AND r.cont_size=? and r.cont_type=? and r.cont_status=? and r.over_size=? and r.dg=? and r.gross_class=?"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findPlanningReceivingList", query = "SELECT r.id, r.block,r.fr_slot,r.to_slot,r.fr_row,r.to_row FROM planning_receiving r, planning_receiving_allocation as p WHERE r.receiving_allocation_id=p.id AND r.receiving_allocation_id=? ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findPlanningReceivingSearchId", query = "SELECT p.id,p.no_ppkb FROM planning_receiving_allocation as p WHERE p.no_ppkb= ? ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findAllByPPKB", query = "SELECT b.name, a.cont_size, a.cont_status, c.description, null, change(a.dg), change(over_size), e.name, a.count_baplie, a.total_booking, a.allocated, a.id, null as block,case when (select id from planning_cont_receiving pr where pr.no_ppkb=a.no_ppkb AND pr.cont_size=a.cont_size AND pr.cont_status=a.cont_status AND pr.gross_class=a.gross_class AND pr.cont_type=a.cont_type AND pr.dg=a.dg AND pr.over_size=a.over_size AND pr.disch_port=a.port_code AND pr.is_export=a.is_export limit 1) is null then false else true end as generate, "
                                                                                                + "CASE WHEN a.cont_size = 40 AND nvl(pra.realized) > 0 THEN nvl(pra.realized) / 2 ELSE nvl(pra.realized) END AS realized, a.total_booking2, a.is_export, b.type_in_general "
                                                                                        + "FROM planning_receiving_allocation a "
                                                                                                + "LEFT JOIN (SELECT pya.id, COUNT(*) realized "
                                                                                                        + "FROM m_yard_coordinat myc "
                                                                                                                + "JOIN (SELECT pya.id, pya.no_ppkb, py.block, py.fr_slot, py.to_slot, py.fr_row, py.to_row "
                                                                                                                        + "FROM planning_receiving_allocation pya "
                                                                                                                        + "JOIN planning_receiving py ON (py.receiving_allocation_id=pya.id)) AS pya ON (myc.no_ppkb = pya.no_ppkb AND myc.block = pya.block AND (myc.slot BETWEEN pya.fr_slot AND pya.to_slot AND myc.row BETWEEN pya.fr_row AND pya.to_row)) "
                                                                                                        + "WHERE char_length(myc.cont_no) = 11 AND myc.status = 'exist' "
                                                                                                        + "GROUP BY pya.id) pra ON (a.id=pra.id), "
                                                                                                + "m_container_type b, m_gross_class c, m_port e "
                                                                                        + "WHERE a.cont_type=b.cont_type AND a.gross_class=c.gross_class AND a.port_code=e.port_code AND a.no_ppkb = ? ORDER BY a.id DESC"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findLastOfId", query = "SELECT MAX(id) FROM planning_receiving_allocation"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findValidasiConstraint", query = "SELECT r.id FROM planning_receiving_allocation r where r.no_ppkb= ? AND r.cont_size=? and r.cont_type=? and r.cont_status=? and r.over_size=? and r.dg=? and r.gross_class=? AND r.port_code=? AND r.is_export=? limit 1"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findDuplicateConstraint", query = "SELECT id FROM planning_receiving_allocation WHERE no_ppkb = ? AND cont_type= ? AND cont_status= ? AND gross_class= ? AND dg= ? AND over_size= ? AND port_code= ? AND is_export=?"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findAllByID", query = "SELECT b.type_in_general, a.cont_size, a.cont_status, c.description, null, change(a.dg), change(over_size), e.name, a.count_baplie, a.total_booking, id FROM planning_receiving_allocation a, m_container_type b, m_gross_class c, m_port e  WHERE a.cont_type=b.cont_type AND a.gross_class=c.gross_class AND a.port_code=e.port_code AND a.id = ?"),
    @NamedNativeQuery(name = "PlanningReceivingAllocation.Native.findAllocationBooking", query = "SELECT sum(total_booking)::INTEGER  FROM planning_receiving_allocation WHERE no_ppkb=?")})
public class PlanningReceivingAllocation implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "port_code", length = 10)
    private String portCode;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_status", length = 10)
    private String contStatus;
    @Column(name = "over_size")
    private Boolean overSize;
    @Column(name = "dg")
    private Boolean dg;
    @Column(name = "total_booking")
    private Integer totalBooking;
    @Column(name = "realisasi")
    private Short realisasi;
    @Column(name = "sisa")
    private Short sisa;
    @Column(name = "count_baplie")
    private Integer countBaplie;
    @Column(name = "count_baplie2")
    private Integer countBaplie2;
    @Column(name = "total_booking2")
    private Integer totalBooking2;
    @Column(name = "allocated")
    private Integer allocated;
    @Basic(optional = false)
    @Column(name = "is_export", nullable = false)
    private boolean isExport = false;
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
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "gross_class", referencedColumnName = "gross_class")
    @ManyToOne
    private MasterGrossClass masterGrossClass;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "comodity", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;

    public PlanningReceivingAllocation() {
    }

    public PlanningReceivingAllocation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public Short getContSize() {
        return contSize;
    }

    public void setContSize(Short contSize) {
        this.contSize = contSize;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public Boolean getOverSize() {
        return overSize;
    }

    public void setOverSize(Boolean overSize) {
        this.overSize = overSize;
    }

    public Boolean getDg() {
        return dg;
    }

    public void setDg(Boolean dg) {
        this.dg = dg;
    }

    public Integer getTotalBooking() {
        return totalBooking;
    }

    public void setTotalBooking(Integer totalBooking) {
        this.totalBooking = totalBooking;
    }

    public Short getRealisasi() {
        return realisasi;
    }

    public void setRealisasi(Short realisasi) {
        this.realisasi = realisasi;
    }

    public Short getSisa() {
        return sisa;
    }

    public void setSisa(Short sisa) {
        this.sisa = sisa;
    }

    /**
     * @return the countBaplie
     */
    public Integer getCountBaplie() {
        return countBaplie;
    }

    /**
     * @param countBaplie the countBaplie to set
     */
    public void setCountBaplie(Integer countBaplie) {
        this.countBaplie = countBaplie;
    }

    /**
     * @return the booking
     */
    public Integer getAllocated() {
        return allocated;
    }

    /**
     * @param booking the booking to set
     */
    public void setAllocated(Integer allocated) {
        this.allocated = allocated;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public MasterGrossClass getMasterGrossClass() {
        return masterGrossClass;
    }

    public void setMasterGrossClass(MasterGrossClass masterGrossClass) {
        this.masterGrossClass = masterGrossClass;
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    public Integer getCountBaplie2() {
        return countBaplie2;
    }

    public void setCountBaplie2(Integer countBaplie2) {
        this.countBaplie2 = countBaplie2;
    }

    public Integer getTotalBooking2() {
        return totalBooking2;
    }

    public void setTotalBooking2(Integer totalBooking2) {
        this.totalBooking2 = totalBooking2;
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

    public boolean getIsExport() {
        return isExport;
    }

    public void setIsExport(boolean isExport) {
        this.isExport = isExport;
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
        if (!(object instanceof PlanningReceivingAllocation)) {
            return false;
        }
        PlanningReceivingAllocation other = (PlanningReceivingAllocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningReceivingAllocation[id=" + id + "]";
    }
}
