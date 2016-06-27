/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterVesselFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterVessel;
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
public class MasterVesselFacade extends AbstractFacade<MasterVessel> implements MasterVesselFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterVesselFacade() {
        super(MasterVessel.class);
    }
    public List<Object[]> findMasterVessels(){    
        return getEntityManager().createNamedQuery("MasterVessel.Native.findMasterVessels").getResultList();
   }
    public Object[] findMasterVesselsByVesselCode(String vessel_code){
        return (Object[])getEntityManager().createNamedQuery("MasterVessel.Native.findMasterVesselsByVesselCode").setParameter(1, vessel_code).getSingleResult();
    }
    public List<Object[]> findMasterVesselsByVesselName(String name){
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("MasterVessel.Native.findMasterVesselsByVesselName").setParameter(1, name).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }
   public List<Object[]> findMasterVesselByCodeDelete (int vessel_type_code){
        return getEntityManager().createNamedQuery("MasterVessel.Native.findMasterVesselByCodeDelete").setParameter(1, vessel_type_code).getResultList();
   }
   public List<Object[]> findMasterVesselByPPKB (String no_ppkb){
        return getEntityManager().createNamedQuery("MasterVessel.Native.findMasterVesselByPPKB").setParameter(1, no_ppkb).getResultList();
   }
   public List<Object[]> findMasterVesselsForChoice(){
        return getEntityManager().createNamedQuery("MasterVessel.Native.findMasterVesselsForChoice").getResultList();
   }
   public Object[] findMasterVesselDetail(String vessel_code){
        return (Object[]) getEntityManager().createNamedQuery("MasterVessel.Native.findMasterVesselDetail").setParameter(1, vessel_code).getSingleResult();
   }

   public List<Object[]> findMasterVesselsByCustCode(String cust_code){
        return getEntityManager().createNamedQuery("MasterVessel.Native.findMasterVesselsByCustCode").setParameter(1, cust_code).getResultList();
   }
}
