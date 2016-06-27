/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean.tag;

import com.pelindo.ebtos.ejb.facade.remote.MasterCommodityFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterUserGroupFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterUserGroup;
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
@ManagedBean(name="masterGroupSelector")
@ViewScoped
public class MasterGroupSelector {
    @EJB
    private  MasterUserGroupFacadeRemote masterUserGroupFacadeRemote;

    private List<MasterUserGroup> masterGroups;

    public MasterGroupSelector() {}

    @PostConstruct
    private void construct() {
        populateAvailableEntities();
    }

    private void populateAvailableEntities() {
        masterGroups = masterUserGroupFacadeRemote.findAll();
    }

    public List<MasterUserGroup> getMasterGroups() {
        return masterGroups;
    }
}
