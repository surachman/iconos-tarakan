/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ReceivingBarangService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ReceivingBarangServiceFacadeRemote {

    void create(ReceivingBarangService receivingBarangService);

    void edit(ReceivingBarangService receivingBarangService);

    void remove(ReceivingBarangService receivingBarangService);

    ReceivingBarangService find(Object id);

    List<ReceivingBarangService> findAll();

    List<ReceivingBarangService> findRange(int[] range);

    int count();

    List<Object[]> findReceivingBarangServices(String no_reg);

    String findReceivingBarangServiceByGenerate(String bulan);

    Object[] findReceivingBarangServiceByStuffing(String job_slip);

    List<String> findReceivingBarangServiceByAutoComplete(String jobslip);

    List<Object[]> findReceivingBarangServiceByJobSlip(String no_reg);

    List<Object[]> findReceivingBarangServiceByList(String Bl);

    List<Object[]> findReceivingBarangServiceByListAll();

    List<Object[]> findByListBatalNotaService(String no_ppkb, String no_reg);

    List<Object[]> findByNoregValidasiPrint(String no_reg);

}
