/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.ReceivingService;
import com.pelindo.ebtos.model.db.master.MasterPort;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface ReceivingServiceFacadeLocal {

    void create(ReceivingService receivingService);

    void edit(ReceivingService receivingService);

    void remove(ReceivingService receivingService);

    ReceivingService find(Object id);

    List<ReceivingService> findAll();

    List<ReceivingService> findRange(int[] range);

    int count();

    List<Object[]> findReceivingServiceByNoReg(String no_reg);

    String findReceivingServiceByGenerate(String bulan);

    List<Object[]> findReceivingServiceByClosingTime(Date close_rec);

    List<Object[]> findPerhitungan(String no_reg);

    String findInvoice(String cont_no, String no_ppkb, String no_reg);

    List<Object[]> findReceivingRecaps(String no_ppkb);

    List<Object[]> findCountableLoadReefers(String no_ppkb);

    List<Object[]> findHandlingLoadMonitoring(String no_ppkb);

    Object[] findReceivingServiceContNo(String no_ppkb, String cont_no);

    Object[] findReceivingServiceByClosingTimeByJobSlip(String cont_no);

    Long findJobSlipAmount(String no_ppkb);

    List<String> findGateInPassableJobSlips(String jobslip);

    List<String> findReceivingServiceByAutoCompleteCancelLoading(String no_ppkb, String cont_no);

    String findReceivingServiceByUpdateDate(String no_ppkb, String cont_no);

    List<Object[]> findCountableLoadReefersByNoBl(String bl_no, String no_ppkb);

    List<Object[]> findByListBatalNota(String no_reg);

    List<Object[]> findByListBatalNotaService(String no_ppkb, String no_reg);

    public int updatePlanningVessel(com.pelindo.ebtos.model.db.PlanningVessel newValue, com.pelindo.ebtos.model.db.PlanningVessel oldValue);

    void removeThatRelatedToReceiving(ReceivingService receivingService);

    public void removeThatRelatedToReceiving(java.lang.String noReg);

    public int deleteByNoPpkbAndContNoNotCancelLoading(java.lang.String noPpkb, java.lang.String contNo);
    
    public java.lang.Object[] findReceivingCapacityStatusByNoPpkb(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findNotEnteringGateYetByContNo(java.lang.String contNo);

    ReceivingService findByNoPpkbAndContNoNotCancelLoading(String noPpkb, String contNo);

    ReceivingService findByNoPpkbAndContNoCancelLoading(String noPpkb, String contNo);

    int updatePlanningVesselByJobSlips(PlanningVessel newValue, MasterPort nextPort, PlanningVessel oldValue, List<String> jobSlips);
}
