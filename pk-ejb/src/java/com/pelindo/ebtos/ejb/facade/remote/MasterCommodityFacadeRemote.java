/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterCommodity;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterCommodityFacadeRemote {

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

    public com.pelindo.ebtos.model.db.master.MasterCommodity getEmptyCommodity();

    public java.util.List<com.pelindo.ebtos.model.db.master.MasterCommodity> findAll(int first, int pageSize, java.lang.String sortField, boolean sortOrder, Map<String,String> likes);

    public int findAll_Count(Map<String,String> likes);
}
