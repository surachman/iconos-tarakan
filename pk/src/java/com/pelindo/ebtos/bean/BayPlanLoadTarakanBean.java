/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.BaplieDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterGrossParameterFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterProfileDetailFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationFilterFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationTempFacadeRemote;
import com.pelindo.ebtos.util.GrossConverter;
import com.pelindo.ebtos.model.db.BaplieDischarge;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import com.pelindo.ebtos.model.db.PlanningContLoad;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.YardAllocation;
import com.pelindo.ebtos.model.db.YardAllocationFilter;
import com.pelindo.ebtos.model.db.YardAllocationTemp;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterGrossParameter;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.pelindo.ebtos.util.GrossConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.bytecode.stackmap.BasicBlock;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "bayPlanLoadTarakanBean")
@ViewScoped
public class BayPlanLoadTarakanBean implements Serializable {

    @EJB
    private MasterVesselProfileFacadeRemote masterVesselProfileFacade;
    @EJB
    private MasterProfileDetailFacadeRemote masterProfileDetailFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
//    @EJB
//    private BaplieDischargeFacadeRemote baplieDischargeFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private PlanningContLoadFacadeRemote planningContLoadFacadeRemote;
    @EJB
    private PlanningShiftDischargeFacadeRemote planningShiftDischargeFacadeRemote;
    @EJB
    private PlanningTransDischargeFacadeRemote planningTransDischargeFacadeRemote;
    @EJB
    private YardAllocationFacadeRemote yardAllocationFacadeRemote;
    @EJB
    private YardAllocationTempFacadeRemote yardAllocationTempFacadeRemote;
    @EJB
    private YardAllocationFilterFacadeRemote yardAllocationFilterFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private ServiceContLoadFacadeRemote serviceContLoadFacadeRemote;
    
//    private static MasterGrossParameterFacadeRemote masterGrossParameterFacade = lookupMasterGrossParameterFacadeRemote();
//    private static MasterGrossParameter masterGrossParameter;
    
    
    private YardAllocation yardAllocation;
    private YardAllocationFilter yardAllocationFilter;
    private YardAllocationTemp yardAllocationTemp;
    private MasterYardCoordinat masterYardCoordinat;
    private List<Object[]> planningVessels;
    private Object[] planningVessel;
    private PlanningVessel vessel;
    private List<PlanningContLoad> planningContLoads;
    private List<PlanningContLoad> planningContLoads2;
    private PlanningContLoad planningContLoad;
    private String opsi;
    private List<SelectItem> bays;
    private Integer selectedBayIndex;
    private String noPPKB;
    private String vesselCode;
    private List<Integer> bayChoices;
    private List<Integer> rowChoices;
    private List<Integer> tierChoices;
    private List<Integer> rowUps;
    private List<Integer> rowBottoms;
    private List<Object[]> masterCustomers;
    private String selectedPort1;
    private String selectedPort2;
    private String selectedPort3;
    private YardPosition yardPlanPosition;
    private Map<String, Map<String, String>> portNames;

    public Map<String,Map<String, String>> getPortNames() {
        return portNames;
    }

    public void setPortNames(Map<String, Map<String, String>> portNames) {
        this.portNames = portNames;
    }

    /** Creates a new instance of BayPlanDischargeBean */
    public BayPlanLoadTarakanBean() {
        bayChoices = new ArrayList<Integer>();
        rowChoices = new ArrayList<Integer>();
        tierChoices = new ArrayList<Integer>();
        rowUps = new ArrayList<Integer>();
        rowBottoms = new ArrayList<Integer>();
        bays = new ArrayList();
        portNames = new HashMap<>();
    }

    @PostConstruct
    private void construct() {
        initial();
        vesselCode = null;
    }

    public void handleNewPpkb(ActionEvent event) {
        planningVessels = planningVesselFacadeRemote.findPlanningVesselsByActivityYardAlocation();
    }

    public void initial() {
        planningContLoad = new PlanningContLoad();
        MasterYard masterYard = masterYardFacadeRemote.find("A");
        yardPlanPosition = new YardPosition(masterYard);
        yardPlanPosition.setSlot((short) 1);
        yardPlanPosition.setRow((short) 1);
        yardPlanPosition.setTier((short) 1);
    }

    public void handleDownloadTransactionRecap(ActionEvent event){
        RequestContext context = RequestContext.getCurrentInstance();
        context.addCallbackParam("doPrint", true);
        context.addCallbackParam("url", StringEscapeUtils.escapeHtml3("../../../../ReportBayPlanDischarge.pdf?no_ppkb=" + noPPKB));
    }

    /**
     * @return the planningVessel
     */
    public Object[] getPlanningVessel() {
        return planningVessel;
    }

    public PlanningVessel getVessel() {
        return vessel;
    }

    /**
     * @return the baplieDischarges
     */
    public List<PlanningContLoad> getPlanningContLoads() {
        return planningContLoads;
    }

    /**
     * @return the planningVessels
     */
    public List<Object[]> getPlanningVessels() {
        return planningVessels;
    }

    /**
     * @return the baplieDischarge
     */
    public PlanningContLoad getPlanningContLoad() {
        return planningContLoad;
    }

    /**
     * @param planningContLoad the baplieDischarge to set
     */
    public void setPlanningContLoad(PlanningContLoad planningContLoad) {
        this.planningContLoad = planningContLoad;
    }

    public String getVesselProfileUrl() {
        if (planningVessel == null || bays == null) {
            return "#";
        }
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../bayPlan.png?"
                + "w=850&"
                + "h=400&"
                + "bay=" + bays.get(selectedBayIndex).getLabel() + "&"
                + "ppkb=" + planningVessel[0];
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
    
// private static MasterGrossParameterFacadeRemote lookupMasterGrossParameterFacadeRemote() {
//        try {
//            Context c = new InitialContext();
//            return (MasterGrossParameterFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterGrossParameterFacade!com.pelindo.ebtos.ejb.facade.remote.MasterGrossParameterFacadeRemote");
//        } catch (NamingException ne) {
//            Logger.getLogger(GrossConverter.class.getName()).log(Level.SEVERE, "exception caught", ne);
//        }
//        return null;
//    }
 
    public void saveEdit(ActionEvent event) {
        boolean isValid = false;
        List<PlanningContLoad> sameContainers = planningContLoadFacadeRemote.findTheSameContainer(vessel.getNoPpkb(), planningContLoad.getContNo());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        if (planningContLoad.getContNo() == null || planningContLoad.getContGross() == null) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.transaction.failed");
        } else if (planningContLoad.getMasterContainerType().getContType() == 3) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.transaction.failed");
        } else if (opsi.equals("add") && !sameContainers.isEmpty()) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.validation.containerno_exist");
//        } else if (opsi.equals("add") && masterYardCoordinatFacadeRemote.findDuplicate(planningContLoad.getContNo())) {
//            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.validation.containerno_exist");
        } else {
            if (planningContLoad.getPlanningVessel() == null) {
                planningContLoad.setPlanningVessel(vessel);
            }
            
            boolean dataComplete = true;
            int gross = planningContLoad.getContGross();
            short size = planningContLoad.getContSize();
           //FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", baplieDischarge.getContGross());
            
            //String a =
//            planningContLoad.setContGross(gross);GrossClass(GrossConverter.convert(size, gross));
//            try{
//                baplieDischarge.setGrossClass(this.convert((short) size, gross));
//                if (baplieDischarge.getGrossClass().equalsIgnoreCase("XX")) {
//                dataComplete = false;
//                
//            }
//                
//            }
//            catch(RuntimeException re) {
//            
//            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "ok");
//            }
                    
                    
                
            
            
            MasterPort mp1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
            if (mp1!=null)
                planningContLoad.setLoadPort(mp1.getPortCode());
            else {
                planningContLoad.setLoadPort(null);
                dataComplete = false;
            }
            MasterPort mp2 = masterPortFacadeRemote.findMasterPortByName(selectedPort2);
            if (mp2!=null)
                planningContLoad.setDischPort(mp2.getPortCode());
            else {
                planningContLoad.setDischPort(null);
                dataComplete = false;
            }
            
            MasterPort pod = masterPortFacadeRemote.findMasterPortByName(selectedPort3);
            if (pod!=null)
                planningContLoad.setPortOfDelivery(pod);
            else {
                planningContLoad.setPortOfDelivery(null);
                dataComplete = false;
            }
            
            if(dataComplete) {
                planningContLoad.setCompleted("TRUE");
            } else {
                planningContLoad.setCompleted("FALSE");
            }
             

            String implementedPortCode = masterSettingAppFacadeRemote.findImplementedPortCode();

            if (vessel.getStatus().equals("Servicing")) {
                if (planningContLoad.getPortOfDelivery().getPortCode().equals(implementedPortCode)) {
                    MasterYardCoordinat targetLocation = masterYardCoordinatFacadeRemote.findByCoordinat(yardPlanPosition.getBlock().getBlock(), yardPlanPosition.getSlot(), yardPlanPosition.getRow(), yardPlanPosition.getTier());
                    MasterYardCoordinat targetLocation40ft = masterYardCoordinatFacadeRemote.findByCoordinat(yardPlanPosition.getBlock().getBlock(), yardPlanPosition.getSlot() + 1, yardPlanPosition.getRow(), yardPlanPosition.getTier());
                    MasterYardCoordinat targetLocation45ft = masterYardCoordinatFacadeRemote.findByCoordinat(yardPlanPosition.getBlock().getBlock(), yardPlanPosition.getSlot() + 2, yardPlanPosition.getRow(), yardPlanPosition.getTier());

                    if ((planningContLoad.getContSize() == 20 && targetLocation != null && targetLocation.getStatus().equals("empty")) || 
                            (planningContLoad.getContSize() == 40 && targetLocation != null && targetLocation.getStatus().equals("empty") && targetLocation40ft != null && targetLocation40ft.getStatus().equals("empty")) ||
                            (planningContLoad.getContSize() == 45 && targetLocation != null && targetLocation.getStatus().equals("empty") && targetLocation40ft != null && targetLocation40ft.getStatus().equals("empty") && targetLocation45ft != null && targetLocation45ft.getStatus().equals("empty"))) {
                        PlanningContLoad planningContLoadSaved = planningContLoadFacadeRemote.findByNoPpkbContNoAndStatusCancelLoading(vessel.getNoPpkb(), planningContLoad.getContNo(), false);

                        if (planningContLoad == null) {
                            planningContLoad = new PlanningContLoad();
                        }

                        planningContLoadSaved.setMasterYard(yardPlanPosition.getBlock());
                        planningContLoadSaved.setMasterContainerType(planningContLoad.getMasterContainerType());
                        planningContLoadSaved.setMasterCommodity(planningContLoad.getMasterCommodity());
                        planningContLoadSaved.setPlanningVessel(vessel);
                        planningContLoadSaved.setContNo(planningContLoad.getContNo());
                        planningContLoadSaved.setMlo(planningContLoad.getMlo());
                        planningContLoadSaved.setContSize(planningContLoad.getContSize());
                        planningContLoadSaved.setContStatus(planningContLoad.getContStatus());
                        planningContLoadSaved.setContGross(planningContLoad.getContGross());
                        planningContLoadSaved.setSealNo(planningContLoad.getSealNo());
                        planningContLoadSaved.setDg(planningContLoad.getDg());
                        planningContLoadSaved.setIsExportImport(planningContLoad.getIsExportImport());
                        planningContLoadSaved.setDgLabel(planningContLoad.getDgLabel());
                        planningContLoadSaved.setTwentyOneFeet(planningContLoad.getTwentyOneFeet());
                        planningContLoadSaved.setOverSize(planningContLoad.getOverSize());
//                        planningContLoad.setTradeType(planningContLoad.getTradeType());
                        planningContLoadSaved.setPortOfDelivery(planningContLoad.getPortOfDelivery());
                        planningContLoadSaved.setDischPort(planningContLoad.getDischPort());
                        planningContLoadSaved.setVBay(planningContLoad.getVBay());
                        planningContLoadSaved.setVRow(planningContLoad.getVRow());
                        planningContLoadSaved.setVTier(planningContLoad.getVTier());
                        planningContLoadSaved.setYSlot(yardPlanPosition.getSlot());
                        planningContLoadSaved.setYRow(yardPlanPosition.getRow());
                        planningContLoadSaved.setYTier(yardPlanPosition.getTier());
//                        planningContLoadSaved.setGrossClass(planningContLoad.getGrossClass());
                        planningContLoadSaved.setBlNo(planningContLoad.getBlNo());

                        ServiceContLoad serviceContLoad = serviceContLoadFacadeRemote.findByNoPpkbAndContNo(vessel.getNoPpkb(), planningContLoad.getContNo());

                        if (serviceContLoad == null) {
                            serviceContLoad = new ServiceContLoad();
                        }

                        serviceContLoad.setContNo(planningContLoad.getContNo());
                        serviceContLoad.setMlo(planningContLoad.getMlo());
                        serviceContLoad.setServiceVessel(vessel.getServiceVessel());
                        serviceContLoad.setMasterCommodity(planningContLoad.getMasterCommodity());
                        serviceContLoad.setContSize(planningContLoad.getContSize());
                        serviceContLoad.setMasterContainerType(planningContLoad.getMasterContainerType());
                        serviceContLoad.setContStatus(planningContLoad.getContStatus());
                        serviceContLoad.setContGross(planningContLoad.getContGross());
                        serviceContLoad.setSealNo(planningContLoad.getSealNo());
                        serviceContLoad.setMasterYard(planningContLoad.getMasterYard());
                        serviceContLoad.setTwentyOneFeet(planningContLoad.getTwentyOneFeet());
                        serviceContLoad.setDangerous(planningContLoad.getDg());
                        serviceContLoad.setDgLabel(planningContLoad.getDgLabel());
                        serviceContLoad.setOverSize(planningContLoad.getOverSize());
                        serviceContLoad.setVBay(planningContLoad.getVBay());
                        serviceContLoad.setVRow(planningContLoad.getVRow());
                        serviceContLoad.setVTier(planningContLoad.getVTier());
                        serviceContLoad.setYRow(planningContLoad.getYRow());
                        serviceContLoad.setYSlot(planningContLoad.getYSlot());
                        serviceContLoad.setYTier(planningContLoad.getYTier());
                        serviceContLoad.setLoadDate(vessel.getServiceVessel().getStartWorkTime());
                        serviceContLoad.setCrane("L");
                        serviceContLoad.setPosition("01");
                        serviceContLoad.setIsChangeVessel("FALSE");
                        serviceContLoad.setIsSeling("FALSE");
                        serviceContLoad.setIsTranshipment("FALSE");
//                        serviceContLoad.setIsInspection("FALSE");
//                        serviceContDischarge.setGrossClass(planningContLoad.getGrossClass());
                        serviceContLoad.setIsExportImport(planningContLoad.getIsExportImport());
                        serviceContLoad.setBlNo(planningContLoad.getBlNo());
                        serviceContLoad.setStatusCancelLoading("FALSE");
                        serviceContLoad.setPortOfDelivery(planningContLoad.getPortOfDelivery());
                        
                        targetLocation.setStatus("planning");
                        targetLocation.setContNo(serviceContLoad.getContNo());
                        targetLocation.setMlo(serviceContLoad.getMlo());
                        targetLocation.setContSize(serviceContLoad.getContSize());
                        targetLocation.setContType(serviceContLoad.getMasterContainerType().getContType().toString());
                        targetLocation.setContWeight(serviceContLoad.getContGross().intValue());
                        targetLocation.setPod(null);
                        targetLocation.setNoPpkb(serviceContLoad.getServiceVessel().getNoPpkb());
//                        targetLocation.setGrossClass(serviceContLoad.getGrossClass());
                        masterYardCoordinatFacadeRemote.edit(targetLocation);

                        if (serviceContLoad.getContSize() == 40) {
                            targetLocation40ft.setStatus("planning");
                            targetLocation40ft.setContNo(serviceContLoad.getContNo());
                            targetLocation40ft.setMlo(serviceContLoad.getMlo());
                            targetLocation40ft.setContSize(serviceContLoad.getContSize());
                            targetLocation40ft.setContType(serviceContLoad.getMasterContainerType().getContType().toString());
                            targetLocation40ft.setContWeight(serviceContLoad.getContGross().intValue());
                            targetLocation40ft.setPod(null);
                            targetLocation40ft.setNoPpkb(serviceContLoad.getServiceVessel().getNoPpkb());
//                            targetLocation40ft.setGrossClass(serviceContLoad.getGrossClass());
                            masterYardCoordinatFacadeRemote.edit(targetLocation40ft);
                        }

                        if (serviceContLoad.getContSize() == 45) {
                            targetLocation45ft.setStatus("planning");
                            targetLocation45ft.setContNo(serviceContLoad.getContNo());
                            targetLocation45ft.setMlo(serviceContLoad.getMlo());
                            targetLocation45ft.setContSize(serviceContLoad.getContSize());
                            targetLocation45ft.setContType(serviceContLoad.getMasterContainerType().getContType().toString());
                            targetLocation45ft.setContWeight(serviceContLoad.getContGross().intValue());
                            targetLocation45ft.setPod(null);
                            targetLocation45ft.setNoPpkb(serviceContLoad.getServiceVessel().getNoPpkb());
//                            targetLocation45ft.setGrossClass(serviceContLoad.getGrossClass());
                            masterYardCoordinatFacadeRemote.edit(targetLocation45ft);
                        }

//                        baplieDischargeFacadeRemote.edit(planningContLoad);
                        serviceContLoadFacadeRemote.edit(serviceContLoad);
                        planningContLoadFacadeRemote.edit(planningContLoad);
                        FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.save.succeeded");
                        isValid = true;
                    } else {
                        FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "application.validation.yard");
                    }
                } else {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "Data gagal disimpan, untuk direct input Shifting harus menggunakan menu Shifting Planning", null);
                }
            } else {
                planningContLoadFacadeRemote.edit(planningContLoad);

                if (opsi.equals("add")) {
                    handleButtonAdd(event);
                }

                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.save.succeeded");
            }

            planningContLoads = planningContLoadFacadeRemote.findByNoPpkb(noPPKB);
            reloadPortNames();
            planningContLoads2 = findContainerStatus(noPPKB);

            if (opsi.equals("delete")){
                isValid = true;
                planningContLoad = new PlanningContLoad();
                planningContLoad.setMasterCommodity(new MasterCommodity());
                planningContLoad.setMasterContainerType(new MasterContainerType());
                
                planningContLoad.setPortOfDelivery(new MasterPort());
            }
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", isValid);
    }
    
    private List findContainerStatus(String noPPKB)
    {
        List results = planningContLoadFacadeRemote.findContainerStatus(noPPKB);
        Object[] totalRow = new Object[5];
        totalRow[0] = "TOTAL";
        totalRow[1] = totalRow[2] = totalRow[3] = totalRow[4] = 0;
        for(Object r: results)
        {
            Object[] row = (Object[]) r;
            for(int i = 1; i <= 4; i++)
            {
                totalRow[i] = ((int) totalRow[i]) + ((Number) row[i]).intValue();
            }
        }
        results.add(totalRow);
        return results;
    }

    public void delete(ActionEvent event) {
        try {
            if (opsi.equalsIgnoreCase("delete")) {
                planningContLoadFacadeRemote.remove(planningContLoad);
            } else {
//                planningContLoads = planningContLoadFacadeRemote.findByNoPpkb(noPPKB);
//                for (PlanningContLoad row: planningContLoads) {
//                    planningContLoadFacadeRemote.remove(row);
//                }

                List <PlanningContLoad> planningContLoads = planningContLoadFacadeRemote.findByNoPpkb(noPPKB);
                for (PlanningContLoad planningContLoad: planningContLoads) {
                    planningContLoadFacadeRemote.remove(planningContLoad);
                }
            }
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.succeeded");
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.failed");
        }
        planningContLoads = planningContLoadFacadeRemote.findByNoPpkb(noPPKB);
        reloadPortNames();
        planningContLoads2 = findContainerStatus(noPPKB);
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        
        Logger.getAnonymousLogger().info("Selected noPPKB => " + noPPKB);
        
        planningVessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(noPPKB);
        planningContLoads = planningContLoadFacadeRemote.findByNoPpkb(noPPKB);
        reloadPortNames();
        planningContLoads2 = findContainerStatus(noPPKB);
        vessel = planningVesselFacadeRemote.find(noPPKB);
        Logger.getAnonymousLogger().info("vesselCode => " + planningVessel[10].toString());
        vesselCode = planningVessel[10].toString();
        //ngurusin bay
        bayChoices.clear();
        bayChoices = masterVesselProfileFacade.findBaysByVessel(vesselCode);
    }

    public void handleShowMasterCustomers(ActionEvent event) {
        masterCustomers = masterCustomerFacadeRemote.findMasterCustomers();
    }
    
    public void handleSelectCommodity() {
        planningContLoad.setDg("FALSE");

        if (planningContLoad.getMasterCommodity() != null && planningContLoad.getMasterCommodity().getMasterDangerousClass() != null) {
            planningContLoad.setDg("TRUE");
        }
    }

    public void handleSelectMasterCustomers(ActionEvent event) {
        String custCode = (String) event.getComponent().getAttributes().get("custCode");
        MasterCustomer mlo = masterCustomerFacadeRemote.find(custCode);
        planningContLoad.setMlo(mlo);
    }

    public void handleResetBayPlanPreview() {
        List<Integer> bay = masterVesselProfileFacade.findBaysByVessel(vesselCode);
        for (int i = 0; i < bay.size(); i++) {
            bays.add(new SelectItem(new Integer(i), String.valueOf(bay.get(i))));
        }
        selectedBayIndex = (Integer) bays.get(0).getValue();
    }

    public void handleEditDeleteButton(ActionEvent event) {
        bayChoices = masterVesselProfileFacade.findBaysByVessel(vesselCode);
        Integer id = (Integer) event.getComponent().getAttributes().get("idCont");
        planningContLoad = planningContLoadFacadeRemote.find(id);
        opsi = "delete";
//        Tarakan
        //untuk mengkondisikan pemilihan bay
//        if (planningContLoad.getVBay() == 0) {
//            planningContLoad.setVBay(Short.parseShort(bayChoices.get(0).toString()));
//            changeBay(planningContLoad.getVBay());
//            changeRow(Short.parseShort(rowChoices.get(0).toString()));
//        } else {
//            changeBay(planningContLoad.getVBay());
//            changeRow(planningContLoad.getVRow());
//        }

//        masterPorts = masterPortFacadeRemote.findAll();
        String loadPortCode = planningContLoad.getLoadPort();
        selectedPort1 = loadPortCode!=null ? masterPortFacadeRemote.find(loadPortCode).getName() : null;
        String dischPortCode = planningContLoad.getDischPort();
        selectedPort2 = dischPortCode!=null ? masterPortFacadeRemote.find(dischPortCode).getName() : null;
        selectedPort3 = planningContLoad.getPortOfDelivery()!=null ? planningContLoad.getPortOfDelivery().getName() : null;

//        Tarakan
//        fixEmptyCyChoices();
    }

    private void fixEmptyCyChoices(){
        fixEmptyBayChoices();
        fixEmptyRowChoices();
        fixEmptyTierChoices();
    }

    private void fixEmptyBayChoices() {
        if ((bayChoices.isEmpty() || !bayChoices.contains(planningContLoad.getVBay().intValue())) && opsi.equals("delete")) {

            bayChoices.add(planningContLoad.getVBay().intValue());
        }
    }

    private void fixEmptyRowChoices() {
        if ((rowChoices.isEmpty() || !rowChoices.contains(planningContLoad.getVRow().intValue())) && opsi.equals("delete")) {
            rowChoices.add(planningContLoad.getVRow().intValue());
        }
    }

    private void fixEmptyTierChoices() {
        if ((tierChoices.isEmpty() || !tierChoices.contains(planningContLoad.getVTier().intValue())) && opsi.equals("delete")) {
            tierChoices.add(planningContLoad.getVTier().intValue());
        }
    }

    public void handleDeleteAll(ActionEvent event) {
        opsi = "deleteall";
    }

    public void handleButtonAdd(ActionEvent event) {
        String portCode = masterSettingAppFacadeRemote.findImplementedPortCode();
        MasterPort masterPortDefault = masterPortFacadeRemote.find(portCode), 
                loadPort = masterPortFacadeRemote.find("IDSUB"); // Surabaya Port
        selectedPort1 = masterPortDefault.getName(); // Set load port default value to Surabaya Port
        selectedPort2 = loadPort.getName();
        selectedPort3 = loadPort.getName();

        planningContLoad.setContNo(null);
        planningContLoad.setId(null);
//        planningContLoad.setMasterPort1(masterPortDefault);
        planningContLoad.setPortOfDelivery(loadPort);
        planningContLoad.setCompleted("FALSE");
        planningContLoad.setContGross(0);
        planningContLoad.setSealNo("");
        planningContLoad.setMasterCommodity(null);
        planningContLoad.setBlNo("");
        planningContLoad.setMlo(null);

        //untuk mengkondisikan pemilihan bay
        opsi = "add";

        if (bayChoices.isEmpty()) {
            bayChoices.add(1); // Test Update by MKR
        }
        if (tierChoices.isEmpty()) {
            tierChoices.add(0);
        }
        if (rowChoices.isEmpty()) {
            rowChoices.add(0);
        }
        
        planningContLoad.setVBay(Short.parseShort(bayChoices.get(0).toString()));
        changeBay(planningContLoad.getVBay());

        if (planningContLoad.getContSize() == null) {
            MasterContainerType mct = masterContainerTypeFacadeRemote.find(1);
            planningContLoad.setMasterContainerType(mct);
            planningContLoad.setContSize(mct.getFeetMark());
            planningContLoad.setContStatus("FCL");
        }

        MasterYard masterYard = masterYardFacadeRemote.find("A");
        yardPlanPosition = new YardPosition(masterYard);
        yardPlanPosition.setSlot((short) 1);
        yardPlanPosition.setRow((short) 1);
        yardPlanPosition.setTier((short) 1);
    }

    public void handleButtonCopy(ActionEvent event) {
        List<PlanningContLoad> notCompletedPlanningContLoads = planningContLoadFacadeRemote.findByPpkbNotCompleted(noPPKB);
        
        if (notCompletedPlanningContLoads.isEmpty()) {
//            planningContLoadFacadeRemote.finishBaplieDischarges(noPPKB);
            vessel = planningVesselFacadeRemote.find(vessel.getNoPpkb());
            vessel.setBaplieLoad("TRUE");
            planningVesselFacadeRemote.edit(vessel);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.finish.succeeded");
        } else {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "application.finish.failed");
        }
    }

    public void handleButtonUnfinish() {
        List<Integer> generateSatu = masterYardCoordinatFacadeRemote.unFinishBayPlanDischargeByPPKB(noPPKB);
        for (int i = 0; i < generateSatu.size(); i++) {
            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(generateSatu.get(i));
            
            //PENAMBAHAN PENGOSONGAN CY UNTUK CANCEL FINIS DISCHARD BY ADE CHELSEA TANGGAL 7 MAY 2014 01:05 WIT JAYAPURA        
            masterYardCoordinatFacadeRemote.updateMasterYardContainerGateOut(noPPKB,masterYardCoordinat.getContNo());
        
            masterYardCoordinat.setContNo(null);
            masterYardCoordinat.setMlo(null);
            masterYardCoordinat.setContWeight(null);
            masterYardCoordinat.setPod(null);
            masterYardCoordinat.setNoPpkb(null);
            masterYardCoordinat.setStatus("empty");
            masterYardCoordinat.setContSize(null);
            masterYardCoordinat.setContType(null);
            masterYardCoordinat.setGrossClass(null);
            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
            
        }
        
        
        
        List<String> generateDua = yardAllocationFacadeRemote.unFinishBayPlanDischargeByPPKB(noPPKB);
        for (int i = 0; i < generateDua.size(); i++) {
            yardAllocation = yardAllocationFacadeRemote.find(generateDua.get(i));
            yardAllocationFacadeRemote.remove(yardAllocation);
        }
        List<Integer> generateTiga = yardAllocationFilterFacadeRemote.unFinishBayPlanDischargeByPPKB(noPPKB);
        for (int i = 0; i < generateTiga.size(); i++) {
            yardAllocationFilter = yardAllocationFilterFacadeRemote.find(generateTiga.get(i));
            yardAllocationFilterFacadeRemote.remove(yardAllocationFilter);
        }
        List<Integer> generateEmpat = yardAllocationTempFacadeRemote.unFinishBayPlanDischargeByPPKB(noPPKB);
        for (int i = 0; i < generateEmpat.size(); i++) {
            yardAllocationTemp = yardAllocationTempFacadeRemote.find(generateEmpat.get(i));
            yardAllocationTempFacadeRemote.remove(yardAllocationTemp);
        }
//        planningContLoadFacadeRemote.unFinishBayPlanDischargeByPPKB(noPPKB);
        planningShiftDischargeFacadeRemote.unFinishBayPlanDischargeByPPKB(noPPKB);
        planningTransDischargeFacadeRemote.unFinishBayPlanDischargeByPPKB(noPPKB);
        vessel.setBaplieLoad("FALSE");
        planningVesselFacadeRemote.edit(vessel);
        FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.unfinish.succeeded");
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

    public String getSelectedPort1() {
        return selectedPort1;
    }

    public void setSelectedPort1(String selectedPort1) {
        this.selectedPort1 = selectedPort1;
    }

    public String getSelectedPort2() {
        return selectedPort2;
    }

    public void setSelectedPort2(String selectedPort2) {
        this.selectedPort2 = selectedPort2;
    }

    public String getSelectedPort3() {
        return selectedPort3;
    }

    public void setSelectedPort3(String selectedPort3) {
        this.selectedPort3 = selectedPort3;
    }

    //handling bay position
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

    public List<Object[]> getMasterCustomers() {
        return masterCustomers;
    }

    //rubah-rubah bay
    public void onchangeBay(ValueChangeEvent event) {
        Short newBay = (Short) event.getNewValue();
        planningContLoad.setVBay(newBay);
        changeBay(newBay);
    }

    public void onChangeRow(ValueChangeEvent event) {
        Short newRow = (Short) event.getNewValue();
        planningContLoad.setVRow(newRow);
        changeRow(newRow);
    }

    public void changeBay(Short newBay) {
        Integer startRow = 0;
        Integer totalRow = 0;
        //nyiapin data row
        rowChoices.clear();
        rowBottoms.clear();
        rowUps.clear();
        
        Logger.getAnonymousLogger().info("Inquiring MasterVessel using newBay.intValue() => " + newBay.toString());
        
        List<Object[]> loopRowBottom = masterVesselProfileFacade.findRowByBay(vesselCode, "bottom", newBay.intValue());
        
        //Dbg
        if(loopRowBottom.isEmpty()){
            Logger.getAnonymousLogger().info("loopRowBottom => EMPTY");
        }
        //Dbg
        

        if (!loopRowBottom.isEmpty()) {
            startRow = ((Integer) loopRowBottom.get(0)[0]).intValue();
            totalRow = ((Integer) loopRowBottom.get(0)[1]).intValue();
        }
        Integer entryRow = startRow;
        if (totalRow != null) {
            for (Integer i = 0; i < totalRow; i++) {
                rowChoices.add(entryRow);
                rowBottoms.add(entryRow);
                entryRow += 1;
            }
        }
        List<Object[]> loopRowUp = masterVesselProfileFacade.findRowByBay(vesselCode, "up", newBay.intValue());
        
        //Dbg
        if(loopRowUp.isEmpty()){
            Logger.getAnonymousLogger().info("loopRowUp => EMPTY");
        }
        //Dbg
        
        
        if (!loopRowUp.isEmpty()) {
            startRow = ((Integer) loopRowBottom.get(0)[0]).intValue();
            totalRow = ((Integer) loopRowBottom.get(0)[1]).intValue();
        }
        entryRow = startRow;
        if (totalRow != null) {
            for (Integer i = 0; i < totalRow; i++) {
                if (!rowChoices.contains(entryRow)) {
                    rowChoices.add(entryRow);
                }
                rowUps.add(entryRow);
                entryRow += 1;
            }
        }

        if (planningContLoad.getVRow() == null) {
            try {
                planningContLoad.setVRow(rowChoices.get(0).shortValue());
            } catch (RuntimeException re) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            }            
        }

        changeRow(planningContLoad.getVRow());
        fixEmptyRowChoices();
    }

    public void changeRow(Short newRow) {
		
        Logger.getAnonymousLogger().info("Starting at ");
        Integer startTier = 0;
        Integer totalTier = 0;
        Integer row = newRow.intValue();
        tierChoices.clear();
        if (rowUps.contains(row) && rowBottoms.contains(row)) {
            List<Object[]> loopTierBottom = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", planningContLoad.getVBay().intValue(), row);
            if (!loopTierBottom.isEmpty()) {
                startTier = ((Integer) loopTierBottom.get(0)[0]).intValue();
                totalTier = ((Integer) loopTierBottom.get(0)[1]).intValue();
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
            List<Object[]> loopTierUp = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", planningContLoad.getVBay().intValue(), row);
            if (!loopTierUp.isEmpty()) {
                startTier = ((Integer)  loopTierUp.get(0)[0]).intValue();
                totalTier = ((Integer)  loopTierUp.get(0)[1]).intValue();
            }
            entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                if (!tierChoices.contains(entryTier)) {
                    tierChoices.add(entryTier);
                }
                entryTier += 2;
            }
        } else if (rowBottoms.contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", planningContLoad.getVBay().intValue(), row);
            if (!loopTier.isEmpty()) {
                startTier = ((Integer) loopTier.get(0)[0]).intValue();
                totalTier = ((Integer) loopTier.get(0)[1]).intValue();
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
        } else if (rowUps.contains(row)) {
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", planningContLoad.getVBay().intValue(), row);
            if (!loopTier.isEmpty()) {
                startTier = ((Integer) loopTier.get(0)[0]).intValue();
                totalTier = ((Integer) loopTier.get(0)[1]).intValue();
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
        }

        if (planningContLoad.getVTier() == null) {
            planningContLoad.setVTier(tierChoices.get(0).shortValue());
        }
        
        fixEmptyTierChoices();
    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        MasterContainerType mct = masterContainerTypeFacadeRemote.find(newItem);
        planningContLoad.setMasterContainerType(mct);
        planningContLoad.setContSize(mct.getFeetMark());
    }

    public String getOpsi() {
        return opsi;
    }

    public YardPosition getYardPlanPosition() {
        return yardPlanPosition;
    }

    public void setYardPlanPosition(YardPosition yardPlanPosition) {
        this.yardPlanPosition = yardPlanPosition;
    }

    private void reloadPortNames() {
        portNames.clear();
        for (PlanningContLoad planningContLoad : planningContLoads)
        {
            Map<String, String> portName = new HashMap<>();
            String loadPortCode = planningContLoad.getLoadPort();
            if (loadPortCode!=null)
                portName.put("loadPort", masterPortFacadeRemote.find(loadPortCode).getName());
            String dischPortCode = planningContLoad.getDischPort();
            if (dischPortCode!=null)
                portName.put("dischPort", masterPortFacadeRemote.find(dischPortCode).getName());
            portNames.put(planningContLoad.getContNo(), portName);
                
        }
    }

    public class YardPosition {
        private MasterYard block;
        private Short slot;
        private Short row;
        private Short tier;

        public YardPosition(MasterYard block) {
            this.block = block;
        }

        public MasterYard getBlock() {
            return block;
        }

        public void setBlock(MasterYard block) {
            this.block = block;
        }

        public Short getRow() {
            return row;
        }

        public void setRow(Short row) {
            this.row = row;
        }

        public Short getSlot() {
            return slot;
        }

        public void setSlot(Short slot) {
            this.slot = slot;
        }

        public Short getTier() {
            return tier;
        }

        public void setTier(Short tier) {
            this.tier = tier;
        }
        
        
    }

    public List<PlanningContLoad> getPlanningContLoads2() {
        return planningContLoads2;
    }

    public void setPlanningContLoad2(List<PlanningContLoad> planningContLoads2) {
        this.planningContLoads2 = planningContLoads2;
    }
    
   
}
