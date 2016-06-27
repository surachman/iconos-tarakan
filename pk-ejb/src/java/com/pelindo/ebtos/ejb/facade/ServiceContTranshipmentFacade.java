/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceContTranshipmentFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContTranshipmentFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ServiceContTranshipmentFacade extends AbstractFacade<ServiceContTranshipment> implements ServiceContTranshipmentFacadeRemote, ServiceContTranshipmentFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceContTranshipmentFacade() {
        super(ServiceContTranshipment.class);
    }

    public Object[] findByContNo(String cont_no, String pos) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceContTranshipment.Native.findByContNo").setParameter(1, cont_no).setParameter(2, pos).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public ServiceContTranshipment findByTranshipmentContainerByPosition(String noPpkb, String contNo, String position) {
        try {
            return (ServiceContTranshipment) getEntityManager().createNamedQuery("ServiceContTranshipment.findByTranshipmentContainerByPosition")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("position", position)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceContTranshipment findByTranshipmentByContNoAndPosition(String contNo, String position) {
        try {
            return (ServiceContTranshipment) getEntityManager().createNamedQuery("ServiceContTranshipment.findByTranshipmentByContNoAndPosition")
                    .setParameter("contNo", contNo)
                    .setParameter("position", position)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Object[]> findServiceContTranshipmentByPpkbList(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceContTranshipment.Native.findServiceContTranshipmentByPpkbList").setParameter(1, no_ppkb).getResultList();
    }

    //ServiceContTranshipment.Native.findServiceContTranshipmentsConfirm
    public List<Object[]> findServiceContTranshipmentsConfirm(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceContTranshipment.Native.findServiceContTranshipmentsConfirm").setParameter(1, no_ppkb).getResultList();
    }

    //ServiceContTranshipment.Native.findServiceContTranshipmentsSelect
    public List<Object[]> findServiceContTranshipmentsSelect(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceContTranshipment.Native.findServiceContTranshipmentsSelect").setParameter(1, no_ppkb).getResultList();
    }

    //ServiceContTranshipment.Native.updateServiceContTranshipmentsForDeleteAll
    public int updateServiceContTranshipmentsForDeleteAll(int id) {
        return getEntityManager().createNamedQuery("ServiceContTranshipment.Native.updateServiceContTranshipmentsForDeleteAll").setParameter(1, id).executeUpdate();
    }

    public List<Object[]> findServiceContTranshipmentsConfirmServed(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceContTranshipment.Native.findServiceContTranshipmentsConfirmServed").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findServiceContTranshipmentsSelectServed(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceContTranshipment.Native.findServiceContTranshipmentsSelectServed").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findServiceContTranshipmentsByNewPpkbNull() {
        return getEntityManager().createNamedQuery("ServiceContTranshipment.Native.findServiceContTranshipmentsByNewPpkbNull").getResultList();
    }

    public int updateServiceContTranshipmentsForDeleteAllPlacement(int id) {
        return getEntityManager().createNamedQuery("ServiceContTranshipment.Native.updateServiceContTranshipmentsForDeleteAllPlacement").setParameter(1, id).executeUpdate();
    }

    public List<Object[]> findRekap(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceContTranshipment.Native.findRekap").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findByPpkb(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceContTranshipment.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Integer findByIdTranshipment(String no_ppkb, String cont_no) {
        Integer idtrans = null;
        try {
            idtrans = (Integer) getEntityManager().createNamedQuery("ServiceContTranshipment.Native.findByIdTranshipment").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            idtrans = null;
        }
        return idtrans;
    }

    public List<ServiceContTranshipment> findContainersThatHaveNotReachedCY(String noPpkb) {
        List<String> positionExceptions = Arrays.asList("01", "02");

        return getEntityManager().createNamedQuery("ServiceContTranshipment.findByPpkbWithPositions")
                .setParameter("noPpkb", noPpkb)
                .setParameter("positions", positionExceptions)
                .getResultList();
    }

    public List<ServiceContTranshipment> findByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("ServiceContTranshipment.findByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

    @Override
    public ServiceContTranshipment findByNewPpkbAndContNo(String newPpkb, String contNo) {
        try {
            return (ServiceContTranshipment) getEntityManager().createNamedQuery("ServiceContTranshipment.findByNewPpkbAndContNo")
                    .setParameter("newPpkb", newPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
