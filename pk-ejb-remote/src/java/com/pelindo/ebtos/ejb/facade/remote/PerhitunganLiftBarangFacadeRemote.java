/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganLiftBarang;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganLiftBarangFacadeRemote {

    void create(PerhitunganLiftBarang perhitunganLiftBarang);

    void edit(PerhitunganLiftBarang perhitunganLiftBarang);

    void remove(PerhitunganLiftBarang perhitunganLiftBarang);

    PerhitunganLiftBarang find(Object id);

    List<PerhitunganLiftBarang> findAll();

    List<PerhitunganLiftBarang> findRange(int[] range);

    int count();

    Object[] findPerhitunganLiftBarangByEdit(String jobSlip, String cont_no);

    List<Object[]> findPerhitunganLiftBarangByList(String no_reg);

    Integer findCancelInvoice(String job_slip, String no_ppkb, String no_reg);

    List<Object> findByReg(String no_reg);
}
