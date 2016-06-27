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
import javax.persistence.CascadeType;
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
@Table(name = "m_currency")
@NamedQueries({
    @NamedQuery(name = "MasterCurrency.findAll", query = "SELECT m FROM MasterCurrency m"),
    @NamedQuery(name = "MasterCurrency.findByCurrCode", query = "SELECT m FROM MasterCurrency m WHERE m.currCode = :currCode"),
    @NamedQuery(name = "MasterCurrency.findByMataUangId", query = "SELECT m FROM MasterCurrency m WHERE m.mataUangId = :mataUangId"),
    @NamedQuery(name = "MasterCurrency.findByName", query = "SELECT m FROM MasterCurrency m WHERE m.name = :name"),
    @NamedQuery(name = "MasterCurrency.findByTradetype", query = "SELECT m FROM MasterCurrency m WHERE m.tradeType = :tradeType")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterCurrency.Native.findSymbolByTradetype", query = "SELECT symbol FROM m_currency WHERE trade_type=?")
    })
public class MasterCurrency implements Serializable, EntityAuditable {
    public static final String USD_CURR_CODE = "USD";
    public static final String IDR_CURR_CODE = "IDR";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "curr_code", nullable = false, length = 5)
    private String currCode;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "language")
    private String language;
    @Column(name = "country")
    private String country;
    @OneToMany(mappedBy = "masterCurrency")
    private List<MasterTarifCont> masterTarifContList;
    @OneToMany(mappedBy = "masterCurrency")
    private List<MasterTarifHistory> masterTarifHistoryList;
    @Column(name = "trade_type")
    private String tradeType;
    @Column(name = "mata_uang_id", length = 3)
    private String mataUangId;
    @Basic(optional = false)
    @Column(name = "locale", nullable = false, length = 3)
    private String locale;
    @Basic(optional = false)
    @Column(name = "default_fraction_digits", nullable = false)
    private int defaultFractionDigits;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masterCurrency")
    private List<MasterMaterai> masterMateraiList;

    public MasterCurrency() {
    }

    public MasterCurrency(String currCode) {
        this.currCode = currCode;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MasterTarifCont> getMasterTarifContList() {
        return masterTarifContList;
    }

    public void setMasterTarifContList(List<MasterTarifCont> masterTarifContList) {
        this.masterTarifContList = masterTarifContList;
    }

    public List<MasterTarifHistory> getMasterTarifHistoryList() {
        return masterTarifHistoryList;
    }

    public void setMasterTarifHistoryList(List<MasterTarifHistory> masterTarifHistoryList) {
        this.masterTarifHistoryList = masterTarifHistoryList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currCode != null ? currCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterCurrency)) {
            return false;
        }
        MasterCurrency other = (MasterCurrency) object;
        if ((this.currCode == null && other.currCode != null) || (this.currCode != null && !this.currCode.equals(other.currCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterCurrency[currCode=" + currCode + "]";
    }

    /**
     * @return the simbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param simbol the simbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public List<MasterMaterai> getMasterMateraiList() {
        return masterMateraiList;
    }

    public void setMasterMateraiList(List<MasterMaterai> masterMateraiList) {
        this.masterMateraiList = masterMateraiList;
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

    public String getMataUangId() {
        return mataUangId;
    }

    public void setMataUangId(String mataUangId) {
        this.mataUangId = mataUangId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getDefaultFractionDigits() {
        return defaultFractionDigits;
    }

    public void setDefaultFractionDigits(int defaultFractionDigits) {
        this.defaultFractionDigits = defaultFractionDigits;
    }
}
