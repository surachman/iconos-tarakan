/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterIdletimeFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterIdletimeFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterIdletime;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author USER
 */
@Stateless
public class MasterIdletimeFacade extends AbstractFacade<MasterIdletime> implements MasterIdletimeFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterIdletimeFacade() {
        super(MasterIdletime.class);
    }

    @Override
    public List<Object[]> findAllNative() {
        return getEntityManager().createNamedQuery("MasterIdletime.Native.findAllMasterIdletime").getResultList();
    }
    public List<Object[]> findAllMasterIdletimeByDelete (int id_type){
        return getEntityManager().createNamedQuery("MasterIdletime.Native.findAllMasterIdletimeByDelete").setParameter(1, id_type).getResultList();
   }

   public List<Object[]> findAllMasterIdletimeById (int id_type){
        return getEntityManager().createNamedQuery("MasterIdletime.Native.findAllMasterIdletimeById").setParameter(1, id_type).getResultList();
   }
}
