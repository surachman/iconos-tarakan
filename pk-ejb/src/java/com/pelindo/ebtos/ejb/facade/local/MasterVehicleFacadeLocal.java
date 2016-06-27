/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterVehicle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface MasterVehicleFacadeLocal {

    void create(MasterVehicle masterVehicle);

    void edit(MasterVehicle masterVehicle);

    void remove(MasterVehicle masterVehicle);

    MasterVehicle find(Object id);

    List<MasterVehicle> findAll();

    List<MasterVehicle> findRange(int[] range);

    int count();

    public java.util.List<java.lang.Object[]> findLikeNumber(java.lang.String nopol);

}
