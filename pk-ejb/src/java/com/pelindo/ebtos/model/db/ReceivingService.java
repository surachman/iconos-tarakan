/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
@Table(name = "receiving_service")
@NamedQueries({
    @NamedQuery(name = "ReceivingService.findAll", query = "SELECT r FROM ReceivingService r"),
    //Penambahan pencarian data receiving by ade chelsea
    @NamedQuery(name = "ReceivingService.findDataReceiving", query = "SELECT r FROM ReceivingService r where r.contNo = :contNo and r.statusCancelJobslip = 'FALSE'"),
    @NamedQuery(name = "ReceivingService.findByJobSlip", query = "SELECT r FROM ReceivingService r WHERE r.jobSlip = :jobSlip"),
    @NamedQuery(name = "ReceivingService.findByNoReg", query = "SELECT r FROM ReceivingService r WHERE r.registration.noReg = :noReg"),
    @NamedQuery(name = "ReceivingService.findByNoInvoice", query = "SELECT r FROM ReceivingService r WHERE r.noInvoice = :noInvoice"),
    @NamedQuery(name = "ReceivingService.findByContNo", query = "SELECT r FROM ReceivingService r WHERE r.contNo = :contNo"),
    @NamedQuery(name = "ReceivingService.updatePlanningVessel", query = "UPDATE ReceivingService r SET r.planningVessel = :newValue WHERE r.planningVessel = :oldValue"),
    @NamedQuery(name = "ReceivingService.updatePlanningVesselByJobSlips", query = "UPDATE ReceivingService r SET r.planningVessel = :newValue, r.masterPort = :masterPort, r.portOfDelivery= :portOfDelivery WHERE r.planningVessel = :oldValue AND r.jobSlip IN :jobSlips"),
    @NamedQuery(name = "ReceivingService.findByNoPpkbAndContNo", query = "SELECT r FROM ReceivingService r WHERE r.contNo = :contNo AND r.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "ReceivingService.findByNoPpkbAndContNoNotCancelLoading", query = "SELECT r FROM ReceivingService r WHERE r.contNo = :contNo AND r.planningVessel.noPpkb = :noPpkb AND r.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "ReceivingService.findByNoPpkbAndContNoCancelLoading", query = "SELECT r FROM ReceivingService r WHERE r.contNo = :contNo AND r.planningVessel.noPpkb = :noPpkb AND r.statusCancelLoading = TRUE"),
    @NamedQuery(name = "ReceivingService.deleteByNoPpkbAndContNoNotCancelLoading", query = "DELETE FROM ReceivingService r WHERE r.contNo = :contNo AND r.planningVessel.noPpkb = :noPpkb AND r.statusCancelLoading <> TRUE"),
    @NamedQuery(name = "ReceivingService.findByContSize", query = "SELECT r FROM ReceivingService r WHERE r.contSize = :contSize"),
    @NamedQuery(name = "ReceivingService.findByContStatus", query = "SELECT r FROM ReceivingService r WHERE r.contStatus = :contStatus"),
    @NamedQuery(name = "ReceivingService.findByContGross", query = "SELECT r FROM ReceivingService r WHERE r.contGross = :contGross"),
    @NamedQuery(name = "ReceivingService.findBySealNo", query = "SELECT r FROM ReceivingService r WHERE r.sealNo = :sealNo"),
    @NamedQuery(name = "ReceivingService.findByOverSize", query = "SELECT r FROM ReceivingService r WHERE r.overSize = :overSize"),
    @NamedQuery(name = "ReceivingService.findByDg", query = "SELECT r FROM ReceivingService r WHERE r.dg = :dg"),
    @NamedQuery(name = "ReceivingService.findByDgLabel", query = "SELECT r FROM ReceivingService r WHERE r.dgLabel = :dgLabel"),
    @NamedQuery(name = "ReceivingService.findByValidDate", query = "SELECT r FROM ReceivingService r WHERE r.validDate = :validDate"),
    @NamedQuery(name = "ReceivingService.findCancelableContainerByContNoAndNoPpkb", query = "SELECT r FROM ReceivingService r WHERE r.planningVessel.noPpkb = :noPpkb AND r.contNo = :contNo AND r.statusCancelLoading <> TRUE AND r.statusCancelJobslip <> TRUE"),
    @NamedQuery(name = "ReceivingService.test", query = "SELECT COUNT(rs) "
                                                        + "FROM ReceivingService rs, ServiceGateReceiving sgr "
                                                        + "WHERE rs.contNo=sgr.contNo AND rs.planningVessel.noPpkb=sgr.noPpkb AND rs.registration.noReg = :noReg")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ReceivingService.Native.test", query = "SELECT COUNT(*) "
                                                                                + "FROM receiving_service rs JOIN service_gate_receiving sgr ON (rs.cont_no=sgr.cont_no AND rs.no_ppkb=sgr.no_ppkb) "
                                                                                + "WHERE rs.no_reg = ?"),
/*
    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceByNoReg", query = "SELECT r.job_slip, r.cont_no, r.cont_size, mt.type_in_general as name, r.cont_status, r.cont_gross, CASE WHEN r.over_size=true THEN 'Yes' WHEN r.over_size=false THEN 'No' END as over_size, p.close_rec FROM receiving_service r, m_container_type mt, planning_vessel p WHERE r.cont_type=mt.cont_type AND r.no_reg=? AND p.no_ppkb=r.no_ppkb ORDER BY substring(r.job_slip,7,4) DESC"),
*/
    
    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceByNoReg", query = 
"SELECT r.job_slip, " 
+"  r.cont_no, " 
+"  r.cont_size, " 
+"  mt.type_in_general AS name, " 
+"  r.cont_status, " 
+"  r.cont_gross, " 
+"  DECODE(r.over_size, 'TRUE', 'Yes', 'No') AS over_size, " 
+"  p.close_rec " 
+"FROM receiving_service r, " 
+"  m_container_type mt, " 
+"  planning_vessel p " 
+"WHERE r.cont_type=mt.cont_type " 
+"AND r.no_reg     =? " 
+"AND p.no_ppkb    =r.no_ppkb " 
+"ORDER BY SUBSTR(r.job_slip,7,4) DESC"),    

/*
    @NamedNativeQuery(name = "ReceivingService.Native.findContainersWithPosition", query = "SELECT rs.cont_no, CASE WHEN sr.cont_type IS NOT NULL THEN sr.cont_type ELSE rs.cont_type END, rs.commodity_code, rs.cont_status, "
                                                                                                    + "rs.cont_gross, rs.seal_no, rs.over_size, rs.dg, rs.dg_label, rs.bl_no, rs.mlo, rs.is_export, "
                                                                                                    + "rs.disch_port_code, CASE WHEN sgr.no_ppkb IS NULL THEN 'OUTSIDE' ELSE 'CY' END "
                                                                                            + "FROM receiving_service rs "
                                                                                                    + "LEFT JOIN service_gate_receiving sgr ON (rs.job_slip = sgr.job_slip) "
                                                                                                    + "LEFT JOIN service_receiving sr ON (rs.cont_no = sr.cont_no AND rs.no_ppkb = sr.no_ppkb) "
                                                                                            + "WHERE rs.no_ppkb = ? AND rs.status_cancel_loading = FALSE"),
*/

    @NamedNativeQuery(name = "ReceivingService.Native.findContainersWithPosition", query = 
"SELECT rs.cont_no, " 
+"  CASE " 
+"    WHEN sr.cont_type IS NOT NULL " 
+"    THEN sr.cont_type " 
+"    ELSE rs.cont_type " 
+"  END, " 
+"  rs.commodity_code, " 
+"  rs.cont_status, " 
+"  rs.cont_gross, " 
+"  rs.seal_no, " 
+"  rs.over_size, " 
+"  rs.dg, " 
+"  rs.dg_label, " 
+"  rs.bl_no, " 
+"  rs.mlo, " 
+"  rs.is_export, " 
+"  rs.disch_port_code, " 
+"  CASE " 
+"    WHEN sgr.no_ppkb IS NULL " 
+"    THEN 'OUTSIDE' " 
+"    ELSE 'CY' " 
+"  END " 
+"FROM receiving_service rs " 
+"LEFT JOIN service_gate_receiving sgr " 
+"ON (rs.job_slip = sgr.job_slip) " 
+"LEFT JOIN service_receiving sr " 
+"ON (rs.cont_no                       = sr.cont_no " 
+"AND rs.no_ppkb                       = sr.no_ppkb) " 
+"WHERE rs.no_ppkb                     = ? " 
+"AND NVL(rs.status_cancel_loading, 0) = 'TRUE'"),                                                                                            

/*
    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceByGenerate", query = "SELECT MAX(substring(job_slip,7,5))FROM receiving_service WHERE substring(job_slip,3,4)= ?"),
*/

    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceByGenerate", query = 
"SELECT MAX(SUBSTR(job_slip,7,5)) " 
+"FROM receiving_service " 
+"WHERE SUBSTR(job_slip,3,4)= ?"),
/*
    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceByClosingTime", query = "SELECT pp.job_slip,pp.no_ppkb,pp.cont_no,pp.seal_no,mm.iso_code, r.no_reg FROM planning_vessel p, receiving_service pp,m_container_type mm,registration r where p.no_ppkb=pp.no_ppkb AND pp.no_reg=r.no_reg AND r.status_service='approve' AND pp.cont_type=mm.cont_type AND p.close_rec>=now() AND pp.valid_date >=now() AND pp.job_slip NOT IN (select job_slip from service_gate_receiving) ORDER BY substring(pp.job_slip,7,4) DESC"),
*/

    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceByClosingTime", query = 
"SELECT pp.job_slip, " 
+"  pp.no_ppkb, " 
+"  pp.cont_no, " 
+"  pp.seal_no, " 
+"  mm.iso_code, " 
+"  r.no_reg " 
+"FROM planning_vessel p, " 
+"  receiving_service pp, " 
+"  m_container_type mm, " 
+"  registration r " 
+"WHERE p.no_ppkb      =pp.no_ppkb " 
+"AND pp.no_reg        =r.no_reg " 
+"AND r.status_service ='approve' " 
+"AND pp.cont_type     =mm.cont_type " 
+"AND p.close_rec     >= sysdate " 
+"AND pp.valid_date   >= sysdate " 
+"AND pp.job_slip NOT IN " 
+"  (SELECT job_slip FROM service_gate_receiving " 
+"  ) " 
+"ORDER BY SUBSTR(pp.job_slip,7,4) DESC"),
    
/*
    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceByClosingTimeByJobSlip", query = "SELECT pp.cont_no,pp.no_ppkb,pp.job_slip,pp.seal_no,mm.iso_code, r.no_reg,pp.cont_status "
                                                                                                        + "FROM planning_vessel p "
                                                                                                                + "JOIN receiving_service pp on (p.no_ppkb=pp.no_ppkb) "
                                                                                                                        + "JOIN m_container_type mm ON (pp.cont_type=mm.cont_type) "
                                                                                                                        + "LEFT JOIN service_gate_receiving sgr ON (pp.job_slip=sgr.job_slip) "
                                                                                                                + "JOIN registration r ON (pp.no_reg=r.no_reg) "
                                                                                                        + "WHERE pp.cont_no = ? AND ((p.close_rec >= now() AND NOT pp.dg) OR (p.close_dg >= now() AND pp.dg)) AND pp.status_cancel_loading = FALSE AND sgr.job_slip IS NULL AND pp.counter_print > 0 "
                                                                                                        + "ORDER BY substring(pp.job_slip,7,4) DESC"),
*/

    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceByClosingTimeByJobSlip", query = 
"SELECT pp.cont_no, " 
+"  pp.no_ppkb, " 
+"  pp.job_slip, " 
+"  pp.seal_no, " 
+"  mm.iso_code, " 
+"  r.no_reg, " 
+"  pp.cont_status " 
+"FROM planning_vessel p " 
+"JOIN receiving_service pp " 
+"ON (p.no_ppkb=pp.no_ppkb) " 
+"JOIN m_container_type mm " 
+"ON (pp.cont_type=mm.cont_type) " 
+"LEFT JOIN service_gate_receiving sgr " 
+"ON (pp.job_slip=sgr.job_slip) " 
+"JOIN registration r " 
+"ON (pp.no_reg                       =r.no_reg) " 
+"WHERE pp.cont_no                    = ? " 
+"AND ((p.close_rec                  >= sysdate " 
+"AND NVL(pp.dg, 0)                   = 'FALSE') " 
+"OR (p.close_dg                     >= sysdate " 
+"AND NVL(pp.dg, 0)                   = 'TRUE')) " 
+"AND NVL(pp.status_cancel_loading,0) = 'FALSE' " 
+"AND sgr.job_slip                   IS NULL " 
+"AND pp.counter_print                > 0 " 
+"ORDER BY SUBSTR(pp.job_slip,7,4) DESC"),

/*                                                                                                            
    @NamedNativeQuery(name = "ReceivingService.Native.perhitungan", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_status, ds.cont_size, m.type_in_general as name, CASE WHEN ds.dg=true THEN 'Yes' WHEN ds.dg=false THEN 'No' END as dg, CASE WHEN ds.dg_label=true THEN 'Yes' WHEN ds.dg_label=false THEN 'No' END as dg_label, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, pls.charge,c.symbol,pls.curr_code,c.language,c.country,ds.bl_no,ds.is_generate, nvl(pp.masa_1), nvl(pp.masa_2), nvl(pp.charge_m1), nvl(pp.charge_m2), nvl(phd.charge) , nvl(ppg.total_charge), nvl(pp.charge), mp.name, nvl(pub.charge) "
                                                                             + "FROM (((((((receiving_service ds "
                                                                                     + "LEFT JOIN perhitungan_handling_discharge phd ON (phd.cont_no = ds.cont_no AND phd.no_reg = ds.no_reg)) "
                                                                                     + "LEFT JOIN perhitungan_penumpukan pp ON (pp.cont_no = ds.cont_no AND pp.no_reg = ds.no_reg)) "
                                                                                     + "LEFT JOIN perhitungan_pass_gate ppg ON (ppg.job_slip=ds.job_slip)) "
                                                                                     + "LEFT JOIN perhitungan_upah_buruh pub ON (pub.jobslip=ds.job_slip)) "
                                                                                     + "JOIN perhitungan_lift_service pls ON (pls.cont_no = ds.cont_no AND pls.no_reg = ds.no_reg)) "
                                                                                     + "JOIN m_port mp ON (mp.port_code=ds.disch_port_code)) "
                                                                                     + "JOIN m_container_type m ON (ds.cont_type=m.cont_type)) "
                                                                                     + "JOIN m_currency c ON (pls.curr_code=c.curr_code) "
                                                                             + "WHERE ds.no_reg = ?"),
*/

    @NamedNativeQuery(name = "ReceivingService.Native.perhitungan", query = 
"SELECT ds.job_slip, " 
+"  ds.cont_no, " 
+"  ds.cont_status, " 
+"  ds.cont_size, " 
+"  m.type_in_general AS name, " 
+"  CASE " 
+"    WHEN ds.dg = 'TRUE' " 
+"    THEN 'Yes' " 
+"    ELSE 'No' " 
+"  END AS dg, " 
+"  CASE " 
+"    WHEN ds.dg_label = 'TRUE' " 
+"    THEN 'Yes' " 
+"    ELSE 'No' " 
+"  END AS dg_label, " 
+"  CASE " 
+"    WHEN ds.over_size = 'TRUE' " 
+"    THEN 'Yes' " 
+"    ELSE 'No' " 
+"  END AS over_size, " 
+"  pls.charge, " 
+"  c.symbol, " 
+"  pls.curr_code, " 
+"  c.language, " 
+"  c.country, " 
+"  ds.bl_no, " 
+"  ds.is_generate, " 
+"  nvln(pp.masa_1), " 
+"  nvln(pp.masa_2), " 
+"  nvln(pp.charge_m1), " 
+"  nvln(pp.charge_m2), " 
+"  nvln(pst.charge) , " 
+"  nvln(pht.charge) , " 
+"  nvln(pho.charge) , " 
+"  nvln(ppg.total_charge), " 
+"  nvln(pp.charge), " 
+"  mp.name, " 
+"  nvln(pub.charge) " 
+"FROM (((((((((receiving_service ds " 
+"LEFT JOIN perhitungan_handling_discharge pst " 
+"ON (pst.cont_no = ds.cont_no " 
+"AND pst.no_reg  = ds.no_reg AND pst.activity_code IN"
+ "(select activity_code from m_activity_tarif_rule where main_activity='STEVEDORING'))) " 
+"LEFT JOIN perhitungan_handling_discharge pht " 
+"ON (pht.cont_no = ds.cont_no " 
+"AND pht.no_reg  = ds.no_reg AND pht.activity_code IN"
+ "(select activity_code from m_activity_tarif_rule where main_activity='HAULAGE_TRUCKING'))) " 
+"LEFT JOIN perhitungan_handling_discharge pho " 
+"ON (pho.cont_no = ds.cont_no " 
+"AND pho.no_reg  = ds.no_reg AND pho.activity_code IN"
+ "(select activity_code from m_activity_tarif_rule where main_activity='OTHER_CHARGES_HANDLING'))) " 
+"LEFT JOIN perhitungan_penumpukan pp " 
+"ON (pp.cont_no = ds.cont_no " 
+"AND pp.no_reg  = ds.no_reg)) " 
+"LEFT JOIN perhitungan_pass_gate ppg " 
+"ON (ppg.job_slip=ds.job_slip)) " 
+"LEFT JOIN perhitungan_upah_buruh pub " 
+"ON (pub.jobslip=ds.job_slip)) " 
+"JOIN perhitungan_lift_service pls " 
+"ON (pls.cont_no = ds.cont_no " 
+"AND pls.no_reg  = ds.no_reg)) " 
+"JOIN m_port mp " 
+"ON (mp.port_code=ds.disch_port_code)) " 
+"JOIN m_container_type m " 
+"ON (ds.cont_type=m.cont_type)) " 
+"JOIN m_currency c " 
+"ON (pls.curr_code=c.curr_code) " 
+"WHERE ds.no_reg  = ?"),                                                                             

/*
    @NamedNativeQuery(name = "ReceivingService.Native.cancelableDocumentContainer", query = "SELECT ds.job_slip, ds.cont_no, pls.charge lift_charge, nvl(phd.charge) handling_charge, nvl(ppg.total_charge) pass_gate_charge, "
                                                                                                    + "nvl(pp.total_charge) penumpukan_charge, nvl(pub.charge) upah_buruh_charge "
                                                                                            + "FROM (((((((((receiving_service ds "
                                                                                                    + "LEFT JOIN perhitungan_handling_discharge phd ON (phd.cont_no = ds.cont_no AND phd.no_reg = ds.no_reg)) "
                                                                                                    + "LEFT JOIN perhitungan_penumpukan pp ON (pp.cont_no = ds.cont_no AND pp.no_reg = ds.no_reg)) "
                                                                                                    + "LEFT JOIN perhitungan_pass_gate ppg ON (ppg.job_slip=ds.job_slip)) "
                                                                                                    + "LEFT JOIN perhitungan_upah_buruh pub ON (pub.jobslip=ds.job_slip)) "
                                                                                                    + "JOIN perhitungan_lift_service pls ON (pls.cont_no = ds.cont_no AND pls.no_reg = ds.no_reg)) "
                                                                                                    + "JOIN m_port disc_p ON (disc_p.port_code=ds.disch_port_code)) "
                                                                                                    + "JOIN m_port del_p ON (del_p.port_code=ds.disch_port_code)) "
                                                                                                    + "JOIN m_container_type m ON (ds.cont_type=m.cont_type)) "
                                                                                                    + "JOIN m_commodity mc ON (ds.commodity_code=mc.commodity_Code)) "
                                                                                                    + "JOIN registration r ON (ds.no_reg=r.no_reg) "
                                                                                                            + "JOIN invoice i ON (r.no_reg=i.no_reg) "
                                                                                                    + "LEFT JOIN service_gate_receiving sgr ON (ds.job_slip=sgr.job_slip) "
                                                                                                    + "LEFT JOIN cancel_document_service cds ON (ds.job_slip=cds.job_slip)"
                                                                                            + "WHERE i.payment_date IS NOT NULL AND sgr.job_slip IS NULL AND cds.job_slip IS NULL AND ds.status_cancel_loading = FALSE AND ds.job_slip = ?"),
*/

    @NamedNativeQuery(name = "ReceivingService.Native.cancelableDocumentContainer", query = 
"SELECT ds.job_slip, " 
+"  ds.cont_no, " 
+"  pls.charge lift_charge, " 
+"  NVLN(phd.charge) handling_charge, " 
+"  NVLN(ppg.total_charge) pass_gate_charge, " 
+"  NVLN(pp.total_charge) penumpukan_charge, " 
+"  NVLN(pub.charge) upah_buruh_charge " 
+"FROM (((((((((receiving_service ds " 
+"LEFT JOIN perhitungan_handling_discharge phd " 
+"ON (phd.cont_no = ds.cont_no " 
+"AND phd.no_reg  = ds.no_reg)) " 
+"LEFT JOIN perhitungan_penumpukan pp " 
+"ON (pp.cont_no = ds.cont_no " 
+"AND pp.no_reg  = ds.no_reg)) " 
+"LEFT JOIN perhitungan_pass_gate ppg " 
+"ON (ppg.job_slip=ds.job_slip)) " 
+"LEFT JOIN perhitungan_upah_buruh pub " 
+"ON (pub.jobslip=ds.job_slip)) " 
+"JOIN perhitungan_lift_service pls " 
+"ON (pls.cont_no = ds.cont_no " 
+"AND pls.no_reg  = ds.no_reg)) " 
+"JOIN m_port disc_p " 
+"ON (disc_p.port_code=ds.disch_port_code)) " 
+"JOIN m_port del_p " 
+"ON (del_p.port_code=ds.disch_port_code)) " 
+"JOIN m_container_type m " 
+"ON (ds.cont_type=m.cont_type)) " 
+"JOIN m_commodity mc " 
+"ON (ds.commodity_code=mc.commodity_Code)) " 
+"JOIN registration r " 
+"ON (ds.no_reg=r.no_reg) " 
+"JOIN invoice i " 
+"ON (r.no_reg=i.no_reg) " 
+"LEFT JOIN service_gate_receiving sgr " 
+"ON (ds.job_slip=sgr.job_slip) " 
+"LEFT JOIN cancel_document_service cds " 
+"ON (ds.job_slip                     =cds.job_slip) " 
+"WHERE i.payment_date               IS NOT NULL " 
+"AND sgr.job_slip                   IS NULL " 
+"AND cds.job_slip                   IS NULL " 
+"AND NVL(ds.status_cancel_loading,0) = 'FALSE' " 
+"AND ds.job_slip                     = ?"),     

    
/* Cari yg merefer ke NQ ini */
    @NamedNativeQuery(name = "ReceivingService.Native.isPassedThroughTheGate", query = "SELECT CASE WHEN COUNT(*) > 0 THEN 'TRUE' ELSE 'FALSE' END "
    + "FROM receiving_service rs JOIN service_gate_receiving sgr ON (rs.cont_no=sgr.cont_no AND rs.no_ppkb=sgr.no_ppkb) "
    + "WHERE rs.no_reg = ?"),
    @NamedNativeQuery(name = "ReceivingService.Native.findInvoice", query = "SELECT ds.job_slip FROM receiving_service ds WHERE ds.cont_no = ? AND ds.no_ppkb = ? AND ds.no_reg = ?"),

/*    
    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingRecaps", query = "SELECT r.job_slip, r.cont_no, r.cont_status, r.cont_size, c.type_in_general as name, change(r.over_size), change(r.dg), change(r.dg_label), s.transaction_date, e.equip_code, e.start_time, e.end_time, chargenol(e.equip_code, p.charge) AS charge, curr.symbol, curr.language, curr.country "
    + "FROM (((( "
    + "receiving_service r LEFT JOIN m_container_type c ON r.cont_type=c.cont_type) "
    + "LEFT JOIN service_receiving s ON r.cont_no=s.cont_no AND r.no_ppkb=s.no_ppkb) "
    + "LEFT JOIN equipment e ON r.cont_no=e.cont_no AND r.no_ppkb=e.no_ppkb_recv AND e.operation = 'LOAD' AND e.equipment_act_code='RECEIVING') "
    + "LEFT JOIN perhitungan_lift_service p ON r.no_reg=p.no_reg AND r.cont_no=p.cont_no AND r.no_ppkb=p.no_ppkb), m_currency curr "
    + "WHERE r.no_ppkb=? AND p.curr_code = curr.curr_code ORDER BY charge DESC"),
*/

    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingRecaps", query = 
"SELECT r.job_slip, " 
+"  r.cont_no, " 
+"  r.cont_status, " 
+"  r.cont_size, " 
+"  c.type_in_general AS name, " 
+"  change(r.over_size), " 
+"  change(r.dg), " 
+"  change(r.dg_label), " 
+"  s.transaction_date, " 
+"  e.equip_code, " 
+"  e.start_time, " 
+"  e.end_time, " 
+"  chargenol(e.equip_code, p.charge) AS charge, " 
+"  curr.symbol, " 
+"  curr.language, " 
+"  curr.country " 
+"FROM (((( receiving_service r " 
+"LEFT JOIN m_container_type c " 
+"ON r.cont_type=c.cont_type) " 
+"LEFT JOIN service_receiving s " 
+"ON r.cont_no =s.cont_no " 
+"AND r.no_ppkb=s.no_ppkb) " 
+"LEFT JOIN equipment e " 
+"ON r.cont_no            =e.cont_no " 
+"AND r.no_ppkb           =e.no_ppkb_recv " 
+"AND e.operation         = 'LOAD' " 
+"AND e.equipment_act_code='RECEIVING') " 
+"LEFT JOIN perhitungan_lift_service p " 
+"ON r.no_reg  =p.no_reg " 
+"AND r.cont_no=p.cont_no " 
+"AND r.no_ppkb=p.no_ppkb), " 
+"  m_currency curr " 
+"WHERE r.no_ppkb =? " 
+"AND p.curr_code = curr.curr_code " 
+"ORDER BY charge DESC"),    

/*
    @NamedNativeQuery(name = "ReceivingService.Native.findCountableLoadReefers", query = "SELECT r.job_slip, r.cont_no,r.cont_size,mt.type_in_general as name,r.cont_status,r.cont_gross,r.over_size,r.bl_no, "
                                                                                            + "CASE WHEN gir.cont_no IS NULL THEN "
                                                                                                + "'Outside Terminal' "
                                                                                        + "ELSE "
                                                                                            + "CASE WHEN sr.cont_no IS NULL THEN "
                                                                                                + "'Not Plugged On' "
                                                                                            + "ELSE "
                                                                                                + "CASE WHEN sr.plug_off IS NULL THEN "
                                                                                                    + "'Plugged On' "
                                                                                                + "ELSE "
                                                                                                    + "'Plugged Off' "
                                                                                                + "END "
                                                                                            + "END "
                                                                                        + "END AS status "
                                                                                        + "FROM receiving_service r "
                                                                                                + "LEFT JOIN service_reefer sr ON (r.cont_no=sr.cont_no AND r.no_ppkb=sr.no_ppkb) "
                                                                                                + "LEFT JOIN service_gate_receiving gir ON (r.cont_no=gir.cont_no AND r.no_ppkb=gir.no_ppkb) "
                                                                                                + "LEFT JOIN reefer_load_service rls ON (r.cont_no=rls.cont_no AND r.no_ppkb=rls.no_ppkb) "
                                                                                                + "JOIN m_container_type mt ON (r.cont_type=mt.cont_type) "
                                                                                        + "WHERE rls.job_slip IS NULL AND mt.type_in_general = 'RFR' AND r.no_ppkb = ? "
                                                                                        + "ORDER BY substring(r.job_slip,7,4) DESC;"),

*/
    @NamedNativeQuery(name = "ReceivingService.Native.findCountableLoadReefers", query = 
"SELECT r.job_slip, " 
+"  r.cont_no, " 
+"  r.cont_size, " 
+"  mt.type_in_general AS name, " 
+"  r.cont_status, " 
+"  r.cont_gross, " 
+"  r.over_size, " 
+"  r.bl_no, " 
+"  CASE " 
+"    WHEN gir.cont_no IS NULL " 
+"    THEN 'Outside Terminal' " 
+"    ELSE " 
+"      CASE " 
+"        WHEN sr.cont_no IS NULL " 
+"        THEN 'Not Plugged On' " 
+"        ELSE " 
+"          CASE " 
+"            WHEN sr.plug_off IS NULL " 
+"            THEN 'Plugged On' " 
+"            ELSE 'Plugged Off' " 
+"          END " 
+"      END " 
+"  END AS status " 
+"FROM receiving_service r " 
+"LEFT JOIN service_reefer sr " 
+"ON (r.cont_no=sr.cont_no " 
+"AND r.no_ppkb=sr.no_ppkb) " 
+"LEFT JOIN service_gate_receiving gir " 
+"ON (r.cont_no=gir.cont_no " 
+"AND r.no_ppkb=gir.no_ppkb) " 
+"LEFT JOIN reefer_load_service rls " 
+"ON (r.cont_no=rls.cont_no " 
+"AND r.no_ppkb=rls.no_ppkb) " 
+"JOIN m_container_type mt " 
+"ON (r.cont_type        =mt.cont_type) " 
+"WHERE rls.job_slip    IS NULL " 
+"AND mt.type_in_general = 'RFR' " 
+"AND r.no_ppkb          = ? " 
+"ORDER BY SUBSTR(r.job_slip,7,4) DESC"),                                                                                        
//    @NamedNativeQuery(name = "ReceivingService.Native.findHandlingLoadMonitoring", query = "SELECT p.cont_type, p.cont_no, p.cont_status, p.cont_size, c.type_in_general as name, e1.equip_code, e1.start_time, e1.end_time, e2.equip_code, e2.start_time, e2.end_time, e3.equip_code, e3.start_time, e3.end_time, p.seal_no "
//    + "FROM (((receiving_service p LEFT JOIN m_container_type c ON p.cont_type=c.cont_type) "
//    + "LEFT JOIN equipment e1 ON p.cont_no = e1.cont_no AND p.no_ppkb = e1.no_ppkb AND e1.operation = 'LOAD' AND e1.equipment_act_code = 'LIFTON') "
//    + "LEFT JOIN equipment e2 ON p.cont_no = e2.cont_no AND p.no_ppkb = e2.no_ppkb AND e2.operation = 'LOAD' AND e2.equipment_act_code = 'H/T') "
//    + "LEFT JOIN equipment e3 ON p.cont_no = e3.cont_no AND p.no_ppkb = e3.no_ppkb AND e3.operation = 'LOAD' AND e3.equipment_act_code = 'STEVEDORING' "
//    + "WHERE p.no_ppkb = ?"),

/*
    @NamedNativeQuery(name = "ReceivingService.Native.findHandlingLoadMonitoring", query = "SELECT	 scl.cont_no,scl.cont_size, (SELECT mct1.type_in_general FROM m_container_type mct1 WHERE scl.cont_type::text = mct1.cont_type::text) AS type ,scl.cont_status, "
            + "scl.seal_no, e3.equip_code AS tt_code, e3.end_time AS lift_date, e2.equip_code AS ht, e2.end_time AS ht_date, e1.equip_code AS cc_code, e1.end_time AS stv_date "
            + "FROM service_cont_load scl "
            + "LEFT JOIN equipment e1 ON e1.no_ppkb::text = scl.no_ppkb::text AND e1.cont_no::text = scl.cont_no::text AND e1.equipment_act_code::text = 'STEVEDORING'::text AND e1.operation::text = 'LOAD'::text "
            + "LEFT JOIN equipment e2 ON e2.no_ppkb::text = scl.no_ppkb::text AND e2.cont_no::text = scl.cont_no::text AND e2.equipment_act_code::text = 'H/T'::text AND e2.operation::text = 'LOAD'::text "
            + "LEFT JOIN equipment e3 ON e3.no_ppkb::text = scl.no_ppkb::text AND e3.cont_no::text = scl.cont_no::text AND e3.equipment_act_code::text = 'LIFTON'::text AND e3.operation::text = 'LOAD'::text "
            + "WHERE scl.is_transhipment = FALSE AND scl.status_cancel_loading = FALSE AND is_transhipment = FALSE AND scl.no_ppkb = ?"),
*/

    @NamedNativeQuery(name = "ReceivingService.Native.findHandlingLoadMonitoring", query = 
"SELECT scl.cont_no, " 
+"  scl.cont_size, " 
+"  (SELECT mct1.type_in_general " 
+"  FROM m_container_type mct1 " 
+"  WHERE scl.cont_type = mct1.cont_type " 
+"  ) AS type , " 
+"  scl.cont_status, " 
+"  scl.seal_no, " 
+"  e3.equip_code AS tt_code, " 
+"  e3.end_time   AS lift_date, " 
+"  e2.equip_code AS ht, " 
+"  e2.end_time   AS ht_date, " 
+"  e1.equip_code AS cc_code, " 
+"  e1.end_time   AS stv_date " 
+"FROM service_cont_load scl " 
+"LEFT JOIN equipment e1 " 
+"ON e1.no_ppkb             = scl.no_ppkb " 
+"AND e1.cont_no            = scl.cont_no " 
+"AND e1.equipment_act_code = 'STEVEDORING' " 
+"AND e1.operation          = 'LOAD' " 
+"LEFT JOIN equipment e2 " 
+"ON e2.no_ppkb             = scl.no_ppkb " 
+"AND e2.cont_no            = scl.cont_no " 
+"AND e2.equipment_act_code = 'H/T' " 
+"AND e2.operation          = 'LOAD' " 
+"LEFT JOIN equipment e3 " 
+"ON e3.no_ppkb                         = scl.no_ppkb " 
+"AND e3.cont_no                        = scl.cont_no " 
+"AND e3.equipment_act_code             = 'LIFTON' " 
+"AND e3.operation                      = 'LOAD' " 
+"WHERE scl.is_transhipment     = 'FALSE' " 
+"AND scl.status_cancel_loading = 'FALSE' " 
+"AND is_transhipment           = 'FALSE' " 
+"AND scl.no_ppkb                       = ?"),            

/*
    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceContNo", query = "SELECT scd.cont_no,scd.job_slip FROM receiving_service AS scd WHERE scd.no_ppkb = ? AND scd.cont_no = ? AND scd.cont_no NOT IN (select cont_no from cancel_loading_service where no_ppkb=scd.no_ppkb)"),
    
*/
    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceContNo", query = 
"SELECT scd.cont_no, " 
+"  scd.job_slip " 
+"FROM receiving_service scd " 
+"WHERE scd.no_ppkb    = ? " 
+"AND scd.cont_no      = ? " 
+"AND scd.cont_no NOT IN " 
+"  (SELECT cont_no FROM cancel_loading_service WHERE no_ppkb=scd.no_ppkb " 
+"  )"),

/*    
    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceByAutoCompleteCancelLoading", query = "SELECT scd.cont_no FROM receiving_service AS scd WHERE scd.no_ppkb = ? AND scd.cont_no LIKE ('%'|| ? ||'%') AND scd.cont_no NOT IN (select cont_no from cancel_loading_service where no_ppkb=scd.no_ppkb)"),
*/

    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceByAutoCompleteCancelLoading", query = 
"SELECT scd.cont_no " 
+"FROM receiving_service scd " 
+"WHERE scd.no_ppkb = ? " 
+"AND scd.cont_no LIKE ('%' " 
+"  || ? " 
+"  ||'%') " 
+"AND scd.cont_no NOT IN " 
+"  (SELECT cont_no FROM cancel_loading_service WHERE no_ppkb=scd.no_ppkb " 
+"  )"),
    
    @NamedNativeQuery(name = "ReceivingService.Native.findJobSlipAmount", query = "SELECT count(no_ppkb) FROM receiving_service WHERE no_ppkb = ?"),

/*
    @NamedNativeQuery(name = "ReceivingService.Native.findNotEnteringYetByContNo", query = "SELECT r.job_slip, r.no_reg, r.cont_type, r.commodity_code, r.no_ppkb, r.load_port_code, "
                                                                                                + "r.disch_port_code, r.no_invoice, r.cont_no, r.cont_size, r.cont_status, "
                                                                                                + "r.cont_gross, r.seal_no, r.over_size, r.dg, r.dg_label, r.valid_date, r.masa_1, "
                                                                                                + "r.masa_2, r.end_storage_date, r.is_generate, r.bl_no, r.created_by, r.created_date, "
                                                                                                + "r.modified_by, r.modified_date, r.counter_print, r.real_penumpukan, r.status_cancel_loading, "
                                                                                                + "r.mlo, r.status_cancel_jobslip "
                                                                                            + "FROM receiving_service r "
                                                                                            + "WHERE r.cont_no = ? AND (r.status_cancel_jobslip IS NOT TRUE OR r.status_cancel_jobslip IS NULL) AND r.status_cancel_loading <> TRUE AND r.job_slip NOT IN (SELECT job_slip FROM service_gate_receiving);"),
*/
    @NamedNativeQuery(name = "ReceivingService.Native.findNotEnteringYetByContNo", query = 
"SELECT r.job_slip, " 
+"  r.no_reg, " 
+"  r.cont_type, " 
+"  r.commodity_code, " 
+"  r.no_ppkb, " 
+"  r.load_port_code, " 
+"  r.disch_port_code, " 
+"  r.no_invoice, " 
+"  r.cont_no, " 
+"  r.cont_size, " 
+"  r.cont_status, " 
+"  r.cont_gross, " 
+"  r.seal_no, " 
+"  r.over_size, " 
+"  r.dg, " 
+"  r.dg_label, " 
+"  r.valid_date, " 
+"  r.masa_1, " 
+"  r.masa_2, " 
+"  r.end_storage_date, " 
+"  r.is_generate, " 
+"  r.bl_no, " 
+"  r.created_by, " 
+"  r.created_date, " 
+"  r.modified_by, " 
+"  r.modified_date, " 
+"  r.counter_print, " 
+"  r.real_penumpukan, " 
+"  r.status_cancel_loading, " 
+"  r.mlo, " 
+"  r.status_cancel_jobslip " 
+"FROM receiving_service r " 
+"WHERE r.cont_no                     = ? " 
+"AND r.status_cancel_jobslip = 'FALSE' " 
+"AND r.status_cancel_loading = 'FALSE' " 
+"AND r.job_slip NOT                 IN " 
+"  (SELECT job_slip FROM service_gate_receiving " 
+"  )"),
                                                                                            
    @NamedNativeQuery(name = "ReceivingService.Native.findGateInPassableJobSlips", query = "SELECT d.cont_no "
                                                                                            + "FROM receiving_service d "
                                                                                                    + "LEFT JOIN service_gate_receiving sgr ON (d.job_slip=sgr.job_slip) "
                                                                                            + "WHERE d.cont_no LIKE ('%'|| ? ||'%') AND sgr.job_slip IS NULL AND d.counter_print > 0 "
                                                                                            + "ORDER BY d.created_date DESC"),
                                                                                            
    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingServiceByUpdateDate", query = "SELECT scd.job_slip FROM receiving_service scd WHERE scd.no_ppkb = ? AND scd.cont_no = ? "),
/*    
    @NamedNativeQuery(name = "ReceivingService.Native.findCountableLoadReefersByNoBl", query = "SELECT r.job_slip, r.cont_no,r.cont_size,mt.name,r.cont_status,r.cont_gross,r.over_size,r.bl_no, "
                                                                                                    + "CASE WHEN gir.cont_no IS NULL THEN "
                                                                                                        + "'Outside Terminal' "
                                                                                                + "ELSE "
                                                                                                    + "CASE WHEN sr.cont_no IS NULL THEN "
                                                                                                        + "'Not Plugged On' "
                                                                                                    + "ELSE "
                                                                                                        + "CASE WHEN sr.plug_off IS NULL THEN "
                                                                                                            + "'Plugged On' "
                                                                                                        + "ELSE "
                                                                                                            + "'Plugged Off' "
                                                                                                        + "END "
                                                                                                    + "END "
                                                                                                + "END AS status "
                                                                                                + "FROM receiving_service r "
                                                                                                        + "LEFT JOIN service_reefer sr ON (r.cont_no=sr.cont_no AND r.no_ppkb=sr.no_ppkb) "
                                                                                                        + "LEFT JOIN service_gate_receiving gir ON (r.cont_no=gir.cont_no AND r.no_ppkb=gir.no_ppkb) "
                                                                                                        + "LEFT JOIN reefer_load_service rls ON (r.cont_no=rls.cont_no AND r.no_ppkb=rls.no_ppkb) "
                                                                                                        + "JOIN m_container_type mt ON (r.cont_type=mt.cont_type) "
                                                                                                + "WHERE rls.job_slip IS NULL AND mt.type_in_general = 'RFR' AND r.bl_no = ? AND r.no_ppkb = ? "
                                                                                                + "ORDER BY substring(r.job_slip,7,4) DESC;"),
*/

    @NamedNativeQuery(name = "ReceivingService.Native.findCountableLoadReefersByNoBl", query = 
"SELECT r.job_slip, " 
+"  r.cont_no, " 
+"  r.cont_size, " 
+"  mt.name, " 
+"  r.cont_status, " 
+"  r.cont_gross, " 
+"  r.over_size, " 
+"  r.bl_no, " 
+"  CASE " 
+"    WHEN gir.cont_no IS NULL " 
+"    THEN 'Outside Terminal' " 
+"    ELSE " 
+"      CASE " 
+"        WHEN sr.cont_no IS NULL " 
+"        THEN 'Not Plugged On' " 
+"        ELSE " 
+"          CASE " 
+"            WHEN sr.plug_off IS NULL " 
+"            THEN 'Plugged On' " 
+"            ELSE 'Plugged Off' " 
+"          END " 
+"      END " 
+"  END AS status " 
+"FROM receiving_service r " 
+"LEFT JOIN service_reefer sr " 
+"ON (r.cont_no=sr.cont_no " 
+"AND r.no_ppkb=sr.no_ppkb) " 
+"LEFT JOIN service_gate_receiving gir " 
+"ON (r.cont_no=gir.cont_no " 
+"AND r.no_ppkb=gir.no_ppkb) " 
+"LEFT JOIN reefer_load_service rls " 
+"ON (r.cont_no=rls.cont_no " 
+"AND r.no_ppkb=rls.no_ppkb) " 
+"JOIN m_container_type mt " 
+"ON (r.cont_type        =mt.cont_type) " 
+"WHERE rls.job_slip    IS NULL " 
+"AND mt.type_in_general = 'RFR' " 
+"AND r.bl_no            = ? " 
+"AND r.no_ppkb          = ? " 
+"ORDER BY SUBSTR(r.job_slip,7,4) DESC"),
                                                                                                
    @NamedNativeQuery(name = "ReceivingService.Native.findByListBatalNota", query = "select d.bl_no,d.job_slip,d.cont_no,d.cont_status,mt.name,d.cont_gross from receiving_service d,m_container_type mt where d.no_reg=? AND d.cont_type=mt.cont_type"),
    @NamedNativeQuery(name = "ReceivingService.Native.findByListBatalNotaService", query = "select d.job_slip,d.no_reg,d.no_ppkb,d.cont_no from receiving_service d where d.no_ppkb=? AND d.no_reg=?"),
    @NamedNativeQuery(name = "ReceivingService.Native.findByNoregValidasiPrint", query = "select ds.job_slip from receiving_service ds where ds.counter_print >=1 AND ds.no_reg=?"),
    @NamedNativeQuery(name = "ReceivingService.Native.findContainerDetail", query = "select r.no_reg,r.no_ppkb,r.bl_no,r.job_slip,r.cont_no,r.cont_status,r.cont_size,r.created_by,r.modified_by,r.created_date,r.modified_date from receiving_service r where r.no_ppkb= ? "),

/*    
    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingCapacityStatusByNoPpkb", query = "SELECT nvl(SUM(ds.cont_gross)), nvl(SUM(ds.cont_size)) "
                                                                                                    + "FROM "
                                                                                                            + "(SELECT ds.no_ppkb, ds.cont_gross / 1000 AS cont_gross, CASE WHEN ds.cont_size = 20 THEN 1 WHEN ds.cont_size = 40 THEN 2 END AS cont_size "
                                                                                                            + "FROM receiving_service ds "
                                                                                                                    + "JOIN perhitungan_lift_service pls ON (pls.cont_no = ds.cont_no AND pls.no_reg = ds.no_reg) "
                                                                                                            + "WHERE ds.status_cancel_loading = FALSE "
                                                                                                            + "UNION ALL "
                                                                                                            + "SELECT cls.new_ppkb, cls.cont_gross / 1000, CASE WHEN cls.cont_size = 20 THEN 1 WHEN cls.cont_size = 40 THEN 2 END "
                                                                                                            + "FROM cancel_loading_service cls) ds "
                                                                                                    + "WHERE ds.no_ppkb = ?"),
*/

    @NamedNativeQuery(name = "ReceivingService.Native.findReceivingCapacityStatusByNoPpkb", query = 
"SELECT nvln(SUM(ds.cont_gross)), " 
+"  NVLN(SUM(ds.cont_size)) " 
+"FROM " 
+"  (SELECT ds.no_ppkb, " 
+"    ds.cont_gross / 1000 AS cont_gross, " 
+"    CASE " 
+"      WHEN ds.cont_size = 20 " 
+"      THEN 1 " 
+"      WHEN ds.cont_size = 40 " 
+"      THEN 2 " 
+"    END AS cont_size " 
+"  FROM receiving_service ds " 
+"  JOIN perhitungan_lift_service pls " 
+"  ON (pls.cont_no                        = ds.cont_no " 
+"  AND pls.no_reg                         = ds.no_reg) " 
+"  WHERE ds.status_cancel_loading = 'FALSE' " 
+"  UNION ALL " 
+"  SELECT cls.new_ppkb, " 
+"    cls.cont_gross / 1000, " 
+"    CASE " 
+"      WHEN cls.cont_size = 20 " 
+"      THEN 1 " 
+"      WHEN cls.cont_size = 40 " 
+"      THEN 2 " 
+"    END " 
+"  FROM cancel_loading_service cls " 
+"  ) ds " 
+"WHERE ds.no_ppkb = ?"),
                                                                                                    
/*
    @NamedNativeQuery(name = "ReceivingService.Native.findCashierLoad", query = "SELECT rs.job_slip, rs.valid_date,inv.no_invoice,inv.payment_date,rs.created_by,m.name as consignee,r.pengurus_do,rs.no_reg "
            + "FROM receiving_service rs "
            + "LEFT JOIN invoice inv ON inv.no_reg::TEXT = rs.no_reg::TEXT LEFT JOIN registration r ON r.no_reg = rs.no_reg LEFT JOIN m_customer m ON m.cust_code = r.cust_code WHERE rs.no_ppkb = ? AND rs.cont_no= ?"),
*/            

    @NamedNativeQuery(name = "ReceivingService.Native.findCashierLoad", query = 
"SELECT rs.job_slip, " 
+"  rs.valid_date, " 
+"  inv.no_invoice, " 
+"  inv.payment_date, " 
+"  rs.created_by, " 
+"  m.name AS consignee, " 
+"  r.pengurus_do, " 
+"  rs.no_reg " 
+"FROM receiving_service rs " 
+"LEFT JOIN invoice inv " 
+"ON inv.no_reg = rs.no_reg " 
+"LEFT JOIN registration r " 
+"ON r.no_reg = rs.no_reg " 
+"LEFT JOIN m_customer m " 
+"ON m.cust_code   = r.cust_code " 
+"WHERE rs.no_ppkb = ? " 
+"AND rs.cont_no   = ?"),            

/*
    @NamedNativeQuery(name = "ReceivingService.Native.findCashierLoadCancel", query = "SELECT rs.job_slip, rs.valid_date,inv.no_invoice,inv.payment_date,rs.created_by,m.name as consignee,r.pengurus_do,sr.block,sr.y_slot,sr.y_row,sr.y_tier "
            + "FROM receiving_service rs "
            + "LEFT JOIN invoice inv ON inv.no_reg::TEXT = rs.no_reg::TEXT LEFT JOIN registration r ON r.no_reg = rs.no_reg LEFT JOIN m_customer m ON m.cust_code = r.cust_code LEFT JOIN service_receiving sr ON sr.no_ppkb = rs.no_ppkb AND sr.cont_no = rs.cont_no AND sr.status_cancel_loading=TRUE WHERE rs.no_ppkb = ? AND rs.cont_no= ? AND rs.status_cancel_loading=TRUE"
*/
    @NamedNativeQuery(name = "ReceivingService.Native.findCashierLoadCancel", query = 
"SELECT rs.job_slip, " 
+"  rs.valid_date, " 
+"  inv.no_invoice, " 
+"  inv.payment_date, " 
+"  rs.created_by, " 
+"  m.name AS consignee, " 
+"  r.pengurus_do, " 
+"  sr.block, " 
+"  sr.y_slot, " 
+"  sr.y_row, " 
+"  sr.y_tier " 
+"FROM receiving_service rs " 
+"LEFT JOIN invoice inv " 
+"ON inv.no_reg = rs.no_reg " 
+"LEFT JOIN registration r " 
+"ON r.no_reg = rs.no_reg " 
+"LEFT JOIN m_customer m " 
+"ON m.cust_code = r.cust_code " 
+"LEFT JOIN service_receiving sr " 
+"ON sr.no_ppkb                        = rs.no_ppkb " 
+"AND sr.cont_no                       = rs.cont_no " 
+"AND sr.status_cancel_loading  = 'TRUE' " 
+"WHERE rs.no_ppkb                     = ? " 
+"AND rs.cont_no                       = ? " 
+"AND rs.status_cancel_loading = 'TRUE'"
            )})

    public class ReceivingService implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private String twentyOneFeet = "FALSE";
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
        
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobSlip;

    @Column(name = "cont_no", length = 11)
    private String contNo;
    
    @Column(name = "no_ppkb", insertable=false, updatable=false)
    private String noPpkb;
    @Column(name = "no_reg", insertable=false, updatable=false)
    private String noReg;
    @Column(name = "no_invoice", length = 30)
    private String noInvoice;
    
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_status", length = 10)
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "over_size")
    private String overSize;
    @Column(name = "dg")
    private String dg;
    @Column(name = "dg_label")
    private String dgLabel;
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @Column(name = "masa_1")
    private Short masa1;
    @Column(name = "masa_2")
    private Short masa2;
    @Column(name = "is_generate")
    private String isGenerate;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg")
    @ManyToOne
    private Registration registration;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "disch_port_code", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort masterPort;
    @JoinColumn(name = "load_port_code", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort masterPort1;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @Column(name = "end_storage_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endStorageDate;
    @Column(name = "counter_print")
    private Integer counterPrint = 0;
    @Column(name = "real_penumpukan")
    private Integer realPenumpukan;
    @Column(name = "status_cancel_loading")
    private String statusCancelLoading = "FALSE";
    @Column(name = "status_cancel_jobslip")
    private String statusCancelJobslip = "FALSE";
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
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Basic(optional = false)
    @Column(name = "is_export", nullable = false)
    private String isExport = "FALSE";
    @JoinColumn(name = "port_of_delivery", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort portOfDelivery;

    public ReceivingService() {
    }

    public ReceivingService(String contNo) {
        this.contNo = contNo;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getNoInvoice() {
        return noInvoice;
    }

    public void setNoInvoice(String noInvoice) {
        this.noInvoice = noInvoice;
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

    public String getOverSize() {
        return overSize;
    }

    public void setOverSize(String overSize) {
        this.overSize = overSize;
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

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;

        if (registration != null) {
            noReg = registration.getNoReg();
        }
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

    public MasterPort getMasterPort() {
        return masterPort;
    }

    public void setMasterPort(MasterPort masterPort) {
        this.masterPort = masterPort;
    }

    public MasterPort getMasterPort1() {
        return masterPort1;
    }

    public void setMasterPort1(MasterPort masterPort1) {
        this.masterPort1 = masterPort1;
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
     * @return the masa1
     */
    public Short getMasa1() {
        return masa1;
    }

    /**
     * @param masa1 the masa1 to set
     */
    public void setMasa1(Short masa1) {
        this.masa1 = masa1;
    }

    /**
     * @return the masa2
     */
    public Short getMasa2() {
        return masa2;
    }

    /**
     * @param masa2 the masa2 to set
     */
    public void setMasa2(Short masa2) {
        this.masa2 = masa2;
    }

    public Date getEndStorageDate() {
        return endStorageDate;
    }

    public void setEndStorageDate(Date endStorageDate) {
        this.endStorageDate = endStorageDate;
    }

    public String getIsGenerate() {
        return isGenerate;
    }

    public void setIsGenerate(String isGenerate) {
        this.isGenerate = isGenerate;
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

    public Integer getCounterPrint() {
        return counterPrint;
    }

    public void setCounterPrint(Integer counterPrint) {
        this.counterPrint = counterPrint;
    }

    public Integer getRealPenumpukan() {
        return realPenumpukan;
    }

    public void setRealPenumpukan(Integer realPenumpukan) {
        this.realPenumpukan = realPenumpukan;
    }

    public String getStatusCancelLoading() {
        return statusCancelLoading;
    }

    public void setStatusCancelLoading(String statusCancelLoading) {
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

    public String getNoReg() {
        return noReg;
    }

    public String getStatusCancelJobslip() {
        return statusCancelJobslip;
    }

    public void setStatusCancelJobslip(String statusCancelJobslip) {
        this.statusCancelJobslip = statusCancelJobslip;
    }

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    public Integer getContTeus() {
        if (contSize != null) {
            return (int) Math.ceil((int) (contSize.doubleValue()/ (double) 20));
        }

        return null;
    }

    public MasterPort getPortOfDelivery() {
        return portOfDelivery;
    }

    public void setPortOfDelivery(MasterPort portOfDelivery) {
        this.portOfDelivery = portOfDelivery;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash += (contNo != null ? contNo.hashCode() : 0);
        hash += (jobSlip != null ? jobSlip.hashCode() : 1);
        
        return hash;
        
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceivingService)) {
            return false;
        }
        ReceivingService other = (ReceivingService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip == null && other.jobSlip != null) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ReceivingService[jobSlip=" + jobSlip + "]";
    }

    public String getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(String twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
