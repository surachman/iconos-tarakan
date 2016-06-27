/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.UcDeliveryService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author postgres
 */
@Local
public interface UcDeliveryServiceFacadeLocal {

    void create(UcDeliveryService ucDeliveryService);

    void edit(UcDeliveryService ucDeliveryService);

    void remove(UcDeliveryService ucDeliveryService);

    UcDeliveryService find(Object id);

    List<UcDeliveryService> findAll();

    List<UcDeliveryService> findRange(int[] range);

    int count();

    public String findByPpkbNIdUC(Integer integer, String noPpkb);

}
