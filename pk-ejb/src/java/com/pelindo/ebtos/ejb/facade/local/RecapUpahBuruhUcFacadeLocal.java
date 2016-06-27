/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapUpahBuruhUc;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface RecapUpahBuruhUcFacadeLocal {

    void create(RecapUpahBuruhUc recapUpahBuruhUc);

    void edit(RecapUpahBuruhUc recapUpahBuruhUc);

    void remove(RecapUpahBuruhUc recapUpahBuruhUc);

    RecapUpahBuruhUc find(Object id);

    List<RecapUpahBuruhUc> findAll();

    List<RecapUpahBuruhUc> findRange(int[] range);

    int count();

    public com.pelindo.ebtos.model.db.RecapUpahBuruhUc findByBentuk3Constraint(java.lang.String noPpkb, java.lang.Integer weightGroup, java.lang.String crane, java.lang.String isExportImport, java.lang.String activityCode, java.lang.String currCode, java.lang.String operation, java.lang.String activity, BigDecimal charge);

    public com.pelindo.ebtos.model.db.RecapUpahBuruhUc findByBentuk3ConstraintWithStatus(java.lang.String noPpkb, java.lang.Integer weightGroup, java.lang.String crane, java.lang.String isExportImport, java.lang.String activityCode, java.lang.String currCode, java.lang.String operation, java.lang.String activity, java.lang.String status, BigDecimal charge);

    public int deleteByNoPpkb(java.lang.String noPpkb);

}
