/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.Invoice;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface InvoiceFacadeRemote {

    void create(Invoice invoice);

    void edit(Invoice invoice);

    void remove(Invoice invoice);

    Invoice find(Object id);

    List<Invoice> findAll();

    List<Invoice> findRange(int[] range);

    int count();

    String generateId(String bulan);

    Object[] findByReg(String no_reg);

    List<Object[]> findJournalByKasir(String kasir);

    String findNoInvoice(String no_reg);
    
    List<Object[]> findInvoiceCredit();

    String findInvoice(String ppkb_handling);

    List<Object[]> findBatalNota();

    Object[] findInvoiceBatalNota(String no_invoice);

    public java.util.List<com.pelindo.ebtos.model.db.Invoice> findJournalInvoiceCash(java.lang.String kasir, java.lang.String currCode, String custCode);

    public java.util.List<com.pelindo.ebtos.model.db.Invoice> findJournalInvoiceKredit(java.lang.String noInvoice);

    public java.util.List<Object[]> findJournalDetailKredit(java.lang.String noInvoice, java.lang.String serviceCode);

    public List<Object[]> findSPTWithInterval(String currCode, String month, String year) throws java.io.IOException;

    public Object[] insertIntoJurnal(java.lang.String sumber, java.lang.String tipeJurnalId, java.lang.String username, java.lang.String currCode, java.lang.String registeredUser, java.lang.String keterangan, java.lang.String isCash) throws java.io.IOException;

    public String insertIntoJurnalDetail(java.lang.String noJurnal, java.lang.String kodeRekening, java.lang.String masterId, java.lang.String noNota, BigDecimal debit, BigDecimal kredit) throws java.io.IOException;

    public java.lang.String findNoInvoiceByPpkbHandling(java.lang.String ppkb_handling);

    public java.lang.Boolean doInvoicePayment(java.lang.String noInvoice, Date paymentDate, java.lang.String guid);

    public java.util.List<java.lang.Object[]> findJournalDetailCash(java.lang.String kasir, java.lang.String custCode, java.lang.String currCode, java.lang.String tipeJurnalId);

    public java.util.List<java.lang.Object[]> findJKMCandidateByKasirAndDate(java.lang.String kasir, Date date);

    public java.util.List<java.lang.Object[]> findJKMCandidateByKasir(java.lang.String kasir);

    public java.util.List<java.lang.Object[]> findJKMByKasir(java.lang.String kasir, Date date);

    public java.math.BigDecimal getKurs(java.lang.String currCode);

    public com.pelindo.ebtos.model.db.Invoice findByNoInvoice(java.lang.String noInvoice);

    public java.lang.Object[] getUtipInfo(java.lang.String nomorMaster);

    public com.pelindo.ebtos.model.db.Registration findRegistrationByPpkbHandlingAndYard(java.lang.String noPpkb, String yard);

    public com.pelindo.ebtos.model.db.Registration findRegistrationByPpkbHandlingAndCurrency(java.lang.String noPpkb, String currCode);

    public java.util.List<java.lang.Object[]> findJKMCandidatePerInvoiceByKasir(java.lang.String kasir);

    public java.util.List<java.lang.Object[]> findJournalDetail(java.lang.String noInvoice, java.lang.String serviceCode);

    public java.util.List<java.lang.Object[]> findJKMPerInvoiceByKasir(java.lang.String kasir, java.util.Date date);

    Invoice publishInvoice(Invoice invoice);

    public java.util.List<java.lang.Object[]> findJournalsPerInvoice();

    public java.util.List<java.lang.Object[]> findJKMCandidatePerInvoices();

    public java.util.List<java.lang.Object[]> findJKMPerInvoices(java.util.Date date);

}
