/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceCfsStripping;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceCfsStrippingFacadeRemote {

    void create(ServiceCfsStripping serviceCfsStripping);

    void edit(ServiceCfsStripping serviceCfsStripping);

    void remove(ServiceCfsStripping serviceCfsStripping);

    ServiceCfsStripping find(Object id);

    List<ServiceCfsStripping> findAll();

    List<ServiceCfsStripping> findRange(int[] range);

    int count();

    List<Object[]> findAllNative();

    List<Object[]> findForDeliveryGoods();

    List<Object[]> findBL(String contNo);

    List<Object[]> findDeliveryTrue();

    Integer findId(String noPpkb, String noCont, String noBL);

    List<Object[]> findByPpkbNCont(String no_ppkb, String no_cont);
}
