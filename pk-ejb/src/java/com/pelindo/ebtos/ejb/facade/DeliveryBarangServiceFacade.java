/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.DeliveryBarangServiceFacadeRemote;
import com.pelindo.ebtos.model.db.DeliveryBarangService;
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
public class DeliveryBarangServiceFacade extends AbstractFacade<DeliveryBarangService> implements DeliveryBarangServiceFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public DeliveryBarangServiceFacade() {
        super(DeliveryBarangService.class);
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("DeliveryBarangService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public String findInvoice(String cont_no, String no_ppkb, String no_reg) {
        return (String) getEntityManager().createNamedQuery("DeliveryBarangService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
    }

    public List<Object[]> findPerhitungan(String no_reg){
        return getEntityManager().createNamedQuery("DeliveryBarangService.Native.perhitungan").setParameter(1, no_reg).getResultList();
    }

    public List<Object[]> findByReg(String no_reg){
        return getEntityManager().createNamedQuery("DeliveryBarangService.Native.findByReg").setParameter(1, no_reg).getResultList();
    }

    public List<String> findJobslipAutoComplete(String jobslip){
        return getEntityManager().createNamedQuery("DeliveryBarangService.Native.findJobslipAutoComplete").setParameter(1, jobslip).getResultList();
    }

    public Object[] findJobslip(String jobslip) {
        Object[] temp = new Object[1];
        try{
            temp = (Object[]) getEntityManager().createNamedQuery("DeliveryBarangService.Native.findJobslip").setParameter(1, jobslip).getSingleResult();
        } catch (NoResultException ex){
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findDeliveryBarangServiceByList(String Bl) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("DeliveryBarangService.Native.findDeliveryBarangServiceByList").setParameter(1, Bl).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findDeliveryBarangServiceByListAll() {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("DeliveryBarangService.Native.findDeliveryBarangServiceByListAll").getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

     public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("DeliveryBarangService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

}
