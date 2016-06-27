/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterYardType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author wulan
 */
@Remote
public interface MasterYardTypeFacadeRemote {

    void create(MasterYardType masterYardType);

    void edit(MasterYardType masterYardType);

    void remove(MasterYardType masterYardType);

    MasterYardType find(Object id);

    List<MasterYardType> findAll();

    List<MasterYardType> findRange(int[] range);

    int count();

}
