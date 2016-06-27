/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterVesselProfileFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterVesselProfileFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterVesselProfile;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class MasterVesselProfileFacade extends AbstractFacade<MasterVesselProfile> implements MasterVesselProfileFacadeLocal, MasterVesselProfileFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterVesselProfileFacade() {
        super(MasterVesselProfile.class);
    }

    public List<Object[]> findBayIdentityByVessel(String vesselCode, int bay) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterVesselProfile.Native.findBayIdentityByVessel").
                setParameter(1, vesselCode).
                setParameter(2, bay).
                getResultList()
        );
    }

    public List<Object[]> findBayIdentityByPpkb(String noPpkb, int bay) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterVesselProfile.Native.findBayIdentityByPpkb").
                setParameter(1, noPpkb).
                setParameter(2, bay).
                getResultList()
        );
    }

    public List<Integer> findAllByIdVessel(String vessel_code) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("MasterVesselProfile.Native.findAllByIdVessel").setParameter(1, vessel_code).getResultList()
        );
    }

    public int findLastOfID() {
        return (Integer) getEntityManager().createNamedQuery("MasterVesselProfile.Native.findLastOfID").getSingleResult();
    }

    public int deleteByIdVessel(String vessel_code) {
        return getEntityManager().createNamedQuery("MasterVesselProfile.Native.deleteByIdVessel").setParameter(1, vessel_code).executeUpdate();
    }

    @Override
    public List<Integer> findBaysByVessel(String vesselCode) {
        return decimalsToInts(getEntityManager().createNamedQuery("MasterVesselProfile.Native.findBaysByVessel").
                setParameter(1, vesselCode).
                getResultList());
    }

    public List<String> findBaysByNoPpkb(String noPpkb) {
        return getEntityManager().createNamedQuery("MasterVesselProfile.Native.findBaysByNoPpkb").
                setParameter(1, noPpkb).
                getResultList();
    }

    public List<Object[]> findRowByBay(String vessel_code, String position, int bay_no) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterVesselProfile.Native.findRowByBay").setParameter(1, vessel_code).setParameter(2, position).setParameter(3, bay_no).getResultList()
        );
    }

    public List<Object[]> findMVesselBay(String vessel_code) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterVesselProfile.Native.findMVesselBay").setParameter(1, vessel_code).getResultList()
        );
    }

    public List<Object[]> findAllId(String vessel_code, int bay_no) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterVesselProfile.Native.findAllId").setParameter(1, vessel_code).setParameter(2, bay_no).getResultList()
        );
    }

    public List<Object[]> findDistinctAllByVesselCode(String vessel_code) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterVesselProfile.Native.findDistinctAllByVesselCode").setParameter(1, vessel_code).getResultList()
        );
    }

    public List<Object[]> findNotAvailableBaysLocationByNoPpkbAndBay(String noPpkb, Short bay) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterVesselProfile.Native.findNotAvailableBaysLocationByNoPpkbAndBay")
                .setParameter(1, noPpkb)
                .setParameter(2, bay)
                .getResultList()
        );
    }
    
    @Override
    public List<Object[]> findBayIdentityByPPKBAndBay(String ppkb, int bay){
        String sql = "SELECT vesselprofile.row_number, nvl(contLoad.cont_gross) AS gross, "
                + "MIN(vesselProfile.start_row) AS start_row, MIN(profileDetail.start_tier) AS start_tier "
                + "FROM m_vessel_profile vesselProfile "
                + "inner join m_profile_detail profileDetail on profileDetail.profile_code=vesselProfile.profile_code "
                + "inner join planning_vessel planningVessel on planningVessel.vessel_code=vesselProfile.vessel_code "
                + "inner join service_cont_load contLoad on contLoad.no_ppkb=planningVessel.no_ppkb "
                + "where contLoad.no_ppkb='" + ppkb + "' and vesselProfile.bay_no=" + bay
                + " GROUP BY vesselprofile.row_number, contLoad.cont_gross, vesselProfile.position, vesselProfile.start_row "
                + "ORDER BY vesselProfile.position";
        return objectsDecimalsToObjectsInts(getEntityManager().createNativeQuery(sql).getResultList());
    }
}
