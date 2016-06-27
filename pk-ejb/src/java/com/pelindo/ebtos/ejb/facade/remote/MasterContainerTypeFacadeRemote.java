/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterContainerType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterContainerTypeFacadeRemote {

    void create(MasterContainerType masterContainerType);

    void edit(MasterContainerType masterContainerType);

    void remove(MasterContainerType masterContainerType);

    MasterContainerType find(Object id);

    List<MasterContainerType> findAll();

    List<MasterContainerType> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();

    List<Object[]> findAllByISO(String iso_code);

    List<Object[]> findReefer();

    MasterContainerType findByIsoCode(String isoCode);

}
