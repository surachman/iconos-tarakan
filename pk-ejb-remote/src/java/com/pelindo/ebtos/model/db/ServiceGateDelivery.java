/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterVehicle;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "service_gate_delivery")
@NamedQueries({
    @NamedQuery(name = "ServiceGateDelivery.findAll", query = "SELECT s FROM ServiceGateDelivery s"),
    @NamedQuery(name = "ServiceGateDelivery.findById", query = "SELECT s FROM ServiceGateDelivery s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceGateDelivery.findNotDeliveredYetByJobSlip", query = "SELECT s FROM ServiceGateDelivery s WHERE s.jobSlip = :jobSlip AND s.status = 'N'"),
    @NamedQuery(name = "ServiceGateDelivery.findByContAndPpkb", query = "SELECT s FROM ServiceGateDelivery s WHERE s.contNo = :contNo and s.noPpkb = :noPpkb")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceGateDelivery.Native.findServiceGateDeliveryDateOutNull", query = "SELECT r.id,r.job_slip,r.cont_no,m.iso_code,r.no_ppkb,r.seal_no FROM service_gate_delivery r,m_container_type m WHERE r.cont_type=m.cont_type AND r.date_out IS NULL"),
    @NamedNativeQuery(name = "ServiceGateDelivery.Native.findServiceGateDeliveryDateOutByJobSlip", query = "SELECT r.id,r.job_slip,r.cont_no,m.iso_code,r.no_ppkb,r.seal_no FROM service_gate_delivery r,m_container_type m WHERE r.cont_type=m.cont_type AND r.job_slip = ? AND r.status='N' AND r.cont_no NOT IN (SELECT cont_no FROM service_delivery WHERE no_ppkb = r.no_ppkb)"),
    @NamedNativeQuery(name = "ServiceGateDelivery.Native.findGateOutPassableJobslips", query = "SELECT d.job_slip "
                                                                                                        + "FROM service_gate_delivery d "
                                                                                                                + "JOIN (SELECT cont_no, no_ppkb, is_delivery FROM service_cont_discharge "
                                                                                                                        + "UNION ALL "
                                                                                                                        + "SELECT cont_no, no_ppkb, is_delivery FROM cancel_loading_service) sd ON (d.cont_no=sd.cont_no AND d.no_ppkb=sd.no_ppkb) "
                                                                                                        + "WHERE d.job_slip LIKE ('%'|| ? ||'%') AND d.date_out IS NULL AND sd.is_delivery = TRUE "
                                                                                                        + "ORDER BY SUBSTRING(d.job_slip,7,5) DESC "
                                                                                                        + "LIMIT 10"),
    @NamedNativeQuery(name = "ServiceGateDelivery.Native.findGateOutPassableByJobSlip", query = "SELECT r.id, r.job_slip, r.cont_no, m.iso_code, r.no_ppkb, r.seal_no, sd.service_type "
                                                                                                                + "FROM service_gate_delivery r "
                                                                                                                        + "JOIN m_container_type m ON (r.cont_type=m.cont_type) "
                                                                                                                        + "JOIN (SELECT cont_no, no_ppkb, is_delivery, 'DISCHARGE'::varchar service_type FROM service_cont_discharge "
                                                                                                                                + "UNION ALL "
                                                                                                                                + "SELECT cont_no, no_ppkb, is_delivery, 'CANCEL_LOADING' FROM cancel_loading_service) sd ON (r.cont_no=sd.cont_no AND r.no_ppkb=sd.no_ppkb) "
                                                                                                                + "WHERE r.job_slip = ? AND r.date_out IS NULL AND sd.is_delivery = TRUE;"),
    @NamedNativeQuery(name = "ServiceGateDelivery.Native.findForMonitoringTruck", query = "SELECT v.vehicle_code no_polisi, v.name, 'Delivery' as status, s.date_in, s.date_out, to_char((s.date_out - s.date_in), 'HH24:MI') as jumlah_waktu FROM service_gate_delivery s, m_vehicle v WHERE s.vehicle_code = v.vehicle_code AND s.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceGateDelivery.Native.gateMonitoringPerCustomer", query = "SELECT sgd.cont_no, mc.name shipping, mct.feet_mark, mct.type_in_general, sgd.cont_weight, sgd.date_out date_out_delivery, CASE WHEN ss.cont_no IS NOT NULL THEN 'STRIPPING'::varchar ELSE 'DELIVERY' END service, scd.cont_status, sgd.vehicle_code, mv.name vessel_name, prv.voy_in, prv.voy_out, sgr.date_in date_in_stripping, scd.start_placement_date, mc_rd.name "
            + "FROM service_gate_delivery sgd "
                    + "JOIN planning_vessel pv ON (sgd.no_ppkb=pv.no_ppkb) "
                            + "JOIN service_cont_discharge scd ON (scd.cont_no = sgd.cont_no AND scd.no_ppkb=sgd.no_ppkb) "
                                    + "JOIN delivery_service ds ON (scd.cont_no = ds.cont_no AND scd.no_ppkb=ds.no_ppkb) "
                                            + "JOIN registration rd ON (ds.no_reg=rd.no_reg) "
                                                    + "JOIN m_customer mc_rd ON (rd.cust_code=mc_rd.cust_code) "
                            + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                    + "JOIN m_customer mc ON (prv.cust_code=mc.cust_code) "
                            + "JOIN m_vessel mv ON (prv.vessel_code=mv.vessel_code) "
                    + "JOIN m_container_type mct ON (mct.cont_type=sgd.cont_type) "
                    + "LEFT JOIN stripping_service ss ON (ss.cont_no=sgd.cont_no) "
                            + "LEFT JOIN registration r ON (ss.no_reg=r.no_reg AND r.no_ppkb=sgd.no_ppkb) "
                            + "LEFT JOIN service_gate_receiving sgr ON (ss.job_slip=sgr.job_slip) "
            + "WHERE sgd.date_in::date BETWEEN ?::date AND ?::date AND mc.cust_code = ? "
            + "ORDER BY sgd.date_in;"),
    @NamedNativeQuery(name = "ServiceGateDelivery.Native.findByContAndPpkb", query = "SELECT id FROM service_gate_delivery WHERE cont_no = ? AND no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceGateDelivery.Native.findGateInPassableJobSlips", query = "SELECT t.job_slip\n" +
            "FROM\n" +
            "(SELECT d.job_slip, d.created_date\n" +
            "	FROM delivery_service d \n" +
            "		LEFT JOIN service_gate_delivery sgd ON (d.job_slip = sgd.job_slip)\n" +
            "	WHERE sgd.job_slip IS NULL\n" +
            "	UNION ALL\n" +
            "	SELECT scd.job_slip, scd.created_date\n" +
            "	FROM cancel_loading_service scd  \n" +
            "		LEFT JOIN service_gate_delivery sgd ON (sgd.job_slip=scd.job_slip) \n" +
            "	WHERE sgd.job_slip IS NULL AND scd.is_delivery = FALSE AND scd.pull_out = TRUE AND scd.counter_print > 0 AND ((scd.posisi='" + CancelLoadingService.AT_CY + "' AND scd.truck_losing = FALSE AND scd.is_discharge = TRUE AND (scd.category=3 OR scd.category=5)) OR (scd.posisi='" + CancelLoadingService.AT_VESSEL + "' AND scd.category=5 AND scd.is_discharge = FALSE)) ) t \n" +
            "WHERE t.job_slip LIKE ('%'|| ? ||'%') \n" +
            "ORDER BY t.created_date DESC\n" +
            "LIMIT 10;"),
    @NamedNativeQuery(name = "ServiceGateDelivery.Native.findByContAndNoreg", query = "SELECT job_slip FROM service_gate_delivery WHERE job_slip=?")})
public class ServiceGateDelivery implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "job_slip", length = 30)
    private String jobSlip;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "cont_weight")
    private Integer contWeight;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "date_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateIn;
    @Column(name = "date_out")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOut;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "cont_type")
    private Integer cont_type;
    @Column(name = "remark", length = 256)
    private String remark;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "vehicle_code", referencedColumnName = "vehicle_code")
    @ManyToOne
    private MasterVehicle masterVehicle;
    @JoinColumn(name = "cont_damage", referencedColumnName = "id")
    @ManyToOne
    private MasterContDamage masterContDamage;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Basic(optional = false)
    @Column(name = "created_by", nullable = false, length = 256)
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_by", length = 256)
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "bl_no")
    private String blNo;


    public ServiceGateDelivery() {
    }

    public ServiceGateDelivery(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public Integer getContGross() {
        return contGross;
    }

    public void setContGross(Integer contGross) {
        this.contGross = contGross;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getContWeight() {
        return contWeight;
    }

    public void setContWeight(Integer contWeight) {
        this.contWeight = contWeight;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public MasterContDamage getMasterContDamage() {
        return masterContDamage;
    }

    public void setMasterContDamage(MasterContDamage masterContDamage) {
        this.masterContDamage = masterContDamage;
    }

    public MasterVehicle getMasterVehicle() {
        return masterVehicle;
    }

    public void setMasterVehicle(MasterVehicle masterVehicle) {
        this.masterVehicle = masterVehicle;
    }

    /**
     * @return the cont_type
     */
    public Integer getCont_type() {
        return cont_type;
    }

    /**
     * @param cont_type the cont_type to set
     */
    public void setCont_type(Integer cont_type) {
        this.cont_type = cont_type;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * @return the mlo
     */
    public MasterCustomer getMlo() {
        return mlo;
    }

    /**
     * @param mlo the mlo to set
     */
    public void setMlo(MasterCustomer mlo) {
        this.mlo = mlo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiceGateDelivery)) {
            return false;
        }
        ServiceGateDelivery other = (ServiceGateDelivery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceGateDelivery[id=" + id + "]";
    }
}
