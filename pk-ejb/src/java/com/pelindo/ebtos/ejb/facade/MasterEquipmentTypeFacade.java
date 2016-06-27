/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;


import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentTypeFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterEquipmentType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dyware-Dev01
 */
@Stateless
public class MasterEquipmentTypeFacade extends AbstractFacade<MasterEquipmentType> implements MasterEquipmentTypeFacadeRemote{
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterEquipmentTypeFacade() {
        super(MasterEquipmentType.class);
    }
    
    @Override
    public List<Object[]> findAllNative(){
        return getEntityManager().createNamedQuery("MasterEquipmentType.Native.findAll").getResultList();
    }

}
