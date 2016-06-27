/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.model.db.ChangeStatusService;
import com.pelindo.ebtos.model.db.Registration;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author dycode-java
 */
@Named(value="entryChangeStatusBean")
@RequestScoped
public class EntryChangeStatusBean {

    private Registration registration;
    
    private ChangeStatusService changeStatusService;
    private List<ChangeStatusService> changeStatusServices;

    /** Creates a new instance of EntryChangeStatusBean */
    public EntryChangeStatusBean() {
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
     * @return the changeStatusService
     */
    public ChangeStatusService getChangeStatusService() {
        return changeStatusService;
    }

    /**
     * @param changeStatusService the changeStatusService to set
     */
    public void setChangeStatusService(ChangeStatusService changeStatusService) {
        this.changeStatusService = changeStatusService;
    }

    /**
     * @return the changeStatusServices
     */
    public List<ChangeStatusService> getChangeStatusServices() {
        return changeStatusServices;
    }

    /**
     * @param changeStatusServices the changeStatusServices to set
     */
    public void setChangeStatusServices(List<ChangeStatusService> changeStatusServices) {
        this.changeStatusServices = changeStatusServices;
    }

}
