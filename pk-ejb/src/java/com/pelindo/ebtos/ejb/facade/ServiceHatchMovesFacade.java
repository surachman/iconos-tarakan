/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceHatchMovesFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceHatchMovesFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceHatchMoves;
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
public class ServiceHatchMovesFacade extends AbstractFacade<ServiceHatchMoves> implements ServiceHatchMovesFacadeRemote, ServiceHatchMovesFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceHatchMovesFacade() {
        super(ServiceHatchMoves.class);
    }

    public List<Object[]> findServiceHatchMovesByPpkb(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceHatchMoves.Native.findServiceHatchMovesByPpkb").setParameter(1, no_ppkb).getResultList();
    }

     public List<Object[]> findServiceHatchMovesByPpkbDischarge(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceHatchMoves.Native.findServiceHatchMovesByPpkbDischarge").setParameter(1, no_ppkb).getResultList();
    }

     public List<Object[]> findServiceHatchMovesByPpkbLoad(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceHatchMoves.Native.findServiceHatchMovesByPpkbLoad").setParameter(1, no_ppkb).getResultList();
    }

     //penambahan pemilihan discharge by ade chelsea 8 april 2014
    public List<Object[]> findServiceHatchMovesByPpkbOperationDischarge(String no_ppkb,String operation) {
        return getEntityManager().createNamedQuery("ServiceHatchMoves.Native.findServiceHatchMovesByPpkbOperationDischarge").setParameter(1, no_ppkb).setParameter(2,operation).getResultList();
    }
    //penambahan pemilihan load by ade chelsea 8 april 2014
    public List<Object[]> findServiceHatchMovesByPpkbOperationLoad(String no_ppkb,String operation) {
        return getEntityManager().createNamedQuery("ServiceHatchMoves.Native.findServiceHatchMovesByPpkbOperationLoad").setParameter(1, no_ppkb).setParameter(2,operation).getResultList();
    }
    
    public List<Object[]> findServiceHatchMovesByPpkbOperation(String no_ppkb,String operation) {
        return getEntityManager().createNamedQuery("ServiceHatchMoves.Native.findServiceHatchMovesByPpkbOperation").setParameter(1, no_ppkb).setParameter(2,operation).getResultList();
    }

    public List<Object[]> findServiceHatchMovesByPpkbRecap(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceHatchMoves.Native.findServiceHatchMovesByPpkbRecap").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public ServiceHatchMoves findByOperationBayAndHatchMoves(String noPpkb, Short bay, String operation, String hatchMoves) {
        try {
            return (ServiceHatchMoves) getEntityManager().createNamedQuery("ServiceHatchMoves.findByOperationBayAndHatchMoves")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("bay", bay)
                    .setParameter("operation", operation)
                    .setParameter("hatchMoves", hatchMoves)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ServiceHatchMoves> findByNoPpkbAndStatusPaid(String noPpkb) {
        return getEntityManager().createNamedQuery("ServiceHatchMoves.findByNoPpkbAndStatusPaid")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

}
