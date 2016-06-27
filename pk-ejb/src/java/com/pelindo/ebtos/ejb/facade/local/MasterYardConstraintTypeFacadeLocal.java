/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterYardConstraintType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface MasterYardConstraintTypeFacadeLocal {

    void create(MasterYardConstraintType masterYardConstraintType);

    void edit(MasterYardConstraintType masterYardConstraintType);

    void remove(MasterYardConstraintType masterYardConstraintType);

    MasterYardConstraintType find(Object id);

    List<MasterYardConstraintType> findAll();

    List<MasterYardConstraintType> findRange(int[] range);

    int count();

}
