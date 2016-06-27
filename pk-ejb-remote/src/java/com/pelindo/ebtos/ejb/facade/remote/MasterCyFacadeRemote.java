/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterCy;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author x42jr
 */
@Remote
public interface MasterCyFacadeRemote {

    void create(MasterCy masterCy);

    void edit(MasterCy masterCy);

    void remove(MasterCy masterCy);

    MasterCy find(Object id);

    List<MasterCy> findAll();

    List<MasterCy> findRange(int[] range);

    int count();

}
