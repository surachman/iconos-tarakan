/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.exception.MasterSettingAppNotFoundException;
import com.pelindo.ebtos.exception.MasterSettingAppValueNotValidException;
import com.pelindo.ebtos.model.db.master.MasterEquipment;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterEquipmentFacadeRemote {

    void create(MasterEquipment masterEquipment);

    void edit(MasterEquipment masterEquipment);

    void remove(MasterEquipment masterEquipment);

    MasterEquipment find(Object id);

    List<MasterEquipment> findAll();

    List<MasterEquipment> findRange(int[] range);

    List<Object[]> findAllNative();

    int count();

    List<String> findCrane();

    List<String> findHt();

    List<String> findTt();

    List<Object[]> findCraneForView();

    List<Object[]> findHtForView();

    List<Object[]> findTangoForView();

    List<Object[]> findCraneExcKapal();

    List<Object[]> findOwnerReport(String owner);

    List<Object[]> findEquipmentSewaAlat();

    List<MasterEquipment> findMasterEquipmentHTOnly() throws MasterSettingAppNotFoundException, MasterSettingAppValueNotValidException;

    List<MasterEquipment> findMasterEquipmentByType(String type);

    public java.util.List<java.lang.Object> findCrane4HHT();

    public java.util.List<java.lang.Object> findHeadTruck4HHT();

    public java.util.List<java.lang.Object> findTango4HHT();
}
