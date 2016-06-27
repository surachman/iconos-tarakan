/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapUpahBuruhUcFacadeLocal;
import com.pelindo.ebtos.model.db.RecapUpahBuruhUc;
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
public class RecapUpahBuruhUcFacade extends AbstractFacade<RecapUpahBuruhUc> implements RecapUpahBuruhUcFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapUpahBuruhUcFacade() {
        super(RecapUpahBuruhUc.class);
    }

    public RecapUpahBuruhUc findByBentuk3Constraint(String noPpkb, Integer weightGroup, String crane, String isExportImport, String activityCode, String currCode, String operation, String activity, BigDecimal charge) {
        try {
            return (RecapUpahBuruhUc) getEntityManager().createNamedQuery("RecapUpahBuruhUc.findByBentuk3Constraint")
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

    public RecapUpahBuruhUc findByBentuk3ConstraintWithStatus(String noPpkb, Integer weightGroup, String crane, String isExportImport, String activityCode, String currCode, String operation, String activity, String status, BigDecimal charge) {
        try {
            return (RecapUpahBuruhUc) getEntityManager().createNamedQuery("RecapUpahBuruhUc.findByBentuk3ConstraintWithStatus")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("weightGroup", weightGroup)
                    .setParameter("crane", crane)
                    .setParameter("isExportImport", isExportImport)
                    .setParameter("activityCode", activityCode)
                    .setParameter("currCode", currCode)
                    .setParameter("status", status)
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
        return entityManager.createNamedQuery("RecapUpahBuruhUc.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
}
