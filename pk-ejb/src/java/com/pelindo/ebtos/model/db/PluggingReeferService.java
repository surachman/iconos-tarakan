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
@Table(name = "plugging_reefer_service")
@NamedQueries({
    @NamedQuery(name = "PluggingReeferService.findAll", query = "SELECT p FROM PluggingReeferService p"),
    @NamedQuery(name = "PluggingReeferService.findByJobSlip", query = "SELECT p FROM PluggingReeferService p WHERE p.jobSlip = :jobSlip"),
    @NamedQuery(name = "PluggingReeferService.findByContNo", query = "SELECT p FROM PluggingReeferService p WHERE p.contNo = :contNo"),
    @NamedQuery(name = "PluggingReeferService.findByContSize", query = "SELECT p FROM PluggingReeferService p WHERE p.contSize = :contSize"),
    @NamedQuery(name = "PluggingReeferService.findByContStatus", query = "SELECT p FROM PluggingReeferService p WHERE p.contStatus = :contStatus"),
    @NamedQuery(name = "PluggingReeferService.findByContGross", query = "SELECT p FROM PluggingReeferService p WHERE p.contGross = :contGross"),
    @NamedQuery(name = "PluggingReeferService.findByOverSize", query = "SELECT p FROM PluggingReeferService p WHERE p.overSize = :overSize"),
    @NamedQuery(name = "PluggingReeferService.findByDg", query = "SELECT p FROM PluggingReeferService p WHERE p.dg = :dg"),
    @NamedQuery(name = "PluggingReeferService.findByDgLabel", query = "SELECT p FROM PluggingReeferService p WHERE p.dgLabel = :dgLabel"),
    @NamedQuery(name = "PluggingReeferService.findByShiftPlanning", query = "SELECT p FROM PluggingReeferService p WHERE p.shiftPlanning = :shiftPlanning")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "PluggingReeferService.Native.findByNoReg", query = "SELECT prs.job_slip, prs.cont_no, prs.cont_size, ct.name, prs.cont_status, prs.cont_gross, CASE WHEN prs.over_size='TRUE' THEN 'Yes' WHEN prs.over_size='FALSE' THEN 'No' END as over_size, prs.shift_planning "
                                                                                + "FROM plugging_reefer_service prs "
                                                                                        + "JOIN registration r ON (r.no_reg = prs.no_reg) "
                                                                                        + "JOIN m_container_type ct ON (ct.cont_type = prs.cont_type) "
                                                                                        + "LEFT JOIN service_reefer sr ON (prs.no_reg=sr.no_reg AND prs.cont_no=sr.cont_no) "
                                                                                        + "LEFT JOIN plugging_only_rekap pr ON (prs.job_slip=pr.job_slip) "
                                                                                + "WHERE ct.type_in_general = 'RFR' AND r.no_reg = ? AND sr.cont_no IS NULL AND pr.job_slip IS NULL "
                                                                                + "ORDER BY SUBSTRING(prs.job_slip,7,4) DESC"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.generateId", query = "SELECT MAX(substring(job_slip,7,5))FROM plugging_reefer_service WHERE substring(job_slip,3,4) = ?"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findContainerReefer", query = "SELECT prs.cont_type, prs.cont_no, prs.cont_size, prs.cont_status, prs.no_reg "
                                                                            + "FROM plugging_reefer_service prs "
                                                                                    + "JOIN registration r ON (r.no_reg = prs.no_reg) "
                                                                                    + "JOIN m_container_type ct ON (ct.cont_type = prs.cont_type) "
                                                                                    + "LEFT JOIN service_reefer sr ON (prs.cont_no = sr.cont_no AND sr.no_reg = prs.no_reg) "
                                                                            + "WHERE ct.type_in_general = 'RFR' AND prs.cont_no = ? AND sr.cont_no IS NULL;"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findInvoice", query = "SELECT job_slip FROM plugging_reefer_service WHERE cont_no = ? AND no_reg = ?"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.perhitungan", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_status, ds.cont_size, ds.cont_type, ds.shift_planning, pp.charge, pp.total_charge, pm.charge, pm.total_charge, c.symbol, c.language, c.country "
    + "FROM plugging_reefer_service ds, perhitungan_plugging pp, perhitungan_monitoring pm, m_currency c "
    + "WHERE pp.cont_no = ds.cont_no AND pm.cont_no = ds.cont_no AND pp.no_reg = ds.no_reg AND pm.no_reg = ds.no_reg AND pp.curr_code = c.curr_code AND ds.no_reg = ?"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findByReg", query = "SELECT prs.job_slip, prs.cont_no, prs.cont_size, ct.type_in_general as name, prs.cont_status, prs.cont_gross, CASE WHEN prs.over_size='TRUE' THEN 'Yes' WHEN prs.over_size='FALSE' THEN 'No' END as over_size FROM plugging_reefer_service prs, m_container_type ct WHERE prs.cont_type = ct.cont_type AND prs.no_reg = ?"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findByRegPluggingDelivery", query = "SELECT prs.job_slip, prs.cont_no, prs.cont_size, ct.name, prs.cont_status, prs.cont_gross, CASE WHEN prs.over_size='TRUE' THEN 'Yes' WHEN prs.over_size='FALSE' THEN 'No' END as over_size FROM plugging_reefer_service prs, m_container_type ct WHERE prs.cont_type = ct.cont_type AND prs.no_reg_del = ? AND job_slip IN (select p.job_slip from plugging_only_rekap p) order by job_slip desc"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findJobSlipByPPKBnCONT", query = "SELECT p.job_slip FROM plugging_reefer_service p, registration r WHERE p.no_reg = r.no_reg AND r.no_ppkb = ? AND p.cont_no =?"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findByNoregValidasiPrint", query = "select ds.job_slip from plugging_reefer_service ds where ds.counter_print >=1 AND ds.no_reg=?"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findByNoregPlugingReefer", query = "SELECT ds.job_slip, ds.cont_no, ds.cont_status, ds.cont_size, mt.name,ds.created_date, ds.shift_planning, pp.charge, pp.total_charge, pm.charge, pm.total_charge, c.symbol, c.language, c.country FROM m_container_type mt,((perhitungan_plugging pp RIGHT JOIN m_currency c ON pp.curr_code = c.curr_code) RIGHT JOIN plugging_reefer_service ds ON pp.no_reg = ds.no_reg AND pp.cont_no = ds.cont_no) LEFT JOIN perhitungan_monitoring pm ON pm.no_reg = ds.no_reg AND pm.cont_no = ds.cont_no where mt.cont_type=ds.cont_type AND ds.no_reg=?"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findPluggingReeferServiceByJobSlip", query = "select pp.job_slip,r.no_ppkb_plug,pp.cont_no,pp.bl_no,mm.iso_code, r.no_reg from plugging_reefer_service pp,registration r,m_container_type mm where pp.no_reg=r.no_reg AND pp.cont_type=mm.cont_type AND pp.job_slip NOT IN (select job_slip from service_gate_receiving) AND pp.job_slip=? ORDER BY substring(pp.job_slip,7,4) DESC"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findSelectPluggingReeferService", query = "select rs.job_slip,rs.bl_no,rs.cont_no,rs.cont_size,rs.cont_status,m.name from plugging_reefer_service rs,m_commodity m where rs.commodity_code=m.commodity_code and rs.status='01' AND rs.job_slip IN (select sg.job_slip from service_gate_receiving sg) order by rs.job_slip DESC"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findSelectDeliveryPluggingReeferService", query = "select pr.*,r.cust_code from plugging_reefer_service pr,registration r where pr.no_reg=r.no_reg AND (pr.job_slip NOT IN (select p.job_slip from plugging_only_rekap p)) AND pr.job_slip IN(select sr.plugging_reefer_service_id from service_reefer sr where pr.job_slip=sr.plugging_reefer_service_id) AND status='02' AND r.cust_code=? order by pr.job_slip desc"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findPluggingReeferServiceByGatePlugging", query = "SELECT pp.job_slip,pp.no_ppkb,pp.cont_no,pp.bl_no,mm.iso_code, r.no_reg FROM plugging_reefer_service pp,m_container_type mm,registration r where pp.cont_type=mm.cont_type AND pp.no_reg=r.no_reg AND pp.cont_no=? AND pp.job_slip NOT IN (select d.job_slip from service_gate_delivery d) AND ROWNUM <= 1"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findPluggingReeferServiceByDeliveryConfirm", query = "select pr.*,sr.id from plugging_reefer_service pr,service_receiving sr where pr.status='02' AND pr.job_slip IN (select p.job_slip from plugging_only_rekap p) AND pr.job_slip IN (select sg.job_slip from service_gate_delivery sg) AND sr.cont_no=pr.cont_no AND sr.id_plugging_reefer=pr.job_slip order by pr.job_slip desc"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findPluggingReeferServiceByDeliveryConfirmList", query = "SELECT r.cont_no, r.cont_size, m.name, r.cont_status,r.cont_gross, r.seal_no,r.y_slot,r.y_row,r.y_tier,r.id,r.block,sr.job_slip,sr.no_ppkb FROM service_delivery r,m_container_type m ,plugging_reefer_service sr WHERE r.cont_type=m.cont_type AND r.cont_no=sr.cont_no AND sr.status='04' ORDER BY r.id DESC"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findPerhitunganDeliveryPluggingService", query = "select ps.job_slip, ps.cont_no, ps.cont_status, mt.name,ps.cont_size,ps.shift_planning, pr.massa1,pr.massa2,pr.charge_massa1,pr.charge_massa2,pr.charge_receiving_lift, pr.charge_delivery_lift, pp.total_charge,pm.total_charge,pp.id,pm.id,pr.no_reg,pr.total_charge from plugging_only_rekap pr,plugging_reefer_service ps,m_container_type mt ,perhitungan_monitoring pm,perhitungan_plugging pp where pr.no_reg=? AND ps.job_slip=pr.job_slip AND pm.no_reg=pr.no_reg AND pp.no_reg=pr.no_reg AND pm.cont_no=ps.cont_no AND pp.cont_no=ps.cont_no AND mt.cont_type=ps.cont_type order by pr.job_slip DESC"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findChangeStatusPluggingToLoad", query = "select pr.job_slip, pr.no_reg, pr.cont_type, pr.commodity_code, pr.cont_no, pr.cont_size, pr.cont_status, pr.cont_gross, pr.over_size, pr.dg, pr.dg_label, pr.shift_planning, pr.bl_no, pr.created_by, pr.created_date, pr.modified_by, pr.modified_date, pr.counter_print, pr.no_ppkb, pr.placement_date, pr.status, mt.name, m.name as commodity, null as ppkb, null as port, pr.mlo from plugging_reefer_service pr,m_container_type mt,m_commodity m where pr.status='02' AND pr.job_slip NOT IN(select ps.job_slip from plugging_only_rekap ps) AND pr.job_slip IN (SELECT sr.plugging_reefer_service_id from service_reefer sr) AND pr.cont_type=mt.cont_type AND pr.commodity_code=m.commodity_code"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findUpdateServiceReceiving", query = "select distinct(s.id),pr.job_slip from service_receiving s,equipment e,plugging_reefer_service pr where e.cont_no=pr.cont_no AND e.no_ppkb_plug=pr.no_ppkb AND s.cont_no=pr.cont_no AND e.cont_no=s.cont_no AND s.cont_no=? AND pr.no_ppkb=? AND ROWNUM <= 1 order by s.id desc"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findPreviewServiceReceivingPlugging", query = "select sr.*,ps.job_slip,r.no_ppkb_plug,mp1.name,mp2.name,mc.name,mt.name from service_receiving sr,plugging_reefer_service ps,registration r,planning_cont_receiving pc, m_port mp1,m_port mp2,m_commodity mc,m_container_type mt where sr.no_ppkb=ps.no_ppkb AND sr.cont_no=ps.cont_no AND r.no_reg=ps.no_reg AND pc.no_ppkb=sr.no_ppkb AND pc.cont_no=sr.cont_no AND mp1.port_code=pc.load_port AND mp2.port_code=pc.disch_port AND mc.commodity_code=pc.commodity_code AND ps.status='03' AND mt.cont_type=pc.cont_type"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findPluggingReeferValidasiInput", query = "select job_slip from plugging_reefer_service where cont_no=? AND no_ppkb=? AND no_reg=?"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findSelectPluggingReeferServiceReceivingHht", query = "select rs.job_slip,rs.cont_no,rs.no_ppkb,null as block from plugging_reefer_service rs where rs.status='01' AND rs.job_slip IN (select sg.job_slip from service_gate_receiving sg) AND rs.cont_no=null AND ROWNUM <= 1 order by rs.job_slip DESC"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findPluggingReeferServiceByDeliveryConfirmHht", query = "select pr.job_slip,pr.cont_no,pr.no_ppkb,sr.y_slot,sr.y_row,sr.y_tier,sr.block,null as is_behandle,null as is_cfs,null as is_inspection,sr.id from plugging_reefer_service pr,service_receiving sr where pr.status='02' AND pr.job_slip IN (select p.job_slip from plugging_only_rekap p) AND pr.job_slip IN (select sg.job_slip from service_gate_delivery sg)AND sr.cont_no=pr.cont_no AND pr.cont_no=? AND ROWNUM <= 1 order by pr.job_slip desc"),
    @NamedNativeQuery(name = "PluggingReeferService.Native.findByRegCancelInvoice", query = "SELECT prs.job_slip, prs.cont_no, prs.cont_size, ct.name, prs.cont_status, prs.cont_gross, CASE WHEN prs.over_size='TRUE' THEN 'Yes' WHEN prs.over_size='FALSE' THEN 'No' END as over_size FROM plugging_reefer_service prs, m_container_type ct WHERE prs.cont_type = ct.cont_type AND prs.no_reg_del = ?")})
public class PluggingReeferService implements Serializable, EntityAuditable {

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
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
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
    private String overSize;
    @Column(name = "dg")
    private String dg;
    @Column(name = "dg_label")
    private String dgLabel;
    @Column(name = "shift_planning")
    private Short shiftPlanning=0;
    @Column(name = "no_ppkb")
    private String noPpkb;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "no_reg", referencedColumnName = "no_reg")
    @ManyToOne
    private Registration registration;
//    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
//    @ManyToOne
//    private PlanningVessel planningVessel;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type")
    @ManyToOne
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code")
    @ManyToOne
    private MasterCommodity masterCommodity;
    @Column(name = "counter_print")
    private Integer counterPrint = 0;
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @Column(name = "no_reg_del", length = 50)
    private String noRegDel;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;

    public PluggingReeferService() {
    }

    public PluggingReeferService(String jobSlip) {
        this.jobSlip = jobSlip;
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

    public Short getShiftPlanning() {
        return shiftPlanning;
    }

    public void setShiftPlanning(Short shiftPlanning) {
        this.shiftPlanning = shiftPlanning;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
//
//    public PlanningVessel getPlanningVessel() {
//        return planningVessel;
//    }
//
//    public void setPlanningVessel(PlanningVessel planningVessel) {
//        this.planningVessel = planningVessel;
//    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobSlip != null ? jobSlip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PluggingReeferService)) {
            return false;
        }
        PluggingReeferService other = (PluggingReeferService) object;
        if ((this.jobSlip == null && other.jobSlip != null) || (this.jobSlip != null && !this.jobSlip.equals(other.jobSlip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.PluggingReeferService[jobSlip=" + jobSlip + "]";
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

    public Integer getCounterPrint() {
        return counterPrint;
    }

    public void setCounterPrint(Integer counterPrint) {
        this.counterPrint = counterPrint;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public String getNoRegDel() {
        return noRegDel;
    }

    public void setNoRegDel(String noRegDel) {
        this.noRegDel = noRegDel;
    }

    public MasterCustomer getMlo() {
        return mlo;
    }

    public void setMlo(MasterCustomer mlo) {
        this.mlo = mlo;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
