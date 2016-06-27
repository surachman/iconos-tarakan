/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.remote;

import com.pelindo.ebtos.exception.GrossCapacityExceedTheLimitsException;
import com.pelindo.ebtos.exception.HasJobSlipAndNotEnteringGateYetException;
import com.pelindo.ebtos.exception.NotAllocatedReceivingException;
import com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException;
import com.pelindo.ebtos.exception.RegisteredWithSamePpkbContainerException;
import com.pelindo.ebtos.exception.TeusCapacityExceedTheLimitsException;
import com.pelindo.ebtos.model.db.PlanningVessel;
import javax.ejb.Remote;

/**
 *
 * @author R. Seno Anggoro A
 */
@Remote
public interface StuffingCalculationOperationRemote {

    public void cancelInvoice(com.pelindo.ebtos.model.db.Registration registration, com.pelindo.ebtos.model.db.BatalNota batalNota);

    public java.lang.String preparePerhitunganReport(com.pelindo.ebtos.model.db.Registration registration, com.pelindo.ebtos.model.db.master.MasterCurrency masterCurrency, java.math.BigDecimal ppn, java.math.BigDecimal materai, java.util.List<com.pelindo.ebtos.model.db.StuffingService> stuffingServices);

    public java.lang.String preparePerhitunganInvoiceReport(com.pelindo.ebtos.model.db.Registration registration, java.util.List<com.pelindo.ebtos.model.db.StuffingService> stuffingServices);

    public java.lang.String prepareInvoiceReport(com.pelindo.ebtos.model.db.Registration registration, java.util.List<com.pelindo.ebtos.model.db.StuffingService> stuffingServices);

    public void removeStuffingContainer(com.pelindo.ebtos.model.db.StuffingService stuffingService);

    public com.pelindo.ebtos.model.db.StuffingService saveStuffingContainers(com.pelindo.ebtos.model.db.StuffingService stuffingService, String noPpkb) throws GrossCapacityExceedTheLimitsException, TeusCapacityExceedTheLimitsException, NotAllocatedReceivingException, ReceivingAllocationIsNotEnoughException, RegisteredWithSamePpkbContainerException, HasJobSlipAndNotEnteringGateYetException;
    
}
