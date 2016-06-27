/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.model.db;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author postgres
 */
@Entity
@EntityListeners({AuditTrailEntityListener.class})
@Table(name = "uncontainerized_service")
@NamedQueries({
    @NamedQuery(name = "UncontainerizedService.findAll", query = "SELECT u FROM UncontainerizedService u"),
    @NamedQuery(name = "UncontainerizedService.findByIdUc", query = "SELECT u FROM UncontainerizedService u WHERE u.idUc = :idUc"),
    @NamedQuery(name = "UncontainerizedService.findByBlNo", query = "SELECT u FROM UncontainerizedService u WHERE u.blNo = :blNo"),
    @NamedQuery(name = "UncontainerizedService.findByNoPpkbAndBlNo", query = "SELECT u FROM UncontainerizedService u WHERE u.noPpkb = :noPpkb AND u.blNo = :blNo"),
    @NamedQuery(name = "UncontainerizedService.findByNoPpkb", query = "SELECT u FROM UncontainerizedService u WHERE u.noPpkb = :noPpkb"),
    @NamedQuery(name = "UncontainerizedService.findByBlock", query = "SELECT u FROM UncontainerizedService u WHERE u.block = :block"),
    @NamedQuery(name = "UncontainerizedService.findByCommodity", query = "SELECT u FROM UncontainerizedService u WHERE u.commodity = :commodity"),
    @NamedQuery(name = "UncontainerizedService.findByWeight", query = "SELECT u FROM UncontainerizedService u WHERE u.weight = :weight"),
    @NamedQuery(name = "UncontainerizedService.findUcByStatusOperationAndDeliveryStatus", query = "SELECT u FROM UncontainerizedService u WHERE u.noPpkb = :noPpkb AND u.blNo = :blNo AND u.status = :status AND u.operation = :operation AND u.isDelivery = :isDelivery"),
    @NamedQuery(name = "UncontainerizedService.findByTruckLoosing", query = "SELECT u FROM UncontainerizedService u WHERE u.truckLoosing = :truckLoosing"),
    @NamedQuery(name = "UncontainerizedService.findByDischPort", query = "SELECT u FROM UncontainerizedService u WHERE u.dischPort = :dischPort"),
    @NamedQuery(name = "UncontainerizedService.findByLoadPort", query = "SELECT u FROM UncontainerizedService u WHERE u.loadPort = :loadPort"),
    @NamedQuery(name = "UncontainerizedService.findByCrane", query = "SELECT u FROM UncontainerizedService u WHERE u.crane = :crane"),
    @NamedQuery(name = "UncontainerizedService.findByStatus", query = "SELECT u FROM UncontainerizedService u WHERE u.status = :status"),
    @NamedQuery(name = "UncontainerizedService.resetPlannedUcServiceByNoReg", query = "UPDATE UncontainerizedService u SET u.status = 'PLANNING' WHERE u.ucReceivingService.registration.noReg = :noReg AND u.status <> 'UNPLANNED'"),
    @NamedQuery(name = "UncontainerizedService.deleteUnplannedUcServiceByNoReg", query = "DELETE FROM UncontainerizedService u WHERE u.ucReceivingService.registration.noReg = :noReg AND u.status = 'UNPLANNED'"),
    @NamedQuery(name = "UncontainerizedService.findUCThatHaveNotReachedCY", query = "SELECT u FROM UncontainerizedService u WHERE u.noPpkb = :noPpkb AND u.operation = 'DISCHARGE' AND u.isTranshipment = FALSE AND u.isShifting = FALSE AND NOT (u.status IN ('CY', 'OUTSIDE') OR (u.status = 'STV' AND u.truckLoosing = TRUE))"),
    @NamedQuery(name = "UncontainerizedService.findDeliveryUCThatHaveReachedCY", query = "SELECT u FROM UncontainerizedService u WHERE u.noPpkb = :noPpkb AND u.operation = 'DISCHARGE' AND u.isTranshipment = FALSE AND u.isShifting = FALSE AND (u.status IN ('CY', 'OUTSIDE') OR (u.status = 'STV' AND u.truckLoosing = TRUE))"),
    @NamedQuery(name = "UncontainerizedService.findByStatussesAndOperation", query = "SELECT u FROM UncontainerizedService u WHERE u.noPpkb = :noPpkb AND u.operation = :operation AND u.status IN :statusses"),
    @NamedQuery(name = "UncontainerizedService.findByStatusAndOperation", query = "SELECT u FROM UncontainerizedService u WHERE u.noPpkb = :noPpkb AND u.operation = :operation AND u.status = :status"),
    @NamedQuery(name = "UncontainerizedService.findUCThatHaveNotBeenLoaded", query = "SELECT u FROM UncontainerizedService u WHERE u.noPpkb = :noPpkb AND u.operation = 'LOAD' AND u.status <> 'VESSEL'"),
    @NamedQuery(name = "UncontainerizedService.findReceivingUcThatHadBeenLoaded", query = "SELECT u FROM UncontainerizedService u WHERE u.noPpkb = :noPpkb AND u.status = 'VESSEL' AND u.isShifting = FALSE AND u.isTranshipment = FALSE AND u.operation = 'LOAD'"),
    @NamedQuery(name = "UncontainerizedService.findByIsDelivery", query = "SELECT u FROM UncontainerizedService u WHERE u.isDelivery = :isDelivery"),
    @NamedQuery(name = "UncontainerizedService.findByPlacementDate", query = "SELECT u FROM UncontainerizedService u WHERE u.placementDate = :placementDate"),
    @NamedQuery(name = "UncontainerizedService.findByOperation", query = "SELECT u FROM UncontainerizedService u WHERE u.operation = :operation"),
    @NamedQuery(name = "UncontainerizedService.findTranshipmentUCThatHaveReachedCY", query = "SELECT u from UncontainerizedService u WHERE u.noPpkb=:noPpkb AND u.placementDate IS NOT NULL AND u.isTranshipment = TRUE AND u.operation='DISCHARGE'"),
    @NamedQuery(name = "UncontainerizedService.findDischargableUcByNoPpkbAndBlNo", query = "SELECT u FROM UncontainerizedService u WHERE u.noPpkb = :noPpkb AND u.blNo = :blNo AND u.status = 'PLANNING' AND ((u.isShifting = FALSE AND u.operation = 'DISCHARGE') OR (u.isShifting = TRUE AND u.shiftTo = 'TOCY'))"),
    @NamedQuery(name = "UncontainerizedService.findDeliverableUcByBlNo", query = "SELECT u FROM UncontainerizedService u WHERE u.blNo = :blNo AND u.status = 'CY' AND u.operation = 'DISCHARGE' AND u.isDelivery = FALSE"),
    @NamedQuery(name = "UncontainerizedService.findTranshipmentUCThatHadBeenLoaded", query = "SELECT u from UncontainerizedService u WHERE u.noPpkb=:noPpkb AND u.status = 'VESSEL' AND u.isTranshipment = TRUE AND u.operation='LOAD'"),
    @NamedQuery(name = "UncontainerizedService.findByNoPpkbForRecapJasaDermagaUC", query = "SELECT u FROM UncontainerizedService u WHERE u.noPpkb = :noPpkb and ((u.operation = 'DISCHARGE' and (u.status ='OUTSIDE' or u.status='CY')) or (u.operation = 'LOAD' and u.status='VESSEL'))")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "UncontainerizedService.Native.findIdByPpkb", query = "SELECT pu.id_uc, bl_no from uncontainerized_service pu where pu.no_ppkb = ?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByPpkb", query = "SELECT pu.id_uc from uncontainerized_service pu where pu.no_ppkb = ? AND pu.is_delivery = false"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findUncontainerizedServiceId", query = "Select e.id_uc FROM uncontainerized_service e where e.no_ppkb=? AND e.bl_no=?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByPpkbAndBl", query = "SELECT pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.id_uc, pu.unit,c.name, d.name,'NOLANDED'::character varying(9) from uncontainerized_service pu, m_commodity m,m_port c,m_port d,planning_vessel pv,preservice_vessel pl where is_shifting=FALSE AND pu.commodity = m.commodity_code AND pu.operation='DISCHARGE' AND pu.disch_port=c.port_code AND pu.load_port=d.port_code AND pu.no_ppkb=pv.no_ppkb AND pu.no_ppkb = ? AND pl.booking_code=pv.booking_code AND pl.next_port_code = pu.disch_port"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByPpkbAndBlList", query = "SELECT a.bl_no, b.name, a.weight, c.name, d.name, change(a.truck_loosing), a.shift_to, a.id_uc, a.status "
                                                                                        + "FROM uncontainerized_service a "
                                                                                                + "JOIN m_commodity b ON (a.commodity=b.commodity_code) "
                                                                                                + "JOIN m_port c ON (a.disch_port=c.port_code) "
                                                                                                + "JOIN m_port d ON (a.load_port=d.port_code) "
                                                                                        + "WHERE a.no_ppkb = ? AND is_shifting=TRUE"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByPpkbPlanning", query = "SELECT pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.id_uc, pu.unit, pu.cubication from uncontainerized_service pu, m_commodity m where pu.no_ppkb = ? and pu.status = 'PLANNING' AND pu.commodity = m.commodity_code AND pu.operation = ?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByPpkbOperation", query = "SELECT pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.id_uc, pu.unit from uncontainerized_service pu, m_commodity m where pu.no_ppkb = ? AND pu.commodity = m.commodity_code AND pu.operation = ?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findForLiftOFF", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block, pu.status  FROM uncontainerized_service pu, m_commodity m, m_port c, m_port d where pu.no_ppkb = ? and pu.operation = ? AND pu.truck_loosing=FALSE AND pu.commodity = m.commodity_code AND pu.disch_port = c.port_code AND pu.load_port = d.port_code"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findLiftableOffByNoPpkb", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block "
                                                                                            + "FROM uncontainerized_service pu "
                                                                                                    + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                                    + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                                    + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                                    + "LEFT JOIN uc_shifting_service uss ON (pu.bl_no = uss.bl_no AND pu.no_ppkb = uss.no_ppkb) "
                                                                                            + "WHERE pu.no_ppkb = ? and pu.status = 'STV' AND pu.truck_loosing = FALSE AND ((pu.is_shifting = FALSE AND pu.operation = 'DISCHARGE') OR (pu.is_shifting = TRUE AND pu.shift_to = 'TOCY' AND uss.is_landed = FALSE))"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findHasLiftedOffByNoPpkb", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block "
                                                                                            + "FROM uncontainerized_service pu "
                                                                                                    + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                                    + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                                    + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                                    + "LEFT JOIN uc_shifting_service uss ON (pu.bl_no = uss.bl_no AND pu.no_ppkb = uss.no_ppkb) "
                                                                                            + "WHERE pu.no_ppkb = ? and pu.status = 'CY' AND pu.truck_loosing = FALSE AND ((pu.is_shifting = FALSE AND pu.operation = 'DISCHARGE') OR (pu.is_shifting = TRUE AND pu.shift_to = 'TOCY' AND uss.is_landed = TRUE))"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findReceivableUcsByNoPpkb", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block "
                                                                                    + "FROM uncontainerized_service pu "
                                                                                            + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                            + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                            + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                            + "JOIN uc_receiving_service urs ON (pu.id_uc=urs.id_uc) "
                                                                                                    + "JOIN uc_gatereceiving_service ugs ON (ugs.jobslip=urs.jobslip) "
                                                                                    + "WHERE pu.no_ppkb = ? and pu.status = 'PLANNING' and pu.operation = 'LOAD' AND pu.truck_loosing = FALSE"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByEntryDelivery", query = "SELECT pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.id_uc, pu.unit from uncontainerized_service pu,m_commodity m where pu.commodity=m.commodity_code AND pu.no_ppkb = ? AND pu.operation = 'DISCHARGE' AND (pu.truck_loosing = true OR (pu.truck_loosing = false AND pu.status = 'CY' AND pu.is_delivery = false)) AND pu.id_uc NOT IN (SELECT id_uc FROM uc_delivery_service WHERE id_uc = pu.id_uc) AND is_transhipment=false "),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByEntryReceivingUcLoad", query = "SELECT pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.id_uc, pu.unit from uncontainerized_service pu,m_commodity m where pu.commodity=m.commodity_code AND pu.no_ppkb = ? AND pu.operation = 'LOAD' AND pu.is_transhipment=FALSE AND pu.id_uc NOT IN (SELECT id_uc FROM uc_receiving_service WHERE no_ppkb = pu.no_ppkb)"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByPpkbStatusOperation", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block FROM uncontainerized_service pu, m_commodity m, m_port c, m_port d where pu.no_ppkb = ? and pu.status = ? and pu.operation = ? AND pu.commodity = m.commodity_code AND pu.disch_port = c.port_code AND pu.load_port = d.port_code"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findDischargableUcsByNoPpkb", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block "
                                                                                                + "FROM uncontainerized_service pu "
                                                                                                        + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                                        + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                                        + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                                + "WHERE pu.no_ppkb = ? AND pu.status = 'PLANNING' AND ((pu.is_shifting = FALSE AND pu.operation = 'DISCHARGE') OR (pu.is_shifting = TRUE AND pu.shift_to = 'TOCY'))"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findDischargedUcsByNoPpkb", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block "
                                                                                                + "FROM uncontainerized_service pu "
                                                                                                        + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                                        + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                                        + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                                + "WHERE pu.no_ppkb = ? AND (pu.status = 'STV' OR (pu.truck_loosing = TRUE AND pu.status = 'CY' AND pu.is_delivery = FALSE)) AND ((pu.is_shifting = FALSE AND pu.operation = 'DISCHARGE') OR (pu.is_shifting = TRUE AND pu.shift_to = 'TOCY'))"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findLiftableOnByNoPpkb", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block "
                                                                                            + "FROM uncontainerized_service pu "
                                                                                                    + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                                    + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                                    + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                                    + "LEFT JOIN uc_shifting_service uss ON (pu.bl_no = uss.bl_no AND pu.no_ppkb = uss.no_ppkb) "
                                                                                            + "WHERE pu.no_ppkb = ? and pu.status = 'CY' AND pu.truck_loosing = FALSE AND ((pu.is_shifting = FALSE AND pu.operation = 'LOAD') OR (pu.is_shifting = TRUE AND pu.shift_to = 'TOCY' AND uss.is_landed = TRUE))"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findHasLiftedOnByNoPpkb", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block "
                                                                                            + "FROM uncontainerized_service pu "
                                                                                                    + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                                    + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                                    + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                                    + "LEFT JOIN uc_shifting_service uss ON (pu.bl_no = uss.bl_no AND pu.no_ppkb = uss.no_ppkb) "
                                                                                            + "WHERE pu.no_ppkb = ? and pu.status = 'STV' AND pu.truck_loosing = FALSE AND ((pu.is_shifting = FALSE AND pu.operation = 'LOAD') OR (pu.is_shifting = TRUE AND pu.shift_to = 'TOCY' AND uss.is_landed = TRUE))"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findLoadableByNoPpkb", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block "
                                                                                            + "FROM uncontainerized_service pu "
                                                                                                    + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                                    + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                                    + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                                    + "LEFT JOIN uc_shifting_service uss ON (pu.bl_no = uss.bl_no AND pu.no_ppkb = uss.no_ppkb) "
                                                                                            + "WHERE pu.no_ppkb = ? and pu.status = 'STV' AND ((pu.is_shifting = FALSE AND pu.operation = 'LOAD') OR (pu.is_shifting = TRUE AND pu.shift_to = 'TOCY' AND uss.is_landed = TRUE AND uss.is_reshipping = FALSE))"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findLoadableByNoPpkbAndNoBl", query = "SELECT pu.id_uc, pu.no_ppkb, pu.bl_no, c.name, pu.weight, pu.unit, pu.truck_loosing, pu.block "
                                                                                                + "FROM uncontainerized_service pu "
                                                                                                        + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                                        + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                                        + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                                        + "LEFT JOIN uc_shifting_service uss ON (pu.bl_no = uss.bl_no AND pu.no_ppkb = uss.no_ppkb) "
                                                                                                + "WHERE pu.no_ppkb = ? AND pu.bl_no = ? AND pu.status = 'STV' AND ((pu.is_shifting = FALSE AND pu.operation = 'LOAD') OR (pu.is_shifting = TRUE AND pu.shift_to = 'TOCY' AND uss.is_landed = TRUE AND uss.is_reshipping = FALSE))"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findHasLoadedByNoPpkb", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block "
                                                                                            + "FROM uncontainerized_service pu "
                                                                                                    + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                                    + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                                    + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                                    + "LEFT JOIN uc_shifting_service uss ON (pu.bl_no = uss.bl_no AND pu.no_ppkb = uss.no_ppkb) "
                                                                                            + "WHERE pu.no_ppkb = ? and pu.status = 'VESSEL' AND ((pu.is_shifting = FALSE AND pu.operation = 'LOAD') OR (pu.is_shifting = TRUE AND pu.shift_to = 'TOCY' AND uss.is_landed = TRUE AND uss.is_reshipping = TRUE))"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findDetailByIdUc", query = "SELECT a.bl_no, b.name, a.unit, a.weight, change(a.truck_loosing), c.name, d.name, a.block FROM uncontainerized_service a, m_commodity b, m_port c, m_port d WHERE a.commodity=b.commodity_code AND a.disch_port=c.port_code AND a.load_port=d.port_code AND a.id_uc=?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByPpkbAndBlMobile", query = "SELECT s.id_uc, s.no_ppkb, s.bl_no, c.name, s.weight, s.unit, s.truck_loosing, s.block FROM uncontainerized_service s, m_commodity as c WHERE s.commodity = c.commodity_code AND s.no_ppkb = ? AND s.bl_no = ? AND s.status = ? AND s.operation = ? AND s.is_delivery = ?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByBlNoMobile", query = "SELECT s.id_uc, s.no_ppkb, s.bl_no, c.name, s.weight, s.unit, s.truck_loosing, s.block FROM uncontainerized_service s, m_commodity as c WHERE s.commodity = c.commodity_code AND s.bl_no = ? AND s.status = ? AND s.operation = ? AND s.is_delivery = ?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findDeliveredUcsByNoPpkb", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block, pu.status "
                                                                                    + "FROM uncontainerized_service pu "
                                                                                            + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                            + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                            + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                    + "WHERE pu.no_ppkb = ? AND pu.operation = 'DISCHARGE' AND pu.is_delivery = TRUE AND (pu.truck_loosing=FALSE OR (pu.truck_loosing = TRUE AND pu.status IN ('OUTSIDE', 'CY')));"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findDeliverableUcs", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block "
                                                                                            + "FROM uncontainerized_service pu "
                                                                                                    + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                                    + "JOIN m_port c ON (pu.disch_port = c.port_code) "
                                                                                                    + "JOIN m_port d ON (pu.load_port = d.port_code) "
                                                                                                    + "JOIN uc_delivery_service uds ON (pu.id_uc=uds.id_uc) "
                                                                                                            + "JOIN uc_gatedelivery_service ugs ON (uds.jobslip = ugs.jobslip) "
                                                                                            + "WHERE pu.no_ppkb = ? AND pu.status = 'CY' AND pu.operation = 'DISCHARGE' AND pu.is_delivery = FALSE"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findDeliverableUcByBlNo", query = "SELECT pu.id_uc, pu.no_ppkb, pu.bl_no, m.name, pu.unit, pu.weight, pu.truck_loosing, pu.block "
                                                                                            + "FROM uncontainerized_service pu "
                                                                                                    + "JOIN m_commodity m ON (pu.commodity = m.commodity_code) "
                                                                                                    + "JOIN uc_delivery_service uds ON (pu.id_uc=uds.id_uc) "
                                                                                                            + "JOIN uc_gatedelivery_service ugs ON (uds.jobslip = ugs.jobslip) "
                                                                                            + "WHERE pu.bl_no = ? AND pu.status = 'CY' AND pu.operation = 'DISCHARGE' AND pu.is_delivery = FALSE"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findShifting", query = "SELECT s.id_uc, s.no_ppkb, s.bl_no, c.name, s.weight, s.unit FROM uncontainerized_service s, m_commodity as c WHERE s.commodity = c.commodity_code AND s.no_ppkb = ? AND s.is_shifting = true AND s.operation = 'DISCHARGE' AND s.status != 'STV' AND s.bl_no NOT IN (select bl_no from uc_shifting_service WHERE no_ppkb = s.no_ppkb)"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findShiftingWithout", query = "SELECT s.id_uc, s.no_ppkb, s.bl_no, c.name, s.weight, s.unit,s.operation FROM uncontainerized_service s, m_commodity as c WHERE s.commodity = c.commodity_code AND s.no_ppkb = ? AND s.is_shifting = false AND s.bl_no NOT IN (select bl_no from uc_shifting_service WHERE no_ppkb=s.no_ppkb)"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findWeightByPpkbNBl", query = "SELECT s.weight FROM uncontainerized_service s WHERE s.no_ppkb = ? AND s.bl_no = ?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByBlNoMobileUc", query = "SELECT s.id_uc, s.no_ppkb, s.bl_no, c.name, s.weight, s.unit, s.truck_loosing, s.block FROM uncontainerized_service s, m_commodity as c WHERE s.commodity = c.commodity_code AND s.id_uc IN (select id_uc from uc_receiving_service) AND s.status IN ('PLANNING', 'UNPLANNED') AND s.bl_no = ?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByUcMonitoringFrontDischarge", query = "select uc.bl_no,uc.no_ppkb,c.name,uc.block,pDisch.name,pLoad.name,change(uc.truck_loosing), uc.operation,e1.equip_code, e1.start_time, e1.end_time, e2.equip_code, e2.start_time,e2.end_time, e3.equip_code, e3.start_time, e3.end_time FROM ((((uncontainerized_service uc LEFT JOIN m_commodity c ON uc.commodity=c.commodity_code) LEFT JOIN equipment e1 ON uc.bl_no = e1.bl_no AND uc.no_ppkb = e1.no_ppkb AND e1.operation = 'DISCHARGEUC' AND e1.equipment_act_code = 'STEVEDORING') LEFT JOIN equipment e2 ON uc.bl_no = e2.bl_no AND uc.no_ppkb = e2.no_ppkb AND e2.operation = 'DISCHARGEUC' AND e2.equipment_act_code = 'H/T') LEFT JOIN equipment e3 ON uc.bl_no = e3.bl_no AND uc.no_ppkb = e3.no_ppkb AND e3.operation = 'DISCHARGEUC' AND e3.equipment_act_code = 'LIFTOFF'), m_port pDisch, m_port pLoad WHERE uc.operation='DISCHARGE' AND uc.disch_port = pDisch.port_code AND uc.load_port = pLoad.port_code AND uc.no_ppkb =?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByUcMonitoringFrontLoad", query = "select uc.bl_no,uc.no_ppkb,c.name,uc.block,pDisch.name,pLoad.name,change(uc.truck_loosing), uc.operation,e1.equip_code, e1.start_time, e1.end_time, e2.equip_code, e2.start_time,e2.end_time, e3.equip_code, e3.start_time, e3.end_time FROM ((((uncontainerized_service uc LEFT JOIN m_commodity c ON uc.commodity=c.commodity_code) LEFT JOIN equipment e1 ON uc.bl_no = e1.bl_no AND uc.no_ppkb = e1.no_ppkb AND e1.operation = 'LOADUC' AND e1.equipment_act_code = 'STEVEDORING') LEFT JOIN equipment e2 ON uc.bl_no = e2.bl_no AND uc.no_ppkb = e2.no_ppkb AND e2.operation = 'LOADUC' AND e2.equipment_act_code = 'H/T') LEFT JOIN equipment e3 ON uc.bl_no = e3.bl_no AND uc.no_ppkb = e3.no_ppkb AND e3.operation = 'LOADUC' AND e3.equipment_act_code = 'LIFTON'), m_port pDisch, m_port pLoad WHERE uc.operation='LOAD' AND uc.disch_port = pDisch.port_code AND uc.load_port = pLoad.port_code AND uc.no_ppkb =?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findByBlNoMobileUc", query = "SELECT s.id_uc, s.no_ppkb, s.bl_no, c.name, s.weight, s.unit, s.truck_loosing, s.block FROM uncontainerized_service s, m_commodity as c WHERE s.commodity = c.commodity_code AND s.id_uc IN (select id_uc from uc_receiving_service) AND s.status IN ('PLANNING', 'UNPLANNED') AND s.bl_no = ?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findShiftingWithoutMobile", query = "SELECT s.id_uc, s.no_ppkb, s.bl_no, c.name, s.weight, s.unit,s.operation FROM uncontainerized_service s, m_commodity as c WHERE s.commodity = c.commodity_code AND s.no_ppkb = ? AND s.bl_no = ? AND s.is_shifting = false AND s.bl_no NOT IN (select bl_no from uc_shifting_service WHERE no_ppkb=s.no_ppkb)"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findShiftingMobile", query = "SELECT s.id_uc, s.no_ppkb, s.bl_no, c.name, s.weight, s.unit, s.operation, s.shift_to FROM uncontainerized_service s, m_commodity as c WHERE s.commodity = c.commodity_code AND s.no_ppkb = ? AND s.bl_no = ? AND s.is_shifting = true AND s.operation = 'DISCHARGE' AND s.bl_no NOT IN (select bl_no from uc_shifting_service WHERE no_ppkb = s.no_ppkb)"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findForLoadConfirmList", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block FROM uncontainerized_service pu, m_commodity m, m_port c, m_port d where pu.no_ppkb = ? and pu.status = ? and pu.operation = ? AND pu.commodity = m.commodity_code AND pu.disch_port = c.port_code AND pu.load_port = d.port_code"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findForLoadConfirmByTL", query = "SELECT pu.id_uc, pu.bl_no, m.name, pu.unit, pu.weight, change(pu.truck_loosing), c.name, d.name, pu.block FROM uncontainerized_service pu, m_commodity m, m_port c, m_port d where pu.no_ppkb = ? and ((pu.truck_loosing = true AND pu.status = 'PLANNING') OR (pu.truck_loosing = false AND pu.status = 'STV')) and pu.operation = 'LOAD' AND pu.commodity = m.commodity_code AND pu.disch_port = c.port_code AND pu.load_port = d.port_code"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findUncontainerizedTranshipment", query = "SELECT pu.id_uc,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.id_uc, pu.unit,pu.block,mp1.name as loadPort,mp2.name as dischPort,pu.status,pu.operation from uncontainerized_service pu, m_commodity m,m_port mp1,m_port mp2 where pu.commodity = m.commodity_code AND mp1.port_code=load_port AND mp2.port_code=disch_port AND pu.disch_port != (SELECT value_string FROM m_setting_app WHERE id = 'ebtos.implement_port_code') AND pu.operation='DISCHARGE' AND is_transhipment = false AND is_shifting = false AND pu.status='PLANNING' AND pu.no_ppkb=?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findUncontainerizedAfterTranshipment", query = "SELECT pu.id_uc,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.id_uc, pu.unit,pu.block,mp1.name as loadPort,mp2.name as dischPort,pu.status,pu.operation from uncontainerized_service pu, m_commodity m,m_port mp1,m_port mp2 where pu.commodity = m.commodity_code AND mp1.port_code=load_port AND mp2.port_code=disch_port AND pu.disch_port != (SELECT value_string FROM m_setting_app WHERE id = 'ebtos.implement_port_code') AND pu.operation='DISCHARGE' AND is_transhipment=true AND pu.no_ppkb=?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findUncontainerizedBayPlanLoadList", query = "SELECT pu.id_uc,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.unit,pu.block,mp1.name as loadPort,mp2.name as dischPort,pu.status,pu.operation,pu.is_transhipment,pu.bay_plan from uncontainerized_service pu, m_commodity m,m_port mp1,m_port mp2 where pu.commodity = m.commodity_code AND mp1.port_code=pu.load_port AND mp2.port_code=pu.disch_port AND pu.operation='LOAD' and pu.no_ppkb=?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findUncontainerizedBayPlanLoadListSelect", query = "SELECT pu.id_uc,pu.bl_no, m.name, pu.weight, change(pu.truck_loosing), pu.unit,pu.block,mp1.name as loadPort,mp2.name as dischPort,pu.status,pu.operation,pu.is_transhipment,pu.new_ppkb from uncontainerized_service pu, m_commodity m,m_port mp1,m_port mp2 where pu.commodity = m.commodity_code AND mp1.port_code=pu.load_port AND mp2.port_code=pu.disch_port AND pu.is_transhipment=true  AND (pu.new_ppkb= ? or pu.new_ppkb IS NULL) AND pu.status='CY' AND pu.bay_plan=false AND pu.bl_no NOT IN (select u.bl_no from uncontainerized_service u where pu.no_ppkb =u.no_ppkb AND pu.bl_no=u.bl_no AND operation='LOAD' AND is_transhipment=false)"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findHandlingDischarge", query = "SELECT us.bl_no, "
                                                                                            + "(SELECT mc.name FROM m_commodity mc WHERE us.commodity::text = mc.commodity_code) AS commodity, "
                                                                                            + "us.unit, us.weight, us.cubication AS vol, us.truck_loosing,"
                                                                                            + "e1.equip_code AS cc_code, e1.end_time AS stv_date, "
                                                                                            + "e2.equip_code AS ht_code, e2.end_time AS ht_date, "
                                                                                            + "e3.equip_code AS tt_code, e3.end_time AS lift_date "
                                                                                            + "FROM uncontainerized_service us "
                                                                                            + "LEFT JOIN equipment e1 ON e1.no_ppkb::text = us.no_ppkb::text AND e1.bl_no::text = us.bl_no::text AND e1.equipment_act_code = 'STEVEDORING'::text AND e1.operation::text = 'DISCHARGEUC'::text "
                                                                                            + "LEFT JOIN equipment e2 ON e2.no_ppkb::text = us.no_ppkb::text AND e2.bl_no::text = us.bl_no::text AND e2.equipment_act_code = 'H/T'::text AND e2.operation::text = 'DISCHARGEUC'::text "
                                                                                            + "LEFT JOIN equipment e3 ON e3.no_ppkb::text = us.no_ppkb::text AND e3.bl_no::text = us.bl_no::text AND e3.equipment_act_code = 'LIFTOFF'::text AND e3.operation::text = 'DISCHARGEUC'::text "
                                                                                            + "WHERE us.operation='DISCHARGE' AND us.is_shifting=FALSE AND us.no_ppkb = ?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findHandlingLoad", query = "SELECT us.bl_no, "
                                                                                        + "(SELECT mc.name FROM m_commodity mc WHERE us.commodity::text = mc.commodity_code) AS commodity, "
                                                                                        + "us.unit, us.weight, us.cubication AS vol, us.truck_loosing, "
                                                                                        + "e1.equip_code AS tt_code, e1.end_time AS lift_date, "
                                                                                        + "e2.equip_code AS ht_code, e2.end_time AS ht_date, "
                                                                                        + "e3.equip_code AS cc_code, e3.end_time AS stv_date "
                                                                                        + "FROM uncontainerized_service us "
                                                                                        + "LEFT JOIN equipment e1 ON e1.no_ppkb::text = us.no_ppkb::text AND e1.bl_no::text = us.bl_no::text AND e1.equipment_act_code = 'LIFTON'::text AND e1.operation::text = 'LOADUC'::text "
                                                                                        + "LEFT JOIN equipment e2 ON e2.no_ppkb::text = us.no_ppkb::text AND e2.bl_no::text = us.bl_no::text AND e2.equipment_act_code = 'H/T'::text AND e2.operation::text = 'LOADUC'::text "
                                                                                        + "LEFT JOIN equipment e3 ON e3.no_ppkb::text = us.no_ppkb::text AND e3.bl_no::text = us.bl_no::text AND e3.equipment_act_code = 'STEVEDORING'::text AND e3.operation::text = 'LOADUC'::text "
                                                                                        + "WHERE us.operation='LOAD' AND us.is_shifting=FALSE AND us.no_ppkb = ?"),
    @NamedNativeQuery(name = "UncontainerizedService.Native.findUncontainerizedBayPlanLoadId", query = "select pu.id_uc from uncontainerized_service pu where pu.operation='DISCHARGE' AND pu.is_transhipment=true AND pu.no_ppkb=? AND pu.bl_no=? limit 1")
})
public class UncontainerizedService implements Serializable, EntityAuditable {
    public static final String STATUS_OUTSIDE = "OUTSIDE";
    public static final String STATUS_STV = "STV";
    public static final String STATUS_CY = "CY";
    public static final String STATUS_VESSEL = "VESSEL";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_uc", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUc;
    @Column(name = "bl_no")
    private String blNo;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "block", length = 5)
    private String block;
    @Column(name = "commodity", length = 5)
    private String commodity;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "truck_loosing")
    private Boolean truckLoosing = false;
    @Column(name = "disch_port", length = 10)
    private String dischPort;
    @Column(name = "load_port", length = 10)
    private String loadPort;
    @Column(name = "crane", length = 4)
    private String crane;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "is_delivery")
    private Boolean isDelivery;
    @Column(name = "placement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placementDate;
    @Column(name = "operation", length = 20)
    private String operation;
    @Column(name = "unit")
    private Integer unit;
    @Column(name = "is_shifting")
    private Boolean isShifting;
    @Column(name = "shift_to")
    private String shiftTo;
    @Column(name = "is_transhipment")
    private Boolean isTranshipment=false;
    @Column(name = "new_ppkb")
    private String newPpkb;
    @Column(name = "temp_ppkb")
    private String tempPpkb;
    @Column(name = "bay_plan")
    private Boolean bayPlan=Boolean.TRUE;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
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
    @Column(name = "cubication", precision = 19, scale = 5)
    private BigDecimal cubication;
    @OneToOne(mappedBy = "uncontainerizedService")
    private UcReceivingService ucReceivingService;
    @OneToOne(mappedBy = "uncontainerizedService")
    private UcDeliveryService ucDeliveryService;
    @Basic(optional = false)
    @Column(name = "is_export_import", nullable = false)
    private boolean isExportImport = false;

    public UncontainerizedService() {
    }

    public String getShiftTo() {
        return shiftTo;
    }

    public void setShiftTo(String shiftTo) {
        this.shiftTo = shiftTo;
    }

    public Boolean getIsShifting() {
        return isShifting;
    }

    public void setIsShifting(Boolean isShifting) {
        this.isShifting = isShifting;
    }

    public UncontainerizedService(Integer idUc) {
        this.idUc = idUc;
    }

    public Integer getIdUc() {
        return idUc;
    }

    public void setIdUc(Integer idUc) {
        this.idUc = idUc;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getTruckLoosing() {
        return truckLoosing;
    }

    public void setTruckLoosing(Boolean truckLoosing) {
        this.truckLoosing = truckLoosing;
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

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Boolean isDelivery) {
        this.isDelivery = isDelivery;
    }

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
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

    public Boolean getIsTranshipment() {
        return isTranshipment;
    }

    public void setIsTranshipment(Boolean isTranshipment) {
        this.isTranshipment = isTranshipment;
    }

    public String getNewPpkb() {
        return newPpkb;
    }

    public void setNewPpkb(String newPpkb) {
        this.newPpkb = newPpkb;
    }

    public String getTempPpkb() {
        return tempPpkb;
    }

    public void setTempPpkb(String tempPpkb) {
        this.tempPpkb = tempPpkb;
    }

    public Boolean getBayPlan() {
        return bayPlan;
    }

    public void setBayPlan(Boolean bayPlan) {
        this.bayPlan = bayPlan;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getCubication() {
        return cubication;
    }

    public void setCubication(BigDecimal cubication) {
        this.cubication = cubication;
    }

    public UcReceivingService getUcReceivingService() {
        return ucReceivingService;
    }

    public void setUcReceivingService(UcReceivingService ucReceivingService) {
        this.ucReceivingService = ucReceivingService;
    }

    public UcDeliveryService getUcDeliveryService() {
        return ucDeliveryService;
    }

    public void setUcDeliveryService(UcDeliveryService ucDeliveryService) {
        this.ucDeliveryService = ucDeliveryService;
    }

    public boolean getIsExportImport() {
        return isExportImport;
    }

    public void setIsExportImport(boolean isExportImport) {
        this.isExportImport = isExportImport;
    }

    public Integer getWeightClassification() {
        if (weight != null) {
            if (weight <= 20000) {
                return 0;
            } else {
                return 1;
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUc != null ? idUc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UncontainerizedService)) {
            return false;
        }
        UncontainerizedService other = (UncontainerizedService) object;
        if ((this.idUc == null && other.idUc != null) || (this.idUc != null && !this.idUc.equals(other.idUc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.UncontainerizedService[idUc=" + idUc + "]";
    }
}
