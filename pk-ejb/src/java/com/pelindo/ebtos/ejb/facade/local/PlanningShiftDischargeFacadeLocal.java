/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.exception.ReceivingAllocationCont40ftIsNotEnoughException;
import com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException;
import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import com.pelindo.ebtos.model.db.PlanningVessel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface PlanningShiftDischargeFacadeLocal {

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
    void allocateShiftingToReceivingAllocation(PlanningVessel planningVessel, String contListAsString, List<Object[]> nonToCyShiftings, List<Object[]> toCyShiftings, List<Object[]> loadPlanningAllocationsInfo, List<Object[]> notSavedObjects) throws ReceivingAllocationIsNotEnoughException, ReceivingAllocationCont40ftIsNotEnoughException;

    List<Integer> findBaplieByConstraint(int cont_type, String cont_status, String gross_class, boolean dg, String no_ppkb);

    List<PlanningShiftDischarge> findByNoPpkb(String noPpkb);

    PlanningShiftDischarge findByPpkbAndContNo(String noPpkb, String contNo);

    List<Object[]> findPlanningShiftDischargeListNative(String no_ppkb);

    Object[] findPlanningShiftDischargesById(int id);

    List<Object[]> findPlanningShiftDischargesByPPKB(String no_ppkb);

    PlanningShiftDischarge findShiftableContainer(String noPpkb, String contNo);

    List<Object[]> generateConstraintsByPPKB(String no_ppkb);

    int unFinishBayPlanDischargeByPPKB(String no_ppkb);

    public void edit(PlanningShiftDischarge planningShiftDischarge);

}
