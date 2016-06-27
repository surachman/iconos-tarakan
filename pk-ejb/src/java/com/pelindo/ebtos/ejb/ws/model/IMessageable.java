/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.ws.model;

/**
 *
 * @author x42jr
 */
public interface IMessageable {
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";
    public String getMessageCode();
    public String getMessage();
    public void setMessageCode(String messageCode);
    public void setMessage(String message);
}
