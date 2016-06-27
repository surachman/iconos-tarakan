/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
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
@Table(name = "service_sewa_alat")
@NamedQueries({
    @NamedQuery(name = "ServiceSewaAlat.findAll", query = "SELECT s FROM ServiceSewaAlat s"),
    @NamedQuery(name = "ServiceSewaAlat.findById", query = "SELECT s FROM ServiceSewaAlat s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceSewaAlat.findByNoReg", query = "SELECT s FROM ServiceSewaAlat s WHERE s.noReg = :noReg"),
    @NamedQuery(name = "ServiceSewaAlat.findByNoSpk", query = "SELECT s FROM ServiceSewaAlat s WHERE s.noSpk = :noSpk"),
    @NamedQuery(name = "ServiceSewaAlat.findByCustCode", query = "SELECT s FROM ServiceSewaAlat s WHERE s.custCode = :custCode"),
    @NamedQuery(name = "ServiceSewaAlat.findByEquipCode", query = "SELECT s FROM ServiceSewaAlat s WHERE s.equipCode = :equipCode"),
    @NamedQuery(name = "ServiceSewaAlat.findByJenisSewa", query = "SELECT s FROM ServiceSewaAlat s WHERE s.jenisSewa = :jenisSewa"),
    @NamedQuery(name = "ServiceSewaAlat.findByTarifCont", query = "SELECT s FROM ServiceSewaAlat s WHERE s.tarifCont = :tarifCont"),
    @NamedQuery(name = "ServiceSewaAlat.findByContSize", query = "SELECT s FROM ServiceSewaAlat s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceSewaAlat.findByContStatus", query = "SELECT s FROM ServiceSewaAlat s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceSewaAlat.findByJmlCont", query = "SELECT s FROM ServiceSewaAlat s WHERE s.jmlCont = :jmlCont"),
    @NamedQuery(name = "ServiceSewaAlat.findByTotalCharge", query = "SELECT s FROM ServiceSewaAlat s WHERE s.totalCharge = :totalCharge"),
    @NamedQuery(name = "ServiceSewaAlat.findByEstJam", query = "SELECT s FROM ServiceSewaAlat s WHERE s.estJam = :estJam")})

     @NamedNativeQueries({
    @NamedNativeQuery(name ="ServiceSewaAlat.Native.findAllSewaAlat", query = "select sa.id,sa.no_spk,sa.jenis_sewa,sa.est_jam,sa.tarif_cont,sa.cont_status,sa.cont_size,sa.jml_cont,sa.total_charge,me.name,mc.name from service_sewa_alat sa,m_equipment me,m_customer mc where sa.equip_code=me.equip_code AND sa.cust_code=mc.cust_code ORDER BY sa.id DESC")})

public class ServiceSewaAlat implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "no_reg", length = 2147483647)
    private String noReg;
    @Basic(optional = false)
    @Column(name = "no_spk", nullable = false, length = 2147483647)
    private String noSpk;
    @Basic(optional = false)
    @Column(name = "cust_code", nullable = false, length = 2147483647)
    private String custCode;
    @Column(name = "equip_code", length = 2147483647)
    private String equipCode;
    @Basic(optional = false)
    @Column(name = "jenis_sewa", nullable = false, length = 5)
    private String jenisSewa;
    @Column(name = "tarif_cont", precision = 19, scale = 2)
    private BigDecimal tarifCont;
    @Column(name = "cont_size")
    private Integer contSize;
    @Column(name = "cont_status", length = 2147483647)
    private String contStatus;
    @Column(name = "jml_cont")
    private Integer jmlCont;
    @Column(name = "total_charge", precision = 19, scale = 2)
    private BigDecimal totalCharge;
    @Column(name = "est_jam")
    private Integer estJam;
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

    public ServiceSewaAlat() {
    }

    public ServiceSewaAlat(Integer id) {
        this.id = id;
    }

    public ServiceSewaAlat(Integer id, String noSpk, String custCode, String jenisSewa) {
        this.id = id;
        this.noSpk = noSpk;
        this.custCode = custCode;
        this.jenisSewa = jenisSewa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public String getNoSpk() {
        return noSpk;
    }

    public void setNoSpk(String noSpk) {
        this.noSpk = noSpk;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    public String getJenisSewa() {
        return jenisSewa;
    }

    public void setJenisSewa(String jenisSewa) {
        this.jenisSewa = jenisSewa;
    }

    public BigDecimal getTarifCont() {
        return tarifCont;
    }

    public void setTarifCont(BigDecimal tarifCont) {
        this.tarifCont = tarifCont;
    }

    public Integer getContSize() {
        return contSize;
    }

    public void setContSize(Integer contSize) {
        this.contSize = contSize;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public Integer getJmlCont() {
        return jmlCont;
    }

    public void setJmlCont(Integer jmlCont) {
        this.jmlCont = jmlCont;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Integer getEstJam() {
        return estJam;
    }

    public void setEstJam(Integer estJam) {
        this.estJam = estJam;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
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



    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiceSewaAlat)) {
            return false;
        }
        ServiceSewaAlat other = (ServiceSewaAlat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceSewaAlat[id=" + id + "]";
    }

}
