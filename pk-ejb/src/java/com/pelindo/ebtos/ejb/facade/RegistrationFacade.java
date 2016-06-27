/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.RegistrationFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.RegistrationFacadeRemote;
import com.pelindo.ebtos.model.db.PlanningVessel;
import com.pelindo.ebtos.model.db.Registration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author dycoder
 */
@Stateless
public class RegistrationFacade extends AbstractFacade<Registration> implements RegistrationFacadeRemote, RegistrationFacadeLocal {

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @PersistenceUnit(unitName="pk-ejbPU")
    private EntityManagerFactory emf;

    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistrationFacade() {
        super(Registration.class);
    }

    public Registration editAndFetch(Registration entity) {
        entity = getEntityManager().merge(entity);
        return find(entity.getNoReg());
    }

    public List<Registration> findByStatusses(String... statusses) {
        return getEntityManager().createNamedQuery("Registration.findByStatusses")
                .setParameter("statusses", Arrays.asList(statusses))
                .getResultList();
    }

    public List<Registration> findByStatussesAndCreatedDate(Date date, String... statusses) {
        Calendar now = Calendar.getInstance();

        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        Date theDayAtZeroOClock = now.getTime();

        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 59);

        Date theDayAfterAtZeroOClock = now.getTime();
        
        return getEntityManager().createNamedQuery("Registration.findByStatussesAndCreatedDate")
                .setParameter("theDayAtZeroOClock", theDayAtZeroOClock)
                .setParameter("theDayAfterAtZeroOClock", theDayAfterAtZeroOClock)
                .setParameter("statusses", Arrays.asList(statusses))
                .getResultList();
    }

    public List<Object[]> findRegistrations() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrations").getResultList();
    }

    public String findRegistrationByGenerate() {
        return (String) getEntityManager().createNamedQuery("Registration.Native.findRegistrationByGenerate").getSingleResult();
    }

    public List<Object[]> findRegistrationList() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationList").getResultList();
    }

    public Object[] findRegistrationByNoReg(String no_reg) {
        return (Object[]) getEntityManager().createNamedQuery("Registration.Native.findRegistrationByNoReg").setParameter(1, no_reg).getSingleResult();
    }

    public List<Object[]> findRegistrationPluggingOnly() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationPluggingOnly").getResultList();
    }

    public List<Object[]> findRegistrationRecUC() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationRecUC").getResultList();
    }

    public List<Object[]> findRegistrationDeliveryUC() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationDeliveryUC").getResultList();
    }

    public List<Object[]> findRegistrationDelivery() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationDelivery").getResultList();
    }

    public List<Object[]> findRegistrationReceiving() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationReceiving").getResultList();
    }

    public List<Object[]> findRegistrationReeferDischarge() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationReeferDischarge").getResultList();
    }

    public List<Object[]> findRegistrationReeferLoad() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationReeferLoad").getResultList();
    }

    public List<Object[]> findRegistrationReeferLoadServiceOnly() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationReeferLoadServiceOnly").getResultList();
    }

    public List<Object[]> findRegistrationPenumpukanSusulan() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationPenumpukanSusulan").getResultList();
    }

    public List<Object[]> findRegistrationPenumpukanSusulanUC() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationPenumpukanSusulanUC").getResultList();
    }

    public List<Object[]> findRegistrationAngsur() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationAngsur").getResultList();
    }

    public List<Object[]> findRegistrationBatalMuat() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationBatalMuat").getResultList();
    }

    public List<Object[]> findRegistrationBatalKapal() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationBatalKapal").getResultList();
    }

    public List<Object[]> findRegistrationCancelDocument() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationCancelDocument").getResultList();
    }

    public List<Object[]> findRegistrationBehandle() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationBehandle").getResultList();
    }

    public List<Object[]> findRegistrationStriping() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationStriping").getResultList();
    }

    public List<Object[]> findRegistrationStuffing() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationStuffing").getResultList();
    }

    public List<Object[]> findRegistrationDeliveryReceiving() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationDeliveryReceiving").getResultList();
    }

    public List<Object[]> findRegistrationPluggingReefer() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationPluggingReefer").getResultList();
    }

    public List<Object[]> findRegistrationStriffingStuffing() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationStriffingStuffing").getResultList();
    }

    public List<Object[]> findRegistrationInvoice() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationInvoice").getResultList();
    }

    public List<Object[]> findRegistrationInvoiceCash() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationInvoiceCash").getResultList();
    }

    public List<Object[]> findRegistrationInvoiceCashAndCredit() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationInvoiceCashAndCredit").getResultList();
    }

    public List<Object[]> findRegistrationReceivingBarang() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationReceivingBarang").getResultList();
    }

    public List<Object[]> findRegistrationChangeStatus() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationChangeStatus").getResultList();
    }

    public List<Object[]> findRegistrationDeliveryBarang() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationDeliveryBarang").getResultList();
    }

    public List<Object[]> findRegistrationDeliveryReceivingUC() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationDeliveryReceivingUC").getResultList();
    }

    public List<Object[]> findRegistrationDeliveryReceivingGoods() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationDeliveryReceivingGoods").getResultList();
    }

    public List<Object[]> findRegistrationBehandleJobslip() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationBehandleJobslip").getResultList();
    }

    public List<Object[]> findRegistrationBatalMuatJobslip() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationBatalMuatJobslip").getResultList();
    }

    public String findRegistrationGenerateId(String bulan) {
        return (String) getEntityManager().createNamedQuery("Registration.Native.findRegistrationGenerateId").setParameter(1, bulan).getSingleResult();
    }

    public Registration findByPpkbAndServiceCode(String noPpkb, String serviceCode) {
        try {
            return (Registration) getEntityManager().createNamedQuery("Registration.findByPpkbAndServiceCode")
                    .setParameter("noPpkb", noPpkb)
                    .setParameter("serviceCode", serviceCode).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Object[]> findRegistrationInvoiceCashAndCreditApprove() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationInvoiceCashAndCreditApprove").getResultList();
    }

    public List<Object[]> findRegistrationInvoiceCashAndCreditConfirm() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationInvoiceCashAndCreditConfirm").getResultList();
    }

    public int updatePlanningVesselExceptCancelVessel(PlanningVessel newValue, PlanningVessel oldValue) {
        EntityManager entityManager = emf.createEntityManager();
        return entityManager.createNamedQuery("Registration.updatePlanningVesselExceptCancelVessel")
                .setParameter("newValue", newValue)
                .setParameter("oldValue", oldValue)
                .executeUpdate();
    }

    @Override
    public List<Object[]> findRegistrationDischargeToLoad() {
        return getEntityManager().createNamedQuery("Registration.Native.findRegistrationDischargeToLoad").getResultList();
    }
}
