/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RecapHandlingDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.RecapHandlingDischargeFacadeRemote;
import com.pelindo.ebtos.model.db.RecapHandlingDischarge;
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
public class RecapHandlingDischargeFacade extends AbstractFacade<RecapHandlingDischarge> implements RecapHandlingDischargeFacadeRemote, RecapHandlingDischargeFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapHandlingDischargeFacade() {
        super(RecapHandlingDischarge.class);
    }

    public Integer findByActCode(String actCode, String no_ppkb) {
        Integer temp = null;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("RecapHandlingDischarge.Native.findByActCode").setParameter(1, actCode).setParameter(2, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public RecapHandlingDischarge findByActivityCodeAndNoPpkb(String activityCode, String noPpkb) {
        try {
            return (RecapHandlingDischarge) getEntityManager().createNamedQuery("RecapHandlingDischarge.findByActivityCodeAndNoPpkb")
                    .setParameter("activityCode", activityCode)
                    .setParameter("noPpkb", noPpkb)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int deleteByNoPpkb(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("RecapHandlingDischarge.deleteByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }

    public Object[] findVesselHandlingByPPKB(String no_ppkb) {
        Object[] temp = new Object[123];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("RecapHandlingDischarge.Native.findVesselHandlingByPPKB").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findVesselHandlingByPPKBno(String no_ppkb) {
        Object[] temp = new Object[75];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("RecapHandlingDischarge.Native.findVesselHandlingByPPKBno").setParameter(1, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Integer> findByPpkb(String no_ppkb) {
        List<Integer> temp = new ArrayList<Integer>();
        try {
            temp = getEntityManager().createNamedQuery("RecapHandlingDischarge.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] countHandlingDischarge(String no_ppkb, Integer include){
        Object[] temp = new Object[132];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("RecapHandlingDischarge.Native.countHandlingDischarge").setParameter(1, no_ppkb).setParameter(2, include).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public RecapHandlingDischarge findByBentuk3Constraint(Short contSize, String contStatus, String crane, String isExportImport, String openDoor, String sling, String containerType, String activityCode, String noPpkb, String currCode, String twentyOneFeet, BigDecimal charge) {
        try {
            return (RecapHandlingDischarge) getEntityManager().createNamedQuery("RecapHandlingDischarge.findByBentuk3Constraint")
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
