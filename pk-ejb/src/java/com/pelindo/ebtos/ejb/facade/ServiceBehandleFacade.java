/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.ServiceBehandleFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.ServiceBehandleFacadeLocal;
import com.pelindo.ebtos.model.db.ServiceBehandle;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dyware-Dev01
 */
@Stateless
public class ServiceBehandleFacade extends AbstractFacade<ServiceBehandle> implements ServiceBehandleFacadeLocal, ServiceBehandleFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceBehandleFacade() {
        super(ServiceBehandle.class);
    }

    public Object[] findByContNo(String no_ppkb, String cont_no) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceBehandle.Native.findByContNo").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public ServiceBehandle findNotFinishedYetByNoPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (ServiceBehandle) getEntityManager().createNamedQuery("ServiceBehandle.findNotFinishedYetByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
