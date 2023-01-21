package es.pablouser1.umacraft.helpers;

import es.pablouser1.umacraft.models.EmailConfig;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Mail {
    final private Message message;

    private static final String DOMAIN = "@uma.es";
    public Mail(EmailConfig data) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", data.host);
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", data.host);
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(data.username, data.password);
            }
        });
        this.message = new MimeMessage(session);
        try {
            this.message.setFrom(new InternetAddress(data.username));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendCode(String niu, String code) {
        boolean success = true;
        try {
            this.message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(niu + Mail.DOMAIN));
            this.message.setSubject("¡Bienvenido a UMACraft!");

            String msg = "Tu Código: " + code;

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            this.message.setContent(multipart);

            Transport.send(this.message);
        } catch (Exception e) {
            success = false;
        }
        return success;
    }
}
