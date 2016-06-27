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
@Table(name = "service_receiving")
@NamedQueries({
    @NamedQuery(name = "ServiceReceiving.findAll", query = "SELECT s FROM ServiceReceiving s"),
    @NamedQuery(name = "ServiceReceiving.findById", query = "SELECT s FROM ServiceReceiving s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceReceiving.findByContNo", query = "SELECT s FROM ServiceReceiving s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceReceiving.findMovableContainer", query = "SELECT s FROM ServiceReceiving s WHERE s.contNo = :contNo AND s.isLifton <> TRUE AND s.masterYard IS NOT NULL AND s.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "ServiceReceiving.updateStatusCancelLoadingByNoPpkb", query = "UPDATE ServiceReceiving s SET s.statusCancelLoading = :statusCancelLoading WHERE s.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "ServiceReceiving.findByNoPpkb", query = "SELECT s FROM ServiceReceiving s WHERE s.planningVessel.noPpkb = :noPpkb AND s.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "ServiceReceiving.updatePlanningVessel", query = "UPDATE ServiceReceiving s SET s.planningVessel = :newValue WHERE s.planningVessel = :oldValue AND s.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "ServiceReceiving.updatePlanningVesselByContNo", query = "UPDATE ServiceReceiving s SET s.planningVessel = :newValue, s.portOfDelivery= :portOfDelivery WHERE s.planningVessel = :oldValue AND s.statusCancelLoading <> TRUE AND s.contNo IN :containers"),
    @NamedQuery(name = "ServiceReceiving.findByNoPpkbAndContNo", query = "SELECT s FROM ServiceReceiving s WHERE s.planningVessel.noPpkb = :noPpkb AND s.contNo = :contNo"),
    @NamedQuery(name = "ServiceReceiving.findByNoPpkbAndContNoNotCancelLoading", query = "SELECT s FROM ServiceReceiving s WHERE s.planningVessel.noPpkb = :noPpkb AND s.contNo = :contNo AND s.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "ServiceReceiving.findByNoPpkbAndContNoCancelLoading", query = "SELECT s FROM ServiceReceiving s WHERE s.planningVessel.noPpkb = :noPpkb AND s.contNo = :contNo AND s.statusCancelLoading = TRUE"),
    @NamedQuery(name = "ServiceReceiving.findByNoPpkb.count", query = "SELECT COUNT(s) FROM ServiceReceiving s WHERE s.planningVessel.noPpkb = :noPpkb AND s.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "ServiceReceiving.findCancelableContainerByContNoAndNoPpkb", query = "SELECT s FROM ServiceReceiving s WHERE s.planningVessel.noPpkb = :noPpkb AND s.contNo = :contNo AND s.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "ServiceReceiving.findByContSize", query = "SELECT s FROM ServiceReceiving s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceReceiving.findByContStatus", query = "SELECT s FROM ServiceReceiving s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceReceiving.findByContGross", query = "SELECT s FROM ServiceReceiving s WHERE s.contGross = :contGross"),
    @NamedQuery(name = "ServiceReceiving.findBySealNo", query = "SELECT s FROM ServiceReceiving s WHERE s.sealNo = :sealNo"),
    @NamedQuery(name = "ServiceReceiving.findByOverSize", query = "SELECT s FROM ServiceReceiving s WHERE s.overSize = :overSize"),
    @NamedQuery(name = "ServiceReceiving.findByDangerous", query = "SELECT s FROM ServiceReceiving s WHERE s.dangerous = :dangerous"),
    @NamedQuery(name = "ServiceReceiving.findByDgLabel", query = "SELECT s FROM ServiceReceiving s WHERE s.dgLabel = :dgLabel"),
    @NamedQuery(name = "ServiceReceiving.findByYSlot", query = "SELECT s FROM ServiceReceiving s WHERE s.ySlot = :ySlot"),
    @NamedQuery(name = "ServiceReceiving.findByYRow", query = "SELECT s FROM ServiceReceiving s WHERE s.yRow = :yRow"),
    @NamedQuery(name = "ServiceReceiving.findByYTier", query = "SELECT s FROM ServiceReceiving s WHERE s.yTier = :yTier"),
    @NamedQuery(name = "ServiceReceiving.findByTransactionDate", query = "SELECT s FROM ServiceReceiving s WHERE s.transactionDate = :transactionDate")
    })
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivings", query = "SELECT s.no_ppkb, pp.vessel_code, pp.voy_in, pp.voy_out FROM service_receiving s,planning_vessel p, preservice_vessel pp where s.no_ppkb =p.no_ppkb and p.booking_code=pp.booking_code ORDER BY s.id DESC"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findVesselChangeAbleContainers", query = "SELECT sr.cont_no, sr.cont_size, mct.type_in_general, sr.cont_status, sr.over_size, sr.dangerous, sr.dg_label, pcl.disch_port, rs.job_slip, "
    + " sr.transaction_date, sr.block, sr.y_slot, sr.y_row, sr.y_tier,sr.cont_gross,(SELECT g.gross_class FROM m_gross_parameter g WHERE g.cont_size=sr.cont_size AND sr.cont_gross >= g.min_gross AND sr.cont_gross < g.max_gross) as gross_class,mc.name commodity_name  "
                                                                                                + "FROM service_receiving sr "
                                                                                                        + "LEFT JOIN service_cont_load scl ON (sr.cont_no=scl.cont_no AND sr.no_ppkb=scl.no_ppkb) "
                                                                                                        + "JOIN m_container_type mct ON (sr.cont_type=mct.cont_type) "
                                                                                                        + "JOIN planning_cont_load pcl ON (sr.cont_no=pcl.cont_no AND sr.no_ppkb=pcl.no_ppkb) "
                                                                                                        + "JOIN receiving_service rs ON (sr.cont_no=rs.cont_no AND sr.no_ppkb=rs.no_ppkb) "
                                                                                                        + " inner join m_commodity mc on sr.commodity_code= mc.commodity_code "
                                                                                                + "WHERE scl.cont_no IS NULL AND sr.no_ppkb = ? AND rs.status_cancel_loading <> TRUE AND sr.status_cancel_loading <> TRUE AND pcl.status_cancel_loading <> TRUE;"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingByPpkbb", query = "SELECT s.no_ppkb, pp.vessel_code, pp.voy_in, pp.voy_out FROM service_receiving s,planning_vessel p, preservice_vessel pp where s.no_ppkb =p.no_ppkb and p.booking_code=pp.booking_code and s.no_ppkb= ?"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingByPpkbList2", query = "SELECT r.cont_no, r.cont_size, m.type_in_general as name, r.cont_status,r.cont_gross, r.seal_no,r.y_slot,r.y_row,r.y_tier,r.id,r.block,r.is_lifton FROM service_receiving r,m_container_type m WHERE r.cont_type=m.cont_type AND r.no_ppkb = ? AND r.status_cancel_loading=false ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingByPpkbList", query = "SELECT r.id, r.commodity_code, r.block, r.cont_type,r.no_ppkb, r.cont_no,r.cont_size,r.cont_status,r.cont_gross,r.seal_no,r.over_size,r.dg_label,r.dangerous,r.y_slot,r.y_row,r.y_tier,r.bl_no,r.mlo FROM service_receiving r WHERE r.no_ppkb = ? AND r.cont_no NOT IN (select cont_no from planning_cont_load where no_ppkb=r.no_ppkb) AND r.status_cancel_loading=false ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingByPpkbReefer", query = "SELECT sr.cont_no, sr.cont_size, mct.name, sr.cont_status, sr.cont_gross, sr.seal_no, sr.id "
                                                                                                + "FROM service_receiving AS sr "
                                                                                                        + "JOIN m_container_type AS mct ON (sr.cont_type = mct.cont_type) "
                                                                                                        + "LEFT JOIN service_reefer srf ON (srf.no_ppkb = sr.no_ppkb AND srf.cont_no = sr.cont_no) "
                                                                                                + "WHERE mct.type_in_general = 'RFR' AND sr.no_ppkb = ? AND srf.cont_no IS NULL ORDER BY sr.id DESC"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findContainerReefer", query = "SELECT sr.cont_type, sr.cont_no, sr.cont_size, sr.cont_status, sr.no_ppkb "
                                                                                    + "FROM service_receiving AS sr "
                                                                                            + "JOIN m_container_type AS mct ON (sr.cont_type = mct.cont_type) "
                                                                                            + "LEFT JOIN service_reefer srf ON (srf.no_ppkb = sr.no_ppkb AND srf.cont_no = sr.cont_no) "
                                                                                    + "WHERE mct.type_in_general = 'RFR' AND sr.no_ppkb = ? AND srf.cont_no IS NULL ORDER BY sr.id DESC"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findLoadRecapsByPPKB", query = "SELECT s.cont_type, s.cont_no, s.cont_status, s.cont_size, c.type_in_general as name, s.transaction_date, e.start_time, r.masa_1, r.masa_2, p.charge_m1, p.charge_m2, p.jasa_dermaga, p.total_charge, curr.symbol, curr.language, curr.country "
    + "FROM (((("
    + "service_receiving s LEFT JOIN  m_container_type c ON s.cont_type=c.cont_type) "
    + "LEFT JOIN equipment e ON s.cont_no=e.cont_no AND s.no_ppkb=e.no_ppkb AND e.operation='LOAD' AND e.equipment_act_code='LIFTON') "
    + "LEFT JOIN receiving_service r ON s.cont_no=r.cont_no AND s.no_ppkb=r.no_ppkb) "
    + "LEFT JOIN perhitungan_penumpukan p ON s.cont_no=p.cont_no AND s.no_ppkb=p.no_ppkb), m_currency curr "
    + "WHERE s.no_ppkb=? AND p.curr_code = curr.curr_code"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingByPPKB", query = "SELECT s.id, e.id, r.job_slip, r.no_reg FROM service_receiving s ,equipment e, receiving_service r WHERE s.cont_no=e.cont_no AND s.no_ppkb=e.no_ppkb AND e.operation='LOAD' AND e.equipment_act_code='LIFTON' AND s.cont_no=r.cont_no AND s.no_ppkb=r.no_ppkb AND s.no_ppkb=?"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingContNo", query = "SELECT scd.cont_no, scd.id FROM service_receiving AS scd WHERE scd.no_ppkb = ? AND scd.cont_no = ? AND scd.cont_no NOT IN (select cont_no from cancel_loading_service where no_ppkb=scd.no_ppkb)"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingByAutoComplete", query = "SELECT scd.cont_no FROM service_receiving AS scd WHERE scd.no_ppkb = ? AND scd.cont_no LIKE ('%'|| ? ||'%') AND scd.cont_no NOT IN (select cont_no from cancel_loading_service where no_ppkb=scd.no_ppkb)"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingsStuffingService", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, com.name, p.over_size, p.dangerous, p.dg_label, p.bl_no, p.mlo, p.id "
                                                                                                    + "FROM service_receiving as p "
                                                                                                            + "JOIN m_container_type as c ON (p.cont_type=c.cont_type) "
                                                                                                            + "JOIN m_commodity as com ON (p.commodity_code = com.commodity_code) "
                                                                                                            + "LEFT JOIN stuffing_service ss ON (ss.cont_no = p.cont_no) "
                                                                                                                    + "LEFT JOIN registration r ON (ss.no_reg = r.no_reg AND r.no_ppkb = p.no_ppkb) "
                                                                                                    + "WHERE ss.cont_no IS NULL AND p.cont_status = 'MTY' AND p.no_ppkb = ? AND p.status_cancel_loading <> TRUE "
                                                                                                    + "ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findRealisasiJobSlip", query = "SELECT count(no_ppkb) FROM service_receiving WHERE no_ppkb =?"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findByPpkbAndContNo", query = "SELECT r.id, r.no_ppkb,r.block, r.y_slot, r.y_row, r.y_tier FROM service_receiving r WHERE r.no_ppkb = ? AND r.cont_no = ?"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findByPpkbAndContNoFront", query = "SELECT p.id, r.no_ppkb,r.cont_no,r.cont_size,r.cont_status,r.cont_gross,r.seal_no,p.block, p.y_slot, p.y_row, p.y_tier FROM (receiving_service r LEFT JOIN service_receiving p ON r.cont_no = p.cont_no AND r.no_ppkb = p.no_ppkb) WHERE r.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingFrontByPPKB", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, change(p.over_size), change(p.dg), change(p.dg_label), e1.equip_code, e1.start_time, e1.end_time, e2.equip_code, e2.start_time, e2.end_time, e3.equip_code, e3.start_time, e3.end_time FROM ((((receiving_service p LEFT JOIN m_container_type c ON p.cont_type=c.cont_type) LEFT JOIN equipment e1 ON p.cont_no = e1.cont_no AND p.no_ppkb = e1.no_ppkb_recv AND e1.operation = 'LOAD' AND e1.equipment_act_code = 'RECEIVING') LEFT JOIN equipment e2 ON p.cont_no = e2.cont_no AND p.no_ppkb = e2.no_ppkb AND e2.operation = 'LOAD' AND e2.equipment_act_code = 'LIFTON') LEFT JOIN equipment e3 ON p.cont_no = e3.cont_no AND p.no_ppkb = e3.no_ppkb AND e3.operation = 'LOAD' AND e3.equipment_act_code = 'STEVEDORING') WHERE p.no_ppkb = ? "),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingByIsLiftOn", query = "SELECT id, cont_no, no_ppkb, y_slot, y_row, y_tier, block, is_behandle, is_cfs, is_inspection, 'receiving' as asal FROM service_receiving WHERE cont_no = ? AND is_lifton = ?"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingsMovement", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.is_behandle, p.is_cfs, p.is_inspection, CASE WHEN NOT is_behandle AND NOT is_cfs AND NOT is_inspection THEN TRUE ELSE FALSE END AS is_cy FROM service_receiving as p, m_container_type as c WHERE p.cont_type=c.cont_type AND p.is_lifton = false AND p.block IS NOT NULL ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingsStuffingServiceByBL", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, com.name, p.over_size, p.dangerous, p.dg_label, p.bl_no, p.mlo FROM service_receiving as p, m_container_type as c, m_commodity as com WHERE p.commodity_code = com.commodity_code AND p.cont_status = 'MTY' AND p.cont_type=c.cont_type AND p.bl_no = ? AND p.no_ppkb = ? AND p.cont_no NOT IN (SELECT cont_no FROM stuffing_service WHERE no_ppkb = p.no_ppkb) ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingsChangeStatus", query = "SELECT scd.id,scd.bl_no,scd.cont_no,mct.name,scd.cont_gross,mc.name,scd.seal_no, scd.cont_size,scd.cont_status,scd.block, scd.y_slot, scd.y_row, scd.y_tier FROM service_receiving AS scd, m_container_type AS mct,m_commodity as mc WHERE scd.cont_type = mct.cont_type AND scd.commodity_code=mc.commodity_code AND scd.change_status=true AND scd.no_ppkb=? ORDER BY scd.id DESC"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingsChangeDestination", query = "SELECT scd.id,scd.bl_no,scd.cont_no,mct.name,scd.cont_gross,mc.name,scd.seal_no, scd.cont_size,scd.cont_status,scd.block, scd.y_slot, scd.y_row, scd.y_tier FROM service_receiving AS scd, m_container_type AS mct,m_commodity as mc WHERE scd.cont_type = mct.cont_type AND scd.commodity_code=mc.commodity_code AND scd.is_change_destination=true AND scd.no_ppkb=? ORDER BY scd.id DESC"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingByDestination", query = "SELECT r.id, r.old_ppkb FROM service_receiving r WHERE r.is_change_destination=TRUE AND r.old_ppkb = ? AND r.cont_no = ?"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingFrontByPPKBVersi2", query = "SELECT s.cont_no,s.cont_size,mct.type_in_general,s.cont_status,s.cont_gross,s.over_size,s.dangerous,s.dg_label,sr.transaction_date,"
                                                                                                        + " e2.equip_code, e2.start_time,e2.end_time, e3.equip_code, e3.start_time, e3.end_time,"
                                                                                                        + " e4.equip_code, e4.start_time, e4.end_time,sr.created_date, sr.block "
                                                                                                        + " FROM service_cont_load s "
                                                                                                        + " LEFT JOIN m_container_type mct ON mct.cont_type=s.cont_type "
                                                                                                        + " LEFT JOIN service_receiving sr ON sr.cont_no=s.cont_no AND sr.no_ppkb=s.no_ppkb AND sr.status_cancel_loading <> TRUE "
                                                                                                        + " LEFT JOIN equipment e2 ON s.cont_no = e2.cont_no AND s.no_ppkb = e2.no_ppkb AND e2.operation = 'LOAD' AND e2.equipment_act_code = 'LIFTON' "
                                                                                                        + " LEFT JOIN equipment e3 ON s.cont_no = e3.cont_no AND s.no_ppkb = e3.no_ppkb AND e3.operation = 'LOAD' AND e3.equipment_act_code = 'H/T' "
                                                                                                        + " LEFT JOIN equipment e4 ON s.cont_no = e4.cont_no AND s.no_ppkb = e4.no_ppkb AND e4.operation = 'LOAD' AND e4.equipment_act_code = 'STEVEDORING' "
                                                                                                        + " WHERE s.no_ppkb = ? AND s.position='01';"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingFrontByContainerNo", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, change(p.over_size), change(p.dg), change(p.dg_label), e3.equip_code, e3.start_time, e3.end_time , e2.equip_code, e2.start_time, e2.end_time,e1.equip_code, e1.start_time, e1.end_time,m1.name asalPelabuhan,m2.name as tujuanPelabuhan,mv.name,p.no_ppkb,ps.voy_in,ps.voy_out FROM m_port m1,m_port m2,planning_vessel pv,m_vessel mv,preservice_vessel ps,((((receiving_service p LEFT JOIN m_container_type c ON p.cont_type=c.cont_type) LEFT JOIN equipment e1 ON p.cont_no = e1.cont_no AND p.no_ppkb = e1.no_ppkb_recv AND e1.operation = 'LOAD' AND e1.equipment_act_code = 'RECEIVING') LEFT JOIN equipment e2 ON p.cont_no = e2.cont_no AND p.no_ppkb = e2.no_ppkb AND e2.operation = 'LOAD' AND e2.equipment_act_code = 'LIFTON') LEFT JOIN equipment e3 ON p.cont_no = e3.cont_no AND p.no_ppkb = e3.no_ppkb AND e3.operation = 'LOAD' AND e3.equipment_act_code = 'STEVEDORING') where p.cont_no LIKE ('%'|| ? ||'%') AND m1.port_code=p.load_port_code AND m2.port_code=p.disch_port_code AND p.no_ppkb=pv.no_ppkb AND pv.vessel_code=mv.vessel_code AND ps.booking_code=pv.booking_code order by p.job_slip desc"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingFrontByTrackingCont", query = "SELECT p.id,p.no_ppkb,p.bl_no,p.cont_no, p.cont_size, p.cont_status, p.cont_gross,p.seal_no,p.block,p.y_slot,p.y_row,p.y_tier, case when p.cont_no = sl.cont_no and p.no_ppkb=sl.no_ppkb then 'pos'||sl.position else 'pos'||p.position end FROM (planning_cont_load p left join service_cont_load sl on p.no_ppkb = sl.no_ppkb and p.cont_no=sl.cont_no) where p.no_ppkb=? AND p.cont_no=?  order by p.id desc limit 1"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findServiceReceivingPluggingOnly", query = "SELECT r.cont_no, r.cont_size, m.name, r.cont_status,r.cont_gross, r.seal_no,r.y_slot,r.y_row,r.y_tier,r.id,r.block,r.is_lifton,sr.job_slip,sr.no_ppkb FROM service_receiving r,m_container_type m ,plugging_reefer_service sr WHERE r.cont_type=m.cont_type AND r.cont_no=sr.cont_no AND r.id_plugging_reefer=sr.job_slip ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "ServiceReceiving.Native.findYardLoad", query = "SELECT e1.equip_code, (SELECT mopt.name FROM m_operator mopt WHERE e1.opt_code::text = mopt.opt_code::text) AS opt_name, "
            + "e1.end_time,  sr.block,  sr.y_slot, sr.y_row,  sr.y_tier, sr.created_by AS confirm_by "
            + "FROM service_receiving sr "
            + "LEFT JOIN equipment e1 ON e1.no_ppkb_recv::text = sr.no_ppkb::text AND e1.cont_no::text = sr.cont_no::text AND "
            + "e1.equipment_act_code::text = 'RECEIVING'::text AND e1.operation::text = 'LOAD'::text "
            + "WHERE sr.no_ppkb =? AND sr.cont_no=?")})
public class ServiceReceiving implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Basic(optional = false)
    @Column(name = "cont_size", nullable = false)
    private short contSize;
    @Basic(optional = false)
    @Column(name = "cont_status", nullable = false, length = 5)
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "over_size")
    private Boolean overSize;
    @Column(name = "dangerous")
    private Boolean dangerous;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Basic(optional = false)
    @Column(name = "y_slot")
    private Short ySlot;
    @Basic(optional = false)
    @Column(name = "y_row")
    private Short yRow;
    @Basic(optional = false)
    @Column(name = "y_tier")
    private Short yTier;
    @Basic(optional = false)
    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Column(name = "is_lifton")
    private Boolean isLifton;
    @Column(name = "is_behandle")
    private Boolean isBehandle;
    @Column(name = "is_cfs")
    private Boolean isCfs;
    @Column(name = "is_inspection")
    private Boolean isInspection;
    @Column(name = "ex_stuffing")
    private Boolean exStuffing;
    @Column(name = "gross_class", length = 5)
    private String grossClass;
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
    @Column(name = "status_muat", length = 20)
    private String statusMuat;
    @Column(name = "change_status")
    private Boolean changeStatus;
    @Column(name = "old_ppkb", length = 30)
    private String old_ppkb;
    //status_cancel_loading
    @Column(name = "is_change_destination")
    private Boolean isChangeDestination;
    @Column(name = "status_cancel_loading")
    private Boolean statusCancelLoading;
    @Basic(optional = false)
    @Column(name = "ex_discharge_to_load", nullable = false)
    private boolean exDischargeToLoad = false;
    @OneToOne
    @JoinColumns(value = {
        @JoinColumn(name = "cont_no", referencedColumnName = "cont_no", insertable = false, updatable = false, nullable = true),
        @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", insertable = false, updatable = false, nullable = true)
    })
    private ReceivingService receivingService;
    @Column(name = "no_ppkb", insertable=false, updatable=false)
    private String noPpkb;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Column(name = "id_plugging_reefer", length = 30)
    private String idPluggingReefer;
    @OneToOne
    @JoinColumns(value = {
        @JoinColumn(name = "cont_no", referencedColumnName = "cont_no", insertable = false, updatable = false, nullable=true),
        @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", insertable = false, updatable = false, nullable=true)
    })
    private PlanningContLoad planningContLoad;
    @Basic(optional = false)
    @Column(name = "is_sling", nullable = false)
    private boolean isSling = Boolean.FALSE;
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
    @JoinColumn(name = "port_of_delivery", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort portOfDelivery;

    public ServiceReceiving() {
    }

    public ServiceReceiving(Integer id) {
        this.id = id;
    }

    public ServiceReceiving(ServiceContDischarge serviceContDischarge) {
        this.masterCommodity = serviceContDischarge.getMasterCommodity();
        this.masterYard = serviceContDischarge.getMasterYard();
        this.masterContainerType = serviceContDischarge.getMasterContainerType();
        this.contNo = serviceContDischarge.getContNo();
        this.mlo = serviceContDischarge.getMlo();
        this.contSize = serviceContDischarge.getContSize();
        this.contStatus = serviceContDischarge.getContStatus();
        this.contGross = serviceContDischarge.getContGross();
        this.sealNo = serviceContDischarge.getSealNo();
        this.dangerous = serviceContDischarge.getDangerous();
        this.dgLabel = serviceContDischarge.getDgLabel();
        this.overSize = serviceContDischarge.getOverSize();
        this.isCfs = serviceContDischarge.getIsCfs();
        this.isBehandle = serviceContDischarge.getIsBehandle();
        this.isInspection = serviceContDischarge.getIsInspection();
        this.exStuffing = Boolean.TRUE;
        this.isLifton = Boolean.FALSE;
        this.blNo = serviceContDischarge.getBlNo();
    }

    public ServiceReceiving(Integer id, String contNo, short contSize, String contStatus, Short ySlot, Short yRow, Short yTier, Date transactionDate, MasterCustomer mlo) {
        this.id = id;
        this.contNo = contNo;
        this.mlo = mlo;
        this.contSize = contSize;
        this.contStatus = contStatus;
        this.ySlot = ySlot;
        this.yRow = yRow;
        this.yTier = yTier;
        this.transactionDate = transactionDate;
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

    public short getContSize() {
        return contSize;
    }

    public void setContSize(short contSize) {
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

    public Boolean getDangerous() {
        return dangerous;
    }

    public void setDangerous(Boolean dangerous) {
        this.dangerous = dangerous;
    }

    public Boolean getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(Boolean dgLabel) {
        this.dgLabel = dgLabel;
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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;

        if (planningVessel != null) {
            noPpkb = planningVessel.getNoPpkb();
        }
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

    public ReceivingService getReceivingService() {
        return receivingService;
    }

    /**
     * @return the isLifton
     */
    public Boolean getIsLifton() {
        return isLifton;
    }

    /**
     * @param isLifton the isLifton to set
     */
    public void setIsLifton(Boolean isLifton) {
        this.isLifton = isLifton;
    }

    public Boolean getExStuffing() {
        return exStuffing;
    }

    public void setExStuffing(Boolean exStuffing) {
        this.exStuffing = exStuffing;
    }

    public Boolean getIsBehandle() {
        return isBehandle;
    }

    public void setIsBehandle(Boolean isBehandle) {
        this.isBehandle = isBehandle;
    }

    public Boolean getIsCfs() {
        return isCfs;
    }

    public void setIsCfs(Boolean isCfs) {
        this.isCfs = isCfs;
    }

    public Boolean getIsInspection() {
        return isInspection;
    }

    public void setIsInspection(Boolean isInspection) {
        this.isInspection = isInspection;
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

    public String getStatusMuat() {
        return statusMuat;
    }

    public void setStatusMuat(String statusMuat) {
        this.statusMuat = statusMuat;
    }

    public Boolean getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(Boolean changeStatus) {
        this.changeStatus = changeStatus;
    }

    public String getOld_ppkb() {
        return old_ppkb;
    }

    public void setOld_ppkb(String old_ppkb) {
        this.old_ppkb = old_ppkb;
    }

    public Boolean getIsChangeDestination() {
        return isChangeDestination;
    }

    public void setIsChangeDestination(Boolean isChangeDestination) {
        this.isChangeDestination = isChangeDestination;
    }

    public Boolean getStatusCancelLoading() {
        return statusCancelLoading;
    }

    public void setStatusCancelLoading(Boolean statusCancelLoading) {
        this.statusCancelLoading = statusCancelLoading;
    }

    public String getNoPpkb() {
        return noPpkb;
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

    public String getIdPluggingReefer() {
        return idPluggingReefer;
    }

    public void setIdPluggingReefer(String idPluggingReefer) {
        this.idPluggingReefer = idPluggingReefer;
    }

    public PlanningContLoad getPlanningContLoad() {
        return planningContLoad;
    }

    public boolean getIsSling() {
        return isSling;
    }

    public void setIsSling(boolean isSling) {
        this.isSling = isSling;
    }

    public MasterPort getPortOfDelivery() {
        return portOfDelivery;
    }

    public void setPortOfDelivery(MasterPort portOfDelivery) {
        this.portOfDelivery = portOfDelivery;
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }

    public boolean isExDischargeToLoad() {
        return exDischargeToLoad;
    }

    public void setExDischargeToLoad(boolean exDischargeToLoad) {
        this.exDischargeToLoad = exDischargeToLoad;
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
        if (!(object instanceof ServiceReceiving)) {
            return false;
        }
        ServiceReceiving other = (ServiceReceiving) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceReceiving[id=" + id + "]";
    }
}
