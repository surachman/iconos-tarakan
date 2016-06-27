/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.bean.tag;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author R. Seno Anggoro A
 */
@ManagedBean(name="containerAttributesBean")
@RequestScoped
public class ContainerAttributesBean {
    private List<String> containerSizes;

    /** Creates a new instance of ContainerAttributesBean */
    public ContainerAttributesBean() {}

    @PostConstruct
    private void construct() {
        containerSizes = Arrays.asList(new String[] {"20", "40", "45"});
    }

    public List<String> getContainerSizes() {
        return containerSizes;
    }
}
