/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapReeferLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.RecapReeferLoadFacadeRemote;
import com.pelindo.ebtos.model.db.RecapReeferLoad;
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
public class RecapReeferLoadFacade extends AbstractFacade<RecapReeferLoad> implements RecapReeferLoadFacadeRemote, RecapReeferLoadFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapReeferLoadFacade() {
        super(RecapReeferLoad.class);
    }

    public Integer findByPpkb(String no_ppkb) {
        Integer temp = null;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("RecapReeferLoad.Native.findByPpkb").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public RecapReeferLoad findByNoPpkb(String noPpkb) {
        try {
            return (RecapReeferLoad) getEntityManager().createNamedQuery("RecapReeferLoad.findByNoPpkb")
                    .setParameter("noPpkb", noPpkb)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapReeferLoad.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
}
