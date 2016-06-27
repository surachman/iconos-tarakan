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
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "service_cont_discharge")
@NamedQueries({
    @NamedQuery(name = "ServiceContDischarge.findAll", query = "SELECT s FROM ServiceContDischarge s"),
    @NamedQuery(name = "ServiceContDischarge.findById", query = "SELECT s FROM ServiceContDischarge s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceContDischarge.findByCrane", query = "SELECT s FROM ServiceContDischarge s WHERE s.crane = :crane"),
    @NamedQuery(name = "ServiceContDischarge.findByContNo", query = "SELECT s FROM ServiceContDischarge s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceContDischarge.findByNoPpkbContNoAndPosition", query = "SELECT s FROM ServiceContDischarge s WHERE s.contNo = :contNo AND s.serviceVessel.noPpkb = :noPpkb AND s.position = :position"),
    @NamedQuery(name = "ServiceContDischarge.findByContNoAndPosition", query = "SELECT s FROM ServiceContDischarge s WHERE s.contNo = :contNo AND s.position = :position"),
    @NamedQuery(name = "ServiceContDischarge.findByNoPpkbAndContNo", query = "SELECT s FROM ServiceContDischarge s WHERE  s.serviceVessel.noPpkb = :noPpkb AND s.contNo = :contNo"),
    @NamedQuery(name = "ServiceContDischarge.findMovableContainer", query = "SELECT s FROM ServiceContDischarge s WHERE s.contNo = :contNo AND s.isDelivery = 'FALSE' AND s.position = '" + ServiceContDischarge.LOCATION_CY + "' AND s.masterYard IS NOT NULL"),
    @NamedQuery(name = "ServiceContDischarge.findMovableOffContainer", query = "SELECT s FROM ServiceContDischarge s WHERE s.contNo = :contNo AND s.isDelivery = 'FALSE' AND s.position = '" + ServiceContDischarge.LOCATION_MOVEMENT + "'"),
    @NamedQuery(name = "ServiceContDischarge.findByContSize", query = "SELECT s FROM ServiceContDischarge s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceContDischarge.findByContStatus", query = "SELECT s FROM ServiceContDischarge s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceContDischarge.findByContGross", query = "SELECT s FROM ServiceContDischarge s WHERE s.contGross = :contGross"),
    @NamedQuery(name = "ServiceContDischarge.findBySealNo", query = "SELECT s FROM ServiceContDischarge s WHERE s.sealNo = :sealNo"),
    @NamedQuery(name = "ServiceContDischarge.findByOverSize", query = "SELECT s FROM ServiceContDischarge s WHERE s.overSize = :overSize"),
    @NamedQuery(name = "ServiceContDischarge.findByDangerous", query = "SELECT s FROM ServiceContDischarge s WHERE s.dangerous = :dangerous"),
    @NamedQuery(name = "ServiceContDischarge.findByDgLabel", query = "SELECT s FROM ServiceContDischarge s WHERE s.dgLabel = :dgLabel"),
    @NamedQuery(name = "ServiceContDischarge.findByVBay", query = "SELECT s FROM ServiceContDischarge s WHERE s.vBay = :vBay"),
    @NamedQuery(name = "ServiceContDischarge.findByVRow", query = "SELECT s FROM ServiceContDischarge s WHERE s.vRow = :vRow"),
    @NamedQuery(name = "ServiceContDischarge.findByVTier", query = "SELECT s FROM ServiceContDischarge s WHERE s.vTier = :vTier"),
    @NamedQuery(name = "ServiceContDischarge.findByYSlot", query = "SELECT s FROM ServiceContDischarge s WHERE s.ySlot = :ySlot"),
    @NamedQuery(name = "ServiceContDischarge.findByYRow", query = "SELECT s FROM ServiceContDischarge s WHERE s.yRow = :yRow"),
    @NamedQuery(name = "ServiceContDischarge.findByYTier", query = "SELECT s FROM ServiceContDischarge s WHERE s.yTier = :yTier"),
    @NamedQuery(name = "ServiceContDischarge.findByStartPlacementDate", query = "SELECT s FROM ServiceContDischarge s WHERE s.startPlacementDate = :startPlacementDate"),
    @NamedQuery(name = "ServiceContDischarge.findByPosition", query = "SELECT s FROM ServiceContDischarge s WHERE s.position = :position"),
    @NamedQuery(name = "ServiceContDischarge.findByPpkbAndPositions", query = "SELECT s FROM ServiceContDischarge s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.position IN :positions"),
    @NamedQuery(name = "ServiceContDischarge.findByPpkbAndPosition", query = "SELECT s FROM ServiceContDischarge s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.position = :position"),
    @NamedQuery(name = "ServiceContDischarge.updateMasterActivityByPpkbAndPosition", query = "UPDATE ServiceContDischarge s SET s.masterActivity = :masterActivity WHERE s.serviceVessel.noPpkb = :noPpkb AND s.position = :position"),
    @NamedQuery(name = "ServiceContDischarge.findByIsDelivery", query = "SELECT s FROM ServiceContDischarge s WHERE s.isDelivery = :isDelivery")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findByNoPpkb", query = "SELECT cont_no, start_placement_date, v_bay, v_row, v_tier, id FROM service_cont_discharge WHERE no_ppkb = ? AND cont_no = ? AND position = ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findByPpkbAndContNo", query = "SELECT r.id, r.no_ppkb,r.block, r.y_slot, r.y_row, r.y_tier, r.is_seling FROM service_cont_discharge r WHERE r.no_ppkb = ? AND r.cont_no = ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesByPpkb", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier FROM service_cont_discharge as p, m_container_type as c WHERE p.cont_type=c.cont_type AND no_ppkb = ? ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.exDischargeStocksPerCustomer", query = "SELECT scd.cont_no, mc.name, scd.cont_size, scd.cont_status, mct.type_in_general, scd.block, scd.y_slot, scd.y_row, scd.y_tier, mv.name, prv.voy_in, prv.voy_out, scd.start_placement_date, mcs.name "
                                                                                                + "FROM service_cont_discharge scd "
                                                                                                        + "JOIN planning_vessel pv ON (scd.no_ppkb=pv.no_ppkb) "
                                                                                                                + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                                                                                                        + "JOIN m_customer mc ON (mc.cust_code=prv.cust_code) "
                                                                                                                + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
                                                                                                        + "JOIN m_container_type mct ON (scd.cont_type=mct.cont_type) "
                                                                                                        + "LEFT JOIN delivery_service ds ON (scd.cont_no=ds.cont_no AND scd.no_ppkb=ds.no_ppkb) "
                                                                                                                + "LEFT JOIN registration r ON (ds.no_reg=r.no_reg) "
                                                                                                                        + "LEFT JOIN m_customer mcs ON (r.cust_code=mcs.cust_code) "
                                                                                                + "WHERE scd.position = '" + ServiceContDischarge.LOCATION_CY + "' AND scd.is_delivery <> TRUE AND mc.cust_code = ? "
                                                                                                + "ORDER BY scd.block, scd.y_slot, scd.y_row, scd.y_tier;"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.exDischargeStocksPerCustomer_summary", query = "SELECT mc.cust_code, COUNT(CASE WHEN scd.cont_status = 'MTY' AND scd.cont_size = 40 THEN 1 END), "
                                                                                                        + "COUNT(CASE WHEN scd.cont_status = 'MTY' AND scd.cont_size = 20 THEN 1 END), "
                                                                                                        + "COUNT(CASE WHEN scd.cont_status = 'FCL' AND scd.cont_size = 40 THEN 1 END), "
                                                                                                        + "COUNT(CASE WHEN scd.cont_status = 'FCL' AND scd.cont_size = 20 THEN 1 END) "
                                                                                                + "FROM service_cont_discharge scd "
                                                                                                        + "JOIN planning_vessel pv ON (scd.no_ppkb=pv.no_ppkb) "
                                                                                                                + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                                                                                                        + "JOIN m_customer mc ON (mc.cust_code=prv.cust_code) "
                                                                                                                + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
                                                                                                        + "JOIN m_container_type mct ON (scd.cont_type=mct.cont_type) "
                                                                                                        + "LEFT JOIN delivery_service ds ON (scd.cont_no=ds.cont_no AND scd.no_ppkb=ds.no_ppkb) "
                                                                                                                + "LEFT JOIN registration r ON (ds.no_reg=r.no_reg) "
                                                                                                                        + "LEFT JOIN m_customer mcs ON (r.cust_code=mcs.cust_code) "
                                                                                                + "WHERE scd.position = '" + ServiceContDischarge.LOCATION_CY + "' AND scd.is_delivery <> TRUE AND mc.cust_code = ? "
                                                                                                + "GROUP BY mc.cust_code;"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findDeliverableContainersByPpkb", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier "
                                                                                                    + "FROM service_cont_discharge AS p "
                                                                                                            + "JOIN m_container_type AS c ON (p.cont_type=c.cont_type) "
                                                                                                            + "JOIN delivery_service ds ON (ds.cont_no=p.cont_no AND ds.no_ppkb=p.no_ppkb) "
                                                                                                                    + "JOIN invoice i ON (ds.no_reg = i.no_reg) "
                                                                                                            + "JOIN service_gate_delivery sgd ON (sgd.cont_no=p.cont_no AND sgd.no_ppkb=p.no_ppkb) "
                                                                                                    + "WHERE p.no_ppkb = ? AND i.payment_status = 'PAYMENT' AND p.is_delivery = FALSE AND p.position = '" + ServiceContDischarge.LOCATION_CY + "' "
                                                                                                    + "ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesForRekapDischarge", query = "SELECT p.id, p.cont_no FROM service_cont_discharge as p WHERE p.position = '" + ServiceContDischarge.LOCATION_CY + "' AND no_ppkb = ? ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesByPpkbDeliveryTrue", query = "SELECT p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier, p.position FROM service_cont_discharge as p, m_container_type as c WHERE p.cont_type=c.cont_type AND p.is_delivery = 'TRUE' AND no_ppkb = ? ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischarges", query = "SELECT r.cont_no, r.cont_size, r.cont_type, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,change(r.over_size),change(r.dg_label),r.id from service_cont_discharge r ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesStatusSatu", query = "SELECT r.cont_no, r.cont_size, r.cont_type, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,change(r.over_size),change(r.dg_label),r.id,r.bl_no from service_cont_discharge r where r.position='" + ServiceContDischarge.LOCATION_VESSEL + "' AND no_ppkb = ? ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesConfirm", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.v_bay, scd.v_row, scd.v_tier, scd.id, scd.position = '" + ServiceContDischarge.LOCATION_HT + "' deletable "
                                                                                                    + "FROM service_cont_discharge AS scd "
                                                                                                            + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                                    + "WHERE scd.position NOT IN ('" + ServiceContDischarge.LOCATION_VESSEL + "') AND scd.no_ppkb = ? "
                                                                                                    + "ORDER BY scd.modified_date ASC;"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesSelect", query = "SELECT scd.cont_no, scd.v_bay, scd.v_row, scd.v_tier, scd.id "
                                                                                                    + "FROM service_cont_discharge AS scd "
                                                                                                            + "LEFT JOIN service_gate_delivery sgd ON (scd.cont_no=sgd.cont_no AND scd.no_ppkb=sgd.no_ppkb) "
                                                                                                            + "JOIN m_commodity mc ON (scd.commodity_code=mc.commodity_code) "
                                                                                                    + "WHERE scd.position='" + ServiceContDischarge.LOCATION_VESSEL + "' AND ((mc.dangerous_class IS NULL OR mc.dangerous_class NOT IN ('1', '7')) OR (mc.dangerous_class IN ('1', '7') AND sgd.cont_no IS NOT NULL)) AND scd.no_ppkb = ? "
                                                                                                    + "ORDER BY scd.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findDischargableContainer", query = "SELECT scd.cont_no, scd.v_bay, scd.v_row, scd.v_tier, scd.id "
                                                                                                    + "FROM service_cont_discharge AS scd "
                                                                                                            + "LEFT JOIN service_gate_delivery sgd ON (scd.cont_no=sgd.cont_no AND scd.no_ppkb=sgd.no_ppkb) "
                                                                                                            + "JOIN m_commodity mc ON (scd.commodity_code=mc.commodity_code) "
                                                                                                    + "WHERE scd.position='" + ServiceContDischarge.LOCATION_VESSEL + "' AND ((mc.dangerous_class IS NULL OR mc.dangerous_class NOT IN ('1', '7')) OR (mc.dangerous_class IN ('1', '7') AND sgd.cont_no IS NOT NULL)) AND scd.no_ppkb = ? AND scd.cont_no = ? "
                                                                                                    + "ORDER BY scd.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.updateServiceContDischargesForDeleteAll", query = "UPDATE service_cont_discharge SET position = '" + ServiceContDischarge.LOCATION_VESSEL + "' WHERE id =?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesReefer", query = "SELECT scd.cont_no, scd.cont_size, mct.name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.id, scd.bl_no "
                                                                                                    + "FROM service_cont_discharge AS scd "
                                                                                                            + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                                            + "LEFT JOIN service_reefer sr ON (sr.no_ppkb = scd.no_ppkb AND sr.cont_no = scd.cont_no) "
                                                                                                    + "WHERE mct.type_in_general = 'RFR' AND scd.no_ppkb = ? AND sr.cont_no IS NULL AND scd.position = '" + ServiceContDischarge.LOCATION_CY + "' "
                                                                                                    + "ORDER BY scd.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findDeliverableCont", query = "SELECT scd.id, scd.cont_no, scd.no_ppkb, scd.y_slot, scd.y_row, scd.y_tier, scd.block, scd.is_behandle, scd.is_cfs, scd.is_inspection, 'discharge' as asal, mv.name vessel, scd.cont_size, scd.cont_status, scd.over_size, scd.dangerous, mcd.name condition, sgd.job_slip IS NOT NULL has_entering_gate "
                                                                                        + "FROM "
                                                                                                + "((service_cont_discharge scd "
                                                                                                        + "JOIN (delivery_service ds "
                                                                                                                + "JOIN invoice i ON (ds.no_reg = i.no_reg) "
                                                                                                                + "LEFT JOIN service_gate_delivery sgd ON (ds.job_slip = sgd.job_slip)) ON (ds.cont_no=scd.cont_no AND ds.no_ppkb=scd.no_ppkb)) "
                                                                                                        + "LEFT JOIN m_cont_damage mcd ON (scd.cont_damage=mcd.id)) "
                                                                                                        + "JOIN planning_vessel pv ON (pv.no_ppkb=scd.no_ppkb) "
                                                                                                                + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code) "
                                                                                        + "WHERE scd.cont_no = ? AND scd.position = '" + ServiceContDischarge.LOCATION_CY + "' AND scd.is_delivery = FALSE AND i.payment_status = 'PAYMENT';"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesConfirmServiced", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.block, scd.y_slot, scd.y_row, scd.y_tier, scd.id, false as topTier, scd.position = '" + ServiceContDischarge.LOCATION_CY + "' AND is_delivery = FALSE deletable "
                                                                                                            + "FROM service_cont_discharge AS scd "
                                                                                                                    + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                                            + "WHERE scd.position NOT IN ('" + ServiceContDischarge.LOCATION_VESSEL + "', '" + ServiceContDischarge.LOCATION_HT + "') AND scd.no_ppkb = ? "
                                                                                                            + "ORDER BY scd.modified_date DESC;"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesSelectServiced", query = "SELECT scd.cont_no, scd.v_bay, scd.v_row, scd.v_tier, scd.id FROM service_cont_discharge AS scd WHERE scd.position='" + ServiceContDischarge.LOCATION_HT + "' AND scd.no_ppkb = ? ORDER BY scd.modified_date ASC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.updateServiceContDischargesForDeleteAllPlacement", query = "UPDATE service_cont_discharge SET position = '" + ServiceContDischarge.LOCATION_HT + "' WHERE id =?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findContainerReefer", query = "SELECT scd.cont_type, scd.cont_no, scd.cont_size, scd.cont_status, scd.no_ppkb "
                                                                                        + "FROM service_cont_discharge AS scd "
                                                                                                + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                                + "LEFT JOIN service_reefer sr ON (sr.no_ppkb = scd.no_ppkb AND sr.cont_no = scd.cont_no) "
                                                                                        + "WHERE mct.type_in_general = 'RFR' AND scd.cont_no = ? AND sr.cont_no IS NULL AND scd.position = '" + ServiceContDischarge.LOCATION_CY + "'"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesDeliveryService", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier,p.bl_no, p.is_import "
                                                                                                            + "FROM service_cont_discharge AS p "
                                                                                                                    + "JOIN m_container_type AS c ON (p.cont_type=c.cont_type) "
                                                                                                                    + "JOIN m_commodity mc ON (p.commodity_code=mc.commodity_code) "
                                                                                                                    + "LEFT JOIN delivery_service ds ON (ds.no_ppkb = p.no_ppkb AND ds.cont_no = p.cont_no) "
                                                                                                            + "WHERE p.no_ppkb = ? AND ds.cont_no IS NULL AND p.is_delivery = 'FALSE' "
                                                                                                                    + "AND (p.position = '" + ServiceContDischarge.LOCATION_CY + "' "
                                                                                                                            + "OR (p.position = '" + ServiceContDischarge.LOCATION_VESSEL + "' AND c.type_in_general = 'RFR' AND (SELECT value_string FROM m_setting_app WHERE id = 'ebtos.validation.level') = 'MEDIUM') "
                                                                                                                            + "OR (p.position = '" + ServiceContDischarge.LOCATION_VESSEL + "' AND (SELECT value_string FROM m_setting_app WHERE id = 'ebtos.validation.level') = 'LOW') "
                                                                                                                            + "OR (p.position = '" + ServiceContDischarge.LOCATION_VESSEL + "' AND (mc.dangerous_class = '1' OR mc.dangerous_class = '7')) "
                                                                                                                         + ") "
                                                                                                            + "ORDER BY p.id DESC;"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesAngsurService", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier,p.bl_no FROM service_cont_discharge as p, m_container_type as c WHERE p.cont_type=c.cont_type AND p.is_delivery = 'FALSE' AND p.position = '" + ServiceContDischarge.LOCATION_CY + "' AND no_ppkb = ? AND p.cont_no NOT IN (SELECT cont_no FROM angsur_service WHERE no_ppkb = p.no_ppkb) ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesStrippingService", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier,p.bl_no "
                                                                                                            + "FROM service_cont_discharge as p "
                                                                                                                    + "JOIN m_container_type as c ON (p.cont_type=c.cont_type) "
                                                                                                                            + "LEFT JOIN (SELECT ss.cont_no, r.no_ppkb "
                                                                                                                                    + "FROM stripping_service ss "
                                                                                                                                            + "JOIN registration r ON (r.no_reg=ss.no_reg)) ss ON (p.cont_no=ss.cont_no AND p.no_ppkb=ss.no_ppkb) "
                                                                                                            + "WHERE ss.cont_no IS NULL AND p.is_delivery = 'FALSE' AND p.is_behandle = 'FALSE' AND p.is_cfs = 'FALSE' AND p.is_inspection = 'FALSE' AND p.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesStuffingService", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, com.name, p.over_size, p.dangerous, p.dg_label,p.bl_no, p.mlo FROM service_cont_discharge as p, m_container_type as c, m_commodity as com WHERE p.commodity_code = com.commodity_code AND p.cont_status = 'MTY' AND p.cont_type=c.cont_type AND p.is_delivery = 'FALSE' AND p.no_ppkb = ? AND p.cont_no NOT IN (SELECT cont_no FROM stuffing_service WHERE no_ppkb = p.no_ppkb) ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesBehandleService", query = "SELECT p.cont_no, p.cont_size, c.type_in_general, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier "
                                                                                                            + "FROM service_cont_discharge as p "
                                                                                                                    + "JOIN m_container_type as c ON (p.cont_type=c.cont_type) "
                                                                                                                    + "LEFT JOIN behandle_service bs ON (p.cont_no=bs.cont_no AND p.no_ppkb=bs.no_ppkb) "
                                                                                                            + "WHERE bs.cont_no IS NULL AND p.is_delivery = 'FALSE' AND p.is_behandle = 'FALSE' AND p.is_cfs = 'FALSE' AND p.is_inspection = 'FALSE' AND p.no_ppkb = ? AND p.position='03' AND p.cont_status='FCL' "
                                                                                                            + "ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findConvertableToLoadContainers", query = "SELECT p.cont_no, p.cont_size, c.type_in_general, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier "
                                                                                                            + "FROM service_cont_discharge as p "
                                                                                                                    + "JOIN m_container_type as c ON (p.cont_type=c.cont_type) "
                                                                                                                    + "LEFT JOIN discharge_to_load_service dtl ON (dtl.service_cont_discharge_id=p.id) "
                                                                                                                    + "LEFT JOIN delivery_service ds ON (ds.cont_no=p.cont_no AND ds.no_ppkb=p.no_ppkb) "
                                                                                                                    + "LEFT JOIN behandle_service bs ON (bs.cont_no=p.cont_no AND bs.no_ppkb=p.no_ppkb) "
                                                                                                            + "WHERE dtl.job_slip IS NULL AND ds.cont_no IS NULL AND bs.cont_no IS NULL AND p.is_delivery = 'FALSE' AND p.is_behandle = 'FALSE' AND p.is_cfs = 'FALSE' AND p.is_inspection = 'FALSE' AND p.no_ppkb = ? AND p.position='03' "
                                                                                                            + "ORDER BY p.id DESC;"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesMovement", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier, p.is_behandle, p.is_cfs, p.is_inspection, CASE WHEN NOT is_behandle AND NOT is_cfs AND NOT is_inspection THEN TRUE ELSE FALSE END AS is_cy FROM service_cont_discharge as p, m_container_type as c WHERE p.cont_type=c.cont_type AND p.is_delivery = 'FALSE' AND p.position = '" + ServiceContDischarge.LOCATION_CY + "' AND p.block IS NOT NULL ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesMovementBehandle", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier FROM service_cont_discharge as p, m_container_type as c WHERE p.cont_type=c.cont_type AND p.is_delivery = 'FALSE' AND p.is_cfs = 'FALSE' AND p.is_inspection = 'FALSE' ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesMovementInspection", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier FROM service_cont_discharge as p, m_container_type as c WHERE p.cont_type=c.cont_type AND p.is_delivery = 'FALSE' AND p.is_behandle = 'FALSE' AND p.is_cfs = 'FALSE' ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesByContAndPpkb", query = "SELECT p.id,p.no_ppkb,p.block,p.y_slot,p.y_row,p.y_tier FROM service_cont_discharge as p WHERE p.is_delivery = 'FALSE' AND p.no_ppkb=? AND p.cont_no=? "),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findDischargeMonitoringByPPKB", query = "SELECT p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, change(p.over_size), change(p.dangerous), change(p.dg_label), e1.equip_code, e1.start_time, e1.end_time, e2.equip_code, e2.start_time, e2.end_time, e3.equip_code, e3.start_time, e3.end_time, p.block, p.start_placement_date, mc.name "
                                                                                                + "FROM "
                                                                                                        + "service_cont_discharge p "
                                                                                                                + "LEFT JOIN m_container_type c ON p.cont_type=c.cont_type "
                                                                                                                + "LEFT JOIN equipment e1 ON p.cont_no = e1.cont_no AND p.no_ppkb = e1.no_ppkb AND e1.operation = 'DISCHARGE' AND e1.equipment_act_code = 'STEVEDORING' "
                                                                                                                + "LEFT JOIN equipment e2 ON p.cont_no = e2.cont_no AND p.no_ppkb = e2.no_ppkb AND e2.operation = 'DISCHARGE' AND e2.equipment_act_code = 'H/T' "
                                                                                                                + "LEFT JOIN equipment e3 ON p.cont_no = e3.cont_no AND p.no_ppkb = e3.no_ppkb AND e3.operation = 'DISCHARGE' AND e3.equipment_act_code = 'LIFTOFF' "
                                                                                                                + "LEFT JOIN delivery_service ds ON (p.cont_no=ds.cont_no AND p.no_ppkb=ds.no_ppkb) "
                                                                                                                        + "LEFT JOIN registration r ON (ds.no_reg=r.no_reg) "
                                                                                                                                + "LEFT JOIN m_customer mc ON (r.cust_code=mc.cust_code) "
                                                                                                + "WHERE p.no_ppkb = ? AND p.position = ? "),
       @NamedNativeQuery(name = "ServiceContDischarge.Native.findDischargeMonitoringByPPKB1", query = "SELECT p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, change(p.over_size), change(p.dangerous), change(p.dg_label), e1.equip_code, e1.start_time, e1.end_time, e2.equip_code, e2.start_time, e2.end_time, e3.equip_code, e3.start_time, e3.end_time, p.block, p.start_placement_date, mc.name "
                                                                                                + "FROM "
                                                                                                        + "service_cont_discharge p "
                                                                                                                + "LEFT JOIN m_container_type c ON p.cont_type=c.cont_type "
                                                                                                                + "LEFT JOIN equipment e1 ON p.cont_no = e1.cont_no AND p.no_ppkb = e1.no_ppkb AND e1.operation = 'DISCHARGE' AND e1.equipment_act_code = 'STEVEDORING' "
                                                                                                                + "LEFT JOIN equipment e2 ON p.cont_no = e2.cont_no AND p.no_ppkb = e2.no_ppkb AND e2.operation = 'DISCHARGE' AND e2.equipment_act_code = 'H/T' "
                                                                                                                + "LEFT JOIN equipment e3 ON p.cont_no = e3.cont_no AND p.no_ppkb = e3.no_ppkb AND e3.operation = 'DISCHARGE' AND e3.equipment_act_code = 'LIFTOFF' "
                                                                                                                + "LEFT JOIN delivery_service ds ON (p.cont_no=ds.cont_no AND p.no_ppkb=ds.no_ppkb) "
                                                                                                                        + "LEFT JOIN registration r ON (ds.no_reg=r.no_reg) "
                                                                                                                                + "LEFT JOIN m_customer mc ON (r.cust_code=mc.cust_code) "
                                                                                                + "WHERE p.no_ppkb = ? AND p.position = ? AND p.is_delivery = ? "),
        @NamedNativeQuery(name = "ServiceContDischarge.Native.findDischargeMonitoringByPPKB2", query = "SELECT p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, change(p.over_size), change(p.dangerous), change(p.dg_label), e1.equip_code, e1.start_time, e1.end_time, e2.equip_code, e2.start_time, e2.end_time, e3.equip_code, e3.start_time, e3.end_time, p.block, p.start_placement_date, mc.name "
                                                                                                + "FROM "
                                                                                                        + "service_cont_discharge p "
                                                                                                                + "LEFT JOIN m_container_type c ON p.cont_type=c.cont_type "
                                                                                                                + "LEFT JOIN equipment e1 ON p.cont_no = e1.cont_no AND p.no_ppkb = e1.no_ppkb AND e1.operation = 'DISCHARGE' AND e1.equipment_act_code = 'STEVEDORING' "
                                                                                                                + "LEFT JOIN equipment e2 ON p.cont_no = e2.cont_no AND p.no_ppkb = e2.no_ppkb AND e2.operation = 'DISCHARGE' AND e2.equipment_act_code = 'H/T' "
                                                                                                                + "LEFT JOIN equipment e3 ON p.cont_no = e3.cont_no AND p.no_ppkb = e3.no_ppkb AND e3.operation = 'DISCHARGE' AND e3.equipment_act_code = 'LIFTOFF' "
                                                                                                                + "LEFT JOIN delivery_service ds ON (p.cont_no=ds.cont_no AND p.no_ppkb=ds.no_ppkb) "
                                                                                                                        + "LEFT JOIN registration r ON (ds.no_reg=r.no_reg) "
                                                                                                                                + "LEFT JOIN m_customer mc ON (r.cust_code=mc.cust_code) "
                                                                                                + "WHERE p.no_ppkb = ? AND p.is_delivery= ? "),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findDischargeMonitoringByPPKBFront", query = "SELECT p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, change(p.over_size), change(p.dangerous), change(p.dg_label), e1.equip_code, e1.start_time, e1.end_time, e2.equip_code, e2.start_time, e2.end_time, e3.equip_code, e3.start_time, e3.end_time, p.block, p.start_placement_date, mc.name "
                                                                                                + "FROM "
                                                                                                        + "service_cont_discharge p "
                                                                                                                + "LEFT JOIN m_container_type c ON p.cont_type=c.cont_type "
                                                                                                                + "LEFT JOIN equipment e1 ON p.cont_no = e1.cont_no AND p.no_ppkb = e1.no_ppkb AND e1.operation = 'DISCHARGE' AND e1.equipment_act_code = 'STEVEDORING' "
                                                                                                                + "LEFT JOIN equipment e2 ON p.cont_no = e2.cont_no AND p.no_ppkb = e2.no_ppkb AND e2.operation = 'DISCHARGE' AND e2.equipment_act_code = 'H/T' "
                                                                                                                + "LEFT JOIN equipment e3 ON p.cont_no = e3.cont_no AND p.no_ppkb = e3.no_ppkb AND e3.operation = 'DISCHARGE' AND e3.equipment_act_code = 'LIFTOFF' "
                                                                                                                + "LEFT JOIN delivery_service ds ON (p.cont_no=ds.cont_no AND p.no_ppkb=ds.no_ppkb) "
                                                                                                                        + "LEFT JOIN registration r ON (ds.no_reg=r.no_reg) "
                                                                                                                                + "LEFT JOIN m_customer mc ON (r.cust_code=mc.cust_code) "
                                                                                                + "WHERE p.no_ppkb = ? AND p.position NOT IN ('01','02') "),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findUcDischargeMonitoringByPPKB", query = "SELECT uc.bl_no, mc.name, uc.unit, uc.weight, uc.cubication AS vol, uc.truck_loosing, e1.equip_code AS cc_code, e1.start_time AS start_stv, e1.end_time AS end_stv, e2.equip_code AS ht_code, e2.start_time AS start_ht, e2.end_time AS end_ht, e3.equip_code AS tt_code, e3.start_time AS start_tt, e3.end_time AS end_tt "
                                                                                                    + "FROM uncontainerized_service uc JOIN m_commodity  mc ON uc.commodity = mc.commodity_code "
                                                                                                    + "LEFT JOIN equipment e1 ON e1.no_ppkb::text = uc.no_ppkb::text AND e1.bl_no::text = uc.bl_no::text AND e1.equipment_act_code::text = 'STEVEDORING' AND (e1.operation::text = 'DISCHARGEUC' or e1.operation::text = 'TRANSDISCUC') "
                                                                                                    + "LEFT JOIN equipment e2 ON e2.no_ppkb::text = uc.no_ppkb::text AND e2.bl_no::text = uc.bl_no::text AND e2.equipment_act_code::text = 'H/T' AND (e2.operation::text = 'DISCHARGEUC' or e2.operation::text = 'TRANSDISCUC') "
                                                                                                    + "LEFT JOIN equipment e3 ON e3.no_ppkb::text = uc.no_ppkb::text AND e3.bl_no::text = uc.bl_no::text AND e3.equipment_act_code::text = 'LIFTOFF' AND (e3.operation::text = 'DISCHARGEUC' or e3.operation::text = 'TRANSDISCUC') "
                                                                                                    + "WHERE uc.operation = 'DISCHARGE' AND uc.is_shifting = FALSE AND uc.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findByContNoMov", query = "SELECT cont_no, start_placement_date, v_bay, v_row, v_tier, id, y_slot, y_row, y_tier, block, no_ppkb, cont_size FROM service_cont_discharge WHERE cont_no = ? AND position = ? AND is_delivery = ? AND is_behandle = ? AND is_cfs = ? AND is_inspection = ? "),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findReeferDischargeService", query = "SELECT scd.cont_no, scd.cont_size, mct.name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.id, scd.bl_no, COALESCE(MAX(rds.valid_date), sr.plug_on) plug_on, MAX(sr.plug_off) plug_off  "
                                                                                                + "FROM service_cont_discharge AS scd "
                                                                                                        + "JOIN service_reefer sr ON (scd.no_ppkb = sr.no_ppkb AND scd.cont_no = sr.cont_no) "
                                                                                                        + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                                        + "LEFT JOIN reefer_discharge_service rds ON (scd.no_ppkb = rds.no_ppkb AND scd.cont_no = rds.cont_no) "
                                                                                                                + "LEFT JOIN (SELECT rds.cont_no, rds.no_ppkb, rds.no_reg "
                                                                                                                        + "FROM reefer_discharge_service rds "
                                                                                                                                + "JOIN registration r ON (rds.no_reg=r.no_reg ) "
                                                                                                                        + "WHERE r.status_service IN ('registrasi','confirm')) rds2 ON (rds.no_ppkb = rds2.no_ppkb AND rds.cont_no = rds2.cont_no) "
                                                                                                + "WHERE scd.no_ppkb = ? AND scd.position NOT IN ('01', '02') AND sr.plug_on IS NOT NULL AND (sr.plug_off IS NULL OR sr.plug_off > rds.valid_date) AND rds2.cont_no IS NULL "
                                                                                                + "GROUP BY scd.cont_no, scd.cont_size, mct.name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.id, scd.bl_no, sr.plug_on "
                                                                                                + "ORDER BY scd.id DESC;"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findHandlingDischargeMonitoring", query = "SELECT p.cont_type, p.cont_no, p.cont_status, p.cont_size, c.type_in_general as name, e1.equip_code, e1.start_time, e1.end_time, e2.equip_code, e2.start_time, e2.end_time, e3.equip_code, e3.start_time, e3.end_time, p.seal_no "
    + "FROM (((service_cont_discharge p LEFT JOIN m_container_type c ON p.cont_type=c.cont_type) "
    + "LEFT JOIN equipment e1 ON p.cont_no = e1.cont_no AND p.no_ppkb = e1.no_ppkb AND e1.operation = 'DISCHARGE' AND e1.equipment_act_code = 'STEVEDORING') "
    + "LEFT JOIN equipment e2 ON p.cont_no = e2.cont_no AND p.no_ppkb = e2.no_ppkb AND e2.operation = 'DISCHARGE' AND e2.equipment_act_code = 'H/T') "
    + "LEFT JOIN equipment e3 ON p.cont_no = e3.cont_no AND p.no_ppkb = e3.no_ppkb AND e3.operation = 'DISCHARGE' AND e3.equipment_act_code = 'LIFTOFF' "
    + "WHERE p.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findRecapPenumpukan", query = "SELECT scd.id, scd.cont_no, scd.cont_type, scd.cont_size, scd.cont_status, scd.cont_gross, CASE WHEN scd.over_size=true THEN 'Yes' WHEN scd.over_size=false THEN 'No' END as over_size, rp.charge_masa_1, rp.jasa_dermaga, rp.total_charge, curr.symbol, curr.language, curr.country,mt.type_in_general as name FROM service_cont_discharge scd, recap_penumpukan rp, m_currency curr ,m_container_type mt WHERE scd.cont_no = rp.cont_no AND scd.no_ppkb = rp.no_ppkb AND rp.curr_code = curr.curr_code AND mt.cont_type=scd.cont_type AND scd.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findByContNo", query = "SELECT cont_no, start_placement_date, v_bay, v_row, v_tier, id, y_slot, y_row, y_tier, block, no_ppkb, cont_size, 's' as origin FROM service_cont_discharge WHERE cont_no = ? AND position = ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargeByPpkbAndContNoFront", query = "SELECT r.id, r.no_ppkb,r.cont_no,r.cont_size,r.cont_status,r.cont_gross,r.seal_no,r.block, r.y_slot, r.y_row, r.y_tier FROM service_cont_discharge r WHERE r.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesDeliveryServiceByBL", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier,p.bl_no, p.is_import "
                                                                                                            + "FROM service_cont_discharge AS p "
                                                                                                                    + "JOIN m_container_type AS c ON (p.cont_type=c.cont_type) "
                                                                                                                    + "JOIN m_commodity mc ON (p.commodity_code=mc.commodity_code) "
                                                                                                                    + "LEFT JOIN delivery_service ds ON (ds.no_ppkb = p.no_ppkb AND ds.cont_no = p.cont_no) "
                                                                                                            + "WHERE p.bl_no = ? AND p.no_ppkb = ? AND ds.cont_no IS NULL AND p.is_delivery = 'FALSE' "
                                                                                                                    + "AND (p.position = '" + ServiceContDischarge.LOCATION_CY + "' "
                                                                                                                            + "OR (p.position = '" + ServiceContDischarge.LOCATION_VESSEL + "' AND c.type_in_general = 'RFR' AND (SELECT value_string FROM m_setting_app WHERE id = 'ebtos.validation.level') = 'MEDIUM') "
                                                                                                                            + "OR (p.position = '" + ServiceContDischarge.LOCATION_VESSEL + "' AND (SELECT value_string FROM m_setting_app WHERE id = 'ebtos.validation.level') = 'LOW') "
                                                                                                                            + "OR (p.position = '" + ServiceContDischarge.LOCATION_VESSEL + "' AND (mc.dangerous_class = '1' OR mc.dangerous_class = '7')) "
                                                                                                                         + ") "
                                                                                                            + "ORDER BY p.id DESC;"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesStrippingServiceByBL", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier,p.bl_no FROM service_cont_discharge as p, m_container_type as c WHERE p.cont_type=c.cont_type AND p.is_delivery = 'FALSE' AND p.is_behandle = 'FALSE' AND p.is_cfs = 'FALSE' AND p.is_inspection = 'FALSE' AND p.bl_no = ? AND no_ppkb = ? AND p.cont_no NOT IN (SELECT cont_no FROM stripping_service WHERE no_ppkb = p.no_ppkb) ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargesStuffingServiceByBL", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, com.name, p.over_size, p.dangerous, p.dg_label,p.bl_no,p.mlo FROM service_cont_discharge as p, m_container_type as c, m_commodity as com WHERE p.commodity_code = com.commodity_code AND p.cont_status = 'MTY' AND p.cont_type=c.cont_type AND p.is_delivery = 'FALSE' AND p.bl_no = ? AND p.no_ppkb = ? AND p.cont_no NOT IN (SELECT cont_no FROM stuffing_service WHERE no_ppkb = p.no_ppkb) ORDER BY p.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findReeferDischargeServiceByBL", query = "SELECT scd.cont_no, scd.cont_size, mct.name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.id,scd.bl_no,sr.plug_on "
                                                                                                + "FROM service_cont_discharge AS scd "
                                                                                                        + "JOIN service_reefer sr ON (scd.no_ppkb = sr.no_ppkb AND scd.cont_no = sr.cont_no) "
                                                                                                        + "LEFT JOIN reefer_discharge_service rds ON (scd.no_ppkb = rds.no_ppkb AND scd.cont_no = rds.cont_no) "
                                                                                                        + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                                + "WHERE scd.bl_no = ? AND scd.no_ppkb = ? AND scd.position = '" + ServiceContDischarge.LOCATION_CY + "' AND scd.is_delivery = false AND rds.cont_no IS NULL ORDER BY scd.id DESC"),
    @NamedNativeQuery(name="ServiceContDischarge.Native.findServiceContDischargeUnplacement", query= "SELECT scd.cont_no, scd.cont_size, scd.cont_status, scd.v_bay, scd.v_row, scd.v_tier, scd.id FROM service_cont_discharge scd WHERE scd.position='" + ServiceContDischarge.LOCATION_VESSEL + "' AND scd.no_ppkb=?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findContMovementMobile", query = "SELECT id, cont_no, no_ppkb, y_slot, y_row, y_tier, block, is_behandle, is_cfs, is_inspection, 'discharge' as asal FROM service_cont_discharge WHERE cont_no = ? AND position = ? AND is_delivery = ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findContainerDetails", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, mc.name, scd.cont_gross, scd.gross_class, scd.seal_no, change(scd.over_size), change(scd.dangerous), change(scd.dg_label), scd.id, scd.no_ppkb, scd.bl_no, scd.position "
                                                                                        + "FROM service_cont_discharge scd "
                                                                                                + "JOIN m_container_type mct ON (scd.cont_type = mct.cont_type) "
                                                                                                + "JOIN m_commodity mc ON (scd.commodity_code =  mc.commodity_code) "
                                                                                        + "WHERE scd.no_ppkb = ? AND scd.cont_no = ? "
                                                                                        + "ORDER BY scd.modified_date DESC;"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findContainerDetailsPPKBnull", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, mc.name, scd.cont_gross, scd.gross_class, scd.seal_no, change(scd.over_size), change(scd.dangerous), change(scd.dg_label), scd.id, scd.no_ppkb, scd.bl_no, scd.position "
                                                                                        + "FROM service_cont_discharge scd "
                                                                                                + "JOIN m_container_type mct ON (scd.cont_type = mct.cont_type) "
                                                                                                + "JOIN m_commodity mc ON (scd.commodity_code =  mc.commodity_code) "
                                                                                        + "WHERE scd.cont_no = ? "
                                                                                        + "ORDER BY scd.modified_date DESC;"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findContainerDetailsCONTnull", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, mc.name, scd.cont_gross, scd.gross_class, scd.seal_no, change(scd.over_size), change(scd.dangerous), change(scd.dg_label), scd.id, scd.no_ppkb, scd.bl_no, scd.position "
                                                                                        + "FROM service_cont_discharge scd "
                                                                                                + "JOIN m_container_type mct ON (scd.cont_type = mct.cont_type) "
                                                                                                + "JOIN m_commodity mc ON (scd.commodity_code =  mc.commodity_code) "
                                                                                        + "WHERE scd.no_ppkb = ? "
                                                                                        + "ORDER BY scd.modified_date DESC;"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findContainerNumbers", query = "SELECT cont_no FROM service_cont_discharge WHERE upper(no_ppkb) LIKE ? AND upper(cont_no) LIKE ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findContIds", query = "SELECT find_contid(?,?,?)"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findPKs", query = "SELECT find_pk(?,?,?,?)"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceCondischargeToLoads", query = "SELECT scd.id,scd.bl_no,scd.cont_no,mct.name,scd.cont_gross,mc.name,scd.seal_no, scd.cont_size,scd.cont_status,mp.name,mp1.name,scd.block, scd.y_slot, scd.y_row, scd.y_tier FROM service_cont_discharge AS scd, m_container_type AS mct,m_commodity as mc,m_port mp,m_port mp1 WHERE scd.cont_type = mct.cont_type AND scd.position='" + ServiceContDischarge.LOCATION_CY + "' AND scd.commodity_code=mc.commodity_code AND scd.load_port=mp.port_code AND scd.disch_port=mp1.port_code AND scd.is_delivery = FALSE AND scd.no_ppkb=? AND scd.cont_no NOT IN (select cont_no from service_receiving where no_ppkb=scd.no_ppkb) ORDER BY scd.id DESC"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceCondischargeMonitoringContDisch", query = "SELECT p.bl_no,p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, com.name,p.start_placement_date,ds.masa_1,ds.masa_2,p.position FROM (service_cont_discharge as p LEFT JOIN delivery_service ds ON p.no_ppkb=ds.no_ppkb AND p.cont_no=ds.cont_no), m_container_type c, m_commodity as com WHERE p.commodity_code = com.commodity_code AND p.cont_type=c.cont_type AND p.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findContainerThatHasConfirmed", query = "SELECT scd.cont_no, scd.cont_size, mct.name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.block, scd.y_slot, scd.y_row, scd.y_tier, scd.id "
    + "FROM service_cont_discharge AS scd, m_container_type AS mct "
    + "WHERE scd.cont_type = mct.cont_type AND scd.position='" + ServiceContDischarge.LOCATION_CY + "' AND is_delivery = FALSE AND scd.no_ppkb = ? AND scd.cont_no = ?"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findServiceContDischargeByPPKBAndBlock",
    query = "SELECT scd.cont_no, scd.cont_size, (SELECT mct.type_in_general FROM m_container_type mct "
    + "WHERE scd.cont_type = mct.cont_type) AS tipe,  scd.cont_status,  "
    + "(SELECT mgc.description FROM m_gross_class mgc WHERE scd.gross_class::TEXT = mgc.gross_class::TEXT) AS gross_class, "
    + "scd.dangerous, scd.over_size,scd.is_import,  scd.block, scd.y_slot, scd.y_row, scd.y_tier "
    + "FROM service_cont_discharge scd "
    + "WHERE (scd.position = '01' or scd.position = '02') and scd.block =? and scd.no_ppkb = ? "
    + " ORDER BY scd.cont_size, scd.gross_class, scd.cont_type"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findSearchingContainerDischargeFront", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, change(p.over_size), change(p.dangerous), change(p.dg_label), e1.equip_code, e1.start_time, e1.end_time, e2.equip_code, e2.start_time, e2.end_time, e3.equip_code, e3.start_time, e3.end_time,m1.name asalPelabuhan,m2.name as tujuanPelabuhan,mv.name,p.no_ppkb,ps.voy_in,ps.voy_out FROM m_port m1,m_port m2,planning_vessel pv,m_vessel mv,preservice_vessel ps,((((service_cont_discharge p LEFT JOIN m_container_type c ON p.cont_type=c.cont_type) LEFT JOIN equipment e1 ON p.cont_no = e1.cont_no AND p.no_ppkb = e1.no_ppkb AND e1.operation = 'DISCHARGE' AND e1.equipment_act_code = 'STEVEDORING') LEFT JOIN equipment e2 ON p.cont_no = e2.cont_no AND p.no_ppkb = e2.no_ppkb AND e2.operation = 'DISCHARGE' AND e2.equipment_act_code = 'H/T') LEFT JOIN equipment e3 ON p.cont_no = e3.cont_no AND p.no_ppkb = e3.no_ppkb AND e3.operation = 'DISCHARGE' AND e3.equipment_act_code = 'LIFTOFF') where p.cont_no LIKE ('%'|| ? ||'%') AND m1.port_code=p.load_port AND m2.port_code=p.disch_port AND p.no_ppkb=pv.no_ppkb AND pv.vessel_code=mv.vessel_code AND ps.booking_code=pv.booking_code order by p.id desc"),
    @NamedNativeQuery(name = "ServiceContDischarge.Native.findSearchingContainerTrackingDischargeFront", query = "SELECT r.id, r.no_ppkb,r.bl_no,r.cont_no,r.cont_size,r.cont_status,r.cont_gross,r.seal_no,r.block, r.y_slot, r.y_row, r.y_tier,r.position FROM service_cont_discharge r where r.no_ppkb=? and r.cont_no=? order by r.id desc limit 1 ")})

public class ServiceContDischarge implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;
    public static final String LOCATION_MOVEMENT = "MV";
    public static final String LOCATION_VESSEL = "01";
    public static final String LOCATION_HT = "02";
    public static final String LOCATION_CY = "03";
    public static final String LOCATION_OUTSIDE = "04";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "crane", length = 2)
    private String crane;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Basic(optional = false)
    @Column(name = "cont_size", nullable = false)
    private Short contSize;
    @Column(name = "no_ppkb", insertable=false, updatable=false)
    private String noPpkb;
    @Basic(optional = false)
    @Column(name = "cont_status", nullable = false, length = 5)
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "over_size")
    private Boolean overSize = false;
    @Column(name = "dangerous")
    private Boolean dangerous = false;
    @Column(name = "dg_label")
    private Boolean dgLabel = false;
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
    @Column(name = "start_placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startPlacementDate;
    @Column(name = "position", length = 2)
    private String position;
    @Column(name = "is_delivery")
    private Boolean isDelivery = false;
    @Column(name = "is_behandle")
    private Boolean isBehandle = false;
    @Column(name = "is_cfs")
    private Boolean isCfs = false;
    @Column(name = "is_inspection")
    private Boolean isInspection = false;
    @Column(name = "ex_stripping")
    private Boolean exStripping = false;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private ServiceVessel serviceVessel;
    @JoinColumn(name = "block", referencedColumnName = "block")
    @ManyToOne
    private MasterYard masterYard;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type", nullable = false)
    @ManyToOne(optional = false)
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCommodity masterCommodity;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @JoinColumn(name = "cont_damage", referencedColumnName = "id")
    @ManyToOne
    private MasterContDamage masterContDamage;
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
    @Column(name = "position_bay")
    private String positionBay;
    @Column(name = "cancel_loading")
    private Boolean cancelLoading = false;
    @Column(name = "load_port")
    private String loadPort;
    @Column(name = "disch_port")
    private String dischPort;
    @Column(name = "is_seling")
    private Boolean isSeling = false;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Basic(optional = false)
    @Column(name = "is_import", nullable = false)
    private boolean isImport;
    @Basic(optional = false)
    @Column(name = "discharge_to_load", nullable = false)
    private boolean dischargeToLoad = false;


    public ServiceContDischarge() {
    }

    public ServiceContDischarge(Integer id) {
        this.id = id;
    }

    public ServiceContDischarge(Integer id, String contNo, short contSize, String contStatus, MasterCustomer mlo) {
        this.id = id;
        this.contNo = contNo;
        this.mlo = mlo;
        this.contSize = contSize;
        this.contStatus = contStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
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

    public Date getStartPlacementDate() {
        return startPlacementDate;
    }

    public void setStartPlacementDate(Date startPlacementDate) {
        this.startPlacementDate = startPlacementDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Boolean isDelivery) {
        this.isDelivery = isDelivery;
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

    public Boolean getExStripping() {
        return exStripping;
    }

    public void setExStripping(Boolean exStripping) {
        this.exStripping = exStripping;
    }

    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;

        if (serviceVessel != null) {
            noPpkb = serviceVessel.getNoPpkb();
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

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }

    public String getGrossClass() {
        return grossClass;
    }

    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
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

    public Boolean getCancelLoading() {
        return cancelLoading;
    }

    public void setCancelLoading(Boolean cancelLoading) {
        this.cancelLoading = cancelLoading;
    }

    public String getDischPort() {
        return dischPort;
    }

    public void setDischPort(String dischPort) {
        this.dischPort = dischPort;
    }

    public String getLoadPort() {
        return loadPort;
    }

    public void setLoadPort(String loadPort) {
        this.loadPort = loadPort;
    }

    public Boolean getIsSeling() {
        return isSeling;
    }

    public void setIsSeling(Boolean isSeling) {
        this.isSeling = isSeling;
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public MasterContDamage getMasterContDamage() {
        return masterContDamage;
    }

    public void setMasterContDamage(MasterContDamage masterContDamage) {
        this.masterContDamage = masterContDamage;
    }

    public boolean getIsImport() {
        return isImport;
    }

    public void setIsImport(boolean isImport) {
        this.isImport = isImport;
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }

    public boolean isDischargeToLoad() {
        return dischargeToLoad;
    }

    public void setDischargeToLoad(boolean dischargeToLoad) {
        this.dischargeToLoad = dischargeToLoad;
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
        if (!(object instanceof ServiceContDischarge)) {
            return false;
        }
        ServiceContDischarge other = (ServiceContDischarge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceContDischarge[id=" + id + "]";
    }
}
