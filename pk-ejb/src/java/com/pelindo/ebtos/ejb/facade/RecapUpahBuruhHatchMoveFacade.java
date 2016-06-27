/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapUpahBuruhHatchMoveFacadeLocal;
import com.pelindo.ebtos.model.db.RecapUpahBuruhHatchMove;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Seno
 */
@Stateless
public class RecapUpahBuruhHatchMoveFacade extends AbstractFacade<RecapUpahBuruhHatchMove> implements RecapUpahBuruhHatchMoveFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapUpahBuruhHatchMoveFacade() {
        super(RecapUpahBuruhHatchMove.class);
    }

    @Override
    public RecapUpahBuruhHatchMove findByBentuk3Constraint(String noPpkb, String crane) {
        try {
            return (RecapUpahBuruhHatchMove) getEntityManager().createNamedQuery("RecapUpahBuruhHatchMove.findByBentuk3Constraint")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("crane", crane)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapUpahBuruhHatchMove.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
    
}
