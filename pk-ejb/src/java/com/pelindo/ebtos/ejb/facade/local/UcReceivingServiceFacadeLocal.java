/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UcReceivingService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author postgres
 */
@Local
public interface UcReceivingServiceFacadeLocal {

    void create(UcReceivingService ucReceivingService);

    void edit(UcReceivingService ucReceivingService);

    void remove(UcReceivingService ucReceivingService);

    UcReceivingService find(Object id);

    List<UcReceivingService> findAll();

    List<UcReceivingService> findRange(int[] range);

    int count();

    public java.util.List<com.pelindo.ebtos.model.db.UcReceivingService> findByNoPpkbAndNotTruckLoosing(java.lang.String noPpkb);

    public int removeMasaByNoPpkbAndNotTruckLoosing(java.lang.String noPpkb);

    List<Object[]> findByPpkbTLfalse(String no_ppkb);
}
