/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.bean.tag;

import com.pelindo.ebtos.ejb.facade.remote.MasterVehicleFacadeRemote;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author eka
 */
@ManagedBean(name="nopolSelector")
@RequestScoped

public class NopolSelector {
    
    @EJB MasterVehicleFacadeRemote masterVehicleFacadeLocal;
    
    public NopolSelector() {
    }
    
    public List<Object[]> handleFilterByNopol(String nopol) {
        List<Object[]> results = masterVehicleFacadeLocal.findLikeNumber(nopol);
        
        return results;
    }
}
