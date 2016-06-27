/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.ws.model;

import java.io.Serializable;

/**
 *
 * @author x42jr
 */
public class Message implements Serializable, IMessageable {
    private String messageCode;
    private String message;

    public Message() {
    }

    public Message(String messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

}
