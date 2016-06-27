/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.ActiveDirectoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCountryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.model.NamedObject;
import com.pelindo.ebtos.model.db.master.MasterCountry;
import com.pelindo.ebtos.model.db.master.MasterCustType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="masterCustomerBean")
@ViewScoped
public class MasterCustomerBean implements Serializable{
    private static final String DEFAULT_PASSWORD_VALUE = "97038849";
    private static final String AGENT_CUST_TYPE_ID_PARAM = "ebtos.agent_cust_type_id";

    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacade;
    @EJB
    private ActiveDirectoryFacadeRemote activeDirectoryFacade;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;
    @EJB
    private MasterCustTypeFacadeRemote masterCustTypeFacadeRemote;
    @EJB
    private MasterCountryFacadeRemote masterCountryFacadeRemote;
    
    private MasterCustomer mi;
    private List<MasterCustomer> mis;
    private List<MasterCustType> mct;
    private List<MasterCountry> mc;
    private String password;
    private String repeatPassword;
    private NamedObject activeDirectoryUser;
    private int custTypeId;
    private Boolean isEdit;


    /** Creates a new instance of MasterCustomerBean */
    public MasterCustomerBean() {}

    @PostConstruct
    private void construct() {
        mi = new MasterCustomer();
        mi.setMasterCustType(new MasterCustType());
        mi.setMasterCountry(new MasterCountry());
        custTypeId = masterSettingAppFacade.find(AGENT_CUST_TYPE_ID_PARAM).getValueInteger();
        mis = masterCustomerFacade.findAll();
        mct = masterCustTypeFacadeRemote.findAll();
        mc = masterCountryFacadeRemote.findAll();
    }

    public void handleOnAdd(ActionEvent event) {
        mi = new MasterCustomer();
        mi.setMasterCustType(new MasterCustType());
        mi.setMasterCountry(new MasterCountry());
        isEdit = false;
    }

    public void handleClick(ActionEvent event) {
        String customerCode = (String) event.getComponent().getAttributes().get("code");
        mi = masterCustomerFacade.find(customerCode);
        isEdit = true;

        if (mi.getMasterCountry() == null){
            if (mc.size() > 0){
                mi.setMasterCountry(mc.get(0));
            }
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.validation.master_country");
        }

        if (isAgent()){
            activeDirectoryUser = activeDirectoryFacade.findUserByUid(customerCode);
            password = DEFAULT_PASSWORD_VALUE;
            repeatPassword = DEFAULT_PASSWORD_VALUE;
        }
    }

    public void handleSaveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        try {
            if (isEdit && isAgent() && activeDirectoryUser != null){
                if (password.equals(repeatPassword) && !password.equals(DEFAULT_PASSWORD_VALUE)) {
                    activeDirectoryFacade.changePassword(activeDirectoryUser, password);
                } else {
                    context.addCallbackParam("loggedIn", false);
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Warning", "application.validation.password");
                    return;
                }
            }

            masterCustomerFacade.edit(mi);
            mis = masterCustomerFacade.findAll();
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            loggedIn = true;
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.failed");
        }

        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void handleOnDelete(ActionEvent event) {
        masterCustomerFacade.remove(mi);
        if (isAgent()){
            setActiveDirectoryUser(activeDirectoryFacade.findUserByUid(mi.getCustCode()));
            password = DEFAULT_PASSWORD_VALUE;
            repeatPassword = DEFAULT_PASSWORD_VALUE;
            if (getActiveDirectoryUser() != null){
                activeDirectoryFacade.remove(activeDirectoryUser);
                activeDirectoryUser = null;
            }
        }
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        mis = masterCustomerFacade.findAll();
        mi = new MasterCustomer();
        mi.setMasterCustType(new MasterCustType());
        mi.setMasterCountry(new MasterCountry());
    }

    public void handleRegisterActiveDirectory(ActionEvent event){
        activeDirectoryUser = new NamedObject(mi.getName(), null, mi.getName(), mi.getCustCode());
        activeDirectoryFacade.create(activeDirectoryUser, NamedObject.AGENT_DN, mi.getCustCode());
        if (activeDirectoryFacade.getMessage() != null){
            activeDirectoryUser = null;
            FacesHelper.addErrorFacesMessage(FacesContext.getCurrentInstance(), activeDirectoryFacade.getMessage());
        }
        else
            activeDirectoryUser = activeDirectoryFacade.findUserByUid(activeDirectoryUser.getUid());
    }

    public void handleUnregisterActiveDirectory(ActionEvent event){
        activeDirectoryFacade.remove(activeDirectoryUser);
        activeDirectoryUser = null;
        if (activeDirectoryFacade.getMessage() != null)
            FacesHelper.addErrorFacesMessage(FacesContext.getCurrentInstance(), activeDirectoryFacade.getMessage());
    }

    public void handleMasterCustTypeChanged(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        activeDirectoryUser = null;
        
        if (isEdit && newItem == custTypeId) {
            activeDirectoryUser = activeDirectoryFacade.findUserByUid(mi.getCustCode());
            password = DEFAULT_PASSWORD_VALUE;
            repeatPassword = DEFAULT_PASSWORD_VALUE;
        }
    }

    public boolean isAgent(){
        if (mi.getMasterCustType() == null){
            return false;
        } else {
            if (mi.getMasterCustType().getCustTypeId() == null)
                return false;
            return (mi.getMasterCustType().getCustTypeId() == custTypeId);
        }
    }

    public List<MasterCustType> getMct() {
        return mct;
    }

    public void setMct(List<MasterCustType> mct) {
        this.mct = mct;
    }

    public List<MasterCountry> getMc() {
        return mc;
    }

    public void setMc(List<MasterCountry> mc) {
        this.mc = mc;
    }

    public MasterCustomer getMi() {
        return mi;
    }

    public void setMi(MasterCustomer mi) {
        this.mi = mi;
    }

    public List<MasterCustomer> getMis() {
        return mis;
    }

    public void setMis(List<MasterCustomer> mis) {
        this.mis = mis;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public NamedObject getActiveDirectoryUser() {
        return activeDirectoryUser;
    }
    
    public void setActiveDirectoryUser(NamedObject activeDirectoryUser) {
        this.activeDirectoryUser = activeDirectoryUser;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }
}
