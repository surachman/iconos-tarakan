/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.Registration;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
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
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_service")
@NamedQueries({
    @NamedQuery(name = "MasterService.findAll", query = "SELECT m FROM MasterService m"),
    @NamedQuery(name = "MasterService.findByServiceCode", query = "SELECT m FROM MasterService m WHERE m.serviceCode = :serviceCode"),
    @NamedQuery(name = "MasterService.findByServiceName", query = "SELECT m FROM MasterService m WHERE m.serviceName = :serviceName")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterService.Native.findMasterServices", query = "SELECT m.service_code,m.service_name FROM m_service m ORDER BY m.service_code ASC")})
public class MasterService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    public static String RECEIVING_SERVICE_CODE = "SC001";
    public static String DELIVERY_SERVICE_CODE = "SC002";
    public static String CANCEL_LOADING_SERVICE_CODE = "SC005";
    public static String RECEIVING_UC_SERVICE_CODE = "SC013";
    public static String DELIVERY_UC_SERVICE_CODE = "SC014";

    @Id
    @Basic(optional = false)
    @Column(name = "service_code", nullable = false, length = 10)
    private String serviceCode;
    @Column(name = "service_name", length = 50)
    private String serviceName;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "masterService")
    private List<Registration> registrationList;
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

    public MasterService() {
    }

    public MasterService(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<Registration> getRegistrationList() {
        return registrationList;
    }

    public void setRegistrationList(List<Registration> registrationList) {
        this.registrationList = registrationList;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceCode != null ? serviceCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterService)) {
            return false;
        }
        MasterService other = (MasterService) object;
        if ((this.serviceCode == null && other.serviceCode != null) || (this.serviceCode != null && !this.serviceCode.equals(other.serviceCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterService[serviceCode=" + serviceCode + "]";
    }

}
