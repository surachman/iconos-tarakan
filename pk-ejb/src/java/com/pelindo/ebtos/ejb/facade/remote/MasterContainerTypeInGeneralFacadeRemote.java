/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterContainerTypeInGeneral;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author senoanggoro
 */
@Remote
public interface MasterContainerTypeInGeneralFacadeRemote {

    void create(MasterContainerTypeInGeneral masterContainerTypeInGeneral);

    void edit(MasterContainerTypeInGeneral masterContainerTypeInGeneral);

    void remove(MasterContainerTypeInGeneral masterContainerTypeInGeneral);

    MasterContainerTypeInGeneral find(Object id);

    List<MasterContainerTypeInGeneral> findAll();

    List<MasterContainerTypeInGeneral> findRange(int[] range);

    int count();

    public java.lang.Integer findGenericContType(short contSize, java.lang.String contType);

}
