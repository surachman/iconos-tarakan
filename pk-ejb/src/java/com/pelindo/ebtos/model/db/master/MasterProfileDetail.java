/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
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
@Table(name = "m_profile_detail")
@NamedQueries({
    @NamedQuery(name = "MasterProfileDetail.findAll", query = "SELECT m FROM MasterProfileDetail m"),
    @NamedQuery(name = "MasterProfileDetail.findById", query = "SELECT m FROM MasterProfileDetail m WHERE m.id = :id"),
    @NamedQuery(name = "MasterProfileDetail.findByRowName", query = "SELECT m FROM MasterProfileDetail m WHERE m.rowName = :rowName"),
    @NamedQuery(name = "MasterProfileDetail.findByTierNumber", query = "SELECT m FROM MasterProfileDetail m WHERE m.tierNumber = :tierNumber")})
 @NamedNativeQueries({
    @NamedNativeQuery(name ="MasterProfileDetail.Native.findBayDetailByVessel",query="SELECT mpd.row_name, mpd.tier_number, mpd.start_tier, mvp.position "
                + "FROM m_vessel_profile mvp JOIN m_profile_detail mpd ON (mvp.profile_code=mpd.profile_code) "
                + "WHERE mvp.vessel_code=? AND mvp.bay_no=?"),
    @NamedNativeQuery(name ="MasterProfileDetail.Native.findBayDetailByPpkb",query="SELECT mpd.row_name, mpd.tier_number, mpd.start_tier, mvp.position "
                    + "FROM planning_vessel pv JOIN m_vessel_profile mvp ON (pv.vessel_code=mvp.vessel_code) JOIN m_profile_detail mpd ON (mvp.profile_code=mpd.profile_code) "
                    + "WHERE pv.no_ppkb=? AND mvp.bay_no=?"),
    @NamedNativeQuery(name ="MasterProfileDetail.Native.findAllByProfileCode",query="SELECT * FROM m_profile_detail WHERE profile_code = ? ORDER BY row_name"),
    @NamedNativeQuery(name ="MasterProfileDetail.Native.findLastOfID",query="SELECT MAX(id) FROM m_profile_detail"),
    @NamedNativeQuery(name ="MasterProfileDetail.Native.deleteByProfileCode",query="DELETE FROM m_profile_detail WHERE profile_code = ?"),
    @NamedNativeQuery(name ="MasterProfileDetail.Native.findTierByRow",query="SELECT start_tier, tier_number FROM m_profile_detail WHERE profile_code IN (SELECT profile_code FROM m_vessel_profile WHERE vessel_code=? AND position=? AND bay_no=?) AND row_name=?")
 })
public class MasterProfileDetail implements Serializable, EntityAuditable {
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
    @Column(name = "row_name")
    private Integer rowName;
    @Column(name = "tier_number")
    private Integer tierNumber;
    @Column(name = "start_tier")
    private Integer startTier;
    @JoinColumn(name = "profile_code", referencedColumnName = "profile_code")
    @ManyToOne
    private MasterVesselProfile masterVesselProfile;

    public MasterProfileDetail() {
    }

    public MasterProfileDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRowName() {
        return rowName;
    }

    public void setRowName(Integer rowName) {
        this.rowName = rowName;
    }

    public Integer getTierNumber() {
        return tierNumber;
    }

    public void setTierNumber(Integer tierNumber) {
        this.tierNumber = tierNumber;
    }

    /**
     * @return the startTier
     */
    public Integer getStartTier() {
        return startTier;
    }

    /**
     * @param startTier the startTier to set
     */
    public void setStartTier(Integer startTier) {
        this.startTier = startTier;
    }

    public MasterVesselProfile getMasterVesselProfile() {
        return masterVesselProfile;
    }

    public void setMasterVesselProfile(MasterVesselProfile masterVesselProfile) {
        this.masterVesselProfile = masterVesselProfile;
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
        if (!(object instanceof MasterProfileDetail)) {
            return false;
        }
        MasterProfileDetail other = (MasterProfileDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterProfileDetail[id=" + id + "]";
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
