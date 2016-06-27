/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterManualActivity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Dyware-Dev01
 */
@Remote
public interface MasterManualActivityFacadeRemote {

    void create(MasterManualActivity mManualActivity);

    void edit(MasterManualActivity mManualActivity);

    void remove(MasterManualActivity mManualActivity);

    MasterManualActivity find(Object id);

    List<MasterManualActivity> findAll();

    List<MasterManualActivity> findRange(int[] range);

    int count();

    List<Object[]> findByType(int id);

}
