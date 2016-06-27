/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.Registration;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dycoder
 */
@Remote
public interface RegistrationFacadeRemote {

    void create(Registration registration);

    void edit(Registration registration);

    Registration editAndFetch(Registration registration);

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

    List<Object[]> findRegistrationBatalKapal();

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

    public java.util.List<java.lang.Object[]> findRegistrationCancelDocument();

    public java.util.List<java.lang.Object[]> findRegistrationReeferLoadServiceOnly();

    public java.util.List<java.lang.Object[]> findRegistrationChangeStatus();

    List<Registration> findByStatusses(String... statusses);

    List<Registration> findByStatussesAndCreatedDate(Date date, String... statusses);

    public List<Object[]> findRegistrationDischargeToLoad();
}
 