/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.DischargeToLoadServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.DischargeToLoadServiceFacadeRemote;
import com.pelindo.ebtos.model.db.DischargeToLoadService;
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
public class DischargeToLoadServiceFacade extends AbstractFacade<DischargeToLoadService> implements DischargeToLoadServiceFacadeLocal, DischargeToLoadServiceFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public DischargeToLoadServiceFacade() {
        super(DischargeToLoadService.class);
    }

    @Override
    public int deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("DischargeToLoadService.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    @Override
    public List<DischargeToLoadService> findByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("DischargeToLoadService.findByNoReg")
                .setParameter("noReg", noReg)
                .getResultList();
    }
}
