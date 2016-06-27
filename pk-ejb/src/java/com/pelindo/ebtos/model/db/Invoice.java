/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterBank;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "invoice")
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findByNoInvoice", query = "SELECT i FROM Invoice i WHERE i.noInvoice = :noInvoice AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findUnpaidByNoInvoice", query = "SELECT i FROM Invoice i WHERE i.noInvoice = :noInvoice AND i.paymentStatus = '" + Invoice.UNPAID_STATUS + "' AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findNotJournaledInvoiceByKasir", query = "SELECT i FROM Invoice i WHERE i.kasir = :kasir AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findByJumlahTagihan", query = "SELECT i FROM Invoice i WHERE i.jumlahTagihan = :jumlahTagihan AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findByMaterai", query = "SELECT i FROM Invoice i WHERE i.materai = :materai AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findByPpn", query = "SELECT i FROM Invoice i WHERE i.ppn = :ppn AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findByTotalTagihan", query = "SELECT i FROM Invoice i WHERE i.totalTagihan = :totalTagihan AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findByPaymentStatus", query = "SELECT i FROM Invoice i WHERE i.paymentStatus = :paymentStatus AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findJournalInvoiceCash", query = "SELECT i "
                                                                + "FROM Invoice i "
                                                                + "WHERE i.kasir = :kasir AND i.totalTagihan > 0 AND i.paymentType = 'CASH' AND i.masterCurrency.currCode = :currCode AND i.journaledDate IS NULL AND i.paymentStatus = 'PAYMENT' AND i.registration.masterCustomer.custCode = :custCode AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findRegistrationByPpkbHandlingAndYard", query = "SELECT i.registration FROM Invoice i WHERE i.ppkbHandling = :noPpkb AND i.registration.masterCy.yard = :yard AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findRegistrationByPpkbHandlingAndCurrency", query = "SELECT i.registration FROM Invoice i WHERE i.ppkbHandling = :noPpkb AND i.masterCurrency.currCode = :currCode AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findByNoAndGuid", query = "SELECT i FROM Invoice i WHERE i.noInvoice = :noInvoice AND i.masterBank.guid = :guid AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findByGuid", query = "SELECT i FROM Invoice i WHERE i.masterBank.guid = :guid AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findJournalInvoiceKredit", query = "SELECT i FROM Invoice i WHERE i.noInvoice = :noInvoice AND i.journaledDate IS NULL AND i.cancelInvoice <> TRUE"),
    @NamedQuery(name = "Invoice.findByRegForUpdatePrintNumber", query = "SELECT i FROM Invoice i WHERE i.noReg = :noReg")})
    
@NamedNativeQueries({
//    @NamedNativeQuery(name = "Invoice.Native.generateId", query = "SELECT MAX(substring(no_invoice,8,7))FROM invoice WHERE substring(no_invoice,3,4) = ?"),

    @NamedNativeQuery(name = "Invoice.Native.generateId", query = "SELECT MAX(substr(no_invoice,8,7))FROM invoice WHERE substr(no_invoice,3,4) = ?"),
    @NamedNativeQuery(name = "Invoice.Native.findByReg", query = "SELECT i.no_invoice, i.total_tagihan, CASE WHEN i.payment_status = 'PAYMENT' THEN 'PAID' ELSE 'UNPAID' END, c.symbol, i.payment_tunai, i.payment_transfer, i.payment_cek, i.payment_bg, i.payment_type FROM invoice i, m_currency c WHERE i.no_reg = ? AND i.curr_code = c.curr_code"),
    @NamedNativeQuery(name = "Invoice.Native.findNoInvoice", query = "SELECT i.no_invoice FROM invoice i,registration r WHERE i.no_reg = ? AND i.no_reg=r.no_reg"),
/*    
    @NamedNativeQuery(name = "Invoice.Native.findTotalNotJournaledInvoiceByKasir", query = "SELECT SUM(CASE WHEN i.curr_code = 'IDR' THEN i.total_tagihan ELSE 0 END), "
    + "SUM(CASE WHEN i.curr_code = 'USD' THEN i.total_tagihan ELSE 0 END) "
    + "FROM invoice i WHERE i.kasir = ? AND i.jurnal_date is null AND i.cancel_invoice <> TRUE"),
*/
    @NamedNativeQuery(name = "Invoice.Native.findTotalNotJournaledInvoiceByKasir", query =     
"SELECT SUM( " 
+"  CASE " 
+"    WHEN i.curr_code = 'IDR' " 
+"    THEN i.total_tagihan " 
+"    ELSE 0 " 
+"  END), " 
+"  SUM( " 
+"  CASE " 
+"    WHEN i.curr_code = 'USD' " 
+"    THEN i.total_tagihan " 
+"    ELSE 0 " 
+"  END) " 
+"FROM invoice i " 
+"WHERE i.kasir         = ? " 
+"AND i.journaled_date IS NULL " 
+"AND i.cancel_invoice <> 1"),    
/*
    @NamedNativeQuery(name = "Invoice.Native.findInvoiceCredit", query = "SELECT r.no_reg, i.no_invoice, i.total_tagihan, i.payment_status, c.symbol, mc.name, CASE WHEN i.no_faktur_pajak IS NULL AND i.no_invoice IS NULL THEN 'printable' ELSE 'unprintable' END AS status_print, i.no_faktur_pajak, r.service_code, r.no_ppkb, c.country FROM invoice i, m_currency c, m_customer mc, registration r  WHERE i.curr_code = c.curr_code AND i.payment_type = 'KREDIT' AND i.payment_status = 'UNPAID' AND r.cust_code = mc.cust_code AND r.no_reg = i.no_reg AND i.verification_code IS NULL AND i.total_tagihan > 0::numeric "),
*/

    @NamedNativeQuery(name = "Invoice.Native.findInvoiceCredit", query =     
"SELECT r.no_reg, " 
+"  i.no_invoice, " 
+"  i.total_tagihan, " 
+"  i.payment_status, " 
+"  c.symbol, " 
+"  mc.name, " 
+"  CASE " 
+"    WHEN i.no_faktur_pajak IS NULL " 
+"    AND i.no_invoice       IS NULL " 
+"    THEN 'printable' " 
+"    ELSE 'unprintable' " 
+"  END AS status_print, " 
+"  i.no_faktur_pajak, " 
+"  r.service_code, " 
+"  r.no_ppkb, " 
+"  c.country " 
+"FROM invoice i, " 
+"  m_currency c, " 
+"  m_customer mc, " 
+"  registration r " 
+"WHERE i.curr_code        = c.curr_code " 
+"AND i.payment_type       = 'KREDIT' " 
+"AND i.payment_status     = 'UNPAID' " 
+"AND r.cust_code          = mc.cust_code " 
+"AND r.no_reg             = i.no_reg " 
+"AND i.verification_code IS NULL " 
+"AND i.total_tagihan      > 0"),    

/*
    @NamedNativeQuery(name = "Invoice.Native.findJournalDetailCash", query = "SELECT rekening, SUM(debit) debit, SUM(kredit) kredit "
                                                                            + "FROM jurnal_detail_cash(?, ?, ?, ?) "
                                                                            + "GROUP BY rekening "
                                                                            + "ORDER BY rekening;"),
*/
    @NamedNativeQuery(name = "Invoice.Native.findJournalDetailCash", query = 
"SELECT rekening, " 
+"  SUM(debit) debit, " 
+"  SUM(kredit) kredit " 
+"FROM TABLE(jurnal_detail_cash(?, ?, ?, ?)) " 
+"GROUP BY rekening " 
+"ORDER BY rekening"),
                                                                            
    @NamedNativeQuery(name = "Invoice.Native.findInvoice", query = "SELECT no_reg FROM invoice WHERE ppkb_handling=?"),
    @NamedNativeQuery(name = "Invoice.Native.findNoInvoiceByPpkbHandling", query = "SELECT no_invoice FROM invoice WHERE ppkb_handling=?"),
    @NamedNativeQuery(name = "Invoice.Native.findInvoiceBatalNota", query = "select distinct i.no_invoice,i.no_reg,m.name,i.created_date,i.total_tagihan,r.service_code,ms.service_name,r.cust_code from invoice i,registration r,m_customer m,m_service ms where r.no_reg=i.no_reg AND m.cust_code=r.cust_code AND ms.service_code=r.service_code AND i.no_invoice=?"),

/*    
    @NamedNativeQuery(name = "Invoice.Native.findBatalNota", query = "select distinct i.no_invoice,i.no_reg,m.name,i.created_date,i.total_tagihan,r.service_code,ms.service_name,'dev' || r.cust_code from invoice i,registration r,m_customer m,delivery_service d,m_service ms where r.no_reg=i.no_reg AND m.cust_code=r.cust_code AND ms.service_code=r.service_code AND d.no_reg=i.no_reg AND i.no_invoice IS NOT NULL AND d.job_slip NOT IN (select s.job_slip from service_gate_delivery s where s.date_out =null AND s.job_slip=d.job_slip) UNION select distinct i.no_invoice,i.no_reg,m.name,i.created_date,i.total_tagihan,r.service_code,ms.service_name,'rec' || r.cust_code  from invoice i,registration r,m_customer m,receiving_service d,m_service ms where r.no_reg=i.no_reg AND m.cust_code=r.cust_code AND d.no_reg=i.no_reg AND ms.service_code=r.service_code AND i.no_invoice IS NOT NULL AND d.job_slip NOT IN (select s.job_slip from service_gate_receiving s WHERE s.job_slip=d.job_slip)UNION select distinct i.no_invoice,i.no_reg,m.name,i.created_date,i.total_tagihan,r.service_code,ms.service_name,'canel' || r.cust_code  from invoice i,registration r,m_customer m,cancel_loading_service d,m_service ms where r.no_reg=i.no_reg AND m.cust_code=r.cust_code AND ms.service_code=r.service_code AND i.no_invoice IS NOT NULL AND d.no_reg=i.no_reg AND d.job_slip NOT IN (select s.job_slip from service_gate_delivery s WHERE s.job_slip=d.job_slip AND s.date_out =null)"),
*/

    @NamedNativeQuery(name = "Invoice.Native.findBatalNota", query =     
"SELECT DISTINCT i.no_invoice, " 
+"  i.no_reg, " 
+"  m.name, " 
+"  i.created_date, " 
+"  i.total_tagihan, " 
+"  r.service_code, " 
+"  ms.service_name, " 
+"  'dev' " 
+"  || r.cust_code " 
+"FROM invoice i, " 
+"  registration r, " 
+"  m_customer m, " 
+"  delivery_service d, " 
+"  m_service ms " 
+"WHERE r.no_reg      =i.no_reg " 
+"AND m.cust_code     =r.cust_code " 
+"AND ms.service_code =r.service_code " 
+"AND d.no_reg        =i.no_reg " 
+"AND i.no_invoice   IS NOT NULL " 
+"AND d.job_slip NOT IN " 
+"  (SELECT s.job_slip " 
+"  FROM service_gate_delivery s " 
+"  WHERE s.date_out =NULL " 
+"  AND s.job_slip   =d.job_slip " 
+"  ) " 
+"UNION " 
+"SELECT DISTINCT i.no_invoice, " 
+"  i.no_reg, " 
+"  m.name, " 
+"  i.created_date, " 
+"  i.total_tagihan, " 
+"  r.service_code, " 
+"  ms.service_name, " 
+"  'rec' " 
+"  || r.cust_code " 
+"FROM invoice i, " 
+"  registration r, " 
+"  m_customer m, " 
+"  receiving_service d, " 
+"  m_service ms " 
+"WHERE r.no_reg      =i.no_reg " 
+"AND m.cust_code     =r.cust_code " 
+"AND d.no_reg        =i.no_reg " 
+"AND ms.service_code =r.service_code " 
+"AND i.no_invoice   IS NOT NULL " 
+"AND d.job_slip NOT IN " 
+"  (SELECT s.job_slip FROM service_gate_receiving s WHERE s.job_slip=d.job_slip " 
+"  ) " 
+"UNION " 
+"SELECT DISTINCT i.no_invoice, " 
+"  i.no_reg, " 
+"  m.name, " 
+"  i.created_date, " 
+"  i.total_tagihan, " 
+"  r.service_code, " 
+"  ms.service_name, " 
+"  'canel' " 
+"  || r.cust_code " 
+"FROM invoice i, " 
+"  registration r, " 
+"  m_customer m, " 
+"  cancel_loading_service d, " 
+"  m_service ms " 
+"WHERE r.no_reg      =i.no_reg " 
+"AND m.cust_code     =r.cust_code " 
+"AND ms.service_code =r.service_code " 
+"AND i.no_invoice   IS NOT NULL " 
+"AND d.no_reg        =i.no_reg " 
+"AND d.job_slip NOT IN " 
+"  (SELECT s.job_slip " 
+"  FROM service_gate_delivery s " 
+"  WHERE s.job_slip=d.job_slip " 
+"  AND s.date_out IS NULL " 
+"  )"),    

/// Cek ulang hasil upgrade function    
    @NamedNativeQuery(name = "Invoice.Native.insertIntoJurnal", query = "SELECT * FROM table(insert_into_t_jurnal(?, ?, ?, ?, ?, ?, ?))"),
    @NamedNativeQuery(name = "Invoice.Native.insertIntoJurnalDetail", query = "SELECT insert_into_t_jurnal_detail(?, ?, ?, ?, ?, ?) from dual"),
    @NamedNativeQuery(name = "Invoice.Native.getKurs", query = "SELECT get_kurs(?) from dual"),
    @NamedNativeQuery(name = "Invoice.Native.getUtipInfo", query = "SELECT * FROM table(get_utip_info(?))"),
/// Cek ulang hasil upgrade function
    
    @NamedNativeQuery(name = "Invoice.Native.findSPTWithInterval", query = "SELECT "
                                                                        + "'A', " //1
                                                                        + "'2', " //2
                                                                        + "mc.kode_status_pajak, " //3
                                                                        + "'1', " //4
                                                                        + "'0', " //5
                                                                        + "mc.npwp, " //6
                                                                        + "mc.name, " //7
                                                                        + "i.no_faktur_pajak, " //8
                                                                        + "'0', " //9
                                                                        + "'', " //10
                                                                        + "'', " //11
                                                                        + "to_char(i.payment_date, 'dd/mm/yyyy'), " //12
                                                                        + "'', " //13
                                                                        + "to_char(i.payment_date, 'mmmm'), " //14
                                                                        + "to_char(i.payment_date, 'yyyy'), " //15
                                                                        + "'', " //16
                                                                        + "i.jumlah_tagihan, " //17
                                                                        + "i.ppn, " //18
                                                                        + "'0', " //19
                                                                        + "mcr.language, "
                                                                        + "mcr.country, "
                                                                        + "mcr.default_fraction_digits "
                                                                    + "FROM "
                                                                        + "invoice i "
                                                                            + "JOIN registration r ON (i.no_reg=r.no_reg) "
                                                                                + "JOIN m_customer mc ON (r.cust_code=mc.cust_code) "
                                                                            + "JOIN m_currency mcr ON (i.curr_code=mcr.curr_code) "
                                                                    + "WHERE i.curr_code = ? AND i.no_faktur_pajak IS NOT NULL AND to_char(i.payment_date, 'mm') = ? AND to_char(i.payment_date, 'yyyy') = ?"),
                                                                    
/*                                                                    
    @NamedNativeQuery(name = "Invoice.Native.findJournalByKasir", query = "SELECT ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, get_kurs(i.curr_code), i.payment_type, i.no_invoice, ms.service_code, r.cust_code, mcs.name, SUM(i.total_tagihan), COUNT(*), rji.sumber, mc.language, mc.country, mc.default_fraction_digits "
                                                                            + "FROM "
                                                                                + "((invoice i "
                                                                                    + "JOIN (registration r "
                                                                                        + "JOIN m_customer mcs ON (r.cust_code=mcs.cust_code)) ON (i.no_reg=r.no_reg) "
                                                                                        + "JOIN m_service ms ON (r.service_code=ms.service_code)) "
                                                                                + "JOIN m_currency mc ON (i.curr_code=mc.curr_code)) "
                                                                                + "JOIN recap_jurnal_invoice rji ON (i.no_invoice = rji.no_invoice) "
                                                                            + "WHERE i.journaled_date IS NULL AND i.payment_type = 'CASH' AND i.payment_status = 'PAYMENT' AND i.kasir = ? "
                                                                            + "GROUP BY ms.tipe_jurnal_id, i.no_invoice, ms.service_code, i.curr_code, mc.mata_uang_id, get_kurs(i.curr_code), i.payment_type, r.cust_code, mcs.name, rji.sumber, mc.language, mc.country, mc.default_fraction_digits "
                                                                            + "UNION ALL "
                                                                            + "SELECT ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, get_kurs(i.curr_code), i.payment_type, i.no_invoice, r.service_code, mcs.cust_code, mcs.name, i.total_tagihan, 1, '', mc.language, mc.country, mc.default_fraction_digits "
                                                                            + "FROM "
                                                                                + "(invoice i "
                                                                                    + "JOIN (registration r "
                                                                                        + "JOIN m_customer mcs ON (r.cust_code=mcs.cust_code)) ON (i.no_reg=r.no_reg) "
                                                                                        + "JOIN m_service ms ON (r.service_code=ms.service_code)) "
                                                                                + "JOIN m_currency mc ON (i.curr_code=mc.curr_code) "
                                                                            + "WHERE i.journaled_date IS NULL AND i.payment_type = 'KREDIT' AND i.no_faktur_pajak IS NOT NULL AND i.cancel_invoice <> TRUE AND i.created_date::date <= ?::date;"),
*/

    @NamedNativeQuery(name = "Invoice.Native.findJournalByKasir", query =   
"SELECT ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  get_kurs(i.curr_code), " 
+"  i.payment_type, " 
+"  i.no_invoice, " 
+"  ms.service_code, " 
+"  r.cust_code, " 
+"  mcs.name, " 
+"  SUM(i.total_tagihan), " 
+"  COUNT(*), " 
+"  rji.sumber, " 
+"  dbms_lob.substr(mc.language,4000,1) language, " 
+"  dbms_lob.substr(mc.country, 4000,1) country, " 
+"  mc.default_fraction_digits " 
+"FROM ((invoice i " 
+"JOIN (registration r " 
+"JOIN m_customer mcs " 
+"ON (r.cust_code=mcs.cust_code)) " 
+"ON (i.no_reg   =r.no_reg) " 
+"JOIN m_service ms " 
+"ON (r.service_code=ms.service_code)) " 
+"JOIN m_currency mc " 
+"ON (i.curr_code=mc.curr_code)) " 
+"JOIN recap_jurnal_invoice rji " 
+"ON (i.no_invoice        = rji.no_invoice) " 
+"WHERE i.journaled_date IS NULL " 
+"AND i.payment_type      = 'CASH' " 
+"AND i.payment_status    = 'PAYMENT' " 
+"AND i.kasir             = ? " 
+"GROUP BY ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  get_kurs(i.curr_code), " 
+"  rji.sumber, " 
+"  i.payment_type, " 
+"  i.no_invoice, " 
+"  ms.service_code, " 
+"  r.cust_code, " 
+"  mcs.name, " 
+"  dbms_lob.substr(mc.language,4000,1), " 
+"  dbms_lob.substr(mc.country, 4000,1), " 
+"  mc.default_fraction_digits " 
+"UNION ALL " 
+"SELECT ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  get_kurs(i.curr_code), " 
+"  i.payment_type, " 
+"  i.no_invoice, " 
+"  r.service_code, " 
+"  mcs.cust_code, " 
+"  mcs.name, " 
+"  i.total_tagihan, " 
+"  1, " 
+"  '', " 
+"  dbms_lob.substr(mc.language, 4000, 1) language, " 
+"  dbms_lob.substr(mc.country, 4000, 1) country, " 
+"  mc.default_fraction_digits " 
+"FROM (invoice i " 
+"JOIN (registration r " 
+"JOIN m_customer mcs " 
+"ON (r.cust_code=mcs.cust_code)) " 
+"ON (i.no_reg   =r.no_reg) " 
+"JOIN m_service ms " 
+"ON (r.service_code=ms.service_code)) " 
+"JOIN m_currency mc " 
+"ON (i.curr_code         =mc.curr_code) " 
+"WHERE i.journaled_date IS NULL " 
+"AND i.payment_type      = 'KREDIT' " 
+"AND i.no_faktur_pajak  IS NOT NULL " 
+"AND i.cancel_invoice   <> 1 " 
+"AND i.created_date     <= CAST( ? AS DATE)"),                                                                            

/*
    @NamedNativeQuery(name = "Invoice.Native.findJournalsPerInvoice", query = "SELECT *" +
"FROM " +
"	(SELECT ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, get_kurs(i.curr_code), i.payment_type, i.no_invoice, ms.service_code, r.cust_code, mcs.name, SUM(i.total_tagihan), COUNT(*), rji.sumber, mc.language, mc.country, mc.default_fraction_digits, i.created_date \n" +
"	FROM \n" +
"	    ((invoice i \n" +
"		JOIN (registration r \n" +
"		    JOIN m_customer mcs ON (r.cust_code=mcs.cust_code)) ON (i.no_reg=r.no_reg) \n" +
"		    JOIN m_service ms ON (r.service_code=ms.service_code)) \n" +
"	    JOIN m_currency mc ON (i.curr_code=mc.curr_code)) \n" +
"	    JOIN recap_jurnal_invoice rji ON (i.no_invoice = rji.no_invoice) \n" +
"	WHERE i.journaled_date IS NULL AND i.payment_type = 'CASH' AND i.payment_status = 'PAYMENT' \n" +
"	GROUP BY ms.tipe_jurnal_id, i.no_invoice, i.created_date, ms.service_code, i.curr_code, mc.mata_uang_id, get_kurs(i.curr_code), i.payment_type, r.cust_code, mcs.name, rji.sumber, mc.language, mc.country, mc.default_fraction_digits \n" +
"	UNION ALL \n" +
"	SELECT ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, get_kurs(i.curr_code), i.payment_type, i.no_invoice, r.service_code, mcs.cust_code, mcs.name, i.total_tagihan, 1, '', mc.language, mc.country, mc.default_fraction_digits, i.created_date \n" +
"	FROM \n" +
"	    (invoice i \n" +
"		JOIN (registration r \n" +
"		    JOIN m_customer mcs ON (r.cust_code=mcs.cust_code)) ON (i.no_reg=r.no_reg) \n" +
"		    JOIN m_service ms ON (r.service_code=ms.service_code)) \n" +
"	    JOIN m_currency mc ON (i.curr_code=mc.curr_code) \n" +
"	WHERE i.journaled_date IS NULL AND i.payment_type = 'KREDIT' AND i.no_faktur_pajak IS NOT NULL AND i.cancel_invoice <> TRUE AND i.created_date::date <= ?::date) t\n" +
"ORDER BY t.payment_type, t.created_date;"),
*/
    @NamedNativeQuery(name = "Invoice.Native.findJournalsPerInvoice", query = 
"SELECT * " 
+"FROM " 
+"  (SELECT ms.tipe_jurnal_id, " 
+"    i.curr_code, " 
+"    mc.mata_uang_id, " 
+"    get_kurs(i.curr_code), " 
+"    i.payment_type, " 
+"    i.no_invoice, " 
+"    ms.service_code, " 
+"    r.cust_code, " 
+"    mcs.name, " 
+"    SUM(i.total_tagihan), " 
+"    COUNT(*), " 
+"    rji.sumber, " 
+"    dbms_lob.substr(mc.language, 4000,1) language, " 
+"    dbms_lob.substr(mc.country, 4000, 1), " 
+"    mc.default_fraction_digits, " 
+"    i.created_date " 
+"  FROM ((invoice i " 
+"  JOIN (registration r " 
+"  JOIN m_customer mcs " 
+"  ON (r.cust_code=mcs.cust_code)) " 
+"  ON (i.no_reg   =r.no_reg) " 
+"  JOIN m_service ms " 
+"  ON (r.service_code=ms.service_code)) " 
+"  JOIN m_currency mc " 
+"  ON (i.curr_code=mc.curr_code)) " 
+"  JOIN recap_jurnal_invoice rji " 
+"  ON (i.no_invoice        = rji.no_invoice) " 
+"  WHERE i.journaled_date IS NULL " 
+"  AND i.payment_type      = 'CASH' " 
+"  AND i.payment_status    = 'PAYMENT' " 
+"  GROUP BY ms.tipe_jurnal_id, " 
+"    i.curr_code, " 
+"    mc.mata_uang_id, " 
+"    get_kurs(i.curr_code), " 
+"    i.payment_type, " 
+"    i.no_invoice, " 
+"    ms.service_code, " 
+"    r.cust_code, " 
+"    mcs.name, " 
+"    rji.sumber, " 
+"    dbms_lob.substr(mc.language, 4000,1), " 
+"    dbms_lob.substr(mc.country, 4000, 1), " 
+"    mc.default_fraction_digits, " 
+"    i.created_date " 
+"  UNION ALL " 
+"  SELECT ms.tipe_jurnal_id, " 
+"    i.curr_code, " 
+"    mc.mata_uang_id, " 
+"    get_kurs(i.curr_code), " 
+"    i.payment_type, " 
+"    i.no_invoice, " 
+"    r.service_code, " 
+"    mcs.cust_code, " 
+"    mcs.name, " 
+"    i.total_tagihan, " 
+"    1, " 
+"    '', " 
+"    dbms_lob.substr(mc.language, 4000, 1) language, " 
+"    dbms_lob.substr(mc.country, 4000, 1) country, " 
+"    mc.default_fraction_digits, " 
+"    i.created_date " 
+"  FROM (invoice i " 
+"  JOIN (registration r " 
+"  JOIN m_customer mcs " 
+"  ON (r.cust_code=mcs.cust_code)) " 
+"  ON (i.no_reg   =r.no_reg) " 
+"  JOIN m_service ms " 
+"  ON (r.service_code=ms.service_code)) " 
+"  JOIN m_currency mc " 
+"  ON (i.curr_code         =mc.curr_code) " 
+"  WHERE i.journaled_date IS NULL " 
+"  AND i.payment_type      = 'KREDIT' " 
+"  AND i.no_faktur_pajak  IS NOT NULL " 
+"  AND i.cancel_invoice   <> 'TRUE' " 
+"  AND i.created_date     <= CAST( ? AS DATE) " 
+"  ) t " 
+"ORDER BY t.payment_type, " 
+"  t.created_date"),

/*
    @NamedNativeQuery(name = "Invoice.Native.findJKMCandidateByKasirAndDate", query = "SELECT "
                                                                                + "ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, i.payment_type, r.cust_code, mcs.name, rji.sumber, SUM(i.total_tagihan), COUNT(*), mc.language, mc.country, mc.default_fraction_digits "
                                                                            + "FROM "
                                                                                + "((invoice i "
                                                                                    + "JOIN (registration r "
                                                                                        + "JOIN m_customer mcs ON (r.cust_code=mcs.cust_code)) ON (i.no_reg=r.no_reg) "
                                                                                        + "JOIN m_service ms ON (r.service_code=ms.service_code)) "
                                                                                    + "JOIN m_currency mc ON (i.curr_code=mc.curr_code)) "
                                                                                    + "LEFT JOIN recap_jurnal_invoice rji ON (i.no_invoice = rji.no_invoice) "
                                                                            + "WHERE i.journaled_date IS NULL AND i.payment_type = 'CASH' AND i.payment_status = 'PAYMENT' AND i.kasir = ? AND i.payment_date::date = ? AND i.cancel_invoice <> TRUE "
                                                                            + "GROUP BY ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, i.payment_type, r.cust_code, mcs.name, rji.sumber, mc.language, mc.country, mc.default_fraction_digits "
                                                                            + "HAVING rji.sumber IS NULL;"),
*/

    @NamedNativeQuery(name = "Invoice.Native.findJKMCandidateByKasirAndDate", query =   
"SELECT ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  i.payment_type, " 
+"  r.cust_code, " 
+"  mcs.name, " 
+"  rji.sumber, " 
+"  SUM(i.total_tagihan), " 
+"  COUNT(*), " 
+"  dbms_lob.substr(mc.language, 4000, 0) language, " 
+"  dbms_lob.substr(mc.country, 4000, 0) country, " 
+"  mc.default_fraction_digits " 
+"FROM ((invoice i " 
+"JOIN (registration r " 
+"JOIN m_customer mcs " 
+"ON (r.cust_code=mcs.cust_code)) " 
+"ON (i.no_reg   =r.no_reg) " 
+"JOIN m_service ms " 
+"ON (r.service_code=ms.service_code)) " 
+"JOIN m_currency mc " 
+"ON (i.curr_code=mc.curr_code)) " 
+"LEFT JOIN recap_jurnal_invoice rji " 
+"ON (i.no_invoice        = rji.no_invoice) " 
+"WHERE i.journaled_date IS NULL " 
+"AND i.payment_type      = 'CASH' " 
+"AND i.payment_status    = 'PAYMENT' " 
+"AND i.kasir             = ? " 
+"AND i.payment_date      = ? " 
+"AND i.cancel_invoice   <> 'TRUE' " 
+"GROUP BY ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  i.payment_type, " 
+"  r.cust_code, " 
+"  mcs.name, " 
+"  rji.sumber, " 
+"  dbms_lob.substr(mc.language, 4000, 0), " 
+"  dbms_lob.substr(mc.country, 4000, 0), " 
+"  mc.default_fraction_digits " 
+"HAVING rji.sumber IS NULL"),

/*                                                                            
    @NamedNativeQuery(name = "Invoice.Native.findJKMCandidateByKasir", query = "SELECT "
                                                                                + "ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, i.payment_type, r.cust_code, mcs.name, rji.sumber, SUM(i.total_tagihan), COUNT(*), mc.language, mc.country, mc.default_fraction_digits "
                                                                            + "FROM "
                                                                                + "((invoice i "
                                                                                    + "JOIN (registration r "
                                                                                        + "JOIN m_customer mcs ON (r.cust_code=mcs.cust_code)) ON (i.no_reg=r.no_reg) "
                                                                                        + "JOIN m_service ms ON (r.service_code=ms.service_code)) "
                                                                                    + "JOIN m_currency mc ON (i.curr_code=mc.curr_code)) "
                                                                                    + "LEFT JOIN recap_jurnal_invoice rji ON (i.no_invoice = rji.no_invoice) "
                                                                            + "WHERE i.journaled_date IS NULL AND i.payment_type = 'CASH' AND i.payment_status = 'PAYMENT' AND i.kasir = ? AND i.cancel_invoice <> TRUE "
                                                                            + "GROUP BY ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, i.payment_type, r.cust_code, mcs.name, rji.sumber, mc.language, mc.country, mc.default_fraction_digits "
                                                                            + "HAVING rji.sumber IS NULL;"),
*/
    @NamedNativeQuery(name = "Invoice.Native.findJKMCandidateByKasir", query = 
"SELECT ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  i.payment_type, " 
+"  r.cust_code, " 
+"  mcs.name, " 
+"  rji.sumber, " 
+"  SUM(i.total_tagihan), " 
+"  COUNT(*), " 
+"  dbms_lob.substr(mc.language, 4000, 1) language, " 
+"  dbms_lob.substr(mc.country, 4000, 1) country, " 
+"  mc.default_fraction_digits " 
+"FROM ((invoice i " 
+"JOIN (registration r " 
+"JOIN m_customer mcs " 
+"ON (r.cust_code=mcs.cust_code)) " 
+"ON (i.no_reg   =r.no_reg) " 
+"JOIN m_service ms " 
+"ON (r.service_code=ms.service_code)) " 
+"JOIN m_currency mc " 
+"ON (i.curr_code=mc.curr_code)) " 
+"LEFT JOIN recap_jurnal_invoice rji " 
+"ON (i.no_invoice        = rji.no_invoice) " 
+"WHERE i.journaled_date IS NULL " 
+"AND i.payment_type      = 'CASH' " 
+"AND i.payment_status    = 'PAYMENT' " 
+"AND i.kasir             = ? " 
+"AND i.cancel_invoice   <> 1 " 
+"GROUP BY ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  i.payment_type, " 
+"  r.cust_code, " 
+"  mcs.name, " 
+"  rji.sumber, " 
+"  dbms_lob.substr(mc.language, 4000, 1) , " 
+"  dbms_lob.substr(mc.country, 4000, 1), " 
+"  mc.default_fraction_digits " 
+"HAVING rji.sumber IS NULL"),

/*
    @NamedNativeQuery(name = "Invoice.Native.findJKMCandidatePerInvoiceByKasir", query = "SELECT ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, i.payment_type, r.cust_code, mcs.name, rji.sumber, i.total_tagihan, 1, mc.language, mc.country, mc.default_fraction_digits, null, i.no_invoice, r.service_code "
                                                                            + "FROM "
                                                                                + "((invoice i "
                                                                                    + "JOIN (registration r "
                                                                                        + "JOIN m_customer mcs ON (r.cust_code=mcs.cust_code)) ON (i.no_reg=r.no_reg) "
                                                                                        + "JOIN m_service ms ON (r.service_code=ms.service_code)) "
                                                                                    + "JOIN m_currency mc ON (i.curr_code=mc.curr_code)) "
                                                                                    + "LEFT JOIN recap_jurnal_invoice rji ON (i.no_invoice = rji.no_invoice) "
                                                                            + "WHERE rji.sumber IS NULL AND i.journaled_date IS NULL AND i.payment_type = 'CASH' AND i.total_tagihan > 0 AND i.payment_status = 'PAYMENT' AND i.kasir = ? AND i.cancel_invoice <> TRUE "),
*/
    @NamedNativeQuery(name = "Invoice.Native.findJKMCandidatePerInvoiceByKasir", query = 
"SELECT ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  i.payment_type, " 
+"  r.cust_code, " 
+"  mcs.name, " 
+"  rji.sumber, " 
+"  i.total_tagihan, " 
+"  1, " 
+"  mc.language, " 
+"  mc.country, " 
+"  mc.default_fraction_digits, " 
+"  NULL, " 
+"  i.no_invoice, " 
+"  r.service_code " 
+"FROM ((invoice i " 
+"JOIN (registration r " 
+"JOIN m_customer mcs " 
+"ON (r.cust_code=mcs.cust_code)) " 
+"ON (i.no_reg   =r.no_reg) " 
+"JOIN m_service ms " 
+"ON (r.service_code=ms.service_code)) " 
+"JOIN m_currency mc " 
+"ON (i.curr_code=mc.curr_code)) " 
+"LEFT JOIN recap_jurnal_invoice rji " 
+"ON (i.no_invoice      = rji.no_invoice) " 
+"WHERE rji.sumber     IS NULL " 
+"AND i.journaled_date IS NULL " 
+"AND i.payment_type    = 'CASH' " 
+"AND i.total_tagihan   > 0 " 
+"AND i.payment_status  = 'PAYMENT' " 
+"AND i.kasir           = ? " 
+"AND i.cancel_invoice <> 1"),

/*                                                                            
    @NamedNativeQuery(name = "Invoice.Native.findJKMCandidatePerInvoices", query = "SELECT ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, i.payment_type, r.cust_code, mcs.name, rji.sumber, i.total_tagihan, 1, mc.language, mc.country, mc.default_fraction_digits, null, i.no_invoice, r.service_code, i.payment_date "
                                                                            + "FROM "
                                                                                + "((invoice i "
                                                                                    + "JOIN (registration r "
                                                                                        + "JOIN m_customer mcs ON (r.cust_code=mcs.cust_code)) ON (i.no_reg=r.no_reg) "
                                                                                        + "JOIN m_service ms ON (r.service_code=ms.service_code)) "
                                                                                    + "JOIN m_currency mc ON (i.curr_code=mc.curr_code)) "
                                                                                    + "LEFT JOIN recap_jurnal_invoice rji ON (i.no_invoice = rji.no_invoice) "
                                                                            + "WHERE rji.sumber IS NULL AND i.journaled_date IS NULL AND i.payment_type = 'CASH' AND i.total_tagihan > 0 AND i.payment_status = 'PAYMENT' AND i.cancel_invoice <> TRUE "
                                                                            + "ORDER BY i.payment_date;"),
*/                                                                            
    @NamedNativeQuery(name = "Invoice.Native.findJKMCandidatePerInvoices", query = 
    "SELECT ms.tipe_jurnal_id, " 
    +"  i.curr_code, " 
    +"  mc.mata_uang_id, " 
    +"  i.payment_type, " 
    +"  r.cust_code, " 
    +"  mcs.name, " 
    +"  rji.sumber, " 
    +"  i.total_tagihan, " 
    +"  1, " 
    +"  dbms_lob.substr(mc.language, 4000, 1) language, " 
    +"  dbms_lob.substr(mc.country, 4000, 1) country, " 
    +"  mc.default_fraction_digits, " 
    +"  NULL, " 
    +"  i.no_invoice, " 
    +"  r.service_code, " 
    +"  i.payment_date " 
    +"FROM ((invoice i " 
    +"JOIN (registration r " 
    +"JOIN m_customer mcs " 
    +"ON (r.cust_code=mcs.cust_code)) " 
    +"ON (i.no_reg   =r.no_reg) " 
    +"JOIN m_service ms " 
    +"ON (r.service_code=ms.service_code)) " 
    +"JOIN m_currency mc " 
    +"ON (i.curr_code=mc.curr_code)) " 
    +"LEFT JOIN recap_jurnal_invoice rji " 
    +"ON (i.no_invoice      = rji.no_invoice) " 
    +"WHERE rji.sumber     IS NULL " 
    +"AND i.journaled_date IS NULL " 
    +"AND i.payment_type    = 'CASH' " 
    +"AND i.total_tagihan   > 0 " 
    +"AND i.payment_status  = 'PAYMENT' " 
    +"AND i.cancel_invoice <> 'TRUE' " 
    +"ORDER BY i.payment_date"),
/*                                                                            
    @NamedNativeQuery(name = "Invoice.Native.findJKMByKasir", query = "SELECT "
                                                                                + "ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, i.payment_type, r.cust_code, mcs.name, rji.sumber, SUM(i.total_tagihan), COUNT(*), mc.language, mc.country, mc.default_fraction_digits, rj.no_jurnal "
                                                                            + "FROM "
                                                                                + "((invoice i "
                                                                                    + "JOIN (registration r "
                                                                                        + "JOIN m_customer mcs ON (r.cust_code=mcs.cust_code)) ON (i.no_reg=r.no_reg) "
                                                                                        + "JOIN m_service ms ON (r.service_code=ms.service_code)) "
                                                                                    + "JOIN m_currency mc ON (i.curr_code=mc.curr_code)) "
                                                                                    + "LEFT JOIN (recap_jurnal_invoice rji "
                                                                                        + "JOIN recap_jurnal rj ON (rji.sumber=rj.sumber)) ON (i.no_invoice = rji.no_invoice) "
                                                                            + "WHERE i.payment_type = 'CASH' AND i.payment_status = 'PAYMENT' AND i.kasir = ? AND rj.created_date::date = ? AND i.cancel_invoice <> TRUE "
                                                                            + "GROUP BY ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, i.payment_type, r.cust_code, mcs.name, rji.sumber, rj.no_jurnal, mc.language, mc.country, mc.default_fraction_digits, i.journaled_date "
                                                                            + "HAVING rji.sumber IS NOT NULL "
                                                                            + "ORDER BY i.journaled_date IS NULL ASC, i.journaled_date DESC"),
*/
    @NamedNativeQuery(name = "Invoice.Native.findJKMByKasir", query =                                                                             
"SELECT ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  i.payment_type, " 
+"  r.cust_code, " 
+"  mcs.name, " 
+"  rji.sumber, " 
+"  SUM(i.total_tagihan), " 
+"  COUNT(*), " 
+"  dbms_lob.substr(mc.language, 4000, 1) language, " 
+"  dbms_lob.substr(mc.country, 4000, 1) country, " 
+"  mc.default_fraction_digits, " 
+"  rj.no_jurnal " 
+"FROM ((invoice i " 
+"JOIN (registration r " 
+"JOIN m_customer mcs " 
+"ON (r.cust_code=mcs.cust_code)) " 
+"ON (i.no_reg   =r.no_reg) " 
+"JOIN m_service ms " 
+"ON (r.service_code=ms.service_code)) " 
+"JOIN m_currency mc " 
+"ON (i.curr_code=mc.curr_code)) " 
+"LEFT JOIN (recap_jurnal_invoice rji " 
+"JOIN recap_jurnal rj " 
+"ON (rji.sumber        =rj.sumber)) " 
+"ON (i.no_invoice      = rji.no_invoice) " 
+"WHERE i.payment_type  = 'CASH' " 
+"AND i.payment_status  = 'PAYMENT' " 
+"AND i.kasir           = ? " 
+"AND rj.created_date   = ? " 
+"AND i.cancel_invoice <> 1 " 
+"GROUP BY ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  i.payment_type, " 
+"  r.cust_code, " 
+"  mcs.name, " 
+"  rji.sumber, " 
+"  rj.no_jurnal, " 
+"  dbms_lob.substr(mc.language, 4000, 1), " 
+"  dbms_lob.substr(mc.country, 4000, 1), " 
+"  mc.default_fraction_digits, " 
+"  i.journaled_date " 
+"HAVING rji.sumber IS NOT NULL " 
+"ORDER BY i.journaled_date NULLs FIRST , " 
+"  i.journaled_date DESC"),                                                                            
/*
    @NamedNativeQuery(name = "Invoice.Native.findJKMPerInvoiceByKasir", query = "SELECT "
                                                                                    + "ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, i.payment_type, r.cust_code, mcs.name, rji.sumber, i.total_tagihan, 1, mc.language, mc.country, mc.default_fraction_digits, rj.no_jurnal, i.no_invoice, r.service_code "
                                                                                + "FROM "
                                                                                    + "((invoice i "
                                                                                        + "JOIN (registration r "
                                                                                            + "JOIN m_customer mcs ON (r.cust_code=mcs.cust_code)) ON (i.no_reg=r.no_reg) "
                                                                                            + "JOIN m_service ms ON (r.service_code=ms.service_code)) "
                                                                                        + "JOIN m_currency mc ON (i.curr_code=mc.curr_code)) "
                                                                                        + "LEFT JOIN (recap_jurnal_invoice rji "
                                                                                            + "JOIN recap_jurnal rj ON (rji.sumber=rj.sumber)) ON (i.no_invoice = rji.no_invoice) "
                                                                                + "WHERE i.payment_type = 'CASH' AND i.total_tagihan > 0 AND i.payment_status = 'PAYMENT' AND i.kasir = ? AND rj.created_date::date = ? AND i.cancel_invoice <> TRUE  "
                                                                                + "ORDER BY i.journaled_date IS NULL ASC, i.journaled_date DESC;"),
*/
    @NamedNativeQuery(name = "Invoice.Native.findJKMPerInvoiceByKasir", query = 
"SELECT ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  i.payment_type, " 
+"  r.cust_code, " 
+"  mcs.name, " 
+"  rji.sumber, " 
+"  i.total_tagihan, " 
+"  1, " 
+"  dbms_lob.substr(mc.language, 4000, 1) language, " 
+"  dbms_lob.substr(mc.country, 4000, 1) country, " 
+"  mc.default_fraction_digits, " 
+"  rj.no_jurnal, " 
+"  i.no_invoice, " 
+"  r.service_code " 
+"FROM ((invoice i " 
+"JOIN (registration r " 
+"JOIN m_customer mcs " 
+"ON (r.cust_code=mcs.cust_code)) " 
+"ON (i.no_reg   =r.no_reg) " 
+"JOIN m_service ms " 
+"ON (r.service_code=ms.service_code)) " 
+"JOIN m_currency mc " 
+"ON (i.curr_code=mc.curr_code)) " 
+"LEFT JOIN (recap_jurnal_invoice rji " 
+"JOIN recap_jurnal rj " 
+"ON (rji.sumber        =rj.sumber)) " 
+"ON (i.no_invoice      = rji.no_invoice) " 
+"WHERE i.payment_type  = 'CASH' " 
+"AND i.total_tagihan   > 0 " 
+"AND i.payment_status  = 'PAYMENT' " 
+"AND i.kasir           = ? " 
+"AND rj.created_date   = ? " 
+"AND i.cancel_invoice <> 1 " 
+"ORDER BY i.journaled_date NULLS FIRST, " 
+"  i.journaled_date DESC"),

/*
    @NamedNativeQuery(name = "Invoice.Native.findJKMPerInvoices", query = "SELECT "
                                                                                    + "ms.tipe_jurnal_id, i.curr_code, mc.mata_uang_id, i.payment_type, r.cust_code, mcs.name, rji.sumber, i.total_tagihan, 1, mc.language, mc.country, mc.default_fraction_digits, rj.no_jurnal, i.no_invoice, r.service_code "
                                                                                + "FROM "
                                                                                    + "((invoice i "
                                                                                        + "JOIN (registration r "
                                                                                            + "JOIN m_customer mcs ON (r.cust_code=mcs.cust_code)) ON (i.no_reg=r.no_reg) "
                                                                                            + "JOIN m_service ms ON (r.service_code=ms.service_code)) "
                                                                                        + "JOIN m_currency mc ON (i.curr_code=mc.curr_code)) "
                                                                                        + "LEFT JOIN (recap_jurnal_invoice rji "
                                                                                            + "JOIN recap_jurnal rj ON (rji.sumber=rj.sumber)) ON (i.no_invoice = rji.no_invoice) "
                                                                                + "WHERE i.payment_type = 'CASH' AND i.total_tagihan > 0 AND i.payment_status = 'PAYMENT' AND rj.created_date::date = ? AND i.cancel_invoice <> TRUE  "
                                                                                + "ORDER BY i.journaled_date IS NULL ASC, i.journaled_date DESC;"
*/
    @NamedNativeQuery(name = "Invoice.Native.findJKMPerInvoices", query = 
"SELECT ms.tipe_jurnal_id, " 
+"  i.curr_code, " 
+"  mc.mata_uang_id, " 
+"  i.payment_type, " 
+"  r.cust_code, " 
+"  mcs.name, " 
+"  rji.sumber, " 
+"  i.total_tagihan, " 
+"  1, " 
+"  dbms_lob.substr(mc.language, 4000, 1) language, " 
+"  dbms_lob.substr(mc.country, 4000, 1) country, " 
+"  mc.default_fraction_digits, " 
+"  rj.no_jurnal, " 
+"  i.no_invoice, " 
+"  r.service_code " 
+"FROM ((invoice i " 
+"JOIN (registration r " 
+"JOIN m_customer mcs " 
+"ON (r.cust_code=mcs.cust_code)) " 
+"ON (i.no_reg   =r.no_reg) " 
+"JOIN m_service ms " 
+"ON (r.service_code=ms.service_code)) " 
+"JOIN m_currency mc " 
+"ON (i.curr_code=mc.curr_code)) " 
+"LEFT JOIN (recap_jurnal_invoice rji " 
+"JOIN recap_jurnal rj " 
+"ON (rji.sumber        =rj.sumber)) " 
+"ON (i.no_invoice      = rji.no_invoice) " 
+"WHERE i.payment_type  = 'CASH' " 
+"AND i.total_tagihan   > 0 " 
+"AND i.payment_status  = 'PAYMENT' " 
+"AND rj.created_date   = ? " 
+"AND i.cancel_invoice <> 'TRUE' " 
+"ORDER BY i.journaled_date NULLS FIRST, " 
+"  i.journaled_date DESC")})

public class Invoice implements Serializable, EntityAuditable {
    public static final String USD = "USD";
    public static final String IDR = "IDR";
    public static final String PAID_STATUS = "PAYMENT";
    public static final String UNPAID_STATUS = "UNPAID";


    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_reg", nullable = false, length = 30)
    private String noReg;
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
    @Column(name = "no_invoice", length = 30)
    private String noInvoice;
    @Column(name = "jumlah_tagihan", precision = 19, scale = 2)
    private BigDecimal jumlahTagihan = BigDecimal.ZERO;
    @Column(name = "additional_charge", precision = 19, scale = 2)
    private BigDecimal additionalCharge = BigDecimal.ZERO;
    @Column(name = "materai", precision = 19, scale = 2)
    private BigDecimal materai = BigDecimal.ZERO;
    @Column(name = "ppn", precision = 19, scale = 2)
    private BigDecimal ppn = BigDecimal.ZERO;
    @Column(name = "total_tagihan", precision = 19, scale = 2)
    private BigDecimal totalTagihan = BigDecimal.ZERO;
    @Column(name = "payment_status", length = 10)
    private String paymentStatus;
    @Column(name = "payment_type", length = 10)
    private String paymentType;
    @Column(name = "kasir", length = 50)
    private String kasir;
    @Column(name = "rekening", length = 50)
    private String rekening;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @Column(name = "payment_tunai", precision = 19, scale = 2)
    private BigDecimal paymentTunai = BigDecimal.ZERO;
    @Column(name = "payment_transfer", precision = 19, scale = 2)
    private BigDecimal paymentTransfer = BigDecimal.ZERO;
    @Column(name = "payment_cek", precision = 19, scale = 2)
    private BigDecimal paymentCek = BigDecimal.ZERO;
    @Column(name = "payment_bg", precision = 19, scale = 2)
    private BigDecimal paymentBg = BigDecimal.ZERO;
    @Column(name = "payment_bank", precision = 19, scale = 2)
    private BigDecimal paymentBank = BigDecimal.ZERO;
    @Column(name = "verification_code", length = 30)
    private String verificationCode;
    @Column(name = "no_faktur_pajak", length = 30)
    private String noFakturPajak;
    @Column(name = "pengurus_do", length = 50)
    private String pengurusDo;
    @Basic(optional = false)
    @Column(name = "validasi_print", nullable = false)
    private String validasiPrint = "FALSE";
    @Column(name = "payment_tool", length = 10)
    private String paymentTool;
    @Column(name = "ppkb_handling")
    private String ppkbHandling;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", insertable = false, updatable = false)
    @OneToOne
    private Registration registration;
    @Column(name = "cancel_invoice", nullable = false)
    private String cancelInvoice = "FALSE";
    @Column(name = "journaled_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date journaledDate;
    @JoinColumn(name = "bank", referencedColumnName = "id")
    @ManyToOne
    private MasterBank masterBank;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
    private List<RecapJurnalInvoice> recapJurnalInvoiceList;
    @JoinColumn(name = "curr_code", referencedColumnName = "curr_code")
    @ManyToOne
    private MasterCurrency masterCurrency;
    @Column(name = "payment_utip", precision = 19, scale = 2)
    private BigDecimal paymentUtip = BigDecimal.ZERO;
    @Column(name = "printed")
    private Integer printed;

    public Invoice() {
    }

    public Invoice(String noReg) {
        this.noReg = noReg;
    }

    public String getNoInvoice() {
        return noInvoice;
    }

    public void setNoInvoice(String noInvoice) {
        this.noInvoice = noInvoice;
    }

    public BigDecimal getJumlahTagihan() {
        return jumlahTagihan;
    }

    public void setJumlahTagihan(BigDecimal jumlahTagihan) {
        this.jumlahTagihan = jumlahTagihan;
    }

    public BigDecimal getMaterai() {
        return materai;
    }

    public void setMaterai(BigDecimal materai) {
        this.materai = materai;
    }

    public BigDecimal getPpn() {
        return ppn;
    }

    public void setPpn(BigDecimal ppn) {
        this.ppn = ppn;
    }

    public BigDecimal getTotalTagihan() {
        return totalTagihan;
    }

    public void setTotalTagihan(BigDecimal totalTagihan) {
        this.totalTagihan = totalTagihan;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getKasir() {
        return kasir;
    }

    public void setKasir(String kasir) {
        this.kasir = kasir;
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getTotalTagihanIDR() {
        if (masterCurrency != null && masterCurrency.getCurrCode().equals("IDR")) {
            return totalTagihan;
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getTotalTagihanUSD() {
        if (masterCurrency != null && masterCurrency.getCurrCode().equals("USD")) {
            return totalTagihan;
        }
        return BigDecimal.ZERO;
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

    public String getPaymentTool() {
        return paymentTool;
    }

    public void setPaymentTool(String paymentTool) {
        this.paymentTool = paymentTool;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public BigDecimal getPaymentTunai() {
        if (paymentTunai == null) {
            return BigDecimal.ZERO;
        }
        return paymentTunai;
    }

    public void setPaymentTunai(BigDecimal paymentTunai) {
        this.paymentTunai = paymentTunai;
    }

    public BigDecimal getPaymentTransfer() {
        if (paymentTransfer == null) {
            return BigDecimal.ZERO;
        }
        return paymentTransfer;
    }

    public void setPaymentTransfer(BigDecimal paymentTransfer) {
        this.paymentTransfer = paymentTransfer;
    }

    public BigDecimal getPaymentCek() {
        if (paymentCek == null) {
            return BigDecimal.ZERO;
        }
        return paymentCek;
    }

    public void setPaymentCek(BigDecimal paymentCek) {
        this.paymentCek = paymentCek;
    }

    public BigDecimal getPaymentBg() {
        if (paymentBg == null) {
            return BigDecimal.ZERO;
        }
        return paymentBg;
    }

    public void setPaymentBg(BigDecimal paymentBg) {
        this.paymentBg = paymentBg;
    }

    public BigDecimal getPaymentBank() {
        if (paymentBank == null) {
            return BigDecimal.ZERO;
        }
        return paymentBank;
    }

    public void setPaymentBank(BigDecimal paymentBank) {
        this.paymentBank = paymentBank;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getNoFakturPajak() {
        return noFakturPajak;
    }

    public void setNoFakturPajak(String noFakturPajak) {
        this.noFakturPajak = noFakturPajak;
    }

    public String getPengurusDo() {
        return pengurusDo;
    }

    public void setPengurusDo(String pengurusDo) {
        this.pengurusDo = pengurusDo;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public String getPpkbHandling() {
        return ppkbHandling;
    }

    public void setPpkbHandling(String ppkbHandling) {
        this.ppkbHandling = ppkbHandling;
    }

    public MasterBank getMasterBank() {
        return masterBank;
    }

    public void setMasterBank(MasterBank masterBank) {
        this.masterBank = masterBank;
    }

    public BigDecimal getPaymentUtip() {
        if (paymentUtip == null) {
            return BigDecimal.ZERO;
        }
        return paymentUtip;
    }

    public void setPaymentUtip(BigDecimal paymentUtip) {
        this.paymentUtip = paymentUtip;
    }

    public String getValidasiPrint() {
        return validasiPrint;
    }

    public void setValidasiPrint(String validasiPrint) {
        this.validasiPrint = validasiPrint;
    }

    public String isCancelInvoice() {
        return cancelInvoice;
    }

    public void setCancelInvoice(String cancelInvoice) {
        this.cancelInvoice = cancelInvoice;
    }

    public Date getJournaledDate() {
        return journaledDate;
    }

    public void setJournaledDate(Date journaledDate) {
        this.journaledDate = journaledDate;
    }

    public List<RecapJurnalInvoice> getRecapJurnalInvoiceList() {
        return recapJurnalInvoiceList;
    }

    public void setRecapJurnalInvoiceList(List<RecapJurnalInvoice> recapJurnalInvoiceList) {
        this.recapJurnalInvoiceList = recapJurnalInvoiceList;
    }

    public MasterCurrency getMasterCurrency() {
        return masterCurrency;
    }

    public void setMasterCurrency(MasterCurrency masterCurrency) {
        this.masterCurrency = masterCurrency;
    }

    public BigDecimal getAdditionalCharge() {
        return additionalCharge;
    }

    public void setAdditionalCharge(BigDecimal additionalCharge) {
        this.additionalCharge = additionalCharge;
    }

    public Integer getPrinted() {
        return printed;
    }

    public void setPrinted(Integer printed) {
        this.printed = printed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noReg != null ? noReg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.noReg == null && other.noReg != null) || (this.noReg != null && !this.noReg.equals(other.noReg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.Invoice[noReg=" + noReg + "]";
    }
}
