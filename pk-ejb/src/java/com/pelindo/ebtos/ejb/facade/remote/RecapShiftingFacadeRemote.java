/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapShifting;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface RecapShiftingFacadeRemote {

    void create(RecapShifting recapShifting);

    void edit(RecapShifting recapShifting);

    void remove(RecapShifting recapShifting);

    RecapShifting find(Object id);

    List<RecapShifting> findAll();

    List<RecapShifting> findRange(int[] range);

    int count();

    Integer findByActCode(String actCode, String no_ppkb, String operation);

    Object[] findHandlingInvoice(String no_ppkb);

    List<Integer> findByPpkb(String no_ppkb);
}
