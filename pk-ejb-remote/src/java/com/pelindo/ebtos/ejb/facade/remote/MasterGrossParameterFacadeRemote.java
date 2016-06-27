/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterGrossParameter;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author arie
 */
@Remote
public interface MasterGrossParameterFacadeRemote {

    void create(MasterGrossParameter masterGrossParameter);

    void edit(MasterGrossParameter masterGrossParameter);

    void remove(MasterGrossParameter masterGrossParameter);

    MasterGrossParameter find(Object id);

    List<MasterGrossParameter> findAll();

    List<MasterGrossParameter> findRange(int[] range);

    MasterGrossParameter findByContTypeAndGross(Short contSize, int contGros);

    int count();

}
