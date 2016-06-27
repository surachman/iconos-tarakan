/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.qtasnim.util.CurrencyHelper;
import com.pelindo.ebtos.ejb.facade.local.BankingSyncFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.ChangeVesselServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.DeliveryServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.IDGeneratorFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterBankFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PenumpukanSusulanServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.UcDeliveryServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.UcPenumpukanSusulanServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.local.ChangeVesselOperationLocal;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterBank;
import com.pelindo.ebtos.model.db.master.MasterService;
import com.pelindo.ebtos.security.UserUtil;
import com.pelindo.ebtos.util.VerificationID;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 *
 * @author dycoder
 */
@Stateless
public class InvoiceFacade extends AbstractFacade<Invoice> implements InvoiceFacadeRemote, InvoiceFacadeLocal {
    private static final Logger logger = Logger.getLogger(InvoiceFacade.class.getName());

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;
    @EJB
    private MasterBankFacadeLocal masterBankFacade;
    @EJB
    private IDGeneratorFacadeLocal iDGeneratorFacade;
    @EJB
    private MasterServiceFacadeLocal masterServiceFacade;
    @EJB
    private BankingSyncFacadeLocal bankingSyncFacade;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacade;
    @EJB
    private PenumpukanSusulanServiceFacadeLocal penumpukanSusulanServiceFacadeLocal;
    @EJB
    private DeliveryServiceFacadeLocal deliveryServiceFacadeLocal;
    @EJB
    private UcPenumpukanSusulanServiceFacadeLocal ucPenumpukanSusulanServiceFacadeLocal;
    @EJB
    private UcDeliveryServiceFacadeLocal ucDeliveryServiceFacadeLocal;
    @EJB
    private ChangeVesselServiceFacadeLocal changeVesselServiceFacadeLocal;
    @EJB
    private ChangeVesselOperationLocal changeVesselOperationLocal;

    protected EntityManager getEntityManager() {
        return em;
    }

    public InvoiceFacade() {
        super(Invoice.class);
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("Invoice.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public Object[] findByReg(String no_reg) {
        try {
            return (Object[]) getEntityManager().createNamedQuery("Invoice.Native.findByReg").setParameter(1, no_reg).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param date = Kasir
     * @return
     *      0 = Total IDR
     *      1 = Total USD
     */
    public Object[] findTotalNotJournaledInvoiceByKasir(String kasir) {

        Object[] value = (Object[]) getEntityManager().createNamedQuery("Invoice.Native.findTotalNotJournaledInvoiceByKasir").setParameter(1, kasir).getSingleResult();
        if (value[0] != null && value[1] != null) {
            return new Object[]{CurrencyHelper.moneyFormat((BigDecimal) value[0], "IDR"), CurrencyHelper.moneyFormat((BigDecimal) value[1], "USD")};
        }
        return new Object[]{"Rp0,00", "$0.00"};
    }

    /**
     *
     * @param String = Kasir
     * @return
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
     *      12 = language
     *      13 = country
     *      14 = fractionDigits
     */
    public List<Object[]> findJournalByKasir(String kasir) {
        return getEntityManager().createNamedQuery("Invoice.Native.findJournalByKasir")
                .setParameter(1, kasir)
                .setParameter(2, new Date())
                .getResultList();
    }

    /**
     *
     * @param String = Kasir
     * @return
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
     *      12 = language
     *      13 = country
     *      14 = fractionDigits
     */
    public List<Object[]> findJournalsPerInvoice() {
        return getEntityManager().createNamedQuery("Invoice.Native.findJournalsPerInvoice")
                .setParameter(1, new Date())
                .getResultList();
    }

    /**
     *
     * @param kasir
     * @return
     *      0 = tipe_jurnal_id,
     *      1 = curr_code,
     *      2 = mata_uang_id,
     *      3 = payment_type,
     *      4 = cust_code,
     *      5 = customer_name,
     *      6 = sumber,
     *      7 = total_tagihan,
     *      8 = jumlah_invoice,
     *      9 = language
     *      10 = country
     *      11 = fractionDigits
     */
    public List<Object[]> findJKMCandidateByKasirAndDate(String kasir, Date date) {
        return getEntityManager().createNamedQuery("Invoice.Native.findJKMCandidateByKasirAndDate")
                .setParameter(1, kasir)
                .setParameter(2, date)
                .getResultList();
    }

    /**
     *
     * @param kasir
     * @return
     *      0 = tipe_jurnal_id,
     *      1 = curr_code,
     *      2 = mata_uang_id,
     *      3 = payment_type,
     *      4 = cust_code,
     *      5 = customer_name,
     *      6 = sumber,
     *      7 = total_tagihan,
     *      8 = jumlah_invoice,
     *      9 = language
     *      10 = country
     *      11 = fractionDigits
     */
    public List<Object[]> findJKMCandidateByKasir(String kasir) {
        return getEntityManager().createNamedQuery("Invoice.Native.findJKMCandidateByKasir")
                .setParameter(1, kasir)
                .getResultList();
    }

    /**
     *
     * @param kasir
     * @return
     *      0 = tipe_jurnal_id,
     *      1 = curr_code,
     *      2 = mata_uang_id,
     *      3 = payment_type,
     *      4 = cust_code,
     *      5 = customer_name,
     *      6 = sumber,
     *      7 = total_tagihan,
     *      8 = jumlah_invoice,
     *      9 = language
     *      10 = country
     *      11 = fractionDigits
     *      12 = no_jurnal (null)
     *      13 = no_invoice
     *      14 = service_code
     */
    public List<Object[]> findJKMCandidatePerInvoiceByKasir(String kasir) {
        return getEntityManager().createNamedQuery("Invoice.Native.findJKMCandidatePerInvoiceByKasir")
                .setParameter(1, kasir)
                .getResultList();
    }

    /**
     *
     * @param kasir
     * @return
     *      0 = tipe_jurnal_id,
     *      1 = curr_code,
     *      2 = mata_uang_id,
     *      3 = payment_type,
     *      4 = cust_code,
     *      5 = customer_name,
     *      6 = sumber,
     *      7 = total_tagihan,
     *      8 = jumlah_invoice,
     *      9 = language
     *      10 = country
     *      11 = fractionDigits
     *      12 = no_jurnal (null)
     *      13 = no_invoice
     *      14 = service_code
     */
    public List<Object[]> findJKMCandidatePerInvoices() {
        return getEntityManager().createNamedQuery("Invoice.Native.findJKMCandidatePerInvoices")
                .getResultList();
    }

    /**
     *
     * @param kasir
     * @return
     *      0 = tipe_jurnal_id,
     *      1 = curr_code,
     *      2 = mata_uang_id,
     *      3 = payment_type,
     *      4 = cust_code,
     *      5 = customer_name,
     *      6 = sumber,
     *      7 = total_tagihan,
     *      8 = jumlah_invoice,
     *      9 = language
     *      10 = country
     *      11 = fractionDigits
     */
    public List<Object[]> findJKMByKasir(String kasir, Date date) {
        return getEntityManager().createNamedQuery("Invoice.Native.findJKMByKasir")
                .setParameter(1, kasir)
                .setParameter(2, date)
                .getResultList();
    }

    /**
     *
     * @param kasir
     * @return
     *      0 = tipe_jurnal_id,
     *      1 = curr_code,
     *      2 = mata_uang_id,
     *      3 = payment_type,
     *      4 = cust_code,
     *      5 = customer_name,
     *      6 = sumber,
     *      7 = total_tagihan,
     *      8 = jumlah_invoice,
     *      9 = language
     *      10 = country
     *      11 = fractionDigits
     */
    public List<Object[]> findJKMPerInvoiceByKasir(String kasir, Date date) {
        return getEntityManager().createNamedQuery("Invoice.Native.findJKMPerInvoiceByKasir")
                .setParameter(1, kasir)
                .setParameter(2, date)
                .getResultList();
    }

    /**
     *
     * @param kasir
     * @return
     *      0 = tipe_jurnal_id,
     *      1 = curr_code,
     *      2 = mata_uang_id,
     *      3 = payment_type,
     *      4 = cust_code,
     *      5 = customer_name,
     *      6 = sumber,
     *      7 = total_tagihan,
     *      8 = jumlah_invoice,
     *      9 = language
     *      10 = country
     *      11 = fractionDigits
     */
    public List<Object[]> findJKMPerInvoices(Date date) {
        return getEntityManager().createNamedQuery("Invoice.Native.findJKMPerInvoices")
                .setParameter(1, date)
                .getResultList();
    }

    public List<Invoice> findJournalInvoiceCash(String kasir, String currCode, String custCode){
        return getEntityManager().createNamedQuery("Invoice.findJournalInvoiceCash")
                .setParameter("kasir", kasir)
                .setParameter("currCode", currCode)
                .setParameter("custCode", custCode)
                .getResultList();
    }

    public Registration findRegistrationByPpkbHandlingAndYard(String noPpkb, String yard){
        try {
            return (Registration) getEntityManager().createNamedQuery("Invoice.findRegistrationByPpkbHandlingAndYard")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("yard", yard)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public Registration findRegistrationByPpkbHandlingAndCurrency(java.lang.String noPpkb, String currCode){
        try {
            return (Registration) getEntityManager().createNamedQuery("Invoice.findRegistrationByPpkbHandlingAndCurrency")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("currCode", currCode)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Invoice> findJournalInvoiceKredit(String noInvoice){
        return getEntityManager().createNamedQuery("Invoice.findJournalInvoiceKredit")
                .setParameter("noInvoice", noInvoice)
                .getResultList();
    }

    public List<Object[]> findJournalDetailCash(String kasir, String custCode, String currCode, String tipeJurnalId){
        return getEntityManager().createNamedQuery("Invoice.Native.findJournalDetailCash")
                .setParameter(1, kasir)
                .setParameter(2, custCode)
                .setParameter(3, currCode)
                .setParameter(4, tipeJurnalId)
                .getResultList();
    }

    public List<Object[]> findJournalDetailKredit(String noInvoice, String serviceCode){
        return getEntityManager().createNativeQuery("SELECT * FROM table(jurnal_detail_kredit_" + serviceCode.toLowerCase() + "( '" + noInvoice + "' )) ORDER BY rekening").getResultList();
    }
    
    public List<Object[]> findJournalDetail(String noInvoice, String serviceCode){
        return getEntityManager().createNativeQuery("SELECT rekening, nvl(debit,0), nvl(kredit,0) FROM table(jurnal_detail_" + serviceCode.toLowerCase() + "( '" + noInvoice + "' )) ORDER BY rekening").getResultList();
    }

    public String findNoInvoice(String no_reg) {
        String idPlan = null;
        try {
            idPlan = (String) getEntityManager().createNamedQuery("Invoice.Native.findNoInvoice").setParameter(1, no_reg).getSingleResult();
        } catch (NoResultException ex) {
            idPlan = null;
        }
        return idPlan;
    }

    public List<Object[]> findInvoiceCredit() {
        List<Object[]> list = new ArrayList<Object[]>();
        try {
            list = getEntityManager().createNamedQuery("Invoice.Native.findInvoiceCredit").getResultList();
        } catch (NoResultException e) {
            list = null;
        }
        return list;
    }

    public String findInvoice(String ppkb_handling) {
        String temp;
        try {
            temp = (String) getEntityManager().createNamedQuery("Invoice.Native.findInvoice").setParameter(1, ppkb_handling).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
    
    public String findNoInvoiceByPpkbHandling(String ppkb_handling) {
        String temp;
        try {
            temp = (String) getEntityManager().createNamedQuery("Invoice.Native.findNoInvoiceByPpkbHandling").setParameter(1, ppkb_handling).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public BigDecimal getKurs(String currCode) {
        try {
            return (BigDecimal) getEntityManager().createNamedQuery("Invoice.Native.getKurs")
                    .setParameter(1, currCode)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Object[] findInvoiceBatalNota(String no_invoice) {
        try {
            return (Object[]) getEntityManager().createNamedQuery("Invoice.Native.findInvoiceBatalNota").setParameter(1, no_invoice).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Object[]> findBatalNota() {
        List<Object[]> list = new ArrayList<Object[]>();
        try {
            list = getEntityManager().createNamedQuery("Invoice.Native.findBatalNota").getResultList();
        } catch (NoResultException e) {
            list = null;
        }
        return list;
    }

    public List<Object[]> findSPTWithInterval(String currCode, String month, String year) throws IOException{
        return getEntityManager().createNamedQuery("Invoice.Native.findSPTWithInterval")
                .setParameter(1, currCode)
                .setParameter(2, month)
                .setParameter(3, year)
                .getResultList();
    }

    /**
     *
     * @param 
     *      1 = sumber
     *      2 = tipeJurnalId
     *      3 = username
     *      4 = currCode
     *      5 = registeredUser
     *      6 = keterangan
     *      7 = isCash
     * @return
     *      0 = no_jurnal
     *      1 = mata_uang_id
     *      2 = tanggal_jurnal
     *      3 = kurs
     *      4 = work_station
     *      5 = cabang_id
     *      6 = cara_penerimaan
     */
    public Object[] insertIntoJurnal(String sumber, String tipeJurnalId, String username, String currCode, String registeredUser, String keterangan, String isCash) throws IOException{
        try {
            Object[] result = (Object[]) getEntityManager().createNamedQuery("Invoice.Native.insertIntoJurnal")
                    .setParameter(1, sumber)
                    .setParameter(2, tipeJurnalId)
                    .setParameter(3, username)
                    .setParameter(4, currCode)
                    .setParameter(5, registeredUser)
                    .setParameter(6, keterangan)
                    .setParameter(7, isCash)
                    .getSingleResult();
            return (result == null || result[0] == null ? null : result);
        }
        catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param
     *      1 = nomorMaster
     * @return
     *      0 = nomorMaster
     *      1 = nama
     *      2 = saldo
     */
    public Object[] getUtipInfo(String nomorMaster) {
        try {
            Object[] result = (Object[]) getEntityManager().createNamedQuery("Invoice.Native.getUtipInfo")
                    .setParameter(1, nomorMaster)
                    .getSingleResult();
            return result;
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param
     *      0 = noJurnal
     *      1 = kodeRekening
     *      2 = masterId
     *      3 = noNota
     *      4 = debit
     *      5 = kredit
     * @return
     *      namaRekening
     */
    public String insertIntoJurnalDetail(String noJurnal, String kodeRekening, String masterId, String noNota, BigDecimal debit, BigDecimal kredit) throws IOException{
        try {
            return (String) getEntityManager().createNamedQuery("Invoice.Native.insertIntoJurnalDetail")
                    .setParameter(1, noJurnal)
                    .setParameter(2, kodeRekening)
                    .setParameter(3, masterId)
                    .setParameter(4, noNota)
                    .setParameter(5, debit)
                    .setParameter(6, kredit)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Boolean doInvoicePayment(String noInvoice, Date paymentDate, String guid){
        try {
            MasterBank masterBank = masterBankFacade.findByGuid(guid);
            
            Invoice invoice = (Invoice) getEntityManager().createNamedQuery("Invoice.findUnpaidByNoInvoice")
                    .setParameter("noInvoice", noInvoice)
                    .getSingleResult();

            if (masterBank != null && invoice.getPaymentStatus().equals(Invoice.UNPAID_STATUS)) {
                UserUtil.setCurrentUser("bri");
                invoice.setPaymentStatus(Invoice.PAID_STATUS);
                invoice.setKasir(masterBank.getId());
                invoice.setPaymentDate(paymentDate);
                invoice.setMasterBank(masterBank);
                invoice.setPaymentBg(BigDecimal.ZERO);
                invoice.setPaymentCek(BigDecimal.ZERO);
                invoice.setPaymentTransfer(BigDecimal.ZERO);
                invoice.setPaymentTunai(BigDecimal.ZERO);
                invoice.setPaymentBank(invoice.getTotalTagihan());
                invoice.getRegistration().setStatusService(Registration.STATUS_APPROVE);

                VerificationID generator = new VerificationID(invoice.getRegistration().getNoReg(), invoice.getNoInvoice());
                invoice.setVerificationCode(generator.getGeneratedID());
                Calendar now = Calendar.getInstance();
                
                if (invoice.getRegistration().getMasterService().getServiceCode().equals("SC009")) {
                    List<Object[]> penumpukanSusulans = penumpukanSusulanServiceFacadeLocal.findPenumpukanSusulanServiceByPPKBnReg(invoice.getRegistration().getPlanningVessel().getNoPpkb(), invoice.getRegistration().getNoReg());

                    if (penumpukanSusulans != null) {
                        for (Object[] o : penumpukanSusulans) {
                            String jobSlipDel = deliveryServiceFacadeLocal.findByPpkbNCont((String) o[2], invoice.getRegistration().getPlanningVessel().getNoPpkb());
                            DeliveryService deliveryService = deliveryServiceFacadeLocal.find(jobSlipDel);
                            deliveryService.setPlacementDate((Date) o[8]);
                            deliveryService.setCounterPrint(0);
                            deliveryService.setValidDate(new Date(((Date) o[9]).getYear(),((Date) o[9]).getMonth(),((Date) o[9]).getDate(),23,59,59));
                            deliveryServiceFacadeLocal.edit(deliveryService);
                        }
                    }
                } else if (invoice.getRegistration().getMasterService().getServiceCode().equals("SC021")) {
                    List<Object[]> penumpukanSusulans = ucPenumpukanSusulanServiceFacadeLocal.findPenumpukanSusulanServiceByPPKBnReg(invoice.getRegistration().getPlanningVessel().getNoPpkb(), invoice.getRegistration().getNoReg());
                    if (penumpukanSusulans != null) {
                        for (Object[] o : penumpukanSusulans) {
                            String jobSlipDel = ucDeliveryServiceFacadeLocal.findByPpkbNIdUC((Integer) o[5], invoice.getRegistration().getPlanningVessel().getNoPpkb());
                            UcDeliveryService ucDeliveryService = ucDeliveryServiceFacadeLocal.find(jobSlipDel);
                            UncontainerizedService uncontainerizedService = ucDeliveryService.getUncontainerizedService();

                            if ((uncontainerizedService.getTruckLoosing()).contentEquals("FALSE")) {
                                if (now.getTime().getHours() > uncontainerizedService.getPlacementDate().getHours()) {
                                    now.add(Calendar.DATE, 1);
                                    now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), uncontainerizedService.getPlacementDate().getHours(), uncontainerizedService.getPlacementDate().getMinutes(), uncontainerizedService.getPlacementDate().getSeconds());
                                    ucDeliveryService.setValidDate(now.getTime());
                                } else if (now.getTime().getHours() == uncontainerizedService.getPlacementDate().getHours()) {
                                    if (now.getTime().getMinutes() > uncontainerizedService.getPlacementDate().getMinutes()) {
                                        now.add(Calendar.DATE, 1);
                                        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), uncontainerizedService.getPlacementDate().getHours(), uncontainerizedService.getPlacementDate().getMinutes(), uncontainerizedService.getPlacementDate().getSeconds());
                                        ucDeliveryService.setValidDate(now.getTime());
                                    } else if (now.getTime().getMinutes() == uncontainerizedService.getPlacementDate().getMinutes()) {
                                        if (now.getTime().getSeconds() > uncontainerizedService.getPlacementDate().getSeconds()) {
                                            now.add(Calendar.DATE, 1);
                                            now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), uncontainerizedService.getPlacementDate().getHours(), uncontainerizedService.getPlacementDate().getMinutes(), uncontainerizedService.getPlacementDate().getSeconds());
                                            ucDeliveryService.setValidDate(now.getTime());
                                        } else if (now.getTime().getSeconds() == uncontainerizedService.getPlacementDate().getSeconds()) {
                                            now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), uncontainerizedService.getPlacementDate().getHours(), uncontainerizedService.getPlacementDate().getMinutes(), uncontainerizedService.getPlacementDate().getSeconds());
                                            ucDeliveryService.setValidDate(now.getTime());
                                        }
                                    }
                                } else {
                                    now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), uncontainerizedService.getPlacementDate().getHours(), uncontainerizedService.getPlacementDate().getMinutes(), uncontainerizedService.getPlacementDate().getSeconds());
                                    ucDeliveryService.setValidDate(now.getTime());
                                }
                                ucDeliveryServiceFacadeLocal.edit(ucDeliveryService);
                            }
                        }
                    }
                } else if (invoice.getRegistration().getMasterService().getServiceCode().equals("SC023")) {
                    PlanningVessel newPlanningVessel = changeVesselServiceFacadeLocal.findNewPlanningVesselByNoReg(invoice.getRegistration().getNoReg());
                    changeVesselOperationLocal.handleUpdateVessel(newPlanningVessel, invoice.getRegistration().getPlanningVessel());
                }

                edit(invoice);
                return true;
            }
        } catch (NoResultException nre) {
            return false;
        } catch (Exception nre) {
            return false;
        }
        return false;
    }

    public Invoice findByNoInvoice(String noInvoice) {
        try {
            return (Invoice) getEntityManager().createNamedQuery("Invoice.findByNoInvoice")
                    .setParameter("noInvoice", noInvoice)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public String invoiceCopyStatus(String noInvoice) {
        Invoice invoice = findByNoInvoice(noInvoice);
        if (invoice != null) {
            Integer counter = invoice.getPrinted() == null ? 0 : invoice.getPrinted();

            invoice.setPrinted(counter + 1);
            edit(invoice);
            if (counter == 0) {
                return "ASLI";
            } else {
                return "SALINAN";
            }
        }
        return null;
    }

    public Invoice publishInvoice(Invoice invoice) {
        invoice.setNoInvoice(iDGeneratorFacade.generateInvoiceID());
        invoice.setNoFakturPajak(iDGeneratorFacade.generateFakturPajakID());
        invoice.setValidasiPrint("TRUE");
        edit(invoice);

        MasterService masterService = masterServiceFacade.find(invoice.getRegistration().getMasterService().getServiceCode());
        invoice.getRegistration().setMasterService(masterService);
        
        if (masterSettingAppFacade.isPaymentBankingEnabled() && invoice.getPaymentType() != null && invoice.getPaymentType().equals("CASH")) {
            try {
                GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
                gc.setTime(invoice.getCreatedDate());
                String noInvoice = bankingSyncFacade.createInvoice(
                        invoice.getNoInvoice(),
                        invoice.getRegistration().getMasterCustomer().getCustCode(),
                        invoice.getRegistration().getMasterCustomer().getName(),
                        invoice.getRegistration().getPlanningVessel().getVesselCode(),
                        invoice.getRegistration().getPlanningVessel().getPreserviceVessel().getMasterVessel().getName(),
                        invoice.getRegistration().getMasterService().getServiceCode(),
                        invoice.getRegistration().getMasterService().getServiceName(),
                        invoice.getMasterCurrency().getCurrCode(),
                        invoice.getTotalTagihan(),
                        DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
            } catch (DatatypeConfigurationException ex) {
                logger.log(Level.SEVERE, null, ex);
            } catch (RuntimeException ex) {
                logger.log(Level.SEVERE, "failed post data to e-banking", ex);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "failed post data to e-banking", ex);
            }
        }

        return invoice;
    }

    public List<String> findLikeReg(String noReg) {
        List<String> result = new ArrayList<String>();
        String sql = "select no_reg from invoice where no_reg like '%" + noReg + "%' and rownum <= 10";
        result = getEntityManager().createNativeQuery(sql).getResultList();
        return result;
    }

    @Override
    public Invoice findByRegForUpdatePrintNumber(String noReg){
        return (Invoice)em.createNamedQuery("Invoice.findByRegForUpdatePrintNumber").setParameter("noReg", noReg).getSingleResult();
    }
}
