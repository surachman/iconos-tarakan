/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean.tag;

import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author eka
 */
@ManagedBean(name="masterPortSelector")
@RequestScoped
public class MasterPortSelector {
    @EJB
    private MasterPortFacadeRemote masterPortFacadeRemote;

    public MasterPortSelector() {}

    public List<Object[]> handleFilterByName(String portName) {
        List<Object[]> results = masterPortFacadeRemote.findLikeName(portName);
        
        return results;
    }
}
