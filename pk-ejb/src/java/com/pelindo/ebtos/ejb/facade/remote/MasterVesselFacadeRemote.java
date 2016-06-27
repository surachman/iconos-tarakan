/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterVesselFacadeRemote {

    void create(MasterVessel masterVessel);

    void edit(MasterVessel masterVessel);

    void remove(MasterVessel masterVessel);

    MasterVessel find(Object id);

    List<MasterVessel> findAll();

    List<MasterVessel> findRange(int[] range);

    int count();

    List<Object[]> findMasterVessels();

    Object[] findMasterVesselsByVesselCode(String vessel_code);

    List<Object[]> findMasterVesselsByVesselName(String name);

     List<Object[]> findMasterVesselByCodeDelete (int vessel_type_code);

     List<Object[]> findMasterVesselByPPKB (String no_ppkb);

     List<Object[]> findMasterVesselsForChoice();

     Object[] findMasterVesselDetail(String vessel_code);

     List<Object[]> findMasterVesselsByCustCode(String cust_code);

}
