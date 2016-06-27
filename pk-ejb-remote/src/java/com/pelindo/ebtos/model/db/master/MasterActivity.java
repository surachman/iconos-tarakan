/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.Angsur;
import com.pelindo.ebtos.model.db.ChangeStatus;
import com.pelindo.ebtos.model.db.DischargeProductivityRecap;
import com.pelindo.ebtos.model.db.LiftService;
import com.pelindo.ebtos.model.db.LoadProductivityRecap;
import com.pelindo.ebtos.model.db.Monitoring;
import com.pelindo.ebtos.model.db.Penumpukan;
import com.pelindo.ebtos.model.db.PenumpukanSusulan;
import com.pelindo.ebtos.model.db.PerhitunganAngsur;
import com.pelindo.ebtos.model.db.PerhitunganChangeStatus;
import com.pelindo.ebtos.model.db.PerhitunganLiftService;
import com.pelindo.ebtos.model.db.PerhitunganMonitoring;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukan;
import com.pelindo.ebtos.model.db.PerhitunganPenumpukanSusulan;
import com.pelindo.ebtos.model.db.PerhitunganPlugging;
import com.pelindo.ebtos.model.db.Plugging;
import com.pelindo.ebtos.model.db.ServiceContDischarge;
import com.pelindo.ebtos.model.db.ServiceContLoad;
import com.pelindo.ebtos.model.db.ServiceContTranshipment;
import com.pelindo.ebtos.model.db.ServiceShifting;
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
@Table(name = "m_activity")
@NamedQueries({
    @NamedQuery(name = "MasterActivity.findAll", query = "SELECT m FROM MasterActivity m"),
    @NamedQuery(name = "MasterActivity.findByActivityCode", query = "SELECT m FROM MasterActivity m WHERE m.activityCode = :activityCode"),
    @NamedQuery(name = "MasterActivity.findByDescription", query = "SELECT m FROM MasterActivity m WHERE m.description = :description")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterActivity.Native.findAllActivity", query = "SELECT activity_code, description FROM m_activity ORDER BY activity_code"),
    @NamedNativeQuery(name = "MasterActivity.Native.findActivityBySimulasi", query = "select m.activity_code,m.description,mt.curr_code,mt.amount as tarif_idr,mt1.amount as tarif_usd from m_activity m,m_tarif_cont mt,m_tarif_cont mt1 where m.activity_code LIKE ('%'|| 'A0' ||'%') and m.activity_code=mt.activity_code AND m.activity_code=mt1.activity_code AND mt.curr_code = 'IDR' AND mt1.curr_code = 'USD'"),
    @NamedNativeQuery(name = "MasterActivity.Native.findAllActivityExc02", query = "SELECT activity_code, description FROM m_activity WHERE activity_code NOT IN ('RS01x', 'RS02x', 'RS03x') ORDER BY activity_code")
})
public class MasterActivity implements Serializable, EntityAuditable {
    public enum Type {
        FCL, LCL, MTY, TRANSHIPMENT
    }

    public enum ShiftingType {
        LANDED, NOLANDED, TOCY
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "activity_code", nullable = false, length = 10)
    private String activityCode;
    @Column(name = "description", length = 256)
    private String description;
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
    @OneToMany(mappedBy = "masterActivity")
    private List<ServiceShifting> serviceShiftingList;
    @OneToMany(mappedBy = "masterActivity")
    private List<PerhitunganLiftService> perhitunganLiftServiceList;
    @OneToMany(mappedBy = "masterActivity")
    private List<DischargeProductivityRecap> dischargeProductivityRecapList;
    @OneToMany(mappedBy = "masterActivity")
    private List<MasterTarifCont> masterTarifContList;
    @OneToMany(mappedBy = "masterActivity")
    private List<Monitoring> monitoringList;
    @OneToMany(mappedBy = "masterActivity")
    private List<MasterDiscount> masterDiscountList;
    @OneToMany(mappedBy = "masterActivity")
    private List<Penumpukan> penumpukanList;
    @OneToMany(mappedBy = "masterActivity")
    private List<ServiceContDischarge> serviceContDischargeList;
    @OneToMany(mappedBy = "masterActivity")
    private List<PenumpukanSusulan> penumpukanSusulanList;
    @OneToMany(mappedBy = "masterActivity")
    private List<Plugging> pluggingList;
    @OneToMany(mappedBy = "masterActivity")
    private List<PerhitunganPenumpukanSusulan> perhitunganPenumpukanSusulanList;
    @OneToMany(mappedBy = "masterActivity")
    private List<PerhitunganAngsur> perhitunganAngsurList;
    @OneToMany(mappedBy = "masterActivity")
    private List<Angsur> angsurList;
    @OneToMany(mappedBy = "masterActivity")
    private List<PerhitunganMonitoring> perhitunganMonitoringList;
    @OneToMany(mappedBy = "masterActivity")
    private List<LoadProductivityRecap> loadProductivityRecapList;
    @OneToMany(mappedBy = "masterActivity")
    private List<LiftService> liftServiceList;
    @OneToMany(mappedBy = "masterActivity")
    private List<MasterTarifHistory> masterTarifHistoryList;
    @OneToMany(mappedBy = "masterActivity")
    private List<PerhitunganPenumpukan> perhitunganPenumpukanList;
    @OneToMany(mappedBy = "masterActivity")
    private List<ChangeStatus> changeStatusList;
    @OneToMany(mappedBy = "masterActivity")
    private List<PerhitunganPlugging> perhitunganPluggingList;
    @OneToMany(mappedBy = "masterActivity")
    private List<ServiceContLoad> serviceContLoadList;
    @OneToMany(mappedBy = "masterActivity")
    private List<PerhitunganChangeStatus> perhitunganChangeStatusList;
    @OneToMany(mappedBy = "masterActivity")
    private List<ServiceContTranshipment> serviceContTranshipmentList;
    @OneToMany(mappedBy = "masterActivity")
    private List<MasterAnggaran> masterAnggaranList;

    public MasterActivity() {
    }

    public MasterActivity(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ServiceShifting> getServiceShiftingList() {
        return serviceShiftingList;
    }

    public void setServiceShiftingList(List<ServiceShifting> serviceShiftingList) {
        this.serviceShiftingList = serviceShiftingList;
    }

    public List<PerhitunganLiftService> getPerhitunganLiftServiceList() {
        return perhitunganLiftServiceList;
    }

    public void setPerhitunganLiftServiceList(List<PerhitunganLiftService> perhitunganLiftServiceList) {
        this.perhitunganLiftServiceList = perhitunganLiftServiceList;
    }

    public List<DischargeProductivityRecap> getDischargeProductivityRecapList() {
        return dischargeProductivityRecapList;
    }

    public void setDischargeProductivityRecapList(List<DischargeProductivityRecap> dischargeProductivityRecapList) {
        this.dischargeProductivityRecapList = dischargeProductivityRecapList;
    }

    public List<MasterTarifCont> getMasterTarifContList() {
        return masterTarifContList;
    }

    public void setMasterTarifContList(List<MasterTarifCont> masterTarifContList) {
        this.masterTarifContList = masterTarifContList;
    }

    public List<Monitoring> getMonitoringList() {
        return monitoringList;
    }

    public void setMonitoringList(List<Monitoring> monitoringList) {
        this.monitoringList = monitoringList;
    }

    public List<MasterDiscount> getMasterDiscountList() {
        return masterDiscountList;
    }

    public void setMasterDiscountList(List<MasterDiscount> masterDiscountList) {
        this.masterDiscountList = masterDiscountList;
    }

    public List<Penumpukan> getPenumpukanList() {
        return penumpukanList;
    }

    public void setPenumpukanList(List<Penumpukan> penumpukanList) {
        this.penumpukanList = penumpukanList;
    }

    public List<ServiceContDischarge> getServiceContDischargeList() {
        return serviceContDischargeList;
    }

    public void setServiceContDischargeList(List<ServiceContDischarge> serviceContDischargeList) {
        this.serviceContDischargeList = serviceContDischargeList;
    }

    public List<PenumpukanSusulan> getPenumpukanSusulanList() {
        return penumpukanSusulanList;
    }

    public void setPenumpukanSusulanList(List<PenumpukanSusulan> penumpukanSusulanList) {
        this.penumpukanSusulanList = penumpukanSusulanList;
    }

    public List<Plugging> getPluggingList() {
        return pluggingList;
    }

    public void setPluggingList(List<Plugging> pluggingList) {
        this.pluggingList = pluggingList;
    }

    public List<PerhitunganPenumpukanSusulan> getPerhitunganPenumpukanSusulanList() {
        return perhitunganPenumpukanSusulanList;
    }

    public void setPerhitunganPenumpukanSusulanList(List<PerhitunganPenumpukanSusulan> perhitunganPenumpukanSusulanList) {
        this.perhitunganPenumpukanSusulanList = perhitunganPenumpukanSusulanList;
    }

    public List<PerhitunganAngsur> getPerhitunganAngsurList() {
        return perhitunganAngsurList;
    }

    public void setPerhitunganAngsurList(List<PerhitunganAngsur> perhitunganAngsurList) {
        this.perhitunganAngsurList = perhitunganAngsurList;
    }

    public List<Angsur> getAngsurList() {
        return angsurList;
    }

    public void setAngsurList(List<Angsur> angsurList) {
        this.angsurList = angsurList;
    }

    public List<PerhitunganMonitoring> getPerhitunganMonitoringList() {
        return perhitunganMonitoringList;
    }

    public void setPerhitunganMonitoringList(List<PerhitunganMonitoring> perhitunganMonitoringList) {
        this.perhitunganMonitoringList = perhitunganMonitoringList;
    }

    public List<LoadProductivityRecap> getLoadProductivityRecapList() {
        return loadProductivityRecapList;
    }

    public void setLoadProductivityRecapList(List<LoadProductivityRecap> loadProductivityRecapList) {
        this.loadProductivityRecapList = loadProductivityRecapList;
    }

    public List<LiftService> getLiftServiceList() {
        return liftServiceList;
    }

    public void setLiftServiceList(List<LiftService> liftServiceList) {
        this.liftServiceList = liftServiceList;
    }

    public List<MasterTarifHistory> getMasterTarifHistoryList() {
        return masterTarifHistoryList;
    }

    public void setMasterTarifHistoryList(List<MasterTarifHistory> masterTarifHistoryList) {
        this.masterTarifHistoryList = masterTarifHistoryList;
    }

    public List<PerhitunganPenumpukan> getPerhitunganPenumpukanList() {
        return perhitunganPenumpukanList;
    }

    public void setPerhitunganPenumpukanList(List<PerhitunganPenumpukan> perhitunganPenumpukanList) {
        this.perhitunganPenumpukanList = perhitunganPenumpukanList;
    }

    public List<ChangeStatus> getChangeStatusList() {
        return changeStatusList;
    }

    public void setChangeStatusList(List<ChangeStatus> changeStatusList) {
        this.changeStatusList = changeStatusList;
    }

    public List<PerhitunganPlugging> getPerhitunganPluggingList() {
        return perhitunganPluggingList;
    }

    public void setPerhitunganPluggingList(List<PerhitunganPlugging> perhitunganPluggingList) {
        this.perhitunganPluggingList = perhitunganPluggingList;
    }

    public List<ServiceContLoad> getServiceContLoadList() {
        return serviceContLoadList;
    }

    public void setServiceContLoadList(List<ServiceContLoad> serviceContLoadList) {
        this.serviceContLoadList = serviceContLoadList;
    }

    public List<PerhitunganChangeStatus> getPerhitunganChangeStatusList() {
        return perhitunganChangeStatusList;
    }

    public void setPerhitunganChangeStatusList(List<PerhitunganChangeStatus> perhitunganChangeStatusList) {
        this.perhitunganChangeStatusList = perhitunganChangeStatusList;
    }

    public List<ServiceContTranshipment> getServiceContTranshipmentList() {
        return serviceContTranshipmentList;
    }

    public void setServiceContTranshipmentList(List<ServiceContTranshipment> serviceContTranshipmentList) {
        this.serviceContTranshipmentList = serviceContTranshipmentList;
    }

    public List<MasterAnggaran> getMasterAnggaranList() {
        return masterAnggaranList;
    }

    public void setMasterAnggaranList(List<MasterAnggaran> masterAnggaranList) {
        this.masterAnggaranList = masterAnggaranList;
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
    public int hashCode() {
        int hash = 0;
        hash += (activityCode != null ? activityCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterActivity)) {
            return false;
        }
        MasterActivity other = (MasterActivity) object;
        if ((this.activityCode == null && other.activityCode != null) || (this.activityCode != null && !this.activityCode.equals(other.activityCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.MasterActivity[activityCode=" + activityCode + "]";
    }
}
