/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterYardConstraintType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author USER
 */
@Remote
public interface MasterYardConstraintTypeFacadeRemote {

    void create(MasterYardConstraintType masterYardConstraintType);

    void edit(MasterYardConstraintType masterYardConstraintType);

    void remove(MasterYardConstraintType masterYardConstraintType);

    MasterYardConstraintType find(Object id);

    List<MasterYardConstraintType> findAll();

    List<MasterYardConstraintType> findRange(int[] range);

    int count();

}
