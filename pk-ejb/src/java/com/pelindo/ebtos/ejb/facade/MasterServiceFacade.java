/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterServiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterServiceFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterService;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterServiceFacade extends AbstractFacade<MasterService> implements MasterServiceFacadeRemote, MasterServiceFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterServiceFacade() {
        super(MasterService.class);
    }
    
    public List<Object[]> findMasterServices(){
        return getEntityManager().createNamedQuery("MasterService.Native.findMasterServices").getResultList();
   }
    
    public List<Object[]> findSelectService(){
        return getEntityManager().createNamedQuery("MasterService.findSelectService").getResultList();
   }
    
    public List<Object[]> findSelectServiceDelivery(){
        return getEntityManager().createNamedQuery("MasterService.findSelectServiceDelivery").getResultList();
   }
    
    public List<Object[]> findSelectServiceDeliveryUC(){
        return getEntityManager().createNamedQuery("MasterService.findSelectServiceDeliveryUC").getResultList();
   }
    
    public List<Object[]> findSelectServiceUC(){
        return getEntityManager().createNamedQuery("MasterService.findSelectServiceUC").getResultList();
   }
    
    
    
    
}
