/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterPluggingReefer;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterPluggingReeferFacadeRemote {

    void create(MasterPluggingReefer masterPluggingReefer);

    void edit(MasterPluggingReefer masterPluggingReefer);

    void remove(MasterPluggingReefer masterPluggingReefer);

    MasterPluggingReefer find(Object id);

    List<MasterPluggingReefer> findAll();

    List<MasterPluggingReefer> findRange(int[] range);

    List<String> findNotInServiceReefer();

    int count();

    List<String> findPluggingCode();

    List<Object[]> findPluggingCodeList();

}
