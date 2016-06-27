/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.BaplieDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterYardFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningContDischargeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.PlanningVesselFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.BaplieDischargeFacadeRemote;
import com.pelindo.ebtos.model.db.BaplieDischarge;
import com.pelindo.ebtos.model.db.PlanningContDischarge;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.master.MasterYard;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author senoanggoro
 */
@Stateless
public class BaplieDischargeFacade extends AbstractFacade<BaplieDischarge> implements BaplieDischargeFacadeRemote, BaplieDischargeFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    @EJB
    private PlanningVesselFacadeLocal planningVesselFacadeLocal;
    @EJB
    private PlanningContDischargeFacadeLocal planningContDischargeFacadeLocal;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacadeLocal;
    @EJB
    private MasterYardFacadeLocal masterYardFacadeLocal;

    protected EntityManager getEntityManager() {
        return em;
    }

    public BaplieDischargeFacade() {
        super(BaplieDischarge.class);
    }

    public List<Object[]> findContainerByTypeContainer (int cont_type){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("BaplieDischarge.Native.findContainerByTypeContainer").setParameter(1, cont_type).getResultList()
        );
   }

    @Override
    public List<Object[]> findBaplieDischargesByPpkb (String no_ppkb){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("BaplieDischarge.Native.findBaplieDischargesByPpkb").setParameter(1, no_ppkb).getResultList()
        );        
    }
     public List<Object[]> findBaplieDischargesByCode (String commodity_code){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("BaplieDischarge.Native.findBaplieDischargesByCode").setParameter(1, commodity_code).getResultList()
        );
   }

   public List<Object[]> findBaplieDischargesByPpkbForCopy (String no_ppkb){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("BaplieDischarge.Native.findBaplieDischargesByPpkbForCopy").setParameter(1, no_ppkb).getResultList()
        );
   }

   public List<Object[]> findTranshipmentContainerByAllocationConstraint (String noPpkb, String portCode, Integer contSize, Integer contType, String contStatus, String grossClass, Integer overSize, Integer dg, Integer isExport, String contNoAsString){
       String query = "SELECT pbd.id,pbd.bl_no,pbd.cont_type,pbd.mlo,pbd.commodity_code,pbd.no_ppkb,pbd.cont_no,pbd.cont_size, "
                        + "pbd.cont_status,pbd.cont_gross,pbd.seal_no,pbd.dg,pbd.dg_label,pbd.over_size, "
                        + "pbd.trade_type,pbd.load_port_code,pbd.disch_port_code,pbd.v_bay,pbd.v_row, "
                        + "pbd.v_tier,pbd.gross_class,pbd.port_of_delivery,pbd.is_export_import,pbd.twenty_one_feet "
                    + "FROM baplie_discharge pbd "
                            + "LEFT JOIN planning_trans_discharge psd ON (pbd.id = psd.id) "
                            + "JOIN m_container_type mct ON (pbd.cont_type = mct.cont_type) "
                            + "JOIN m_port mpt ON (pbd.load_port_code = mpt.port_code) "
                            + "JOIN m_port mpo ON (pbd.disch_port_code = mpo.port_code) "
                            + "JOIN m_commodity mc ON (pbd.commodity_code=mc.commodity_code) "
                            + "JOIN planning_vessel pv ON (pbd.no_ppkb=pv.no_ppkb) "
                                    + "JOIN preservice_vessel pp ON (pv.booking_code=pp.booking_code) "
                    + "WHERE psd.id IS NULL AND pbd.no_ppkb = ? AND pbd.disch_port_code != (SELECT value_string FROM m_setting_app WHERE id = 'ebtos.implement_port_code') AND pbd.disch_port_code != pp.next_port_code "
                        + "AND pbd.id NOT IN (SELECT id FROM planning_trans_discharge) AND pbd.cont_size=? AND pbd.cont_type=? AND pbd.cont_status=? AND pbd.gross_class=? AND pbd.disch_port_code=? AND pbd.over_size = ? AND pbd.dg = ? AND pbd.is_export_import = ? "
                        + "AND pbd.cont_no IN (%s);";

       query = String.format(query, contNoAsString);
       
       return objectsDecimalsToObjectsInts(
               getEntityManager().createNativeQuery(query)
                .setParameter(1, noPpkb)
                .setParameter(2, contSize)
                .setParameter(3, contType)
                .setParameter(4, contStatus)
                .setParameter(5, grossClass)
                .setParameter(6, portCode)
                .setParameter(7, overSize)
                .setParameter(8, dg)
                .setParameter(9, isExport)
                .getResultList()
       );
   }

   public List<Object[]> findShiftingContainerByAllocationConstraint (String noPpkb, String portCode, Integer contSize, Integer contType, String contStatus, String grossClass, Integer overSize, Integer dg, Integer isExport, String contNoAsString){
       String query = "SELECT pbd.id,pbd.bl_no,pbd.cont_type,pbd.mlo,pbd.commodity_code,pbd.no_ppkb,pbd.cont_no,pbd.cont_size, "
                        + "pbd.cont_status,pbd.cont_gross,pbd.seal_no,pbd.dg,pbd.dg_label,pbd.over_size, "
                        + "pbd.trade_type,pbd.load_port_code,pbd.disch_port_code,pbd.v_bay,pbd.v_row, "
                        + "pbd.v_tier,pbd.gross_class,pbd.is_export_import,pbd.twenty_one_feet "
                    + "FROM baplie_discharge pbd "
                            + "LEFT JOIN planning_shift_discharge psd ON (pbd.id = psd.id) "
                            + "JOIN m_container_type mct ON (pbd.cont_type = mct.cont_type) "
                            + "JOIN m_port mpt ON (pbd.load_port_code = mpt.port_code) "
                            + "JOIN m_port mpo ON (pbd.disch_port_code = mpo.port_code) "
                            + "JOIN m_commodity mc ON (pbd.commodity_code=mc.commodity_code) "
                            + "JOIN planning_vessel pv ON (pbd.no_ppkb=pv.no_ppkb) "
                                    + "JOIN preservice_vessel pp ON (pv.booking_code=pp.booking_code) "
                    + "WHERE psd.id IS NULL AND pp.next_port_code = pbd.disch_port_code AND pbd.no_ppkb = ? "
                        + "AND pbd.cont_size=? AND pbd.cont_type=? AND pbd.cont_status=? AND pbd.gross_class=? "
                        + "AND pbd.disch_port_code=? AND pbd.over_size = ? AND pbd.dg = ? AND pbd.is_export_import = ? AND pbd.cont_no IN (%s);";
       
       query = String.format(query, contNoAsString);

       return objectsDecimalsToObjectsInts(
               getEntityManager().createNativeQuery(query)
                .setParameter(1, noPpkb)
                .setParameter(2, contSize)
                .setParameter(3, contType)
                .setParameter(4, contStatus)
                .setParameter(5, grossClass)
                .setParameter(6, portCode)
                .setParameter(7, overSize)
                .setParameter(8, dg)
                .setParameter(9, isExport)
                .getResultList()
       );
   }

   public List<BaplieDischarge> findByPpkbAndPortCode(String noPpkb, String portCode){
        return getEntityManager().createNamedQuery("BaplieDischarge.findByPpkbAndPortCode")
                .setParameter("noPpkb", noPpkb)
                .setParameter("portCode", portCode)
                .getResultList();
   }

   public List<BaplieDischarge> findByPpkbNotCompleted(String noPpkb){
        return getEntityManager().createNamedQuery("BaplieDischarge.findByPpkbNotCompleted")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
   }

   public List<Object[]> findBaplieDischargesByPPKB (String no_ppkb){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("BaplieDischarge.Native.findBaplieDischargesByPPKB").setParameter(1, no_ppkb).getResultList()
        );
   }

   public List<Object[]> findBaplieDischargesByPpkbShifting (String no_ppkb){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("BaplieDischarge.Native.findBaplieDischargesByPpkbShifting").setParameter(1, no_ppkb).getResultList()
        );
    }

   public Object[] findContOnBay(String noPpkb, int bay, int row, int tier) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("BaplieDischarge.Native.findContOnBay")
                    .setParameter(1, noPpkb)
                    .setParameter(2, bay)
                    .setParameter(3, row)
                    .setParameter(4, tier)
                    .getSingleResult()
            );
        } catch(NoResultException e) {
            return null;
        } catch (NonUniqueResultException nuse) {
            return new Object[] {
                "error", " ", "error"
            };
        }
    }

   public List<Object[]> findTheSameContainer (String cont_no){
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("BaplieDischarge.Native.findTheSameContainer").setParameter(1, cont_no).getResultList()
        );
    }

    public List<BaplieDischarge> findByNoPpkb (String noPpkb){
        return getEntityManager().createNamedQuery("BaplieDischarge.findByNoPpkb")
                .setParameter("noPpkb", noPpkb)
                .getResultList();
    }
    
    //    penambahan keterangan container status by ade chelsea tanggal 29 april 2014 13:40 jayapura
    public List<BaplieDischarge> findContainerStatus (String noPpkb){
        return getEntityManager().createNamedQuery("BaplieDischarge.Native.findContainerStatus").setParameter(1, noPpkb).getResultList();
//                .setParameter("noPpkb", noPpkb)
//                .getResultList();
    }

    public String mPort(){
    return masterSettingAppFacadeLocal.findImplementedPortCode();
    }
            
    public void finishBaplieDischarges(String noPpkb){
        String portCode = masterSettingAppFacadeLocal.findImplementedPortCode();
        List<BaplieDischarge> baplieBitung = findByPpkbAndPortCode(noPpkb, portCode);
        PlanningVessel vessel = planningVesselFacadeLocal.find(noPpkb);
        MasterYard masterYard = masterYardFacadeLocal.find("A");

        for (BaplieDischarge bd: baplieBitung) {
            PlanningContDischarge pcd = new PlanningContDischarge();
            pcd.setMasterYard(masterYard);
            pcd.setMasterContainerType(bd.getMasterContainerType());
            pcd.setMasterCommodity(bd.getMasterCommodity());
            pcd.setPlanningVessel(vessel);
            pcd.setContNo(bd.getContNo());
            pcd.setMlo(bd.getMlo());
            pcd.setContSize(bd.getContSize());
            pcd.setContStatus(bd.getContStatus());
            pcd.setContGross(bd.getContGross());
            pcd.setSealNo(bd.getSealNo());
            pcd.setDg(bd.getDg());
            pcd.setIsImport(bd.getIsExportImport());
            pcd.setDgLabel(bd.getDgLabel());
            pcd.setTwentyOneFeet(bd.getTwentyOneFeet());
            pcd.setOverSize(bd.getOverSize());
            pcd.setTradeType(bd.getTradeType());
            pcd.setLoadPort(bd.getMasterPort().getPortCode());
            pcd.setDischPort(bd.getMasterPort1().getPortCode());
            pcd.setVBay(bd.getVBay());
            pcd.setVRow(bd.getVRow());
            pcd.setVTier(bd.getVTier());
            pcd.setGrossClass(bd.getGrossClass());
            pcd.setBlNo(bd.getBlNo());
            //save data ke planning_cont_discharge
            planningContDischargeFacadeLocal.create(pcd);
        }

        //untuk men-set baplie
        vessel.setBaplieDischarge("TRUE");
        planningVesselFacadeLocal.edit(vessel);
    }

    public List<Object[]> findBaplieDischargeByPpkbPodDpc(String no_ppkb, String disch_port_code, String pod) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("BaplieDischarge.Native.findBaplieDischargeByPpkbPodDpc").setParameter(1, no_ppkb).setParameter(2, disch_port_code).setParameter(3, pod).getResultList()
        );
    }

    public List<Object[]> findBaplieDischargeByPpkbNotPod(String no_ppkb, String pod, String pod2) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("BaplieDischarge.Native.findBaplieDischargeByPpkbNotPod").setParameter(1, no_ppkb).setParameter(2, pod).setParameter(3, pod2).getResultList()
        );
    }

    public List<Object[]> findBaplieDischargeByPpkbShiftingViaCY(String no_ppkb, String pod, String pod2) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("BaplieDischarge.Native.findBaplieDischargeByPpkbShiftingViaCY").setParameter(1, no_ppkb).setParameter(2, pod).setParameter(3, pod2).getResultList()
        );
    }

    public int deleteByContNoAndNoPpkb(String contNo, String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("BaplieDischarge.deleteByContNoAndNoPpkb")
                .setParameter("contNo", contNo)
                .setParameter("noPpkb", noPpkb)
                .executeUpdate();
    }
}
