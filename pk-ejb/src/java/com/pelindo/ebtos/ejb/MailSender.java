/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb;

import com.pelindo.ebtos.ejb.local.MailSenderLocal;
import com.pelindo.ebtos.ejb.remote.MailSenderRemote;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author R. Seno Anggoro A
 */
@Stateless
public class MailSender implements MailSenderLocal, MailSenderRemote {
    @Resource(name="mail/ebtosMail_session")
    private Session mailSession;

    public void sendEmailTest() {
        String host = "rsenoanggoro-pc";
        String sender = "support@mail.rsenoanggoro-pc";
        String port = "25";
        String recipient = "xn_mue@yahoo.co.id";
        String password = "support";
        String subject = "email test";
        String message = "this email sended automatically";

        MailSender mailSender = new MailSender();

        try {
            String file = "ini adalah attachment";
            DataSource source = new ByteArrayDataSource(file.getBytes(), "application/txt");
            mailSender.sendEmail(sender, recipient, host, port, password, subject, message, source, "test.txt");
            Logger.getLogger(MailSender.class.getName()).log(Level.INFO, "Email sended");
        } catch (MessagingException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, "MessagingException caught", ex);
        } catch (RuntimeException re) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, "RuntimeException caught", re);
        }
    }

    public void sendEmail(String recipient, String subject, String text, DataSource attachment, String attachmentName) throws MessagingException {
        if (mailSession != null) {
            sendEmail(mailSession, recipient, subject, text, attachment, attachmentName);
            return;
        }

        throw new NullPointerException("gf resource 'mail/ebtosMail_session' not found");
    }
    
    private void sendEmail(Session session, String recipient, String subject, String text, DataSource attachment, String attachmentName) throws MessagingException{
        Transport transport = null;

        try {
            String sender = session.getProperties().getProperty("mail.smtp.user");
            String host = session.getProperties().getProperty("mail.smtp.host");
            String password = session.getProperties().getProperty("mail.smtp.password");
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender, "e-BTOS Support"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);

            if (attachment != null) {
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(text);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.setDataHandler(new DataHandler(attachment));
                attachmentBodyPart.setFileName(attachmentName != null ? attachmentName : "attachment.txt");
                multipart.addBodyPart(attachmentBodyPart);

                message.setContent(multipart);
            } else {
                message.setText(text);
            }

            transport = session.getTransport("smtp");
            transport.connect(host, sender, password);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException mex) {
            throw mex;
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    private void sendEmail(String sender, String recipient, String host, String port, String password, String subject, String text) throws MessagingException{
        sendEmail(sender, recipient, host, port, password, subject, text, null, null);
    }

    private void sendEmail(String sender, String recipient, String host, String port, String password, String subject, String text, DataSource attachment, String attachmentName) throws MessagingException{
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", sender);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getDefaultInstance(props);
            sendEmail(session, recipient, subject, text, attachment, attachmentName);
        } catch (MessagingException mex) {
            throw mex;
        }
    }
}
