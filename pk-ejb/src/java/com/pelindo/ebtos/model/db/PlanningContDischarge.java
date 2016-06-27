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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "planning_cont_discharge")
@NamedQueries({
    @NamedQuery(name = "PlanningContDischarge.findAll", query = "SELECT p FROM PlanningContDischarge p"),
    @NamedQuery(name = "PlanningContDischarge.findById", query = "SELECT p FROM PlanningContDischarge p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningContDischarge.findByContNo", query = "SELECT p FROM PlanningContDischarge p WHERE p.contNo = :contNo"),
    @NamedQuery(name = "PlanningContDischarge.findByNoPpkb", query = "SELECT p FROM PlanningContDischarge p WHERE p.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "PlanningContDischarge.deleteByContNoAndNoPpkb", query = "DELETE FROM PlanningContDischarge p WHERE p.planningVessel.noPpkb = :noPpkb AND p.contNo = :contNo"),
    @NamedQuery(name = "PlanningContDischarge.findByContNoAndNoPpkb", query = "SELECT p FROM PlanningContDischarge p WHERE p.planningVessel.noPpkb = :noPpkb AND p.contNo = :contNo"),
    @NamedQuery(name = "PlanningContDischarge.findByContSize", query = "SELECT p FROM PlanningContDischarge p WHERE p.contSize = :contSize"),
    @NamedQuery(name = "PlanningContDischarge.findByContStatus", query = "SELECT p FROM PlanningContDischarge p WHERE p.contStatus = :contStatus"),
    @NamedQuery(name = "PlanningContDischarge.findByContGross", query = "SELECT p FROM PlanningContDischarge p WHERE p.contGross = :contGross"),
    @NamedQuery(name = "PlanningContDischarge.findBySealNo", query = "SELECT p FROM PlanningContDischarge p WHERE p.sealNo = :sealNo"),
    @NamedQuery(name = "PlanningContDischarge.findByDg", query = "SELECT p FROM PlanningContDischarge p WHERE p.dg = :dg"),
    @NamedQuery(name = "PlanningContDischarge.findByDgLabel", query = "SELECT p FROM PlanningContDischarge p WHERE p.dgLabel = :dgLabel"),
    @NamedQuery(name = "PlanningContDischarge.findByOverSize", query = "SELECT p FROM PlanningContDischarge p WHERE p.overSize = :overSize"),
    @NamedQuery(name = "PlanningContDischarge.findByTradeType", query = "SELECT p FROM PlanningContDischarge p WHERE p.tradeType = :tradeType"),
    @NamedQuery(name = "PlanningContDischarge.findByLoadPort", query = "SELECT p FROM PlanningContDischarge p WHERE p.loadPort = :loadPort"),
    @NamedQuery(name = "PlanningContDischarge.findByDischPort", query = "SELECT p FROM PlanningContDischarge p WHERE p.dischPort = :dischPort"),
    @NamedQuery(name = "PlanningContDischarge.findByVBay", query = "SELECT p FROM PlanningContDischarge p WHERE p.vBay = :vBay"),
    @NamedQuery(name = "PlanningContDischarge.findByVRow", query = "SELECT p FROM PlanningContDischarge p WHERE p.vRow = :vRow"),
    @NamedQuery(name = "PlanningContDischarge.findByVTier", query = "SELECT p FROM PlanningContDischarge p WHERE p.vTier = :vTier"),
    @NamedQuery(name = "PlanningContDischarge.findByYSlot", query = "SELECT p FROM PlanningContDischarge p WHERE p.ySlot = :ySlot"),
    @NamedQuery(name = "PlanningContDischarge.findByYRow", query = "SELECT p FROM PlanningContDischarge p WHERE p.yRow = :yRow"),
    @NamedQuery(name = "PlanningContDischarge.findByYTier", query = "SELECT p FROM PlanningContDischarge p WHERE p.yTier = :yTier")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PlanningContDischarge.Native.findPlanningContDischargesByPpkb", query = "SELECT p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier FROM planning_cont_discharge p, m_container_type c WHERE p.cont_type=c.cont_type AND no_ppkb = ? ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "PlanningContDischarge.Native.findPlanningContDischarges", query = "SELECT r.cont_no, r.cont_size, r.cont_type, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,change(r.over_size),change(r.dg),r.id from planning_cont_discharge r ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "PlanningContDischarge.Native.findPlanningContDischargesByPPKB", query = "SELECT * FROM planning_cont_discharge WHERE no_ppkb = ? ORDER BY id DESC"),
    @NamedNativeQuery(name = "PlanningContDischarge.Native.findBaplieByConstraint", query = "SELECT id "
                                                                                            + "FROM planning_cont_discharge "
                                                                                            + "WHERE cont_type = ? AND cont_status = ? AND gross_class = ? AND dg = ? AND over_size = ? AND is_import = ? AND no_ppkb = ? ORDER BY v_tier DESC, v_row DESC, v_bay ASC"),
    @NamedNativeQuery(name = "PlanningContDischarge.Native.findContOnBay", query = "SELECT cont_no, cont_size, cont_gross FROM planning_cont_discharge WHERE no_ppkb=? AND v_bay=? AND v_row=? AND v_tier=?"),

/*
    @NamedNativeQuery(name = "PlanningContDischarge.Native.findIdGenerateByPpkb", query = "SELECT pcd.id, pcd.id_coordinat, pcd.id_yard_allocation, ya.yard_allocation_filter FROM planning_cont_discharge AS pcd, yard_allocation ya WHERE pcd.id_yard_allocation=ya.id  AND no_ppkb = ? AND ya.yard_allocation_filter = ? ORDER BY ya.yard_allocation_filter"),
*/
    @NamedNativeQuery(name = "PlanningContDischarge.Native.findIdGenerateByPpkb", query = 
"SELECT pcd.id, " 
+"  pcd.id_coordinat, " 
+"  pcd.id_yard_allocation, " 
+"  ya.yard_allocation_filter " 
+"FROM planning_cont_discharge pcd, " 
+"  yard_allocation ya " 
+"WHERE pcd.id_yard_allocation  =ya.id " 
+"AND no_ppkb                   = ? " 
+"AND ya.yard_allocation_filter = ? " 
+"ORDER BY ya.yard_allocation_filter"),
    
    @NamedNativeQuery(name = "PlanningContDischarge.Native.countByPPKB", query = "SELECT COUNT(*) FROM planning_cont_discharge WHERE no_ppkb = ?"),
    @NamedNativeQuery(name = "PlanningContDischarge.Native.generateConstraintsByPPKB", query="SELECT a.cont_size, b.cont_type, a.cont_status, a.gross_class, null, a.dg, count(*) as jumlah, a.is_import, a.over_size "
                                                                                            + "FROM planning_cont_discharge a "
                                                                                                + "JOIN m_container_type b ON (a.cont_type = b.cont_type) "
                                                                                            + "WHERE a.no_ppkb = ? GROUP BY a.cont_size, b.cont_type, a.cont_status, a.gross_class, a.dg, a.is_import, a.over_size ORDER BY a.cont_size, a.cont_status"),
    @NamedNativeQuery(name = "PlanningContDischarge.Native.unFinishBayPlanDischargeByPPKB", query="DELETE FROM planning_cont_discharge WHERE no_ppkb=?"),
/*    
    @NamedNativeQuery(name = "PlanningContDischarge.Native.monitoringContainer", query = "SELECT pcd.id, pcd.cont_no, pcd.no_ppkb, v.name vessel_name, pcd.created_date waktu_daftar, e.operation status, eq.name cc_code, e.start_time cc_time, eq2.name ht_code, e2.start_time ht_time, eq3.name lo_code, e3.start_time lo_time, ds.created_date billing, sgd.date_in gate_in, eq4.name dlv_code, e4.start_time dlv_time, sgd.date_out gate_out, i.no_invoice, i.created_by as user, ct.type_in_general, pcd.cont_size, pcd.cont_status, (SELECT mc.name FROM m_customer mc, registration r WHERE ds.no_reg::TEXT = r.no_reg AND mc.cust_code = r.cust_code) as cust FROM ((((((planning_cont_discharge pcd LEFT JOIN (equipment e LEFT JOIN m_equipment eq ON eq.equip_code = e.equip_code) ON pcd.cont_no = e.cont_no AND pcd.no_ppkb = e.no_ppkb AND e.equipment_act_code = 'STEVEDORING') LEFT JOIN (equipment e2 LEFT JOIN m_equipment eq2 ON eq2.equip_code = e2.equip_code) ON pcd.cont_no = e2.cont_no AND pcd.no_ppkb = e2.no_ppkb AND e2.equipment_act_code = 'H/T') LEFT JOIN (equipment e3 LEFT JOIN m_equipment eq3 ON eq3.equip_code = e3.equip_code) ON pcd.cont_no = e3.cont_no AND pcd.no_ppkb = e3.no_ppkb AND e3.equipment_act_code = 'LIFTOFF') LEFT JOIN delivery_service ds ON pcd.cont_no = ds.cont_no AND pcd.no_ppkb = ds.no_ppkb) LEFT JOIN invoice i ON i.no_reg = ds.no_reg) LEFT JOIN service_gate_delivery sgd ON pcd.cont_no = sgd.cont_no AND pcd.no_ppkb = sgd.no_ppkb) LEFT JOIN (equipment e4 LEFT JOIN m_equipment eq4 ON eq4.equip_code = e4.equip_code) ON pcd.cont_no = e4.cont_no AND pcd.no_ppkb = e4.no_ppkb AND e4.equipment_act_code = 'DELIVERY', planning_vessel pv, m_vessel v, m_container_type ct WHERE pcd.cont_type = ct.cont_type AND pcd.no_ppkb = pv.no_ppkb AND pv.vessel_code = v.vessel_code AND pcd.cont_no = ?"
                                                                                       + " UNION "
                                                                                       + "SELECT pcd.id, pcd.cont_no, pcd.no_ppkb, v.name vessel_name, pcd.created_date waktu_daftar, e.operation status, eq.name cc_code, e.start_time cc_time, eq2.name ht_code, e2.start_time ht_time, eq3.name lo_code, e3.start_time lo_time, ds.created_date billing, sgd.date_in gate_in, eq4.name dlv_code, e4.start_time dlv_time, sgd.date_out gate_out, i.no_invoice, i.created_by as user, ct.type_in_general, pcd.cont_size, pcd.cont_status, (SELECT mc.name FROM m_customer mc, registration r WHERE ds.no_reg::TEXT = r.no_reg AND mc.cust_code = r.cust_code) as cust FROM ((((((planning_cont_receiving pcd LEFT JOIN receiving_service ds ON pcd.cont_no = ds.cont_no AND pcd.no_ppkb = ds.no_ppkb) LEFT JOIN invoice i ON i.no_reg = ds.no_reg) LEFT JOIN service_gate_receiving sgd ON pcd.cont_no = sgd.cont_no AND pcd.no_ppkb = sgd.no_ppkb) LEFT JOIN (equipment e4 LEFT JOIN m_equipment eq4 ON eq4.equip_code = e4.equip_code) ON pcd.cont_no = e4.cont_no AND pcd.no_ppkb = e4.no_ppkb_recv AND e4.equipment_act_code = 'RECEIVING') LEFT JOIN (equipment e3 LEFT JOIN m_equipment eq3 ON eq3.equip_code = e3.equip_code) ON pcd.cont_no = e3.cont_no AND pcd.no_ppkb = e3.no_ppkb AND e3.equipment_act_code = 'LIFTON') LEFT JOIN (equipment e2 LEFT JOIN m_equipment eq2 ON eq2.equip_code = e2.equip_code) ON pcd.cont_no = e2.cont_no AND pcd.no_ppkb = e2.no_ppkb AND e2.equipment_act_code = 'H/T') LEFT JOIN (equipment e LEFT JOIN m_equipment eq ON eq.equip_code = e.equip_code) ON pcd.cont_no = e.cont_no AND pcd.no_ppkb = e.no_ppkb AND e.equipment_act_code = 'STEVEDORING', planning_vessel pv, m_vessel v, m_container_type ct WHERE pcd.cont_type = ct.cont_type AND pcd.no_ppkb = pv.no_ppkb AND pv.vessel_code = v.vessel_code AND pcd.cont_no = ?")
*/
    @NamedNativeQuery(name = "PlanningContDischarge.Native.monitoringContainer", query = 
"SELECT pcd.id, " 
+"  pcd.cont_no, " 
+"  pcd.no_ppkb, " 
+"  v.name vessel_name, " 
+"  pcd.created_date waktu_daftar, " 
+"  e.operation status, " 
+"  eq.name cc_code, " 
+"  e.start_time cc_time, " 
+"  eq2.name ht_code, " 
+"  e2.start_time ht_time, " 
+"  eq3.name lo_code, " 
+"  e3.start_time lo_time, " 
+"  ds.created_date billing, " 
+"  sgd.date_in gate_in, " 
+"  eq4.name dlv_code, " 
+"  e4.start_time dlv_time, " 
+"  sgd.date_out gate_out, " 
+"  i.no_invoice, " 
+"  i.created_by, " 
+"  ct.type_in_general, " 
+"  pcd.cont_size, " 
+"  pcd.cont_status, " 
+"  (SELECT mc.name " 
+"  FROM m_customer mc, " 
+"    registration r " 
+"  WHERE ds.no_reg  = r.no_reg " 
+"  AND mc.cust_code = r.cust_code " 
+"  ) AS cust " 
+"FROM ((((((planning_cont_discharge pcd " 
+"LEFT JOIN (equipment e " 
+"LEFT JOIN m_equipment eq " 
+"ON eq.equip_code         = e.equip_code) " 
+"ON pcd.cont_no           = e.cont_no " 
+"AND pcd.no_ppkb          = e.no_ppkb " 
+"AND e.equipment_act_code = 'STEVEDORING') " 
+"LEFT JOIN (equipment e2 " 
+"LEFT JOIN m_equipment eq2 " 
+"ON eq2.equip_code         = e2.equip_code) " 
+"ON pcd.cont_no            = e2.cont_no " 
+"AND pcd.no_ppkb           = e2.no_ppkb " 
+"AND e2.equipment_act_code = 'H/T') " 
+"LEFT JOIN (equipment e3 " 
+"LEFT JOIN m_equipment eq3 " 
+"ON eq3.equip_code         = e3.equip_code) " 
+"ON pcd.cont_no            = e3.cont_no " 
+"AND pcd.no_ppkb           = e3.no_ppkb " 
+"AND e3.equipment_act_code = 'LIFTOFF') " 
+"LEFT JOIN delivery_service ds " 
+"ON pcd.cont_no  = ds.cont_no " 
+"AND pcd.no_ppkb = ds.no_ppkb) " 
+"LEFT JOIN invoice i " 
+"ON i.no_reg = ds.no_reg) " 
+"LEFT JOIN service_gate_delivery sgd " 
+"ON pcd.cont_no  = sgd.cont_no " 
+"AND pcd.no_ppkb = sgd.no_ppkb) " 
+"LEFT JOIN (equipment e4 " 
+"LEFT JOIN m_equipment eq4 " 
+"ON eq4.equip_code         = e4.equip_code) " 
+"ON pcd.cont_no            = e4.cont_no " 
+"AND pcd.no_ppkb           = e4.no_ppkb " 
+"AND e4.equipment_act_code = 'DELIVERY', " 
+"  planning_vessel pv, " 
+"  m_vessel v, " 
+"  m_container_type ct " 
+"WHERE pcd.cont_type = ct.cont_type " 
+"AND pcd.no_ppkb     = pv.no_ppkb " 
+"AND pv.vessel_code  = v.vessel_code " 
+"AND pcd.cont_no     = ? " 
+"UNION " 
+"SELECT pcd.id, " 
+"  pcd.cont_no, " 
+"  pcd.no_ppkb, " 
+"  v.name vessel_name, " 
+"  pcd.created_date waktu_daftar, " 
+"  e.operation status, " 
+"  eq.name cc_code, " 
+"  e.start_time cc_time, " 
+"  eq2.name ht_code, " 
+"  e2.start_time ht_time, " 
+"  eq3.name lo_code, " 
+"  e3.start_time lo_time, " 
+"  ds.created_date billing, " 
+"  sgd.date_in gate_in, " 
+"  eq4.name dlv_code, " 
+"  e4.start_time dlv_time, " 
+"  sgd.date_out gate_out, " 
+"  i.no_invoice, " 
+"  i.created_by, " 
+"  ct.type_in_general, " 
+"  pcd.cont_size, " 
+"  pcd.cont_status, " 
+"  (SELECT mc.name " 
+"  FROM m_customer mc, " 
+"    registration r " 
+"  WHERE ds.no_reg  = r.no_reg " 
+"  AND mc.cust_code = r.cust_code " 
+"  ) AS cust " 
+"FROM ((((((planning_cont_receiving pcd " 
+"LEFT JOIN receiving_service ds " 
+"ON pcd.cont_no  = ds.cont_no " 
+"AND pcd.no_ppkb = ds.no_ppkb) " 
+"LEFT JOIN invoice i " 
+"ON i.no_reg = ds.no_reg) " 
+"LEFT JOIN service_gate_receiving sgd " 
+"ON pcd.cont_no  = sgd.cont_no " 
+"AND pcd.no_ppkb = sgd.no_ppkb) " 
+"LEFT JOIN (equipment e4 " 
+"LEFT JOIN m_equipment eq4 " 
+"ON eq4.equip_code         = e4.equip_code) " 
+"ON pcd.cont_no            = e4.cont_no " 
+"AND pcd.no_ppkb           = e4.no_ppkb_recv " 
+"AND e4.equipment_act_code = 'RECEIVING') " 
+"LEFT JOIN (equipment e3 " 
+"LEFT JOIN m_equipment eq3 " 
+"ON eq3.equip_code         = e3.equip_code) " 
+"ON pcd.cont_no            = e3.cont_no " 
+"AND pcd.no_ppkb           = e3.no_ppkb " 
+"AND e3.equipment_act_code = 'LIFTON') " 
+"LEFT JOIN (equipment e2 " 
+"LEFT JOIN m_equipment eq2 " 
+"ON eq2.equip_code         = e2.equip_code) " 
+"ON pcd.cont_no            = e2.cont_no " 
+"AND pcd.no_ppkb           = e2.no_ppkb " 
+"AND e2.equipment_act_code = 'H/T') " 
+"LEFT JOIN (equipment e " 
+"LEFT JOIN m_equipment eq " 
+"ON eq.equip_code         = e.equip_code) " 
+"ON pcd.cont_no           = e.cont_no " 
+"AND pcd.no_ppkb          = e.no_ppkb " 
+"AND e.equipment_act_code = 'STEVEDORING', " 
+"  planning_vessel pv, " 
+"  m_vessel v, " 
+"  m_container_type ct " 
+"WHERE pcd.cont_type = ct.cont_type " 
+"AND pcd.no_ppkb     = pv.no_ppkb " 
+"AND pv.vessel_code  = v.vessel_code " 
+"AND pcd.cont_no     = ?")
})
public class PlanningContDischarge implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private String twentyOneFeet = "FALSE";
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    
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
    @Column(name = "y_slot")
    private Short ySlot;
    @Column(name = "y_row")
    private Short yRow;
    @Column(name = "y_tier")
    private Short yTier;
    @Column(name = "gross_class", length = 5)
    private String grossClass;
    @Column(name = "id_coordinat")
    private Integer idCoordinat;
    @Column(name = "id_yard_allocation")
    private String idYardAllocation;
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
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Basic(optional = false)
    @Column(name = "is_import", nullable = false)
    private String isImport;
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

    public PlanningContDischarge() {
    }

    public PlanningContDischarge(Integer id) {
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

    public String getGrossClass() {
        return grossClass;
    }


    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    /**
     * @return the idCoordinat
     */
    public Integer getIdCoordinat() {
        return idCoordinat;
    }

    /**
     * @param idCoordinat the idCoordinat to set
     */
    public void setIdCoordinat(Integer idCoordinat) {
        this.idCoordinat = idCoordinat;
    }

    /**
     * @return the idYardAllocation
     */
    public String getIdYardAllocation() {
        return idYardAllocation;
    }

    /**
     * @param idYardAllocation the idYardAllocation to set
     */
    public void setIdYardAllocation(String idYardAllocation) {
        this.idYardAllocation = idYardAllocation;
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

    public String getIsImport() {
        return isImport;
    }

    public void setIsImport(String isImport) {
        this.isImport = isImport;
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
        if (!(object instanceof PlanningContDischarge)) {
            return false;
        }
        PlanningContDischarge other = (PlanningContDischarge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningContDischarge[id=" + id + "]";
    }

    public String getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(String twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
