/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PlanningTransDischarge;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface PlanningTransDischargeFacadeRemote {

    void create(PlanningTransDischarge planningTransDischarge);

    void edit(PlanningTransDischarge planningTransDischarge);

    void remove(PlanningTransDischarge planningTransDischarge);

    PlanningTransDischarge find(Object id);

    List<PlanningTransDischarge> findAll();

    List<PlanningTransDischarge> findRange(int[] range);

    int count();

    List<Object[]> findPlanningTransDischargeListNative (String no_ppkb);

    List<Object[]> findPlanningTransDischargesByPpkbTranshipment (String no_ppkb, String new_ppkb);

    int unFinishBayPlanDischargeByPPKB(String no_ppkb);

    List<Object[]> findAllPlanningTransDischarges(String no_ppkb);

    List<Integer> findBaplieByConstraint (int cont_type, String cont_status, String gross_class, String commodity, boolean dg,String no_ppkb);

    List<Object[]> generateConstraintsByPPKB(String no_ppkb);

    public java.util.List<com.pelindo.ebtos.model.db.PlanningTransDischarge> findByNoPpkb(java.lang.String noPpkb);

    List<Object[]> findPlanningTranshipmentMonitoring(String no_ppkb);

    List<Object[]> findNativeByPpkb(String no_ppkb);
}
