/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author R. Seno Anggoro A
 */
@Entity
@Table(name = "change_vessel_service")
@EntityListeners({AuditTrailEntityListener.class})
@NamedQueries({
    @NamedQuery(name = "ChangeVesselService.findAll", query = "SELECT c FROM ChangeVesselService c"),
    @NamedQuery(name = "ChangeVesselService.findById", query = "SELECT c FROM ChangeVesselService c WHERE c.id = :id"),
    @NamedQuery(name = "ChangeVesselService.findByNoReg", query = "SELECT c FROM ChangeVesselService c WHERE c.registration.noReg = :noReg"),
    @NamedQuery(name = "ChangeVesselService.deleteByNoPpkb", query = "DELETE FROM ChangeVesselService c WHERE c.registration.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "ChangeVesselService.findByContNo", query = "SELECT c FROM ChangeVesselService c WHERE c.contNo = :contNo"),
    @NamedQuery(name = "ChangeVesselService.findNewPlanningVesselByNoReg", query = "SELECT DISTINCT c.newPlanningVessel FROM ChangeVesselService c WHERE c.registration.noReg = :noReg"),
    @NamedQuery(name = "ChangeVesselService.findByContSize", query = "SELECT c FROM ChangeVesselService c WHERE c.contSize = :contSize"),
    @NamedQuery(name = "ChangeVesselService.findByContStatus", query = "SELECT c FROM ChangeVesselService c WHERE c.contStatus = :contStatus"),
    @NamedQuery(name = "ChangeVesselService.findByContGross", query = "SELECT c FROM ChangeVesselService c WHERE c.contGross = :contGross"),
    @NamedQuery(name = "ChangeVesselService.findBySealNo", query = "SELECT c FROM ChangeVesselService c WHERE c.sealNo = :sealNo"),
    @NamedQuery(name = "ChangeVesselService.findByOverSize", query = "SELECT c FROM ChangeVesselService c WHERE c.overSize = :overSize"),
    @NamedQuery(name = "ChangeVesselService.findByDg", query = "SELECT c FROM ChangeVesselService c WHERE c.dg = :dg"),
    @NamedQuery(name = "ChangeVesselService.findByDgLabel", query = "SELECT c FROM ChangeVesselService c WHERE c.dgLabel = :dgLabel"),
    @NamedQuery(name = "ChangeVesselService.findByBlNo", query = "SELECT c FROM ChangeVesselService c WHERE c.blNo = :blNo"),
    @NamedQuery(name = "ChangeVesselService.findByIsExport", query = "SELECT c FROM ChangeVesselService c WHERE c.isExport = :isExport"),
    @NamedQuery(name = "ChangeVesselService.findByPosition", query = "SELECT c FROM ChangeVesselService c WHERE c.position = :position"),
    @NamedQuery(name = "ChangeVesselService.findByCharge", query = "SELECT c FROM ChangeVesselService c WHERE c.charge = :charge"),
    @NamedQuery(name = "ChangeVesselService.findByCreatedBy", query = "SELECT c FROM ChangeVesselService c WHERE c.createdBy = :createdBy"),
    @NamedQuery(name = "ChangeVesselService.findByCreatedDate", query = "SELECT c FROM ChangeVesselService c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "ChangeVesselService.findByModifiedBy", query = "SELECT c FROM ChangeVesselService c WHERE c.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "ChangeVesselService.findByModifiedDate", query = "SELECT c FROM ChangeVesselService c WHERE c.modifiedDate = :modifiedDate")})
public class ChangeVesselService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Basic(optional = false)
    @Column(name = "cont_size", nullable = false)
    private short contSize;
    @Basic(optional = false)
    @Column(name = "cont_status", nullable = false, length = 5)
    private String contStatus;
    @Basic(optional = false)
    @Column(name = "cont_gross", nullable = false)
    private int contGross = 0;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Basic(optional = false)
    @Column(name = "over_size", nullable = false)
    private String overSize = "FALSE";
    @Basic(optional = false)
    @Column(name = "dg", nullable = false)
    private String dg = "FALSE";
    @Basic(optional = false)
    @Column(name = "dg_label", nullable = false)
    private String dgLabel = "FALSE";
    @Column(name = "bl_no", length = 100)
    private String blNo;
    @Basic(optional = false)
    @Column(name = "is_export", nullable = false)
    private String isExport = "FALSE";
    @Basic(optional = false)
    @Column(name = "position", nullable = false, length = 10)
    private String position;
    @Basic(optional = false)
    @Column(name = "charge", nullable = false, precision = 19, scale = 2)
    private BigDecimal charge;
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
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg", nullable = false)
    @ManyToOne(optional = false)
    private Registration registration;
    @JoinColumn(name = "new_ppkb", referencedColumnName = "no_ppkb", nullable = false)
    @ManyToOne(optional = false)
    private PlanningVessel newPlanningVessel;
    @JoinColumn(name = "disch_port_code", referencedColumnName = "port_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterPort dischargePort;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type", nullable = false)
    @ManyToOne(optional = false)
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCommodity masterCommodity;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterActivity masterActivity;

    public ChangeVesselService() {
    }

    public ChangeVesselService(Integer id) {
        this.id = id;
    }

    public ChangeVesselService(Integer id, String contNo, short contSize, String contStatus, int contGross, String overSize, String dg, String dgLabel, String isExport, String position, BigDecimal charge, String createdBy, Date createdDate) {
        this.id = id;
        this.contNo = contNo;
        this.contSize = contSize;
        this.contStatus = contStatus;
        this.contGross = contGross;
        this.overSize = overSize;
        this.dg = dg;
        this.dgLabel = dgLabel;
        this.isExport = isExport;
        this.position = position;
        this.charge = charge;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public short getContSize() {
        return contSize;
    }

    public void setContSize(short contSize) {
        this.contSize = contSize;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public int getContGross() {
        return contGross;
    }

    public void setContGross(int contGross) {
        this.contGross = contGross;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getOverSize() {
        return overSize;
    }

    public void setOverSize(String overSize) {
        this.overSize = overSize;
    }

    public String getDg() {
        return dg;
    }

    public void setDg(String dg) {
        this.dg = dg;
    }

    public String getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(String dgLabel) {
        this.dgLabel = dgLabel;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
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

    public PlanningVessel getNewPlanningVessel() {
        return newPlanningVessel;
    }

    public void setNewPlanningVessel(PlanningVessel newPlanningVessel) {
        this.newPlanningVessel = newPlanningVessel;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public MasterCommodity getMasterCommodity() {
        return masterCommodity;
    }

    public void setMasterCommodity(MasterCommodity masterCommodity) {
        this.masterCommodity = masterCommodity;
    }

    public MasterActivity getMasterActivity() {
        return masterActivity;
    }

    public void setMasterActivity(MasterActivity masterActivity) {
        this.masterActivity = masterActivity;
    }

    public MasterCustomer getMlo() {
        return mlo;
    }

    public void setMlo(MasterCustomer mlo) {
        this.mlo = mlo;
    }

    public MasterPort getDischargePort() {
        return dischargePort;
    }

    public void setDischargePort(MasterPort dischargePort) {
        this.dischargePort = dischargePort;
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
        if (!(object instanceof ChangeVesselService)) {
            return false;
        }
        ChangeVesselService other = (ChangeVesselService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ChangeVesselService[id=" + id + "]";
    }

}
