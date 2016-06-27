/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.AngkutPetikemas;
import com.pelindo.ebtos.model.db.PreserviceVessel;
import com.pelindo.ebtos.model.db.RecapJurnal;
import com.pelindo.ebtos.model.db.Registration;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_customer")
@NamedQueries({
    @NamedQuery(name = "MasterCustomer.findAll", query = "SELECT m FROM MasterCustomer m"),
    @NamedQuery(name = "MasterCustomer.findByCustCode", query = "SELECT m FROM MasterCustomer m WHERE m.custCode = :custCode"),
    @NamedQuery(name = "MasterCustomer.findByName", query = "SELECT m FROM MasterCustomer m WHERE m.name = :name"),
    @NamedQuery(name = "MasterCustomer.findByAddress", query = "SELECT m FROM MasterCustomer m WHERE m.address = :address"),
    @NamedQuery(name = "MasterCustomer.findByPhone1", query = "SELECT m FROM MasterCustomer m WHERE m.phone1 = :phone1"),
    @NamedQuery(name = "MasterCustomer.findByPhone2", query = "SELECT m FROM MasterCustomer m WHERE m.phone2 = :phone2"),
    @NamedQuery(name = "MasterCustomer.findByFax", query = "SELECT m FROM MasterCustomer m WHERE m.fax = :fax"),
    @NamedQuery(name = "MasterCustomer.findByCity", query = "SELECT m FROM MasterCustomer m WHERE m.city = :city"),
    @NamedQuery(name = "MasterCustomer.findByNpwp", query = "SELECT m FROM MasterCustomer m WHERE m.npwp = :npwp"),
    @NamedQuery(name = "MasterCustomer.findByFlagImgUrl", query = "SELECT m FROM MasterCustomer m WHERE m.flagImgUrl = :flagImgUrl"),
    @NamedQuery(name = "MasterCustomer.findByCreatedBy", query = "SELECT m FROM MasterCustomer m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MasterCustomer.findByCreatedDate", query = "SELECT m FROM MasterCustomer m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MasterCustomer.findByModifiedBy", query = "SELECT m FROM MasterCustomer m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MasterCustomer.findByModifiedDate", query = "SELECT m FROM MasterCustomer m WHERE m.modifiedDate = :modifiedDate")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterCustomer.Native.findMasterCustomers",query="SELECT p.cust_code,p.name,p.phone1,p.address FROM m_customer p"),
/*
    @NamedNativeQuery(name = "MasterCustomer.Native.findByName", query = "SELECT p.cust_code, p.name || ' ' || p.cust_code  FROM m_customer p WHERE p.name || ' ' || p.cust_code LIKE ('%' || UPPER(?) || '%') LIMIT 10"),
*/
    @NamedNativeQuery(name = "MasterCustomer.Native.findByName", query = 
"SELECT p.cust_code, " 
+"  p.name " 
+"  || ' ' " 
+"  || p.cust_code " 
+"FROM m_customer p " 
+"WHERE p.name " 
+"  || ' ' " 
+"  || p.cust_code LIKE ('%' " 
+"  || UPPER(?) " 
+"  || '%') " 
+"AND ROWNUM < 11"),
    
    @NamedNativeQuery(name = "MasterCustomer.Native.findMasterCustomerByCustCode",query="SELECT p.cust_code,p.name,p.phone1,p.address FROM m_customer p WHERE p.cust_code = ?"),
    @NamedNativeQuery(name = "MasterCustomer.Native.findAllMasterCustomerByDelete",query="SELECT v.cust_type_id FROM m_customer v, m_cust_type p WHERE v.cust_type_id = p.cust_type_id AND v.cust_type_id = ?"),
    @NamedNativeQuery(name = "MasterCustomer.Native.findDetailAgen",query="SELECT name, address, city, phone1, phone2, fax, npwp, flag_img_url FROM m_customer WHERE cust_code = ?")
})

public class MasterCustomer implements Serializable, EntityAuditable{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cust_code", nullable = false, length = 10)
    private String custCode;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    @Column(name = "address", length = 256)
    private String address;
    @Column(name = "phone1", length = 15)
    private String phone1;
    @Column(name = "phone2", length = 15)
    private String phone2;
    @Column(name = "fax", length = 15)
    private String fax;
    @Column(name = "city", length = 50)
    private String city;
    @Column(name = "npwp", length = 10)
    private String npwp;
    @Column(name = "flag_img_url", length = 256)
    private String flagImgUrl;
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
    @OneToMany(mappedBy = "masterCustomer")
    private List<MasterDiscount> masterDiscountList;
    @JoinColumn(name = "cust_type_id", referencedColumnName = "cust_type_id")
    @ManyToOne
    private MasterCustType masterCustType;
    @JoinColumn(name = "country_code", referencedColumnName = "country_code")
    @ManyToOne
    private MasterCountry masterCountry;
    @OneToMany(mappedBy = "masterCustomer")
    private List<MasterVessel> masterVesselList;
    @OneToMany(mappedBy = "masterCustomer")
    private List<PreserviceVessel> preserviceVesselList;
    @OneToMany(mappedBy = "masterCustomer")
    private List<Registration> registrationList;
    @OneToMany(mappedBy = "masterCustomer")
    private List<RecapJurnal> recapJurnalList;
    @OneToMany(mappedBy = "masterCustomer")
    private List<AngkutPetikemas> angkutPetikemasList;
    @Column(name = "kode_status_pajak", length = 3)
    private String kodeStatusPajak = "Ya";

    public MasterCustomer() {
    }

    public MasterCustomer(String custCode) {
        this.custCode = custCode;
    }

    public MasterCustomer(String custCode, String name, String createdBy, Date createdDate) {
        this.custCode = custCode;
        this.name = name;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getFlagImgUrl() {
        return flagImgUrl;
    }

    public void setFlagImgUrl(String flagImgUrl) {
        this.flagImgUrl = flagImgUrl;
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

    public List<MasterDiscount> getMasterDiscountList() {
        return masterDiscountList;
    }

    public void setMasterDiscountList(List<MasterDiscount> masterDiscountList) {
        this.masterDiscountList = masterDiscountList;
    }

    public MasterCustType getMasterCustType() {
        return masterCustType;
    }

    public void setMasterCustType(MasterCustType masterCustType) {
        this.masterCustType = masterCustType;
    }

    public MasterCountry getMasterCountry() {
        return masterCountry;
    }

    public void setMasterCountry(MasterCountry masterCountry) {
        this.masterCountry = masterCountry;
    }

    public List<MasterVessel> getMasterVesselList() {
        return masterVesselList;
    }

    public void setMasterVesselList(List<MasterVessel> masterVesselList) {
        this.masterVesselList = masterVesselList;
    }

    public List<PreserviceVessel> getPreserviceVesselList() {
        return preserviceVesselList;
    }

    public void setPreserviceVesselList(List<PreserviceVessel> preserviceVesselList) {
        this.preserviceVesselList = preserviceVesselList;
    }

    public List<Registration> getRegistrationList() {
        return registrationList;
    }

    public void setRegistrationList(List<Registration> registrationList) {
        this.registrationList = registrationList;
    }

    public List<RecapJurnal> getRecapJurnalList() {
        return recapJurnalList;
    }

    public void setRecapJurnalList(List<RecapJurnal> recapJurnalList) {
        this.recapJurnalList = recapJurnalList;
    }

    public List<AngkutPetikemas> getAngkutPetikemasList() {
        return angkutPetikemasList;
    }

    public void setAngkutPetikemasList(List<AngkutPetikemas> angkutPetikemasList) {
        this.angkutPetikemasList = angkutPetikemasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (custCode != null ? custCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterCustomer)) {
            return false;
        }
        MasterCustomer other = (MasterCustomer) object;
        if ((this.custCode == null && other.custCode != null) || (this.custCode != null && !this.custCode.equals(other.custCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterCustomer[custCode=" + custCode + "]";
    }

    public String getKodeStatusPajak() {
        return kodeStatusPajak;
    }

    public void setKodeStatusPajak(String kodeStatusPajak) {
        this.kodeStatusPajak = kodeStatusPajak;
    }

}
