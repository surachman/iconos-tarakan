/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.ws;

import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterBankFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterBank;
import java.util.Date;
import java.util.UUID;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author R Seno Anggoro
 */
@WebService()
@Stateless()
public class InvoiceSync {
    @EJB private InvoiceFacadeRemote invoiceFacadeRemote;
    @EJB private MasterBankFacadeRemote masterBankFacadeRemote;
    /**
     * Web service operation
     */
    @WebMethod(operationName = "doInvoicePayment")
    public Boolean doInvoicePayment(@WebParam(name = "noInvoice")
    final String noInvoice, @WebParam(name = "paymentDate")
    final Date paymentDate, @WebParam(name = "guid")
    final String guid) {
        return invoiceFacadeRemote.doInvoicePayment(noInvoice, paymentDate, guid);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "resetMasterBankGuid")
    public String resetMasterBankGuid(@WebParam(name = "guid")
    final String guid) {
        MasterBank masterBank = masterBankFacadeRemote.findByGuid(guid);
        if (masterBank != null) {
            masterBank.setGuid(UUID.randomUUID().toString());
            masterBankFacadeRemote.edit(masterBank);
            return masterBank.getGuid();
        }
        return null;
    }
}
