/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.exception.GrossCapacityExceedTheLimitsException;
import com.pelindo.ebtos.exception.HasJobSlipAndNotEnteringGateYetException;
import com.pelindo.ebtos.exception.NotAllocatedReceivingException;
import com.pelindo.ebtos.exception.ReceivingAllocationIsNotEnoughException;
import com.pelindo.ebtos.exception.RegisteredWithSamePpkbContainerException;
import com.pelindo.ebtos.exception.TeusCapacityExceedTheLimitsException;
import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.DischargeToLoadService;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterCurrency;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R. Seno Anggoro A
 */
@Remote
public interface DischargeToLoadOperationRemote {

    void cancelInvoice(Registration registration, BatalNota batalNota);

    String prepareInvoiceReport(Registration registration, List<DischargeToLoadService> dischargeToLoadServices);

    String preparePerhitunganInvoiceReport(Registration registration, List<DischargeToLoadService> dischargeToLoadServices);

    String preparePerhitunganReport(Registration registration, MasterCurrency masterCurrency, BigDecimal ppn, BigDecimal materai, List<DischargeToLoadService> dischargeToLoadServices);

    DischargeToLoadService saveDischargeToLoadContainers(DischargeToLoadService dischargeToLoadService, PlanningVessel planningVessel, MasterPort dischargePort, MasterPort portOfDelivery) throws GrossCapacityExceedTheLimitsException, TeusCapacityExceedTheLimitsException, NotAllocatedReceivingException, ReceivingAllocationIsNotEnoughException, RegisteredWithSamePpkbContainerException, HasJobSlipAndNotEnteringGateYetException;

    public void removeDischargeToLoadContainer(com.pelindo.ebtos.model.db.DischargeToLoadService dischargeToLoadService);
}
