/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.ServiceReefer;
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
@Table(name = "m_plugging_reefer")
@NamedQueries({
    @NamedQuery(name = "MasterPluggingReefer.findAll", query = "SELECT m FROM MasterPluggingReefer m"),
    @NamedQuery(name = "MasterPluggingReefer.findByPluggingCode", query = "SELECT m FROM MasterPluggingReefer m WHERE m.pluggingCode = :pluggingCode"),
    @NamedQuery(name = "MasterPluggingReefer.findByName", query = "SELECT m FROM MasterPluggingReefer m WHERE m.name = :name")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterPluggingReefer.Native.findNotInServiceReefer", query = "SELECT mpr.plugging_code FROM m_plugging_reefer mpr WHERE mpr.availability = 'TRUE'"),
/*
    @NamedNativeQuery(name = "MasterPluggingReefer.Native.findPluggingCode", query = "SELECT plugging_code FROM m_plugging_reefer WHERE availability = TRUE ORDER BY plugging_code"),
*/    
    @NamedNativeQuery(name = "MasterPluggingReefer.Native.findPluggingCode", query =     
"SELECT plugging_code " 
+"FROM m_plugging_reefer " 
+"WHERE availability = 'TRUE' " 
+"ORDER BY plugging_code"),    
/*
    @NamedNativeQuery(name = "MasterPluggingReefer.Native.findPluggingCodeList", query = "select mp.plugging_code,mp.name,change(mp.availability) from m_plugging_reefer mp where mp.plugging_code !='NULL'")
*/
    @NamedNativeQuery(name = "MasterPluggingReefer.Native.findPluggingCodeList", query = 
"SELECT mp.plugging_code, " 
+"  mp.name, " 
+"  change(mp.availability) " 
+"FROM m_plugging_reefer mp " 
+"WHERE mp.plugging_code IS NOT NULL")    
})
public class MasterPluggingReefer implements Serializable, EntityAuditable {
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
    @Column(name = "plugging_code", nullable = false, length = 5)
    private String pluggingCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    @Column(name = "availability")
    private String availability;
    @OneToMany(mappedBy = "masterPluggingReefer")
    private List<ServiceReefer> serviceReeferList;

    public MasterPluggingReefer() {
    }

    public MasterPluggingReefer(String pluggingCode) {
        this.pluggingCode = pluggingCode;
    }

    public MasterPluggingReefer(String pluggingCode, String name) {
        this.pluggingCode = pluggingCode;
        this.name = name;
    }

    public String getPluggingCode() {
        return pluggingCode;
    }

    public void setPluggingCode(String pluggingCode) {
        this.pluggingCode = pluggingCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
      
    /**
     * @return the serviceReeferList
     */
    public List<ServiceReefer> getServiceReeferList() {
        return serviceReeferList;
    }

    /**
     * @param serviceReeferList the serviceReeferList to set
     */
    public void setServiceReeferList(List<ServiceReefer> serviceReeferList) {
        this.serviceReeferList = serviceReeferList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pluggingCode != null ? pluggingCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterPluggingReefer)) {
            return false;
        }
        MasterPluggingReefer other = (MasterPluggingReefer) object;
        if ((this.pluggingCode == null && other.pluggingCode != null) || (this.pluggingCode != null && !this.pluggingCode.equals(other.pluggingCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterPluggingReefer[pluggingCode=" + pluggingCode + "]";
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
