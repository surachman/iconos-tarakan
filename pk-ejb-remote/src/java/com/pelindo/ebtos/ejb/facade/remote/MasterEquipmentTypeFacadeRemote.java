/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterEquipmentType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface MasterEquipmentTypeFacadeRemote {

    void create(MasterEquipmentType masterEquipmentType);

    void edit(MasterEquipmentType masterEquipmentType);

    void remove(MasterEquipmentType masterEquipmentType);

    MasterEquipmentType find(Object id);

    List<MasterEquipmentType> findAll();

    List<MasterEquipmentType> findRange(int[] range);

    int count();

}
