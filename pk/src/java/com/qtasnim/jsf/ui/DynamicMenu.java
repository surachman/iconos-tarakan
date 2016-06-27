package com.qtasnim.jsf.ui;


import com.qtasnim.jsf.FacesHelper;
import com.qtasnim.security.RoleValidator;
import java.io.IOException;
import java.util.List;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import org.primefaces.component.menubar.Menubar;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;

/**
 *
 * @author senoanggoro
 */
@FacesComponent(value = "dynamicMenu")
public class DynamicMenu extends UIComponentBase {
    private Menu menuRoot;
    private RoleValidator roleValidator;
    private List<String> userGroup;
    private String contextName;

    @Override
    public String getFamily() {
        return "custom";
    }


    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        menuRoot = (Menu) getAttributes().get("menuRoot");
        roleValidator = (RoleValidator) getAttributes().get("roleValidator");
        userGroup = (List<String>) getAttributes().get("userGroup");
        contextName = (String) getAttributes().get("contextName");
        if (roleValidator instanceof com.qtasnim.security.RoleValidator) {
            Menubar menubar = new Menubar();
            //menubar.setAutoSubmenuDisplay(true);
            construct(menubar, getMenuRoot().getChildren(), context);
            menubar.encodeAll(context);
        } else {
            throw new RuntimeException("Comparator must be instance of com.qtasnim.util.RoleValidator");
        }
    }

    private void construct(UIComponent parent, List<Menu> children, FacesContext context){
        for (Menu menu: children){
            if (menu.childrenNumber() == 0){
                if (roleValidator.isValid(menu.getUrl(), getUserGroup())){
                    MenuItem menuItem = new MenuItem();
                    menuItem.setValue(FacesHelper.getLocaleMessage(menu.getLabel(), context));
                    menuItem.setUrl(contextName + menu.getUrl());
                    parent.getChildren().add(menuItem);
                }
            } else {
                Submenu sub = new Submenu();
                sub.setLabel(FacesHelper.getLocaleMessage(menu.getLabel(), context));
                construct(sub, menu.getChildren(), context);
                if (!sub.getChildren().isEmpty())
                    parent.getChildren().add(sub);
            }
        }
    }

    public Menu getMenuRoot() {
        return menuRoot;
    }

    public void setMenuRoot(Menu menuRoot) {
        this.menuRoot = menuRoot;
    }

    public RoleValidator getRoleValidator() {
        return roleValidator;
    }

    public void setRoleValidator(RoleValidator comparator) {
        this.roleValidator = comparator;
    }

    public List<String> getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(List<String> userGroup) {
        this.userGroup = userGroup;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }
 }