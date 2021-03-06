/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapTranshipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.RecapTranshipmentFacadeRemote;
import com.pelindo.ebtos.model.db.RecapTranshipment;
import java.math.BigDecimal;
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
public class RecapTranshipmentFacade extends AbstractFacade<RecapTranshipment> implements RecapTranshipmentFacadeRemote, RecapTranshipmentFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapTranshipmentFacade() {
        super(RecapTranshipment.class);
    }

    public Integer findByActCode(String actCode, String no_ppkb, String operation) {
        Integer temp = null;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("RecapTranshipment.Native.findByActCode").setParameter(1, actCode).setParameter(2, no_ppkb).setParameter(3, operation).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public RecapTranshipment findByActivityCodeNoPpkbAndOperation(String activityCode, String noPpkb, String operation) {
        try {
            return (RecapTranshipment) getEntityManager().createNamedQuery("RecapTranshipment.findByActivityCodeNoPpkbAndOperation")
                    .setParameter("activityCode", activityCode)
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("operation", operation)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Integer> findByPpkb(String no_ppkb) {
        List<Integer> list;
        try {
            list = getEntityManager().createNamedQuery("RecapTranshipment.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            list = null;
        }
        return list;
    }

    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapTranshipment.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    public RecapTranshipment findByBentuk3Constraint(Short contSize, String contStatus, String crane, String isExportImport, String openDoor, String sling, String containerType, String activityCode, String operation, String noPpkb, String currCode, String twentyOneFeet, BigDecimal charge) {
        try {
            return (RecapTranshipment) getEntityManager().createNamedQuery("RecapTranshipment.findByBentuk3Constraint")
                    .setParameter("contSize", contSize)
                    .setParameter("contStatus", contStatus)
                    .setParameter("crane", crane)
                    .setParameter("isExportImport", isExportImport)
                    .setParameter("openDoor", openDoor)
                    .setParameter("sling", sling)
                    .setParameter("currCode", currCode)
                    .setParameter("containerType", containerType)
                    .setParameter("activityCode", activityCode)
                    .setParameter("operation", operation)
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("twentyOneFeet", twentyOneFeet)
                    .setParameter("charge", charge)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
