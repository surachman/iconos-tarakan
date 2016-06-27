/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PerhitunganPenumpukanTranshipment;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface PerhitunganPenumpukanTranshipmentFacadeRemote {

    void create(PerhitunganPenumpukanTranshipment perhitunganPenumpukanTranshipment);

    void edit(PerhitunganPenumpukanTranshipment perhitunganPenumpukanTranshipment);

    void remove(PerhitunganPenumpukanTranshipment perhitunganPenumpukanTranshipment);

    PerhitunganPenumpukanTranshipment find(Object id);

    List<PerhitunganPenumpukanTranshipment> findAll();

    List<PerhitunganPenumpukanTranshipment> findRange(int[] range);

    int count();

    List<Integer> findByPpkbNCont(String cont_no, String no_ppkb);

}
