/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
 * @author dycoder
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_setting_app")
@NamedQueries({
    @NamedQuery(name = "MasterSettingApp.findAllSorted", query = "SELECT m FROM MasterSettingApp m ORDER BY m.id"),
    @NamedQuery(name = "MasterSettingApp.findById", query = "SELECT m FROM MasterSettingApp m WHERE m.id = :id"),
    @NamedQuery(name = "MasterSettingApp.findByDescription", query = "SELECT m FROM MasterSettingApp m WHERE m.description = :description"),
    @NamedQuery(name = "MasterSettingApp.findByValueString", query = "SELECT m FROM MasterSettingApp m WHERE m.valueString = :valueString"),
    @NamedQuery(name = "MasterSettingApp.findByValueInteger", query = "SELECT m FROM MasterSettingApp m WHERE m.valueInteger = :valueInteger"),
    @NamedQuery(name = "MasterSettingApp.findByValueFloat", query = "SELECT m FROM MasterSettingApp m WHERE m.valueFloat = :valueFloat"),
    @NamedQuery(name = "MasterSettingApp.findByValueDate", query = "SELECT m FROM MasterSettingApp m WHERE m.valueDate = :valueDate")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterSettingApp.Native.findCustomerSyncParameter", query = "SELECT (SELECT value_string FROM m_setting_app WHERE id = 'integration.m_customer.expression.day_of_month'), "
                                                + "(SELECT value_string FROM m_setting_app WHERE id = 'integration.m_customer.expression.hour'), "
                                                + "(SELECT value_string FROM m_setting_app WHERE id = 'integration.m_customer.expression.minute') from dual"),
    @NamedNativeQuery(name = "MasterSettingApp.Native.findImplementedPortCode", query = "select value_string from m_setting_app where id='ebtos.implement_port_code'")})
public class MasterSettingApp implements Serializable, EntityAuditable {
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
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Column(name = "value_string")
    private String valueString;
    @Column(name = "value_integer")
    private Integer valueInteger;
    @Column(name = "value_float")
    private BigDecimal valueFloat;
    @Column(name = "value_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDate;
    @Column(name = "value_boolean")
    private Boolean valueBoolean;
    @Basic(optional = false)
    @Column(name = "editable", nullable = false)
    private Integer editable;
    @Column(name = "value_nip")
    private String valueNip;
    
    public MasterSettingApp() {
    }

    public MasterSettingApp(String id) {
        this.id = id;
    }

    public MasterSettingApp(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public Integer getValueInteger() {
        return valueInteger;
    }

    public void setValueInteger(Integer valueInteger) {
        this.valueInteger = valueInteger;
    }

    public BigDecimal getValueFloat() {
        return valueFloat;
    }

    public void setValueFloat(BigDecimal valueFloat) {
        this.valueFloat = valueFloat;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public Integer getEditable() {
        return editable;
    }

    public void setEditable(Integer editable) {
        this.editable = editable;
    }

    public Boolean getValueBoolean() {
        return valueBoolean;
    }

    public void setValueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
    }
    @Override
    public String getCreatedBy() {
        return createdBy;
    }
    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    @Override
    public Date getCreatedDate() {
        return createdDate;
    }
    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getValueNip() {
        return valueNip;
    }

    public void setValueNip(String valueNip) {
        this.valueNip = valueNip;
    }
    
    
    @Override
    public String getModifiedBy() {
        return modifiedBy;
    }
    @Override
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    @Override
    public Date getModifiedDate() {
        return modifiedDate;
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
        if (!(object instanceof MasterSettingApp)) {
            return false;
        }
        MasterSettingApp other = (MasterSettingApp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MSettingApp[id=" + id + "]";
    }
    @Override
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
