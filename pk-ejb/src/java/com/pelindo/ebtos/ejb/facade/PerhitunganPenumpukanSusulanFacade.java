/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganPenumpukanSusulanFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganPenumpukanSusulanFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanSusulan;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class PerhitunganPenumpukanSusulanFacade extends AbstractFacade<PerhitunganPenumpukanSusulan> implements PerhitunganPenumpukanSusulanFacadeRemote, PerhitunganPenumpukanSusulanFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganPenumpukanSusulanFacade() {
        super(PerhitunganPenumpukanSusulan.class);
    }

    public Integer findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return ((Number) getEntityManager().createNamedQuery("PerhitunganPenumpukanSusulan.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult()).intValue();
    }

    public Integer findInvoiceUC(String bl_no, String no_ppkb, String no_reg) {
        return (Integer) getEntityManager().createNamedQuery("PerhitunganPenumpukanSusulan.Native.findInvoiceUC").setParameter(1, bl_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public List<Object> findByReg(String no_reg){
        return getEntityManager().createNamedQuery("PerhitunganPenumpukanSusulan.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

}
