/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganPenumpukanBarang;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganPenumpukanBarangFacadeRemote {

    void create(PerhitunganPenumpukanBarang perhitunganPenumpukanBarang);

    void edit(PerhitunganPenumpukanBarang perhitunganPenumpukanBarang);

    void remove(PerhitunganPenumpukanBarang perhitunganPenumpukanBarang);

    PerhitunganPenumpukanBarang find(Object id);

    List<PerhitunganPenumpukanBarang> findAll();

    List<PerhitunganPenumpukanBarang> findRange(int[] range);

    int count();

    Object[] findPerhitunganPenumpukanBarangByEdit(String job_slip,String cont_no);

    List<Object[]> findPerhitunganPenumpukanBarangByList();

    List<Object> findByReg(String no_reg);
}
