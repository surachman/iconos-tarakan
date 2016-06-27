/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.ActiveDirectoryFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterCustTypeFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.MasterCustomerFacadeRemote;
import com.pelindo.ebtos.ejb.facade.local.MasterCustomerFacadeLocal;
import com.pelindo.ebtos.ejb.facade.local.MasterSettingAppFacadeLocal;
import com.pelindo.ebtos.model.NamedObject;
import com.pelindo.ebtos.model.db.master.MasterCustomer;
import com.pelindo.ebtos.model.db.master.MasterSettingApp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class MasterCustomerFacade extends AbstractFacade<MasterCustomer> implements MasterCustomerFacadeRemote, MasterCustomerFacadeLocal {
    private static final String AGEN_PELAYARAN_LAST_UPDATE_PARAM = "integration.simpat.agen_pelayaran_last_update";
    
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;
    @EJB
    private MasterSettingAppFacadeLocal masterSettingAppFacadeLocal;
    @EJB
    private MasterCustTypeFacadeLocal masterCustTypeFacadeLocal;
    @EJB
    private ActiveDirectoryFacadeLocal activeDirectoryFacadeLocal;

    protected EntityManager getEntityManager() {
        return em;
    }

    public MasterCustomerFacade() {
        super(MasterCustomer.class);
    }
    public List<Object[]> findMasterCustomers(){
        return getEntityManager().createNamedQuery("MasterCustomer.Native.findMasterCustomers").getResultList();
    }
    public Object[] findMasterCustomerByCustCode(String cust_code){
        return (Object[])getEntityManager().createNamedQuery("MasterCustomer.Native.findMasterCustomerByCustCode").setParameter(1, cust_code).getSingleResult();
    }
    /**
     *
     * @param from = Last Update
     * @return
     *      [0] kode_agen,
     *      [1] nama_agen,
     *      [2] alamat,
     *      [3] no_tlp,
     *      [4] no_npwp,
     *      [5] jenis_agen,
     *      [6] kena_ppn
     */
    public List<Object[]> fetchNewestAgenPelayaran(Date from){
        return getEntityManager().createNativeQuery("SELECT kode_agen, nama_agen, alamat, no_tlp, no_npwp, jenis_agen, kena_ppn FROM m_agen_pelayaran m WHERE m.last_update >= ? AND m.flag = 'Y'")
                .setParameter(1, from)
                .getResultList();
    }

    public List<Object[]> findAllmasterCustomerByDelete (int id_type){
        return getEntityManager().createNamedQuery("MasterCustomer.Native.findAllMasterCustomerByDelete").setParameter(1, id_type).getResultList();
   }

    public List<Object[]> findLikeName (String customerName){
        return getEntityManager().createNamedQuery("MasterCustomer.Native.findByName")
            .setParameter(1, customerName)
            .getResultList();
    }

   public Object[] findDetailAgen(String cust_code){
        return (Object[]) getEntityManager().createNamedQuery("MasterCustomer.Native.findDetailAgen").setParameter(1, cust_code).getSingleResult();
    }

    public Integer syncMasterCustomer(){
        MasterSettingApp lastUpdate = masterSettingAppFacadeLocal.find(AGEN_PELAYARAN_LAST_UPDATE_PARAM);

        if (lastUpdate != null) {
            List<Object[]> agenPelayarans = fetchNewestAgenPelayaran(lastUpdate.getValueDate());
            Date now = new Date();

            for (Object[] agen: agenPelayarans){
                MasterCustomer customer = find(agen[0].toString());
                boolean isNew = false;

                if (customer == null){
                    customer = new MasterCustomer();
                    isNew = true;
                }

                customer.setCustCode((String) agen[0]);
                customer.setName((String) agen[1]);
                customer.setAddress((String) agen[2]);
                customer.setPhone1((String) agen[3]);
                customer.setNpwp((String) agen[4]);
                customer.setKodeStatusPajak((String) agen[6]);
                customer.setMasterCustType(masterCustTypeFacadeLocal.find(Integer.parseInt((String) agen[5])));

                if (isNew){
                    customer.setCreatedBy("System");
                    create(customer);
                } else {
                    customer.setModifiedBy("System");
                    edit(customer);
                }
            }

            lastUpdate.setValueDate(now);
            lastUpdate.setModifiedBy("System");
            masterSettingAppFacadeLocal.edit(lastUpdate);
            return agenPelayarans.size();
        } else {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, AGEN_PELAYARAN_LAST_UPDATE_PARAM + " not found!");
        }

        return 0;
    }
}
