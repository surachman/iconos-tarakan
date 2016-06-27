/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganMonitoring;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganMonitoringFacadeRemote {

    void create(PerhitunganMonitoring perhitunganMonitoring);

    void edit(PerhitunganMonitoring perhitunganMonitoring);

    void remove(PerhitunganMonitoring perhitunganMonitoring);

    PerhitunganMonitoring find(Object id);

    List<PerhitunganMonitoring> findAll();

    List<PerhitunganMonitoring> findRange(int[] range);

    int count();

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    Integer findInvoicePlugging(String cont_no, String no_reg);

    List<Object> findByReg(String no_reg);

    public PerhitunganMonitoring findByContNoAndNoReg(String contNo, String noReg);
}
