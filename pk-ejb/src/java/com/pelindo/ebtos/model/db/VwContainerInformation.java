/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author R. Seno Anggoro A
 */
@Entity
@Table(name = "vw_container_information")
@NamedQueries({
    @NamedQuery(name = "VwContainerInformation.findAll", query = "SELECT v FROM VwContainerInformation v"),
    @NamedQuery(name = "VwContainerInformation.findByNoPpkb", query = "SELECT v FROM VwContainerInformation v WHERE v.noPpkb = :noPpkb"),
    @NamedQuery(name = "VwContainerInformation.findByVesselCode", query = "SELECT v FROM VwContainerInformation v WHERE v.vesselCode = :vesselCode"),
    @NamedQuery(name = "VwContainerInformation.findByVesselName", query = "SELECT v FROM VwContainerInformation v WHERE v.vesselName = :vesselName"),
    @NamedQuery(name = "VwContainerInformation.findByLastPort", query = "SELECT v FROM VwContainerInformation v WHERE v.lastPort = :lastPort"),
    @NamedQuery(name = "VwContainerInformation.findByNextPort", query = "SELECT v FROM VwContainerInformation v WHERE v.nextPort = :nextPort"),
    @NamedQuery(name = "VwContainerInformation.findByVoyIn", query = "SELECT v FROM VwContainerInformation v WHERE v.voyIn = :voyIn"),
    @NamedQuery(name = "VwContainerInformation.findByVoyOut", query = "SELECT v FROM VwContainerInformation v WHERE v.voyOut = :voyOut"),
    @NamedQuery(name = "VwContainerInformation.findByEta", query = "SELECT v FROM VwContainerInformation v WHERE v.eta = :eta"),
    @NamedQuery(name = "VwContainerInformation.findByEtd", query = "SELECT v FROM VwContainerInformation v WHERE v.etd = :etd"),
    @NamedQuery(name = "VwContainerInformation.findByArrivalTime", query = "SELECT v FROM VwContainerInformation v WHERE v.arrivalTime = :arrivalTime"),
    @NamedQuery(name = "VwContainerInformation.findByDepartureTime", query = "SELECT v FROM VwContainerInformation v WHERE v.departureTime = :departureTime"),
    @NamedQuery(name = "VwContainerInformation.findByBlNo", query = "SELECT v FROM VwContainerInformation v WHERE v.blNo = :blNo"),
    @NamedQuery(name = "VwContainerInformation.findByContNo", query = "SELECT v FROM VwContainerInformation v WHERE v.contNo = :contNo"),
    @NamedQuery(name = "VwContainerInformation.findByIsoCode", query = "SELECT v FROM VwContainerInformation v WHERE v.isoCode = :isoCode"),
    @NamedQuery(name = "VwContainerInformation.findByNewIsoCode", query = "SELECT v FROM VwContainerInformation v WHERE v.newIsoCode = :newIsoCode"),
    @NamedQuery(name = "VwContainerInformation.findByContSize", query = "SELECT v FROM VwContainerInformation v WHERE v.contSize = :contSize"),
    @NamedQuery(name = "VwContainerInformation.findByCommodity", query = "SELECT v FROM VwContainerInformation v WHERE v.commodity = :commodity"),
    @NamedQuery(name = "VwContainerInformation.findByType", query = "SELECT v FROM VwContainerInformation v WHERE v.type = :type"),
    @NamedQuery(name = "VwContainerInformation.findByContStatus", query = "SELECT v FROM VwContainerInformation v WHERE v.contStatus = :contStatus"),
    @NamedQuery(name = "VwContainerInformation.findBySealNo", query = "SELECT v FROM VwContainerInformation v WHERE v.sealNo = :sealNo"),
    @NamedQuery(name = "VwContainerInformation.findByContGross", query = "SELECT v FROM VwContainerInformation v WHERE v.contGross = :contGross"),
    @NamedQuery(name = "VwContainerInformation.findByOd", query = "SELECT v FROM VwContainerInformation v WHERE v.od = :od"),
    @NamedQuery(name = "VwContainerInformation.findByDg", query = "SELECT v FROM VwContainerInformation v WHERE v.dg = :dg"),
    @NamedQuery(name = "VwContainerInformation.findByDgLbl", query = "SELECT v FROM VwContainerInformation v WHERE v.dgLbl = :dgLbl"),
    @NamedQuery(name = "VwContainerInformation.findByPosition", query = "SELECT v FROM VwContainerInformation v WHERE v.position = :position"),
    @NamedQuery(name = "VwContainerInformation.findByLoadPort", query = "SELECT v FROM VwContainerInformation v WHERE v.loadPort = :loadPort"),
    @NamedQuery(name = "VwContainerInformation.findByDischargePort", query = "SELECT v FROM VwContainerInformation v WHERE v.dischargePort = :dischargePort"),
    @NamedQuery(name = "VwContainerInformation.findByPod", query = "SELECT v FROM VwContainerInformation v WHERE v.pod = :pod"),
    @NamedQuery(name = "VwContainerInformation.findByOwner", query = "SELECT v FROM VwContainerInformation v WHERE v.owner = :owner"),
    @NamedQuery(name = "VwContainerInformation.findByVBay", query = "SELECT v FROM VwContainerInformation v WHERE v.vBay = :vBay"),
    @NamedQuery(name = "VwContainerInformation.findByVRow", query = "SELECT v FROM VwContainerInformation v WHERE v.vRow = :vRow"),
    @NamedQuery(name = "VwContainerInformation.findByVTier", query = "SELECT v FROM VwContainerInformation v WHERE v.vTier = :vTier"),
    @NamedQuery(name = "VwContainerInformation.findByCrane", query = "SELECT v FROM VwContainerInformation v WHERE v.crane = :crane"),
    @NamedQuery(name = "VwContainerInformation.findByStevedoring", query = "SELECT v FROM VwContainerInformation v WHERE v.stevedoring = :stevedoring"),
    @NamedQuery(name = "VwContainerInformation.findByStvStart", query = "SELECT v FROM VwContainerInformation v WHERE v.stvStart = :stvStart"),
    @NamedQuery(name = "VwContainerInformation.findByStvEnd", query = "SELECT v FROM VwContainerInformation v WHERE v.stvEnd = :stvEnd"),
    @NamedQuery(name = "VwContainerInformation.findByStvOptName", query = "SELECT v FROM VwContainerInformation v WHERE v.stvOptName = :stvOptName"),
    @NamedQuery(name = "VwContainerInformation.findByStvConfirmBy", query = "SELECT v FROM VwContainerInformation v WHERE v.stvConfirmBy = :stvConfirmBy"),
    @NamedQuery(name = "VwContainerInformation.findByHt", query = "SELECT v FROM VwContainerInformation v WHERE v.ht = :ht"),
    @NamedQuery(name = "VwContainerInformation.findByHtStart", query = "SELECT v FROM VwContainerInformation v WHERE v.htStart = :htStart"),
    @NamedQuery(name = "VwContainerInformation.findByHtEnd", query = "SELECT v FROM VwContainerInformation v WHERE v.htEnd = :htEnd"),
    @NamedQuery(name = "VwContainerInformation.findByHtOptName", query = "SELECT v FROM VwContainerInformation v WHERE v.htOptName = :htOptName"),
    @NamedQuery(name = "VwContainerInformation.findByHtConfirmBy", query = "SELECT v FROM VwContainerInformation v WHERE v.htConfirmBy = :htConfirmBy"),
    @NamedQuery(name = "VwContainerInformation.findByLiftOffOn", query = "SELECT v FROM VwContainerInformation v WHERE v.liftOffOn = :liftOffOn"),
    @NamedQuery(name = "VwContainerInformation.findByLiftStart", query = "SELECT v FROM VwContainerInformation v WHERE v.liftStart = :liftStart"),
    @NamedQuery(name = "VwContainerInformation.findByLiftEnd", query = "SELECT v FROM VwContainerInformation v WHERE v.liftEnd = :liftEnd"),
    @NamedQuery(name = "VwContainerInformation.findByLiftOptName", query = "SELECT v FROM VwContainerInformation v WHERE v.liftOptName = :liftOptName"),
    @NamedQuery(name = "VwContainerInformation.findByLiftConfirmBy", query = "SELECT v FROM VwContainerInformation v WHERE v.liftConfirmBy = :liftConfirmBy"),
    @NamedQuery(name = "VwContainerInformation.findByBlock", query = "SELECT v FROM VwContainerInformation v WHERE v.block = :block"),
    @NamedQuery(name = "VwContainerInformation.findByYSlot", query = "SELECT v FROM VwContainerInformation v WHERE v.ySlot = :ySlot"),
    @NamedQuery(name = "VwContainerInformation.findByYRow", query = "SELECT v FROM VwContainerInformation v WHERE v.yRow = :yRow"),
    @NamedQuery(name = "VwContainerInformation.findByYTier", query = "SELECT v FROM VwContainerInformation v WHERE v.yTier = :yTier"),
    @NamedQuery(name = "VwContainerInformation.findByDelRecvLoketDate", query = "SELECT v FROM VwContainerInformation v WHERE v.delRecvLoketDate = :delRecvLoketDate"),
    @NamedQuery(name = "VwContainerInformation.findByDelLoketBy", query = "SELECT v FROM VwContainerInformation v WHERE v.delLoketBy = :delLoketBy"),
    @NamedQuery(name = "VwContainerInformation.findByGateInDate", query = "SELECT v FROM VwContainerInformation v WHERE v.gateInDate = :gateInDate"),
    @NamedQuery(name = "VwContainerInformation.findByGateinConfirmBy", query = "SELECT v FROM VwContainerInformation v WHERE v.gateinConfirmBy = :gateinConfirmBy"),
    @NamedQuery(name = "VwContainerInformation.findByDelRecv", query = "SELECT v FROM VwContainerInformation v WHERE v.delRecv = :delRecv"),
    @NamedQuery(name = "VwContainerInformation.findByDelRecvStart", query = "SELECT v FROM VwContainerInformation v WHERE v.delRecvStart = :delRecvStart"),
    @NamedQuery(name = "VwContainerInformation.findByDelRecvEnd", query = "SELECT v FROM VwContainerInformation v WHERE v.delRecvEnd = :delRecvEnd"),
    @NamedQuery(name = "VwContainerInformation.findByDelRecvOptName", query = "SELECT v FROM VwContainerInformation v WHERE v.delRecvOptName = :delRecvOptName"),
    @NamedQuery(name = "VwContainerInformation.findByDelRecvConfirmBy", query = "SELECT v FROM VwContainerInformation v WHERE v.delRecvConfirmBy = :delRecvConfirmBy"),
    @NamedQuery(name = "VwContainerInformation.findByGateOutDate", query = "SELECT v FROM VwContainerInformation v WHERE v.gateOutDate = :gateOutDate"),
    @NamedQuery(name = "VwContainerInformation.findByGateoutConfirmBy", query = "SELECT v FROM VwContainerInformation v WHERE v.gateoutConfirmBy = :gateoutConfirmBy"),
    @NamedQuery(name = "VwContainerInformation.findByOperation", query = "SELECT v FROM VwContainerInformation v WHERE v.operation = :operation")})
@NamedNativeQueries({

/*
    @NamedNativeQuery(name = "VwContainerInformation.Native.findContList", query = "SELECT distinct vw.cont_no "
            + "FROM vw_container_information vw "
            + "WHERE upper(vw.cont_no) LIKE upper('%'|| ? ||'%')"
            + "ORDER BY vw.cont_no LIMIT 10"),
*/            
    @NamedNativeQuery(name = "VwContainerInformation.Native.findContList", query = 
"SELECT cont_no " 
+"FROM " 
+"  ( SELECT DISTINCT vw.cont_no " 
+"  FROM vw_container_information vw " 
+"  WHERE upper(vw.cont_no) LIKE upper('%' " 
+"    || ? " 
+"    ||'%') " 
+"  ORDER BY vw.cont_no " 
+"  ) " 
+"WHERE rownum <= 10"),

    @NamedNativeQuery(name = "VwContainerInformation.Native.findHistoryContList", query = "SELECT vw.no_ppkb, vw.cont_no, vw.position "
            + "FROM vw_container_information vw "
            + "WHERE vw.cont_no = ? "
            + "ORDER BY vw.no_ppkb"),
/*            
    @NamedNativeQuery(name = "VwContainerInformation.Native.findContInfo", query = "SELECT distinct vw.cont_no, vw.cont_size, vw.type, vw.new_iso_code "
            + "FROM vw_container_information vw "
            + "WHERE vw.cont_no=? limit 1"),
*/
    @NamedNativeQuery(name = "VwContainerInformation.Native.findContInfo", query = 
"SELECT DISTINCT vw.cont_no, " 
+"  vw.cont_size, " 
+"  vw.type, " 
+"  vw.new_iso_code " 
+"FROM vw_container_information vw " 
+"WHERE vw.cont_no = ? and rownum <= 2"),

/*            
    @NamedNativeQuery(name = "VwContainerInformation.Native.findVesselDetail", query = "SELECT vw.vessel_name, vw.voy_in, vw.voy_out, vw.arrival_time, vw.departure_time, "
            + "vw.last_port, vw.next_port "
            + "FROM vw_container_information vw "
            + "WHERE vw.cont_no= ? AND vw.no_ppkb = ? limit 1"),
*/
    @NamedNativeQuery(name = "VwContainerInformation.Native.findVesselDetail", query = 
"SELECT vessel_name, " 
+"  voy_in, " 
+"  voy_out, " 
+"  arrival_time, " 
+"  departure_time, " 
+"  last_port, " 
+"  next_port " 
+"FROM " 
+"  (SELECT vw.vessel_name, " 
+"    vw.voy_in, " 
+"    vw.voy_out, " 
+"    vw.arrival_time, " 
+"    vw.departure_time, " 
+"    vw.last_port, " 
+"    vw.next_port " 
+"  FROM vw_container_information vw " 
+"  WHERE vw.cont_no= ? " 
+"  AND vw.no_ppkb  = ? " 
+"  ) " 
+"WHERE rownum < 2"),

/*            
    @NamedNativeQuery(name = "VwContainerInformation.Native.findContainerDetail", query = "SELECT vw.bl_no, vw.seal_no, vw.cont_status, vw.cont_gross, vw.od, vw.dg, vw.dg_lbl, "
            + "vw.load_port, vw.discharge_port, vw.operation "
            + "FROM vw_container_information vw "
            + "WHERE vw.cont_no= ?  AND vw.no_ppkb = ? limit 1"),
*/
    @NamedNativeQuery(name = "VwContainerInformation.Native.findContainerDetail", query = 
"SELECT bl_no, " 
+"  seal_no, " 
+"  cont_status, " 
+"  cont_gross, " 
+"  od, " 
+"  dg, " 
+"  dg_lbl, " 
+"  load_port, " 
+"  discharge_port, " 
+"  operation " 
+"FROM " 
+"  (SELECT vw.bl_no, " 
+"    vw.seal_no, " 
+"    vw.cont_status, " 
+"    vw.cont_gross, " 
+"    vw.od, " 
+"    vw.dg, " 
+"    vw.dg_lbl, " 
+"    vw.load_port, " 
+"    vw.discharge_port, " 
+"    vw.operation " 
+"  FROM vw_container_information vw " 
+"  WHERE vw.cont_no= ? " 
+"  AND vw.no_ppkb  = ? " 
+"  ) " 
+"WHERE rownum < 2"),
            
    @NamedNativeQuery(name = "VwContainerInformation.Native.findHandlingDischarge", query = "SELECT vw.stevedoring, vw.stv_opt_name, vw.stv_end, vw.stv_confirm_by,vw.v_bay, vw.v_row, vw.v_tier, "
            + "vw.ht, vw.ht_opt_name, vw.ht_end, vw.ht_confirm_by,  vw.lift_off_on, vw.lift_opt_name, vw.lift_end, "
            + "vw.lift_confirm_by,vw.block, vw.y_slot, vw.y_row, vw.y_tier "
            + "FROM vw_container_information vw "
            + "WHERE vw.cont_no= ? AND vw.no_ppkb = ?  AND (operation = 'DISCHARGE' OR operation = 'TRANSDISCHARGE' "
            + "OR operation = 'SHIFT-TOCY-D')"),
    @NamedNativeQuery(name = "VwContainerInformation.Native.findHandlingLoad", query = "SELECT vw.lift_off_on, vw.lift_opt_name, vw.lift_end, vw.lift_confirm_by,vw.block, vw.y_slot, vw.y_row, vw.y_tier, "
            + " vw.ht, vw.ht_opt_name, vw.ht_end, vw.ht_confirm_by, "
            + "vw.stevedoring, vw.stv_opt_name, vw.stv_end, vw.stv_confirm_by,vw.v_bay, vw.v_row, vw.v_tier "
            + "FROM vw_container_information vw WHERE vw.cont_no=? AND vw.no_ppkb =? AND (operation = 'LOAD' OR operation = 'TRANSLOAD' OR operation = 'SHIFT-TOCY-M')"),
    @NamedNativeQuery(name = "VwContainerInformation.Native.findGateDischarge", query = "SELECT vw.gate_in_date, vw.gatein_confirm_by, vw.gate_out_date, vw.gateout_confirm_by "
            + "FROM vw_container_information vw "
            + "WHERE vw.cont_no= ? AND vw.no_ppkb = ? AND vw.operation = 'DISCHARGE'"),
    @NamedNativeQuery(name = "VwContainerInformation.Native.findGateLoad", query = "SELECT vw.gate_in_date, vw.gatein_confirm_by, vw.gate_out_date, vw.gateout_confirm_by "
            + "FROM vw_container_information vw "
            + "WHERE vw.cont_no= ? AND vw.no_ppkb = ? AND vw.operation = 'LOAD'")})
public class VwContainerInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "no_ppkb", length = 30)
    private String noPpkb;
    @Column(name = "vessel_code", length = 10)
    private String vesselCode;
    @Column(name = "vessel_name", length = 50)
    private String vesselName;
    @Column(name = "last_port", length = 50)
    private String lastPort;
    @Column(name = "next_port", length = 50)
    private String nextPort;
    @Column(name = "voy_in", length = 10)
    private String voyIn;
    @Column(name = "voy_out", length = 10)
    private String voyOut;
    @Column(name = "eta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eta;
    @Column(name = "etd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date etd;
    @Column(name = "arrival_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalTime;
    @Column(name = "departure_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;
    @Column(name = "bl_no", length = 100)
    private String blNo;
    @Column(name = "cont_no", length = 11)
    @Id
    private String contNo;
    @Column(name = "iso_code", length = 10)
    private String isoCode;
    @Column(name = "new_iso_code", length = 10)
    private String newIsoCode;
    @Column(name = "cont_size")
    private Short contSize;
    @Column(name = "commodity", length = 256)
    private String commodity;
    @Column(name = "type", length = 10)
    private String type;
    @Column(name = "cont_status", length = 5)
    private String contStatus;
    @Column(name = "seal_no", length = 10)
    private String sealNo;
    @Column(name = "cont_gross")
    private Integer contGross;
    @Column(name = "od")
    private Integer od;
    @Column(name = "dg")
    private Integer dg;
    @Column(name = "dg_lbl")
    private Integer dgLbl;
    @Column(name = "position", length = 2147483647)
    private String position;
    @Column(name = "load_port", length = 50)
    private String loadPort;
    @Column(name = "discharge_port", length = 50)
    private String dischargePort;
    @Column(name = "pod", length = 50)
    private String pod;
    @Column(name = "owner", length = 256)
    private String owner;
    @Column(name = "v_bay")
    private Short vBay;
    @Column(name = "v_row")
    private Short vRow;
    @Column(name = "v_tier")
    private Short vTier;
    @Column(name = "crane", length = 2)
    private String crane;
    @Column(name = "stevedoring", length = 10)
    private String stevedoring;
    @Column(name = "stv_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stvStart;
    @Column(name = "stv_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stvEnd;
    @Column(name = "stv_opt_name", length = 50)
    private String stvOptName;
    @Column(name = "stv_confirm_by", length = 256)
    private String stvConfirmBy;
    @Column(name = "ht", length = 10)
    private String ht;
    @Column(name = "ht_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date htStart;
    @Column(name = "ht_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date htEnd;
    @Column(name = "ht_opt_name", length = 50)
    private String htOptName;
    @Column(name = "ht_confirm_by", length = 256)
    private String htConfirmBy;
    @Column(name = "lift_off_on", length = 10)
    private String liftOffOn;
    @Column(name = "lift_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date liftStart;
    @Column(name = "lift_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date liftEnd;
    @Column(name = "lift_opt_name", length = 50)
    private String liftOptName;
    @Column(name = "lift_confirm_by", length = 256)
    private String liftConfirmBy;
    @Column(name = "block", length = 5)
    private String block;
    @Column(name = "y_slot")
    private Short ySlot;
    @Column(name = "y_row")
    private Short yRow;
    @Column(name = "y_tier")
    private Short yTier;
    @Column(name = "del_recv_loket_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date delRecvLoketDate;
    @Column(name = "del_loket_by", length = 256)
    private String delLoketBy;
    @Column(name = "gate_in_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gateInDate;
    @Column(name = "gatein_confirm_by", length = 256)
    private String gateinConfirmBy;
    @Column(name = "del_recv", length = 10)
    private String delRecv;
    @Column(name = "del_recv_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date delRecvStart;
    @Column(name = "del_recv_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date delRecvEnd;
    @Column(name = "del_recv_opt_name", length = 50)
    private String delRecvOptName;
    @Column(name = "del_recv_confirm_by", length = 256)
    private String delRecvConfirmBy;
    @Column(name = "gate_out_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gateOutDate;
    @Column(name = "gateout_confirm_by", length = 256)
    private String gateoutConfirmBy;
    @Column(name = "operation", length = 2147483647)
    private String operation;

    public VwContainerInformation() {
    }

    public String getNoPpkb() {
        return noPpkb;
    }

    public void setNoPpkb(String noPpkb) {
        this.noPpkb = noPpkb;
    }

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getLastPort() {
        return lastPort;
    }

    public void setLastPort(String lastPort) {
        this.lastPort = lastPort;
    }

    public String getNextPort() {
        return nextPort;
    }

    public void setNextPort(String nextPort) {
        this.nextPort = nextPort;
    }

    public String getVoyIn() {
        return voyIn;
    }

    public void setVoyIn(String voyIn) {
        this.voyIn = voyIn;
    }

    public String getVoyOut() {
        return voyOut;
    }

    public void setVoyOut(String voyOut) {
        this.voyOut = voyOut;
    }

    public Date getEta() {
        return eta;
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    public Date getEtd() {
        return etd;
    }

    public void setEtd(Date etd) {
        this.etd = etd;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getBlNo() {
        return blNo;
    }

    public void setBlNo(String blNo) {
        this.blNo = blNo;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getNewIsoCode() {
        return newIsoCode;
    }

    public void setNewIsoCode(String newIsoCode) {
        this.newIsoCode = newIsoCode;
    }

    public Short getContSize() {
        return contSize;
    }

    public void setContSize(Short contSize) {
        this.contSize = contSize;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public Integer getContGross() {
        return contGross;
    }

    public void setContGross(Integer contGross) {
        this.contGross = contGross;
    }

    public Integer getOd() {
        return od;
    }

    public void setOd(Integer od) {
        this.od = od;
    }

    public Integer getDg() {
        return dg;
    }

    public void setDg(Integer dg) {
        this.dg = dg;
    }

    public Integer getDgLbl() {
        return dgLbl;
    }

    public void setDgLbl(Integer dgLbl) {
        this.dgLbl = dgLbl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLoadPort() {
        return loadPort;
    }

    public void setLoadPort(String loadPort) {
        this.loadPort = loadPort;
    }

    public String getDischargePort() {
        return dischargePort;
    }

    public void setDischargePort(String dischargePort) {
        this.dischargePort = dischargePort;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public String getStevedoring() {
        return stevedoring;
    }

    public void setStevedoring(String stevedoring) {
        this.stevedoring = stevedoring;
    }

    public Date getStvStart() {
        return stvStart;
    }

    public void setStvStart(Date stvStart) {
        this.stvStart = stvStart;
    }

    public Date getStvEnd() {
        return stvEnd;
    }

    public void setStvEnd(Date stvEnd) {
        this.stvEnd = stvEnd;
    }

    public String getStvOptName() {
        return stvOptName;
    }

    public void setStvOptName(String stvOptName) {
        this.stvOptName = stvOptName;
    }

    public String getStvConfirmBy() {
        return stvConfirmBy;
    }

    public void setStvConfirmBy(String stvConfirmBy) {
        this.stvConfirmBy = stvConfirmBy;
    }

    public String getHt() {
        return ht;
    }

    public void setHt(String ht) {
        this.ht = ht;
    }

    public Date getHtStart() {
        return htStart;
    }

    public void setHtStart(Date htStart) {
        this.htStart = htStart;
    }

    public Date getHtEnd() {
        return htEnd;
    }

    public void setHtEnd(Date htEnd) {
        this.htEnd = htEnd;
    }

    public String getHtOptName() {
        return htOptName;
    }

    public void setHtOptName(String htOptName) {
        this.htOptName = htOptName;
    }

    public String getHtConfirmBy() {
        return htConfirmBy;
    }

    public void setHtConfirmBy(String htConfirmBy) {
        this.htConfirmBy = htConfirmBy;
    }

    public String getLiftOffOn() {
        return liftOffOn;
    }

    public void setLiftOffOn(String liftOffOn) {
        this.liftOffOn = liftOffOn;
    }

    public Date getLiftStart() {
        return liftStart;
    }

    public void setLiftStart(Date liftStart) {
        this.liftStart = liftStart;
    }

    public Date getLiftEnd() {
        return liftEnd;
    }

    public void setLiftEnd(Date liftEnd) {
        this.liftEnd = liftEnd;
    }

    public String getLiftOptName() {
        return liftOptName;
    }

    public void setLiftOptName(String liftOptName) {
        this.liftOptName = liftOptName;
    }

    public String getLiftConfirmBy() {
        return liftConfirmBy;
    }

    public void setLiftConfirmBy(String liftConfirmBy) {
        this.liftConfirmBy = liftConfirmBy;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
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

    public Date getDelRecvLoketDate() {
        return delRecvLoketDate;
    }

    public void setDelRecvLoketDate(Date delRecvLoketDate) {
        this.delRecvLoketDate = delRecvLoketDate;
    }

    public String getDelLoketBy() {
        return delLoketBy;
    }

    public void setDelLoketBy(String delLoketBy) {
        this.delLoketBy = delLoketBy;
    }

    public Date getGateInDate() {
        return gateInDate;
    }

    public void setGateInDate(Date gateInDate) {
        this.gateInDate = gateInDate;
    }

    public String getGateinConfirmBy() {
        return gateinConfirmBy;
    }

    public void setGateinConfirmBy(String gateinConfirmBy) {
        this.gateinConfirmBy = gateinConfirmBy;
    }

    public String getDelRecv() {
        return delRecv;
    }

    public void setDelRecv(String delRecv) {
        this.delRecv = delRecv;
    }

    public Date getDelRecvStart() {
        return delRecvStart;
    }

    public void setDelRecvStart(Date delRecvStart) {
        this.delRecvStart = delRecvStart;
    }

    public Date getDelRecvEnd() {
        return delRecvEnd;
    }

    public void setDelRecvEnd(Date delRecvEnd) {
        this.delRecvEnd = delRecvEnd;
    }

    public String getDelRecvOptName() {
        return delRecvOptName;
    }

    public void setDelRecvOptName(String delRecvOptName) {
        this.delRecvOptName = delRecvOptName;
    }

    public String getDelRecvConfirmBy() {
        return delRecvConfirmBy;
    }

    public void setDelRecvConfirmBy(String delRecvConfirmBy) {
        this.delRecvConfirmBy = delRecvConfirmBy;
    }

    public Date getGateOutDate() {
        return gateOutDate;
    }

    public void setGateOutDate(Date gateOutDate) {
        this.gateOutDate = gateOutDate;
    }

    public String getGateoutConfirmBy() {
        return gateoutConfirmBy;
    }

    public void setGateoutConfirmBy(String gateoutConfirmBy) {
        this.gateoutConfirmBy = gateoutConfirmBy;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}
