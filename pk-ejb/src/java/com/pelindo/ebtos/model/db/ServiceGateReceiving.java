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
@Table(name = "service_gate_receiving")
@NamedQueries({
    @NamedQuery(name = "ServiceGateReceiving.findAll", query = "SELECT s FROM ServiceGateReceiving s"),
    @NamedQuery(name = "ServiceGateReceiving.findById", query = "SELECT s FROM ServiceGateReceiving s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceGateReceiving.findByJobSlip", query = "SELECT s FROM ServiceGateReceiving s WHERE s.jobSlip = :jobSlip"),
    @NamedQuery(name = "ServiceGateReceiving.findByContNo", query = "SELECT s FROM ServiceGateReceiving s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceGateReceiving.findByNoPpkbAndContNo", query = "SELECT s FROM ServiceGateReceiving s WHERE s.noPpkb = :noPpkb AND s.contNo = :contNo ORDER BY s.createdDate DESC"),
    @NamedQuery(name = "ServiceGateReceiving.updateNoPpkb", query = "UPDATE ServiceGateReceiving s SET s.noPpkb = :newValue WHERE s.noPpkb = :oldValue"),
    @NamedQuery(name = "ServiceGateReceiving.updateNoPpkbByJobSlips", query = "UPDATE ServiceGateReceiving s SET s.noPpkb = :newValue WHERE s.noPpkb = :oldValue AND s.jobSlip IN :jobSlips"),
    @NamedQuery(name = "ServiceGateReceiving.findByContGross", query = "SELECT s FROM ServiceGateReceiving s WHERE s.contGross = :contGross"),
    @NamedQuery(name = "ServiceGateReceiving.findBySealNo", query = "SELECT s FROM ServiceGateReceiving s WHERE s.sealNo = :sealNo"),
    @NamedQuery(name = "ServiceGateReceiving.findByWeight", query = "SELECT s FROM ServiceGateReceiving s WHERE s.weight = :weight"),
    @NamedQuery(name = "ServiceGateReceiving.findByContWeight", query = "SELECT s FROM ServiceGateReceiving s WHERE s.contWeight = :contWeight"),
    @NamedQuery(name = "ServiceGateReceiving.findByDateIn", query = "SELECT s FROM ServiceGateReceiving s WHERE s.dateIn = :dateIn"),
    @NamedQuery(name = "ServiceGateReceiving.findByDateOut", query = "SELECT s FROM ServiceGateReceiving s WHERE s.dateOut = :dateOut"),
    @NamedQuery(name = "ServiceGateReceiving.findByRemark", query = "SELECT s FROM ServiceGateReceiving s WHERE s.remark = :remark")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceGateReceiving.Native.findServiceGateReceivingDateOutNull", query = "SELECT r.id,r.job_slip,r.cont_no,m.iso_code,r.no_ppkb FROM service_gate_receiving r,m_container_type m WHERE r.cont_type=m.cont_type AND r.date_out IS NULL"),
    @NamedNativeQuery(name = "ServiceGateReceiving.Native.findGateOutPassableByJobSlip", query = "SELECT r.id, r.job_slip, r.cont_no, m.iso_code, r.no_ppkb "
                                                                                                + "FROM service_gate_receiving r "
                                                                                                        + "JOIN service_receiving sr ON (r.no_ppkb=sr.no_ppkb AND r.cont_no=sr.cont_no AND sr.status_cancel_loading='FALSE') "
                                                                                                        + "JOIN m_container_type m ON (r.cont_type=m.cont_type) "
                                                                                                + "WHERE job_slip = ? AND r.date_out IS NULL"),
    @NamedNativeQuery(name = "ServiceGateReceiving.Native.findServiceGateReceivingDateOutByJobSlip", query = "SELECT r.id,r.job_slip,r.cont_no,m.iso_code,r.no_ppkb,r.seal_no FROM service_gate_receiving r,m_container_type m WHERE r.cont_type=m.cont_type AND r.job_slip = ?"),
    @NamedNativeQuery(name = "ServiceGateReceiving.Native.findGateOutPassableJobslips", query = "SELECT d.job_slip "
                                                                                                        + "FROM service_gate_receiving d "
                                                                                                                + "JOIN service_receiving sr ON (d.no_ppkb=sr.no_ppkb AND d.cont_no=sr.cont_no) "
                                                                                                        + "WHERE d.job_slip LIKE ('%'|| ? ||'%') AND d.date_out IS NULL "
                                                                                                        + "ORDER BY substring(d.job_slip,7,5) DESC"),
    @NamedNativeQuery(name = "ServiceGateReceiving.Native.findForMonitoringTruck", query = "SELECT s.vehicle_code no_polisi, v.name, 'Receiving' as status, s.date_in, s.date_out, to_char((s.date_out - s.date_in), 'HH24:MI') as jumlah_waktu FROM service_gate_receiving s, m_vehicle v WHERE s.vehicle_code = v.vehicle_code AND s.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceGateReceiving.Native.gateMonitoringPerCustomer", query = "SELECT sgr.cont_no, mc.name, mct.feet_mark, mct.type_in_general, sgr.cont_weight, sgr.date_in, rs.cont_status, sgr.vehicle_code, sgr.seal_no, mcd.name "
                                                                                            + "FROM service_gate_receiving sgr "
                                                                                                    + "JOIN planning_vessel pv ON (sgr.no_ppkb=pv.no_ppkb) "
                                                                                                            + "JOIN preservice_vessel prv ON (pv.booking_code=prv.booking_code) "
                                                                                                                    + "JOIN m_customer mc ON (prv.cust_code=mc.cust_code) "
                                                                                                    + "JOIN m_container_type mct ON (mct.cont_type=sgr.cont_type) "
                                                                                                    + "JOIN receiving_service rs ON (rs.job_slip = sgr.job_slip) "
                                                                                                    + "JOIN m_cont_damage mcd ON (sgr.cont_damage=mcd.id) "
                                                                                            + "WHERE sgr.date_in::date BETWEEN ?::date AND ?::date AND mc.cust_code = ? "
                                                                                            + "ORDER BY sgr.date_in;"),
    @NamedNativeQuery(name = "ServiceGateReceiving.Native.findByJobslipPpkb", query = "SELECT id FROM service_gate_receiving WHERE job_slip = ? AND no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceGateReceiving.Native.findByContPpkb", query = "SELECT id FROM service_gate_receiving WHERE cont_no = ? AND no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceGateReceiving.Native.findReceivingCapacityStatusByNoPpkb", query = "SELECT nvln(SUM(sgr.cont_gross)) / 1000, COUNT(CASE WHEN mct.feet_mark = 20 THEN 1 WHEN mct.feet_mark = 40 THEN 2 END) "
                                                                                                        + "FROM service_gate_receiving sgr JOIN m_container_type mct ON (sgr.cont_type=mct.cont_type) "
                                                                                                        + "WHERE sgr.no_ppkb=?")})
public class ServiceGateReceiving implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "job_slip", length = 30)
    private String jobSlip;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "weight")
    private Integer weight = 0;
    @Column(name = "cont_weight")
    private Integer contWeight;
    @Column(name = "date_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateIn;
    @Column(name = "date_out")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOut;
    @Column(name = "remark", length = 256)
    private String remark;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "cont_type")
    private Integer contType;
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


    public ServiceGateReceiving() {
    }

    public ServiceGateReceiving(Integer id) {
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

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public MasterVehicle getMasterVehicle() {
        return masterVehicle;
    }

    public void setMasterVehicle(MasterVehicle masterVehicle) {
        this.masterVehicle = masterVehicle;
    }

    public MasterContDamage getMasterContDamage() {
        return masterContDamage;
    }

    public void setMasterContDamage(MasterContDamage masterContDamage) {
        this.masterContDamage = masterContDamage;
    }

    public Integer getContType() {
        return contType;
    }

    public void setContType(Integer contType) {
        this.contType = contType;
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
        if (!(object instanceof ServiceGateReceiving)) {
            return false;
        }
        ServiceGateReceiving other = (ServiceGateReceiving) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceGateReceiving[id=" + id + "]";
    }
}
