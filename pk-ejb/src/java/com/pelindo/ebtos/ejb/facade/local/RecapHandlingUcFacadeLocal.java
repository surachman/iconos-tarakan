/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapHandlingUc;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface RecapHandlingUcFacadeLocal {

    void create(RecapHandlingUc recapHandlingUc);

    void edit(RecapHandlingUc recapHandlingUc);

    void remove(RecapHandlingUc recapHandlingUc);

    RecapHandlingUc find(Object id);

    List<RecapHandlingUc> findAll();

    List<RecapHandlingUc> findRange(int[] range);

    int count();

    public com.pelindo.ebtos.model.db.RecapHandlingUc findByBentuk3Constraint(java.lang.String noPpkb, java.lang.Integer weightGroup, java.lang.String crane, java.lang.String isExportImport, java.lang.String activityCode, java.lang.String currCode, java.lang.String operation, java.lang.String activity, BigDecimal charge);

    public int deleteByNoPpkb(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.RecapHandlingUc findByBentuk3ConstraintWithStatus(java.lang.String noPpkb, java.lang.Integer weightGroup, java.lang.String crane, java.lang.String isExportImport, java.lang.String activityCode, java.lang.String currCode, java.lang.String operation, java.lang.String activity, java.lang.String status, BigDecimal charge);

}
