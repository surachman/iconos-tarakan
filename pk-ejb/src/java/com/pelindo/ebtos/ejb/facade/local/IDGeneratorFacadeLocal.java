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
public interface IDGeneratorFacadeLocal {
    public String generateID(String patternString, int value);

    public String generatePpkbID();

    public String generatePpkbKeuanganID();

    public String generateRegistrationID();

    public String generateInvoiceID();

    public String generateBentukTigaID();
    
    public String generateInvoiceNotaManualID();

    public String generateFakturPajakID();

    public String generateJobSlipID(String prefix);
    
    public java.lang.String generateJkmID();

    public java.lang.String generatePpkbPlugOnlyID();
}
