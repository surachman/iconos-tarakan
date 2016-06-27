/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.remote;

import com.pelindo.ebtos.model.db.PerhitunganStripping;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.StrippingService;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R. Seno Anggoro A
 */
@Remote
public interface StrippingCalculationOperationRemote {

    public void cancelInvoice(com.pelindo.ebtos.model.db.Registration registration, com.pelindo.ebtos.model.db.BatalNota batalNota);

    String prepareInvoiceReport(Registration registration, List<StrippingService> PerhitunganStrippings);

    String preparePerhitunganInvoiceReport(Registration registration, List<StrippingService> PerhitunganStrippings);

    String preparePerhitunganReport(Registration registration, MasterCurrency masterCurrency, BigDecimal ppn, BigDecimal materai, List<StrippingService> PerhitunganStrippings);

    public com.pelindo.ebtos.model.db.StrippingService saveStrippingContainers(com.pelindo.ebtos.model.db.StrippingService strippingService, com.pelindo.ebtos.model.db.PlanningVessel planningVessel, com.pelindo.ebtos.model.db.master.MasterPort dischargePort, com.pelindo.ebtos.model.db.master.MasterPort portOfDelivery) throws com.pelindo.ebtos.exception.GrossCapacityExceedTheLimitsException, com.pelindo.ebtos.exception.TeusCapacityExceedTheLimitsException, com.pelindo.ebtos.exception.NotAllocatedReceivingException, com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException, com.pelindo.ebtos.exception.RegisteredWithSamePpkbContainerException, com.pelindo.ebtos.exception.HasJobSlipAndNotEnteringGateYetException;

    public void removeStrippingContainer(com.pelindo.ebtos.model.db.StrippingService strippingService);
    
}
