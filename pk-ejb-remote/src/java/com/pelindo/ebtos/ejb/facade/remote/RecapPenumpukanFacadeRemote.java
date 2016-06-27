/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapPenumpukan;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface RecapPenumpukanFacadeRemote {

    void create(RecapPenumpukan recapPenumpukan);

    void edit(RecapPenumpukan recapPenumpukan);

    void remove(RecapPenumpukan recapPenumpukan);

    RecapPenumpukan find(Object id);

    List<RecapPenumpukan> findAll();

    List<RecapPenumpukan> findRange(int[] range);

    int count();

    List<Integer> findByPpkbNCont(String cont_no, String no_ppkb);

}
