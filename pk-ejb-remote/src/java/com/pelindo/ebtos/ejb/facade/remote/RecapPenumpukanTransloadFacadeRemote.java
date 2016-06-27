/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapPenumpukanTransload;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface RecapPenumpukanTransloadFacadeRemote {

    void create(RecapPenumpukanTransload recapPenumpukanTransload);

    void edit(RecapPenumpukanTransload recapPenumpukanTransload);

    void remove(RecapPenumpukanTransload recapPenumpukanTransload);

    RecapPenumpukanTransload find(Object id);

    List<RecapPenumpukanTransload> findAll();

    List<RecapPenumpukanTransload> findRange(int[] range);

    int count();

   Integer findRecapPenumpukanTransloadByPpkb(String operation, String no_ppkb);

}
