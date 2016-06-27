/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterVesselProfile;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface MasterVesselProfileFacadeLocal {

    void create(MasterVesselProfile masterVesselProfile);

    void edit(MasterVesselProfile masterVesselProfile);

    void remove(MasterVesselProfile masterVesselProfile);

    MasterVesselProfile find(Object id);

    List<MasterVesselProfile> findAll();

    List<MasterVesselProfile> findRange(int[] range);

    int count();

    List<Object[]> findBayIdentityByVessel(String vesselCode, int bay);

    List<Object[]> findBayIdentityByPpkb(String noPpkb, int bay);
    
    public List<Object[]> findBayIdentityByPPKBAndBay(String ppkb, int bay);
}
