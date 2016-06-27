/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.OperatorModel;
import com.pelindo.ebtos.model.db.master.MasterOperator;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterOperatorFacadeRemote {

    void create(MasterOperator masterOperator);

    void edit(MasterOperator masterOperator);

    void remove(MasterOperator masterOperator);

    MasterOperator find(Object id);

    List<MasterOperator> findAll();

    List<MasterOperator> findRange(int[] range);

    int count();

    List<OperatorModel> findOperators();

    List<Object[]> findMasterOperators4HHT();
}
