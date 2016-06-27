/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "reefer_monitoring")
@NamedQueries({
    @NamedQuery(name = "ReeferMonitoring.findAll", query = "SELECT r FROM ReeferMonitoring r"),
    @NamedQuery(name = "ReeferMonitoring.findById", query = "SELECT r FROM ReeferMonitoring r WHERE r.id = :id"),
    @NamedQuery(name = "ReeferMonitoring.findByContNo", query = "SELECT r FROM ReeferMonitoring r WHERE r.contNo = :contNo"),
    @NamedQuery(name = "ReeferMonitoring.findByDateMontoring", query = "SELECT r FROM ReeferMonitoring r WHERE r.dateMontoring = :dateMontoring"),
    @NamedQuery(name = "ReeferMonitoring.findByTemperature", query = "SELECT r FROM ReeferMonitoring r WHERE r.temperature = :temperature")})
@NamedNativeQueries({
/*
    @NamedNativeQuery(name = "ReeferMonitoring.Native.findByIdReefer", query = "SELECT rm.id, rm.id_reefer, rm.cont_no, rm.date_montoring, rm.temperature FROM reefer_monitoring AS rm, service_reefer AS sr WHERE sr.id_reefer = rm.id_reefer AND sr.id_reefer = ? ORDER BY rm.id DESC"),
*/
    @NamedNativeQuery(name = "ReeferMonitoring.Native.findByIdReefer", query = 
"SELECT rm.id, " 
+"  rm.id_reefer, " 
+"  rm.cont_no, " 
+"  rm.date_montoring, " 
+"  rm.temperature " 
+"FROM reefer_monitoring rm, " 
+"  service_reefer sr " 
+"WHERE sr.id_reefer = rm.id_reefer " 
+"AND sr.id_reefer   = ? " 
+"ORDER BY rm.id DESC"),
    
    @NamedNativeQuery(name = "ReeferMonitoring.Native.findAllByIdReefer", query = "SELECT id FROM reefer_monitoring WHERE id_reefer=?"),
    @NamedNativeQuery(name = "ReeferMonitoring.Native.findByContNo", query = "SELECT * FROM reefer_monitoring WHERE cont_no=? ORDER BY ID ASC"),
    @NamedNativeQuery(name = "ReeferMonitoring.Native.findLastTemperature", query = "SELECT rm.temperature FROM reefer_monitoring rm WHERE rm.date_montoring = (SELECT MAX(date_montoring) FROM reefer_monitoring WHERE cont_no=rm.cont_no) AND rm.cont_no=?")
})
public class ReeferMonitoring implements Serializable, EntityAuditable {
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

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Basic(optional = false)
    @Column(name = "date_montoring", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateMontoring;
    @Basic(optional = false)
    @Column(name = "temperature", precision = 5, scale = 2)
    private BigDecimal temperature;
    @Column(name = "bl_no")
    private String blNo;
    @JoinColumn(name = "id_reefer", referencedColumnName = "id_reefer", nullable = false)
    @ManyToOne(optional = false)
    private ServiceReefer serviceReefer;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public ReeferMonitoring() {
    }

    public ReeferMonitoring(Integer id) {
        this.id = id;
    }

    public ReeferMonitoring(Integer id, String contNo, Date dateMontoring, BigDecimal temperature, MasterCustomer mlo) {
        this.id = id;
        this.contNo = contNo;
        this.dateMontoring = dateMontoring;
        this.temperature = temperature;
        this.mlo = mlo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public Date getDateMontoring() {
        return dateMontoring;
    }

    public void setDateMontoring(Date dateMontoring) {
        this.dateMontoring = dateMontoring;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public ServiceReefer getServiceReefer() {
        return serviceReefer;
    }

    public void setServiceReefer(ServiceReefer serviceReefer) {
        this.serviceReefer = serviceReefer;
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
        if (!(object instanceof ReeferMonitoring)) {
            return false;
        }
        ReeferMonitoring other = (ReeferMonitoring) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ReeferMonitoring[id=" + id + "]";
    }
}