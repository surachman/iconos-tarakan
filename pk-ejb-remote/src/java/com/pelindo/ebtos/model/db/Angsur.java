/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.Registration;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "angsur")
@NamedQueries({
    @NamedQuery(name = "Angsur.findAll", query = "SELECT a FROM Angsur a"),
    @NamedQuery(name = "Angsur.findById", query = "SELECT a FROM Angsur a WHERE a.id = :id"),
    @NamedQuery(name = "Angsur.findByContNo", query = "SELECT a FROM Angsur a WHERE a.contNo = :contNo"),
    @NamedQuery(name = "Angsur.findByBlNo", query = "SELECT a FROM Angsur a WHERE a.blNo = :blNo")})
public class Angsur implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "bl_no")
    private String blNo;
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
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg")
    @ManyToOne
    private Registration registration;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    /**
     * 
     */
    public Angsur() {
    }

    /**
     *
     * @param id
     */
    public Angsur(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getContNo() {
        return contNo;
    }

    /**
     *
     * @param contNo
     */
    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    /**
     *
     * @return
     */
    public String getBlNo() {
        return blNo;
    }

    /**
     *
     * @param blNo
     */
    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    /**
     *
     * @return
     */
    public Registration getRegistration() {
        return registration;
    }

    /**
     *
     * @param registration
     */
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    /**
     *
     * @return
     */
    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    /**
     *
     * @param masterActivity
     */
    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
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

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Angsur)) {
            return false;
        }
        Angsur other = (Angsur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.Angsur[id=" + id + "]";
    }

    /**
     *
     * @return
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     *
     * @param modifiedBy
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     *
     * @return
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     *
     * @param modifiedDate
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
