/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.PlanningTransDischargeFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningTransDischarge;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class PlanningTransDischargeFacade extends AbstractFacade<PlanningTransDischarge> implements PlanningTransDischargeFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningTransDischargeFacade() {
        super(PlanningTransDischarge.class);
    }

    @Override
    public List<Object[]> findPlanningTransDischargeListNative(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("PlanningTransDischarge.Native.findPlanningTransDischargesByPpkb").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<PlanningTransDischarge> findByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("PlanningTransDischarge.findByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    public List<Object[]> findPlanningTransDischargesByPpkbTranshipment(String no_ppkb, String new_ppkb) {
        return getEntityManager().createNamedQuery("PlanningTransDischarge.Native.findPlanningTransDischargesByPpkbTranshipment").setParameter(1, no_ppkb).setParameter(2, new_ppkb).getResultList();
    }

    public int unFinishBayPlanDischargeByPPKB(String no_ppkb) {
        return getEntityManager().createNamedQuery("PlanningTransDischarge.Native.unFinishBayPlanDischargeByPPKB").setParameter(1, no_ppkb).executeUpdate();
    }

    public List<Object[]> findAllPlanningTransDischarges(String no_ppkb) {
        return getEntityManager().createNamedQuery("PlanningTransDischarge.Native.findAllPlanningTransDischarges").setParameter(1, no_ppkb).getResultList();
    }

//    public List<Integer> findBaplieByConstraint (int cont_type, String cont_status, String gross_class, String commodity, boolean dg,String no_ppkb){
//        return  getEntityManager().createNamedQuery("PlanningTransDischarge.Native.findBaplieByConstraint")
//                .setParameter(1, cont_type)
//                .setParameter(2, cont_status)
//                .setParameter(3, gross_class)
//                .setParameter(4, commodity)
//                .setParameter(5, dg)
//                .setParameter(6, no_ppkb)
//                .getResultList();
//    }

    public List<Integer> findBaplieByConstraint (int cont_type, String cont_status, String gross_class, String commodity, boolean dg,String no_ppkb){
        return  getEntityManager().createNamedQuery("PlanningTransDischarge.Native.findBaplieByConstraint")
                .setParameter(1, cont_type)
                .setParameter(2, cont_status)
                .setParameter(3, gross_class)
                //.setParameter(4, commodity)
                .setParameter(4, dg)
                .setParameter(5, no_ppkb)
                .getResultList();
    }

    public List<Object[]> generateConstraintsByPPKB(String no_ppkb) {
        return getEntityManager().createNamedQuery("PlanningTransDischarge.Native.generateConstraintsByPPKB").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findPlanningTranshipmentMonitoring(String no_ppkb) {
        return getEntityManager().createNamedQuery("PlanningTransDischarge.Native.findPlanningTranshipmentMonitoring").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findNativeByPpkb(String no_ppkb) {
        return getEntityManager().createNamedQuery("PlanningTransDischarge.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
    }
}
