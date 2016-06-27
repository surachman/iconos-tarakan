/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.ServiceHatchMoves;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface ServiceHatchMovesFacadeLocal {

    void create(ServiceHatchMoves serviceHatchMoves);

    void edit(ServiceHatchMoves serviceHatchMoves);

    void remove(ServiceHatchMoves serviceHatchMoves);

    ServiceHatchMoves find(Object id);

    List<ServiceHatchMoves> findAll();

    List<ServiceHatchMoves> findRange(int[] range);

    int count();

     List<Object[]> findServiceHatchMovesByPpkb (String no_ppkb);

     List<Object[]> findServiceHatchMovesByPpkbOperation(String no_ppkb,String operation);

     List<Object[]> findServiceHatchMovesByPpkbDischarge(String no_ppkb);

     List<Object[]> findServiceHatchMovesByPpkbLoad(String no_ppkb);

     List<Object[]> findServiceHatchMovesByPpkbRecap(String no_ppkb);

    public java.util.List<com.pelindo.ebtos.model.db.ServiceHatchMoves> findByNoPpkbAndStatusPaid(java.lang.String noPpkb);

}
