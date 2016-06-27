/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterYardConstraintSize;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface MasterYardConstraintSizeFacadeLocal {

    void create(MasterYardConstraintSize masterYardConstraintSize);

    void edit(MasterYardConstraintSize masterYardConstraintSize);

    void remove(MasterYardConstraintSize masterYardConstraintSize);

    MasterYardConstraintSize find(Object id);

    List<MasterYardConstraintSize> findAll();

    List<MasterYardConstraintSize> findRange(int[] range);

    int count();

}
