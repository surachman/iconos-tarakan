/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.remote;

import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.BehandleService;
import com.pelindo.ebtos.model.db.PerhitunganBehandle;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R. Seno Anggoro A
 */
@Remote
public interface BehandleCalculationOperationRemote {

    PerhitunganBehandle saveBehandleContainers(PerhitunganBehandle perhitunganBehandle);

    String preparePerhitunganReport(Registration registration, MasterCurrency masterCurrency, BigDecimal ppn, BigDecimal materai, List<PerhitunganBehandle> perhitunganBehandles);

    void cancelInvoice(Registration registration, BatalNota batalNota);

    String prepareInvoiceReport(Registration registration, List<PerhitunganBehandle> perhitunganBehandles);

    String preparePerhitunganInvoiceReport(Registration registration, List<PerhitunganBehandle> perhitunganBehandles);

}
