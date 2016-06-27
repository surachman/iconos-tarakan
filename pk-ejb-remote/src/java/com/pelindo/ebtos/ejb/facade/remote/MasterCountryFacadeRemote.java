/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterCountry;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterCountryFacadeRemote {

    void create(MasterCountry masterCountry);

    void edit(MasterCountry masterCountry);

    void remove(MasterCountry masterCountry);

    MasterCountry find(Object id);

    List<MasterCountry> findAll();

    List<MasterCountry> findRange(int[] range);

    List<Object[]> findAllNative();

    int count();

}
