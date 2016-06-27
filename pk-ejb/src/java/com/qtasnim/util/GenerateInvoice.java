/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qtasnim.util;

import com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author dycoder
 */
public class GenerateInvoice {

    InvoiceFacadeRemote invoiceFacade = lookupInvoiceFacadeRemote();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
    String tgl = dateFormat.format(Calendar.getInstance().getTime());
    String i;
    String id = invoiceFacade.generateId(tgl);

    public void generateId(){
        if (id == null) {
            i = "0000001";
        } else {
            i = String.valueOf(Integer.valueOf(id) + 1);
        }

        if (i.length() == 1) {
            i = "000000" + i;
        } else if (i.length() == 2) {
            i = "00000" + i;
        } else if (i.length() == 3) {
            i = "0000" + i;
        } else if (i.length() == 4) {
            i = "000" + i;
        } else if (i.length() == 5) {
            i = "00" + i;
        } else if (i.length() == 6) {
            i = "0" + i;
        }
    }

    public String container() {
        generateId();
        return "4D" + tgl + "." + i;
    }

    public String goods() {
        generateId();
        return "4B" + tgl + "." + i;
    }

    private InvoiceFacadeRemote lookupInvoiceFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (InvoiceFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/InvoiceFacade!com.pelindo.ebtos.ejb.facade.remote.InvoiceFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
