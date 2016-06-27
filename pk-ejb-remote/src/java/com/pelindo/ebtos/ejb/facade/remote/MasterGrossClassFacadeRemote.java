/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterGrossClass;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterGrossClassFacadeRemote {

    void create(MasterGrossClass masterGrossClass);

    void edit(MasterGrossClass masterGrossClass);

    void remove(MasterGrossClass masterGrossClass);

    MasterGrossClass find(Object id);

    List<MasterGrossClass> findAll();

    List<MasterGrossClass> findRange(int[] range);

    int count();

    List<Object[]> findGrossClasses();

}
