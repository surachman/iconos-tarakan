/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterIdletime;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface MasterIdletimeFacadeLocal {

    void create(MasterIdletime masterIdletime);

    void edit(MasterIdletime masterIdletime);

    void remove(MasterIdletime masterIdletime);

    MasterIdletime find(Object id);

    List<MasterIdletime> findAll();

    List<MasterIdletime> findRange(int[] range);

    int count();

}
