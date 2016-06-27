/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceCfsStrippingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStrippingFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceCfsStripping;
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
public class ServiceCfsStrippingFacade extends AbstractFacade<ServiceCfsStripping> implements ServiceCfsStrippingFacadeRemote, ServiceCfsStrippingFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceCfsStrippingFacade() {
        super(ServiceCfsStripping.class);
    }

    public List<Object[]> findAllNative(){
        List<Object[]> list = new ArrayList<Object[]>();
        try{
            list = (List<Object[]>) objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceCfsStripping.Native.findAll").getResultList()
            );
            
        } catch (NoResultException ex){
            list = null;
        }
        return list;
    }

    public List<Object[]> findForDeliveryGoods(){
        List<Object[]> list = new ArrayList<Object[]>();
        try{
            list = (List<Object[]>) objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceCfsStripping.Native.findForDeliveryGoods").getResultList());
        } catch (NoResultException ex){
            list = null;
        }
        return list;
    }

    public List<Object[]> findBL(String contNo){
        List<Object[]> list = new ArrayList<Object[]>();
        try{
            list = (List<Object[]>)  objectsDecimalsToObjectsInts( getEntityManager().createNamedQuery("ServiceCfsStripping.Native.findBL").setParameter(1, contNo).getResultList());
        } catch (NoResultException ex){
            list = null;
        }
        return list;
    }

    public List<Object[]> findDeliveryTrue(){
        List<Object[]> list = new ArrayList<Object[]>();
        try{
            list = (List<Object[]>) objectsDecimalsToObjectsInts( getEntityManager().createNamedQuery("ServiceCfsStripping.Native.findDelivery").getResultList());
        } catch (NoResultException ex){
            list = null;
        }
        return list;
    }


    public Integer findId(String noPpkb, String noCont, String noBL) {
        Integer temp = null;
        try {
            temp = ((Number) getEntityManager().createNamedQuery("ServiceCfsStripping.Native.findId")
                    .setParameter(1, noPpkb)
                    .setParameter(2, noCont)
                    .setParameter(3, noBL)
                    .getSingleResult()).intValue();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPpkbNCont(String no_ppkb, String no_cont) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp =  objectsDecimalsToObjectsInts( getEntityManager().createNamedQuery("ServiceCfsStripping.Native.findByPpkbNCont").setParameter(1, no_ppkb).setParameter(2, no_cont).getResultList());
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

}
