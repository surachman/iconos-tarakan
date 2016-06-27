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
import java.util.Date;
import java.util.List;
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
@ManagedBean(name="createJournalBean")
@ViewScoped
public class CreateJournalBean implements Serializable{
    @EJB
    private RecapJurnalFacadeRemote recapJurnalFacade;
    @EJB
    private InvoiceFacadeRemote invoiceFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private JurnalFinanceOperationRemote jurnalFinanceOperationRemote;

    private List<Object[]> journalCandidates;
    private List<Object[]> journals;
    private List<Object[]> journalDetail;
    private List<Invoice> invoices;
    private Object[] selectedJournalCandidates;
    private Object[] selectedJournal;
    private Object[] totalKeseluruhan;
    private Boolean isJKMGroupingByCustomer; 
    private Date dateFilter = new Date();

    /** Creates a new instance of journalBean */
    public CreateJournalBean() {}

    @PostConstruct
    private void construct(){
        LoginSessionBean session = LoginSessionBean.getCurrentInstance();
        isJKMGroupingByCustomer = masterSettingAppFacade.isJKMGroupingByCustomerEnabled();
        
        if (session != null && session.isActive()) {
            if (isJKMGroupingByCustomer) {
                journalCandidates = invoiceFacade.findJKMCandidateByKasir(session.getUsername());
                journals = invoiceFacade.findJKMByKasir(session.getUsername(), dateFilter);
            } else {
                journalCandidates = invoiceFacade.findJKMCandidatePerInvoices();
                journals = invoiceFacade.findJKMPerInvoices(dateFilter);
            }
            selectedJournalCandidates = getJournalCandidates().toArray();
        }
    }

    public void handleJKMFilter(ActionEvent event){
        construct();
    }

    public void handleCreateJurnal(ActionEvent event){
        LoginSessionBean login = LoginSessionBean.getCurrentInstance();

        try {
            recapJurnalFacade.createJKMRecap(getSelectedJournalCandidates(), login.getUsername());
            dateFilter = new Date();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.validation.create_jkm_succeed");
        } catch (RuntimeException e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.validation.create_jkm_failed");
        } catch (Exception e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "Error", "application.validation.create_jkm_failed");
        }

        construct();
    }

    public void handleSelectJournal(ActionEvent event){
        CommandButton sender = (CommandButton) event.getSource();
        LoginSessionBean login = LoginSessionBean.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();

        String list = context.getExternalContext().getRequestParameterMap().get("list");
        Integer index = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("index"));

        if (list.equals("journalCandidates")) {
            selectedJournal = journalCandidates.get(index);
        } else if (list.equals("journals")) {
            selectedJournal = journals.get(index);
        } else {
            return;
        }
        
        String currCode = (String) selectedJournal[1];
        String custCode = (String) selectedJournal[4];
        String tipeJurnalId = (String) selectedJournal[0];
        
        if (sender.getId().equals("invoicesButton")){
            invoices = invoiceFacade.findJournalInvoiceCash(login.getUsername(), currCode, custCode);
        } else if (sender.getId().equals("detailButton")){
            if (isJKMGroupingByCustomer) {
                journalDetail = invoiceFacade.findJournalDetailCash(login.getUsername(), custCode, currCode, tipeJurnalId);
            } else {
                String noInvoice = (String) selectedJournal[13];
                String serviceCode = (String) selectedJournal[14];
                journalDetail = invoiceFacade.findJournalDetail(noInvoice, serviceCode);
            }
        }
    }

    public void handlePrintJournal(ActionEvent event) {
        String sumber = (String) event.getComponent().getAttributes().get("sumber");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (sumber != null) {
            String key = jurnalFinanceOperationRemote.preparePrintBuktiJKM(sumber);

            if (key != null) {
                requestContext.addCallbackParam("doPrint", true);
                requestContext.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(facesContext, String.format("/report?key=%s", key))));
                return;
            } else {
                throw new RuntimeException("failed to generate report");
            }
        }

        requestContext.addCallbackParam("doPrint", false);
    }

    public void handlePrintJournal1(ActionEvent event) {
        Boolean doPrint = false;
        String sumber = (String) event.getComponent().getAttributes().get("sumber");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (sumber != null) {
            String key = jurnalFinanceOperationRemote.preparePrintBuktiJKM(sumber);

            if (key != null) {
                doPrint = true;
                requestContext.addCallbackParam("target", (String) event.getComponent().getAttributes().get("target"));
                requestContext.addCallbackParam("url", StringEscapeUtils.escapeHtml3(UrlHelper.resolveFullAddress(facesContext, String.format("/report?key=%s", key))));
            } else {
                throw new RuntimeException("failed to generate report");
            }
        }

        requestContext.addCallbackParam("doPrint", doPrint);
    }
    
    public List<Object[]> getJournalCandidates() {
        return journalCandidates;
    }

    public void setJournalCandidates(List<Object[]> journals) {
        this.journalCandidates = journals;
    }

    public Object[] getTotalKeseluruhan() {
        return totalKeseluruhan;
    }

    public void setTotalKeseluruhan(Object[] totalKeseluruhan) {
        this.totalKeseluruhan = totalKeseluruhan;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Object[]> getJournalDetail() {
        return journalDetail;
    }

    public void setJournalDetail(List<Object[]> journalDetail) {
        this.journalDetail = journalDetail;
    }

    public Object[] getSelectedJournalCandidates() {
        return selectedJournalCandidates;
    }

    public void setSelectedJournalCandidates(Object[] selectedJournals) {
        this.selectedJournalCandidates = selectedJournals;
    }

    public Date getDateFilter() {
        return dateFilter;
    }

    public void setDateFilter(Date dateFilter) {
        this.dateFilter = dateFilter;
    }

    public List<Object[]> getJournals() {
        return journals;
    }

    public void setJournals(List<Object[]> journals) {
        this.journals = journals;
    }

    public Object[] getSelectedJournal() {
        return selectedJournal;
    }

    public Boolean getIsJKMGroupingByCustomer() {
        return isJKMGroupingByCustomer;
    }
}
