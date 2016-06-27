/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.YardAllocationFilterFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.YardAllocationFilterFacadeLocal;
import com.pelindo.ebtos.model.db.YardAllocationFilter;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycode-java
 */
@Stateless
public class YardAllocationFilterFacade extends AbstractFacade<YardAllocationFilter> implements YardAllocationFilterFacadeLocal, YardAllocationFilterFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public YardAllocationFilterFacade() {
        super(YardAllocationFilter.class);
    }

    public List<Object[]> yardAllocationFilterfindByNoPpkb(String no_ppkb, String is_trans, String isShift) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("YardAllocationFilter.Native.yardAllocationFilterfindByNoPpkb").setParameter(1, no_ppkb).setParameter(2, is_trans).setParameter(3,isShift).getResultList()
        );
    }

    public int truncate(){
        return getEntityManager().createNamedQuery("YardAllocationFilter.Native.truncate").executeUpdate();
    }

    public int deleteById(String id){
        return getEntityManager().createNamedQuery("YardAllocationFilter.Native.deleteById").setParameter(1, id).executeUpdate();
    }

    public long sumCountByPPKB(String no_ppkb) {
        try {
            return ((BigDecimal) getEntityManager().createNamedQuery("YardAllocationFilter.Native.sumCountByPPKB").setParameter(1, no_ppkb).getSingleResult()).longValue();
        } catch(NoResultException e) {
            return 0;
        }
    }

    public Object[] findById(int id) {
        return objectDecimalsToObjectInts(
                (Object[]) getEntityManager().createNamedQuery("YardAllocationFilter.Native.findById").setParameter(1, id).getSingleResult()
        );
    }

    public List<Integer> unFinishBayPlanDischargeByPPKB(String no_ppkb) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("YardAllocationFilter.Native.unFinishBayPlanDischargeByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }
}
