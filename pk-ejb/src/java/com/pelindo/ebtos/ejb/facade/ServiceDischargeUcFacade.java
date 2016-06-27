/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceDischargeUcFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceDischargeUcFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceDischargeUc;
import java.util.ArrayList;
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
public class ServiceDischargeUcFacade extends AbstractFacade<ServiceDischargeUc> implements ServiceDischargeUcFacadeRemote, ServiceDischargeUcFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceDischargeUcFacade() {
        super(ServiceDischargeUc.class);
    }

    public List<Object[]> findByPpkb(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceDischargeUc.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findByPpkbPick(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceDischargeUc.Native.findByPpkbPick").setParameter(1, no_ppkb).getResultList();
    }

    public Object[] findByBlNo(String no_ppkb, String bl_no, String pos){
        Object[] temp = new Object[10];
        try{
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceDischargeUc.Native.findByBlNo").setParameter(1, no_ppkb).setParameter(2, bl_no).setParameter(3, pos).getSingleResult();
        } catch (NoResultException ex){
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPpkbAja(String no_ppkb){
         List<Object[]> temp = new ArrayList<Object[]>();
        try{
            temp = getEntityManager().createNamedQuery("ServiceDischargeUc.Native.findByPpkbAja").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException ex){
            temp = null;
        }
        return temp;
    }

}
