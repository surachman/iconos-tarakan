/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.RecapJurnal;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author R Seno Anggoro
 */
@Remote
public interface RecapJurnalFacadeRemote {

    void create(RecapJurnal recapJurnal);

    void edit(RecapJurnal recapJurnal);

    void remove(RecapJurnal recapJurnal);

    RecapJurnal find(Object id);

    List<RecapJurnal> findAll();

    List<RecapJurnal> findRange(int[] range);

    int count();

    public List<RecapJurnal> createJKMRecap(java.lang.Object[] array, java.lang.String username) throws javax.ejb.EJBException;

    public java.lang.Boolean postingJurnalToSimpat(Object[] journals, java.lang.String username);

    public BigDecimal findTotalTagihanBySumber(String sumber);

    public java.util.List<com.pelindo.ebtos.model.db.Invoice> findInvoicesBySumber(java.lang.String sumber);

}
