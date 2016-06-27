/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.IDGeneratorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCurrencyFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterManualActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterManualActivityTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterMateraiFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganNotaManualDetailFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganNotaManualFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.Invoice;
import com.pelindo.ebtos.model.db.PerhitunganNotaManual;
import com.pelindo.ebtos.model.db.PerhitunganNotaManualDetail;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import com.qtasnim.url.UrlHelper;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Dyware-Dev01
 */
@ManagedBean(name = "notaManualBean")
@ViewScoped
public class NotaManualBean implements Serializable {

    @EJB
    private IDGeneratorFacadeRemote iDGeneratorFacade;
    @EJB
    private PerhitunganNotaManualFacadeRemote notaManualFacadeRemote;
    @EJB
    private PerhitunganNotaManualDetailFacadeRemote notaManualDetailFacadeRemote;
    @EJB
    private MasterManualActivityTypeFacadeRemote manualActivityTypeFacadeRemote;
    @EJB
    private MasterManualActivityFacadeRemote manualActivityFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private MasterCurrencyFacadeRemote masterCurrencyFacadeRemote;

    private List<Object[]> notaManuals;
    private List<Object[]> manualActivityTypes;
    private List<PerhitunganNotaManualDetail> notaManualDetails;
    private List<Object[]> customers;
    private List<Object[]> manualActivityNames;
    private CustomData[] customDatas = new CustomData[8];
    private Boolean isIncludeTax = false;
    private PerhitunganNotaManualDetail notaManualDetail;
    private String tb1;
    private Invoice invoice;
    private Registration registration;
    private PerhitunganNotaManual perhitunganNotaManual;
    private BigDecimal jumTagihan = BigDecimal.ZERO;
    private BigDecimal tax = BigDecimal.ZERO;
    private BigDecimal materai = BigDecimal.ZERO;
    private BigDecimal grandTot = BigDecimal.ZERO;
    private int loopInt = 0;
    LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();

    /** Creates a new instance of NotaManualBean */
    public NotaManualBean() {}

    @PostConstruct
    private void construct(){
        this.notaManuals = notaManualFacadeRemote.findAllNative();
        this.manualActivityTypes = manualActivityTypeFacadeRemote.findAllNative();
        this.customers = masterCustomerFacadeRemote.findMasterCustomers();
        notaManualDetails = new ArrayList<PerhitunganNotaManualDetail>(8);
        notaManualDetail = new PerhitunganNotaManualDetail();
        perhitunganNotaManual = new PerhitunganNotaManual();
    }

    public BigDecimal getGrandTot() {
        return grandTot;
    }

    public void setGrandTot(BigDecimal grandTot) {
        this.grandTot = grandTot;
    }

    public BigDecimal getMaterai() {
        return materai;
    }

    public void setMaterai(BigDecimal materai) {
        this.materai = materai;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public int getLoopInt() {
        return loopInt;
    }

    public void setLoopInt(int loopInt) {
        this.loopInt = loopInt;
    }

    public BigDecimal getJumTagihan() {
        return jumTagihan;
    }

    public void setJumTagihan(BigDecimal jumTagihan) {
        this.jumTagihan = jumTagihan;
    }

    public PerhitunganNotaManual getPerhitunganNotaManual() {
        return perhitunganNotaManual;
    }

    public void setPerhitunganNotaManual(PerhitunganNotaManual perhitunganNotaManual) {
        this.perhitunganNotaManual = perhitunganNotaManual;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Boolean getIsIncludeTax() {
        return isIncludeTax;
    }

    public void setIsIncludeTax(Boolean isIncludeTax) {
        this.isIncludeTax = isIncludeTax;
    }

    public CustomData[] getCustomDatas() {
        return customDatas;
    }

    public List<Object[]> getNotaManuals() {
        return notaManuals;
    }

    public void setNotaManuals(List<Object[]> notaManuals) {
        this.notaManuals = notaManuals;
    }

    public List<Object[]> getManualActivityTypes() {
        return manualActivityTypes;
    }

    public void setManualActivityTypes(List<Object[]> manualActivityTypes) {
        this.manualActivityTypes = manualActivityTypes;
    }

    public PerhitunganNotaManualDetail getNotaManualDetail() {
        return notaManualDetail;
    }

    public void setNotaManualDetail(PerhitunganNotaManualDetail notaManualDetail) {
        this.notaManualDetail = notaManualDetail;
    }

    public List<PerhitunganNotaManualDetail> getNotaManualDetails() {
        return notaManualDetails;
    }

    public void setNotaManualDetails(List<PerhitunganNotaManualDetail> notaManualDetails) {
        this.notaManualDetails = notaManualDetails;
    }

    public List<Object[]> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Object[]> customers) {
        this.customers = customers;
    }

    public String getTb1() {
        return tb1;
    }

    public void setTb1(String tb1) {
        this.tb1 = tb1;
    }

    /** Command Action */
    public void onChangeActivityType(ValueChangeEvent event) {
        int id = Integer.valueOf(event.getNewValue().toString());
        this.manualActivityNames = manualActivityFacadeRemote.findByType(id);
        loopInt = manualActivityNames.size();
        /** clear list */
        for (int i = 0; i < 8; i++) {
            CustomData cd = new CustomData();
            cd.id = null;
            cd.name = "-";
            cd.amount = BigDecimal.ZERO;
            customDatas[i] = cd;
            this.jumTagihan = BigDecimal.ZERO;
        }
        /** fill the list */
        for (int i = 0; i < manualActivityNames.size(); i++) {
            Object[] temp = manualActivityNames.get(i);
            CustomData cd = new CustomData();
//            cd.id = temp[0].toString();
            cd.id = "";
            cd.name = temp[1].toString();
            cd.amount = new BigDecimal(((Number) temp[2]).doubleValue());
            customDatas[i] = cd;
            this.jumTagihan = jumTagihan.add(cd.amount);
        }
    }

    public void onChangeTax(ValueChangeEvent event) {
        boolean newValue = (Boolean) event.getNewValue();
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("isInclude", newValue);
    }

    public void handleOnAdd(ActionEvent event) {
        resetObject();
        System.out.println(invoice.getNoReg());
    }

    public void resetObject() {
        customDatas = new CustomData[8];
        registration = new Registration();
        perhitunganNotaManual = new PerhitunganNotaManual();
        invoice = new Invoice();
        this.manualActivityTypes = manualActivityTypeFacadeRemote.findAllNative();
        this.customers = masterCustomerFacadeRemote.findMasterCustomers();
        this.isIncludeTax = false;
        this.jumTagihan = BigDecimal.ZERO;
        this.grandTot = BigDecimal.ZERO;
        this.materai = BigDecimal.ZERO;
        this.tax = BigDecimal.ZERO;
    }

    public void handleOnSubmit(ActionEvent event) {
        String regId = iDGeneratorFacade.generateRegistrationID();
        //String NoPpkb = iDGeneratorFacade.generatePpkbKeuanganID();

        /*
         * Input ke Registrasi
         */
        registration = new Registration();
        registration.setNoReg(regId);
        registration.setMasterCustomer(new MasterCustomer(perhitunganNotaManual.getCustCode()));
        registration.setMasterService(new MasterService("SCNMA"));
        registrationFacadeRemote.edit(registration);
        System.out.println("Input Registrasi " + regId);
        /*
         * Input ke invoice 
         */
        System.out.println("jumlah Tagihan :" + this.getJumTagihan() + " | tax  :" + this.getTax() + " | materai : " + this.getMaterai());
        invoice = new Invoice();
        invoice.setNoReg(regId);
        invoice.setMasterCurrency(masterCurrencyFacadeRemote.getIDRCurrency());
        invoice.setPaymentType("KREDIT");
        invoice.setPaymentStatus("UNPAID");
        invoice.setJumlahTagihan(this.getJumTagihan());
        invoice.setValidasiPrint("FALSE");
        invoice.setPpn(this.getTax());
        invoice.setMaterai(this.getMaterai());
        invoice.setNoInvoice(iDGeneratorFacade.generateInvoiceNotaManualID());

        if (!invoice.getPpn().equals(BigDecimal.ZERO)) {
            invoice.setNoFakturPajak(iDGeneratorFacade.generateFakturPajakID());
        }

        invoice.setTotalTagihan(this.getJumTagihan().add(invoice.getPpn()).add(invoice.getMaterai()));
        invoiceFacadeRemote.edit(invoice);
        System.out.println("Input Invoice ");
        /*
         * Input ke nota manual         */
        perhitunganNotaManual.setNoReg(regId);
        perhitunganNotaManual.setNoPpkb("");
        perhitunganNotaManual.setPrintCounter(0);
        perhitunganNotaManual.setRevCounter(0);
        notaManualFacadeRemote.edit(perhitunganNotaManual);
        System.out.println("Input perhitungan manual " + regId);

        System.out.println(loopInt);
        /*
         * Input ke nota manual detail
         */

        for (int i = 0; i < 8; i++) {
            if ((!customDatas[i].getName().equalsIgnoreCase("-")) || (!customDatas[i].getId().isEmpty())) {
                PerhitunganNotaManualDetail notaManualDetailss = new PerhitunganNotaManualDetail();
                notaManualDetailss.setActivityName(customDatas[i].getName());
                notaManualDetailss.setActivityType(notaManualDetail.getActivityType());
                notaManualDetailss.setAmount(customDatas[i].getAmount());
                notaManualDetailss.setNoReg(regId);
                notaManualDetailFacadeRemote.edit(notaManualDetailss);
            }
        }
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        this.notaManuals = notaManualFacadeRemote.findAllNative();
        /*
         * Generate
         */


        /*
         * Print
         */
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("./../../../../NotaManualInvoice.pdf?no_reg=" + regId + "&username=" + loginSessionBean.getUsername() + "&to=" + invoice.getTotalTagihan() + "&curr=IDR"));

    }

    public void handleOnPrint(ActionEvent event) {
        String regId = (String) event.getComponent().getAttributes().get("noReg");
        invoice = invoiceFacadeRemote.find(regId);
        /*
         * Print
         */
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
//        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("./../../../../NotaManualInvoice.pdf?no_reg=" + regId + "&username=" + loginSessionBean.getUsername() + "&to=" + invoice.getTotalTagihan().intValueExact() + "&curr=IDR"));
        FacesContext fc = FacesContext.getCurrentInstance();
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/NotaManualInvoice.pdf?no_reg=" + regId + "&username=" + loginSessionBean.getUsername() + "&to=" + invoice.getTotalTagihan().intValueExact() + "&curr=IDR")));
        
    }

    public void handleOnEdit(ActionEvent event) {
        String regId = (String) event.getComponent().getAttributes().get("noReg");
        BigDecimal nmId = (BigDecimal) event.getComponent().getAttributes().get("nmId");

        resetObject();

        /** Find registrasi    */
        registration = registrationFacadeRemote.find(regId);

        /** Find invoice  */
        invoice = invoiceFacadeRemote.find(regId);
        this.tax = invoice.getPpn();
        this.materai = invoice.getMaterai();
        this.grandTot = invoice.getTotalTagihan();
        if (tax.compareTo(BigDecimal.ZERO) == 1) {
            this.isIncludeTax = true;
        } else {
            this.isIncludeTax = false;
        }

        /** Find nota manual */
        perhitunganNotaManual = notaManualFacadeRemote.find(nmId);

        /* Find nota manual detail */
//        this.notaManualDetail = notaManualDetailFacadeRemote.findByNoReg(perhitunganNotaManual.getNoReg());
        List<Object[]> notaManualss = notaManualDetailFacadeRemote.findByNoReg(regId);
        this.notaManualDetail = notaManualDetailFacadeRemote.find(new BigDecimal(notaManualss.get(0)[0].toString()));


        /** clear list */
        for (int i = 0; i < 8; i++) {
            CustomData cd = new CustomData();
            cd.id = null;
            cd.name = "-";
            cd.amount = BigDecimal.ZERO;
            customDatas[i] = cd;
            this.jumTagihan = BigDecimal.ZERO;
        }

        /** fill the list */
        for (int i = 0; i < notaManualss.size();  i++) {
            Object[] temp = notaManualss.get(i);
            CustomData cd = new CustomData();
            cd.id = temp[0].toString();
            cd.name = temp[1].toString();
            cd.amount = new BigDecimal(((Number) temp[2]).doubleValue());
            customDatas[i] = cd;
            this.jumTagihan = jumTagihan.add(cd.amount);
        }
    }

    public void handleOnSubmitEdit(ActionEvent event) {
        String regId = (String) event.getComponent().getAttributes().get("noReg");
//        BigDecimal nmId = (BigDecimal) event.getComponent().getAttributes().get("nmId");

        PerhitunganNotaManualDetail tNotaDetail;

        /**  edit list  */
        for (int i = 0; i < 8; i++) {
            if (!customDatas[i].getId().isEmpty()) {
                tNotaDetail = notaManualDetailFacadeRemote.find(new BigDecimal(customDatas[i].getId()));
                /** cek apakah harus di hapus*/
                if ((customDatas[i].getName().equals("-")) && (customDatas[i].getAmount().compareTo(BigDecimal.ZERO) == 0)) {
                    System.out.println("hapus " + tNotaDetail.getId());
                    notaManualDetailFacadeRemote.remove(tNotaDetail);
                } else {
                    tNotaDetail.setActivityName(customDatas[i].getName());
                    tNotaDetail.setAmount(customDatas[i].getAmount());
                    System.out.println("edit " + tNotaDetail.getId());
                    notaManualDetailFacadeRemote.edit(tNotaDetail);
                }
            } else {
                if ((!customDatas[i].getName().equalsIgnoreCase("-"))) {
                    tNotaDetail = new PerhitunganNotaManualDetail();
                    tNotaDetail.setActivityType(notaManualDetail.getActivityType());
                    tNotaDetail.setActivityName(customDatas[i].getName());
                    tNotaDetail.setAmount(customDatas[i].getAmount());
                    tNotaDetail.setNoReg(regId);
                    System.out.println("add " + tNotaDetail.getId());
                    notaManualDetailFacadeRemote.edit(tNotaDetail);
                }
            }
        }

        invoice.setJumlahTagihan(this.getJumTagihan());
        invoice.setPpn(this.getTax());
        invoice.setMaterai(this.getMaterai());
        invoice.setTotalTagihan(this.getJumTagihan().add(invoice.getPpn()).add(invoice.getMaterai()));
        System.out.println("edit " + invoice.getNoInvoice());
        invoiceFacadeRemote.edit(invoice);

        System.out.println("edit " + perhitunganNotaManual.getId() + " isi " + perhitunganNotaManual.getNoBentukTiga());
        notaManualFacadeRemote.edit(perhitunganNotaManual);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        this.notaManuals = notaManualFacadeRemote.findAllNative();
    }

    /** Custom Class */
    public class CustomData implements Serializable {

        String id;
        String name;
        BigDecimal amount;

        public CustomData() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
}
