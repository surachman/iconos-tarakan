/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.PerhitunganMonitoring;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface PerhitunganMonitoringFacadeLocal {

    void create(PerhitunganMonitoring perhitunganMonitoring);

    void edit(PerhitunganMonitoring perhitunganMonitoring);

    void remove(PerhitunganMonitoring perhitunganMonitoring);

    PerhitunganMonitoring find(Object id);

    List<PerhitunganMonitoring> findAll();

    List<PerhitunganMonitoring> findRange(int[] range);

    int count();

    Integer findInvoice(String cont_no, String no_ppkb, String no_reg);

    Integer findInvoicePlugging(String cont_no, String no_reg);

    public int deleteByNoPpkbLoadOnly(java.lang.String noPpkb);

    public PerhitunganMonitoring findByContNoAndNoReg(String contNo, String noReg);

}
