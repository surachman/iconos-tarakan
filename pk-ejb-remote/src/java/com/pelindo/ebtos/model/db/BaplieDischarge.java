/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.qtasnim.port.util.ContainerUtilization;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterPort;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
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
@Table(name = "baplie_discharge")
@NamedQueries({
    @NamedQuery(name = "BaplieDischarge.findAll", query = "SELECT b FROM BaplieDischarge b"),
    @NamedQuery(name = "BaplieDischarge.findByNoPpkb", query = "SELECT b FROM BaplieDischarge b WHERE b.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "BaplieDischarge.deleteByContNoAndNoPpkb", query = "DELETE FROM BaplieDischarge b WHERE b.planningVessel.noPpkb = :noPpkb AND b.contNo = :contNo"),
    @NamedQuery(name = "BaplieDischarge.findById", query = "SELECT b FROM BaplieDischarge b WHERE b.id = :id"),
    @NamedQuery(name = "BaplieDischarge.findByPpkbAndPortCode", query = "SELECT b FROM BaplieDischarge b WHERE b.portOfDelivery.portCode = :portCode AND b.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "BaplieDischarge.findByPpkbNotCompleted", query = "SELECT b FROM BaplieDischarge b WHERE b.dataComplete = FALSE AND b.planningVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "BaplieDischarge.findByContNo", query = "SELECT b FROM BaplieDischarge b WHERE b.contNo = :contNo"),
    @NamedQuery(name = "BaplieDischarge.findByContSize", query = "SELECT b FROM BaplieDischarge b WHERE b.contSize = :contSize"),
    @NamedQuery(name = "BaplieDischarge.findByContStatus", query = "SELECT b FROM BaplieDischarge b WHERE b.contStatus = :contStatus"),
    @NamedQuery(name = "BaplieDischarge.findByContGross", query = "SELECT b FROM BaplieDischarge b WHERE b.contGross = :contGross"),
    @NamedQuery(name = "BaplieDischarge.findBySealNo", query = "SELECT b FROM BaplieDischarge b WHERE b.sealNo = :sealNo"),
    @NamedQuery(name = "BaplieDischarge.findByDg", query = "SELECT b FROM BaplieDischarge b WHERE b.dg = :dg"),
    @NamedQuery(name = "BaplieDischarge.findByDgLabel", query = "SELECT b FROM BaplieDischarge b WHERE b.dgLabel = :dgLabel"),
    @NamedQuery(name = "BaplieDischarge.findByOverSize", query = "SELECT b FROM BaplieDischarge b WHERE b.overSize = :overSize"),
    @NamedQuery(name = "BaplieDischarge.findByVBay", query = "SELECT b FROM BaplieDischarge b WHERE b.vBay = :vBay"),
    @NamedQuery(name = "BaplieDischarge.findByVRow", query = "SELECT b FROM BaplieDischarge b WHERE b.vRow = :vRow"),
    @NamedQuery(name = "BaplieDischarge.findByVTier", query = "SELECT b FROM BaplieDischarge b WHERE b.vTier = :vTier"),
    @NamedQuery(name = "BaplieDischarge.findByTradeType", query = "SELECT b FROM BaplieDischarge b WHERE b.tradeType = :tradeType"),
    @NamedQuery(name = "BaplieDischarge.findByIsTranshipment", query = "SELECT b FROM BaplieDischarge b WHERE b.isTranshipment = :isTranshipment")})

@NamedNativeQueries({
    @NamedNativeQuery(name ="BaplieDischarge.Native.findContainerByTypeContainer",query="SELECT b.id, b.cont_type FROM baplie_discharge b, m_container_type t WHERE b.cont_type = t.cont_type AND t.cont_type = ?"),
    @NamedNativeQuery(name = "BaplieDischarge.Native.findBaplieDischargesByPpkb", query = "SELECT pbd.cont_no, pbd.cont_size, mct.type_in_general as name, pbd.cont_status, pbd.cont_gross, pbd.seal_no, pbd.v_bay, pbd.v_row, pbd.v_tier, mpt.name, mpo.name, pbd.id, pbd.data_complete, pbd.bl_no, mco.name FROM baplie_discharge pbd, m_container_type mct, m_port mpt, m_port mpo, m_commodity mco WHERE pbd.commodity_code=mco.commodity_code AND pbd.cont_type = mct.cont_type AND pbd.load_port_code = mpt.port_code AND pbd.disch_port_code = mpo.port_code AND pbd.no_ppkb = ?"),
    @NamedNativeQuery(name = "BaplieDischarge.Native.findBaplieDischargesByCode", query = "SELECT p.commodity_code FROM baplie_discharge p, m_commodity m WHERE p.commodity_code = m.commodity_code AND p.commodity_code = ?"),
    @NamedNativeQuery(name = "BaplieDischarge.Native.findBaplieDischargesByPpkbForCopy", query = "SELECT b.id, b.cont_type, b.commodity_code, b.no_ppkb, b.cont_no, b.cont_size, b.cont_status, b.cont_gross, b.seal_no, b.dg, b.dg_label, b.over_size, b.trade_type, b.load_port_code, b.disch_port_code, b.v_bay, b.v_row, b.v_tier FROM baplie_discharge AS b WHERE b.disch_port_code = (SELECT value_string FROM m_setting_app WHERE id = 'ebtos.implement_port_code') AND b.no_ppkb = ?"),
    @NamedNativeQuery(name = "BaplieDischarge.Native.findBaplieDischargesByPPKB", query = "SELECT pbd.bl_no, pbd.cont_no, pbd.cont_size, mct.name, pbd.cont_status, pbd.cont_gross, mc.name, pbd.seal_no, pbd.v_bay, pbd.v_row, pbd.v_tier, mpt.name, mpo.name, pbd.id "
                                                                                        + "FROM baplie_discharge pbd "
                                                                                                + "JOIN m_container_type mct ON (pbd.cont_type = mct.cont_type) "
                                                                                                + "JOIN m_port mpt ON (pbd.load_port_code = mpt.port_code) "
                                                                                                + "JOIN m_port mpo ON (pbd.disch_port_code = mpo.port_code) "
                                                                                                + "JOIN m_commodity mc ON (pbd.commodity_code = mc.commodity_code) "
                                                                                                + "JOIN planning_vessel pl ON (pbd.no_ppkb = pl.no_ppkb) "
                                                                                                        + "JOIN preservice_vessel pv ON (pl.booking_code = pv.booking_code) "
                                                                                                + "LEFT JOIN planning_trans_discharge ptd ON (pbd.id = ptd.id) "
                                                                                        + "WHERE ptd.id IS NULL AND pbd.no_ppkb = ? AND pbd.port_of_delivery != (SELECT value_string FROM m_setting_app WHERE id = 'ebtos.implement_port_code') AND pbd.disch_port_code != pv.next_port_code"),
    @NamedNativeQuery(name = "BaplieDischarge.Native.findContOnBay", query = "SELECT cont_no, cont_size, cont_gross FROM baplie_discharge WHERE no_ppkb=? AND v_bay=? AND v_row=? AND v_tier=?"),
    @NamedNativeQuery(name = "BaplieDischarge.Native.findBaplieDischargesByPpkbShifting", query = "SELECT pbd.bl_no, pbd.cont_no, pbd.cont_size, mct.type_in_general as name, pbd.cont_status, pbd.cont_gross, mc.name, pbd.seal_no, pbd.v_bay, pbd.v_row, pbd.v_tier, mpt.name, mpo.name, 'NOLANDED'::character varying(9) as shift_to, pbd.id, pbd.is_new_shift FROM baplie_discharge pbd, m_container_type mct, m_port mpt, m_port mpo, m_commodity mc, planning_vessel pv, preservice_vessel pp WHERE pbd.commodity_code=mc.commodity_code AND pbd.cont_type = mct.cont_type AND pbd.load_port_code = mpt.port_code AND pbd.disch_port_code = mpo.port_code AND pbd.no_ppkb=pv.no_ppkb AND pv.booking_code=pp.booking_code AND pp.next_port_code = pbd.disch_port_code AND pbd.no_ppkb = ? AND pbd.id NOT IN (SELECT id FROM planning_shift_discharge) ORDER BY pbd.id DESC"),
    @NamedNativeQuery(name = "BaplieDischarge.Native.findTheSameContainer", query= "SELECT bd.id, bd.no_ppkb, bd.cont_no FROM baplie_discharge bd, planning_vessel pv WHERE bd.no_ppkb=pv.no_ppkb AND pv.status='Approved' AND bd.cont_no=?"),
    @NamedNativeQuery(name = "BaplieDischarge.Native.findBaplieDischargeByPpkbPodDpc", query= "SELECT * FROM baplie_discharge bd WHERE bd.no_ppkb=? AND bd.disch_port_code=? AND bd.port_of_delivery=?"),
    @NamedNativeQuery(name = "BaplieDischarge.Native.findBaplieDischargeByPpkbNotPod", query= "SELECT * FROM baplie_discharge bd WHERE bd.no_ppkb=? AND bd.port_of_delivery!=? AND bd.port_of_delivery!=?"),
    @NamedNativeQuery(name = "BaplieDischarge.Native.findBaplieDischargeByPpkbShiftingViaCY", query= "SELECT * FROM baplie_discharge bd WHERE bd.no_ppkb=? AND bd.port_of_delivery!=? AND bd.port_of_delivery=?")
    })

public class BaplieDischarge implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_status", length = 5)
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "dg")
    private Boolean dg;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Column(name = "over_size")
    private Boolean overSize;
    @Column(name = "v_bay")
    private Short vBay;
    @Column(name = "v_row")
    private Short vRow;
    @Column(name = "v_tier")
    private Short vTier;
    @Column(name = "trade_type", length = 3)
    private String tradeType;
    @Column(name = "is_transhipment")
    private Boolean isTranshipment;
    @Column(name = "gross_class", length = 5)
    private String grossClass;
    @Column(name = "data_complete")
    private Boolean dataComplete;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "load_port_code", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort masterPort;
    @JoinColumn(name = "disch_port_code", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort masterPort1;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private boolean isExportImport;
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
    @Column(name = "is_new_shift", nullable = false)
    private boolean isNewShift;
    @JoinColumn(name = "port_of_delivery", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort portOfDelivery;

    public BaplieDischarge() {
    }

    public BaplieDischarge(Integer id) {
        this.id = id;
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

    public Integer getContGross() {
        return contGross;
    }

    public void setContGross(Integer contGross) {
        this.contGross = contGross;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public Boolean getDg() {
        return dg;
    }

    public void setDg(Boolean dg) {
        this.dg = dg;
    }

    public Boolean getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(Boolean dgLabel) {
        this.dgLabel = dgLabel;
    }

    public Boolean getOverSize() {
        return overSize;
    }

    public void setOverSize(Boolean overSize) {
        this.overSize = overSize;
    }

    public Short getVBay() {
        return vBay;
    }

    public void setVBay(Short vBay) {
        this.vBay = vBay;
    }

    public Short getVRow() {
        return vRow;
    }

    public void setVRow(Short vRow) {
        this.vRow = vRow;
    }

    public Short getVTier() {
        return vTier;
    }

    public void setVTier(Short vTier) {
        this.vTier = vTier;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Boolean getIsTranshipment() {
        return isTranshipment;
    }

    public void setIsTranshipment(Boolean isTranshipment) {
        this.isTranshipment = isTranshipment;
    }

    public String getGrossClass() {
        return grossClass;
    }


    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    public Boolean getDataComplete() {
        return dataComplete;
    }

    public void setDataComplete(Boolean dataComplete) {
        this.dataComplete = dataComplete;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public MasterPort getMasterPort() {
        return masterPort;
    }

    public void setMasterPort(MasterPort masterPort) {
        this.masterPort = masterPort;
    }

    public MasterPort getMasterPort1() {
        return masterPort1;
    }

    public void setMasterPort1(MasterPort masterPort1) {
        this.masterPort1 = masterPort1;
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

    public boolean isValid() {
        return getMasterCommodity() != null && getGrossClass() != null && getContSize() != null && getMasterContainerType() != null && getMasterPort() != null && getMasterPort1() != null && getContNo() != null && getContStatus() != null;
    }

    public boolean isContainerNumberValid() {
        if (getContNo() != null) {
            return ContainerUtilization.checkContainerNumberValidation(getContNo());
        }
        return false;
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

    public boolean getIsExportImport() {
        return isExportImport;
    }

    public void setIsExportImport(boolean isExportImport) {
        this.isExportImport = isExportImport;
    }

    public MasterPort getPortOfDelivery() {
        return portOfDelivery;
    }

    public void setPortOfDelivery(MasterPort portOfDelivery) {
        this.portOfDelivery = portOfDelivery;
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
        if (!(object instanceof BaplieDischarge)) {
            return false;
        }
        BaplieDischarge other = (BaplieDischarge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.BaplieDischarge[id=" + id + "]";
    }

    /**
     * @return the isNewShift
     */
    public boolean isIsNewShift() {
        return isNewShift;
    }

    /**
     * @param isNewShift the isNewShift to set
     */
    public void setIsNewShift(boolean isNewShift) {
        this.isNewShift = isNewShift;
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
