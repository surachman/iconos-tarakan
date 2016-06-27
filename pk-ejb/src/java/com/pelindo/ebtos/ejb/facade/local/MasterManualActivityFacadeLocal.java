/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterManualActivity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dyware-Dev01
 */
@Local
public interface MasterManualActivityFacadeLocal {

    void create(MasterManualActivity mManualActivity);

    void edit(MasterManualActivity mManualActivity);

    void remove(MasterManualActivity mManualActivity);

    MasterManualActivity find(Object id);

    List<MasterManualActivity> findAll();

    List<MasterManualActivity> findRange(int[] range);

    int count();

}
