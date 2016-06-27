/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapUpahBuruhFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.RecapUpahBuruhFacadeRemote;
import com.pelindo.ebtos.model.db.RecapUpahBuruh;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author R Seno Anggoro
 */
@Stateless
public class RecapUpahBuruhFacade extends AbstractFacade<RecapUpahBuruh> implements RecapUpahBuruhFacadeLocal, RecapUpahBuruhFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapUpahBuruhFacade() {
        super(RecapUpahBuruh.class);
    }

    public RecapUpahBuruh findByActivityCodeAndNoPpkb(String activityCode, String noPpkb) {
        try {
            return (RecapUpahBuruh) getEntityManager().createNamedQuery("RecapUpahBuruh.findByActivityCodeAndNoPpkb")
                    .setParameter("activityCode", activityCode)
                    .setParameter("noPpkb", noPpkb)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    @Override
    public int deleteByNoPpkb(String noPpkb) {
        return emf.createEntityManager().createNamedQuery("RecapUpahBuruh.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    @Override
    public RecapUpahBuruh findByBentuk3Constraint(Short contSize, String contStatus, String crane, String isExportImport, String openDoor, String sling, String containerType, String activityCode, String operation, String activity, String noPpkb, String currCode, String twentyOneFeet, BigDecimal charge) {
        try {
            return (RecapUpahBuruh) getEntityManager().createNamedQuery("RecapUpahBuruh.findByBentuk3Constraint")
                    .setParameter("contStatus", contStatus)
                    .setParameter("contSize", contSize)
                    .setParameter("crane", crane)
                    .setParameter("openDoor", openDoor)
                    .setParameter("isExportImport", isExportImport)
                    .setParameter("currCode", currCode)
                    .setParameter("sling", sling)
                    .setParameter("containerType", containerType)
                    .setParameter("activityCode", activityCode)
                    .setParameter("operation", operation)
                    .setParameter("activity", activity)
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("twentyOneFeet", twentyOneFeet)
                    .setParameter("charge", charge)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public RecapUpahBuruh findByBentuk3ConstraintWithStatus(Short contSize, String contStatus, String crane, String isExportImport, String openDoor, String sling, String containerType, String activityCode, String operation, String activity, String noPpkb, String currCode, String status, String twentyOneFeet, BigDecimal charge) {
        try {
            return (RecapUpahBuruh) getEntityManager().createNamedQuery("RecapUpahBuruh.findByBentuk3ConstraintWithStatus")
                    .setParameter("contStatus", contStatus)
                    .setParameter("crane", crane)
                    .setParameter("contSize", contSize)
                    .setParameter("openDoor", openDoor)
                    .setParameter("isExportImport", isExportImport)
                    .setParameter("currCode", currCode)
                    .setParameter("sling", sling)
                    .setParameter("containerType", containerType)
                    .setParameter("activityCode", activityCode)
                    .setParameter("operation", operation)
                    .setParameter("activity", activity)
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("status", status)
                    .setParameter("twentyOneFeet", twentyOneFeet)
                    .setParameter("charge", charge)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
