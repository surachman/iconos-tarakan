/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qtasnim.jsf.ui;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author senoanggoro
 */
public class Menu {
    private String label;
    private String url;
    private List<Menu> children;


    public Menu(){
        children = new ArrayList();
    }

    public Menu(String label){
        this();
        this.label = label;
    }

    public Menu(String label, String url){
        this();
        this.label = label;
        this.url = url;
    }

    public void addChild(Menu menuitem){
        children.add(menuitem);
    }

    public List<Menu> getChildren(){
        return children;
    }

    public int childrenNumber(){
        return children.size();
    }

    public String getLabel(){
        return label;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
