/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.qtasnim.port.util.ContainerUtilization;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "planning_cont_receiving")
@NamedQueries({
    @NamedQuery(name = "PlanningContReceiving.findAll", query = "SELECT p FROM PlanningContReceiving p"),
    @NamedQuery(name = "PlanningContReceiving.findById", query = "SELECT p FROM PlanningContReceiving p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningContReceiving.findByNoPpkb", query = "SELECT p FROM PlanningContReceiving p WHERE p.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "PlanningContReceiving.findByNoPpkbAndContNo", query = "SELECT p FROM PlanningContReceiving p WHERE p.planningVessel.noPpkb = :noPpkb AND p.contNo = :contNo"),
    @NamedQuery(name = "PlanningContReceiving.deleteByNoPpkbAndContNo", query = "DELETE FROM PlanningContReceiving p WHERE p.planningVessel.noPpkb = :noPpkb AND p.contNo = :contNo"),
    @NamedQuery(name = "PlanningContReceiving.updatePlanningVessel", query = "UPDATE PlanningContReceiving p SET p.planningVessel = :newValue WHERE p.planningVessel = :oldValue"),
    @NamedQuery(name = "PlanningContReceiving.updatePlanningVesselByContNo", query = "UPDATE PlanningContReceiving p SET p.planningVessel = :newValue, p.dischPort = :dischPort, p.portOfDelivery= :portOfDelivery WHERE p.planningVessel = :oldValue AND p.contNo IN :containers"),
    @NamedQuery(name = "PlanningContReceiving.findByContNo", query = "SELECT p FROM PlanningContReceiving p WHERE p.contNo = :contNo"),
    @NamedQuery(name = "PlanningContReceiving.findByContSize", query = "SELECT p FROM PlanningContReceiving p WHERE p.contSize = :contSize"),
    @NamedQuery(name = "PlanningContReceiving.findByContStatus", query = "SELECT p FROM PlanningContReceiving p WHERE p.contStatus = :contStatus"),
    @NamedQuery(name = "PlanningContReceiving.findByContGross", query = "SELECT p FROM PlanningContReceiving p WHERE p.contGross = :contGross"),
    @NamedQuery(name = "PlanningContReceiving.findBySealNo", query = "SELECT p FROM PlanningContReceiving p WHERE p.sealNo = :sealNo"),
    @NamedQuery(name = "PlanningContReceiving.findByOverSize", query = "SELECT p FROM PlanningContReceiving p WHERE p.overSize = :overSize"),
    @NamedQuery(name = "PlanningContReceiving.findByDg", query = "SELECT p FROM PlanningContReceiving p WHERE p.dg = :dg"),
    @NamedQuery(name = "PlanningContReceiving.findByDgLabel", query = "SELECT p FROM PlanningContReceiving p WHERE p.dgLabel = :dgLabel"),
    @NamedQuery(name = "PlanningContReceiving.findByLoadPort", query = "SELECT p FROM PlanningContReceiving p WHERE p.loadPort = :loadPort"),
    @NamedQuery(name = "PlanningContReceiving.findByDischPort", query = "SELECT p FROM PlanningContReceiving p WHERE p.dischPort = :dischPort")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContReceivings", query = "SELECT r.cont_no, r.cont_size,r.cont_type, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,r.over_size,r.dg,r.id from planning_cont_receiving r"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContReceivingCreateGenerate", query = "SELECT r.bl_no , r.cont_type,r.commodity_code, r.load_port, r.disch_port, r.cont_no,r.cont_size,r.cont_status,r.cont_gross,r.seal_no,r.dg ,r.dg_label,r.over_size,r.bl_no,r.no_ppkb from planning_cont_receiving r where r.id=?"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContReceivingsByNoPpkb", query = "SELECT r.cont_no, r.cont_size, mt.name, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,r.over_size,r.dg,r.id from planning_cont_receiving as r,m_container_type mt,receiving_service rc WHERE r.no_ppkb = ? AND r.no_ppkb =rc.no_ppkb AND r.cont_no=rc.cont_no AND r.cont_type=mt.cont_type AND r.cont_no NOT IN (select cont_no from service_receiving where no_ppkb=r.no_ppkb) ORDER BY r.id ASC"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findReceivingConfirm", query = "SELECT r.cont_no, r.cont_size, mt.name, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,r.over_size,r.dg,r.id "
                                                                                        + "FROM planning_cont_receiving as r "
                                                                                                + "JOIN m_container_type mt ON (r.cont_type=mt.cont_type) "
                                                                                                + "JOIN receiving_service rc ON (r.no_ppkb =rc.no_ppkb AND r.cont_no=rc.cont_no) "
                                                                                                + "LEFT JOIN (SELECT cont_no, no_ppkb FROM service_receiving WHERE status_cancel_loading <> TRUE) sr ON (r.no_ppkb = sr.no_ppkb AND r.cont_no = sr.cont_no) "
                                                                                                + "JOIN service_gate_receiving sgr ON (rc.job_slip=sgr.job_slip) "
                                                                                        + "WHERE sr.cont_no IS NULL AND rc.status_cancel_loading <> TRUE AND r.no_ppkb = ? "
                                                                                        + "ORDER BY r.id ASC"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContReceivingByNoPpkb", query = "SELECT r.cont_no, r.cont_size, "
    + "r.cont_type, r.cont_status, r.cont_gross, r.seal_no, r.block, r.fr_slot, r.fr_row FROM planning_cont_receiving as r WHERE r.no_ppkb = ?"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findByContDelete", query = "SELECT id, cont_no, no_ppkb FROM planning_cont_receiving WHERE no_ppkb=? AND cont_no = ?"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findByContNo", query = "SELECT p.id, p.cont_no, p.no_ppkb, pr.block FROM planning_cont_receiving p, planning_vessel s, receiving_service r, planning_receiving pr WHERE p.no_ppkb = s.no_ppkb AND p.no_ppkb = r.no_ppkb AND p.cont_no = r.cont_no AND p.cont_no NOT IN (select cont_no from service_receiving where no_ppkb = p.no_ppkb) AND p.no_ppkb = ? AND p.cont_no = ? AND p.position = ? AND (s.status = 'Approved' OR s.status = 'Servicing' OR s.status = 'ReadyService') AND p.cont_no IN (select cont_no from service_gate_receiving where no_ppkb=p.no_ppkb) AND pr.receiving_allocation_id = p.id_constrain_planning LIMIT 1"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContReceivingIsGenerateTrue", query = "SELECT r.id, r.commodity_code, r.cont_type,r.no_ppkb, r.cont_no,r.cont_size,r.cont_status,r.cont_gross,r.seal_no,r.over_size,r.dg_label,r.is_generate,r.load_port,r.disch_port,r.dg,r.bl_no,r.mlo FROM planning_cont_receiving r WHERE r.no_ppkb = ? AND is_generate =TRUE ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContReceivingIsGenerateTrueAndBlNo", query = "SELECT r.id, r.commodity_code, r.cont_type,r.no_ppkb, r.cont_no,r.cont_size,r.cont_status,r.cont_gross,r.seal_no,r.over_size,r.dg_label,r.is_generate,r.load_port,r.disch_port,r.dg,r.bl_no FROM planning_cont_receiving r WHERE r.no_ppkb = ? AND r.bl_no=? AND is_generate =TRUE ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContReceivingBaplieLoad", query = "SELECT r.id, m.name, mt.name,r.no_ppkb, r.cont_no,r.cont_size,r.cont_status,r.cont_gross,r.seal_no,r.over_size,r.dg_label,r.is_generate,r.load_port,r.disch_port,r.dg, CASE WHEN r.is_completed=true THEN 'Yes' WHEN r.is_completed=false THEN 'No' END as is_completed,r.bl_no FROM planning_cont_receiving r,m_container_type mt,m_commodity m WHERE r.cont_type=mt.cont_type AND r.commodity_code=m.commodity_code AND r.no_ppkb = ? AND r.is_completed IS NOT NULL ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContReceivingGateReceiving", query = "SELECT r.id, r.no_ppkb, r.cont_no,r.id_constrain_planning FROM planning_cont_receiving r WHERE r.no_ppkb = ? AND r.cont_no=?"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findBaplieByConstraint", query = "SELECT id FROM planning_cont_receiving WHERE cont_type = ? AND cont_status = ? AND gross_class = ? AND dg = ? AND over_size = ? AND disch_port = ? AND is_export = ? AND no_ppkb = ?"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findIdByIdConstraint", query = "SELECT id FROM planning_cont_receiving WHERE id_constrain_planning = ?"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.createGenerateByPPKB", query = "SELECT cont_type, cont_size, cont_status, gross_class, null, dg, over_size, disch_port, count(*) as jumlah, is_export "
                                                                                        + "FROM planning_cont_receiving "
                                                                                        + "WHERE no_ppkb = ? "
                                                                                        + "GROUP BY cont_type, cont_size, cont_status, gross_class, dg, over_size, disch_port, is_export ORDER BY cont_size, cont_status"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.deleteByIdConstraint", query = "UPDATE planning_cont_receiving SET id_constrain_planning=null WHERE id_constrain_planning=?"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContReceivingByCekExist", query = "SELECT * "
                                                                                                        + "FROM planning_cont_receiving r "
                                                                                                                + "LEFT JOIN receiving_service rs ON (r.cont_no=rs.cont_no AND r.no_ppkb=rs.no_ppkb) "
                                                                                                        + "WHERE r.cont_no=? AND r.no_ppkb= ? AND (rs.status_cancel_loading IS NULL OR rs.status_cancel_loading = FALSE);"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningCont", query = "Select id from planning_cont_receiving pcr,receiving_service rs WHERE pcr.cont_no=rs.cont_no AND pcr.no_ppkb=rs.no_ppkb AND pcr.cont_no=? AND pcr.no_ppkb=? AND rs.is_generate=? "),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findByContNoMobile", query = "SELECT p.id, p.cont_no, p.no_ppkb FROM planning_cont_receiving p, planning_vessel s, receiving_service r WHERE p.no_ppkb = s.no_ppkb AND p.no_ppkb = r.no_ppkb AND p.cont_no = r.cont_no AND p.cont_no NOT IN (select cont_no from service_receiving where no_ppkb = p.no_ppkb) AND p.cont_no = ? AND p.position = ? AND s.close_rec > NOW() AND (s.status = 'Approved' OR s.status = 'Servicing' OR s.status = 'ReadyService')"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findContainerDetails", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, mc.name, scd.cont_gross, scd.gross_class, scd.seal_no, change(scd.over_size), change(scd.dg), change(scd.dg_label), scd.id, scd.no_ppkb, scd.bl_no FROM planning_cont_receiving scd, m_container_type mct, m_commodity mc WHERE scd.cont_type = mct.cont_type AND scd.commodity_code =  mc.commodity_code AND scd.no_ppkb=? AND scd.cont_no=? ORDER BY scd.modified_date DESC"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findContainerDetailsPPKBnull", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, mc.name, scd.cont_gross, scd.gross_class, scd.seal_no, change(scd.over_size), change(scd.dg), change(scd.dg_label), scd.id, scd.no_ppkb, scd.bl_no FROM planning_cont_receiving scd, m_container_type mct, m_commodity mc WHERE scd.cont_type = mct.cont_type AND scd.commodity_code =  mc.commodity_code AND scd.cont_no=? ORDER BY scd.modified_date DESC"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findContainerDetailsCONTnull", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, mc.name, scd.cont_gross, scd.gross_class, scd.seal_no, change(scd.over_size), change(scd.dg), change(scd.dg_label), scd.id, scd.no_ppkb, scd.bl_no FROM planning_cont_receiving scd, m_container_type mct, m_commodity mc WHERE scd.cont_type = mct.cont_type AND scd.commodity_code =  mc.commodity_code AND scd.no_ppkb=? ORDER BY scd.modified_date DESC"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findContainerNumbers", query = "SELECT cont_no FROM planning_cont_receiving WHERE upper(no_ppkb) LIKE ? AND upper(cont_no) LIKE ?"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findContIds", query = "SELECT find_contid(?,?,?)"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPKs", query = "SELECT find_pk(?,?,?,?)"),
    //@NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContMonitoringLoad", query = "SELECT p.bl_no,p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, com.name,'cy' || p.position,ds.position,'vs' || s.position  FROM ((planning_cont_load as p RIGHT JOIN planning_cont_receiving ds ON p.no_ppkb=ds.no_ppkb AND p.cont_no=ds.cont_no)LEFT JOIN service_cont_load s ON p.no_ppkb=s.no_ppkb AND p.cont_no=s.cont_no), m_container_type c, m_commodity as com WHERE p.commodity_code = com.commodity_code AND p.cont_type=c.cont_type AND p.no_ppkb=?"),
    //@NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContMonitoringLoad", query = "SELECT p.bl_no,p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, com.name,CASE when p.position is null THEN 'cy00' else 'cy' || p.position END FROM planning_cont_receiving p, m_container_type c, m_commodity as com WHERE p.commodity_code = com.commodity_code AND p.cont_type=c.cont_type AND p.no_ppkb=?"),
    //@NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContMonitoringLoad", query = " SELECT p.bl_no,p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, com.name,(SELECT mp.name FROM m_port mp WHERE mp.port_code::TEXT = p.port_of_delivery::TEXT) AS pod, 'pos' || p.position,sl.position,case when p.cont_no = sl.cont_no and p.no_ppkb=sl.no_ppkb then (case when sl.position = '01' then 'VESSEL' else (case when sl.position='02' then 'TRUCK' else 'CY' end ) end ) else (case when p.position = '01' then 'VESSEL' else (case when p.position='02' then 'TRUCK' else 'CY' end ) end ) end AS posisi FROM (planning_cont_load p left join service_cont_load sl on p.no_ppkb = sl.no_ppkb and p.cont_no=sl.cont_no), m_container_type c, m_commodity as com WHERE p.commodity_code = com.commodity_code AND p.cont_type=c.cont_type AND p.no_ppkb=? "),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findPlanningContMonitoringLoad", query = "SELECT p.bl_no,p.cont_no,p.cont_size,mct.type_in_general,p.cont_status,p.cont_gross,mc.name as commodity,mp.name as pod,p.position FROM planning_cont_load p LEFT JOIN m_container_type mct ON mct.cont_type = p.cont_type LEFT JOIN m_port mp ON mp.port_code = p.port_of_delivery LEFT JOIN m_commodity mc ON mc.commodity_code = p.commodity_code WHERE no_ppkb= ? AND position= ? "),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findReceivableContainer", query = "SELECT p.id, p.cont_no, p.no_ppkb "
                                                                                            + "FROM planning_vessel s, receiving_service r, "
                                                                                                    + "(planning_cont_receiving p "
                                                                                                            + "JOIN service_gate_receiving sgr ON (p.cont_no=sgr.cont_no AND p.no_ppkb=sgr.no_ppkb)) "
                                                                                                            + "LEFT JOIN service_receiving sr ON (p.cont_no=sr.cont_no AND p.no_ppkb=sr.no_ppkb) "
                                                                                            + "WHERE sr.cont_no IS NULL AND sr.no_ppkb IS NULL AND p.no_ppkb = s.no_ppkb AND p.no_ppkb = r.no_ppkb AND p.cont_no = r.cont_no AND p.cont_no = ? AND p.position = '01' AND (s.status = 'Approved' OR s.status = 'Servicing' OR s.status = 'ReadyService')"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findReceivableContainerWithSuggestedLocation", query = "SELECT p.id, p.cont_no, p.no_ppkb, rng.block, rng.slot, rng.row, rng.tier, CASE WHEN rng.tier <= 3 THEN 0 ELSE -1 END AS ordering, p.cont_size, p.cont_status, p.over_size, p.dg, p.disch_port, sgr.cont_weight, mcd.name, mv.name, sgr.cont_no IS NOT NULL has_entering_gate "
                                                                                            + "FROM planning_cont_receiving p "
                                                                                                    + "JOIN planning_vessel s ON (p.no_ppkb = s.no_ppkb) "
                                                                                                            + "JOIN m_vessel mv ON (s.vessel_code=mv.vessel_code) "
                                                                                                    + "JOIN receiving_service r ON (p.no_ppkb = r.no_ppkb AND p.cont_no = r.cont_no) "
                                                                                                    + "LEFT JOIN (service_gate_receiving sgr "
                                                                                                            + "JOIN m_cont_damage mcd ON (sgr.cont_damage=mcd.id)) ON (p.cont_no=sgr.cont_no AND p.no_ppkb=sgr.no_ppkb) "
                                                                                                    + "LEFT JOIN service_receiving sr ON (p.cont_no=sr.cont_no AND p.no_ppkb=sr.no_ppkb AND sr.status_cancel_loading <> TRUE) "
                                                                                                    + "LEFT JOIN (SELECT myc.cont_no, pya.no_ppkb, pya.port_code, pya.cont_type, pya.gross_class, pya.cont_size, pya.cont_status, pya.over_size, pya.dg, myc.block, myc.slot, myc.row, myc.tier, mct.type_in_general "
                                                                                                            + "FROM "
                                                                                                                    + "(SELECT pya.id, pya.no_ppkb, py.block, py.fr_slot, py.to_slot, py.fr_row, py.to_row, pya.port_code, pya.cont_type, pya.gross_class, pya.cont_size, pya.cont_status, pya.over_size, pya.dg "
                                                                                                                            + "FROM planning_receiving_allocation pya "
                                                                                                                                    + "JOIN planning_receiving py ON (py.receiving_allocation_id=pya.id)) AS pya "
                                                                                                                                            + "JOIN m_yard_coordinat myc ON (myc.no_ppkb=pya.no_ppkb AND myc.block = pya.block AND (myc.slot BETWEEN pya.fr_slot AND pya.to_slot AND myc.row BETWEEN pya.fr_row AND pya.to_row)) "
                                                                                                                                    + "JOIN m_container_type mct ON (pya.cont_type=mct.cont_type) "
                                                                                                            + "WHERE (char_length(myc.cont_no) > 11)) AS rng "
                                                                                                            + "ON (p.no_ppkb=rng.no_ppkb AND p.disch_port=rng.port_code AND p.gross_class=rng.gross_class AND p.cont_size=rng.cont_size AND p.cont_status=rng.cont_status AND p.over_size=rng.over_size AND p.dg=rng.dg) "
                                                                                                    + "JOIN m_container_type mc ON (p.cont_type=mc.cont_type) "
                                                                                            + "WHERE sr.cont_no IS NULL AND sr.no_ppkb IS NULL AND p.cont_no = ? AND p.position = '01' AND (s.status = 'Approved' OR s.status = 'Servicing' OR s.status = 'ReadyService') AND r.status_cancel_loading <> TRUE "
                                                                                            + "ORDER BY rng.block ASC, ordering DESC, rng.slot ASC, rng.tier ASC, rng.row ASC "
                                                                                            + "LIMIT 1"),
    @NamedNativeQuery(name = "PlanningContReceiving.Native.findByContValidasiConstraint", query = "SELECT r.id FROM planning_cont_receiving r where r.no_ppkb= ? AND r.cont_size=? and r.cont_type=? and r.cont_status=? and r.over_size=? and r.dg=? and r.gross_class=? AND r.disch_port=? AND r.is_export=? limit 1")
})
public class PlanningContReceiving implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "over_size")
    private Boolean overSize;
    @Column(name = "dg")
    private Boolean dg;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Column(name = "load_port", length = 10)
    private String loadPort;
    @Column(name = "disch_port", length = 10)
    private String dischPort;
    @Column(name = "position")
    private String position;
    @Column(name = "is_generate")
    private Boolean isGenerate;
    @Column(name = "is_completed")
    private Boolean isCompleted;
    @Column(name = "gross_class", length = 5)
    private String grossClass;
    @Column(name = "planning")
    private Boolean planning = true;
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
    @Column(name = "is_copy")
    private Boolean isCopy;
    @Column(name = "bl_no")
    private String blNo;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @Column(name = "id_constrain_planning")
    private Integer idConstrainPlanning;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @OneToOne
    @JoinColumns(value = {
        @JoinColumn(name = "cont_no", referencedColumnName = "cont_no", insertable = false, updatable = false, nullable = true),
        @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", insertable = false, updatable = false, nullable = true)
    })
    private ServiceGateReceiving serviceGateReceiving;
    @JoinColumn(name = "port_of_delivery", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort portOfDelivery;

    public PlanningContReceiving() {
    }

    public PlanningContReceiving(Integer id) {
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

    public Boolean getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(Boolean dgLabel) {
        this.dgLabel = dgLabel;
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

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getIsGenerate() {
        return isGenerate;
    }

    public void setIsGenerate(Boolean isGenerate) {
        this.isGenerate = isGenerate;
    }

    public Boolean getPlanning() {
        return planning;
    }

    public void setPlanning(Boolean planning) {
        this.planning = planning;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
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

    /**
     * @return the idConstrainPlanning
     */
    public Integer getIdConstrainPlanning() {
        return idConstrainPlanning;
    }

    /**
     * @param idConstrainPlanning the idConstrainPlanning to set
     */
    public void setIdConstrainPlanning(Integer idConstrainPlanning) {
        this.idConstrainPlanning = idConstrainPlanning;
    }

    /**
     * @return the isCompleted
     */
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * @param isCompleted the isCompleted to set
     */
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * @return the grossClass
     */
    public String getGrossClass() {
        return grossClass;
    }

    /**
     * @param grossClass the grossClass to set
     */
    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    public Boolean getIsCopy() {
        return isCopy;
    }

    public void setIsCopy(Boolean isCopy) {
        this.isCopy = isCopy;
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

    public boolean isValid() {
        return getMasterCommodity() != null && getGrossClass() != null && getContSize() != null && getMasterContainerType() != null && getLoadPort() != null && getDischPort() != null && getContNo() != null && getContStatus() != null;
    }

    public boolean isContainerNumberValid() {
        if (getContNo() != null) {
            return ContainerUtilization.checkContainerNumberValidation(getContNo());
        }
        return false;
    }
    
    public MasterCustomer getMlo() {
        return mlo;
    }

    public void setMlo(MasterCustomer mlo) {
        this.mlo = mlo;
    }

    public boolean getIsExport() {
        return isExport;
    }

    public void setIsExport(boolean isExport) {
        this.isExport = isExport;
    }

    public Integer getContTeus() {
        if (contSize != null) {
            return (int) Math.ceil((int) (contSize.doubleValue()/ (double) 20));
        }

        return null;
    }

    public ServiceGateReceiving getServiceGateReceiving() {
        return serviceGateReceiving;
    }

    public MasterPort getPortOfDelivery() {
        return portOfDelivery;
    }

    public void setPortOfDelivery(MasterPort portOfDelivery) {
        this.portOfDelivery = portOfDelivery;
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
        if (!(object instanceof PlanningContReceiving)) {
            return false;
        }
        PlanningContReceiving other = (PlanningContReceiving) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningContReceiving[id=" + id + "]";
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
