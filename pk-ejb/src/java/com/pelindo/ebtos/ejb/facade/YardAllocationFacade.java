/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.YardAllocationFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.YardAllocationFacadeLocal;
import com.pelindo.ebtos.model.db.YardAllocation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycode-java
 */
@Stateless
public class YardAllocationFacade extends AbstractFacade<YardAllocation> implements YardAllocationFacadeLocal, YardAllocationFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public YardAllocationFacade() {
        super(YardAllocation.class);
    }

    public List<Object[]> findAllByYardAllocationFilter(int yaf) {
        return getEntityManager().createNamedQuery("YardAllocation.Native.findAllByYardAllocationFilter").setParameter(1, yaf).getResultList();
    }

    public int truncate(){
        return getEntityManager().createNamedQuery("YardAllocation.Native.truncate").executeUpdate();
    }

    //YardAllocation.Native.deleteByIdConstraint
    public int deleteByIdConstraint(String id_constraint){
        return getEntityManager().createNamedQuery("YardAllocation.Native.deleteByIdConstraint").setParameter(1, id_constraint).executeUpdate();
    }

    public List<String> unFinishBayPlanDischargeByPPKB(String no_ppkb) {
        return getEntityManager().createNamedQuery("YardAllocation.Native.unFinishBayPlanDischargeByPPKB").setParameter(1, no_ppkb).getResultList();
    }
}
