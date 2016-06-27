/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.bean.application.Navigation;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name="masterSettingAppBean")
@ViewScoped
public class MasterSettingAppBean implements Serializable{
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacade;

    private List<MasterSettingApp> settingApps;
    private MasterSettingApp settingApp;
    private Navigation navigation;
    private String selectedItem;

    /** Creates a new instance of MasterSettingAppBean */
    public MasterSettingAppBean() {}

    @PostConstruct
    private void construct(){
        settingApps = masterSettingAppFacade.findAllSorted();
        navigation = Navigation.getCurrentInstance();
    }

    public void handleEdit(ActionEvent event){
        String id_settingApp = (String) event.getComponent().getAttributes().get("idSettingApp");
        settingApp = masterSettingAppFacade.find(id_settingApp);
    }

    public void handleSave(ActionEvent event){
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean isValid = false;

        try {
            if (settingApp.getId().equals("ebtos.finance.is_create_two_handling_invoice")) {
                Boolean changed = masterSettingAppFacade.changeHandlingInvoiceSetting(settingApp.getValueBoolean());
                
                if (changed) {
                    navigation.resetMenus();
                }
            } else {
                masterSettingAppFacade.edit(settingApp);
            }

            settingApps = masterSettingAppFacade.findAllSorted();
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            isValid = true;
        } catch (Exception re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when call handleSave", re);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
        }

        requestContext.addCallbackParam("isValid", isValid);
    }

    public List<MasterSettingApp> getSettingApps() {
        return settingApps;
    }

    public MasterSettingApp getSettingApp() {
        return settingApp;
    }
    
    public void setSettingApp(MasterSettingApp settingApp) {
        this.settingApp = settingApp;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }
}
