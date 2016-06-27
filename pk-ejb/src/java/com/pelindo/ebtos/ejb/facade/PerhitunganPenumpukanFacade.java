/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganPenumpukanFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
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
public class PerhitunganPenumpukanFacade extends AbstractFacade<PerhitunganPenumpukan> implements PerhitunganPenumpukanFacadeRemote, PerhitunganPenumpukanFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganPenumpukanFacade() {
        super(PerhitunganPenumpukan.class);
    }

    public Integer findInvoice(String cont_no, String no_ppkb, String no_reg) {
        Integer temp;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("PerhitunganPenumpukan.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Integer deleteByContNoAndNoReg(String contNo, String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganPenumpukan.deleteByContNoAndNoReg")
                .setParameter("contNo", contNo)
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    public Integer deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganPenumpukan.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    public List<Object> findByReg(String no_reg) {
        return getEntityManager().createNamedQuery("PerhitunganPenumpukan.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

    public Integer findByCancelInvoice(String no_reg,String no_ppkb) {
        Integer temp;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("PerhitunganPenumpukan.Native.findByCancelInvoice").setParameter(1, no_reg).setParameter(2, no_ppkb).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public int deleteByNoPpkbReceivingOnly(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganPenumpukan.deleteByNoPpkbReceivingOnly")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    @Override
    public PerhitunganPenumpukan findByContNoAndNoReg(String contNo, String noReg) {
        try {
            return (PerhitunganPenumpukan) getEntityManager().createNamedQuery("PerhitunganPenumpukan.findByContNoAndNoReg")
                    .setParameter("contNo", contNo)
                    .setParameter("noReg", noReg)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public int updateNoPpkb(String newValue, String oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganPenumpukan.updateNoPpkb")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }
}
