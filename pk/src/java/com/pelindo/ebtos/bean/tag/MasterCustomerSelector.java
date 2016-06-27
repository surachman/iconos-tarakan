/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean.tag;

import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author x42jr
 */
@ManagedBean(name="masterCustomerSelector")
@RequestScoped
public class MasterCustomerSelector {
    @EJB
    private MasterCustomerFacadeRemote masterCustomerFacadeRemote;

    public MasterCustomerSelector() {}

    public List<Object[]> handleFilterByName(String customerName) {
        List<Object[]> results = masterCustomerFacadeRemote.findLikeName(customerName);
        
        return results;
    }
}
