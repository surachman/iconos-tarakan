/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapPenumpukanLoad;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycode-java
 */
@Remote
public interface RecapPenumpukanLoadFacadeRemote {

    void create(RecapPenumpukanLoad recapPenumpukanLoad);

    void edit(RecapPenumpukanLoad recapPenumpukanLoad);

    void remove(RecapPenumpukanLoad recapPenumpukanLoad);

    RecapPenumpukanLoad find(Object id);

    List<RecapPenumpukanLoad> findAll();

    List<RecapPenumpukanLoad> findRange(int[] range);

    int count();

}
