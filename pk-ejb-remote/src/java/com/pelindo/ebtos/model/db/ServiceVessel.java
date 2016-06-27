/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
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
@Table(name = "service_vessel")
@NamedQueries({
    @NamedQuery(name = "ServiceVessel.findAll", query = "SELECT s FROM ServiceVessel s"),
    @NamedQuery(name = "ServiceVessel.findByNoPpkb", query = "SELECT s FROM ServiceVessel s WHERE s.noPpkb = :noPpkb"),
    @NamedQuery(name = "ServiceVessel.findByDockCode", query = "SELECT s FROM ServiceVessel s WHERE s.dockCode = :dockCode"),
    @NamedQuery(name = "ServiceVessel.findByBookingCode", query = "SELECT s FROM ServiceVessel s WHERE s.bookingCode = :bookingCode"),
    @NamedQuery(name = "ServiceVessel.findByTipePelayaran", query = "SELECT s FROM ServiceVessel s WHERE s.tipePelayaran = :tipePelayaran"),
    @NamedQuery(name = "ServiceVessel.findByPpkbAndStatus", query = "SELECT s FROM ServiceVessel s WHERE s.noPpkb = :noPpkb AND s.status = :status"),
    @NamedQuery(name = "ServiceVessel.findByEstDischarge", query = "SELECT s FROM ServiceVessel s WHERE s.estDischarge = :estDischarge"),
    @NamedQuery(name = "ServiceVessel.findByEstLoad", query = "SELECT s FROM ServiceVessel s WHERE s.estLoad = :estLoad"),
    @NamedQuery(name = "ServiceVessel.findByEta", query = "SELECT s FROM ServiceVessel s WHERE s.eta = :eta"),
    @NamedQuery(name = "ServiceVessel.findByEbt", query = "SELECT s FROM ServiceVessel s WHERE s.ebt = :ebt"),
    @NamedQuery(name = "ServiceVessel.findByEtd", query = "SELECT s FROM ServiceVessel s WHERE s.etd = :etd"),
    @NamedQuery(name = "ServiceVessel.findByEstStartWork", query = "SELECT s FROM ServiceVessel s WHERE s.estStartWork = :estStartWork"),
    @NamedQuery(name = "ServiceVessel.findByEstEndWork", query = "SELECT s FROM ServiceVessel s WHERE s.estEndWork = :estEndWork"),
    @NamedQuery(name = "ServiceVessel.findByCloseRec", query = "SELECT s FROM ServiceVessel s WHERE s.closeRec = :closeRec"),
    @NamedQuery(name = "ServiceVessel.findByCloseDocRec", query = "SELECT s FROM ServiceVessel s WHERE s.closeDocRec = :closeDocRec"),
    @NamedQuery(name = "ServiceVessel.findByCloseEmpty", query = "SELECT s FROM ServiceVessel s WHERE s.closeEmpty = :closeEmpty"),
    @NamedQuery(name = "ServiceVessel.findByCloseReefer", query = "SELECT s FROM ServiceVessel s WHERE s.closeReefer = :closeReefer"),
    @NamedQuery(name = "ServiceVessel.findByCloseDocMtyref", query = "SELECT s FROM ServiceVessel s WHERE s.closeDocMtyref = :closeDocMtyref"),
    @NamedQuery(name = "ServiceVessel.findByDischarge", query = "SELECT s FROM ServiceVessel s WHERE s.discharge = :discharge"),
    @NamedQuery(name = "ServiceVessel.findByLoad", query = "SELECT s FROM ServiceVessel s WHERE s.load = :load"),
    @NamedQuery(name = "ServiceVessel.findByArrivalTime", query = "SELECT s FROM ServiceVessel s WHERE s.arrivalTime = :arrivalTime"),
    @NamedQuery(name = "ServiceVessel.findByBerhtingTime", query = "SELECT s FROM ServiceVessel s WHERE s.berhtingTime = :berhtingTime"),
    @NamedQuery(name = "ServiceVessel.findByDepartureTime", query = "SELECT s FROM ServiceVessel s WHERE s.departureTime = :departureTime"),
    @NamedQuery(name = "ServiceVessel.findByStartWorkTime", query = "SELECT s FROM ServiceVessel s WHERE s.startWorkTime = :startWorkTime"),
    @NamedQuery(name = "ServiceVessel.findByEndWorkTime", query = "SELECT s FROM ServiceVessel s WHERE s.endWorkTime = :endWorkTime"),
    @NamedQuery(name = "ServiceVessel.findByPaymentType", query = "SELECT s FROM ServiceVessel s WHERE s.paymentType = :paymentType"),
    @NamedQuery(name = "ServiceVessel.findByStatus", query = "SELECT s FROM ServiceVessel s WHERE s.status = :status")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselByOnline", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out,p.eta,p.arrival_time,p.etd,p.departure_time,p.close_rec,served(p.status),m.name, p.status FROM service_vessel p, preservice_vessel pp, m_vessel mv,m_customer m where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code AND pp.cust_code=m.cust_code AND pp.cust_code=? ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselByStatusServicing", query = "SELECT s.no_ppkb, v.name, mp.name, mp2.name, pre.voy_in, pre.voy_out, s.est_discharge, s.discharge, s.est_load, s.load, s.eta, s.arrival_time, s.etd, s.departure_time, s.status "
    + "FROM service_vessel s, planning_vessel p, preservice_vessel pre, m_vessel v, m_port mp, m_port mp2 "
    + "WHERE s.no_ppkb = p.no_ppkb AND s.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code AND pre.last_port_code = mp.port_code AND pre.next_port_code = mp2.port_code AND s.status = 'Servicing' "
    + "ORDER BY s.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findPlanningVesselByStatusService", query = "SELECT s.no_ppkb, v.name, c.name, pre.voy_in, pre.voy_out, s.discharge, s.load, s.arrival_time, s.departure_time, s.status "
                                                                                            + "FROM service_vessel s, planning_vessel p, preservice_vessel pre, m_vessel v, m_customer c "
                                                                                            + "WHERE s.no_ppkb = p.no_ppkb AND s.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code AND pre.cust_code = c.cust_code AND (s.status = 'Servicing' OR s.status = 'Served') "
                                                                                            + "ORDER BY s.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findPlanningVesselStatusServed", query = "SELECT s.no_ppkb, v.name, c.name, pre.voy_in, pre.voy_out, s.discharge, s.load, s.arrival_time, s.departure_time, s.status "
    + "FROM service_vessel s, planning_vessel p, preservice_vessel pre, m_vessel v, m_customer c "
    + "WHERE s.no_ppkb = p.no_ppkb AND s.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code AND pre.cust_code = c.cust_code AND s.status = 'Served' "
    + "ORDER BY s.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselByPpkb", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out, pp.vessel_code, p.est_discharge, p.close_rec, pp.cust_code "
                                                                                    + "FROM service_vessel p "
                                                                                            + "JOIN preservice_vessel pp ON (p.booking_code=pp.booking_code) "
                                                                                            + "JOIN m_vessel mv ON (pp.vessel_code = mv.vessel_code) "
                                                                                    + "WHERE p.no_ppkb = ? AND p.status IN ('Approved', 'Servicing', 'ReadyService', 'Served')"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVessels", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out, p.booking_code, p.arrival_time, p.berhting_time, p.departure_time, p.start_work_time, p.end_work_time, CASE WHEN p.status = 'Served' THEN 'End Service' ELSE p.status END FROM service_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsByMonthAndYear", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out, p.booking_code, p.arrival_time, p.berhting_time, p.departure_time, p.start_work_time, p.end_work_time, CASE WHEN p.status = 'Served' THEN 'End Service' ELSE p.status END FROM service_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code AND EXTRACT(MONTH FROM p.created_date) = ? AND EXTRACT(YEAR FROM p.created_date) = ? ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsServicing", query = "SELECT sv.no_ppkb, mv.name, pv.voy_in, pv.voy_out FROM service_vessel sv, preservice_vessel pv, m_vessel mv WHERE sv.booking_code = pv.booking_code AND pv.vessel_code = mv.vessel_code AND sv.status = 'Servicing' ORDER BY sv.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsServiced", query = "SELECT sv.no_ppkb, mv.name, pv.voy_in, pv.voy_out FROM service_vessel sv, preservice_vessel pv, m_vessel mv WHERE sv.booking_code = pv.booking_code AND pv.vessel_code = mv.vessel_code AND sv.status = 'Served' ORDER BY sv.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsServicedDischarge", query = "SELECT sv.no_ppkb, mv.name, pv.voy_in, pv.voy_out FROM service_vessel sv, preservice_vessel pv, m_vessel mv WHERE sv.booking_code = pv.booking_code AND pv.vessel_code = mv.vessel_code AND sv.status = 'Served' AND (pv.activity = 1 OR pv.activity = 3) ORDER BY sv.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsServicedLoad", query = "SELECT sv.no_ppkb, mv.name, pv.voy_in, pv.voy_out FROM service_vessel sv, preservice_vessel pv, m_vessel mv WHERE sv.booking_code = pv.booking_code AND pv.vessel_code = mv.vessel_code AND sv.status = 'Served' AND (pv.activity = 2 OR pv.activity = 3) ORDER BY sv.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselDetail", query = "SELECT s.no_ppkb, p.booking_code, v.name, v.grt, v.dwt, v.brt, v.nrt, v.loa, c.name, pre.voy_in, pre.voy_out, "
    + "s.est_discharge, s.est_load, s.discharge, s.load, d.name, p.fr_meter, p.to_meter, "
    + "s.eta, s.ebt, s.est_start_work, s.est_end_work, s.etd, "
    + "s.arrival_time, s.berhting_time, s.departure_time, s.start_work_time, s.end_work_time, "
    + "s.close_rec, s.close_doc_rec, s.close_empty, s.close_reefer, s.close_doc_mtyref, s.fr_meter, s.to_meter "
    + "FROM planning_vessel p LEFT JOIN m_dock d ON (p.dock_code = d.dock_code), preservice_vessel pre, m_vessel v, m_customer c, service_vessel s "
    + "WHERE s.no_ppkb = p.no_ppkb AND s.no_ppkb = ? AND p.booking_code = pre.booking_code AND p.vessel_code = v.vessel_code AND pre.cust_code = c.cust_code"),
    @NamedNativeQuery(name = "ServiceVessel.Native.checkSpaceAvailability", query = "SELECT sv.no_ppkb, sv.fr_meter, sv.to_meter FROM service_vessel sv, "
    + "(SELECT ? AS fr_meter,? AS to_meter) AS obj "
    + "WHERE (((obj.fr_meter - 5 > sv.fr_meter - 5 AND obj.fr_meter - 5 < sv.to_meter + 5) OR (obj.to_meter + 5 > sv.fr_meter - 5 AND obj.to_meter + 5 < sv.to_meter + 5)) "
    + "OR ((sv.fr_meter - 5 > obj.fr_meter - 5 AND sv.fr_meter - 5 < obj.to_meter + 5) OR (sv.to_meter + 5 > obj.fr_meter - 5 AND sv.to_meter + 5 < obj.to_meter + 5)) "
    + "OR (sv.fr_meter - 5 = obj.fr_meter - 5 AND sv.to_meter + 5 = obj.to_meter + 5)) "
    + "AND sv.dock_code = ? AND sv.departure_time IS NOT NULL AND sv.no_ppkb <> ?"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findTruckDestinationByAppKey", query = "SELECT e.cont_no, "
                                                                                                + "CASE WHEN e.operation='DISCHARGE' OR e.operation='SHIFTING-CY' OR e.operation='TRANSDISCHARGE' THEN "
                                                                                                        + "'Block ' || scd.block "
                                                                                                + "WHEN e.operation='LOAD' OR e.operation='LD-SHIFTING-CY' OR e.operation='TRANSHIPMENT' THEN "
                                                                                                        + "mdc.name || ' [' || mdc.dock_code || ']' "
                                                                                                + "END AS destination1, "
                                                                                                + "CASE WHEN e.operation='DISCHARGE' OR e.operation='SHIFTING-CY' OR e.operation='TRANSDISCHARGE' THEN "
                                                                                                        + "'S:' || scd.y_slot || ' R:' || scd.y_row || ' T:' || scd.y_tier "
                                                                                                + "WHEN e.operation='LOAD' OR e.operation='LD-SHIFTING-CY' OR e.operation='TRANSHIPMENT' THEN "
                                                                                                        + "mv.name || ' [' || mv.vessel_code || ']' "
                                                                                                + "END AS destination2 "
                                                                                        + "FROM (service_vessel sv "
                                                                                                + "JOIN m_dock mdc ON (sv.dock_code=mdc.dock_code)) "
                                                                                                + "JOIN planning_vessel pv ON (sv.no_ppkb=pv.no_ppkb) "
                                                                                                        + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
                                                                                                + "JOIN equipment e ON (sv.no_ppkb=e.no_ppkb) "
                                                                                                        + "LEFT JOIN (SELECT no_ppkb, cont_no, block, y_slot, y_row, y_tier FROM service_cont_discharge "
                                                                                                                + "UNION ALL SELECT no_ppkb, cont_no, block, y_slot, y_row, y_tier FROM cancel_loading_service "
                                                                                                                + "UNION ALL SELECT no_ppkb, cont_no, block, y_slot, y_row, y_tier FROM service_cont_transhipment "
                                                                                                                + "UNION ALL SELECT no_ppkb, cont_no, block, y_slot, y_row, y_tier FROM planning_shift_discharge) scd ON (scd.no_ppkb=e.no_ppkb AND scd.cont_no=e.cont_no) "
                                                                                                        + "JOIN m_device_registration mdr ON (e.equip_code=mdr.equip_code) "
                                                                                                                + "JOIN m_device md ON (mdr.device_id=md.device_id) "
                                                                                        + "WHERE e.equipment_act_code='H/T' AND e.end_time IS null AND md.app_id = ?"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsServicingByDock", query = "SELECT prv.voy_in, mv.name, pv.eta, pv.etd, sv.fr_meter, sv.to_meter "
                                                                                                + "FROM (service_vessel sv "
                                                                                                        + "JOIN ((planning_vessel pv "
                                                                                                                + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code)) "
                                                                                                                + "JOIN preservice_vessel prv ON (prv.booking_code=pv.booking_code)) ON (sv.no_ppkb=pv.no_ppkb)) "
                                                                                                + "WHERE sv.departure_time IS NULL AND sv.dock_code=? AND sv.fr_meter IS NOT NULL"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findVesselsOnPort", query = "SELECT mv.name "
    + "FROM service_vessel sv JOIN planning_vessel pv ON (sv.no_ppkb=pv.no_ppkb) JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
    + "WHERE departure_time IS NULL"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findRecentArrivalsAndDepartures", query = "SELECT mv.name, sv.arrival_time, sv.departure_time "
    + "FROM service_vessel sv JOIN planning_vessel pv ON (sv.no_ppkb=pv.no_ppkb) JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
    + "WHERE sv.arrival_time IS NOT NULL OR sv.departure_time IS NOT NULL "
    + "ORDER BY sv.created_date DESC LIMIT 5"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findArrivalsAndDeparturesLastMonth", query = "SELECT month.date, "
    + "(SELECT COUNT(*) FROM service_vessel sv WHERE sv.arrival_time::date=month.date) arrival_time, "
    + "(SELECT COUNT(*) FROM service_vessel sv WHERE sv.departure_time::date=month.date) departure_time "
    + "FROM (SELECT DISTINCT date_trunc('day', (current_date - offs))::date as date FROM generate_series(0,30,1) as offs) month "
    + "ORDER BY month.date ASC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findPpkbCfs", query = "SELECT sv.no_ppkb, mv.name, pv.voy_in, pv.voy_out FROM service_vessel sv, preservice_vessel pv, m_vessel mv WHERE sv.booking_code = pv.booking_code AND pv.vessel_code = mv.vessel_code AND sv.no_ppkb IN (SELECT no_ppkb FROM service_cfs) ORDER BY sv.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.getVesselActivities", query = "SELECT COUNT(CASE WHEN (prv.status='Booking' AND plv.no_ppkb is null) THEN 1 ELSE NULL END) AS booking, "
    + "COUNT(CASE WHEN (prv.status='Confirm' AND plv.status='Confirm') THEN 1 ELSE NULL END) AS confirm, "
    + "COUNT(CASE WHEN (plv.status='Approved' AND sv.no_ppkb is null) THEN 1 ELSE NULL END) AS approved, "
    + "COUNT(CASE WHEN (plv.status='ReadyService' AND sv.no_ppkb is null) THEN 1 ELSE NULL END) AS ready_service, "
    + "COUNT(CASE WHEN (plv.status='Servicing' AND sv.status='Servicing') THEN 1 ELSE NULL END) AS servicing, "
    + "COUNT(CASE WHEN (sv.status='Served') THEN 1 ELSE NULL END) AS served "
    + "FROM preservice_vessel prv "
    + "LEFT JOIN planning_vessel plv ON (prv.booking_code=plv.booking_code) "
    + "LEFT JOIN service_vessel sv ON (plv.no_ppkb=sv.no_ppkb) "
    + "WHERE sv.departure_time IS NULL OR sv.departure_time::date = now()::date"),
    @NamedNativeQuery(name = "ServiceVessel.Native.getContServiceActivitiesToday", query = "SELECT (SELECT COUNT(*) AS count FROM service_cont_discharge WHERE position='03' AND start_placement_date::date=date.now) AS scd, "
    + "(SELECT COUNT(*) AS count FROM service_cont_load WHERE position='01' AND transaction_date::date=date.now) AS scl, "
    + "(SELECT COUNT(*) AS count FROM service_behandle WHERE start_time::date=date.now) AS sb, "
    + "(SELECT COUNT(*) AS count FROM service_cont_transhipment WHERE start_placement_date::date=date.now) AS sct, "
    + "(SELECT COUNT(*) AS count FROM service_shifting s JOIN equipment e ON (s.id_equipment=e.id AND e.start_time::date=date.now)) AS ss "
    + "FROM (SELECT NOW()::date AS now) date"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceByStatus", query = "SELECT no_ppkb,to_char(arrival_time, 'dd/MM/yyyy HH:mm') as a, to_char(berhting_time, 'dd/MM/yyyy HH:mm') as b,to_char(start_work_time, 'dd/MM/yyyy HH:mm') as c ,to_char(end_work_time, 'dd/MM/yyyy HH:mm') as d, to_char(departure_time, 'dd/MM/yyyy HH:mm') as e FROM service_vessel WHERE no_ppkb = ? and status = ?"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findPpkbNumbers", query = "SELECT no_ppkb FROM service_vessel WHERE upper(no_ppkb) LIKE ? AND status='Servicing'"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsToLoad", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out FROM service_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND (pp.activity = 2 OR pp.activity = 3) AND pp.vessel_code = mv.vessel_code AND (p.status='Approved' OR p.status='Servicing' OR p.status='ReadyService') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsFromDischarge", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out FROM service_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND (pp.activity = 1 OR pp.activity = 3) AND pp.vessel_code = mv.vessel_code AND (p.status = 'Servicing' OR p.status = 'Served') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsToChange", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out "
                                                                                        + "FROM planning_vessel p "
                                                                                                + "JOIN preservice_vessel pp ON (p.booking_code=pp.booking_code) "
                                                                                                + "JOIN m_vessel mv ON (pp.vessel_code = mv.vessel_code) "
                                                                                        + "WHERE (pp.activity = 2 OR pp.activity = 3) AND p.status IN ('Approved', 'ReadyService', 'Servicing') "
                                                                                        + "ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsFromChange", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out "
                                                                                        + "FROM planning_vessel p "
                                                                                                + "JOIN preservice_vessel pp ON (p.booking_code=pp.booking_code) "
                                                                                                + "JOIN m_vessel mv ON (pp.vessel_code = mv.vessel_code) "
                                                                                        + "WHERE (pp.activity = 2 OR pp.activity = 3) AND p.status IN ('Approved', 'ReadyService', 'Servicing', 'Served') "
                                                                                        + "ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsCancelLoadingConfirm", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out FROM service_vessel p, preservice_vessel pp, m_vessel mv where p.booking_code=pp.booking_code AND pp.vessel_code = mv.vessel_code AND (p.status = 'Servicing' OR p.status = 'Served' OR p.status='Approved' OR p.status='ReadyService') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsByMonitoringContDischarge", query = "SELECT sv.no_ppkb, mv.name, pv.voy_in, pv.voy_out,sv.est_discharge FROM service_vessel sv, preservice_vessel pv, m_vessel mv WHERE sv.booking_code = pv.booking_code AND pv.vessel_code = mv.vessel_code AND (sv.status = 'Servicing' or sv.status='Served') AND (pv.activity =1 OR pv.activity = 3) ORDER BY sv.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsByMonitoringContLoad", query = "SELECT sv.no_ppkb, mv.name, pv.voy_in, pv.voy_out,sv.est_load,sv.close_rec FROM service_vessel sv, preservice_vessel pv, m_vessel mv WHERE sv.booking_code = pv.booking_code AND pv.vessel_code = mv.vessel_code AND (sv.status = 'Servicing' or sv.status='Approved' or sv.status='ReadyService') AND (pv.activity =2 OR pv.activity = 3) ORDER BY sv.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsByMonitoringCountDischarge", query = "select sv.no_ppkb ,(SELECT COUNT(scd.id) FROM service_cont_discharge scd WHERE scd.no_ppkb=sv.no_ppkb AND (position='03' OR position='04') AND cont_size=40 AND cont_status='MTY') as cont40MtyDisch,(SELECT COUNT(scd.id) FROM service_cont_discharge scd WHERE scd.no_ppkb=sv.no_ppkb AND (position='03' OR position='04') AND cont_size=20 AND cont_status='MTY') as cont20MtyDisch,(SELECT COUNT(scd.id) FROM service_cont_discharge scd WHERE scd.no_ppkb=sv.no_ppkb AND (position='03' OR position='04') AND cont_size=40 AND cont_status='FCL') as cont40FclDisch,(SELECT COUNT(scd.id) FROM service_cont_discharge scd WHERE scd.no_ppkb=sv.no_ppkb AND (position='03' OR position='04') AND cont_size=20 AND cont_status='FCL') as cont20FclDisch from service_vessel sv where sv.no_ppkb=?"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsByMonitoringCountLoad", query = "select sv.no_ppkb ,(SELECT COUNT(scd.id) FROM service_cont_load scd WHERE scd.no_ppkb=sv.no_ppkb AND position='01' AND cont_size=40 AND cont_status='MTY') as cont40MtyLoad,(SELECT COUNT(scd.id) FROM service_cont_load scd WHERE scd.no_ppkb=sv.no_ppkb AND position='01' AND cont_size=20 AND cont_status='MTY') as cont20MtyLoad,(SELECT COUNT(scd.id) FROM service_cont_load scd WHERE scd.no_ppkb=sv.no_ppkb AND position='01' AND cont_size=40 AND cont_status='FCL') as cont40FclLoad,(SELECT COUNT(scd.id) FROM service_cont_load scd WHERE scd.no_ppkb=sv.no_ppkb AND position='01' AND cont_size=20 AND cont_status='FCL') as cont20FclLoad from service_vessel sv where sv.no_ppkb=?"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsByMonitoringCountLoad2", query = "SELECT pv.no_ppkb,(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=20 AND pcl.cont_status='FCL' AND pcl.position = '03') as cont20FC, "
    + "(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=20 AND pcl.cont_status='MTY' AND pcl.position = '03') as cont20MC, "
    + "(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=40 AND pcl.cont_status='FCL' AND pcl.position = '03') as cont40FC, "
    + "(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=40 AND pcl.cont_status='MTY' AND pcl.position = '03') as cont40MC, "
    + "(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=20 AND pcl.cont_status='FCL' AND pcl.position = '02') as cont20FH, "
    + "(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=20 AND pcl.cont_status='MTY' AND pcl.position = '02') as cont20MH, "
    + "(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=40 AND pcl.cont_status='FCL' AND pcl.position = '02') as cont40FH, "
    + "(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=40 AND pcl.cont_status='MTY' AND pcl.position = '02') as cont40MH, "
    + "(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=20 AND pcl.cont_status='FCL' AND pcl.position = '01') as cont20FV, "
    + "(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=20 AND pcl.cont_status='MTY' AND pcl.position = '01') as cont20MV, "
    + "(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=40 AND pcl.cont_status='FCL' AND pcl.position = '01') as cont40FV, "
    + "(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb AND pcl.cont_size=40 AND pcl.cont_status='MTY' AND pcl.position = '01') as cont40MV,(SELECT COUNT(pcl.id) FROM planning_cont_load pcl WHERE pcl.no_ppkb=pv.no_ppkb ) as total "
    + " FROM planning_vessel pv WHERE pv.no_ppkb= ? "),
    @NamedNativeQuery(name = "ServiceVessel.Native.findServiceVesselsByYardPlanningMonitoring", query = "SELECT p.no_ppkb, m.name, r.voy_in, r.voy_out, p.check_baplie FROM planning_vessel p, preservice_vessel r, m_vessel m WHERE p.booking_code = r.booking_code AND r.vessel_code = m.vessel_code AND (p.status='Approved' OR p.status='Servicing' OR p.status='ReadyService') ORDER BY p.created_date DESC"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findAllReceivingService", query = "select p.no_ppkb, r.name, t.name, q.voy_in, q.voy_out "
    + "from planning_vessel p, preservice_vessel q,m_vessel r, m_customer t "
    + "where p.booking_code=q.booking_code and q.vessel_code=r.vessel_code and q.cust_code=t.cust_code and p.status IN ('Approved', 'ReadyService', 'Servicing')"),
    @NamedNativeQuery(name = "ServiceVessel.Native.findReceivingServiceByPpkb", query = "SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out, pp.vessel_code, p.est_discharge, p.close_rec, pp.cust_code "
                                                                                    + "FROM planning_vessel p "
                                                                                            + "JOIN preservice_vessel pp ON (p.booking_code=pp.booking_code) "
                                                                                            + "JOIN m_vessel mv ON (pp.vessel_code = mv.vessel_code) "
                                                                                    + "WHERE p.no_ppkb = ? AND p.status IN ('Approved', 'Servicing', 'ReadyService')")})
public class ServiceVessel implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "no_ppkb", nullable = false, length = 30)
    private String noPpkb;
    @Basic(optional = false)
    @Column(name = "dock_code", nullable = false, length = 10)
    private String dockCode;
    @Column(name = "booking_code", length = 20)
    private String bookingCode;
    @Column(name = "tipe_pelayaran", length = 15)
    private String tipePelayaran = PlanningVessel.DOMESTIK;
    @Column(name = "est_discharge")
    private Short estDischarge;
    @Column(name = "est_load")
    private Short estLoad;
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
    @Column(name = "discharge")
    private Short discharge;
    @Column(name = "load")
    private Short load;
    @Column(name = "arrival_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalTime;
    @Column(name = "berhting_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date berhtingTime;
    @Column(name = "departure_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;
    @Column(name = "start_work_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startWorkTime;
    @Column(name = "end_work_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endWorkTime;
    @Column(name = "load_service_type", length = 10)
    private String loadServiceType;
    @Column(name = "discharge_service_type", length = 10)
    private String dischargeServiceType;
    @Column(name = "payment_type", length = 10)
    private String paymentType;
    @Column(name = "status", length = 15)
    private String status;
    @Column(name = "fr_meter")
    private Short frMeter;
    @Column(name = "to_meter")
    private Short toMeter;
    @Column(name = "is_discharge")
    private Boolean isDischarge;
    @Column(name = "is_load")
    private Boolean isLoad;
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
    @Column(name = "air_kapal", nullable = false)
    private Integer airKapal = 0;
    @OneToMany(mappedBy = "serviceVessel")
    private List<ServiceShifting> serviceShiftingList;
    @OneToMany(mappedBy = "serviceVessel")
    private List<ServiceContDischarge> serviceContDischargeList;
    @OneToMany(mappedBy = "serviceVessel")
    private List<ServiceContLoad> serviceContLoadList;
    @OneToMany(mappedBy = "serviceVessel")
    private List<ServiceContTranshipment> serviceContTranshipmentList;
    @OneToMany(mappedBy = "serviceVessel")
    private List<ServiceDischargeUc> serviceDischargeUcList;
    @OneToMany(mappedBy = "serviceVessel")
    private List<ServiceLoadUc> serviceLoadUcList;
    @OneToOne(mappedBy = "serviceVessel")
    private PlanningVessel planningVessel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceVessel")
    private List<RecapBaplie> recapBaplieList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceVessel")
    private List<RecapPenumpukanUc> recapPenumpukanUcList;

    public ServiceVessel() {
    }

    public ServiceVessel(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public ServiceVessel(String noPpkb, String dockCode) {
        this.noPpkb = noPpkb;
        this.dockCode = dockCode;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getDockCode() {
        return dockCode;
    }

    public void setDockCode(String dockCode) {
        this.dockCode = dockCode;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
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

    public Short getDischarge() {
        return discharge;
    }

    public void setDischarge(Short discharge) {
        this.discharge = discharge;
    }

    public Short getLoad() {
        return load;
    }

    public void setLoad(Short load) {
        this.load = load;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getBerhtingTime() {
        return berhtingTime;
    }

    public void setBerhtingTime(Date berhtingTime) {
        this.berhtingTime = berhtingTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(Date startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public Date getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(Date endWorkTime) {
        this.endWorkTime = endWorkTime;
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

    public Boolean getIsDischarge() {
        return isDischarge;
    }

    public void setIsDischarge(Boolean isDischarge) {
        this.isDischarge = isDischarge;
    }

    public Boolean getIsLoad() {
        return isLoad;
    }

    public void setIsLoad(Boolean isLoad) {
        this.isLoad = isLoad;
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

    public Boolean getIsLoadCyToCy() {
        if (loadServiceType != null && loadServiceType.equals(PlanningVessel.CY_TO_CY))
            return true;

        return false;
    }

    public Boolean getIsLoadPortToPort() {
        if (loadServiceType != null && loadServiceType.equals(PlanningVessel.PORT_TO_PORT))
            return true;

        return false;
    }

    public Boolean getIsDischargeCyToCy() {
        if (dischargeServiceType != null && dischargeServiceType.equals(PlanningVessel.CY_TO_CY))
            return true;

        return false;
    }

    public Boolean getIsDischargePortToPort() {
        if (dischargeServiceType != null && dischargeServiceType.equals(PlanningVessel.PORT_TO_PORT))
            return true;

        return false;
    }

    public List<ServiceShifting> getServiceShiftingList() {
        return serviceShiftingList;
    }

    public void setServiceShiftingList(List<ServiceShifting> serviceShiftingList) {
        this.serviceShiftingList = serviceShiftingList;
    }

    public List<ServiceContDischarge> getServiceContDischargeList() {
        return serviceContDischargeList;
    }

    public void setServiceContDischargeList(List<ServiceContDischarge> serviceContDischargeList) {
        this.serviceContDischargeList = serviceContDischargeList;
    }

    public List<ServiceContLoad> getServiceContLoadList() {
        return serviceContLoadList;
    }

    public void setServiceContLoadList(List<ServiceContLoad> serviceContLoadList) {
        this.serviceContLoadList = serviceContLoadList;
    }

    public List<ServiceContTranshipment> getServiceContTranshipmentList() {
        return serviceContTranshipmentList;
    }

    public void setServiceContTranshipmentList(List<ServiceContTranshipment> serviceContTranshipmentList) {
        this.serviceContTranshipmentList = serviceContTranshipmentList;
    }

    public List<ServiceDischargeUc> getServiceDischargeUcList() {
        return serviceDischargeUcList;
    }

    public void setServiceDischargeUcList(List<ServiceDischargeUc> serviceDischargeUcList) {
        this.serviceDischargeUcList = serviceDischargeUcList;
    }

    public List<ServiceLoadUc> getServiceLoadUcList() {
        return serviceLoadUcList;
    }

    public void setServiceLoadUcList(List<ServiceLoadUc> serviceLoadUcList) {
        this.serviceLoadUcList = serviceLoadUcList;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public List<RecapPenumpukanUc> getRecapPenumpukanUcList() {
        return recapPenumpukanUcList;
    }

    public void setRecapPenumpukanUcList(List<RecapPenumpukanUc> recapPenumpukanUcList) {
        this.recapPenumpukanUcList = recapPenumpukanUcList;
    }

    public List<RecapBaplie> getRecapBaplieList() {
        return recapBaplieList;
    }

    public void setRecapBaplieList(List<RecapBaplie> recapBaplieList) {
        this.recapBaplieList = recapBaplieList;
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

    public Integer getAirKapal() {
        return airKapal;
    }

    public void setAirKapal(Integer airKapal) {
        this.airKapal = airKapal;
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
        if (!(object instanceof ServiceVessel)) {
            return false;
        }
        ServiceVessel other = (ServiceVessel) object;
        if ((this.noPpkb == null && other.noPpkb != null) || (this.noPpkb != null && !this.noPpkb.equals(other.noPpkb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceVessel[noPpkb=" + noPpkb + "]";
    }
}