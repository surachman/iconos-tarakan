/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.ServiceIdleTimeFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceIdleTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class ServiceIdleTimeFacade extends AbstractFacade<ServiceIdleTime> implements ServiceIdleTimeFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceIdleTimeFacade() {
        super(ServiceIdleTime.class);
    }
  public List<Object[]> findServiceIdleTimesByPpkb(String no_ppkb){
       return getEntityManager().createNamedQuery("ServiceIdleTime.Native.findServiceIdleTimesByPpkb").setParameter(1, no_ppkb).getResultList();
    }
}
