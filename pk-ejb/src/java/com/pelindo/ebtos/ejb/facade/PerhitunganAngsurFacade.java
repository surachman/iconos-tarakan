/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PerhitunganAngsurFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PerhitunganAngsurFacadeRemote;
import com.pelindo.ebtos.model.db.PerhitunganAngsur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class PerhitunganAngsurFacade extends AbstractFacade<PerhitunganAngsur> implements PerhitunganAngsurFacadeRemote, PerhitunganAngsurFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PerhitunganAngsurFacade() {
        super(PerhitunganAngsur.class);
    }

    public int findPerhitunganAngsurId(String no_ppkb, String no_reg, String cont_no) {
        return (Integer) getEntityManager().createNamedQuery("PerhitunganAngsur.Native.findPerhitunganAngsurId").setParameter(1, no_ppkb).setParameter(2, no_reg).setParameter(3, cont_no).getSingleResult();
    }
}
