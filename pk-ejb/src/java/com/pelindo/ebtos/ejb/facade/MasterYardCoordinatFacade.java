/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.remote.MasterYardCoordinatFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterYardCoordinatFacadeLocal;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterYardCoordinat;
import com.pelindo.ebtos.util.ContainerBookingGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author dycode-java
 */
@Stateless
public class MasterYardCoordinatFacade extends AbstractFacade<MasterYardCoordinat> implements MasterYardCoordinatFacadeLocal, MasterYardCoordinatFacadeRemote {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterYardCoordinatFacade() {
        super(MasterYardCoordinat.class);
    }

    //MasterYardCoordinat.Native.findIdStatusByCoordinat
    public Object[] findIdStatusByCoordinat(String block, short slot, short row, short tier) {
        Object[] temp = new Object[11];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findIdStatusByCoordinat").setParameter(1, block).setParameter(2, slot).setParameter(3, row).setParameter(4, tier).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public boolean isFlyingContainerWhenAttached(String block, int slot, int row, int tier) {
        List<String> isValid = (List<String>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.isFlyingContainerWhenAttached").setParameter(1, block).setParameter(2, slot).setParameter(3, row).setParameter(4, tier).getResultList();
        if (isValid.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public List<Object[]> findContByBlockAndSlot(String block, int slot, String ppkb) {
        if (ppkb == null) {
            return objectsDecimalsToObjectsInts(
                    (List<Object[]>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findContByBlockAndSlot").setParameter(1, block).setParameter(2, slot).getResultList()
            );
        }
        return objectsDecimalsToObjectsInts(
                (List<Object[]>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findContByBlockSlotAndPpkb").setParameter(1, block).setParameter(2, slot).setParameter(3, ppkb).getResultList()
        );
    }

    public List<Object[]> findContByBlockAndTier(String block, int tier, String ppkb) {
        if (ppkb == null) {
            return objectsDecimalsToObjectsInts(
                    (List<Object[]>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findContByBlockAndTier").setParameter(1, block).setParameter(2, tier).getResultList()
            );
        }
        return objectsDecimalsToObjectsInts(
                (List<Object[]>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findContByBlockTierAndPpkb").setParameter(1, block).setParameter(2, tier).setParameter(3, ppkb).getResultList()
        );
    }

    public List<Object[]> findContByBlockAndSlotExistOnly(String block, int slot, String ppkb) {
        if (ppkb == null) {
            return objectsDecimalsToObjectsInts(
                    (List<Object[]>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findContByBlockAndSlotExistOnly").setParameter(1, block).setParameter(2, slot).getResultList()
            );
        }
        return objectsDecimalsToObjectsInts(
                (List<Object[]>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findContByBlockSlotAndPpkbExistOnly")
                .setParameter(1, slot)
                .setParameter(2, block)
                .setParameter(3, ppkb)
                .getResultList()
        );
    }

    public List<Object[]> findContByBlockAndTierExistOnly(String block, int tier, String ppkb) {
        if (ppkb == null) {
            return objectsDecimalsToObjectsInts(
                    (List<Object[]>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findContByBlockAndTierExistOnly").setParameter(1, block).setParameter(2, tier).getResultList()
            );
        }
        return objectsDecimalsToObjectsInts(
                (List<Object[]>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findContByBlockTierAndPpkbExistOnly").setParameter(1, block).setParameter(2, tier).setParameter(3, ppkb).getResultList()
        );
    }

    public List<Object[]> findOverallContByBlock(String block) {
        return objectsDecimalsToObjectsInts(
                (List<Object[]>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findOverallContByBlock").setParameter(1, block).getResultList()
        );
    }

    public Object[] findIdCordinatByStatus(String block, int slot, int row, int tier) {
        Object[] temp = new Object[15];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findIdCordinatByStatus").setParameter(1, block).setParameter(2, slot).setParameter(3, row).setParameter(4, tier).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public Object[] findIdCordinatByStatusPlanningEmpty(String block, int slot, int row, int tier) {
        Object[] temp = new Object[15];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findIdCordinatByStatusPlanningEmpty").setParameter(1, block).setParameter(2, slot).setParameter(3, row).setParameter(4, tier).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public MasterYardCoordinat findAvailableCoordinat(String block, int slot, int row, int tier) {
        try {
            return (MasterYardCoordinat) getEntityManager().createNamedQuery("MasterYardCoordinat.findAvailableCoordinat")
                    .setParameter("block", block)
                    .setParameter("slot", slot)
                    .setParameter("row", row)
                    .setParameter("tier", tier)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public MasterYardCoordinat findByCoordinat(String block, int slot, int row, int tier) {
        try {
            return (MasterYardCoordinat) getEntityManager().createNamedQuery("MasterYardCoordinat.findByCoordinat")
                    .setParameter("block", block)
                    .setParameter("slot", slot)
                    .setParameter("row", row)
                    .setParameter("tier", tier)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Object[] findIdCordinatByPlanning(String block, int slot, int row, int tier) {
        Object[] temp = new Object[15];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findIdCordinatByPlanning").setParameter(1, block).setParameter(2, slot).setParameter(3, row).setParameter(4, tier).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public Object[] findIdCordinatByStatusExist(String block, int slot, int row, int tier) {
        Object[] temp = new Object[15];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findIdCordinatByStatusExist").setParameter(1, block).setParameter(2, slot).setParameter(3, row).setParameter(4, tier).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }

        return temp;
    }

    public List<Object[]> findByContNo(String cont_no) {
        List<Object[]> list = new ArrayList<Object[]>();
        try {
            list = objectsDecimalsToObjectsInts(
                    (List<Object[]>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findByContNo").setParameter(1, cont_no).getResultList()
            );
        } catch (NoResultException ex) {
            list = null;
        }
        return list;
    }

    public List<Integer> findIdByContNo(String cont_no) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findIdByContNo").setParameter(1, cont_no).getResultList()
        );
    }

    public List<MasterYardCoordinat> findCoordinatesByContNo(String contNo) {
        return getEntityManager().createNamedQuery("MasterYardCoordinat.findByContNo")
                .setParameter("contNo", contNo)
                .getResultList();
    }

    public int deleteByBlock(String block) {
        return getEntityManager().createNamedQuery("MasterYardCoordinat.Native.deleteByBlock").setParameter(1, block).executeUpdate();
    }

    public int clearYardsByContNo(String contNo) {
        int result = getEntityManager().createNamedQuery("MasterYardCoordinat.clearYardsByContNo")
                .setParameter("contNo", contNo)
                .executeUpdate();
        return result;
    }

    public int revertContNoToBookingByPpkb(String noPpkb, String targetContNo) {
        String lastContBookingId = findContNoGenerate(noPpkb);
        String revertedContNo = ContainerBookingGenerator.generateId(noPpkb, lastContBookingId);

        return getEntityManager().createNamedQuery("MasterYardCoordinat.revertContNoByPpkb")
                .setParameter("noPpkb", noPpkb)
                .setParameter("revertedContNo", revertedContNo)
                .setParameter("targetContNo", targetContNo)
                .executeUpdate();
    }

    public int changeStatusToPlanningByNoPpkbAndContNo(String noPpkb, String contNo) {
        return getEntityManager().createNamedQuery("MasterYardCoordinat.changeStatusToPlanningByNoPpkbAndContNo")
                .setParameter("noPpkb", noPpkb)
                .setParameter("contNo", contNo)
                .executeUpdate();
    }
    
   
    public List<Object[]> findAllStatusByBlock(String block) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findAllStatusByBlock").setParameter(1, block).getResultList()
        );
    }

    public int deleteById(int id) {
        return getEntityManager().createNamedQuery("MasterYardCoordinat.Native.deleteById").setParameter(1, id).executeUpdate();
    }

    public List<Object[]> findGenerate(String no_ppkb, boolean is_trans,boolean isShift)  {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findGenerate").setParameter(1, no_ppkb).setParameter(2, is_trans ? "TRUE" : "FALSE").setParameter(3, isShift ? "TRUE" : "FALSE").getResultList()
        );
    }

    public String findContNoGenerate(String no_ppkb) {
        return (String) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findContNoGenerate").setParameter(1, no_ppkb).getSingleResult();
    }

    public int deleteByContNo(String cont_no) {
        return getEntityManager().createNamedQuery("MasterYardCoordinat.Native.deleteByContNo").setParameter(1, cont_no).executeUpdate();
    }

    public List<Integer> unFinishBayPlanDischargeByPPKB(String no_ppkb) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("MasterYardCoordinat.Native.unFinishBayPlanDischargeByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }
 
    public List<Integer> unFinishBayPlanDischargeByPPKBCont(String no_ppkb) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("MasterYardCoordinat.Native.unFinishBayPlanDischargeByPPKBCont").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Integer> findIdByContNoUpdatePlugging(String cont_no,String no_ppkb) {
        return decimalsToInts(
                getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findIdByContNoUpdatePlugging").setParameter(1, cont_no).setParameter(2, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findByContNoStatusExist(String contNo) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findByContNoStatusExist")
                .setParameter(1, contNo)
                .getResultList()
        );
    }

    public List<MasterYardCoordinat> findAvailableCoordinateByAllocationConstraint(String noPpkb, String portCode, Integer contSize, Integer contType, String contStatus, String grossClass, Integer overSize, Integer dg, Integer isExport) {
        List<Integer> plannedCoordinateIds = getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findAvailableCoordinateIdByAllocationConstraint")
                .setParameter(1, noPpkb)
                .setParameter(2, portCode)
                .setParameter(3, contSize)
                .setParameter(4, contType)
                .setParameter(5, contStatus)
                .setParameter(6, grossClass)
                .setParameter(7, overSize)
                .setParameter(8, dg)
                .setParameter(9, isExport)
                .getResultList();

        List<MasterYardCoordinat> result = new ArrayList<MasterYardCoordinat>();
        if (!plannedCoordinateIds.isEmpty()) {
            result = getEntityManager().createNamedQuery("MasterYardCoordinat.findByIdCollection")
                .setParameter("ids", plannedCoordinateIds)
                .getResultList();
        }

        return result;
    }

    /**
     *
     * @param originPpkb
     * @param destinationPpkb
     * @param containersAsString
     * @return
     *      0: bd.port_code,
     *      1: bd.cont_type,
     *      2: mct.name,
     *      3: bd.gross_class,
     *      4: mgc.description,
     *      5: bd.cont_size,
     *      6: bd.cont_status,
     *      7: bd.over_size,
     *      8: bd.dg,
     *      9: bd.is_export,
     *      10: required,
     *      11: allocated
     */
    public List<Object[]> findLoadPlanningAllocations(String originPpkb, String destinationPpkb, String containersAsString) {
        if (containersAsString != null) {
            /*
            String query = "SELECT bd.port_code, bd.cont_type, mct.name, bd.gross_class, mgc.description, bd.cont_size, bd.cont_status, bd.over_size, bd.dg, bd.is_export, nvl(bd.required) required, CASE WHEN bd.cont_size = 40 AND nvl(yp.allocated) > 0 THEN nvl(yp.allocated) / 2 ELSE nvl(yp.allocated) END AS allocated "
                        + "FROM "
                                + "(SELECT bd.no_ppkb, bd.disch_port_code port_code, bd.cont_type, bd.gross_class, bd.cont_size, bd.cont_status, bd.over_size, bd.dg, bd.is_export_import is_export, COUNT(*) required "
                                        + "FROM baplie_discharge bd "
                                        + "WHERE bd.cont_no IN (%s) AND bd.no_ppkb = ? "
                                        + "GROUP BY bd.no_ppkb, bd.disch_port_code, bd.cont_type, bd.gross_class, bd.cont_size, bd.cont_status, bd.over_size, bd.dg, bd.is_export_import) bd "
                                        + "LEFT JOIN (SELECT  myc.no_ppkb, pya.port_code, pya.cont_type, pya.gross_class, pya.cont_size, pya.cont_status, pya.over_size, pya.dg, pya.is_export, COUNT(*) allocated  "
                                                + "FROM m_yard_coordinat myc "
                                                        + "JOIN (SELECT pya.no_ppkb, py.block, py.fr_slot, py.to_slot, py.fr_row, py.to_row, pya.port_code, pya.cont_type, pya.gross_class, pya.cont_size, pya.cont_status, pya.over_size, pya.dg, pya.is_export "
                                                                + "FROM planning_receiving_allocation pya "
                                                                + "JOIN planning_receiving py ON (py.receiving_allocation_id=pya.id)) AS pya ON (myc.no_ppkb = pya.no_ppkb AND myc.block = pya.block AND (myc.slot BETWEEN pya.fr_slot AND pya.to_slot AND myc.row BETWEEN pya.fr_row AND pya.to_row)) "
                                                + "WHERE char_length(myc.cont_no) > 11 AND myc.status = 'planning' AND pya.no_ppkb = ? "
                                                + "GROUP BY myc.no_ppkb, pya.port_code, pya.cont_type, pya.gross_class, pya.cont_size, pya.cont_status, pya.over_size, pya.dg, pya.is_export) yp "
                                                + "ON (yp.port_code=bd.port_code AND yp.cont_type=bd.cont_type AND yp.gross_class=bd.gross_class AND yp.cont_size=bd.cont_size AND yp.cont_status=bd.cont_status AND yp.over_size=bd.over_size AND yp.dg=bd.dg AND yp.is_export=bd.is_export) "
                                        + "JOIN m_container_type mct ON (bd.cont_type=mct.cont_type) "
                                        + "JOIN m_gross_class mgc ON (mgc.gross_class=bd.gross_class) ";
            */
            //Edit from srach
            String query = "SELECT bd.port_code, bd.cont_type, mct.name, bd.gross_class, mgc.description, bd.cont_size, bd.cont_status, bd.over_size, bd.dg, bd.is_export, nvl(bd.required,0) required, CASE WHEN bd.cont_size = 40 AND nvl(yp.allocated,0) > 0 THEN nvl(yp.allocated,0) / 2 ELSE nvl(yp.allocated,0) END AS allocated "
                        + "FROM "
                                + "(SELECT bd.no_ppkb, bd.disch_port_code port_code, bd.cont_type, bd.gross_class, bd.cont_size, bd.cont_status, bd.over_size, bd.dg, bd.is_export_import is_export, COUNT(*) required "
                                        + "FROM baplie_discharge bd "
                                        + "WHERE bd.cont_no IN (%s) AND bd.no_ppkb = ? "
                                        + "GROUP BY bd.no_ppkb, bd.disch_port_code, bd.cont_type, bd.gross_class, bd.cont_size, bd.cont_status, bd.over_size, bd.dg, bd.is_export_import) bd "
                                        + "LEFT JOIN (SELECT  myc.no_ppkb, pya.port_code, pya.cont_type, pya.gross_class, pya.cont_size, pya.cont_status, pya.over_size, pya.dg, pya.is_export, COUNT(*) allocated  "
                                                + "FROM m_yard_coordinat myc "
                                                        + "JOIN (SELECT pya.no_ppkb, py.block, py.fr_slot, py.to_slot, py.fr_row, py.to_row, pya.port_code, pya.cont_type, pya.gross_class, pya.cont_size, pya.cont_status, pya.over_size, pya.dg, pya.is_export "
                                                                + "FROM planning_receiving_allocation pya "
                                                                + "JOIN planning_receiving py ON (py.receiving_allocation_id=pya.id)) pya ON (myc.no_ppkb = pya.no_ppkb AND myc.block = pya.block AND (myc.slot BETWEEN pya.fr_slot AND pya.to_slot AND myc.\"ROW\" BETWEEN pya.fr_row AND pya.to_row)) "
                                                + "WHERE length(myc.cont_no) > 11 AND myc.status = 'planning' AND pya.no_ppkb = ? "
                                                + "GROUP BY myc.no_ppkb, pya.port_code, pya.cont_type, pya.gross_class, pya.cont_size, pya.cont_status, pya.over_size, pya.dg, pya.is_export) yp "
                                                + "ON (yp.port_code=bd.port_code AND yp.cont_type=bd.cont_type AND yp.gross_class=bd.gross_class AND yp.cont_size=bd.cont_size AND yp.cont_status=bd.cont_status AND yp.over_size=bd.over_size AND yp.dg=bd.dg AND yp.is_export=bd.is_export) "
                                        + "JOIN m_container_type mct ON (bd.cont_type=mct.cont_type) "
                                        + "JOIN m_gross_class mgc ON (mgc.gross_class=bd.gross_class) ";
            query = String.format(query, containersAsString);

            return objectsDecimalsToObjectsInts(
                    getEntityManager().createNativeQuery(query)
                    .setParameter(1, originPpkb)
                    .setParameter(2, destinationPpkb)
                    .getResultList()
            );
        }

        return new ArrayList();
    }

    /**
     *
     * @param noPpkb
     * @param containersAsString
     * @param portCode
     * @return
     *      0. bd.port_code,
     *      1. bd.cont_type,
     *      2. mct.name,
     *      3. bd.gross_class,
     *      4. mgc.description,
     *      5. bd.cont_size,
     *      6. bd.cont_status,
     *      7. bd.over_size,
     *      8. bd.dg,
     *      9. bd.is_export,
     *      10. capacity,
     *      11. current_applications
     */
    public List<Object[]> findReceivingPlanningAllocations(String noPpkb, String containersAsString) {
        if (containersAsString != null) {
            String query = "SELECT bd.port_code, bd.cont_type, mct.name, bd.gross_class, mgc.description, bd.cont_size, bd.cont_status, bd.over_size, bd.dg, bd.is_export, nvl(yp.total_booking) capacity, yp.total_booking2 current_application "
                            + "FROM "
                                    + "(SELECT bd.no_ppkb, bd.disch_port port_code, bd.cont_type, bd.gross_class, bd.cont_size, bd.cont_status, bd.over_size, bd.dg, bd.is_export, COUNT(*) required "
                                            + "FROM planning_cont_receiving bd "
                                            + "WHERE bd.cont_no IN (%s) AND bd.is_generate = TRUE "
                                            + "GROUP BY bd.no_ppkb, bd.disch_port, bd.cont_type, bd.gross_class, bd.cont_size, bd.cont_status, bd.over_size, bd.dg, bd.is_export) bd "
                                            + "LEFT JOIN planning_receiving_allocation yp ON (yp.no_ppkb=bd.no_ppkb AND yp.port_code=bd.port_code AND yp.cont_type=bd.cont_type AND yp.gross_class=bd.gross_class AND yp.cont_size=bd.cont_size AND yp.cont_status=bd.cont_status AND yp.over_size=bd.over_size AND yp.dg=bd.dg AND yp.is_export=bd.is_export) "
                                    + "JOIN m_container_type mct ON (bd.cont_type=mct.cont_type) "
                                    + "JOIN m_gross_class mgc ON (mgc.gross_class=bd.gross_class) "
                            + "WHERE yp.no_ppkb= ? ";

            query = String.format(query, containersAsString);

            return objectsDecimalsToObjectsInts(
                    getEntityManager().createNativeQuery(query)
                    .setParameter(1, noPpkb)
                    .getResultList()
            );
        }

        return new ArrayList();
    }

    public Integer clearYardByPpkbStatusPlanning(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("MasterYardCoordinat.clearYardByPpkbAndStatus")
                .setParameter("noPpkb", noPpkb)
                .setParameter("status", "planning")
                .executeUpdate();
    }

    public int clearYardsByNoPpkbAndContNo(String noPpkb, String contNo, String... statusses) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("MasterYardCoordinat.clearYardsByNoPpkbAndContNo")
                .setParameter("noPpkb", noPpkb)
                .setParameter("contNo", contNo)
                .setParameter("statusses", Arrays.asList(statusses))
                .executeUpdate();
    }

    public Integer changeNoPpkbByContNo(String newPpkb, String oldPpkb, String contNo) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("MasterYardCoordinat.changeNoPpkbByContNo")
                .setParameter("newPpkb", newPpkb)
                .setParameter("oldPpkb", oldPpkb)
                .setParameter("contNo", contNo)
                .executeUpdate();
    }

    public int deleteConstraintByAllocationRange(String noPpkb, Short frSlot, Short toSlot, Short frRow, Short toRow) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("MasterYardCoordinat.deleteConstraintByAllocationRange")
                .setParameter("noPpkb", noPpkb)
                .setParameter("frSlot", frSlot)
                .setParameter("toSlot", toSlot)
                .setParameter("frRow", frRow)
                .setParameter("toRow", toRow)
                .executeUpdate();
    }

    public boolean findDuplicate(String cont_no) {
        if (getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findDuplicate").setParameter(1, cont_no).getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public Object[] checkContDuplicateWith(String contNo) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findDuplicate")
                    .setParameter(1, contNo)
                    .getSingleResult()
            );
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<java.lang.Object[]> findOverallContByBlockColors(String block) {
        return objectsDecimalsToObjectsInts(
                (List<java.lang.Object[]>) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findOverallContByBlockColors").setParameter(1, block).getResultList()
        );
    }
    
    @Override
    public Integer[] findStatistics() {
        Integer[] total = new Integer[3];
        Number stat = (Number) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findStatistics").setParameter(1, 20).getSingleResult();
        int stat1 = Integer.valueOf(stat.intValue());
        total[0] = stat1;
        
        stat = (Number) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findStatistics").setParameter(1, 40).getSingleResult();
        int stat2 = Integer.valueOf(stat.intValue());
        total[1] = stat2;
        
        stat = (Number) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.findStatistics").setParameter(1, 45).getSingleResult();
        int stat3 = Integer.valueOf(stat.intValue());
        total[2] = stat3;
        
        return total;
    }

    @Override
    public boolean findIsContainerExistInPreviosRow(String cont_no, int slot, int row, int tier, String block, String noppkb) {
        String sql = "select myc.cont_size from m_yard_coordinat myc "
                + "where cont_no='" + cont_no + "' and slot=" + slot + " and \"ROW\"=" + row
                + " and tier=" + tier + " and \"BLOCK\"='" + block + "' and no_ppkb='" + noppkb + "' and ROWNUM <= 1";

        if (getEntityManager().createNativeQuery(sql).getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public int updatePlanningVesselByContNo(String newValue, MasterPort nextPort, String oldValue, List<String> containers) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("MasterYardCoordinat.updatePlanningVesselByContNo")
                .setParameter("newValue", newValue)
                .setParameter("pod", nextPort.getPortCode())
                .setParameter("oldValue", oldValue)
                .setParameter("containers", containers)
                .executeUpdate();
    }
    
     //penambahan update master yard container ketika gate out by ade chelsea 11 april 2014
    public int updateMasterYardContainerGateOut(String noPpkb, String contNo) {
        return getEntityManager().createNamedQuery("MasterYardCoordinat.updateMasterYardContainerGateOut")
                .setParameter("noPpkb", noPpkb)
                .setParameter("contNo", contNo)
                .executeUpdate();
    }


    @Override
    public MasterYardCoordinat findByCoordinatAndStatus(String block, short slot, short row, short tier, String... status) {
        try {
            return (MasterYardCoordinat) getEntityManager().createNamedQuery("MasterYardCoordinat.findByCoordinatAndStatus")
                    .setParameter("block", block)
                    .setParameter("slot", slot)
                    .setParameter("row", row)
                    .setParameter("tier", tier)
                    .setParameter("statusses", Arrays.asList(status))
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public boolean isBottomAvailable(String block, short slot, short row, short tier, short teus) {
        try {
            return ((String) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.isBottomAvailable")
                    .setParameter(1, block)
                    .setParameter(2, slot)
                    .setParameter(3, row)
                    .setParameter(4, tier)
                    .setParameter(5, teus)
                    .getSingleResult()).equalsIgnoreCase("TRUE");
        } catch (NoResultException ex) {
            return false;
        }
    }

    public boolean isTopEmpty(String block, short slot, short row, short tier, short teus) {
        try {
            return ((String) getEntityManager().createNamedQuery("MasterYardCoordinat.Native.isTopEmpty")
                    .setParameter(1, block)
                    .setParameter(2, slot)
                    .setParameter(3, row)
                    .setParameter(4, tier)
                    .setParameter(5, teus)
                    .getSingleResult()).equalsIgnoreCase("TRUE");
        } catch (NoResultException ex) {
            return false;
        }
    }
    
    @Override
    public int deleteByContNoAndPpkb(String cont_no, String no_ppkb) {
        return getEntityManager().createNamedQuery("MasterYardCoordinat.Native.deleteByContNoAndPpkb").setParameter(1, cont_no).setParameter(2, no_ppkb).executeUpdate();
    }

    public List<MasterYardCoordinat> findByContNoAndStatusExist(String contNo){
        return em.createNamedQuery("MasterYardCoordinat.findByContNoAndStatusExist").setParameter("contNo", contNo).getResultList();
    }
    
    
}
