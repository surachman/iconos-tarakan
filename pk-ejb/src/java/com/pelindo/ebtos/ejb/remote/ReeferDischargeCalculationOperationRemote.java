/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.remote;

import javax.ejb.Remote;

/**
 *
 * @author R. Seno Anggoro A
 */
@Remote
public interface ReeferDischargeCalculationOperationRemote {

    void handleDeleteDischargeReefer(String contNo, String noReg);

    public void handleAddReeferDischarge(com.pelindo.ebtos.model.db.ReeferDischargeService reeferDischargeService, com.pelindo.ebtos.model.db.master.MasterCurrency masterCurrency);

    public void cancelInvoice(com.pelindo.ebtos.model.db.Registration registration, com.pelindo.ebtos.model.db.BatalNota batalNota);

}
