/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.BaplieDischarge;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface BaplieDischargeFacadeRemote {

    void create(BaplieDischarge baplieDischarge);

    void edit(BaplieDischarge baplieDischarge);

    void remove(BaplieDischarge baplieDischarge);

    BaplieDischarge find(Object id);

    List<BaplieDischarge> findAll();

    List<BaplieDischarge> findRange(int[] range);

    int count();

    List<Object[]> findContainerByTypeContainer (int cont_type);

    List<Object[]> findBaplieDischargesByPpkb (String no_ppkb);
    
    List<Object[]> findBaplieDischargesByCode (String commodity_code);

    List<Object[]> findBaplieDischargesByPpkbForCopy (String no_ppkb);

    List<Object[]> findBaplieDischargesByPPKB (String no_ppkb);

    List<Object[]> findBaplieDischargesByPpkbShifting (String no_ppkb);

    List<Object[]> findTheSameContainer (String cont_no);

    public java.util.List<com.pelindo.ebtos.model.db.BaplieDischarge> findByNoPpkb(java.lang.String noPpkb);
    
    //    penambahan keterangan container status by ade chelsea tanggal 29 april 2014 13:40 jayapura
    public java.util.List<com.pelindo.ebtos.model.db.BaplieDischarge> findContainerStatus(java.lang.String noPpkb);

    public void finishBaplieDischarges(java.lang.String noPpkb);

    public java.util.List<com.pelindo.ebtos.model.db.BaplieDischarge> findByPpkbNotCompleted(java.lang.String noPpkb);

    public java.util.List<java.lang.Object[]> findTranshipmentContainerByAllocationConstraint(String noPpkb, String portCode, Integer contSize, Integer contType, String contStatus, String grossClass, Integer overSize, Integer dg, java.lang.Integer isExport, String contNoAsString);

    public java.util.List<java.lang.Object[]> findShiftingContainerByAllocationConstraint(java.lang.String noPpkb, java.lang.String portCode, java.lang.Integer contSize, java.lang.Integer contType, java.lang.String contStatus, java.lang.String grossClass, java.lang.Integer overSize, java.lang.Integer dg, java.lang.Integer isExport, java.lang.String contNoAsString);

    List<Object[]> findBaplieDischargeByPpkbPodDpc(String no_ppkb, String disch_port_code, String pod);
    
    List<Object[]> findBaplieDischargeByPpkbNotPod(String no_ppkb, String pod, String pod2);
    
    List<Object[]> findBaplieDischargeByPpkbShiftingViaCY(String no_ppkb, String pod, String pod2);
    
    public String mPort();
}
