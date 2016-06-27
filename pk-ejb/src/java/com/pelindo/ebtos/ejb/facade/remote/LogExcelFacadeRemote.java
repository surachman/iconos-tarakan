/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.LogExcel;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycode-java
 */
@Remote
public interface LogExcelFacadeRemote {

    void create(LogExcel logExcel);

    void edit(LogExcel logExcel);

    void remove(LogExcel logExcel);

    LogExcel find(Object id);

    List<LogExcel> findAll();

    List<LogExcel> findRange(int[] range);

    int count();

    List<Object[]> findAllExcel();

}
