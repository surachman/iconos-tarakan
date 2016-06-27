/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.UcGatereceivingService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author postgres
 */
@Remote
public interface UcGatereceivingServiceFacadeRemote {

    void create(UcGatereceivingService ucGatereceivingService);

    void edit(UcGatereceivingService ucGatereceivingService);

    void remove(UcGatereceivingService ucGatereceivingService);

    UcGatereceivingService find(Object id);

    List<UcGatereceivingService> findAll();

    List<UcGatereceivingService> findRange(int[] range);

    int count();

    public Object[] findUcGatereceivingServiceByGatee(String job_slip);

    public Object[] findUcGatereceivingServiceByDateOutNull(String job_slip);

    public List<String> findUcGatereceivingServiceByAutoComplete(String jobslip);

    Object[] findUcGatereceivingServiceByCancelInvoice(String job_slip);
}
