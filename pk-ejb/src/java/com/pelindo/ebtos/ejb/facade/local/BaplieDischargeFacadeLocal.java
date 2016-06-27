/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.BaplieDischarge;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author senoanggoro
 */
@Local
public interface BaplieDischargeFacadeLocal {
    BaplieDischarge find(Object id);

    void create(BaplieDischarge baplieDischarge);

    void edit(BaplieDischarge baplieDischarge);

    void remove(BaplieDischarge baplieDischarge);

    Object[] findContOnBay(String noPpkb, int bay, int row, int tier);

    List<Object[]> findTheSameContainer (String cont_no);

    public java.util.List<java.lang.Object[]> findTranshipmentContainerByAllocationConstraint(String noPpkb, String portCode, Integer contSize, Integer contType, String contStatus, String grossClass, Integer overSize, Integer dg, java.lang.Integer isExport, String contNoAsString);

    public java.util.List<java.lang.Object[]> findShiftingContainerByAllocationConstraint(java.lang.String noPpkb, java.lang.String portCode, java.lang.Integer contSize, java.lang.Integer contType, java.lang.String contStatus, java.lang.String grossClass, java.lang.Integer overSize, java.lang.Integer dg, java.lang.Integer isExport, java.lang.String contNoAsString);

    public int deleteByContNoAndNoPpkb(java.lang.String contNo, java.lang.String noPpkb);
}
