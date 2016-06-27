/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapPenumpukanLoad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycode-java
 */
@Local
public interface RecapPenumpukanLoadFacadeLocal {

    void create(RecapPenumpukanLoad recapPenumpukanLoad);

    void edit(RecapPenumpukanLoad recapPenumpukanLoad);

    void remove(RecapPenumpukanLoad recapPenumpukanLoad);

    RecapPenumpukanLoad find(Object id);

    List<RecapPenumpukanLoad> findAll();

    List<RecapPenumpukanLoad> findRange(int[] range);

    int count();

}
