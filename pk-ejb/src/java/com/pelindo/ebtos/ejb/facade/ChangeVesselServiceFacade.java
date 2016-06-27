/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ChangeVesselServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ChangeVesselServiceFacadeRemote;
import com.pelindo.ebtos.model.db.ChangeVesselService;
import com.pelindo.ebtos.model.db.PlanningVessel;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class ChangeVesselServiceFacade extends AbstractFacade<ChangeVesselService> implements ChangeVesselServiceFacadeRemote, ChangeVesselServiceFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ChangeVesselServiceFacade() {
        super(ChangeVesselService.class);
    }

    public List<ChangeVesselService> findByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("ChangeVesselService.findByNoReg")
                .setParameter("noReg", noReg)
                .getResultList();
    }

    public PlanningVessel findNewPlanningVesselByNoReg(String noReg) {
        try {
            return (PlanningVessel) getEntityManager().createNamedQuery("ChangeVesselService.findNewPlanningVesselByNoReg")
                    .setParameter("noReg", noReg)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ChangeVesselService.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
}
