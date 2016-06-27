/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ChangeVesselServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.DeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterBankFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PenumpukanSusulanServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcDeliveryServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UcPenumpukanSusulanServiceFacadeRemote;
import com.pelindo.ebtos.ejb.remote.ChangeVesselOperationRemote;
import com.pelindo.ebtos.model.db.DeliveryService;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.UcDeliveryService;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterBank;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterService;
import com.pelindo.ebtos.util.VerificationID;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "cashInvoiceBean")
@ViewScoped
public class CashInvoiceBean implements Serializable {
    @EJB
    private BankingSyncFacadeRemote bankingSyncFacade;
    @EJB
    private DeliveryServiceFacadeRemote deliveryServiceFacade;
    @EJB
    private PenumpukanSusulanServiceFacadeRemote penumpukanSusulanServiceFacade;
    @EJB
    private UcPenumpukanSusulanServiceFacadeRemote ucPenumpukanSusulanServiceFacade;
    @EJB
    private UcDeliveryServiceFacadeRemote ucDeliveryServiceFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacade;
    @EJB
    private MasterBankFacadeRemote masterBankFacadeRemote;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacadeRemote;
    @EJB
    private ChangeVesselServiceFacadeRemote changeVesselServiceFacadeRemote;
    @EJB
    private ChangeVesselOperationRemote changeVesselOperationRemote;

    private List<Object[]> registrations;
    private Registration registration;
    private String no_reg;
    private Date startDate;
    private Date endDate;
    private String selectedBankId;
    private List<MasterBank> masterBanks;
    private Object[] utipDetail;
    private String username;

    /** Creates a new instance of CashInvoiceBean */
    public CashInvoiceBean() {
    }

    @PostConstruct
    private void construct() {
        registration = new Registration();
        registration.setMasterService(new MasterService());
        registration.setMasterCustomer(new MasterCustomer());
        masterBanks = masterBankFacadeRemote.findAll();
        registrations = registrationFacade.findRegistrationInvoiceCashAndCredit();
        LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
        username = loginSessionBean.getUsername();
    }

    public void handleSelectRegistration(ActionEvent event) {
        no_reg = (String) event.getComponent().getAttributes().get("noReg");
        registration = registrationFacade.find(no_reg);
        if (registration.getInvoice() != null && registration.getInvoice().getMasterBank() != null) {
            selectedBankId = registration.getInvoice().getMasterBank().getId();
        }
    }

    public void handleUtipConfirmation(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        utipDetail = invoiceFacade.getUtipInfo("40001");
        context.addCallbackParam("showConfirmation", true);
    }

    public boolean isBalanceSufficient() {
        if (utipDetail != null) {
            return ((BigDecimal) utipDetail[2]).compareTo(registration.getInvoice().getPaymentUtip()) != -1;
        }
        return false;
    }

    public void handlePayment(ActionEvent event) {
        if (registration.getInvoice().getPaymentStatus().equals("PAYMENT")) {
            return;
        }

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        BigDecimal total = registration.getInvoice().getPaymentBg().add(registration.getInvoice().getPaymentCek()).add(registration.getInvoice().getPaymentTransfer()).add(registration.getInvoice().getPaymentTunai()).add(registration.getInvoice().getPaymentUtip());
        String sender = ((CommandButton) event.getComponent()).getId();

        if (total.compareTo(registration.getInvoice().getTotalTagihan()) == 0) {
            if (false && registration.getInvoice().getPaymentUtip().compareTo(BigDecimal.ZERO) == 1 && !sender.contains("paymentWithUtip")) {
                handleUtipConfirmation(event);
                return;
            }

            Date dateNow = Calendar.getInstance().getTime();
            LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
            registration.getInvoice().setPaymentStatus("PAYMENT");
            registration.getInvoice().setPaymentDate(dateNow);
            registration.getInvoice().setKasir(loginSessionBean.getName());
            if (registration.getInvoice().getPaymentTransfer().compareTo(BigDecimal.ZERO) != 0) {
                MasterBank masterBank = masterBankFacadeRemote.find(selectedBankId);
                registration.getInvoice().setMasterBank(masterBank);
            }
            if (registration.getInvoice().getPaymentType().equals("CASH")) {
                registration.setStatusService("approve");
            }
            VerificationID generator = new VerificationID(registration.getNoReg(), registration.getInvoice().getNoInvoice());
            registration.getInvoice().setVerificationCode(generator.getGeneratedID());

            if (registration.getMasterService().getServiceCode().equals("SC009")) {
                Calendar now = Calendar.getInstance();
                List<Object[]> penumpukanSusulans = penumpukanSusulanServiceFacade.findPenumpukanSusulanServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);

                if (penumpukanSusulans != null) {
                    for (Object[] o : penumpukanSusulans) {
                        String jobSlipDel = deliveryServiceFacade.findByPpkbNCont((String) o[2], registration.getPlanningVessel().getNoPpkb());
                        DeliveryService deliveryService = deliveryServiceFacade.find(jobSlipDel);
                        deliveryService.setPlacementDate((Date) o[8]);
                        deliveryService.setCounterPrint(0);
                        deliveryService.setValidDate(new Date(((Date) o[9]).getYear(),((Date) o[9]).getMonth(),((Date) o[9]).getDate(),23,59,59));
                        deliveryServiceFacade.edit(deliveryService);
                    }
                }
            } else if (registration.getMasterService().getServiceCode().equals("SC021")) {
                Calendar now = Calendar.getInstance();
                List<Object[]> penumpukanSusulans = ucPenumpukanSusulanServiceFacade.findPenumpukanSusulanServiceByPPKBnReg(registration.getPlanningVessel().getNoPpkb(), no_reg);
                if (penumpukanSusulans != null) {
                    for (Object[] o : penumpukanSusulans) {
                        String jobSlipDel = ucDeliveryServiceFacade.findByPpkbNIdUC((Integer) o[5], registration.getPlanningVessel().getNoPpkb());
                        UcDeliveryService ucDeliveryService = ucDeliveryServiceFacade.find(jobSlipDel);
                        UncontainerizedService uncontainerizedService = ucDeliveryService.getUncontainerizedService();

                        if (uncontainerizedService.getTruckLoosing().equals("FALSE")) {
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
                            ucDeliveryServiceFacade.edit(ucDeliveryService);
                        }
                    }
                }
            } else if (registration.getMasterService().getServiceCode().equals("SC023")) {
                PlanningVessel newPlanningVessel = changeVesselServiceFacadeRemote.findNewPlanningVesselByNoReg(registration.getNoReg());
                changeVesselOperationRemote.handleUpdateVessel(newPlanningVessel, registration.getPlanningVessel());
            }
            
            if (masterSettingAppFacadeRemote.isPaymentBankingEnabled())
                try {
                    GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
                    gc.setTime(dateNow);
                    bankingSyncFacade.doPayment(registration.getInvoice().getNoInvoice(), DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(CashInvoiceBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RuntimeException re) {
                    Logger.getLogger(CashInvoiceBean.class.getName()).log(Level.SEVERE, null, re);
                } catch (Exception e) {
                    Logger.getLogger(CashInvoiceBean.class.getName()).log(Level.SEVERE, null, e);
                }

            context.addCallbackParam("doPrint", true);
            context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoicePaidLabel.pdf?no_reg=" + registration.getNoReg() + "&servicecode=" + registration.getMasterService().getServiceCode())));

            registrationFacade.edit(registration);
            registrations = registrationFacade.findRegistrationInvoiceCashAndCredit();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
        } else {
            context.addCallbackParam("doPrint", false);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.validation.bills_not_paid");
        }
    }
    
    //penambahan function re print invoice by ade chelsea tanggal 29 april 2014 09:10 jayapura
    public void reprintHandlePayment(ActionEvent event) {
       

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        BigDecimal total = registration.getInvoice().getPaymentBg().add(registration.getInvoice().getPaymentCek()).add(registration.getInvoice().getPaymentTransfer()).add(registration.getInvoice().getPaymentTunai()).add(registration.getInvoice().getPaymentUtip());
        String sender = ((CommandButton) event.getComponent()).getId();

        if (total.compareTo(registration.getInvoice().getTotalTagihan()) == 0) {
            
            
            context.addCallbackParam("doPrint", true);
            context.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
            context.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(fc, "/invoicePaidLabel.pdf?no_reg=" + registration.getNoReg() + "&servicecode=" + registration.getMasterService().getServiceCode())));


            registrations = registrationFacade.findRegistrationInvoiceCashAndCredit();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "Re-Print Nota");
        } else {
            context.addCallbackParam("doPrint", false);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.validation.bills_not_paid");
        }
    }

    public void handleOpenTransactionRecapDialog(ActionEvent event) {
        Calendar now = Calendar.getInstance();
        startDate = now.getTime();
        now.add(Calendar.DAY_OF_MONTH, 1);
        endDate = now.getTime();
    }

    public void handleShowRegistrationsConfirm(ActionEvent event) {
        registrations = registrationFacade.findRegistrationInvoiceCashAndCreditConfirm();
    }

    public void handleShowRegistrationsApprove(ActionEvent event){
        registrations = registrationFacade.findRegistrationInvoiceCashAndCreditApprove();
    }

    public void handleResetTransactionRecapParameter(DateSelectEvent event) {
        org.primefaces.component.calendar.Calendar sender = (org.primefaces.component.calendar.Calendar) event.getSource();
        Date date = (Date) sender.getValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (sender.getId().equals("startDate")) {
            if (date.compareTo(endDate) >= 0) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                endDate = cal.getTime();
            }
        } else if (sender.getId().equals("endDate")) {
            if (date.compareTo(startDate) <= 0) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
                startDate = cal.getTime();
            }
        }
    }

    public void handleDownloadTransactionRecap(ActionEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        RequestContext context = RequestContext.getCurrentInstance();
        CommandButton sender = (CommandButton) event.getSource();

        String reportType = "1";
        if (sender.getId().equals("reportType2")) {
            reportType = "2";
        }

        context.addCallbackParam("doPrint", true); //harus ada, buat ngeflag di izinkan ngeprint ato ngga
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../RealisasiValidasiNota.pdf?"
                + "from=" + formatter.format(startDate) + "&"
                + "to=" + formatter.format(endDate) + "&"
                + "username=" + username + "&"
                + "tipe=" + reportType));
    }

    /**
     * @return the registration
     */
    public Registration getRegistration() {
        return registration;
    }

    /**
     * @param registration the registration to set
     */
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    /**
     * @return the registrations
     */
    public List<Object[]> getRegistrations() {
        return registrations;
    }

    /**
     * @param registrations the registrations to set
     */
    public void setRegistrations(List<Object[]> registrations) {
        this.registrations = registrations;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSelectedBankId() {
        return selectedBankId;
    }

    public void setSelectedBankId(String SelectedBankId) {
        this.selectedBankId = SelectedBankId;
    }

    public List<MasterBank> getMasterBanks() {
        return masterBanks;
    }

    public void setMasterBanks(List<MasterBank> masterBanks) {
        this.masterBanks = masterBanks;
    }

    public Object[] getUtipDetail() {
        return utipDetail;
    }

    public void setUtipDetail(Object[] utipDetail) {
        this.utipDetail = utipDetail;
    }
}
