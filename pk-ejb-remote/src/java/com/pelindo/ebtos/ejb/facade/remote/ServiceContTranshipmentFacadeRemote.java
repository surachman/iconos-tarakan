/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceContTranshipmentFacadeRemote {

    void create(ServiceContTranshipment serviceContTranshipment);

    void edit(ServiceContTranshipment serviceContTranshipment);

    void remove(ServiceContTranshipment serviceContTranshipment);

    ServiceContTranshipment find(Object id);

    List<ServiceContTranshipment> findAll();

    List<ServiceContTranshipment> findRange(int[] range);

    int count();

    Object[] findByContNo(String cont_no, String pos);

    List<Object[]> findServiceContTranshipmentByPpkbList(String no_ppkb);

    List<Object[]> findServiceContTranshipmentsConfirm(String no_ppkb);

    List<Object[]> findServiceContTranshipmentsSelect(String no_ppkb);

    int updateServiceContTranshipmentsForDeleteAll(int id);

    List<Object[]> findServiceContTranshipmentsConfirmServed(String no_ppkb);

    List<Object[]> findServiceContTranshipmentsSelectServed(String no_ppkb);

    int updateServiceContTranshipmentsForDeleteAllPlacement(int id);

    List<Object[]> findServiceContTranshipmentsByNewPpkbNull();

    List<Object[]> findRekap(String no_ppkb);

    List<Object[]> findByPpkb(String no_ppkb);

    Integer findByIdTranshipment(String no_ppkb, String cont_no);

    public java.util.List<com.pelindo.ebtos.model.db.ServiceContTranshipment> findContainersThatHaveNotReachedCY(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.ServiceContTranshipment findByTranshipmentContainerByPosition(java.lang.String noPpkb, java.lang.String contNo, java.lang.String position);

    public com.pelindo.ebtos.model.db.ServiceContTranshipment findByTranshipmentByContNoAndPosition(java.lang.String contNo, java.lang.String position);

    public ServiceContTranshipment findByNewPpkbAndContNo(String newPpkb, String contNo);
}
