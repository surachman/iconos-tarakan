/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean.tag;

import com.pelindo.ebtos.ejb.facade.local.InvoiceFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterPortFacadeRemote;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author eka
 */
@ManagedBean(name="registrationtSelector")
@RequestScoped
public class RegistrationtSelector {
    @EJB
    private InvoiceFacadeLocal invoiceFacadeLocal;

    public RegistrationtSelector() {}

    public List<String> handleFilterByReg(String noReg) {
        List<String> results = invoiceFacadeLocal.findLikeReg(noReg);
        
        return results;
    }
}
