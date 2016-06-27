/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
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
import javax.persistence.JoinColumns;
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
@Table(name = "penumpukan_susulan_service")
@NamedQueries({
    @NamedQuery(name = "PenumpukanSusulanService.findAll", query = "SELECT p FROM PenumpukanSusulanService p"),
    @NamedQuery(name = "PenumpukanSusulanService.findByJobSlip", query = "SELECT p FROM PenumpukanSusulanService p WHERE p.jobSlip = :jobSlip"),
    @NamedQuery(name = "PenumpukanSusulanService.findByContNo", query = "SELECT p FROM PenumpukanSusulanService p WHERE p.contNo = :contNo"),
    @NamedQuery(name = "PenumpukanSusulanService.findByContSize", query = "SELECT p FROM PenumpukanSusulanService p WHERE p.contSize = :contSize"),
    @NamedQuery(name = "PenumpukanSusulanService.findByContStatus", query = "SELECT p FROM PenumpukanSusulanService p WHERE p.contStatus = :contStatus"),
    @NamedQuery(name = "PenumpukanSusulanService.findByContGross", query = "SELECT p FROM PenumpukanSusulanService p WHERE p.contGross = :contGross"),
    @NamedQuery(name = "PenumpukanSusulanService.findByOverSize", query = "SELECT p FROM PenumpukanSusulanService p WHERE p.overSize = :overSize"),
    @NamedQuery(name = "PenumpukanSusulanService.findByDg", query = "SELECT p FROM PenumpukanSusulanService p WHERE p.dg = :dg"),
    @NamedQuery(name = "PenumpukanSusulanService.findByDgLabel", query = "SELECT p FROM PenumpukanSusulanService p WHERE p.dgLabel = :dgLabel"),
    @NamedQuery(name = "PenumpukanSusulanService.findByPlacementDate", query = "SELECT p FROM PenumpukanSusulanService p WHERE p.placementDate = :placementDate"),
    @NamedQuery(name = "PenumpukanSusulanService.findByMasa1", query = "SELECT p FROM PenumpukanSusulanService p WHERE p.masa1 = :masa1"),
    @NamedQuery(name = "PenumpukanSusulanService.findByMasa2", query = "SELECT p FROM PenumpukanSusulanService p WHERE p.masa2 = :masa2")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PenumpukanSusulanService.Native.findByPpkbNReg", query = "SELECT ds.job_slip, c.name, ds.cont_no, ds.cont_size, ct.name, ds.cont_status, ds.cont_gross, change(ds.over_size), ds.placement_date, ds.valid_date, MAX(ds.created_date) "
                                                                                    + "FROM penumpukan_susulan_service ds "
                                                                                            + "JOIN m_commodity c ON (ds.commodity_code = c.commodity_code) "
                                                                                            + "JOIN m_container_type ct ON (ds.cont_type = ct.cont_type) "
                                                                                    + "WHERE ds.no_ppkb = ? AND ds.no_reg = ? "
                                                                                    + "GROUP BY ds.job_slip, c.name, ds.cont_no, ds.cont_size, ct.name, ds.cont_status, ds.cont_gross, ds.over_size, ds.placement_date, ds.valid_date"),
    @NamedNativeQuery(name = "PenumpukanSusulanService.Native.generateId", query = "SELECT MAX(substring(job_slip,7,5))FROM penumpukan_susulan_service WHERE substring(job_slip,3,4) = ?"),
    @NamedNativeQuery(name = "PenumpukanSusulanService.Native.findInvoice", query = "SELECT ds.job_slip FROM penumpukan_susulan_service ds WHERE ds.cont_no = ? AND ds.no_ppkb = ? AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "PenumpukanSusulanService.Native.perhitungan", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_status, ds.cont_size, m.type_in_general as name, CASE WHEN ds.over_size=true THEN 'Yes' WHEN ds.over_size=false THEN 'No' END as over_size, CASE WHEN ds.dg=true THEN 'Yes' WHEN ds.dg=false THEN 'No' END as dg,CASE WHEN ds.dg_label=true THEN 'Yes' WHEN ds.dg_label=false THEN 'No' END as dg_label, ds.placement_date, ds.masa_1, ds.masa_2, pp.charge_m1, pp.charge_m2, pp.total_charge,c.symbol,c.language,c.country,ds.bl_no,ds.no_ppkb, ds.id FROM penumpukan_susulan_service ds, perhitungan_penumpukan_susulan pp,m_container_type m,m_currency c WHERE pp.cont_no = ds.cont_no AND pp.curr_code=c.curr_code AND ds.cont_type=m.cont_type AND pp.no_reg = ds.no_reg AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "PenumpukanSusulanService.Native.findByListBatalNotaService", query = "select d.job_slip,d.no_reg,d.no_ppkb,d.cont_no,d.id from penumpukan_susulan_service d where d.no_ppkb=? AND d.no_reg=?"),
    @NamedNativeQuery(name = "PenumpukanSusulanService.Native.findCekValidasi", query = "SELECT ds.job_slip FROM penumpukan_susulan_service ds WHERE ds.cont_no = ? AND ds.no_ppkb = ? AND ds.no_reg = ?")})
public class PenumpukanSusulanService implements Serializable, EntityAuditable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "job_slip", nullable = false, length = 30)
    private String jobSlip;
    @Column(name = "cont_no", length = 11)
    private String contNo;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_status", length = 10)
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "over_size")
    private Boolean overSize;
    @Column(name = "dg")
    private Boolean dg;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @Column(name = "masa_1")
    private Short masa1;
    @Column(name = "masa_2")
    private Short masa2;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg")
    @ManyToOne
    private Registration registration;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @Column(name = "valid_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validDate;
    @ManyToOne
    @JoinColumns(value = {
        @JoinColumn(name = "cont_no", referencedColumnName = "cont_no", insertable = false, updatable = false),
        @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", insertable = false, updatable = false)
    })
    private DeliveryService deliveryService;
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
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;

    public PenumpukanSusulanService() {
    }

    public PenumpukanSusulanService(String jobSlip) {
        this.jobSlip = jobSlip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobSlip() {
        return jobSlip;
    }

    public void setJobSlip(String jobSlip) {
        this.jobSlip = jobSlip;
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

    public Boolean getOverSize() {
        return overSize;
    }

    public void setOverSize(Boolean overSize) {
        this.overSize = overSize;
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

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public Short getMasa1() {
        return masa1;
    }

    public void setMasa1(Short masa1) {
        this.masa1 = masa1;
    }

    public Short getMasa2() {
        return masa2;
    }

    public void setMasa2(Short masa2) {
        this.masa2 = masa2;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
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

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
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

    public boolean isTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobSlip != null ? jobSlip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PenumpukanSusulanService)) {
            return false;
        }
        PenumpukanSusulanService other = (PenumpukanSusulanService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PenumpukanSusulanService[id=" + id + "]";
    }
}
