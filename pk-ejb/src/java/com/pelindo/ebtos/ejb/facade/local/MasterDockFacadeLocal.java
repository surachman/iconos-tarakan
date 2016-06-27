/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterDock;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterDockFacadeLocal {

    void create(MasterDock masterDock);

    void edit(MasterDock masterDock);

    void remove(MasterDock masterDock);

    MasterDock find(Object id);

    List<MasterDock> findAll();

    List<MasterDock> findRange(int[] range);

    int count();

}
