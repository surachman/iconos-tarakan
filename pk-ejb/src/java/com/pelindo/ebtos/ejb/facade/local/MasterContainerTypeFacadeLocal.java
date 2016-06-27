/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterContainerType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface MasterContainerTypeFacadeLocal {

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

    com.pelindo.ebtos.model.db.master.MasterContainerType findByIsoCode(java.lang.String isoCode);

    public com.pelindo.ebtos.model.db.master.MasterContainerType findByIsoCodeSimplified(java.lang.String isoCode);

}
