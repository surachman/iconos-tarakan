/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterYardType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wulan
 */
@Local
public interface MasterYardTypeFacadeLocal {

    void create(MasterYardType masterYardType);

    void edit(MasterYardType masterYardType);

    void remove(MasterYardType masterYardType);

    MasterYardType find(Object id);

    List<MasterYardType> findAll();

    List<MasterYardType> findRange(int[] range);

    int count();

}
