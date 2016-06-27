/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganLiftServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganLiftServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author dycoder
 */
@Stateless
public class PerhitunganLiftServiceFacade extends AbstractFacade<PerhitunganLiftService> implements PerhitunganLiftServiceFacadeRemote, PerhitunganLiftServiceFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganLiftServiceFacade() {
        super(PerhitunganLiftService.class);
    }

    public Integer findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return (Integer) getEntityManager().createNamedQuery("PerhitunganLiftService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public Integer deleteByContNoAndNoReg(String contNo, String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganLiftService.deleteByContNoAndNoReg")
                .setParameter("contNo", contNo)
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    public Integer deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganLiftService.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    public List<Object> findByReg(String no_reg) {
        return getEntityManager().createNamedQuery("PerhitunganLiftService.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

    @Override
    public PerhitunganLiftService findByContNoAndNoReg(String contNo, String noReg) {
        try {
            return (PerhitunganLiftService) getEntityManager().createNamedQuery("PerhitunganLiftService.findByContNoAndNoReg")
                    .setParameter("contNo", contNo)
                    .setParameter("noReg", noReg)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public int updateNoPpkb(String newValue, String oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganLiftService.updateNoPpkb")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }
}
