/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterSatuan;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterSatuanFacadeLocal {

    void create(MasterSatuan masterSatuan);

    void edit(MasterSatuan masterSatuan);

    void remove(MasterSatuan masterSatuan);

    MasterSatuan find(Object id);

    List<MasterSatuan> findAll();

    List<MasterSatuan> findRange(int[] range);

    int count();

}
