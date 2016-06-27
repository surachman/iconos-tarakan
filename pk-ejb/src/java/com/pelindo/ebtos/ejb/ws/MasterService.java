/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.ws;

import com.pelindo.ebtos.model.OperatorModel;
import com.pelindo.ebtos.ejb.facade.remote.ActiveDirectoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterDockFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterEquipmentFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterOperatorFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterPluggingReeferFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterYardFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.PlanningVesselFacadeRemote;
import com.pelindo.ebtos.model.NamedObject;
import com.pelindo.ebtos.model.TallyModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author Dyware-Dev01
 */
@WebService()
@Stateless()
public class MasterService {

    private static final String TALLY_GROUP_NAME_PARAM = "ebtos.group.tally";
    private static final String TALLY_GROUP_NAME_PARAM_SVP = "ebtos.group.spv_tally";
    private static final String TALLY_GROUP_NAME_PARAM_ADMIN = "ebtos.group.supervisor_group_name";
    @EJB
    private PlanningVesselFacadeRemote planningVesselFacade;
    @EJB
    private MasterEquipmentFacadeRemote masterEquipmentFacadeRemote;
    @EJB
    private MasterPluggingReeferFacadeRemote masterPluggingReeferFacadeRemote;
    @EJB
    private MasterOperatorFacadeRemote masterOperatorFacadeRemote;
    @EJB
    private ActiveDirectoryFacadeRemote ldapLogin;
    @EJB
    private MasterYardFacadeRemote masterYardFacadeRemote;
    @EJB
    private MasterDockFacadeRemote masterDockFacadeRemote;
    @EJB
    private MasterSettingAppFacadeRemote masterSettingAppFacadeRemote;
    private List<String> noPpkb;
    private List<String> craneCode;
    private List<String> headTruckCode;
    private List<String> tangoCode;
    private List<String> pluggingCode;
    private List<String> masterBlock;
    private List<OperatorModel> opCode;
    private List<String> masterDock;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getNoPpkb")
    public List<Object[]>  getNoPpkb() {
        List<Object[]> results = planningVesselFacade.findPlanningVesselByStatus();

        if (results.size() < 1) {
            results.add(0, new String[] {
                "No Data PPKB", "No Data Vessel"
            });
        }
        
        return results;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getMasterCrane")
    public List<String> getMasterCrane() {
        //TODO write your implementation code here:
        craneCode = new ArrayList<String>();
        craneCode = masterEquipmentFacadeRemote.findCrane();
        return craneCode;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getHeadTruck")
    public List<String> getHeadTruck() {
        //TODO write your implementation code here:
        headTruckCode = new ArrayList<String>();
        headTruckCode = masterEquipmentFacadeRemote.findHt();
        return headTruckCode;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTangoCode")
    public List<String> getTangoCode() {
        //TODO write your implementation code here:
        tangoCode = new ArrayList<String>();
        tangoCode = masterEquipmentFacadeRemote.findTt();
        return tangoCode;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getServerTime")
    public String getServerTime() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getPluggingCode")
    public List<String> getPluggingCode() {
        //TODO write your implementation code here:
        pluggingCode = new ArrayList<String>();
        pluggingCode = masterPluggingReeferFacadeRemote.findPluggingCode();
        return pluggingCode;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "authenticateUser")
    public Boolean authenticateUser(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        NamedObject user = ldapLogin.findUserByUid(username);
        if (ldapLogin.authenticate(user, password)) //check credential
        {
            return user.getGroups().contains(masterSettingAppFacadeRemote.find(TALLY_GROUP_NAME_PARAM).getValueString());
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getOperatorEquipment")
    public List<OperatorModel> getOperatorEquipment() {
        //TODO write your implementation code here:

        opCode = new ArrayList<OperatorModel>();
        opCode = masterOperatorFacadeRemote.findOperators();

        return opCode;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "masterBlock")
    public List<String> getMasterBlock() {
        //TODO write your implementation code here:
        masterBlock = new ArrayList<String>();
        masterBlock = masterYardFacadeRemote.findAllBlocks();

        return masterBlock;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getMasterDock")
    public List<String> getMasterDock() {
        //TODO write your implementation code here:
        masterDock = new ArrayList<String>();
        masterDock = masterDockFacadeRemote.findDocks();

        return masterDock;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "authenticateTally")
    public TallyModel authenticateTally(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        //TODO write your implementation code here: 
        TallyModel tallyModel = new TallyModel(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
        NamedObject user = ldapLogin.findUserByUid(username);
        tallyModel.setUsername(username);

        if (ldapLogin.authenticate(user, password)) //check credential
        {
            tallyModel.setIsAuthenticated(Boolean.TRUE);
            tallyModel.setIsAdmin(user.getGroups().contains(masterSettingAppFacadeRemote.find(TALLY_GROUP_NAME_PARAM_ADMIN).getValueString()));
            tallyModel.setIsSupervisor(user.getGroups().contains(masterSettingAppFacadeRemote.find(TALLY_GROUP_NAME_PARAM_SVP).getValueString()));
        }
        return tallyModel;
    }
}
