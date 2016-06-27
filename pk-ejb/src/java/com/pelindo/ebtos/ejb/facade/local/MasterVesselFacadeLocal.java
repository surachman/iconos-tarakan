/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterVessel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterVesselFacadeLocal {

    void create(MasterVessel masterVessel);

    void edit(MasterVessel masterVessel);

    void remove(MasterVessel masterVessel);

    MasterVessel find(Object id);

    List<MasterVessel> findAll();

    List<MasterVessel> findRange(int[] range);

    int count();

}
