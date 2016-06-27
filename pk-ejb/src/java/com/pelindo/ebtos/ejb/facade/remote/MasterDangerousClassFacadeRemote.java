/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterDangerousClass;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R. Seno Anggoro A
 */
@Remote
public interface MasterDangerousClassFacadeRemote {

    void create(MasterDangerousClass masterDangerousClass);

    void edit(MasterDangerousClass masterDangerousClass);

    void remove(MasterDangerousClass masterDangerousClass);

    MasterDangerousClass find(Object id);

    List<MasterDangerousClass> findAll();

    List<MasterDangerousClass> findRange(int[] range);

    int count();

}
