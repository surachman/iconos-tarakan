/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterCommodityType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterCommodityTypeFacadeLocal {

    void create(MasterCommodityType masterCommodityType);

    void edit(MasterCommodityType masterCommodityType);

    void remove(MasterCommodityType masterCommodityType);

    MasterCommodityType find(Object id);

    List<MasterCommodityType> findAll();

    List<MasterCommodityType> findRange(int[] range);

    int count();

}
