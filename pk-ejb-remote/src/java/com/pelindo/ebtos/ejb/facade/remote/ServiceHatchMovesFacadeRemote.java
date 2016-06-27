/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.ServiceHatchMoves;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface ServiceHatchMovesFacadeRemote {

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

    public com.pelindo.ebtos.model.db.ServiceHatchMoves findByOperationBayAndHatchMoves(java.lang.String noPpkb, java.lang.Short bay, java.lang.String operation, java.lang.String hatchMoves);

}
