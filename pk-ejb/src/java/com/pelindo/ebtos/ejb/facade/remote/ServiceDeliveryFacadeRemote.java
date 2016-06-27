/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceDelivery;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceDeliveryFacadeRemote {

    void create(ServiceDelivery serviceDelivery);

    void edit(ServiceDelivery serviceDelivery);

    void remove(ServiceDelivery serviceDelivery);

    ServiceDelivery find(Object id);

    List<ServiceDelivery> findAll();

    List<ServiceDelivery> findRange(int[] range);

    int findIdDeliveryConfirm (String no_ppkb, String cont_no);

    ServiceDelivery findByNoPpkbAndContNo (String noPpkb, String contNo);
    
    int count();

    List<Object[]> findYardDischarge (String noPpkb, String contNo);

    public java.util.List<java.lang.String> findLikeNoPPKB(java.lang.String noPpkb);

    public java.util.List<java.lang.String> findLikeContNo(java.lang.String noPpkb, java.lang.String contNo);

    public java.util.List<Object[]> findByNoPpkbAndContNoUpdateDelivery(java.lang.String noPpkb, java.lang.String contNo);

}
