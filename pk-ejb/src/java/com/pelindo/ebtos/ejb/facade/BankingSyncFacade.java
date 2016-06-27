/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.BankingSyncFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.BankingSyncFacadeLocal;
import com.pelindo.ebtos.ejb.ws.client.PaymentBankingSyncService;
import javax.ejb.Stateless;

/**
 *
 * @author R Seno Anggoro
 */
@Stateless
public class BankingSyncFacade implements BankingSyncFacadeRemote, BankingSyncFacadeLocal {
    private PaymentBankingSyncService service;

    public String createInvoice(
            java.lang.String noInvoice,
            java.lang.String custCode,
            java.lang.String custName,
            java.lang.String vesselCode,
            java.lang.String vesselName,
            java.lang.String serviceCode,
            java.lang.String serviceName,
            java.lang.String currCode,
            java.math.BigDecimal charge,
            javax.xml.datatype.XMLGregorianCalendar invoiceDate) {
        service = new PaymentBankingSyncService();
        com.pelindo.ebtos.ejb.ws.client.PaymentBankingSync port = service.getPaymentBankingSyncPort();
        return port.createInvoice(noInvoice, custCode, custName, vesselCode, vesselName, serviceCode, serviceName, currCode, charge, invoiceDate);
    }

    public Boolean doPayment(java.lang.String noInvoice, javax.xml.datatype.XMLGregorianCalendar paymentDate) {
        service = new PaymentBankingSyncService();
        com.pelindo.ebtos.ejb.ws.client.PaymentBankingSync port = service.getPaymentBankingSyncPort();
        return port.doPayment(noInvoice, paymentDate);
    }

    public Boolean cancelInvoice(java.lang.String noInvoice) {
        service = new PaymentBankingSyncService();
        com.pelindo.ebtos.ejb.ws.client.PaymentBankingSync port = service.getPaymentBankingSyncPort();
        return port.cancelInvoice(noInvoice);
    }
}
