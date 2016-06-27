/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapHandlingUcFacadeLocal;
import com.pelindo.ebtos.model.db.RecapHandlingUc;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class RecapHandlingUcFacade extends AbstractFacade<RecapHandlingUc> implements RecapHandlingUcFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapHandlingUcFacade() {
        super(RecapHandlingUc.class);
    }

    public RecapHandlingUc findByBentuk3Constraint(String noPpkb, Integer weightGroup, String crane, String isExportImport, String activityCode, String currCode, String operation, String activity, BigDecimal charge) {
        try {
            return (RecapHandlingUc) getEntityManager().createNamedQuery("RecapHandlingUc.findByBentuk3Constraint")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("weightGroup", weightGroup)
                    .setParameter("crane", crane)
                    .setParameter("isExportImport", isExportImport)
                    .setParameter("activityCode", activityCode)
                    .setParameter("currCode", currCode)
                    .setParameter("operation", operation)
                    .setParameter("activity", activity)
                    .setParameter("charge", charge)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public RecapHandlingUc findByBentuk3ConstraintWithStatus(String noPpkb, Integer weightGroup, String crane, String isExportImport, String activityCode, String currCode, String operation, String activity, String status, BigDecimal charge) {
        try {
            return (RecapHandlingUc) getEntityManager().createNamedQuery("RecapHandlingUc.findByBentuk3ConstraintWithStatus")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("weightGroup", weightGroup)
                    .setParameter("crane", crane)
                    .setParameter("isExportImport", isExportImport)
                    .setParameter("activityCode", activityCode)
                    .setParameter("status", status)
                    .setParameter("currCode", currCode)
                    .setParameter("operation", operation)
                    .setParameter("activity", activity)
                    .setParameter("charge", charge)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapHandlingUc.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
}
