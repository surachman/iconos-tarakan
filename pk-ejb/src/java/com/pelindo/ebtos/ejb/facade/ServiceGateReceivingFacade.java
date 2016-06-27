/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceGateReceivingFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateReceivingFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceGateReceiving;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ServiceGateReceivingFacade extends AbstractFacade<ServiceGateReceiving> implements ServiceGateReceivingFacadeRemote, ServiceGateReceivingFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceGateReceivingFacade() {
        super(ServiceGateReceiving.class);
    }

    public List<Object[]> findServiceGateReceivingDateOutNull() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceGateReceiving.Native.findServiceGateReceivingDateOutNull").getResultList()
        );
    }

    public Object[] findGateOutPassableByJobSlip(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceGateReceiving.Native.findGateOutPassableByJobSlip").setParameter(1, job_slip).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
    
    public ServiceGateReceiving findByNoPpkbAndContNo(java.lang.String noPpkb, java.lang.String contNo) {
        try {
            return(ServiceGateReceiving) getEntityManager().createNamedQuery("ServiceGateReceiving.findByNoPpkbAndContNo")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("contNo", contNo)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Object[] findServiceGateReceivingDateOutByJobSlip(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceGateReceiving.Native.findServiceGateReceivingDateOutByJobSlip").setParameter(1, job_slip).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<String> findGateOutPassableJobslips(String jobslip) {
        return getEntityManager().createNamedQuery("ServiceGateReceiving.Native.findGateOutPassableJobslips").setParameter(1, jobslip).getResultList();
    }

    public List<Object[]> findForMonitoringTruck(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceGateReceiving.Native.findForMonitoringTruck").setParameter(1, no_ppkb).getResultList()
        );
    }

    public List<Object[]> gateMonitoringPerCustomer(Date from, Date to, String custCode) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceGateReceiving.Native.gateMonitoringPerCustomer")
                .setParameter(1, from)
                .setParameter(2, to)
                .setParameter(3, custCode)
                .getResultList()
        );
    }

    public Object findByJobslipPpkb(String jobslip, String no_ppkb) {
        Object temp = new Object();
        try {
            temp = (Object) getEntityManager().createNamedQuery("ServiceGateReceiving.Native.findByJobslipPpkb").setParameter(1, jobslip).setParameter(2, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object findByContPpkb(String cont_no, String no_ppkb) {
        Object temp = new Object();
        try {
            temp = (Object) getEntityManager().createNamedQuery("ServiceGateReceiving.Native.findByContPpkb").setParameter(1, cont_no).setParameter(2, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findReceivingCapacityStatusByNoPpkb(String noPpkb) {
        try {
            return (Object[]) getEntityManager().createNamedQuery("ServiceGateReceiving.Native.findReceivingCapacityStatusByNoPpkb")
                    .setParameter(1, noPpkb)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return new Object[] {
                new Long("0"), new Long("0")
            };
        }
    }

    public int updateNoPpkb(String newValue, String oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ServiceGateReceiving.updateNoPpkb")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }

    public int updateNoPpkbByJobSlips(String newValue, String oldValue, List<String> jobSlips) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("ServiceGateReceiving.updateNoPpkbByJobSlips")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .setParameter("jobSlips", jobSlips)
                .executeUpdate();
    }

    @Override
    public List<Object[]> findGateActivityMonitoring(Date startDate, Date endDate) {
        System.out.println((new SimpleDateFormat("dd-MM-YYYY")).format(startDate));
        
        String sql = "SELECT sgr.job_slip,sgr.cont_no,rs.cont_size,mct.type_in_general,"
                + " rs.cont_status,sgr.seal_no,sgr.remark,sgr.date_in,sgr.date_out,"
                + " sgr.vehicle_code,sgr.no_ppkb, "
                + " m.name as vessel_name,pv.voy_in as voyage,mc.name as consignee, sgr.id "
                + " FROM service_gate_receiving sgr "
                + " LEFT JOIN receiving_service rs ON rs.job_slip = sgr.job_slip "
                + " LEFT JOIN m_container_type mct ON mct.cont_type = rs.cont_type "
                + " LEFT JOIN planning_vessel p ON p.no_ppkb = sgr.no_ppkb "
                + " LEFT JOIN preservice_vessel pv ON pv.booking_code = p.booking_code "
                + " LEFT JOIN  m_vessel m ON m.vessel_code = pv.vessel_code "
                + " LEFT JOIN registration r ON r.no_reg = rs.no_reg "
                + " LEFT JOIN m_customer mc ON mc.cust_code = r.cust_code "
                + " WHERE sgr.date_in BETWEEN + "
                + "to_date('" + (new SimpleDateFormat("dd-MM-YYYY")).format(startDate)+"','DD-MM-YYYY')" 
                + " AND " 
                + "to_date('" + (new SimpleDateFormat("dd-MM-YYYY")).format(endDate) + "','DD-MM-YYYY')";
        
        try {
            return objectsDecimalsToObjectsInts(
                    em.createNativeQuery(sql).getResultList()
            );
        } catch (NoResultException nre) {
            return new ArrayList<Object[]>();
        }
    }

    @Override
    public List<Object[]> findUnrealizedActivityMonitoring() {
        String sql = "SELECT sgr.job_slip,sgr.cont_no, sgr.no_ppkb,m.name, "
                + " ps.voy_in as voyage,sgr.date_in "
                + " FROM service_gate_receiving sgr "
                + " LEFT JOIN service_receiving sr ON (sgr.cont_no = sr.cont_no AND sgr.no_ppkb = sr.no_ppkb) "
                + " LEFT JOIN planning_vessel p ON p.no_ppkb = sgr.no_ppkb "
                + " LEFT JOIN preservice_vessel ps ON ps.booking_code = p.booking_code "
                + " LEFT JOIN m_vessel m ON m.vessel_code = ps.vessel_code "
                + " WHERE sgr.date_out is null AND sr.cont_no IS NULL "
                + " ORDER BY sgr.date_in";
        try {
            return objectsDecimalsToObjectsInts(
                    em.createNativeQuery(sql).getResultList()
            );
        } catch (NoResultException nre) {
            return new ArrayList<Object[]>();
        }
    }
}
