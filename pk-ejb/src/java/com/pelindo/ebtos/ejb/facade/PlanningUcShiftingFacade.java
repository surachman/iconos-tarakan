/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.PlanningUcShiftingFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.PlanningUcShiftingFacadeLocal;
import com.pelindo.ebtos.model.db.PlanningUcShifting;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycode-java
 */
@Stateless
public class PlanningUcShiftingFacade extends AbstractFacade<PlanningUcShifting> implements PlanningUcShiftingFacadeLocal, PlanningUcShiftingFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningUcShiftingFacade() {
        super(PlanningUcShifting.class);
    }


    public List<Object[]> findAllByPPKB(String no_ppkb){
        return getEntityManager().createNamedQuery("PlanningUcShifting.Native.findAllByPPKB").setParameter(1, no_ppkb).getResultList();
    }

}
