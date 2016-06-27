/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterPattern;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface MasterPatternFacadeRemote {

    void create(MasterPattern masterPattern);

    void edit(MasterPattern masterPattern);

    void remove(MasterPattern masterPattern);

    MasterPattern find(Object id);

    List<MasterPattern> findAll();

    List<MasterPattern> findRange(int[] range);

    int count();

}
