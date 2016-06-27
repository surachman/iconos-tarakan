/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterTarifHistory;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterTarifHistoryFacadeRemote {

    void create(MasterTarifHistory masterTarifHistory);

    void edit(MasterTarifHistory masterTarifHistory);

    void remove(MasterTarifHistory masterTarifHistory);

    MasterTarifHistory find(Object id);

    List<MasterTarifHistory> findAll();

    List<MasterTarifHistory> findRange(int[] range);

    int count();

}
