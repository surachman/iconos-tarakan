/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycode-java
 */
@Local
public interface MasterYardCoordinatFacadeLocal {

    void create(MasterYardCoordinat masterYardCoordinat);

    void edit(MasterYardCoordinat masterYardCoordinat);

    void remove(MasterYardCoordinat masterYardCoordinat);

    MasterYardCoordinat find(Object id);

    List<MasterYardCoordinat> findAll();

    List<MasterYardCoordinat> findRange(int[] range);

    int count();

    List<Object[]> findContByBlockAndSlot(String block, int slot, String ppkb);

    List<Object[]> findContByBlockAndTier(String block, int tier, String ppkb);
    
    List<Object[]> findContByBlockAndSlotExistOnly(String block, int slot, String ppkb);

    List<Object[]> findContByBlockAndTierExistOnly(String block, int tier, String ppkb);

    public java.util.List<java.lang.Object[]> findOverallContByBlock(java.lang.String block);
    public java.util.List<java.lang.Object[]> findOverallContByBlockColors(java.lang.String block);

    Object[] findIdStatusByCoordinat(String block, short slot, short row, short tier);

    public java.util.List<MasterYardCoordinat> findAvailableCoordinateByAllocationConstraint(String noPpkb, String portCode, Integer contSize, Integer contType, String contStatus, String grossClass, Integer overSize, Integer dg, Integer isExport);

    public java.util.List<java.lang.Object[]> findLoadPlanningAllocations(java.lang.String originPpkb, java.lang.String destinationPpkb, String containersAsString);
    
    public boolean findIsContainerExistInPreviosRow(String cont_no, int slot, int row, int tier, String block, String noppkb);

    public int updatePlanningVesselByContNo(java.lang.String newValue, MasterPort nextPort, java.lang.String oldValue, java.util.List<java.lang.String> containers);

    public MasterYardCoordinat findByCoordinatAndStatus(String block, short slot, short row, short tier, String... status);

    boolean isBottomAvailable(String block, short slot, short row, short tier, short teus);

    boolean isTopEmpty(String block, short slot, short row, short tier, short teus);

    int clearYardsByNoPpkbAndContNo(String noPpkb, String contNo, String... statusses);

    public int revertContNoToBookingByPpkb(String noPpkb, String contNo);

    public int changeStatusToPlanningByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String targetContNo);
    
    

    public MasterYardCoordinat findByCoordinat(String block, int slot, int row, int tier);

    public List<MasterYardCoordinat> findByContNoAndStatusExist(java.lang.String contNo);

}
