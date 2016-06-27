/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapBaplieFacadeLocal;
import com.pelindo.ebtos.model.db.RecapBaplie;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author R Seno Anggoro
 */
@Stateless
public class RecapBaplieFacade extends AbstractFacade<RecapBaplie> implements RecapBaplieFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapBaplieFacade() {
        super(RecapBaplie.class);
    }

    public RecapBaplie findByNoPpkb(String noPpkb) {
        try {
            return (RecapBaplie) getEntityManager().createNamedQuery("RecapBaplie.findByNoPpkb")
                    .setParameter("noPpkb", noPpkb)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException re) {
            return null;
        } catch (RuntimeException re) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", re);
            return null;
        }
    }

}
