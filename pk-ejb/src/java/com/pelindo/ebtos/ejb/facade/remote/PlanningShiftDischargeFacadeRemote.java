/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PlanningShiftDischarge;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface PlanningShiftDischargeFacadeRemote {

    void create(PlanningShiftDischarge planningShiftDischarge);

    void edit(PlanningShiftDischarge planningShiftDischarge);

    void remove(PlanningShiftDischarge planningShiftDischarge);

    PlanningShiftDischarge find(Object id);

    List<PlanningShiftDischarge> findAll();

    List<PlanningShiftDischarge> findRange(int[] range);

    List<Object[]> findPlanningShiftDischargeListNative(String no_ppkb);

    List<Object[]> findPlanningShiftDischargesByPPKB(String no_ppkb);

    int count();

    int unFinishBayPlanDischargeByPPKB(String no_ppkb);

    Object[] findPlanningShiftDischargesById(int id);

    public java.util.List<com.pelindo.ebtos.model.db.PlanningShiftDischarge> findByNoPpkb(java.lang.String noPpkb);

    List<Integer> findBaplieByConstraint (int cont_type, String cont_status, String gross_class, boolean dg,String no_ppkb);

    List<Object[]> generateConstraintsByPPKB(String no_ppkb);

    public void allocateShiftingToReceivingAllocation(com.pelindo.ebtos.model.db.PlanningVessel planningVessel, java.lang.String contListAsString, java.util.List<java.lang.Object[]> nonToCyShiftings, java.util.List<java.lang.Object[]> toCyShiftings, java.util.List<java.lang.Object[]> loadPlanningAllocationsInfo, java.util.List<java.lang.Object[]> notSavedObjects) throws com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException, com.pelindo.ebtos.exception.ReceivingAllocationCont40ftIsNotEnoughException;

    public com.pelindo.ebtos.model.db.PlanningShiftDischarge findByPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.PlanningShiftDischarge findShiftableContainer(java.lang.String noPpkb, java.lang.String contNo);

}
