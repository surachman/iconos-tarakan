/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapHandlingLoad;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface RecapHandlingLoadFacadeLocal {

    void create(RecapHandlingLoad recapHandlingLoad);

    void edit(RecapHandlingLoad recapHandlingLoad);

    void remove(RecapHandlingLoad recapHandlingLoad);

    RecapHandlingLoad find(Object id);

    List<RecapHandlingLoad> findAll();

    List<RecapHandlingLoad> findRange(int[] range);

    int count();

    Integer findByActCode(String actCode, String no_ppkb);

    Object[] findVesselHandlingByPPKB(String no_ppkb);

    Object[] findHandlingInvoice(String no_ppkb);

    List<Integer> findByPpkb(String no_ppkb);

    Object[] countHandlingLoad(String no_ppkb);

    public com.pelindo.ebtos.model.db.RecapHandlingLoad findByActivityCodeAndNoPpkb(java.lang.String activityCode, java.lang.String noPpkb);

    public int deleteByNoPpkb(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.RecapHandlingLoad findByBentuk3Constraint(Short contSize, java.lang.String contStatus, java.lang.String crane, java.lang.String isExportImport, java.lang.String openDoor, java.lang.String sling, java.lang.String containerType, java.lang.String activityCode, java.lang.String noPpkb, String currCode, java.lang.String twentyOneFeet, BigDecimal charge);
}
