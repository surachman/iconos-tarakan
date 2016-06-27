/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapPenumpukanFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.RecapPenumpukanFacadeRemote;
import com.pelindo.ebtos.model.db.RecapPenumpukan;
import java.util.ArrayList;
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
public class RecapPenumpukanFacade extends AbstractFacade<RecapPenumpukan> implements RecapPenumpukanFacadeRemote, RecapPenumpukanFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapPenumpukanFacade() {
        super(RecapPenumpukan.class);
    }

    public List<Integer> findByPpkbNCont(String cont_no, String no_ppkb) {
        List<Integer> temp = new ArrayList<Integer>();
        try {
            temp = getEntityManager().createNamedQuery("RecapPenumpukan.Native.findByPpkbAndCont").setParameter(1, cont_no).setParameter(2, no_ppkb).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public int deleteThatHaveReachedCY(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapPenumpukan.deleteThatHaveReachedCY")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapPenumpukan.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
}
