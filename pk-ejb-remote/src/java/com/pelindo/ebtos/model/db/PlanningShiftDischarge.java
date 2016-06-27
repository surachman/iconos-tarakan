/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "planning_shift_discharge")
@NamedQueries({
    @NamedQuery(name = "PlanningShiftDischarge.findAll", query = "SELECT p FROM PlanningShiftDischarge p"),
    @NamedQuery(name = "PlanningShiftDischarge.findById", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningShiftDischarge.findByContSize", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.contSize = :contSize"),
    @NamedQuery(name = "PlanningShiftDischarge.findByNoPpkb", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "PlanningShiftDischarge.findByContStatus", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.contStatus = :contStatus"),
    @NamedQuery(name = "PlanningShiftDischarge.findByContGross", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.contGross = :contGross"),
    @NamedQuery(name = "PlanningShiftDischarge.findBySealNo", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.sealNo = :sealNo"),
    @NamedQuery(name = "PlanningShiftDischarge.findByDg", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.dg = :dg"),
    @NamedQuery(name = "PlanningShiftDischarge.findByDgLabel", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.dgLabel = :dgLabel"),
    @NamedQuery(name = "PlanningShiftDischarge.findByPpkbAndContNo", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.planningVessel.noPpkb = :noPpkb AND p.contNo = :contNo"),
    @NamedQuery(name = "PlanningShiftDischarge.findShiftableContainer", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.planningVessel.noPpkb = :noPpkb AND p.contNo = :contNo AND p.shiftTo IN ('LANDED', 'NOLANDED')"),
    @NamedQuery(name = "PlanningShiftDischarge.findByOverSize", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.overSize = :overSize"),
    @NamedQuery(name = "PlanningShiftDischarge.findByTradeType", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.tradeType = :tradeType"),
    @NamedQuery(name = "PlanningShiftDischarge.findByLoadPort", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.loadPort = :loadPort"),
    @NamedQuery(name = "PlanningShiftDischarge.findByDischPort", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.dischPort = :dischPort"),
    @NamedQuery(name = "PlanningShiftDischarge.findByVBay", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.vBay = :vBay"),
    @NamedQuery(name = "PlanningShiftDischarge.findByVRow", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.vRow = :vRow"),
    @NamedQuery(name = "PlanningShiftDischarge.findByVTier", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.vTier = :vTier"),
    @NamedQuery(name = "PlanningShiftDischarge.findByYSlot", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.ySlot = :ySlot"),
    @NamedQuery(name = "PlanningShiftDischarge.findByYRow", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.yRow = :yRow"),
    @NamedQuery(name = "PlanningShiftDischarge.findByYTier", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.yTier = :yTier"),
    @NamedQuery(name = "PlanningShiftDischarge.findByShiftTo", query = "SELECT p FROM PlanningShiftDischarge p WHERE p.shiftTo = :shiftTo")})
    @NamedNativeQueries({
    @NamedNativeQuery(name = "PlanningShiftDischarge.Native.findPlanningShiftDischargesByPpkb", query = "SELECT p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, p.seal_no, p.v_bay, p.v_row, p.v_tier, p.id, p.cont_no FROM planning_shift_discharge as p, m_container_type as c WHERE p.cont_type=c.cont_type AND no_ppkb = ? ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "PlanningShiftDischarge.Native.findPlanningShiftDischargesByPPKB", query = "SELECT p.bl_no, p.cont_no, p.cont_size, t.type_in_general, p.cont_status, p.cont_gross, c.name, p.seal_no, p.v_bay, p.v_row, p.v_tier, mp1.name, mp2.name, p.shift_to, p.id FROM planning_shift_discharge p, m_container_type t, m_commodity c, m_port mp1, m_port mp2 WHERE p.cont_type=t.cont_type AND p.commodity_code=c.commodity_code AND p.load_port=mp1.port_code AND p.disch_port=mp2.port_code AND p.no_ppkb = ? ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "PlanningShiftDischarge.Native.unFinishBayPlanDischargeByPPKB", query="DELETE FROM planning_shift_discharge WHERE no_ppkb=?"),
    @NamedNativeQuery(name = "PlanningShiftDischarge.Native.findPlanningShiftDischargesById", query = "SELECT p.bl_no, p.cont_no, p.cont_size, t.type_in_general, p.cont_status, p.cont_gross, c.name, p.seal_no, change(p.over_size), change(p.dg), change(p.dg_label), p.v_bay, p.v_row, p.v_tier, mp1.name, mp2.name, p.shift_to, p.id FROM planning_shift_discharge p, m_container_type t, m_commodity c, m_port mp1, m_port mp2 WHERE p.cont_type=t.cont_type AND p.commodity_code=c.commodity_code AND p.load_port=mp1.port_code AND p.disch_port=mp2.port_code  AND p.id=?"),
    @NamedNativeQuery(name = "PlanningShiftDischarge.Native.generateConstraintsByPPKB", query = "SELECT a.cont_size, b.cont_type, a.cont_status, a.gross_class, null, a.dg, count(*) as jumlah FROM planning_shift_discharge a, m_container_type b  WHERE  a.cont_type = b.cont_type AND a.no_ppkb = ? AND a.shift_to = 'TOCY' GROUP BY a.cont_size, b.cont_type, a.cont_status, a.gross_class, a.dg ORDER BY a.cont_size, a.cont_status"),
    @NamedNativeQuery(name = "PlanningShiftDischarge.Native.findBaplieByConstraint", query = "SELECT id FROM planning_shift_discharge WHERE cont_type = ? AND cont_status = ? AND gross_class = ? AND dg = ? AND no_ppkb = ? ORDER BY v_tier DESC, v_row DESC, v_bay ASC")
    })
public class PlanningShiftDischarge implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_status", length = 5)
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "dg")
    private Boolean dg;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Column(name = "over_size")
    private Boolean overSize;
    @Column(name = "trade_type", length = 3)
    private String tradeType;
    @Column(name = "load_port", length = 10)
    private String loadPort;
    @Column(name = "disch_port", length = 10)
    private String dischPort;
    @Column(name = "v_bay")
    private Short vBay;
    @Column(name = "v_row")
    private Short vRow;
    @Column(name = "v_tier")
    private Short vTier;
    @Column(name = "y_slot")
    private Short ySlot;
    @Column(name = "y_row")
    private Short yRow;
    @Column(name = "y_tier")
    private Short yTier;
    @Basic(optional = false)
    @Column(name = "shift_to", nullable = false, length = 10)
    private String shiftTo;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "block", referencedColumnName = "block")
    @ManyToOne
    private MasterYard masterYard;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @Column(name = "gross_class", length = 5)
    private String grossClass;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private boolean isExportImport = false;
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
    @Column(name = "bl_no")
    private String blNo;

    public PlanningShiftDischarge() {
    }

    public PlanningShiftDischarge(Integer id) {
        this.id = id;
    }

    public PlanningShiftDischarge(Integer id, String shiftTo) {
        this.id = id;
        this.shiftTo = shiftTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the contNo
     */
    public String getContNo() {
        return contNo;
    }

    /**
     * @param contNo the contNo to set
     */
    public void setContNo(String contNo) {
        this.contNo = contNo;
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

    public Integer getContGross() {
        return contGross;
    }

    public void setContGross(Integer contGross) {
        this.contGross = contGross;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public Boolean getDg() {
        return dg;
    }

    public void setDg(Boolean dg) {
        this.dg = dg;
    }

    public Boolean getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(Boolean dgLabel) {
        this.dgLabel = dgLabel;
    }

    public Boolean getOverSize() {
        return overSize;
    }

    public void setOverSize(Boolean overSize) {
        this.overSize = overSize;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getLoadPort() {
        return loadPort;
    }

    public void setLoadPort(String loadPort) {
        this.loadPort = loadPort;
    }

    public String getDischPort() {
        return dischPort;
    }

    public void setDischPort(String dischPort) {
        this.dischPort = dischPort;
    }

    public Short getVBay() {
        return vBay;
    }

    public void setVBay(Short vBay) {
        this.vBay = vBay;
    }

    public Short getVRow() {
        return vRow;
    }

    public void setVRow(Short vRow) {
        this.vRow = vRow;
    }

    public Short getVTier() {
        return vTier;
    }

    public void setVTier(Short vTier) {
        this.vTier = vTier;
    }

    public Short getYSlot() {
        return ySlot;
    }

    public void setYSlot(Short ySlot) {
        this.ySlot = ySlot;
    }

    public Short getYRow() {
        return yRow;
    }

    public void setYRow(Short yRow) {
        this.yRow = yRow;
    }

    public Short getYTier() {
        return yTier;
    }

    public void setYTier(Short yTier) {
        this.yTier = yTier;
    }

    public String getShiftTo() {
        return shiftTo;
    }

    public void setShiftTo(String shiftTo) {
        this.shiftTo = shiftTo;
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

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
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
    public String getGrossClass() {
        return grossClass;
    }

    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
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

    public boolean getIsExportImport() {
        return isExportImport;
    }

    public void setIsExportImport(boolean isExportImport) {
        this.isExportImport = isExportImport;
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
        if (!(object instanceof PlanningShiftDischarge)) {
            return false;
        }
        PlanningShiftDischarge other = (PlanningShiftDischarge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningShiftDischarge[id=" + id + "]";
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
