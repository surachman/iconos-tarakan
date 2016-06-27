/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceReeferFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceReeferFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceReefer;
import java.util.Date;
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
public class ServiceReeferFacade extends AbstractFacade<ServiceReefer> implements ServiceReeferFacadeRemote, ServiceReeferFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceReeferFacade() {
        super(ServiceReefer.class);
    }

    public Object[] findServiceReeferById(Integer id) {
        return (Object[]) getEntityManager().createNamedQuery("ServiceReefer.Native.findServiceReeferById").setParameter(1, id).getSingleResult();
    }

    public ServiceReefer findByContNoPpkbAndOperation(String contNo, String noPpkb, String operation) {
        try {
            return (ServiceReefer) getEntityManager().createNamedQuery("ServiceReefer.findByContNoPpkbAndOperation")
                    .setParameter("contNo", contNo)
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("operation", operation)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public ServiceReefer findByCountableLoadReefer(String contNo, String noPpkb) {
        try {
            return (ServiceReefer) getEntityManager().createNamedQuery("ServiceReefer.findByCountableLoadReefer")
                    .setParameter("contNo", contNo)
                    .setParameter("noPpkb", noPpkb)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Object[]> findServiceReeferByPpkbDischarge(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceReefer.Native.findServiceReeferByPpkbDischarge").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findServiceReeferByPpkbLoad(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceReefer.Native.findServiceReeferByPpkbLoad").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findServiceReeferByRegPlugging(String no_reg) {
        return getEntityManager().createNamedQuery("ServiceReefer.Native.findServiceReeferByRegPlugging").setParameter(1, no_reg).getResultList();
    }

    public Object[] findByContNo(String cont_no) {
        Object[] temp = new Object[5];
        try {
            return (Object[]) getEntityManager().createNamedQuery("ServiceReefer.Native.findByContNo").setParameter(1, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Date findPlugOn(String cont_no, String no_ppkb) {
        Date temp = new Date();
        try {
            temp = (Date) getEntityManager().createNamedQuery("ServiceReefer.Native.findPlugOnForReeferDischargeService").setParameter(1, cont_no).setParameter(2, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Date findPlugOnForReeferPluggingService(String cont_no) {
        Date temp = new Date();
        try {
            temp = (Date) getEntityManager().createNamedQuery("ServiceReefer.Native.findPlugOnForReeferPluggingService").setParameter(1, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Date findPlugOffForReeferPluggingService(String cont_no) {
        Date temp = new Date();
        try {
            temp = (Date) getEntityManager().createNamedQuery("ServiceReefer.Native.findPlugOffForReeferPluggingService").setParameter(1, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findPlugOnOffForReeferLoadService(String cont_no, String no_ppkb) {
        Object[] temp;
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceReefer.Native.findPlugOnOffForReeferLoadService").setParameter(1, cont_no).setParameter(2, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Integer> findIDReeferByPPKBnCONT(String no_ppkb, String cont_no) {
        return getEntityManager().createNamedQuery("ServiceReefer.Native.findIDReeferByPPKBnCONT").setParameter(1, no_ppkb).setParameter(2, cont_no).getResultList();
    }

    public Integer findValidasiPenumpukan(String no_ppkb, String cont_no, String operation) {
        Integer temp = 0;        
        try {
            temp = (Integer) getEntityManager().createNamedQuery("ServiceReefer.Native.findValidasiPenumpukan")
                    .setParameter(1, no_ppkb)
                    .setParameter(2, cont_no)
                    .setParameter(3, operation).getSingleResult();
        } catch (NoResultException ex) {
            temp = 0;
        }
        return temp;
    }
    
    public Integer findValidasiUpdatePlugging(String jobslip, String cont_no, String operation) {
        Integer temp = 0;        
        try {
            temp = (Integer) getEntityManager().createNamedQuery("ServiceReefer.Native.findValidasiUpdatePlugging")
                    .setParameter(1, jobslip)
                    .setParameter(2, cont_no)
                    .setParameter(3, operation).getSingleResult();
        } catch (NoResultException ex) {
            temp = 0;
        }
        return temp;
    }


}
