/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.util.GrossConverter;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.TempContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReplacementContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.ReplacementContDischarge;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.TempContDischarge;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "replacementContainerBean")
@ViewScoped
public class ReplacementContainerBean implements Serializable {
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private ReplacementContDischargeFacadeRemote replacementContDischargeFacadeRemote;
    @EJB
    private TempContDischargeFacadeRemote tempContDischargeFacadeRemote;
    @EJB
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;

    private List<Object[]> serviceVessels;
    private ServiceVessel serviceVessel;
    private Object[] vessel;
    private String noPPKB;
    private List<Object[]> serviceContDischarges;
    private List<Object[]> tempContDischarges;
    private List<Object[]> replacementContDischarges;
    private ServiceContDischarge serviceContDischarge;
    private TempContDischarge tempContDischarge;
    private ReplacementContDischarge replacementContDischarge;
    private Object[] originalData;
    private Object[] entryData;
    private Object[][] selectedReplacements;
    private boolean isDelete;
    private Equipment equipment;
    private MasterYardCoordinat masterYardCoordinat;
    private List<Object[]> masterCommodities;
    private List<Object[]> masterContainerTypes;

    /** Creates a new instance of ReplacementContainerBean */
    public ReplacementContainerBean() {
    }

    @PostConstruct
    private void construct() {
        serviceContDischarge = new ServiceContDischarge();
        serviceContDischarge.setMasterActivity(new MasterActivity());
        serviceContDischarge.setMasterCommodity(new MasterCommodity());
        serviceContDischarge.setMasterContainerType(new MasterContainerType());
        serviceContDischarge.setMasterYard(new MasterYard());
        serviceContDischarge.setServiceVessel(new ServiceVessel());
        tempContDischarge = new TempContDischarge();
        equipment = new Equipment();
        masterYardCoordinat = new MasterYardCoordinat();
        masterContainerTypes = masterContainerTypeFacadeRemote.findAllNative();
        masterCommodities = masterCommodityFacadeRemote.findAllNative();
    }

    public void handleShowServiceVessels(ActionEvent event){
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselsServicing();
    }

    /**
     * @return the noPPKB
     */
    public String getNoPPKB() {
        return noPPKB;
    }

    /**
     * @param noPPKB to set
     */
    public void SetNoPPKB(String noPPKB) {
        this.noPPKB = noPPKB;
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @return the vessel
     */
    public Object[] getVessel() {
        return vessel;
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceContDischarges() {
        return serviceContDischarges;
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getTempContDischarges() {
        return tempContDischarges;
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getReplacementContDischarges() {
        return replacementContDischarges;
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        vessel = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB);
        serviceVessel = serviceVesselFacadeRemote.find(noPPKB);
        tempContDischarges = tempContDischargeFacadeRemote.findTempContDischargeByStatus(noPPKB, "UNREPLACED");
        serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargeUnplacement(noPPKB);
        replacementContDischarges = replacementContDischargeFacadeRemote.findReplacementContDischargeByPPKB(noPPKB);
    }

    public void newContainer(ActionEvent event) {
        int idContainer = (Integer) event.getComponent().getAttributes().get("idContainer");
        tempContDischarge = tempContDischargeFacadeRemote.find(idContainer);
        replacementContDischarge = new ReplacementContDischarge();
        replacementContDischarge.setBlNo(null);
        replacementContDischarge.setBlock(tempContDischarge.getBlock());
        replacementContDischarge.setCancelLoading(tempContDischarge.getCancelLoading());
        replacementContDischarge.setCommodityCode("000");
        replacementContDischarge.setContGross(0);
        replacementContDischarge.setContNo(tempContDischarge.getContNo());
        replacementContDischarge.setMlo(tempContDischarge.getMlo());
        replacementContDischarge.setContSize(tempContDischarge.getContSize());
        replacementContDischarge.setContStatus(tempContDischarge.getContStatus());
        replacementContDischarge.setContType(3);
        replacementContDischarge.setCrane(tempContDischarge.getCrane());
        replacementContDischarge.setDangerous("FALSE");
        replacementContDischarge.setDgLabel("FALSE");
        replacementContDischarge.setExStripping(tempContDischarge.getExStripping());
        replacementContDischarge.setGrossClass(null);
        replacementContDischarge.setIsBehandle("FALSE");
        replacementContDischarge.setIsCfs("FALSE");
        replacementContDischarge.setIsDelivery("FALSE");
        replacementContDischarge.setIsInspection("FALSE");
        replacementContDischarge.setNoPpkb(noPPKB);
        replacementContDischarge.setOldContNo(null);
        replacementContDischarge.setOriginalContNo(tempContDischarge.getContNo());
        replacementContDischarge.setOverSize("FALSE");
        replacementContDischarge.setPosition("03");
        replacementContDischarge.setSealNo(tempContDischarge.getSealNo());
        replacementContDischarge.setStartPlacementDate(tempContDischarge.getStartPlacementDate());
        replacementContDischarge.setVBay(tempContDischarge.getVBay());
        replacementContDischarge.setVRow(tempContDischarge.getVRow());
        replacementContDischarge.setVTier(tempContDischarge.getVTier());
        replacementContDischarge.setYSlot(tempContDischarge.getYSlot());
        replacementContDischarge.setYRow(tempContDischarge.getYRow());
        replacementContDischarge.setYTier(tempContDischarge.getYTier());
        //replacementContDischarge.setIdService(0);
        replacementContDischarge.setIdTemp(tempContDischarge.getId());
        replacementContDischarge.setIsReplacement("FALSE");
        replacementContDischarge.setIsNew("TRUE");
        //update database
        replacementContDischargeFacadeRemote.create(replacementContDischarge);
//            serviceContDischarge.setPosition("RC");
//            serviceContDischargeFacadeRemote.edit(serviceContDischarge);
        tempContDischarge.setStatusReplacement("ASSIGN");
        tempContDischargeFacadeRemote.edit(tempContDischarge);
        originalData = null;
        entryData = null;
        //update data
        tempContDischarges = tempContDischargeFacadeRemote.findTempContDischargeByStatus(noPPKB, "UNREPLACED");
        serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargeUnplacement(noPPKB);
        replacementContDischarges = replacementContDischargeFacadeRemote.findReplacementContDischargeByPPKB(noPPKB);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
    }

    public void handleReplaceButton() {
        if (originalData == null || entryData == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.transaction.failed");
        } else {
            serviceContDischarge = serviceContDischargeFacadeRemote.find(originalData[6]);
            tempContDischarge = tempContDischargeFacadeRemote.find(entryData[6]);
            //building data replacementContDischarge
            replacementContDischarge = new ReplacementContDischarge();
            //replacementContDischarge.setActivityCode(serviceContDischarge.getMasterActivity().getActivityCode());
            replacementContDischarge.setBlNo(serviceContDischarge.getBlNo());
            replacementContDischarge.setBlock(tempContDischarge.getBlock());
            replacementContDischarge.setCancelLoading(tempContDischarge.getCancelLoading());
            replacementContDischarge.setCommodityCode(serviceContDischarge.getMasterCommodity().getCommodityCode());
            replacementContDischarge.setContGross(serviceContDischarge.getContGross());
            replacementContDischarge.setContNo(tempContDischarge.getContNo());
            replacementContDischarge.setMlo(tempContDischarge.getMlo());
            replacementContDischarge.setContSize(tempContDischarge.getContSize());
            replacementContDischarge.setContStatus(tempContDischarge.getContStatus());
            replacementContDischarge.setContType(serviceContDischarge.getMasterContainerType().getContType());
            replacementContDischarge.setCrane(tempContDischarge.getCrane());
            replacementContDischarge.setDangerous(serviceContDischarge.getDangerous());
            replacementContDischarge.setDgLabel(serviceContDischarge.getDgLabel());
            replacementContDischarge.setExStripping(tempContDischarge.getExStripping());
            replacementContDischarge.setGrossClass(serviceContDischarge.getGrossClass());
            replacementContDischarge.setIsBehandle("FALSE");
            replacementContDischarge.setIsCfs("FALSE");
            replacementContDischarge.setIsDelivery("FALSE");
            replacementContDischarge.setIsInspection("FALSE");
            replacementContDischarge.setNoPpkb(noPPKB);
            replacementContDischarge.setOldContNo(serviceContDischarge.getContNo());
            replacementContDischarge.setOriginalContNo(tempContDischarge.getContNo());
            replacementContDischarge.setOverSize(serviceContDischarge.getOverSize());
            replacementContDischarge.setPosition("03");
            replacementContDischarge.setSealNo(tempContDischarge.getSealNo());
            replacementContDischarge.setStartPlacementDate(tempContDischarge.getStartPlacementDate());
            replacementContDischarge.setVBay(tempContDischarge.getVBay());
            replacementContDischarge.setVRow(tempContDischarge.getVRow());
            replacementContDischarge.setVTier(tempContDischarge.getVTier());
            replacementContDischarge.setYSlot(tempContDischarge.getYSlot());
            replacementContDischarge.setYRow(tempContDischarge.getYRow());
            replacementContDischarge.setYTier(tempContDischarge.getYTier());
            replacementContDischarge.setIdService(serviceContDischarge.getId());
            replacementContDischarge.setIdTemp(tempContDischarge.getId());
            replacementContDischarge.setIsReplacement("FALSE");
            replacementContDischarge.setIsNew("FALSE");
            //update database
            replacementContDischargeFacadeRemote.create(replacementContDischarge);
            serviceContDischarge.setPosition("RC");
            serviceContDischargeFacadeRemote.edit(serviceContDischarge);
            tempContDischarge.setStatusReplacement("ASSIGN");
            tempContDischargeFacadeRemote.edit(tempContDischarge);
            originalData = null;
            entryData = null;
            //update data
            tempContDischarges = tempContDischargeFacadeRemote.findTempContDischargeByStatus(noPPKB, "UNREPLACED");
            serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargeUnplacement(noPPKB);
            replacementContDischarges = replacementContDischargeFacadeRemote.findReplacementContDischargeByPPKB(noPPKB);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
        }
    }

    public void handleEditDeleteButton(ActionEvent event) {
        Integer idCont = (Integer) event.getComponent().getAttributes().get("idCont");
        replacementContDischarge = replacementContDischargeFacadeRemote.find(idCont);
        isDelete = true;
    }

    public void handleDeleteAll() {
        isDelete = false;
    }

    public void delete() {
        if (isDelete) {
            if (replacementContDischarge.getIsNew().equals("TRUE")) {
                serviceContDischarge = serviceContDischargeFacadeRemote.find(replacementContDischarge.getIdService());
                serviceContDischarge.setPosition("01");
                serviceContDischargeFacadeRemote.edit(serviceContDischarge);
            }
            tempContDischarge = tempContDischargeFacadeRemote.find(replacementContDischarge.getIdTemp());
            tempContDischarge.setStatusReplacement("UNREPLACED");
            tempContDischargeFacadeRemote.edit(tempContDischarge);
            replacementContDischargeFacadeRemote.remove(replacementContDischarge);
        } else {
            for (int i = 0; i < selectedReplacements.length; i++) {
                if (!((Boolean) selectedReplacements[i][16])) {
                    replacementContDischarge = replacementContDischargeFacadeRemote.find(selectedReplacements[i][15]);
                    if (replacementContDischarge.getIsNew().equals("FALSE")) {
                        serviceContDischarge = serviceContDischargeFacadeRemote.find(replacementContDischarge.getIdService());
                        serviceContDischarge.setPosition("01");
                        serviceContDischargeFacadeRemote.edit(serviceContDischarge);
                    }
                    tempContDischarge = tempContDischargeFacadeRemote.find(replacementContDischarge.getIdTemp());
                    tempContDischarge.setStatusReplacement("UNREPLACED");
                    tempContDischargeFacadeRemote.edit(tempContDischarge);
                    replacementContDischargeFacadeRemote.remove(replacementContDischarge);
                }
            }
        }
        //update data
        tempContDischarges = tempContDischargeFacadeRemote.findTempContDischargeByStatus(noPPKB, "UNREPLACED");
        serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargeUnplacement(noPPKB);
        replacementContDischarges = replacementContDischargeFacadeRemote.findReplacementContDischargeByPPKB(noPPKB);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.succeeded");
    }

    public void saveEdit() {
        boolean loggedIn = false;
        if (replacementContDischarge.getContNo() == null || replacementContDischarge.getContGross() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "info", "application.transaction.failed");
        } else {
            loggedIn = true;
            replacementContDischarge.setGrossClass(GrossConverter.convert(replacementContDischarge.getContSize(), replacementContDischarge.getContGross()));
            replacementContDischargeFacadeRemote.edit(replacementContDischarge);
            //update data
            tempContDischarges = tempContDischargeFacadeRemote.findTempContDischargeByStatus(noPPKB, "UNREPLACED");
            serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargeUnplacement(noPPKB);
            replacementContDischarges = replacementContDischargeFacadeRemote.findReplacementContDischargeByPPKB(noPPKB);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void submit() {
        MasterActivity ma = new MasterActivity();
        ma = null;
        for (int i = 0; i < replacementContDischarges.size(); i++) {
            if (!((Boolean) replacementContDischarges.get(i)[16])) {
                replacementContDischarge = replacementContDischargeFacadeRemote.find(replacementContDischarges.get(i)[15]);
                //merubah service cont discharge
                serviceContDischarge = new ServiceContDischarge();
                serviceContDischarge.setMasterActivity(new MasterActivity());
                serviceContDischarge.setMasterCommodity(new MasterCommodity());
                serviceContDischarge.setMasterContainerType(new MasterContainerType());
                serviceContDischarge.setMasterYard(new MasterYard());
                serviceContDischarge.setServiceVessel(new ServiceVessel());
                if (replacementContDischarge.getIsNew().equals("FALSE")) {
                    serviceContDischarge = serviceContDischargeFacadeRemote.find(replacementContDischarge.getIdService());
                }
                serviceContDischarge.setBlNo(replacementContDischarge.getBlNo());
                serviceContDischarge.setCancelLoading(replacementContDischarge.getCancelLoading());
                serviceContDischarge.setContGross(replacementContDischarge.getContGross());
                serviceContDischarge.setContNo(replacementContDischarge.getContNo());
                serviceContDischarge.setMlo(replacementContDischarge.getMlo());
                serviceContDischarge.setContSize(replacementContDischarge.getContSize());
                serviceContDischarge.setContStatus(replacementContDischarge.getContStatus());
                serviceContDischarge.setCrane(replacementContDischarge.getCrane());
                serviceContDischarge.setDangerous(replacementContDischarge.getDangerous());
                serviceContDischarge.setDgLabel(replacementContDischarge.getDgLabel());
                serviceContDischarge.setExStripping(replacementContDischarge.getExStripping());
                serviceContDischarge.setGrossClass(replacementContDischarge.getGrossClass());
                serviceContDischarge.setIsBehandle(replacementContDischarge.getIsBehandle());
                serviceContDischarge.setIsCfs(replacementContDischarge.getIsCfs());
                serviceContDischarge.setIsDelivery(replacementContDischarge.getIsDelivery());
                serviceContDischarge.setIsInspection(replacementContDischarge.getIsInspection());
                serviceContDischarge.setMasterActivity(ma);
                serviceContDischarge.getMasterCommodity().setCommodityCode(replacementContDischarge.getCommodityCode());
                serviceContDischarge.getMasterContainerType().setContType(replacementContDischarge.getContType());
                serviceContDischarge.getMasterYard().setBlock(replacementContDischarge.getBlock());
                serviceContDischarge.setOverSize(replacementContDischarge.getOverSize());
                serviceContDischarge.setPosition(replacementContDischarge.getPosition());
                serviceContDischarge.setPositionBay(replacementContDischarge.getPositionBay());
                serviceContDischarge.setSealNo(replacementContDischarge.getSealNo());
                serviceContDischarge.setServiceVessel(serviceVessel);
                serviceContDischarge.setStartPlacementDate(replacementContDischarge.getStartPlacementDate());
                serviceContDischarge.setVBay(replacementContDischarge.getVBay());
                serviceContDischarge.setVRow(replacementContDischarge.getVRow());
                serviceContDischarge.setVTier(replacementContDischarge.getVTier());
                serviceContDischarge.setYSlot(replacementContDischarge.getYSlot());
                serviceContDischarge.setYRow(replacementContDischarge.getYRow());
                serviceContDischarge.setYTier(replacementContDischarge.getYTier());
                if (replacementContDischarge.getIsNew().equals("FALSE")) {
                    serviceContDischargeFacadeRemote.edit(serviceContDischarge);
                } else {
                    serviceContDischargeFacadeRemote.create(serviceContDischarge);
                }
                //merubah temp cont dishcarge
                tempContDischarge = tempContDischargeFacadeRemote.find(replacementContDischarge.getIdTemp());
                tempContDischargeFacadeRemote.remove(tempContDischarge);

                //merubah master Yard Coordinat
                Object[] idMYC = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(replacementContDischarge.getBlock(), replacementContDischarge.getYSlot(), replacementContDischarge.getYRow(), replacementContDischarge.getYTier());
                masterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) idMYC[0]);
                masterYardCoordinat.setBlNo(replacementContDischarge.getBlNo());
                masterYardCoordinat.setContNo(replacementContDischarge.getContNo());
                masterYardCoordinat.setMlo(replacementContDischarge.getMlo());
                masterYardCoordinat.setContSize(replacementContDischarge.getContSize());
                masterYardCoordinat.setContType(replacementContDischarge.getContType().toString());
                masterYardCoordinat.setContWeight(replacementContDischarge.getContGross());
                masterYardCoordinat.setPod(null);
                masterYardCoordinat.setNoPpkb(replacementContDischarge.getNoPpkb());
                masterYardCoordinat.setGrossClass(replacementContDischarge.getGrossClass());
                masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                //merubah Equipment
                List<Integer> idEquipments = equipmentFacadeRemote.findIdByPPKBnContNo(noPPKB, replacementContDischarge.getOriginalContNo());
                for (int j = 0; j < idEquipments.size(); j++) {
                    equipment = equipmentFacadeRemote.find(idEquipments.get(j));
                    equipment.setContNo(replacementContDischarge.getContNo());
                    equipment.setMlo(replacementContDischarge.getMlo());
                    equipment.setBlNo(replacementContDischarge.getBlNo());
                    equipmentFacadeRemote.edit(equipment);
                }
                //save Replacement
                replacementContDischarge.setIsReplacement("TRUE");
                replacementContDischargeFacadeRemote.edit(replacementContDischarge);
            }
        }
        //update data
        tempContDischarges = tempContDischargeFacadeRemote.findTempContDischargeByStatus(noPPKB, "UNREPLACED");
        serviceContDischarges = serviceContDischargeFacadeRemote.findServiceContDischargeUnplacement(noPPKB);
        replacementContDischarges = replacementContDischargeFacadeRemote.findReplacementContDischargeByPPKB(noPPKB);

        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
    }

    /**
     * @return the originalData
     */
    public Object[] getOriginalData() {
        return originalData;


    }

    /**
     * @param originalData the originalData to set
     */
    public void setOriginalData(Object[] originalData) {
        this.originalData = originalData;


    }

    /**
     * @return the entryData
     */
    public Object[] getEntryData() {
        return entryData;


    }

    /**
     * @param entryData the entryData to set
     */
    public void setEntryData(Object[] entryData) {
        this.entryData = entryData;


    }

    /**
     * @return the replacementContDischarge
     */
    public ReplacementContDischarge getReplacementContDischarge() {
        return replacementContDischarge;


    }

    /**
     * @param replacementContDischarge the replacementContDischarge to set
     */
    public void setReplacementContDischarge(ReplacementContDischarge replacementContDischarge) {
        this.replacementContDischarge = replacementContDischarge;


    }

    public Object[][] getSelectedReplacements() {
        return selectedReplacements;


    }

    public void setSelectedReplacements(Object[][] selectedReplacements) {
        this.selectedReplacements = selectedReplacements;


    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        MasterContainerType mct = masterContainerTypeFacadeRemote.find(newItem);
        replacementContDischarge.setContSize(mct.getFeetMark());


    }

    public List<Object[]> getMasterContainerTypes() {
        return masterContainerTypes;


    }

    public List<Object[]> getMasterCommodities() {
        return masterCommodities;

    }
}
