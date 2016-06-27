/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.TempContDischargeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.TempContDischargeFacadeLocal;
import com.pelindo.ebtos.model.db.TempContDischarge;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dyware-Dev01
 */
@Stateless
public class TempContDischargeFacade extends AbstractFacade<TempContDischarge> implements TempContDischargeFacadeLocal, TempContDischargeFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public TempContDischargeFacade() {
        super(TempContDischarge.class);
    }

    public List<Object[]> findTempContDischargeByStatus(String no_ppkb, String status) {
        return getEntityManager().createNamedQuery("TempContDischarge.Native.findTempContDischargeByStatus").setParameter(1, no_ppkb).setParameter(2, status).getResultList();
    }

    public List<Object[]> findTempContDischargeByPpkb(String no_ppkb) {
        return getEntityManager().createNamedQuery("TempContDischarge.Native.findTempContDischargeByPpkb").setParameter(1, no_ppkb).getResultList();
    }

    public Object[] findByContNo(String cont_no, String pos) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("TempContDischarge.Native.findByContNo").setParameter(1, cont_no).setParameter(2, pos).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
}
