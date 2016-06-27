/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.remote;

import com.pelindo.ebtos.model.db.CancelDocumentService;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author x42jr
 */
@Remote
public interface CancelDocumentServiceFacadeRemote {

    void create(CancelDocumentService cancelDocumentService);

    void edit(CancelDocumentService cancelDocumentService);

    void remove(CancelDocumentService cancelDocumentService);

    CancelDocumentService find(Object id);

    List<CancelDocumentService> findAll();

    List<CancelDocumentService> findRange(int[] range);

    List<CancelDocumentService> findByNoReg(String noReg);

    int count();

    public com.pelindo.ebtos.model.db.CancelDocumentService createAndFetch(com.pelindo.ebtos.model.db.CancelDocumentService cancelDocumentService);

}
