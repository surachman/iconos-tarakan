/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterYardConstraintSize;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author USER
 */
@Remote
public interface MasterYardConstraintSizeFacadeRemote {

    void create(MasterYardConstraintSize masterYardConstraintSize);

    void edit(MasterYardConstraintSize masterYardConstraintSize);

    void remove(MasterYardConstraintSize masterYardConstraintSize);

    MasterYardConstraintSize find(Object id);

    List<MasterYardConstraintSize> findAll();

    List<MasterYardConstraintSize> findRange(int[] range);

    int count();

}
