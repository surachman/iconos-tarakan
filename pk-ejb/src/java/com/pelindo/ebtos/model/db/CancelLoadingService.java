/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "cancel_loading_service")
@NamedQueries({
    @NamedQuery(name = "CancelLoadingService.findAll", query = "SELECT c FROM CancelLoadingService c"),
    @NamedQuery(name = "CancelLoadingService.findById", query = "SELECT c FROM CancelLoadingService c WHERE c.jobSlip = :jobSlip"),
    @NamedQuery(name = "CancelLoadingService.findByNoReg", query = "SELECT c FROM CancelLoadingService c WHERE c.registration.noReg = :noReg"),
    @NamedQuery(name = "CancelLoadingService.findByNoPpkb", query = "SELECT c FROM CancelLoadingService c WHERE c.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "CancelLoadingService.findByNoPpkbContNoAndStatusDelivery", query = "SELECT c FROM CancelLoadingService c WHERE c.planningVessel.noPpkb = :noPpkb AND c.contNo = :contNo AND c.isDelivery = :isDelivery"),
    @NamedQuery(name = "CancelLoadingService.findByNewPpkbAndRelocationStatus", query = "SELECT c FROM CancelLoadingService c WHERE c.newPlanningVessel.noPpkb = :noPpkb AND c.contNo = :contNo AND c.hasRelocated = :hasRelocated"),
    @NamedQuery(name = "CancelLoadingService.findByNewPpkbAndContNo", query = "SELECT c FROM CancelLoadingService c WHERE c.newPlanningVessel.noPpkb = :noPpkb AND c.contNo = :contNo"),
    @NamedQuery(name = "CancelLoadingService.findByNoPpkbAndContNo", query = "SELECT c FROM CancelLoadingService c WHERE c.planningVessel.noPpkb = :noPpkb AND c.contNo = :contNo"),
    @NamedQuery(name = "CancelLoadingService.findByContNo", query = "SELECT c FROM CancelLoadingService c WHERE c.contNo = :contNo"),
    @NamedQuery(name = "CancelLoadingService.findDischargableContainerByContNoAndPpkb", query = "SELECT c FROM CancelLoadingService c WHERE c.planningVessel.noPpkb = :noPpkb AND c.contNo = :contNo AND c.isDischarge = FALSE AND c.posisi = '" + CancelLoadingService.AT_VESSEL + "' AND (c.category=4 OR c.category=5) AND c.counterPrint > 0"),
    @NamedQuery(name = "CancelLoadingService.findLiftableOffByContNo", query = "SELECT c FROM CancelLoadingService c WHERE c.contNo = :contNo AND c.isDischarge = TRUE AND c.truckLosing = FALSE AND c.posisi = '" + CancelLoadingService.AT_HAULAGE_TRUCK + "' AND (c.category=4 or c.category=5)"),
    @NamedQuery(name = "CancelLoadingService.findMovableContainer", query = "SELECT c FROM CancelLoadingService c WHERE c.contNo = :contNo AND c.posisi = '" + CancelLoadingService.AT_CY + "' AND (((c.category=2 OR c.category=4) AND c.hasRelocated = TRUE) OR ((c.category=3 OR c.category=4) AND c.isDelivery <> TRUE))"),
    @NamedQuery(name = "CancelLoadingService.findMovableOffContainer", query = "SELECT c FROM CancelLoadingService c WHERE c.contNo = :contNo AND c.posisi = '" + CancelLoadingService.AT_MOVEMENT + "' AND (((c.category=2 OR c.category=4) AND c.hasRelocated = TRUE) OR ((c.category=3 OR c.category=4) AND c.isDelivery <> TRUE))"),
    @NamedQuery(name = "CancelLoadingService.findByContSize", query = "SELECT c FROM CancelLoadingService c WHERE c.contSize = :contSize"),
    @NamedQuery(name = "CancelLoadingService.findByContType", query = "SELECT c FROM CancelLoadingService c WHERE c.masterContainerType.contType = :contType"),
    @NamedQuery(name = "CancelLoadingService.findByCommodityCode", query = "SELECT c FROM CancelLoadingService c WHERE c.masterCommodity.commodityCode = :commodityCode"),
    @NamedQuery(name = "CancelLoadingService.findByContStatus", query = "SELECT c FROM CancelLoadingService c WHERE c.contStatus = :contStatus"),
    @NamedQuery(name = "CancelLoadingService.findByContGross", query = "SELECT c FROM CancelLoadingService c WHERE c.contGross = :contGross"),
    @NamedQuery(name = "CancelLoadingService.findBySealNo", query = "SELECT c FROM CancelLoadingService c WHERE c.sealNo = :sealNo"),
    @NamedQuery(name = "CancelLoadingService.findByOverSize", query = "SELECT c FROM CancelLoadingService c WHERE c.overSize = :overSize"),
    @NamedQuery(name = "CancelLoadingService.findByDg", query = "SELECT c FROM CancelLoadingService c WHERE c.dg = :dg"),
    @NamedQuery(name = "CancelLoadingService.findByDgLabel", query = "SELECT c FROM CancelLoadingService c WHERE c.dgLabel = :dgLabel"),
    @NamedQuery(name = "CancelLoadingService.findByPosition", query = "SELECT c FROM CancelLoadingService c WHERE c.position = :position"),
    @NamedQuery(name = "CancelLoadingService.findByPullOut", query = "SELECT c FROM CancelLoadingService c WHERE c.pullOut = :pullOut"),
    @NamedQuery(name = "CancelLoadingService.findByCategory", query = "SELECT c FROM CancelLoadingService c WHERE c.category = :category"),
    @NamedQuery(name = "CancelLoadingService.findByCharge", query = "SELECT c FROM CancelLoadingService c WHERE c.charge = :charge")})
@NamedNativeQueries({

/* 
* Updated by SRACH
* Mon 27 Oct 2014 05:47:09 PM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/

/*
    @NamedNativeQuery(name = "CancelLoadingService.Native.findCancelLoadingServiceByNoreg", query = "SELECT r.cont_no,r.cont_size,m.type_in_general as name,r.cont_status,r.seal_no,r.cont_gross,change(r.pull_out)as pull_out,r.category,r.charge,r.job_slip,c.symbol,c.language,c.country,r.status,r.bl_no from cancel_loading_service r,m_container_type m,m_currency c WHERE r.curr_code=c.curr_code AND r.cont_type=m.cont_type AND r.no_reg=? ORDER BY r.job_slip DESC"),
*/
    @NamedNativeQuery(name = "CancelLoadingService.Native.findCancelLoadingServiceByNoreg", query =     
"SELECT r.cont_no, " 
+"  r.cont_size, " 
+"  m.type_in_general AS name, " 
+"  r.cont_status, " 
+"  r.seal_no, " 
+"  r.cont_gross, " 
+"  DECODE(r.pull_out, 1, 'Yes', 'No') AS pull_out, " 
+"  r.category, " 
+"  r.charge, " 
+"  r.job_slip, " 
+"  c.symbol, " 
+"  c.language, " 
+"  c.country, " 
+"  r.status, " 
+"  r.bl_no " 
+"FROM cancel_loading_service r, " 
+"  m_container_type m, " 
+"  m_currency c " 
+"WHERE r.curr_code=c.curr_code " 
+"AND r.cont_type  =m.cont_type " 
+"AND r.no_reg     = ? " 
+"ORDER BY r.job_slip DESC"),    


/* 
* Updated by SRACH
* Tue 28 Oct 2014 08:55:58 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/

/*
    @NamedNativeQuery(name = "CancelLoadingService.Native.findPerhitungan", query = "SELECT cls.job_slip, cls.bl_no, cls.cont_no, cls.cont_size, m.type_in_general, cls.cont_status, cls.seal_no, cls.dg, cls.dg_label, cls.over_size, cls.cont_gross, cls.pull_out, cls.category, cls.status, cls.position, c.default_fraction_digits, c.language, c.country, cls.placement_date, nvl(pp.masa_1) masa_1, nvl(pp.masa_2) masa_2, nvl(pp.charge_m1) charge_m1, nvl(pp.charge_m2) charge_m2, nvl(pp.total_charge) penumpukan_charge, nvl(cls.charge) cancel_charge, nvl(ppg.total_charge) pass_gate_charge, cls.new_ppkb, nvl(pub.charge) upah_buruh_charge, cls.is_transhipment "
                                                                                     + "FROM cancel_loading_service cls "
                                                                                             + "JOIN m_container_type m ON (cls.cont_type = m.cont_type) "
                                                                                             + "JOIN m_currency c ON (cls.curr_code = c.curr_code) "
                                                                                             + "LEFT JOIN perhitungan_penumpukan pp ON (pp.no_reg = cls.no_reg AND pp.cont_no = cls.cont_no) "
                                                                                             + "LEFT JOIN perhitungan_pass_gate ppg ON (ppg.job_slip = cls.job_slip) "
                                                                                             + "LEFT JOIN perhitungan_upah_buruh pub ON (pub.jobslip = cls.job_slip) "
                                                                                     + "WHERE cls.no_reg = ? "
                                                                                     + "ORDER BY cls.job_slip DESC;"),
*/                                                                                     

    @NamedNativeQuery(name = "CancelLoadingService.Native.findPerhitungan", query = 
"SELECT cls.job_slip, " 
+"  cls.bl_no, " 
+"  cls.cont_no, " 
+"  cls.cont_size, " 
+"  m.type_in_general, " 
+"  cls.cont_status, " 
+"  cls.seal_no, " 
+"  cls.dg, " 
+"  cls.dg_label, " 
+"  cls.over_size, " 
+"  cls.cont_gross, " 
+"  cls.pull_out, " 
+"  cls.category, " 
+"  cls.status, " 
+"  cls.position, " 
+"  c.default_fraction_digits, " 
+"  c.language, " 
+"  c.country, " 
+"  cls.placement_date, " 
+"  NVL(pp.masa_1,0) masa_1, " 
+"  NVL(pp.masa_2,0) masa_2, " 
+"  NVL(pp.charge_m1,0) charge_m1, " 
+"  NVL(pp.charge_m2,0) charge_m2, " 
+"  NVL(pp.total_charge,0) penumpukan_charge, " 
+"  NVL(cls.charge,0) cancel_charge, " 
+"  NVL(ppg.total_charge,0) pass_gate_charge, " 
+"  cls.new_ppkb, " 
+"  NVL(pub.charge,0) upah_buruh_charge, " 
+"  cls.is_transhipment " 
+"FROM cancel_loading_service cls " 
+"JOIN m_container_type m " 
+"ON (cls.cont_type = m.cont_type) " 
+"JOIN m_currency c " 
+"ON (cls.curr_code = c.curr_code) " 
+"LEFT JOIN perhitungan_penumpukan pp " 
+"ON (pp.no_reg  = cls.no_reg " 
+"AND pp.cont_no = cls.cont_no) " 
+"LEFT JOIN perhitungan_pass_gate ppg " 
+"ON (ppg.job_slip = cls.job_slip) " 
+"LEFT JOIN perhitungan_upah_buruh pub " 
+"ON (pub.jobslip  = cls.job_slip) " 
+"WHERE cls.no_reg = ? " 
+"ORDER BY cls.job_slip DESC"),                                                                                     
    //@NamedNativeQuery(name = "CancelLoadingService.Native.findCancelLoadingServiceId", query = "Select e.job_slip FROM cancel_loading_service s where e.no_ppkb=s.no_ppkb AND e.no_reg=s.no_reg AND e.cont_no=s.cont_no AND e.no_ppkb=? AND e.no_reg=? AND e.cont_no=?"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 08:55:58 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/

/*
    @NamedNativeQuery(name = "CancelLoadingService.Native.generateId", query = "SELECT MAX(substring(job_slip,7,5))FROM cancel_loading_service WHERE substring(job_slip,3,4) = ?"),
*/
    
    @NamedNativeQuery(name = "CancelLoadingService.Native.generateId", query = 
"SELECT MAX(SUBSTR(job_slip,7,5)) " 
+"FROM cancel_loading_service " 
+"WHERE SUBSTR(job_slip,3,4) = ?"),    

    @NamedNativeQuery(name = "CancelLoadingService.Native.findStuffAbleContainers", query = "SELECT cls.job_slip, cls.bl_no, cls.cont_no, cls.cont_size, mct.type_in_general, cls.cont_status, cls.cont_gross "
                                                                                            + "FROM cancel_loading_service cls "
                                                                                                    + "JOIN registration rc ON (cls.no_reg = rc.no_reg) "
                                                                                                    + "JOIN m_container_type mct ON (cls.cont_type = mct.cont_type) "
                                                                                                    + "LEFT JOIN stuffing_service ss ON (cls.cont_no = ss.cont_no) "
                                                                                                            + "LEFT JOIN registration r ON (r.no_reg = ss.no_reg AND r.no_ppkb = cls.no_ppkb) "
                                                                                            + "WHERE ss.cont_no IS NULL AND cls.category = 3 AND cls.cont_status = 'MTY' AND cls.no_ppkb = ? AND rc.status_service IN ('confirm','approve')"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 09:04:54 AM WIT  
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*                                                                                            
    @NamedNativeQuery(name = "CancelLoadingService.Native.findByChangeDestination", query = "SELECT scd.job_slip,scd.bl_no,scd.cont_no,mct.type_in_general as name,scd.cont_gross,mc.name,scd.seal_no, scd.cont_size,scd.cont_status,scd.block, scd.y_slot, scd.y_row, scd.y_tier FROM cancel_loading_service AS scd, m_container_type AS mct,m_commodity as mc WHERE scd.cont_type = mct.cont_type AND scd.commodity_code=mc.commodity_code AND scd.is_discharge=TRUE AND (scd.category=2 or scd.category=4 ) AND scd.change_destination=FALSE AND scd.no_ppkb=?"),
*/
    @NamedNativeQuery(name = "CancelLoadingService.Native.findByChangeDestination", query =     
"SELECT scd.job_slip, " 
+"  scd.bl_no, " 
+"  scd.cont_no, " 
+"  mct.type_in_general AS name, " 
+"  scd.cont_gross, " 
+"  mc.name, " 
+"  scd.seal_no, " 
+"  scd.cont_size, " 
+"  scd.cont_status, " 
+"  scd.block, " 
+"  scd.y_slot, " 
+"  scd.y_row, " 
+"  scd.y_tier " 
+"FROM cancel_loading_service scd, " 
+"  m_container_type mct, " 
+"  m_commodity mc " 
+"WHERE scd.cont_type        = mct.cont_type " 
+"AND scd.commodity_code     = mc.commodity_code " 
+"AND scd.is_discharge       = 1 " 
+"AND (scd.category          = 2 " 
+"OR scd.category            = 4 ) " 
+"AND scd.change_destination = 0 " 
+"AND scd.no_ppkb            = ?"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 09:06:32 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/    
/*
    @NamedNativeQuery(name = "CancelLoadingService.Native.findCancelLoadingServiceByDestination", query = "SELECT r.job_slip, r.no_ppkb FROM cancel_loading_service r WHERE r.change_destination=TRUE AND r.no_ppkb = ? AND r.cont_no=?"),
*/    
    @NamedNativeQuery(name = "CancelLoadingService.Native.findCancelLoadingServiceByDestination", query =     
"SELECT r.job_slip, " 
+"  r.no_ppkb " 
+"FROM cancel_loading_service r " 
+"WHERE r.change_destination= 1 " 
+"AND r.no_ppkb             = ? " 
+"AND r.cont_no             = ?"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 09:13:39 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/    
/*    
    @NamedNativeQuery(name = "CancelLoadingService.Native.findLiftableOffByNoPpkb", query = "SELECT scd.job_slip, scd.bl_no, scd.cont_no, mct.name, scd.cont_gross, mc.name, scd.seal_no, scd.cont_size, scd.cont_status, scd.block, scd.y_slot, scd.y_row, scd.y_tier, scd.category "
                                                                                                        + "FROM cancel_loading_service AS scd "
                                                                                                                + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                                                + "JOIN m_commodity as mc ON (scd.commodity_code=mc.commodity_code) "
                                                                                                        + "WHERE scd.no_ppkb=? AND scd.is_discharge = TRUE AND scd.truck_losing = FALSE AND scd.posisi = '" + CancelLoadingService.AT_HAULAGE_TRUCK + "' AND (scd.category=4 or scd.category=5)"),
*/

    @NamedNativeQuery(name = "CancelLoadingService.Native.findLiftableOffByNoPpkb", query = 
"SELECT scd.job_slip, " 
+"  scd.bl_no, " 
+"  scd.cont_no, " 
+"  mct.name, " 
+"  scd.cont_gross, " 
+"  mc.name, " 
+"  scd.seal_no, " 
+"  scd.cont_size, " 
+"  scd.cont_status, " 
+"  scd.block, " 
+"  scd.y_slot, " 
+"  scd.y_row, " 
+"  scd.y_tier, " 
+"  scd.category " 
+"FROM cancel_loading_service scd " 
+"JOIN m_container_type mct " 
+"ON (scd.cont_type = mct.cont_type) " 
+"JOIN m_commodity mc " 
+"ON (scd.commodity_code=mc.commodity_code) " 
+"WHERE scd.no_ppkb     =? " 
+"AND scd.is_discharge  = 1 " 
+"AND scd.truck_losing  = 0 " 
+"AND scd.posisi        = '"+ CancelLoadingService.AT_HAULAGE_TRUCK +"' " 
+"AND (scd.category     =4 "
+"OR scd.category       =5)"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 09:18:22 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/    
/*                                                                                                          
    @NamedNativeQuery(name = "CancelLoadingService.Native.findHasLiftedOffByNoPpkb", query = "SELECT scd.job_slip, scd.bl_no, scd.cont_no, mct.name, scd.cont_gross, mc.name, scd.seal_no, scd.cont_size, scd.cont_status, scd.block, scd.y_slot, scd.y_row, scd.y_tier, scd.category, false "
                                                                                                        + "FROM cancel_loading_service AS scd "
                                                                                                                + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                                                + "JOIN m_commodity as mc ON (scd.commodity_code=mc.commodity_code) "
                                                                                                        + "WHERE scd.no_ppkb=? AND scd.is_discharge = TRUE AND scd.truck_losing = FALSE AND scd.posisi = '" + CancelLoadingService.AT_CY + "' AND (scd.category=4 or scd.category=5)"),
*/                                                                                                        
    @NamedNativeQuery(name = "CancelLoadingService.Native.findHasLiftedOffByNoPpkb", query = 
"SELECT scd.job_slip, " 
+"  scd.bl_no, " 
+"  scd.cont_no, " 
+"  mct.name, " 
+"  scd.cont_gross, " 
+"  mc.name, " 
+"  scd.seal_no, " 
+"  scd.cont_size, " 
+"  scd.cont_status, " 
+"  scd.block, " 
+"  scd.y_slot, " 
+"  scd.y_row, " 
+"  scd.y_tier, " 
+"  scd.category, " 
+"  0 " 
+"FROM cancel_loading_service scd " 
+"JOIN m_container_type mct " 
+"ON (scd.cont_type = mct.cont_type) " 
+"JOIN m_commodity mc " 
+"ON (scd.commodity_code=mc.commodity_code) " 
+"WHERE scd.no_ppkb     = ? " 
+"AND scd.is_discharge  = 1 " 
+"AND scd.truck_losing  = 0"
+"AND scd.posisi = '" + CancelLoadingService.AT_CY +"'" 
+"AND (scd.category=4 or scd.category=5)"),         

/* 
* Updated by SRACH
* Tue 28 Oct 2014 09:22:42 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/    
/*                                                                                               
    @NamedNativeQuery(name = "CancelLoadingService.Native.findDischargableContainerByNoPpkb", query = "SELECT scd.job_slip, scd.bl_no, scd.cont_no, mct.name, scd.cont_gross, mc.name, scd.seal_no, scd.cont_size, scd.cont_status, scd.block, scd.y_slot, scd.y_row, scd.y_tier, scd.category "
                                                                                                    + "FROM cancel_loading_service AS scd "
                                                                                                            + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                                            + "JOIN m_commodity as mc ON (scd.commodity_code=mc.commodity_code) "
                                                                                                    + "WHERE scd.no_ppkb=? AND scd.is_discharge = FALSE AND scd.posisi = '" + CancelLoadingService.AT_VESSEL + "' AND (scd.category=4 or scd.category=5) AND scd.counter_print > 0"),
*/
    @NamedNativeQuery(name = "CancelLoadingService.Native.findDischargableContainerByNoPpkb", query = 
"SELECT scd.job_slip, " 
+"  scd.bl_no, " 
+"  scd.cont_no, " 
+"  mct.name, " 
+"  scd.cont_gross, " 
+"  mc.name, " 
+"  scd.seal_no, " 
+"  scd.cont_size, " 
+"  scd.cont_status, " 
+"  scd.block, " 
+"  scd.y_slot, " 
+"  scd.y_row, " 
+"  scd.y_tier, " 
+"  scd.category " 
+"FROM cancel_loading_service scd " 
+"JOIN m_container_type mct " 
+"ON (scd.cont_type = mct.cont_type) " 
+"JOIN m_commodity mc " 
+"ON (scd.commodity_code=mc.commodity_code) " 
+"WHERE scd.no_ppkb     = ? " 
+"AND scd.is_discharge  = 0"
+"AND scd.posisi        = '" + CancelLoadingService.AT_VESSEL +"'"
+"AND (scd.category =4 OR scd.category =5) AND scd.counter_print > 0"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 09:26:51 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*                                                                                                    
    @NamedNativeQuery(name = "CancelLoadingService.Native.findHasDischargedContainerByNoPpkb", query = "SELECT scd.job_slip, scd.bl_no, scd.cont_no, mct.type_in_general as name, scd.cont_gross, mc.name, scd.seal_no, scd.cont_size, scd.cont_status, scd.vessel_bay, scd.vessel_row, scd.vessel_tier "
                                                                                                            + "FROM cancel_loading_service AS scd "
                                                                                                                    + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                                                    + "JOIN m_commodity as mc ON (scd.commodity_code = mc.commodity_code) "
                                                                                                            + "WHERE scd.no_ppkb = ? AND scd.is_discharge = TRUE AND scd.posisi = '" + CancelLoadingService.AT_HAULAGE_TRUCK + "' AND (scd.category = 4 or scd.category = 5)"),
*/
    @NamedNativeQuery(name = "CancelLoadingService.Native.findHasDischargedContainerByNoPpkb", query = 
"SELECT scd.job_slip, " 
+"  scd.bl_no, " 
+"  scd.cont_no, " 
+"  mct.type_in_general AS name, " 
+"  scd.cont_gross, " 
+"  mc.name, " 
+"  scd.seal_no, " 
+"  scd.cont_size, " 
+"  scd.cont_status, " 
+"  scd.vessel_bay, " 
+"  scd.vessel_row, " 
+"  scd.vessel_tier " 
+"FROM cancel_loading_service scd " 
+"JOIN m_container_type mct " 
+"ON (scd.cont_type = mct.cont_type) " 
+"JOIN m_commodity mc " 
+"ON (scd.commodity_code = mc.commodity_code) " 
+"WHERE scd.no_ppkb      = ? " 
+"AND scd.is_discharge   = 1 "
+"AND scd.posisi         = '" + CancelLoadingService.AT_HAULAGE_TRUCK + "' "
+"AND (scd.category = 4 OR scd.category = 5)"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 09:30:12 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*
    @NamedNativeQuery(name = "CancelLoadingService.Native.findForDelivery", query = "SELECT scd.job_slip,scd.bl_no,scd.cont_no,mct.type_in_general as name,scd.cont_gross,mc.name,scd.seal_no, scd.cont_size,scd.cont_status,scd.block, scd.y_slot, scd.y_row, scd.y_tier,scd.category,scd.posisi FROM cancel_loading_service AS scd, m_container_type AS mct,m_commodity as mc WHERE scd.cont_type = mct.cont_type AND scd.commodity_code=mc.commodity_code AND scd.is_discharge=TRUE AND (scd.category=3 or scd.category=5) AND (scd.posisi='" + CancelLoadingService.AT_OUTSIDE + "' or scd.posisi='" + CancelLoadingService.AT_CY + "') AND scd.no_ppkb=?"),
*/
    @NamedNativeQuery(name = "CancelLoadingService.Native.findForDelivery", query =     
"SELECT scd.job_slip, " 
+"  scd.bl_no, " 
+"  scd.cont_no, " 
+"  mct.type_in_general AS name, " 
+"  scd.cont_gross, " 
+"  mc.name, " 
+"  scd.seal_no, " 
+"  scd.cont_size, " 
+"  scd.cont_status, " 
+"  scd.block, " 
+"  scd.y_slot, " 
+"  scd.y_row, " 
+"  scd.y_tier, " 
+"  scd.category, " 
+"  scd.posisi " 
+"FROM cancel_loading_service scd, " 
+"  m_container_type mct, " 
+"  m_commodity mc " 
+"WHERE scd.cont_type   = mct.cont_type " 
+"AND scd.commodity_code=mc.commodity_code " 
+"AND scd.is_discharge  = 1 " 
+"AND (scd.category     = 3 " 
+"OR scd.category       = 5) "
+"AND (scd.posisi       = '" + CancelLoadingService.AT_OUTSIDE + "' " 
+"OR scd.posisi         = '" + CancelLoadingService.AT_CY + "') "
+"AND scd.no_ppkb       = ?"),    

/* 
* Updated by SRACH
* Tue 28 Oct 2014 09:36:54 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*
    @NamedNativeQuery(name = "CancelLoadingService.Native.findCancelableContainerByNoPpkb", query = "SELECT pcl.cont_no "
                                                                                                    + "FROM planning_cont_load pcl "
                                                                                                            + "LEFT JOIN receiving_service AS rs ON (pcl.cont_no=rs.cont_no AND pcl.no_ppkb=rs.no_ppkb) "
                                                                                                            + "LEFT JOIN service_cont_load scl ON (pcl.no_ppkb=scl.no_ppkb AND pcl.cont_no=scl.cont_no) "
                                                                                                            + "LEFT JOIN service_vessel sv ON (sv.no_ppkb=pcl.no_ppkb) "
                                                                                                    + "WHERE pcl.no_ppkb = ? AND pcl.cont_no LIKE ('%'|| ? ||'%') AND pcl.is_shifting IS NOT TRUE AND rs.status_cancel_loading IS NOT TRUE AND pcl.status_cancel_loading IS NOT TRUE AND scl.status_cancel_loading IS NOT TRUE AND sv.departure_time IS NULL "
                                                                                                    + "LIMIT 10;"),
*/                                                                                                    
    @NamedNativeQuery(name = "CancelLoadingService.Native.findCancelableContainerByNoPpkb", query = 
"SELECT cont_no " 
+"FROM " 
+"  (SELECT pcl.cont_no " 
+"  FROM planning_cont_load pcl " 
+"  LEFT JOIN receiving_service rs " 
+"  ON (pcl.cont_no=rs.cont_no " 
+"  AND pcl.no_ppkb=rs.no_ppkb) " 
+"  LEFT JOIN service_cont_load scl " 
+"  ON (pcl.no_ppkb=scl.no_ppkb " 
+"  AND pcl.cont_no=scl.cont_no) " 
+"  LEFT JOIN service_vessel sv " 
+"  ON (sv.no_ppkb    =pcl.no_ppkb) " 
+"  WHERE pcl.no_ppkb = ? " 
+"  AND pcl.cont_no LIKE ('%' " 
+"    || ? " 
+"    ||'%') " 
+"  AND pcl.is_shifting           = 0 " 
+"  AND rs.status_cancel_loading  = 0 " 
+"  AND pcl.status_cancel_loading = 0 " 
+"  AND scl.status_cancel_loading = 0 " 
+"  AND sv.departure_time IS NULL " 
+"  ) " 
+"WHERE rownum <= 10"),                  

/* 
* Updated by SRACH
* Tue 28 Oct 2014 09:36:54 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*                                                                                  
    @NamedNativeQuery(name = "CancelLoadingService.Native.findGateInPassableJobslips", query = "SELECT scd.job_slip "
                                                                                                        + "FROM cancel_loading_service scd  "
                                                                                                                + "LEFT JOIN service_gate_delivery sgd ON (sgd.job_slip=scd.job_slip) "
                                                                                                        + "WHERE sgd.job_slip IS NULL AND scd.job_slip LIKE ('%'|| ? ||'%') AND scd.is_delivery = FALSE AND scd.pull_out = TRUE AND scd.counter_print > 0 AND ((scd.posisi='" + CancelLoadingService.AT_CY + "' AND scd.truck_losing = FALSE AND scd.is_discharge = TRUE AND (scd.category=3 OR scd.category=5)) OR (scd.posisi='" + CancelLoadingService.AT_VESSEL + "' AND scd.category=5 AND scd.is_discharge = FALSE)) ORDER BY substring(scd.job_slip,7,5) DESC "
                                                                                                        + "LIMIT 10"),
*/
    @NamedNativeQuery(name = "CancelLoadingService.Native.findGateInPassableJobslips", query = 
"SELECT job_slip FROM ( "
+"SELECT scd.job_slip " 
+"FROM cancel_loading_service scd " 
+"LEFT JOIN service_gate_delivery sgd " 
+"ON (sgd.job_slip    = scd.job_slip) " 
+"WHERE sgd.job_slip IS NULL " 
+"  --AND scd.job_slip LIKE ('%'|| ? ||'%') " 
+"AND scd.is_delivery   = 0 " 
+"AND scd.pull_out      = 1 " 
+"AND scd.counter_print > 0 "
+"AND ((scd.posisi      = '" + CancelLoadingService.AT_CY + "' "
+"AND scd.truck_losing = 0 AND scd.is_discharge = 1 AND (scd.category =3 OR scd.category =5))"
+"OR (scd.posisi         = '" + CancelLoadingService.AT_VESSEL + "' "
+"AND scd.category =5 AND scd.is_discharge = 0)) ORDER BY SUBSTR(scd.job_slip,7,5) DESC"
+") WHERE ROWNUM <= 10"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 09:57:42 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*                                                                                  
                                                                                                        
    @NamedNativeQuery(name = "CancelLoadingService.Native.findGateInPassableByJobSlip", query = "SELECT scd.job_slip,scd.bl_no,scd.cont_no,mct.name,scd.cont_gross,mc.name,scd.seal_no, scd.cont_size,scd.cont_status,scd.block, scd.y_slot, scd.y_row, scd.y_tier,scd.category,scd.posisi,scd.cont_type,scd.no_ppkb,scd.mlo "
                                                                                                + "FROM cancel_loading_service AS scd "
                                                                                                        + "JOIN m_container_type AS mct ON (scd.cont_type = mct.cont_type) "
                                                                                                        + "JOIN m_commodity as mc ON (scd.commodity_code=mc.commodity_code) "
                                                                                                        + "LEFT JOIN service_gate_delivery sgd ON (sgd.job_slip=scd.job_slip) "
                                                                                                + "WHERE sgd.job_slip IS NULL AND scd.cont_no=? AND scd.pull_out = TRUE AND scd.is_delivery = FALSE AND scd.counter_print > 0 AND ((scd.posisi='" + CancelLoadingService.AT_CY + "' AND scd.truck_losing = FALSE AND scd.is_discharge = TRUE AND (scd.category=3 OR scd.category=5)) OR (scd.posisi='" + CancelLoadingService.AT_VESSEL + "' AND scd.category=5 AND scd.is_discharge = FALSE))"),
*/
    @NamedNativeQuery(name = "CancelLoadingService.Native.findGateInPassableByJobSlip", query = 
"SELECT scd.job_slip, " 
+"  scd.bl_no, " 
+"  scd.cont_no, " 
+"  mct.name, " 
+"  scd.cont_gross, " 
+"  mc.name, " 
+"  scd.seal_no, " 
+"  scd.cont_size, " 
+"  scd.cont_status, " 
+"  scd.block, " 
+"  scd.y_slot, " 
+"  scd.y_row, " 
+"  scd.y_tier, " 
+"  scd.category, " 
+"  scd.posisi, " 
+"  scd.cont_type, " 
+"  scd.no_ppkb, " 
+"  scd.mlo " 
+"FROM cancel_loading_service scd " 
+"JOIN m_container_type mct " 
+"ON (scd.cont_type = mct.cont_type) " 
+"JOIN m_commodity mc " 
+"ON (scd.commodity_code=mc.commodity_code) " 
+"LEFT JOIN service_gate_delivery sgd " 
+"ON (sgd.job_slip      =scd.job_slip) " 
+"WHERE sgd.job_slip   IS NULL " 
+"AND scd.cont_no       = ? " 
+"AND scd.pull_out      = 1 " 
+"AND scd.is_delivery   = 0 " 
+"AND scd.counter_print > 0 "
+"AND ((scd.posisi      ='" + CancelLoadingService.AT_CY + "' "
+"AND scd.truck_losing = 0 AND scd.is_discharge = 1 AND (scd.category =3 OR scd.category =5))"
+"OR (scd.posisi        ='" + CancelLoadingService.AT_VESSEL + "' "
+"AND scd.category =5 AND scd.is_discharge = 0))"),
                        
/* 
* Updated by SRACH
* Tue 28 Oct 2014 10:02:17 AM WIT  
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*
    @NamedNativeQuery(name = "CancelLoadingService.Native.findDeliverableContainersByPpkb", query = "SELECT cls.job_slip, cls.cont_no, cls.cont_size, mct.type_in_general, cls.cont_status, cls.cont_gross, cls.block, cls.y_slot, cls.y_row, cls.y_tier, cls.seal_no, cls.vessel_bay, cls.vessel_row, cls.vessel_tier "
                                                                                                + "FROM cancel_loading_service AS cls "
                                                                                                        + "JOIN m_container_type AS mct ON (cls.cont_type=mct.cont_type) "
                                                                                                        + "JOIN invoice i ON (cls.no_reg = i.no_reg) "
                                                                                                        + "JOIN service_gate_delivery sgd ON (sgd.cont_no=cls.cont_no AND sgd.no_ppkb=cls.no_ppkb) "
                                                                                                + "WHERE cls.no_ppkb = ? AND i.payment_status = 'PAYMENT' AND cls.is_delivery = FALSE AND cls.posisi = '" + CancelLoadingService.AT_CY + "' "
                                                                                                + "ORDER BY cls.job_slip DESC"),
*/                                                                                                
    @NamedNativeQuery(name = "CancelLoadingService.Native.findDeliverableContainersByPpkb", query = 
"SELECT cls.job_slip, " 
+"  cls.cont_no, " 
+"  cls.cont_size, " 
+"  mct.type_in_general, " 
+"  cls.cont_status, " 
+"  cls.cont_gross, " 
+"  cls.block, " 
+"  cls.y_slot, " 
+"  cls.y_row, " 
+"  cls.y_tier, " 
+"  cls.seal_no, " 
+"  cls.vessel_bay, " 
+"  cls.vessel_row, " 
+"  cls.vessel_tier " 
+"FROM cancel_loading_service cls " 
+"JOIN m_container_type mct " 
+"ON (cls.cont_type=mct.cont_type) " 
+"JOIN invoice i " 
+"ON (cls.no_reg = i.no_reg) " 
+"JOIN service_gate_delivery sgd " 
+"ON (sgd.cont_no      =cls.cont_no " 
+"AND sgd.no_ppkb      =cls.no_ppkb) " 
+"WHERE cls.no_ppkb    = ? " 
+"AND i.payment_status = 'PAYMENT' " 
+"AND cls.is_delivery  = 0 "
+"AND cls.posisi       = '" + CancelLoadingService.AT_CY + "' "
+"ORDER BY cls.job_slip DESC"),                                                                                                

/* 
* Updated by SRACH
* Tue 28 Oct 2014 10:17:17 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*    
    //penambahan nama kapal , vo in voy out by ade chelsea tanggal 17 maret 2014
    @NamedNativeQuery(name = "CancelLoadingService.Native.findDeliverableContainersByPpkb2", query = "SELECT cls.job_slip, cls.cont_no, cls.cont_size, mct.type_in_general, cls.cont_status, cls.cont_gross, cls.block, cls.y_slot, cls.y_row, cls.y_tier, cls.seal_no, cls.vessel_bay, cls.vessel_row, cls.vessel_tier, kapal.no_ppkb,kapal.name,kapal.voy_in,kapal.voy_out "
                                                                                                + "FROM cancel_loading_service AS cls "
                                                                                                        + "JOIN m_container_type AS mct ON (cls.cont_type=mct.cont_type) "
                                                                                                        + "JOIN invoice i ON (cls.no_reg = i.no_reg) "
                                                                                                        + "JOIN service_gate_delivery sgd ON (sgd.cont_no=cls.cont_no AND sgd.no_ppkb=cls.no_ppkb) "
            + "JOIN (	SELECT p.no_ppkb, mv.name, pp.voy_in, pp.voy_out "
+ "FROM planning_vessel p  "
+ "JOIN preservice_vessel pp ON (p.booking_code=pp.booking_code)  "
+ "JOIN m_vessel mv ON (pp.vessel_code = mv.vessel_code)  "
+ "WHERE p.status IN ('Approved', 'Servicing', 'ReadyService', 'Served') ) kapal on cls.no_ppkb=kapal.no_ppkb " 
                                                                                                + "WHERE i.payment_status = 'PAYMENT' AND cls.is_delivery = FALSE AND cls.posisi = '" + CancelLoadingService.AT_CY + "' "
                                                                                                + "ORDER BY cls.job_slip DESC"),
*/
                                                                                                
    @NamedNativeQuery(name = "CancelLoadingService.Native.findDeliverableContainersByPpkb2", query = 
"SELECT cls.job_slip, " 
+"  cls.cont_no, " 
+"  cls.cont_size, " 
+"  mct.type_in_general, " 
+"  cls.cont_status, " 
+"  cls.cont_gross, " 
+"  cls.block, " 
+"  cls.y_slot, " 
+"  cls.y_row, " 
+"  cls.y_tier, " 
+"  cls.seal_no, " 
+"  cls.vessel_bay, " 
+"  cls.vessel_row, " 
+"  cls.vessel_tier, " 
+"  kapal.no_ppkb, " 
+"  kapal.name, " 
+"  kapal.voy_in, " 
+"  kapal.voy_out " 
+"FROM cancel_loading_service cls " 
+"JOIN m_container_type mct " 
+"ON (cls.cont_type=mct.cont_type) " 
+"JOIN invoice i " 
+"ON (cls.no_reg = i.no_reg) " 
+"JOIN service_gate_delivery sgd " 
+"ON (sgd.cont_no=cls.cont_no " 
+"AND sgd.no_ppkb=cls.no_ppkb) " 
+"JOIN " 
+"  (SELECT p.no_ppkb, " 
+"    mv.name, " 
+"    pp.voy_in, " 
+"    pp.voy_out " 
+"  FROM planning_vessel p " 
+"  JOIN preservice_vessel pp " 
+"  ON (p.booking_code=pp.booking_code) " 
+"  JOIN m_vessel mv " 
+"  ON (pp.vessel_code    = mv.vessel_code) " 
+"  WHERE p.status       IN ('Approved', 'Servicing', 'ReadyService', 'Served') " 
+"  ) kapal ON cls.no_ppkb=kapal.no_ppkb " 
+"WHERE i.payment_status  = 'PAYMENT' " 
+"AND cls.is_delivery     = 0 " 
+"AND cls.posisi = '" + CancelLoadingService.AT_CY +"' " 
+"ORDER BY cls.job_slip DESC"),                                                                                                
                                                                                                
/* 
* Updated by SRACH
* Tue 28 Oct 2014 10:17:17 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*
    @NamedNativeQuery(name = "CancelLoadingService.Native.findDeliverableContainerByContNo", query = "SELECT cls.job_slip, cls.cont_no, cls.cont_size, mct.type_in_general, cls.cont_status, cls.cont_gross, cls.seal_no, cls.over_size, cls.dg, mcd.name cont_damage, mv.name vessel_name, cls.block, cls.y_slot, cls.y_row, cls.y_tier "
                                                                                                    + "FROM cancel_loading_service AS cls "
                                                                                                            + "JOIN m_container_type AS mct ON (cls.cont_type=mct.cont_type) "
                                                                                                            + "JOIN invoice i ON (cls.no_reg = i.no_reg) "
                                                                                                            + "JOIN service_gate_delivery sgd ON (sgd.cont_no=cls.cont_no AND sgd.no_ppkb=cls.no_ppkb) "
                                                                                                            + "JOIN m_cont_damage mcd ON (cls.cont_damage=mcd.id) "
                                                                                                            + "JOIN planning_vessel pv ON (cls.no_ppkb = pv.no_ppkb) "
                                                                                                                    + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                                                                                                            + "JOIN m_vessel mv ON (mv.vessel_code=prv.vessel_code) "
                                                                                                    + "WHERE cls.cont_no = ? AND i.payment_status = 'PAYMENT' AND cls.is_delivery = FALSE AND cls.posisi = '" + CancelLoadingService.AT_CY + "' "
                                                                                                    + "ORDER BY cls.job_slip DESC"),
*/

    @NamedNativeQuery(name = "CancelLoadingService.Native.findDeliverableContainerByContNo", query = 
"SELECT cls.job_slip, " 
+"  cls.cont_no, " 
+"  cls.cont_size, " 
+"  mct.type_in_general, " 
+"  cls.cont_status, " 
+"  cls.cont_gross, " 
+"  cls.seal_no, " 
+"  cls.over_size, " 
+"  cls.dg, " 
+"  mcd.name cont_damage, " 
+"  mv.name vessel_name, " 
+"  cls.block, " 
+"  cls.y_slot, " 
+"  cls.y_row, " 
+"  cls.y_tier " 
+"FROM cancel_loading_service cls " 
+"JOIN m_container_type mct " 
+"ON (cls.cont_type=mct.cont_type) " 
+"JOIN invoice i " 
+"ON (cls.no_reg = i.no_reg) " 
+"JOIN service_gate_delivery sgd " 
+"ON (sgd.cont_no=cls.cont_no " 
+"AND sgd.no_ppkb=cls.no_ppkb) " 
+"JOIN m_cont_damage mcd " 
+"ON (cls.cont_damage=mcd.id) " 
+"JOIN planning_vessel pv " 
+"ON (cls.no_ppkb = pv.no_ppkb) " 
+"JOIN preservice_vessel prv " 
+"ON (pv.booking_code=prv.booking_code) " 
+"JOIN m_vessel mv " 
+"ON (mv.vessel_code   =prv.vessel_code) " 
+"WHERE cls.cont_no    = ? " 
+"AND i.payment_status = 'PAYMENT' " 
+"AND cls.is_delivery  = 0 " 
+"AND cls.posisi       = '" + CancelLoadingService.AT_CY + "' " 
+"ORDER BY cls.job_slip DESC"),                                                                                                    

/* 
* Updated by SRACH
* Tue 28 Oct 2014 10:29:04 AM WIT  
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*
    @NamedNativeQuery(name = "CancelLoadingService.Native.findDeliveredContainersByPpkb", query = "SELECT cls.job_slip, cls.cont_no, cls.cont_size, mct.type_in_general, cls.cont_status, cls.cont_gross, cls.block, cls.y_slot, cls.y_row, cls.y_tier, cls.seal_no, cls.vessel_bay, cls.vessel_row, cls.vessel_tier, cls.posisi "
                                                                                                    + "FROM cancel_loading_service AS cls "
                                                                                                            + "JOIN m_container_type AS mct ON (cls.cont_type=mct.cont_type) "
                                                                                                            + "JOIN invoice i ON (cls.no_reg = i.no_reg) "
                                                                                                            + "JOIN service_gate_delivery sgd ON (sgd.cont_no=cls.cont_no AND sgd.no_ppkb=cls.no_ppkb) "
                                                                                                    + "WHERE cls.no_ppkb = ? AND i.payment_status = 'PAYMENT' AND cls.is_delivery = TRUE "
                                                                                                    + "ORDER BY cls.job_slip DESC"),
*/                                                                                                    
    @NamedNativeQuery(name = "CancelLoadingService.Native.findDeliveredContainersByPpkb", query = 
"SELECT cls.job_slip, " 
+"  cls.cont_no, " 
+"  cls.cont_size, " 
+"  mct.type_in_general, " 
+"  cls.cont_status, " 
+"  cls.cont_gross, " 
+"  cls.block, " 
+"  cls.y_slot, " 
+"  cls.y_row, " 
+"  cls.y_tier, " 
+"  cls.seal_no, " 
+"  cls.vessel_bay, " 
+"  cls.vessel_row, " 
+"  cls.vessel_tier, " 
+"  cls.posisi " 
+"FROM cancel_loading_service cls " 
+"JOIN m_container_type mct " 
+"ON (cls.cont_type=mct.cont_type) " 
+"JOIN invoice i " 
+"ON (cls.no_reg = i.no_reg) " 
+"JOIN service_gate_delivery sgd " 
+"ON (sgd.cont_no      = cls.cont_no " 
+"AND sgd.no_ppkb      = cls.no_ppkb) " 
+"WHERE cls.no_ppkb    = ? " 
+"AND i.payment_status = 'PAYMENT' " 
+"AND cls.is_delivery  = 1 " 
+"ORDER BY cls.job_slip DESC"),

    @NamedNativeQuery(name = "CancelLoadingService.Native.findByPPKB", query = "SELECT job_slip, no_reg, no_ppkb, cont_no FROM cancel_loading_service WHERE no_ppkb = ?"),
    @NamedNativeQuery(name = "CancelLoadingService.Native.findByPPKBandCont", query = "SELECT job_slip FROM cancel_loading_service WHERE no_ppkb = ? AND cont_no = ? AND change_destination = ?"),
    @NamedNativeQuery(name = "CancelLoadingService.Native.findByListBatalNotaService", query = "select d.job_slip,d.no_reg,d.no_ppkb,d.cont_no from cancel_loading_service d where d.no_ppkb=? AND d.no_reg=?"),
    @NamedNativeQuery(name = "CancelLoadingService.Native.findByPPKBandCont", query = "SELECT job_slip FROM cancel_loading_service WHERE no_ppkb = ? AND cont_no = ? AND change_destination = ?"),
    
/* 
* Updated by SRACH
* Tue 28 Oct 2014 10:29:04 AM WIT  
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*    
    @NamedNativeQuery(name = "CancelLoadingService.Native.findByPpkbAndContMobile", query = "SELECT job_slip, no_ppkb, cont_no, cont_size, position, status FROM cancel_loading_service WHERE no_ppkb = ? AND cont_no = ? AND (category = 4 OR category = 5) AND is_discharge = FALSE"),
*/

    @NamedNativeQuery(name = "CancelLoadingService.Native.findByPpkbAndContMobile", query = 
"SELECT job_slip, " 
+"  no_ppkb, " 
+"  cont_no, " 
+"  cont_size, " 
+"  position, " 
+"  status " 
+"FROM cancel_loading_service " 
+"WHERE no_ppkb    = ? " 
+"AND cont_no      = ? " 
+"AND (category    = 4 " 
+"OR category      = 5) " 
+"AND is_discharge = 0"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 10:29:04 AM WIT  
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*        
    @NamedNativeQuery(name = "CancelLoadingService.Native.findByPpkbAndContMobileDelivery", query = "SELECT job_slip, no_ppkb, cont_no, cont_size, position, status, block, y_slot, y_row, y_tier FROM cancel_loading_service WHERE no_ppkb = ? AND cont_no = ? AND (category = 3 OR category = 5) AND is_discharge = TRUE AND (posisi='" + CancelLoadingService.AT_OUTSIDE + "' or posisi='" + CancelLoadingService.AT_CY + "')"),
*/
    @NamedNativeQuery(name = "CancelLoadingService.Native.findByPpkbAndContMobileDelivery", query = 
"SELECT job_slip, " 
+"  no_ppkb, " 
+"  cont_no, " 
+"  cont_size, " 
+"  position, " 
+"  status, " 
+"  block, " 
+"  y_slot, " 
+"  y_row, " 
+"  y_tier " 
+"FROM cancel_loading_service " 
+"WHERE no_ppkb    = ? " 
+"AND cont_no      = ? " 
+"AND (category    = 3 " 
+"OR category      = 5) " 
+"AND is_discharge = 1 " 
+"AND (posisi      ='" + CancelLoadingService.AT_OUTSIDE + "' " 
+"OR posisi        ='" + CancelLoadingService.AT_CY + "')"),    
    
    @NamedNativeQuery(name = "CancelLoadingService.Native.findByNoregValidasiPrint", query = "select ds.job_slip from cancel_loading_service ds where ds.counter_print >=1 AND ds.no_reg=?"),

/* 
* Updated by SRACH
* Tue 28 Oct 2014 10:39:50 AM WIT 
* Update Notes: Upgrade to Oracle 11g SQL Compliance
*/
/*    
    @NamedNativeQuery(name = "CancelLoadingService.Native.findCashierCancelLoading", query = "SELECT cls.job_slip,inv.no_invoice,inv.payment_date,cls.created_by,m.name as consignee,r.pengurus_do,cls.status,cls.posisi "
                                                                                                + "FROM cancel_loading_service cls "
                                                                                                + "LEFT JOIN invoice inv ON inv.no_reg::TEXT = cls.no_reg::TEXT "
                                                                                                + "LEFT JOIN registration r ON r.no_reg = cls.no_reg "
                                                                                                + "LEFT JOIN m_customer m ON m.cust_code = r.cust_code "
                                                                                                + "WHERE cls.no_ppkb = ? AND cls.cont_no= ? "
*/
    @NamedNativeQuery(name = "CancelLoadingService.Native.findCashierCancelLoading", query = 
"SELECT cls.job_slip, " 
+"  inv.no_invoice, " 
+"  inv.payment_date, " 
+"  cls.created_by, " 
+"  m.name AS consignee, " 
+"  r.pengurus_do, " 
+"  cls.status, " 
+"  cls.posisi " 
+"FROM cancel_loading_service cls " 
+"LEFT JOIN invoice inv " 
+"ON inv.no_reg = cls.no_reg " 
+"LEFT JOIN registration r " 
+"ON r.no_reg = cls.no_reg " 
+"LEFT JOIN m_customer m " 
+"ON m.cust_code    = r.cust_code " 
+"WHERE cls.no_ppkb = ? " 
+"AND cls.cont_no   = ?")})

public class CancelLoadingService implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private String twentyOneFeet = "FALSE";
    public static final String AT_OUTSIDE = "01";
    public static final String AT_CY = "02";
    public static final String AT_HAULAGE_TRUCK = "03";
    public static final String AT_VESSEL = "04";
    public static final String AT_MOVEMENT = "MV";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "job_slip", length = 30)
    private String jobSlip;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "cont_size")
    private Short contSize;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type", nullable= false)
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code", nullable=false)
    @ManyToOne
    private MasterCommodity masterCommodity;
    @Column(name = "cont_status", length = 5)
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
    @Column(name = "position", length = 10)
    private String position;
    @Column(name = "pull_out")
    private String pullOut;
    @Column(name = "category")
    private Short category;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "crane")
    private String crane;
    @Column(name = "curr_code", length = 5)
    private String currCode;
    @Column(name = "status", length = 50)
    private String Status;
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
    @Column(name = "counter_print")
    private Integer counterPrint = 0;
    @Column(name = "y_slot")
    private Short ySlot;
    @Basic(optional = false)
    @Column(name = "y_row")
    private Short yRow;
    @Basic(optional = false)
    @Column(name = "y_tier")
    private Short yTier;
    @Column(name = "is_discharge")
    private String isDischarge;
    @Column(name = "change_destination")
    private String changeDestination;
    @Column(name = "truck_losing")
    private String truckLosing;
    @Column(name = "posisi", length = 5)
    private String posisi;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @JoinColumn(name = "disch_port_code", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort dischargePort;
    @Basic(optional = false)
    @Column(name = "is_export", nullable = false)
    private String isExport;
    @Basic(optional = false)
    @Column(name = "has_relocated", nullable = false)
    private String hasRelocated = "FALSE";
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterActivity masterActivity;
    @Basic(optional = false)
    @Column(name = "is_transhipment", nullable = false)
    private String isTranshipment = "FALSE";
    @Column(name = "vessel_bay")
    private Short vesselBay;
    @Column(name = "vessel_row")
    private Short vesselRow;
    @Column(name = "vessel_tier")
    private Short vesselTier;
    @Basic(optional = false)
    @Column(name = "is_delivery", nullable = false)
    private String isDelivery = "FALSE";
    @JoinColumn(name = "cont_damage", referencedColumnName = "id")
    @ManyToOne
    private MasterContDamage masterContDamage;
    @Basic(optional = false)
    @Column(name = "no_stacking", nullable = false)
    private String noStacking = "FALSE";
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration registration;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "new_ppkb", referencedColumnName = "no_ppkb", nullable = false)
    @ManyToOne(optional = false)
    private PlanningVessel newPlanningVessel;
    @JoinColumn(name = "block", referencedColumnName = "block", nullable = false)
    @ManyToOne(optional = false)
    private MasterYard masterYard;

    public CancelLoadingService() {
    }

    public CancelLoadingService(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
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

    public Integer getContTeus() {
        if (contSize != null) {
            return (int) Math.ceil((int) (contSize.doubleValue()/ (double) 20));
        }

        return null;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPullOut() {
        return pullOut;
    }

    public void setPullOut(String pullOut) {
        this.pullOut = pullOut;
    }

    public Short getCategory() {
        return category;
    }

    public void setCategory(Short category) {
        this.category = category;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    /**
     * @return the crane
     */
    public String getCrane() {
        return crane;
    }

    /**
     * @param crane the crane to set
     */
    public void setCrane(String crane) {
        this.crane = crane;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
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

    public String getIsDischarge() {
        return isDischarge;
    }

    public void setIsDischarge(String isDischarge) {
        this.isDischarge = isDischarge;
    }

    public Short getyRow() {
        return yRow;
    }

    public void setyRow(Short yRow) {
        this.yRow = yRow;
    }

    public Short getySlot() {
        return ySlot;
    }

    public void setySlot(Short ySlot) {
        this.ySlot = ySlot;
    }

    public Short getyTier() {
        return yTier;
    }

    public void setyTier(Short yTier) {
        this.yTier = yTier;
    }

    public String getChangeDestination() {
        return changeDestination;
    }

    public void setChangeDestination(String changeDestination) {
        this.changeDestination = changeDestination;
    }

    public String getTruckLosing() {
        return truckLosing;
    }

    public void setTruckLosing(String truckLosing) {
        this.truckLosing = truckLosing;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
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

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    public MasterPort getDischargePort() {
        return dischargePort;
    }

    public void setDischargePort(MasterPort dischargePort) {
        this.dischargePort = dischargePort;
    }

    public String getHasRelocated() {
        return hasRelocated;
    }

    public void setHasRelocated(String hasRelocated) {
        this.hasRelocated = hasRelocated;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }

    public String getIsTranshipment() {
        return isTranshipment;
    }

    public void setIsTranshipment(String isTranshipment) {
        this.isTranshipment = isTranshipment;
    }

    public Short getVesselBay() {
        return vesselBay;
    }

    public void setVesselBay(Short vesselBay) {
        this.vesselBay = vesselBay;
    }

    public Short getVesselRow() {
        return vesselRow;
    }

    public void setVesselRow(Short vesselRow) {
        this.vesselRow = vesselRow;
    }

    public Short getVesselTier() {
        return vesselTier;
    }

    public void setVesselTier(Short vesselTier) {
        this.vesselTier = vesselTier;
    }

    public String getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(String isDelivery) {
        this.isDelivery = isDelivery;
    }

    public MasterContDamage getMasterContDamage() {
        return masterContDamage;
    }

    public void setMasterContDamage(MasterContDamage masterContDamage) {
        this.masterContDamage = masterContDamage;
    }

    public String getNoStacking() {
        return noStacking;
    }

    public void setNoStacking(String noStacking) {
        this.noStacking = noStacking;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
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

    public PlanningVessel getNewPlanningVessel() {
        return newPlanningVessel;
    }

    public void setNewPlanningVessel(PlanningVessel newPlanningVessel) {
        this.newPlanningVessel = newPlanningVessel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobSlip != null ? jobSlip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CancelLoadingService)) {
            return false;
        }
        CancelLoadingService other = (CancelLoadingService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.CancelLoadingService[id=" + jobSlip + "]";
    }

    public String getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(String twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
