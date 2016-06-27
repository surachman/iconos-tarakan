/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterOwnerEquipment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface MasterOwnerEquipmentFacadeLocal {

    void create(MasterOwnerEquipment masterOwnerEquipment);

    void edit(MasterOwnerEquipment masterOwnerEquipment);

    void remove(MasterOwnerEquipment masterOwnerEquipment);

    MasterOwnerEquipment find(Object id);

    List<MasterOwnerEquipment> findAll();

    List<MasterOwnerEquipment> findRange(int[] range);

    int count();

}
