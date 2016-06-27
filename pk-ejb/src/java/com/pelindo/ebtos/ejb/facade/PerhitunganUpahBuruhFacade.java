/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganUpahBuruhFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganUpahBuruhFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganUpahBuruh;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author R Seno Anggoro
 */
@Stateless
public class PerhitunganUpahBuruhFacade extends AbstractFacade<PerhitunganUpahBuruh> implements PerhitunganUpahBuruhFacadeLocal, PerhitunganUpahBuruhFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganUpahBuruhFacade() {
        super(PerhitunganUpahBuruh.class);
    }

    @Override
    public int deleteByJobslip(String jobslip) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganUpahBuruh.deleteByJobslip")
                .setParameter("jobslip", jobslip)
                .executeUpdate();
    }

    @Override
    public int deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganUpahBuruh.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    @Override
    public PerhitunganUpahBuruh findByJobslip(String jobslip) {
        try {
            return (PerhitunganUpahBuruh) getEntityManager().createNamedQuery("PerhitunganUpahBuruh.findByJobslip")
                    .setParameter("jobslip", jobslip)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

}
