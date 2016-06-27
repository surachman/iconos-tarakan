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
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationFilterFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationTempFacadeRemote;
import com.pelindo.ebtos.util.GrossConverter;
import com.pelindo.ebtos.model.db.BaplieDischarge;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
@ManagedBean(name = "bayPlanDischargeBean")
@ViewScoped
public class BayPlanDischargeBean implements Serializable {

    @EJB
    private MasterVesselProfileFacadeRemote masterVesselProfileFacade;
    @EJB
    private MasterProfileDetailFacadeRemote masterProfileDetailFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private BaplieDischargeFacadeRemote baplieDischargeFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private PlanningContDischargeFacadeRemote planningContDischargeFacadeRemote;
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
    private ServiceContDischargeFacadeRemote serviceContDischargeFacadeRemote;
    
//    private static MasterGrossParameterFacadeRemote masterGrossParameterFacade = lookupMasterGrossParameterFacadeRemote();
//    private static MasterGrossParameter masterGrossParameter;
    
    
    private YardAllocation yardAllocation;
    private YardAllocationFilter yardAllocationFilter;
    private YardAllocationTemp yardAllocationTemp;
    private MasterYardCoordinat masterYardCoordinat;
    private List<Object[]> planningVessels;
    private Object[] planningVessel;
    private PlanningVessel vessel;
    private List<BaplieDischarge> baplieDischarges;
    private List<BaplieDischarge> baplieDischarges2;
    private BaplieDischarge baplieDischarge;
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

    /** Creates a new instance of BayPlanDischargeBean */
    public BayPlanDischargeBean() {
        bayChoices = new ArrayList<Integer>();
        rowChoices = new ArrayList<Integer>();
        tierChoices = new ArrayList<Integer>();
        rowUps = new ArrayList<Integer>();
        rowBottoms = new ArrayList<Integer>();
        bays = new ArrayList();
    }

    @PostConstruct
    private void construct() {
        initial();
        vesselCode = null;
    }

    public void handleNewPpkb(ActionEvent event) {
        planningVessels = planningVesselFacadeRemote.findPlanningVesselsByActivity();
    }

    public void initial() {
        baplieDischarge = new BaplieDischarge();
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
    public List<BaplieDischarge> getBaplieDischarges() {
        return baplieDischarges;
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
    public BaplieDischarge getBaplieDischarge() {
        return baplieDischarge;
    }

    /**
     * @param baplieDischarge the baplieDischarge to set
     */
    public void setBaplieDischarge(BaplieDischarge baplieDischarge) {
        this.baplieDischarge = baplieDischarge;
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
        List<Object[]> sameContainers = baplieDischargeFacadeRemote.findTheSameContainer(baplieDischarge.getContNo());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        if (baplieDischarge.getContNo() == null || baplieDischarge.getContGross() == null) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.transaction.failed");
        } else if (baplieDischarge.getMasterContainerType().getContType() == 3) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.transaction.failed");
        } else if (opsi.equals("add") && !sameContainers.isEmpty()) {
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.validation.containerno_exist");
//        } else if (opsi.equals("add") && masterYardCoordinatFacadeRemote.findDuplicate(baplieDischarge.getContNo())) {
//            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.validation.containerno_exist");
        } else {
            if (baplieDischarge.getPlanningVessel() == null) {
                baplieDischarge.setPlanningVessel(vessel);
            }
            
            boolean dataComplete = true;
            int gross = baplieDischarge.getContGross();
            short size = baplieDischarge.getContSize();
           //FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", baplieDischarge.getContGross());
            
            //String a =
            baplieDischarge.setGrossClass(GrossConverter.convert(size, gross));
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
            baplieDischarge.setMasterPort(mp1);

            MasterPort mp2 = masterPortFacadeRemote.findMasterPortByName(selectedPort2);
            baplieDischarge.setMasterPort1(mp2);

            MasterPort pod = masterPortFacadeRemote.findMasterPortByName(selectedPort3);
            baplieDischarge.setPortOfDelivery(pod);
            
            if(dataComplete) {
                baplieDischarge.setDataComplete("TRUE");
            } else {
                baplieDischarge.setDataComplete("FALSE");
            }
                
            

            String implementedPortCode = masterSettingAppFacadeRemote.findImplementedPortCode();

            if (vessel.getStatus().equals("Servicing")) {
                if (baplieDischarge.getPortOfDelivery().getPortCode().equals(implementedPortCode)) {
                    MasterYardCoordinat targetLocation = masterYardCoordinatFacadeRemote.findByCoordinat(yardPlanPosition.getBlock().getBlock(), yardPlanPosition.getSlot(), yardPlanPosition.getRow(), yardPlanPosition.getTier());
                    MasterYardCoordinat targetLocation40ft = masterYardCoordinatFacadeRemote.findByCoordinat(yardPlanPosition.getBlock().getBlock(), yardPlanPosition.getSlot() + 1, yardPlanPosition.getRow(), yardPlanPosition.getTier());
                    MasterYardCoordinat targetLocation45ft = masterYardCoordinatFacadeRemote.findByCoordinat(yardPlanPosition.getBlock().getBlock(), yardPlanPosition.getSlot() + 2, yardPlanPosition.getRow(), yardPlanPosition.getTier());

                    if ((baplieDischarge.getContSize() == 20 && targetLocation != null && targetLocation.getStatus().equals("empty")) || 
                            (baplieDischarge.getContSize() == 40 && targetLocation != null && targetLocation.getStatus().equals("empty") && targetLocation40ft != null && targetLocation40ft.getStatus().equals("empty")) ||
                            (baplieDischarge.getContSize() == 45 && targetLocation != null && targetLocation.getStatus().equals("empty") && targetLocation40ft != null && targetLocation40ft.getStatus().equals("empty") && targetLocation45ft != null && targetLocation45ft.getStatus().equals("empty"))) {
                        PlanningContDischarge planningContDischarge = planningContDischargeFacadeRemote.findByContNoAndNoPpkb(vessel.getNoPpkb(), baplieDischarge.getContNo());

                        if (planningContDischarge == null) {
                            planningContDischarge = new PlanningContDischarge();
                        }

                        planningContDischarge.setMasterYard(yardPlanPosition.getBlock());
                        planningContDischarge.setMasterContainerType(baplieDischarge.getMasterContainerType());
                        planningContDischarge.setMasterCommodity(baplieDischarge.getMasterCommodity());
                        planningContDischarge.setPlanningVessel(vessel);
                        planningContDischarge.setContNo(baplieDischarge.getContNo());
                        planningContDischarge.setMlo(baplieDischarge.getMlo());
                        planningContDischarge.setContSize(baplieDischarge.getContSize());
                        planningContDischarge.setContStatus(baplieDischarge.getContStatus());
                        planningContDischarge.setContGross(baplieDischarge.getContGross());
                        planningContDischarge.setSealNo(baplieDischarge.getSealNo());
                        planningContDischarge.setDg(baplieDischarge.getDg());
                        planningContDischarge.setIsImport(baplieDischarge.getIsExportImport());
                        planningContDischarge.setDgLabel(baplieDischarge.getDgLabel());
                        planningContDischarge.setTwentyOneFeet(baplieDischarge.getTwentyOneFeet());
                        planningContDischarge.setOverSize(baplieDischarge.getOverSize());
                        planningContDischarge.setTradeType(baplieDischarge.getTradeType());
                        planningContDischarge.setLoadPort(baplieDischarge.getMasterPort().getPortCode());
                        planningContDischarge.setDischPort(baplieDischarge.getMasterPort1().getPortCode());
                        planningContDischarge.setVBay(baplieDischarge.getVBay());
                        planningContDischarge.setVRow(baplieDischarge.getVRow());
                        planningContDischarge.setVTier(baplieDischarge.getVTier());
                        planningContDischarge.setYSlot(yardPlanPosition.getSlot());
                        planningContDischarge.setYRow(yardPlanPosition.getRow());
                        planningContDischarge.setYTier(yardPlanPosition.getTier());
                        planningContDischarge.setGrossClass(baplieDischarge.getGrossClass());
                        planningContDischarge.setBlNo(baplieDischarge.getBlNo());

                        ServiceContDischarge serviceContDischarge = serviceContDischargeFacadeRemote.findByNoPpkbAndContNo(vessel.getNoPpkb(), baplieDischarge.getContNo());

                        if (serviceContDischarge == null) {
                            serviceContDischarge = new ServiceContDischarge();
                        }

                        serviceContDischarge.setContNo(planningContDischarge.getContNo());
                        serviceContDischarge.setMlo(planningContDischarge.getMlo());
                        serviceContDischarge.setServiceVessel(vessel.getServiceVessel());
                        serviceContDischarge.setMasterCommodity(planningContDischarge.getMasterCommodity());
                        serviceContDischarge.setContSize(planningContDischarge.getContSize());
                        serviceContDischarge.setMasterContainerType(planningContDischarge.getMasterContainerType());
                        serviceContDischarge.setContStatus(planningContDischarge.getContStatus());
                        serviceContDischarge.setContGross(planningContDischarge.getContGross());
                        serviceContDischarge.setSealNo(planningContDischarge.getSealNo());
                        serviceContDischarge.setMasterYard(planningContDischarge.getMasterYard());
                        serviceContDischarge.setTwentyOneFeet(planningContDischarge.getTwentyOneFeet());
                        serviceContDischarge.setDangerous(planningContDischarge.getDg());
                        serviceContDischarge.setDgLabel(planningContDischarge.getDgLabel());
                        serviceContDischarge.setOverSize(planningContDischarge.getOverSize());
                        serviceContDischarge.setVBay(planningContDischarge.getVBay());
                        serviceContDischarge.setVRow(planningContDischarge.getVRow());
                        serviceContDischarge.setVTier(planningContDischarge.getVTier());
                        serviceContDischarge.setYRow(planningContDischarge.getYRow());
                        serviceContDischarge.setYSlot(planningContDischarge.getYSlot());
                        serviceContDischarge.setYTier(planningContDischarge.getYTier());
                        serviceContDischarge.setStartPlacementDate(vessel.getServiceVessel().getStartWorkTime());
                        serviceContDischarge.setCrane("L");
                        serviceContDischarge.setPosition("01");
                        serviceContDischarge.setIsDelivery("FALSE");
                        serviceContDischarge.setIsBehandle("FALSE");
                        serviceContDischarge.setIsCfs("FALSE");
                        serviceContDischarge.setIsInspection("FALSE");
                        serviceContDischarge.setGrossClass(planningContDischarge.getGrossClass());
                        serviceContDischarge.setIsImport(planningContDischarge.getIsImport());
                        serviceContDischarge.setBlNo(planningContDischarge.getBlNo());
                        serviceContDischarge.setCancelLoading("FALSE");
                        serviceContDischarge.setDischPort(planningContDischarge.getDischPort());
                        serviceContDischarge.setLoadPort(planningContDischarge.getLoadPort());

                        targetLocation.setStatus("planning");
                        targetLocation.setContNo(serviceContDischarge.getContNo());
                        targetLocation.setMlo(serviceContDischarge.getMlo());
                        targetLocation.setContSize(serviceContDischarge.getContSize());
                        targetLocation.setContType(serviceContDischarge.getMasterContainerType().getContType().toString());
                        targetLocation.setContWeight(serviceContDischarge.getContGross().intValue());
                        targetLocation.setPod(null);
                        targetLocation.setNoPpkb(serviceContDischarge.getServiceVessel().getNoPpkb());
                        targetLocation.setGrossClass(serviceContDischarge.getGrossClass());
                        masterYardCoordinatFacadeRemote.edit(targetLocation);

                        if (serviceContDischarge.getContSize() == 40) {
                            targetLocation40ft.setStatus("planning");
                            targetLocation40ft.setContNo(serviceContDischarge.getContNo());
                            targetLocation40ft.setMlo(serviceContDischarge.getMlo());
                            targetLocation40ft.setContSize(serviceContDischarge.getContSize());
                            targetLocation40ft.setContType(serviceContDischarge.getMasterContainerType().getContType().toString());
                            targetLocation40ft.setContWeight(serviceContDischarge.getContGross().intValue());
                            targetLocation40ft.setPod(null);
                            targetLocation40ft.setNoPpkb(serviceContDischarge.getServiceVessel().getNoPpkb());
                            targetLocation40ft.setGrossClass(serviceContDischarge.getGrossClass());
                            masterYardCoordinatFacadeRemote.edit(targetLocation40ft);
                        }

                        if (serviceContDischarge.getContSize() == 45) {
                            targetLocation45ft.setStatus("planning");
                            targetLocation45ft.setContNo(serviceContDischarge.getContNo());
                            targetLocation45ft.setMlo(serviceContDischarge.getMlo());
                            targetLocation45ft.setContSize(serviceContDischarge.getContSize());
                            targetLocation45ft.setContType(serviceContDischarge.getMasterContainerType().getContType().toString());
                            targetLocation45ft.setContWeight(serviceContDischarge.getContGross().intValue());
                            targetLocation45ft.setPod(null);
                            targetLocation45ft.setNoPpkb(serviceContDischarge.getServiceVessel().getNoPpkb());
                            targetLocation45ft.setGrossClass(serviceContDischarge.getGrossClass());
                            masterYardCoordinatFacadeRemote.edit(targetLocation45ft);
                        }

                        baplieDischargeFacadeRemote.edit(baplieDischarge);
                        serviceContDischargeFacadeRemote.edit(serviceContDischarge);
                        planningContDischargeFacadeRemote.edit(planningContDischarge);
                        FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.save.succeeded");
                        isValid = true;
                    } else {
                        FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "application.validation.yard");
                    }
                } else if (!baplieDischarge.getPortOfDelivery().getPortCode().equals(implementedPortCode) && !baplieDischarge.getMasterPort1().getPortCode().equals(vessel.getPreserviceVessel().getMasterPort().getPortCode())) {
                    baplieDischargeFacadeRemote.edit(baplieDischarge);
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "Data berhasil di input sebagai transhipment, selanjutnya Anda harus melakukan Transhipment Planning", null);
                    isValid = true;
                } else {
                    FacesHelper.addFacesTextMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "Data gagal disimpan, untuk direct input Shifting harus menggunakan menu Shifting Planning", null);
                }
            } else {
                baplieDischargeFacadeRemote.edit(baplieDischarge);

                if (opsi.equals("add")) {
                    handleButtonAdd(event);
                }

                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "info", "application.save.succeeded");
            }

            baplieDischarges = baplieDischargeFacadeRemote.findByNoPpkb(noPPKB);
            baplieDischarges2 = findContainerStatus(noPPKB);

            if (opsi.equals("delete")){
                isValid = true;
                baplieDischarge = new BaplieDischarge();
                baplieDischarge.setMasterCommodity(new MasterCommodity());
                baplieDischarge.setMasterContainerType(new MasterContainerType());
                baplieDischarge.setMasterPort(new MasterPort());
                baplieDischarge.setMasterPort1(new MasterPort());
                baplieDischarge.setPortOfDelivery(new MasterPort());
            }
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", isValid);
    }
    
    private List findContainerStatus(String noPPKB)
    {
        List results = baplieDischargeFacadeRemote.findContainerStatus(noPPKB);
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
                baplieDischargeFacadeRemote.remove(baplieDischarge);
            } else {
                baplieDischarges = baplieDischargeFacadeRemote.findByNoPpkb(noPPKB);
                for (BaplieDischarge row: baplieDischarges) {
                    baplieDischargeFacadeRemote.remove(row);
                }

                List <PlanningContDischarge> planningContDischarges = planningContDischargeFacadeRemote.findByNoPpkb(noPPKB);
                for (PlanningContDischarge planningContDischarge: planningContDischarges) {
                    planningContDischargeFacadeRemote.remove(planningContDischarge);
                }
            }
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.succeeded");
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "application.delete.failed");
        }
        baplieDischarges = baplieDischargeFacadeRemote.findByNoPpkb(noPPKB);
        baplieDischarges2 = findContainerStatus(noPPKB);
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        
        Logger.getAnonymousLogger().info("Selected noPPKB => " + noPPKB);
        
        planningVessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(noPPKB);
        baplieDischarges = baplieDischargeFacadeRemote.findByNoPpkb(noPPKB);
        baplieDischarges2 = findContainerStatus(noPPKB);
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
        baplieDischarge.setDg("FALSE");

        if (baplieDischarge.getMasterCommodity() != null && baplieDischarge.getMasterCommodity().getMasterDangerousClass() != null) {
            baplieDischarge.setDg("TRUE");
        }
    }

    public void handleSelectMasterCustomers(ActionEvent event) {
        String custCode = (String) event.getComponent().getAttributes().get("custCode");
        MasterCustomer mlo = masterCustomerFacadeRemote.find(custCode);
        baplieDischarge.setMlo(mlo);
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
        baplieDischarge = baplieDischargeFacadeRemote.find(id);
        opsi = "delete";
        //untuk mengkondisikan pemilihan bay
//        Tarakan
        if (baplieDischarge.getVBay() == 0) {
            baplieDischarge.setVBay(Short.parseShort(bayChoices.get(0).toString()));
            changeBay(baplieDischarge.getVBay());
            changeRow(Short.parseShort(rowChoices.get(0).toString()));
        } else {
            changeBay(baplieDischarge.getVBay());
            changeRow(baplieDischarge.getVRow());
        }

//        masterPorts = masterPortFacadeRemote.findAll();
        selectedPort1 = baplieDischarge.getMasterPort().getName();
        selectedPort2 = baplieDischarge.getMasterPort1().getName();
        selectedPort3 = baplieDischarge.getPortOfDelivery().getName();

//        Tarakan
//        fixEmptyCyChoices();
    }

    private void fixEmptyCyChoices(){
        fixEmptyBayChoices();
        fixEmptyRowChoices();
        fixEmptyTierChoices();
    }

    private void fixEmptyBayChoices() {
        if ((bayChoices.isEmpty() || !bayChoices.contains(baplieDischarge.getVBay().intValue())) && opsi.equals("delete")) {

            bayChoices.add(baplieDischarge.getVBay().intValue());
        }
    }

    private void fixEmptyRowChoices() {
        if ((rowChoices.isEmpty() || !rowChoices.contains(baplieDischarge.getVRow().intValue())) && opsi.equals("delete")) {
            rowChoices.add(baplieDischarge.getVRow().intValue());
        }
    }

    private void fixEmptyTierChoices() {
        if ((tierChoices.isEmpty() || !tierChoices.contains(baplieDischarge.getVTier().intValue())) && opsi.equals("delete")) {
            tierChoices.add(baplieDischarge.getVTier().intValue());
        }
    }

    public void handleDeleteAll(ActionEvent event) {
        opsi = "deleteall";
    }

    public void handleButtonAdd(ActionEvent event) {
        String portCode = masterSettingAppFacadeRemote.findImplementedPortCode();
        MasterPort masterPortDefault = masterPortFacadeRemote.find(portCode), 
                loadPort = masterPortFacadeRemote.find("IDSUB"); // Surabaya Port
        selectedPort1 = loadPort.getName(); // Set load port default value to Surabaya Port
        selectedPort2 = masterPortDefault.getName();
        selectedPort3 = masterPortDefault.getName();

        baplieDischarge.setContNo(null);
        baplieDischarge.setId(null);
        baplieDischarge.setMasterPort1(masterPortDefault);
        baplieDischarge.setPortOfDelivery(masterPortDefault);
        baplieDischarge.setDataComplete("FALSE");
        baplieDischarge.setContGross(0);
        baplieDischarge.setSealNo("");
        baplieDischarge.setMasterCommodity(null);
        baplieDischarge.setBlNo("");
        baplieDischarge.setMlo(null);

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
        
        baplieDischarge.setVBay(Short.parseShort(bayChoices.get(0).toString()));
        changeBay(baplieDischarge.getVBay());

        if (baplieDischarge.getContSize() == null) {
            MasterContainerType mct = masterContainerTypeFacadeRemote.find(1);
            baplieDischarge.setMasterContainerType(mct);
            baplieDischarge.setContSize(mct.getFeetMark());
            baplieDischarge.setContStatus("FCL");
        }

        MasterYard masterYard = masterYardFacadeRemote.find("A");
        yardPlanPosition = new YardPosition(masterYard);
        yardPlanPosition.setSlot((short) 1);
        yardPlanPosition.setRow((short) 1);
        yardPlanPosition.setTier((short) 1);
    }

    public void handleButtonCopy(ActionEvent event) {
        List<BaplieDischarge> notCompletedBaplieDischarges = baplieDischargeFacadeRemote.findByPpkbNotCompleted(noPPKB);
        
        if (notCompletedBaplieDischarges.isEmpty()) {
            baplieDischargeFacadeRemote.finishBaplieDischarges(noPPKB);
            vessel = planningVesselFacadeRemote.find(vessel.getNoPpkb());
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
            yardAllocationTemp = yardAllocationTempFacadeRemote.find(new BigDecimal(generateEmpat.get(i)));
            yardAllocationTempFacadeRemote.remove(yardAllocationTemp);
        }
        planningContDischargeFacadeRemote.unFinishBayPlanDischargeByPPKB(noPPKB);
        planningShiftDischargeFacadeRemote.unFinishBayPlanDischargeByPPKB(noPPKB);
        planningTransDischargeFacadeRemote.unFinishBayPlanDischargeByPPKB(noPPKB);
        vessel.setBaplieDischarge("FALSE");
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
        baplieDischarge.setVBay(newBay);
        changeBay(newBay);
    }

    public void onChangeRow(ValueChangeEvent event) {
        Short newRow = (Short) event.getNewValue();
        baplieDischarge.setVRow(newRow);
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

        if (baplieDischarge.getVRow() == null) {
            try {
                baplieDischarge.setVRow(rowChoices.get(0).shortValue());
            } catch (RuntimeException re) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            }            
        }

        changeRow(baplieDischarge.getVRow());
        fixEmptyRowChoices();
    }

    public void changeRow(Short newRow) {
		
        Logger.getAnonymousLogger().info("Starting at ");
        Integer startTier = 0;
        Integer totalTier = 0;
        Integer row = newRow.intValue();
        tierChoices.clear();
        if (rowUps.contains(row) && rowBottoms.contains(row)) {
            List<Object[]> loopTierBottom = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", baplieDischarge.getVBay().intValue(), row);
            if (!loopTierBottom.isEmpty()) {
                startTier = ((Integer) loopTierBottom.get(0)[0]).intValue();
                totalTier = ((Integer) loopTierBottom.get(0)[1]).intValue();
            }
            Integer entryTier = startTier;
            for (Integer j = 0; j < totalTier; j++) {
                tierChoices.add(entryTier);
                entryTier += 2;
            }
            List<Object[]> loopTierUp = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", baplieDischarge.getVBay().intValue(), row);
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
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "bottom", baplieDischarge.getVBay().intValue(), row);
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
            List<Object[]> loopTier = masterProfileDetailFacadeRemote.findTierByRow(vesselCode, "up", baplieDischarge.getVBay().intValue(), row);
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

        if (baplieDischarge.getVTier() == null) {
            baplieDischarge.setVTier(tierChoices.get(0).shortValue());
        }
        
        fixEmptyTierChoices();
    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        MasterContainerType mct = masterContainerTypeFacadeRemote.find(newItem);
        baplieDischarge.setMasterContainerType(mct);
        baplieDischarge.setContSize(mct.getFeetMark());
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

    public List<BaplieDischarge> getBaplieDischarges2() {
        return baplieDischarges2;
    }

    public void setBaplieDischarges2(List<BaplieDischarge> baplieDischarges2) {
        this.baplieDischarges2 = baplieDischarges2;
    }
    
   
}
