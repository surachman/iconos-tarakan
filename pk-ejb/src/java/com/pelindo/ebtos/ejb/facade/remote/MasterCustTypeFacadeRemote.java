/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterCustType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterCustTypeFacadeRemote {

    void create(MasterCustType masterCustType);

    void edit(MasterCustType masterCustType);

    void remove(MasterCustType masterCustType);

    MasterCustType find(Object id);

    List<MasterCustType> findAll();

    List<MasterCustType> findRange(int[] range);

    int count();

}
