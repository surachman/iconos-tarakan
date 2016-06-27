/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.UcPerhitunganLiftFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.UcPerhitunganLiftFacadeLocal;
import com.pelindo.ebtos.model.db.UcPerhitunganLift;
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
public class UcPerhitunganLiftFacade extends AbstractFacade<UcPerhitunganLift> implements UcPerhitunganLiftFacadeLocal, UcPerhitunganLiftFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UcPerhitunganLiftFacade() {
        super(UcPerhitunganLift.class);
    }
     public Integer findByUcPerhitunganLift(String jobSlip) {
         Integer temp;
         try {
             temp = (Integer) getEntityManager().createNamedQuery("UcPerhitunganLift.Native.findByJobslip").setParameter(1, jobSlip).getSingleResult();
         } catch (NoResultException e) {
             temp = null;
         }
         return temp;
    }

    public List<Object> findByReg(String no_reg) {
        return getEntityManager().createNamedQuery("UcPerhitunganLift.Native.findByReg").setParameter(1, no_reg).getResultList();
    }
    
    public int deleteByNoReg(String noReg) {
        EntityManager entityManager = emf.createEntityManager();
        
        return entityManager.createNamedQuery("UcPerhitunganLift.deleteByNoReg")
                .setParameter("noReg", noReg)
                .executeUpdate();
    }
    
    public int deleteByJobslip(String jobslip) {
        EntityManager entityManager = emf.createEntityManager();
        
        return entityManager.createNamedQuery("UcPerhitunganLift.deleteByJobslip")
                .setParameter("jobslip", jobslip)
                .executeUpdate();
    }
}
