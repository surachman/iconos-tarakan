/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.UcGatedeliveryService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author postgres
 */
@Remote
public interface UcGatedeliveryServiceFacadeRemote {

    void create(UcGatedeliveryService ucGatedeliveryService);

    void edit(UcGatedeliveryService ucGatedeliveryService);

    void remove(UcGatedeliveryService ucGatedeliveryService);

    UcGatedeliveryService find(Object id);

    List<UcGatedeliveryService> findAll();

    List<UcGatedeliveryService> findRange(int[] range);

    int count();

    public Object[] findUcGatedeliveryServiceByGate(String job_slip);

    public Object[] findUcGatedeliveryServiceByDateOutNull(String job_slip);

     public List<String> findUcGatedeliveryServiceByAutoComplete(String jobslip);

     UcGatedeliveryService findNotDeliveredYetByJobSlip(String jobslip);
}
