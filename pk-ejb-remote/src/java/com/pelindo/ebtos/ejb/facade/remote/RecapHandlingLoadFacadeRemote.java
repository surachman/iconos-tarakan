/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapHandlingLoad;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface RecapHandlingLoadFacadeRemote {

    void create(RecapHandlingLoad recapHandlingLoad);

    void edit(RecapHandlingLoad recapHandlingLoad);

    void remove(RecapHandlingLoad recapHandlingLoad);

    RecapHandlingLoad find(Object id);

    List<RecapHandlingLoad> findAll();

    List<RecapHandlingLoad> findRange(int[] range);

    int count();

    Integer findByActCode(String actCode, String no_ppkb);

    Object[] findVesselHandlingByPPKB(String no_ppkb);

    Object[] findHandlingInvoice(String no_ppkb);

    List<Integer> findByPpkb(String no_ppkb);

    Object[] countHandlingLoad(String no_ppkb);
}
