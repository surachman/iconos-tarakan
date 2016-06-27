/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db.master;

import com.qtasnim.persistence.AuditTrailEntityListener;
import com.qtasnim.persistence.EntityAuditable;
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
@Table(name = "m_yard_coordinat")
@NamedQueries({
    @NamedQuery(name = "MasterYardCoordinat.findAll", query = "SELECT m FROM MasterYardCoordinat m"),
    @NamedQuery(name = "MasterYardCoordinat.findByContNo", query = "SELECT m FROM MasterYardCoordinat m WHERE m.contNo = :contNo"),
    @NamedQuery(name = "MasterYardCoordinat.findByContType", query = "SELECT m FROM MasterYardCoordinat m WHERE m.contType = :contType"),
    @NamedQuery(name = "MasterYardCoordinat.findByContSize", query = "SELECT m FROM MasterYardCoordinat m WHERE m.contSize = :contSize"),
    @NamedQuery(name = "MasterYardCoordinat.findByContWeight", query = "SELECT m FROM MasterYardCoordinat m WHERE m.contWeight = :contWeight"),
    @NamedQuery(name = "MasterYardCoordinat.findBySlot", query = "SELECT m FROM MasterYardCoordinat m WHERE m.slot = :slot"),
    @NamedQuery(name = "MasterYardCoordinat.findByRow", query = "SELECT m FROM MasterYardCoordinat m WHERE m.row = :row"),
    @NamedQuery(name = "MasterYardCoordinat.findByTier", query = "SELECT m FROM MasterYardCoordinat m WHERE m.tier = :tier"),
    @NamedQuery(name = "MasterYardCoordinat.updatePlanningVesselByContNo", query = "UPDATE MasterYardCoordinat e SET e.noPpkb = :newValue, e.pod= :pod WHERE e.noPpkb = :oldValue AND e.contNo IN :containers"),
    @NamedQuery(name = "MasterYardCoordinat.findAvailableCoordinat", query="SELECT m FROM MasterYardCoordinat m WHERE m.block = :block AND m.slot = :slot AND m.row = :row AND m.tier = :tier AND m.status IN ('empty', 'planning')"),
    @NamedQuery(name = "MasterYardCoordinat.findByCoordinatAndStatus", query="SELECT m FROM MasterYardCoordinat m WHERE m.block = :block AND m.slot = :slot AND m.row = :row AND m.tier = :tier AND m.status IN :statusses"),
    @NamedQuery(name = "MasterYardCoordinat.findByCoordinat", query="SELECT m FROM MasterYardCoordinat m WHERE m.block = :block AND m.slot = :slot AND m.row = :row AND m.tier = :tier"),
    @NamedQuery(name = "MasterYardCoordinat.findByIdCollection", query = "SELECT m FROM MasterYardCoordinat m WHERE m.id IN :ids ORDER BY m.block, m.tier, m.row, m.slot"),
    @NamedQuery(name = "MasterYardCoordinat.findByBlock", query = "SELECT m FROM MasterYardCoordinat m WHERE m.block = :block"),
    @NamedQuery(name = "MasterYardCoordinat.findByStatus", query = "SELECT m FROM MasterYardCoordinat m WHERE m.status = :status"),
    @NamedQuery(name = "MasterYardCoordinat.findById", query = "SELECT m FROM MasterYardCoordinat m WHERE m.id = :id"),
    @NamedQuery(name = "MasterYardCoordinat.findByContNoAndStatusExist", query = "SELECT m FROM MasterYardCoordinat m WHERE m.contNo = :contNo AND m.status = 'exist'"),
    @NamedQuery(name = "MasterYardCoordinat.clearYardByPpkbAndStatus", query = "UPDATE MasterYardCoordinat m SET m.contNo = NULL, m.contType = NULL, m.contSize = NULL, m.noPpkb = NULL, m.contWeight = NULL, m.status = 'empty', m.grossClass = NULL, m.blNo = NULL, m.mlo = NULL, m.pod = NULL WHERE m.noPpkb = :noPpkb AND m.status = :status"),
    @NamedQuery(name = "MasterYardCoordinat.clearYardsByContNo", query = "UPDATE MasterYardCoordinat m SET m.contNo = NULL, m.contType = NULL, m.contSize = NULL, m.noPpkb = NULL, m.contWeight = NULL, m.status = 'empty', m.grossClass = NULL, m.blNo = NULL, m.mlo = NULL, m.pod = NULL WHERE m.contNo = :contNo"),
    @NamedQuery(name = "MasterYardCoordinat.clearYardsByNoPpkbAndContNo", query = "UPDATE MasterYardCoordinat m SET m.contNo = NULL, m.contType = NULL, m.contSize = NULL, m.noPpkb = NULL, m.contWeight = NULL, m.status = 'empty', m.grossClass = NULL, m.blNo = NULL, m.mlo = NULL, m.pod = NULL WHERE m.contNo = :contNo AND m.noPpkb = :noPpkb AND m.status IN :statusses"),
    @NamedQuery(name = "MasterYardCoordinat.revertContNoById", query = "UPDATE MasterYardCoordinat m SET m.contNo = :revertedContNo, m.mlo = NULL, m.contWeight = NULL WHERE m.id = :id"),
    @NamedQuery(name = "MasterYardCoordinat.changeNoPpkbByContNo", query = "UPDATE MasterYardCoordinat m SET m.noPpkb = :newPpkb WHERE m.noPpkb = :oldPpkb AND m.contNo = :contNo"),
    @NamedQuery(name = "MasterYardCoordinat.revertContNoByPpkb", query = "UPDATE MasterYardCoordinat m SET m.contNo = :revertedContNo, m.mlo = NULL, m.contWeight = NULL WHERE m.noPpkb = :noPpkb AND m.contNo = :targetContNo"),
    @NamedQuery(name = "MasterYardCoordinat.changeStatusToPlanningByNoPpkbAndContNo", query = "UPDATE MasterYardCoordinat m SET m.status = 'planning', m.mlo = NULL, m.contWeight = NULL WHERE m.noPpkb = :noPpkb AND m.contNo = :contNo"),
    @NamedQuery(name = "MasterYardCoordinat.deleteConstraintByAllocationRange", query = "UPDATE MasterYardCoordinat m "
        + "SET m.contNo = NULL, m.contType = NULL, m.contSize = NULL, m.noPpkb = NULL, m.contWeight = NULL, m.status = 'empty', m.grossClass = NULL, m.blNo = NULL, m.mlo = NULL, m.pod = NULL "
        + "WHERE m.noPpkb = :noPpkb AND m.slot BETWEEN :frSlot AND :toSlot AND m.row BETWEEN :frRow AND :toRow AND LENGTH(m.contNo) > 11 ")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "MasterYardCoordinat.Native.findContByBlockAndSlot", query = "SELECT "
    + "myc.cont_no, myc.cont_type, myc.cont_size, myc.cont_weight, myc.slot, myc.row, myc.tier, myc.block, myc.status, myc.id, myc.gross_class, mgc.color, mct.feet_mark, myc.no_ppkb, mp.name, myc.pod "
    + "FROM "
    + "(m_yard_coordinat myc "
    + "LEFT JOIN m_gross_class mgc ON (mgc.gross_class=myc.gross_class)) "
    + "LEFT JOIN (m_container_type mct "
    + "JOIN m_container_type_in_general mctig ON (mct.type_in_general=mctig.id) "
    + "JOIN m_pattern mp ON (mctig.pattern=mp.name)) ON (myc.cont_type::integer=mct.cont_type) "
    + "WHERE block=? AND status<>'empty' AND slot=?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findContByBlockSlotAndPpkb",query="SELECT " +
                                                                                                "myc.cont_no, myc.cont_type, myc.cont_size, myc.cont_weight, myc.slot, myc.row, myc.tier, myc.block, myc.status, myc.id, myc.gross_class, mgc.color, mct.feet_mark, myc.no_ppkb, mp.name " +
                                                                                        "FROM " +
                                                                                                "(m_yard_coordinat myc " +
                                                                                                        "LEFT JOIN m_gross_class mgc ON (mgc.gross_class=myc.gross_class)) " +
                                                                                                        "LEFT JOIN (m_container_type mct " +
                                                                                                                "JOIN m_container_type_in_general mctig ON (mct.type_in_general=mctig.id) " +
                                                                                                                        "JOIN m_pattern mp ON (mctig.pattern=mp.name)) ON (myc.cont_type::integer=mct.cont_type) " +
                                                                                        "WHERE " +
                                                                                                "block=? AND status<>'empty' AND slot=? AND no_ppkb LIKE ('%'|| ? ||'%');"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findContByBlockAndTier",query="SELECT " +
                                                                                            "myc.cont_no, myc.cont_type, myc.cont_size, myc.cont_weight, myc.slot, myc.row, myc.tier, myc.block, myc.status, myc.id, myc.gross_class, mgc.color, mct.feet_mark, myc.no_ppkb, mp.name, myc.pod " +
                                                                                    "FROM " +
                                                                                            "(m_yard_coordinat myc " +
                                                                                                    "LEFT JOIN m_gross_class mgc ON (mgc.gross_class=myc.gross_class)) " +
                                                                                                    "LEFT JOIN (m_container_type mct " +
                                                                                                            "JOIN m_container_type_in_general mctig ON (mct.type_in_general=mctig.id) " +
                                                                                                                    "JOIN m_pattern mp ON (mctig.pattern=mp.name)) ON (myc.cont_type::integer=mct.cont_type) " +
                                                                                    "WHERE " +
                                                                                            "block=? AND status<>'empty' AND tier=? " +
                                                                                    "ORDER BY " +
                                                                                            "myc.cont_size, myc.cont_no, myc.slot"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findContByBlockTierAndPpkb",query="SELECT " +
                                                                                            "myc.cont_no, myc.cont_type, myc.cont_size, myc.cont_weight, myc.slot, myc.row, myc.tier, myc.block, myc.status, myc.id, myc.gross_class, mgc.color, mct.feet_mark, myc.no_ppkb, mp.name " +
                                                                                    "FROM " +
                                                                                            "(m_yard_coordinat myc " +
                                                                                                    "LEFT JOIN m_gross_class mgc ON (mgc.gross_class=myc.gross_class)) " +
                                                                                                    "LEFT JOIN (m_container_type mct " +
                                                                                                            "JOIN m_container_type_in_general mctig ON (mct.type_in_general=mctig.id) " +
                                                                                                                    "JOIN m_pattern mp ON (mctig.pattern=mp.name)) ON (myc.cont_type::integer=mct.cont_type) " +
                                                                                    "WHERE " +
                                                                                            "block=? AND status<>'empty' AND tier=? AND no_ppkb LIKE ('%'|| ? ||'%') " +
                                                                                    "ORDER BY " +
                                                                                            "myc.cont_size, myc.cont_no, myc.slot"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findContByBlockAndSlotExistOnly",query="SELECT " +
                                                                                            "myc.cont_no, myc.cont_type, myc.cont_size, myc.cont_weight, myc.slot, myc.row, myc.tier, myc.block, myc.status, myc.id, myc.gross_class, mgc.color, mct.feet_mark, myc.no_ppkb, mp.name, myc.pod " +
                                                                                    "FROM " +
                                                                                            "(m_yard_coordinat myc " +
                                                                                                    "LEFT JOIN m_gross_class mgc ON (mgc.gross_class=myc.gross_class)) " +
                                                                                                    "LEFT JOIN (m_container_type mct " +
                                                                                                            "JOIN m_container_type_in_general mctig ON (mct.type_in_general=mctig.id) " +
                                                                                                                    "JOIN m_pattern mp ON (mctig.pattern=mp.name)) ON (myc.cont_type::integer=mct.cont_type) " +
                                                                                    "WHERE " +
                                                                                            "block=? AND status = 'exist' AND slot=?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findContByBlockSlotAndPpkbExistOnly",query="SELECT "
                                                                                                        + "myc.cont_no, myc.cont_type, myc.cont_size, myc.cont_weight, myc.slot, myc.row, myc.tier, myc.block, myc.status, myc.id, myc.gross_class, myc.color, myc.feet_mark, myc.no_ppkb, myc.name, pcl.v_bay, pcl.v_row, pcl.v_tier, myc.is_tail, myc.pod "
                                                                                                + "FROM (SELECT "
                                                                                                        + "myc.cont_no, myc.cont_type, myc.cont_size, myc.cont_weight, myc.slot, myc.row, myc.tier, myc.block, myc.status, myc.id, myc.gross_class, mgc.color, mct.feet_mark, myc.no_ppkb, mp.name, myc.pod, "
                                                                                                        + "(obj.slot <> myc.slot AND myc.cont_size = 40) is_tail "
                                                                                                        + "FROM (SELECT ? AS slot) obj, "
                                                                                                                + "((m_yard_coordinat myc "
                                                                                                                        + "LEFT JOIN m_gross_class mgc ON (mgc.gross_class=myc.gross_class)) "
                                                                                                                        + "LEFT JOIN (m_container_type mct "
                                                                                                                                + "JOIN m_container_type_in_general mctig ON (mct.type_in_general=mctig.id) "
                                                                                                                                        + "JOIN m_pattern mp ON (mctig.pattern=mp.name)) ON (myc.cont_type::integer=mct.cont_type)) "
                                                                                                        + "WHERE "
                                                                                                                + "myc.block=? AND myc.status = 'exist' AND (myc.slot=obj.slot OR (myc.slot=obj.slot-1 AND myc.cont_size = 40)) AND myc.no_ppkb LIKE ('%' || ? || '%')) myc "
                                                                                                        + "LEFT JOIN (SELECT no_ppkb, cont_no, block, y_slot, y_row, y_tier, v_bay, v_row, v_tier, MAX(created_by) "
                                                                                                                    + "FROM planning_cont_load "
                                                                                                                    + "GROUP BY no_ppkb, cont_no, block, y_slot, y_row, y_tier, v_bay, v_row, v_tier) pcl ON (myc.no_ppkb=pcl.no_ppkb AND myc.cont_no=pcl.cont_no) "
                                                                                                + "WHERE myc.slot=pcl.y_slot;"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findContByBlockAndTierExistOnly",query="SELECT " +
                                                                                            "myc.cont_no, myc.cont_type, myc.cont_size, myc.cont_weight, myc.slot, myc.row, myc.tier, myc.block, myc.status, myc.id, myc.gross_class, mgc.color, mct.feet_mark, myc.no_ppkb, mp.name, myc.pod " +
                                                                                    "FROM " +
                                                                                            "(m_yard_coordinat myc " +
                                                                                                    "LEFT JOIN m_gross_class mgc ON (mgc.gross_class=myc.gross_class)) " +
                                                                                                    "LEFT JOIN (m_container_type mct " +
                                                                                                            "JOIN m_container_type_in_general mctig ON (mct.type_in_general=mctig.id) " +
                                                                                                                    "JOIN m_pattern mp ON (mctig.pattern=mp.name)) ON (myc.cont_type::integer=mct.cont_type) " +
                                                                                    "WHERE " +
                                                                                            "block=? AND status = 'exist' AND tier=? " +
                                                                                    "ORDER BY " +
                                                                                            "myc.cont_size, myc.cont_no, myc.slot"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findContBfindByContNoStatusExistyBlockTierAndPpkbExistOnly",query="SELECT " +
                                                                                                            "myc.cont_no, myc.cont_type, myc.cont_size, myc.cont_weight, myc.slot, myc.row, myc.tier, myc.block, myc.status, myc.id, myc.gross_class, mgc.color, mct.feet_mark, myc.no_ppkb, mp.name, pcl.v_bay, pcl.v_row, pcl.v_tier " +
                                                                                                    "FROM " +
                                                                                                            "((m_yard_coordinat myc " +
                                                                                                                    "LEFT JOIN m_gross_class mgc ON (mgc.gross_class=myc.gross_class)) " +
                                                                                                                    "LEFT JOIN (m_container_type mct " +
                                                                                                                            "JOIN m_container_type_in_general mctig ON (mct.type_in_general=mctig.id) " +
                                                                                                                                    "JOIN m_pattern mp ON (mctig.pattern=mp.name)) ON (myc.cont_type::integer=mct.cont_type)) " +
                                                                                                                    "LEFT JOIN (SELECT no_ppkb, block, y_slot, y_row, y_tier, v_bay, v_row, v_tier, MAX(created_by) FROM planning_cont_load GROUP BY no_ppkb, block, y_slot, y_row, y_tier, v_bay, v_row, v_tier) pcl ON (myc.no_ppkb=pcl.no_ppkb AND myc.block=pcl.block AND  myc.slot=pcl.y_slot AND  myc.row=pcl.y_row AND  myc.tier=pcl.y_tier) " +
                                                                                                    "WHERE " +
                                                                                                            "myc.block=? AND myc.status = 'exist' AND myc.tier=? AND myc.no_ppkb LIKE ('%'|| ? ||'%') " +
                                                                                                    "ORDER BY " +
                                                                                                            "myc.cont_size, myc.cont_no, myc.slot"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findOverallContByBlock",query="SELECT myc.slot, myc.row, SUM(CASE WHEN status = 'exist' THEN 1 ELSE 0 END)::integer "
                                                                                            + "FROM m_yard_coordinat myc WHERE block = ? "
                                                                                            + "GROUP BY myc.slot, myc.row "
                                                                                            + "ORDER BY myc.slot, myc.row"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.isBottomAvailable",query="SELECT (p.tier = 1 OR (COUNT(CASE WHEN myc.cont_no IS NOT NULL THEN 1 END) = p.teus)) is_bottom_available "
                                                                                + "FROM (SELECT ?::varchar block, ? slot, ? \"row\", ? tier, ? teus) p "
                                                                                        + "LEFT JOIN m_yard_coordinat myc ON (myc.block = p.block AND myc.status = 'exist' AND myc.row = p.row AND myc.tier = (p.tier - 1) AND myc.slot BETWEEN p.slot AND (p.slot + p.teus - 1)) "
                                                                                + "GROUP BY p.tier, p.teus"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.isTopEmpty",query="SELECT (p.tier = my.tier OR (COUNT(CASE WHEN myc.status IS NOT NULL THEN 1 END) = p.teus)) is_top_empty "
                                                                                + "FROM (SELECT ?::varchar block, ? slot, ? \"row\", ? tier, ? teus) p "
                                                                                        + "LEFT JOIN m_yard_coordinat myc ON (myc.block = p.block AND myc.status IN ('planning', 'empty') AND myc.row = p.row AND myc.tier = (p.tier + 1) AND myc.slot BETWEEN p.slot AND (p.slot + p.teus - 1)) "
                                                                                        + "JOIN m_yard my ON (p.block = my.block) "
                                                                                + "GROUP BY p.tier, p.teus, my.tier"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findOverallContByBlockColors",query="SELECT myc.slot, myc.row, SUM(CASE WHEN status = 'exist' THEN 1 ELSE 0 END)::integer, myc.tier "
                                                                                            + "FROM m_yard_coordinat myc WHERE block = ? "
                                                                                            + "GROUP BY myc.slot, myc.row, myc.tier "
                                                                                            + "ORDER BY myc.slot, myc.row, myc.tier"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findIdStatusByCoordinat",query="SELECT id, status, cont_no, no_ppkb FROM m_yard_coordinat WHERE block =? AND slot =? AND row =? AND tier =?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findIdCordinatByStatus", query="SELECT id, status FROM m_yard_coordinat WHERE block =? AND slot =? AND row =? AND tier =? AND status <> 'exist'"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findIdCordinatByStatusPlanningEmpty", query="SELECT id,status FROM m_yard_coordinat WHERE block =? AND slot =? AND row =? AND tier =? AND (status = 'empty' OR status='planning')"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findIdCordinatByPlanning", query="SELECT id,status FROM m_yard_coordinat WHERE block =? AND slot =? AND row =? AND tier =? AND status='planning'"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findByContNo", query="SELECT id,status FROM m_yard_coordinat WHERE cont_no = ? AND (status = 'empty' OR status='planning')"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findIdCordinatByStatusExist", query="SELECT id,status FROM m_yard_coordinat WHERE block =? AND slot =? AND row =? AND tier =? AND status='exist'"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findIdByContNo", query="SELECT id FROM m_yard_coordinat WHERE cont_no = ?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findIdByNoPpkbAndContNo", query="SELECT id FROM m_yard_coordinat WHERE no_ppkb = ? AND cont_no = ?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.deleteByBlock", query="DELETE FROM m_yard_coordinat WHERE block=?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findAllStatusByBlock", query="SELECT id, status FROM m_yard_coordinat WHERE block=?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.deleteById", query="DELETE FROM m_yard_coordinat WHERE id=?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findGenerate", query="SELECT a.cont_no, a.cont_size, b.type_in_general as name, a.cont_weight, c.description, a.block, a.slot, a.row, a.tier, a.id FROM m_yard_coordinat a, m_container_type b, m_gross_class c WHERE a.cont_type::integer=b.cont_type AND a.gross_class=c.gross_class AND a.id IN(SELECT id_coordinat FROM yard_allocation_temp WHERE no_ppkb=? AND is_transhipment=? AND is_shifting=?) ORDER BY a.id"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findContNoGenerate", query="SELECT MAX(substring(cont_no,12,4))FROM m_yard_coordinat WHERE substring(cont_no,1,10) = ?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.deleteByContNo", query="UPDATE m_yard_coordinat SET cont_no = null, cont_type=null, cont_size=null, cont_weight=null, status = 'empty', gross_class = null, no_ppkb = null, mlo = null, pod = null WHERE status <> 'exist' AND cont_no = ?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.deleteByContNoAndPpkb", query="UPDATE m_yard_coordinat SET cont_no = null, cont_type=null, cont_size=null, cont_weight=null, status = 'empty', gross_class = null, no_ppkb = null, mlo = null, pod = null WHERE status = 'planning' AND cont_no = ? and no_ppkb=?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.isFlyingContainer", query="SELECT status FROM m_yard_coordinat WHERE block=? and slot=? and row=? and tier=?-1 AND status<>'exist'"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.unFinishBayPlanDischargeByPPKB", query="SELECT DISTINCT id FROM m_yard_coordinat WHERE id IN (SELECT DISTINCT id_coordinat FROM yard_allocation_temp WHERE no_ppkb=? AND id_coordinat IS NOT NULL)"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findDuplicate", query="SELECT cont_no, block, slot, row, tier FROM m_yard_coordinat WHERE cont_no=? LIMIT 1"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findByContNoStatusExist", query="SELECT myc.cont_no, myc.cont_type, myc.cont_size, myc.cont_weight, myc.slot, myc.row, myc.tier, "
                                                                                + "myc.block, myc.status, myc.id, myc.gross_class, myc.bl_no, myc.created_by, myc.created_date, "
                                                                                + "myc.modified_by, myc.modified_date, myc.no_ppkb, myc.mlo "
                                                                            + "FROM m_yard_coordinat myc "
                                                                            + "WHERE myc.cont_no = ? AND myc.status = 'exist'"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findIdByContNoUpdatePlugging", query="SELECT id FROM m_yard_coordinat WHERE cont_no = ? AND no_ppkb=?"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findPlannedCoordinateByNoPpkbAndDestination", query="SELECT myc.id "
                                                                                            + "FROM m_yard_coordinat myc "
                                                                                                    + "JOIN (SELECT pya.no_ppkb, py.block, py.fr_slot, py.to_slot, py.fr_row, py.to_row, pya.port_code "
                                                                                                            + "FROM planning_receiving_allocation pya "
                                                                                                                    + "JOIN planning_receiving py ON (py.receiving_allocation_id=pya.id)) AS pya ON (myc.no_ppkb = pya.no_ppkb AND myc.block = pya.block AND (myc.slot BETWEEN pya.fr_slot AND pya.to_slot AND myc.row BETWEEN pya.fr_row AND pya.to_row)) "
                                                                                            + "WHERE myc.no_ppkb = ? AND char_length(myc.cont_no) > 11 AND myc.status = 'planning' AND pya.port_code = ? "),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findAvailableCoordinateIdByAllocationConstraint", query="SELECT myc.id "
                                                                                                        + "FROM m_yard_coordinat myc "
                                                                                                                + "JOIN (SELECT pya.no_ppkb, py.block, py.fr_slot, py.to_slot, py.fr_row, py.to_row, pya.port_code, pya.cont_type, pya.gross_class, pya.cont_size, pya.cont_status, pya.over_size, pya.dg, pya.is_export "
                                                                                                                        + "FROM planning_receiving_allocation pya "
                                                                                                                        + "JOIN planning_receiving py ON (py.receiving_allocation_id=pya.id)) AS pya "
                                                                                                                + "ON (myc.no_ppkb = pya.no_ppkb AND myc.block = pya.block AND (myc.slot BETWEEN pya.fr_slot AND pya.to_slot AND myc.row BETWEEN pya.fr_row AND pya.to_row)) "
                                                                                                        + "WHERE myc.no_ppkb = ?  AND char_length(myc.cont_no) > 11 AND myc.status = 'planning' AND pya.port_code = ? AND pya.cont_size=? AND pya.cont_type=? AND pya.cont_status=? AND pya.gross_class=? AND pya.over_size = ? AND pya.dg = ? AND pya.is_export = ?;"),
    @NamedNativeQuery(name="MasterYardCoordinat.Native.findStatistics",query="SELECT COUNT(cont_size) jumlah from m_yard_coordinat where status='exist' and cont_size = ?")
    })
public class MasterYardCoordinat implements Serializable, EntityAuditable {
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
    @Column(name = "cont_no")
    private String contNo;
    @Column(name = "cont_type", length = 10)
    private String contType;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "cont_weight")
    private Integer contWeight;
    @Column(name = "slot")
    private Short slot;
    @Column(name = "row")
    private Short row;
    @Column(name = "tier")
    private Short tier;
    @Column(name = "block", length = 2147483647)
    private String block;
    @Column(name = "status", length = 2147483647)
    private String status;
    @Column(name = "gross_class", length = 2)
    private String grossClass;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "mlo", referencedColumnName = "cust_code")
    @ManyToOne
    private MasterCustomer mlo;
    @Column(name = "pod", length = 10)
    private String pod;
    
    public MasterYardCoordinat() {
    }

    public MasterYardCoordinat(Integer id) {
        this.id = id;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getContType() {
        return contType;
    }

    public void setContType(String contType) {
        this.contType = contType;
    }

    public Short getContSize() {
        return contSize;
    }

    public void setContSize(Short contSize) {
        this.contSize = contSize;
    }

    public Integer getContWeight() {
        return contWeight;
    }

    public void setContWeight(Integer contWeight) {
        this.contWeight = contWeight;
    }

    public Short getSlot() {
        return slot;
    }

    public void setSlot(Short slot) {
        this.slot = slot;
    }

    public Short getRow() {
        return row;
    }

    public void setRow(Short row) {
        this.row = row;
    }

    public Short getTier() {
        return tier;
    }

    public void setTier(Short tier) {
        this.tier = tier;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the grossClass
     */
    public String getGrossClass() {
        return grossClass;
    }

    /**
     * @param grossClass the grossClass to set
     */
    public void setGrossClass(String grossClass) {
        this.grossClass = grossClass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public MasterCustomer getMlo() {
        return mlo;
    }

    public void setMlo(MasterCustomer mlo) {
        this.mlo = mlo;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
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
        if (!(object instanceof MasterYardCoordinat)) {
            return false;
        }
        MasterYardCoordinat other = (MasterYardCoordinat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pelindo.ebtos.model.db.master.MasterYardCoordinat[id=" + id + "]";
    }
}
