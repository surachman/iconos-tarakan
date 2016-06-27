/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapOtherPackage;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface RecapOtherPackageFacadeLocal {

    void create(RecapOtherPackage recapOtherPackage);

    void edit(RecapOtherPackage recapOtherPackage);

    void remove(RecapOtherPackage recapOtherPackage);

    RecapOtherPackage find(Object id);

    List<RecapOtherPackage> findAll();

    List<RecapOtherPackage> findRange(int[] range);

    int count();

    Integer findByActCode(String actCode, String no_ppkb);

    Object[] findVesselHandlingByPPKB(String no_ppkb);

    Object[] findVesselHandlingByPPKBno(String no_ppkb);

    List<Integer> findByPpkb(String no_ppkb);

    Object[] countHandlingDischarge(String no_ppkb, Integer include);

    public com.pelindo.ebtos.model.db.RecapOtherPackage findByActivityCodeAndNoPpkb(java.lang.String activityCode, java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.RecapOtherPackage findByBentuk3Constraint(Short contSize, java.lang.String contStatus, java.lang.String crane, String isExportImport, String openDoor, String sling, java.lang.String containerType, java.lang.String activityCode, java.lang.String noPpkb, String currCode, String twentyOneFeet, BigDecimal charge);

    public int deleteByNoPpkb(java.lang.String noPpkb);

}
