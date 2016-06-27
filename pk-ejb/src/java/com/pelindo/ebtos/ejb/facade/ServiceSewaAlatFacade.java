/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.ServiceSewaAlatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.ServiceSewaAlatFacadeLocal;
import com.pelindo.ebtos.model.db.ServiceSewaAlat;
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
public class ServiceSewaAlatFacade extends AbstractFacade<ServiceSewaAlat> implements ServiceSewaAlatFacadeLocal, ServiceSewaAlatFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceSewaAlatFacade() {
        super(ServiceSewaAlat.class);
    }

    public List<Object[]> findEquipmentSewaAlat() {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("ServiceSewaAlat.Native.findAllSewaAlat").getResultList();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
}
