/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapPenumpukanUcFacadeLocal;
import com.pelindo.ebtos.model.db.RecapPenumpukanUc;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author R Seno Anggoro
 */
@Stateless
public class RecapPenumpukanUcFacade extends AbstractFacade<RecapPenumpukanUc> implements RecapPenumpukanUcFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapPenumpukanUcFacade() {
        super(RecapPenumpukanUc.class);
    }

    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapPenumpukanUc.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
}
