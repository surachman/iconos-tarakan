/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UcGatedeliveryService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author postgres
 */
@Local
public interface UcGatedeliveryServiceFacadeLocal {

    void create(UcGatedeliveryService ucGatedeliveryService);

    void edit(UcGatedeliveryService ucGatedeliveryService);

    void remove(UcGatedeliveryService ucGatedeliveryService);

    UcGatedeliveryService find(Object id);

    List<UcGatedeliveryService> findAll();

    List<UcGatedeliveryService> findRange(int[] range);

    int count();

}
