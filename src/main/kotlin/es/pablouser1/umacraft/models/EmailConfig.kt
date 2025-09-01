package es.pablouser1.umacraft.models

import es.pablouser1.umacraft.enums.MailEncryptionEnum

data class EmailConfig(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val encryption: MailEncryptionEnum,
)
