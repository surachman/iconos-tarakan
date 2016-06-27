/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterGrossClass;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterGrossClassFacadeLocal {

    void create(MasterGrossClass masterGrossClass);

    void edit(MasterGrossClass masterGrossClass);

    void remove(MasterGrossClass masterGrossClass);

    MasterGrossClass find(Object id);

    List<MasterGrossClass> findAll();

    List<MasterGrossClass> findRange(int[] range);

    int count();

    List<Object[]> findGrossClasses();

}
