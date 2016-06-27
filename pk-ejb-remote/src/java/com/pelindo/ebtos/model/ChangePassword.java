/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.model;

import java.io.Serializable;
/**
 *
 * @author R Seno Anggoro
 */
public class ChangePassword implements Serializable {
    private String currentPassword;
    private String newPassword;
    private String repeatNewPassword;

    public ChangePassword(){}

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }

    public boolean isValid(){
        return getCurrentPassword() != null && getNewPassword() != null && getRepeatNewPassword() != null;
    }

    public boolean isNewPasswordValid(){
        return isValid() && getNewPassword().equals(getRepeatNewPassword());
    }
}
