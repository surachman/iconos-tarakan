/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MPaymentType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MPaymentTypeFacadeRemote {

    void create(MPaymentType mPaymentType);

    void edit(MPaymentType mPaymentType);

    void remove(MPaymentType mPaymentType);

    MPaymentType find(Object id);

    List<MPaymentType> findAll();

    List<MPaymentType> findRange(int[] range);

    int count();

    List<Object[]> findByAll();
}
