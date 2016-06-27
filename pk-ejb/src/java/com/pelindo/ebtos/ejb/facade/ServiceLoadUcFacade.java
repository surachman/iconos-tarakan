/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceLoadUcFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceLoadUcFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceLoadUc;
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
public class ServiceLoadUcFacade extends AbstractFacade<ServiceLoadUc> implements ServiceLoadUcFacadeRemote, ServiceLoadUcFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceLoadUcFacade() {
        super(ServiceLoadUc.class);
    }

    public Integer findByPpkbNBLno(String no_ppkb, String no_bl) {
        return (Integer) getEntityManager().createNamedQuery("ServiceLoadUc.Native.findIdByPPKBnBLno").setParameter(1, no_ppkb).setParameter(2, no_bl).getSingleResult();
    }

    public List<Object[]> findByPpkb(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceLoadUc.Native.findByPpkb").setParameter(1, no_ppkb).getResultList();
    }

    public List<Object[]> findByPpkbPick(String no_ppkb) {
        return getEntityManager().createNamedQuery("ServiceLoadUc.Native.findByPpkbPick").setParameter(1, no_ppkb).getResultList();
    }

    public Object[] findByBlNo(String no_ppkb, String bl_no, String pos) {
        Object[] temp = new Object[10];
        try {
            temp = (Object[]) getEntityManager().createNamedQuery("ServiceLoadUc.Native.findByBlNo").setParameter(1, no_ppkb).setParameter(2, bl_no).setParameter(3, pos).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
}
