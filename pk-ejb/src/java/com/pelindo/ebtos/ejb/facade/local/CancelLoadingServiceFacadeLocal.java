/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.CancelLoadingService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface CancelLoadingServiceFacadeLocal {

    void create(CancelLoadingService cancelLoadingService);

    void edit(CancelLoadingService cancelLoadingService);

    void remove(CancelLoadingService cancelLoadingService);

    CancelLoadingService find(Object id);

    List<CancelLoadingService> findAll();

    List<CancelLoadingService> findRange(int[] range);

    int count();

    List<Object[]> findCancelLoadingServiceByNoreg(String no_reg);

    //String findCancelLoadingServiceId(String no_ppkb, String no_reg, String cont_no);
    String generateId(String bulan);

    List<String> findGateInPassableJobslips(String jobslip);

    public CancelLoadingService findMovableContainer(String contNo);

    public CancelLoadingService findByNewPpkbAndRelocationStatus(String noPpkb, String contNo, Boolean b);

    public CancelLoadingService findByNewPpkbAndContNo(String noPpkb, String contNo);
}
