/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.CancelLoadingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceDeliveryFacadeRemote;
import com.pelindo.ebtos.model.db.CancelLoadingService;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceDelivery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "updateDeliveryBean")
@ViewScoped
public class UpdateDeliveryBean implements Serializable {

    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private ServiceDeliveryFacadeRemote serviceDeliveryFacadeRemote;
    @EJB
    private CancelLoadingServiceFacadeRemote cancelLoadingServiceFacadeRemote;
    private List<Object[]> objects;
    Object[] selectedObj;
    private String noPPKB;
    private String contNo;

    @PostConstruct
    private void construct() {
        noPPKB = null;
        contNo = null;
        objects = new ArrayList<Object[]>();
    }

    public List<String> handleFilterByNoPpkb(String noPpkb) {
        List<String> first = serviceDeliveryFacadeRemote.findLikeNoPPKB(noPpkb);
        List<String> second = cancelLoadingServiceFacadeRemote.findLikeNoPPKB(noPpkb);
        Set setboth = new HashSet(first);
        setboth.addAll(second);
        first.clear();
        first.addAll(setboth);
        return first;
    }

    public List<String> handleFilterByNoPpkbContNo(String contNo) {
        List<String> results = serviceDeliveryFacadeRemote.findLikeContNo(noPPKB, contNo);
        results.addAll(cancelLoadingServiceFacadeRemote.findLikeContNo(noPPKB, contNo));

        return results;
    }

    public void onSelectPPKB(SelectEvent event) {
        noPPKB = event.getObject().toString();
        contNo = null;
        objects.clear();
    }

    public void handleEdit(ActionEvent event) {
        selectedObj = (Object[]) event.getComponent().getAttributes().get("obj");
    }

    public void onSearch() {
        objects = serviceDeliveryFacadeRemote.findByNoPpkbAndContNoUpdateDelivery(noPPKB, contNo);
        if (objects.isEmpty()) {
            objects = cancelLoadingServiceFacadeRemote.findByNoPpkbAndContNoUpdateDelivery(noPPKB, contNo);
        }
    }

    public void handleSave(ActionEvent event) {
        boolean loggedIn = false;
        try {
            ServiceDelivery serviceDelivery = serviceDeliveryFacadeRemote.findByNoPpkbAndContNo((String) selectedObj[0], (String) selectedObj[2]);
            if (serviceDelivery != null) {
                serviceDelivery.getMasterYard().setBlock((String) selectedObj[4]);
                serviceDelivery.setYSlot(Short.valueOf(selectedObj[5].toString()));
                serviceDelivery.setYRow(Short.valueOf(selectedObj[6].toString()));
                serviceDelivery.setYTier(Short.valueOf(selectedObj[7].toString()));
                serviceDeliveryFacadeRemote.edit(serviceDelivery);

                ServiceContDischarge serviceContDischarge = serviceContDischargeFacadeRemote.findByNoPpkbAndContNo((String) selectedObj[0], (String) selectedObj[2]);
                serviceContDischarge.getMasterYard().setBlock((String) selectedObj[4]);
                serviceContDischarge.setYSlot(Short.valueOf(selectedObj[5].toString()));
                serviceContDischarge.setYRow(Short.valueOf(selectedObj[6].toString()));
                serviceContDischarge.setYTier(Short.valueOf(selectedObj[7].toString()));
                serviceContDischargeFacadeRemote.edit(serviceContDischarge);
            } else {
                CancelLoadingService cancelLoadingService = cancelLoadingServiceFacadeRemote.findByNoPpkbAndContNo((String) selectedObj[0], (String) selectedObj[2]);

                cancelLoadingService.getMasterYard().setBlock((String) selectedObj[4]);
                cancelLoadingService.setySlot(Short.valueOf(selectedObj[5].toString()));
                cancelLoadingService.setyRow(Short.valueOf(selectedObj[6].toString()));
                cancelLoadingService.setyTier(Short.valueOf(selectedObj[7].toString()));
                cancelLoadingServiceFacadeRemote.edit(cancelLoadingService);
            }
            loggedIn = true;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
        } catch (Exception ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "application.save.failed");
            ex.printStackTrace();
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public Object[] getSelectedObj() {
        return selectedObj;
    }

    public void setSelectedObj(Object[] selectedObj) {
        this.selectedObj = selectedObj;
    }

    public List<Object[]> getObjects() {
        return objects;
    }

    public void setObjects(List<Object[]> objects) {
        this.objects = objects;
    }

    public String getNoPPKB() {
        return noPPKB;
    }

    public void setNoPPKB(String noPPKB) {
        this.noPPKB = noPPKB;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }
}
