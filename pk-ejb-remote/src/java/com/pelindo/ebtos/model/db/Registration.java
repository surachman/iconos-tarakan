/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterCy;
import com.pelindo.ebtos.model.db.master.MasterService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
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
@Table(name = "registration")
@NamedQueries({
    @NamedQuery(name = "Registration.findAll", query = "SELECT r FROM Registration r"),
    @NamedQuery(name = "Registration.findByNoReg", query = "SELECT r FROM Registration r WHERE r.noReg = :noReg"),
    @NamedQuery(name = "Registration.findByStatusses", query = "SELECT r FROM Registration r WHERE r.statusService IN :statusses ORDER BY r.noReg DESC"),
    @NamedQuery(name = "Registration.findByStatussesAndCreatedDate", query = "SELECT r FROM Registration r WHERE r.statusService IN :statusses AND r.createdDate >= :theDayAtZeroOClock and r.createdDate < :theDayAfterAtZeroOClock ORDER BY r.noReg DESC"),
    @NamedQuery(name = "Registration.findByPpkbAndServiceCode", query = "SELECT r FROM Registration r WHERE r.planningVessel.noPpkb = :noPpkb AND r.masterService.serviceCode = :serviceCode"),
    @NamedQuery(name = "Registration.updatePlanningVesselExceptCancelVessel", query = "UPDATE Registration r SET r.planningVessel = :newValue WHERE r.planningVessel = :oldValue AND r.masterService.serviceName = 'SC023'"),
    @NamedQuery(name = "Registration.findByPaymentType", query = "SELECT r FROM Registration r WHERE r.paymentType = :paymentType"),
    @NamedQuery(name = "Registration.findByStatusService", query = "SELECT r FROM Registration r WHERE r.statusService = :statusService")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "Registration.Native.findRegistrations", query = "SELECT r.no_reg,m.name,ms.service_name,r.status_service,r.no_ppkb from registration r,m_customer m,m_service ms where r.cust_code=m.cust_code and r.service_code=ms.service_code order by substring(no_reg,8,13) DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationByGenerate", query = "SELECT MAX(substring(no_reg,4,10))FROM registration"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationList", query = "select r.no_reg,r.no_ppkb,m.name,vv.voy_in,vv.voy_out,ms.name,mm.service_name from registration r LEFT JOIN planning_vessel pp on (r.no_ppkb=pp.no_ppkb) LEFT join preservice_vessel vv on(pp.booking_code=vv.booking_code)LEFT join m_vessel m on (vv.vessel_code=m.vessel_code) LEFT JOIN m_customer mc on(vv.cust_code=mc.cust_code),m_customer ms,m_service mm where r.cust_code=ms.cust_code AND r.service_code=mm.service_code AND r.service_code='SC001' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationByNoReg", query = "select r.no_reg,r.no_ppkb,m.name,vv.voy_in,vv.voy_out,ms.name,r.status_service from registration r LEFT JOIN planning_vessel pp on (r.no_ppkb=pp.no_ppkb) LEFT join preservice_vessel vv on(pp.booking_code=vv.booking_code)LEFT join m_vessel m on (vv.vessel_code=m.vessel_code) LEFT JOIN m_customer mc on(vv.cust_code=mc.cust_code),m_customer ms where r.cust_code=ms.cust_code AND r.no_reg=?"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationPluggingOnly", query = "SELECT r.no_reg, c.name FROM registration r, m_customer c, m_service s WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND s.service_code = 'SC003' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationRecUC", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC013' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationDeliveryUC", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC014' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationDelivery", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC002' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationBehandle", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC004' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationStriping", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC006' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationReceiving", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC001' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationChangeStatus", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC024' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationReeferDischarge", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC015' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationReeferLoad", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC016' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationReeferLoadServiceOnly", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC016' AND p.status IN ('Served', 'Servicing') ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationDischargeToLoad", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC025' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationPenumpukanSusulan", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC009' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationPenumpukanSusulanUC", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC021' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationAngsur", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC017' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationReceivingBarang", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC018' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationDeliveryBarang", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC019' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationBatalMuat", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC005' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationBatalKapal", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC023' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationCancelDocument", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC022' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationStuffing", query = "SELECT r.no_reg, c.name, r.no_ppkb FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC007' ORDER BY no_reg DESC"),
    //@NamedNativeQuery(name = "Registration.Native.findRegistrationDeliveryReceiving", query = "SELECT r.no_reg, c.name, r.no_ppkb, s.service_name FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND r.status_service = 'approve' AND (s.service_code = 'SC002' OR s.service_code = 'SC001') ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationDeliveryReceiving", query = "SELECT r.no_reg, c.name, r.no_ppkb, s.service_name,r.status_service "
                                                                                            + "FROM registration r "
                                                                                                    + "JOIN m_customer c ON (r.cust_code = c.cust_code) "
                                                                                                    + "JOIN m_service s ON (r.service_code = s.service_code) "
                                                                                                    + "JOIN planning_vessel p ON (r.no_ppkb = p.no_ppkb) "
                                                                                            + "WHERE r.status_service = 'approve' AND s.service_code IN ('SC001', 'SC002') "
                                                                                            + "ORDER BY r.created_date DESC;"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationDeliveryReceivingUC", query = "SELECT r.no_reg, c.name, r.no_ppkb, s.service_name FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.status_service = 'approve' AND r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND (s.service_code = 'SC013' OR s.service_code = 'SC014') ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationStriffingStuffing", query = "SELECT r.no_reg, c.name, r.no_ppkb, s.service_name FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.status_service = 'approve' AND r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND (s.service_code = 'SC007' OR s.service_code = 'SC006') ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationPluggingReefer", query = "SELECT r.no_reg, c.name, r.no_ppkb, s.service_name FROM registration r, m_customer c, m_service s WHERE r.status_service = 'approve' AND r.cust_code = c.cust_code AND r.service_code = s.service_code AND (s.service_code = 'SC016' OR s.service_code = 'SC015') ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationInvoice", query = "SELECT r.no_reg, m.name, ms.service_name, r.status_service FROM registration r, m_customer m, m_service ms WHERE (r.status_service = 'approve' OR r.status_service = 'confirm') AND r.cust_code = m.cust_code and r.service_code = ms.service_code order by no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationInvoiceCash", query = "SELECT r.no_reg, m.name, ms.service_name, r.status_service FROM registration r, m_customer m, m_service ms, invoice i WHERE i.payment_type != 'KREDIT' AND i.no_reg = r.no_reg AND (r.status_service = 'approve' OR r.status_service = 'confirm') AND r.cust_code = m.cust_code and r.service_code = ms.service_code order by no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationInvoiceCashAndCreditConfirm", query = "SELECT r.no_reg, m.name, ms.service_name, r.status_service FROM registration r, m_customer m, m_service ms, invoice i WHERE i.no_reg = r.no_reg AND r.status_service = 'confirm' AND r.cust_code = m.cust_code AND r.service_code = ms.service_code AND i.cancel_invoice=false AND i.no_invoice IS NOT NULL ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationInvoiceCashAndCreditApprove", query = "SELECT r.no_reg, m.name, ms.service_name, r.status_service FROM registration r, m_customer m, m_service ms, invoice i WHERE i.no_reg = r.no_reg AND r.status_service = 'approve' AND r.cust_code = m.cust_code AND r.service_code = ms.service_code AND i.cancel_invoice=false AND i.no_invoice IS NOT NULL ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationInvoiceCashAndCredit", query = "SELECT r.no_reg, m.name, ms.service_name, r.status_service FROM registration r, m_customer m, m_service ms, invoice i WHERE i.no_reg = r.no_reg AND (r.status_service = 'approve' OR r.status_service = 'confirm') AND r.cust_code = m.cust_code AND r.service_code = ms.service_code AND i.cancel_invoice=false AND i.no_invoice IS NOT NULL ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationDeliveryReceivingGoods", query = "SELECT r.no_reg, c.name, r.no_ppkb, s.service_name FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND (s.service_code = 'SC018' OR s.service_code = 'SC019') ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationBehandleJobslip", query = "SELECT r.no_reg, c.name, r.no_ppkb, s.service_name FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.status_service = 'approve' AND r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC004' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationBatalMuatJobslip", query = "SELECT r.no_reg, c.name, r.no_ppkb, s.service_name FROM registration r, m_customer c, m_service s, planning_vessel p WHERE r.status_service = 'approve' AND r.cust_code = c.cust_code AND r.service_code = s.service_code AND r.no_ppkb = p.no_ppkb AND s.service_code = 'SC005' ORDER BY no_reg DESC"),
    @NamedNativeQuery(name = "Registration.Native.findRegistrationGenerateId", query = "SELECT MAX(substring(no_reg,8,13))FROM registration WHERE substring(no_reg,1,6) = ?")})
public class Registration implements Serializable, EntityAuditable {
    public static final String STATUS_APPROVE = "approve";
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
    @Column(name = "no_reg", nullable = false, length = 30)
    private String noReg;
    @Basic(optional = false)
    @Column(name = "payment_type", length = 10)
    private String paymentType;
    @Column(name = "status_service", length = 10)
    private String statusService;
    @Column(name = "is_include_handling")
    private Boolean isIncludeHandling;
    @OneToMany(mappedBy = "registration")
    private List<ReceivingService> receivingServiceList;
    @OneToMany(mappedBy = "registration")
    private List<ReeferLoadService> reeferLoadServiceList;
    @OneToMany(mappedBy = "registration")
    private List<PerhitunganLiftService> perhitunganLiftServiceList;
    @OneToMany(mappedBy = "registration")
    private List<Monitoring> monitoringList;
    @OneToMany(mappedBy = "registration")
    private List<Penumpukan> penumpukanList;
    @OneToMany(mappedBy = "registration")
    private List<PenumpukanSusulan> penumpukanSusulanList;
    @OneToMany(mappedBy = "registration")
    private List<Plugging> pluggingList;
    @OneToMany(mappedBy = "registration")
    private List<PerhitunganPenumpukanSusulan> perhitunganPenumpukanSusulanList;
    @OneToMany(mappedBy = "registration")
    private List<PerhitunganAngsur> perhitunganAngsurList;
    @OneToMany(mappedBy = "registration")
    private List<AngsurService> angsurServiceList;
    @OneToMany(mappedBy = "registration")
    private List<Angsur> angsurList;
    @OneToMany(mappedBy = "registration")
    private List<PerhitunganMonitoring> perhitunganMonitoringList;
    @OneToMany(mappedBy = "registration")
    private List<DeliveryService> deliveryServiceList;
    @OneToMany(mappedBy = "registration")
    private List<ChangeStatusService> changeStatusServiceList;
    @OneToMany(mappedBy = "registration")
    private List<ReceivingUc> receivingUcList;
    @OneToMany(mappedBy = "registration")
    private List<LiftService> liftServiceList;
    @OneToMany(mappedBy = "registration")
    private List<PenumpukanSusulanService> penumpukanSusulanServiceList;
    @OneToMany(mappedBy = "registration")
    private List<PerhitunganPenumpukan> perhitunganPenumpukanList;
    @OneToMany(mappedBy = "registration")
    private List<ChangeStatus> changeStatusList;
    @OneToMany(mappedBy = "registration")
    private List<PluggingReeferService> pluggingReeferServiceList;
    @OneToMany(mappedBy = "registration")
    private List<ReeferDischargeService> reeferDischargeServiceList;
    @OneToMany(mappedBy = "registration")
    private List<PerhitunganPlugging> perhitunganPluggingList;
    @OneToMany(mappedBy = "registration")
    private List<UcPerhitunganPenumpukan> ucPerhitunganPenumpukanList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "registration")
    private List<PerhitunganPassGate> perhitunganPassGateList;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", nullable = true)
    @ManyToOne
    private PlanningVessel planningVessel;
    @JoinColumn(name = "service_code", referencedColumnName = "service_code")
    @ManyToOne
    private MasterService masterService;
    @JoinColumn(name = "cust_code", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer masterCustomer;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "registration")
    private Invoice invoice;
    @OneToMany(mappedBy = "registration")
    private List<PerhitunganChangeStatus> perhitunganChangeStatusList;
    @Column(name = "pengurus_do", length = 100)
    private String pengurusDo;
    @OneToMany(mappedBy = "registration")
    private List<UcReceivingService> ucReceivingServiceList;
    @Column(name = "no_ppkb_plug",  length = 50)
    private String no_ppkb_plug;
    @JoinColumn(name = "yard", referencedColumnName = "yard")
    @ManyToOne
    private MasterCy masterCy;

    public Registration() {
    }

    public Registration(String noReg) {
        this.noReg = noReg;
    }

    public Registration(String noReg, String paymentType) {
        this.noReg = noReg;
        this.paymentType = paymentType;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatusService() {
        return statusService;
    }

    public void setStatusService(String statusService) {
        this.statusService = statusService;
    }

    public Boolean getIsIncludeHandling() {
        return isIncludeHandling;
    }

    public void setIsIncludeHandling(Boolean isIncludeHandling) {
        this.isIncludeHandling = isIncludeHandling;
    }

    public List<ReceivingService> getReceivingServiceList() {
        return receivingServiceList;
    }

    public void setReceivingServiceList(List<ReceivingService> receivingServiceList) {
        this.receivingServiceList = receivingServiceList;
    }

    public List<ReeferLoadService> getReeferLoadServiceList() {
        return reeferLoadServiceList;
    }

    public void setReeferLoadServiceList(List<ReeferLoadService> reeferLoadServiceList) {
        this.reeferLoadServiceList = reeferLoadServiceList;
    }

    public List<PerhitunganLiftService> getPerhitunganLiftServiceList() {
        return perhitunganLiftServiceList;
    }

    public void setPerhitunganLiftServiceList(List<PerhitunganLiftService> perhitunganLiftServiceList) {
        this.perhitunganLiftServiceList = perhitunganLiftServiceList;
    }

    public List<Monitoring> getMonitoringList() {
        return monitoringList;
    }

    public void setMonitoringList(List<Monitoring> monitoringList) {
        this.monitoringList = monitoringList;
    }

    public List<Penumpukan> getPenumpukanList() {
        return penumpukanList;
    }

    public void setPenumpukanList(List<Penumpukan> penumpukanList) {
        this.penumpukanList = penumpukanList;
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

    public List<AngsurService> getAngsurServiceList() {
        return angsurServiceList;
    }

    public void setAngsurServiceList(List<AngsurService> angsurServiceList) {
        this.angsurServiceList = angsurServiceList;
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

    public List<DeliveryService> getDeliveryServiceList() {
        return deliveryServiceList;
    }

    public void setDeliveryServiceList(List<DeliveryService> deliveryServiceList) {
        this.deliveryServiceList = deliveryServiceList;
    }

    public List<ChangeStatusService> getChangeStatusServiceList() {
        return changeStatusServiceList;
    }

    public void setChangeStatusServiceList(List<ChangeStatusService> changeStatusServiceList) {
        this.changeStatusServiceList = changeStatusServiceList;
    }

    public List<ReceivingUc> getReceivingUcList() {
        return receivingUcList;
    }

    public void setReceivingUcList(List<ReceivingUc> receivingUcList) {
        this.receivingUcList = receivingUcList;
    }

    public List<LiftService> getLiftServiceList() {
        return liftServiceList;
    }

    public void setLiftServiceList(List<LiftService> liftServiceList) {
        this.liftServiceList = liftServiceList;
    }

    public List<PenumpukanSusulanService> getPenumpukanSusulanServiceList() {
        return penumpukanSusulanServiceList;
    }

    public void setPenumpukanSusulanServiceList(List<PenumpukanSusulanService> penumpukanSusulanServiceList) {
        this.penumpukanSusulanServiceList = penumpukanSusulanServiceList;
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

    public List<PluggingReeferService> getPluggingReeferServiceList() {
        return pluggingReeferServiceList;
    }

    public void setPluggingReeferServiceList(List<PluggingReeferService> pluggingReeferServiceList) {
        this.pluggingReeferServiceList = pluggingReeferServiceList;
    }

    public List<ReeferDischargeService> getReeferDischargeServiceList() {
        return reeferDischargeServiceList;
    }

    public void setReeferDischargeServiceList(List<ReeferDischargeService> reeferDischargeServiceList) {
        this.reeferDischargeServiceList = reeferDischargeServiceList;
    }

    public List<PerhitunganPlugging> getPerhitunganPluggingList() {
        return perhitunganPluggingList;
    }

    public void setPerhitunganPluggingList(List<PerhitunganPlugging> perhitunganPluggingList) {
        this.perhitunganPluggingList = perhitunganPluggingList;
    }

    public PlanningVessel getPlanningVessel() {
        return planningVessel;
    }

    public void setPlanningVessel(PlanningVessel planningVessel) {
        this.planningVessel = planningVessel;
    }

    public MasterService getMasterService() {
        return masterService;
    }

    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }

    public MasterCustomer getMasterCustomer() {
        return masterCustomer;
    }

    public void setMasterCustomer(MasterCustomer masterCustomer) {
        this.masterCustomer = masterCustomer;
    }

    public List<PerhitunganChangeStatus> getPerhitunganChangeStatusList() {
        return perhitunganChangeStatusList;
    }

    public void setPerhitunganChangeStatusList(List<PerhitunganChangeStatus> perhitunganChangeStatusList) {
        this.perhitunganChangeStatusList = perhitunganChangeStatusList;
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

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getPengurusDo() {
        return pengurusDo;
    }

    public void setPengurusDo(String pengurusDo) {
        this.pengurusDo = pengurusDo;
    }

    public List<UcReceivingService> getUcReceivingServiceList() {
        return ucReceivingServiceList;
    }

    public void setUcReceivingServiceList(List<UcReceivingService> ucReceivingServiceList) {
        this.ucReceivingServiceList = ucReceivingServiceList;
    }

    public String getNo_ppkb_plug() {
        return no_ppkb_plug;
    }

    public void setNo_ppkb_plug(String no_ppkb_plug) {
        this.no_ppkb_plug = no_ppkb_plug;
    }

    public MasterCy getMasterCy() {
        return masterCy;
    }

    public void setMasterCy(MasterCy mCy) {
        this.masterCy = mCy;
    }

    public List<UcPerhitunganPenumpukan> getUcPerhitunganPenumpukanList() {
        return ucPerhitunganPenumpukanList;
    }

    public void setUcPerhitunganPenumpukanList(List<UcPerhitunganPenumpukan> ucPerhitunganPenumpukanList) {
        this.ucPerhitunganPenumpukanList = ucPerhitunganPenumpukanList;
    }

    public List<PerhitunganPassGate> getPerhitunganPassGateList() {
        return perhitunganPassGateList;
    }

    public void setPerhitunganPassGateList(List<PerhitunganPassGate> perhitunganPassGateList) {
        this.perhitunganPassGateList = perhitunganPassGateList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noReg != null ? noReg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registration)) {
            return false;
        }
        Registration other = (Registration) object;
        if ((this.noReg == null && other.noReg != null) || (this.noReg != null && !this.noReg.equals(other.noReg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.Registration[noReg=" + noReg + "]";
    }
}
