/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterViewFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.model.db.master.MasterRole;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import com.pelindo.ebtos.model.db.master.MasterView;
import com.qtasnim.validation.ValidationLevel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterSettingAppFacade extends AbstractFacade<MasterSettingApp> implements MasterSettingAppFacadeLocal, MasterSettingAppFacadeRemote {
    private static final String PAYMENT_BANKING_PARAM = "ebtos.feature.payment_banking";
    private static final String MASA1_CONTAINER_PARAM = "ebtos.masa1_range.container";
    private static final String MASA2_CONTAINER_PARAM = "ebtos.masa2_range.container";
    private static final String MASA1_UC_PARAM = "ebtos.masa1_range.uc";
    private static final String MASA1_GOODS_PARAM = "ebtos.masa1_range.goods";
    private static final String MASA1_FREE_PARAM = "masa";
    private static final String VALIDATION_LEVEL_PARAM = "ebtos.validation.level";
    private static final String JKM_GROUPING_BY_CUSTOMER_PARAM = "ebtos.finance.is_jkm_grouping_by_customer";
    private static final String HANDLING_INVOICE_SETTING_PARAM = "ebtos.finance.is_create_two_handling_invoice";

    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    @EJB
    private MasterViewFacadeLocal masterViewFacadeLocal;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterSettingAppFacade() {
        super(MasterSettingApp.class);
    }

    public List<MasterSettingApp> findAllSorted() {
        return getEntityManager().createNamedQuery("MasterSettingApp.findAllSorted").getResultList();
    }

    public String findImplementedPortCode(){
        try {
            return (String) getEntityManager().createNamedQuery("MasterSettingApp.Native.findImplementedPortCode").getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Object[] findCustomerSyncParameter(){
        return (Object[]) getEntityManager().createNamedQuery("MasterSettingApp.Native.findCustomerSyncParameter").getSingleResult();
    }

    public Boolean isPaymentBankingEnabled() {
        MasterSettingApp paymentBankingSetting = find(PAYMENT_BANKING_PARAM);
        if (paymentBankingSetting != null && paymentBankingSetting.getValueBoolean() != null) {
            return paymentBankingSetting.getValueBoolean();
        }
        return false;
    }

    public Boolean isJKMGroupingByCustomerEnabled() {
        MasterSettingApp setting = find(JKM_GROUPING_BY_CUSTOMER_PARAM);
        if (setting != null && setting.getValueBoolean() != null) {
            return setting.getValueBoolean();
        }
        return false;
    }

    public Boolean changeHandlingInvoiceSetting(Boolean newValue) {
        MasterSettingApp setting = find(HANDLING_INVOICE_SETTING_PARAM);

        if (!setting.getValueBoolean().equals(newValue)) {
            edit(setting);
            setting.setValueBoolean(newValue);

            if (setting.getValueBoolean()) {
                MasterView _117 = masterViewFacadeLocal.find(117);
                _117.setView("/modules/Billing/Invoice/HandlingInvoice");

                MasterView _118 = new MasterView(118);
                _118.setView("/modules/Billing/Invoice/HandlingInvoice/HandlingLoadInvoice.xhtml");
                _118.setMessage("menu.billing.invoice.handling_invoice.handling_invoice_load");
                _118.setMasterRoleList(new ArrayList<MasterRole>());
                _118.setParent(117);

                for (MasterRole row: _117.getMasterRoleList()) {
                    MasterRole role = new MasterRole();
                    role.setMasterUserGroup(row.getMasterUserGroup());
                    role.setMasterView(_118);
                    _118.getMasterRoleList().add(role);
                }

                MasterView _119 = new MasterView(119);
                _119.setView("/modules/Billing/Invoice/HandlingInvoice/HandlingDischargeInvoice.xhtml");
                _119.setMessage("menu.billing.invoice.handling_invoice.handling_invoice_discharge");
                _119.setMasterRoleList(new ArrayList<MasterRole>());
                _119.setParent(117);

                for (MasterRole row: _117.getMasterRoleList()) {
                    MasterRole role = new MasterRole();
                    role.setMasterUserGroup(row.getMasterUserGroup());
                    role.setMasterView(_119);
                    _119.getMasterRoleList().add(role);
                }

                masterViewFacadeLocal.create(_118);
                masterViewFacadeLocal.create(_119);
                masterViewFacadeLocal.edit(_117);
            } else {
                MasterView _118 = masterViewFacadeLocal.find(118);
                MasterView _119 = masterViewFacadeLocal.find(119);
                MasterView _117 = masterViewFacadeLocal.find(117);
                _117.setView("/modules/Billing/Invoice/HandlingInvoice.xhtml");

                masterViewFacadeLocal.edit(_117);
                masterViewFacadeLocal.remove(_118);
                masterViewFacadeLocal.remove(_119);
            }

            return true;
        }

        return false;
    }

    public boolean isCYValidationEnabledWhenReceiving() {
        MasterSettingApp setting = find(VALIDATION_LEVEL_PARAM);
        
        if (setting != null) {
            return ValidationLevel.valueOf(setting.getValueString()).equals(ValidationLevel.HIGH);
        }

        return false;
    }

    public int getMasa1ContainerRange(){
        MasterSettingApp setting = find(MASA1_CONTAINER_PARAM);

        if (setting != null) {
            return setting.getValueInteger();
        }

        return 0;
    }
    
    public int getMasa2ContainerRange(){
        MasterSettingApp setting = find(MASA2_CONTAINER_PARAM);

        if (setting != null) {
            return setting.getValueInteger();
        }

        return 0;
    }

    public int getMasa1UcRange(){
        MasterSettingApp setting = find(MASA1_UC_PARAM);

        if (setting != null) {
            return setting.getValueInteger();
        }

        return 0;
    }

    public int getMasa1GoodsRange(){
        MasterSettingApp setting = find(MASA1_GOODS_PARAM);

        if (setting != null) {
            return setting.getValueInteger();
        }

        return 0;
    }

    public int getMasa1FreeRange(){
        MasterSettingApp setting = find(MASA1_FREE_PARAM);

        if (setting != null) {
            return setting.getValueInteger();
        }

        return 0;
    }

    @Override
    public int findTotalCYCapacity(){
        String sql = "SELECT value_integer FROM m_setting_app WHERE id='ebtos.kapasitas.total_cy'";
        Integer ival = ((BigDecimal)em.createNativeQuery(sql).getSingleResult()).intValue();
        return ival.intValue();
    }
}
