/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterDock;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "planning_vessel")
@NamedQueries({
    @NamedQuery(name = "PlanningVessel.findAll", query = "SELECT p FROM PlanningVessel p"),
    @NamedQuery(name = "PlanningVessel.findByNoPpkb", query = "SELECT p FROM PlanningVessel p WHERE p.noPpkb = :noPpkb"),
    @NamedQuery(name = "PlanningVessel.findByKapalBayanganStatus", query = "SELECT p FROM PlanningVessel p WHERE p.preserviceVessel.masterVessel.kapalBayangan = :kapalBayangan"),
    @NamedQuery(name = "PlanningVessel.findByPpkbAndStatus", query = "SELECT p FROM PlanningVessel p WHERE p.noPpkb = :noPpkb AND p.status = :status"),
    @NamedQuery(name = "PlanningVessel.findByStatusses", query = "SELECT p FROM PlanningVessel p WHERE p.status IN :statusses"),
    @NamedQuery(name = "PlanningVessel.findByVesselCode", query = "SELECT p FROM PlanningVessel p WHERE p.vesselCode = :vesselCode"),
    @NamedQuery(name = "PlanningVessel.findByTipePelayaran", query = "SELECT p FROM PlanningVessel p WHERE p.tipePelayaran = :tipePelayaran"),
    @NamedQuery(name = "PlanningVessel.findByEstDischarge", query = "SELECT p FROM PlanningVessel p WHERE p.estDischarge = :estDischarge"),
    @NamedQuery(name = "PlanningVessel.findByEstLoad", query = "SELECT p FROM PlanningVessel p WHERE p.estLoad = :estLoad"),
    @NamedQuery(name = "PlanningVessel.findByEta", query = "SELECT p FROM PlanningVessel p WHERE p.eta = :eta"),
    @NamedQuery(name = "PlanningVessel.findByEbt", query = "SELECT p FROM PlanningVessel p WHERE p.ebt = :ebt"),
    @NamedQuery(name = "PlanningVessel.findByEtd", query = "SELECT p FROM PlanningVessel p WHERE p.etd = :etd"),
    @NamedQuery(name = "PlanningVessel.findByEstStartWork", query = "SELECT p FROM PlanningVessel p WHERE p.estStartWork = :estStartWork"),
    @NamedQuery(name = "PlanningVessel.findByEstEndWork", query = "SELECT p FROM PlanningVessel p WHERE p.estEndWork = :estEndWork"),
    @NamedQuery(name = "PlanningVessel.findByCloseRec", query = "SELECT p FROM PlanningVessel p WHERE p.closeRec = :closeRec"),
    @NamedQuery(name = "PlanningVessel.findByCloseDocRec", query = "SELECT p FROM PlanningVessel p WHERE p.closeDocRec = :closeDocRec"),
    @NamedQuery(name = "PlanningVessel.findByCloseEmpty", query = "SELECT p FROM PlanningVessel p WHERE p.closeEmpty = :closeEmpty"),
    @NamedQuery(name = "PlanningVessel.findByCloseReefer", query = "SELECT p FROM PlanningVessel p WHERE p.closeReefer = :closeReefer"),
    @NamedQuery(name = "PlanningVessel.findByCloseDocMtyref", query = "SELECT p FROM PlanningVessel p WHERE p.closeDocMtyref = :closeDocMtyref"),
    @NamedQuery(name = "PlanningVessel.findByPaymentType", query = "SELECT p FROM PlanningVessel p WHERE p.paymentType = :paymentType"),
    @NamedQuery(name = "PlanningVessel.findByStatus", query = "SELECT p FROM PlanningVessel p WHERE p.status = :status")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVessels", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out FROM planning_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findGatePassedContainer", query = "SELECT s.no_ppkb, s.cont_no, s.iso_code, s.cont_status, s.bl_no, s.pass_date, s.cont_gross, s.service_type "
                                                                                + "FROM (SELECT sgd.no_ppkb, sgd.cont_no, m.iso_code, sd.cont_status, sd.bl_no, sgd.date_out pass_date, sd.cont_gross, sd.service_type "
                                                                                        + "FROM service_gate_delivery sgd "
                                                                                                + "JOIN m_container_type m ON (sgd.cont_type=m.cont_type) "
                                                                                                + "JOIN (SELECT cont_no, no_ppkb, is_delivery, cont_status, bl_no, cont_gross, 'DISCHARGE'::varchar service_type FROM service_cont_discharge "
                                                                                                        + "UNION ALL "
                                                                                                        + "SELECT cont_no, no_ppkb, is_delivery, cont_status, bl_no, cont_gross, 'CANCEL_LOADING' FROM cancel_loading_service) sd ON (sgd.cont_no=sd.cont_no AND sgd.no_ppkb=sd.no_ppkb) "
                                                                                        + "WHERE sgd.date_out IS NOT NULL AND sd.is_delivery = TRUE "
                                                                                        + "UNION ALL "
                                                                                        + "SELECT sgr.no_ppkb, sgr.cont_no, m.iso_code, rs.cont_status, rs.bl_no, sgr.date_in, rs.cont_gross, 'RECEIVING' "
                                                                                        + "FROM service_gate_receiving sgr "
                                                                                                + "JOIN m_container_type m ON (sgr.cont_type=m.cont_type) "
                                                                                                + "JOIN receiving_service rs ON (sgr.cont_no=rs.cont_no AND sgr.no_ppkb=rs.no_ppkb) "
                                                                                        + "WHERE rs.status_cancel_loading = FALSE) s "
                                                                                + "WHERE s.no_ppkb = ? AND s.cont_no = ?"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselsCy", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out FROM planning_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND (pp.activity = 2 OR pp.activity = 3) AND pp.vessel_code = mv.vessel_code AND (p.status='Approved' OR p.status='Servicing' OR p.status='ReadyService') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselsReceivingConfirm", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out,p.status FROM planning_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code AND (p.status='Approved' OR p.status='ReadyService' OR p.status='Servicing') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselsServicingOnly", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out,p.status FROM planning_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code AND p.status='Servicing' ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselsSg", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out "
                                                                                    + "FROM planning_vessel p "
                                                                                            + "JOIN preservice_vessel pp ON (p.booking_code=pp.booking_code) "
                                                                                            + "JOIN m_vessel mv ON (pp.vessel_code = mv.vessel_code) "
                                                                                    + "WHERE (p.status IN ('Servicing', 'ReadyService') OR (p.status = 'Approved' AND p.enable_open_stack = TRUE)) AND p.close_rec>=now() "
                                                                                    + "ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselsSgOther", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out "
                                                                                        + "FROM planning_vessel p "
                                                                                                + "JOIN preservice_vessel pp ON (p.booking_code=pp.booking_code) "
                                                                                                + "JOIN m_vessel mv ON (pp.vessel_code = mv.vessel_code) "
                                                                                        + "WHERE p.status IN ('Approved', 'Servicing', 'ReadyService', 'Served') "
                                                                                        + "ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findCancelStatusAbleVessels", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out "
                                                                                        + "FROM planning_vessel p "
                                                                                                + "JOIN preservice_vessel pp ON (p.booking_code=pp.booking_code) "
                                                                                                + "JOIN m_vessel mv ON (pp.vessel_code = mv.vessel_code) "
                                                                                        + "WHERE p.status IN ('Servicing', 'Served') "
                                                                                        + "ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findVesselsCanBeCanceled", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out, COUNT(*) "
                                                                                        + "FROM planning_vessel p "
                                                                                                + "JOIN preservice_vessel pp ON (p.booking_code=pp.booking_code) "
                                                                                                + "JOIN m_vessel mv ON (pp.vessel_code = mv.vessel_code) "
                                                                                                + "JOIN planning_receiving_allocation prc ON (prc.no_ppkb = p.no_ppkb) "
                                                                                                        + "JOIN planning_receiving pr ON (pr.receiving_allocation_id = prc.id) "
                                                                                                + "LEFT JOIN baplie_discharge bp ON (bp.no_ppkb = p.no_ppkb) "
                                                                                        + "WHERE bp.no_ppkb IS NULL AND p.status = 'Approved' "
                                                                                        + "GROUP BY p.no_ppkb, mv.name, pp.voy_in, pp.voy_out "
                                                                                        + "HAVING COUNT(*) > 0 "
                                                                                        + "ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findConflictBerthings", query = "SELECT pv.no_ppkb FROM planning_vessel pv, "
    + "(SELECT ?::timestamp AS eta, ?::timestamp AS etd, ? AS fr_meter, ? AS to_meter) AS obj "
    + "WHERE (obj.eta BETWEEN pv.eta AND pv.etd OR obj.etd BETWEEN pv.eta AND pv.etd) "
    + "AND ((obj.fr_meter - 5 > pv.fr_meter - 5 AND obj.fr_meter - 5 < pv.to_meter + 5) OR (obj.to_meter + 5 > pv.fr_meter - 5 AND obj.to_meter + 5 < pv.to_meter + 5)) "
    + "AND pv.dock_code = ? AND pv.status NOT IN ('Cancel', 'Booking', 'Confirm') "
    + "AND pv.no_ppkb <> ?"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findBerthingsWithRange", query = "SELECT pv.no_ppkb, pv.fr_meter, pv.to_meter, pv.eta, pv.etd, mv.name FROM planning_vessel pv, m_vessel mv, "
    + "(SELECT ?::timestamp AS eta, ?::timestamp AS etd) AS obj "
    + "WHERE (pv.eta BETWEEN obj.eta AND obj.etd OR pv.etd BETWEEN obj.eta AND obj.etd) AND pv.vessel_code = mv.vessel_code AND pv.dock_code = ? AND pv.no_ppkb NOT LIKE ? AND pv.status NOT IN ('Cancel', 'Booking', 'Confirm')"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findVesselScheduleByDateRangeAndDock", query = "SELECT "
                                                                                                            + "prv.voy_in, pv.fr_meter, pv.to_meter, pv.eta, pv.etd, mv.name as vessel_name "
                                                                                                    + "FROM "
                                                                                                            + "(((planning_vessel pv "
                                                                                                                    + "JOIN m_vessel mv ON (pv.vessel_code = mv.vessel_code)) "
                                                                                                                    + "JOIN m_dock md ON (md.dock_code=pv.dock_code)) "
                                                                                                                    + "JOIN (SELECT ?::timestamp AS eta, ?::timestamp AS etd) AS obj ON (pv.eta BETWEEN obj.eta AND obj.etd OR pv.etd BETWEEN obj.eta AND obj.etd)) "
                                                                                                                    + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                                                                                    + "WHERE "
                                                                                                            + "md.dock_code = ? AND pv.status IN ('Confirm', 'Approved', 'ReadyService') AND mv.kapal_bayangan <> TRUE;"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findBerthingsBoundWithRange", query = "SELECT MIN(pv.eta) AS min_eta, MAX(pv.etd) AS max_etd FROM planning_vessel pv, "
    + "(SELECT ?::timestamp AS eta, ?::timestamp AS etd) AS obj "
    + "WHERE (pv.eta BETWEEN obj.eta AND obj.etd OR pv.etd BETWEEN obj.eta AND obj.etd) "),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselList", query = "SELECT p.no_ppkb, p.dock_code, p.booking_code, v.name, c.name, pre.voy_in, pre.voy_out, p.eta, p.etd, pre.open_stack, p.close_rec, p.status "
                                                                                    + "FROM planning_vessel p, preservice_vessel pre, m_vessel v, m_customer c "
                                                                                    + "WHERE p.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code AND pre.cust_code = c.cust_code AND (p.status = 'Confirm' OR p.status = 'Approved') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselsServicing", query = "SELECT p.no_ppkb, p.dock_code, p.booking_code, v.name, c.name, pre.voy_in, pre.voy_out, p.eta, p.etd, pre.open_stack, p.close_rec, p.status "
                                                                                    + "FROM planning_vessel p, preservice_vessel pre, m_vessel v, m_customer c "
                                                                                    + "WHERE p.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code AND pre.cust_code = c.cust_code AND (p.status = 'Servicing') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselDetail", query = "SELECT p.no_ppkb, p.dock_code, p.booking_code, v.name, c.name, pre.voy_in, pre.voy_out, p.eta, p.etd, "
                                                                                            + "mp.name, mp2.name, p.fr_meter, p.to_meter, p.est_discharge, p.est_load, p.ebt, p.est_start_work, p.est_end_work, "
                                                                                            + "pre.open_stack, p.close_rec, p.close_doc_rec, p.close_empty, p.close_reefer, p.close_doc_mtyref, p.payment_type, "
                                                                                            + "p.vessel_code, v.grt, v.dwt, v.brt, v.nrt, v.loa, pre.open_stack, pre.closing_time, d.name, p.discharge_service_type, p.load_service_type,"
                                                                                            + "p.close_uc, p.close_dg "
                                                                                        + "FROM planning_vessel p LEFT JOIN m_dock d ON (p.dock_code = d.dock_code), preservice_vessel pre, m_vessel v, m_customer c, m_port mp, m_port mp2 "
                                                                                        + "WHERE p.no_ppkb = ? AND p.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code "
                                                                                            + "AND pre.cust_code = c.cust_code AND pre.next_port_code = mp.port_code AND pre.last_port_code = mp2.port_code"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByPpkb", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out, pp.eta, pp.etd, p.fr_meter, p.to_meter, p.eta, p.etd, p.vessel_code FROM planning_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code AND p.no_ppkb = ?"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselsByActivity", query = "SELECT p.no_ppkb, m.name, r.voy_in, r.voy_out, p.check_baplie, p.status FROM planning_vessel p, preservice_vessel r, m_vessel m WHERE p.booking_code = r.booking_code AND r.vessel_code = m.vessel_code AND r.activity <> 2 AND p.status IN ('Approved', 'Servicing') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselsByActivityYardAlocation", query = "SELECT p.no_ppkb, m.name, r.voy_in, r.voy_out, p.check_baplie FROM planning_vessel p, preservice_vessel r, m_vessel m WHERE p.booking_code = r.booking_code AND r.vessel_code = m.vessel_code AND r.activity <> 1 AND p.status IN ('Confirm', 'Approved', 'Servicing', 'ReadyService') AND p.check_baplie=TRUE ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByVessel", query = "SELECT p.no_ppkb FROM planning_vessel p WHERE p.vessel_code = ? ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByDock", query = "SELECT p.no_ppkb, p.dock_code FROM planning_vessel p, m_dock d WHERE p.dock_code = d.dock_code AND d.dock_code = ? ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByVesselCode", query = "SELECT p.no_ppkb, p.booking_code FROM planning_vessel p,preservice_vessel pp WHERE p.booking_code = pp.booking_code AND p.booking_code = ? ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselServicingOnly", query = "SELECT pv.no_ppkb, mv.name "
                                                                                        + "FROM planning_vessel pv "
                                                                                                + "JOIN m_vessel mv ON (mv.vessel_code=pv.vessel_code) "
                                                                                        + "WHERE pv.status='Servicing' ORDER BY pv.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByStatus", query = "SELECT pv.no_ppkb, mv.name "
                                                                                        + "FROM planning_vessel pv "
                                                                                                + "JOIN m_vessel mv ON (mv.vessel_code=pv.vessel_code) "
                                                                                        + "WHERE pv.status = ? ORDER BY pv.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByStatusApproval", query = "SELECT p.no_ppkb, p.dock_code, p.booking_code, v.name, c.name, pre.voy_in, pre.voy_out, p.eta, p.etd, pre.open_stack, p.close_rec, p.status "
    + "FROM planning_vessel p, preservice_vessel pre, m_vessel v, m_customer c "
    + "WHERE p.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code AND pre.cust_code = c.cust_code AND (p.status = 'Approved' OR p.status = 'Servicing') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByStatusApprovalReady", query = "SELECT p.no_ppkb, p.dock_code, p.booking_code, v.name, c.name, pre.voy_in, pre.voy_out, p.eta, p.etd, pre.open_stack, p.close_rec, p.status "
    + "FROM planning_vessel p, preservice_vessel pre, m_vessel v, m_customer c "
    + "WHERE p.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code AND pre.cust_code = c.cust_code AND (p.status = 'Approved' OR p.status = 'ReadyService') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByStatusReadyService", query = "SELECT p.no_ppkb, p.dock_code, p.booking_code, v.name, c.name, pre.voy_in, pre.voy_out, p.eta, p.etd, pre.open_stack, p.close_rec, p.status "
    + "FROM planning_vessel p, preservice_vessel pre, m_vessel v, m_customer c "
    + "WHERE p.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code AND pre.cust_code = c.cust_code AND (p.status = 'ReadyService' OR p.status = 'Servicing') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByStatusAppReadyServicing", query = "SELECT p.no_ppkb, p.dock_code, p.booking_code, v.name, c.name, pre.voy_in, pre.voy_out, p.eta, p.etd, pre.open_stack, p.close_rec, p.status "
    + "FROM planning_vessel p, preservice_vessel pre, m_vessel v, m_customer c "
    + "WHERE p.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code AND pre.cust_code = c.cust_code AND (p.status = 'Approved' OR p.status = 'ReadyService' OR p.status = 'Servicing') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByActivityLoad", query = "SELECT p.no_ppkb, m.name, r.voy_in, r.voy_out FROM planning_vessel p, preservice_vessel r, m_vessel m WHERE p.booking_code = r.booking_code AND r.vessel_code = m.vessel_code AND r.activity <> 1 AND (p.status='Approved' OR p.status='Servicing' OR p.status='ReadyService') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.vesselMonitoringToday", query = "SELECT mv.name, prv.voy_in, prv.voy_out, prv.last_port_code, prv.next_port_code, pv.est_discharge, sv.discharge, pv.est_load, sv.load, pv.eta, sv.arrival_time, pv.etd, sv.departure_time, pv.close_rec, "
    + "CASE WHEN sv.status IS NULL THEN pv.status "
    + "ELSE "
    + "CASE WHEN sv.status = 'Servicing' THEN "
    + "CASE WHEN sv.is_load = true THEN sv.status || ': load' "
    + "ELSE sv.status || ': discharge' "
    + "END "
    + "ELSE sv.status "
    + "END "
    + "END AS status "
    + "FROM (((SELECT * FROM planning_vessel WHERE status IN ('Approved', 'ReadyService', 'Servicing')) pv "
    + "LEFT JOIN service_vessel sv ON (pv.no_ppkb=sv.no_ppkb)) "
    + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code AND mv.kapal_bayangan = FALSE)) "
    + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
    + "WHERE (EXTRACT(DOY FROM ?::timestamp) >= EXTRACT(DOY FROM pv.eta) AND EXTRACT(DOY FROM ?::timestamp) <= EXTRACT(DOY FROM sv.departure_time)) OR sv.departure_time IS NULL "
    + "ORDER BY pv.eta"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByStatusApprovalAndDock", query = "SELECT prv.voy_in, mv.name, pv.eta, pv.etd, pv.fr_meter, pv.to_meter "
                                                                                                        + "FROM planning_vessel pv "
                                                                                                                + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                                                                                                + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
                                                                                                        + "WHERE pv.dock_code=? AND pv.status IN ('Approved') AND mv.kapal_bayangan <> TRUE;"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findBooking", query = "SELECT est_load FROM planning_vessel WHERE no_ppkb=?"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findCurrCode", query = "SELECT tipe_pelayaran FROM planning_vessel WHERE no_ppkb=?"),
    @NamedNativeQuery(name = "PlanningVessel.Native.vesselMonitoringOnline", query = "SELECT mv.name, prv.voy_in, prv.voy_out, loadPort.name, dischPort.name, pv.est_discharge, sv.discharge, pv.est_load, sv.load, pv.eta, sv.arrival_time, pv.etd, sv.departure_time, sv.close_rec,m.name,CASE WHEN sv.status IS NULL THEN served(pv.status) ELSE served(sv.status) END AS status "
                                                                                    + "FROM "
                                                                                            + "planning_vessel pv "
                                                                                                    + "LEFT JOIN service_vessel sv ON (pv.no_ppkb=sv.no_ppkb) "
                                                                                                    + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
                                                                                                    + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                                                                                            + "JOIN m_customer m on(prv.cust_code=m.cust_code) "
                                                                                                            + "JOIN m_port loadPort ON (prv.last_port_code = loadPort.port_code) "
                                                                                                            + "JOIN m_port dischPort ON (prv.next_port_code = dischPort.port_code) "
                                                                                    + "WHERE prv.cust_code = ? "
                                                                                    + "ORDER BY pv.created_date DESC;"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findTipePelayaranByPPKB", query = "SELECT tipe_pelayaran FROM planning_vessel WHERE no_ppkb=?"),
    @NamedNativeQuery(name = "PlanningVessel.Native.generateId", query = "SELECT MAX(substring(no_ppkb,5,6)) FROM planning_vessel WHERE substring(no_ppkb,1,2) = ?"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findReadyServicesMobile", query = "SELECT pv.booking_code, v.name, d.dock_code, pv.fr_meter, pv.to_meter, pv.est_discharge, pv.est_load, to_char(pv.eta, 'dd/MM/yyyy HH:mm') as a, v.loa  FROM planning_vessel pv, m_vessel v, m_dock d WHERE v.vessel_code = pv.vessel_code and d.dock_code = pv.dock_code AND pv.no_ppkb = ? AND pv.status = 'ReadyService'"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselsByFinished", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out FROM planning_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code AND p.baplie_discharge=true ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselsByFinishedNotServed", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out FROM planning_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code AND p.baplie_discharge=true ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselsHasApprovedtoLoad", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out FROM planning_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code AND p.status IN ('Approved', 'ReadyService', 'Servicing') AND pp.activity <> 1 ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPpkbNumbers", query = "SELECT no_ppkb FROM planning_vessel WHERE upper(no_ppkb) LIKE ?"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselExcConfirm", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out FROM planning_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code AND (p.status = 'Approved' OR p.status = 'ReadyService' OR p.status = 'Servicing' OR p.status = 'Served') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByBeritaAcara", query = "SELECT p.no_ppkb, p.dock_code, p.booking_code, v.name, c.name, pre.voy_in, pre.voy_out, p.eta, p.etd, pre.open_stack, p.close_rec, p.status FROM planning_vessel p, preservice_vessel pre, m_vessel v, m_customer c,service_vessel sv WHERE p.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code AND pre.cust_code = c.cust_code AND sv.status = 'Served' AND sv.no_ppkb=? AND p.no_ppkb=sv.no_ppkb ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findPlanningVesselByPluggingToLoad", query = "select pv.no_ppkb, mv.name from planning_vessel pv,m_vessel mv where mv.vessel_code=pv.vessel_code AND (pv.status='Approve' OR pv.status='Servicing' OR pv.status='ReadyService') ORDER BY pv.no_ppkb DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findCanceledPpkb", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out "
                                                                                + "FROM planning_vessel p, preservice_vessel pp, m_vessel mv "
                                                                                + "WHERE p.booking_code=pp.booking_code AND (pp.activity = 2 OR pp.activity = 3) AND pp.vessel_code = mv.vessel_code AND (p.status = 'Canceled') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "PlanningVessel.Native.findReceivingAllocationCountByNoPpkb", query = "SELECT COUNT(*)::int "
                                                                                + "FROM planning_receiving_allocation pra "
                                                                                        + "JOIN planning_receiving pr ON (pra.id=pr.receiving_allocation_id) "
                                                                                + "WHERE pra.no_ppkb = ?")})
public class PlanningVessel implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    public static final String CY_TO_CY = "CTC";
    public static final String PORT_TO_PORT = "PTP";
    public static final String DOMESTIK = "d";
    public static final String INTERNASONAL = "i";


    @Id
    @Basic(optional = false)
    @Column(name = "no_ppkb", nullable = false, length = 30)
    private String noPpkb;
    @Column(name = "vessel_code", length = 10)
    private String vesselCode;
    @Column(name = "tipe_pelayaran", length = 15)
    private String tipePelayaran = DOMESTIK;
    @Column(name = "discharge_service_type", length = 10)
    private String dischargeServiceType;
    @Column(name = "load_service_type", length = 10)
    private String loadServiceType;
    @Column(name = "est_discharge")
    private Short estDischarge = 0;
    @Column(name = "est_load")
    private Short estLoad = 0;
    @Column(name = "eta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eta;
    @Column(name = "ebt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ebt;
    @Column(name = "etd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date etd;
    @Column(name = "est_start_work")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estStartWork;
    @Column(name = "est_end_work")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estEndWork;
    @Column(name = "close_rec")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeRec;
    @Column(name = "close_doc_rec")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDocRec;
    @Column(name = "close_empty")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeEmpty;
    @Column(name = "close_reefer")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeReefer;
    @Column(name = "close_doc_mtyref")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDocMtyref;
    @Column(name = "close_uc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeUc;
    @Column(name = "close_dg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDg;
    @Column(name = "fr_meter")
    private Short frMeter;
    @Column(name = "to_meter")
    private Short toMeter;
    @Column(name = "payment_type", length = 10)
    private String paymentType;
    @Column(name = "status", length = 15)
    private String status;
    @Column(name = "check_baplie")
    private Boolean checkBaplie;
    @Column(name = "baplie_discharge")
    private Boolean baplieDischarge;
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
    @Column(name = "enable_open_stack")
    private Boolean enableOpenStack = false;
    @OneToOne
    @JoinColumns(value = {
        @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", insertable = false, updatable = false)
    })
    private ServiceVessel serviceVessel;
    @OneToMany(mappedBy = "planningVessel")
    private List<ReceivingService> receivingServiceList;
    @OneToMany(mappedBy = "planningVessel")
    private List<ReeferLoadService> reeferLoadServiceList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningucShiftingPlacement> planningucShiftingPlacementList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningUcTrans> planningUcTransList;
    @OneToMany(mappedBy = "planningVessel")
    private List<ServiceReceiving> serviceReceivingList;
    @JoinColumn(name = "booking_code", referencedColumnName = "booking_code")
    @OneToOne
    private PreserviceVessel preserviceVessel;
    @JoinColumn(name = "dock_code", referencedColumnName = "dock_code")
    @ManyToOne
    private MasterDock masterDock;
    @Column(name = "open_stack")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openStack;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningUncontainerized> planningUncontainerizedList;
    @OneToMany(mappedBy = "planningVessel")
    private List<AngsurService> angsurServiceList;
    @OneToMany(mappedBy = "planningVessel")
    private List<Payment> paymentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planningVessel")
    private List<PlanningContLoad> planningContLoadList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningTransLoad> planningTransLoadList;
    @OneToMany(mappedBy = "planningVessel")
    private List<DeliveryService> deliveryServiceList;
    @OneToMany(mappedBy = "planningVessel")
    private List<HandlingInvoice> handlingInvoiceList;
    @OneToMany(mappedBy = "planningVessel")
    private List<BaplieDischarge> baplieDischargeList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningShiftDischarge> planningShiftDischargeList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningUcTransload> planningUcTransloadList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningUcShifting> planningUcShiftingList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningContDischarge> planningContDischargeList;
    @OneToMany(mappedBy = "planningVessel")
    private List<ChangeStatusService> changeStatusServiceList;
    @OneToMany(mappedBy = "planningVessel")
    private List<ReceivingUc> receivingUcList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningUcLoad> planningUcLoadList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PenumpukanSusulanService> penumpukanSusulanServiceList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningContReceiving> planningContReceivingList;
    @OneToMany(mappedBy = "planningVessel")
    private List<ReeferDischargeService> reeferDischargeServiceList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningReceivingAllocation> planningReceivingAllocationList;
    @OneToMany(mappedBy = "planningVessel")
    private List<Registration> registrationList;
    @OneToMany(mappedBy = "planningVessel")
    private List<PlanningTransDischarge> planningTransDischargeList;
    @OneToMany(mappedBy = "planningVessel1")
    private List<PlanningTransDischarge> planningTransDischargeList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planningVessel")
    private List<PerhitunganPassGate> perhitunganPassGateList;

    public PlanningVessel() {
        loadServiceType = CY_TO_CY;
        dischargeServiceType = CY_TO_CY;
        tipePelayaran = DOMESTIK;
    }

    public PlanningVessel(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getTipePelayaran() {
        return tipePelayaran;
    }

    public void setTipePelayaran(String tipePelayaran) {
        this.tipePelayaran = tipePelayaran;
    }

    public Short getEstDischarge() {
        return estDischarge;
    }

    public void setEstDischarge(Short estDischarge) {
        this.estDischarge = estDischarge;
    }

    public Short getEstLoad() {
        return estLoad;
    }

    public void setEstLoad(Short estLoad) {
        this.estLoad = estLoad;
    }

    public Date getEta() {
        return eta;
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    public Date getEbt() {
        return ebt;
    }

    public void setEbt(Date ebt) {
        this.ebt = ebt;
    }

    public Date getEtd() {
        return etd;
    }

    public void setEtd(Date etd) {
        this.etd = etd;
    }

    public Date getEstStartWork() {
        return estStartWork;
    }

    public void setEstStartWork(Date estStartWork) {
        this.estStartWork = estStartWork;
    }

    public Date getEstEndWork() {
        return estEndWork;
    }

    public void setEstEndWork(Date estEndWork) {
        this.estEndWork = estEndWork;
    }

    public Date getCloseRec() {
        return closeRec;
    }

    public void setCloseRec(Date closeRec) {
        this.closeRec = closeRec;
    }

    public Date getCloseDocRec() {
        return closeDocRec;
    }

    public void setCloseDocRec(Date closeDocRec) {
        this.closeDocRec = closeDocRec;
    }

    public Date getCloseEmpty() {
        return closeEmpty;
    }

    public void setCloseEmpty(Date closeEmpty) {
        this.closeEmpty = closeEmpty;
    }

    public Date getCloseReefer() {
        return closeReefer;
    }

    public void setCloseReefer(Date closeReefer) {
        this.closeReefer = closeReefer;
    }

    public Date getCloseDocMtyref() {
        return closeDocMtyref;
    }

    public void setCloseDocMtyref(Date closeDocMtyref) {
        this.closeDocMtyref = closeDocMtyref;
    }

    public Short getFrMeter() {
        return frMeter;
    }

    public void setFrMeter(Short frMeter) {
        this.frMeter = frMeter;
    }

    public Short getToMeter() {
        return toMeter;
    }

    public void setToMeter(Short toMeter) {
        this.toMeter = toMeter;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the checkBaplie
     */
    public Boolean getCheckBaplie() {
        return checkBaplie;
    }

    /**
     * @param checkBaplie the checkBaplie to set
     */
    public void setCheckBaplie(Boolean checkBaplie) {
        this.checkBaplie = checkBaplie;
    }

    /**
     * @return the BaplieDischarge
     */
    public Boolean getBaplieDischarge() {
        return baplieDischarge;
    }

    /**
     * @param BaplieDischarge the BaplieDischarge to set
     */
    public void setBaplieDischarge(Boolean baplieDischarge) {
        this.baplieDischarge = baplieDischarge;
    }

    public List<ReceivingService> getReceivingServiceList() {
        return receivingServiceList;
    }

    public void setReceivingServiceList(List<ReceivingService> receivingServiceList) {
        this.receivingServiceList = receivingServiceList;
    }

    public List<ReeferLoadService> getReeferLoadServiceList() {
        return reeferLoadServiceList;
    }

    public void setReeferLoadServiceList(List<ReeferLoadService> reeferLoadServiceList) {
        this.reeferLoadServiceList = reeferLoadServiceList;
    }

    public List<PlanningucShiftingPlacement> getPlanningucShiftingPlacementList() {
        return planningucShiftingPlacementList;
    }

    public void setPlanningucShiftingPlacementList(List<PlanningucShiftingPlacement> planningucShiftingPlacementList) {
        this.planningucShiftingPlacementList = planningucShiftingPlacementList;
    }

    public List<PlanningUcTrans> getPlanningUcTransList() {
        return planningUcTransList;
    }

    public void setPlanningUcTransList(List<PlanningUcTrans> planningUcTransList) {
        this.planningUcTransList = planningUcTransList;
    }

    public List<ServiceReceiving> getServiceReceivingList() {
        return serviceReceivingList;
    }

    public void setServiceReceivingList(List<ServiceReceiving> serviceReceivingList) {
        this.serviceReceivingList = serviceReceivingList;
    }

    public PreserviceVessel getPreserviceVessel() {
        return preserviceVessel;
    }

    public void setPreserviceVessel(PreserviceVessel preserviceVessel) {
        this.preserviceVessel = preserviceVessel;
    }

    public MasterDock getMasterDock() {
        return masterDock;
    }

    public void setMasterDock(MasterDock masterDock) {
        this.masterDock = masterDock;
    }

    public List<PlanningUncontainerized> getPlanningUncontainerizedList() {
        return planningUncontainerizedList;
    }

    public void setPlanningUncontainerizedList(List<PlanningUncontainerized> planningUncontainerizedList) {
        this.planningUncontainerizedList = planningUncontainerizedList;
    }

    public List<AngsurService> getAngsurServiceList() {
        return angsurServiceList;
    }

    public void setAngsurServiceList(List<AngsurService> angsurServiceList) {
        this.angsurServiceList = angsurServiceList;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<PlanningContLoad> getPlanningContLoadList() {
        return planningContLoadList;
    }

    public void setPlanningContLoadList(List<PlanningContLoad> planningContLoadList) {
        this.planningContLoadList = planningContLoadList;
    }

    public List<PlanningTransLoad> getPlanningTransLoadList() {
        return planningTransLoadList;
    }

    public void setPlanningTransLoadList(List<PlanningTransLoad> planningTransLoadList) {
        this.planningTransLoadList = planningTransLoadList;
    }

    public List<DeliveryService> getDeliveryServiceList() {
        return deliveryServiceList;
    }

    public void setDeliveryServiceList(List<DeliveryService> deliveryServiceList) {
        this.deliveryServiceList = deliveryServiceList;
    }

    public List<HandlingInvoice> getHandlingInvoiceList() {
        return handlingInvoiceList;
    }

    public void setHandlingInvoiceList(List<HandlingInvoice> handlingInvoiceList) {
        this.handlingInvoiceList = handlingInvoiceList;
    }

    public List<BaplieDischarge> getBaplieDischargeList() {
        return baplieDischargeList;
    }

    public void setBaplieDischargeList(List<BaplieDischarge> baplieDischargeList) {
        this.baplieDischargeList = baplieDischargeList;
    }

    public List<PlanningShiftDischarge> getPlanningShiftDischargeList() {
        return planningShiftDischargeList;
    }

    public void setPlanningShiftDischargeList(List<PlanningShiftDischarge> planningShiftDischargeList) {
        this.planningShiftDischargeList = planningShiftDischargeList;
    }

    public List<PlanningUcTransload> getPlanningUcTransloadList() {
        return planningUcTransloadList;
    }

    public void setPlanningUcTransloadList(List<PlanningUcTransload> planningUcTransloadList) {
        this.planningUcTransloadList = planningUcTransloadList;
    }

    public List<PlanningUcShifting> getPlanningUcShiftingList() {
        return planningUcShiftingList;
    }

    public void setPlanningUcShiftingList(List<PlanningUcShifting> planningUcShiftingList) {
        this.planningUcShiftingList = planningUcShiftingList;
    }

    public List<PlanningContDischarge> getPlanningContDischargeList() {
        return planningContDischargeList;
    }

    public void setPlanningContDischargeList(List<PlanningContDischarge> planningContDischargeList) {
        this.planningContDischargeList = planningContDischargeList;
    }

    public List<ChangeStatusService> getChangeStatusServiceList() {
        return changeStatusServiceList;
    }

    public void setChangeStatusServiceList(List<ChangeStatusService> changeStatusServiceList) {
        this.changeStatusServiceList = changeStatusServiceList;
    }

    public List<ReceivingUc> getReceivingUcList() {
        return receivingUcList;
    }

    public void setReceivingUcList(List<ReceivingUc> receivingUcList) {
        this.receivingUcList = receivingUcList;
    }

    public List<PlanningUcLoad> getPlanningUcLoadList() {
        return planningUcLoadList;
    }

    public void setPlanningUcLoadList(List<PlanningUcLoad> planningUcLoadList) {
        this.planningUcLoadList = planningUcLoadList;
    }

    public List<PenumpukanSusulanService> getPenumpukanSusulanServiceList() {
        return penumpukanSusulanServiceList;
    }

    public void setPenumpukanSusulanServiceList(List<PenumpukanSusulanService> penumpukanSusulanServiceList) {
        this.penumpukanSusulanServiceList = penumpukanSusulanServiceList;
    }
    
    public List<PlanningContReceiving> getPlanningContReceivingList() {
        return planningContReceivingList;
    }

    public void setPlanningContReceivingList(List<PlanningContReceiving> planningContReceivingList) {
        this.planningContReceivingList = planningContReceivingList;
    }

    public List<ReeferDischargeService> getReeferDischargeServiceList() {
        return reeferDischargeServiceList;
    }

    public void setReeferDischargeServiceList(List<ReeferDischargeService> reeferDischargeServiceList) {
        this.reeferDischargeServiceList = reeferDischargeServiceList;
    }

    public List<PlanningReceivingAllocation> getPlanningReceivingAllocationList() {
        return planningReceivingAllocationList;
    }

    public void setPlanningReceivingAllocationList(List<PlanningReceivingAllocation> planningReceivingAllocationList) {
        this.planningReceivingAllocationList = planningReceivingAllocationList;
    }

    public List<Registration> getRegistrationList() {
        return registrationList;
    }

    public void setRegistrationList(List<Registration> registrationList) {
        this.registrationList = registrationList;
    }

    public List<PlanningTransDischarge> getPlanningTransDischargeList() {
        return planningTransDischargeList;
    }

    public void setPlanningTransDischargeList(List<PlanningTransDischarge> planningTransDischargeList) {
        this.planningTransDischargeList = planningTransDischargeList;
    }

    public List<PlanningTransDischarge> getPlanningTransDischargeList1() {
        return planningTransDischargeList1;
    }

    public void setPlanningTransDischargeList1(List<PlanningTransDischarge> planningTransDischargeList1) {
        this.planningTransDischargeList1 = planningTransDischargeList1;
    }

    public List<PerhitunganPassGate> getPerhitunganPassGateList() {
        return perhitunganPassGateList;
    }

    public void setPerhitunganPassGateList(List<PerhitunganPassGate> perhitunganPassGateList) {
        this.perhitunganPassGateList = perhitunganPassGateList;
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

    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    public Date getOpenStack() {
        return openStack;
    }

    public void setOpenStack(Date openStack) {
        this.openStack = openStack;
    }

    public String getDischargeServiceType() {
        return dischargeServiceType;
    }

    public void setDischargeServiceType(String dischargeServiceType) {
        this.dischargeServiceType = dischargeServiceType;
    }

    public String getLoadServiceType() {
        return loadServiceType;
    }

    public void setLoadServiceType(String loadServiceType) {
        this.loadServiceType = loadServiceType;
    }

    public Boolean getEnableOpenStack() {
        return enableOpenStack;
    }

    public void setEnableOpenStack(Boolean enableOpenStack) {
        this.enableOpenStack = enableOpenStack;
    }

    public Boolean getIsLoadCyToCy() {
        if (serviceVessel != null) {
            return serviceVessel.getIsLoadCyToCy();
        } else if (loadServiceType != null) {
            return loadServiceType.equals(CY_TO_CY);
        }

        return false;
    }

    public Boolean getIsLoadPortToPort() {
        if (serviceVessel != null) {
            return serviceVessel.getIsLoadPortToPort();
        } else if (loadServiceType != null) {
            return loadServiceType.equals(PORT_TO_PORT);
        }

        return false;
    }

    public Boolean getIsDischargeCyToCy() {
        if (serviceVessel != null) {
            return serviceVessel.getIsDischargeCyToCy();
        } else if (dischargeServiceType != null) {
            return dischargeServiceType.equals(CY_TO_CY);
        }

        return false;
    }

    public Boolean getIsDischargePortToPort() {
        if (serviceVessel != null) {
            return serviceVessel.getIsDischargePortToPort();
        } else if (dischargeServiceType != null) {
            return dischargeServiceType.equals(PORT_TO_PORT);
        }

        return false;
    }

    public Boolean getIsClosingDocExpired() {
        if (closeDocRec != null) {
            Date now = new Date();
            if (now.compareTo(closeDocRec) > 0) {
                return true;
            }
        }

        return false;
    }

    public Boolean getIsClosingRecExpired() {
        if (closeRec != null) {
            Date now = new Date();
            if (now.compareTo(closeRec) > 0) {
                return true;
            }
        }

        return false;
    }

    public Boolean getIsClosingDocReeferExpired() {
        if (closeDocMtyref != null) {
            Date now = new Date();
            if (now.compareTo(closeDocMtyref) > 0) {
                return true;
            }
        }

        return false;
    }

    public Boolean isClosingTimeChanged(PlanningVessel planningVessel) {
        if (planningVessel != null && planningVessel.getCloseDocMtyref() != null && planningVessel.getCloseDocRec() != null && planningVessel.getCloseEmpty() != null && planningVessel.getCloseRec() != null && planningVessel.getCloseReefer() != null &&
                closeDocMtyref != null && closeDocRec != null && closeEmpty != null && closeRec != null && closeReefer != null) {
            return !planningVessel.getCloseDocMtyref().equals(closeDocMtyref) || !planningVessel.getCloseDocRec().equals(closeDocRec) || !planningVessel.getCloseEmpty().equals(closeEmpty) || !planningVessel.getCloseRec().equals(closeRec) || !planningVessel.getCloseReefer().equals(closeReefer);
        }

        return false;
    }

    public Boolean getIsClosingRecReeferExpired() {
        if (closeReefer != null) {
            Date now = new Date();
            if (now.compareTo(closeReefer) > 0) {
                return true;
            }
        }

        return false;
    }

    public Date getCloseUc() {
        return closeUc;
    }

    public void setCloseUc(Date closeUc) {
        this.closeUc = closeUc;
    }

    public Date getCloseDg() {
        return closeDg;
    }

    public void setCloseDg(Date closeDg) {
        this.closeDg = closeDg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noPpkb != null ? noPpkb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanningVessel)) {
            return false;
        }
        PlanningVessel other = (PlanningVessel) object;
        if ((this.noPpkb == null && other.noPpkb != null) || (this.noPpkb != null && !this.noPpkb.equals(other.noPpkb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningVessel[noPpkb=" + noPpkb + "]";
    }
}
