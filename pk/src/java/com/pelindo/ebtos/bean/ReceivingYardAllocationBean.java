/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterContainerTypeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterGrossClassFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingAllocationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningReceivingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningContReceiving;
import com.pelindo.ebtos.model.db.PlanningReceiving;
import com.pelindo.ebtos.model.db.PlanningReceivingAllocation;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterGrossClass;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.qtasnim.jsf.FacesHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dycode-java
 */
@ManagedBean(name = "receivingYardAllocationBean")
@ViewScoped
public class ReceivingYardAllocationBean implements Serializable {

    @EJB
    private PlanningContReceivingFacadeRemote planningContReceivingFacadeRemote;
    @EJB
    private PlanningReceivingAllocationFacadeRemote planningReceivingAllocationFacadeRemote;
    @EJB
    private PlanningReceivingFacadeRemote planningReceivingFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private MasterContainerTypeFacadeRemote masterContainerTypeFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private MasterGrossClassFacadeRemote masterGrossClassFacadeRemote;
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;

    private List<Object[]> planningReceivings;
    private List<Object[]> vessels;
    private List<Object[]> yards;
    private List<Object[]> planningReceivingAllocations;
    private List<Object[]> blocks;
    private Object[] vessel;
    private Object[] receivingAllocation;
    private Map<Integer, Integer> slots;
    private Map<Integer, Integer> rows;
    private Map<Integer, Integer> tiers;
    private List<MasterGrossClass> masterGrossClasses;
    private List<MasterPort> masterPorts;
    private List<SelectItem> selectSlots;
    private List<SelectItem> selectTiers;
    private MasterCommodity masterCommodity;
    private PlanningContReceiving planningContReceiving;
    private PlanningReceivingAllocation planningReceivingAllocation;
    private PlanningReceiving planningReceiving;
    private PlanningVessel planningVessel;
    private MasterYardCoordinat masterYardCoordinat;
    private Integer selectedSlot;
    private Integer selectedTier;
    private Integer idPlanningReceivingAllocation;
    private Integer idLama = 0;
    private Integer idBaru = 0;
    private String noPPKB;
    private String selectedBlock;
    private String existOnly;
    private Long requiredReceivingPositions = (long) 0;
    private Long allocatedReceivingPositions = (long) 0;
    private Long onApplicationReceivings = (long) 0;
    private Long onYardReceivings = (long) 0;
    private int heightBird;
    private int widthBird;
    private int heightSide;
    private int widthSide;
    private int counterSlot, counterRow, counterTier;
    private boolean edit = Boolean.FALSE;
    private boolean deleteAll;
    
    private String selectedPort1;

    /** Creates a new instance of ReceivingYardAllocationBean */
    public ReceivingYardAllocationBean() {}

    @PostConstruct
    private void construct() {
        vessels = planningVesselFacadeRemote.findPlanningVesselsByActivityYardAlocation();
        yards = masterYardFacadeRemote.findMasterYards();
        blocks = masterYardFacadeRemote.findMasterYards();
        masterCommodity = new MasterCommodity();
        planningReceivingAllocation = new PlanningReceivingAllocation();

        //dialog monitoring
        selectSlots = new ArrayList();
        for (int i = 1; i <= ((Number) blocks.get(0)[2]).intValue(); i++) {
            selectSlots.add(new SelectItem(new Integer(i), String.valueOf(i)));
        }

        selectedSlot = new Integer(1);
        selectTiers = new ArrayList();

        for (int i = 1; i <= ((Number) blocks.get(0)[4]).intValue(); i++) {
            selectTiers.add(new SelectItem(new Integer(i), String.valueOf(i)));
        }

        selectedTier = new Integer(1);
        selectedBlock = blocks.get(0)[0].toString();
        existOnly = "f";

        Object[] firstYard = masterYardFacadeRemote.findMasterYardByBlock(selectedBlock);
        int slot = ((Number) firstYard[0]).intValue();
        int row = ((Number) firstYard[1]).intValue();
        int tier = ((Number) firstYard[2]).intValue();
        heightBird = (row * 47) + 100;
        widthBird = (slot * 118) + 100;
        heightSide = (tier * 167) + 100;
        widthSide = (row * 164) + 100;
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        String noPpkb = noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        resetReceivingAllocation(noPpkb);
    }

    private void resetReceivingAllocation(String noPpkb) {
        noPPKB = noPpkb;
        vessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(noPPKB);
        planningVessel = planningVesselFacadeRemote.find(noPPKB);
        //generate constraint
        List<Object[]> constraintList = planningContReceivingFacadeRemote.createGenerateByPPKB(noPPKB);
        setBlock();
        if (planningReceivingAllocations.isEmpty()) {
            for (int i = 0; i < constraintList.size(); i++) {
                //inisialisasi data
                planningReceivingAllocation = new PlanningReceivingAllocation();
                planningReceivingAllocation.setPlanningVessel(new PlanningVessel());
                //planningReceivingAllocation.setMasterCommodity(new MasterCommodity());
                planningReceivingAllocation.setMasterContainerType(new MasterContainerType());
                planningReceivingAllocation.setMasterGrossClass(new MasterGrossClass());
                //mulai isi
                MasterPort port1 = masterPortFacadeRemote.findMasterPortByName(planningReceivingAllocation.getPortCode());
                this.selectedPort1 = port1.getName();
                
                planningReceivingAllocation.setPlanningVessel(planningVessel);
                planningReceivingAllocation.getMasterContainerType().setContType((Integer) constraintList.get(i)[0]);
                planningReceivingAllocation.setContSize(Short.parseShort(constraintList.get(i)[1].toString()));
                planningReceivingAllocation.setContStatus((String) constraintList.get(i)[2]);
                planningReceivingAllocation.getMasterGrossClass().setGrossClass((String) constraintList.get(i)[3]);
                //planningReceivingAllocation.getMasterCommodity().setCommodityCode((String) constraintList.get(i)[4]);
                
                if ((Boolean) constraintList.get(i)[5] == true) {
                    planningReceivingAllocation.setDg("TRUE");
                } else {
                    planningReceivingAllocation.setDg("FALSE");
                }
                
                if((Boolean) constraintList.get(i)[6] == true) {
                    planningReceivingAllocation.setOverSize("TRUE");
                } else {
                    planningReceivingAllocation.setOverSize("FALSE");
                }
                
                if ((Boolean) constraintList.get(i)[9] == true) {
                    planningReceivingAllocation.setIsExport("TRUE");
                } else {
                    planningReceivingAllocation.setIsExport("FALSE");
                }
                planningReceivingAllocation.setPortCode((String) constraintList.get(i)[7]);
                planningReceivingAllocation.setCountBaplie(Integer.valueOf(((Long) constraintList.get(i)[8]).toString()));
                planningReceivingAllocation.setCountBaplie2(planningReceivingAllocation.getCountBaplie());
                planningReceivingAllocation.setTotalBooking(0);
                planningReceivingAllocation.setTotalBooking2(0);
                planningReceivingAllocation.setAllocated(0);
                planningReceivingAllocationFacadeRemote.create(planningReceivingAllocation);
            }

            setBlock();
        }
    }

    public void handleAddButton() {
        planningReceivingAllocation = new PlanningReceivingAllocation();
        planningReceivingAllocation.setPlanningVessel(new PlanningVessel());
        planningReceivingAllocation.setMasterCommodity(new MasterCommodity());
        planningReceivingAllocation.setMasterContainerType(new MasterContainerType());
        planningReceivingAllocation.setMasterGrossClass(new MasterGrossClass());

        //masterGrossClasses = masterGrossClassFacadeRemote.findAll();
        masterGrossClasses = masterGrossClassFacadeRemote.findByDescriptionOnChange("empty");

        MasterContainerType mct = masterContainerTypeFacadeRemote.find(1);
        planningReceivingAllocation.setMasterContainerType(mct);
        planningReceivingAllocation.setContSize(mct.getFeetMark());
    }
    
//penambahan untuk pemilihan gross class selain empty by ade chelsea 10 april 2014    
     public void viewGrossClass(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();
        masterGrossClasses = null;
        if (newItem.equals("MTY")){
            masterGrossClasses = null;
            masterGrossClasses = masterGrossClassFacadeRemote.findByDescriptionOnChange("empty");
            
            planningReceivingAllocation.setMasterGrossClass(new MasterGrossClass());
        }else{
            masterGrossClasses = null;
            masterGrossClasses = masterGrossClassFacadeRemote.findByDescriptionOnChange2("empty");
            
            planningReceivingAllocation.setMasterGrossClass(new MasterGrossClass());
        }
            
    }
    public void save() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean loggedIn = true;
            
        MasterPort port1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
        
        if (port1 == null) {
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.pod.not_found");
            return;
        }
        
        planningReceivingAllocation.setPortCode(port1.getPortCode());
        Object temp = planningReceivingAllocationFacadeRemote.findDuplicateConstraint(noPPKB, planningReceivingAllocation.getMasterContainerType().getContType(), planningReceivingAllocation.getContStatus(), planningReceivingAllocation.getMasterGrossClass().getGrossClass(), planningReceivingAllocation.getDg(), planningReceivingAllocation.getOverSize(), planningReceivingAllocation.getIsExport(), planningReceivingAllocation.getPortCode());

        if (temp != null) {
            loggedIn = false;
            context.addMessage(null, new FacesMessage("Duplikat Constraint, Constraint Already Exist In Database"));
        } else {
            MasterGrossClass grossClass = masterGrossClassFacadeRemote.find(planningReceivingAllocation.getMasterGrossClass().getGrossClass());
            planningReceivingAllocation.setMasterGrossClass(grossClass);
            planningReceivingAllocation.setMasterCommodity(new MasterCommodity());
            planningReceivingAllocation.getMasterCommodity().setCommodityCode("000");
            planningReceivingAllocation.setPlanningVessel(planningVessel);
            planningReceivingAllocation.setCountBaplie(0);
            planningReceivingAllocation.setCountBaplie2(planningReceivingAllocation.getCountBaplie());
            planningReceivingAllocation.setTotalBooking(0);
            planningReceivingAllocation.setTotalBooking2(0);
            planningReceivingAllocation.setAllocated(0);
                
            planningReceivingAllocationFacadeRemote.create(planningReceivingAllocation);
            context.addMessage(null, new FacesMessage("Succesfull, add constraint"));
        }

        planningReceivingAllocations = planningReceivingAllocationFacadeRemote.findAllByPPKB(noPPKB);
        resetFooterSummary();
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    public void handleRefreshReceivingAllocations(ActionEvent event) {
        resetReceivingAllocation(noPPKB);
        resetFooterSummary();
    }

    public void handleButtonEdit(ActionEvent event) {
        idPlanningReceivingAllocation = (Integer) event.getComponent().getAttributes().get("idEdit");
        receivingAllocation = planningReceivingAllocationFacadeRemote.findAllByID(idPlanningReceivingAllocation);
        planningReceivings = planningReceivingFacadeRemote.findAllByIdReceivingAllocation(idPlanningReceivingAllocation);
        planningReceiving = new PlanningReceiving();
        planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(idPlanningReceivingAllocation);

        if (!yards.isEmpty()) {
            readyYard((String) yards.get(0)[0]);
            handleFrSlotChanged();
        }

        List<Integer> containersByConstraint = planningContReceivingFacadeRemote.findBaplieByConstraint(planningReceivingAllocation.getMasterContainerType().getContType(), planningReceivingAllocation.getContStatus(), planningReceivingAllocation.getMasterGrossClass().getGrossClass(), planningReceivingAllocation.getMasterCommodity() == null ? null : planningReceivingAllocation.getMasterCommodity().getCommodityCode(), planningReceivingAllocation.getDg(), planningReceivingAllocation.getOverSize(), planningReceivingAllocation.getIsExport(), planningReceivingAllocation.getPortCode(), noPPKB);
        for (int x = 0; x < containersByConstraint.size(); x++) {
            planningContReceiving = planningContReceivingFacadeRemote.find(containersByConstraint.get(x));
            planningContReceiving.setIdConstrainPlanning(idPlanningReceivingAllocation);
            planningContReceivingFacadeRemote.edit(planningContReceiving);
            planningContReceiving = new PlanningContReceiving();
        }
        planningReceivingAllocation.setCountBaplie(containersByConstraint.size());
        planningReceivingAllocation.setCountBaplie2(planningReceivingAllocation.getCountBaplie());
    }

    public void saveEditCont(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean isValid = false;

        try {
           idBaru = planningReceivingAllocationFacadeRemote.findValidasiConstraint(noPPKB, planningReceivingAllocation.getContSize(), planningReceivingAllocation.getMasterContainerType().getContType(), planningReceivingAllocation.getContStatus(), planningReceivingAllocation.getOverSize(), planningReceivingAllocation.getDg(), planningReceivingAllocation.getIsExport(), planningReceivingAllocation.getMasterGrossClass().getGrossClass(), planningReceivingAllocation.getPortCode());

            if (idLama.equals(idBaru) || idBaru == null || idBaru == 0) {
                isValid = true;
                MasterGrossClass grossClass = masterGrossClassFacadeRemote.find(planningReceivingAllocation.getMasterGrossClass().getGrossClass());
                planningReceivingAllocation.setMasterGrossClass(grossClass);
                
                MasterPort port1 = masterPortFacadeRemote.findMasterPortByName(selectedPort1);
                planningReceivingAllocation.setPortCode(port1.getPortCode());
                
                planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
                planningReceivingAllocations = planningReceivingAllocationFacadeRemote.findAllByPPKB(noPPKB);
                resetFooterSummary();
                FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_INFO, "Info", "application.save.succeeded");
            } else {
                FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
            }

            cekValidasi();
        } catch (EJBException e) {
            FacesHelper.addFacesMessage(context, FacesMessage.SEVERITY_ERROR, "Error", "application.save.failed");
        }

        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", isValid);
    }

    public void handleButtonDelete(ActionEvent event) {
        idPlanningReceivingAllocation = (Integer) event.getComponent().getAttributes().get("idDelete");
        planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(idPlanningReceivingAllocation);
        planningReceivings = planningReceivingFacadeRemote.findAllByIdReceivingAllocation(idPlanningReceivingAllocation);
        deleteAll = false;
    }

    public void handleButtonDeleteEdit(ActionEvent event) {
        idLama = (Integer) event.getComponent().getAttributes().get("idDelete");
        planningReceivingAllocation = planningReceivingAllocationFacadeRemote.find(idLama);

        masterGrossClasses = masterGrossClassFacadeRemote.findAll();
//        masterPorts = masterPortFacadeRemote.findAll();
        //masterContainerTypes = lookupMasterContainerTypeFacadeRemote().findAll();
    }

    public void delete() {
        if (deleteAll) {
        } else {
            //fitur delete
            for (int x = 0; x < planningReceivings.size(); x++) {
                int idRemove = (Integer) planningReceivings.get(x)[6];
                planningReceiving = planningReceivingFacadeRemote.find(idRemove);

                masterYardCoordinatFacadeRemote.deleteConstraintByAllocationRange(noPPKB, planningReceiving.getFrSlot(), planningReceiving.getToSlot(), planningReceiving.getFrRow(), planningReceiving.getToRow());
                planningReceivingFacadeRemote.remove(planningReceiving);
            }

            planningReceivingAllocationFacadeRemote.remove(planningReceivingAllocation);
            planningContReceivingFacadeRemote.deleteByIdConstraint(planningReceivingAllocation.getId());
        }

        planningReceiving = new PlanningReceiving();
        handleFrSlotChanged();
        setBlock();
    }

    public void cekValidasi() {
        int id = planningContReceivingFacadeRemote.findByContValidasiConstraint(noPPKB, planningReceivingAllocation.getContSize(), planningReceivingAllocation.getMasterContainerType().getContType(), planningReceivingAllocation.getContStatus(), planningReceivingAllocation.getOverSize(), planningReceivingAllocation.getDg(), planningReceivingAllocation.getIsExport(), planningReceivingAllocation.getMasterGrossClass().getGrossClass(), planningReceivingAllocation.getPortCode());
        System.out.println("cek Id : " + id);
        if (id >= 1) {
            this.edit = Boolean.TRUE;
        } else {
            this.edit = Boolean.FALSE;
        }
    }

    public void handleButtonClose() {
        planningReceivingAllocationFacadeRemote.edit(planningReceivingAllocation);
        setBlock();
    }

    private void setBlock() {
        planningReceivingAllocations = planningReceivingAllocationFacadeRemote.findAllByPPKB(noPPKB);
        resetFooterSummary();
        for (Object[] o : planningReceivingAllocations) {
            List<Object[]> planRec = planningReceivingFacadeRemote.findAllByIdReceivingAllocation((Integer) o[11]);
            String block = "";
            String bl = "";
            for (Object[] ob : planRec) {
                if (!bl.equalsIgnoreCase((String) ob[0])) {
                    if (block.equals("")) {
                        block = (String) ob[0];
                    } else {
                        block = block + ", " + (String) ob[0];
                    }
                }
                bl = (String) ob[0];
            }
            o[12] = block;
        }
    }

    public void readyYard(String block) {
        //untuk mengkondisikan pemilihan yard
        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock(block);
        counterSlot = (Integer) mYard[0];
        counterRow = (Integer) mYard[1];
        counterTier = (Integer) mYard[2];
        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
    }

    public void onChangeEvent(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();
        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock(newItem);
        Integer slot = (Integer) mYard[0];
        Integer row = (Integer) mYard[1];
        Integer tier = (Integer) mYard[2];
        getSlots().clear();
        getRows().clear();
        getTiers().clear();
        counterSlot = slot;
        counterRow = row;
        counterTier = tier;
    }

    public void onChangeBooking(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        planningReceivingAllocation.setTotalBooking(newItem);
    }

    public void onChangeConstraintType(ValueChangeEvent event) {
        int newItem = (Integer) event.getNewValue();
        MasterContainerType mct = masterContainerTypeFacadeRemote.find(newItem);
        planningReceivingAllocation.setMasterContainerType(mct);
        planningReceivingAllocation.setContSize(mct.getFeetMark());
    }

    public void handleFrSlotChanged() {
        short teus = (short) Math.ceil((double) planningReceivingAllocation.getContSize() / (double) 20);

        if ((planningReceiving.getToSlot() - planningReceiving.getFrSlot()) < (teus - 1)) {
            planningReceiving.setToSlot((short) (planningReceiving.getFrSlot() + teus - 1));
        }

        slots = getSlots();

        if (planningReceiving.getToSlot() > slots.size()) {
            planningReceiving.setToSlot(slots.get(slots.size()).shortValue());
            planningReceiving.setFrSlot((short) (planningReceiving.getToSlot() - teus + 1));
        }
    }

    public void handleToSlotChanged() {
        short teus = (short) Math.ceil((double) planningReceivingAllocation.getContSize() / (double) 20);
        
        if ((planningReceiving.getToSlot() - planningReceiving.getFrSlot()) < (teus - 1)) {
            planningReceiving.setFrSlot((short) (planningReceiving.getToSlot() - teus + 1));
        }

        if (planningReceiving.getFrSlot() < 1) {
            planningReceiving.setFrSlot((short) 1);
            planningReceiving.setToSlot((short) (planningReceiving.getFrSlot() + teus - 1));
        }
    }

    public void reinitPlanningReceiving() {
        planningReceiving = new PlanningReceiving();
        //reset range
        if (!yards.isEmpty()) {
            readyYard((String) yards.get(0)[0]);
            handleFrSlotChanged();
        }
    }

    public void deletePlanningReceiving(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        int itemDelete = (Integer) event.getComponent().getAttributes().get("itemDelete");
        planningReceiving = planningReceivingFacadeRemote.find(itemDelete);

        planningReceivingAllocation.setAllocated(planningReceivingAllocation.getAllocated() - planningReceiving.getCountAllocation());

        masterYardCoordinatFacadeRemote.deleteConstraintByAllocationRange(noPPKB, planningReceiving.getFrSlot(), planningReceiving.getToSlot(), planningReceiving.getFrRow(), planningReceiving.getToRow());
        planningReceivingFacadeRemote.remove(planningReceiving);

        if (!yards.isEmpty()) {
            readyYard((String) yards.get(0)[0]);
        }

        planningReceivings = planningReceivingFacadeRemote.findAllByIdReceivingAllocation(idPlanningReceivingAllocation);
        planningReceiving = new PlanningReceiving();
        handleFrSlotChanged();
        
        context.addMessage(null, new FacesMessage("deleted is completed"));
    }

    public void addPlanningReceiving() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (planningReceivingAllocation.getTotalBooking() != 0) {
            boolean isBreak = false;
            //loop for master yard coordinat
            List<Integer> idMYC = new ArrayList<Integer>();
            int countAllocated = planningReceivingAllocation.getAllocated();
            String block = planningReceiving.getBlock();
            short frSlot = planningReceiving.getFrSlot();
            short toSlot = planningReceiving.getToSlot();
            short frRow = planningReceiving.getFrRow();
            short toRow = planningReceiving.getToRow();
            System.out.println(String.format("%s, %s, %s, %s, %s", block, frSlot, toSlot, frRow, toRow));
            Object[] toYard = masterYardFacadeRemote.findMasterYardByBlock(block);
            short toTier = Short.parseShort(toYard[2].toString());
//            if (frSlot <= toSlot && frRow <= toRow) {   
//                if (planningReceivingAllocation.getContSize() == 20) {
//                    for (short slot = frSlot; slot <= toSlot; slot++) {
//                        for (short tier = 1; tier <= toTier; tier++) {
//                            for (short row = frRow; row <= toRow; row++) {
//                                if (countAllocated < planningReceivingAllocation.getTotalBooking()) {
//                                    Object[] targetLocation = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, slot, row, tier);
//                                    
//                                    if (targetLocation[1].toString().equalsIgnoreCase("exist") && targetLocation[3] != null && targetLocation[3].toString().equals(noPPKB)) {
//                                        continue;
//                                    } else if (targetLocation[1].toString().equalsIgnoreCase("empty")) {
//                                        idMYC.add((Integer) targetLocation[0]);
//                                        countAllocated++;
//                                    } else {
//                                        idMYC.clear();
//                                        countAllocated = planningReceivingAllocation.getAllocated();
//                                        context.addMessage(null, new FacesMessage("Booking yard is aborted"));
//                                        context.addMessage(null, new FacesMessage("because range of yard is not empty"));
//                                        isBreak = true;
//                                    }
//                                } else {
//                                    isBreak = true;
//                                }
//                                if (isBreak) {
//                                    break;
//                                }
//                            }
//                            if (isBreak) {
//                                break;
//                            }
//                        }
//                        if (isBreak) {
//                            break;
//                        }
//                    }
//                } else if (planningReceivingAllocation.getContSize() == 40) {
//                    for (short slot = frSlot; slot <= toSlot; slot += 2) {
//                        for (short tier = 1; tier <= toTier; tier++) {
//                            for (short row = frRow; row <= toRow; row++) {
//                                if (countAllocated < planningReceivingAllocation.getTotalBooking()) {
//                                    Object[] targetLocation = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, slot, row, tier);
//                                    Object[] targetLocation40ft = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, (short) (slot + 1), row, tier);
//
//                                    if ((targetLocation[1].toString().equalsIgnoreCase("exist") && targetLocation[3] != null && targetLocation[3].toString().equals(noPPKB)) || (targetLocation40ft[1].toString().equalsIgnoreCase("exist") && targetLocation40ft[3] != null && targetLocation40ft[3].toString().equals(noPPKB))) {
//                                        continue;
//                                    } else if (targetLocation[1].toString().equalsIgnoreCase("empty") && targetLocation40ft[1].toString().equalsIgnoreCase("empty")) {
//                                        idMYC.add((Integer) targetLocation[0]);
//                                        countAllocated++;
//                                    } else {
//                                        idMYC.clear();
//                                        countAllocated = planningReceivingAllocation.getAllocated();
//                                        context.addMessage(null, new FacesMessage("Booking yard is aborted"));
//                                        context.addMessage(null, new FacesMessage("because range of yard is not empty"));
//                                        isBreak = true;
//                                    }
//                                } else {
//                                    isBreak = true;
//                                }
//                                if (isBreak) {
//                                    break;
//                                }
//                            }
//                            if (isBreak) {
//                                break;
//                            }
//                        }
//                        if (isBreak) {
//                            break;
//                        }
//                    }
//                } else if (planningReceivingAllocation.getContSize() == 45) {
//                    for (short slot = frSlot; slot <= toSlot; slot += 3) {
//                        for (short tier = 1; tier <= toTier; tier++) {
//                            for (short row = frRow; row <= toRow; row++) {
//                                if (countAllocated < planningReceivingAllocation.getTotalBooking()) {
//                                    Object[] targetLocation = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, slot, row, tier);
//                                    Object[] targetLocation40ft = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, (short) (slot + 1), row, tier);
//                                    Object[] targetLocation45ft = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, (short) (slot + 2), row, tier);
//
//                                    if ((targetLocation[1].toString().equalsIgnoreCase("exist") && targetLocation[3] != null && targetLocation[3].toString().equals(noPPKB)) ||
//                                            (targetLocation40ft[1].toString().equalsIgnoreCase("exist") && targetLocation40ft[3] != null && targetLocation40ft[3].toString().equals(noPPKB)) ||
//                                            (targetLocation45ft[1].toString().equalsIgnoreCase("exist") && targetLocation45ft[3] != null && targetLocation45ft[3].toString().equals(noPPKB))) {
//                                        continue;
//                                    } else if (targetLocation[1].toString().equalsIgnoreCase("empty") && targetLocation40ft[1].toString().equalsIgnoreCase("empty") && targetLocation45ft[1].toString().equalsIgnoreCase("empty")) {
//                                        idMYC.add((Integer) targetLocation[0]);
//                                        countAllocated++;
//                                    } else {
//                                        idMYC.clear();
//                                        countAllocated = planningReceivingAllocation.getAllocated();
//                                        context.addMessage(null, new FacesMessage("Booking yard is aborted"));
//                                        context.addMessage(null, new FacesMessage("because range of yard is not empty"));
//                                        isBreak = true;
//                                    }
//                                } else {
//                                    isBreak = true;
//                                }
//                                if (isBreak) {
//                                    break;
//                                }
//                            }
//                            if (isBreak) {
//                                break;
//                            }
//                        }
//                        if (isBreak) {
//                            break;
//                        }
//                    }
//                }
//
//                //control view add
//                if (!idMYC.isEmpty()) {
//                    //create planning receiving
//                    planningReceiving.setCountAllocation(idMYC.size());
//                    planningReceiving.setReceivingAllocationId(idPlanningReceivingAllocation);
//                    planningReceivingFacadeRemote.create(planningReceiving);
//                    Integer idPlanningReceiving = planningReceivingFacadeRemote.findLastOfId();
//                    //looping booking m yard coordinat
//                    masterYardCoordinat = new MasterYardCoordinat();
//                    if (planningReceivingAllocation.getContSize() == 20) {
//                        for (int i = 0; i < idMYC.size(); i++) {
//                            String contNo = generateId(noPPKB);
//                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(idMYC.get(i));
//                            masterYardCoordinat.setContNo(contNo);
//                            masterYardCoordinat.setContType(planningReceivingAllocation.getMasterContainerType().getContType().toString());
//                            masterYardCoordinat.setPod(planningReceivingAllocation.getPortCode());
//                            masterYardCoordinat.setContSize(planningReceivingAllocation.getContSize());
//                            masterYardCoordinat.setGrossClass(planningReceivingAllocation.getMasterGrossClass().getGrossClass());
//                            masterYardCoordinat.setNoPpkb(planningReceivingAllocation.getPlanningVessel().getNoPpkb());
//                            masterYardCoordinat.setStatus("planning");
//                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
//                        }
//                    } else if (planningReceivingAllocation.getContSize() == 40) {
//                        for (int i = 0; i < idMYC.size(); i++) {
//                            String contNo = generateId(noPPKB);
//                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(idMYC.get(i));
//                            masterYardCoordinat.setContNo(contNo);
//                            masterYardCoordinat.setPod(planningReceivingAllocation.getPortCode());
//                            masterYardCoordinat.setContType(planningReceivingAllocation.getMasterContainerType().getContType().toString());
//                            masterYardCoordinat.setContSize(planningReceivingAllocation.getContSize());
//                            masterYardCoordinat.setGrossClass(planningReceivingAllocation.getMasterGrossClass().getGrossClass());
//                            masterYardCoordinat.setNoPpkb(planningReceivingAllocation.getPlanningVessel().getNoPpkb());
//                            masterYardCoordinat.setStatus("planning");
//                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
//                            //kembar
//                            Object[] twin = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(masterYardCoordinat.getBlock(), (short) (masterYardCoordinat.getSlot() + 1), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
//                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) twin[0]);
//                            masterYardCoordinat.setContNo(contNo);
//                            masterYardCoordinat.setPod(planningReceivingAllocation.getPortCode());
//                            masterYardCoordinat.setContType(planningReceivingAllocation.getMasterContainerType().getContType().toString());
//                            masterYardCoordinat.setContSize(planningReceivingAllocation.getContSize());
//                            masterYardCoordinat.setGrossClass(planningReceivingAllocation.getMasterGrossClass().getGrossClass());
//                            masterYardCoordinat.setNoPpkb(planningReceivingAllocation.getPlanningVessel().getNoPpkb());
//                            masterYardCoordinat.setStatus("planning");
//                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
//                        }
//                    } else if (planningReceivingAllocation.getContSize() == 45) {
//                        for (int i = 0; i < idMYC.size(); i++) {
//                            String contNo = generateId(noPPKB);
//                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find(idMYC.get(i));
//                            masterYardCoordinat.setContNo(contNo);
//                            masterYardCoordinat.setPod(planningReceivingAllocation.getPortCode());
//                            masterYardCoordinat.setContType(planningReceivingAllocation.getMasterContainerType().getContType().toString());
//                            masterYardCoordinat.setContSize(planningReceivingAllocation.getContSize());
//                            masterYardCoordinat.setGrossClass(planningReceivingAllocation.getMasterGrossClass().getGrossClass());
//                            masterYardCoordinat.setNoPpkb(planningReceivingAllocation.getPlanningVessel().getNoPpkb());
//                            masterYardCoordinat.setStatus("planning");
//                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
//
//                            masterYardCoordinat = masterYardCoordinatFacadeRemote.findByCoordinat(masterYardCoordinat.getBlock(), (short) (masterYardCoordinat.getSlot() + 1), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
//                            masterYardCoordinat.setContNo(contNo);
//                            masterYardCoordinat.setPod(planningReceivingAllocation.getPortCode());
//                            masterYardCoordinat.setContType(planningReceivingAllocation.getMasterContainerType().getContType().toString());
//                            masterYardCoordinat.setContSize(planningReceivingAllocation.getContSize());
//                            masterYardCoordinat.setGrossClass(planningReceivingAllocation.getMasterGrossClass().getGrossClass());
//                            masterYardCoordinat.setNoPpkb(planningReceivingAllocation.getPlanningVessel().getNoPpkb());
//                            masterYardCoordinat.setStatus("planning");
//                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
//
//                            masterYardCoordinat = masterYardCoordinatFacadeRemote.findByCoordinat(masterYardCoordinat.getBlock(), (short) (masterYardCoordinat.getSlot() + 1), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
//                            masterYardCoordinat.setContNo(contNo);
//                            masterYardCoordinat.setPod(planningReceivingAllocation.getPortCode());
//                            masterYardCoordinat.setContType(planningReceivingAllocation.getMasterContainerType().getContType().toString());
//                            masterYardCoordinat.setContSize(planningReceivingAllocation.getContSize());
//                            masterYardCoordinat.setGrossClass(planningReceivingAllocation.getMasterGrossClass().getGrossClass());
//                            masterYardCoordinat.setNoPpkb(planningReceivingAllocation.getPlanningVessel().getNoPpkb());
//                            masterYardCoordinat.setStatus("planning");
//                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
//                        }
//                    }
//                    context.addMessage(null, new FacesMessage("succesfull, add yard"));
//                }
//                planningReceiving = new PlanningReceiving();
//                //reset range
//                if (!yards.isEmpty()) {
//                    readyYard((String) yards.get(0)[0]);
//                    handleFrSlotChanged();
//                }
//
//                planningReceivings = planningReceivingFacadeRemote.findAllByIdReceivingAllocation(idPlanningReceivingAllocation);
//                //untuk menghitung lahan yang tersedia
//                int allocated = 0;
//                for (int i = 0; i < planningReceivings.size(); i++) {
//                    allocated += (Integer) planningReceivings.get(i)[5];
//                }
//                planningReceivingAllocation.setAllocated(allocated);
//            } else if (frSlot > toSlot) {
//                context.addMessage(null, new FacesMessage("Attention, to Slot is bigger then from Slot"));
//            } else if (frRow > toRow) {
//                context.addMessage(null, new FacesMessage("Attention, to Row is bigger then from Row"));
//            }
            
            
//            Tarakan
            planningReceivingAllocation.setAllocated(planningReceivingAllocation.getTotalBooking());
            planningReceiving.setCountAllocation(1);
            planningReceiving.setReceivingAllocationId(idPlanningReceivingAllocation);
            
            planningReceivingFacadeRemote.create(planningReceiving);
            
            planningReceivings = planningReceivingFacadeRemote.findAllByIdReceivingAllocation(idPlanningReceivingAllocation);

            context.addMessage(null, new FacesMessage("succesfull, add yard"));
              
        } else {
            context.addMessage(null, new FacesMessage("Attention, Total booking cannot be zero"));
        }
    }

    public String getSelectedPort1() {
        return selectedPort1;
    }

    public void setSelectedPort1(String selectedPort1) {
        this.selectedPort1 = selectedPort1;
    }
    
    /**
     * @return the yards
     */
    public List<Object[]> getYards() {
        return yards;
    }

    /**
     * @return the slots
     */
    public Map<Integer, Integer> getSlots() {
        for (int i = 1; i <= counterSlot; i++) {
            slots.put(i, i);
        }
        return slots;
    }

    /**
     * @return the rows
     */
    public Map<Integer, Integer> getRows() {
        for (int i = 1; i <= counterRow; i++) {
            rows.put(i, i);
        }
        return rows;
    }

    /**
     * @return the tiers
     */
    public Map<Integer, Integer> getTiers() {
        for (int i = 1; i <= counterTier; i++) {
            tiers.put(i, i);
        }
        return tiers;
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
     * @return the planningReceiving
     */
    public PlanningReceiving getPlanningReceiving() {
        return planningReceiving;
    }

    /**
     * @param planningReceiving the planningReceiving to set
     */
    public void setPlanningReceiving(PlanningReceiving planningReceiving) {
        this.planningReceiving = planningReceiving;
    }

    /**
     * @return the vessels
     */
    public List<Object[]> getVessels() {
        return vessels;
    }

    /**
     * @return the vessel
     */
    public Object[] getVessel() {
        return vessel;
    }

    /**
     * @return the planningReceivingAllocations
     */
    public List<Object[]> getPlanningReceivingAllocations() {
        return planningReceivingAllocations;
    }

    /**
     * @return the masterGrossClasses
     */
    public List<MasterGrossClass> getMasterGrossClasses() {
        return masterGrossClasses;
    }

    /**
     * @return the masterPorts
     */
    public List<MasterPort> getMasterPorts() {
        return masterPorts;
    }

    /**
     * @return the receivingAllocation
     */
    public Object[] getReceivingAllocation() {
        return receivingAllocation;
    }

    /**
     * @return the planningReceivingList
     */
    public List<Object[]> getPlanningReceivings() {
        return planningReceivings;
    }

    public String generateId(String noPpkb) {
        String i;
        String id = masterYardCoordinatFacadeRemote.findContNoGenerate(noPpkb);
        if (id == null) {
            i = "0001";
        } else {
            i = String.valueOf(Integer.valueOf(id) + 1);
        }

        if (i.length() == 1) {
            i = noPpkb + "-000" + i;
        } else if (i.length() == 2) {
            i = noPpkb + "-00" + i;
        } else if (i.length() == 3) {
            i = noPpkb + "-0" + i;
        } else {
            i = noPpkb + "-" + i;
        }
        return i;
    }

    //dialog monitoring
    /**
     * @return the selectedBlock
     */
    public String getSelectedBlock() {
        return selectedBlock;
    }

    /**
     * @param selectedBlock the selectedBlock to set
     */
    public void setSelectedBlock(String selectedBlock) {
        this.selectedBlock = selectedBlock;
    }

    /**
     * @return the selectedSlot
     */
    public Integer getSelectedSlot() {
        return selectedSlot;
    }

    /**
     * @param selectedSlot the selectedSlot to set
     */
    public void setSelectedSlot(Integer selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public String getExistOnly() {
        return existOnly;
    }

    public void setExistOnly(String existOnly) {
        this.existOnly = existOnly;
    }

    public List<Object[]> getBlocks() {
        return blocks;
    }

    public List<SelectItem> getSelectSlots() {
        return selectSlots;
    }

    public String getSideViewUrl() {
        skala(selectedBlock);
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../cySideView.png?"
                + "block=" + selectedBlock + "&"
                + "slot=" + selectedSlot + "&"
                + "w=" + widthSide + "&"
                + "h=" + heightSide + "&"
                + "eo=" + existOnly;
    }

    public void handleChangeBlock(ValueChangeEvent event) {
        Object[] block = masterYardFacadeRemote.findMasterYardByBlock(event.getNewValue().toString());
        selectSlots = new ArrayList();
        for (int i = 1; i <= (Integer) block[0]; i++) {
            selectSlots.add(new SelectItem(new Integer(i), String.valueOf(i)));
        }
        selectedSlot = new Integer(1);
        //for birdView
        selectTiers = new ArrayList();
        for (int i = 1; i <= (Integer) block[2]; i++) {
            selectTiers.add(new SelectItem(new Integer(i), String.valueOf(i)));
        }
        selectedTier = new Integer(1);
    }

    public void handleChangeSlot(ValueChangeEvent event) {
        selectedSlot = (Integer) event.getNewValue();
    }

    public void handleChangeExistOnly(ValueChangeEvent event) {
        existOnly = (String) event.getNewValue();
    }

    public void handleClickNextButton(ActionEvent event) {
        if (!(selectedSlot == Integer.parseInt(selectSlots.get(selectSlots.size() - 1).getValue().toString()))) {
            selectedSlot = new Integer(selectedSlot + 1);
        }

    }

    public void handleClickPrevButton(ActionEvent event) {
        if (!(selectedSlot == Integer.parseInt(selectSlots.get(0).getValue().toString()))) {
            selectedSlot = new Integer(selectedSlot - 1);
        }

    }

    //birdView
    /**
     * @return the selectedSlot
     */
    public Integer getSelectedTier() {
        return selectedTier;
    }

    /**
     * @param selectedTier the selectedSlot to set
     */
    public void setSelectedTier(Integer selectedTier) {
        this.selectedTier = selectedTier;
    }

    public List<SelectItem> getSelectTiers() {
        return selectTiers;
    }

    public String getBirdViewUrl() {
        skala(selectedBlock);
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/../cyBirdView.png?"
                + "block=" + selectedBlock + "&"
                + "tier=" + selectedTier + "&"
                + "w=" + widthBird + "&"
                + "h=" + heightBird + "&"
                + "eo=" + existOnly;
    }

    public void handleChangeTier(ValueChangeEvent event) {
        selectedTier = (Integer) event.getNewValue();
    }

    public void handleClickNextButtonBird(ActionEvent event) {
        if (!(selectedTier == Integer.parseInt(selectTiers.get(selectTiers.size() - 1).getValue().toString()))) {
            selectedTier = new Integer(selectedTier + 1);
        }
    }

    public void handleClickPrevButtonBird(ActionEvent event) {
        if (!(selectedTier == Integer.parseInt(selectTiers.get(0).getValue().toString()))) {
            selectedTier = new Integer(selectedTier - 1);
        }
    }

    //skala
    public void skala(String newBlock) {
        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock(newBlock);
        int slot = ((Number) mYard[0]).intValue();
        int row = ((Number) mYard[1]).intValue();
        int tier = ((Number) mYard[2]).intValue();
        heightBird = (row * 47) + 100;
        widthBird = (slot * 118) + 100;
        heightSide = (tier * 167) + 100;
        widthSide = (row * 164) + 100;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public Integer getIdBaru() {
        return idBaru;
    }

    public void setIdBaru(Integer idBaru) {
        this.idBaru = idBaru;
    }

    public Integer getIdLama() {
        return idLama;
    }

    public void setIdLama(Integer idLama) {
        this.idLama = idLama;
    }

    public Long sumOnYardReceivings(Long value) {
        onYardReceivings += value;
        return value;
    }

    public Long sumAllocatedReceivingPositions(Long value) {
        allocatedReceivingPositions += value;
        return value;
    }

    public Long sumRequiredReceivingPositions(Long value) {
        requiredReceivingPositions += value;
        return value;
    }

    public Long sumOnApplicationReceivings(Long value) {
        onApplicationReceivings += value;
        return value;
    }

    public Long getOnYardReceivings() {
        return onYardReceivings;
    }

    public Long getAllocatedReceivingPositions() {
        return allocatedReceivingPositions;
    }

    public Long getOnApplicationReceivings() {
        return onApplicationReceivings;
    }

    public Long getRequiredReceivingPositions() {
        return requiredReceivingPositions;
    }

    private void resetFooterSummary() {
        requiredReceivingPositions = (long) 0;
        allocatedReceivingPositions = (long) 0;
        onApplicationReceivings = (long) 0;
        onYardReceivings = (long) 0;
    }
}
