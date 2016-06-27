/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceCfsStuffing;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceCfsStuffingFacadeRemote {

    void create(ServiceCfsStuffing serviceCfsStuffing);

    void edit(ServiceCfsStuffing serviceCfsStuffing);

    void remove(ServiceCfsStuffing serviceCfsStuffing);

    ServiceCfsStuffing find(Object id);

    List<ServiceCfsStuffing> findAll();

    List<ServiceCfsStuffing> findRange(int[] range);

    int count();
    
    List<Object[]> findAllNative();

    List<Object[]> findServiceCfsStuffingByList();

    Object[] findServiceCfsStuffingByPenumpukan(String job_slip);

    List<String> findServiceCfsStuffingByAutoComplete(String jobslip);

    List<Object[]> findByPpkbNCont(String no_ppkb, String no_cont);

    List<Object[]> findServiceCfsStuffingAllAndBl(String bl_no);

    List<Object[]> findServiceCfsStuffingAll();

}
