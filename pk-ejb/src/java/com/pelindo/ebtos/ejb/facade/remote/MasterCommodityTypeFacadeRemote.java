/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterCommodityType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterCommodityTypeFacadeRemote {

    void create(MasterCommodityType masterCommodityType);

    void edit(MasterCommodityType masterCommodityType);

    void remove(MasterCommodityType masterCommodityType);

    MasterCommodityType find(Object id);

    List<MasterCommodityType> findAll();

    List<MasterCommodityType> findRange(int[] range);

    int count();
  
    List<Object[]> findMasterCommodityTypes();

    String findMasterCommodityByGenerate();

    Object[] findMasterCommodityTypeCode(String commodity_code);
}
