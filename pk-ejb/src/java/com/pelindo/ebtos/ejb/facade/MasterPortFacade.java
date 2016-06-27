/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterPortFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterPortFacade extends AbstractFacade<MasterPort> implements MasterPortFacadeLocal, MasterPortFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterPortFacade() {
        super(MasterPort.class);
    }

    public List<Object[]> findAllNative(){
        return getEntityManager().createNamedQuery("MasterPort.Native.findAll").getResultList();
    }

    public List<Object[]> findMasterPortForIdentify(String idPort){
        return getEntityManager().createNamedQuery("MasterPort.Native.findMasterPortForIdentify").setParameter(1, idPort).getResultList();
    }

    public List<Object[]> findNoError(){
        return getEntityManager().createNamedQuery("MasterPort.Native.findNoError").getResultList();
    }

    @Override
    public List<Object[]> findByName(String portName) {
        return getEntityManager().createNamedQuery("MasterPort.Native.findByName").setParameter(1, portName).getResultList();
    }

    @Override
    public List<Object[]> findLikeName(String portName) {
        return getEntityManager().createNamedQuery("MasterPort.Native.findLikeName").setParameter(1, portName).getResultList();
    }
    @Override
    public MasterPort findMasterPortByName(String portName) {
        try {
            return (MasterPort) getEntityManager().createNamedQuery("MasterPort.findByName")
                    .setParameter("name", portName)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }
    
    @Override
    public Object findNameByCode(String portCode) {
        return getEntityManager().createNamedQuery("MasterPort.Native.findNameByCode").setParameter(1, portCode).getSingleResult();
    }
}
