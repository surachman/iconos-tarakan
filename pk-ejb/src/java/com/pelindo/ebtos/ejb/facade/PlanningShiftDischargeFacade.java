/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PlanningShiftDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.BaplieDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterCommodityFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterContainerTypeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterCustomerFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardCoordinatFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PlanningShiftDischargeFacadeRemote;
import com.pelindo.ebtos.exception.ReceivingAllocationCont40ftIsNotEnoughException;
import com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException;
import com.pelindo.ebtos.model.db.BaplieDischarge;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.qtasnim.text.SqlHelper;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class PlanningShiftDischargeFacade extends AbstractFacade<PlanningShiftDischarge> implements PlanningShiftDischargeFacadeRemote, PlanningShiftDischargeFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @EJB
    private MasterYardCoordinatFacadeLocal masterYardCoordinatFacadeLocal;
    @EJB
    private BaplieDischargeFacadeLocal baplieDischargeFacadeLocal;
    @EJB
    private MasterCustomerFacadeLocal masterCustomerFacadeLocal;
    @EJB
    private MasterContainerTypeFacadeLocal masterContainerTypeFacadeLocal;
    @EJB
    private MasterCommodityFacadeLocal masterCommodityFacadeLocal;
    @EJB
    private MasterYardFacadeLocal masterYardFacadeLocal;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningShiftDischargeFacade() {
        super(PlanningShiftDischarge.class);
    }

    public List<Object[]> findPlanningShiftDischargeListNative(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("PlanningShiftDischarge.Native.findPlanningShiftDischargesByPpkb").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findPlanningShiftDischargesByPPKB(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningShiftDischarge.Native.findPlanningShiftDischargesByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }


    public PlanningShiftDischarge findByPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (PlanningShiftDischarge) getEntityManager().createNamedQuery("PlanningShiftDischarge.findByPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public PlanningShiftDischarge findShiftableContainer(String noPpkb, String contNo) {
        try {
            return (PlanningShiftDischarge) getEntityManager().createNamedQuery("PlanningShiftDischarge.findShiftableContainer")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<PlanningShiftDischarge> findByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("PlanningShiftDischarge.findByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    public int unFinishBayPlanDischargeByPPKB(String no_ppkb) {
        return getEntityManager().createNamedQuery("PlanningShiftDischarge.Native.unFinishBayPlanDischargeByPPKB").setParameter(1, no_ppkb).executeUpdate();
    }

    public Object[] findPlanningShiftDischargesById(int id){
        return objectDecimalsToObjectInts( (Object[])
                getEntityManager().createNamedQuery("PlanningShiftDischarge.Native.findPlanningShiftDischargesById").setParameter(1, id).getSingleResult()
        );
    }

     public List<Object[]> generateConstraintsByPPKB(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningShiftDischarge.Native.generateConstraintsByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Integer> findBaplieByConstraint (int cont_type, String cont_status, String gross_class, boolean dg,String no_ppkb){
        return  decimalsToInts(
                getEntityManager().createNamedQuery("PlanningShiftDischarge.Native.findBaplieByConstraint")
                .setParameter(1, cont_type)
                .setParameter(2, cont_status)
                .setParameter(3, gross_class)
                .setParameter(4, dg)
                .setParameter(5, no_ppkb)
                .getResultList()
        );
    }

    /**
     *
     * @param planningVessel
     * @param contListAsString
     * @param nonToCyShiftings (typeof BaplieDischargeFacadeRemote.findBaplieDischargesByPpkbShifting)
     * @param toCyShiftings (typeof BaplieDischargeFacade.findBaplieDischargesByPpkbShifting)
     * @param loadPlanningAllocationsInfo (typeof MasterYardCoordinatFacade.findLoadPlanningAllocations)
     * @param notSavedObjects (typeof BaplieDischargeFacade.findBaplieDischargesByPpkbShifting)
     * @throws ReceivingAllocationIsNotEnoughException
     * @throws ReceivingAllocationCont40ftIsNotEnoughException
     */
    public void allocateShiftingToReceivingAllocation(PlanningVessel planningVessel, String contListAsString, List<Object[]> nonToCyShiftings, List<Object[]> toCyShiftings, List<Object[]> loadPlanningAllocationsInfo, List<Object[]> notSavedObjects) throws ReceivingAllocationIsNotEnoughException, ReceivingAllocationCont40ftIsNotEnoughException {
        if (!toCyShiftings.isEmpty()) {
            for (Object[] constraint: loadPlanningAllocationsInfo) {
                Long required = (Long) constraint[10];
                Long allocated = (Long) constraint[11];

                if (allocated < required) {
                    notSavedObjects.addAll(toCyShiftings);
                    notSavedObjects.addAll(nonToCyShiftings);
                    throw new ReceivingAllocationIsNotEnoughException();
                }
            }

            for (Object[] constraint: loadPlanningAllocationsInfo) {
                String portCode = (String) constraint[0];
                List<Object[]> shiftContainers = baplieDischargeFacadeLocal.findShiftingContainerByAllocationConstraint(planningVessel.getNoPpkb(), portCode, (Integer) constraint[5], (Integer) constraint[1], (String) constraint[6], (String) constraint[3], (Integer) constraint[7], (Integer) constraint[8], (Integer) constraint[9], contListAsString);
                List<MasterYardCoordinat> plannedCoordinates = masterYardCoordinatFacadeLocal.findAvailableCoordinateByAllocationConstraint(planningVessel.getNoPpkb(), portCode, (Integer) constraint[5], (Integer) constraint[1], (String) constraint[6], (String) constraint[3], (Integer) constraint[7], (Integer) constraint[8], (Integer) constraint[9]);
                Integer coordinateIndex = 0;

                for (Object[] container: shiftContainers) {
                    MasterCustomer mlo = masterCustomerFacadeLocal.find((String) container[3]);
                    MasterContainerType masterContainerType = masterContainerTypeFacadeLocal.find((Integer) container[2]);
                    MasterCommodity masterCommodity = masterCommodityFacadeLocal.find((String) container[4]);

                    PlanningShiftDischarge planningShiftDischarge = new PlanningShiftDischarge();
                    planningShiftDischarge.setId((Integer) container[0]);
                    planningShiftDischarge.setBlNo((String) container[1]);
                    planningShiftDischarge.setMasterContainerType(masterContainerType);
                    planningShiftDischarge.setMlo(mlo);
                    planningShiftDischarge.setMasterCommodity(masterCommodity);
                    planningShiftDischarge.setPlanningVessel(planningVessel);
                    planningShiftDischarge.setContNo((String) container[6]);
                    planningShiftDischarge.setContSize(((Integer) container[7]).shortValue());
                    planningShiftDischarge.setContStatus((String) container[8]);
                    planningShiftDischarge.setContGross((Integer) container[9]);
                    planningShiftDischarge.setSealNo((String) container[10]);
                    planningShiftDischarge.setDg((String) container[11]);
                    planningShiftDischarge.setDgLabel((String) container[12]);
                    planningShiftDischarge.setOverSize((String) container[13]);
                    planningShiftDischarge.setIsExportImport((String) container[21]);
                    planningShiftDischarge.setTwentyOneFeet((String) container[22]);
                    planningShiftDischarge.setTradeType((String) container[14]);
                    planningShiftDischarge.setLoadPort((String) container[15]);
                    planningShiftDischarge.setDischPort((String) container[16]);
                    planningShiftDischarge.setVBay(((Integer) container[17]).shortValue());
                    planningShiftDischarge.setVRow(((Integer) container[18]).shortValue());
                    planningShiftDischarge.setVTier(((Integer) container[19]).shortValue());
                    planningShiftDischarge.setGrossClass((String) container[20]);
                    planningShiftDischarge.setShiftTo("TOCY");

                    MasterYardCoordinat coordinate = null;

                    if (planningShiftDischarge.getContSize() == 20) {
                        coordinate = plannedCoordinates.get(coordinateIndex);
                        coordinate.setContNo(planningShiftDischarge.getContNo());
                        coordinate.setContWeight(planningShiftDischarge.getContGross());
                        coordinate.setPod(planningShiftDischarge.getDischPort());
                        coordinate.setMlo(planningShiftDischarge.getMlo());
                        coordinate.setBlNo(planningShiftDischarge.getBlNo());
                    } else if (planningShiftDischarge.getContSize() == 40) {
                        coordinate = plannedCoordinates.get(coordinateIndex);
                        coordinateIndex++;

                        while (coordinateIndex < plannedCoordinates.size()) {
                            MasterYardCoordinat coordinat40ft = plannedCoordinates.get(coordinateIndex);

                            if (coordinat40ft.getBlock().equals(coordinate.getBlock()) && coordinat40ft.getSlot() == (coordinate.getSlot() + 1) && coordinat40ft.getRow().equals(coordinate.getRow()) && coordinat40ft.getTier().equals(coordinate.getTier())) {
                                coordinate.setContNo(planningShiftDischarge.getContNo());
                                coordinate.setContWeight(planningShiftDischarge.getContGross());
                                coordinate.setPod(planningShiftDischarge.getDischPort());
                                coordinate.setMlo(planningShiftDischarge.getMlo());
                                coordinate.setBlNo(planningShiftDischarge.getBlNo());
                                coordinat40ft.setContNo(planningShiftDischarge.getContNo());
                                coordinat40ft.setMlo(planningShiftDischarge.getMlo());
                                coordinat40ft.setContWeight(planningShiftDischarge.getContGross());
                                coordinat40ft.setPod(planningShiftDischarge.getDischPort());
                                coordinat40ft.setBlNo(planningShiftDischarge.getBlNo());
                                masterYardCoordinatFacadeLocal.edit(coordinat40ft);
                                break;
                            }

                            coordinate = plannedCoordinates.get(coordinateIndex);
                            coordinateIndex++;
                        }

                        if (coordinateIndex == plannedCoordinates.size()) {
                            notSavedObjects.add(container);
                            notSavedObjects.addAll(nonToCyShiftings);
                            throw new ReceivingAllocationCont40ftIsNotEnoughException();
                        }
                    } else if (planningShiftDischarge.getContSize() == 45) {
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
                                coordinate.setContNo(planningShiftDischarge.getContNo());
                                coordinate.setContWeight(planningShiftDischarge.getContGross());
                                coordinate.setPod(planningShiftDischarge.getDischPort());
                                coordinate.setMlo(planningShiftDischarge.getMlo());
                                coordinate.setBlNo(planningShiftDischarge.getBlNo());

                                coordinat40ft.setContNo(planningShiftDischarge.getContNo());
                                coordinat40ft.setMlo(planningShiftDischarge.getMlo());
                                coordinat40ft.setContWeight(planningShiftDischarge.getContGross());
                                coordinat40ft.setPod(planningShiftDischarge.getDischPort());
                                coordinat40ft.setBlNo(planningShiftDischarge.getBlNo());

                                coordinat45ft.setContNo(planningShiftDischarge.getContNo());
                                coordinat45ft.setMlo(planningShiftDischarge.getMlo());
                                coordinat45ft.setContWeight(planningShiftDischarge.getContGross());
                                coordinat45ft.setPod(planningShiftDischarge.getDischPort());
                                coordinat45ft.setBlNo(planningShiftDischarge.getBlNo());

                                masterYardCoordinatFacadeLocal.edit(coordinat40ft);
                                masterYardCoordinatFacadeLocal.edit(coordinat45ft);
                                break;
                            }

                            coordinate = plannedCoordinates.get(coordinateIndex);
                            coordinateIndex++;
                        }

                        if (coordinateIndex == plannedCoordinates.size()) {
                            notSavedObjects.add(container);
                            notSavedObjects.addAll(nonToCyShiftings);
                            throw new ReceivingAllocationCont40ftIsNotEnoughException();
                        }
                    }

                    masterYardCoordinatFacadeLocal.edit(coordinate);

                    MasterYard masterYard = masterYardFacadeLocal.find(coordinate.getBlock());
                    planningShiftDischarge.setMasterYard(masterYard);
                    planningShiftDischarge.setYSlot(coordinate.getSlot());
                    planningShiftDischarge.setYRow(coordinate.getRow());
                    planningShiftDischarge.setYTier(coordinate.getTier());
                    create(planningShiftDischarge);
                    SqlHelper.removeItemFromSqlString(contListAsString, planningShiftDischarge.getContNo());
                    coordinateIndex++;
                }
            }
        }

        for (Object[] container: nonToCyShiftings) {
            BaplieDischarge baplieDischarge = baplieDischargeFacadeLocal.find(((Number) container[14]).intValue());
            PlanningShiftDischarge planningShiftDischarge = new PlanningShiftDischarge();
            planningShiftDischarge.setBlNo(baplieDischarge.getBlNo());
            planningShiftDischarge.setId(baplieDischarge.getId());
            planningShiftDischarge.setMasterContainerType(baplieDischarge.getMasterContainerType());
            planningShiftDischarge.setMasterCommodity(baplieDischarge.getMasterCommodity());
            planningShiftDischarge.setPlanningVessel(planningVessel);
            planningShiftDischarge.setContNo(baplieDischarge.getContNo());
            planningShiftDischarge.setMlo(baplieDischarge.getMlo());
            planningShiftDischarge.setContSize(baplieDischarge.getContSize());
            planningShiftDischarge.setContStatus(baplieDischarge.getContStatus());
            planningShiftDischarge.setContGross(baplieDischarge.getContGross());
            planningShiftDischarge.setSealNo(baplieDischarge.getSealNo());
            planningShiftDischarge.setDg(baplieDischarge.getDg());
            planningShiftDischarge.setDgLabel(baplieDischarge.getDgLabel());
            planningShiftDischarge.setOverSize(baplieDischarge.getOverSize());
            planningShiftDischarge.setTwentyOneFeet(baplieDischarge.getTwentyOneFeet());
            planningShiftDischarge.setTradeType(baplieDischarge.getTradeType());
            planningShiftDischarge.setLoadPort(baplieDischarge.getMasterPort().getPortCode());
            planningShiftDischarge.setDischPort(baplieDischarge.getMasterPort1().getPortCode());
            planningShiftDischarge.setVBay(baplieDischarge.getVBay());
            planningShiftDischarge.setVRow(baplieDischarge.getVRow());
            planningShiftDischarge.setVTier(baplieDischarge.getVTier());
            planningShiftDischarge.setShiftTo((String) container[13]);
            planningShiftDischarge.setGrossClass(baplieDischarge.getGrossClass());
            create(planningShiftDischarge);
        }
    }
}
