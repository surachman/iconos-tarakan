/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.CancelLoadingService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface CancelLoadingServiceFacadeRemote {

    void create(CancelLoadingService cancelLoadingService);

    void edit(CancelLoadingService cancelLoadingService);

    void remove(CancelLoadingService cancelLoadingService);

    CancelLoadingService find(Object id);

    CancelLoadingService findByNewPpkbAndRelocationStatus(String noPpkb, String contNo, Boolean relocatedStatus);

    List<CancelLoadingService> findAll();

    List<CancelLoadingService> findRange(int[] range);

    int count();

    List<Object[]> findCancelLoadingServiceByNoreg(String no_reg);

    //String findCancelLoadingServiceId(String no_ppkb, String no_reg, String cont_no);
    String generateId(String bulan);

    List<String> findGateInPassableJobslips(String jobslip);

    List<Object[]> findByChangeDestination(String no_ppkb);

    Object[] findCancelLoadingServiceByDestination(String no_ppkb, String cont_no);

    List<Object[]> findHasDischargedContainerByNoPpkb(String no_ppkb);

    List<Object[]> findDischargableContainerByNoPpkb(String no_ppkb);

    Object[] findGateInPassableByJobSlip(String job_slip);

    List<Object[]> findByPPKB(String no_ppkb);

    Object findByPPKBandCont(String no_ppkb, String no_cont, Boolean destination);

    Object[] findByPpkbAndContMobile(String no_ppkb, String no_cont);

    Object[] findByPpkbAndContMobileDelivery(String no_ppkb, String no_cont);

    List<Object[]> findByListBatalNotaService(String no_ppkb, String no_reg);

    List<Object[]> findByNoregValidasiPrint(String no_reg);

    List<Object[]> findCashierCancelLoading(String noPpkb, String contNo);

    public java.util.List<com.pelindo.ebtos.model.db.CancelLoadingService> findByNoReg(java.lang.String noReg);

    public java.util.List<java.lang.String> findCancelableContainerByNoPpkb(java.lang.String noPpkb, java.lang.String contNo);

    public java.util.List<java.lang.Object[]> findPerhitungan(java.lang.String noReg);

    public java.util.List<java.lang.Object[]> findHasLiftedOffByNoPpkb(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findLiftableOffByNoPpkb(java.lang.String noPpkb);

    public java.lang.Object[] findDeliverableContainerByContNo(java.lang.String contNo);

    public com.pelindo.ebtos.model.db.CancelLoadingService findByNewPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo);

    public java.util.List<java.lang.Object[]> findDeliverableContainersByPpkb(java.lang.String noPpkb);
    //penambahan nama kapal , vo in voy out by ade chelsea tanggal 17 maret 2014
    public java.util.List<java.lang.Object[]> findDeliverableContainersByPpkb2();

    public java.util.List<java.lang.Object[]> findDeliveredContainersByPpkb(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.CancelLoadingService findByNoPpkbContNoAndStatusDelivery(java.lang.String noPpkb, java.lang.String contNo, java.lang.Boolean isDelivery);

    public com.pelindo.ebtos.model.db.CancelLoadingService findDischargableContainerByContNoAndPpkb(java.lang.String noPpkb, java.lang.String contNo);

    public com.pelindo.ebtos.model.db.CancelLoadingService findLiftableOffByContNo(java.lang.String contNo);

    List<Object[]> findStuffAbleContainers(String noPpkb);

    public CancelLoadingService findByNoPpkbAndContNo(String noPpkb, String contNo);

    public CancelLoadingService findMovableOffContainer(String contNo);

    public List<Object[]> findByNoPpkbAndContNoUpdateDelivery(java.lang.String noPpkb, java.lang.String contNo);

    public java.util.List<java.lang.String> findLikeContNo(java.lang.String noPpkb, java.lang.String contNo);

    public java.util.List<java.lang.String> findLikeNoPPKB(java.lang.String noPpkb);
}
