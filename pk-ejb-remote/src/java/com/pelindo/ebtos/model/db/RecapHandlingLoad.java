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
@Table(name = "recap_handling_load")
@NamedQueries({
    @NamedQuery(name = "RecapHandlingLoad.findAll", query = "SELECT r FROM RecapHandlingLoad r"),
    @NamedQuery(name = "RecapHandlingLoad.findById", query = "SELECT r FROM RecapHandlingLoad r WHERE r.id = :id"),
    @NamedQuery(name = "RecapHandlingLoad.findByNoPpkb", query = "SELECT r FROM RecapHandlingLoad r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapHandlingLoad.deleteByNoPpkb", query = "DELETE FROM RecapHandlingLoad r WHERE r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapHandlingLoad.findByActivityCode", query = "SELECT r FROM RecapHandlingLoad r WHERE r.activityCode = :activityCode"),
    @NamedQuery(name = "RecapHandlingLoad.findByAmount", query = "SELECT r FROM RecapHandlingLoad r WHERE r.amount = :amount"),
    @NamedQuery(name = "RecapHandlingLoad.findByCharge", query = "SELECT r FROM RecapHandlingLoad r WHERE r.charge = :charge"),
    @NamedQuery(name = "RecapHandlingLoad.findByTotalCharge", query = "SELECT r FROM RecapHandlingLoad r WHERE r.totalCharge = :totalCharge"),
    @NamedQuery(name = "RecapHandlingLoad.findByActivityCodeAndNoPpkb", query = "SELECT r FROM RecapHandlingLoad r WHERE r.activityCode = :activityCode AND r.noPpkb = :noPpkb"),
    @NamedQuery(name = "RecapHandlingLoad.findByBentuk3Constraint", query = "SELECT r FROM RecapHandlingLoad r WHERE r.contStatus = :contStatus AND r.crane = :crane AND r.isExportImport = :isExportImport AND r.sling = :sling AND r.containerType.id = :containerType AND r.activityCode = :activityCode AND r.noPpkb = :noPpkb AND r.currCode=:currCode AND r.openDoor = :openDoor AND r.contSize = :contSize AND r.twentyOneFeet = :twentyOneFeet AND r.charge = :charge")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "RecapHandlingLoad.Native.findByActCode", query = "SELECT id FROM recap_handling_load WHERE activity_code = ? AND no_ppkb = ?"),
    @NamedNativeQuery(name = "RecapHandlingLoad.Native.findVesselHandlingByPPKB", query = "SELECT nvl(SUM(m.amount)) as totpalka, nvl(SUM(m.total_charge)) as chargepalka, "
                + "nvl(l1.amount) as A001, nvl(l5.amount) as A005, (nvl(l1.amount)+nvl(l5.amount)) as jml1, nvl(l2.amount) as A002, nvl(l6.amount) as A006, (nvl(l2.amount)+nvl(l6.amount)) as jml2, (nvl(l1.amount)+nvl(l5.amount)+nvl(l2.amount)+nvl(l6.amount)) as total1, (nvl(l1.total_charge)+nvl(l5.total_charge)+nvl(l2.total_charge)+nvl(l6.total_charge)) as charge1,"
                + "nvl(l13.amount) as A013, nvl(l17.amount) as A017, (nvl(l13.amount)+nvl(l17.amount)) as jml3, nvl(l14.amount) as A014, nvl(l18.amount) as A018, (nvl(l14.amount)+nvl(l18.amount)) as jml4, (nvl(l13.amount)+nvl(l17.amount)+nvl(l14.amount)+nvl(l18.amount)) as total2, (nvl(l13.total_charge)+nvl(l17.total_charge)+nvl(l14.total_charge)+nvl(l18.total_charge)) as charge2,"
                + "nvl(l9.amount) as A009, nvl(l11.amount) as A011, (nvl(l9.amount)+nvl(l11.amount)) as jml5, nvl(l2.amount) as A2002, nvl(l6.amount) as A2006, (nvl(l2.amount)+nvl(l6.amount)) as jml6,(nvl(l9.amount)+nvl(l11.amount)+nvl(l2.amount)+nvl(l6.amount)) as total3, (nvl(l9.total_charge)+nvl(l11.total_charge)+nvl(l2.total_charge)+nvl(l6.total_charge)) as charge3,"
                + "nvl(s5.amount) as G005, nvl(s6.amount) as G006, (nvl(s5.amount)+nvl(s6.amount)) as jml7, nvl(s21.amount) as G021, nvl(s22.amount) as G022, (nvl(s21.amount)+nvl(s22.amount)) as jml8,(nvl(s5.amount)+nvl(s6.amount)+nvl(s21.amount)+nvl(s22.amount)) as total4, (nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)) as charge4,"
                + "nvl(s1.amount) as G001, nvl(s2.amount) as G002, (nvl(s1.amount)+nvl(s2.amount)) as jml9, nvl(s17.amount) as G017, nvl(s18.amount) as G018, (nvl(s17.amount)+nvl(s18.amount)) as jml10,(nvl(s1.amount)+nvl(s2.amount)+nvl(s17.amount)+nvl(s18.amount)) as total5, (nvl(s1.total_charge)+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)) as charge5,"
                + "nvl(s13.amount) as G013, nvl(s14.amount) as G014, (nvl(s13.amount)+nvl(s14.amount)) as jml11, (nvl(s13.amount)+nvl(s14.amount)) as total6,(nvl(s13.total_charge)+nvl(s14.total_charge)) as charge6,"
                + "nvl(s9.amount) as G009, nvl(s10.amount) as G010, (nvl(s9.amount)+nvl(s10.amount)) as jml12, (nvl(s9.amount)+nvl(s10.amount)) as total7,(nvl(s9.total_charge)+nvl(s10.total_charge)) as charge7,"
                + "(nvl(l1.total_charge)+nvl(l5.total_charge)+nvl(l2.total_charge)+nvl(l6.total_charge)+nvl(l13.total_charge)+nvl(l17.total_charge)+nvl(l14.total_charge)+nvl(l18.total_charge)+nvl(l9.total_charge)+nvl(l11.total_charge)+nvl(l2.total_charge)+nvl(l6.total_charge)+nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+"
                + "nvl(s1.total_charge)+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(SUM(m.total_charge))) as jumlah1, "
                + "nvl(l3.amount) as A003, nvl(l7.amount) as A007, (nvl(l3.amount)+nvl(l7.amount)) as jml13, nvl(l4.amount) as A004, nvl(l8.amount) as A008, (nvl(l4.amount)+nvl(l8.amount)) as jml14, (nvl(l3.amount)+nvl(l7.amount)+nvl(l4.amount)+nvl(l8.amount)) as total8,(nvl(l3.total_charge)+nvl(l7.total_charge)+nvl(l4.total_charge)+nvl(l8.total_charge)) as charge8, "
                + "nvl(l15.amount) as A015, nvl(l19.amount) as A019, (nvl(l15.amount)+nvl(l19.amount)) as jml15, nvl(l16.amount) as A016, nvl(l20.amount) as A020, (nvl(l16.amount)+nvl(l20.amount)) as jml16, (nvl(l15.amount)+nvl(l19.amount)+nvl(l16.amount)+nvl(l20.amount)) as total9,(nvl(l15.total_charge)+nvl(l19.total_charge)+nvl(l16.total_charge)+nvl(l20.total_charge)) as charge9,"
                + "nvl(l10.amount) as A010, nvl(l12.amount) as A012, (nvl(l10.amount)+nvl(l12.amount)) as jml17, nvl(l4.amount) as A2004, nvl(l8.amount) as A2008, (nvl(l4.amount)+nvl(l8.amount)) as jml18, (nvl(l10.amount)+nvl(l12.amount)+nvl(l4.amount)+nvl(l8.amount)) as total10,(nvl(l10.total_charge)+nvl(l12.total_charge)+nvl(l4.total_charge)+nvl(l8.total_charge)) as charge10,"
                + "nvl(s7.amount) as G007, nvl(s8.amount) as G008, (nvl(s7.amount)+nvl(s8.amount)) as jml19, nvl(s23.amount) as G023, nvl(s24.amount) as G024, (nvl(s23.amount)+nvl(s24.amount)) as jml20, (nvl(s7.amount)+nvl(s8.amount)+nvl(s23.amount)+nvl(s24.amount)) as total11,(nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)) as charge11,"
                + "nvl(s3.amount) as G003, nvl(s4.amount) as G004, (nvl(s3.amount)+nvl(s4.amount)) as jml21, nvl(s19.amount) as G019, nvl(s20.amount) as G020, (nvl(s19.amount)+nvl(s20.amount)) as jml22, (nvl(s3.amount)+nvl(s4.amount)+nvl(s19.amount)+nvl(s20.amount)) as total12,(nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)) as charge12,"
                + "nvl(s15.amount) as G015, nvl(s16.amount) as G016, (nvl(s15.amount)+nvl(s16.amount)) as jml23, (nvl(s15.amount)+nvl(s16.amount)) as total13,(nvl(s15.total_charge)+nvl(s16.total_charge)) as charge13,"
                + "nvl(s11.amount) as G011, nvl(s12.amount) as G012, (nvl(s11.amount)+nvl(s12.amount)) as jml24, (nvl(s11.amount)+nvl(s12.amount)) as total14,(nvl(s11.total_charge)+nvl(s12.total_charge)) as charge14,"
                + "(nvl(l3.total_charge)+nvl(l7.total_charge)+nvl(l4.total_charge)+nvl(l8.total_charge)+nvl(l15.total_charge)+nvl(l19.total_charge)+nvl(l16.total_charge)+nvl(l20.total_charge)+nvl(l10.total_charge)+nvl(l12.total_charge)+nvl(l4.total_charge)+nvl(l8.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)"
                + "+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)) as jumlah2,"
                + "(nvl(l1.total_charge)+nvl(l5.total_charge)+nvl(l2.total_charge)+nvl(l6.total_charge)+nvl(l13.total_charge)+nvl(l17.total_charge)+nvl(l14.total_charge)+nvl(l18.total_charge)+nvl(l9.total_charge)+nvl(l11.total_charge)+nvl(l2.total_charge)+nvl(l6.total_charge)+nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)"
                + "+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(l3.total_charge)+nvl(l7.total_charge)+nvl(l4.total_charge)+nvl(l8.total_charge)+nvl(l15.total_charge)+nvl(l19.total_charge)+nvl(l16.total_charge)+nvl(l20.total_charge)+nvl(l10.total_charge)+nvl(l12.total_charge)"
                + "+nvl(l4.total_charge)+nvl(l8.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(SUM(m.total_charge))) as jumlah1n2,"
                + "((nvl(l1.total_charge)+nvl(l5.total_charge)+nvl(l2.total_charge)+nvl(l6.total_charge)+nvl(l13.total_charge)+nvl(l17.total_charge)+nvl(l14.total_charge)+nvl(l18.total_charge)+nvl(l9.total_charge)+nvl(l11.total_charge)+nvl(l2.total_charge)+nvl(l6.total_charge)+nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)"
                + "+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(l3.total_charge)+nvl(l7.total_charge)+nvl(l4.total_charge)+nvl(l8.total_charge)+nvl(l15.total_charge)+nvl(l19.total_charge)+nvl(l16.total_charge)+nvl(l20.total_charge)+nvl(l10.total_charge)+nvl(l12.total_charge)"
                + "+nvl(l4.total_charge)+nvl(l8.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(SUM(m.total_charge)))*10/100) as ppn,"
                + "((nvl(l1.total_charge)+nvl(l5.total_charge)+nvl(l2.total_charge)+nvl(l6.total_charge)+nvl(l13.total_charge)+nvl(l17.total_charge)+nvl(l14.total_charge)+nvl(l18.total_charge)+nvl(l9.total_charge)+nvl(l11.total_charge)+nvl(l2.total_charge)+nvl(l6.total_charge)+nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)"
                + "+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(l3.total_charge)+nvl(l7.total_charge)+nvl(l4.total_charge)+nvl(l8.total_charge)+nvl(l15.total_charge)+nvl(l19.total_charge)+nvl(l16.total_charge)+nvl(l20.total_charge)+nvl(l10.total_charge)+nvl(l12.total_charge)"
                + "+nvl(l4.total_charge)+nvl(l8.total_charge)+nvl(s7.total_charge)+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(SUM(m.total_charge)))+((nvl(l1.total_charge)+nvl(l5.total_charge)+nvl(l2.total_charge)"
                + "+nvl(l6.total_charge)+nvl(l13.total_charge)+nvl(l17.total_charge)+nvl(l14.total_charge)+nvl(l18.total_charge)+nvl(l9.total_charge)+nvl(l11.total_charge)+nvl(l2.total_charge)+nvl(l6.total_charge)+nvl(s5.total_charge)+nvl(s6.total_charge)+nvl(s21.total_charge)+nvl(s22.total_charge)+nvl(s1.total_charge)+nvl(s2.total_charge)+nvl(s17.total_charge)+nvl(s18.total_charge)"
                + "+nvl(s13.total_charge)+nvl(s14.total_charge)+nvl(s9.total_charge)+nvl(s10.total_charge)+nvl(l3.total_charge)+nvl(l7.total_charge)+nvl(l4.total_charge)+nvl(l8.total_charge)+nvl(l15.total_charge)+nvl(l19.total_charge)+nvl(l16.total_charge)+nvl(l20.total_charge)+nvl(l10.total_charge)+nvl(l12.total_charge)+nvl(l4.total_charge)+nvl(l8.total_charge)+nvl(s7.total_charge)"
                + "+nvl(s8.total_charge)+nvl(s23.total_charge)+nvl(s24.total_charge)+nvl(s3.total_charge)+nvl(s4.total_charge)+nvl(s19.total_charge)+nvl(s20.total_charge)+nvl(s15.total_charge)+nvl(s16.total_charge)+nvl(s11.total_charge)+nvl(s12.total_charge)+nvl(SUM(m.total_charge)))*10/100)) as totalbayar, mc.symbol, sv.no_ppkb "
                + "FROM ((((((((((((((((((((((((((((((((((((((((((((((service_vessel sv LEFT JOIN m_currency mc ON sv.tipe_pelayaran=mc.trade_type)LEFT JOIN recap_hatch_move m ON sv.no_ppkb=m.no_ppkb AND m.operation='LOAD')"
                + "LEFT JOIN recap_handling_load l1 ON sv.no_ppkb=l1.no_ppkb AND l1.activity_code='A001')"
                + "LEFT JOIN recap_handling_load l2 ON sv.no_ppkb=l2.no_ppkb AND l2.activity_code='A002')"
                + "LEFT JOIN recap_handling_load l3 ON sv.no_ppkb=l3.no_ppkb AND l3.activity_code='A003')"
                + "LEFT JOIN recap_handling_load l4 ON sv.no_ppkb=l4.no_ppkb AND l4.activity_code='A004')"
                + "LEFT JOIN recap_handling_load l5 ON sv.no_ppkb=l5.no_ppkb AND l5.activity_code='A005')"
                + "LEFT JOIN recap_handling_load l6 ON sv.no_ppkb=l6.no_ppkb AND l6.activity_code='A006')"
                + "LEFT JOIN recap_handling_load l7 ON sv.no_ppkb=l7.no_ppkb AND l7.activity_code='A007')"
                + "LEFT JOIN recap_handling_load l8 ON sv.no_ppkb=l8.no_ppkb AND l8.activity_code='A008')"
                + "LEFT JOIN recap_handling_load l9 ON sv.no_ppkb=l9.no_ppkb AND l9.activity_code='A009')"
                + "LEFT JOIN recap_handling_load l10 ON sv.no_ppkb=l10.no_ppkb AND l10.activity_code='A010')"
                + "LEFT JOIN recap_handling_load l11 ON sv.no_ppkb=l11.no_ppkb AND l11.activity_code='A011')"
                + "LEFT JOIN recap_handling_load l12 ON sv.no_ppkb=l12.no_ppkb AND l12.activity_code='A012')"
                + "LEFT JOIN recap_handling_load l13 ON sv.no_ppkb=l13.no_ppkb AND l13.activity_code='A013')"
                + "LEFT JOIN recap_handling_load l14 ON sv.no_ppkb=l14.no_ppkb AND l14.activity_code='A014')"
                + "LEFT JOIN recap_handling_load l15 ON sv.no_ppkb=l15.no_ppkb AND l15.activity_code='A015')"
                + "LEFT JOIN recap_handling_load l16 ON sv.no_ppkb=l16.no_ppkb AND l16.activity_code='A016')"
                + "LEFT JOIN recap_handling_load l17 ON sv.no_ppkb=l17.no_ppkb AND l17.activity_code='A017')"
                + "LEFT JOIN recap_handling_load l18 ON sv.no_ppkb=l18.no_ppkb AND l18.activity_code='A018')"
                + "LEFT JOIN recap_handling_load l19 ON sv.no_ppkb=l19.no_ppkb AND l19.activity_code='A019')"
                + "LEFT JOIN recap_handling_load l20 ON sv.no_ppkb=l20.no_ppkb AND l20.activity_code='A020')"
                + "LEFT JOIN recap_shifting s1 ON sv.no_ppkb=s1.no_ppkb AND s1.operation='LOAD' AND s1.activity_code='G001')"
                + "LEFT JOIN recap_shifting s2 ON sv.no_ppkb=s2.no_ppkb AND s2.operation='LOAD' AND s2.activity_code='G002')"
                + "LEFT JOIN recap_shifting s3 ON sv.no_ppkb=s3.no_ppkb AND s3.operation='LOAD' AND s3.activity_code='G003')"
                + "LEFT JOIN recap_shifting s4 ON sv.no_ppkb=s4.no_ppkb AND s4.operation='LOAD' AND s4.activity_code='G004')"
                + "LEFT JOIN recap_shifting s5 ON sv.no_ppkb=s5.no_ppkb AND s5.operation='LOAD' AND s5.activity_code='G005')"
                + "LEFT JOIN recap_shifting s6 ON sv.no_ppkb=s6.no_ppkb AND s6.operation='LOAD' AND s6.activity_code='G006')"
                + "LEFT JOIN recap_shifting s7 ON sv.no_ppkb=s7.no_ppkb AND s7.operation='LOAD' AND s7.activity_code='G007')"
                + "LEFT JOIN recap_shifting s8 ON sv.no_ppkb=s8.no_ppkb AND s8.operation='LOAD' AND s8.activity_code='G008')"
                + "LEFT JOIN recap_shifting s9 ON sv.no_ppkb=s9.no_ppkb AND s9.operation='LOAD' AND s9.activity_code='G009')"
                + "LEFT JOIN recap_shifting s10 ON sv.no_ppkb=s10.no_ppkb AND s10.operation='LOAD' AND s10.activity_code='G010')"
                + "LEFT JOIN recap_shifting s11 ON sv.no_ppkb=s11.no_ppkb AND s11.operation='LOAD' AND s11.activity_code='G011')"
                + "LEFT JOIN recap_shifting s12 ON sv.no_ppkb=s12.no_ppkb AND s12.operation='LOAD' AND s12.activity_code='G012')"
                + "LEFT JOIN recap_shifting s13 ON sv.no_ppkb=s13.no_ppkb AND s13.operation='LOAD' AND s13.activity_code='G013')"
                + "LEFT JOIN recap_shifting s14 ON sv.no_ppkb=s14.no_ppkb AND s14.operation='LOAD' AND s14.activity_code='G014')"
                + "LEFT JOIN recap_shifting s15 ON sv.no_ppkb=s15.no_ppkb AND s15.operation='LOAD' AND s15.activity_code='G015')"
                + "LEFT JOIN recap_shifting s16 ON sv.no_ppkb=s16.no_ppkb AND s16.operation='LOAD' AND s16.activity_code='G016')"
                + "LEFT JOIN recap_shifting s17 ON sv.no_ppkb=s17.no_ppkb AND s17.operation='LOAD' AND s17.activity_code='G017')"
                + "LEFT JOIN recap_shifting s18 ON sv.no_ppkb=s18.no_ppkb AND s18.operation='LOAD' AND s18.activity_code='G018')"
                + "LEFT JOIN recap_shifting s19 ON sv.no_ppkb=s19.no_ppkb AND s19.operation='LOAD' AND s19.activity_code='G019')"
                + "LEFT JOIN recap_shifting s20 ON sv.no_ppkb=s20.no_ppkb AND s20.operation='LOAD' AND s20.activity_code='G020')"
                + "LEFT JOIN recap_shifting s21 ON sv.no_ppkb=s21.no_ppkb AND s21.operation='LOAD' AND s21.activity_code='G021')"
                + "LEFT JOIN recap_shifting s22 ON sv.no_ppkb=s22.no_ppkb AND s22.operation='LOAD' AND s22.activity_code='G022')"
                + "LEFT JOIN recap_shifting s23 ON sv.no_ppkb=s23.no_ppkb AND s23.operation='LOAD' AND s23.activity_code='G023')"
                + "LEFT JOIN recap_shifting s24 ON sv.no_ppkb=s24.no_ppkb AND s24.operation='LOAD' AND s24.activity_code='G024')"
                + "WHERE sv.no_ppkb=? "
                + "GROUP BY mc.symbol, sv.no_ppkb, l1.amount, l5.amount, l2.amount , l6.amount, l1.total_charge, l5.total_charge, l2.total_charge , l6.total_charge, "
                + "l13.amount, l17.amount, l14.amount, l18.amount, l13.total_charge, l17.total_charge, l14.total_charge, l18.total_charge, "
                + "l9.amount, l11.amount, l9.total_charge, l11.total_charge, "
                + "s5.amount, s6.amount, s21.amount, s22.amount,s5.total_charge, s6.total_charge, s21.total_charge, s22.total_charge,"
                + "s1.amount, s2.amount, s17.amount, s18.amount,s1.total_charge, s2.total_charge, s17.total_charge, s18.total_charge,"
                + "s13.amount, s14.amount, s13.total_charge, s14.total_charge, "
                + "s9.amount, s10.amount,s9.total_charge, s10.total_charge,"
                + "l3.amount, l7.amount, l4.amount, l8.amount, l3.total_charge, l7.total_charge, l4.total_charge, l8.total_charge, "
                + "l15.amount, l19.amount, l16.amount, l20.amount, l15.total_charge, l19.total_charge, l16.total_charge, l20.total_charge, "
                + "l10.amount, l12.amount,l10.total_charge, l12.total_charge,"
                + "s7.amount, s8.amount, s23.amount, s24.amount, s7.total_charge, s8.total_charge, s23.total_charge, s24.total_charge,"
                + "s3.amount, s4.amount, s19.amount, s20.amount, s3.total_charge, s4.total_charge, s19.total_charge, s20.total_charge,"
                + "s15.amount, s16.amount, s15.total_charge, s16.total_charge, "
                + "s11.amount, s12.amount,s11.total_charge, s12.total_charge"),
    @NamedNativeQuery(name = "RecapHandlingLoad.Native.findHandlingInvoice", query = "SELECT v.no_ppkb,((SELECT nvl(SUM(h.total_charge)) FROM recap_handling_load h WHERE h.no_ppkb=v.no_ppkb)+(SELECT nvl(SUM(s.total_charge))FROM recap_shifting s WHERE v.no_ppkb = s.no_ppkb AND s.operation = 'LOAD')+(SELECT nvl(SUM(m.total_charge)) FROM recap_hatch_move m WHERE v.no_ppkb = m.no_ppkb AND m.operation = 'LOAD')) as paket_handling, "
                + "nvl(p.total_charge) as receiving, (SELECT nvl(SUM(l.charge)) FROM recap_penumpukan_transload l WHERE v.no_ppkb = l.no_ppkb) as penumpukan, nvl(r.charge) as reefer, nvl(SUM(c.charge)) as batal_muat, "
                + "((SELECT nvl(SUM(h.total_charge)) FROM recap_handling_load h WHERE h.no_ppkb=v.no_ppkb)+(SELECT nvl(SUM(s.total_charge))FROM recap_shifting s WHERE v.no_ppkb = s.no_ppkb AND s.operation = 'LOAD')+(SELECT nvl(SUM(m.total_charge)) FROM recap_hatch_move m WHERE v.no_ppkb = m.no_ppkb AND m.operation = 'LOAD')+nvln(p.total_charge)+(SELECT nvl(SUM(l.charge)) FROM recap_penumpukan_transload l WHERE v.no_ppkb = l.no_ppkb)+nvln(r.charge)+nvln(SUM(c.charge))) as jumlah1 "
                + "FROM (((planning_vessel v "
                + "LEFT JOIN recap_receiving p ON v.no_ppkb = p.no_ppkb) "
                + "LEFT JOIN recap_reefer_load r ON v.no_ppkb = r.no_ppkb) "
                + "LEFT JOIN cancel_loading_service c ON v.no_ppkb = c.no_ppkb)"
                + "WHERE v.no_ppkb=?  "
                + "GROUP BY v.no_ppkb, p.total_charge, r.charge"),
    @NamedNativeQuery(name = "RecapHandlingLoad.Native.findByPpkb", query = "SELECT id FROM recap_handling_load WHERE no_ppkb = ?"),
    @NamedNativeQuery(name = "RecapHandlingLoad.Native.countHandlingLoad", query = "SELECT * FROM count_handling(?)")
})
public class RecapHandlingLoad implements Serializable, EntityAuditable {
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
    @Column(name = "cont_status", length = 5)
    private String contStatus;
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;

    public RecapHandlingLoad() {
    }

    public RecapHandlingLoad(Integer id) {
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
        if (!(object instanceof RecapHandlingLoad)) {
            return false;
        }
        RecapHandlingLoad other = (RecapHandlingLoad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.RecapHandlingLoad[id=" + id + "]";
    }
}
