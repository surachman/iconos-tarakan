/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.PreserviceVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PreserviceVesselFacadeRemote;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class PreserviceVesselFacade extends AbstractFacade<PreserviceVessel> implements PreserviceVesselFacadeRemote, PreserviceVesselFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PreserviceVesselFacade() {
        super(PreserviceVessel.class);
    }

   public List<Object[]> findPreserviceVessels(){
        return getEntityManager().createNamedQuery("PreserviceVessel.Native.findPreserviceVessels").getResultList();
   }  

   public List<Object[]> findCanceledPreserviceVessels(){
        return getEntityManager().createNamedQuery("PreserviceVessel.Native.findCanceledPreserviceVessels").getResultList();
   }

   public Object[] findPreserviceVesselByBooking(String booking_code){
        return (Object[]) getEntityManager().createNamedQuery("PreserviceVessel.Native.findPreserviceVesselByBooking").setParameter(1, booking_code).getSingleResult();
   }

   public List<Object[]> findPreserviceVesselByPort (String port_code){
        return getEntityManager().createNamedQuery("PreserviceVessel.Native.findPreserviceVesselByPort").setParameter(1, port_code).setParameter(2, port_code).getResultList();
   }

   public List<Object[]> findPreserviceVesselByVessel (String vessel_code){
        return getEntityManager().createNamedQuery("PreserviceVessel.Native.findPreserviceVesselByVessel").setParameter(1, vessel_code).getResultList();
   }//PreserviceVessel.Native.findPreserviceVesselOnlines
    public List<Object[]> findPreserviceVesselOnlines(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("PreserviceVessel.Native.findPreserviceVesselOnlines").setParameter(1, no_ppkb).getResultList();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }
    @EJB MasterPortFacadeRemote masterPortFacadeRemote;

    @Override
    public MasterPort findNextPortByBookingCode(String bookingCode){
        String sql = "select next_port_code from preservice_vessel where booking_code='" + bookingCode + "'";
        String portCode = (String)em.createNativeQuery(sql).getSingleResult();
        return masterPortFacadeRemote.find(portCode);
    }

}
