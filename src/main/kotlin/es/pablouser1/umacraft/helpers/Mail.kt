package es.pablouser1.umacraft.helpers

import es.pablouser1.umacraft.enums.MailEncryptionEnum
import es.pablouser1.umacraft.models.EmailConfig
import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimeMessage
import jakarta.mail.internet.MimeMultipart
import java.util.*

class Mail(data: EmailConfig) {
    companion object {
        private const val DOMAIN = "@uma.es"
    }

    private val message: Message

    init {
        val prop = Properties()
        prop["mail.smtp.host"] = data.host
        prop["mail.smtp.port"] = data.port
        prop["mail.smtp.auth"] = !data.password.isEmpty()

        if (data.encryption == MailEncryptionEnum.STARTTLS) {
            prop["mail.smtp.starttls.enable"] = true
            prop["mail.smtp.ssl.trust"] = data.host
        }

        val session = if (!data.password.isEmpty()) Session.getInstance(prop, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(data.username, data.password)
            }
        }) else Session.getDefaultInstance(prop)

        this.message = MimeMessage(session)
        try {
            this.message.setFrom(InternetAddress(data.username))
        } catch (e: MessagingException) {
            throw RuntimeException(e)
        }
    }

    fun sendCode(niu: String, code: String) {
        this.message.setRecipient(Message.RecipientType.TO, InternetAddress.parse(niu + DOMAIN).first())
        this.message.subject = "¡Bienvenid@ a UMACraft!"

        val msg = "Tu código: $code"

        val mimeBodyPart = MimeBodyPart()
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8")

        val multipart: Multipart = MimeMultipart()
        multipart.addBodyPart(mimeBodyPart)

        this.message.setContent(multipart)

        Transport.send(this.message)
    }
}