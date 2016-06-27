/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterDock;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterDockFacadeRemote {

    void create(MasterDock masterDock);

    void edit(MasterDock masterDock);

    void remove(MasterDock masterDock);

    MasterDock find(Object id);

    List<MasterDock> findAll();

    List<MasterDock> findRange(int[] range);

    int count();

    List<String> findDocks();

}
