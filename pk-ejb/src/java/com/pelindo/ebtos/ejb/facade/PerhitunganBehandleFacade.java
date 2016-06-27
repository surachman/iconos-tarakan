/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganBehandleFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganBehandleFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganBehandle;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author dycoder
 */
@Stateless
public class PerhitunganBehandleFacade extends AbstractFacade<PerhitunganBehandle> implements PerhitunganBehandleFacadeRemote, PerhitunganBehandleFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganBehandleFacade() {
        super(PerhitunganBehandle.class);
    }

    public Integer findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return (Integer) getEntityManager().createNamedQuery("PerhitunganBehandle.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public List<Object> findByReg(String no_reg) {
        return getEntityManager().createNamedQuery("PerhitunganBehandle.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

    @Override
    public PerhitunganBehandle findByContNoAndNoReg(String contNo, String noReg) {
        try {
            return (PerhitunganBehandle) getEntityManager().createNamedQuery("PerhitunganBehandle.findByContNoAndNoReg")
                    .setParameter("contNo", contNo)
                    .setParameter("noReg", noReg)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<PerhitunganBehandle> findByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("PerhitunganBehandle.findByNoReg")
                .setParameter("noReg", noReg)
                .getResultList();
    }

    @Override
    public int deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganBehandle.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }
}
