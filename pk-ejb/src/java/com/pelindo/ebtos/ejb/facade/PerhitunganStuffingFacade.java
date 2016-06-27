/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganStuffingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganStuffingFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganStuffing;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class PerhitunganStuffingFacade extends AbstractFacade<PerhitunganStuffing> implements PerhitunganStuffingFacadeRemote, PerhitunganStuffingFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganStuffingFacade() {
        super(PerhitunganStuffing.class);
    }

    public Integer findInvoice(String cont_no, String no_reg) {
        return (Integer) getEntityManager().createNamedQuery("PerhitunganStuffing.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_reg).getSingleResult();
    }

    public Integer findByCancelInvoice(String no_reg) {
        Integer temp;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("PerhitunganStuffing.Native.findByCancelInvoice").setParameter(1, no_reg).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    @Override
    public PerhitunganStuffing findByContNoAndNoReg(String contNo, String noReg) {
        try {
            return (PerhitunganStuffing) getEntityManager().createNamedQuery("PerhitunganStuffing.findByContNoAndNoReg")
                    .setParameter("noReg", noReg)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    @Override
    public int deleteByNoReg(String noReg) {
        return getEntityManager().createNamedQuery("PerhitunganStuffing.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }
}
