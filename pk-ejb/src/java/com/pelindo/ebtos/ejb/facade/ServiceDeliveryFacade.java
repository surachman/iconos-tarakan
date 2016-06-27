/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceDeliveryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceDeliveryFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceDelivery;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

/**
 *
 * @author dycoder
 */
@Stateless
public class ServiceDeliveryFacade extends AbstractFacade<ServiceDelivery> implements ServiceDeliveryFacadeRemote, ServiceDeliveryFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceDeliveryFacade() {
        super(ServiceDelivery.class);
    }

    public int findIdDeliveryConfirm (String no_ppkb, String cont_no){
        return (Integer) getEntityManager().createNamedQuery("ServiceDelivery.Native.findIdDeliveryConfirm").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult();
    }

    @Override
    public ServiceDelivery findByNoPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (ServiceDelivery) getEntityManager().createNamedQuery("ServiceDelivery.findByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    //public List<Object[]> findYardDischarge(String noPpkb, String contNo) {
    //    try {
    //        return (List<Object[]>) getEntityManager().createNamedQuery("ServiceDelivery.Native.findYardDischarge")
    //                .setParameter(1, noPpkb)
    //                .setParameter(2, contNo).getSingleResult();
    //    } catch (RuntimeException e) {
    //        return null;
    //    }
    // }

    public List<Object[]> findYardDischarge(String noPpkb,String contNo) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceDelivery.Native.findYardDischarge")
                    .setParameter(1, noPpkb)
                    .setParameter(2, contNo).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    @Override
    public List<String> findLikeNoPPKB(String noPpkb) {
        List<String> result = new ArrayList<String>();
        String sql = "select distinct(sd.no_ppkb) from service_delivery sd "
                + " inner join planning_vessel pv on sd.no_ppkb=pv.no_ppkb "
                + " and sd.no_ppkb like '%" + noPpkb + "%' and "
                + " (pv.status='Servicing' or pv.status='Served' or pv.status='Approved') and rownum <= 10";
        result = getEntityManager().createNativeQuery(sql).getResultList();
        return result;
    }
    
    @Override
    public List<String> findLikeContNo(String noPpkb, String contNo) {
        List<String> result = new ArrayList<String>();
        String sql = "select cont_no from service_delivery where no_ppkb = '" + noPpkb
                + "' and cont_no like '%" + contNo + "%' and rownum <= 10";
        result = getEntityManager().createNativeQuery(sql).getResultList();
        return result;
    }

    @Override
    public List<Object[]> findByNoPpkbAndContNoUpdateDelivery(String noPpkb, String contNo) {
        List<Object[]> result = new ArrayList<Object[]>();
        String sql = "select no_ppkb, bl_no, cont_no, cont_size, block, y_slot, y_row, y_tier from service_delivery where no_ppkb = '" + noPpkb + "' "
                + " and cont_no like '%" + contNo + "%'";
        result = getEntityManager().createNativeQuery(sql).getResultList();
        return result;
    }
}
