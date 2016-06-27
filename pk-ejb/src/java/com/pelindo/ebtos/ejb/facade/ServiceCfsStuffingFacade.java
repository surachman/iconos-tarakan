/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceCfsStuffingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceCfsStuffingFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceCfsStuffing;
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
public class ServiceCfsStuffingFacade extends AbstractFacade<ServiceCfsStuffing> implements ServiceCfsStuffingFacadeRemote, ServiceCfsStuffingFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceCfsStuffingFacade() {
        super(ServiceCfsStuffing.class);
    }

    public List<Object[]> findServiceCfsStuffingByList() {
        return  objectsDecimalsToObjectsInts( 
                getEntityManager().createNamedQuery("ServiceCfsStuffing.Native.findServiceCfsStuffingByList").getResultList()
        );
    
    }

     public List<String> findServiceCfsStuffingByAutoComplete(String jobslip) {
//        List<String> temp = new ArrayList<String>();
//        try {
//            temp = getEntityManager().createNamedQuery("ReceivingBarangService.Native.findReceivingBarangServiceByAutoComplete").setParameter(1, jobslip).getResultList();
//        } catch (NoResultException e) {
//            temp = null;
//        }
//        return temp;
         System.out.println("dari faceade :" + jobslip);
        return getEntityManager().createNamedQuery("ServiceCfsStuffing.Native.findServiceCfsStuffingByAutoComplete").setParameter(1, jobslip).getResultList();
    }


    public Object[] findServiceCfsStuffingByPenumpukan(String job_slip) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts( 
                    (Object[]) getEntityManager().createNamedQuery("ServiceCfsStuffing.Native.findServiceCfsStuffingByPenumpukan").setParameter(1, job_slip).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public List<Object[]> findByPpkbNCont(String no_ppkb, String no_cont) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceCfsStuffing.Native.findByPpkbNCont").setParameter(1, no_ppkb).setParameter(2, no_cont).getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }
    public List<Object[]> findServiceCfsStuffingAll() {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts( 
                    getEntityManager().createNamedQuery("ServiceCfsStuffing.Native.findServiceCfsStuffingAll").getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

     public List<Object[]> findServiceCfsStuffingAllAndBl(String bl_no) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceCfsStuffing.Native.findServiceCfsStuffingAllAndBl").setParameter(1, bl_no).getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }
     
public List<Object[]> findAllNative(){
        List<Object[]> list = new ArrayList<Object[]>();
        try{
            list = (List<Object[]>) objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceCfsStuffing.Native.findAll").getResultList()
            );
            
        } catch (NoResultException ex){
            list = null;
        }
        return list;
    }
}
