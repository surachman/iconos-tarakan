/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean;

import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationFilterFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.YardAllocationTempFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import com.pelindo.ebtos.model.db.YardAllocation;
import com.pelindo.ebtos.model.db.YardAllocationFilter;
import com.pelindo.ebtos.model.db.YardAllocationTemp;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
@ManagedBean(name = "dischargeYardAllocationBean")
@ViewScoped
public class DischargeYardAllocationBean implements Serializable {
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacadeRemote;
    @EJB
    private YardAllocationFilterFacadeRemote yardAllocationFilterFacadeRemote;
    @EJB
    private PlanningContDischargeFacadeRemote planningContDischargeFacadeRemote;
    @EJB
    private YardAllocationFacadeRemote yardAllocationFacadeRemote;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private MasterYardCoordinatFacadeRemote masterYardCoordinatFacadeRemote;
    @EJB
    private YardAllocationTempFacadeRemote yardAllocationTempFacadeRemote;
    
    private List<Object[]> vessels;
    private Object[] vessel;
    private List<Object[]> yardAllocationFilters;
    private List<Object[]> yardAllocations;
    private YardAllocation yardAllocation;
    private List<Object[]> generates;
    private MasterYardCoordinat masterYardCoordinat;
    private List<Object[]> masterYard;
    private Map<Integer, Integer> slots;
    private Map<Integer, Integer> rows;
    private Map<Integer, Integer> tiers;
    private int counterSlot, counterRow, counterTier;
    private YardAllocationFilter yardAllocationFilter;
    private String noPPKB;
    private List<Object[]> constraints;
    private Object[] constraint;
    private Integer idConstraint;
    private int totalAllocationPerConstraint;
    private boolean isBreak;
    private PlanningContDischarge planningContDischarge;
    private YardAllocationTemp yardAllocationTemp;
    private List<Integer> idContainers;
    private MasterYardCoordinat oldMasterYardCoordinat;
    private MasterYardCoordinat newMasterYardCoordinat;
    private int idCoordinatGenerate;
    //dialog monitoring
    private String selectedBlock;
    private String existOnly;
    private Integer selectedSlot;
    private List<Object[]> blocks ;
    private List<SelectItem> selectSlots;
    private Integer selectedTier;
    private List<SelectItem> selectTiers;
    private int heightBird;
    private int widthBird;
    private int heightSide;
    private int widthSide;

    /** Creates a new instance of DischargeYardAllocationBean */
    public DischargeYardAllocationBean() {}

    @PostConstruct
    private void construct() {
        masterYard = masterYardFacadeRemote.findMasterYards();
        yardAllocationFilter = new YardAllocationFilter();
        //inisialiasi
        yardAllocation = new YardAllocation();
        masterYardCoordinat = new MasterYardCoordinat();
        oldMasterYardCoordinat = new MasterYardCoordinat();
        newMasterYardCoordinat = new MasterYardCoordinat();
        blocks = masterYardFacadeRemote.findMasterYards();
        
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

    public void handleNewPpkb(ActionEvent event){
        vessels = planningVesselFacadeRemote.findPlanningVesselsByActivity();
    }

    public List<YardAllocationTemp> getData() {
        return yardAllocationTempFacadeRemote.findAll();
    }

    public void handleSelectNoPpkb(ActionEvent event) {
        noPPKB = (String) event.getComponent().getAttributes().get("noPpkb");
        vessel = planningVesselFacadeRemote.findPlanningVesselByPpkb(noPPKB);
        setBlock();
        generates = masterYardCoordinatFacadeRemote.findGenerate(noPPKB, false,false);
        if (yardAllocationFilters.isEmpty()) {
            constraints = planningContDischargeFacadeRemote.generateConstraintsByPPKB(noPPKB);
            for (int i = 0; i < constraints.size(); i++) {
                yardAllocationFilter.setContSize(Short.parseShort(constraints.get(i)[0].toString()));
                yardAllocationFilter.setContType(((Number) constraints.get(i)[1]).intValue());
                yardAllocationFilter.setContStatus((String) constraints.get(i)[2]);
                yardAllocationFilter.setGrossClass((String) constraints.get(i)[3]);
                yardAllocationFilter.setCommodity((String) constraints.get(i)[4]);
                if( ((String)constraints.get(i)[5]).equalsIgnoreCase("TRUE")) {
                    yardAllocationFilter.setDg("TRUE");
                } else {
                    yardAllocationFilter.setDg("FALSE");
                }
                
                if (((String) constraints.get(i)[7]).equalsIgnoreCase("TRUE")) {
                    yardAllocationFilter.setIsImport("TRUE");
                } else {
                    yardAllocationFilter.setIsImport("FALSE");
                }
                if (((String) constraints.get(i)[8]).equalsIgnoreCase("TRUE")) {
                    yardAllocationFilter.setOverSize("TRUE");
                } else {
                    yardAllocationFilter.setOverSize("FALSE");
                }
//                yardAllocationFilter.setCount(Integer.valueOf(((Long) constraints.get(i)[6]).toString()));
                yardAllocationFilter.setCount(((Number)constraints.get(i)[6]).intValue());
                yardAllocationFilter.setNoPpkb(noPPKB);
                yardAllocationFilter.setAllocated(0);
                yardAllocationFilterFacadeRemote.create(yardAllocationFilter);
            }
            setBlock();
        }
    }

    public void saveYardAllocation() {
        isBreak = false;
        boolean isFloat = false;
        FacesContext context = FacesContext.getCurrentInstance();
        int countAlokasi = 0;
        String block = yardAllocation.getBlock();
        short frSlot = yardAllocation.getFrSlot();
        short toSlot = yardAllocation.getToSlot();
        short frRow = yardAllocation.getFrRow();
        short toRow = yardAllocation.getToRow();
        if (frSlot <= toSlot && frRow <= toRow) {
            Date today = Calendar.getInstance().getTime();
            DateFormat df = new SimpleDateFormat("MMddyyhhmmss");
            String idYardAllocation = df.format(today);
            Object[] toYard = masterYardFacadeRemote.findMasterYardByBlock(block);
            short toTier = Short.parseShort(toYard[2].toString());
            if (((Number) constraint[1]).intValue() == 20) {
                for (short slot = frSlot; slot <= toSlot; slot++) {
                    for (short row = frRow; row <= toRow; row++) {
                        for (short tier = 1; tier <= toTier; tier++) {
                            if (countAlokasi < yardAllocationFilter.getCount()) {
                                Object[] idCoordinat = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, slot, row, tier);
//                                if (idCoordinat[1].toString().equalsIgnoreCase("empty")) {
                                    //find container not allocation
                                    List<Object[]> idc = yardAllocationTempFacadeRemote.findIdContNotGenerate(noPPKB, idConstraint);
                                    if (idc.isEmpty()) {
                                        isBreak = true;
                                        break;
                                    } else {
                                        //find container
                                        planningContDischarge = new PlanningContDischarge();
                                        planningContDischarge = planningContDischargeFacadeRemote.find(((Number) idc.get(0)[0]).intValue());
                                        planningContDischarge.setMasterYard(new MasterYard());
                                        planningContDischarge.getMasterYard().setBlock(block);
                                        planningContDischarge.setYSlot(slot);
                                        planningContDischarge.setYRow(row);
                                        planningContDischarge.setYTier(tier);
                                        planningContDischargeFacadeRemote.edit(planningContDischarge);
                                        //update temporary
                                        yardAllocationTempFacadeRemote.updateCoordinat(((Number) idCoordinat[0]).intValue(), idYardAllocation, ((Number) idc.get(0)[1]).intValue());
                                        //find m_yard_coordinat
                                        masterYardCoordinat = new MasterYardCoordinat();
                                        masterYardCoordinat = masterYardCoordinatFacadeRemote.find(((Number) idCoordinat[0]).intValue());
                                        masterYardCoordinat.setContNo(planningContDischarge.getContNo());
                                        masterYardCoordinat.setMlo(planningContDischarge.getMlo());
                                        masterYardCoordinat.setContWeight(planningContDischarge.getContGross());
                                        masterYardCoordinat.setPod(null);
                                        masterYardCoordinat.setNoPpkb(planningContDischarge.getPlanningVessel().getNoPpkb());
                                        masterYardCoordinat.setStatus("planning");
                                        masterYardCoordinat.setContSize(yardAllocationFilter.getContSize());
                                        masterYardCoordinat.setContType(yardAllocationFilter.getContType().toString());
                                        masterYardCoordinat.setGrossClass(yardAllocationFilter.getGrossClass());
                                        masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                        countAlokasi++;
                                    }
//                                }
                            } else {
                                isBreak = true;
                                break;
                            }
                        }
                        if (isBreak) {
                            break;
                        }
                    }
                    if (isBreak) {
                        break;
                    }
                }
            } else if (((Number) constraint[1]).intValue() == 40) {
                for (short slot = frSlot; slot <= toSlot; slot += 2) {
                    for (short row = frRow; row <= toRow; row++) {
                        for (short tier = 1; tier <= toTier; tier++) {
                            if (countAlokasi < yardAllocationFilter.getCount()) {
                                Object[] idCoordinat = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, slot, row, tier);
                                Object[] idCoordinat2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, (short) (slot + 1), row, tier);
//                                Object[] idCoordinat3 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, (short) (slot + 1), row, (short) (tier - 1));
//                                if (idCoordinat3 == null) {
//                                    isFloat = true;
//                                } else if (!idCoordinat3[1].toString().equalsIgnoreCase("empty")) {
//                                    isFloat = true;
//                                } else {
//                                    isFloat = false;
//                                }
//                                if (idCoordinat != null && idCoordinat2 != null && isFloat) {
//                                    if (idCoordinat[1].toString().equalsIgnoreCase("empty") && idCoordinat2[1].toString().equalsIgnoreCase("empty")) {
//                                        //find container not allocation
                                        List<Object[]> idc = yardAllocationTempFacadeRemote.findIdContNotGenerate(noPPKB, idConstraint);
                                        if (idc.isEmpty()) {
                                            isBreak = true;
                                            break;
                                        } else {
                                            //find container baplie
                                            planningContDischarge = new PlanningContDischarge();
                                            planningContDischarge = planningContDischargeFacadeRemote.find(((Number) idc.get(0)[0]).intValue());
                                            planningContDischarge.setMasterYard(new MasterYard());
                                            planningContDischarge.getMasterYard().setBlock(block);
                                            planningContDischarge.setYSlot(slot);
                                            planningContDischarge.setYRow(row);
                                            planningContDischarge.setYTier(tier);
                                            planningContDischargeFacadeRemote.edit(planningContDischarge);
                                            //update temporary
//                                            yardAllocationTempFacadeRemote.updateCoordinat((Integer) idCoordinat[0], idYardAllocation, ((Number) idc.get(0)[1]).intValue());
                                            //find m_yard_coordinat
                                            masterYardCoordinat = new MasterYardCoordinat();
                                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) idCoordinat[0]);
                                            masterYardCoordinat.setContNo(planningContDischarge.getContNo());
                                            masterYardCoordinat.setMlo(planningContDischarge.getMlo());
                                            masterYardCoordinat.setContWeight(planningContDischarge.getContGross());
                                            masterYardCoordinat.setPod(null);
                                            masterYardCoordinat.setNoPpkb(planningContDischarge.getPlanningVessel().getNoPpkb());
                                            masterYardCoordinat.setStatus("planning");
                                            masterYardCoordinat.setContSize(yardAllocationFilter.getContSize());
                                            masterYardCoordinat.setContType(yardAllocationFilter.getContType().toString());
                                            masterYardCoordinat.setGrossClass(yardAllocationFilter.getGrossClass());
                                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                            //twin
                                            masterYardCoordinat = new MasterYardCoordinat();
                                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) idCoordinat2[0]);
                                            masterYardCoordinat.setContNo(planningContDischarge.getContNo());
                                            masterYardCoordinat.setMlo(planningContDischarge.getMlo());
                                            masterYardCoordinat.setContWeight(planningContDischarge.getContGross());
                                            masterYardCoordinat.setPod(null);
                                            masterYardCoordinat.setNoPpkb(planningContDischarge.getPlanningVessel().getNoPpkb());
                                            masterYardCoordinat.setStatus("planning");
                                            masterYardCoordinat.setContSize(yardAllocationFilter.getContSize());
                                            masterYardCoordinat.setContType(yardAllocationFilter.getContType().toString());
                                            masterYardCoordinat.setGrossClass(yardAllocationFilter.getGrossClass());
                                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                            countAlokasi++;
                                        }
//                                    }
//                                }
                            } else {
                                isBreak = true;
                                break;
                            }
                        }
                        if (isBreak) {
                            break;
                        }
                    }
                    if (isBreak) {
                        break;
                    }
                }
            } else if (((Number) constraint[1]).intValue() == 45) {
                for (short slot = frSlot; slot <= toSlot; slot += 3) {
                    for (short row = frRow; row <= toRow; row++) {
                        for (short tier = 1; tier <= toTier; tier++) {
                            if (countAlokasi < yardAllocationFilter.getCount()) {
                                Object[] idCoordinat = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, slot, row, tier);
                                Object[] idCoordinat2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, (short) (slot + 1), row, tier);
                                Object[] idCoordinat3 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(block, (short) (slot + 2), row, tier);

                                if (idCoordinat != null && idCoordinat2 != null && idCoordinat3 != null) {
                                    if (idCoordinat[1].toString().equalsIgnoreCase("empty") && idCoordinat2[1].toString().equalsIgnoreCase("empty") && idCoordinat3[1].toString().equalsIgnoreCase("empty")) {
                                        //find container not allocation
                                        List<Object[]> idc = yardAllocationTempFacadeRemote.findIdContNotGenerate(noPPKB, idConstraint);
                                        if (idc.isEmpty()) {
                                            isBreak = true;
                                            break;
                                        } else {
                                            //find container baplie
                                            planningContDischarge = new PlanningContDischarge();
                                            planningContDischarge = planningContDischargeFacadeRemote.find(((Number) idc.get(0)[0]).intValue());
                                            planningContDischarge.setMasterYard(new MasterYard());
                                            planningContDischarge.getMasterYard().setBlock(block);
                                            planningContDischarge.setYSlot(slot);
                                            planningContDischarge.setYRow(row);
                                            planningContDischarge.setYTier(tier);
                                            planningContDischargeFacadeRemote.edit(planningContDischarge);
                                            //update temporary
                                            yardAllocationTempFacadeRemote.updateCoordinat(((Number) idCoordinat[0]).intValue(), idYardAllocation, ((Number) idc.get(0)[1]).intValue());
                                            //find m_yard_coordinat
                                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) idCoordinat[0]);
                                            masterYardCoordinat.setContNo(planningContDischarge.getContNo());
                                            masterYardCoordinat.setMlo(planningContDischarge.getMlo());
                                            masterYardCoordinat.setContWeight(planningContDischarge.getContGross());
                                            masterYardCoordinat.setPod(null);
                                            masterYardCoordinat.setNoPpkb(planningContDischarge.getPlanningVessel().getNoPpkb());
                                            masterYardCoordinat.setStatus("planning");
                                            masterYardCoordinat.setContSize(yardAllocationFilter.getContSize());
                                            masterYardCoordinat.setContType(yardAllocationFilter.getContType().toString());
                                            masterYardCoordinat.setGrossClass(yardAllocationFilter.getGrossClass());
                                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);

                                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) idCoordinat2[0]);
                                            masterYardCoordinat.setContNo(planningContDischarge.getContNo());
                                            masterYardCoordinat.setMlo(planningContDischarge.getMlo());
                                            masterYardCoordinat.setContWeight(planningContDischarge.getContGross());
                                            masterYardCoordinat.setPod(null);
                                            masterYardCoordinat.setNoPpkb(planningContDischarge.getPlanningVessel().getNoPpkb());
                                            masterYardCoordinat.setStatus("planning");
                                            masterYardCoordinat.setContSize(yardAllocationFilter.getContSize());
                                            masterYardCoordinat.setContType(yardAllocationFilter.getContType().toString());
                                            masterYardCoordinat.setGrossClass(yardAllocationFilter.getGrossClass());
                                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);

                                            masterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) idCoordinat3[0]);
                                            masterYardCoordinat.setContNo(planningContDischarge.getContNo());
                                            masterYardCoordinat.setMlo(planningContDischarge.getMlo());
                                            masterYardCoordinat.setContWeight(planningContDischarge.getContGross());
                                            masterYardCoordinat.setPod(null);
                                            masterYardCoordinat.setNoPpkb(planningContDischarge.getPlanningVessel().getNoPpkb());
                                            masterYardCoordinat.setStatus("planning");
                                            masterYardCoordinat.setContSize(yardAllocationFilter.getContSize());
                                            masterYardCoordinat.setContType(yardAllocationFilter.getContType().toString());
                                            masterYardCoordinat.setGrossClass(yardAllocationFilter.getGrossClass());
                                            masterYardCoordinatFacadeRemote.edit(masterYardCoordinat);
                                            countAlokasi++;
                                        }
                                    }
                                }
                            } else {
                                isBreak = true;
                                break;
                            }
                        }
                        if (isBreak) {
                            break;
                        }
                    }
                    if (isBreak) {
                        break;
                    }
                }
            }
            //to database
            yardAllocation.setYardAllocationFilter(idConstraint);
            yardAllocation.setCountAllocation(countAlokasi);
            yardAllocation.setId(idYardAllocation);
            yardAllocationFacadeRemote.edit(yardAllocation);
            readyYard((String) masterYard.get(0)[0]);

            //untuk menghitung lahan yang tersedia
            totalAllocationPerConstraint = 0;
            for (int i = 0; i < yardAllocations.size(); i++) {
                totalAllocationPerConstraint += ((Number) yardAllocations.get(i)[5]).intValue();
            }
            //save ke database
            yardAllocationFilter.setAllocated(totalAllocationPerConstraint);
            yardAllocationFilterFacadeRemote.edit(yardAllocationFilter);
            if (yardAllocationFilter.getCount() > yardAllocationFilter.getAllocated()) {
                context.addMessage(null, new FacesMessage("Attention, allocation is still less"));
            } else {
                context.addMessage(null, new FacesMessage("Attention, complete allocation"));
            }
        } else if (frSlot > toSlot) {
            context.addMessage(null, new FacesMessage("Attention, to Slot is bigger then from Slot"));
        } else if (frRow > toRow) {
            context.addMessage(null, new FacesMessage("Attention, to Row is bigger then from Row"));
        }
    }

    public void reinitYardAllocation() {
        readyYard((String) masterYard.get(0)[0]);
    }

    public void editYardAllocation(ActionEvent event) {
        String idAllocation = (String) event.getComponent().getAttributes().get("idAllocation");
        yardAllocation = yardAllocationFacadeRemote.find(idAllocation);
        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock(yardAllocation.getBlock());
        Integer slot = ((Number) mYard[0]).intValue();
        Integer row = ((Number) mYard[1]).intValue();
        Integer tier = ((Number) mYard[2]).intValue();
        getSlots().clear();
        getRows().clear();
        getTiers().clear();
        counterSlot = slot;
        counterRow = row;
        counterTier = tier;
    }

    public void removeYardAllocation() {
        FacesContext context = FacesContext.getCurrentInstance();
        yardAllocationFacadeRemote.remove(yardAllocation);
        List<Object[]> itemRemove = yardAllocationTempFacadeRemote.removeGenerate(yardAllocation.getId());
        if (((Number) constraint[1]).intValue() == 20) {
            for (int a = 0; a < itemRemove.size(); a++) {
                //kembalikan ke semula
                planningContDischarge = planningContDischargeFacadeRemote.find(((Number) itemRemove.get(a)[0]).intValue());
                planningContDischarge.setMasterYard(null);
                planningContDischarge.setYSlot(null);
                planningContDischarge.setYRow(null);
                planningContDischarge.setYTier(null);
                planningContDischargeFacadeRemote.edit(planningContDischarge);
                //yardAllocationTemp
                yardAllocationTempFacadeRemote.removeIdCoordinat(((Number) itemRemove.get(a)[3]).intValue());
                //m_yard_coordinat
                masterYardCoordinat = masterYardCoordinatFacadeRemote.find(((Number) itemRemove.get(a)[1]).intValue());
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
        } else if (((Number) constraint[1]).intValue() == 40) {
            for (int b = 0; b < itemRemove.size(); b++) {
                //kembalikan ke semula
                planningContDischarge = planningContDischargeFacadeRemote.find(((Number) itemRemove.get(b)[0]).intValue());
                planningContDischarge.setMasterYard(null);
                planningContDischarge.setYSlot(null);
                planningContDischarge.setYRow(null);
                planningContDischarge.setYTier(null);
                planningContDischargeFacadeRemote.edit(planningContDischarge);
                //yardAllocationTemp
                yardAllocationTempFacadeRemote.removeIdCoordinat(((Number) itemRemove.get(b)[3]).intValue());
                //m_yard_coordinat
                masterYardCoordinat = masterYardCoordinatFacadeRemote.find(((Number) itemRemove.get(b)[1]).intValue());
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
                //twin
                Object[] oldId = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(masterYardCoordinat.getBlock(), (short) (masterYardCoordinat.getSlot() + 1), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
                masterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) oldId[0]);
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
        } else if (((Number) constraint[1]).intValue() == 45) {
            for (int b = 0; b < itemRemove.size(); b++) {
                //kembalikan ke semula
                planningContDischarge = planningContDischargeFacadeRemote.find((Integer) itemRemove.get(b)[0]);
                planningContDischarge.setMasterYard(null);
                planningContDischarge.setYSlot(null);
                planningContDischarge.setYRow(null);
                planningContDischarge.setYTier(null);
                planningContDischargeFacadeRemote.edit(planningContDischarge);
                //yardAllocationTemp
                yardAllocationTempFacadeRemote.removeIdCoordinat((Integer) itemRemove.get(b)[3]);
                //m_yard_coordinat
                masterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) itemRemove.get(b)[1]);
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
                //twin
                Object[] oldId = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(masterYardCoordinat.getBlock(), (short) (masterYardCoordinat.getSlot() + 1), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
                masterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) oldId[0]);
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

                oldId = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(masterYardCoordinat.getBlock(), (short) (masterYardCoordinat.getSlot() + 1), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
                masterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) oldId[0]);
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
        }
        readyYard((String) masterYard.get(0)[0]);
        totalAllocationPerConstraint = 0;
        for (int i = 0; i < yardAllocations.size(); i++) {
            totalAllocationPerConstraint += ((Number) yardAllocations.get(i)[5]).intValue();
        }
        //update allocated
        yardAllocationFilter.setAllocated(totalAllocationPerConstraint);
        yardAllocationFilterFacadeRemote.edit(yardAllocationFilter);
        context.addMessage(null, new FacesMessage("Succesfull delete is completed"));
    }

    public void handleButtonYardAllocation(ActionEvent event) {
        idConstraint = ((Number) event.getComponent().getAttributes().get("idConstraint")).intValue();
        constraint = yardAllocationFilterFacadeRemote.findById(idConstraint);
        yardAllocationFilter = yardAllocationFilterFacadeRemote.find(idConstraint);
        yardAllocationTemp = new YardAllocationTemp();
        List<Integer> idf = yardAllocationTempFacadeRemote.findCreateGenerate(noPPKB, idConstraint); //
        if (idf.isEmpty()) {
            idContainers = planningContDischargeFacadeRemote.findBaplieByConstraint(
                    yardAllocationFilter.getContType(), 
                    yardAllocationFilter.getContStatus(), 
                    yardAllocationFilter.getGrossClass(), 
                    yardAllocationFilter.getCommodity(), 
                    yardAllocationFilter.getDg(), 
                    yardAllocationFilter.getOverSize(), 
                    yardAllocationFilter.getIsImport(), noPPKB);

            for (int j = 0; j < idContainers.size(); j++) {
                yardAllocationTemp.setIdCont(idContainers.get(j));
                yardAllocationTemp.setContType(yardAllocationFilter.getContType());
                yardAllocationTemp.setCommodityCode(yardAllocationFilter.getCommodity());
                yardAllocationTemp.setContStatus(yardAllocationFilter.getContStatus());
                yardAllocationTemp.setGrossClass(yardAllocationFilter.getGrossClass());
                yardAllocationTemp.setDg(yardAllocationFilter.getDg());
                yardAllocationTemp.setOverSize(yardAllocationFilter.getOverSize());
                yardAllocationTemp.setIsImport(yardAllocationFilter.getIsImport());
                yardAllocationTemp.setNoPpkb(noPPKB);
                yardAllocationTemp.setIdYardAllocationFilter(idConstraint);
                yardAllocationTempFacadeRemote.create(yardAllocationTemp);
                yardAllocationTemp = new YardAllocationTemp();
            }
        }
        readyYard((String) masterYard.get(0)[0]);
        totalAllocationPerConstraint = 0;
        for (int i = 0; i < yardAllocations.size(); i++) {
            totalAllocationPerConstraint += ((Number) yardAllocations.get(i)[5]).intValue();
        }
    }

    public void refreshTableAllocation() {
        setBlock();
        generates = masterYardCoordinatFacadeRemote.findGenerate(noPPKB, false,false);
    }

    public void handleEditGenerate(ActionEvent event) {
        idCoordinatGenerate = ((Number) event.getComponent().getAttributes().get("idCoordinat")).intValue();
        masterYardCoordinat = masterYardCoordinatFacadeRemote.find(idCoordinatGenerate);
        //untuk mengkondisikan pemilihan yard
        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock(masterYardCoordinat.getBlock());
        counterSlot = ((Number) mYard[0]).intValue();
        counterRow = ((Number)  mYard[1]).intValue();
        counterTier = ((Number) mYard[2]).intValue();
        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
        oldMasterYardCoordinat = masterYardCoordinatFacadeRemote.find(idCoordinatGenerate);
    }

    public void saveEditGenerate() {
        boolean loggedIn = false;
        FacesContext context = FacesContext.getCurrentInstance();
        if (masterYardCoordinat.getContSize() == 20) {
            Object[] newIdCoordiant = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(masterYardCoordinat.getBlock(), masterYardCoordinat.getSlot(), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
            if (newIdCoordiant[1].toString().equalsIgnoreCase("empty")) {
                newMasterYardCoordinat = masterYardCoordinatFacadeRemote.find( ((Number) newIdCoordiant[0]).intValue());
                newMasterYardCoordinat.setContNo(masterYardCoordinat.getContNo());
                newMasterYardCoordinat.setMlo(masterYardCoordinat.getMlo());
                newMasterYardCoordinat.setContWeight(masterYardCoordinat.getContWeight());
                newMasterYardCoordinat.setPod(masterYardCoordinat.getPod());
                newMasterYardCoordinat.setNoPpkb(masterYardCoordinat.getNoPpkb());
                newMasterYardCoordinat.setStatus("planning");
                newMasterYardCoordinat.setContSize(masterYardCoordinat.getContSize());
                newMasterYardCoordinat.setContType(masterYardCoordinat.getContType());
                newMasterYardCoordinat.setGrossClass(masterYardCoordinat.getGrossClass());
                masterYardCoordinatFacadeRemote.edit(newMasterYardCoordinat);
                //yang lama dikembalikan
                oldMasterYardCoordinat.setContNo(null);
                oldMasterYardCoordinat.setMlo(null);
                oldMasterYardCoordinat.setContWeight(null);
                oldMasterYardCoordinat.setPod(null);
                oldMasterYardCoordinat.setNoPpkb(null);
                oldMasterYardCoordinat.setStatus("empty");
                oldMasterYardCoordinat.setContSize(null);
                oldMasterYardCoordinat.setContType(null);
                oldMasterYardCoordinat.setGrossClass(null);
                masterYardCoordinatFacadeRemote.edit(oldMasterYardCoordinat);
                //update yard_allocation_temp
                yardAllocationTempFacadeRemote.updateIdCoordinat((Integer) newIdCoordiant[0], idCoordinatGenerate);
                context.addMessage(null, new FacesMessage("sussesful, save new coordinat for container " + masterYardCoordinat.getContSize()));
                loggedIn = true;
            } else {
                context.addMessage(null, new FacesMessage("attention, new coordinat for container " + masterYardCoordinat.getContSize() + " is not empty"));
            }
        } else if (masterYardCoordinat.getContSize() == 40) {
            Object[] newIdCoordiant = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(masterYardCoordinat.getBlock(), masterYardCoordinat.getSlot(), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
            Object[] newIdCoordiant2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(masterYardCoordinat.getBlock(), (short) (masterYardCoordinat.getSlot() + 1), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
            if (newIdCoordiant[1].toString().equalsIgnoreCase("empty") && newIdCoordiant2[1].toString().equalsIgnoreCase("empty")) {
                newMasterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) newIdCoordiant[0]);
                newMasterYardCoordinat.setContNo(masterYardCoordinat.getContNo());
                newMasterYardCoordinat.setMlo(masterYardCoordinat.getMlo());
                newMasterYardCoordinat.setContWeight(masterYardCoordinat.getContWeight());
                newMasterYardCoordinat.setPod(masterYardCoordinat.getPod());
                newMasterYardCoordinat.setNoPpkb(masterYardCoordinat.getNoPpkb());
                newMasterYardCoordinat.setStatus("planning");
                newMasterYardCoordinat.setContSize(masterYardCoordinat.getContSize());
                newMasterYardCoordinat.setContType(masterYardCoordinat.getContType());
                newMasterYardCoordinat.setGrossClass(masterYardCoordinat.getGrossClass());
                masterYardCoordinatFacadeRemote.edit(newMasterYardCoordinat);
                //yang lama dikembalikan
                oldMasterYardCoordinat.setContNo(null);
                oldMasterYardCoordinat.setMlo(null);
                oldMasterYardCoordinat.setContWeight(null);
                oldMasterYardCoordinat.setPod(null);
                oldMasterYardCoordinat.setNoPpkb(null);
                oldMasterYardCoordinat.setStatus("empty");
                oldMasterYardCoordinat.setContSize(null);
                oldMasterYardCoordinat.setContType(null);
                oldMasterYardCoordinat.setGrossClass(null);
                masterYardCoordinatFacadeRemote.edit(oldMasterYardCoordinat);
                //twin
                newMasterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) newIdCoordiant2[0]);
                newMasterYardCoordinat.setContNo(masterYardCoordinat.getContNo());
                newMasterYardCoordinat.setMlo(masterYardCoordinat.getMlo());
                newMasterYardCoordinat.setContWeight(masterYardCoordinat.getContWeight());
                newMasterYardCoordinat.setPod(masterYardCoordinat.getPod());
                newMasterYardCoordinat.setNoPpkb(masterYardCoordinat.getNoPpkb());
                newMasterYardCoordinat.setStatus("planning");
                newMasterYardCoordinat.setContSize(masterYardCoordinat.getContSize());
                newMasterYardCoordinat.setContType(masterYardCoordinat.getContType());
                newMasterYardCoordinat.setGrossClass(masterYardCoordinat.getGrossClass());
                masterYardCoordinatFacadeRemote.edit(newMasterYardCoordinat);
                //yang lama dikembalikan
                Object[] oldIdCoordiant = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(oldMasterYardCoordinat.getBlock(), (short) (oldMasterYardCoordinat.getSlot() + 1), oldMasterYardCoordinat.getRow(), oldMasterYardCoordinat.getTier());
                oldMasterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) oldIdCoordiant[0]);
                oldMasterYardCoordinat.setContNo(null);
                oldMasterYardCoordinat.setMlo(null);
                oldMasterYardCoordinat.setContWeight(null);
                oldMasterYardCoordinat.setPod(null);
                oldMasterYardCoordinat.setNoPpkb(null);
                oldMasterYardCoordinat.setStatus("empty");
                oldMasterYardCoordinat.setContSize(null);
                oldMasterYardCoordinat.setContType(null);
                oldMasterYardCoordinat.setGrossClass(null);
                masterYardCoordinatFacadeRemote.edit(oldMasterYardCoordinat);
                //update yard_allocation_temp
                yardAllocationTempFacadeRemote.updateIdCoordinat((Integer) newIdCoordiant[0], idCoordinatGenerate);
                context.addMessage(null, new FacesMessage("sussesful, save new coordinat for container " + masterYardCoordinat.getContSize()));
                loggedIn = true;
            } else {
                context.addMessage(null, new FacesMessage("attention, new coordinat for container " + masterYardCoordinat.getContSize() + " is not empty"));
            }
        } else if (masterYardCoordinat.getContSize() == 45) {
            Object[] newIdCoordiant = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(masterYardCoordinat.getBlock(), masterYardCoordinat.getSlot(), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
            Object[] newIdCoordiant2 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(masterYardCoordinat.getBlock(), (short) (masterYardCoordinat.getSlot() + 1), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
            Object[] newIdCoordiant3 = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(masterYardCoordinat.getBlock(), (short) (masterYardCoordinat.getSlot() + 2), masterYardCoordinat.getRow(), masterYardCoordinat.getTier());
            if (newIdCoordiant[1].toString().equalsIgnoreCase("empty") && newIdCoordiant2[1].toString().equalsIgnoreCase("empty")) {
                newMasterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) newIdCoordiant[0]);
                newMasterYardCoordinat.setContNo(masterYardCoordinat.getContNo());
                newMasterYardCoordinat.setMlo(masterYardCoordinat.getMlo());
                newMasterYardCoordinat.setContWeight(masterYardCoordinat.getContWeight());
                newMasterYardCoordinat.setPod(masterYardCoordinat.getPod());
                newMasterYardCoordinat.setNoPpkb(masterYardCoordinat.getNoPpkb());
                newMasterYardCoordinat.setStatus("planning");
                newMasterYardCoordinat.setContSize(masterYardCoordinat.getContSize());
                newMasterYardCoordinat.setContType(masterYardCoordinat.getContType());
                newMasterYardCoordinat.setGrossClass(masterYardCoordinat.getGrossClass());
                masterYardCoordinatFacadeRemote.edit(newMasterYardCoordinat);
                //yang lama dikembalikan
                oldMasterYardCoordinat.setContNo(null);
                oldMasterYardCoordinat.setMlo(null);
                oldMasterYardCoordinat.setContWeight(null);
                oldMasterYardCoordinat.setPod(null);
                oldMasterYardCoordinat.setNoPpkb(null);
                oldMasterYardCoordinat.setStatus("empty");
                oldMasterYardCoordinat.setContSize(null);
                oldMasterYardCoordinat.setContType(null);
                oldMasterYardCoordinat.setGrossClass(null);
                masterYardCoordinatFacadeRemote.edit(oldMasterYardCoordinat);
                //twin
                newMasterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) newIdCoordiant2[0]);
                newMasterYardCoordinat.setContNo(masterYardCoordinat.getContNo());
                newMasterYardCoordinat.setMlo(masterYardCoordinat.getMlo());
                newMasterYardCoordinat.setContWeight(masterYardCoordinat.getContWeight());
                newMasterYardCoordinat.setPod(masterYardCoordinat.getPod());
                newMasterYardCoordinat.setNoPpkb(masterYardCoordinat.getNoPpkb());
                newMasterYardCoordinat.setStatus("planning");
                newMasterYardCoordinat.setContSize(masterYardCoordinat.getContSize());
                newMasterYardCoordinat.setContType(masterYardCoordinat.getContType());
                newMasterYardCoordinat.setGrossClass(masterYardCoordinat.getGrossClass());
                masterYardCoordinatFacadeRemote.edit(newMasterYardCoordinat);
                //yang lama dikembalikan
                Object[] oldIdCoordiant = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(oldMasterYardCoordinat.getBlock(), (short) (oldMasterYardCoordinat.getSlot() + 1), oldMasterYardCoordinat.getRow(), oldMasterYardCoordinat.getTier());
                oldMasterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) oldIdCoordiant[0]);
                oldMasterYardCoordinat.setContNo(null);
                oldMasterYardCoordinat.setMlo(null);
                oldMasterYardCoordinat.setContWeight(null);
                oldMasterYardCoordinat.setPod(null);
                oldMasterYardCoordinat.setNoPpkb(null);
                oldMasterYardCoordinat.setStatus("empty");
                oldMasterYardCoordinat.setContSize(null);
                oldMasterYardCoordinat.setContType(null);
                oldMasterYardCoordinat.setGrossClass(null);
                masterYardCoordinatFacadeRemote.edit(oldMasterYardCoordinat);
                //twin
                newMasterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) newIdCoordiant3[0]);
                newMasterYardCoordinat.setContNo(masterYardCoordinat.getContNo());
                newMasterYardCoordinat.setMlo(masterYardCoordinat.getMlo());
                newMasterYardCoordinat.setContWeight(masterYardCoordinat.getContWeight());
                newMasterYardCoordinat.setPod(masterYardCoordinat.getPod());
                newMasterYardCoordinat.setNoPpkb(masterYardCoordinat.getNoPpkb());
                newMasterYardCoordinat.setStatus("planning");
                newMasterYardCoordinat.setContSize(masterYardCoordinat.getContSize());
                newMasterYardCoordinat.setContType(masterYardCoordinat.getContType());
                newMasterYardCoordinat.setGrossClass(masterYardCoordinat.getGrossClass());
                masterYardCoordinatFacadeRemote.edit(newMasterYardCoordinat);
                //yang lama dikembalikan
                oldIdCoordiant = masterYardCoordinatFacadeRemote.findIdStatusByCoordinat(oldMasterYardCoordinat.getBlock(), (short) (oldMasterYardCoordinat.getSlot() + 1), oldMasterYardCoordinat.getRow(), oldMasterYardCoordinat.getTier());
                oldMasterYardCoordinat = masterYardCoordinatFacadeRemote.find((Integer) oldIdCoordiant[0]);
                oldMasterYardCoordinat.setContNo(null);
                oldMasterYardCoordinat.setMlo(null);
                oldMasterYardCoordinat.setContWeight(null);
                oldMasterYardCoordinat.setPod(null);
                oldMasterYardCoordinat.setNoPpkb(null);
                oldMasterYardCoordinat.setStatus("empty");
                oldMasterYardCoordinat.setContSize(null);
                oldMasterYardCoordinat.setContType(null);
                oldMasterYardCoordinat.setGrossClass(null);
                masterYardCoordinatFacadeRemote.edit(oldMasterYardCoordinat);
                //update yard_allocation_temp
                yardAllocationTempFacadeRemote.updateIdCoordinat((Integer) newIdCoordiant[0], idCoordinatGenerate);
                context.addMessage(null, new FacesMessage("sussesful, save new coordinat for container " + masterYardCoordinat.getContSize()));
                loggedIn = true;
            } else {
                context.addMessage(null, new FacesMessage("attention, new coordinat for container " + masterYardCoordinat.getContSize() + " is not empty"));
            }
        }
        //update view
        generates = masterYardCoordinatFacadeRemote.findGenerate(noPPKB, false,false);
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
    }

    private void setBlock(){
        yardAllocationFilters = yardAllocationFilterFacadeRemote.yardAllocationFilterfindByNoPpkb(noPPKB, "FALSE", "FALSE");
        for(Object[] o : yardAllocationFilters){
            List<Object[]> yardAllocat = yardAllocationFacadeRemote.findAllByYardAllocationFilter(((Number) o[8]).intValue());
            String block = "";
            String bl = "";
            for(Object[] ob : yardAllocat){
                if(!bl.equalsIgnoreCase((String) ob[0])){
                    if(block.equals(""))
                        block = (String) ob[0];
                    else
                        block = block + ", " + (String) ob[0];
                }
                bl = (String) ob[0];
            }
            o[9] = block;
        }
    }

    public void readyYard(String block) {
        //untuk mengkondisikan pemilihan yard
        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock(block);
        counterSlot = ((Number) mYard[0]).intValue();
        counterRow = ((Number) mYard[1]).intValue();
        counterTier = ((Number) mYard[2]).intValue();
        slots = new LinkedHashMap<Integer, Integer>();
        rows = new LinkedHashMap<Integer, Integer>();
        tiers = new LinkedHashMap<Integer, Integer>();
        //untuk membuat nilai max
        yardAllocation = new YardAllocation();
        yardAllocation.setToSlot(Short.parseShort(mYard[0].toString()));
        yardAllocation.setToRow(Short.parseShort(mYard[1].toString()));
        yardAllocations = yardAllocationFacadeRemote.findAllByYardAllocationFilter(idConstraint);
        handleFrSlotChanged();
    }

    public void onChangeEvent(ValueChangeEvent event) {
        String newItem = (String) event.getNewValue();
        Object[] mYard = masterYardFacadeRemote.findMasterYardByBlock(newItem);
        Integer slot = ((Number) mYard[0]).intValue();
        Integer row = ((Number) mYard[1]).intValue();
        Integer tier = ((Number) mYard[2]).intValue();
        getSlots().clear();
        getRows().clear();
        getTiers().clear();
        counterSlot = slot;
        counterRow = row;
        counterTier = tier;
        yardAllocation.setToSlot(Short.parseShort(mYard[0].toString()));
        yardAllocation.setToRow(Short.parseShort(mYard[1].toString()));
    }

    public void handleFrSlotChanged() {
        short teus = (short) Math.ceil((double) yardAllocationFilter.getContSize() / (double) 20);

        if ((yardAllocation.getToSlot() - yardAllocation.getFrSlot()) < (teus - 1)) {
            yardAllocation.setToSlot((short) (yardAllocation.getFrSlot() + teus - 1));
        }

        slots = getSlots();

        if (yardAllocation.getToSlot() > slots.size()) {
            yardAllocation.setToSlot(slots.get(slots.size()).shortValue());
            yardAllocation.setFrSlot((short) (yardAllocation.getToSlot() - teus + 1));
        }
    }

    public void handleToSlotChanged() {
        short teus = (short) Math.ceil((double) yardAllocationFilter.getContSize() / (double) 20);

        if ((yardAllocation.getToSlot() - yardAllocation.getFrSlot()) < (teus - 1)) {
            yardAllocation.setFrSlot((short) (yardAllocation.getToSlot() - teus + 1));
        }

        if (yardAllocation.getFrSlot() < 1) {
            yardAllocation.setFrSlot((short) 1);
            yardAllocation.setToSlot((short) (yardAllocation.getFrSlot() + teus - 1));
        }
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
     * @return the yardAllocationFilters
     */
    public List<Object[]> getYardAllocationFilters() {
        return yardAllocationFilters;
    }

    /**
     * @return the yardAllocations
     */
    public List<Object[]> getYardAllocations() {
        return yardAllocations;
    }

    /**
     * @return the yardAllocation
     */
    public YardAllocation getYardAllocation() {
        return yardAllocation;
    }

    /**
     * @param yardAllocation the yardAllocation to set
     */
    public void setYardAllocation(YardAllocation yardAllocation) {
        this.yardAllocation = yardAllocation;
    }

    /**
     * @return the masterYard
     */
    public List<Object[]> getMasterYard() {
        return masterYard;
    }

    /**
     * @return the constraint
     */
    public Object[] getConstraint() {
        return constraint;
    }

    /**
     * @return the totalAllocationPerConstraint
     */
    public int getTotalAllocationPerConstraint() {
        return totalAllocationPerConstraint;
    }

    /**
     * @return the generates
     */
    public List<Object[]> getGenerates() {
        return generates;
    }

    public MasterYardCoordinat getMasterYardCoordinat() {
        return masterYardCoordinat;
    }

    public void setMasterYardCoordinat(MasterYardCoordinat masterYardCoordinat) {
        this.masterYardCoordinat = masterYardCoordinat;
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
}
