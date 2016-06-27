/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterContainerTypeInGeneral;
import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
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
@Table(name = "recap_handling_discharge")
@NamedQueries({
    @NamedQuery(name = "RecapHandlingDischarge.findAll", query = "SELECT r FROM RecapHandlingDischarge r"),
    @NamedQuery(name = "RecapHandlingDischarge.findById", query = "SELECT r FROM RecapHandlingDischarge r WHERE r.id = :id"),
    @NamedQuery(name = "RecapHandlingDischarge.findByNoPpkb", query = "SELECT r FROM RecapHandlingDischarge r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapHandlingDischarge.deleteByNoPpkb", query = "DELETE FROM RecapHandlingDischarge r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapHandlingDischarge.findByActivityCode", query = "SELECT r FROM RecapHandlingDischarge r WHERE r.activityCode = :activityCode"),
    @NamedQuery(name = "RecapHandlingDischarge.findByAmount", query = "SELECT r FROM RecapHandlingDischarge r WHERE r.amount = :amount"),
    @NamedQuery(name = "RecapHandlingDischarge.findByCharge", query = "SELECT r FROM RecapHandlingDischarge r WHERE r.charge = :charge"),
    @NamedQuery(name = "RecapHandlingDischarge.findByTotalCharge", query = "SELECT r FROM RecapHandlingDischarge r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapHandlingDischarge.findByActivityCodeAndNoPpkb", query = "SELECT r FROM RecapHandlingDischarge r WHERE r.activityCode = :activityCode AND r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapHandlingDischarge.findByBentuk3Constraint", query = "SELECT r FROM RecapHandlingDischarge r WHERE r.contStatus = :contStatus AND r.crane = :crane AND r.isExportImport = :isExportImport AND r.sling = :sling AND r.containerType.id = :containerType AND r.activityCode = :activityCode AND r.noPpkb = :noPpkb AND r.currCode = :currCode AND r.openDoor = :openDoor AND r.contSize = :contSize AND r.twentyOneFeet = :twentyOneFeet AND r.charge = :charge")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "RecapHandlingDischarge.Native.findByActCode", query = "SELECT id FROM recap_handling_discharge WHERE activity_code = ? AND no_ppkb = ?"),
    @NamedNativeQuery(name = "RecapHandlingDischarge.Native.findVesselHandlingByPPKB", query = "SELECT nvl(SUM(m.amount)) as totpalka, nvl(SUM(m.total_charge)) as chargepalka, "
                + "nvl(d1.amount) as A001, nvl(d5.amount) as A005, (nvl(d1.amount)+nvl(d5.amount)) as jml1, nvl(d2.amount) as A002, nvl(d6.amount) as A006, (nvl(d2.amount)+nvl(d6.amount)) as jml2, (nvl(d1.amount)+nvl(d5.amount)+nvl(d2.amount)+nvl(d6.amount)) as total1, (nvl(d1.total_charge)+nvl(d5.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)) as charge1,"
                + "nvl(d13.amount) as A013, nvl(d17.amount) as A017, (nvl(d13.amount)+nvl(d17.amount)) as jml3, nvl(d14.amount) as A014, nvl(d18.amount) as A018, (nvl(d14.amount)+nvl(d18.amount)) as jml4, (nvl(d13.amount)+nvl(d17.amount)+nvl(d14.amount)+nvl(d18.amount)) as total2, (nvl(d13.total_charge)+nvl(d17.total_charge)+nvl(d14.total_charge)+nvl(d18.total_charge)) as charge2,"
                + "nvl(d9.amount) as A009, nvl(d11.amount) as A011, (nvl(d9.amount)+nvl(d11.amount)) as jml5, nvl(d2.amount) as A2002, nvl(d6.amount) as A2006, (nvl(d2.amount)+nvl(d6.amount)) as jml6,(nvl(d9.amount)+nvl(d11.amount)+nvl(d2.amount)+nvl(d6.amount)) as total3, (nvl(d9.total_charge)+nvl(d11.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)) as charge3,"
                + "nvl(s5.amount) as G005, nvl(s6.amount) as G006, (nvl(s5.amount)+nvl(s6.amount)) as jml7, nvl(s21.amount) as G021, nvl(s22.amount) as G022, (nvl(s21.amount)+nvl(s22.amount)) as jml8,(nvl(s5.amount)+nvl(s6.amount)+nvl(s21.amount)+nvl(s22.amount)) as total4, (nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)) as charge4,"
                + "nvl(s1.amount) as G001, nvl(s2.amount) as G002, (nvl(s1.amount)+nvl(s2.amount)) as jml9, nvl(s17.amount) as G017, nvl(s18.amount) as G018, (nvl(s17.amount)+nvl(s18.amount)) as jml10,(nvl(s1.amount)+nvl(s2.amount)+nvl(s17.amount)+nvl(s18.amount)) as total5, (nvl(s1.total_charge)+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)) as charge5,"
                + "nvl(s13.amount) as G013, nvl(s14.amount) as G014, (nvl(s13.amount)+nvl(s14.amount)) as jml11, (nvl(s13.amount)+nvl(s14.amount)) as total6,(nvl(s13.total_charge)+nvl(s14.total_charge)) as charge6,"
                + "nvl(s9.amount) as G009, nvl(s10.amount) as G010, (nvl(s9.amount)+nvl(s10.amount)) as jml12, (nvl(s9.amount)+nvl(s10.amount)) as total7,(nvl(s9.total_charge)+nvl(s10.total_charge)) as charge7,"
                + "(nvl(d1.total_charge)+nvl(d5.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)+nvl(d13.total_charge)+nvl(d17.total_charge)+nvl(d14.total_charge)+nvl(d18.total_charge)+nvl(d9.total_charge)+nvl(d11.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)+nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)"
                + "+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)+nvl(t6.total_charge)+nvl(SUM(m.total_charge))) as jumlah1, "
                + "nvl(d3.amount) as A003, nvl(d7.amount) as A007, (nvl(d3.amount)+nvl(d7.amount)) as jml13, nvl(d4.amount) as A004, nvl(d8.amount) as A008, (nvl(d4.amount)+nvl(d8.amount)) as jml14, (nvl(d3.amount)+nvl(d7.amount)+nvl(d4.amount)+nvl(d8.amount)) as total8,(nvl(d3.total_charge)+nvl(d7.total_charge)+nvl(d4.total_charge)+nvl(d8.total_charge)) as charge8, "
                + "nvl(d15.amount) as A015, nvl(d19.amount) as A019, (nvl(d15.amount)+nvl(d19.amount)) as jml15, nvl(d16.amount) as A016, nvl(d20.amount) as A020, (nvl(d16.amount)+nvl(d20.amount)) as jml16, (nvl(d15.amount)+nvl(d19.amount)+nvl(d16.amount)+nvl(d20.amount)) as total9,(nvl(d15.total_charge)+nvl(d19.total_charge)+nvl(d16.total_charge)+nvl(d20.total_charge)) as charge9,"
                + "nvl(d10.amount) as A010, nvl(d12.amount) as A012, (nvl(d10.amount)+nvl(d12.amount)) as jml17, nvl(d4.amount) as A2004, nvl(d8.amount) as A2008, (nvl(d4.amount)+nvl(d8.amount)) as jml18, (nvl(d10.amount)+nvl(d12.amount)+nvl(d4.amount)+nvl(d8.amount)) as total10,(nvl(d10.total_charge)+nvl(d12.total_charge)+nvl(d4.total_charge)+nvl(d8.total_charge)) as charge10,"
                + "nvl(s7.amount) as G007, nvl(s8.amount) as G008, (nvl(s7.amount)+nvl(s8.amount)) as jml19, nvl(s23.amount) as G023, nvl(s24.amount) as G024, (nvl(s23.amount)+nvl(s24.amount)) as jml20, (nvl(s7.amount)+nvl(s8.amount)+nvl(s23.amount)+nvl(s24.amount)) as total11,(nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)) as charge11,"
                + "nvl(s3.amount) as G003, nvl(s4.amount) as G004, (nvl(s3.amount)+nvl(s4.amount)) as jml21, nvl(s19.amount) as G019, nvl(s20.amount) as G020, (nvl(s19.amount)+nvl(s20.amount)) as jml22, (nvl(s3.amount)+nvl(s4.amount)+nvl(s19.amount)+nvl(s20.amount)) as total12,(nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)) as charge12,"
                + "nvl(s15.amount) as G015, nvl(s16.amount) as G016, (nvl(s15.amount)+nvl(s16.amount)) as jml23, (nvl(s15.amount)+nvl(s16.amount)) as total13,(nvl(s15.total_charge)+nvl(s16.total_charge)) as charge13,"
                + "nvl(s11.amount) as G011, nvl(s12.amount) as G012, (nvl(s11.amount)+nvl(s12.amount)) as jml24, (nvl(s11.amount)+nvl(s12.amount)) as total14,(nvl(s11.total_charge)+nvl(s12.total_charge)) as charge14,"
                + "(nvl(d3.total_charge)+nvl(d7.total_charge)+nvl(d4.total_charge)+nvl(d8.total_charge)+nvl(d15.total_charge)+nvl(d19.total_charge)+nvl(d16.total_charge)+nvl(d20.total_charge)+nvl(d10.total_charge)+nvl(d12.total_charge)+nvl(d4.total_charge)+nvl(d8.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)"
                + "+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)) as jumlah2,"
                + "(nvl(d1.total_charge)+nvl(d5.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)+nvl(d13.total_charge)+nvl(d17.total_charge)+nvl(d14.total_charge)+nvl(d18.total_charge)+nvl(d9.total_charge)+nvl(d11.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)+nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)"
                + "+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(d3.total_charge)+nvl(d7.total_charge)+nvl(d4.total_charge)+nvl(d8.total_charge)+nvl(d15.total_charge)+nvl(d19.total_charge)+nvl(d16.total_charge)+nvl(d20.total_charge)+nvl(d10.total_charge)+nvl(d12.total_charge)"
                + "+nvl(d4.total_charge)+nvl(d8.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)"
                + "+nvl(t6.total_charge)+nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)+nvl(SUM(m.total_charge))) as jumlah1n2,"
                + "((nvl(d1.total_charge)+nvl(d5.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)+nvl(d13.total_charge)+nvl(d17.total_charge)+nvl(d14.total_charge)+nvl(d18.total_charge)+nvl(d9.total_charge)+nvl(d11.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)+nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)"
                + "+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(d3.total_charge)+nvl(d7.total_charge)+nvl(d4.total_charge)+nvl(d8.total_charge)+nvl(d15.total_charge)+nvl(d19.total_charge)+nvl(d16.total_charge)+nvl(d20.total_charge)+nvl(d10.total_charge)+nvl(d12.total_charge)"
                + "+nvl(d4.total_charge)+nvl(d8.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)"
                + "+nvl(t6.total_charge)+nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)+nvl(SUM(m.total_charge)))*10/100) as ppn,"
                + "((nvl(d1.total_charge)+nvl(d5.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)+nvl(d13.total_charge)+nvl(d17.total_charge)+nvl(d14.total_charge)+nvl(d18.total_charge)+nvl(d9.total_charge)+nvl(d11.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)+nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)"
                + "+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(d3.total_charge)+nvl(d7.total_charge)+nvl(d4.total_charge)+nvl(d8.total_charge)+nvl(d15.total_charge)+nvl(d19.total_charge)+nvl(d16.total_charge)+nvl(d20.total_charge)+nvl(d10.total_charge)+nvl(d12.total_charge)"
                + "+nvl(d4.total_charge)+nvl(d8.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)"
                + "+nvl(t6.total_charge)+nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)+nvl(SUM(m.total_charge)))+((nvl(d1.total_charge)+nvl(d5.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)+nvl(d13.total_charge)+nvl(d17.total_charge)+nvl(d14.total_charge)+nvl(d18.total_charge)+nvl(d9.total_charge)+nvl(d11.total_charge)+nvl(d2.total_charge)+nvl(d6.total_charge)"
                + "+nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(d3.total_charge)+nvl(d7.total_charge)+nvl(d4.total_charge)+nvl(d8.total_charge)+nvl(d15.total_charge)"
                + "+nvl(d19.total_charge)+nvl(d16.total_charge)+nvl(d20.total_charge)+nvl(d10.total_charge)+nvl(d12.total_charge)+nvl(d4.total_charge)+nvl(d8.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)"
                + "+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)+nvl(t6.total_charge)++nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)+nvl(SUM(m.total_charge)))*10/100)) as totalbayar, "
                + "nvl(t1.amount) as H001, nvl(t2.amount) as H002, (nvl(t1.amount)+nvl(t2.amount)) as jml25, nvl(t5.amount) as H005, nvl(t6.amount) as H006, (nvl(t5.amount)+nvl(t6.amount)) as jml26, (nvl(t1.amount)+nvl(t2.amount)+nvl(t5.amount)+nvl(t6.amount)) as total15, (nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)+nvl(t6.total_charge)) as charge15, "
                + "nvl(t3.amount) as H003, nvl(t4.amount) as H004, (nvl(t3.amount)+nvl(t4.amount)) as jml27, nvl(t7.amount) as H007, nvl(t8.amount) as H008, (nvl(t7.amount)+nvl(t8.amount)) as jml28, (nvl(t3.amount)+nvl(t4.amount)+nvl(t7.amount)+nvl(t8.amount)) as total16, (nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)) as charge16, mc.symbol, sv.no_ppkb "
                + "FROM ((((((((((((((((((((((((((((((((((((((((((((((((((((((service_vessel sv LEFT JOIN m_currency mc ON sv.tipe_pelayaran=mc.trade_type)LEFT JOIN recap_hatch_move m ON sv.no_ppkb=m.no_ppkb AND m.operation='DISCHARGE')"
                + "LEFT JOIN recap_handling_discharge d1 ON sv.no_ppkb=d1.no_ppkb AND d1.activity_code='A001')"
                + "LEFT JOIN recap_handling_discharge d2 ON sv.no_ppkb=d2.no_ppkb AND d2.activity_code='A002')"
                + "LEFT JOIN recap_handling_discharge d3 ON sv.no_ppkb=d3.no_ppkb AND d3.activity_code='A003')"
                + "LEFT JOIN recap_handling_discharge d4 ON sv.no_ppkb=d4.no_ppkb AND d4.activity_code='A004')"
                + "LEFT JOIN recap_handling_discharge d5 ON sv.no_ppkb=d5.no_ppkb AND d5.activity_code='A005')"
                + "LEFT JOIN recap_handling_discharge d6 ON sv.no_ppkb=d6.no_ppkb AND d6.activity_code='A006')"
                + "LEFT JOIN recap_handling_discharge d7 ON sv.no_ppkb=d7.no_ppkb AND d7.activity_code='A007')"
                + "LEFT JOIN recap_handling_discharge d8 ON sv.no_ppkb=d8.no_ppkb AND d8.activity_code='A008')"
                + "LEFT JOIN recap_handling_discharge d9 ON sv.no_ppkb=d9.no_ppkb AND d9.activity_code='A009')"
                + "LEFT JOIN recap_handling_discharge d10 ON sv.no_ppkb=d10.no_ppkb AND d10.activity_code='A010')"
                + "LEFT JOIN recap_handling_discharge d11 ON sv.no_ppkb=d11.no_ppkb AND d11.activity_code='A011')"
                + "LEFT JOIN recap_handling_discharge d12 ON sv.no_ppkb=d12.no_ppkb AND d12.activity_code='A012')"
                + "LEFT JOIN recap_handling_discharge d13 ON sv.no_ppkb=d13.no_ppkb AND d13.activity_code='A013')"
                + "LEFT JOIN recap_handling_discharge d14 ON sv.no_ppkb=d14.no_ppkb AND d14.activity_code='A014')"
                + "LEFT JOIN recap_handling_discharge d15 ON sv.no_ppkb=d15.no_ppkb AND d15.activity_code='A015')"
                + "LEFT JOIN recap_handling_discharge d16 ON sv.no_ppkb=d16.no_ppkb AND d16.activity_code='A016')"
                + "LEFT JOIN recap_handling_discharge d17 ON sv.no_ppkb=d17.no_ppkb AND d17.activity_code='A017')"
                + "LEFT JOIN recap_handling_discharge d18 ON sv.no_ppkb=d18.no_ppkb AND d18.activity_code='A018')"
                + "LEFT JOIN recap_handling_discharge d19 ON sv.no_ppkb=d19.no_ppkb AND d19.activity_code='A019')"
                + "LEFT JOIN recap_handling_discharge d20 ON sv.no_ppkb=d20.no_ppkb AND d20.activity_code='A020')"
                + "LEFT JOIN recap_shifting s1 ON sv.no_ppkb=s1.no_ppkb AND s1.operation='DISCHARGE' AND s1.activity_code='G001')"
                + "LEFT JOIN recap_shifting s2 ON sv.no_ppkb=s2.no_ppkb AND s2.operation='DISCHARGE' AND s2.activity_code='G002')"
                + "LEFT JOIN recap_shifting s3 ON sv.no_ppkb=s3.no_ppkb AND s3.operation='DISCHARGE' AND s3.activity_code='G003')"
                + "LEFT JOIN recap_shifting s4 ON sv.no_ppkb=s4.no_ppkb AND s4.operation='DISCHARGE' AND s4.activity_code='G004')"
                + "LEFT JOIN recap_shifting s5 ON sv.no_ppkb=s5.no_ppkb AND s5.operation='DISCHARGE' AND s5.activity_code='G005')"
                + "LEFT JOIN recap_shifting s6 ON sv.no_ppkb=s6.no_ppkb AND s6.operation='DISCHARGE' AND s6.activity_code='G006')"
                + "LEFT JOIN recap_shifting s7 ON sv.no_ppkb=s7.no_ppkb AND s7.operation='DISCHARGE' AND s7.activity_code='G007')"
                + "LEFT JOIN recap_shifting s8 ON sv.no_ppkb=s8.no_ppkb AND s8.operation='DISCHARGE' AND s8.activity_code='G008')"
                + "LEFT JOIN recap_shifting s9 ON sv.no_ppkb=s9.no_ppkb AND s9.operation='DISCHARGE' AND s9.activity_code='G009')"
                + "LEFT JOIN recap_shifting s10 ON sv.no_ppkb=s10.no_ppkb AND s10.operation='DISCHARGE' AND s10.activity_code='G010')"
                + "LEFT JOIN recap_shifting s11 ON sv.no_ppkb=s11.no_ppkb AND s11.operation='DISCHARGE' AND s11.activity_code='G011')"
                + "LEFT JOIN recap_shifting s12 ON sv.no_ppkb=s12.no_ppkb AND s12.operation='DISCHARGE' AND s12.activity_code='G012')"
                + "LEFT JOIN recap_shifting s13 ON sv.no_ppkb=s13.no_ppkb AND s13.operation='DISCHARGE' AND s13.activity_code='G013')"
                + "LEFT JOIN recap_shifting s14 ON sv.no_ppkb=s14.no_ppkb AND s14.operation='DISCHARGE' AND s14.activity_code='G014')"
                + "LEFT JOIN recap_shifting s15 ON sv.no_ppkb=s15.no_ppkb AND s15.operation='DISCHARGE' AND s15.activity_code='G015')"
                + "LEFT JOIN recap_shifting s16 ON sv.no_ppkb=s16.no_ppkb AND s16.operation='DISCHARGE' AND s16.activity_code='G016')"
                + "LEFT JOIN recap_shifting s17 ON sv.no_ppkb=s17.no_ppkb AND s17.operation='DISCHARGE' AND s17.activity_code='G017')"
                + "LEFT JOIN recap_shifting s18 ON sv.no_ppkb=s18.no_ppkb AND s18.operation='DISCHARGE' AND s18.activity_code='G018')"
                + "LEFT JOIN recap_shifting s19 ON sv.no_ppkb=s19.no_ppkb AND s19.operation='DISCHARGE' AND s19.activity_code='G019')"
                + "LEFT JOIN recap_shifting s20 ON sv.no_ppkb=s20.no_ppkb AND s20.operation='DISCHARGE' AND s20.activity_code='G020')"
                + "LEFT JOIN recap_shifting s21 ON sv.no_ppkb=s21.no_ppkb AND s21.operation='DISCHARGE' AND s21.activity_code='G021')"
                + "LEFT JOIN recap_shifting s22 ON sv.no_ppkb=s22.no_ppkb AND s22.operation='DISCHARGE' AND s22.activity_code='G022')"
                + "LEFT JOIN recap_shifting s23 ON sv.no_ppkb=s23.no_ppkb AND s23.operation='DISCHARGE' AND s23.activity_code='G023')"
                + "LEFT JOIN recap_shifting s24 ON sv.no_ppkb=s24.no_ppkb AND s24.operation='DISCHARGE' AND s24.activity_code='G024')"
                + "LEFT JOIN recap_transhipment t1 ON sv.no_ppkb=t1.no_ppkb AND t1.activity_code='H001')"
                + "LEFT JOIN recap_transhipment t2 ON sv.no_ppkb=t2.no_ppkb AND t2.activity_code='H002')"
                + "LEFT JOIN recap_transhipment t3 ON sv.no_ppkb=t3.no_ppkb AND t3.activity_code='H003')"
                + "LEFT JOIN recap_transhipment t4 ON sv.no_ppkb=t4.no_ppkb AND t4.activity_code='H004')"
                + "LEFT JOIN recap_transhipment t5 ON sv.no_ppkb=t5.no_ppkb AND t5.activity_code='H005')"
                + "LEFT JOIN recap_transhipment t6 ON sv.no_ppkb=t6.no_ppkb AND t6.activity_code='H006')"
                + "LEFT JOIN recap_transhipment t7 ON sv.no_ppkb=t7.no_ppkb AND t7.activity_code='H007')"
                + "LEFT JOIN recap_transhipment t8 ON sv.no_ppkb=t8.no_ppkb AND t8.activity_code='H008')"
                + "WHERE sv.no_ppkb=? "
                + "GROUP BY mc.symbol, sv.no_ppkb, d1.amount, d5.amount, d2.amount , d6.amount, d1.total_charge, d5.total_charge, d2.total_charge , d6.total_charge, "
                + "d13.amount, d17.amount, d14.amount, d18.amount, d13.total_charge, d17.total_charge, d14.total_charge, d18.total_charge, "
                + "d9.amount, d11.amount, d9.total_charge, d11.total_charge, "
                + "s5.amount, s6.amount, s21.amount, s22.amount,s5.total_charge, s6.total_charge, s21.total_charge, s22.total_charge,"
                + "s1.amount, s2.amount, s17.amount, s18.amount,s1.total_charge, s2.total_charge, s17.total_charge, s18.total_charge,"
                + "s13.amount, s14.amount, s13.total_charge, s14.total_charge, "
                + "s9.amount, s10.amount,s9.total_charge, s10.total_charge,"
                + "d3.amount, d7.amount, d4.amount, d8.amount, d3.total_charge, d7.total_charge, d4.total_charge, d8.total_charge, "
                + "d15.amount, d19.amount, d16.amount, d20.amount, d15.total_charge, d19.total_charge, d16.total_charge, d20.total_charge, "
                + "d10.amount, d12.amount,d10.total_charge, d12.total_charge,"
                + "s7.amount, s8.amount, s23.amount, s24.amount, s7.total_charge, s8.total_charge, s23.total_charge, s24.total_charge,"
                + "s3.amount, s4.amount, s19.amount, s20.amount, s3.total_charge, s4.total_charge, s19.total_charge, s20.total_charge,"
                + "s15.amount, s16.amount, s15.total_charge, s16.total_charge, "
                + "s11.amount, s12.amount,s11.total_charge, s12.total_charge, "
                + "t1.amount, t2.amount, t5.amount, t6.amount, t1.total_charge, t2.total_charge, t5.total_charge, t6.total_charge, "
                + "t3.amount, t4.amount, t7.amount, t8.amount, t3.total_charge, t4.total_charge, t7.total_charge, t8.total_charge"),
@NamedNativeQuery(name = "RecapHandlingDischarge.Native.findVesselHandlingByPPKBno", query = "SELECT nvl(SUM(m.amount)) as totpalka, nvl(SUM(m.total_charge)) as chargepalka, "
                + "nvl(t1.amount) as H001, nvl(t2.amount) as H002, (nvl(t1.amount)+nvl(t2.amount)) as jml1, nvl(t5.amount) as H005, nvl(t6.amount) as H006, (nvl(t5.amount)+nvl(t6.amount)) as jml2, (nvl(t1.amount)+nvl(t2.amount)+nvl(t5.amount)+nvl(t6.amount)) as total1, (nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)+nvl(t6.total_charge)) as charge1, "
                + "nvl(s5.amount) as G005, nvl(s6.amount) as G006, (nvl(s5.amount)+nvl(s6.amount)) as jml3, nvl(s21.amount) as G021, nvl(s22.amount) as G022, (nvl(s21.amount)+nvl(s22.amount)) as jml4,(nvl(s5.amount)+nvl(s6.amount)+nvl(s21.amount)+nvl(s22.amount)) as total2, (nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)) as charge2,"
                + "nvl(s1.amount) as G001, nvl(s2.amount) as G002, (nvl(s1.amount)+nvl(s2.amount)) as jml5, nvl(s17.amount) as G017, nvl(s18.amount) as G018, (nvl(s17.amount)+nvl(s18.amount)) as jml6,(nvl(s1.amount)+nvl(s2.amount)+nvl(s17.amount)+nvl(s18.amount)) as total3, (nvl(s1.total_charge)+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)) as charge3,"
                + "nvl(s13.amount) as G013, nvl(s14.amount) as G014, (nvl(s13.amount)+nvl(s14.amount)) as jml7, (nvl(s13.amount)+nvl(s14.amount)) as total4,(nvl(s13.total_charge)+nvl(s14.total_charge)) as charge4,"
                + "nvl(s9.amount) as G009, nvl(s10.amount) as G010, (nvl(s9.amount)+nvl(s10.amount)) as jml8, (nvl(s9.amount)+nvl(s10.amount)) as total5,(nvl(s9.total_charge)+nvl(s10.total_charge)) as charge5,"
                + "(nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)+nvl(t6.total_charge)+nvl(SUM(m.total_charge))) as jumlah1, "
                + "nvl(t3.amount) as H003, nvl(t4.amount) as H004, (nvl(t3.amount)+nvl(t4.amount)) as jml9, nvl(t7.amount) as H007, nvl(t8.amount) as H008, (nvl(t7.amount)+nvl(t8.amount)) as jml10, (nvl(t3.amount)+nvl(t4.amount)+nvl(t7.amount)+nvl(t8.amount)) as total6, (nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)) as charge6,"
                + "nvl(s7.amount) as G007, nvl(s8.amount) as G008, (nvl(s7.amount)+nvl(s8.amount)) as jml11, nvl(s23.amount) as G023, nvl(s24.amount) as G024, (nvl(s23.amount)+nvl(s24.amount)) as jml12, (nvl(s7.amount)+nvl(s8.amount)+nvl(s23.amount)+nvl(s24.amount)) as total7,(nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)) as charge7,"
                + "nvl(s3.amount) as G003, nvl(s4.amount) as G004, (nvl(s3.amount)+nvl(s4.amount)) as jml13, nvl(s19.amount) as G019, nvl(s20.amount) as G020, (nvl(s19.amount)+nvl(s20.amount)) as jml14, (nvl(s3.amount)+nvl(s4.amount)+nvl(s19.amount)+nvl(s20.amount)) as total8,(nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)) as charge8,"
                + "nvl(s15.amount) as G015, nvl(s16.amount) as G016, (nvl(s15.amount)+nvl(s16.amount)) as jml15, (nvl(s15.amount)+nvl(s16.amount)) as total9,(nvl(s15.total_charge)+nvl(s16.total_charge)) as charge9,"
                + "nvl(s11.amount) as G011, nvl(s12.amount) as G012, (nvl(s11.amount)+nvl(s12.amount)) as jml16, (nvl(s11.amount)+nvl(s12.amount)) as total10,(nvl(s11.total_charge)+nvl(s12.total_charge)) as charge10,"
                + "(nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)) as jumlah2,"
                + "(nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)"
                + "+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)+nvl(t6.total_charge)+nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)+nvl(SUM(m.total_charge))) as jumlah1n2,"
                + "((nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)"
                + "+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)+nvl(t6.total_charge)+nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)+nvl(SUM(m.total_charge)))*10/100) as ppn,"
                + "((nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)"
                + "+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)+nvl(t6.total_charge)+nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)+nvl(SUM(m.total_charge)))+((nvl(s5.total_charge)+nvl(s6.total_charge)"
                + "+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)"
                + "+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(t1.total_charge)+nvl(t2.total_charge)+nvl(t5.total_charge)+nvl(t6.total_charge)++nvl(t3.total_charge)+nvl(t4.total_charge)+nvl(t7.total_charge)+nvl(t8.total_charge)+nvl(SUM(m.total_charge)))*10/100)) as totalbayar, mc.symbol, sv.no_ppkb "
                + "FROM ((((((((((((((((((((((((((((((((((service_vessel sv LEFT JOIN m_currency mc ON sv.tipe_pelayaran=mc.trade_type)LEFT JOIN recap_hatch_move m ON sv.no_ppkb=m.no_ppkb AND m.operation='DISCHARGE')"
                + "LEFT JOIN recap_shifting s1 ON sv.no_ppkb=s1.no_ppkb AND s1.operation='DISCHARGE' AND s1.activity_code='G001')"
                + "LEFT JOIN recap_shifting s2 ON sv.no_ppkb=s2.no_ppkb AND s2.operation='DISCHARGE' AND s2.activity_code='G002')"
                + "LEFT JOIN recap_shifting s3 ON sv.no_ppkb=s3.no_ppkb AND s3.operation='DISCHARGE' AND s3.activity_code='G003')"
                + "LEFT JOIN recap_shifting s4 ON sv.no_ppkb=s4.no_ppkb AND s4.operation='DISCHARGE' AND s4.activity_code='G004')"
                + "LEFT JOIN recap_shifting s5 ON sv.no_ppkb=s5.no_ppkb AND s5.operation='DISCHARGE' AND s5.activity_code='G005')"
                + "LEFT JOIN recap_shifting s6 ON sv.no_ppkb=s6.no_ppkb AND s6.operation='DISCHARGE' AND s6.activity_code='G006')"
                + "LEFT JOIN recap_shifting s7 ON sv.no_ppkb=s7.no_ppkb AND s7.operation='DISCHARGE' AND s7.activity_code='G007')"
                + "LEFT JOIN recap_shifting s8 ON sv.no_ppkb=s8.no_ppkb AND s8.operation='DISCHARGE' AND s8.activity_code='G008')"
                + "LEFT JOIN recap_shifting s9 ON sv.no_ppkb=s9.no_ppkb AND s9.operation='DISCHARGE' AND s9.activity_code='G009')"
                + "LEFT JOIN recap_shifting s10 ON sv.no_ppkb=s10.no_ppkb AND s10.operation='DISCHARGE' AND s10.activity_code='G010')"
                + "LEFT JOIN recap_shifting s11 ON sv.no_ppkb=s11.no_ppkb AND s11.operation='DISCHARGE' AND s11.activity_code='G011')"
                + "LEFT JOIN recap_shifting s12 ON sv.no_ppkb=s12.no_ppkb AND s12.operation='DISCHARGE' AND s12.activity_code='G012')"
                + "LEFT JOIN recap_shifting s13 ON sv.no_ppkb=s13.no_ppkb AND s13.operation='DISCHARGE' AND s13.activity_code='G013')"
                + "LEFT JOIN recap_shifting s14 ON sv.no_ppkb=s14.no_ppkb AND s14.operation='DISCHARGE' AND s14.activity_code='G014')"
                + "LEFT JOIN recap_shifting s15 ON sv.no_ppkb=s15.no_ppkb AND s15.operation='DISCHARGE' AND s15.activity_code='G015')"
                + "LEFT JOIN recap_shifting s16 ON sv.no_ppkb=s16.no_ppkb AND s16.operation='DISCHARGE' AND s16.activity_code='G016')"
                + "LEFT JOIN recap_shifting s17 ON sv.no_ppkb=s17.no_ppkb AND s17.operation='DISCHARGE' AND s17.activity_code='G017')"
                + "LEFT JOIN recap_shifting s18 ON sv.no_ppkb=s18.no_ppkb AND s18.operation='DISCHARGE' AND s18.activity_code='G018')"
                + "LEFT JOIN recap_shifting s19 ON sv.no_ppkb=s19.no_ppkb AND s19.operation='DISCHARGE' AND s19.activity_code='G019')"
                + "LEFT JOIN recap_shifting s20 ON sv.no_ppkb=s20.no_ppkb AND s20.operation='DISCHARGE' AND s20.activity_code='G020')"
                + "LEFT JOIN recap_shifting s21 ON sv.no_ppkb=s21.no_ppkb AND s21.operation='DISCHARGE' AND s21.activity_code='G021')"
                + "LEFT JOIN recap_shifting s22 ON sv.no_ppkb=s22.no_ppkb AND s22.operation='DISCHARGE' AND s22.activity_code='G022')"
                + "LEFT JOIN recap_shifting s23 ON sv.no_ppkb=s23.no_ppkb AND s23.operation='DISCHARGE' AND s23.activity_code='G023')"
                + "LEFT JOIN recap_shifting s24 ON sv.no_ppkb=s24.no_ppkb AND s24.operation='DISCHARGE' AND s24.activity_code='G024')"
                + "LEFT JOIN recap_transhipment t1 ON sv.no_ppkb=t1.no_ppkb AND t1.activity_code='H001')"
                + "LEFT JOIN recap_transhipment t2 ON sv.no_ppkb=t2.no_ppkb AND t2.activity_code='H002')"
                + "LEFT JOIN recap_transhipment t3 ON sv.no_ppkb=t3.no_ppkb AND t3.activity_code='H003')"
                + "LEFT JOIN recap_transhipment t4 ON sv.no_ppkb=t4.no_ppkb AND t4.activity_code='H004')"
                + "LEFT JOIN recap_transhipment t5 ON sv.no_ppkb=t5.no_ppkb AND t5.activity_code='H005')"
                + "LEFT JOIN recap_transhipment t6 ON sv.no_ppkb=t6.no_ppkb AND t6.activity_code='H006')"
                + "LEFT JOIN recap_transhipment t7 ON sv.no_ppkb=t7.no_ppkb AND t7.activity_code='H007')"
                + "LEFT JOIN recap_transhipment t8 ON sv.no_ppkb=t8.no_ppkb AND t8.activity_code='H008')"
                + "WHERE sv.no_ppkb=? "
                + "GROUP BY mc.symbol, sv.no_ppkb, s5.amount, s6.amount, s21.amount, s22.amount,s5.total_charge, s6.total_charge, s21.total_charge, s22.total_charge,"
                + "s1.amount, s2.amount, s17.amount, s18.amount,s1.total_charge, s2.total_charge, s17.total_charge, s18.total_charge,"
                + "s13.amount, s14.amount, s13.total_charge, s14.total_charge, "
                + "s9.amount, s10.amount,s9.total_charge, s10.total_charge,"
                + "s7.amount, s8.amount, s23.amount, s24.amount, s7.total_charge, s8.total_charge, s23.total_charge, s24.total_charge,"
                + "s3.amount, s4.amount, s19.amount, s20.amount, s3.total_charge, s4.total_charge, s19.total_charge, s20.total_charge,"
                + "s15.amount, s16.amount, s15.total_charge, s16.total_charge, "
                + "s11.amount, s12.amount,s11.total_charge, s12.total_charge, "
                + "t1.amount, t2.amount, t5.amount, t6.amount, t1.total_charge, t2.total_charge, t5.total_charge, t6.total_charge, "
                + "t3.amount, t4.amount, t7.amount, t8.amount, t3.total_charge, t4.total_charge, t7.total_charge, t8.total_charge"),
   @NamedNativeQuery(name = "RecapHandlingDischarge.Native.findByPpkb", query = "SELECT id FROM recap_handling_discharge WHERE no_ppkb = ?"),
   @NamedNativeQuery(name = "RecapHandlingDischarge.Native.countHandlingDischarge", query = "SELECT * FROM count_handling(?,?)")
})
public class RecapHandlingDischarge implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "activity_code", length = 10)
    private String activityCode;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
    @Column(name = "curr_code", length = 5)
    private String currCode;
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
    @Basic(optional = false)
    @Column(name = "crane", length = 2)
    private String crane;
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private boolean isExportImport;
    @Basic(optional = false)
    @Column(name = "sling", nullable = false)
    private boolean sling;
    @Column(name = "open_door", nullable = false)
    private boolean openDoor = false;
    @Basic(optional = false)
    @Column(name = "cont_size", nullable = false)
    private short contSize;
    @JoinColumn(name = "cont_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MasterContainerTypeInGeneral containerType;
    @Column(name = "cont_status", length = 5)
    private String contStatus;
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;

    public RecapHandlingDischarge() {
    }

    public RecapHandlingDischarge(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
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

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public boolean getIsExportImport() {
        return isExportImport;
    }

    public void setIsExportImport(boolean isExportImport) {
        this.isExportImport = isExportImport;
    }

    public boolean getSling() {
        return sling;
    }

    public void setSling(boolean sling) {
        this.sling = sling;
    }

    public MasterContainerTypeInGeneral getContainerType() {
        return containerType;
    }

    public void setContainerType(MasterContainerTypeInGeneral containerType) {
        this.containerType = containerType;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public boolean isOpenDoor() {
        return openDoor;
    }

    public void setOpenDoor(boolean openDoor) {
        this.openDoor = openDoor;
    }

    public short getContSize() {
        return contSize;
    }

    public void setContSize(short contSize) {
        this.contSize = contSize;
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
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
        if (!(object instanceof RecapHandlingDischarge)) {
            return false;
        }
        RecapHandlingDischarge other = (RecapHandlingDischarge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapHandlingDischarge[id=" + id + "]";
    }
}
