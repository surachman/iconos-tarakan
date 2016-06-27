/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.jsf;

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.bean.application.Navigation;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

/**
 *
 * @author R Seno Anggoro
 */
public class ViewExpiredExceptionExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public ViewExpiredExceptionExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();
            if (t instanceof ViewExpiredException) {
                ViewExpiredException vee = (ViewExpiredException) t;
                FacesContext fc = FacesContext.getCurrentInstance();
                try {
                    try {
                        fc.getExternalContext().getFlash().setKeepMessages(true);
                        FacesHelper.addFacesMessage(fc, FacesMessage.SEVERITY_ERROR, "Error", "message.access.session_expired");
                        fc.getExternalContext().redirect(Navigation.resolveUrl(fc, vee.getViewId()));
                    } catch (IOException ex) {
                        Logger.getLogger(ViewExpiredExceptionExceptionHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } finally {
                    i.remove();
                }
            }
        }
        getWrapped().handle();

    }
}