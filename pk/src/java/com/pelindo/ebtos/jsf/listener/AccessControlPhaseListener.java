/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.jsf.listener;

import com.qtasnim.jsf.FacesHelper;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import com.pelindo.ebtos.bean.security.LoginSessionBean;
import com.pelindo.ebtos.bean.application.Navigation;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author senoanggoro
 */
public class AccessControlPhaseListener implements PhaseListener {
    private static final long serialVersionUID = 1L;

    @Override
    public void afterPhase(PhaseEvent event){
        try {
            FacesContext context = event.getFacesContext();
            //context.getExternalContext().getFlash().setKeepMessages(false);
            LoginSessionBean loginSessionBean = LoginSessionBean.getCurrentInstance();
            Navigation navigation = Navigation.getCurrentInstance();
            if (loginSessionBean == null) {
                Logger.getLogger(getClass().getName()).log(Level.INFO, "Could not obtain instance of sessionBean");
                return;
            }

            String viewId = context.getViewRoot().getViewId();
            
            if (!navigation.getRoleValidator().isValid(viewId, loginSessionBean.getGroups()))
                if (loginSessionBean.isActive())
                    redirectTo(context, Navigation.DEFAULT_HOME_URL, "message.access.not_authorized");
                else{
                    if (viewId != null && !viewId.equals(""))
                        FacesHelper.putParam("module", viewId, context);
                    redirectTo(context, Navigation.DEFAULT_HOME_URL, "message.access.login_required");
                }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "beforePhase caught exception", e);
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {

    }

    private void redirectTo(FacesContext context, String nav, String message) {
        if (message != null)
            FacesHelper.addErrorFacesMessage(context, "Error",  message);
        context.getApplication().getNavigationHandler().handleNavigation(context, null, nav);
    }



    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}