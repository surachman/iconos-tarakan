/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PlanningContDischarge;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface PlanningContDischargeFacadeRemote {

    void create(PlanningContDischarge planningContDischarge);

    void edit(PlanningContDischarge planningContDischarge);

    void remove(PlanningContDischarge planningContDischarge);

    PlanningContDischarge find(Object id);

    List<PlanningContDischarge> findAll();

    List<PlanningContDischarge> findRange(int[] range);

    int count();

    List<Object[]> findPlanningContDischargeListNative(String no_ppkb);

    List<Object[]> findPlanningContDischarges();

    List<Object[]> findPlanningContDischargesByPPKB(String no_ppkb);

    List<Integer> findBaplieByConstraint (int cont_type, String cont_status, String gross_class, String commodity, boolean dg, boolean overSize, boolean isImport,String no_ppkb);

    List<Object[]> findIdGenerateByPpkb(String no_ppkb, String id_constraint);

    long countByPPKB(String no_ppkb);

    List<Object[]> generateConstraintsByPPKB(String no_ppkb);

    int unFinishBayPlanDischargeByPPKB(String no_ppkb);

    List<Object[]> monitoringContainer(String cont_no);

    public java.util.List<com.pelindo.ebtos.model.db.PlanningContDischarge> findByNoPpkb(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.PlanningContDischarge findByContNoAndNoPpkb(java.lang.String noPpkb, java.lang.String contNo);
}
