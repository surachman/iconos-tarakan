/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapHatchMoveFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.RecapHatchMoveFacadeRemote;
import com.pelindo.ebtos.model.db.RecapHatchMove;
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
public class RecapHatchMoveFacade extends AbstractFacade<RecapHatchMove> implements RecapHatchMoveFacadeRemote, RecapHatchMoveFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapHatchMoveFacade() {
        super(RecapHatchMove.class);
    }

    public List<Integer> findByPpkb(String no_ppkb) {
        List<Integer> list;
        try {
            list = getEntityManager().createNamedQuery("RecapHatchMove.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            list = null;
        }
        return list;
    }

    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapHatchMove.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    @Override
    public RecapHatchMove findByBentuk3Constraint(String noPpkb, String crane) {
        try {
            return (RecapHatchMove) getEntityManager().createNamedQuery("RecapHatchMove.findByBentuk3Constraint")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("crane", crane)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public RecapHatchMove findByBentuk3ConstraintTarakan(String noPpkb, String crane, String operation) {
        try {
            return (RecapHatchMove) getEntityManager().createNamedQuery("RecapHatchMove.findByBentuk3ConstraintTarakan")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("crane", crane)
                    .setParameter("operation", operation)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
