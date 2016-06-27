/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import com.pelindo.ebtos.model.db.master.MasterContainerType;
import com.pelindo.ebtos.model.db.master.MasterCommodity;
import com.pelindo.ebtos.model.db.master.MasterActivity;
import com.pelindo.ebtos.model.db.master.MasterContDamage;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import java.util.Date;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author senoanggoro
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "service_shifting")
@NamedQueries({
    @NamedQuery(name = "ServiceShifting.findAll", query = "SELECT s FROM ServiceShifting s"),
    @NamedQuery(name = "ServiceShifting.findById", query = "SELECT s FROM ServiceShifting s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceShifting.findByCrane", query = "SELECT s FROM ServiceShifting s WHERE s.crane = :crane"),
    @NamedQuery(name = "ServiceShifting.findByContNo", query = "SELECT s FROM ServiceShifting s WHERE s.contNo = :contNo"),
    @NamedQuery(name = "ServiceShifting.findByNoPpkbAndContNo", query = "SELECT s FROM ServiceShifting s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.contNo = :contNo"),
    @NamedQuery(name = "ServiceShifting.findByNoPpkbContNoAndReshippingStatus", query = "SELECT s FROM ServiceShifting s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.contNo = :contNo AND s.isReshipping = :isReshipping"),
    @NamedQuery(name = "ServiceShifting.findByContSize", query = "SELECT s FROM ServiceShifting s WHERE s.contSize = :contSize"),
    @NamedQuery(name = "ServiceShifting.findByContStatus", query = "SELECT s FROM ServiceShifting s WHERE s.contStatus = :contStatus"),
    @NamedQuery(name = "ServiceShifting.findByContGross", query = "SELECT s FROM ServiceShifting s WHERE s.contGross = :contGross"),
    @NamedQuery(name = "ServiceShifting.findBySealNo", query = "SELECT s FROM ServiceShifting s WHERE s.sealNo = :sealNo"),
    @NamedQuery(name = "ServiceShifting.findByOverSize", query = "SELECT s FROM ServiceShifting s WHERE s.overSize = :overSize"),
    @NamedQuery(name = "ServiceShifting.findByDg", query = "SELECT s FROM ServiceShifting s WHERE s.dg = :dg"),
    @NamedQuery(name = "ServiceShifting.findByDgLabel", query = "SELECT s FROM ServiceShifting s WHERE s.dgLabel = :dgLabel"),
    @NamedQuery(name = "ServiceShifting.findByLoadPort", query = "SELECT s FROM ServiceShifting s WHERE s.loadPort = :loadPort"),
    @NamedQuery(name = "ServiceShifting.findByDischPort", query = "SELECT s FROM ServiceShifting s WHERE s.dischPort = :dischPort"),
    @NamedQuery(name = "ServiceShifting.findByVBay", query = "SELECT s FROM ServiceShifting s WHERE s.vBay = :vBay"),
    @NamedQuery(name = "ServiceShifting.findByVRow", query = "SELECT s FROM ServiceShifting s WHERE s.vRow = :vRow"),
    @NamedQuery(name = "ServiceShifting.findContainerShiftingByPositionAndShiftDestination", query = "SELECT s FROM ServiceShifting s WHERE s.position = :position AND s.shiftTo = :shiftTo AND s.serviceVessel.noPpkb = :noPpkb AND s.contNo = :contNo"),
    @NamedQuery(name = "ServiceShifting.findContainerShiftingByContNoPositionAndShiftDestination", query = "SELECT s FROM ServiceShifting s WHERE s.position = :position AND s.shiftTo = :shiftTo AND s.contNo = :contNo"),
    @NamedQuery(name = "ServiceShifting.findByPositionAndContNo", query = "SELECT s FROM ServiceShifting s WHERE s.position = :position AND s.serviceVessel.noPpkb = :noPpkb AND s.contNo = :contNo"),
    @NamedQuery(name = "ServiceShifting.findByVTier", query = "SELECT s FROM ServiceShifting s WHERE s.vTier = :vTier"),
    @NamedQuery(name = "ServiceShifting.findByShiftTo", query = "SELECT s FROM ServiceShifting s WHERE s.shiftTo = :shiftTo"),
    @NamedQuery(name = "ServiceShifting.findByIsReshipping", query = "SELECT s FROM ServiceShifting s WHERE s.isReshipping = :isReshipping"),
    @NamedQuery(name = "ServiceShifting.findByNoPpkbAndReshippingStatus", query = "SELECT s FROM ServiceShifting s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.isReshipping = :isReshipping"),
    @NamedQuery(name = "ServiceShifting.findByNotReshippedShiftingByNoPpkb", query = "SELECT s FROM ServiceShifting s WHERE s.serviceVessel.noPpkb = :noPpkb AND NOT ((s.isReshipping = TRUE AND s.shiftTo <> 'TOCY') OR (s.position = '05' AND s.shiftTo = 'TOCY'))"),
    @NamedQuery(name = "ServiceShifting.findByOperation", query = "SELECT s FROM ServiceShifting s WHERE s.operation = :operation"),
    @NamedQuery(name = "ServiceShifting.findByIsPaid", query = "SELECT s FROM ServiceShifting s WHERE s.isPaid = :isPaid"),
    @NamedQuery(name = "ServiceShifting.findByNoPpkbAndStatusPaid", query = "SELECT s FROM ServiceShifting s WHERE s.serviceVessel.noPpkb = :noPpkb AND s.isPaid = :isPaid"),
    @NamedQuery(name = "ServiceShifting.updateMasterActivityByNoPpkbAndStatusPaid", query = "UPDATE ServiceShifting s SET s.masterActivity = :masterActivity WHERE s.serviceVessel.noPpkb = :noPpkb AND s.isPaid = :isPaid")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "ServiceShifting.Native.findServiceShiftingReshippingFalse", query = "SELECT ss.id, ss.cont_no, ss.cont_size, c.name, ss.cont_status, ss.cont_gross, ss.seal_no, ss.shift_to, ss.v_bay, ss.v_row, ss.v_tier "
                                                                                                + "FROM service_shifting as ss "
                                                                                                        + "JOIN m_container_type as c on (ss.cont_type = c.cont_type) "
                                                                                                        + "LEFT JOIN equipment e ON (e.no_ppkb = ss.no_ppkb AND e.cont_no = ss.cont_no AND (e.operation LIKE 'SHIFTING%' OR e.operation = 'RESHIPPING')) "
                                                                                                + "WHERE ss.no_ppkb = ? AND ss.is_reshipping = 'FALSE' AND ss.is_planning = 'TRUE' AND e.cont_no IS NULL AND ss.shift_to <> 'TOCY' "
                                                                                                + "ORDER BY ss.id DESC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findShiftableContainer", query = "SELECT ss.id, ss.cont_no, ss.bl_no, ss.cont_gross, ss.cont_size, ss.cont_status, ss.dg, ss.dg_label, ss.cont_type, ss.commodity_code, ss.mlo, ss.seal_no, ss.is_export_import, ss.shift_to, ss.v_bay, ss.v_row, ss.v_tier, ss.over_size, COALESCE(c.new_iso_code, c.iso_code) iso_code "
                                                                                    + "FROM service_shifting as ss "
                                                                                            + "JOIN m_container_type as c on (ss.cont_type = c.cont_type) "
                                                                                            + "LEFT JOIN equipment e ON (e.no_ppkb = ss.no_ppkb AND e.cont_no = ss.cont_no AND e.operation LIKE 'SHIFTING%') "
                                                                                    + "WHERE ss.no_ppkb = ? AND ss.cont_no = ? AND ss.is_reshipping = 'FALSE' AND ss.is_planning = 'TRUE' AND e.cont_no IS NULL AND ss.shift_to <> 'TOCY';"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findServiceShiftingReshipping", query = "SELECT ss.id, ss.cont_no, ss.cont_size, c.type_in_general as name, ss.cont_status, ss.cont_gross, ss.seal_no, ss.shift_to, ss.v_bay, ss.v_row, ss.v_tier, ss.is_reshipping, ss.id_equipment, ss.id_equipment_reshipping "
    + "FROM service_shifting as ss, m_container_type as c "
    + "WHERE ss.cont_type = c.cont_type AND ss.no_ppkb = ? AND (ss.is_reshipping = 'TRUE' OR ss.is_landed = 'TRUE') AND ss.is_planning = 'TRUE' ORDER BY ss.id DESC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findServiceShiftingReshippingNotToCY", query = "SELECT ss.id, ss.cont_no, ss.cont_size, c.type_in_general as name, ss.cont_status, ss.cont_gross, ss.seal_no, ss.shift_to, ss.v_bay, ss.v_row, ss.v_tier, ss.is_reshipping, ss.id_equipment, ss.id_equipment_reshipping "
    + "FROM service_shifting as ss, m_container_type as c "
    + "WHERE ss.cont_type = c.cont_type AND ss.no_ppkb = ? AND (ss.is_reshipping = 'TRUE' OR ss.is_landed = 'TRUE') AND ss.is_planning = 'TRUE' AND ss.shift_to <>'TOCY' ORDER BY ss.id DESC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findServiceShiftingReshippingWithout", query = "SELECT ss.id, ss.cont_no, ss.cont_size, c.type_in_general as name, ss.cont_status, ss.cont_gross, ss.seal_no, ss.shift_to, ss.v_bay, ss.v_row, ss.v_tier,ss.id_equipment,ss.is_reshipping,ss.id_equipment_reshipping,ss.bl_no FROM service_shifting as ss, m_container_type as c WHERE ss.cont_type = c.cont_type AND ss.no_ppkb = ? AND (ss.is_reshipping = 'TRUE' OR ss.is_landed = 'TRUE') AND ss.is_planning = 'FALSE' ORDER BY ss.id DESC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByContNo", query = "SELECT ss.id, ss.no_ppkb, ss.cont_no, ss.v_bay, ss.v_row, ss.v_tier, ss.shift_to FROM service_shifting as ss WHERE ss.no_ppkb = ? AND ss.cont_no = ? AND ss.is_reshipping = FALSE AND ss.is_planning = TRUE AND ss.cont_no NOT IN (SELECT e.cont_no FROM equipment e WHERE e.no_ppkb = ss.no_ppkb AND e.cont_no IS NOT NULL AND (e.operation LIKE 'SHIFTING%' OR e.operation = 'RESHIPPING'))"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByContNoMobile", query = "SELECT ss.id, ss.no_ppkb, ss.cont_no, ss.v_bay, ss.v_row, ss.v_tier, ss.shift_to "
    + "FROM service_shifting as ss "
    + "WHERE ss.no_ppkb = ? AND ss.cont_no = ? AND ss.is_reshipping = FALSE AND ss.is_planning = TRUE AND ss.cont_no NOT IN (SELECT e.cont_no FROM equipment e WHERE e.no_ppkb = ss.no_ppkb AND e.cont_no IS NOT NULL AND (e.operation LIKE 'SHIFTING%' OR e.operation = 'RESHIPPING'))"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByContNoReship", query = "SELECT id, no_ppkb, cont_no, v_bay, v_row, v_tier, shift_to FROM service_shifting WHERE no_ppkb = ? AND cont_no =? AND is_reshipping = ?"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findRekap", query = "SELECT ss.id, ss.cont_no, ss.cont_size, ct.type_in_general as name, ss.cont_status, CASE WHEN ss.over_size=true THEN 'Yes' WHEN ss.over_size=false THEN 'No' END as over_size, CASE WHEN ss.dg=true THEN 'Yes' WHEN ss.dg=false THEN 'No' END as dg, CASE WHEN ss.dg_label=true THEN 'Yes' WHEN ss.dg_label=false THEN 'No' END as dg_label, ss.crane, ss.is_landed, e.equip_code, e.start_time, e.end_time, er.equip_code, er.start_time, er.end_time "
    + "FROM service_shifting ss LEFT JOIN  equipment er ON ss.id_equipment_reshipping = er.id, m_container_type ct, equipment e "
    + "WHERE ss.cont_type = ct.cont_type AND ss.id_equipment = e.id AND ss.is_paid = true AND ss.no_ppkb = ? AND ss.operation = ?"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkb", query = "SELECT id, cont_no FROM service_shifting WHERE no_ppkb = ?"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbPaid", query = "SELECT id FROM service_shifting WHERE no_ppkb = ? AND is_paid = true"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbDischargeConfirmList", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.v_bay, scd.v_row, scd.v_tier, scd.id FROM service_shifting AS scd, m_container_type AS mct WHERE scd.cont_type = mct.cont_type AND scd.position='02' AND scd.no_ppkb = ? ORDER BY scd.modified_date ASC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbDischargeConfirmSelect", query = "SELECT scd.cont_no, scd.cont_size, mct.name, scd.cont_status, scd.cont_gross, scd.seal_no, scd.v_bay, scd.v_row, scd.v_tier, scd.id FROM service_shifting AS scd, m_container_type AS mct WHERE scd.cont_type = mct.cont_type AND scd.position='01' AND scd.no_ppkb = ? AND scd.shift_to='TOCY' ORDER BY scd.modified_date ASC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbLiftOffList", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, scd.cont_gross, scd.seal_no, ps.block, ps.y_slot, ps.y_row, ps.y_tier, ps.id, false as topTier,scd.id FROM service_shifting AS scd, m_container_type AS mct,planning_shift_discharge ps WHERE scd.cont_type = mct.cont_type AND scd.position='03' AND scd.no_ppkb = ? AND ps.no_ppkb=scd.no_ppkb AND ps.cont_no=scd.cont_no ORDER BY scd.modified_date DESC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbLiftOffSelect", query = "SELECT scd.cont_no, scd.cont_size, mct.name, scd.cont_status, scd.cont_gross, scd.seal_no, ps.block, ps.y_slot, ps.y_row, ps.y_tier, ps.id, false as topTier,scd.id FROM service_shifting AS scd, m_container_type AS mct,planning_shift_discharge ps WHERE scd.cont_type = mct.cont_type AND scd.position='02' AND scd.no_ppkb = ? AND ps.no_ppkb=scd.no_ppkb AND scd.shift_to='TOCY' AND ps.cont_no=scd.cont_no ORDER BY scd.modified_date DESC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbBayPlanLoadList", query = "SELECT pcl.cont_no, pcl.cont_size, mct.type_in_general as name, pcl.cont_status, pcl.cont_gross, pcl.seal_no, pcl.v_bay, pcl.v_row, pcl.v_tier, pcl.load_port, pcl.disch_port, pcl.id, ps.block, ps.y_slot, ps.y_row, ps.y_tier,pcl.bl_no,m1.name as loadPort,m2.name as dischPort,pcl.id,ps.id,pcl.position FROM service_shifting pcl,planning_shift_discharge ps,m_container_type mct,m_port m1,m_port m2 WHERE pcl.cont_type = mct.cont_type AND pcl.no_ppkb=ps.no_ppkb AND pcl.cont_no=ps.cont_no AND mct.cont_type=ps.cont_type AND pcl.load_port=m1.port_code AND pcl.disch_port=m2.port_code AND (pcl.position='03' OR pcl.position='04' OR pcl.position='05') AND pcl.shift_to='TOCY' AND pcl.no_ppkb = ? ORDER BY pcl.id DESC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbLiftOnList", query = "SELECT scd.cont_no, scd.cont_size, mct.type_in_general as name, scd.cont_status, scd.cont_gross, scd.seal_no, ps.y_slot, ps.y_row, ps.y_tier, scd.id,ps.block,scd.bl_no,ps.id FROM service_shifting AS scd, m_container_type AS mct,planning_shift_discharge ps WHERE scd.cont_type = mct.cont_type AND scd.position='04' AND scd.no_ppkb = ? AND ps.cont_no=scd.cont_no AND ps.no_ppkb=scd.no_ppkb ORDER BY scd.id DESC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbLiftOnListSelect", query = "SELECT scd.cont_no, scd.cont_size, mct.name, scd.cont_status, scd.cont_gross, scd.seal_no, ps.y_slot, ps.y_row, ps.y_tier, scd.id,ps.block,scd.bl_no,ps.id,ps.disch_port FROM service_shifting AS scd, m_container_type AS mct,planning_shift_discharge ps WHERE scd.cont_type = mct.cont_type AND scd.position='03' AND scd.no_ppkb = ? AND ps.cont_no=scd.cont_no AND ps.no_ppkb=scd.no_ppkb ORDER BY scd.id DESC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findLiftableOnContainer", query = "SELECT pcl.cont_no, pcl.no_ppkb, mv.name vessel_name, pcl.block, pcl.y_slot, pcl.y_row, pcl.y_tier, pcl.cont_size, pcl.cont_status, pcl.over_size, pcl.dg, mcd.name "
                                                                                        + "FROM (((service_shifting AS ss "
                                                                                                        + "JOIN m_cont_damage mcd ON (ss.cont_damage=mcd.id)) "
                                                                                                + "JOIN (planning_vessel pv "
                                                                                                        + "JOIN m_vessel mv ON (pv.vessel_code=mv.vessel_code)) ON (ss.no_ppkb=pv.no_ppkb)) "
                                                                                                + "JOIN planning_cont_load pcl ON (pcl.cont_no=ss.cont_no AND pcl.no_ppkb=ss.no_ppkb)) "
                                                                                                + "JOIN m_container_type AS mct ON (ss.cont_type = mct.cont_type) "
                                                                                        + "WHERE ss.position='03' AND ss.cont_no = ? AND pcl.is_shifting = true;"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbLoadConfirmList", query = "SELECT p.cont_no, p.cont_size, c.type_in_general as name, p.cont_status, p.cont_gross, ps.block, ps.y_slot, ps.y_row, ps.y_tier, ps.id, p.seal_no, p.v_bay, p.v_row, p.v_tier,p.bl_no,mc.name as comodity,p.id FROM service_shifting as p, m_container_type as c,m_commodity mc,planning_shift_discharge ps WHERE p.cont_type=c.cont_type AND p.position='05' AND p.commodity_code=mc.commodity_code AND p.no_ppkb=ps.no_ppkb AND p.cont_no=ps.cont_no AND p.no_ppkb=? order by p.id desc"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbLoadConfirmSelect", query = "SELECT p.cont_no, p.cont_size, c.name, p.cont_status, p.cont_gross, ps.block, ps.y_slot, ps.y_row, ps.y_tier, ps.id, p.seal_no, p.v_bay, p.v_row, p.v_tier,p.bl_no,mc.name as comodity,p.id FROM service_shifting as p, m_container_type as c,m_commodity mc,planning_shift_discharge ps WHERE p.cont_type=c.cont_type AND p.position='04' AND p.commodity_code=mc.commodity_code AND p.no_ppkb=ps.no_ppkb AND p.cont_no=ps.cont_no AND p.no_ppkb =? order by p.id desc"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbDischargeConfirmSelectHht", query = "SELECT scd.cont_no, scd.placement_date, scd.v_bay, scd.v_row, scd.v_tier, scd.id FROM service_shifting AS scd, m_container_type AS mct WHERE scd.cont_type = mct.cont_type AND scd.position='01' AND scd.shift_to='TOCY' AND scd.no_ppkb = ? AND scd.cont_no=? ORDER BY scd.modified_date ASC"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbLiftOffeConfirmSelectHht", query = "SELECT scd.cont_no, scd.placement_date, scd.v_bay, scd.v_row, scd.v_tier, ps.id,  ps.y_slot, ps.y_row, ps.y_tier, ps.block, scd.no_ppkb, scd.cont_size, 's' as origin ,scd.id FROM service_shifting AS scd, m_container_type AS mct,planning_shift_discharge ps WHERE scd.cont_type = mct.cont_type AND ps.no_ppkb=scd.no_ppkb AND ps.cont_no=scd.cont_no AND scd.shift_to='TOCY' AND scd.cont_no = ? AND scd.position=? limit 1"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbLiftOnConfirmSelectHht", query = "SELECT ps.id,scd.cont_no,scd.no_ppkb,ps.block, ps.y_slot, ps.y_row, ps.y_tier, scd.id FROM service_shifting AS scd, m_container_type AS mct,planning_shift_discharge ps WHERE scd.cont_type = mct.cont_type AND ps.cont_no=scd.cont_no AND ps.no_ppkb=scd.no_ppkb AND scd.cont_no = ? AND scd.position=? limit 1"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbLoadConfirmSelectHht", query = "SELECT p.id,p.cont_no, p.no_ppkb, ps.y_slot, ps.y_row, ps.y_tier,  p.v_bay, p.v_row, p.v_tier, ps.id FROM service_shifting as p, m_container_type as c,m_commodity mc,planning_shift_discharge ps WHERE p.cont_type=c.cont_type  AND p.commodity_code=mc.commodity_code AND p.no_ppkb=ps.no_ppkb AND p.no_ppkb =? AND p.cont_no = ? AND p.position = ? AND p.cont_no=ps.cont_no limit 1"),
    @NamedNativeQuery(name = "ServiceShifting.Native.findByPpkbLoadConfirmSelectHht2", query = "SELECT p.id,p.cont_no, p.no_ppkb, ps.y_slot, ps.y_row, ps.y_tier,  p.v_bay, p.v_row, p.v_tier, ps.id FROM service_shifting as p, m_container_type as c,m_commodity mc,planning_shift_discharge ps WHERE p.cont_type=c.cont_type  AND p.commodity_code=mc.commodity_code AND p.no_ppkb=ps.no_ppkb AND p.cont_no = ? AND p.position = ? AND p.cont_no=ps.cont_no limit 1")})
public class ServiceShifting implements Serializable, EntityAuditable {
    @Basic(optional = false)
    @Column(name = "twenty_one_feet", nullable = false)
    private boolean twentyOneFeet = false;
    private static final long serialVersionUID = 1L;

    public static final String LOCATION_SHIFTING = "01";
    public static final String LOCATION_HT_SHIFTING = "02";
    public static final String LOCATION_CY = "03";
    public static final String LOCATION_HT_RESHIPPING = "04";
    public static final String LOCATION_RESHIPPING = "05";
    public static final String LOCATION_MOVEMENT = "MV";

    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator="service_shifting_generator", strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize=1, name="service_shifting_generator", sequenceName = "service_shifting_id_seq")
    private Integer id;
    @Column(name = "crane", length = 2)
    private String crane;
    @Basic(optional = false)
    @Column(name = "cont_no", nullable = false, length = 11)
    private String contNo;
    @Basic(optional = false)
    @Column(name = "cont_size", nullable = false)
    private short contSize;
    @Basic(optional = false)
    @Column(name = "cont_status", nullable = false, length = 5)
    private String contStatus;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Basic(optional = false)
    @Column(name = "over_size", nullable = false)
    private boolean overSize;
    @Basic(optional = false)
    @Column(name = "dg", nullable = false)
    private boolean dg;
    @Column(name = "dg_label")
    private Boolean dgLabel;
    @Column(name = "load_port", length = 10)
    private String loadPort;
    @Column(name = "disch_port", length = 10)
    private String dischPort;
    @Basic(optional = false)
    @Column(name = "v_bay")
    private Short vBay;
    @Column(name = "v_row")
    private Short vRow;
    @Basic(optional = false)
    @Column(name = "v_tier")
    private Short vTier;
    @Basic(optional = false)
    @Column(name = "shift_to", nullable = false, length = 10)
    private String shiftTo;
    @Column(name = "is_reshipping")
    private Boolean isReshipping;
    @Column(name = "operation", length = 10)
    private String operation;
    @Column(name = "is_paid")
    private Boolean isPaid;
    @Column(name = "is_landed")
    private Boolean isLanded;
    @Column(name = "is_planning")
    private Boolean isPlanning;
    @Column(name = "id_equipment")
    private Integer idEquipment;
    @Column(name = "id_equipment_reshipping")
    private Integer idEquipmentReshipping;
    @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb")
    @ManyToOne
    private ServiceVessel serviceVessel;
    @JoinColumn(name = "cont_type", referencedColumnName = "cont_type", nullable = false)
    @ManyToOne(optional = false)
    private MasterContainerType masterContainerType;
    @JoinColumn(name = "commodity_code", referencedColumnName = "commodity_code", nullable = false)
    @ManyToOne(optional = false)
    private MasterCommodity masterCommodity;
    @JoinColumn(name = "activity_code", referencedColumnName = "activity_code")
    @ManyToOne
    private MasterActivity masterActivity;
    @Column(name = "position_bay")
    private String positionBay;
    @Column(name = "shifting_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date shiftingDate;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Column(name = "position", length = 5)
    private String position;
    @Column(name = "placement_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @OneToOne
    @JoinColumns(value = {
        @JoinColumn(name = "cont_no", referencedColumnName = "cont_no", insertable = false, updatable = false),
        @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", insertable = false, updatable = false)
    })
    private PlanningShiftDischarge planningShiftDischarge;
    @OneToOne
    @JoinColumns(value = {
        @JoinColumn(name = "cont_no", referencedColumnName = "cont_no", insertable = false, updatable = false),
        @JoinColumn(name = "no_ppkb", referencedColumnName = "no_ppkb", insertable = false, updatable = false)
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
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private boolean isExportImport = false;
    @Basic(optional = false)
    @Column(name = "is_sling", nullable = false)
    private boolean isSling = false;

    public ServiceShifting() {
    }

    public ServiceShifting(Integer id) {
        this.id = id;
    }

    public ServiceShifting(Integer id, String contNo, short contSize, String contStatus, boolean overSize, boolean dg, String shiftTo, MasterCustomer mlo) {
        this.id = id;
        this.contNo = contNo;
        this.mlo = mlo;
        this.contSize = contSize;
        this.contStatus = contStatus;
        this.overSize = overSize;
        this.dg = dg;
        this.shiftTo = shiftTo;
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

    public boolean getOverSize() {
        return overSize;
    }

    public void setOverSize(boolean overSize) {
        this.overSize = overSize;
    }

    public boolean getDg() {
        return dg;
    }

    public void setDg(boolean dg) {
        this.dg = dg;
    }

    public Boolean getDgLabel() {
        return dgLabel;
    }

    public void setDgLabel(Boolean dgLabel) {
        this.dgLabel = dgLabel;
    }

    public String getLoadPort() {
        return loadPort;
    }

    public void setLoadPort(String loadPort) {
        this.loadPort = loadPort;
    }

    public String getDischPort() {
        return dischPort;
    }

    public void setDischPort(String dischPort) {
        this.dischPort = dischPort;
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

    public String getShiftTo() {
        return shiftTo;
    }

    public void setShiftTo(String shiftTo) {
        this.shiftTo = shiftTo;
    }

    public Boolean getIsReshipping() {
        return isReshipping;
    }

    public void setIsReshipping(Boolean isReshipping) {
        this.isReshipping = isReshipping;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * @return the isLanded
     */
    public Boolean getIsLanded() {
        return isLanded;
    }

    /**
     * @param isLanded the isLanded to set
     */
    public void setIsLanded(Boolean isLanded) {
        this.isLanded = isLanded;
    }

    /**
     * @return the isPlanning
     */
    public Boolean getIsPlanning() {
        return isPlanning;
    }

    /**
     * @param isPlanning the isPlanning to set
     */
    public void setIsPlanning(Boolean isPlanning) {
        this.isPlanning = isPlanning;
    }

    /**
     * @return the idEquipment
     */
    public Integer getIdEquipment() {
        return idEquipment;
    }

    /**
     * @param idEquipment the idEquipment to set
     */
    public void setIdEquipment(Integer idEquipment) {
        this.idEquipment = idEquipment;
    }

    /**
     * @return the idEquipmentReshipping
     */
    public Integer getIdEquipmentReshipping() {
        return idEquipmentReshipping;
    }

    /**
     * @param idEquipmentReshipping the idEquipmentReshipping to set
     */
    public void setIdEquipmentReshipping(Integer idEquipmentReshipping) {
        this.idEquipmentReshipping = idEquipmentReshipping;
    }

    public ServiceVessel getServiceVessel() {
        return serviceVessel;
    }

    public void setServiceVessel(ServiceVessel serviceVessel) {
        this.serviceVessel = serviceVessel;
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

    /**
     * @return the positionBay
     */
    public String getPositionBay() {
        return positionBay;
    }

    /**
     * @param positionBay the positionBay to set
     */
    public void setPositionBay(String positionBay) {
        this.positionBay = positionBay;
    }

    public Date getShiftingDate() {
        return shiftingDate;
    }

    public void setShiftingDate(Date shiftingDate) {
        this.shiftingDate = shiftingDate;
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

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public PlanningShiftDischarge getPlanningShiftDischarge() {
        return planningShiftDischarge;
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
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiceShifting)) {
            return false;
        }
        ServiceShifting other = (ServiceShifting) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.ServiceShifting[id=" + id + "]";
    }

    public boolean getTwentyOneFeet() {
        return twentyOneFeet;
    }

    public void setTwentyOneFeet(boolean twentyOneFeet) {
        this.twentyOneFeet = twentyOneFeet;
    }
}
