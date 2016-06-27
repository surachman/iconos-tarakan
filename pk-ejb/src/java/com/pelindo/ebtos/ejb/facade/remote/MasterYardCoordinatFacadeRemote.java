/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycode-java
 */
@Remote
public interface MasterYardCoordinatFacadeRemote {

    void create(MasterYardCoordinat masterYardCoordinat);

    void edit(MasterYardCoordinat masterYardCoordinat);

    void remove(MasterYardCoordinat masterYardCoordinat);

    MasterYardCoordinat find(Object id);

    List<MasterYardCoordinat> findAll();

    List<MasterYardCoordinat> findRange(int[] range);

    int count();

    Object[] findIdCordinatByStatusPlanningEmpty (String block, int slot, int row, int tier);

    Object[] findIdStatusByCoordinat(String block, short slot, short row, short tier);

    List<Object[]> findContByBlockAndSlot(String block, int slot, String ppkb);

    List<Object[]> findContByBlockAndTier(String block, int tier, String ppkb);

    Object[] findIdCordinatByStatus(String block, int slot, int row, int tier);

    List<Object[]> findByContNo(String cont_no);

    Object[] findIdCordinatByStatusExist(String block, int slot, int row, int tier);

    List<Object[]> findContByBlockAndSlotExistOnly(String block, int slot, String ppkb);

    List<Object[]> findContByBlockAndTierExistOnly(String block, int tier, String ppkb);

    Object[] findIdCordinatByPlanning(String block, int slot, int row, int tier);

    List<Integer> findIdByContNo(String cont_no);

    int deleteByBlock(String block);

    List<Object[]> findAllStatusByBlock(String block);

    int deleteById(int id);

    List<Object[]> findGenerate(String no_ppkb, boolean is_trans,boolean isShift);

    String findContNoGenerate(String no_ppkb);

    int deleteByContNo(String cont_no);

    List<Integer> unFinishBayPlanDischargeByPPKB(String no_ppkb);
    
    List<Integer> unFinishBayPlanDischargeByPPKBCont(String no_ppkb);

    boolean findDuplicate(String cont_no);

    public java.lang.Object[] checkContDuplicateWith(java.lang.String contNo);

    List<Integer> findIdByContNoUpdatePlugging(String cont_no,String no_ppkb);

    public java.lang.Integer clearYardByPpkbStatusPlanning(java.lang.String noPpkb);

    public java.util.List<Object[]> findByContNoStatusExist(java.lang.String contNo);

    public java.util.List<java.lang.Object[]> findLoadPlanningAllocations(java.lang.String originPpkb, java.lang.String destinationPpkb, String containersAsString);

    public java.util.List<MasterYardCoordinat> findAvailableCoordinateByAllocationConstraint(String noPpkb, String portCode, Integer contSize, Integer contType, String contStatus, String grossClass, Integer overSize, Integer dg, Integer isExport);

    public int revertContNoToBookingByPpkb(java.lang.String noPpkb, java.lang.String targetContNo);

    public List<Object[]> findReceivingPlanningAllocations(String noPpkb, String containersAsString);

    public com.pelindo.ebtos.model.db.master.MasterYardCoordinat findAvailableCoordinat(java.lang.String block, int slot, int row, int tier);

    public com.pelindo.ebtos.model.db.master.MasterYardCoordinat findByCoordinat(java.lang.String block, int slot, int row, int tier);

    public int clearYardsByContNo(java.lang.String contNo);

    public java.util.List<com.pelindo.ebtos.model.db.master.MasterYardCoordinat> findCoordinatesByContNo(java.lang.String contNo);

    public java.lang.Integer changeNoPpkbByContNo(java.lang.String newPpkb, java.lang.String oldPpkb, java.lang.String contNo);

    public int deleteConstraintByAllocationRange(java.lang.String noPpkb, java.lang.Short frSlot, java.lang.Short toSlot, java.lang.Short frRow, java.lang.Short toRow);
    
    public Integer[] findStatistics();

    public boolean isBottomAvailable(java.lang.String block, short slot, short row, short tier, short teus);

    public boolean isTopEmpty(java.lang.String block, short slot, short row, short tier, short teus);

    public int deleteByContNoAndPpkb(java.lang.String cont_no, java.lang.String no_ppkb);
    
    //penambahan update master yard container ketika gate out by ade chelsea 11 april 2014
    public int updateMasterYardContainerGateOut(java.lang.String noPpkb, java.lang.String targetContNo);
}
