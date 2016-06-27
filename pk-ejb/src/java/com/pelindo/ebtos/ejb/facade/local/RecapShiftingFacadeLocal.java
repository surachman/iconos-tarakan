/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapShifting;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface RecapShiftingFacadeLocal {

    void create(RecapShifting recapShifting);

    void edit(RecapShifting recapShifting);

    void remove(RecapShifting recapShifting);

    RecapShifting find(Object id);

    List<RecapShifting> findAll();

    List<RecapShifting> findRange(int[] range);

    int count();

    Integer findByActCode(String actCode, String no_ppkb, String operation);

    Object[] findHandlingInvoice(String no_ppkb);

    List<Integer> findByPpkb(String no_ppkb);

    public com.pelindo.ebtos.model.db.RecapShifting findByActivityCodeNoPpkbAndOperation(java.lang.String activityCode, java.lang.String noPpkb, java.lang.String operation);

    public int deleteByNoPpkb(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.RecapShifting findByBentuk3Constraint(Short contSize, java.lang.String contStatus, java.lang.String crane, String isExportImport, String openDoor, String sling, java.lang.String containerType, java.lang.String activityCode, java.lang.String operation, java.lang.String shiftTo, java.lang.String noPpkb, String currCode, String twentyOneFeet, BigDecimal charge);
}
