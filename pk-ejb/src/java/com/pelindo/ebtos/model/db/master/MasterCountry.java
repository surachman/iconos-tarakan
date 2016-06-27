/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
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
@Table(name = "m_country")
@NamedQueries({
    @NamedQuery(name = "MasterCountry.findAll", query = "SELECT m FROM MasterCountry m"),
    @NamedQuery(name = "MasterCountry.findByCountryCode", query = "SELECT m FROM MasterCountry m WHERE m.countryCode = :countryCode"),
    @NamedQuery(name = "MasterCountry.findByName", query = "SELECT m FROM MasterCountry m WHERE m.name = :name"),
    @NamedQuery(name = "MasterCountry.findByThreeletterIsoName", query = "SELECT m FROM MasterCountry m WHERE m.threeletterIsoName = :threeletterIsoName"),
    @NamedQuery(name = "MasterCountry.findByCurrencyIsoName", query = "SELECT m FROM MasterCountry m WHERE m.currencyIsoName = :currencyIsoName"),
    @NamedQuery(name = "MasterCountry.findByFlagImgUrl", query = "SELECT m FROM MasterCountry m WHERE m.flagImgUrl = :flagImgUrl")})

    @NamedNativeQueries({
    @NamedNativeQuery(name ="MasterCountry.Native.findAll", query = "SELECT m.country_code, m.name, m.threeletter_iso_name, m.currency_iso_name FROM m_country m")})
public class MasterCountry implements Serializable, EntityAuditable {
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
    @Column(name = "country_code", nullable = false, length = 5)
    private String countryCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    @Column(name = "threeletter_iso_name", length = 5)
    private String threeletterIsoName;
    @Column(name = "currency_iso_name", length = 5)
    private String currencyIsoName;
    @Column(name = "flag_img_url", length = 256)
    private String flagImgUrl;
    @OneToMany(mappedBy = "masterCountry")
    private List<MasterCustomer> masterCustomerList;
    @OneToMany(mappedBy = "masterCountry")
    private List<MasterPort> masterPortList;

    public MasterCountry() {
    }

    public MasterCountry(String countryCode) {
        this.countryCode = countryCode;
    }

    public MasterCountry(String countryCode, String name) {
        this.countryCode = countryCode;
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThreeletterIsoName() {
        return threeletterIsoName;
    }

    public void setThreeletterIsoName(String threeletterIsoName) {
        this.threeletterIsoName = threeletterIsoName;
    }

    public String getCurrencyIsoName() {
        return currencyIsoName;
    }

    public void setCurrencyIsoName(String currencyIsoName) {
        this.currencyIsoName = currencyIsoName;
    }

    public String getFlagImgUrl() {
        return flagImgUrl;
    }

    public void setFlagImgUrl(String flagImgUrl) {
        this.flagImgUrl = flagImgUrl;
    }

    public List<MasterCustomer> getMasterCustomerList() {
        return masterCustomerList;
    }

    public void setMasterCustomerList(List<MasterCustomer> masterCustomerList) {
        this.masterCustomerList = masterCustomerList;
    }

    public List<MasterPort> getMasterPortList() {
        return masterPortList;
    }

    public void setMasterPortList(List<MasterPort> masterPortList) {
        this.masterPortList = masterPortList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countryCode != null ? countryCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterCountry)) {
            return false;
        }
        MasterCountry other = (MasterCountry) object;
        if ((this.countryCode == null && other.countryCode != null) || (this.countryCode != null && !this.countryCode.equals(other.countryCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterCountry[countryCode=" + countryCode + "]";
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
