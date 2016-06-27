/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPluggingReefer;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "service_reefer")
@NamedQueries({
    @NamedQuery(name = "ServiceReefer.findAll", query = "SELECT s FROM ServiceReefer s"),
    @NamedQuery(name = "ServiceReefer.findByIdReefer", query = "SELECT s FROM ServiceReefer s WHERE s.idReefer = :idReefer"),
    @NamedQuery(name = "ServiceReefer.findByContNo", query = "SELECT s FROM ServiceReefer s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceReefer.findByContSize", query = "SELECT s FROM ServiceReefer s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceReefer.findByContStatus", query = "SELECT s FROM ServiceReefer s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceReefer.findByPlugOn", query = "SELECT s FROM ServiceReefer s WHERE s.plugOn = :plugOn"),
    @NamedQuery(name = "ServiceReefer.findByPlugOff", query = "SELECT s FROM ServiceReefer s WHERE s.plugOff = :plugOff"),
    @NamedQuery(name = "ServiceReefer.findByContNoPpkbAndOperation", query = "SELECT s FROM ServiceReefer s WHERE s.contNo = :contNo AND s.noPpkb = :noPpkb AND s.operation = :operation"),
    @NamedQuery(name = "ServiceReefer.findByCountableLoadReefer", query = "SELECT s FROM ServiceReefer s WHERE s.contNo = :contNo AND s.noPpkb = :noPpkb AND s.operation IN ('LOAD', 'TRANSHIPMENT', 'SHIFTING')"),
    @NamedQuery(name = "ServiceReefer.findByOperation", query = "SELECT s FROM ServiceReefer s WHERE s.operation = :operation")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceReefer.Native.findServiceReeferById", query = "SELECT sr.id_reefer, sr.cont_no, sr.cont_size, c.cont_type, sr.cont_status, sr.first_plug_on, sr.plug_on, pr.plugging_code, sr.plug_off, sr.operation, sr.no_ppkb, sr.change_plugging, sr.last_plugging_code, sr.mlo "
    + "FROM service_reefer as sr, m_container_type as c, m_plugging_reefer as pr WHERE sr.cont_type = c.cont_type AND sr.plugging_code = pr.plugging_code AND sr.id_reefer = ?"),
    @NamedNativeQuery(name = "ServiceReefer.Native.findServiceReeferByPpkbDischarge", query = "SELECT sr.id_reefer, sr.cont_no, sr.cont_size, c.type_in_general as name, sr.cont_status, sr.first_plug_on, sr.plug_on, pr.plugging_code, sr.plug_off, sr.last_plugging_code FROM service_reefer sr, m_container_type c, m_plugging_reefer pr WHERE sr.cont_type = c.cont_type AND sr.plugging_code = pr.plugging_code AND sr.operation = 'DISCHARGE' AND sr.no_ppkb = ? ORDER BY sr.id_reefer DESC"),
    @NamedNativeQuery(name = "ServiceReefer.Native.findServiceReeferByPpkbLoad", query = "SELECT sr.id_reefer, sr.cont_no, sr.cont_size, c.type_in_general as name, sr.cont_status, sr.first_plug_on, sr.plug_on, pr.plugging_code, sr.plug_off, sr.last_plugging_code "
                                                                                        + "FROM service_reefer sr "
                                                                                                + "JOIN m_container_type c ON (sr.cont_type = c.cont_type) "
                                                                                                + "JOIN m_plugging_reefer pr ON (sr.plugging_code = pr.plugging_code) "
                                                                                        + "WHERE sr.operation IN ('LOAD', 'TRANSHIPMENT', 'SHIFTING') AND sr.no_ppkb = ? "
                                                                                        + "ORDER BY sr.id_reefer DESC"),
    @NamedNativeQuery(name = "ServiceReefer.Native.findServiceReeferByRegPlugging", query = "SELECT sr.id_reefer, sr.cont_no, sr.cont_size, c.name, sr.cont_status, sr.first_plug_on, sr.plug_on, pr.plugging_code, sr.plug_off, sr.last_plugging_code FROM service_reefer as sr, m_container_type as c, m_plugging_reefer as pr WHERE sr.cont_type = c.cont_type AND sr.plugging_code = pr.plugging_code AND sr.operation = 'PLUGGING' AND sr.no_reg = ? ORDER BY sr.id_reefer DESC"),
    @NamedNativeQuery(name = "ServiceReefer.Native.findByContNo", query = "SELECT id_reefer, plugging_code, last_plugging_code FROM service_reefer WHERE cont_no = ? AND plug_off IS NULL"),
    @NamedNativeQuery(name = "ServiceReefer.Native.findPlugOnForReeferDischargeService", query = "SELECT plug_on FROM service_reefer WHERE operation = 'DISCHARGE' AND cont_no = ? AND no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceReefer.Native.findPlugOnForReeferPluggingService", query = "SELECT plug_on FROM service_reefer WHERE operation = 'PLUGGING' AND plugging_reefer_service_id = ? "),
    @NamedNativeQuery(name = "ServiceReefer.Native.findPlugOffForReeferPluggingService", query = "SELECT plug_off FROM service_reefer WHERE operation = 'PLUGGING' AND plugging_reefer_service_id = ? "),
    @NamedNativeQuery(name = "ServiceReefer.Native.findPlugOnOffForReeferLoadService", query = "SELECT plug_on, plug_off FROM service_reefer WHERE operation = 'LOAD' AND cont_no = ? AND no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceReefer.Native.findIDReeferByPPKBnCONT", query = "SELECT id_reefer FROM service_reefer WHERE no_ppkb=? AND cont_no=?"),
    @NamedNativeQuery(name = "ServiceReefer.Native.findValidasiPenumpukan", query = "select id_reefer from service_reefer where no_ppkb=? AND cont_no=? AND operation=?"),
    @NamedNativeQuery(name = "ServiceReefer.Native.findValidasiUpdatePlugging", query = "select id_reefer from service_reefer where plugging_reefer_service_id=? AND cont_no=? AND operation=?")})
public class ServiceReefer implements Serializable, EntityAuditable {
    public static final String OPERATION_LOAD = "LOAD";
    public static final String OPERATION_TRANSHIPMENT = "TRANSHIPMENT";
    public static final String OPERATION_SHIFTING = "SHIFTING";
    public static final String OPERATION_DISCHARGE = "DISCHARGE";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_reefer", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReefer;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_status", length = 5)
    private String contStatus;
    @Basic(optional = false)
    @Column(name = "plug_on", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date plugOn;
    @Basic(optional = false)
    @Column(name = "plug_off", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date plugOff;
    @Column(name = "operation", length = 20)
    private String operation;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "change_plugging")
    private Short changePlugging;
    @Column(name = "no_reg", length = 30)
    private String noReg;
    @Column(name = "first_plug_on", length = 5)
    private String firstPlugOn;
    @Column(name = "last_plugging_code", length = 5)
    private String lastPluggingCode;
    @JoinColumn(name = "plugging_code", referencedColumnName = "plugging_code")
    @ManyToOne
    private MasterPluggingReefer masterPluggingReefer;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceReefer")
    private List<ReeferMonitoring> reeferMonitoringList;
    @Column(name = "plugging_reefer_service_id", length = 30)
    private String pluggingReeferServiceId;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
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
    @Column(name = "bl_no")
    private String blNo;

    public ServiceReefer() {
    }

    public ServiceReefer(Integer idReefer) {
        this.idReefer = idReefer;
    }

    public ServiceReefer(Integer idReefer, String contNo, Date plugOn, Date plugOff, MasterCustomer mlo) {
        this.idReefer = idReefer;
        this.contNo = contNo;
        this.mlo = mlo;
        this.plugOn = plugOn;
        this.plugOff = plugOff;
    }

    public Integer getIdReefer() {
        return idReefer;
    }

    public void setIdReefer(Integer idReefer) {
        this.idReefer = idReefer;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public Short getContSize() {
        return contSize;
    }

    public void setContSize(Short contSize) {
        this.contSize = contSize;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public Date getPlugOn() {
        return plugOn;
    }

    public void setPlugOn(Date plugOn) {
        this.plugOn = plugOn;
    }

    public Date getPlugOff() {
        return plugOff;
    }

    public void setPlugOff(Date plugOff) {
        this.plugOff = plugOff;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getLastPluggingCode() {
        return lastPluggingCode;
    }

    public void setLastPluggingCode(String lastPluggingCode) {
        this.lastPluggingCode = lastPluggingCode;
    }

    /**
     * @return the noPpkb
     */
    public String getNoPpkb() {
        return noPpkb;
    }

    /**
     * @param noPpkb the noPpkb to set
     */
    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    /**
     * @return the changePlugging
     */
    public Short getChangePlugging() {
        return changePlugging;
    }

    /**
     * @param changePlugging the changePlugging to set
     */
    public void setChangePlugging(Short changePlugging) {
        this.changePlugging = changePlugging;
    }

    /**
     * @return the noReg
     */
    public String getNoReg() {
        return noReg;
    }

    /**
     * @param noReg the noReg to set
     */
    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public String getFirstPlugOn() {
        return firstPlugOn;
    }

    public void setFirstPlugOn(String firstPlugOn) {
        this.firstPlugOn = firstPlugOn;
    }

    public MasterPluggingReefer getMasterPluggingReefer() {
        return masterPluggingReefer;
    }

    public void setMasterPluggingReefer(MasterPluggingReefer masterPluggingReefer) {
        this.masterPluggingReefer = masterPluggingReefer;
    }

    public MasterContainerType getMasterContainerType() {
        return masterContainerType;
    }

    public void setMasterContainerType(MasterContainerType masterContainerType) {
        this.masterContainerType = masterContainerType;
    }

    public List<ReeferMonitoring> getReeferMonitoringList() {
        return reeferMonitoringList;
    }

    public void setReeferMonitoringList(List<ReeferMonitoring> reeferMonitoringList) {
        this.reeferMonitoringList = reeferMonitoringList;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
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
    public String getPluggingReeferServiceId() {
        return pluggingReeferServiceId;
    }

    public void setPluggingReeferServiceId(String pluggingReeferServiceId) {
        this.pluggingReeferServiceId = pluggingReeferServiceId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReefer != null ? idReefer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiceReefer)) {
            return false;
        }
        ServiceReefer other = (ServiceReefer) object;
        if ((this.idReefer == null && other.idReefer != null) || (this.idReefer != null && !this.idReefer.equals(other.idReefer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceReefer[idReefer=" + idReefer + "]";
    }

}
