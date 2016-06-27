/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.AngsurService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R Seno Anggoro
 */
@Remote
public interface IDGeneratorFacadeRemote {
    public String generateID(String patternString, int value);

    public String generatePpkbID();

    public String generatePpkbKeuanganID();

    public String generateRegistrationID();

    public String generateInvoiceID();

    public String generateInvoiceNotaManualID();

    public String generateFakturPajakID();

    public String generateJobSlipID(String prefix);
    
    public java.lang.String generateJkmID();

    public java.lang.String generatePpkbPlugOnlyID();
}
