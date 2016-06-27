/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapPenumpukanUc;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R Seno Anggoro
 */
@Local
public interface RecapPenumpukanUcFacadeLocal {

    void create(RecapPenumpukanUc recapPenumpukanUc);

    void edit(RecapPenumpukanUc recapPenumpukanUc);

    void remove(RecapPenumpukanUc recapPenumpukanUc);

    RecapPenumpukanUc find(Object id);

    List<RecapPenumpukanUc> findAll();

    List<RecapPenumpukanUc> findRange(int[] range);

    int count();

    public int deleteByNoPpkb(java.lang.String noPpkb);

}
