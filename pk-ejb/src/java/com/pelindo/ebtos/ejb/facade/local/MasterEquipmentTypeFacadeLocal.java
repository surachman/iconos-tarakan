/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterEquipmentType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R Seno Anggoro
 */
@Local
public interface MasterEquipmentTypeFacadeLocal {

    void create(MasterEquipmentType masterEquipmentType);

    void edit(MasterEquipmentType masterEquipmentType);

    void remove(MasterEquipmentType masterEquipmentType);

    MasterEquipmentType find(Object id);

    List<Object[]> findAllNative();

    List<MasterEquipmentType> findRange(int[] range);

    int count();

}
