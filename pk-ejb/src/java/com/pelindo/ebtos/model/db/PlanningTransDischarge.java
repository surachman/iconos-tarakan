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
import com.pelindo.ebtos.model.db.master.MasterPort;
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
@Table(name = "planning_trans_discharge")
@NamedQueries({
    @NamedQuery(name = "PlanningTransDischarge.findAll", query = "SELECT p FROM PlanningTransDischarge p"),
    @NamedQuery(name = "PlanningTransDischarge.findById", query = "SELECT p FROM PlanningTransDischarge p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningTransDischarge.findByContNo", query = "SELECT p FROM PlanningTransDischarge p WHERE p.contNo = :contNo"),
    @NamedQuery(name = "PlanningTransDischarge.findByNoPpkb", query = "SELECT p FROM PlanningTransDischarge p WHERE p.planningVessel1.noPpkb = :noPpkb"),
    @NamedQuery(name = "PlanningTransDischarge.findByContSize", query = "SELECT p FROM PlanningTransDischarge p WHERE p.contSize = :contSize"),
    @NamedQuery(name = "PlanningTransDischarge.findByContStatus", query = "SELECT p FROM PlanningTransDischarge p WHERE p.contStatus = :contStatus"),
    @NamedQuery(name = "PlanningTransDischarge.findByContGross", query = "SELECT p FROM PlanningTransDischarge p WHERE p.contGross = :contGross"),
    @NamedQuery(name = "PlanningTransDischarge.findBySealNo", query = "SELECT p FROM PlanningTransDischarge p WHERE p.sealNo = :sealNo"),
    @NamedQuery(name = "PlanningTransDischarge.findByDg", query = "SELECT p FROM PlanningTransDischarge p WHERE p.dg = :dg"),
    @NamedQuery(name = "PlanningTransDischarge.findByDgLabel", query = "SELECT p FROM PlanningTransDischarge p WHERE p.dgLabel = :dgLabel"),
    @NamedQuery(name = "PlanningTransDischarge.findByOverSize", query = "SELECT p FROM PlanningTransDischarge p WHERE p.overSize = :overSize"),
    @NamedQuery(name = "PlanningTransDischarge.findByTradeType", query = "SELECT p FROM PlanningTransDischarge p WHERE p.tradeType = :tradeType"),
    @NamedQuery(name = "PlanningTransDischarge.findByLoadPort", query = "SELECT p FROM PlanningTransDischarge p WHERE p.loadPort = :loadPort"),
    @NamedQuery(name = "PlanningTransDischarge.findByDischPort", query = "SELECT p FROM PlanningTransDischarge p WHERE p.dischPort = :dischPort"),
    @NamedQuery(name = "PlanningTransDischarge.findByVBay", query = "SELECT p FROM PlanningTransDischarge p WHERE p.vBay = :vBay"),
    @NamedQuery(name = "PlanningTransDischarge.findByVRow", query = "SELECT p FROM PlanningTransDischarge p WHERE p.vRow = :vRow"),
    @NamedQuery(name = "PlanningTransDischarge.findByVTier", query = "SELECT p FROM PlanningTransDischarge p WHERE p.vTier = :vTier"),
    @NamedQuery(name = "PlanningTransDischarge.findByYRow", query = "SELECT p FROM PlanningTransDischarge p WHERE p.yRow = :yRow"),
    @NamedQuery(name = "PlanningTransDischarge.findByYTier", query = "SELECT p FROM PlanningTransDischarge p WHERE p.yTier = :yTier"),
    @NamedQuery(name = "PlanningTransDischarge.findByYSlot", query = "SELECT p FROM PlanningTransDischarge p WHERE p.ySlot = :ySlot")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PlanningTransDischarge.Native.findByPpkb", query = "SELECT * FROM planning_trans_discharge p WHERE p.no_ppkb = ? ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "PlanningTransDischarge.Native.findPlanningTransDischargesByPpkb", query = "SELECT p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier FROM planning_trans_discharge p, m_container_type c WHERE p.cont_type=c.cont_type AND p.no_ppkb = ? ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "PlanningTransDischarge.Native.findPlanningTransDischargesByPpkbTranshipment", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.seal_no, mv.name, pv.voy_out, p.id FROM planning_trans_discharge p, planning_vessel v, planning_vessel lv, m_container_type c, preservice_vessel pv, m_vessel mv WHERE p.no_ppkb = v.no_ppkb AND p.new_ppkb = lv.no_ppkb AND p.cont_type = c.cont_type AND v.booking_code = pv.booking_code AND pv.vessel_code = mv.vessel_code AND p.no_ppkb = ? AND p.new_ppkb = ? ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "PlanningTransDischarge.Native.unFinishBayPlanDischargeByPPKB", query="DELETE FROM planning_trans_discharge WHERE no_ppkb=?"),
    @NamedNativeQuery(name = "PlanningTransDischarge.Native.findAllPlanningTransDischarges", query = "SELECT p.bl_no, p.cont_no, c.name as name, p.cont_status, p.cont_gross, mc.name, p.new_ppkb, mv.name, pv.voy_in, pv.voy_out, p.id, p.cont_size FROM (((((planning_trans_discharge p LEFT JOIN planning_vessel lv ON p.new_ppkb = lv.no_ppkb) LEFT JOIN preservice_vessel pv ON pv.booking_code = lv.booking_code) LEFT JOIN m_vessel mv ON pv.vessel_code = mv.vessel_code) LEFT JOIN m_container_type c ON p.cont_type = c.cont_type) LEFT JOIN m_commodity mc ON p.commodity_code = mc.commodity_code) WHERE  p.no_ppkb = ? ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "PlanningTransDischarge.Native.findBaplieByConstraint", query = "SELECT id FROM planning_trans_discharge WHERE cont_type = ? AND cont_status = ? AND gross_class = ? "
            //+ "AND commodity_code = ? "
            + "AND dg = ? AND no_ppkb = ? ORDER BY v_tier DESC, v_row DESC, v_bay ASC"),
    @NamedNativeQuery(name = "PlanningTransDischarge.Native.generateConstraintsByPPKB", query="SELECT a.cont_size, b.cont_type, a.cont_status, a.gross_class, null, a.dg, count(*) as jumlah FROM planning_trans_discharge a, m_container_type b  WHERE  a.cont_type = b.cont_type AND a.no_ppkb = ?  GROUP BY a.cont_size, b.cont_type, a.cont_status, a.gross_class, a.dg ORDER BY a.cont_size, a.cont_status"),
    @NamedNativeQuery(name = "PlanningTransDischarge.Native.findPlanningTranshipmentMonitoring", query = "select cont_no, port_of_delivery, cont_size, g.type_in_general cont, cont_status, cont_gross, b.name as commodity, e.name customer, new_ppkb, (select x.name from m_vessel x, planning_vessel y, preservice_vessel z where y.booking_code=z.booking_code and y.no_ppkb=a.new_ppkb and z.vessel_code=x.vessel_code) as vessel, block, y_slot, y_row, y_tier "
            + "from planning_trans_discharge a, m_commodity b, planning_vessel c, preservice_vessel d, m_customer e, m_container_type g "
            + "where a.commodity_code=b.commodity_code	and a.no_ppkb=c.no_ppkb and a.commodity_code=b.commodity_code and a.cont_type = g.cont_type and c.booking_code=d.booking_code and d.cust_code=e.cust_code and a.no_ppkb = ?")
    })
public class PlanningTransDischarge implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private String twentyOneFeet = "FALSE";
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "cont_no", length = 11)
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
    private String dg;
    @Column(name = "dg_label")
    private String dgLabel;
    @Column(name = "over_size")
    private String overSize;
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
    @Column(name = "y_row")
    private Short yRow;
    @Column(name = "y_tier")
    private Short yTier;
    @Column(name = "y_slot")
    private Short ySlot;
    @Column(name = "gross_class", length = 5)
    private String grossClass;
    @JoinColumn(name = "new_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel1;
    @JoinColumn(name = "block", referencedColumnName = "block")
    @ManyToOne
    private MasterYard masterYard;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @JoinColumn(name = "port_of_delivery", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort portOfDelivery;
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
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private String isExportImport = "FALSE";

    public PlanningTransDischarge() {
    }

    public PlanningTransDischarge(Integer id) {
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

    public String getDg() {
        return dg;
    }

    public void setDg(String dg) {
        this.dg = dg;
    }

    public String getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(String dgLabel) {
        this.dgLabel = dgLabel;
    }

    public String getOverSize() {
        return overSize;
    }

    public void setOverSize(String overSize) {
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

    public Short getYSlot() {
        return ySlot;
    }

    public void setYSlot(Short ySlot) {
        this.ySlot = ySlot;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public PlanningVessel getPlanningVessel1() {
        return planningVessel1;
    }

    public void setPlanningVessel1(PlanningVessel planningVessel1) {
        this.planningVessel1 = planningVessel1;
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

    public MasterPort getPortOfDelivery() {
        return portOfDelivery;
    }

    public void setPortOfDelivery(MasterPort portOfDelivery) {
        this.portOfDelivery = portOfDelivery;
    }

    public String getIsExportImport() {
        return isExportImport;
    }

    public void setIsExportImport(String isExportImport) {
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
        if (!(object instanceof PlanningTransDischarge)) {
            return false;
        }
        PlanningTransDischarge other = (PlanningTransDischarge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningTransDischarge[id=" + id + "]";
    }

    public String getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(String twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
