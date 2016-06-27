/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.EquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterProfileDetailFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.Equipment;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceVessel;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycoder
 */
@ManagedBean(name = "transhipmentConfirmBean")
@ViewScoped
public class TranshipmentConfirmBean implements Serializable {
    @EJB
    private ServiceVesselFacadeRemote serviceVesselFacadeRemote;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacadeRemote;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private EquipmentFacadeRemote equipmentFacadeRemote;
    @EJB
    private MasterVesselProfileFacadeRemote masterVesselProfileFacadeRemote;
    @EJB
    private MasterProfileDetailFacadeRemote masterProfileDetailFacadeRemote;
    
    private List<MasterOperator> masterOperators;
    private List<Integer> bayChoices;
    private List<Integer> rowChoices;
    private List<Integer> tierChoices;
    private List<Integer> rowUps;
    private List<Integer> rowBottoms;
    private List<Object[]> masterCranes;
    private List<Object[]> serviceContTranshipmentSelect;
    private List<Object[]> masterHTs;
    private List<Object[]> serviceVessels;
    private List<Object[]> serviceContTranshipments;
    private ServiceContTranshipment serviceContTranshipment;
    private ServiceVessel serviceVessel;
    private Equipment crane;
    private Equipment ht;
    private Object[] vesselDetail;
    private String opsi;
    private String noPPKB;
    private String vesselCode;

    public TranshipmentConfirmBean() {}

    @PostConstruct
    private void construct() {
        initial();
        bayChoices = new ArrayList<Integer>();
        rowChoices = new ArrayList<Integer>();
        tierChoices = new ArrayList<Integer>();
        rowUps = new ArrayList<Integer>();
        rowBottoms = new ArrayList<Integer>();

        masterCranes = masterEquipmentFacadeRemote.findCraneForView();
        masterHTs = masterEquipmentFacadeRemote.findHtForView();
        masterOperators = masterOperatorFacadeRemote.findAll();
    }

    public void initial() {
        //inisialisasi container
        serviceContTranshipment = new ServiceContTranshipment();
        serviceContTranshipment.setMasterCommodity(new MasterCommodity());
        serviceContTranshipment.setMasterContainerType(new MasterContainerType());
        serviceContTranshipment.setMasterYard(new MasterYard());
        serviceContTranshipment.setServiceVessel(new ServiceVessel());
        serviceContTranshipment.setCrane("L");
        //inisialisasi crane
        crane = new Equipment();
        crane.setMasterEquipment(new MasterEquipment());
        crane.setMasterOperator(new MasterOperator());
        crane.setPlanningVessel(new PlanningVessel());
        //inisialisasi ht
        ht = new Equipment();
        ht.setMasterEquipment(new MasterEquipment());
        ht.setMasterOperator(new MasterOperator());
        ht.setPlanningVessel(new PlanningVessel());
    }

    /**
     * @return the serviceVessels
     */
    public List<Object[]> getServiceVessels() {
        return serviceVessels;
    }

    /**
     * @return the vesselDetail
     */
    public Object[] getVesselDetail() {
        return vesselDetail;
    }

    public void handleShowTranshipableContainer(ActionEvent event) {
        serviceContTranshipmentSelect = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsSelect(noPPKB);
    }

    public void handleShowSelectableVessels(ActionEvent event) {
        serviceVessels = serviceVesselFacadeRemote.findServiceVesselsServicing();
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        //ngurusin data PPKB
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        serviceVessel = serviceVesselFacadeRemote.find(noPPKB);
        vesselDetail = serviceVesselFacadeRemote.findServiceVesselByPpkb(noPPKB);
        vesselCode = vesselDetail[4].toString();
        //ngurusin data Container
        serviceContTranshipments = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsConfirm(noPPKB);
        //ngurusin bay
        bayChoices.clear();
        bayChoices = masterVesselProfileFacadeRemote.findBaysByVessel(vesselCode);
    }

    //handling button tambah
    public void handleButtonAdd() {
        initial();
        //untuk mengkondisikan pemilihan bay
        serviceContTranshipment.setVBay(Short.parseShort(bayChoices.get(0).toString()));
        changeBay(serviceContTranshipment.getVBay());
        changeRow(Short.parseShort(rowChoices.get(0).toString()));
        opsi = "add";
    }

    public void handleButtonRefresh(){
        serviceContTranshipments = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsConfirm(noPPKB);
    }

    /**
     * @return the ServiceContTranshipment
     */
    public ServiceContTranshipment getServiceContTranshipment() {
        return serviceContTranshipment;
    }

    /**
     * @param crane the ServiceContTranshipment to set
     */
    public void setServiceContTranshipment(ServiceContTranshipment serviceContTranshipment) {
        this.serviceContTranshipment = serviceContTranshipment;
    }

    /**
     * @return the serviceContTranshipments
     */
    public List<Object[]> getServiceContTranshipments() {
        return serviceContTranshipments;
    }

    //handling container chooise
    /**
     * @return the serviceContTranshipmentSelect
     */
    public List<Object[]> getServiceContTranshipmentSelect() {
        return serviceContTranshipmentSelect;
    }

    public void handleSelectNoContainer(ActionEvent event) {
        int noContainer = (Integer) event.getComponent().getAttributes().get("noContainer");
        serviceContTranshipment = serviceContTranshipmentFacadeRemote.find(noContainer);
    }

    /**
     * @return the bayChoices
     */
    public List<Integer> getBayChoices() {
        return bayChoices;
    }

    /**
     * @return the rowChoices
     */
    public List<Integer> getRowChoices() {
        return rowChoices;
    }

    /**
     * @return the tierChoices
     */
    public List<Integer> getTierChoices() {
        return tierChoices;
    }

    //rubah-rubah bay
    public void onchangeBay(ValueChangeEvent event) {
        Short newBay = (Short) event.getNewValue();
        serviceContTranshipment.setVBay(newBay);
        changeBay(newBay);
    }

    public void onChangeRow(ValueChangeEvent event) {
        Short newRow = (Short) event.getNewValue();
        serviceContTranshipment.setVRow(newRow);
        changeRow(newRow);
    }

    public void changeBay(Short newBay) {
        Integer startRow = 0;
        Integer totalRow = 0;
        //nyiapin data row
        rowChoices.clear();
        rowBottoms.clear();
        rowUps.clear();
        List<Object[]> loopRowBottom = masterVesselProfileFacadeRemote.findRowByBay(vesselCode, "bottom", newBay.intValue());
        if (!loopRowBottom.isEmpty()) {
            startRow = (Integer) loopRowBottom.get(0)[0];
            totalRow = (Integer) loopRowBottom.get(0)[1];
        }
        Integer entryRow = startRow;
        if(totalRow != null)
        for (Integer i = 0; i < totalRow; i++) {
            rowChoices.add(entryRow);
            rowBottoms.add(entryRow);
            entryRow += 1;
        }
        List<Object[]> loopRowUp = masterVesselProfileFacadeRemote.findRowByBay(vesselCode, "up", newBay.intValue());
        if (!loopRowUp.isEmpty()) {
            startRow = (Integer) loopRowUp.get(0)[0];
            totalRow = (Integer) loopRowUp.get(0)[1];
        }
        entryRow = startRow;
        if(totalRow != null)
        for (Integer i = 0; i < totalRow; i++) {
            if (!rowChoices.contains(entryRow)) {
                rowChoices.add(entryRow);
            }
            rowUps.add(entryRow);
            entryRow += 1;
        }
    }

    public void changeRow(Short newRow) {
        Integer startTier = 0;
        Integer totalTier = 0;
        Integer row = newRow.intValue();
        tierChoices.clear();
        if (rowUps.contains(row) && rowBottoms.contains(row)) {
            List<Object[]> loopTierBottom = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", serviceContTranshipment.getVBay().intValue(), row);
            if (!loopTierBottom.isEmpty()) {
                startTier = (Integer) loopTierBottom.get(0)[0];
                totalTier = (Integer) loopTierBottom.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
            List<Object[]> loopTierUp = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", serviceContTranshipment.getVBay().intValue(), row);
            if (!loopTierUp.isEmpty()) {
                startTier = (Integer) loopTierUp.get(0)[0];
                totalTier = (Integer) loopTierUp.get(0)[1];
            }
            entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                if (!tierChoices.contains(entryTier)) {
                    tierChoices.add(entryTier);
                }
                entryTier += 2;
            }
        } else if (rowBottoms.contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", serviceContTranshipment.getVBay().intValue(), row);
            if (!loopTier.isEmpty()) {
                startTier = (Integer) loopTier.get(0)[0];
                totalTier = (Integer) loopTier.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
        } else if (rowUps.contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", serviceContTranshipment.getVBay().intValue(), row);
            if (!loopTier.isEmpty()) {
                startTier = (Integer) loopTier.get(0)[0];
                totalTier = (Integer) loopTier.get(0)[1];
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
        }
    }

    //handling crane
    public void onChangeCrane(ValueChangeEvent event) {
        String newCrane = (String) event.getNewValue();
        serviceContTranshipment.setCrane(newCrane);
        if (newCrane.equalsIgnoreCase("S")) {
            crane.setMasterEquipment(masterEquipmentFacadeRemote.find("EQ000"));
            crane.setMasterOperator(masterOperatorFacadeRemote.find("0"));
        }else if (newCrane.equalsIgnoreCase("L")) {
            crane.setMasterEquipment(new MasterEquipment());
            crane.setMasterOperator(new MasterOperator());
        }
    }

    /**
     * @return the masterOperators
     */
    public List<MasterOperator> getMasterOperators() {
        return masterOperators;
    }

    /**
     * @return the masterCranes
     */
    public List<Object[]> getMasterCranes() {
        return masterCranes;
    }

    /**
     * @return the masterHTs
     */
    public List<Object[]> getMasterHTs() {
        return masterHTs;
    }

    /**
     * @return the crane
     */
    public Equipment getCrane() {
        return crane;
    }

    /**
     * @return the ht
     */
    public Equipment getHt() {
        return ht;
    }

    public void handleSelectCrane(ActionEvent event) {
        String idCrane = (String) event.getComponent().getAttributes().get("idCrane");
        crane.setMasterEquipment(masterEquipmentFacadeRemote.find(idCrane));
    }

    public void handleSelectHT(ActionEvent event) {
        String idHT = (String) event.getComponent().getAttributes().get("idHT");
        ht.setMasterEquipment(masterEquipmentFacadeRemote.find(idHT));
    }

    public void handleSelectOperatorCrane(ActionEvent event) {
        String idOperatorCrane = (String) event.getComponent().getAttributes().get("idOperatorCrane");
        crane.setMasterOperator(masterOperatorFacadeRemote.find(idOperatorCrane));
    }

    public void handleSelectOperatorHT(ActionEvent event) {
        String idOperatorHT = (String) event.getComponent().getAttributes().get("idOperatorHT");
        ht.setMasterOperator(masterOperatorFacadeRemote.find(idOperatorHT));
    }

    //untuk menangani edit

    public void handleEditDeleteButton(ActionEvent event) {
        int idContainer = (Integer) event.getComponent().getAttributes().get("idCont");
        serviceContTranshipment = serviceContTranshipmentFacadeRemote.find(idContainer);
        int idCrane = equipmentFacadeRemote.findByIdContainer(noPPKB, serviceContTranshipment.getContNo(), "STEVEDORING", "TRANSDISCHARGE");
        crane = equipmentFacadeRemote.find(idCrane);
        int idHT = equipmentFacadeRemote.findByIdContainer(noPPKB, serviceContTranshipment.getContNo(), "H/T", "TRANSDISCHARGE");
        ht = equipmentFacadeRemote.find(idHT);
        //untuk mengkondisikan pemilihan bay
        changeBay(serviceContTranshipment.getVBay());
        changeRow(serviceContTranshipment.getVRow());
        opsi = "edit";
    }

    public void saveEdit(ActionEvent event) {
        boolean loggedIn = false;
        if (serviceContTranshipment.getContNo() == null || crane.getMasterEquipment() == null || crane.getMasterOperator() == null || ht.getMasterEquipment() == null || ht.getMasterOperator() == null || crane.getStartTime() == null || crane.getEndTime() == null) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_FATAL, "application.save.failed");
        } else if (crane.getStartTime().after(crane.getEndTime()) || crane.getStartTime().equals(crane.getEndTime())) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_FATAL, "application.save.failed");
        } else {
            loggedIn = true;
            serviceContTranshipment.setPosition("02");
            serviceContTranshipment.setServiceVessel(serviceVessel);
            serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);

            crane.setPlanningVessel(serviceVessel.getPlanningVessel());
            crane.setContNo(serviceContTranshipment.getContNo());
            crane.setMlo(serviceContTranshipment.getMlo());
            crane.setEquipmentActCode("STEVEDORING");
            crane.setOperation("TRANSDISCHARGE");
            equipmentFacadeRemote.edit(crane);

            ht.setPlanningVessel(serviceVessel.getPlanningVessel());
            ht.setContNo(serviceContTranshipment.getContNo());
            ht.setMlo(serviceContTranshipment.getMlo());
            ht.setEquipmentActCode("H/T");
            ht.setOperation("TRANSDISCHARGE");
            ht.setStartTime(crane.getEndTime());
            equipmentFacadeRemote.edit(ht);

            serviceContTranshipments = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsConfirm(noPPKB);
            serviceContTranshipmentSelect = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsSelect(noPPKB);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
            initial();
            //untuk mengkondisikan pemilihan bay
            serviceContTranshipment.setVBay(Short.parseShort(bayChoices.get(0).toString()));
            changeBay(serviceContTranshipment.getVBay());
            changeRow(Short.parseShort(rowChoices.get(0).toString()));
        }
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void delete(ActionEvent event) {
        serviceContTranshipment.setPosition("01");
        serviceContTranshipmentFacadeRemote.edit(serviceContTranshipment);
        equipmentFacadeRemote.remove(crane);
        equipmentFacadeRemote.remove(ht);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.succeeded");
        serviceContTranshipments = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsConfirm(noPPKB);
        serviceContTranshipmentSelect = serviceContTranshipmentFacadeRemote.findServiceContTranshipmentsSelect(noPPKB);
    }

    public String getOpsi(){
        return opsi;
    }
}
