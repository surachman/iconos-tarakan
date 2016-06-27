/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapHandlingDischarge;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface RecapHandlingDischargeFacadeLocal {

    void create(RecapHandlingDischarge recapHandlingDischarge);

    void edit(RecapHandlingDischarge recapHandlingDischarge);

    void remove(RecapHandlingDischarge recapHandlingDischarge);

    RecapHandlingDischarge find(Object id);

    List<RecapHandlingDischarge> findAll();

    List<RecapHandlingDischarge> findRange(int[] range);

    int count();

    Integer findByActCode(String actCode, String no_ppkb);

    Object[] findVesselHandlingByPPKB(String no_ppkb);

    Object[] findVesselHandlingByPPKBno(String no_ppkb);

    List<Integer> findByPpkb(String no_ppkb);

    Object[] countHandlingDischarge(String no_ppkb, Integer include);

    public com.pelindo.ebtos.model.db.RecapHandlingDischarge findByActivityCodeAndNoPpkb(java.lang.String activityCode, java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.RecapHandlingDischarge findByBentuk3Constraint(Short contSize, java.lang.String contStatus, java.lang.String crane, String isExportImport, String openDoor, String sling, java.lang.String containerType, java.lang.String activityCode, java.lang.String noPpkb, String currCode, String twentyOneFeet, BigDecimal charge);

    public int deleteByNoPpkb(java.lang.String noPpkb);

}
