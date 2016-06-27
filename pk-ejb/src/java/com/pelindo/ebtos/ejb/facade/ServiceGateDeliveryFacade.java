/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ServiceGateDeliveryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.ServiceGateDeliveryFacadeRemote;
import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class ServiceGateDeliveryFacade extends AbstractFacade<ServiceGateDelivery> implements ServiceGateDeliveryFacadeRemote, ServiceGateDeliveryFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceGateDeliveryFacade() {
        super(ServiceGateDelivery.class);
    }

    public List<Object[]> findServiceGateDeliveryDateOutNull() {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceGateDelivery.Native.findServiceGateDeliveryDateOutNull").getResultList()
        );
    }
    
    public List<String> findGateInPassableJobSlips(String jobSlip) {
        return getEntityManager().createNamedQuery("ServiceGateDelivery.Native.findGateInPassableJobSlips")
                .setParameter(1, jobSlip)
                .getResultList();
    }

    /**
     *
     * @param job_slip
     * @return
     *      0. id
     *      1. job_slip
     *      2. cont_no
     *      3. iso_code
     *      4. no_ppkb
     *      5. seal_no
     *      6. service_type
     */
    public Object[] findGateOutPassableByJobSlip(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceGateDelivery.Native.findGateOutPassableByJobSlip").setParameter(1, job_slip).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }
    //penambahan function untuk perubahan pencarian dari jobslip ke nomor container by ade chelsea tanggal 21-03-2014
    public Object[] findGateOutPassableByJobSlip2(String cont_no) {
        Object[] temp = new Object[6];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceGateDelivery.Native.findGateOutPassableByJobSlip2").setParameter(1, cont_no).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public Object[] findServiceGateDeliveryDateOutByJobSlip(String job_slip) {
        Object[] temp = new Object[6];
        try {
            temp = objectDecimalsToObjectInts(
                    (Object[]) getEntityManager().createNamedQuery("ServiceGateDelivery.Native.findServiceGateDeliveryDateOutByJobSlip").setParameter(1, job_slip).getSingleResult()
            );
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    public List<String> findGateOutPassableJobslips(String jobslip) {
        return getEntityManager().createNamedQuery("ServiceGateDelivery.Native.findGateOutPassableJobslips").setParameter(1, jobslip).getResultList();
    }
    //penambahan function untuk perubahan pencarian dari jobslip ke nomor container by ade chelsea tanggal 21-03-2014
    public List<String> findGateOutPassableJobslips2(String cont_no) {
        return getEntityManager().createNamedQuery("ServiceGateDelivery.Native.findGateOutPassableJobslips2").setParameter(1, cont_no).getResultList();
    }

    public List<Object[]> findForMonitoringTruck(String no_ppkb) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceGateDelivery.Native.findForMonitoringTruck").setParameter(1, no_ppkb).getResultList()
        );
    }

    public Object findByContAndPpkb(String cont_no, String no_ppkb) {
        Object temp = new Object();
        try {
            temp = (Object) getEntityManager().createNamedQuery("ServiceGateDelivery.Native.findByContAndPpkb").setParameter(1, cont_no).setParameter(2, no_ppkb).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

     public String findByContAndNoreg(String jobSlip) {
        String temp = null;
        try {
            temp = (String) getEntityManager().createNamedQuery("ServiceGateDelivery.Native.findByContAndNoreg")
                    .setParameter(1, jobSlip).getSingleResult();
        } catch (NoResultException ex) {
            temp = null;
        }
        return temp;
    }

    @Override
    public ServiceGateDelivery findByContPpkb(String contNo, String noPpkb) {
        try {
            return (ServiceGateDelivery) getEntityManager().createNamedQuery("ServiceGateDelivery.findByContAndPpkb")
                    .setParameter("contNo", contNo)
                    .setParameter("noPpkb", noPpkb)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public ServiceGateDelivery findNotDeliveredYetByJobSlip(String jobSlip) {
        try {
            return (ServiceGateDelivery) getEntityManager().createNamedQuery("ServiceGateDelivery.findNotDeliveredYetByJobSlip")
                    .setParameter("jobSlip", jobSlip)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Object[]> gateMonitoringPerCustomer(Date from, Date to, String custCode) {
        return objectsDecimalsToObjectsInts(
                getEntityManager().createNamedQuery("ServiceGateDelivery.Native.gateMonitoringPerCustomer")
                .setParameter(1, from)
                .setParameter(2, to)
                .setParameter(3, custCode)
                .getResultList()
        );
    }

    @Override
    public List<Object[]> findGateActivityMonitoring(Date startDate, Date endDate) {
        String sql = "SELECT sgr.job_slip,sgr.cont_no,rs.cont_size,mct.type_in_general,"
                + " rs.cont_status,sgr.seal_no,sgr.remark,sgr.date_in,sgr.date_out, "
                + " sgr.vehicle_code,sgr.no_ppkb, "
                + " m.name as vessel_name,pv.voy_in as voyage,mc.name as consignee, sgr.id "
                + " FROM service_gate_delivery sgr "
                + " LEFT JOIN delivery_service rs ON rs.job_slip = sgr.job_slip "
                + " LEFT JOIN m_container_type mct ON mct.cont_type = rs.cont_type "
                + " LEFT JOIN planning_vessel p ON p.no_ppkb = sgr.no_ppkb "
                + " LEFT JOIN preservice_vessel pv ON pv.booking_code = p.booking_code "
                + " LEFT JOIN m_vessel m ON m.vessel_code = pv.vessel_code "
                + " LEFT JOIN registration r ON r.no_reg = rs.no_reg "
                + " LEFT JOIN m_customer mc ON mc.cust_code = r.cust_code "
                + " WHERE sgr.date_in BETWEEN " 
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
        String sql = "SELECT sgd.job_slip,sgd.cont_no, sgd.no_ppkb,m.name,"
                + " ps.voy_in as voyage,sgd.date_in, sgd.status "
                + " FROM service_gate_delivery sgd "
                + " LEFT JOIN planning_vessel p ON p.no_ppkb = sgd.no_ppkb "
                + " LEFT JOIN preservice_vessel ps ON ps.booking_code = p.booking_code "
                + " LEFT JOIN m_vessel m ON m.vessel_code = ps.vessel_code "
                + " WHERE sgd.date_out IS NULL AND sgd.status='N'";
        try {
            return objectsDecimalsToObjectsInts(
                    em.createNativeQuery(sql).getResultList()
            );
        } catch (NoResultException nre) {
            return new ArrayList<Object[]>();
        }
    }

}
