/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PlanningContDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PlanningContDischargeFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class PlanningContDischargeFacade extends AbstractFacade<PlanningContDischarge> implements PlanningContDischargeFacadeRemote, PlanningContDischargeFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    public PlanningContDischargeFacade() {
        super(PlanningContDischarge.class);
    }

    @Override
    public List<Object[]> findPlanningContDischargeListNative (String no_ppkb){
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("PlanningContDischarge.Native.findPlanningContDischargesByPpkb").setParameter(1, no_ppkb).getResultList()
            );        
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
    public List<Object[]> findPlanningContDischarges(){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContDischarge.Native.findPlanningContDischarges").getResultList()
        );
    }

    public List<Object[]> findPlanningContDischargesByPPKB (String no_ppkb){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContDischarge.Native.findPlanningContDischargesByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Integer> findBaplieByConstraint (int cont_type, String cont_status, String gross_class, String commodity, String dg, String overSize, String isImport,String no_ppkb){
        return decimalsToInts(getEntityManager().createNamedQuery("PlanningContDischarge.Native.findBaplieByConstraint")
                .setParameter(1, cont_type)
                .setParameter(2, cont_status)
                .setParameter(3, gross_class)
                .setParameter(4, dg)
                .setParameter(5, overSize)
                .setParameter(6, isImport)
                .setParameter(7, no_ppkb)
                .getResultList());
    }
    
   
    public List<Object[]> findIdGenerateByPpkb(String no_ppkb, String id_constraint) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContDischarge.Native.findIdGenerateByPpkb").setParameter(1, no_ppkb).setParameter(2, id_constraint).getResultList()
        );
    }

    public Object[] findContOnBay(String noPpkb, int bay, int row, int tier) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PlanningContDischarge.Native.findContOnBay")
                    .setParameter(1, noPpkb)
                    .setParameter(2, bay)
                    .setParameter(3, row)
                    .setParameter(4, tier)
                    .getSingleResult()
            );
        } catch(NoResultException e) {
            return null;
        }
    }

    public PlanningContDischarge findByContNoAndNoPpkb(String noPpkb, String contNo) {
        try {
            return (PlanningContDischarge) getEntityManager().createNamedQuery("PlanningContDischarge.findByContNoAndNoPpkb")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
    
    public long countByPPKB(String no_ppkb) {
        return (Long) getEntityManager().createNamedQuery("PlanningContDischarge.Native.countByPPKB").setParameter(1, no_ppkb).getSingleResult();
    }

    public List<Object[]> generateConstraintsByPPKB(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContDischarge.Native.generateConstraintsByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }

    public int unFinishBayPlanDischargeByPPKB(String no_ppkb) {
        return getEntityManager().createNamedQuery("PlanningContDischarge.Native.unFinishBayPlanDischargeByPPKB").setParameter(1, no_ppkb).executeUpdate();
    }

    public List<Object[]> monitoringContainer(String cont_no) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PlanningContDischarge.Native.monitoringContainer").setParameter(1, cont_no).setParameter(2, cont_no).getResultList()
        );
    }

    public List<PlanningContDischarge> findByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("PlanningContDischarge.findByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    public int deleteByContNoAndNoPpkb(String contNo, String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PlanningContDischarge.deleteByContNoAndNoPpkb")
                .setParameter("contNo", contNo)
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
}
