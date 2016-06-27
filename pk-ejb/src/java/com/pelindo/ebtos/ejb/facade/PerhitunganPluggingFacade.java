/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganPluggingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPluggingFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganPlugging;
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
public class PerhitunganPluggingFacade extends AbstractFacade<PerhitunganPlugging> implements PerhitunganPluggingFacadeRemote, PerhitunganPluggingFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganPluggingFacade() {
        super(PerhitunganPlugging.class);
    }

    public Integer findInvoice(String cont_no, String no_ppkb, String no_reg) {
        Integer temp;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("PerhitunganPlugging.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public PerhitunganPlugging findByContNoPpkbAndNoReg(String contNo, String noPpkb, String noReg) {
        try {
            return (PerhitunganPlugging) getEntityManager().createNamedQuery("PerhitunganPlugging.findByContNoPpkbAndNoReg")
                    .setParameter("contNo", contNo)
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("noReg", noReg)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int deleteByNoPpkbLoadOnly(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("PerhitunganPlugging.deleteByNoPpkbLoadOnly")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    public Integer findInvoicePlugging(String cont_no, String no_reg) {
        return (Integer) getEntityManager().createNamedQuery("PerhitunganPlugging.Native.findInvoicePlugging")
                .setParameter(1, cont_no).setParameter(2, no_reg)
                .getSingleResult();
    }

    public List<Object> findByReg(String no_reg) {
        return getEntityManager().createNamedQuery("PerhitunganPlugging.Native.findByReg")
                .setParameter(1, no_reg)
                .getResultList();
    }

    @Override
    public PerhitunganPlugging findByContNoAndNoReg(String contNo, String noReg) {
        try {
            return (PerhitunganPlugging) getEntityManager().createNamedQuery("PerhitunganPlugging.findByContNoAndNoReg")
                    .setParameter("contNo", contNo)
                    .setParameter("noReg", noReg)
                    .getSingleResult();
        } catch (NoResultException re) {
            return null;
        }
    }

}
