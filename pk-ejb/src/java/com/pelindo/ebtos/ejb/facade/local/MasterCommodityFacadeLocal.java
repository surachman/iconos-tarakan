/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterCommodity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterCommodityFacadeLocal {

    void create(MasterCommodity masterCommodity);

    void edit(MasterCommodity masterCommodity);

    void remove(MasterCommodity masterCommodity);

    MasterCommodity find(Object id);

    List<MasterCommodity> findAll();

    List<MasterCommodity> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();

    List<Object[]> findMasterCommoditys();

    String findMasterCommodityByGenerate();

    List<Object[]> findMasterCommodityByCode(String commodity_type_code);

    List<Object[]> findMasterCommodityForIdentify(String comm);

    MasterCommodity getEmptyCommodity();
}
