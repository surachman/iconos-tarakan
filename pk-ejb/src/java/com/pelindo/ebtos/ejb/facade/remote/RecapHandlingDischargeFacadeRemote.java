/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapHandlingDischarge;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface RecapHandlingDischargeFacadeRemote {

    void create(RecapHandlingDischarge recapHandlingDischarge);

    void edit(RecapHandlingDischarge recapHandlingDischarge);

    void remove(RecapHandlingDischarge recapHandlingDischarge);

    RecapHandlingDischarge find(Object id);

    List<RecapHandlingDischarge> findAll();

    List<RecapHandlingDischarge> findRange(int[] range);

    int count();

    Integer findByActCode(String actCode, String no_ppkb);

    Object[] findVesselHandlingByPPKB(String no_ppkb);

    Object[] findVesselHandlingByPPKBno(String no_ppkb);

    List<Integer> findByPpkb(String no_ppkb);

    Object[] countHandlingDischarge(String no_ppkb, Integer include);

}
