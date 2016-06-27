/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterVehicleType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author arie
 */
@Local
public interface MasterVehicleTypeFacadeLocal {

    void create(MasterVehicleType masterVehicleType);

    void edit(MasterVehicleType masterVehicleType);

    void remove(MasterVehicleType masterVehicleType);

    MasterVehicleType find(Object id);

    List<MasterVehicleType> findAll();

    List<MasterVehicleType> findRange(int[] range);

    int count();

}
