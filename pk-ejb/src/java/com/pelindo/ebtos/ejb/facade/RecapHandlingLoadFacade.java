/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapHandlingLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.RecapHandlingLoadFacadeRemote;
import com.pelindo.ebtos.model.db.RecapHandlingLoad;
import java.math.BigDecimal;
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
public class RecapHandlingLoadFacade extends AbstractFacade<RecapHandlingLoad> implements RecapHandlingLoadFacadeRemote, RecapHandlingLoadFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapHandlingLoadFacade() {
        super(RecapHandlingLoad.class);
    }

    public Integer findByActCode(String actCode, String no_ppkb) {
        Integer temp = null;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("RecapHandlingLoad.Native.findByActCode").setParameter(1, actCode).setParameter(2, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public RecapHandlingLoad findByActivityCodeAndNoPpkb(String activityCode, String noPpkb) {
        try {
            return (RecapHandlingLoad) getEntityManager().createNamedQuery("RecapHandlingLoad.findByActivityCodeAndNoPpkb")
                    .setParameter("activityCode", activityCode)
                    .setParameter("noPpkb", noPpkb)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Object[] findVesselHandlingByPPKB(String no_ppkb) {
        Object[] temp = new Object[107];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("RecapHandlingLoad.Native.findVesselHandlingByPPKB").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findHandlingInvoice(String no_ppkb) {
        Object[] temp = new Object[6];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("RecapHandlingLoad.Native.findHandlingInvoice").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Integer> findByPpkb(String no_ppkb) {
        List<Integer> temp = new ArrayList<Integer>();
        try {
            temp = getEntityManager().createNamedQuery("RecapHandlingLoad.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapHandlingLoad.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    public Object[] countHandlingLoad(String no_ppkb){
        Object[] temp = new Object[116];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("RecapHandlingLoad.Native.countHandlingLoad").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public RecapHandlingLoad findByBentuk3Constraint(Short contSize, String contStatus, String crane, String isExportImport, String openDoor, String sling, String containerType, String activityCode, String noPpkb, String currCode, String twentyOneFeet, BigDecimal charge) {
        try {
            return (RecapHandlingLoad) getEntityManager().createNamedQuery("RecapHandlingLoad.findByBentuk3Constraint")
                    .setParameter("contSize", contSize)
                    .setParameter("contStatus", contStatus)
                    .setParameter("crane", crane)
                    .setParameter("currCode", currCode)
                    .setParameter("isExportImport", isExportImport)
                    .setParameter("openDoor", openDoor)
                    .setParameter("sling", sling)
                    .setParameter("containerType", containerType)
                    .setParameter("activityCode", activityCode)
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("twentyOneFeet", twentyOneFeet)
                    .setParameter("charge", charge)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
