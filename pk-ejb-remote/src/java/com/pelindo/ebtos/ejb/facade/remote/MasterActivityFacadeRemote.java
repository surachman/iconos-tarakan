/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.master.MasterActivity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface MasterActivityFacadeRemote {

    void create(MasterActivity masterActivity);

    void edit(MasterActivity masterActivity);

    void remove(MasterActivity masterActivity);

    MasterActivity find(Object id);

    List<MasterActivity> findAll();

    List<MasterActivity> findRange(int[] range);

    int count();

    List<Object[]> findAllActivity();

    List<Object[]> findActivityBySimulasi();

    List<Object[]> findAllActivityExc02();

    public java.lang.String translatePenumpukanActivityCode(java.lang.String contStatus, java.lang.String isoCode, java.lang.Boolean overSize, java.lang.Short contSize, java.lang.Boolean isPlugging);

    public java.lang.String translateLoLoActivityCode(java.lang.String contStatus, java.lang.Short contSize, java.lang.Boolean overSize);

    public java.lang.String translateHandlingActivityCode(java.lang.String contStatus, java.lang.Short contSize, java.lang.String crane, java.lang.Boolean overSize);

    public java.lang.String translateSlingActivityCode(java.lang.Short contSize);

    public java.lang.String translatePassGateActivityCode(String contStatus, Short contSize);

    public java.lang.String translateHandlingUcActivityCode(java.lang.Integer weight, java.lang.String crane);

    public java.lang.String translateLoLoUcActivityCode(java.lang.Integer weight);

    public java.lang.String translatePenumpukanUcActivityCode();

    public java.lang.String translatePassGateUcActivityCode();

    public java.lang.String translateTranshipmentActivityCode(java.lang.Boolean overSize, String contStatus, java.lang.String crane, java.lang.Short size);

    public java.lang.String translateTranshipmentUcActivityCode(java.lang.Integer weight, java.lang.String crane);

    public java.lang.String translateShiftingActivityCode(java.lang.Boolean oversize, java.lang.String status, java.lang.String shiftTo, java.lang.String crane, java.lang.Short size);

    public java.lang.String translateShiftingActivityCodeEquipment(java.lang.Boolean oversize, java.lang.String status, java.lang.String shiftTo, java.lang.String crane, java.lang.Short size, java.lang.String equipmentType);

    public java.lang.String translateShiftingUcActivityCode(java.lang.Integer weight, java.lang.String crane, java.lang.Boolean isLanded);

    public java.lang.String translateUbahBuruhHandlingActivityCode(java.lang.String type, java.lang.Short size, java.lang.Boolean overSize);

    public java.lang.String translateUbahBuruhShiftingActivityCode(java.lang.String shiftingType, java.lang.Short size, java.lang.Boolean overSize);

    public java.lang.String translateUbahBuruhUcActivityCode();

    public java.lang.String translateCancelLoadingActivityCode(java.lang.String status, java.lang.Short size);

    public java.lang.String translateExtraMovementActivityCode(java.lang.Short size, java.lang.Boolean isUseSpecialEquipment);

    public java.lang.String translateBehandleActivityCode(java.lang.Short contSize, java.lang.Boolean isUseSpecialEquipment);

    public java.lang.String translateAirKapalActivityCode();

    public String translateCancelDocumentActivityCode();

    public java.lang.String translateHatchMoveActivityCode(java.lang.String crane);

    public java.lang.String translateStrippingStuffingActivityCode(java.lang.Short contSize, boolean isOpenDoor);

    public java.lang.String translateStrippingStuffingActivityCode2(java.lang.Short contSize,java.lang.String status, boolean isOpenDoor);
  
    public java.lang.String translateUpahBuruhStrippingStuffingActivityCode();

    String translateSewaForkLiftActivityCode(String forklift, int contSize);

    String translateStrippingStuffingFullHandlingActivityCode(String fullHandlingName);

    String translateStrippingStuffingMekanikActivityCode(String mekanikName);

    public java.lang.String translateBehandleActivityCode(java.lang.Short contSize);

    public java.lang.String translateSupervisiActivityCode(java.lang.Short contSize);

    public java.lang.String translateDischargeToLoadActivityCode();

    public java.lang.String translatePluggingActivityCode(short contSize);

    public java.lang.String translateMonitoringActivityCode(short contSize);

    public java.lang.String translateJasaDermagaUcActivityCode();

    String translateUpahBuruhHatchMoveActivityCode();
    
    public String translateHandlingActivityCode(final String contType, final Short contSize, final String crane);
    
    public String translateOtherPackageActivityCode(final String mainActivity, final String contType, final Short contSize);
}
