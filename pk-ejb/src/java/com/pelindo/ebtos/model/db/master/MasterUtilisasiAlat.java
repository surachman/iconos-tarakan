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
 * @author wulan
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_utilisasi_alat")
@NamedQueries({
    @NamedQuery(name = "MasterUtilisasiAlat.findAll", query = "SELECT m FROM MasterUtilisasiAlat m"),
    @NamedQuery(name = "MasterUtilisasiAlat.findById", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.id = :id"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByTanggl", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.tanggl = :tanggl"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByAvailableHour", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.availableHour = :availableHour"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByMaintenance", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.maintenance = :maintenance"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByBreakDown", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.breakDown = :breakDown"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByRepair", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.repair = :repair"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByAccident", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.accident = :accident"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByWaiting", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.waiting = :waiting"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByTotal", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.total = :total"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByCreatedDate", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByCreatedBy", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByModifiedDate", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "MasterUtilisasiAlat.findByModifiedBy", query = "SELECT m FROM MasterUtilisasiAlat m WHERE m.modifiedBy = :modifiedBy")})

    @NamedNativeQueries({
/*    
    @NamedNativeQuery(name = "MasterUtilisasiAlat.Native.findAllUtilisasiAlat", query = "select mu.id,mu.tanggl,mu.available_hour,mu.maintenance,mu.break_down,mu.repair,mu.accident,mu.waiting,me.name,mo.owner_name from m_utilisasi_alat mu,m_equipment me,m_owner_equipment mo where mu.equip_code=me.equip_code AND mo.owner_code=me.owner_code ORDER BY mu.id DESC")
*/
    @NamedNativeQuery(name = "MasterUtilisasiAlat.Native.findAllUtilisasiAlat", query = 
"SELECT mu.id, " 
+"  mu.tanggl, " 
+"  mu.available_hour, " 
+"  mu.maintenance, " 
+"  mu.repair, " 
+"  mu.accident, " 
+"  mu.waiting, " 
+"  me.name, " 
+"  mo.owner_name " 
+"FROM m_utilisasi_alat mu, " 
+"  m_equipment me, " 
+"  m_owner_equipment mo " 
+"WHERE mu.equip_code=me.equip_code " 
+"AND mo.owner_code  =me.owner_code " 
+"ORDER BY mu.id DESC")    
    })

public class MasterUtilisasiAlat implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tanggl", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggl;
    @Column(name = "available_hour", precision = 19, scale = 2)
    private BigDecimal availableHour=BigDecimal.ZERO;
    @Column(name = "maintenance", precision = 19, scale = 2)
    private BigDecimal maintenance=BigDecimal.ZERO;
    @Column(name = "break_down", precision = 19, scale = 2)
    private BigDecimal breakDown=BigDecimal.ZERO;
    @Column(name = "repair", precision = 19, scale = 2)
    private BigDecimal repair=BigDecimal.ZERO;
    @Column(name = "accident", precision = 19, scale = 2)
    private BigDecimal accident=BigDecimal.ZERO;
    @Column(name = "waiting", precision = 19, scale = 2)
    private BigDecimal waiting=BigDecimal.ZERO;
    @Column(name = "total", precision = 19, scale = 2)
    private BigDecimal total=BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "created_by", nullable = false, length = 2147483647)
    private String createdBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "modified_by", length = 2147483647)
    private String modifiedBy;
    @Column(name="equip_code")
    private String equipCode;

    public MasterUtilisasiAlat() {
    }

    public MasterUtilisasiAlat(Integer id) {
        this.id = id;
    }

    public MasterUtilisasiAlat(Integer id, Date tanggl, Date createdDate, String createdBy) {
        this.id = id;
        this.tanggl = tanggl;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTanggl() {
        return tanggl;
    }

    public void setTanggl(Date tanggl) {
        this.tanggl = tanggl;
    }

    public BigDecimal getAvailableHour() {
        return availableHour;
    }

    public void setAvailableHour(BigDecimal availableHour) {
        this.availableHour = availableHour;
    }

    public BigDecimal getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(BigDecimal maintenance) {
        this.maintenance = maintenance;
    }

    public BigDecimal getBreakDown() {
        return breakDown;
    }

    public void setBreakDown(BigDecimal breakDown) {
        this.breakDown = breakDown;
    }

    public BigDecimal getRepair() {
        return repair;
    }

    public void setRepair(BigDecimal repair) {
        this.repair = repair;
    }

    public BigDecimal getAccident() {
        return accident;
    }

    public void setAccident(BigDecimal accident) {
        this.accident = accident;
    }

    public BigDecimal getWaiting() {
        return waiting;
    }

    public void setWaiting(BigDecimal waiting) {
        this.waiting = waiting;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
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
        if (!(object instanceof MasterUtilisasiAlat)) {
            return false;
        }
        MasterUtilisasiAlat other = (MasterUtilisasiAlat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterUtilisasiAlat[id=" + id + "]";
    }

}
