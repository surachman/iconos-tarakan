/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.BaplieDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.UncontainerizedServiceFacadeRemote;
import com.pelindo.ebtos.model.db.BaplieDischarge;
import com.pelindo.ebtos.model.db.PlanningTransDischarge;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.UncontainerizedService;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.qtasnim.text.SqlHelper;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.ejb.EJBException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "transhipmentPlanningBean")
@ViewScoped
public class TranshipmentPlanningBean implements Serializable {
    @EJB
    private BaplieDischargeFacadeRemote baplieDischargeFacadeRemote;
    @EJB
    private PlanningTransDischargeFacadeRemote planningTransDischargeFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private UncontainerizedServiceFacadeRemote uncontainerizedServiceFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private ServiceContTranshipmentFacadeRemote serviceContTranshipmentFacadeRemote;

    private List<Object[]> planningVessels;
    private List<Object[]> loadablePlanningVessels;
    private String noPPKB;
    private PlanningVessel planningVessel;
    private List<Object[]> baplieDischarges;
    private Object[] vessel;
    private List<Object[]> planningTransDischarges;
    private Object[] newVessel;
    private PlanningVessel newPlanningVessel;
    private String newPPKB;
    private String opsi;
    private Object[][] selectedContainers;
    private Object[][] selectedTranshipments;
    private Object[][] selectedContainerUc;
    private List<Container> containers;
    private PlanningTransDischarge planningTransDischarge;
    private BaplieDischarge baplieDischarge;
    protected UncontainerizedService uncontainerizedService;
    private List<Object[]> uncontainerizedList;
    private List<Object[]> uncontainerizedAfterList;
    private List<Object[]> loadPlanningAllocationsInfo;

    /** Creates a new instance of TranshipmentPlanningBean */
    public TranshipmentPlanningBean() {
    }

    @PostConstruct
    private void construct() {
        noPPKB = null;
        newPPKB = null;
        containers = new ArrayList<Container>();
        baplieDischarge = new BaplieDischarge();
        planningTransDischarge = new PlanningTransDischarge();
        planningTransDischarge.setPlanningVessel(new PlanningVessel());
        planningTransDischarge.setPlanningVessel1(new PlanningVessel());
        planningTransDischarge.setMasterCommodity(new MasterCommodity());
        planningTransDischarge.setMasterContainerType(new MasterContainerType());
        planningTransDischarge.setMasterYard(new MasterYard());
        uncontainerizedService = new UncontainerizedService();
    }

    public void handleShowPlanningVessels(ActionEvent event) {
        planningVessels = planningVesselFacadeRemote.findPlanningVesselsByFinished();
    }

    public void handleShowLoadablePlanningVessels(ActionEvent event) {
        loadablePlanningVessels = planningVesselFacadeRemote.findPlanningVesselsHasApprovedtoLoad();
    }

    public List<Object[]> getUncontainerizedList() {
        return uncontainerizedList;
    }

    public void setUncontainerizedList(List<Object[]> uncontainerizedList) {
        this.uncontainerizedList = uncontainerizedList;
    }

    public UncontainerizedService getUncontainerizedService() {
        return uncontainerizedService;
    }

    public void setUncontainerizedService(UncontainerizedService uncontainerizedService) {
        this.uncontainerizedService = uncontainerizedService;
    }

    public List<Object[]> getUncontainerizedAfterList() {
        return uncontainerizedAfterList;
    }

    public void setUncontainerizedAfterList(List<Object[]> uncontainerizedAfterList) {
        this.uncontainerizedAfterList = uncontainerizedAfterList;
    }

    /**
     * @return opsi
     */
    public String getOpsi() {
        return opsi;
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
     * @return the noPPKB
     */
    public String getNewPPKB() {
        return newPPKB;
    }

    /**
     * @param noPPKB to set
     */
    public void SetNewPPKB(String newPPKB) {
        this.newPPKB = newPPKB;
    }

    /**
     * @return the planningVessels
     */
    public List<Object[]> getPlanningVessels() {
        return planningVessels;
    }

    /**
     * @return the newPlanningVessels
     */
    public List<Object[]> getLoadablePlanningVessels() {
        return loadablePlanningVessels;
    }

    /**
     * @return the vessel
     */
    public Object[] getVessel() {
        return vessel;
    }

    /**
     * @return the baplieDischarges
     */
    public List<Object[]> getBaplieDischarges() {
        return baplieDischarges;
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        vessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(noPPKB);
        planningVessel = planningVesselFacadeRemote.find(noPPKB);
        baplieDischarges = baplieDischargeFacadeRemote.findBaplieDischargesByPPKB(noPPKB);
        planningTransDischarges = planningTransDischargeFacadeRemote.findAllPlanningTransDischarges(noPPKB);
        uncontainerizedList = uncontainerizedServiceFacadeRemote.findUncontainerizedTranshipment(noPPKB);
        uncontainerizedAfterList = uncontainerizedServiceFacadeRemote.findUncontainerizedAfterTranshipment(noPPKB);
    }

    /**
     * @return the planningTransDischarges
     */
    public List<Object[]> getPlanningTransDischarges() {
        return planningTransDischarges;
    }

    public void handleSelectNewNoPpkbUc(ActionEvent event) {
        Integer id = (Integer) event.getComponent().getAttributes().get("idCont");
        uncontainerizedService = uncontainerizedServiceFacadeRemote.find(id);
    }

    public void handleSelectNewNoPpkb(ActionEvent event) {
        newPPKB = (String) event.getComponent().getAttributes().get("newNoPpkb");
        newVessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(newPPKB);
        newPlanningVessel = planningVesselFacadeRemote.find(newPPKB);
    }

    /**
     * @return the newVessel
     */
    public Object[] getNewVessel() {
        return newVessel;
    }

    /**
     * @return the newPlanningVessel
     */
    public PlanningVessel getNewPlanningVessel() {
        return newPlanningVessel;
    }

    public void handleEditDeleteButton(ActionEvent event) {
        int id = (Integer) event.getComponent().getAttributes().get("idCont");
        planningTransDischarge = planningTransDischargeFacadeRemote.find(id);
        opsi = "delete";
    }

    public void deleteUc(ActionEvent event) {
        try {
            uncontainerizedService.setIsTranshipment("FALSE");
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
            uncontainerizedList = uncontainerizedServiceFacadeRemote.findUncontainerizedTranshipment(noPPKB);
            uncontainerizedAfterList = uncontainerizedServiceFacadeRemote.findUncontainerizedAfterTranshipment(noPPKB);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
        } catch (EJBException e) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.failed");
        }
    }

    public void delete() {
        if (opsi.equalsIgnoreCase("delete")) {
            masterYardCoordinatFacadeRemote.revertContNoToBookingByPpkb(planningTransDischarge.getPlanningVessel().getNoPpkb(), planningTransDischarge.getContNo());
            planningTransDischargeFacadeRemote.remove(planningTransDischarge);
        } else {
            for (int i = 0; i < selectedTranshipments.length; i++) {
                planningTransDischarge = planningTransDischargeFacadeRemote.find((Integer) selectedTranshipments[i][10]);
                masterYardCoordinatFacadeRemote.revertContNoToBookingByPpkb(planningTransDischarge.getPlanningVessel().getNoPpkb(), planningTransDischarge.getContNo());
                planningTransDischargeFacadeRemote.remove(planningTransDischarge);
            }
        }
        //refresh data table
        baplieDischarges = baplieDischargeFacadeRemote.findBaplieDischargesByPPKB(noPPKB);
        planningTransDischarges = planningTransDischargeFacadeRemote.findAllPlanningTransDischarges(noPPKB);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.succeeded");
    }

    public void handleDeleteAll() {
        opsi = "deleteall";
    }

    /**
     * @return the selectedContainers
     */
    public Object[][] getSelectedContainers() {
        return selectedContainers;
    }

    /**
     * @param selectedContainers the selectedContainers to set
     */
    public void setSelectedContainers(Object[][] selectedContainers) {
        this.selectedContainers = selectedContainers;
    }

    /**
     * @return the selectedTranshipments
     */
    public Object[][] getSelectedTranshipments() {
        return selectedTranshipments;
    }

    /**
     * @param selectedContainers the selectedTranshipments to set
     */
    public void setSelectedTranshipments(Object[][] selectedTranshipments) {
        this.selectedTranshipments = selectedTranshipments;
    }

    public void submit() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        String contListAsString = "";
        int notAllocated40ft = 0;
        int saved = 0;

        try {
            if (selectedContainers.length == 0) {
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "application.validation.selection_is_empty");
                requestContext.addCallbackParam("isConstraintNotEnough", false);
                return;
            } else {
                contListAsString = SqlHelper.translateArrayAsSqlString(selectedContainers, 1);
                loadPlanningAllocationsInfo = masterYardCoordinatFacadeRemote.findLoadPlanningAllocations(planningVessel.getNoPpkb(), newPlanningVessel.getNoPpkb(), contListAsString);

                for (Object[] constraint: loadPlanningAllocationsInfo) {
                    Integer required = (Integer) constraint[10];
                    Integer allocated = (Integer) constraint[11];

                    if (allocated < required) {
                        requestContext.addCallbackParam("isConstraintNotEnough", true);
                        return;
                    }
                }
            }

            for (Object[] constraint: loadPlanningAllocationsInfo) {
                String portCode = (String) constraint[0];
                List<Object[]> transhipmentContainers = baplieDischargeFacadeRemote.findTranshipmentContainerByAllocationConstraint(planningVessel.getNoPpkb(), portCode, (Integer) constraint[5], (Integer) constraint[1], (String) constraint[6], (String) constraint[3], (Integer) constraint[7], (Integer) constraint[8], (Integer) constraint[9], contListAsString);
                List<MasterYardCoordinat> plannedCoordinates = masterYardCoordinatFacadeRemote.findAvailableCoordinateByAllocationConstraint(newPlanningVessel.getNoPpkb(), portCode, (Integer) constraint[5], (Integer) constraint[1], (String) constraint[6], (String) constraint[3], (Integer) constraint[7], (Integer) constraint[8], (Integer) constraint[9]);
                Integer coordinateIndex = 0;

                for (Object[] container: transhipmentContainers) {
                    MasterCustomer mlo = masterCustomerFacadeRemote.find((String) container[3]);
                    MasterContainerType masterContainerType = masterContainerTypeFacadeRemote.find((Integer) container[2]);
                    MasterCommodity masterCommodity = masterCommodityFacadeRemote.find((String) container[4]);
                    MasterPort portOfDelivery = masterPortFacadeRemote.find(container[21]);

                    planningTransDischarge = new PlanningTransDischarge();
                    planningTransDischarge.setId((Integer) container[0]);
                    planningTransDischarge.setBlNo((String) container[1]);
                    planningTransDischarge.setMasterContainerType(masterContainerType);
                    planningTransDischarge.setMlo(mlo);
                    planningTransDischarge.setMasterCommodity(masterCommodity);
                    planningTransDischarge.setPlanningVessel(newPlanningVessel);
                    planningTransDischarge.setContNo((String) container[6]);
                    planningTransDischarge.setContSize(((Integer) container[7]).shortValue());
                    planningTransDischarge.setContStatus((String) container[8]);
                    planningTransDischarge.setContGross((Integer) container[9]);
                    planningTransDischarge.setSealNo((String) container[10]);
//                    planningTransDischarge.setDg((Boolean) container[11]);
//                    planningTransDischarge.setDgLabel((Boolean) container[12]);
//                    planningTransDischarge.setOverSize((Boolean) container[13]);
//                    planningTransDischarge.setIsExportImport((Boolean) container[22]);
//                    planningTransDischarge.setTwentyOneFeet((Boolean) container[23]);
                    planningTransDischarge.setDg((String) container[11]);
                    planningTransDischarge.setDgLabel((String) container[12]);
                    planningTransDischarge.setOverSize((String) container[13]);
                    planningTransDischarge.setIsExportImport((String) container[22]);
                    planningTransDischarge.setTwentyOneFeet((String) container[23]);

                    planningTransDischarge.setTradeType((String) container[14]);
                    planningTransDischarge.setLoadPort((String) container[15]);
                    planningTransDischarge.setDischPort((String) container[16]);
                    planningTransDischarge.setVBay(((Integer) container[17]).shortValue());
                    planningTransDischarge.setVRow(((Integer) container[18]).shortValue());
                    planningTransDischarge.setVTier(((Integer) container[19]).shortValue());
                    planningTransDischarge.setPlanningVessel1(planningVessel);
                    planningTransDischarge.setGrossClass((String) container[20]);
                    planningTransDischarge.setPortOfDelivery(portOfDelivery);

                    MasterYardCoordinat coordinate = null;

                    if (planningTransDischarge.getContSize() == 20) {
                        coordinate = plannedCoordinates.get(coordinateIndex);

                        coordinate.setContNo(planningTransDischarge.getContNo());
                        coordinate.setContWeight(planningTransDischarge.getContGross());
                        coordinate.setPod(planningTransDischarge.getDischPort());
                        coordinate.setMlo(planningTransDischarge.getMlo());
                        coordinate.setBlNo(planningTransDischarge.getBlNo());
                    } else if (planningTransDischarge.getContSize() == 40) {
                        coordinate = plannedCoordinates.get(coordinateIndex);
                        coordinateIndex++;

                        while (coordinateIndex < plannedCoordinates.size()) {
                            MasterYardCoordinat coordinat40ft = plannedCoordinates.get(coordinateIndex);

                            if (coordinat40ft.getBlock().equals(coordinate.getBlock()) && coordinat40ft.getSlot() == (coordinate.getSlot() + 1) && coordinat40ft.getRow().equals(coordinate.getRow()) && coordinat40ft.getTier().equals(coordinate.getTier())) {
                                coordinate.setContNo(planningTransDischarge.getContNo());
                                coordinate.setContWeight(planningTransDischarge.getContGross());
                                coordinate.setPod(planningTransDischarge.getDischPort());
                                coordinate.setMlo(planningTransDischarge.getMlo());
                                coordinate.setBlNo(planningTransDischarge.getBlNo());

                                coordinat40ft.setContNo(planningTransDischarge.getContNo());
                                coordinat40ft.setMlo(planningTransDischarge.getMlo());
                                coordinat40ft.setContWeight(planningTransDischarge.getContGross());
                                coordinat40ft.setPod(planningTransDischarge.getDischPort());
                                coordinat40ft.setBlNo(planningTransDischarge.getBlNo());

                                masterYardCoordinatFacadeRemote.edit(coordinat40ft);
                                break;
                            }

                            coordinate = plannedCoordinates.get(coordinateIndex);
                            coordinateIndex++;
                        }

                        if (coordinateIndex == plannedCoordinates.size()) {
                            notAllocated40ft++;
                            break;
                        }
                    } else if (planningTransDischarge.getContSize() == 45) {
                        coordinate = plannedCoordinates.get(coordinateIndex);
                        coordinateIndex++;

                        while (coordinateIndex < plannedCoordinates.size()) {
                            MasterYardCoordinat coordinat40ft = plannedCoordinates.get(coordinateIndex);
                            coordinateIndex++;
                            MasterYardCoordinat coordinat45ft = plannedCoordinates.get(coordinateIndex);

                            if (coordinat40ft.getBlock().equals(coordinate.getBlock())
                                    && coordinat40ft.getSlot() == (coordinate.getSlot() + 1)
                                    && coordinat40ft.getRow().equals(coordinate.getRow())
                                    && coordinat40ft.getTier().equals(coordinate.getTier())
                                    && coordinat45ft.getBlock().equals(coordinate.getBlock())
                                    && coordinat45ft.getSlot() == (coordinate.getSlot() + 2)
                                    && coordinat45ft.getRow().equals(coordinate.getRow())
                                    && coordinat45ft.getTier().equals(coordinate.getTier())) {
                                coordinate.setContNo(planningTransDischarge.getContNo());
                                coordinate.setContWeight(planningTransDischarge.getContGross());
                                coordinate.setPod(planningTransDischarge.getDischPort());
                                coordinate.setMlo(planningTransDischarge.getMlo());
                                coordinate.setBlNo(planningTransDischarge.getBlNo());

                                coordinat40ft.setContNo(planningTransDischarge.getContNo());
                                coordinat40ft.setMlo(planningTransDischarge.getMlo());
                                coordinat40ft.setContWeight(planningTransDischarge.getContGross());
                                coordinat40ft.setPod(planningTransDischarge.getDischPort());
                                coordinat40ft.setBlNo(planningTransDischarge.getBlNo());

                                coordinat45ft.setContNo(planningTransDischarge.getContNo());
                                coordinat45ft.setMlo(planningTransDischarge.getMlo());
                                coordinat45ft.setContWeight(planningTransDischarge.getContGross());
                                coordinat45ft.setPod(planningTransDischarge.getDischPort());
                                coordinat45ft.setBlNo(planningTransDischarge.getBlNo());

                                masterYardCoordinatFacadeRemote.edit(coordinat40ft);
                                masterYardCoordinatFacadeRemote.edit(coordinat45ft);
                                break;
                            }

                            coordinate = plannedCoordinates.get(coordinateIndex);
                            coordinateIndex++;
                        }

                        if (coordinateIndex == plannedCoordinates.size()) {
                            notAllocated40ft++;
                            break;
                        }
                    }

                    masterYardCoordinatFacadeRemote.edit(coordinate);

                    MasterYard masterYard = masterYardFacadeRemote.find(coordinate.getBlock());
                    planningTransDischarge.setMasterYard(masterYard);
                    planningTransDischarge.setYSlot(coordinate.getSlot());
                    planningTransDischarge.setYRow(coordinate.getRow());
                    planningTransDischarge.setYTier(coordinate.getTier());

                    if (planningVessel.getStatus().equals("Servicing")) {
                        ServiceContTranshipment serviceContTranshipment = new ServiceContTranshipment();

                        serviceContTranshipment.setContNo(planningTransDischarge.getContNo());
                        serviceContTranshipment.setMlo(planningTransDischarge.getMlo());
                        serviceContTranshipment.setServiceVessel(planningVessel.getServiceVessel());
                        serviceContTranshipment.setMasterCommodity(planningTransDischarge.getMasterCommodity());
                        serviceContTranshipment.setContSize(planningTransDischarge.getContSize());
                        serviceContTranshipment.setMasterContainerType(planningTransDischarge.getMasterContainerType());
                        serviceContTranshipment.setContStatus(planningTransDischarge.getContStatus());
                        serviceContTranshipment.setContGross(planningTransDischarge.getContGross());
                        serviceContTranshipment.setSealNo(planningTransDischarge.getSealNo());
                        serviceContTranshipment.setMasterYard(planningTransDischarge.getMasterYard());
                        serviceContTranshipment.setDangerous(planningTransDischarge.getDg());
                        serviceContTranshipment.setDgLabel(planningTransDischarge.getDgLabel());
                        serviceContTranshipment.setOverSize(planningTransDischarge.getOverSize());
                        serviceContTranshipment.setTwentyOneFeet(planningTransDischarge.getTwentyOneFeet());
                        serviceContTranshipment.setVBay(planningTransDischarge.getVBay());
                        serviceContTranshipment.setVRow(planningTransDischarge.getVRow());
                        serviceContTranshipment.setVTier(planningTransDischarge.getVTier());
                        serviceContTranshipment.setYRow(planningTransDischarge.getYRow());
                        serviceContTranshipment.setIsExportImport(planningTransDischarge.getIsExportImport());
                        serviceContTranshipment.setYSlot(planningTransDischarge.getYSlot());
                        serviceContTranshipment.setYTier(planningTransDischarge.getYTier());
                        serviceContTranshipment.setStartPlacementDate(planningVessel.getServiceVessel().getStartWorkTime());
                        serviceContTranshipment.setPosition("01");

                        if(planningTransDischarge.getPlanningVessel() != null)
                            serviceContTranshipment.setNewPpkb(planningTransDischarge.getPlanningVessel().getNoPpkb());

                        serviceContTranshipment.setCrane("L");
                        serviceContTranshipment.setBlNo(planningTransDischarge.getBlNo());
                        serviceContTranshipment.setDischPort(planningTransDischarge.getDischPort());
                        serviceContTranshipment.setLoadPort(planningTransDischarge.getLoadPort());
                        serviceContTranshipment.setPortOfDelivery(planningTransDischarge.getPortOfDelivery());

                        serviceContTranshipmentFacadeRemote.create(serviceContTranshipment);
                    }

                    planningTransDischargeFacadeRemote.create(planningTransDischarge);
                    masterYardCoordinatFacadeRemote.edit(coordinate);
                    saved++;
                    coordinateIndex++;
                }
            }

            if (notAllocated40ft > 0){
                FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", String.format(FacesHelper.getLocaleMessage("application.validation.some_data_fail_to_saved", facesContext), saved, selectedContainers.length), null);
            } else{
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
            }

            baplieDischarges = baplieDischargeFacadeRemote.findBaplieDischargesByPPKB(noPPKB);
            planningTransDischarges = planningTransDischargeFacadeRemote.findAllPlanningTransDischarges(noPPKB);
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, "application.save.failed");
        }

        requestContext.addCallbackParam("isConstraintNotEnough", false);
    }

    public void submitUc() {
        for (int i = 0; i < selectedContainerUc.length; i++) {
            uncontainerizedService = new UncontainerizedService();
            uncontainerizedService = uncontainerizedServiceFacadeRemote.find((Integer) selectedContainerUc[i][0]);
            //masukkan ke planning trans discharge
            uncontainerizedService.setIsTranshipment("TRUE");
            if (newPlanningVessel!= null) {
                uncontainerizedService.setNewPpkb(newPlanningVessel.getNoPpkb());
            }
            uncontainerizedService.setBayPlan("FALSE");
            //save database
            uncontainerizedServiceFacadeRemote.edit(uncontainerizedService);
        }
        //refresh data table
        uncontainerizedList = uncontainerizedServiceFacadeRemote.findUncontainerizedTranshipment(noPPKB);
        uncontainerizedAfterList = uncontainerizedServiceFacadeRemote.findUncontainerizedAfterTranshipment(noPPKB);
        //bersihkan newPPKB
        clearNewPPKB();
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.save.succeeded");
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void clearNewPPKB() {
        newPPKB = null;
        newVessel = new Object[4];
        newPlanningVessel = null;
    }

    public Object[][] getSelectedContainerUc() {
        return selectedContainerUc;
    }

    public void setSelectedContainerUc(Object[][] selectedContainerUc) {
        this.selectedContainerUc = selectedContainerUc;
    }

    public List<Object[]> getLoadPlanningAllocationsInfo() {
        return loadPlanningAllocationsInfo;
    }

    /*
    class for table choice
     */
    public class Container {

        private Object blNo;
        private Object contNo;
        private Object contSize;
        private Object contType;
        private Object contStatus;
        private Object contGross;
        private Object commodity;
        private Object sealNo;
        private Object bay;
        private Object row;
        private Object tier;
        private Object loadPort;
        private Object dischPort;
        private Object id;

        // the Bicycle class has one constructor
        public Container(Object blNo, Object contNo, Object contSize, Object contType, Object contStatus, Object contGross, Object commodity, Object sealNo, Object bay, Object row, Object tier, Object loadPort, Object dischPort, Object id) {
            this.blNo = blNo;
            this.contNo = contNo;
            this.contSize = contSize;
            this.contType = contType;
            this.contStatus = contStatus;
            this.contGross = contGross;
            this.commodity = commodity;
            this.sealNo = sealNo;
            this.bay = bay;
            this.row = row;
            this.tier = tier;
            this.loadPort = loadPort;
            this.dischPort = dischPort;
            this.id = id;
        }

        /**
         * @return the blNo
         */
        public Object getBlNo() {
            return blNo;
        }

        /**
         * @param blNo the blNo to set
         */
        public void setBlNo(Object blNo) {
            this.blNo = blNo;
        }

        /**
         * @return the contNo
         */
        public Object getContNo() {
            return contNo;
        }

        /**
         * @param contNo the contNo to set
         */
        public void setContNo(Object contNo) {
            this.contNo = contNo;
        }

        /**
         * @return the contSize
         */
        public Object getContSize() {
            return contSize;
        }

        /**
         * @param contSize the contSize to set
         */
        public void setContSize(Object contSize) {
            this.contSize = contSize;
        }

        /**
         * @return the contType
         */
        public Object getContType() {
            return contType;
        }

        /**
         * @param contType the contType to set
         */
        public void setContType(Object contType) {
            this.contType = contType;
        }

        /**
         * @return the contStatus
         */
        public Object getContStatus() {
            return contStatus;
        }

        /**
         * @param contStatus the contStatus to set
         */
        public void setContStatus(Object contStatus) {
            this.contStatus = contStatus;
        }

        /**
         * @return the contGross
         */
        public Object getContGross() {
            return contGross;
        }

        /**
         * @param contGross the contGross to set
         */
        public void setContGross(Object contGross) {
            this.contGross = contGross;
        }

        /**
         * @return the commodity
         */
        public Object getCommodity() {
            return commodity;
        }

        /**
         * @param commodity the commodity to set
         */
        public void setCommodity(Object commodity) {
            this.commodity = commodity;
        }

        /**
         * @return the sealNo
         */
        public Object getSealNo() {
            return sealNo;
        }

        /**
         * @param sealNo the sealNo to set
         */
        public void setSealNo(Object sealNo) {
            this.sealNo = sealNo;
        }

        /**
         * @return the bay
         */
        public Object getBay() {
            return bay;
        }

        /**
         * @param bay the bay to set
         */
        public void setBay(Object bay) {
            this.bay = bay;
        }

        /**
         * @return the row
         */
        public Object getRow() {
            return row;
        }

        /**
         * @param row the row to set
         */
        public void setRow(Object row) {
            this.row = row;
        }

        /**
         * @return the tier
         */
        public Object getTier() {
            return tier;
        }

        /**
         * @param tier the tier to set
         */
        public void setTier(Object tier) {
            this.tier = tier;
        }

        /**
         * @return the loadPort
         */
        public Object getLoadPort() {
            return loadPort;
        }

        /**
         * @param loadPort the loadPort to set
         */
        public void setLoadPort(Object loadPort) {
            this.loadPort = loadPort;
        }

        /**
         * @return the dischPort
         */
        public Object getDischPort() {
            return dischPort;
        }

        /**
         * @param dischPort the dischPort to set
         */
        public void setDischPort(Object dischPort) {
            this.dischPort = dischPort;
        }

        /**
         * @return the id
         */
        public Object getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(Object id) {
            this.id = id;
        }
    }
}
