/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.ReceivingUc;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author dycoder
 */
@Named(value="receivingUcPreviewBean")
@RequestScoped
public class ReceivingUcPreviewBean {
  
    private ReceivingUc receivingUc;
    private PreserviceVessel preserviceVessel;
    private PlanningVessel planningVessel;
    private MasterContainerType masterContainerType;
    private ReceivingService receivingService;

    /** Creates a new instance of ReceivingUcPreviewBean */
    public ReceivingUcPreviewBean() {
        receivingUc=new ReceivingUc();
        preserviceVessel=new PreserviceVessel();
        planningVessel=new PlanningVessel();
        masterContainerType=new MasterContainerType();
    }

    /**
     * @return the receivingUc
     */
    public ReceivingUc getReceivingUc() {
        return receivingUc;
    }

    /**
     * @param receivingUc the receivingUc to set
     */
    public void setReceivingUc(ReceivingUc receivingUc) {
        this.receivingUc = receivingUc;
    }

    /**
     * @return the preserviceVessel
     */
    public PreserviceVessel getPreserviceVessel() {
        return preserviceVessel;
    }

    /**
     * @param preserviceVessel the preserviceVessel to set
     */
    public void setPreserviceVessel(PreserviceVessel preserviceVessel) {
        this.preserviceVessel = preserviceVessel;
    }

    /**
     * @return the planningVessel
     */
    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    /**
     * @param planningVessel the planningVessel to set
     */
    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    /**
     * @return the masterContainerType
     */
    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    /**
     * @param masterContainerType the masterContainerType to set
     */
    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    /**
     * @return the receivingService
     */
    public ReceivingService getReceivingService() {
        return receivingService;
    }

    /**
     * @param receivingService the receivingService to set
     */
    public void setReceivingService(ReceivingService receivingService) {
        this.receivingService = receivingService;
    }

}
