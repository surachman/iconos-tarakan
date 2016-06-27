/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterProfileDetailFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterProfileDetail;
import com.pelindo.ebtos.model.db.master.MasterVessel;
import com.pelindo.ebtos.model.db.master.MasterVesselProfile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "masterVesselProfileBean")
@ViewScoped
public class MasterVesselProfileBean implements Serializable {

    protected MasterVesselFacadeRemote masterVesselFacadeRemote = lookupMasterVesselFacadeRemote();
    protected MasterVesselProfileFacadeRemote masterVesselProfileFacadeRemote = lookupMasterVesselProfileFacadeRemote();
    protected MasterProfileDetailFacadeRemote masterProfileDetailFacadeRemote = lookupMasterProfileDetailFacadeRemote();

    private List<Object[]> masterVessels;
    private Object[] masterVessel;
    private List<Object[]> masterVesselProfiles;
    private MasterVesselProfile masterVesselProfile;
    private MasterVesselProfile masterVesselProfileUp;
    private MasterVesselProfile masterVesselProfileBottom;
    private MasterProfileDetail masterProfileDetail;
    private MasterProfileDetail masterProfileDetailUp;
    private MasterProfileDetail masterProfileDetailBottom;
    private List<Object[]> profileDetails;
    private List<Object[]> profileDetailsUp;
    private List<Object[]> profileDetailsBottom;

    protected MasterVessel vessel;
    protected String vesselCode;
    protected boolean deleteAction;
    private List<SelectItem> bays;

    private Integer selectedBayIndex;
    private Integer globalStartTierUp;
    private Integer globalTotalTierUp;
    private Integer globalStartTierBottom;
    private Integer globalTotalTierBottom;
    private boolean disableEdit;
    private boolean visibleForm;

    /** Creates a new instance of MasterVesselProfileBean */
    public MasterVesselProfileBean() {
        masterVessels = masterVesselFacadeRemote.findMasterVesselsForChoice();
        masterVesselProfile = new MasterVesselProfile();
        masterVesselProfile.setMasterVessel(new MasterVessel());
        masterProfileDetail = new MasterProfileDetail();
        bays = new ArrayList();

        masterVesselProfileUp = new MasterVesselProfile();
        masterVesselProfileBottom = new MasterVesselProfile();
        masterProfileDetailUp = new MasterProfileDetail();
        masterProfileDetailBottom = new MasterProfileDetail();
        globalStartTierUp = 82;
        globalStartTierBottom = 2;
        disableEdit = true;
        visibleForm = false;

    }

    private MasterVesselFacadeRemote lookupMasterVesselFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterVesselFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterVesselFacade!com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterVesselProfileFacadeRemote lookupMasterVesselProfileFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterVesselProfileFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterVesselProfileFacade!com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterProfileDetailFacadeRemote lookupMasterProfileDetailFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterProfileDetailFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterProfileDetailFacade!com.pelindo.ebtos.ejb.facade.remote.MasterProfileDetailFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * @return the masterVessels
     */
    public List<Object[]> getMasterVessels() {
        return masterVessels;
    }

    /**
     * @return the masterVessel
     */
    public Object[] getMasterVessel() {
        return masterVessel;
    }

    /**
     * @return the masterVesselProfiles
     */
    public List<Object[]> getMasterVesselProfiles() {
        return masterVesselProfiles;
    }

    /**
     * @return the masterVesselProfile
     */
    public MasterVesselProfile getMasterVesselProfile() {
        return masterVesselProfile;
    }

    /**
     * @param masterVesselProfile the masterVesselProfile to set
     */
    public void setMasterVesselProfile(MasterVesselProfile masterVesselProfile) {
        this.masterVesselProfile = masterVesselProfile;
    }

    /**
     * @return the masterProfileDetail
     */
    public MasterProfileDetail getMasterProfileDetail() {
        return masterProfileDetail;
    }

    /**
     * @param masterProfileDetail the masterProfileDetail to set
     */
    public void setMasterProfileDetail(MasterProfileDetail masterProfileDetail) {
        this.masterProfileDetail = masterProfileDetail;
    }

    public void handleSelectVesselCode(ActionEvent event) {
        vesselCode = (String) event.getComponent().getAttributes().get("vesselCode");
        masterVessel = masterVesselFacadeRemote.findMasterVesselDetail(vesselCode);
        masterVesselProfiles = masterVesselProfileFacadeRemote.findDistinctAllByVesselCode(vesselCode);
        vessel = masterVesselFacadeRemote.find(vesselCode);
    }

    public void handlePreviewDetails(ActionEvent event) {
        int profileCode = ((Integer) event.getComponent().getAttributes().get("profileCode")).intValue();
        profileDetails = masterProfileDetailFacadeRemote.findAllByProfileCode(profileCode);
    }

    public void addBay() {
        //cek don't exist bay
        Integer bay = 1;
        List<Integer> listBay = masterVesselProfileFacadeRemote.findBaysByVessel(vesselCode);
        if (!listBay.isEmpty()) {
            for (int i = 0; i < listBay.size(); i++) {
                if (bay.equals(listBay.get(i))) {
                    bay += 2;
                } else {
                    break;
                }
            }
        }
        masterVesselProfile = new MasterVesselProfile();
        masterVesselProfile.setMasterVessel(vessel);
        masterVesselProfile.setBayNo(Short.parseShort(bay.toString()));
        masterVesselProfile.setPosition("up");
        masterVesselProfileFacadeRemote.create(masterVesselProfile);
        masterVesselProfile.setPosition("bottom");
        masterVesselProfileFacadeRemote.create(masterVesselProfile);
        List<Object[]> allId = masterVesselProfileFacadeRemote.findAllId(vesselCode, bay);
        masterVesselProfileUp = masterVesselProfileFacadeRemote.find(((Integer) allId.get(0)[2]).intValue());
        masterVesselProfileBottom = masterVesselProfileFacadeRemote.find(((Integer) allId.get(0)[3]).intValue());
        profileDetailsUp = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileUp.getProfileCode());
        profileDetailsBottom = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileBottom.getProfileCode());
        globalTotalTierUp = null;
        globalTotalTierBottom = null;
        visibleForm = true;
    }

    public void generateUp() {
        Integer entryRow = masterVesselProfileUp.getStartRow();
        for (Integer i = 0; i < masterVesselProfileUp.getRowNumber(); i++) {
            masterProfileDetailUp = new MasterProfileDetail();
            masterProfileDetailUp.setRowName(entryRow);
            masterProfileDetailUp.setStartTier(globalStartTierUp);
            masterProfileDetailUp.setTierNumber(globalTotalTierUp);
            masterProfileDetailUp.setMasterVesselProfile(masterVesselProfileUp);
            masterProfileDetailFacadeRemote.create(masterProfileDetailUp);
            entryRow += 1;
        }
        masterVesselProfileFacadeRemote.edit(masterVesselProfileUp);
        profileDetailsUp = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileUp.getProfileCode());
    }

    public void clearUp() {
        globalTotalTierUp = null;
        masterVesselProfileUp.setStartRow(null);
        masterVesselProfileUp.setRowNumber(null);
        masterVesselProfileFacadeRemote.edit(masterVesselProfileUp);
        masterProfileDetailFacadeRemote.deleteByProfileCode(masterVesselProfileUp.getProfileCode());
        profileDetailsUp = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileUp.getProfileCode());
    }

    public void generateBottom() {
        Integer entryRow = masterVesselProfileBottom.getStartRow();
        for (Integer i = 0; i < masterVesselProfileBottom.getRowNumber(); i++) {
            masterProfileDetailBottom = new MasterProfileDetail();
            masterProfileDetailBottom.setRowName(entryRow);
            masterProfileDetailBottom.setStartTier(globalStartTierBottom);
            masterProfileDetailBottom.setTierNumber(globalTotalTierBottom);
            masterProfileDetailBottom.setMasterVesselProfile(masterVesselProfileBottom);
            masterProfileDetailFacadeRemote.create(masterProfileDetailBottom);
            entryRow += 1;
        }
        masterVesselProfileFacadeRemote.edit(masterVesselProfileBottom);
        profileDetailsBottom = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileBottom.getProfileCode());
    }

    public void clearBottom() {
        globalTotalTierBottom = null;
        masterVesselProfileBottom.setStartRow(null);
        masterVesselProfileBottom.setRowNumber(null);
        masterVesselProfileFacadeRemote.edit(masterVesselProfileBottom);
        masterProfileDetailFacadeRemote.deleteByProfileCode(masterVesselProfileBottom.getProfileCode());
        profileDetailsBottom = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileBottom.getProfileCode());
    }

    public void editDetail(ActionEvent event) {
        int idDetail = ((Integer) event.getComponent().getAttributes().get("idDetail")).intValue();
        masterProfileDetail = masterProfileDetailFacadeRemote.find(idDetail);
        disableEdit = false;
    }

    public void saveDetail() {
        masterProfileDetailFacadeRemote.edit(masterProfileDetail);
        profileDetailsUp = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileUp.getProfileCode());
        profileDetailsBottom = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileBottom.getProfileCode());
        masterProfileDetail = new MasterProfileDetail();
        disableEdit = true;
    }

    public void handleEditButton(ActionEvent event) {
        Integer bayNo = ((Integer) event.getComponent().getAttributes().get("bayNo")).intValue();
        List<Object[]> allId = masterVesselProfileFacadeRemote.findAllId(vesselCode, bayNo);
        masterVesselProfileUp = masterVesselProfileFacadeRemote.find(((Integer) allId.get(0)[2]).intValue());
        masterVesselProfileBottom = masterVesselProfileFacadeRemote.find(((Integer) allId.get(0)[3]).intValue());
        profileDetailsUp = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileUp.getProfileCode());
        profileDetailsBottom = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileBottom.getProfileCode());
        visibleForm = true;
    }

    public void handleDeleteButton(ActionEvent event) {
        Integer bayNo = ((Integer) event.getComponent().getAttributes().get("bayNo")).intValue();
        List<Object[]> allId = masterVesselProfileFacadeRemote.findAllId(vesselCode, bayNo);
        masterVesselProfileUp = masterVesselProfileFacadeRemote.find(((Integer) allId.get(0)[2]).intValue());
        masterVesselProfileBottom = masterVesselProfileFacadeRemote.find(((Integer) allId.get(0)[3]).intValue());
        profileDetailsUp = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileUp.getProfileCode());
        profileDetailsBottom = masterProfileDetailFacadeRemote.findAllByProfileCode(masterVesselProfileBottom.getProfileCode());
        deleteAction = true;
    }

    public void delete() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (deleteAction) {
            masterProfileDetailFacadeRemote.deleteByProfileCode(masterVesselProfileUp.getProfileCode());
            masterProfileDetailFacadeRemote.deleteByProfileCode(masterVesselProfileBottom.getProfileCode());
            masterVesselProfileFacadeRemote.remove(masterVesselProfileUp);
            masterVesselProfileFacadeRemote.remove(masterVesselProfileBottom);
            context.addMessage(null, new FacesMessage("Succesfull, delete bay " + masterVesselProfileUp.getBayNo()));
            context.addMessage(null, new FacesMessage("delete progess is completed"));
        } else {
            List<Integer> profileCodes = masterVesselProfileFacadeRemote.findAllByIdVessel(vesselCode);
            for (Integer i = 0; i < profileCodes.size(); i++) {                
                int idDelete =  profileCodes.get(i).intValue();
                masterProfileDetailFacadeRemote.deleteByProfileCode(idDelete);
            }
            masterVesselProfileFacadeRemote.deleteByIdVessel(vesselCode);
            context.addMessage(null, new FacesMessage("Succesfull, delete all File"));
            context.addMessage(null, new FacesMessage("delete progess is completed"));
        }
        masterVesselProfiles = masterVesselProfileFacadeRemote.findDistinctAllByVesselCode(vesselCode);
    }

    public void handleDeleteAll() {
        deleteAction = false;
    }

    public void handleButtonClose() {
        masterVesselProfiles = masterVesselProfileFacadeRemote.findDistinctAllByVesselCode(vesselCode);
        visibleForm = false;
    }

    public String getImageByBay() {
        if (vesselCode == null || bays == null) {
            return "#";
        }
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../bayPlan.png?"
                + "w=850&"
                + "h=400&"
                + "bay=" + masterVesselProfileUp.getBayNo() + "&"
                + "vessel=" + vesselCode;
    }

    public String getVesselProfileUrl() {
        if (vesselCode == null || bays == null) {
            return "#";
        }
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../bayPlan.png?"
                + "w=850&"
                + "h=400&"
                + "bay=" + bays.get(selectedBayIndex).getLabel() + "&"
                + "vessel=" + vesselCode;
    }

    public void handleClickNextButton(ActionEvent event) {
        if (!(selectedBayIndex == Integer.parseInt(bays.get(bays.size() - 1).getValue().toString()))) {
            selectedBayIndex = new Integer(selectedBayIndex + 1);
        }
    }

    public void handleClickPrevButton(ActionEvent event) {
        if (!(selectedBayIndex == Integer.parseInt(bays.get(0).getValue().toString()))) {
            selectedBayIndex = new Integer(selectedBayIndex - 1);
        }
    }

    public Integer getSelectedBay() {
        return selectedBayIndex;
    }

    public void setSelectedBay(Integer selectedBay) {
        this.selectedBayIndex = selectedBay;
    }

    public List<SelectItem> getBays() {
        return bays;
    }

    public void handleResetBayPlanPreview() {
        List<Integer> bay = masterVesselProfileFacadeRemote.findBaysByVessel(vesselCode);
        for (int i = 0; i < bay.size(); i++) {
            bays.add(new SelectItem(new Integer(i), String.valueOf(bay.get(i))));
        }
        selectedBayIndex = (Integer) bays.get(0).getValue();
    }

    /**
     * @return the masterVesselProfileUp
     */
    public MasterVesselProfile getMasterVesselProfileUp() {
        return masterVesselProfileUp;
    }

    /**
     * @param masterVesselProfileUp the masterVesselProfileUp to set
     */
    public void setMasterVesselProfileUp(MasterVesselProfile masterVesselProfileUp) {
        this.masterVesselProfileUp = masterVesselProfileUp;
    }

    /**
     * @return the masterVesselProfileBottom
     */
    public MasterVesselProfile getMasterVesselProfileBottom() {
        return masterVesselProfileBottom;
    }

    /**
     * @param masterVesselProfileBottom the masterVesselProfileBottom to set
     */
    public void setMasterVesselProfileBottom(MasterVesselProfile masterVesselProfileBottom) {
        this.masterVesselProfileBottom = masterVesselProfileBottom;
    }

    /**
     * @return the masterProfileDetailUp
     */
    public MasterProfileDetail getMasterProfileDetailUp() {
        return masterProfileDetailUp;
    }

    /**
     * @param masterProfileDetailUp the masterProfileDetailUp to set
     */
    public void setMasterProfileDetailUp(MasterProfileDetail masterProfileDetailUp) {
        this.masterProfileDetailUp = masterProfileDetailUp;
    }

    /**
     * @return the masterProfileDetailBottom
     */
    public MasterProfileDetail getMasterProfileDetailBottom() {
        return masterProfileDetailBottom;
    }

    /**
     * @param masterProfileDetailBottom the masterProfileDetailBottom to set
     */
    public void setMasterProfileDetailBottom(MasterProfileDetail masterProfileDetailBottom) {
        this.masterProfileDetailBottom = masterProfileDetailBottom;
    }

    /**
     * @return the globalStartTierUp
     */
    public Integer getGlobalStartTierUp() {
        return globalStartTierUp;
    }

    /**
     * @param globalStartTierUp the globalStartTierUp to set
     */
    public void setGlobalStartTierUp(Integer globalStartTierUp) {
        this.globalStartTierUp = globalStartTierUp;
    }

    /**
     * @return the globarTotalTierUp
     */
    public Integer getGlobalTotalTierUp() {
        return globalTotalTierUp;
    }

    /**
     * @param globalTotalTierUp the globarTotalTierUp to set
     */
    public void setGlobalTotalTierUp(Integer globalTotalTierUp) {
        this.globalTotalTierUp = globalTotalTierUp;
    }

    /**
     * @return the globalStartTierBottom
     */
    public Integer getGlobalStartTierBottom() {
        return globalStartTierBottom;
    }

    /**
     * @param globalStartTierBottom the globalStartTierBottom to set
     */
    public void setGlobalStartTierBottom(Integer globalStartTierBottom) {
        this.globalStartTierBottom = globalStartTierBottom;
    }

    /**
     * @return the globarTotalTierBottom
     */
    public Integer getGlobalTotalTierBottom() {
        return globalTotalTierBottom;
    }

    /**
     * @param globarTotalTierBottom the globarTotalTierBottom to set
     */
    public void setGlobalTotalTierBottom(Integer globalTotalTierBottom) {
        this.globalTotalTierBottom = globalTotalTierBottom;
    }

    /**
     * @return the profileDetailsUp
     */
    public List<Object[]> getProfileDetailsUp() {
        return profileDetailsUp;
    }

    /**
     * @return the profileDetailsBottom
     */
    public List<Object[]> getProfileDetailsBottom() {
        return profileDetailsBottom;
    }

    /**
     * @return the profileDetails
     */
    public List<Object[]> getProfileDetails() {
        return profileDetails;
    }

    /**
     * @return the disableEdit
     */
    public boolean getDisableEdit() {
        return disableEdit;
    }

    /**
     * @return the visibleForm
     */
    public boolean isVisibleForm() {
        return visibleForm;
    }
}
