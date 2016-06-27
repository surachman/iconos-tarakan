/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapUpahBuruh;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R Seno Anggoro
 */
@Local
public interface RecapUpahBuruhFacadeLocal {

    void create(RecapUpahBuruh recapUpahBuruh);

    void edit(RecapUpahBuruh recapUpahBuruh);

    void remove(RecapUpahBuruh recapUpahBuruh);

    RecapUpahBuruh find(Object id);

    List<RecapUpahBuruh> findAll();

    List<RecapUpahBuruh> findRange(int[] range);

    int count();

    public RecapUpahBuruh findByActivityCodeAndNoPpkb(String activityCode, String noPpkb);

    public int deleteByNoPpkb(java.lang.String noPpkb);

    public RecapUpahBuruh findByBentuk3Constraint(Short contSize, java.lang.String contStatus, java.lang.String crane, java.lang.String isExportImport, java.lang.String openDoor, java.lang.String sling, java.lang.String containerType, java.lang.String activityCode, java.lang.String operation, String activity, java.lang.String noPpkb, String currCode, java.lang.String twentyOneFeet, BigDecimal charge);

    public com.pelindo.ebtos.model.db.RecapUpahBuruh findByBentuk3ConstraintWithStatus(Short contSize, java.lang.String contStatus, java.lang.String crane, java.lang.String isExportImport, java.lang.String openDoor, java.lang.String sling, java.lang.String containerType, java.lang.String activityCode, java.lang.String operation, java.lang.String activity, java.lang.String noPpkb, java.lang.String currCode, java.lang.String status, java.lang.String twentyOneFeet, BigDecimal charge);
}
