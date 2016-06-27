/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UcGatereceivingService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author postgres
 */
@Local
public interface UcGatereceivingServiceFacadeLocal {

    void create(UcGatereceivingService ucGatereceivingService);

    void edit(UcGatereceivingService ucGatereceivingService);

    void remove(UcGatereceivingService ucGatereceivingService);

    UcGatereceivingService find(Object id);

    List<UcGatereceivingService> findAll();

    List<UcGatereceivingService> findRange(int[] range);

    int count();

}
