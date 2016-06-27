/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterYard;
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
@Table(name = "planning_cont_load")
@NamedQueries({
    @NamedQuery(name = "PlanningContLoad.findAll", query = "SELECT p FROM PlanningContLoad p"),
    @NamedQuery(name = "PlanningContLoad.findById", query = "SELECT p FROM PlanningContLoad p WHERE p.id = :id"),
    @NamedQuery(name = "PlanningContLoad.findByContNo", query = "SELECT p FROM PlanningContLoad p WHERE p.contNo = :contNo"),
    @NamedQuery(name = "PlanningContLoad.findByNoPpkbAndContNo", query = "SELECT p FROM PlanningContLoad p WHERE p.planningVessel.noPpkb = :noPpkb AND p.contNo = :contNo"),
    @NamedQuery(name = "PlanningContLoad.findByNoPpkbAndContNoExcludeCancelLoading", query = "SELECT p FROM PlanningContLoad p WHERE p.contNo = :contNo AND p.planningVessel.noPpkb = :noPpkb AND p.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "PlanningContLoad.findByNoPpkbAndContNoCancelLoading", query = "SELECT p FROM PlanningContLoad p WHERE p.contNo = :contNo AND p.planningVessel.noPpkb = :noPpkb AND p.statusCancelLoading = TRUE"),
    @NamedQuery(name = "PlanningContLoad.updatePlanningVessel", query = "UPDATE PlanningContLoad p SET p.planningVessel = :newValue WHERE p.planningVessel = :oldValue"),
    @NamedQuery(name = "PlanningContLoad.updatePlanningVesselByContNo", query = "UPDATE PlanningContLoad p SET p.planningVessel = :newValue, p.dischPort = :dischPort, p.portOfDelivery= :portOfDelivery WHERE p.planningVessel = :oldValue AND p.statusCancelLoading <> TRUE AND p.contNo IN :containers"),
    @NamedQuery(name = "PlanningContLoad.findByNoPpkbContNoAndStatusCancelLoading", query = "SELECT p FROM PlanningContLoad p WHERE p.contNo = :contNo AND p.planningVessel.noPpkb = :noPpkb AND p.statusCancelLoading = :statusCancelLoading"),
    @NamedQuery(name = "PlanningContLoad.findByCancelableContainer", query = "SELECT p FROM PlanningContLoad p WHERE p.contNo = :contNo AND p.planningVessel.noPpkb = :noPpkb AND p.statusCancelLoading <> TRUE AND p.isShifting <> TRUE"),
    @NamedQuery(name = "PlanningContLoad.deleteByNoPpkbAndContNo", query = "DELETE FROM PlanningContLoad p WHERE p.contNo = :contNo AND p.planningVessel.noPpkb = :noPpkb AND p.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "PlanningContLoad.findMovableContainer", query = "SELECT p FROM PlanningContLoad p WHERE p.contNo = :contNo AND p.position = '" + PlanningContLoad.LOCATION_CY + "' AND p.masterYard IS NOT NULL AND p.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "PlanningContLoad.findMovableOffContainer", query = "SELECT p FROM PlanningContLoad p WHERE p.contNo = :contNo AND p.position = '" + PlanningContLoad.LOCATION_MOVEMENT + "' AND p.masterYard IS NOT NULL AND p.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "PlanningContLoad.findByContSize", query = "SELECT p FROM PlanningContLoad p WHERE p.contSize = :contSize"),
    @NamedQuery(name = "PlanningContLoad.findByContStatus", query = "SELECT p FROM PlanningContLoad p WHERE p.contStatus = :contStatus"),
    @NamedQuery(name = "PlanningContLoad.findLiftableOnContainer", query = "SELECT p FROM PlanningContLoad p WHERE p.contNo = :contNo AND p.position = '" + PlanningContLoad.LOCATION_CY + "' AND p.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "PlanningContLoad.findByContGross", query = "SELECT p FROM PlanningContLoad p WHERE p.contGross = :contGross"),
    @NamedQuery(name = "PlanningContLoad.findBySealNo", query = "SELECT p FROM PlanningContLoad p WHERE p.sealNo = :sealNo"),
    @NamedQuery(name = "PlanningContLoad.findByOverSize", query = "SELECT p FROM PlanningContLoad p WHERE p.overSize = :overSize"),
    @NamedQuery(name = "PlanningContLoad.findByDg", query = "SELECT p FROM PlanningContLoad p WHERE p.dg = :dg"),
    @NamedQuery(name = "PlanningContLoad.findByDgLabel", query = "SELECT p FROM PlanningContLoad p WHERE p.dgLabel = :dgLabel"),
    @NamedQuery(name = "PlanningContLoad.findByLoadPort", query = "SELECT p FROM PlanningContLoad p WHERE p.loadPort = :loadPort"),
    @NamedQuery(name = "PlanningContLoad.findByDischPort", query = "SELECT p FROM PlanningContLoad p WHERE p.dischPort = :dischPort"),
    @NamedQuery(name = "PlanningContLoad.findByYSlot", query = "SELECT p FROM PlanningContLoad p WHERE p.ySlot = :ySlot"),
    @NamedQuery(name = "PlanningContLoad.findByYRow", query = "SELECT p FROM PlanningContLoad p WHERE p.yRow = :yRow"),
    @NamedQuery(name = "PlanningContLoad.findByYTier", query = "SELECT p FROM PlanningContLoad p WHERE p.yTier = :yTier"),
    @NamedQuery(name = "PlanningContLoad.findByVBay", query = "SELECT p FROM PlanningContLoad p WHERE p.vBay = :vBay"),
    @NamedQuery(name = "PlanningContLoad.findByVRow", query = "SELECT p FROM PlanningContLoad p WHERE p.vRow = :vRow"),
    @NamedQuery(name = "PlanningContLoad.findByVTier", query = "SELECT p FROM PlanningContLoad p WHERE p.vTier = :vTier"),
    @NamedQuery(name = "PlanningContLoad.updateCompleteStatusByNoPpkb", query = "UPDATE PlanningContLoad p SET p.completed = :completed WHERE p.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "PlanningContLoad.findByIsTranshipment", query = "SELECT p FROM PlanningContLoad p WHERE p.isTranshipment = :isTranshipment"),
    @NamedQuery(name = "PlanningContLoad.findNotPlannedContainerByNoPpkbAndYardLocation", query = "SELECT p FROM PlanningContLoad p WHERE p.planningVessel.noPpkb = :noPpkb AND p.masterYard.block = :block AND p.ySlot = :slot AND p.completed = FALSE AND (p.vBay IS NULL OR p.vRow IS NULL OR p.vTier IS NULL) AND p.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "PlanningContLoad.findNotPlannedContainerByPodNoPpkbAndYardLocation", query = "SELECT p FROM PlanningContLoad p WHERE p.planningVessel.noPpkb = :noPpkb AND p.masterYard.block = :block AND p.ySlot = :slot AND p.dischPort = :dischPort AND p.completed = FALSE AND (p.vBay IS NULL OR p.vRow IS NULL OR p.vTier IS NULL) AND p.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "PlanningContLoad.findPlannedContainerByBayLocation", query = "SELECT p FROM PlanningContLoad p WHERE p.planningVessel.noPpkb = :noPpkb AND (p.vBay = :bay OR (p.vBay = :bay - 2 AND p.contSize = 40)) AND p.vRow IS NOT NULL AND p.vTier IS NOT NULL;"),
    @NamedQuery(name = "PlanningContLoad.findByPpkbPositionsAndNotCancelLoading", query = "SELECT p FROM PlanningContLoad p WHERE p.planningVessel.noPpkb = :noPpkb AND p.position IN :positions AND p.statusCancelLoading = FALSE"),
    @NamedQuery(name = "PlanningContLoad.updateStatusCancelLoadingByNoPpkb", query = "UPDATE PlanningContLoad p SET p.statusCancelLoading = :statusCancelLoading WHERE p.planningVessel.noPpkb = :noPpkb")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PlanningContLoad.Native.findPlanningBayplanLoadsByPpkb", query = "SELECT pcl.cont_no, pcl.cont_size, mct.name, pcl.cont_status, pcl.cont_gross, pcl.seal_no, pcl.v_bay, pcl.v_row, pcl.v_tier, pcl.load_port, pcl.disch_port, pcl.id, pcl.block, pcl.y_slot, pcl.y_row, pcl.y_tier,pcl.v_bay,pcl.v_tier,pcl.v_row,pcl.completed,pcl.bl_no,case when pcl.no_ppkb = rs.no_ppkb and pcl.cont_no=rs.cont_no then (select mp.name from m_port mp where mp.port_code=rs.disch_port_code limit 1) when pcl.no_ppkb=st.new_ppkb AND pcl.cont_no=st.cont_no then (select mp.name from m_port mp where mp.port_code=st.disch_port limit 1) end FROM ((planning_cont_load pcl LEFT JOIN receiving_service rs on pcl.no_ppkb=rs.no_ppkb AND pcl.cont_no=rs.cont_no) LEFT JOIN service_cont_transhipment st on pcl.no_ppkb=st.new_ppkb AND pcl.cont_no=st.cont_no), m_container_type mct WHERE pcl.cont_type = mct.cont_type AND pcl.no_ppkb = ? ORDER BY pcl.id DESC"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findLoadCandidatesPerCustomer", query = "SELECT pcl.cont_no, mc.name, pcl.cont_size, pcl.cont_status, mct.type_in_general, pcl.block, pcl.created_date placement_date, mv.name "
                                                                                            + "FROM planning_cont_load pcl "
                                                                                                    + "LEFT JOIN service_cont_load scl ON (pcl.cont_no=scl.cont_no AND pcl.no_ppkb=scl.no_ppkb) "
                                                                                                    + "JOIN planning_vessel pv ON (pcl.no_ppkb=pv.no_ppkb) "
                                                                                                            + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                                                                                                    + "JOIN m_customer mc ON (prv.cust_code=mc.cust_code) "
                                                                                                            + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
                                                                                                    + "JOIN m_container_type mct ON (pcl.cont_type=mct.cont_type) "
                                                                                            + "WHERE scl.cont_no IS NULL AND pcl.position = '" + PlanningContLoad.LOCATION_CY + "' AND pcl.status_cancel_loading <> TRUE AND mc.cust_code = ? "
                                                                                            + "ORDER BY pcl.created_date ASC;"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findLoadCandidatesPerCustomer_summary", query = "SELECT mc.cust_code, COUNT(CASE WHEN pcl.cont_status = 'MTY' AND pcl.cont_size = 40 THEN 1 END), "
                                                                                                    + "COUNT(CASE WHEN pcl.cont_status = 'MTY' AND pcl.cont_size = 20 THEN 1 END), "
                                                                                                    + "COUNT(CASE WHEN pcl.cont_status = 'FCL' AND pcl.cont_size = 40 THEN 1 END), "
                                                                                                    + "COUNT(CASE WHEN pcl.cont_status = 'FCL' AND pcl.cont_size = 20 THEN 1 END) "
                                                                                            + "FROM planning_cont_load pcl "
                                                                                                    + "LEFT JOIN service_cont_load scl ON (pcl.cont_no=scl.cont_no AND pcl.no_ppkb=scl.no_ppkb) "
                                                                                                    + "JOIN planning_vessel pv ON (pcl.no_ppkb=pv.no_ppkb) "
                                                                                                            + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                                                                                                    + "JOIN m_customer mc ON (prv.cust_code=mc.cust_code) "
                                                                                                            + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
                                                                                                    + "JOIN m_container_type mct ON (pcl.cont_type=mct.cont_type) "
                                                                                            + "WHERE scl.cont_no IS NULL AND pcl.position = '03' AND pcl.status_cancel_loading <> TRUE AND mc.cust_code = ? "
                                                                                            + "GROUP BY mc.cust_code;"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findCountableLoadReefers", query = "SELECT null, pcl.cont_no, pcl.cont_size, mt.name, pcl.cont_status, pcl.cont_gross, pcl.over_size, pcl.bl_no, "
                                                                                            + "CASE WHEN pcl.is_transhipment THEN "
                                                                                                + "'Transhipment' "
                                                                                            + "WHEN pcl.is_shifting THEN "
                                                                                                + "'Shifting' "
                                                                                            + "WHEN pcl.is_change_vessel THEN "
                                                                                                + "'Ex Cancel Loading' "
                                                                                            + "ELSE "
                                                                                                + "'Receiving' "
                                                                                            + "END AS status, "
                                                                                            + "sr.plug_on + rls.shift_planning * 8 * (interval '1 Hour') valid_date "
                                                                                        + "FROM planning_cont_load pcl "
                                                                                                + "JOIN service_reefer sr ON (pcl.cont_no=sr.cont_no AND pcl.no_ppkb=sr.no_ppkb) "
                                                                                                + "LEFT JOIN reefer_load_service rls ON (pcl.cont_no=rls.cont_no AND pcl.no_ppkb=rls.no_ppkb) "
                                                                                                        + "LEFT JOIN (SELECT rls.cont_no, rls.no_ppkb, rls.no_reg "
                                                                                                                + "FROM reefer_load_service rls "
                                                                                                                        + "JOIN registration r ON (rls.no_reg=r.no_reg ) "
                                                                                                                + "WHERE r.status_service IN ('registrasi','confirm')) rls2 ON (rls.no_ppkb = rls2.no_ppkb AND rls.cont_no = rls2.cont_no) "
                                                                                                + "JOIN m_container_type mt ON (pcl.cont_type=mt.cont_type) "
                                                                                        + "WHERE rls2.cont_no IS NULL AND (pcl.is_transhipment OR pcl.is_shifting OR pcl.is_change_vessel OR (sr.plug_off > (sr.plug_on + rls.shift_planning * 8 * (interval '1 Hour')))) AND sr.plug_off IS NOT NULL AND mt.type_in_general = 'RFR' AND pcl.no_ppkb = ? "
                                                                                        + "GROUP BY pcl.cont_no, pcl.cont_size, mt.name, pcl.cont_status, pcl.cont_gross, pcl.over_size, pcl.bl_no, pcl.is_transhipment, pcl.is_shifting, pcl.is_change_vessel, sr.plug_on, rls.shift_planning"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findPluggableOnContainersByPpkb", query = "SELECT pcl.cont_no, pcl.cont_size, mct.name, pcl.cont_status, pcl.cont_gross, pcl.seal_no, pcl.id "
                                                                                                + "FROM planning_cont_load AS pcl "
                                                                                                        + "JOIN m_container_type AS mct ON (pcl.cont_type = mct.cont_type) "
                                                                                                        + "LEFT JOIN service_reefer pclf ON (pclf.no_ppkb = pcl.no_ppkb AND pclf.cont_no = pcl.cont_no) "
                                                                                                + "WHERE mct.type_in_general = 'RFR' AND pclf.cont_no IS NULL AND pcl.position = '03' AND pcl.status_cancel_loading <> TRUE AND pcl.no_ppkb = ? "
                                                                                                + "ORDER BY pcl.id DESC"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findPluggableContainer", query = "SELECT pcl.cont_no, pcl.cont_size, pcl.cont_type, pcl.cont_status, pcl.cont_gross, pcl.seal_no, pcl.no_ppkb, pcl.is_transhipment, pcl.is_shifting "
                                                                                                + "FROM planning_cont_load AS pcl "
                                                                                                        + "JOIN m_container_type AS mct ON (pcl.cont_type = mct.cont_type) "
                                                                                                        + "LEFT JOIN service_reefer pclf ON (pclf.no_ppkb = pcl.no_ppkb AND pclf.cont_no = pcl.cont_no) "
                                                                                                + "WHERE mct.type_in_general = 'RFR' AND pclf.cont_no IS NULL AND pcl.position = '03' AND pcl.status_cancel_loading <> TRUE AND pcl.cont_no = ? "
                                                                                                + "ORDER BY pcl.id DESC"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findPodByNoPpkbAndYardLocation", query = "SELECT DISTINCT pcl.disch_port "
                                                                                                + "FROM planning_cont_load pcl "
                                                                                                + "WHERE pcl.no_ppkb = ? AND pcl.block = ? AND pcl.y_slot = ? AND pcl.completed = FALSE AND pcl.status_cancel_loading = FALSE "
                                                                                                + "ORDER BY pcl.disch_port"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findLoadingListByPpkb", query = "SELECT loading.cont_no, loading.cont_size, loading.cont_type, loading.cont_status, loading.cont_gross, loading.seal_no, loading.v_bay, loading.v_row, loading.v_tier, loading.load_port, loading.disch_port, loading.id, loading.block, loading.y_slot, loading.y_row, loading.y_tier, loading.completed , loading.bl_no, loading.cont_type, loading.status, loading.cust_name, loading.i_stat, loading.commodity "
                                                                                    + "FROM (SELECT pcl.no_ppkb, pcl.cont_no, pcl.cont_size, mct.type_in_general cont_type, pcl.cont_status, pcl.cont_gross, "
                                                                                            + "pcl.seal_no, pcl.v_bay, pcl.v_row, pcl.v_tier, pcl.load_port, pcl.disch_port, pcl.id, pcl.block, "
                                                                                            + "pcl.y_slot, pcl.y_row, pcl.y_tier, pcl.completed, pcl.bl_no, mp.name, "
                                                                                            + "CASE WHEN pcl.is_transhipment THEN "
                                                                                                    + "'TRANSHIPMENT' "
                                                                                            + "WHEN pcl.is_change_vessel THEN "
                                                                                                    + "'EX CANCEL LOADING' "
                                                                                            + "WHEN pcl.is_shifting THEN "
                                                                                                    + "'SHIFTING' "
                                                                                            + "ELSE "
                                                                                                    + "'RECEIVING' "
                                                                                            + "END AS status, "
                                                                                            + "CASE WHEN pcl.is_change_vessel THEN "
                                                                                                    + "mc_cls.name "
                                                                                            + "WHEN pcl.is_transhipment OR pcl.is_shifting THEN "
                                                                                                    + "mc_vs.name "
                                                                                            + "ELSE "
                                                                                                    + "mc.name "
                                                                                            + "END AS cust_name, "
                                                                                            + "CASE WHEN (sgr.cont_no IS NOT NULL) OR pcl.is_change_vessel OR pcl.is_transhipment OR pcl.is_shifting THEN "
                                                                                                    + "'OS' "
                                                                                            + "ELSE "
                                                                                                    + "'GT' "
                                                                                            + "END AS i_stat, mco.name commodity, pcl.status_cancel_loading "
                                                                                                    + "FROM planning_cont_load pcl "
                                                                                                            + "LEFT JOIN receiving_service rs ON (pcl.no_ppkb=rs.no_ppkb AND pcl.cont_no=rs.cont_no AND rs.status_cancel_loading=FALSE) "
                                                                                                                    + "LEFT JOIN registration r ON (r.no_reg=rs.no_reg) "
                                                                                                                            + "LEFT JOIN m_customer mc ON (r.cust_code=mc.cust_code) "
                                                                                                                    + "LEFT JOIN service_gate_receiving sgr ON (rs.job_slip=sgr.job_slip) "
                                                                                                            + "LEFT JOIN cancel_loading_service cls ON (pcl.no_ppkb=cls.new_ppkb AND pcl.cont_no=cls.cont_no) "
                                                                                                                    + "LEFT JOIN registration r_cls ON (r_cls.no_reg=cls.no_reg) "
                                                                                                                            + "LEFT JOIN m_customer mc_cls ON (r_cls.cust_code=mc_cls.cust_code) "
                                                                                                            + "LEFT JOIN service_shifting ss ON (pcl.cont_no=ss.cont_no AND pcl.no_ppkb=ss.no_ppkb AND ss.shift_to='TOCY') "
                                                                                                            + "JOIN planning_vessel pv ON (pcl.no_ppkb=pv.no_ppkb) "
                                                                                                                    + "JOIN preservice_vessel prv ON (prv.booking_code=pv.booking_code) "
                                                                                                                            + "JOIN m_customer mc_vs ON (prv.cust_code=mc_vs.cust_code) "
                                                                                                            + "JOIN m_port mp ON (mp.port_code=pcl.disch_port) "
                                                                                                            + "JOIN m_container_type mct ON (pcl.cont_type = mct.cont_type) "
                                                                                                            + "JOIN m_commodity mco ON (mco.commodity_code=pcl.commodity_code)) loading "
                                                                                    + "WHERE loading.no_ppkb = ? AND NOT loading.status_cancel_loading "
                                                                                    + "ORDER BY loading.disch_port, loading.status, loading.cont_gross, loading.cont_type, loading.cont_size, loading.block, loading.y_slot, loading.y_row, loading.y_tier ASC;"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findPlanningBayplanLoadCekBayExist", query = "SELECT pcl.id, pcl.v_bay, pcl.v_row, pcl.v_tier, cont_no, cont_size, cont_gross, pcl.no_ppkb "
                                                                                                    + "FROM planning_cont_load pcl, (SELECT ? AS bay,? AS row,? AS tier) prm "
                                                                                                    + "WHERE (pcl.v_bay=prm.bay OR (pcl.cont_size = 40 AND pcl.v_bay = prm.bay - 2)) AND pcl.v_row=prm.row AND pcl.v_tier=prm.tier AND pcl.no_ppkb=?"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findPlanningBayplanLoadCekNotCompleted", query = "SELECT pcl.id,pcl.v_bay, pcl.v_row, pcl.v_tier,pcl.completed FROM planning_cont_load pcl WHERE pcl.v_bay IS NULL AND pcl.no_ppkb=?"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findPlanningContLoads", query = "SELECT r.cont_no, r.cont_size, r.cont_type, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,r.over_size,r.dg,r.id from planning_cont_load r Where r.position='" + PlanningContLoad.LOCATION_HT + "' ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findPlanningContLoads3", query = "SELECT r.cont_no, r.cont_size, mt.name, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,r.over_size,r.dg,r.id,r.block,r.y_slot,r.y_row,r.y_tier,r.disch_port "
                                                                                    + "FROM planning_cont_load r "
                                                                                            + "JOIN m_container_type mt ON (r.cont_type=mt.cont_type) "
                                                                                            + "JOIN m_yard my ON (my.block=r.block) "
                                                                                    + "WHERE r.position='" + PlanningContLoad.LOCATION_CY + "' AND r.status_cancel_loading <> TRUE AND r.is_shifting <> TRUE AND r.no_ppkb= ? "
                                                                                    + "ORDER BY r.id DESC;"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findPlanningContLoadsTranshipment", query = "SELECT r.cont_no, r.cont_size, mt.name, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,r.over_size,r.dg,r.id,r.block,r.y_slot,r.y_row,r.y_tier,r.disch_port from planning_cont_load r,m_container_type mt,m_yard my Where r.is_transhipment ='true' AND r.position='" + PlanningContLoad.LOCATION_CY + "' AND r.cont_type=mt.cont_type AND r.status_cancel_loading=false AND r.no_ppkb= ? AND my.block=r.block ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findByContNo", query = "SELECT id, cont_no, no_ppkb, block, y_slot, y_row, y_tier FROM planning_cont_load WHERE cont_no = ? AND position = ? AND is_transhipment = ? "),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findLiftableOnContainer", query = "SELECT pcl.cont_no, pcl.no_ppkb, mv.name vessel_name, pcl.block, pcl.y_slot, pcl.y_row, pcl.y_tier, pcl.cont_size, pcl.cont_status, pcl.over_size, pcl.dg, pcl.is_transhipment, pcl.is_shifting IS NOT NULL AND pcl.is_shifting AS is_shifting, CASE WHEN sgr.cont_no IS NOT NULL THEN mcd_gt.name WHEN sct.cont_no IS NOT NULL THEN mcd_d.name ELSE mcd.name END "
                                                                                        + "FROM planning_cont_load pcl "
                                                                                                + "JOIN (planning_vessel pv "
                                                                                                        + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code)) ON (pcl.no_ppkb=pv.no_ppkb) "
                                                                                                        + "LEFT JOIN receiving_service rs ON (pcl.cont_no=rs.cont_no AND pcl.no_ppkb=rs.no_ppkb AND rs.status_cancel_loading <> TRUE) "
                                                                                                                + "LEFT JOIN (service_gate_receiving sgr "
                                                                                                                        + "LEFT JOIN m_cont_damage mcd_gt ON (sgr.cont_damage=mcd_gt.id)) ON (rs.job_slip=sgr.job_slip) "
                                                                                                + "LEFT JOIN service_cont_transhipment sct ON (pcl.cont_no=sct.cont_no AND pcl.no_ppkb=sct.new_ppkb) "
                                                                                                        + "LEFT JOIN (service_cont_discharge scd "
                                                                                                                + "LEFT JOIN m_cont_damage mcd_d ON (scd.cont_damage=mcd_d.id)) ON (sct.cont_no=scd.cont_no AND sct.no_ppkb=scd.no_ppkb) "
                                                                                                + "LEFT JOIN service_shifting AS ss ON (pcl.cont_no=ss.cont_no AND pcl.no_ppkb=ss.no_ppkb) "
                                                                                                        + "LEFT JOIN m_cont_damage mcd ON (ss.cont_damage=mcd.id) "
                                                                                        + "WHERE pcl.cont_no = ? AND pcl.position = '" + PlanningContLoad.LOCATION_CY + "' AND pcl.status_cancel_loading = FALSE;"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findByContNoAndPpkbByEdit", query = "SELECT id FROM planning_cont_load WHERE no_ppkb=? AND cont_no = ?"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findByContNoLiftOn", query = "SELECT id, cont_no, no_ppkb FROM planning_cont_load WHERE no_ppkb=? AND cont_no = ?"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findPlannedContainerByBayLocation", query = "SELECT p.cont_no, mct.new_iso_code, p.cont_gross, p.v_bay, p.v_row, p.v_tier, CASE WHEN obj.bay <> p.v_bay AND p.cont_size <> 20 THEN TRUE ELSE FALSE END AS is_crossed, p.block || ' ' || p.y_slot || ' ' || p.y_row || ' ' || p.y_tier, p.disch_port "
                                                                                                + "FROM planning_cont_load p "
                                                                                                    + "JOIN m_container_type mct ON (p.cont_type=mct.cont_type), "
                                                                                                + "(SELECT ? AS bay) obj "
                                                                                                + "WHERE p.no_ppkb = ? AND (p.v_bay = obj.bay OR (p.v_bay = obj.bay - 2 AND p.cont_size IN (40, 45)) OR (p.v_bay = obj.bay - 4 AND p.cont_size = 45)) AND p.v_row IS NOT NULL AND p.v_tier IS NOT NULL;"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.findGrossCapacityByNoPpkbAndBay", query = "SELECT row_number, nvl(pcl.gross) AS gross "
    + "FROM generate_series(?, ?) AS row_number "
    + "LEFT JOIN (SELECT pcl.v_row, SUM(pcl.cont_gross) AS gross "
    + "FROM planning_cont_load pcl, (SELECT ? AS bay) obj "
    + "WHERE (pcl.v_bay = obj.bay OR (pcl.v_bay = obj.bay - 2 AND pcl.cont_size = 40)) AND pcl.no_ppkb = ? AND pcl.v_tier BETWEEN ? AND ? "
    + "GROUP BY pcl.v_row) AS pcl ON (row_number=pcl.v_row) "
    + "ORDER BY row_number;"),
    @NamedNativeQuery(name = "PlanningContLoad.Native.PlanningContLoadfindCancelLoading", query = "SELECT scd.id,scd.cont_no FROM planning_cont_load AS scd WHERE scd.no_ppkb = ? AND scd.cont_no = ? AND scd.cont_no NOT IN (select cont_no from cancel_loading_service where no_ppkb=scd.no_ppkb)")})
public class PlanningContLoad implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;
    private static final long serialVersionUID = 1L;

    public static final String LOCATION_VESSEL = "01";
    public static final String LOCATION_HT = "02";
    public static final String LOCATION_CY = "03";
    public static final String LOCATION_MOVEMENT = "MV";

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "y_slot")
    private Short ySlot;
    @Column(name = "y_row")
    private Short yRow;
    @Column(name = "y_tier")
    private Short yTier;
    @Column(name = "v_bay")
    private Short vBay;
    @Column(name = "v_row")
    private Short vRow;
    @Column(name = "v_tier")
    private Short vTier;
    @Column(name = "is_transhipment")
    private Boolean isTranshipment = false;
    @Column(name = "is_shifting")
    private Boolean isShifting = false;
    @Column(name = "completed")
    private Boolean completed;
    @Column(name = "unknown")
    private Boolean Unknown;
    @Column(name = "position", length = 2)
    private String position;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", nullable = false)
    @ManyToOne(optional = false)
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
    @Column(name = "position_bay")
    private String positionBay;
    @Column(name = "status_cancel_loading")
    private Boolean statusCancelLoading = false;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Basic(optional = false)
    @Column(name = "is_sling", nullable = false)
    private boolean isSling = false;
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
    @OneToOne(mappedBy = "planningContLoad")
    private ServiceReceiving serviceReceiving;
    @JoinColumn(name = "port_of_delivery", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort portOfDelivery;
    @Basic(optional = false)
    @Column(name = "is_change_vessel", nullable = false)
    private boolean isChangeVessel = false;
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private boolean isExportImport = false;

    public PlanningContLoad() {
    }

    public PlanningContLoad(Integer id) {
        this.id = id;
    }

    public PlanningContLoad(Integer id, String contNo) {
        this.id = id;
        this.contNo = contNo;
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

    public Boolean getIsTranshipment() {
        return isTranshipment;
    }

    public void setIsTranshipment(Boolean isTranshipment) {
        this.isTranshipment = isTranshipment;
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

    /**
     * @return the completed
     */
    public Boolean getCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    /**
     * @return the positionBay
     */
    public String getPositionBay() {
        return positionBay;
    }

    /**
     * @param positionBay the positionBay to set
     */
    public void setPositionBay(String positionBay) {
        this.positionBay = positionBay;
    }

    public Boolean getUnknown() {
        return Unknown;
    }

    public void setUnknown(Boolean Unknown) {
        this.Unknown = Unknown;
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

    public Boolean getStatusCancelLoading() {
        return statusCancelLoading;
    }

    public void setStatusCancelLoading(Boolean statusCancelLoading) {
        this.statusCancelLoading = statusCancelLoading;
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

    public Boolean getIsShifting() {
        return isShifting;
    }

    public void setIsShifting(Boolean isShifting) {
        this.isShifting = isShifting;
    }

    public boolean getIsSling() {
        return isSling;
    }

    public void setIsSling(boolean isSling) {
        this.isSling = isSling;
    }

    public ServiceReceiving getServiceReceiving() {
        return serviceReceiving;
    }

    public MasterPort getPortOfDelivery() {
        return portOfDelivery;
    }

    public void setPortOfDelivery(MasterPort portOfDelivery) {
        this.portOfDelivery = portOfDelivery;
    }

    public boolean getIsChangeVessel() {
        return isChangeVessel;
    }

    public void setIsChangeVessel(boolean isChangeVessel) {
        this.isChangeVessel = isChangeVessel;
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
        if (!(object instanceof PlanningContLoad)) {
            return false;
        }
        PlanningContLoad other = (PlanningContLoad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PlanningContLoad[id=" + id + "]";
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
