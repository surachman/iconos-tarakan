/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.local;

import javax.ejb.Local;

/**
 *
 * @author R. Seno Anggoro A
 */
@Local
public interface MailSenderLocal {

    public void sendEmail(java.lang.String recipient, java.lang.String subject, java.lang.String text, javax.activation.DataSource attachment, java.lang.String attachmentName) throws javax.mail.MessagingException;

}
