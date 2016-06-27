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
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "service_cont_load")
@NamedQueries({
    @NamedQuery(name = "ServiceContLoad.findAll", query = "SELECT s FROM ServiceContLoad s"),
    @NamedQuery(name = "ServiceContLoad.findById", query = "SELECT s FROM ServiceContLoad s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceContLoad.findByCrane", query = "SELECT s FROM ServiceContLoad s WHERE s.crane = :crane"),
    @NamedQuery(name = "ServiceContLoad.findByContNo", query = "SELECT s FROM ServiceContLoad s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceContLoad.findByContSize", query = "SELECT s FROM ServiceContLoad s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceContLoad.findByContStatus", query = "SELECT s FROM ServiceContLoad s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceContLoad.findByContGross", query = "SELECT s FROM ServiceContLoad s WHERE s.contGross = :contGross"),
    @NamedQuery(name = "ServiceContLoad.findBySealNo", query = "SELECT s FROM ServiceContLoad s WHERE s.sealNo = :sealNo"),
    @NamedQuery(name = "ServiceContLoad.findByOverSize", query = "SELECT s FROM ServiceContLoad s WHERE s.overSize = :overSize"),
    @NamedQuery(name = "ServiceContLoad.findByDangerous", query = "SELECT s FROM ServiceContLoad s WHERE s.dangerous = :dangerous"),
    @NamedQuery(name = "ServiceContLoad.findByDgLabel", query = "SELECT s FROM ServiceContLoad s WHERE s.dgLabel = :dgLabel"),
    @NamedQuery(name = "ServiceContLoad.findByContNoAndPosition", query = "SELECT s FROM ServiceContLoad s WHERE s.position = :position AND s.serviceVessel.noPpkb = :noPpkb AND s.contNo = :contNo AND s.statusCancelLoading = FALSE"),
    @NamedQuery(name = "ServiceContLoad.findByYSlot", query = "SELECT s FROM ServiceContLoad s WHERE s.ySlot = :ySlot"),
    @NamedQuery(name = "ServiceContLoad.findByYRow", query = "SELECT s FROM ServiceContLoad s WHERE s.yRow = :yRow"),
    @NamedQuery(name = "ServiceContLoad.findByYTier", query = "SELECT s FROM ServiceContLoad s WHERE s.yTier = :yTier"),
    @NamedQuery(name = "ServiceContLoad.findByVBay", query = "SELECT s FROM ServiceContLoad s WHERE s.vBay = :vBay"),
    @NamedQuery(name = "ServiceContLoad.findByVRow", query = "SELECT s FROM ServiceContLoad s WHERE s.vRow = :vRow"),
    @NamedQuery(name = "ServiceContLoad.findByVTier", query = "SELECT s FROM ServiceContLoad s WHERE s.vTier = :vTier"),
    @NamedQuery(name = "ServiceContLoad.findByIsTranshipment", query = "SELECT s FROM ServiceContLoad s WHERE s.isTranshipment = :isTranshipment"),
    @NamedQuery(name = "ServiceContLoad.findByPosition", query = "SELECT s FROM ServiceContLoad s WHERE s.position = :position"),
    @NamedQuery(name = "ServiceContLoad.findByTransactionDate", query = "SELECT s FROM ServiceContLoad s WHERE s.transactionDate = :transactionDate"),
    @NamedQuery(name = "ServiceContLoad.findByPpkbPositionsAndNotCancelLoading", query = "SELECT s FROM ServiceContLoad s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.position IN :positions AND s.statusCancelLoading = 'FALSE'"),
    @NamedQuery(name = "ServiceContLoad.findByPpkbPositionAndNotCancelLoading", query = "SELECT s FROM ServiceContLoad s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.position = :position AND s.statusCancelLoading <> 'TRUE'"),
    @NamedQuery(name = "ServiceContLoad.findByNoPpkbAndContNo", query = "SELECT s FROM ServiceContLoad s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.contNo = :contNo"),
    @NamedQuery(name = "ServiceContLoad.findByNoPpkbContNoAndStatusCancelLoading", query = "SELECT s FROM ServiceContLoad s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.contNo = :contNo AND s.statusCancelLoading = :statusCancelLoading "),
    @NamedQuery(name = "ServiceContLoad.updateMasterActivityByPpkbPositionAndNotCancelLoading", query = "UPDATE ServiceContLoad s SET s.masterActivity = :masterActivity WHERE s.serviceVessel.noPpkb = :noPpkb AND s.position = :position AND s.statusCancelLoading = 'FALSE'"),
    @NamedQuery(name = "ServiceContLoad.findCancelableContainerByContNoAndNoPpkb", query = "SELECT s FROM ServiceContLoad s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.contNo = :contNo AND s.position='01' AND s.statusCancelLoading <> 'TRUE'")})
@NamedNativeQueries({
/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadsByPpkb", query = 
    "SELECT pcl.cont_no, pcl.cont_size, mct.name, pcl.cont_status, pcl.cont_gross, pcl.seal_no, pcl.block,pcl.y_slot, pcl.y_row, pcl.y_tier,pcl.id,e.id FROM service_cont_load pcl, m_container_type mct,equipment e WHERE pcl.cont_type = mct.cont_type AND pcl.cont_no = e.cont_no AND pcl.no_ppkb = e .no_ppkb AND e.equipment_act_code='LIFT ON' AND pcl.no_ppkb =?"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadsByPpkb", query = 
"SELECT pcl.cont_no, " 
+"  pcl.cont_size, " 
+"  mct.name, " 
+"  pcl.cont_status, " 
+"  pcl.cont_gross, " 
+"  pcl.seal_no, " 
+"  pcl.block, " 
+"  pcl.y_slot, " 
+"  pcl.y_row, " 
+"  pcl.y_tier, " 
+"  pcl.id, " 
+"  e.id " 
+"FROM service_cont_load pcl, " 
+"  m_container_type mct, " 
+"  equipment e " 
+"WHERE pcl.cont_type     = mct.cont_type " 
+"AND pcl.cont_no         = e.cont_no " 
+"AND pcl.no_ppkb         = e .no_ppkb " 
+"AND e.equipment_act_code='LIFT ON' " 
+"AND pcl.no_ppkb         = ?"),    

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadsByPpkbb", query = 
    "SELECT pcl.cont_no, pcl.cont_size, mct.name, pcl.cont_status, pcl.cont_gross, pcl.seal_no, pcl.block,pcl.y_slot, pcl.y_row, pcl.y_tier,pcl.id FROM service_cont_load pcl, m_container_type mct WHERE pcl.cont_type = mct.cont_type AND pcl.position='01' AND pcl.no_ppkb =?"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadsByPpkbb", query = 
"SELECT pcl.cont_no, " 
+"  pcl.cont_size, " 
+"  mct.name, " 
+"  pcl.cont_status, " 
+"  pcl.cont_gross, " 
+"  pcl.seal_no, " 
+"  pcl.block, " 
+"  pcl.y_slot, " 
+"  pcl.y_row, " 
+"  pcl.y_tier, " 
+"  pcl.id " 
+"FROM service_cont_load pcl, " 
+"  m_container_type mct " 
+"WHERE pcl.cont_type = mct.cont_type " 
+"AND pcl.position    = '01' " 
+"AND pcl.no_ppkb     = ?"),

/*    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoads", query = "SELECT r.cont_no, r.cont_size, r.cont_type, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,r.over_size,r.dg_label,r.id from service_cont_load r Where r.position='02' "),
*/    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoads", query = 
"SELECT r.cont_no, " 
+"  r.cont_size, " 
+"  r.cont_type, " 
+"  r.cont_status, " 
+"  r.cont_gross, " 
+"  r.seal_no, " 
+"  r.commodity_code, " 
+"  r.over_size, " 
+"  r.dg_label, " 
+"  r.id " 
+"FROM service_cont_load r " 
+"WHERE r.position='02'" ),

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadsStatusSatu", query = "SELECT r.cont_no, r.cont_size, r.cont_type, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,r.over_size,r.dg_label,r.id,r.bl_no from service_cont_load r Where r.position='01' AND no_ppkb=?"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadsStatusSatu", query = 
"SELECT r.cont_no, " 
+"  r.cont_size, " 
+"  r.cont_type, " 
+"  r.cont_status, " 
+"  r.cont_gross, " 
+"  r.seal_no, " 
+"  r.commodity_code, " 
+"  r.over_size, " 
+"  r.dg_label, " 
+"  r.id, " 
+"  r.bl_no " 
+"FROM service_cont_load r " 
+"WHERE r.position = '01' " 
+"AND no_ppkb      = ?"),

/*    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadss", query = "SELECT r.cont_no, r.cont_size, r.cont_type, r.cont_status, r.cont_gross, r.seal_no,r.commodity_code,r.over_size,r.dg_label,r.id from service_cont_load r Where r.position='02' AND r.no_ppkb =? "),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadss", query = 
"SELECT r.cont_no, " 
+"  r.cont_size, " 
+"  r.cont_type, " 
+"  r.cont_status, " 
+"  r.cont_gross, " 
+"  r.seal_no, " 
+"  r.commodity_code, " 
+"  r.over_size, " 
+"  r.dg_label, " 
+"  r.id " 
+"FROM service_cont_load r " 
+"WHERE r.position='02' " 
+"AND r.no_ppkb   =?" ),

/*    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadByPpkb2", query = "SELECT p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier,p.bl_no,mc.name as comodity FROM service_cont_load as p, m_container_type as c,m_commodity mc WHERE p.cont_type=c.cont_type AND p.position='01' AND p.is_transhipment='false' AND p.no_ppkb = ? AND p.status_cancel_loading=false AND p.commodity_code=mc.commodity_code"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadByPpkb2", query = 
"SELECT p.cont_no, " 
+"  p.cont_size, " 
+"  c.type_in_general AS name, " 
+"  p.cont_status, " 
+"  p.cont_gross, " 
+"  p.block, " 
+"  p.y_slot, " 
+"  p.y_row, " 
+"  p.y_tier, " 
+"  p.id, " 
+"  p.seal_no, " 
+"  p.v_bay, " 
+"  p.v_row, " 
+"  p.v_tier, " 
+"  p.bl_no, " 
+"  mc.name AS comodity " 
+"FROM service_cont_load p, " 
+"  m_container_type c, " 
+"  m_commodity mc " 
+"WHERE p.cont_type          = c.cont_type " 
+"AND p.position             = '01' " 
+"AND p.is_transhipment      = 'FALSE' " 
+"AND p.no_ppkb              = ? " 
+"AND p.status_cancel_loading= 'FALSE' " 
+"AND p.commodity_code       = mc.commodity_code"),    
/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadByPpkb2t", query = "SELECT p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier,p.bl_no,mc.name as comodity FROM service_cont_load as p, m_container_type as c,m_commodity mc WHERE p.cont_type=c.cont_type AND p.position='01' AND p.is_transhipment='true' AND p.no_ppkb = ? AND p.status_cancel_loading=false AND p.commodity_code=mc.commodity_code"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadByPpkb2t", query = 
"SELECT p.cont_no, " 
+"  p.cont_size, " 
+"  c.type_in_general AS name, " 
+"  p.cont_status, " 
+"  p.cont_gross, " 
+"  p.block, " 
+"  p.y_slot, " 
+"  p.y_row, " 
+"  p.y_tier, " 
+"  p.id, " 
+"  p.seal_no, " 
+"  p.v_bay, " 
+"  p.v_row, " 
+"  p.v_tier, " 
+"  p.bl_no, " 
+"  mc.name AS comodity " 
+"FROM service_cont_load p, " 
+"  m_container_type c, " 
+"  m_commodity mc " 
+"WHERE p.cont_type           = c.cont_type " 
+"AND p.position              = '01' " 
+"AND p.is_transhipment       = 'TRUE' " 
+"AND p.no_ppkb               = ? " 
+"AND p.status_cancel_loading = 'FALSE' " 
+"AND p.commodity_code        = mc.commodity_code"),

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadConfirm", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.y_slot, scd.y_row, scd.y_tier, scd.id,scd.block,scd.bl_no "
                                                                                        + "FROM service_cont_load AS scd "
                                                                                                + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                        + "WHERE scd.position='02' AND scd.no_ppkb = ? AND scd.status_cancel_loading=false AND scd.is_transhipment <> TRUE "
                                                                                        + "ORDER BY scd.id DESC;"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadConfirm", query = 
"SELECT scd.cont_no, " 
+"  scd.cont_size, " 
+"  mct.type_in_general AS name, " 
+"  scd.cont_status, " 
+"  scd.cont_gross, " 
+"  scd.seal_no, " 
+"  scd.y_slot, " 
+"  scd.y_row, " 
+"  scd.y_tier, " 
+"  scd.id, " 
+"  scd.block, " 
+"  scd.bl_no " 
+"FROM service_cont_load scd " 
+"JOIN m_container_type mct " 
+"ON (scd.cont_type             = mct.cont_type) " 
+"WHERE scd.position            ='02' " 
+"AND scd.no_ppkb               = ? " 
+"AND scd.status_cancel_loading = 'FALSE' " 
+"AND scd.is_transhipment      <> 'TRUE' " 
+"ORDER BY scd.id DESC"),

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadConfirm2", query = "SELECT scd.cont_no, scd.cont_size, mct.name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.y_slot, scd.y_row, scd.y_tier, scd.id,scd.block,scd.bl_no FROM service_cont_load AS scd, m_container_type AS mct WHERE scd.cont_type = mct.cont_type AND scd.position='02' AND scd.is_transhipment='true' AND scd.no_ppkb = ? AND scd.status_cancel_loading=false ORDER BY scd.id DESC"),
*/    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadConfirm2", query = 
"SELECT scd.cont_no, " 
+"  scd.cont_size, " 
+"  mct.name, " 
+"  scd.cont_status, " 
+"  scd.cont_gross, " 
+"  scd.seal_no, " 
+"  scd.y_slot, " 
+"  scd.y_row, " 
+"  scd.y_tier, " 
+"  scd.id, " 
+"  scd.block, " 
+"  scd.bl_no " 
+"FROM service_cont_load scd, " 
+"  m_container_type mct " 
+"WHERE scd.cont_type          = mct.cont_type " 
+"AND scd.position             ='02' " 
+"AND scd.is_transhipment      ='TRUE' " 
+"AND scd.no_ppkb              = ? " 
+"AND scd.status_cancel_loading='FALSE' " 
+"ORDER BY scd.id DESC"),

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadConfirmTranshipment", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.y_slot, scd.y_row, scd.y_tier, scd.id,scd.block,scd.bl_no FROM service_cont_load AS scd, m_container_type AS mct WHERE scd.cont_type = mct.cont_type AND scd.position='02' AND scd.is_transhipment='true' AND scd.no_ppkb = ? ORDER BY scd.id DESC"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadConfirmTranshipment", query = 
"SELECT scd.cont_no, " 
+"  scd.cont_size, " 
+"  mct.type_in_general AS name, " 
+"  scd.cont_status, " 
+"  scd.cont_gross, " 
+"  scd.seal_no, " 
+"  scd.y_slot, " 
+"  scd.y_row, " 
+"  scd.y_tier, " 
+"  scd.id, " 
+"  scd.block, " 
+"  scd.bl_no " 
+"FROM service_cont_load scd, " 
+"  m_container_type mct " 
+"WHERE scd.cont_type    = mct.cont_type " 
+"AND scd.position       ='02' " 
+"AND scd.is_transhipment='TRUE' " 
+"AND scd.no_ppkb        = ? " 
+"ORDER BY scd.id DESC"),

/*    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadSelect", query = "SELECT scd.cont_no, scd.v_bay, scd.v_row, scd.v_tier, scd.id FROM service_cont_load AS scd WHERE scd.no_ppkb = ?"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadSelect", query =   
"SELECT scd.cont_no, " 
+"  scd.v_bay, " 
+"  scd.v_row, " 
+"  scd.v_tier, " 
+"  scd.id " 
+"FROM service_cont_load scd " 
+"WHERE scd.no_ppkb = ?"),

/*    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findByContNo", query = "SELECT id, cont_no, no_ppkb, y_slot, y_row, y_tier, v_bay, v_row, v_tier FROM service_cont_load WHERE cont_no = ? AND position = ? AND status_cancel_loading = FALSE"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findByContNo", query = 
"SELECT id, " 
+"  cont_no, " 
+"  no_ppkb, " 
+"  y_slot, " 
+"  y_row, " 
+"  y_tier, " 
+"  v_bay, " 
+"  v_row, " 
+"  v_tier " 
+"FROM service_cont_load " 
+"WHERE cont_no             = ? " 
+"AND position              = ? " 
+"AND status_cancel_loading = 'FALSE'"),

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findByContNoPpkb", query = "SELECT id, cont_no, no_ppkb, y_slot, y_row, y_tier, v_bay, v_row, v_tier FROM service_cont_load WHERE no_ppkb = ? AND cont_no = ? AND position = ?"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findByContNoPpkb", query = 
"SELECT id, " 
+"  cont_no, " 
+"  no_ppkb, " 
+"  y_slot, " 
+"  y_row, " 
+"  y_tier, " 
+"  v_bay, " 
+"  v_row, " 
+"  v_tier " 
+"FROM service_cont_load " 
+"WHERE no_ppkb = ? " 
+"AND cont_no   = ? " 
+"AND position  = ?"),

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findLoadMonitoringsByPPKB", query = "SELECT p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, change(p.over_size), change(p.dg), change(p.dg_label), e1.equip_code, e1.start_time, e1.end_time, e2.equip_code, e2.start_time, e2.end_time, e3.equip_code, e3.start_time, e3.end_time FROM ((((planning_cont_load p LEFT JOIN m_container_type c ON p.cont_type=c.cont_type) LEFT JOIN equipment e1 ON p.cont_no = e1.cont_no AND p.no_ppkb = e1.no_ppkb AND e1.operation IN ('LOAD','TRANSHIPMENT') AND e1.equipment_act_code = 'LIFTON') LEFT JOIN equipment e2 ON p.cont_no = e2.cont_no AND p.no_ppkb = e2.no_ppkb AND e2.operation IN ('LOAD','TRANSHIPMENT') AND e2.equipment_act_code = 'H/T') LEFT JOIN equipment e3 ON p.cont_no = e3.cont_no AND p.no_ppkb = e3.no_ppkb AND e3.operation IN ('LOAD','TRANSHIPMENT') AND e3.equipment_act_code = 'STEVEDORING') WHERE p.no_ppkb = ? AND p.position = ? "),
*/    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findLoadMonitoringsByPPKB", query = 
"SELECT p.cont_no, " 
+"  p.cont_size, " 
+"  c.type_in_general AS name, " 
+"  p.cont_status, " 
+"  p.cont_gross, " 
+"  change(p.over_size), " 
+"  change(p.dg), " 
+"  change(p.dg_label), " 
+"  e1.equip_code, " 
+"  e1.start_time, " 
+"  e1.end_time, " 
+"  e2.equip_code, " 
+"  e2.start_time, " 
+"  e2.end_time, " 
+"  e3.equip_code, " 
+"  e3.start_time, " 
+"  e3.end_time " 
+"FROM ((((planning_cont_load p " 
+"LEFT JOIN m_container_type c " 
+"ON p.cont_type=c.cont_type) " 
+"LEFT JOIN equipment e1 " 
+"ON p.cont_no              = e1.cont_no " 
+"AND p.no_ppkb             = e1.no_ppkb " 
+"AND e1.operation         IN ('LOAD','TRANSHIPMENT') " 
+"AND e1.equipment_act_code = 'LIFTON') " 
+"LEFT JOIN equipment e2 " 
+"ON p.cont_no              = e2.cont_no " 
+"AND p.no_ppkb             = e2.no_ppkb " 
+"AND e2.operation         IN ('LOAD','TRANSHIPMENT') " 
+"AND e2.equipment_act_code = 'H/T') " 
+"LEFT JOIN equipment e3 " 
+"ON p.cont_no              = e3.cont_no " 
+"AND p.no_ppkb             = e3.no_ppkb " 
+"AND e3.operation         IN ('LOAD','TRANSHIPMENT') " 
+"AND e3.equipment_act_code = 'STEVEDORING') " 
+"WHERE p.no_ppkb           = ? " 
+"AND p.position            = ?" ),

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findUcLoadMonitoringsByPPKB", query = "SELECT uc.bl_no, mc.name, uc.unit, uc.weight, uc.cubication AS vol, uc.truck_loosing, e1.equip_code AS tt_code, e1.start_time AS start_tt, e1.end_time AS end_tt, e2.equip_code AS ht_code, e2.start_time AS start_ht, e2.end_time AS end_ht, e3.equip_code AS cc_code, e3.start_time AS start_stv, e3.end_time AS end_stv "
                                                                                        + "FROM uncontainerized_service uc JOIN m_commodity  mc ON uc.commodity = mc.commodity_code "
                                                                                        + "LEFT JOIN equipment e1 ON e1.no_ppkb::text = uc.no_ppkb::text AND e1.bl_no::text = uc.bl_no::text AND e1.equipment_act_code::text = 'LIFTON' AND (e1.operation::text = 'LOADUC' or e1.operation::text = 'TRANSDLOADUC') "
                                                                                        + "LEFT JOIN equipment e2 ON e2.no_ppkb::text = uc.no_ppkb::text AND e2.bl_no::text = uc.bl_no::text AND e2.equipment_act_code::text = 'H/T' AND (e2.operation::text = 'LOADUC' or e2.operation::text = 'TRANSDLOADUC') "
                                                                                        + "LEFT JOIN equipment e3 ON e3.no_ppkb::text = uc.no_ppkb::text AND e3.bl_no::text = uc.bl_no::text AND e3.equipment_act_code::text = 'STEVEDORING' AND (e3.operation::text = 'LOADUC' or e3.operation::text = 'TRANSDLOADUC') "
                                                                                        + "WHERE uc.operation = 'LOAD' AND uc.is_shifting = FALSE AND uc.status = 'VESSEL' AND uc.no_ppkb = ? "),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findUcLoadMonitoringsByPPKB", query = 
"SELECT uc.bl_no, " 
+"  mc.name, " 
+"  uc.unit, " 
+"  uc.weight, " 
+"  uc.cubication AS vol, " 
+"  uc.truck_loosing, " 
+"  e1.equip_code AS tt_code, " 
+"  e1.start_time AS start_tt, " 
+"  e1.end_time   AS end_tt, " 
+"  e2.equip_code AS ht_code, " 
+"  e2.start_time AS start_ht, " 
+"  e2.end_time   AS end_ht, " 
+"  e3.equip_code AS cc_code, " 
+"  e3.start_time AS start_stv, " 
+"  e3.end_time   AS end_stv " 
+"FROM uncontainerized_service uc " 
+"JOIN m_commodity mc " 
+"ON uc.commodity = mc.commodity_code " 
+"LEFT JOIN equipment e1 " 
+"ON e1.no_ppkb             = uc.no_ppkb " 
+"AND e1.bl_no              = uc.bl_no " 
+"AND e1.equipment_act_code = 'LIFTON' " 
+"AND (e1.operation         = 'LOADUC' " 
+"OR e1.operation           = 'TRANSDLOADUC') " 
+"LEFT JOIN equipment e2 " 
+"ON e2.no_ppkb             = uc.no_ppkb " 
+"AND e2.bl_no              = uc.bl_no " 
+"AND e2.equipment_act_code = 'H/T' " 
+"AND (e2.operation         = 'LOADUC' " 
+"OR e2.operation           = 'TRANSDLOADUC') " 
+"LEFT JOIN equipment e3 " 
+"ON e3.no_ppkb             = uc.no_ppkb " 
+"AND e3.bl_no              = uc.bl_no " 
+"AND e3.equipment_act_code = 'STEVEDORING' " 
+"AND (e3.operation         = 'LOADUC' " 
+"OR e3.operation           = 'TRANSDLOADUC') " 
+"WHERE uc.operation        = 'LOAD' " 
+"AND uc.is_shifting        = 'FALSE' " 
+"AND uc.status             = 'VESSEL' " 
+"AND uc.no_ppkb            = ?"),

/*                                                                                        
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadContNo", query = "SELECT scd.cont_no, scd.id FROM service_cont_load AS scd WHERE scd.no_ppkb = ? AND scd.cont_no = ? AND scd.position='01' AND scd.cont_no NOT IN (select cont_no from cancel_loading_service where no_ppkb=scd.no_ppkb)"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadContNo", query = 
"SELECT scd.cont_no, " 
+"  scd.id " 
+"FROM service_cont_load scd " 
+"WHERE scd.no_ppkb    = ? " 
+"AND scd.cont_no      = ? " 
+"AND scd.position     ='01' " 
+"AND scd.cont_no NOT IN " 
+"  (SELECT cont_no FROM cancel_loading_service WHERE no_ppkb=scd.no_ppkb " 
+"  )"),

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findShiftableContainer", query = "SELECT scl.id, scl.cont_no, scl.bl_no, scl.cont_gross, scl.cont_size, scl.cont_status, scl.dangerous, scl.dg_label, scl.cont_type, scl.commodity_code, scl.mlo, scl.seal_no, scl.is_export_import, scl.v_bay, scl.v_row, scl.v_tier, scl.over_size, COALESCE(c.new_iso_code, c.iso_code) iso_code "
                                                                                    + "FROM service_cont_load as scl "
                                                                                            + "JOIN m_container_type as c on (scl.cont_type = c.cont_type) "
                                                                                            + "LEFT JOIN service_shifting ss ON (ss.no_ppkb = scl.no_ppkb AND ss.cont_no = scl.cont_no) "
                                                                                    + "WHERE scl.no_ppkb = ? AND scl.cont_no = ? AND ss.cont_no IS NULL;"),
*/    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findShiftableContainer", query = 
"SELECT scl.id, " 
+"  scl.cont_no, " 
+"  scl.bl_no, " 
+"  scl.cont_gross, " 
+"  scl.cont_size, " 
+"  scl.cont_status, " 
+"  scl.dangerous, " 
+"  scl.dg_label, " 
+"  scl.cont_type, " 
+"  scl.commodity_code, " 
+"  scl.mlo, " 
+"  scl.seal_no, " 
+"  scl.is_export_import, " 
+"  scl.v_bay, " 
+"  scl.v_row, " 
+"  scl.v_tier, " 
+"  scl.over_size, " 
+"  COALESCE(c.new_iso_code, c.iso_code) iso_code " 
+"FROM service_cont_load scl " 
+"JOIN m_container_type c " 
+"ON (scl.cont_type = c.cont_type) " 
+"LEFT JOIN service_shifting ss " 
+"ON (ss.no_ppkb    = scl.no_ppkb " 
+"AND ss.cont_no    = scl.cont_no) " 
+"WHERE scl.no_ppkb = ? " 
+"AND scl.cont_no   = ? " 
+"AND ss.cont_no   IS NULL"),

/*                                                                                
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadContByEndService", query = "SELECT s.id, e.id,r.id "
                                                                                                + "FROM service_cont_load s "
                                                                                                        + "JOIN equipment e ON (s.cont_no=e.cont_no AND s.no_ppkb=e.no_ppkb) "
                                                                                                        + "LEFT JOIN service_cont_transhipment r ON (s.cont_no=r.cont_no AND s.no_ppkb=r.new_ppkb) "
                                                                                                + "WHERE e.operation='TRANSHIPMENT' AND e.equipment_act_code='LIFTON' AND s.no_ppkb=? "),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadContByEndService", query = 
"SELECT s.id, " 
+"  e.id, " 
+"  r.id " 
+"FROM service_cont_load s " 
+"JOIN equipment e " 
+"ON (s.cont_no=e.cont_no " 
+"AND s.no_ppkb=e.no_ppkb) " 
+"LEFT JOIN service_cont_transhipment r " 
+"ON (s.cont_no           =r.cont_no " 
+"AND s.no_ppkb           =r.new_ppkb) " 
+"WHERE e.operation       ='TRANSHIPMENT' " 
+"AND e.equipment_act_code='LIFTON' " 
+"AND s.no_ppkb           =?"),

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadContTranshipmentRecap", query = "SELECT scd.id, scd.cont_no, m.type_in_general as name, scd.cont_size, scd.cont_status, scd.cont_gross, CASE WHEN scd.over_size=true THEN 'Yes' WHEN scd.over_size=false THEN 'No' END as over_size, rp.charge_m1,rp.charge_m2, rp.jasa_dermaga, rp.total_charge, curr.symbol, curr.language, curr.country FROM service_cont_load scd, perhitungan_penumpukan_transhipment rp,m_container_type m, m_currency curr WHERE scd.cont_no = rp.cont_no AND scd.cont_type=m.cont_type AND scd.no_ppkb = rp.no_ppkb AND rp.curr_code = curr.curr_code AND scd.no_ppkb = ?"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadContTranshipmentRecap", query = 
"SELECT scd.id, " 
+"  scd.cont_no, " 
+"  m.type_in_general AS name, " 
+"  scd.cont_size, " 
+"  scd.cont_status, " 
+"  scd.cont_gross, " 
+"  CASE " 
+"    WHEN scd.over_size= 'TRUE' " 
+"    THEN 'Yes' " 
+"    WHEN scd.over_size= 'FALSE' " 
+"    THEN 'No' " 
+"  END AS over_size, " 
+"  rp.charge_m1, " 
+"  rp.charge_m2, " 
+"  rp.jasa_dermaga, " 
+"  rp.total_charge, " 
+"  curr.symbol, " 
+"  curr.language, " 
+"  curr.country " 
+"FROM service_cont_load scd, " 
+"  perhitungan_penumpukan_transhi rp, " 
+"  m_container_type m, " 
+"  m_currency curr " 
+"WHERE scd.cont_no = rp.cont_no " 
+"AND scd.cont_type =m.cont_type " 
+"AND scd.no_ppkb   = rp.no_ppkb " 
+"AND rp.curr_code  = curr.curr_code " 
+"AND scd.no_ppkb   = ?"),

/*    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadByRekap", query = "SELECT ss.id, ss.cont_no, ss.cont_size, ct.type_in_general as name, ss.cont_status, es.equip_code, es.start_time, es.end_time, e.equip_code, e.start_time, e.end_time, el.equip_code, el.start_time, el.end_time, ss.seal_no FROM service_cont_load ss, service_cont_load sst LEFT JOIN equipment e ON e.no_ppkb = sst.no_ppkb AND e.cont_no = sst.cont_no AND e.operation = 'TRANSHIPMENT' AND e.equipment_act_code = 'H/T', service_cont_load sstt LEFT JOIN equipment el ON el.no_ppkb = sstt.no_ppkb AND el.cont_no = sstt.cont_no AND el.operation = 'TRANSHIPMENT' AND el.equipment_act_code = 'LIFTON', service_cont_load ssttt LEFT JOIN equipment es ON es.no_ppkb = ssttt.no_ppkb AND es.cont_no = ssttt.cont_no AND es.operation = 'TRANSHIPMENT' AND es.equipment_act_code = 'STEVEDORING', m_container_type ct WHERE ss.cont_type = ct.cont_type AND ss.id = sst.id AND ss.id = sstt.id AND ss.id = ssttt.id AND ss.is_transhipment='true' AND ss.no_ppkb = ?"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadByRekap", query = 
"SELECT ss.id, " 
+"  ss.cont_no, " 
+"  ss.cont_size, " 
+"  ct.type_in_general AS name, " 
+"  ss.cont_status, " 
+"  es.equip_code, " 
+"  es.start_time, " 
+"  es.end_time, " 
+"  e.equip_code, " 
+"  e.start_time, " 
+"  e.end_time, " 
+"  el.equip_code, " 
+"  el.start_time, " 
+"  el.end_time, " 
+"  ss.seal_no " 
+"FROM service_cont_load ss, " 
+"  service_cont_load sst " 
+"LEFT JOIN equipment e " 
+"ON e.no_ppkb             = sst.no_ppkb " 
+"AND e.cont_no            = sst.cont_no " 
+"AND e.operation          = 'TRANSHIPMENT' " 
+"AND e.equipment_act_code = 'H/T', " 
+"  service_cont_load sstt " 
+"LEFT JOIN equipment el " 
+"ON el.no_ppkb             = sstt.no_ppkb " 
+"AND el.cont_no            = sstt.cont_no " 
+"AND el.operation          = 'TRANSHIPMENT' " 
+"AND el.equipment_act_code = 'LIFTON', " 
+"  service_cont_load ssttt " 
+"LEFT JOIN equipment es " 
+"ON es.no_ppkb             = ssttt.no_ppkb " 
+"AND es.cont_no            = ssttt.cont_no " 
+"AND es.operation          = 'TRANSHIPMENT' " 
+"AND es.equipment_act_code = 'STEVEDORING', " 
+"  m_container_type ct " 
+"WHERE ss.cont_type    = ct.cont_type " 
+"AND ss.id             = sst.id " 
+"AND ss.id             = sstt.id " 
+"AND ss.id             = ssttt.id " 
+"AND ss.is_transhipment='TRUE' " 
+"AND ss.no_ppkb        = ?"),

/*    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadByAutoComplete", query = "SELECT scd.cont_no FROM service_cont_load AS scd WHERE scd.no_ppkb = ? AND scd.cont_no LIKE ('%'|| ? ||'%') AND scd.cont_no NOT IN (select cont_no from cancel_loading_service where no_ppkb=scd.no_ppkb)"),
*/    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadByAutoComplete", query = 
"SELECT scd.cont_no " 
+"FROM service_cont_load scd " 
+"WHERE scd.no_ppkb = ? " 
+"AND scd.cont_no LIKE ('%' " 
+"  || ? " 
+"  ||'%') " 
+"AND scd.cont_no NOT IN " 
+"  (SELECT cont_no FROM cancel_loading_service WHERE no_ppkb=scd.no_ppkb " 
+"  )"),

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadByDownloadExcel", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, p.block, p.y_slot, p.y_row, p.y_tier, p.id, p.seal_no, p.v_bay, p.v_row, p.v_tier,p.bl_no,mc.name as comodity FROM service_cont_load as p, m_container_type as c,m_commodity mc WHERE p.cont_type=c.cont_type AND p.position='01' AND p.status_cancel_loading=false AND p.commodity_code=mc.commodity_code and p.no_ppkb=?"),
*/    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadByDownloadExcel", query = 
"SELECT p.cont_no, " 
+"  p.cont_size, " 
+"  c.name, " 
+"  p.cont_status, " 
+"  p.cont_gross, " 
+"  p.block, " 
+"  p.y_slot, " 
+"  p.y_row, " 
+"  p.y_tier, " 
+"  p.id, " 
+"  p.seal_no, " 
+"  p.v_bay, " 
+"  p.v_row, " 
+"  p.v_tier, " 
+"  p.bl_no, " 
+"  mc.name comodity " 
+"FROM service_cont_load p, " 
+"  m_container_type c, " 
+"  m_commodity mc " 
+"WHERE p.cont_type          =c.cont_type " 
+"AND p.position             ='01' " 
+"AND p.status_cancel_loading= 'FALSE' " 
+"AND p.commodity_code       =mc.commodity_code " 
+"AND p.no_ppkb              =?"),
    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findServiceContLoadsStuffingService", query = 
"SELECT p.cont_no, " 
+"  p.cont_size, " 
+"  c.name, " 
+"  p.cont_status, " 
+"  p.cont_gross, " 
+"  p.block, " 
+"  p.y_slot, " 
+"  p.y_row, " 
+"  p.y_tier, " 
+"  p.id, " 
+"  p.seal_no, " 
+"  p.v_bay, " 
+"  p.v_row, " 
+"  p.v_tier, " 
+"  p.bl_no " 
+"FROM service_cont_load p " 
+"JOIN m_container_type c " 
+"ON (p.cont_type=c.cont_type) " 
+"LEFT JOIN " 
+"  (SELECT ss.cont_no, " 
+"    r.no_ppkb " 
+"  FROM stuffing_service ss " 
+"  JOIN registration r " 
+"  ON (r.no_reg      =ss.no_reg) " 
+"  ) ss ON (p.cont_no=ss.cont_no " 
+"AND p.no_ppkb       =ss.no_ppkb) " 
+"WHERE ss.cont_no   IS NULL " 
+"AND p.no_ppkb       = ?"),

/*
    @NamedNativeQuery(name = "ServiceContLoad.Native.findByPpkbAndContNo", query = "SELECT r.id, r.is_seling FROM service_cont_load r WHERE r.no_ppkb = ? AND r.cont_no = ?"),
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findByPpkbAndContNo", query = 
"SELECT r.id, " 
+"  r.is_seling " 
+"FROM service_cont_load r " 
+"WHERE r.no_ppkb = ? " 
+"AND r.cont_no   = ?"),
/*    
    @NamedNativeQuery(name = "ServiceContLoad.Native.findContainersThatHaveBeenLoaded", query = "SELECT scl.cont_no, scl.bl_no, scl.mlo, pcl.disch_port, scl.port_of_delivery, "
                                                                                                    + "mct.new_iso_code, scl.cont_size, scl.cont_status, scl.cont_gross, scl.seal_no, "
                                                                                                    + "scl.over_size, scl.dangerous, scl.dg_label, scl.block, scl.y_slot, scl.y_row, scl.y_tier, "
                                                                                                    + "scl.v_bay, scl.v_row, scl.v_tier, scl.commodity_code, scl.no_ppkb, "
                                                                                                    + "scl.crane, scl.is_transhipment, scl.transaction_date "
                                                                                            + "FROM service_cont_load scl "
                                                                                                    + "JOIN m_container_type mct ON (scl.cont_type=mct.cont_type) "
                                                                                                    + "JOIN planning_cont_load pcl ON (pcl.cont_no=scl.cont_no AND pcl.no_ppkb=scl.no_ppkb) "
                                                                                            + "WHERE scl.status_cancel_loading IS NOT TRUE AND scl.position = '01' AND scl.no_ppkb = ?")
*/
    @NamedNativeQuery(name = "ServiceContLoad.Native.findContainersThatHaveBeenLoaded", query = 
"SELECT scl.cont_no, " 
+"  scl.bl_no, " 
+"  scl.mlo, " 
+"  pcl.disch_port, " 
+"  scl.port_of_delivery, " 
+"  mct.new_iso_code, " 
+"  scl.cont_size, " 
+"  scl.cont_status, " 
+"  scl.cont_gross, " 
+"  scl.seal_no, " 
+"  scl.over_size, " 
+"  scl.dangerous, " 
+"  scl.dg_label, " 
+"  scl.block, " 
+"  scl.y_slot, " 
+"  scl.y_row, " 
+"  scl.y_tier, " 
+"  scl.v_bay, " 
+"  scl.v_row, " 
+"  scl.v_tier, " 
+"  scl.commodity_code, " 
+"  scl.no_ppkb, " 
+"  scl.crane, " 
+"  scl.is_transhipment, " 
+"  scl.transaction_date " 
+"FROM service_cont_load scl " 
+"JOIN m_container_type mct " 
+"ON (scl.cont_type=mct.cont_type) " 
+"JOIN planning_cont_load pcl " 
+"ON (pcl.cont_no                  =scl.cont_no " 
+"AND pcl.no_ppkb                  =scl.no_ppkb) " 
+"WHERE scl.status_cancel_loading <> 'TRUE' " 
+"AND scl.position                 = '01' " 
+"AND scl.no_ppkb                  = ?")

})
public class ServiceContLoad implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private String twentyOneFeet = "FALSE"; 
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "crane", length = 2)
    private String crane;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
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
    private String overSize = "FALSE";
    @Column(name = "dangerous")
    private String dangerous = "FALSE";
    @Column(name = "dg_label")
    private String dgLabel = "FALSE";
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
    private String isTranshipment;
    @Column(name = "position", length = 2)
    private String position;
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
    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Column(name = "temperature", precision = 5, scale = 2)
    private BigDecimal temperature;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private ServiceVessel serviceVessel;
    @JoinColumn(name = "block", referencedColumnName = "block")
    @ManyToOne
    private MasterYard masterYard;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;
    @Column(name = "position_bay")
    private String positionBay;
    @Column(name = "load_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date loadDate;
    @Column(name = "status_cancel_loading")
    private String statusCancelLoading = "FALSE";
    @Column(name = "is_seling")
    private String isSeling = "FALSE";
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @JoinColumn(name = "port_of_delivery", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort portOfDelivery;
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private String isExportImport = "FALSE";
    @Basic(optional = false)
    @Column(name = "is_change_vessel", nullable = false)
    private String isChangeVessel = "FALSE";
    @Column(name = "equipment_group", length = 25)
    private String equipmentGroup;

    public ServiceContLoad() {
    }

    public ServiceContLoad(Integer id) {
        this.id = id;
    }

    public ServiceContLoad(Integer id, String contNo, short contSize, String contStatus, Date transactionDate, MasterCustomer mlo) {
        this.id = id;
        this.contNo = contNo;
        this.mlo = mlo;
        this.contSize = contSize;
        this.contStatus = contStatus;
        this.transactionDate = transactionDate;
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

    public String getOverSize() {
        return overSize;
    }

    public void setOverSize(String overSize) {
        this.overSize = overSize;
    }

    public String getDangerous() {
        return dangerous;
    }

    public void setDangerous(String dangerous) {
        this.dangerous = dangerous;
    }

    public String getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(String dgLabel) {
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

    public String getIsTranshipment() {
        return isTranshipment;
    }

    public void setIsTranshipment(String isTranshipment) {
        this.isTranshipment = isTranshipment;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
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

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
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

    public String getStatusCancelLoading() {
        return statusCancelLoading;
    }

    public void setStatusCancelLoading(String statusCancelLoading) {
        this.statusCancelLoading = statusCancelLoading;
    }

    public String getIsSeling() {
        return isSeling;
    }

    public void setIsSeling(String isSeling) {
        this.isSeling = isSeling;
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * @return the temperature
     */
    public BigDecimal getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public String getIsExportImport() {
        return isExportImport;
    }

    public void setIsExportImport(String isExportImport) {
        this.isExportImport = isExportImport;
    }

    public String getIsChangeVessel() {
        return isChangeVessel;
    }

    public void setIsChangeVessel(String isChangeVessel) {
        this.isChangeVessel = isChangeVessel;
    }

    public String getEquipmentGroup() {
        return equipmentGroup;
    }

    public void setEquipmentGroup(String equipmentGroup) {
        this.equipmentGroup = equipmentGroup;
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
        if (!(object instanceof ServiceContLoad)) {
            return false;
        }
        ServiceContLoad other = (ServiceContLoad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceContLoad[id=" + id + "]";
    }

    public String getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(String twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
