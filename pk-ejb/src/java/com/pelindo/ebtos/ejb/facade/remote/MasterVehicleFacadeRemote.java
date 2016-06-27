/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterVehicle;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author USER
 */
@Remote
public interface MasterVehicleFacadeRemote {

    void create(MasterVehicle masterVehicle);

    void edit(MasterVehicle masterVehicle);

    void remove(MasterVehicle masterVehicle);

    MasterVehicle find(Object id);

    List<MasterVehicle> findAll();

    List<MasterVehicle> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();

    public java.util.List<java.lang.Object[]> findTruckMonitoringList(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findLikeNumber(java.lang.String nopol);

}
