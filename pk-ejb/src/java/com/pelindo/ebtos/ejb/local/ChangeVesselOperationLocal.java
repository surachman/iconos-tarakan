/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.local;

import com.pelindo.ebtos.model.db.BatalNota;
import com.pelindo.ebtos.model.db.PlanningVessel;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface ChangeVesselOperationLocal {

    public void submitChangeVessel(com.pelindo.ebtos.model.db.Registration registration, java.util.List<com.pelindo.ebtos.model.db.ChangeVesselService> containers);

    public void cancelInvoice(com.pelindo.ebtos.model.db.Registration registration, BatalNota batalNota, PlanningVessel oldPlanningVessel);

    public String preparePerhitunganReport(com.pelindo.ebtos.model.db.Registration registration, com.pelindo.ebtos.model.db.master.MasterCurrency masterCurrency, java.math.BigDecimal ppn, java.math.BigDecimal materai, java.util.List<com.pelindo.ebtos.model.db.ChangeVesselService> changeVesselServices);

    public java.lang.String preparePerhitunganInvoiceReport(com.pelindo.ebtos.model.db.Registration registration, java.util.List<com.pelindo.ebtos.model.db.ChangeVesselService> changeVesselServices);

    public java.lang.String prepareInvoiceReport(com.pelindo.ebtos.model.db.Registration registration, java.util.List<com.pelindo.ebtos.model.db.ChangeVesselService> changeVesselServices);

    public void handleUpdateVessel(com.pelindo.ebtos.model.db.PlanningVessel newValue, com.pelindo.ebtos.model.db.PlanningVessel oldValue);
    
}
