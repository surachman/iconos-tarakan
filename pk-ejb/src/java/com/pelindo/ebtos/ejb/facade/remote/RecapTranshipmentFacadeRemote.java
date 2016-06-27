/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapTranshipment;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface RecapTranshipmentFacadeRemote {

    void create(RecapTranshipment recapTranshipment);

    void edit(RecapTranshipment recapTranshipment);

    void remove(RecapTranshipment recapTranshipment);

    RecapTranshipment find(Object id);

    List<RecapTranshipment> findAll();

    List<RecapTranshipment> findRange(int[] range);

    int count();

    Integer findByActCode(String actCode, String no_ppkb, String operation);

    List<Integer> findByPpkb(String no_ppkb);
}
