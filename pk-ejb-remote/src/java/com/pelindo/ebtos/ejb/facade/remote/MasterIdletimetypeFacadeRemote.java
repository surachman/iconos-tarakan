/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterIdletimetype;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author USER
 */
@Remote
public interface MasterIdletimetypeFacadeRemote {

    void create(MasterIdletimetype masterIdletimetype);

    void edit(MasterIdletimetype masterIdletimetype);

    void remove(MasterIdletimetype masterIdletimetype);

    MasterIdletimetype find(Object id);

    List<MasterIdletimetype> findAll();

    List<MasterIdletimetype> findRange(int[] range);

    int count();

}
