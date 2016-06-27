/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean.application;

import com.qtasnim.jsf.ui.Menu;
import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.url.UrlHelper;
import com.qtasnim.security.RoleValidator;
import com.pelindo.ebtos.ejb.facade.remote.EdifactFactoryFacadeRemote;
import com.pelindo.ebtos.ejb.facade.remote.MasterRoleFacadeRemote;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import com.pelindo.ebtos.ejb.facade.remote.MasterViewFacadeRemote;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;


/**
 *
 * @author senoanggoro
 */
@ManagedBean(name=Navigation.NAME, eager=true)
@ApplicationScoped
public final class Navigation {
    @EJB
    private MasterViewFacadeRemote masterViewFacade;
    @EJB
    private MasterRoleFacadeRemote masterRoleFacade;

    public static final String NAME = "navigation";
    public static final String DEFAULT_HOME_URL = "/modules/Home.xhtml";
    public static final String FACES_CONTEXT_NAME = "/faces";

    private Menu menuRoot;
    private HashMap<String, List<String>> roles;
    private RoleValidator roleValidator = new RoleValidator() {
                                                @Override
                                                public boolean isValid(Object url, Object group) {
                                                    return isRoleMatch((String)url, (List<String>)group);
                                                }
                                            };

    public Navigation() {}

    @PostConstruct
    private void construct() {
        try {
            resetMenus();
        } catch (IOException ex) {
            Logger.getLogger(Navigation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resetMenus() throws IOException{
        menuRoot = new Menu();
        roles = new HashMap();
        menuConstruct(menuRoot, 0);
    }

    public void resetMenus(ActionEvent event) throws IOException{
        resetMenus();
    }

    private void menuConstruct(Menu parent, int parentId){
        List<Object[]> items = masterViewFacade.findChildrenByParent(parentId);
        for (Object[] item: items){
            Menu menu = new Menu(item[2].toString());
            if (((BigDecimal) item[4]).intValue() == 0){
                String url = item[1].toString();
                menu.setUrl(url);
                roles.put(url, masterRoleFacade.findGroupsByView(((BigDecimal) item[0]).intValue()));
            } else
                menuConstruct(menu, ((BigDecimal)item[0]).intValue());
            parent.addChild(menu);
        }
    }


    private boolean isRoleMatch(String url, List<String> groups){
        List<String> list = roles.get(url);
        if (list == null || list.isEmpty())
            return true; //public url
        if (groups == null || groups.isEmpty())
            return false; //login required
        for (String group: groups){
            if (UrlHelper.compare(list, group))
                return true;
        }
        return false;
    }

    public Menu getMenuRoot(){
        return menuRoot;
    }

    public RoleValidator getRoleValidator() {
        return roleValidator;
    }

    public String getContextName(){
        return FACES_CONTEXT_NAME;
    }

    public String getCurrentMessage(){
        return masterViewFacade.findMessageByView(FacesContext.getCurrentInstance().getViewRoot().getViewId());
    }

    public static String resolveUrl(FacesContext context, String url){
        return context.getApplication().getViewHandler().getActionURL(context, url);
    }

    public static String resolveHomeUrl(FacesContext context){
        return context.getApplication().getViewHandler().getActionURL(context, DEFAULT_HOME_URL);
    }

    public static Navigation getCurrentInstance(){
        return FacesHelper.translateElExpression(String.format("#{%s}", Navigation.NAME), FacesContext.getCurrentInstance(), Navigation.class);
    }
}
