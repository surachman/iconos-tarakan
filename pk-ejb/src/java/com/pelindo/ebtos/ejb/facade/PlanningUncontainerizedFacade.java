/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.PlanningUncontainerizedFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningUncontainerized;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class PlanningUncontainerizedFacade extends AbstractFacade<PlanningUncontainerized> implements PlanningUncontainerizedFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningUncontainerizedFacade() {
        super(PlanningUncontainerized.class);
    }

    @Override
    public List<Object[]> findPlanningUncontainerizedByPpkb(String no_ppkb){
        return getEntityManager().createNamedQuery("PlanningUncontainerized.Native.findPlanningUncontainerizedByPpkb").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findPlanningUncontainerizedByPpkbDischarge(String no_ppkb){
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("PlanningUncontainerized.Native.findPlanningUncontainerizedByPpkbDischarge").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findPlanningUncontainerizedByPpkbLoad(String no_ppkb){
        return getEntityManager().createNamedQuery("PlanningUncontainerized.Native.findPlanningUncontainerizedByPpkbLoad").setParameter(1, no_ppkb).getResultList();
    }
}
