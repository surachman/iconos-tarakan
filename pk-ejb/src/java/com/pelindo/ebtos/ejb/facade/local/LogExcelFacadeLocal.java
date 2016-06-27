/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.LogExcel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycode-java
 */
@Local
public interface LogExcelFacadeLocal {

    void create(LogExcel logExcel);

    void edit(LogExcel logExcel);

    void remove(LogExcel logExcel);

    LogExcel find(Object id);

    List<LogExcel> findAll();

    List<LogExcel> findRange(int[] range);

    int count();

}
