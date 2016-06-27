/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean.tag;

import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDangerousClassFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterDangerousClass;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author x42jr
 */
@ManagedBean(name="masterDangerousClassSelector")
@RequestScoped
public class MasterDangerousClassSelector {
    @EJB
    private MasterDangerousClassFacadeRemote masterDangerousClassFacadeRemote;

    private List<MasterDangerousClass> masterDangerousClasses;

    public MasterDangerousClassSelector() {}

    @PostConstruct
    private void construct() {
        masterDangerousClasses = masterDangerousClassFacadeRemote.findAll();
    }

    public List<MasterDangerousClass> getMasterDangerousClasses() {
        return masterDangerousClasses;
    }
}
