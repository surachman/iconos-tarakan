/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterYardFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author USER
 */
@Stateless
public class MasterYardFacade extends AbstractFacade<MasterYard> implements MasterYardFacadeLocal, MasterYardFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterYardFacade() {
        super(MasterYard.class);
    }

    public MasterYard find(Object id) {
        return super.find(id.toString().toUpperCase());
    }

    public Object[] findMasterYardByBlock(String block){
        return objectDecimalsToObjectInts(
                (Object[])getEntityManager().createNamedQuery("MasterYard.Native.findMasterYardByBlock").setParameter(1, block).getSingleResult()
        );
    }

    public List<Object[]> findMasterYards() {
        return objectsDecimalsToObjectsInts(
                (List<Object[]>) getEntityManager().createNamedQuery("MasterYard.Native.findMasterYards").getResultList()
        );
    }

    public List<String> findNotPlannedBlockLocationByNoPpkb(String noPpkb) {
        return (List<String>) getEntityManager().createNamedQuery("MasterYard.Native.findNotPlannedBlockLocationByNoPpkb")
                .setParameter(1, noPpkb)
                .getResultList();
    }

    public List<Integer> findNotPlannedSlotLocationByNoPpkbAndBlock(String noPpkb, String block) {
        return decimalsToInts(getEntityManager().createNamedQuery("MasterYard.Native.findNotPlannedSlotLocationByNoPpkbAndBlock")
                .setParameter(1, noPpkb)
                .setParameter(2, block)
                .getResultList());
    }

    public Object[] findAllMax(String block) {
        return (Object[]) getEntityManager().createNamedQuery("MasterYard.Native.findAllMax").setParameter(1, block).getSingleResult();
    }

    public List<String> findAllBlocks(){
        return (List<String>) getEntityManager().createNamedQuery("MasterYard.Native.findAllBlocks").getResultList();
    }

    @Override
    public MasterYard findMasterYardsSimpleByBlock(String block) {
        return (MasterYard) getEntityManager().createNamedQuery("MasterYard.Native.findMasterYardsSimpleByBlock").setParameter(1, block).getSingleResult();
    }

    @Override
    public Long findYardCapacity() {
        return (Long) getEntityManager().createNamedQuery("MasterYard.Native.findYardCapacity").getSingleResult();
    }
    
    

}
