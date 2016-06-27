/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganPassGateFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPassGateFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganPassGate;
import com.pelindo.ebtos.model.db.PlanningVessel;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author x42jr
 */
@Stateless
public class PerhitunganPassGateFacade extends AbstractFacade<PerhitunganPassGate> implements PerhitunganPassGateFacadeRemote, PerhitunganPassGateFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganPassGateFacade() {
        super(PerhitunganPassGate.class);
    }

    public Integer deleteByJobSlip(String jobSlip) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganPassGate.deleteByJobSlip")
                .setParameter("jobSlip", jobSlip)
                .executeUpdate();
    }

    public Integer deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganPassGate.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    @Override
    public PerhitunganPassGate findByJobSlip(String jobSlip) {
        try {
            return (PerhitunganPassGate) getEntityManager().createNamedQuery("PerhitunganPassGate.findByJobSlip")
                    .setParameter("jobSlip", jobSlip)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public int updatePlanningVessel(PlanningVessel newValue, PlanningVessel oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganPassGate.updatePlanningVessel")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }
}
