/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceContLoadFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceContLoadFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceContLoad;
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
 * @author dycoder
 */
@Stateless
public class ServiceContLoadFacade extends AbstractFacade<ServiceContLoad> implements ServiceContLoadFacadeRemote, ServiceContLoadFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceContLoadFacade() {
        super(ServiceContLoad.class);
    }

    @Override
    public List<Object[]> findServiceContLoadsByPpkb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadsByPpkb").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContLoadsByPpkbb(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadsByPpkbb").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContLoads() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoads").getResultList()
        );
    }

    public List<Object[]> findServiceContLoadsStatusSatu(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadsStatusSatu").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContLoadss(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadss").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContLoadByPpkb2(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadByPpkb2").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContLoadByPpkb2t(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadByPpkb2t").setParameter(1, no_ppkb).getResultList()
        );
    }

    //ServiceContDischarge.Native.findServiceContDischargesConfirm
    public List<Object[]> findServiceContLoadConfirm(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadConfirm").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContLoadConfirm2(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadConfirm2").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> findServiceContLoadConfirmTranshipment(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadConfirmTranshipment").setParameter(1, no_ppkb).getResultList()
        );
    }

    //ServiceContDischarge.Native.findServiceContDischargesSelect
    public List<Object[]> findServiceContLoadSelect(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadSelect").setParameter(1, no_ppkb).getResultList()
        );
    }

    public Object[] findByContNo(String cont_no, String pos) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContLoad.Native.findByContNo").setParameter(1, cont_no).setParameter(2, pos).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findByContNoPpkb(String no_ppkb, String cont_no, String pos) {
        Object[] temp = new Object[10];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContLoad.Native.findByContNoPpkb").setParameter(1, no_ppkb).setParameter(2, cont_no).setParameter(3, pos).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findLoadMonitoringsByPPKB(String no_ppkb, String pos) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findLoadMonitoringsByPPKB").setParameter(1, no_ppkb).setParameter(2, pos).getResultList()
        );
    }

    public List<Object[]> findUcLoadMonitoringsByPPKB(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findUcLoadMonitoringsByPPKB").setParameter(1, no_ppkb).getResultList()
        );
    }

    public Object[] findServiceContLoadContNo(String no_ppkb, String cont_no) {
        Object[] temp = new Object[2];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadContNo").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findServiceContLoadByRekap(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadByRekap").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }
    //ServiceContLoad.Native.findServiceContLoadContTranshipmentRecap

    public List<Object[]> findServiceContLoadContTranshipmentRecap(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadContTranshipmentRecap").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<Object[]> findServiceContLoadContByEndService(String no_ppkb) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = objectsDecimalsToObjectsInts(
                    getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadContByEndService").setParameter(1, no_ppkb).getResultList()
            );
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public List<String> findServiceContLoadByAutoComplete(String no_ppkb, String cont_no) {
        return getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadByAutoComplete").setParameter(1, no_ppkb).setParameter(2, cont_no).getResultList();
    }

    public List<Object[]> findServiceContLoadByDownloadExcel(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadByDownloadExcel").setParameter(1, no_ppkb).getResultList()
        );
    }

    public Object[] findByPpkbAndContNo(String no_ppkb, String cont_no) {
        Object[] temp = new Object[2];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContLoad.Native.findByPpkbAndContNo").setParameter(1, no_ppkb).setParameter(2, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public ServiceContLoad findCancelableContainerByContNoAndNoPpkb(String noPpkb, String contNo) {
        try {
            return (ServiceContLoad) getEntityManager().createNamedQuery("ServiceContLoad.findCancelableContainerByContNoAndNoPpkb")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceContLoad findByNoPpkbAndContNo(String noPpkb, String contNo) {
        try {
            return (ServiceContLoad) getEntityManager().createNamedQuery("ServiceContLoad.findByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public ServiceContLoad findByNoPpkbContNoAndStatusCancelLoading(String noPpkb, String contNo, String statusCancelLoading) {
        try {
            return (ServiceContLoad) getEntityManager().createNamedQuery("ServiceContLoad.findByNoPpkbContNoAndStatusCancelLoading")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("statusCancelLoading", statusCancelLoading)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<ServiceContLoad> findContainersThatHaveNotBeenLoaded(String noPpkb) {
        List<String> positionExceptions = Arrays.asList("02", "03");

        return getEntityManager().createNamedQuery("ServiceContLoad.findByPpkbPositionsAndNotCancelLoading")
                .setParameter("noPpkb", noPpkb)
                .setParameter("positions", positionExceptions)
                .getResultList();
    }

    public List<ServiceContLoad> findContainersThatHaveBeenLoaded(String noPpkb) {
        return getEntityManager().createNamedQuery("ServiceContLoad.findByPpkbPositionAndNotCancelLoading")
                .setParameter("noPpkb", noPpkb)
                .setParameter("position", "01")
                .getResultList();
    }

    /**
     *
     * @param noPpkb
     * @return
     *      0 = cont_no,
     *      1 = bl_no,
     *      2 = mlo,
     *      3 = disch_port,
     *      4 = port_of_delivery,
     *      5 = new_iso_code,
     *      6 = cont_size,
     *      7 = cont_status,
     *      8 = cont_gross,
     *      9 = seal_no,
     *      10 = over_size,
     *      11 = dangerous,
     *      12 = dg_label,
     *      13 = block,
     *      14 = y_slot,
     *      15 = y_row,
     *      16 = y_tier,
     *      17 = v_bay,
     *      18 = v_row,
     *      19 = v_tier,
     *      20 = commodity_code,
     *      21 = no_ppkb,
     *      22 = crane,
     *      23 = is_transhipment,
     *      24 = transaction_date
     */
    public List<Object[]> findContainersThatHaveBeenLoadedAsObjectArray(String noPpkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findContainersThatHaveBeenLoaded")
                .setParameter(1, noPpkb)
                .getResultList()
        );
    }

    public ServiceContLoad findByContNoAndPosition(String noPpkb, String contNo, String position) {
        try {
            return (ServiceContLoad) getEntityManager().createNamedQuery("ServiceContLoad.findByContNoAndPosition")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setParameter("position", position)
                    .getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    public int removeMasterActivityThatHaveBeenLoaded(String noPpkb) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ServiceContLoad.updateMasterActivityByPpkbPositionAndNotCancelLoading")
                .setParameter("masterActivity", null)
                .setParameter("noPpkb", noPpkb)
                .setParameter("position", "01")
                .executeUpdate();
    }
    
        public List<Object[]> findServiceContLoadsStuffingService(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceContLoad.Native.findServiceContLoadsStuffingService").setParameter(1, no_ppkb).getResultList()
        );
    }

    /**
     *
     * @param noPpkb
     * @param contNo
     * @return
     *      0 = scl.id,
     *      1 = scl.cont_no,
     *      2 = scl.bl_no,
     *      3 = scl.cont_gross,
     *      4 = scl.cont_size,
     *      5 = scl.cont_status,
     *      6 = scl.dangerous,
     *      7 = scl.dg_label,
     *      8 = scl.cont_type,
     *      9 = scl.commodity_code,
     *      10 = scl.mlo,
     *      11 = scl.seal_no,
     *      12 = scl.is_export_import,
     *      13 = scl.v_bay,
     *      14 = scl.v_row,
     *      15 = scl.v_tier
     *      16 = scl.over_size
     *      17 = isoCode
     */
    @Override
    public Object[] findShiftableContainer(String noPpkb, String contNo) {
        try {
            return objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceContLoad.Native.findShiftableContainer")
                    .setParameter(1, noPpkb)
                    .setParameter(2, contNo)
                    .getSingleResult()
            );
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<ServiceContLoad> findServiceContLoadLoadConfirm(String noPpkb, short bay) {
        String sql = "select scl.id, scl.v_row, scl.v_tier from service_cont_load scl "
                + "where scl.position='01' and status_cancel_loading=false "
                + "and scl.no_ppkb='" + noPpkb + "' and scl.v_bay=" + bay;
        List<Object[]> dbs = getEntityManager().createNativeQuery(sql).getResultList();
        List<ServiceContLoad> objs = new ArrayList<ServiceContLoad>();
        for (int i = 0; i < dbs.size(); i++) {
            Object[] db = dbs.get(i);
            ServiceContLoad obj = new ServiceContLoad();
            obj.setId(Integer.parseInt(db[0].toString()));
            obj.setVRow(Short.parseShort(db[1].toString()));
            obj.setVTier(Short.parseShort(db[2].toString()));
            objs.add(obj);
        }
        
        return objs;
    }
    
    @Override
    public List<Object[]> findServiceContLoadByBayLocationAsObjectArray(String ppkb, int bay){
        String sql = "SELECT p.cont_no, mct.new_iso_code, p.cont_gross, p.v_bay, p.v_row, p.v_tier, "
                + " CASE WHEN obj.bay <> p.v_bay AND p.cont_size <> 20 THEN 'TRUE' ELSE 'FALSE' "
                + " END AS is_crossed, p.\"BLOCK\" || ' ' || p.y_slot || ' ' || p.y_row || ' ' || p.y_tier, "
                + " p.port_of_delivery FROM service_cont_load p "
                + " JOIN m_container_type mct ON (p.cont_type=mct.cont_type), "
                + " (SELECT " + bay + " AS bay from dual) obj WHERE p.no_ppkb = '" + ppkb + "' "
                + " and p.position='01' and p. status_cancel_loading='FALSE' "
                + " AND (p.v_bay = obj.bay OR (p.v_bay = obj.bay - 2 AND p.cont_size IN (40, 45)) OR (p.v_bay = obj.bay - 4 AND p.cont_size = 45)) "
                + " AND p.v_row IS NOT NULL AND p.v_tier IS NOT NULL";
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNativeQuery(sql).getResultList()
        );
    }
    
    @Override
    public List<Object[]> findGrossCapacityByNoPpkbAndBay(String noPpkb, Short bay, Integer minRow, Integer maxRow, Integer minTier, Integer maxTier){
        String sql = "SELECT rmn.column_value row_number, nvl(pcl.gross,0) AS gross "
                + " FROM table(generate_series(" + minRow + ", " + maxRow + ")) rmn "
                + " LEFT JOIN (SELECT pcl.v_row, SUM(pcl.cont_gross) AS gross "
                + " FROM service_cont_load pcl, (SELECT " + bay + " AS bay from dual) obj "
                + " WHERE (pcl.v_bay = obj.bay OR (pcl.v_bay = obj.bay - 2 AND pcl.cont_size = 40)) "
                + " AND pcl.no_ppkb = '" + noPpkb + "' AND pcl.v_tier BETWEEN " + minTier + " AND " + maxTier
                + " GROUP BY pcl.v_row) pcl ON (rmn.column_value=pcl.v_row) "
                + " ORDER BY row_number";
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNativeQuery(sql).getResultList()
        );
    }

    @Override
    public ServiceContLoad findByNoPpkbAndContNoUpdateLiftOn(String noPpkb, String contNo) {
        return (ServiceContLoad)getEntityManager().createNamedQuery("ServiceContLoad.findByNoPpkbAndContNo")
                .setParameter("noPpkb", noPpkb).setParameter("contNo", contNo).getSingleResult();
    }
}
