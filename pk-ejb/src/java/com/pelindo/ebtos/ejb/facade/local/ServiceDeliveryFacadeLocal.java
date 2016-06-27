/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceDelivery;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceDeliveryFacadeLocal {

    void create(ServiceDelivery serviceDelivery);

    void edit(ServiceDelivery serviceDelivery);

    void remove(ServiceDelivery serviceDelivery);

    ServiceDelivery find(Object id);

    List<ServiceDelivery> findAll();

    List<ServiceDelivery> findRange(int[] range);

    int findIdDeliveryConfirm (String no_ppkb, String cont_no);

    int count();

    ServiceDelivery findByNoPpkbAndContNo(String noPpkb, String contNo);

}
