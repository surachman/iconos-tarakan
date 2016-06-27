/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganHandlingDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganHandlingDischargeFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganHandlingDischarge;
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
public class PerhitunganHandlingDischargeFacade extends AbstractFacade<PerhitunganHandlingDischarge> implements PerhitunganHandlingDischargeFacadeRemote, PerhitunganHandlingDischargeFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganHandlingDischargeFacade() {
        super(PerhitunganHandlingDischarge.class);
    }

    public Integer findInvoice(String cont_no, String no_ppkb, String no_reg) {
        try {
            return (Integer) getEntityManager().createNamedQuery("PerhitunganHandlingDischarge.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Integer deleteByContNoAndNoReg(String contNo, String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganHandlingDischarge.deleteByContNoAndNoReg")
                .setParameter("contNo", contNo)
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    public Integer deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganHandlingDischarge.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    public int updateNoPpkb(String newValue, String oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganHandlingDischarge.updateNoPpkb")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }

    public Object[] findHandlingInvoice(String no_ppkb) {
        Object[] temp = new Object[3];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("PerhitunganHandlingDischarge.Native.findHandlingInvoice").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object> findByReg(String no_reg) {
        return getEntityManager().createNamedQuery("PerhitunganHandlingDischarge.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

    public Integer findByCancelInvoice(String no_reg, String no_ppkb) {
        Integer temp;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("PerhitunganHandlingDischarge.Native.findByCancelInvoice").setParameter(1, no_reg).setParameter(2, no_ppkb).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    @Override
    public PerhitunganHandlingDischarge findByContNoAndNoReg(String contNo, String noReg) {
        try {
            return (PerhitunganHandlingDischarge) getEntityManager().createNamedQuery("PerhitunganHandlingDischarge.findByContNoAndNoReg")
                    .setParameter("contNo", contNo)
                    .setParameter("noReg", noReg)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
