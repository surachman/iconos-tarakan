/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapJasaDermagaUcFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.RecapJasaDermagaUcFacadeRemote;
import com.pelindo.ebtos.model.db.RecapJasaDermagaUc;
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
public class RecapJasaDermagaUcFacade extends AbstractFacade<RecapJasaDermagaUc> implements RecapJasaDermagaUcFacadeRemote, RecapJasaDermagaUcFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapJasaDermagaUcFacade() {
        super(RecapJasaDermagaUc.class);
    }

    public int deleteByNoPpkb(java.lang.String noPpkb){
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapJasaDermagaUc.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
}
