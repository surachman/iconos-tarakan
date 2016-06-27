/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.RecapTranshipment;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface RecapTranshipmentFacadeLocal {

    void create(RecapTranshipment recapTranshipment);

    void edit(RecapTranshipment recapTranshipment);

    void remove(RecapTranshipment recapTranshipment);

    RecapTranshipment find(Object id);

    List<RecapTranshipment> findAll();

    List<RecapTranshipment> findRange(int[] range);

    int count();

    Integer findByActCode(String actCode, String no_ppkb, String operation);

    List<Integer> findByPpkb(String no_ppkb);

    public com.pelindo.ebtos.model.db.RecapTranshipment findByActivityCodeNoPpkbAndOperation(java.lang.String activityCode, java.lang.String noPpkb, java.lang.String operation);

    public int deleteByNoPpkb(java.lang.String noPpkb);

    public com.pelindo.ebtos.model.db.RecapTranshipment findByBentuk3Constraint(
            Short contSize,
            java.lang.String contStatus,
            java.lang.String crane,
            String isExportImport,
            String openDoor,
            String sling,
            java.lang.String containerType,
            java.lang.String activityCode,
            java.lang.String operation,
            java.lang.String noPpkb,
            String currCode,
            String twentyOneFeet,
            BigDecimal charge);
}
