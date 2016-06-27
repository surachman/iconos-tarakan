/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterYard;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterPort;
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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "service_cont_transhipment")
@NamedQueries({
    @NamedQuery(name = "ServiceContTranshipment.findAll", query = "SELECT s FROM ServiceContTranshipment s"),
    @NamedQuery(name = "ServiceContTranshipment.findById", query = "SELECT s FROM ServiceContTranshipment s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceContTranshipment.findByCrane", query = "SELECT s FROM ServiceContTranshipment s WHERE s.crane = :crane"),
    @NamedQuery(name = "ServiceContTranshipment.findByContNo", query = "SELECT s FROM ServiceContTranshipment s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceContTranshipment.findByNewPpkbAndContNo", query = "SELECT s FROM ServiceContTranshipment s WHERE s.contNo = :contNo AND s.newPpkb = :newPpkb"),
    @NamedQuery(name = "ServiceContTranshipment.findByContSize", query = "SELECT s FROM ServiceContTranshipment s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceContTranshipment.findByContStatus", query = "SELECT s FROM ServiceContTranshipment s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceContTranshipment.findByContGross", query = "SELECT s FROM ServiceContTranshipment s WHERE s.contGross = :contGross"),
    @NamedQuery(name = "ServiceContTranshipment.findBySealNo", query = "SELECT s FROM ServiceContTranshipment s WHERE s.sealNo = :sealNo"),
    @NamedQuery(name = "ServiceContTranshipment.findByOverSize", query = "SELECT s FROM ServiceContTranshipment s WHERE s.overSize = :overSize"),
    @NamedQuery(name = "ServiceContTranshipment.findByDangerous", query = "SELECT s FROM ServiceContTranshipment s WHERE s.dangerous = :dangerous"),
    @NamedQuery(name = "ServiceContTranshipment.findByDgLabel", query = "SELECT s FROM ServiceContTranshipment s WHERE s.dgLabel = :dgLabel"),
    @NamedQuery(name = "ServiceContTranshipment.findByVBay", query = "SELECT s FROM ServiceContTranshipment s WHERE s.vBay = :vBay"),
    @NamedQuery(name = "ServiceContTranshipment.findByVRow", query = "SELECT s FROM ServiceContTranshipment s WHERE s.vRow = :vRow"),
    @NamedQuery(name = "ServiceContTranshipment.findByVTier", query = "SELECT s FROM ServiceContTranshipment s WHERE s.vTier = :vTier"),
    @NamedQuery(name = "ServiceContTranshipment.findByYSlot", query = "SELECT s FROM ServiceContTranshipment s WHERE s.ySlot = :ySlot"),
    @NamedQuery(name = "ServiceContTranshipment.findByYRow", query = "SELECT s FROM ServiceContTranshipment s WHERE s.yRow = :yRow"),
    @NamedQuery(name = "ServiceContTranshipment.findByYTier", query = "SELECT s FROM ServiceContTranshipment s WHERE s.yTier = :yTier"),
    @NamedQuery(name = "ServiceContTranshipment.findByPosition", query = "SELECT s FROM ServiceContTranshipment s WHERE s.position = :position"),
    @NamedQuery(name = "ServiceContTranshipment.findByTranshipmentContainerByPosition", query = "SELECT s FROM ServiceContTranshipment s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.contNo = :contNo AND s.position = :position"),
    @NamedQuery(name = "ServiceContTranshipment.findByTranshipmentByContNoAndPosition", query = "SELECT s FROM ServiceContTranshipment s WHERE s.contNo = :contNo AND s.position = :position"),
    @NamedQuery(name = "ServiceContTranshipment.findByStartPlacementDate", query = "SELECT s FROM ServiceContTranshipment s WHERE s.startPlacementDate = :startPlacementDate"),
    @NamedQuery(name = "ServiceContTranshipment.findByNewPpkb", query = "SELECT s FROM ServiceContTranshipment s WHERE s.newPpkb = :newPpkb"),
    @NamedQuery(name = "ServiceContTranshipment.findByNoPpkb", query = "SELECT s FROM ServiceContTranshipment s WHERE s.serviceVessel.noPpkb = :noPpkb"),
    @NamedQuery(name = "ServiceContTranshipment.findByPpkbWithPositions", query = "SELECT s FROM ServiceContTranshipment s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.position IN :positions")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.findByPpkb", query = "SELECT id, cont_no FROM service_cont_transhipment WHERE no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.findByContNo", query = "SELECT id, cont_no, no_ppkb, v_bay, v_row, v_tier, block, y_row, y_slot, y_tier FROM service_cont_transhipment WHERE cont_no = ? AND position = ?"),
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.findServiceContTranshipmentConfirm", query = "SELECT scd.cont_no, scd.cont_size, mct.name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.y_slot, scd.y_row, scd.y_tier, scd.id,scd.block FROM service_cont_transhipment AS scd, m_container_type AS mct WHERE scd.cont_type = mct.cont_type AND scd.position='02' AND scd.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.findServiceContTranshipmentByPpkbList", query = "SELECT r.id, r.commodity_code, r.block, r.cont_type,r.new_ppkb, r.cont_no,r.cont_size,r.cont_status,r.cont_gross,r.seal_no,change(r.over_size),change(r.dg_label),change(r.dangerous),r.y_slot,r.y_row,r.y_tier,r.bl_no,r.mlo FROM service_cont_transhipment r WHERE r.position='03' AND r.new_ppkb = ? AND r.cont_no NOT IN (select cont_no from planning_cont_load where no_ppkb=r.new_ppkb)"),
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.findServiceContTranshipmentsConfirm", query = "SELECT sct.cont_no, sct.cont_size, mct.type_in_general as name, sct.cont_status, sct.cont_gross, sct.seal_no, sct.v_bay, sct.v_row, sct.v_tier, sct.id FROM service_cont_transhipment AS sct, m_container_type AS mct WHERE sct.cont_type = mct.cont_type AND sct.position='02' AND sct.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.findServiceContTranshipmentsSelect", query = "SELECT sct.cont_no, sct.v_bay, sct.v_row, sct.v_tier, sct.id FROM service_cont_transhipment AS sct WHERE sct.position='01' AND sct.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.updateServiceContTranshipmentsForDeleteAll", query = "UPDATE service_cont_transhipment SET position = '01' WHERE id =?"),
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.findServiceContTranshipmentsConfirmServed", query = "SELECT sct.cont_no, sct.cont_size, mct.type_in_general as name, sct.cont_status, sct.cont_gross, sct.seal_no, sct.block, sct.y_slot, sct.y_row, sct.y_tier, sct.id, sct.bl_no, c.name, sct.load_port, sct.disch_port, false as topTier FROM service_cont_transhipment AS sct, m_container_type AS mct, m_commodity c WHERE sct.cont_type = mct.cont_type AND sct.position='03' AND c.commodity_code = sct.commodity_code AND sct.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.findServiceContTranshipmentsSelectServed", query = "SELECT sct.cont_no, sct.v_bay, sct.v_row, sct.v_tier, sct.id FROM service_cont_transhipment AS sct WHERE sct.position='02' AND sct.no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.updateServiceContTranshipmentsForDeleteAllPlacement", query = "UPDATE service_cont_transhipment SET position = '02' WHERE id =?"),
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.findServiceContTranshipmentsByNewPpkbNull", query = "SELECT r.id,r.cont_no,m.name,r.cont_size,r.cont_status,r.bl_no,r.block,r.y_slot,r.y_row,r.y_tier FROM service_cont_transhipment r,m_container_type m,m_yard my WHERE r.cont_type=m.cont_type AND r.new_ppkb IS NULL AND r.position='03' AND my.block=r.block"),
    @NamedNativeQuery(name = "ServiceContTranshipment.Native.findRekap", query = "SELECT ss.id, ss.cont_no, ss.cont_size, ct.type_in_general as name, ss.cont_status, es.equip_code, es.start_time, es.end_time, e.equip_code, e.start_time, e.end_time, el.equip_code, el.start_time, el.end_time, ss.seal_no "
                                                                               + "FROM service_cont_transhipment ss, service_cont_transhipment sst LEFT JOIN equipment e ON e.no_ppkb = sst.no_ppkb AND e.cont_no = sst.cont_no AND e.operation = 'TRANSDISCHARGE' AND e.equipment_act_code = 'H/T', service_cont_transhipment sstt LEFT JOIN equipment el ON el.no_ppkb = sstt.no_ppkb AND el.cont_no = sstt.cont_no AND el.operation = 'TRANSDISCHARGE' AND el.equipment_act_code = 'LIFTOFF', service_cont_transhipment ssttt LEFT JOIN equipment es ON es.no_ppkb = ssttt.no_ppkb AND es.cont_no = ssttt.cont_no AND es.operation = 'TRANSDISCHARGE' AND es.equipment_act_code = 'STEVEDORING', m_container_type ct "
                                                                               + "WHERE ss.cont_type = ct.cont_type AND ss.id = sst.id AND ss.id = sstt.id AND ss.id = ssttt.id AND ss.no_ppkb = ?"),
  @NamedNativeQuery(name = "ServiceContTranshipment.Native.findByIdTranshipment", query = "SELECT scd.id FROM service_cont_transhipment scd,planning_cont_load pld WHERE scd.new_ppkb = pld.no_ppkb AND scd.cont_no=pld.cont_no AND scd.new_ppkb = ? AND scd.cont_no=? AND pld.unknown= 'true' ")})
public class ServiceContTranshipment implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "crane", length = 2)
    private String crane;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Basic(optional = false)
    @Column(name = "cont_size", nullable = false)
    private Short contSize;
    @Basic(optional = false)
    @Column(name = "cont_status", nullable = false, length = 5)
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "over_size")
    private Boolean overSize;
    @Column(name = "dangerous")
    private Boolean dangerous;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Column(name = "v_bay")
    private Short vBay;
    @Column(name = "v_row")
    private Short vRow;
    @Column(name = "v_tier")
    private Short vTier;
    @Column(name = "y_slot")
    private Short ySlot;
    @Column(name = "y_row")
    private Short yRow;
    @Column(name = "y_tier")
    private Short yTier;
    @Column(name = "position", length = 2)
    private String position;
    @Column(name = "start_placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startPlacementDate;
    @Column(name = "new_ppkb", length = 30)
    private String newPpkb;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private ServiceVessel serviceVessel;
    @JoinColumn(name = "block", referencedColumnName = "block")
    @ManyToOne
    private MasterYard masterYard;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type", nullable = false)
    @ManyToOne(optional = false)
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCommodity masterCommodity;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;
    @Column(name = "load_port")
    private String loadPort;
    @Column(name = "disch_port")
    private String dischPort;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @OneToOne
    @JoinColumns(value = {
        @JoinColumn(name = "cont_no", referencedColumnName = "cont_no", insertable = false, updatable = false, nullable = true),
        @JoinColumn(name = "new_ppkb", referencedColumnName = "no_ppkb", insertable = false, updatable = false, nullable = true)
    })
    private PlanningContLoad planningContLoad;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @JoinColumn(name = "cont_damage", referencedColumnName = "id")
    @ManyToOne
    private MasterContDamage masterContDamage;
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
    @JoinColumn(name = "port_of_delivery", referencedColumnName = "port_code")
    @ManyToOne
    private MasterPort portOfDelivery;
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private boolean isExportImport = false;
    @Basic(optional = false)
    @Column(name = "is_sling", nullable = false)
    private boolean isSling = false;

    public ServiceContTranshipment() {
    }

    public ServiceContTranshipment(Integer id) {
        this.id = id;
    }

    public ServiceContTranshipment(Integer id, String contNo, short contSize, String contStatus, MasterCustomer mlo) {
        this.id = id;
        this.contNo = contNo;
        this.mlo = mlo;
        this.contSize = contSize;
        this.contStatus = contStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
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

    public Boolean getOverSize() {
        return overSize;
    }

    public void setOverSize(Boolean overSize) {
        this.overSize = overSize;
    }

    public Boolean getDangerous() {
        return dangerous;
    }

    public void setDangerous(Boolean dangerous) {
        this.dangerous = dangerous;
    }

    public Boolean getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(Boolean dgLabel) {
        this.dgLabel = dgLabel;
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

    public Short getYSlot() {
        return ySlot;
    }

    public void setYSlot(Short ySlot) {
        this.ySlot = ySlot;
    }

    public Short getYRow() {
        return yRow;
    }

    public void setYRow(Short yRow) {
        this.yRow = yRow;
    }

    public Short getYTier() {
        return yTier;
    }

    public void setYTier(Short yTier) {
        this.yTier = yTier;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getStartPlacementDate() {
        return startPlacementDate;
    }

    public void setStartPlacementDate(Date startPlacementDate) {
        this.startPlacementDate = startPlacementDate;
    }

    public String getNewPpkb() {
        return newPpkb;
    }

    public void setNewPpkb(String newPpkb) {
        this.newPpkb = newPpkb;
    }

    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
    }

    public MasterYard getMasterYard() {
        return masterYard;
    }

    public void setMasterYard(MasterYard masterYard) {
        this.masterYard = masterYard;
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

    public String getDischPort() {
        return dischPort;
    }

    public void setDischPort(String dischPort) {
        this.dischPort = dischPort;
    }

    public String getLoadPort() {
        return loadPort;
    }

    public void setLoadPort(String loadPort) {
        this.loadPort = loadPort;
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

    public PlanningContLoad getPlanningContLoad() {
        return planningContLoad;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public MasterContDamage getMasterContDamage() {
        return masterContDamage;
    }

    public void setMasterContDamage(MasterContDamage masterContDamage) {
        this.masterContDamage = masterContDamage;
    }

    public MasterPort getPortOfDelivery() {
        return portOfDelivery;
    }

    public void setPortOfDelivery(MasterPort portOfDelivery) {
        this.portOfDelivery = portOfDelivery;
    }

    public boolean getIsExportImport() {
        return isExportImport;
    }

    public void setIsExportImport(boolean isExportImport) {
        this.isExportImport = isExportImport;
    }

    public boolean getIsSling() {
        return isSling;
    }

    public void setIsSling(boolean isSling) {
        this.isSling = isSling;
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
        if (!(object instanceof ServiceContTranshipment)) {
            return false;
        }
        ServiceContTranshipment other = (ServiceContTranshipment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceContTranshipment[id=" + id + "]";
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
