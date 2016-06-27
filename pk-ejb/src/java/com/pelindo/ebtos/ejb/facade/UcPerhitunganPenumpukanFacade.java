/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganPenumpukanFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.UcPerhitunganPenumpukanFacadeLocal;
import com.pelindo.ebtos.model.db.UcPerhitunganPenumpukan;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author postgres
 */
@Stateless
public class UcPerhitunganPenumpukanFacade extends AbstractFacade<UcPerhitunganPenumpukan> implements UcPerhitunganPenumpukanFacadeLocal, UcPerhitunganPenumpukanFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UcPerhitunganPenumpukanFacade() {
        super(UcPerhitunganPenumpukan.class);
    }

     public int findByJobslip(String jobSlip) {
         Integer temp = null;
         try {
             temp = (Integer) getEntityManager().createNamedQuery("UcPerhitunganPenumpukan.Native.findByJobslip").setParameter(1, jobSlip).getSingleResult();
         } catch (NoResultException e) {
             temp = null;
         }
         return temp;
    }

    public List<Object> findByReg(String no_reg) {
        return getEntityManager().createNamedQuery("UcPerhitunganPenumpukan.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

    public int deleteByNoPpkbReceivingOnly(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("UcPerhitunganPenumpukan.deleteByNoPpkbAndTruckLoosingStatus")
                .setParameter("noPpkb", noPpkb)
                .setParameter("truckLoosing", false)
                .executeUpdate();
    }

    public int deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("UcPerhitunganPenumpukan.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }

    public int deleteByJobslip(String jobslip) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("UcPerhitunganPenumpukan.deleteByJobslip")
                .setParameter("jobslip", jobslip)
                .executeUpdate();
    }

}
