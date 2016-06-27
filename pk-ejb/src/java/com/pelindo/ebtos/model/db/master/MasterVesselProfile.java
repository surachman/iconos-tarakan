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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "m_vessel_profile")
@NamedQueries({
    @NamedQuery(name = "MasterVesselProfile.findAll", query = "SELECT m FROM MasterVesselProfile m"),
    @NamedQuery(name = "MasterVesselProfile.findByProfileCode", query = "SELECT m FROM MasterVesselProfile m WHERE m.profileCode = :profileCode"),
    @NamedQuery(name = "MasterVesselProfile.findByBayNo", query = "SELECT m FROM MasterVesselProfile m WHERE m.bayNo = :bayNo"),
    @NamedQuery(name = "MasterVesselProfile.findByPosition", query = "SELECT m FROM MasterVesselProfile m WHERE m.position = :position"),
    @NamedQuery(name = "MasterVesselProfile.findByRowNumber", query = "SELECT m FROM MasterVesselProfile m WHERE m.rowNumber = :rowNumber"),
    @NamedQuery(name = "MasterVesselProfile.findByStartRow", query = "SELECT m FROM MasterVesselProfile m WHERE m.startRow = :startRow")})
 @NamedNativeQueries({
    @NamedNativeQuery(name ="MasterVesselProfile.Native.findBayIdentityByVessel",query="SELECT mvp.position, MAX(mpd.tier_number) AS tier, MAX(mvp.row_number) AS \"row\", MIN(mvp.start_row) AS start_row, MIN(mpd.start_tier) AS start_tier "
                    + "FROM m_vessel_profile mvp JOIN m_profile_detail mpd ON (mvp.profile_code=mpd.profile_code) "
                    + "WHERE mvp.vessel_code=? AND mvp.bay_no=? "
                    + "GROUP BY mvp.position, mvp.start_row "
                    + "ORDER BY mvp.position"),
    @NamedNativeQuery(name ="MasterVesselProfile.Native.findBayIdentityByPpkb",query="SELECT mvp.position, MAX(mpd.tier_number) AS tier, MAX(mvp.row_number) AS \"row\", MIN(mvp.start_row) AS start_row, MIN(mpd.start_tier) AS start_tier "
                    + "FROM planning_vessel pv JOIN m_vessel_profile mvp ON (pv.vessel_code=mvp.vessel_code) JOIN m_profile_detail mpd ON (mvp.profile_code=mpd.profile_code) "
                    + "WHERE pv.no_ppkb=? AND mvp.bay_no=? "
                    + "GROUP BY mvp.position, mvp.start_row "
                    + "ORDER BY mvp.position"),
    @NamedNativeQuery(name ="MasterVesselProfile.Native.findAllByIdVessel",query="SELECT profile_code FROM m_vessel_profile WHERE vessel_code = ?"),
    @NamedNativeQuery(name ="MasterVesselProfile.Native.findLastOfID",query="SELECT MAX(profile_code) FROM m_vessel_profile"),
    @NamedNativeQuery(name ="MasterVesselProfile.Native.deleteByIdVessel",query="DELETE FROM m_vessel_profile WHERE vessel_code = ?"),
    @NamedNativeQuery(name ="MasterVesselProfile.Native.findBaysByVessel",query="SELECT DISTINCT bay_no FROM m_vessel_profile WHERE vessel_code=? ORDER BY bay_no"),
/*    
    @NamedNativeQuery(name = "MasterVesselProfile.Native.findBaysByNoPpkb",query="SELECT DISTINCT RIGHT(to_char(mvp.bay_no, '09'),2) FROM m_vessel_profile mvp JOIN planning_vessel pv ON (mvp.vessel_code = pv.vessel_code) WHERE pv.no_ppkb = ? ORDER BY RIGHT(to_char(mvp.bay_no, '09'),2)"),
*/
    @NamedNativeQuery(name = "MasterVesselProfile.Native.findBaysByNoPpkb",query=    
"SELECT DISTINCT lpad(TO_CHAR(mvp.bay_no), 2, '0') " 
+"FROM m_vessel_profile mvp " 
+"JOIN planning_vessel pv " 
+"ON (mvp.vessel_code = pv.vessel_code) " 
+"WHERE pv.no_ppkb    = ? " 
+"ORDER BY lpad(TO_CHAR(mvp.bay_no), 2, '0')"),    
    @NamedNativeQuery(name = "MasterVesselProfile.Native.findNotAvailableBaysLocationByNoPpkbAndBay",query="SELECT pcl.v_row, pcl.v_tier FROM planning_cont_load pcl WHERE pcl.no_ppkb = ? AND pcl.v_bay = ? AND pcl.v_row IS NOT NULL AND pcl.v_tier IS NOT NULL GROUP BY pcl.v_row, pcl.v_tier;"),
    @NamedNativeQuery(name = "MasterVesselProfile.Native.findRowByBay", query = "SELECT start_row, row_number FROM m_vessel_profile WHERE vessel_code=? AND position=? AND bay_no=?"),
    @NamedNativeQuery(name = "MasterVesselProfile.Native.findMVesselBay", query = "SELECT m.bay_no,m.position FROM m_vessel_profile m WHERE vessel_code=?"),
    @NamedNativeQuery(name = "MasterVesselProfile.Native.findAllId", query="SELECT DISTINCT p.vessel_code, p.bay_no, (SELECT p1.profile_code FROM m_vessel_profile p1 WHERE p1.bay_no=p.bay_no AND p1.vessel_code=p.vessel_code AND p1.position='up') AS up, (SELECT p2.profile_code FROM m_vessel_profile p2 WHERE p2.bay_no=p.bay_no AND p2.vessel_code=p.vessel_code AND p2.position='bottom') AS bottom FROM m_vessel_profile p WHERE p.vessel_code=? AND p.bay_no=?"),
    @NamedNativeQuery(name = "MasterVesselProfile.Native.findDistinctAllByVesselCode", query="SELECT v.vessel_code, p1.bay_no, p1.position, p1.start_row, p1.row_number, p1.profile_code, p2.position, p2.start_row, p2.row_number, p2.profile_code FROM ((m_vessel v LEFT JOIN m_vessel_profile p1 ON v.vessel_code=p1.vessel_code AND p1.position='up') LEFT JOIN m_vessel_profile p2 ON v.vessel_code=p2.vessel_code AND p2.position='bottom') WHERE p1.bay_no=p2.bay_no AND v.vessel_code=? ORDER BY p1.bay_no")
 })

public class MasterVesselProfile implements Serializable, EntityAuditable {
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
    @Column(name = "profile_code", nullable = false)
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    
    

    
    private Integer profileCode;
    @Column(name = "bay_no")
    private Short bayNo;
    @Column(name = "position", length = 2)
    private String position;
    @Column(name = "row_number")
    private Integer rowNumber;
    @Column(name = "start_row")
    private Integer startRow;
    @OneToMany(mappedBy = "masterVesselProfile")
    private List<MasterProfileDetail> masterProfileDetailList;
    @JoinColumn(name = "vessel_code", referencedColumnName = "vessel_code")
    @ManyToOne
    private MasterVessel masterVessel;

    public MasterVesselProfile() {
    }

    public MasterVesselProfile(Integer profileCode) {
        this.profileCode = profileCode;
    }

    public Integer getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(Integer profileCode) {
        this.profileCode = profileCode;
    }

    public Short getBayNo() {
        return bayNo;
    }

    public void setBayNo(Short bayNo) {
        this.bayNo = bayNo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public List<MasterProfileDetail> getMasterProfileDetailList() {
        return masterProfileDetailList;
    }

    public void setMasterProfileDetailList(List<MasterProfileDetail> masterProfileDetailList) {
        this.masterProfileDetailList = masterProfileDetailList;
    }

    public MasterVessel getMasterVessel() {
        return masterVessel;
    }

    public void setMasterVessel(MasterVessel masterVessel) {
        this.masterVessel = masterVessel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (profileCode != null ? profileCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterVesselProfile)) {
            return false;
        }
        MasterVesselProfile other = (MasterVesselProfile) object;
        if ((this.profileCode == null && other.profileCode != null) || (this.profileCode != null && !this.profileCode.equals(other.profileCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterVesselProfile[profileCode=" + profileCode + "]";
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
