/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.YardAllocationTempFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.YardAllocationTempFacadeLocal;
import com.pelindo.ebtos.model.db.YardAllocationTemp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycode-java
 */
@Stateless
public class YardAllocationTempFacade extends AbstractFacade<YardAllocationTemp> implements YardAllocationTempFacadeLocal, YardAllocationTempFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public YardAllocationTempFacade() {
        super(YardAllocationTemp.class);
    }

    public int truncate(){
        return getEntityManager().createNamedQuery("YardAllocationTemp.Native.truncate").executeUpdate();
    }

    public List<Integer> findId(int id_cont){
        return decimalsToInts(
                getEntityManager().createNamedQuery("YardAllocationTemp.Native.findId").setParameter(1, id_cont).getResultList()
        );
    }
    
    public int deleteByIdYardAllocation(String id_yard_allocation){
        return getEntityManager().createNamedQuery("YardAllocationTemp.Native.deleteByIdYardAllocation").setParameter(1, id_yard_allocation).executeUpdate();
    }

    public int deleteByIdYardAllocationFilter(String id_yard_allocation_filter){
        return getEntityManager().createNamedQuery("YardAllocationTemp.Native.deleteByIdYardAllocationFilter").setParameter(1, id_yard_allocation_filter).executeUpdate();
    }

    //YardAllocationTemp.Native.distinctIdCont
    public List<Integer> distinctIdCont(){
        return decimalsToInts(
                getEntityManager().createNamedQuery("YardAllocationTemp.Native.distinctIdCont").getResultList()
        );
    }

    public List<Object[]> coordinatByIdCont(int id_cont) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("YardAllocationTemp.Native.coordinatByIdCont").setParameter(1, id_cont).getResultList()
        );
    }

    //YardAllocationTemp.Native.findAllTemp
    public List<Object[]> findAllTemp() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("YardAllocationTemp.Native.findAllTemp").getResultList()
        );
    }

    public List<Integer> findCreateGenerate(String no_ppkb, int id_constraint) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("YardAllocationTemp.Native.findCreateGenerate").setParameter(1, no_ppkb).setParameter(2, id_constraint).getResultList()
        );
    }


    public List<Object[]> findIdContNotGenerate(String no_ppkb, int id_constraint) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("YardAllocationTemp.Native.findIdContNotGenerate").setParameter(1, no_ppkb).setParameter(2, id_constraint).getResultList()
        );
    }

    public int updateCoordinat(int idCoordinat, String idYA, int id) {
        return getEntityManager().createNamedQuery("YardAllocationTemp.Native.updateCoordinat").setParameter(1, idCoordinat).setParameter(2, idYA).setParameter(3, id).executeUpdate();
    }

    public List<Object[]> removeGenerate(String idYA){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("YardAllocationTemp.Native.removeGenerate").setParameter(1, idYA).getResultList()
        );
    }

    public int updateIdCoordinat(int new_id, int old_id){
        return getEntityManager().createNamedQuery("YardAllocationTemp.Native.updateIdCoordinat").setParameter(1, new_id).setParameter(2, old_id).executeUpdate();
    }

    public int removeIdCoordinat(int id){
        return getEntityManager().createNamedQuery("YardAllocationTemp.Native.removeIdCoordinat").setParameter(1, id).executeUpdate();
    }

    public List<Integer> unFinishBayPlanDischargeByPPKB(String no_ppkb) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("YardAllocationTemp.Native.unFinishBayPlanDischargeByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<YardAllocationTemp> findByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("YardAllocationTemp.findByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }

}
