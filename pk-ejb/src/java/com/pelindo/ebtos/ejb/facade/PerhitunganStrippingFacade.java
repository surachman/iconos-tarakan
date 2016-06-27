/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganStrippingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganStrippingFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganStripping;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class PerhitunganStrippingFacade extends AbstractFacade<PerhitunganStripping> implements PerhitunganStrippingFacadeRemote, PerhitunganStrippingFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganStrippingFacade() {
        super(PerhitunganStripping.class);
    }

    public Integer findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return (Integer) getEntityManager().createNamedQuery("PerhitunganStripping.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_reg).getSingleResult();
    }

    public Integer findByCancelInvoice(String no_reg, String no_ppkb) {
        Integer temp;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("PerhitunganStripping.Native.findByCancelInvoice").setParameter(1, no_reg).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    @Override
    public PerhitunganStripping findByContNoAndNoReg(String noReg, String contNo) {
        try {
            return (PerhitunganStripping) getEntityManager().createNamedQuery("PerhitunganStripping.findByContNoAndNoReg")
                    .setParameter("noReg", noReg)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public List<PerhitunganStripping> findByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("PerhitunganStripping.findByNoReg")
                .setParameter("noReg", noReg)
                .getResultList();
    }

    public int deleteByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("PerhitunganStripping.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }
}
