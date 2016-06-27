/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.IDGeneratorFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterCustomerFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.RecapJurnalFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.RecapJurnalFacadeRemote;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.RecapJurnal;
import com.pelindo.ebtos.model.db.RecapJurnalDetail;
import com.pelindo.ebtos.model.db.RecapJurnalInvoice;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author R Seno Anggoro
 */
@Stateless
public class RecapJurnalFacade extends AbstractFacade<RecapJurnal> implements RecapJurnalFacadeRemote, RecapJurnalFacadeLocal {
    private static final String REKENING_PPN = "409.06.00.00";
    private static final String KETERANGAN_PER_INVOICE_TEMPLATE = "PENDAPATAN NOTA NO %s TGL %s";
    private static final String KETERANGAN_CASH_TEMPLATE = "PENERIMAAN TUNAI NO REKAP %s TGL %s";
    private static List<String> includeInvoice;

    static {
        includeInvoice = new ArrayList<String>();
        includeInvoice.add("103");
        includeInvoice.add("404");
    }

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;
    @EJB
    private IDGeneratorFacadeLocal iDGeneratorFacade;
    @EJB
    private InvoiceFacadeLocal invoiceFacade;
    @EJB
    private RecapJurnalFacadeLocal recapJurnalFacade;
    @EJB
    private MasterCustomerFacadeLocal masterCustomerFacade;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacade;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapJurnalFacade() {
        super(RecapJurnal.class);
    }

    public List<Invoice> findInvoicesBySumber(String sumber) {
        return getEntityManager().createNamedQuery("RecapJurnal.findInvoicesBySumber")
                .setParameter("sumber", sumber)
                .getResultList();
    }

    /**
     *
     * @param array of object:
     *              0 = tipe_jurnal_id,
     *              1 = curr_code,
     *              2 = mata_uang_id,
     *              3 = payment_type,
     *              4 = cust_code,
     *              5 = customer_name,
     *              6 = sumber,
     *              7 = total_tagihan,
     *              8 = jumlah_invoice
     * @param username
     * @return
     * @throws EJBException
     */
    public List<RecapJurnal> createJKMRecap(Object[] array, String username){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<RecapJurnal> jurnals = new ArrayList<RecapJurnal>();
        Boolean isJKMGroupingByCustomer = masterSettingAppFacade.isJKMGroupingByCustomerEnabled();

        for (Object object: array){
            List<RecapJurnalInvoice> recapJurnalInvoices = new ArrayList<RecapJurnalInvoice>();
            List<RecapJurnalDetail> recapJurnalDetails = new ArrayList<RecapJurnalDetail>();
            Object[] journal = (Object[]) object;
            String sumber = iDGeneratorFacade.generateJkmID();
            List<Object[]> journalDetail;

            if (isJKMGroupingByCustomer) {
                journalDetail = invoiceFacade.findJournalDetailCash(username, (String) journal[4], (String) journal[1], (String) journal[0]);
            } else {
                String noInvoice = (String) journal[13];
                String serviceCode = (String) journal[14];
                journalDetail = invoiceFacade.findJournalDetail(noInvoice, serviceCode);
            }

            RecapJurnal recapJurnal = new RecapJurnal(sumber, (String) journal[0], (String) journal[2], username);
            
            for (Object[] detail: journalDetail){
                RecapJurnalDetail recapJurnalDetail = new RecapJurnalDetail();
                recapJurnalDetail.setRecapJurnal(recapJurnal);
                recapJurnalDetail.setKodeRekening((String) detail[0]);
                recapJurnalDetail.setJumlahDebit((BigDecimal) detail[1]);
                recapJurnalDetail.setJumlahKredit((BigDecimal) detail[2]);

                if (recapJurnalDetail.getKodeRekening() != null && includeInvoice.contains(recapJurnalDetail.getKodeRekening().substring(0, 3))) {
                    String masterId = (String) journal[4];
                    
                    if (recapJurnalDetail.getKodeRekening().contains("404.15")) {
                        recapJurnalDetail.setMasterId("40415");
                    } else {
                        recapJurnalDetail.setMasterId(masterId);
                    }
                    
                    recapJurnalDetail.setNoNota(sumber);
                }

                recapJurnalDetails.add(recapJurnalDetail);
            }

            List<Invoice> invoices = new ArrayList<Invoice>();

            if (isJKMGroupingByCustomer) {
                invoices = invoiceFacade.findJournalInvoiceCash(username, (String) journal[1], (String) journal[4]);
            } else {
                invoices.add(invoiceFacade.findByNoInvoice((String) journal[13]));
            }
            
            for (Invoice invoice: invoices){
                RecapJurnalInvoice recapJurnalInvoice = new RecapJurnalInvoice();
                recapJurnalInvoice.setRecapJurnal(recapJurnal);
                recapJurnalInvoice.setInvoice(invoice);
                recapJurnalInvoices.add(recapJurnalInvoice);
            }

            if (isJKMGroupingByCustomer) {
                recapJurnal.setKeterangan(String.format(KETERANGAN_CASH_TEMPLATE, sumber, dateFormat.format(invoices.get(0).getPaymentDate())));
            } else {
                recapJurnal.setKeterangan(String.format(KETERANGAN_PER_INVOICE_TEMPLATE, invoices.get(0).getNoInvoice(), dateFormat.format(invoices.get(0).getPaymentDate())));
            }
            
            MasterCustomer masterCustomer = masterCustomerFacade.find((String) journal[4]);

            recapJurnal.setRecapJurnalInvoiceList(recapJurnalInvoices);
            recapJurnal.setRecapJurnalDetailList(recapJurnalDetails);
            recapJurnal.setMasterCustomer(masterCustomer);

            create(recapJurnal);
            jurnals.add(recapJurnal);
        }

        return jurnals;
    }

    /**
     *
     * @param journals: List of
     *      0 = tipe_jurnal_id
     *      1 = curr_code
     *      2 = mata_uang_id
     *      3 = kurs
     *      4 = payment_type
     *      5 = no_invoice
     *      6 = service_code
     *      7 = cust_code
     *      8 = cust_name
     *      9 = total_tagihan
     *      10 = jumlah invoice
     *      11 = sumber
     * @param username
     * @return
     */
    public Boolean postingJurnalToSimpat(Object[] journals, String username) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Date tgl = Calendar.getInstance().getTime();
        List<Invoice> invoices;
        
        for (Object object: journals){
            Object[] journal = (Object[]) object;
            if (journal[4].equals("CASH")){
                RecapJurnal recapJurnal = recapJurnalFacade.find((String) journal[11]);
                
                try {
                    Object[] simpatJournal = invoiceFacade.insertIntoJurnal(recapJurnal.getSumber(), (String) journal[0], username, (String) journal[1], username, recapJurnal.getKeterangan(), "TRUE");
                    System.out.println(recapJurnal.getSumber()+""+ (String) journal[0]+""+ username+""+ (String) journal[1]+""+ username+""+ recapJurnal.getKeterangan()+""+ true);
                    Invoice invoice = recapJurnal.getRecapJurnalInvoiceList().get(0).getInvoice();

                    if (simpatJournal != null){
                        recapJurnal.setTanggal(invoice.getPaymentDate());
                        recapJurnal.setNoJurnal((String) simpatJournal[0]);
                        recapJurnal.setSumber(recapJurnal.getSumber());
                        recapJurnal.setKurs((BigDecimal) simpatJournal[3]);
                        recapJurnal.setCaraPenerimaan((String) simpatJournal[6]);
                        recapJurnal.setWorkStation((String) simpatJournal[4]);
                        recapJurnal.setCabangId((String) simpatJournal[5]);

                        for (RecapJurnalDetail detail: recapJurnal.getRecapJurnalDetailList()){
                            String namaRekening = invoiceFacade.insertIntoJurnalDetail((String) simpatJournal[0], detail.getKodeRekening(), detail.getMasterId(), detail.getNoNota(), detail.getJumlahDebit(), detail.getJumlahKredit());
                            detail.setNamaRekening(namaRekening);
                        }

                        for (RecapJurnalInvoice recapJurnalInvoice: recapJurnal.getRecapJurnalInvoiceList()){
                            recapJurnalInvoice.getInvoice().setJournaledDate(invoice.getPaymentDate());
                        }

                        recapJurnalFacade.edit(recapJurnal);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            } else if (journal[4].equals("KREDIT")){
                invoices = invoiceFacade.findJournalInvoiceKredit((String) journal[5]);
                try {
                    String keterangan = String.format(KETERANGAN_PER_INVOICE_TEMPLATE, journal[5], dateFormat.format(invoices.get(0).getCreatedDate()));
                    Object[] simpatJournal = invoiceFacade.insertIntoJurnal((String) journal[5], (String) journal[0], username, (String) journal[1], (String) journal[8], keterangan, "FALSE");
                    System.out.println((String) journal[5]+"-"+ (String) journal[0]+"-"+ username+"-"+ (String) journal[1]+"-"+ (String) journal[8]+"-"+ keterangan+"-"+ false);
                    if (simpatJournal != null){
                        List<RecapJurnalInvoice> recapJurnalInvoices = new ArrayList<RecapJurnalInvoice>();
                        List<RecapJurnalDetail> recapJurnalDetails = new ArrayList<RecapJurnalDetail>();
                        MasterCustomer masterCustomer = masterCustomerFacade.find((String) journal[7]);

                        String namaRekening;
                        RecapJurnal recapJurnal = new RecapJurnal((String) journal[5], (String) journal[0], (String) simpatJournal[1], username);
                        //recapJurnal.setTanggal(tgl);
                        recapJurnal.setNoJurnal((String) simpatJournal[0]);
                        recapJurnal.setKeterangan(keterangan);
                        recapJurnal.setKurs((BigDecimal) simpatJournal[3]);
                        recapJurnal.setWorkStation((String) simpatJournal[4]);
                        recapJurnal.setCaraPenerimaan((String) simpatJournal[6]);
                        recapJurnal.setCabangId((String) simpatJournal[5]);
                        recapJurnal.setMasterCustomer(masterCustomer);

                        List<Object[]> journalDetail = invoiceFacade.findJournalDetailKredit((String) journal[5], (String) journal[6]);

                        for (Object[] detail: journalDetail){
                            RecapJurnalDetail recapJurnalDetail = new RecapJurnalDetail();
                            String kodeRekening = (String) detail[0];

                            if (kodeRekening != null && includeInvoice.contains(kodeRekening.substring(0, 3))) {
                                String masterId = (String) journal[7];

                                if (kodeRekening.contains("404.15")) {
                                    recapJurnalDetail.setMasterId("40415");
                                } else {
                                    recapJurnalDetail.setMasterId(masterId);
                                }

                                recapJurnalDetail.setNoNota((String) journal[5]);
                                namaRekening = invoiceFacade.insertIntoJurnalDetail((String) simpatJournal[0], kodeRekening, recapJurnalDetail.getMasterId(), (String) journal[5], (BigDecimal) detail[1], (BigDecimal) detail[2]);
                            } else {
                                namaRekening = invoiceFacade.insertIntoJurnalDetail((String) simpatJournal[0], kodeRekening, null, null, (BigDecimal) detail[1], (BigDecimal) detail[2]);
                            }

                            if (namaRekening != null){
                                recapJurnalDetail.setRecapJurnal(recapJurnal);
                                recapJurnalDetail.setKodeRekening(kodeRekening);
                                recapJurnalDetail.setNamaRekening(namaRekening);
                                recapJurnalDetail.setJumlahDebit((BigDecimal) detail[1]);
                                recapJurnalDetail.setJumlahKredit((BigDecimal) detail[2]);

                                recapJurnalDetails.add(recapJurnalDetail);
                            }
                        }

                        for (Invoice invoice: invoices){
                            invoice.setJournaledDate(invoice.getCreatedDate());
                            recapJurnal.setTanggal(invoice.getCreatedDate());
                            invoiceFacade.edit(invoice);
                            RecapJurnalInvoice recapJurnalInvoice = new RecapJurnalInvoice();
                            recapJurnalInvoice.setRecapJurnal(recapJurnal);
                            recapJurnalInvoice.setInvoice(invoice);
                            recapJurnalInvoices.add(recapJurnalInvoice);
                        }
                        
                        recapJurnal.setRecapJurnalInvoiceList(recapJurnalInvoices);
                        recapJurnal.setRecapJurnalDetailList(recapJurnalDetails);
                        recapJurnalFacade.create(recapJurnal);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    public RecapJurnal findBySumber(String sumber){
        try {
            return (RecapJurnal) getEntityManager().createNamedQuery("RecapJurnal.findBySumber")
                    .setParameter("sumber", sumber)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public BigDecimal findTotalTagihanBySumber(String sumber) {
        return (BigDecimal) getEntityManager().createNamedQuery("RecapJurnal.findTotalTagihanBySumber")
                .setParameter("sumber", sumber)
                .getSingleResult();
    }
}
