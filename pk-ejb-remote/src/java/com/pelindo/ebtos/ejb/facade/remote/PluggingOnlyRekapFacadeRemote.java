/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.PluggingOnlyRekap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author wulan
 */
@Remote
public interface PluggingOnlyRekapFacadeRemote {

    void create(PluggingOnlyRekap pluggingOnlyRekap);

    void edit(PluggingOnlyRekap pluggingOnlyRekap);

    void remove(PluggingOnlyRekap pluggingOnlyRekap);

    PluggingOnlyRekap find(Object id);

    List<PluggingOnlyRekap> findAll();

    List<PluggingOnlyRekap> findRange(int[] range);

    int count();

}
