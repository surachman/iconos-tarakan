/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterIdletimetype;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface MasterIdletimetypeFacadeLocal {

    void create(MasterIdletimetype masterIdletimetype);

    void edit(MasterIdletimetype masterIdletimetype);

    void remove(MasterIdletimetype masterIdletimetype);

    MasterIdletimetype find(Object id);

    List<MasterIdletimetype> findAll();

    List<MasterIdletimetype> findRange(int[] range);

    int count();

}
