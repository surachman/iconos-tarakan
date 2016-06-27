/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PluggingReeferServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.PluggingReeferServiceFacadeRemote;
import com.pelindo.ebtos.model.db.PluggingReeferService;
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
public class PluggingReeferServiceFacade extends AbstractFacade<PluggingReeferService> implements PluggingReeferServiceFacadeRemote, PluggingReeferServiceFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PluggingReeferServiceFacade() {
        super(PluggingReeferService.class);
    }

    public List<Object[]> findByNoRegistration(String no_reg) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PluggingReeferService.Native.findByNoReg").setParameter(1, no_reg).getResultList()
        );
    }

    public String generateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("PluggingReeferService.Native.generateId").setParameter(1, bulan).getSingleResult();
    }

    public Object[] findContainerReefer(String cont_no) {
        Object[] temp = new Object[4];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PluggingReeferService.Native.findContainerReefer").setParameter(1, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public String findInvoice(String cont_no, String no_reg) {
        return (String) getEntityManager().createNamedQuery("PluggingReeferService.Native.findInvoice").setParameter(1, cont_no).setParameter(2, no_reg).getSingleResult();
    }

    public List<Object[]> findPerhitungan(String no_reg) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PluggingReeferService.Native.perhitungan").setParameter(1, no_reg).getResultList()
        );
    }

    public List<Object[]> findPluggingReeferServiceByReg(String no_reg) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PluggingReeferService.Native.findByReg").setParameter(1, no_reg).getResultList()
        );
    }

    public List<Object[]> findByRegPluggingDelivery(String no_reg) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PluggingReeferService.Native.findByRegPluggingDelivery").setParameter(1, no_reg).getResultList()
        );
    }

    public Object[] findPluggingReeferServiceByGatePlugging(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PluggingReeferService.Native.findPluggingReeferServiceByGatePlugging").setParameter(1, job_slip).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findSelectPluggingReeferService() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PluggingReeferService.Native.findSelectPluggingReeferService").getResultList()
        );
    }

    public List<String> findJobSlipByPPKBnCONT(String no_ppkb, String cont_no) {
        return getEntityManager().createNamedQuery("PluggingReeferService.Native.findJobSlipByPPKBnCONT").setParameter(1, no_ppkb).setParameter(2, cont_no).getResultList();
    }

    public List<Object[]> findByNoregValidasiPrint(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("PluggingReeferService.Native.findByNoregValidasiPrint").setParameter(1, no_reg).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByNoregPlugingReefer(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("PluggingReeferService.Native.findByNoregPlugingReefer").setParameter(1, no_reg).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findPluggingReeferServiceByJobSlip(String cont_no) {
        Object[] temp = new Object[6];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PluggingReeferService.Native.findPluggingReeferServiceByJobSlip").setParameter(1, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findSelectDeliveryPluggingReeferService(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("PluggingReeferService.Native.findSelectDeliveryPluggingReeferService").setParameter(1, no_reg).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findPerhitunganDeliveryPluggingService(String no_reg) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("PluggingReeferService.Native.findPerhitunganDeliveryPluggingService").setParameter(1, no_reg).getResultList()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findPluggingReeferServiceByDeliveryConfirm() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PluggingReeferService.Native.findPluggingReeferServiceByDeliveryConfirm").getResultList()
        );
    }

    public List<Object[]> findPluggingReeferServiceByDeliveryConfirmList() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PluggingReeferService.Native.findPluggingReeferServiceByDeliveryConfirmList").getResultList()
        );
    }

    public List<Object[]> findChangeStatusPluggingToLoad() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PluggingReeferService.Native.findChangeStatusPluggingToLoad").getResultList()
        );
    }

    public List<Object[]> findPreviewServiceReceivingPlugging() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PluggingReeferService.Native.findPreviewServiceReceivingPlugging").getResultList()
        );
    }

    public Object[] findUpdateServiceReceiving(String cont_no, String no_ppkb) {
        Object[] temp = new Object[2];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PluggingReeferService.Native.findUpdateServiceReceiving").setParameter(1, cont_no).setParameter(2, no_ppkb).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public String findPluggingReeferValidasiInput(String cont_no, String no_ppkb, String no_reg) {
        String temp = null;
        try {
            temp = (String) getEntityManager().createNamedQuery("PluggingReeferService.Native.findPluggingReeferValidasiInput").setParameter(1, cont_no).setParameter(2, no_ppkb).setParameter(3, no_reg).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findSelectPluggingReeferServiceReceivingHht(String cont_no) {
        Object[] temp = new Object[4];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PluggingReeferService.Native.findSelectPluggingReeferServiceReceivingHht").setParameter(1, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findPluggingReeferServiceByDeliveryConfirmHht(String cont_no) {
        Object[] temp = new Object[11];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("PluggingReeferService.Native.findPluggingReeferServiceByDeliveryConfirmHht").setParameter(1, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByRegCancelInvoice(String no_reg) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("PluggingReeferService.Native.findByRegCancelInvoice").setParameter(1, no_reg).getResultList()
        );
    }

}
