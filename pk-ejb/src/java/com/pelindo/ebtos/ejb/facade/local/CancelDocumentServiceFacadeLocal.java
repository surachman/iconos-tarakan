/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.CancelDocumentService;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author x42jr
 */
@Local
public interface CancelDocumentServiceFacadeLocal {

    void create(CancelDocumentService cancelDocumentService);

    void edit(CancelDocumentService cancelDocumentService);

    void remove(CancelDocumentService cancelDocumentService);

    CancelDocumentService find(Object id);

    List<CancelDocumentService> findAll();

    List<CancelDocumentService> findRange(int[] range);

    List<CancelDocumentService> findByNoReg(String noReg);

    int count();

}
