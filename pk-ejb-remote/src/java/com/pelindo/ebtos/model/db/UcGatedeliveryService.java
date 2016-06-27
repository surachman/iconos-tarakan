/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author postgres
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "uc_gatedelivery_service")
@NamedQueries({
    @NamedQuery(name = "UcGatedeliveryService.findAll", query = "SELECT u FROM UcGatedeliveryService u"),
    @NamedQuery(name = "UcGatedeliveryService.findByIdGate", query = "SELECT u FROM UcGatedeliveryService u WHERE u.idGate = :idGate"),
    @NamedQuery(name = "UcGatedeliveryService.findByJobslip", query = "SELECT u FROM UcGatedeliveryService u WHERE u.jobslip = :jobslip"),
    @NamedQuery(name = "UcGatedeliveryService.findByVehicleCode", query = "SELECT u FROM UcGatedeliveryService u WHERE u.vehicleCode = :vehicleCode"),
    @NamedQuery(name = "UcGatedeliveryService.findByVehicleWeight", query = "SELECT u FROM UcGatedeliveryService u WHERE u.vehicleWeight = :vehicleWeight"),
    @NamedQuery(name = "UcGatedeliveryService.findByWeight", query = "SELECT u FROM UcGatedeliveryService u WHERE u.weight = :weight"),
    @NamedQuery(name = "UcGatedeliveryService.findByUcWeight", query = "SELECT u FROM UcGatedeliveryService u WHERE u.ucWeight = :ucWeight"),
    @NamedQuery(name = "UcGatedeliveryService.findByDateIn", query = "SELECT u FROM UcGatedeliveryService u WHERE u.dateIn = :dateIn"),
    @NamedQuery(name = "UcGatedeliveryService.findNotDeliveredYetByJobSlip", query = "SELECT u FROM UcGatedeliveryService u WHERE u.jobslip = :jobslip AND u.dateOut IS NULL"),
    @NamedQuery(name = "UcGatedeliveryService.findByDateOut", query = "SELECT u FROM UcGatedeliveryService u WHERE u.dateOut = :dateOut")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "UcGatedeliveryService.Native.findUcGatedeliveryServiceByAutoComplete", query = "SELECT ugs.jobslip "
                                                                                                            + "FROM uc_gatedelivery_service ugs "
                                                                                                                    + "JOIN uc_delivery_service uds ON (ugs.jobslip=uds.jobslip) "
                                                                                                                    + "JOIN uncontainerized_service us ON (uds.id_uc=us.id_uc) "
                                                                                                            + "WHERE ugs.jobslip LIKE ('%'|| ? ||'%') AND ugs.date_out IS NULL AND us.is_delivery = TRUE "
                                                                                                            + "ORDER BY substring(ugs.jobslip,7,5) DESC "
                                                                                                            + "LIMIT 10;"),
    @NamedNativeQuery(name = "UcGatedeliveryService.Native.findUcGatedeliveryServiceByDateOutNull", query = "SELECT ugs.jobslip, ugs.id_gate "
                                                                                                          + "FROM uc_gatedelivery_service ugs "
                                                                                                                    + "JOIN uc_delivery_service uds ON (ugs.jobslip=uds.jobslip) "
                                                                                                                    + "JOIN uncontainerized_service us ON (uds.id_uc=us.id_uc) "
                                                                                                          + "WHERE ugs.date_out IS NULL AND us.is_delivery = TRUE AND ugs.jobslip = ?"),
    @NamedNativeQuery(name = "UcGatedeliveryService.Native.findUcGatedeliveryServiceByGate", query = "SELECT s.id_gate,i.jobslip,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing),i.valid_date from uncontainerized_service pu,m_commodity m,uc_delivery_service i,uc_gatedelivery_service s where pu.commodity=m.commodity_code AND pu.id_uc=i.id_uc AND i.jobslip=? AND i.jobslip=s.jobslip")})
public class UcGatedeliveryService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_gate", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGate;
    @Column(name = "jobslip", length = 30)
    private String jobslip;
    @Column(name = "vehicle_code", length = 10)
    private String vehicleCode;
    @Column(name = "vehicle_weight")
    private Integer vehicleWeight;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "uc_weight")
    private Integer ucWeight;
    @Column(name = "date_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateIn;
    @Column(name = "date_out")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOut;
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


    public UcGatedeliveryService() {
    }

    public UcGatedeliveryService(Integer idGate) {
        this.idGate = idGate;
    }

    public Integer getIdGate() {
        return idGate;
    }

    public void setIdGate(Integer idGate) {
        this.idGate = idGate;
    }

    public String getJobslip() {
        return jobslip;
    }

    public void setJobslip(String jobslip) {
        this.jobslip = jobslip;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public Integer getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(Integer vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getUcWeight() {
        return ucWeight;
    }

    public void setUcWeight(Integer ucWeight) {
        this.ucWeight = ucWeight;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGate != null ? idGate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UcGatedeliveryService)) {
            return false;
        }
        UcGatedeliveryService other = (UcGatedeliveryService) object;
        if ((this.idGate == null && other.idGate != null) || (this.idGate != null && !this.idGate.equals(other.idGate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.UcGatedeliveryService[idGate=" + idGate + "]";
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
}
