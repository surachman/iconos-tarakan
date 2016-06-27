/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.ejb.facade.remote.MasterActivityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterGrossClassFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifContFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingAllocationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.ReceivingServiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterGrossClass;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import com.pelindo.ebtos.util.GrossConverter;
import com.pelindo.ebtos.ejb.facade.remote.MasterTarifHistoryFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "entryReceivingServiceBean")
@ViewScoped
public class EntryReceivingServiceBean implements Serializable {

    @EJB
    private MasterTarifHistoryFacadeRemote masterTarifHistoryFacade;
    @EJB
    private RegistrationFacadeRemote registrationFacadeRemote;
    @EJB
    private PlanningReceivingAllocationFacadeRemote planningReceivingAllocationFacadeRemote;
    @EJB
    private PerhitunganLiftServiceFacadeRemote perhitunganLiftServiceFacadeRemote;
    @EJB
    private MasterActivityFacadeRemote masterActivityFacadeRemote;
    @EJB
    private MasterTarifContFacadeRemote masterTarifContFacadeRemote;
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;
    @EJB
    private MasterGrossClassFacadeRemote masterGrossClassFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;
    @EJB
    private ReceivingServiceFacadeRemote receivingServiceFacadeRemote;
    @EJB
    private PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;

    private Registration registration;
    private MasterContainerType masterContainerType;
    private MasterCommodity masterCommodity;
    private PlanningContReceiving planningContReceiving;
    private PlanningContReceiving planningCont;
    private Object[] registrationObjek;
    private ReceivingService receivingService;
    private List<ReceivingService> receivingServices;
    private List<Object[]> receivingServicesList;
    private List<Object[]> registrationListObjek;
    private List<MasterGrossClass> masterGrossClasses;
    private List<MasterContainerType> masterContainerTypes;
    private List<MasterPort> masterPorts;
    private List<MasterCommodity> masterCommoditys;
    private PlanningReceivingAllocation planningReceivingAllocation;
    private Object[] planningReceivingAllocationObjek;
    private Object[] planReceivingAllocation;
    private MasterYard masterYard;
    private MasterPort masterPort;
    private MasterPort masterPort2;
    private String no_reg;
    private List<Object[]> masterCommodityList;
    private List<Object[]> registrationList;
    private List<Object[]> planningContReceivingListGenerate;
    private Boolean visibleGenerate;
    private PlanningContReceiving[] selectedCars;
    private String ppkb;
    private String actLift = null;
    private PerhitunganLiftService perhitunganLiftService;
    private MasterActivity masterActivity;
    private PlanningVessel planningVessel;
    private String currency = null;
    private Integer idPlan = null;
    private Integer idPlanDel = null;
    private String blNo;

    /** Creates a new instance of EntryReceivingServiceBean */
    public EntryReceivingServiceBean() {}

    @PostConstruct
    private void construct() {
        perhitunganLiftService = new PerhitunganLiftService();
        receivingService = new ReceivingService();
        receivingService.setMasterContainerType(new MasterContainerType());
        receivingService.setMasterPort(new MasterPort());
        receivingService.setMasterCommodity(new MasterCommodity());
        receivingService.setMasterPort1(new MasterPort());
        registrationObjek = new Object[1];
        planReceivingAllocation = new Object[1];
        planningContReceiving = new PlanningContReceiving();
        planningReceivingAllocation = new PlanningReceivingAllocation();
        masterYard = new MasterYard();
        receivingService.setMasterContainerType(new MasterContainerType());
        receivingService.setMasterPort(new MasterPort());
        receivingService.setMasterPort1(new MasterPort());
        receivingService.setMasterCommodity(new MasterCommodity());
        masterContainerType = new MasterContainerType();
        masterCommodity = new MasterCommodity();
        masterPort = new MasterPort();
        masterPort2 = new MasterPort();
        visibleGenerate = true;
        planningCont = new PlanningContReceiving();
        planningVessel = new PlanningVessel();
        this.idPlan = null;
        this.idPlanDel = null;
        this.blNo=null;


        masterGrossClasses = masterGrossClassFacadeRemote.findAll();
        masterContainerTypes = masterContainerTypeFacadeRemote.findAll();
        masterPorts = masterPortFacadeRemote.findAll();

        masterCommodityList = masterCommodityFacadeRemote.findMasterCommoditys();
        registrationList = registrationFacadeRemote.findRegistrationList();
    }

    public void clearSave() {
        perhitunganLiftService = new PerhitunganLiftService();
        receivingService = new ReceivingService();
        receivingService.setMasterContainerType(new MasterContainerType());
        receivingService.setMasterPort(new MasterPort());
        receivingService.setMasterCommodity(new MasterCommodity());
        receivingService.setMasterPort1(new MasterPort());
        planningContReceiving = new PlanningContReceiving();
        planningReceivingAllocation = new PlanningReceivingAllocation();
        masterYard = new MasterYard();
        planningCont = new PlanningContReceiving();
        this.idPlan = null;
    }

    public void handleAdd() {
        receivingService = new ReceivingService();
        receivingService.setMasterContainerType(new MasterContainerType());
        receivingService.setMasterPort(new MasterPort());
        receivingService.setMasterCommodity(new MasterCommodity());
        receivingService.setMasterPort1(new MasterPort());
    }

    public void handleSelectCommodity(ActionEvent event) {
        String idHT = (String) event.getComponent().getAttributes().get("idOperatorCrane");
        receivingService.setMasterCommodity(masterCommodityFacadeRemote.find(idHT));
    }

    public void handleOnSelect(ActionEvent event) {
        setNo_reg((String) event.getComponent().getAttributes().get("no_reg"));
        setRegistrationObjek(registrationFacadeRemote.findRegistrationByNoReg(getNo_reg()));
        receivingServicesList = receivingServiceFacadeRemote.findReceivingServiceByNoReg(getNo_reg());
        setPpkb((String) getRegistrationObjek()[1]);
        planningContReceivingListGenerate = planningContReceivingFacadeRemote.findPlanningContReceivingIsGenerateTrue(getPpkb());
        if (planningContReceivingListGenerate.size() < 1) {
            visibleGenerate = true;
        } else {
            visibleGenerate = false;
        }
    }

    public boolean getVisible() {
        if (getRegistrationObjek()[0] == null) {
            return true;
        }
        return false;
    }

    public void handleOnAdd(ActionEvent event) {
        this.clearSave();
    }

    public String getGenerateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String tgl = dateFormat.format(Calendar.getInstance().getTime());
        String tempCode;
        String temp = receivingServiceFacadeRemote.findReceivingServiceByGenerate(tgl.substring(2));
        if (temp == null) {
            tempCode = "00001";
        } else {
            tempCode = String.valueOf(Integer.valueOf(temp) + 1);
        }
        String comCod = "";
        switch (tempCode.length()) {
            case 1:
                tempCode = "0000" + tempCode;
                break;
            case 2:
                tempCode = "000" + tempCode;
                break;
            case 3:
                tempCode = "00" + tempCode;
                break;
            case 4:
                tempCode = "0" + tempCode;
                break;
        }
        comCod = "01" + tgl + tempCode;
        return comCod;
    }

    public void submit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            receivingServiceFacadeRemote.edit(receivingService);
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            receivingServicesList = receivingServiceFacadeRemote.findReceivingServiceByNoReg(getNo_reg());
            this.clearSave();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
            ex.printStackTrace();
        }
    }

    public void handleSelectNoCont(ActionEvent event) {
        Integer cont_no = (Integer) event.getComponent().getAttributes().get("cont_no");
        planningContReceiving = planningContReceivingFacadeRemote.find(cont_no);
        receivingService.setContNo(planningContReceiving.getContNo());
        receivingService.setMlo(planningContReceiving.getMlo());
        receivingService.setContGross(planningContReceiving.getContGross());
        receivingService.setContSize(planningContReceiving.getContSize());
        receivingService.setContStatus(planningContReceiving.getContStatus());
        receivingService.setDg(planningContReceiving.getDg());
        receivingService.setDgLabel(planningContReceiving.getDgLabel());
        receivingService.setMasterCommodity(planningContReceiving.getMasterCommodity());
        receivingService.setMasterContainerType(planningContReceiving.getMasterContainerType());
        receivingService.setOverSize(planningContReceiving.getOverSize());
        receivingService.setPlanningVessel(planningContReceiving.getPlanningVessel());
        receivingService.setSealNo(planningContReceiving.getSealNo());
        receivingService.setBlNo(planningContReceiving.getBlNo());
        masterPort.setPortCode(planningContReceiving.getLoadPort());
        receivingService.setMasterPort(masterPort);
        masterPort2.setPortCode(planningContReceiving.getDischPort());
        receivingService.setMasterPort1(masterPort2);
    }

    public void handleActLift() {
        if (receivingService.getOverSize().equals("FALSE")) {
            if (receivingService.getContSize() == 20) {
                if (receivingService.getContStatus().equals("FCL") || receivingService.getContStatus().equals("LCL")) {
                    actLift = "B001";
                } else if (receivingService.getContStatus().equals("MTY")) {
                    actLift = "B002";
                }
            } else if (receivingService.getContSize() == 40) {
                if (receivingService.getContStatus().equals("FCL") || receivingService.getContStatus().equals("LCL")) {
                    actLift = "B003";
                } else if (receivingService.getContStatus().equals("MTY")) {
                    actLift = "B004";
                }
            }
        } else {
            if (receivingService.getContSize() == 20) {
                actLift = "B005";
            } else if (receivingService.getContSize() == 40) {
                actLift = "B006";
            }
        }
    }

    public void saveBaplie(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        if (receivingService.getContNo() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            if (receivingService.getRegistration() == null) {
                Registration rg = registrationFacadeRemote.find(getRegistrationObjek()[0]);
                receivingService.setRegistration(rg);
                receivingService.setPlanningVessel(planningVesselFacadeRemote.find(getRegistrationObjek()[1]));
            }
            receivingService.setJobSlip(getGenerateCode());
            planningVessel = planningVesselFacadeRemote.find(ppkb);
            receivingService.setValidDate(planningVessel.getCloseRec());
            receivingService.setIsGenerate("TRUE");
            receivingServiceFacadeRemote.edit(receivingService);

            String grossClas;
            grossClas = GrossConverter.convert(receivingService.getContSize(), receivingService.getContGross());
            Object[] temp = planningReceivingAllocationFacadeRemote.findPlanningReceivingByQueryCopy(getPpkb(), receivingService.getContSize(), receivingService.getMasterContainerType().getContType(), receivingService.getContStatus(), receivingService.getOverSize(), receivingService.getDg(), receivingService.getIsExport(), grossClas, receivingService.getMasterPort1().getPortCode());
            if (temp != null) {
                planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(temp[0]);
                int counter = planningReceivingAllocation.getCountBaplie2() - 1;
                planningReceivingAllocation.setCountBaplie2(counter);

                planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
            }

            handleActLift();
            perhitunganLiftService.setContNo(receivingService.getContNo());
            perhitunganLiftService.setMlo(receivingService.getMlo());
            perhitunganLiftService.setNoPpkb(receivingService.getPlanningVessel().getNoPpkb());
            perhitunganLiftService.setRegistration(receivingService.getRegistration());
            perhitunganLiftService.setMasterActivity(masterActivityFacadeRemote.find(getActLift()));
            // set amount berdasarkan tipe pelayaran
            BigDecimal cLiftD = BigDecimal.ZERO;
            BigDecimal cLiftI = BigDecimal.ZERO;

            for (Object[] o1 : masterTarifContFacadeRemote.findAmountByActivity(actLift)) {
                if (o1[1].equals("IDR")) {
                    cLiftD = ((BigDecimal) (o1[0]));
                } else if (o1[1].equals("USD")) {
                    cLiftI = ((BigDecimal) (o1[0]));
                }
            }
            BigDecimal cLift = BigDecimal.ZERO;
            if (receivingService.getPlanningVessel().getTipePelayaran().equals("d")) {
                cLift = cLiftD;
                setCurrency("IDR");
            } else if (receivingService.getPlanningVessel().getTipePelayaran().equals("i")) {
                cLift = cLiftI;
                setCurrency("USD");
            }

            if (receivingService.getDg().equals("TRUE") && receivingService.getDgLabel().equals("TRUE")) {
                perhitunganLiftService.setCharge(cLift.multiply(BigDecimal.valueOf(2)));
            } else if (receivingService.getDg().equals("TRUE")&& receivingService.getDgLabel().equals("FALSE")) {
                perhitunganLiftService.setCharge(cLift.multiply(BigDecimal.valueOf(3)));
            } else {
                perhitunganLiftService.setCharge(cLift);
            }
            perhitunganLiftService.setCurrCode(currency);
            perhitunganLiftServiceFacadeRemote.create(perhitunganLiftService);

            planningCont = planningContReceivingFacadeRemote.find(planningContReceiving.getId());
            planningCont.setIsGenerate("FALSE");
            planningCont.setPosition("01");
            planningContReceivingFacadeRemote.edit(planningCont);

            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            receivingServicesList = receivingServiceFacadeRemote.findReceivingServiceByNoReg(getNo_reg());
            planningContReceivingListGenerate = planningContReceivingFacadeRemote.findPlanningContReceivingIsGenerateTrue(getPpkb());
        }
        context.addCallbackParam("loggedIn", loggedIn);
        receivingService = new ReceivingService();
    }

    public void careateEntryGenerate(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        //boolean loggedIn = false;

        //String ppkb = (String) getRegistrationObjek()[1];
        //planningContReceivingListGenerate = planningContReceivingFacadeRemote.findPlanningContReceivingIsGenerateTrue(getPpkb());

        if (planningContReceivingListGenerate.size() < 1) {
            //loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            //loggedIn = true;
            if (receivingService.getRegistration() == null) {
                Registration rg = registrationFacadeRemote.find(getRegistrationObjek()[0]);
                receivingService.setRegistration(rg);
                receivingService.setPlanningVessel(planningVesselFacadeRemote.find(getRegistrationObjek()[1]));
            }

            for (Object[] src : planningContReceivingListGenerate) {
                receivingService.setJobSlip(getGenerateCode());
                Integer contType = (Integer) src[2];
                masterContainerType.setContType(contType);
                receivingService.setMasterContainerType(masterContainerType);
                String commodityCode = (String) src[1];
                masterCommodity.setCommodityCode(commodityCode);
                receivingService.setMasterCommodity(masterCommodity);
                String port1 = (String) src[12];
                masterPort.setPortCode(port1);
                receivingService.setMasterPort(masterPort);
                String port2 = (String) src[13];
                masterPort2.setPortCode(port2);
                receivingService.setMasterPort1(masterPort2);
                String contNo = (String) src[4];
                receivingService.setContNo(contNo);
                receivingService.setContSize(Short.valueOf(src[5].toString()));
                String contStatus = (String) src[6];
                receivingService.setContStatus(contStatus);
                receivingService.setContGross((Integer) src[7]);
                String sealNo = (String) src[8];
                receivingService.setSealNo(sealNo);
                MasterCustomer mlo = masterCustomerFacadeRemote.find(src[16]);
                receivingService.setMlo(mlo);
                
                if(Boolean.parseBoolean((String) src[14].toString()) == true) {
                    receivingService.setDg("TRUE");
                } else {
                    receivingService.setDg("FALSE");
                }
                
                if (Boolean.parseBoolean((String) src[10].toString()) == true) {
                    receivingService.setDgLabel("TRUE");
                } else {
                    receivingService.setDgLabel("FALSE");
                }
                
                if (Boolean.parseBoolean((String) src[9].toString()) == true) {
                    receivingService.setOverSize("TRUE");
                } else {
                    receivingService.setOverSize("FALSE");
                }
                
                receivingService.setBlNo((String) src[16]);
                planningCont = planningContReceivingFacadeRemote.find(src[0]);
                planningCont.setIsGenerate("FALSE");

                planningVessel = planningVesselFacadeRemote.find(ppkb);
                receivingService.setValidDate(planningVessel.getCloseRec());
                receivingService.setIsGenerate("TRUE");
                receivingServiceFacadeRemote.edit(receivingService);
                planningCont.setPosition("01");
                planningContReceivingFacadeRemote.edit(planningCont);

                String grossCla;
                grossCla = GrossConverter.convert(receivingService.getContSize(), receivingService.getContGross());
                Object[] temp = planningReceivingAllocationFacadeRemote.findPlanningReceivingByQueryCopy(getPpkb(), receivingService.getContSize(), receivingService.getMasterContainerType().getContType(), receivingService.getContStatus(), receivingService.getOverSize(), receivingService.getDg(), receivingService.getIsExport(), grossCla, receivingService.getMasterPort1().getPortCode());
                if (temp != null) {
                    planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(temp[0]);
                    int counter = planningReceivingAllocation.getCountBaplie2() - 1;
                    planningReceivingAllocation.setCountBaplie2(counter);

                    planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                }

                handleActLift();

                perhitunganLiftService.setContNo(receivingService.getContNo());
                perhitunganLiftService.setMlo(receivingService.getMlo());
                perhitunganLiftService.setNoPpkb(receivingService.getPlanningVessel().getNoPpkb());
                perhitunganLiftService.setRegistration(receivingService.getRegistration());
                perhitunganLiftService.setMasterActivity(masterActivityFacadeRemote.find(getActLift()));
                // set amount berdasarkan tipe pelayaran
                BigDecimal cLiftD = BigDecimal.ZERO;
                BigDecimal cLiftI = BigDecimal.ZERO;
                for (Object[] o1 : masterTarifContFacadeRemote.findAmountByActivity(actLift)) {
                    if (o1[1].equals("IDR")) {
                        cLiftD = ((BigDecimal) (o1[0]));
                    } else if (o1[1].equals("USD")) {
                        cLiftI = ((BigDecimal) (o1[0]));
                    }
                }
                BigDecimal cLift = BigDecimal.ZERO;
                if (receivingService.getPlanningVessel().getTipePelayaran().equals("d")) {
                    cLift = cLiftD;
                    setCurrency("IDR");
                } else if (receivingService.getPlanningVessel().getTipePelayaran().equals("i")) {
                    cLift = cLiftI;
                    setCurrency("USD");
                }

                if (receivingService.getDg().equals("TRUE")&& receivingService.getDgLabel().equals("TRUE")) {
                    perhitunganLiftService.setCharge(cLift.multiply(BigDecimal.valueOf(2)));
                } else if (receivingService.getDg().equals("TRUE") && receivingService.getDgLabel().equals("FALSE")) {
                    perhitunganLiftService.setCharge(cLift.multiply(BigDecimal.valueOf(3)));
                } else {
                    perhitunganLiftService.setCharge(cLift);
                }
                perhitunganLiftService.setCurrCode(currency);
                perhitunganLiftServiceFacadeRemote.create(perhitunganLiftService);

                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            }
            visibleGenerate = true;
            receivingServicesList = receivingServiceFacadeRemote.findReceivingServiceByNoReg(getNo_reg());
            planningContReceivingListGenerate = planningContReceivingFacadeRemote.findPlanningContReceivingIsGenerateTrue(getPpkb());
        }

        //context.addCallbackParam("loggedIn", loggedIn);
        this.clearSave();
    }


    public void saveEdit(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
        //short gross = 0;


        if (receivingService.getContGross() == null && receivingService.getSealNo() == null && receivingService.getContNo() == null) {
            loggedIn = false;
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.save.failed");
        } else {
            loggedIn = true;
            if (receivingService.getRegistration() == null) {
                Registration rg = registrationFacadeRemote.find(getRegistrationObjek()[0]);
                receivingService.setRegistration(rg);
                receivingService.setPlanningVessel(planningVesselFacadeRemote.find(getRegistrationObjek()[1]));
            }
            int gross = receivingService.getContGross();
            short size = receivingService.getContSize();

            String grossClass;
            grossClass = GrossConverter.convert(size, gross);

            int con_type = receivingService.getMasterContainerType().getContType();
            //String no_ppkb = (String) (getRegistrationObjek()[1]);
            int cont_size = receivingService.getContSize();

            Object[] temp = planningReceivingAllocationFacadeRemote.findPlanningReceivingByQueryCopy(getPpkb(), cont_size, con_type, receivingService.getContStatus(), receivingService.getOverSize(), receivingService.getDg(), receivingService.getIsExport(), grossClass, receivingService.getMasterPort1().getPortCode());

            Object[] tempCek = planningContReceivingFacadeRemote.findPlanningContReceivingByCekExist(receivingService.getContNo(), getPpkb());

            if (temp != null) {
                planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(temp[0]);


                if (planningReceivingAllocation.getTotalBooking2() >= planningReceivingAllocation.getTotalBooking()) {
                    loggedIn = false;
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.alokasi_booking");

                } else if (tempCek != null) {
                    loggedIn = false;
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.data_exist");
                } else {
                    //kondisi ada

                    loggedIn = true;
                    planningContReceiving.setContGross(receivingService.getContGross());
                    planningContReceiving.setMasterContainerType(receivingService.getMasterContainerType());
                    planningContReceiving.setContSize(receivingService.getContSize());
                    planningContReceiving.setContStatus(receivingService.getContStatus());
                    planningContReceiving.setOverSize(receivingService.getOverSize());
                    planningContReceiving.setDg(receivingService.getDg());
                    planningContReceiving.setDgLabel(receivingService.getDgLabel());
                    planningContReceiving.setContNo(receivingService.getContNo());
                    planningContReceiving.setMlo(receivingService.getMlo());
                    planningContReceiving.setDischPort(receivingService.getMasterPort().getPortCode());
                    planningContReceiving.setLoadPort(receivingService.getMasterPort1().getPortCode());
                    planningContReceiving.setMasterCommodity(receivingService.getMasterCommodity());
                    planningContReceiving.setSealNo(receivingService.getSealNo());
                    planningContReceiving.setPlanningVessel(planningVesselFacadeRemote.find(getRegistrationObjek()[1]));
                    planningContReceiving.setIdConstrainPlanning((Integer) temp[0]);
                    planningContReceiving.setIsGenerate("FALSE");
                    planningContReceiving.setGrossClass(grossClass);
                    planningContReceiving.setPosition("01");
                     planningContReceiving.setIsCompleted("TRUE");
                     planningContReceiving.setBlNo(receivingService.getBlNo());
                    planningContReceivingFacadeRemote.edit(planningContReceiving);
                    if (receivingService.getJobSlip() == null) {
                        receivingService.setJobSlip(getGenerateCode());
                    }
                    planningVessel = planningVesselFacadeRemote.find(ppkb);
                    receivingService.setValidDate(planningVessel.getCloseRec());
                    receivingService.setIsGenerate("FALSE");
                    receivingServiceFacadeRemote.create(receivingService);

                    //update total di tabel receiving yard alocation

//                    short counter = 0;
//                    counter = (short) (planningReceivingAllocation.getSisa() + 1);
//                    planningReceivingAllocation.setSisa(counter);
                    planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() + 1);

                    planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);

                    handleActLift();
                    perhitunganLiftService.setContNo(receivingService.getContNo());
                    perhitunganLiftService.setMlo(receivingService.getMlo());
                    perhitunganLiftService.setNoPpkb(receivingService.getPlanningVessel().getNoPpkb());
                    perhitunganLiftService.setRegistration(receivingService.getRegistration());
                    perhitunganLiftService.setMasterActivity(masterActivityFacadeRemote.find(getActLift()));
                    // set amount berdasarkan tipe pelayaran
                    BigDecimal cLiftD = BigDecimal.ZERO;
                    BigDecimal cLiftI = BigDecimal.ZERO;

                    for (Object[] o1 : masterTarifContFacadeRemote.findAmountByActivity(actLift)) {
                        if (o1[1].equals("IDR")) {
                            cLiftD = ((BigDecimal) (o1[0]));
                        } else if (o1[1].equals("USD")) {
                            cLiftI = ((BigDecimal) (o1[0]));
                        }
                    }
                    BigDecimal cLift = BigDecimal.ZERO;
                    if (receivingService.getPlanningVessel().getTipePelayaran().equals("d")) {
                        cLift = cLiftD;
                        setCurrency("IDR");
                    } else if (receivingService.getPlanningVessel().getTipePelayaran().equals("i")) {
                        cLift = cLiftI;
                        setCurrency("USD");
                    }

                    if (receivingService.getDg().equals("TRUE") && receivingService.getDgLabel().equals("TRUE")) {
                        perhitunganLiftService.setCharge(cLift.multiply(BigDecimal.valueOf(2)));
                    } else if (receivingService.getDg().equals("TRUE") && receivingService.getDgLabel().equals("FALSE")) {
                        perhitunganLiftService.setCharge(cLift.multiply(BigDecimal.valueOf(3)));
                    } else {
                        perhitunganLiftService.setCharge(cLift);
                    }
                    perhitunganLiftService.setCurrCode(currency);
                    perhitunganLiftServiceFacadeRemote.create(perhitunganLiftService);
                    receivingServicesList = receivingServiceFacadeRemote.findReceivingServiceByNoReg(getNo_reg());
                    registrationListObjek = receivingServiceFacadeRemote.findReceivingServiceByNoReg((String) getRegistrationObjek()[0]);

                    this.clearSave();
                    FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
                }
            } else {
                //kondisi tidak ada
                loggedIn = false;
                FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.validation.record_beda");
            }
        }
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        masterContainerType = masterContainerTypeFacadeRemote.find(newItem);
        receivingService.setContSize(masterContainerType.getFeetMark());
    }
    /*
    public void onChangeConstraintType(ValueChangeEvent event) {
    int newItem = (Integer) event.getNewValue();
    MasterContainerType mct = masterContainerTypeFacadeRemote.find(newItem);
    planningReceivingAllocation.setContSize(mct.getFeetMark());
    }
     */

    public void handleEditDeleteButton(ActionEvent event) {
        String id = (String) event.getComponent().getAttributes().get("job_slip");
        receivingService = receivingServiceFacadeRemote.find(id);
        this.idPlan = planningContReceivingFacadeRemote.findPlanningCont(receivingService.getContNo(), receivingService.getPlanningVessel().getNoPpkb(), "TRUE");
        this.idPlanDel = planningContReceivingFacadeRemote.findPlanningCont(receivingService.getContNo(), receivingService.getPlanningVessel().getNoPpkb(), "FALSE");

    }

    public void delete(ActionEvent event) {
        try {
            if (idPlan != null) {
                planningContReceiving = planningContReceivingFacadeRemote.find(idPlan);
                planningContReceiving.setIsGenerate("TRUE");
                planningContReceiving.setPosition(null);
                planningContReceivingFacadeRemote.edit(planningContReceiving);
                String gross;
                gross = GrossConverter.convert(planningContReceiving.getContSize(), planningContReceiving.getContGross());
                Object[] temp = planningReceivingAllocationFacadeRemote.findPlanningReceivingByQueryCopy(getPpkb(), planningContReceiving.getContSize(), planningContReceiving.getMasterContainerType().getContType(), planningContReceiving.getContStatus(), planningContReceiving.getOverSize(), planningContReceiving.getDg(), planningContReceiving.getIsExport(), gross, planningContReceiving.getDischPort());
                if (temp != null) {
                    planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(temp[0]);
                    short counter = 0;
                    counter = (short) (planningReceivingAllocation.getSisa() - 1);
                    planningReceivingAllocation.setSisa(counter);
                    planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                }
            } else if (idPlanDel != null) {
                planningContReceiving = planningContReceivingFacadeRemote.find(idPlanDel);
                planningContReceivingFacadeRemote.remove(planningContReceiving);

                String gross;
                gross = GrossConverter.convert(receivingService.getContSize(), receivingService.getContGross());
                Object[] temp = planningReceivingAllocationFacadeRemote.findPlanningReceivingByQueryCopy(getPpkb(), receivingService.getContSize(), receivingService.getMasterContainerType().getContType(), receivingService.getContStatus(), receivingService.getOverSize(), receivingService.getDg(), receivingService.getIsExport(), gross,  receivingService.getMasterPort1().getPortCode());
                if (temp != null) {
                    planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(temp[0]);
//                    int counter = 0;
//                    counter = planningReceivingAllocation.getSisa() - 1;
//                    planningReceivingAllocation.setTotalBooking2(counter);
//                    short counter2 =0;
//                    counter2=(short)(planningReceivingAllocation.getSisa()-1);
//                    planningReceivingAllocation.setSisa(counter2);
                    planningReceivingAllocation.setTotalBooking2(planningReceivingAllocation.getTotalBooking2() - 1);
                    planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                }
            }
            receivingServiceFacadeRemote.remove(receivingService);
            receivingServicesList = receivingServiceFacadeRemote.findReceivingServiceByNoReg(getNo_reg());
            registrationListObjek = receivingServiceFacadeRemote.findReceivingServiceByNoReg((String) getRegistrationObjek()[0]);
            planningContReceivingListGenerate = planningContReceivingFacadeRemote.findPlanningContReceivingIsGenerateTrue(getPpkb());
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Info", "application.delete.succeeded");
            this.clearSave();
        } catch (EJBException ex) {
            FacesHelper.addFacesMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Info", "application.delete.failed");
            ex.printStackTrace();
        }
    }

    //function multiple select

     public void ambilContNo(ActionEvent event) {
        setBlNo((String) event.getComponent().getAttributes().get("blNo"));
        System.out.println("bl" + getBlNo());
    }

    public List<Object[]> getRegistrationList() {
        return registrationList;
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

    /**
     * @return the receivingServices
     */
    public List<ReceivingService> getReceivingServices() {
        return receivingServices;
    }

    /**
     * @param receivingServices the receivingServices to set
     */
    public void setReceivingServices(List<ReceivingService> receivingServices) {
        this.receivingServices = receivingServices;
    }

    /**
     * @return the registrationObjek
     */
    public Object[] getRegistrationObjek() {
        return registrationObjek;
    }

    /**
     * @param registrationObjek the registrationObjek to set
     */
    public void setRegistrationObjek(Object[] registrationObjek) {
        this.registrationObjek = registrationObjek;
    }

    /**
     * @param registrationList the registrationList to set
     */
    public void setRegistrationList(List<Object[]> registrationList) {
        this.registrationList = registrationList;
    }

    /**
     * @return the receivingServicesList
     */
    public List<Object[]> getReceivingServicesList() {
        return receivingServicesList;
    }

    /**
     * @param receivingServicesList the receivingServicesList to set
     */
    public void setReceivingServicesList(List<Object[]> receivingServicesList) {
        this.receivingServicesList = receivingServicesList;
    }

    /**
     * @return the masterContainerTypes
     */
    public List<MasterContainerType> getMasterContainerTypes() {
        return masterContainerTypes;
    }

    /**
     * @param masterContainerTypes the masterContainerTypes to set
     */
    public void setMasterContainerTypes(List<MasterContainerType> masterContainerTypes) {
        this.masterContainerTypes = masterContainerTypes;
    }

    /**
     * @return the masterGrossClasses
     */
    public List<MasterGrossClass> getMasterGrossClasses() {
        return masterGrossClasses;
    }

    /**
     * @param masterGrossClasses the masterGrossClasses to set
     */
    public void setMasterGrossClasses(List<MasterGrossClass> masterGrossClasses) {
        this.masterGrossClasses = masterGrossClasses;
    }

    /**
     * @return the masterPorts
     */
    public List<MasterPort> getMasterPorts() {
        return masterPorts;
    }

    /**
     * @param masterPorts the masterPorts to set
     */
    public void setMasterPorts(List<MasterPort> masterPorts) {
        this.masterPorts = masterPorts;
    }

//    /**
//     * @return the masterCommoditys
//     */
//    public List<MasterCommodity> getMasterCommoditys() {
//        masterCommoditys = masterCommodityFacadeRemote.findAll();
//        return masterCommoditys;
//    }
//
//    /**
//     * @param masterCommoditys the masterCommoditys to set
//     */
//    public void setMasterCommoditys(List<MasterCommodity> masterCommoditys) {
//        this.masterCommoditys = masterCommoditys;
//    }
    /**
     * @return the planningContReceiving
     */
    public PlanningContReceiving getPlanningContReceiving() {
        return planningContReceiving;
    }

    /**
     * @param planningContReceiving the planningContReceiving to set
     */
    public void setPlanningContReceiving(PlanningContReceiving planningContReceiving) {
        this.planningContReceiving = planningContReceiving;
    }

    /**
     * @return the planningReceivingAllocation
     */
    public PlanningReceivingAllocation getPlanningReceivingAllocation() {
        return planningReceivingAllocation;
    }

    /**
     * @param planningReceivingAllocation the planningReceivingAllocation to set
     */
    public void setPlanningReceivingAllocation(PlanningReceivingAllocation planningReceivingAllocation) {
        this.planningReceivingAllocation = planningReceivingAllocation;
    }

    /**
     * @return the planningReceivingAllocationObjek
     */
    public Object[] getPlanningReceivingAllocationObjek() {
        return planningReceivingAllocationObjek;
    }

    /**
     * @param planningReceivingAllocationObjek the planningReceivingAllocationObjek to set
     */
    public void setPlanningReceivingAllocationObjek(Object[] planningReceivingAllocationObjek) {
        this.planningReceivingAllocationObjek = planningReceivingAllocationObjek;
    }

    /**
     * @return the planReceivingAllocation
     */
    public Object[] getPlanReceivingAllocation() {
        return planReceivingAllocation;
    }

    /**
     * @param planReceivingAllocation the planReceivingAllocation to set
     */
    public void setPlanReceivingAllocation(Object[] planReceivingAllocation) {
        this.planReceivingAllocation = planReceivingAllocation;
    }

    /**
     * @return the masterYard
     */
    public MasterYard getMasterYard() {
        return masterYard;
    }

    /**
     * @param masterYard the masterYard to set
     */
    public void setMasterYard(MasterYard masterYard) {
        this.masterYard = masterYard;
    }

    /**
     * @return the no_reg
     */
    public String getNo_reg() {
        return no_reg;
    }

    /**
     * @param no_reg the no_reg to set
     */
    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
    }

    /**
     * @return the masterCommodityList
     */
    public List<Object[]> getMasterCommodityList() {
        return masterCommodityList;
    }

    /**
     * @param masterCommodityList the masterCommodityList to set
     */
    public void setMasterCommodityList(List<Object[]> masterCommodityList) {
        this.masterCommodityList = masterCommodityList;
    }

    /**
     * @return the planningContReceivingListGenerate
     */
    public List<Object[]> getPlanningContReceivingListGenerate() {
        return planningContReceivingListGenerate;
    }

    /**
     * @param planningContReceivingListGenerate the planningContReceivingListGenerate to set
     */
    public void setPlanningContReceivingListGenerate(List<Object[]> planningContReceivingListGenerate) {
        this.planningContReceivingListGenerate = planningContReceivingListGenerate;
    }

    /**
     * @return the masterPort
     */
    public MasterPort getMasterPort() {
        return masterPort;
    }

    /**
     * @param masterPort the masterPort to set
     */
    public void setMasterPort(MasterPort masterPort) {
        this.masterPort = masterPort;
    }

    /**
     * @return the masterPort2
     */
    public MasterPort getMasterPort2() {
        return masterPort2;
    }

    /**
     * @param masterPort2 the masterPort2 to set
     */
    public void setMasterPort2(MasterPort masterPort2) {
        this.masterPort2 = masterPort2;
    }

    /**
     * @return the visibleGenerate
     */
    public Boolean getVisibleGenerate() {
        return visibleGenerate;
    }

    /**
     * @param visibleGenerate the visibleGenerate to set
     */
    public void setVisibleGenerate(Boolean visibleGenerate) {
        this.visibleGenerate = visibleGenerate;
    }

    /**
     * @return the ppkb
     */
    public String getPpkb() {
        return ppkb;
    }

    /**
     * @param ppkb the ppkb to set
     */
    public void setPpkb(String ppkb) {
        this.ppkb = ppkb;
    }

    /**
     * @return the planningCont
     */
    public PlanningContReceiving getPlanningCont() {
        return planningCont;
    }

    /**
     * @param planningCont the planningCont to set
     */
    public void setPlanningCont(PlanningContReceiving planningCont) {
        this.planningCont = planningCont;
    }

    /**
     * @return the selectedCars
     */
    public PlanningContReceiving[] getSelectedCars() {
        return selectedCars;
    }

    /**
     * @param selectedCars the selectedCars to set
     */
    public void setSelectedCars(PlanningContReceiving[] selectedCars) {
        this.selectedCars = selectedCars;
    }

    /**
     * @return the actLift
     */
    public String getActLift() {
        return actLift;
    }

    /**
     * @param actLift the actLift to set
     */
    public void setActLift(String actLift) {
        this.actLift = actLift;
    }

    /**
     * @return the perhitunganLiftService
     */
    public PerhitunganLiftService getPerhitunganLiftService() {
        return perhitunganLiftService;
    }

    /**
     * @param perhitunganLiftService the perhitunganLiftService to set
     */
    public void setPerhitunganLiftService(PerhitunganLiftService perhitunganLiftService) {
        this.perhitunganLiftService = perhitunganLiftService;
    }

    /**
     * @return the masterActivity
     */
    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    /**
     * @param masterActivity the masterActivity to set
     */
    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
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
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }

    public Integer getIdPlanDel() {
        return idPlanDel;
    }

    public void setIdPlanDel(Integer idPlanDel) {
        this.idPlanDel = idPlanDel;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

}
