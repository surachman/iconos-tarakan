/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterView;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface MasterViewFacadeLocal {

    void create(MasterView masterView);

    void edit(MasterView masterView);

    void remove(MasterView masterView);

    MasterView find(Object id);

    List<MasterView> findAll();

    List<MasterView> findRange(int[] range);

    int count();

    public java.util.List<java.lang.Object[]> findChildrenByParent(java.lang.Integer parent);

}
