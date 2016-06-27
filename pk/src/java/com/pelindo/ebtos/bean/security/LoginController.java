package com.pelindo.ebtos.bean.security;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.qtasnim.jsf.FacesHelper;
import com.pelindo.ebtos.bean.application.Navigation;
import com.pelindo.ebtos.ejb.facade.remote.ActiveDirectoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote;
import com.pelindo.ebtos.model.ChangePassword;
import com.pelindo.ebtos.model.NamedObject;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author senoanggoro
 */
@ManagedBean(name="loginController")
@ViewScoped
public class LoginController implements Serializable{
    private static final String AGENT_GROUP_NAME_PARAM = "ebtos.group.agent";

    private MasterSettingAppFacadeRemote masterSettingAppFacade = lookupMasterSettingAppFacadeRemote();
    private ActiveDirectoryFacadeRemote activeDirectoryFacade = lookupActiveDirectoryFacadeRemote();
    private String username;
    private String password;
    private String module;
    private ChangePassword changePassword;

    public LoginController() {
        module = FacesHelper.translateElExpression("#{module}", FacesContext.getCurrentInstance(), String.class);
        changePassword = new ChangePassword();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ChangePassword getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(ChangePassword changePassword) {
        this.changePassword = changePassword;
    }

    public void invalidate(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(false);
        session.invalidate();
    }

    public void doLogout(ActionEvent event) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        invalidate();
        context.getExternalContext().redirect(Navigation.resolveHomeUrl(context));
    }

    public void changePassword(ActionEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        
        if (getChangePassword() != null && getChangePassword().isValid()) {
            if (getChangePassword().isNewPasswordValid()) {
                LoginSessionBean sessionBean = LoginSessionBean.getCurrentInstance();
                NamedObject user = activeDirectoryFacade.findUserByUid(sessionBean.getUsername());
                if (user != null && activeDirectoryFacade.authenticate(user, changePassword.getCurrentPassword())) {
                    activeDirectoryFacade.changePassword(user, getChangePassword().getNewPassword());
                    requestContext.addCallbackParam("isPasswordChanged", true);
                    FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_INFO, "Info", "application.validation.password_changed");
                    return;
                }
                requestContext.addCallbackParam("isPasswordChanged", false);
                FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.password_wrong");
                return;
            }
            requestContext.addCallbackParam("isPasswordChanged", false);
            FacesHelper.addFacesMessage(facesContext, FacesMessage.SEVERITY_ERROR, "Error", "application.validation.new_password_not_match");
            return;
        }
        requestContext.addCallbackParam("isPasswordChanged", false);
    }

    public void doLogin(ActionEvent event) throws IOException, ServletException {
        FacesContext context = FacesContext.getCurrentInstance();
        NamedObject user = activeDirectoryFacade.findUserByUid(username);
        if (activeDirectoryFacade.authenticate(user, password)){ //check credential
            if (!user.getGroups().contains(masterSettingAppFacade.find(AGENT_GROUP_NAME_PARAM).getValueString())){
                HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
                LoginSessionBean bean = LoginSessionBean.getCurrentInstance();
                session.setAttribute("username", getUsername()); //untuk trigger
                bean.setUsername(getUsername());
                bean.setName(user.getCn());
                bean.setGroups(user.getGroups());
                context.getExternalContext().redirect(Navigation.resolveUrl(context, (module == null || module.equals("")) ? Navigation.DEFAULT_HOME_URL : module));
                return;
            }
            FacesHelper.addErrorFacesMessage(context, "message.access.login_error_credential");
            invalidate();
            return;
        }
        if (activeDirectoryFacade.getMessage() != null)
            FacesHelper.addErrorFacesMessage(context, activeDirectoryFacade.getMessage());
        invalidate();
    }

    private ActiveDirectoryFacadeRemote lookupActiveDirectoryFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (ActiveDirectoryFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/ActiveDirectoryFacade!com.pelindo.ebtos.ejb.facade.remote.ActiveDirectoryFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MasterSettingAppFacadeRemote lookupMasterSettingAppFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (MasterSettingAppFacadeRemote) c.lookup("java:global/pkproject/pk-ejb/MasterSettingAppFacade!com.pelindo.ebtos.ejb.facade.remote.MasterSettingAppFacadeRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
