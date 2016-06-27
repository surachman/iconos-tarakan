/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapAirKapalFacadeLocal;
import com.pelindo.ebtos.model.db.RecapAirKapal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;



@Stateless
public class RecapAirKapalFacade extends AbstractFacade<RecapAirKapal> implements RecapAirKapalFacadeLocal{

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapAirKapalFacade() {
        super(RecapAirKapal.class);
    }

    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapAirKapal.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
}
