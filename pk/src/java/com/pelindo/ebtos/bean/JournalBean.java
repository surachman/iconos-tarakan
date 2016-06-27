/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RecapJurnalFacadeRemote;
import com.pelindo.ebtos.ejb.remote.JurnalFinanceOperationRemote;
import com.pelindo.ebtos.model.db.Invoice;
import java.io.Serializable;
import java.math.BigDecimal;
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
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;

/**
 *
 * @author R Seno Anggoro
 */
@ManagedBean(name="journalBean")
@ViewScoped
public class JournalBean implements Serializable{
    @EJB
    private RecapJurnalFacadeRemote recapJurnalFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacadeRemote;
    @EJB
    private JurnalFinanceOperationRemote jurnalFinanceOperationRemote;
    
    private List<Object[]> journals;
    private List<Object[]> journalDetail;
    private List<Invoice> invoices;
    private Object[] selectedJournals;
    private Object[] selectedJournal;
    private Object[] totalKeseluruhan;
    private Boolean isSimpatReady;
    private Boolean isJKMGroupingByCustomer;
    private LoginSessionBean login;

    /** Creates a new instance of journalBean */
    public JournalBean() {}

    @PostConstruct
    public void construct(){
        LoginSessionBean session = LoginSessionBean.getCurrentInstance();
        isJKMGroupingByCustomer = masterSettingAppFacadeRemote.isJKMGroupingByCustomerEnabled();
        isSimpatReady = false;
        login = LoginSessionBean.getCurrentInstance();

        try {
            BigDecimal idr = invoiceFacade.getKurs("IDR");
            BigDecimal usd = invoiceFacade.getKurs("USD");

            if (idr != null && usd != null) {
                isSimpatReady = true;
            }
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "SIMPAT server not available", re);
        }

        if (isSimpatReady && session != null && session.isActive()) {
            journals = invoiceFacade.findJournalsPerInvoice();
            selectedJournals = getJournals().toArray();
        }
    }

    public void handleSendAndPrint(ActionEvent event){
        boolean doPrint = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Integer index = Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("index"));
        selectedJournal = journals.get(index);
        selectedJournals = new Object[]{ selectedJournal };
        
        String noInvoice = (String) selectedJournal[5];
        String currCode = (String) selectedJournal[1];
        String paymentType = (String) selectedJournal[4];
        String custCode = (String) selectedJournal[7];
        String serviceCode = (String) selectedJournal[6];
        String tipeJurnalId = (String) selectedJournal[0];

        try {
            if (paymentType.equals("CASH")) {
                if (isJKMGroupingByCustomer) {
                    journalDetail = invoiceFacade.findJournalDetailCash(login.getUsername(), custCode, currCode, tipeJurnalId);
                } else {
                    journalDetail = invoiceFacade.findJournalDetail(noInvoice, serviceCode);
                }
            } else if (paymentType.equals("KREDIT")) {
                journalDetail = invoiceFacade.findJournalDetailKredit(noInvoice, serviceCode);
            } else {
                throw new RuntimeException("PaymentType must be CASH or KREDIT");
            }
            
            BigDecimal debit = BigDecimal.ZERO;
            BigDecimal kredit = BigDecimal.ZERO;
            
            for (Object[] row: journalDetail) {
                debit = debit.add((BigDecimal) row[1]);
                kredit = kredit.add((BigDecimal) row[2]);
            }
            
            if (debit.compareTo(kredit) != 0) {
                throw new RuntimeException("Journal not balance");
            }

            if (paymentType.equals("CASH")) {
                String sumber = paymentType.equals("CASH") ? (String) selectedJournal[11] : (String) selectedJournal[5];
                String key = jurnalFinanceOperationRemote.preparePrintBuktiJKM(sumber);

                if (key != null) {
                    doPrint = true;
                    requestContext.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
                    requestContext.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(facesContext, String.format("/report?key=%s", key))));
                } else {
                    throw new RuntimeException("Failed to generate report");
                }
            }

            handleSendJurnal();
            journals.remove(selectedJournal);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.validation.send_journals_succeed");
        } catch (RuntimeException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            FacesHelper.addFacesTextMessage(
                    facesContext, 
                    FacesMessage.SEVERITY_ERROR, 
                    "Error", 
                    String.format("%s: %s" ,FacesHelper.getLocaleMessage("application.validation.send_journals_failed", facesContext), e.getMessage()), 
                    null);
        }
        
        invoices = null;
        journalDetail = null;
        requestContext.addCallbackParam("doPrint", doPrint);
    }

    public void handleSendJurnal(ActionEvent event){
        try {
            handleSendJurnal();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.validation.send_journals_succeed");
        } catch (RuntimeException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.validation.send_journals_failed");
        }

        journals = invoiceFacade.findJournalsPerInvoice();
        invoices = null;
        journalDetail = null;
    }

    private void handleSendJurnal() {
        recapJurnalFacade.postingJurnalToSimpat(getSelectedJournals(), login.getUsername());
    }

    public void handleSelectJournal(ActionEvent event){
        try {
            CommandButton sender = (CommandButton) event.getSource();
            FacesContext context = FacesContext.getCurrentInstance();
            Integer index = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("index"));
            selectedJournal = journals.get(index);

            String noInvoice = (String) selectedJournal[5];
            String currCode = (String) selectedJournal[1];
            String paymentType = (String) selectedJournal[4];
            String custCode = (String) selectedJournal[7];
            String serviceCode = (String) selectedJournal[6];
            String tipeJurnalId = (String) selectedJournal[0];

            if (sender.getId().equals("invoicesButton")){
                if (paymentType.equals("CASH")) {
                    String sumber = (String) selectedJournal[11];
                    invoices = recapJurnalFacade.findInvoicesBySumber(sumber);
                } else if (paymentType.equals("KREDIT")) {
                    invoices = invoiceFacade.findJournalInvoiceKredit(noInvoice);
                } else {
                    throw new RuntimeException("paymentType must be CASH or KREDIT");
                }
            } else if (sender.getId().equals("detailButton")){
                if (paymentType.equals("CASH")) {
                    if (isJKMGroupingByCustomer) {
                        journalDetail = invoiceFacade.findJournalDetailCash(login.getUsername(), custCode, currCode, tipeJurnalId);
                    } else {
                        journalDetail = invoiceFacade.findJournalDetail(noInvoice, serviceCode);
                    }
                } else if (paymentType.equals("KREDIT")) {
                    journalDetail = invoiceFacade.findJournalDetailKredit(noInvoice, serviceCode);
                } else {
                    throw new RuntimeException("paymentType must be CASH or KREDIT");
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
        }
    }
    
    public List<Object[]> getJournals() {
        return journals;
    }

    public Object[] getTotalKeseluruhan() {
        return totalKeseluruhan;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public List<Object[]> getJournalDetail() {
        return journalDetail;
    }
    
    public Object[] getSelectedJournals() {
        return selectedJournals;
    }

    public void setSelectedJournals(Object[] selectedJournals) {
        this.selectedJournals = selectedJournals;
    }

    public Boolean getIsSimpatReady() {
        return isSimpatReady;
    }

    public Object[] getSelectedJournal() {
        return selectedJournal;
    }

    public void setSelectedJournal(Object[] selectedJournal) {
        this.selectedJournal = selectedJournal;
    }
}
