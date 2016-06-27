/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean.tag;

import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author x42jr
 */
@ManagedBean(name="masterCommoditySelector")
@ViewScoped
public class MasterCommoditySelector {
    @EJB
    private MasterCommodityFacadeRemote masterCommodityFacadeRemote;

    private LazyDataModel<MasterCommodity> masterCommodities;

    public MasterCommoditySelector() {}

    @PostConstruct
    private void construct() {
        populateAvailableMasterCommodities();
    }

    private void populateAvailableMasterCommodities() {
        masterCommodities = new LazyDataModel<MasterCommodity>(){
            @Override
            public List<MasterCommodity> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String,String> filters) {
                int count = masterCommodityFacadeRemote.findAll_Count(filters);
                masterCommodities.setRowCount(count);
                List<MasterCommodity> findAll = masterCommodityFacadeRemote.findAll(first, pageSize, sortField, sortOrder, filters);

                return findAll;
            }

            @Override
            public void setRowIndex(final int rowIndex){
                if (rowIndex == -1 || getPageSize() == 0) {
                    super.setRowIndex(-1);
                } else {
                    super.setRowIndex(rowIndex % getPageSize());
                }
            }
        };
    }

    public LazyDataModel<MasterCommodity> getMasterCommodities() {
        return masterCommodities;
    }
}
