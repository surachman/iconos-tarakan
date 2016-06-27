/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.Registration;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface RegistrationFacadeLocal {

    void create(Registration registration);

    void edit(Registration registration);

    void remove(Registration registration);

    Registration find(Object id);

    List<Registration> findAll();

    List<Registration> findRange(int[] range);

    int count();

    List<Object[]> findRegistrations();

    String findRegistrationByGenerate();

    List<Object[]> findRegistrationList();

    Object[] findRegistrationByNoReg(String no_reg);

    List<Object[]> findRegistrationPluggingOnly();

    List<Object[]> findRegistrationRecUC();

    List<Object[]> findRegistrationDeliveryUC();

    List<Object[]> findRegistrationDelivery();

    List<Object[]> findRegistrationReceiving();

    List<Object[]> findRegistrationReeferDischarge();

    List<Object[]> findRegistrationReeferLoad();

    List<Object[]> findRegistrationPenumpukanSusulan();

    List<Object[]> findRegistrationPenumpukanSusulanUC();

    List<Object[]> findRegistrationAngsur();

    List<Object[]> findRegistrationBatalMuat();

    List<Object[]> findRegistrationBehandle();

    List<Object[]> findRegistrationStriping();

    List<Object[]> findRegistrationStuffing();

    List<Object[]> findRegistrationDeliveryReceiving();

    List<Object[]> findRegistrationPluggingReefer();

    List<Object[]> findRegistrationStriffingStuffing();

    List<Object[]> findRegistrationInvoice();

    List<Object[]> findRegistrationInvoiceCash();

    List<Object[]> findRegistrationReceivingBarang();

    List<Object[]> findRegistrationDeliveryBarang();

    public List<Object[]> findRegistrationDeliveryReceivingUC();

    List<Object[]> findRegistrationDeliveryReceivingGoods();

    List<Object[]> findRegistrationBehandleJobslip();

    List<Object[]> findRegistrationBatalMuatJobslip();

    String findRegistrationGenerateId(String bulan);

    public java.util.List<java.lang.Object[]> findRegistrationInvoiceCashAndCredit();

    List<Object[]> findRegistrationInvoiceCashAndCreditApprove();

    List<Object[]> findRegistrationInvoiceCashAndCreditConfirm();

    public com.pelindo.ebtos.model.db.Registration findByPpkbAndServiceCode(java.lang.String noPpkb, java.lang.String serviceCode);

    public int updatePlanningVesselExceptCancelVessel(com.pelindo.ebtos.model.db.PlanningVessel newValue, com.pelindo.ebtos.model.db.PlanningVessel oldValue);
}
