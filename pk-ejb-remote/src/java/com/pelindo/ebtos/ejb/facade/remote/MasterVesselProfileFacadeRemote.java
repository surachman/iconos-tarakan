/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterVesselProfile;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterVesselProfileFacadeRemote {

    void create(MasterVesselProfile masterVesselProfile);

    void edit(MasterVesselProfile masterVesselProfile);

    void remove(MasterVesselProfile masterVesselProfile);

    MasterVesselProfile find(Object id);

    List<MasterVesselProfile> findAll();

    int count();

    List<Integer> findAllByIdVessel(String vessel_code);

    int findLastOfID();

    int deleteByIdVessel(String vessel_code);

    List<Integer> findBaysByVessel(String vesselCode);

    List<Object[]> findRowByBay(String vessel_code, String position, int bay_no);

    List<Object[]> findMVesselBay(String vessel_code);

    List<Object[]> findAllId(String vessel_code, int bay_no);

    List<Object[]> findDistinctAllByVesselCode(String vessel_code);

    public java.util.List<java.lang.String> findBaysByNoPpkb(java.lang.String noPpkb);

    List<Object[]> findBayIdentityByPpkb(String noPpkb, int bay);

    public java.util.List<java.lang.Object[]> findNotAvailableBaysLocationByNoPpkbAndBay(java.lang.String noPpkb, java.lang.Short bay);
    
}
