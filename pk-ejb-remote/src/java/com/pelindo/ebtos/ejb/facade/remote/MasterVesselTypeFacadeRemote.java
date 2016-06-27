/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterVesselType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterVesselTypeFacadeRemote {

    void create(MasterVesselType masterVesselType);

    void edit(MasterVesselType masterVesselType);

    void remove(MasterVesselType masterVesselType);

    MasterVesselType find(Object id);

    List<MasterVesselType> findAll();

    List<MasterVesselType> findRange(int[] range);

    int count();

    List<Object[]> findMasterVesselType();

}
