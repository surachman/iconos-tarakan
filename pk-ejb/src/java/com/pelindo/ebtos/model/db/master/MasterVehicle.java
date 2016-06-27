/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.ServiceGateDelivery;
import com.pelindo.ebtos.model.db.ServiceGateReceiving;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author arie
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_vehicle")
@NamedQueries({
    @NamedQuery(name = "MasterVehicle.findAll", query = "SELECT m FROM MasterVehicle m"),
    @NamedQuery(name = "MasterVehicle.findByVehicleCode", query = "SELECT m FROM MasterVehicle m WHERE m.vehicleCode = :vehicleCode"),
    @NamedQuery(name = "MasterVehicle.findByTonage", query = "SELECT m FROM MasterVehicle m WHERE m.tonage = :tonage"),
    @NamedQuery(name = "MasterVehicle.findByWeightMax", query = "SELECT m FROM MasterVehicle m WHERE m.weightMax = :weightMax"),
    @NamedQuery(name = "MasterVehicle.findByCreatedBy", query = "SELECT m FROM MasterVehicle m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MasterVehicle.findByCreatedDate", query = "SELECT m FROM MasterVehicle m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MasterVehicle.findByModifiedBy", query = "SELECT m FROM MasterVehicle m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MasterVehicle.findByModifiedDate", query = "SELECT m FROM MasterVehicle m WHERE m.modifiedDate = :modifiedDate")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterVehicle.Native.findAll", query = "SELECT vehicle_code, m_vehicle_type.name as type, m_vehicle.tonage FROM m_vehicle, m_vehicle_type where m_vehicle.vehicle_type_code=m_vehicle_type.vehicle_type_code"),
/*    
    @NamedNativeQuery(name = "MasterVehicle.Native.findLikeNopol", query = "SELECT v.vehicle_code FROM m_vehicle v WHERE UPPER(v.vehicle_code) LIKE UPPER('%'|| ? ||'%') LIMIT 20"),
*/
    @NamedNativeQuery(name = "MasterVehicle.Native.findLikeNopol", query =     
"SELECT vehicle_code " 
+"FROM " 
+"  (SELECT v.vehicle_code " 
+"  FROM m_vehicle v " 
+"  WHERE UPPER(v.vehicle_code) LIKE UPPER('%' " 
+"    || ? " 
+"    ||'%') " 
+"  ) " 
+"WHERE rownum <= 20"),
    
    @NamedNativeQuery(name = "MasterVehicle.Native.findTruckMonitoringList", query = "SELECT s.vehicle_code no_polisi, t.name as type, 'Delivery' as status, s.date_in, s.date_out, to_char((s.date_out - s.date_in), 'HH24:MI') as jumlah_waktu, s.cont_no FROM service_gate_delivery s, m_vehicle v, m_vehicle_type t WHERE s.vehicle_code = v.vehicle_code AND v.vehicle_type_code=t.vehicle_type_code AND s.no_ppkb = ? "
                                                                                        + "UNION ALL "
                                                                                    + "SELECT s.vehicle_code no_polisi, t.name as type, 'Receiving' as status, s.date_in, s.date_out, to_char((s.date_out - s.date_in), 'HH24:MI') as jumlah_waktu, s.cont_no FROM service_gate_receiving s, m_vehicle v, m_vehicle_type t WHERE s.vehicle_code = v.vehicle_code AND v.vehicle_type_code=t.vehicle_type_code AND s.no_ppkb = ?")
})
public class MasterVehicle implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "vehicle_code")
    private String vehicleCode;
    @Basic(optional = false)
    @Column(name = "tonage")
    private short tonage;
    @Column(name = "weight_max")
    private Integer weightMax;
    @Basic(optional = false)
    @Column(name = "created_by")
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @JoinColumn(name = "vehicle_type_code", referencedColumnName = "vehicle_type_code")
    @ManyToOne(optional = false)
    private MasterVehicleType masterVehicleType;
    @OneToMany(mappedBy = "masterVehicle")
    private List<ServiceGateDelivery> serviceGateDeliveryList;
    @OneToMany(mappedBy = "masterVehicle")
    private List<ServiceGateReceiving> serviceGateReceivingList;

    public MasterVehicle() {
    }

    public MasterVehicle(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public MasterVehicle(String vehicleCode, short tonage, String createdBy, Date createdDate) {
        this.vehicleCode = vehicleCode;
        this.tonage = tonage;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public short getTonage() {
        return tonage;
    }

    public void setTonage(short tonage) {
        this.tonage = tonage;
    }

    public Integer getWeightMax() {
        return weightMax;
    }

    public void setWeightMax(Integer weightMax) {
        this.weightMax = weightMax;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public MasterVehicleType getMasterVehicleType() {
        return masterVehicleType;
    }

    public void setMasterVehicleType(MasterVehicleType masterVehicleType) {
        this.masterVehicleType = masterVehicleType;
    }

    public List<ServiceGateDelivery> getServiceGateDeliveryList() {
        return serviceGateDeliveryList;
    }

    public void setServiceGateDeliveryList(List<ServiceGateDelivery> serviceGateDeliveryList) {
        this.serviceGateDeliveryList = serviceGateDeliveryList;
    }

    public List<ServiceGateReceiving> getServiceGateReceivingList() {
        return serviceGateReceivingList;
    }

    public void setServiceGateReceivingList(List<ServiceGateReceiving> serviceGateReceivingList) {
        this.serviceGateReceivingList = serviceGateReceivingList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vehicleCode != null ? vehicleCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterVehicle)) {
            return false;
        }
        MasterVehicle other = (MasterVehicle) object;
        if ((this.vehicleCode == null && other.vehicleCode != null) || (this.vehicleCode != null && !this.vehicleCode.equals(other.vehicleCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterVehicle[vehicleCode=" + vehicleCode + "]";
    }

}
