/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.ChangeStatusHistoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.ChangeStatusHistoryFacadeLocal;
import com.pelindo.ebtos.model.db.ChangeStatusHistory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class ChangeStatusHistoryFacade extends AbstractFacade<ChangeStatusHistory> implements ChangeStatusHistoryFacadeLocal, ChangeStatusHistoryFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ChangeStatusHistoryFacade() {
        super(ChangeStatusHistory.class);
    }

    public Object[] findByPpkbAndContNo(String ppkb, String cont_no) {
        Object[] temp = new Object[6];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ChangeStatusHistory.Native.findByPpkbAndContNo").setParameter(1, ppkb).setParameter(2, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }
    
    public List<Object[]> findListTranshipment(String ppkb){
        return getEntityManager().createNamedQuery("ChangeStatusHistory.Native.findListTranshipment").setParameter(1, ppkb).getResultList();
    }

    public List<ChangeStatusHistory> findTranshipmentsByOldPpkb(String noPpkb){
        return getEntityManager().createNamedQuery("ChangeStatusHistory.findTranshipmentsByOldPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }
}
