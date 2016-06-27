/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapOtherPackage;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface RecapOtherPackageFacadeRemote {

    void create(RecapOtherPackage recapOtherPackage);

    void edit(RecapOtherPackage recapOtherPackage);

    void remove(RecapOtherPackage recapOtherPackage);

    RecapOtherPackage find(Object id);

    List<RecapOtherPackage> findAll();

    List<RecapOtherPackage> findRange(int[] range);

    int count();

    Integer findByActCode(String actCode, String no_ppkb);

    Object[] findVesselHandlingByPPKB(String no_ppkb);

    Object[] findVesselHandlingByPPKBno(String no_ppkb);

    List<Integer> findByPpkb(String no_ppkb);

    Object[] countHandlingDischarge(String no_ppkb, Integer include);

}
