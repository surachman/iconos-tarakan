
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.pelindo.ebtos.model;

import java.io.Serializable;

/**
 * Class description
 *
 *
 * @version        $version$, 10/11/23
 * @author         R Seno Anggoro 
 * @company	   Dycode   
 */
public class Ship implements Serializable {
    private int size;

    public Ship(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}



/*
 * @(#)Ship.java   10/11/23
 * 
 * Copyright (c) 2010 Dycode
 */


//~ Formatted by Jindent --- http://www.jindent.com
