/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.ReceivingBarangServiceFacadeRemote;
import com.pelindo.ebtos.model.db.ReceivingBarangService;
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
public class ReceivingBarangServiceFacade extends AbstractFacade<ReceivingBarangService> implements ReceivingBarangServiceFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ReceivingBarangServiceFacade() {
        super(ReceivingBarangService.class);
    }

    public List<Object[]> findReceivingBarangServices(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ReceivingBarangService.Native.findReceivingBarangServices").setParameter(1, no_reg).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findReceivingBarangServiceByJobSlip(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ReceivingBarangService.Native.findReceivingBarangServiceByJobSlip").setParameter(1, no_reg).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findReceivingBarangServiceByList(String Bl) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ReceivingBarangService.Native.findReceivingBarangServiceByList").setParameter(1, Bl).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findReceivingBarangServiceByListAll() {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ReceivingBarangService.Native.findReceivingBarangServiceByListAll").getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<String> findReceivingBarangServiceByAutoComplete(String jobslip) {
//        List<String> temp = new ArrayList<String>();
//        try {
//            temp = getEntityManager().createNamedQuery("ReceivingBarangService.Native.findReceivingBarangServiceByAutoComplete").setParameter(1, jobslip).getResultList();
//        } catch (NoResultException e) {
//            temp = null;
//        }
//        return temp;
        System.out.println("dari faceade :" + jobslip);
        return getEntityManager().createNamedQuery("ReceivingBarangService.Native.findReceivingBarangServiceByAutoComplete").setParameter(1, jobslip).getResultList();
    }

    public Object[] findReceivingBarangServiceByStuffing(String job_slip) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ReceivingBarangService.Native.findReceivingBarangServiceByStuffing").setParameter(1, job_slip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public String findReceivingBarangServiceByGenerate(String bulan) {
        return (String) getEntityManager().createNamedQuery("ReceivingBarangService.Native.findReceivingBarangServiceByGenerate").setParameter(1, bulan).getSingleResult();
    }

     public List<Object[]> findByListBatalNotaService(String no_ppkb, String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ReceivingBarangService.Native.findByListBatalNotaService").setParameter(1, no_ppkb).setParameter(2, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

     public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ReceivingBarangService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

}
