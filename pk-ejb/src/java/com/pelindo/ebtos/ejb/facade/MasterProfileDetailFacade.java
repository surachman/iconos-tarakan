/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterProfileDetailFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterProfileDetailFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterProfileDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterProfileDetailFacade extends AbstractFacade<MasterProfileDetail> implements MasterProfileDetailFacadeLocal, MasterProfileDetailFacadeRemote {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterProfileDetailFacade() {
        super(MasterProfileDetail.class);
    }

    public List<Object[]> findBayDetailByVessel(String vesselCode, int bay) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterProfileDetail.Native.findBayDetailByVessel").
                setParameter(1, vesselCode).
                setParameter(2, bay).
                getResultList()
        );
    }

    public List<Object[]> findBayDetailByPpkb(String noPpkb, int bay) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterProfileDetail.Native.findBayDetailByPpkb").
                setParameter(1, noPpkb).
                setParameter(2, bay).
                getResultList()
        );
    }

    public List<Object[]> findAllByProfileCode (int profile_code){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterProfileDetail.Native.findAllByProfileCode").setParameter(1, profile_code).getResultList()
        );
    }

    public int findLastOfID(){
        return (Integer) getEntityManager().createNamedQuery("MasterProfileDetail.Native.findLastOfID").getSingleResult();
    }

    public int deleteByProfileCode(int profile_code){
        return getEntityManager().createNamedQuery("MasterProfileDetail.Native.deleteByProfileCode").setParameter(1, profile_code).executeUpdate();
    }

    public List<Object[]> findTierByRow(String vessel_code, String position, int bay_no, int row_name){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterProfileDetail.Native.findTierByRow")
                .setParameter(1, vessel_code)
                .setParameter(2, position)
                .setParameter(3, bay_no)
                .setParameter(4, row_name)
                .getResultList()
       );
    }
}
