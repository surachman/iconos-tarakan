/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterVehicleFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterVehicleFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterVehicle;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author USER
 */
@Stateless
public class MasterVehicleFacade extends AbstractFacade<MasterVehicle> implements MasterVehicleFacadeLocal, MasterVehicleFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterVehicleFacade() {
        super(MasterVehicle.class);
    }

    @Override
    public List<Object[]> findAllNative(){
        return getEntityManager().createNamedQuery("MasterVehicle.Native.findAll").getResultList();
    }

    public List<Object[]> findTruckMonitoringList(String noPpkb){
        return getEntityManager().createNamedQuery("MasterVehicle.Native.findTruckMonitoringList")
                .setParameter(1, noPpkb)
                .setParameter(2, noPpkb)
                .getResultList();
    }

    @Override
    public List<Object[]> findLikeNumber(String nopol) {
        return getEntityManager().createNamedQuery("MasterVehicle.Native.findLikeNopol").setParameter(1, nopol).getResultList();
    }

}
