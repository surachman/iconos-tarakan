/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
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
@Table(name = "handling_invoice")
@NamedQueries({
    @NamedQuery(name = "HandlingInvoice.findAll", query = "SELECT h FROM HandlingInvoice h")})
@NamedNativeQueries({

//    @NamedNativeQuery(name = "HandlingInvoice.Native.generateId", query = "SELECT MAX(substring(no_invoice,3,5))FROM handling_invoice WHERE substring(no_invoice,8,2) = ?"),
    
    @NamedNativeQuery(name = "HandlingInvoice.Native.generateId", query =     "SELECT MAX(substr(no_invoice,3,5))FROM handling_invoice WHERE substr(no_invoice,8,2) = ?"),
    @NamedNativeQuery(name = "HandlingInvoice.Native.findInvoice", query = "SELECT no_invoice FROM handling_invoice WHERE no_ppkb=? AND operation=?"),

//TODO:
//Upgrade function handling_invoice to Oracle
    @NamedNativeQuery(name = "HandlingInvoice.Native.handlingInvoiceDischarge", query = "SELECT * FROM handling_invoice(?, ?)"),
    @NamedNativeQuery(name = "HandlingInvoice.Native.handlingInvoiceLoad", query = "SELECT * FROM handling_invoice(?)"),
    @NamedNativeQuery(name = "HandlingInvoice.Native.handlingInvoiceLoadCBTG", query = "SELECT p.jumlah, p.kalippn, p.materai, p.total FROM handling_invoice_cbtg(?) p"),
    @NamedNativeQuery(name = "HandlingInvoice.Native.handlingInvoiceLoadPLND", query = "SELECT p.jumlah, p.kalippn, p.materai, p.total FROM handling_invoice_plnd(?) p"),

//

/*
    @NamedNativeQuery(name = "HandlingInvoice.Native.findContainerServicesChargeDetailByNoPpkb", query = "SELECT (nvl(rhl.total_charge) + nvl(rhd.total_charge) + nvl(rhu.handling_charge)) handling_charge, (nvl(rp.total_charge) + nvl(ppt.total_charge) + nvl(rpu.total_charge)) penumpukan_charge, nvl(rhm.total_charge) hatchmoves_charge, (nvl(rt.total_charge) + nvl(rhu.transhipment_charge)) transhipment_charge, (nvl(rs.total_charge) + nvl(rhu.shifting_charge)) shifting_charge, (nvl(rub.total_charge) + nvl(rubu.total_charge) + nvl(rubhm.total_charge)) upah_buruh_charge, nvl(rak.total_charge) air_kapal_charge "
                                                                                                        + "FROM (SELECT ?::varchar no_ppkb, 'IDR'::varchar curr_code "
                                                                                                                + "UNION SELECT ?::varchar, 'USD'::varchar) AS sv "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_handling_load WHERE activity_code LIKE 'A%' GROUP BY no_ppkb, curr_code) rhl ON (sv.no_ppkb=rhl.no_ppkb AND sv.curr_code=rhl.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_handling_discharge WHERE activity_code LIKE 'A%' GROUP BY no_ppkb, curr_code) rhd ON (sv.no_ppkb=rhd.no_ppkb AND sv.curr_code=rhd.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_penumpukan GROUP BY no_ppkb, curr_code) rp ON (sv.no_ppkb=rp.no_ppkb AND sv.curr_code=rp.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM perhitungan_penumpukan_transhipment GROUP BY no_ppkb, curr_code) ppt ON (sv.no_ppkb=ppt.no_ppkb AND sv.curr_code=ppt.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_penumpukan_uc GROUP BY no_ppkb, curr_code) rpu ON (sv.no_ppkb=rpu.no_ppkb AND sv.curr_code=rpu.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_transhipment GROUP BY no_ppkb, curr_code) rt ON (sv.no_ppkb=rt.no_ppkb AND sv.curr_code=rt.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_shifting GROUP BY no_ppkb, curr_code) rs ON (sv.no_ppkb=rs.no_ppkb AND sv.curr_code=rs.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(CASE WHEN activity = 'SHIFTING' THEN total_charge ELSE 0 END)::numeric(19,2) shifting_charge, "
                                                                                                                                + "SUM(CASE WHEN activity = 'TRANSHIPMENT' THEN total_charge ELSE 0 END)::numeric(19,2) transhipment_charge, "
                                                                                                                                + "SUM(CASE WHEN activity = 'LOAD' OR activity = 'DISCHARGE' THEN total_charge ELSE 0 END)::numeric(19,2) handling_charge "
                                                                                                                        + "FROM recap_handling_uc GROUP BY no_ppkb, curr_code) rhu ON (sv.no_ppkb=rhu.no_ppkb AND sv.curr_code=rhu.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_hatch_move GROUP BY no_ppkb, curr_code) rhm ON (sv.no_ppkb=rhm.no_ppkb AND sv.curr_code=rhm.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_upah_buruh_hatch_move GROUP BY no_ppkb, curr_code) rubhm ON (sv.no_ppkb=rubhm.no_ppkb AND sv.curr_code=rubhm.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_upah_buruh GROUP BY no_ppkb, curr_code) rub ON (sv.no_ppkb=rub.no_ppkb AND sv.curr_code=rub.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_upah_buruh_uc GROUP BY no_ppkb, curr_code) rubu ON (sv.no_ppkb=rubu.no_ppkb AND sv.curr_code=rubu.curr_code) "
                                                                                                                + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_air_kapal GROUP BY no_ppkb, curr_code) rak ON (sv.no_ppkb=rak.no_ppkb AND sv.curr_code=rak.curr_code) "
                                                                                                        + "ORDER BY sv.curr_code"),
*/
    @NamedNativeQuery(name = "HandlingInvoice.Native.findContainerServicesChargeDetailByNoPpkb", query = 
"SELECT (NVL(rhl.total_charge,0) + NVL(rhd.total_charge,0) + NVL(rhu.handling_charge,0)) handling_charge, " 
+"  (NVL(rp.total_charge,0)       + NVL(ppt.total_charge,0) + NVL(rpu.total_charge,0)) penumpukan_charge, " 
+"  NVL(rhm.total_charge,0) hatchmoves_charge, " 
+"  (NVL(rt.total_charge,0)  + NVL(rhu.transhipment_charge,0)) transhipment_charge, " 
+"  (NVL(rs.total_charge,0)  + NVL(rhu.shifting_charge,0)) shifting_charge, " 
+"  (NVL(rub.total_charge,0) + NVL(rubu.total_charge,0) + NVL(rubhm.total_charge,0)) upah_buruh_charge, " 
+"  NVL(rak.total_charge,0) air_kapal_charge " 
+"FROM " 
+"  (SELECT CAST(? AS VARCHAR2(30)) no_ppkb, 'IDR' curr_code FROM dual " 
+"  UNION " 
+"  SELECT CAST(? AS VARCHAR2(30)), 'USD' curr_code FROM dual " 
+"  ) sv " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM recap_handling_load " 
+"  WHERE activity_code LIKE 'A%' " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rhl " 
+"ON (sv.no_ppkb  =rhl.no_ppkb " 
+"AND sv.curr_code=rhl.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM recap_handling_discharge " 
+"  WHERE activity_code LIKE 'A%' " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rhd " 
+"ON (sv.no_ppkb  =rhd.no_ppkb " 
+"AND sv.curr_code=rhd.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM recap_penumpukan " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rp " 
+"ON (sv.no_ppkb  =rp.no_ppkb " 
+"AND sv.curr_code=rp.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM perhitungan_penumpukan_transhi " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) ppt " 
+"ON (sv.no_ppkb  =ppt.no_ppkb " 
+"AND sv.curr_code=ppt.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM recap_penumpukan_uc " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rpu " 
+"ON (sv.no_ppkb  =rpu.no_ppkb " 
+"AND sv.curr_code=rpu.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM recap_transhipment " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rt " 
+"ON (sv.no_ppkb  =rt.no_ppkb " 
+"AND sv.curr_code=rt.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM recap_shifting " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rs " 
+"ON (sv.no_ppkb  =rs.no_ppkb " 
+"AND sv.curr_code=rs.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM( " 
+"    CASE " 
+"      WHEN activity = 'SHIFTING' " 
+"      THEN total_charge " 
+"      ELSE 0 " 
+"    END) shifting_charge, " 
+"    SUM( " 
+"    CASE " 
+"      WHEN activity = 'TRANSHIPMENT' " 
+"      THEN total_charge " 
+"      ELSE 0 " 
+"    END) transhipment_charge, " 
+"    SUM( " 
+"    CASE " 
+"      WHEN activity = 'LOAD' " 
+"      OR activity   = 'DISCHARGE' " 
+"      THEN total_charge " 
+"      ELSE 0 " 
+"    END) handling_charge " 
+"  FROM recap_handling_uc " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rhu " 
+"ON (sv.no_ppkb  =rhu.no_ppkb " 
+"AND sv.curr_code=rhu.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM recap_hatch_move " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rhm " 
+"ON (sv.no_ppkb  =rhm.no_ppkb " 
+"AND sv.curr_code=rhm.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM recap_upah_buruh_hatch_move " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rubhm " 
+"ON (sv.no_ppkb  =rubhm.no_ppkb " 
+"AND sv.curr_code=rubhm.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM recap_upah_buruh " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rub " 
+"ON (sv.no_ppkb  =rub.no_ppkb " 
+"AND sv.curr_code=rub.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM recap_upah_buruh_uc " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rubu " 
+"ON (sv.no_ppkb  =rubu.no_ppkb " 
+"AND sv.curr_code=rubu.curr_code) " 
+"LEFT JOIN " 
+"  (SELECT no_ppkb, " 
+"    curr_code, " 
+"    SUM(total_charge) total_charge " 
+"  FROM recap_air_kapal " 
+"  GROUP BY no_ppkb, " 
+"    curr_code " 
+"  ) rak " 
+"ON (sv.no_ppkb  =rak.no_ppkb " 
+"AND sv.curr_code=rak.curr_code) " 
+"ORDER BY sv.curr_code"),

/*                                                                                                        
    @NamedNativeQuery(name = "HandlingInvoice.Native.findContainerServicesChargeDetailDischargeByNoPpkb", query = "SELECT (nvl(rh.handling_charge) + nvl(rhu.handling_charge)) handling_charge, (nvl(rp.total_charge) + nvl(rpu.total_charge)) penumpukan_charge, nvl(rhm.total_charge) hatchmoves_charge, (nvl(rh.transhipment_charge) + nvl(rhu.transhipment_charge)) transhipment_charge, (nvl(rh.shifting_charge) + nvl(rhu.shifting_charge)) shifting_charge, (nvl(rub.total_charge) + nvl(rubu.total_charge) + nvl(rubhm.total_charge)) upah_buruh_charge, nvl(rak.total_charge) air_kapal_charge "
                                                                                                     + "FROM (SELECT ?::varchar no_ppkb, 'IDR'::varchar curr_code "
                                                                                                             + "UNION SELECT ?::varchar, 'USD'::varchar) AS sv "
                                                                                                             + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(CASE WHEN activity = 'DISCHARGE' THEN total_charge ELSE 0 END)::numeric(19,2) handling_charge, "
                                                                                                                                    + "SUM(CASE WHEN activity = 'TRANSHIPMENT' THEN total_charge ELSE 0 END)::numeric(19,2) transhipment_charge, "
                                                                                                                                    + "SUM(CASE WHEN activity = 'SHIFTING' THEN total_charge ELSE 0 END)::numeric(19,2) shifting_charge "
                                                                                                                            + "FROM vw_perhitungan_handling_petikemas "
                                                                                                                            + "WHERE operation = 'DISCHARGE' "
                                                                                                                            + "GROUP BY no_ppkb, curr_code) rh ON (sv.no_ppkb=rh.no_ppkb AND sv.curr_code=rh.curr_code) "
                                                                                                             + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM vw_perhitungan_penumpukan_bongkaran GROUP BY no_ppkb, curr_code) rp ON (sv.no_ppkb=rp.no_ppkb AND sv.curr_code=rp.curr_code) "
                                                                                                             + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_penumpukan_uc WHERE operation = 'DISCHARGE' GROUP BY no_ppkb, curr_code) rpu ON (sv.no_ppkb=rpu.no_ppkb AND sv.curr_code=rpu.curr_code) "
                                                                                                             + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(CASE WHEN activity = 'SHIFTING' THEN total_charge ELSE 0 END)::numeric(19,2) shifting_charge, "
                                                                                                                                    + "SUM(CASE WHEN activity = 'TRANSHIPMENT' THEN total_charge ELSE 0 END)::numeric(19,2) transhipment_charge, "
                                                                                                                                    + "SUM(CASE WHEN activity = 'LOAD' OR activity = 'DISCHARGE' THEN total_charge ELSE 0 END)::numeric(19,2) handling_charge "
                                                                                                                            + "FROM recap_handling_uc "
                                                                                                                            + "WHERE operation = 'DISCHARGE' "
                                                                                                                            + "GROUP BY no_ppkb, curr_code) rhu ON (sv.no_ppkb=rhu.no_ppkb AND sv.curr_code=rhu.curr_code) "
                                                                                                             + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_hatch_move GROUP BY no_ppkb, curr_code) rhm ON (sv.no_ppkb=rhm.no_ppkb AND sv.curr_code=rhm.curr_code) "
                                                                                                             + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_upah_buruh_hatch_move GROUP BY no_ppkb, curr_code) rubhm ON (sv.no_ppkb=rubhm.no_ppkb AND sv.curr_code=rubhm.curr_code) "
                                                                                                             + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_upah_buruh WHERE operation = 'DISCHARGE' GROUP BY no_ppkb, curr_code) rub ON (sv.no_ppkb=rub.no_ppkb AND sv.curr_code=rub.curr_code) "
                                                                                                             + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_upah_buruh_uc WHERE operation = 'DISCHARGE' GROUP BY no_ppkb, curr_code) rubu ON (sv.no_ppkb=rubu.no_ppkb AND sv.curr_code=rubu.curr_code) "
                                                                                                             + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_air_kapal GROUP BY no_ppkb, curr_code) rak ON (sv.no_ppkb=rak.no_ppkb AND sv.curr_code=rak.curr_code) "
                                                                                                     + "ORDER BY sv.curr_code"),
*/
    @NamedNativeQuery(name = "HandlingInvoice.Native.findContainerServicesChargeDetailDischargeByNoPpkb", query = 
"SELECT " +
"	(NVL(rh.handling_charge,0) + NVL(rhu.handling_charge,0) + NVL(pbm.total_charge, 0)) handling_charge, " +
"	(NVL(rp.total_charge,0) + NVL(rpu.total_charge,0)) penumpukan_charge, " +
"	NVL(rhm.total_charge,0) hatchmoves_charge, " +
"	(NVL(rh.transhipment_charge,0) + NVL(rhu.transhipment_charge,0)) transhipment_charge, " +
"	(NVL(rh.shifting_charge,0) + NVL(rhu.shifting_charge,0)) shifting_charge, " +
"	(NVL(rub.total_charge,0) + NVL(rubu.total_charge,0) + NVL(rubhm.total_charge,0)) upah_buruh_charge, " +
"	NVL(rak.total_charge,0) air_kapal_charge " +
"FROM (" +
"	SELECT CAST( ? AS VARCHAR2(30)) no_ppkb, 'IDR' curr_code FROM dual " +
"	UNION " +
"	SELECT CAST( ? AS VARCHAR2(30)) no_ppkb, 'USD' curr_code FROM dual " +
") sv LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, " +
"		SUM( " +
"		  CASE " +
"			WHEN activity = 'DISCHARGE' " +
"			THEN total_charge " +
"			ELSE 0 " +
"		  END" +
"		) handling_charge, " +
"		SUM( " +
"		  CASE " +
"			WHEN activity = 'TRANSHIPMENT' " +
"			THEN total_charge " +
"			ELSE 0 " +
"		  END" +
"		) transhipment_charge, " +
"		SUM( " +
"		  CASE " +
"			WHEN activity = 'SHIFTING' " +
"			THEN total_charge " +
"			ELSE 0 " +
"		  END" +
"		) shifting_charge " +
"	FROM vw_perhitungan_handling_petike " +
"	WHERE operation = 'DISCHARGE' " +
"	GROUP BY no_ppkb, curr_code " +
") rh ON (sv.no_ppkb  =rh.no_ppkb AND sv.curr_code=rh.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge " +
"	FROM vw_perhitungan_penumpukan_bong " +
"	GROUP BY no_ppkb, curr_code " +
") rp ON (sv.no_ppkb  =rp.no_ppkb AND sv.curr_code=rp.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge " +
"	FROM recap_penumpukan_uc " +
"	WHERE operation = 'DISCHARGE' " +
"	GROUP BY no_ppkb, curr_code " +
") rpu ON (sv.no_ppkb  =rpu.no_ppkb AND sv.curr_code=rpu.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, " +
"	SUM ( " +
"		CASE " +
"			WHEN activity = 'SHIFTING' " +
"			THEN total_charge " +
"			ELSE 0 " +
"		END" +
"	) shifting_charge, " +
"	SUM ( " +
"		CASE " +
"			WHEN activity = 'TRANSHIPMENT' " +
"			THEN total_charge " +
"			ELSE 0 " +
"		END" +
"	) transhipment_charge, " +
"	SUM ( " +
"		CASE " +
"			WHEN activity = 'LOAD' " +
"			OR activity   = 'DISCHARGE' " +
"			THEN total_charge " +
"			ELSE 0 " +
"		END" +
"	) handling_charge " +
"	FROM recap_handling_uc " +
"	WHERE operation = 'DISCHARGE' " +
"	GROUP BY no_ppkb, curr_code " +
") rhu ON (sv.no_ppkb  =rhu.no_ppkb AND sv.curr_code=rhu.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge " +
"	FROM recap_hatch_move WHERE operation='DISCHARGE' " +
"	GROUP BY no_ppkb, curr_code " +
") rhm ON (sv.no_ppkb  =rhm.no_ppkb AND sv.curr_code=rhm.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge " +
"	FROM recap_upah_buruh_hatch_move " +
"	GROUP BY no_ppkb, curr_code " +
") rubhm ON (sv.no_ppkb  =rubhm.no_ppkb AND sv.curr_code=rubhm.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge " +
"	FROM recap_upah_buruh " +
"	WHERE operation = 'DISCHARGE' " +
"	GROUP BY no_ppkb, curr_code " +
") rub ON (sv.no_ppkb  =rub.no_ppkb AND sv.curr_code=rub.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge " +
"	FROM recap_upah_buruh_uc " +
"	WHERE operation = 'DISCHARGE' " +
"	GROUP BY no_ppkb, curr_code " +
") rubu ON (sv.no_ppkb  =rubu.no_ppkb AND sv.curr_code=rubu.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge " +
"	FROM recap_air_kapal " +
"	GROUP BY no_ppkb, curr_code " +
") rak ON (sv.no_ppkb  =rak.no_ppkb AND sv.curr_code=rak.curr_code) LEFT JOIN (" +
"  SELECT no_ppkb, curr_code, SUM(total_charge) total_charge " +
"  FROM recap_other_package" +
"  WHERE main_activity = 'DISCHARGE' AND (tarif_type='PBM' OR tarif_type LIKE 'ANGSUR%' OR tarif_type IN ('STRIPPING','STUFFING')) " +
"  GROUP BY no_ppkb, curr_code" +
") pbm ON (sv.no_ppkb  =pbm.no_ppkb AND sv.curr_code=pbm.curr_code)" +
"ORDER BY sv.curr_code"),                  

/*
    @NamedNativeQuery(name = "HandlingInvoice.Native.findContainerServicesChargeDetailLoadByNoPpkb", query = "SELECT (nvl(rh.handling_charge) + nvl(rhu.handling_charge)) handling_charge, "
                                                                                                                    + "(nvl(rp.total_charge) + nvl(rpu.total_charge)) penumpukan_charge, "
                                                                                                                    + "CASE WHEN prv.activity = 2 THEN nvl(rhm.total_charge) ELSE 0::numeric(19,2) END hatchmoves_charge, "
                                                                                                                    + "(nvl(rh.transhipment_charge) + nvl(rhu.transhipment_charge)) transhipment_charge, "
                                                                                                                    + "(nvl(rh.shifting_charge) + nvl(rhu.shifting_charge)) shifting_charge, "
                                                                                                                    + "(nvl(rub.total_charge) + nvl(rubu.total_charge) + (CASE WHEN prv.activity = 2 THEN nvl(rubhm.total_charge) ELSE 0::numeric(19,2) END)) upah_buruh_charge, "
                                                                                                                    + "CASE WHEN prv.activity = 2 THEN nvl(rak.total_charge) ELSE 0::numeric(19,2) END air_kapal_charge "
                                                                                                            + "FROM (SELECT ?::varchar no_ppkb, 'IDR'::varchar curr_code "
                                                                                                                    + "UNION SELECT ?::varchar, 'USD'::varchar) AS sv "
                                                                                                                            + "JOIN planning_vessel pv ON (sv.no_ppkb=pv.no_ppkb) "
                                                                                                                                    + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                                                                                                    + "LEFT JOIN (SELECT vh.no_ppkb, vh.curr_code, SUM(CASE WHEN vh.activity = 'LOAD' AND vh.operation = 'LOAD' THEN vh.total_charge ELSE 0 END)::numeric(19,2) handling_charge, "
                                                                                                                                   + "SUM(CASE WHEN vh.activity = 'TRANSHIPMENT' AND vh.operation = 'LOAD' THEN vh.total_charge ELSE 0 END)::numeric(19,2) transhipment_charge, "
                                                                                                                                   + "SUM(CASE WHEN (vh.activity = 'SHIFTING' AND vh.operation = 'DISCHARGE' AND prv.activity = 2) OR (vh.activity = 'RESHIPPING' AND vh.operation = 'LOAD') THEN total_charge ELSE 0 END)::numeric(19,2) shifting_charge "
                                                                                                                            + "FROM vw_perhitungan_handling_petikemas vh "
                                                                                                                                    + "JOIN planning_vessel pv ON (vh.no_ppkb=pv.no_ppkb) "
                                                                                                                                            + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                                                                                                            + "GROUP BY vh.no_ppkb, vh.curr_code) rh ON (sv.no_ppkb=rh.no_ppkb AND sv.curr_code=rh.curr_code) "
                                                                                                                    + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM vw_perhitungan_penumpukan_muatan GROUP BY no_ppkb, curr_code) rp ON (sv.no_ppkb=rp.no_ppkb AND sv.curr_code=rp.curr_code) "
                                                                                                                    + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_penumpukan_uc WHERE operation = 'LOAD' GROUP BY no_ppkb, curr_code) rpu ON (sv.no_ppkb=rpu.no_ppkb AND sv.curr_code=rpu.curr_code) "
                                                                                                                    + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(CASE WHEN activity = 'SHIFTING' OR activity = 'RESHIPPING' THEN total_charge ELSE 0 END)::numeric(19,2) shifting_charge, "
                                                                                                                                           + "SUM(CASE WHEN activity = 'TRANSHIPMENT' THEN total_charge ELSE 0 END)::numeric(19,2) transhipment_charge, "
                                                                                                                                           + "SUM(CASE WHEN activity = 'LOAD' THEN total_charge ELSE 0 END)::numeric(19,2) handling_charge "
                                                                                                                                   + "FROM recap_handling_uc "
                                                                                                                                   + "WHERE operation = 'LOAD' "
                                                                                                                                   + "GROUP BY no_ppkb, curr_code) rhu ON (sv.no_ppkb=rhu.no_ppkb AND sv.curr_code=rhu.curr_code) "
                                                                                                                    + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_upah_buruh WHERE operation = 'LOAD' GROUP BY no_ppkb, curr_code) rub ON (sv.no_ppkb=rub.no_ppkb AND sv.curr_code=rub.curr_code) "
                                                                                                                    + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_upah_buruh_uc WHERE operation = 'LOAD' GROUP BY no_ppkb, curr_code) rubu ON (sv.no_ppkb=rubu.no_ppkb AND sv.curr_code=rubu.curr_code) "
                                                                                                                    + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_air_kapal GROUP BY no_ppkb, curr_code) rak ON (sv.no_ppkb=rak.no_ppkb AND sv.curr_code=rak.curr_code) "
                                                                                                                    + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_hatch_move GROUP BY no_ppkb, curr_code) rhm ON (sv.no_ppkb=rhm.no_ppkb AND sv.curr_code=rhm.curr_code) "
                                                                                                                    + "LEFT JOIN (SELECT no_ppkb, curr_code, SUM(total_charge)::numeric(19,2) total_charge FROM recap_upah_buruh_hatch_move GROUP BY no_ppkb, curr_code) rubhm ON (sv.no_ppkb=rubhm.no_ppkb AND sv.curr_code=rubhm.curr_code) "
                                                                                                            + "ORDER BY sv.curr_code"),
*/
    @NamedNativeQuery(name = "HandlingInvoice.Native.findContainerServicesChargeDetailLoadByNoPpkb", query = 
"SELECT" +
"	(NVL(rh.handling_charge,0) + NVL(rhu.handling_charge,0) + NVL(pbm.total_charge, 0)) handling_charge,  " +
"	(NVL(rp.total_charge,0) + NVL(rpu.total_charge,0)) penumpukan_charge,  " +
"	NVL(rhm.total_charge,0) hatchmoves_charge,  " +
"	(NVL(rh.transhipment_charge,0) + NVL(rhu.transhipment_charge,0)) transhipment_charge,  " +
"	(NVL(rh.shifting_charge,0) + NVL(rhu.shifting_charge,0)) shifting_charge,  " +
"	(NVL(rub.total_charge,0) + NVL(rubu.total_charge,0) + (  " +
"	CASE  " +
"		WHEN prv.activity = 2  " +
"		THEN NVL(rubhm.total_charge,0)  " +
"		ELSE 0  " +
"	END)" +
"	) upah_buruh_charge,  " +
"	CASE  " +
"		WHEN prv.activity = 2  " +
"		THEN NVL(rak.total_charge,0)  " +
"		ELSE 0  " +
"	END air_kapal_charge  " +
"FROM  " +
"(" +
"	SELECT CAST( ? AS VARCHAR2(30)) no_ppkb, 'IDR' curr_code FROM DUAL  " +
"	UNION  " +
"	SELECT CAST( ? AS VARCHAR2(30)) no_ppkb, 'USD' curr_code FROM DUAL  " +
") sv  " +
"JOIN planning_vessel pv ON (sv.no_ppkb=pv.no_ppkb) JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) LEFT JOIN (" +
"	SELECT vh.no_ppkb, vh.curr_code, SUM(  " +
"		CASE  " +
"			WHEN vh.activity = 'LOAD'  " +
"			AND vh.operation = 'LOAD'  " +
"			THEN vh.total_charge  " +
"			ELSE 0  " +
"		END" +
"	) handling_charge, SUM(  " +
"		CASE  " +
"			WHEN vh.activity = 'TRANSHIPMENT'  " +
"			AND vh.operation = 'LOAD'  " +
"			THEN vh.total_charge  " +
"			ELSE 0  " +
"		END" +
"	) transhipment_charge, SUM(  " +
"		CASE  " +
"			WHEN (vh.activity = 'SHIFTING'  " +
"			AND vh.operation  = 'DISCHARGE'  " +
"			AND prv.activity  = 2)  " +
"			OR (vh.activity = 'RESHIPPING'  " +
"			AND vh.operation  = 'LOAD')  " +
"			THEN total_charge  " +
"			ELSE 0  " +
"		END" +
"	) shifting_charge  " +
"	FROM vw_perhitungan_handling_petike vh JOIN planning_vessel pv ON (vh.no_ppkb=pv.no_ppkb) JOIN preservice_vessel prv " +
"	ON (pv.booking_code=prv.booking_code)  " +
"	GROUP BY vh.no_ppkb, vh.curr_code  " +
") rh ON (sv.no_ppkb=rh.no_ppkb AND sv.curr_code =rh.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge  " +
"	FROM vw_perhitungan_penumpukan_muat  " +
"	GROUP BY no_ppkb, curr_code  " +
") rp ON (sv.no_ppkb  =rp.no_ppkb AND sv.curr_code=rp.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge  " +
"	FROM recap_penumpukan_uc  " +
"	WHERE operation = 'LOAD'  " +
"	GROUP BY no_ppkb, curr_code  " +
") rpu ON (sv.no_ppkb  =rpu.no_ppkb AND sv.curr_code=rpu.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(  " +
"		CASE  " +
"			WHEN activity = 'SHIFTING'  " +
"			OR activity = 'RESHIPPING'  " +
"			THEN total_charge  " +
"			ELSE 0  " +
"		END" +
"	) shifting_charge, SUM(  " +
"		CASE  " +
"			WHEN activity = 'TRANSHIPMENT'  " +
"			THEN total_charge  " +
"			ELSE 0  " +
"		END" +
"	) transhipment_charge, SUM(  " +
"		CASE  " +
"			WHEN activity = 'LOAD'  " +
"			THEN total_charge  " +
"			ELSE 0  " +
"		END" +
"	) handling_charge  " +
"	FROM recap_handling_uc  " +
"	WHERE operation = 'LOAD'  " +
"	GROUP BY no_ppkb, curr_code  " +
") rhu ON (sv.no_ppkb  =rhu.no_ppkb AND sv.curr_code=rhu.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge  " +
"	FROM recap_upah_buruh  " +
"	WHERE operation = 'LOAD'  " +
"	GROUP BY no_ppkb, curr_code  " +
") rub ON (sv.no_ppkb  =rub.no_ppkb AND sv.curr_code=rub.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge  " +
"	FROM recap_upah_buruh_uc  " +
"	WHERE operation = 'LOAD'  " +
"	GROUP BY no_ppkb, curr_code  " +
") rubu ON (sv.no_ppkb  =rubu.no_ppkb AND sv.curr_code=rubu.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge  " +
"	FROM recap_air_kapal  " +
"	GROUP BY no_ppkb, curr_code  " +
") rak ON (sv.no_ppkb  =rak.no_ppkb AND sv.curr_code=rak.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge  " +
"	FROM recap_hatch_move WHERE operation='LOAD' " +
"	GROUP BY no_ppkb, curr_code  " +
") rhm ON (sv.no_ppkb  =rhm.no_ppkb AND sv.curr_code=rhm.curr_code) LEFT JOIN (" +
"	SELECT no_ppkb, curr_code, SUM(total_charge) total_charge  " +
"	FROM recap_upah_buruh_hatch_move  " +
"	GROUP BY no_ppkb, curr_code  " +
") rubhm ON (sv.no_ppkb  =rubhm.no_ppkb AND sv.curr_code=rubhm.curr_code) LEFT JOIN (" +
"  SELECT no_ppkb, curr_code, SUM(total_charge) total_charge " +
"  FROM recap_other_package" +
"  WHERE main_activity = 'LOAD' AND (tarif_type='PBM' OR tarif_type LIKE 'ANGSUR%' OR tarif_type IN ('STRIPPING','STUFFING')) " +
"  GROUP BY no_ppkb, curr_code" +
") pbm ON (sv.no_ppkb  =pbm.no_ppkb AND sv.curr_code=pbm.curr_code)" +
"ORDER BY sv.curr_code"),
    @NamedNativeQuery(name = "HandlingInvoice.Native.getUpahLembur", 
query = "SELECT A.TOTAL_TAGIHAN " +
"FROM INVOICE A INNER JOIN PERHITUNGAN_NOTA_MANUAL B ON A.NO_REG=B.NO_REG " +
"INNER JOIN (SELECT DISTINCT NO_REG FROM PERHITUNGAN_NOTA_MANUAL_DETAIL WHERE ACTIVITY_TYPE=6) C ON B.NO_REG=C.NO_REG " +
"INNER JOIN NO_PERHITUNGAN_BENTUK_TIGA D ON B.NO_BENTUK_TIGA=D.NO_REGISTRASI " +
"WHERE D.NO_PPKB=?")

})

public class HandlingInvoice implements Serializable, EntityAuditable {
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
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_invoice", nullable = false, length = 30)
    private String noInvoice;
    @Column(name = "operation", length = 15)
    private String operation;
    @Column(name = "charge", precision = 19, scale = 2)
    private BigDecimal charge;
    @Column(name = "ppn", precision = 19, scale = 2)
    private BigDecimal ppn;
    @Column(name = "materai", precision = 19, scale = 2)
    private BigDecimal materai;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
    @Column(name = "curr_code", length = 5)
    private String currCode;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;

    public HandlingInvoice() {
    }

    public HandlingInvoice(String noInvoice) {
        this.noInvoice = noInvoice;
    }

    public String getNoInvoice() {
        return noInvoice;
    }

    public void setNoInvoice(String noInvoice) {
        this.noInvoice = noInvoice;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public BigDecimal getPpn() {
        return ppn;
    }

    public void setPpn(BigDecimal ppn) {
        this.ppn = ppn;
    }

    public BigDecimal getMaterai() {
        return materai;
    }

    public void setMaterai(BigDecimal materai) {
        this.materai = materai;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noInvoice != null ? noInvoice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HandlingInvoice)) {
            return false;
        }
        HandlingInvoice other = (HandlingInvoice) object;
        if ((this.noInvoice == null && other.noInvoice != null) || (this.noInvoice != null && !this.noInvoice.equals(other.noInvoice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.HandlingInvoice[noInvoice=" + noInvoice + "]";
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

}
