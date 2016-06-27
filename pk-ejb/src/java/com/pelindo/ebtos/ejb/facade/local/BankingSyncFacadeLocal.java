/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import javax.ejb.Local;

/**
 *
 * @author R Seno Anggoro
 */
@Local
public interface BankingSyncFacadeLocal {

    public java.lang.String createInvoice(java.lang.String noInvoice, java.lang.String custCode, java.lang.String custName, java.lang.String vesselCode, java.lang.String vesselName, java.lang.String activityCode, java.lang.String activityName, java.lang.String currCode, java.math.BigDecimal charge, javax.xml.datatype.XMLGregorianCalendar invoiceDate);

    public java.lang.Boolean doPayment(java.lang.String noInvoice, javax.xml.datatype.XMLGregorianCalendar paymentDate);

    public java.lang.Boolean cancelInvoice(java.lang.String noInvoice);
}
