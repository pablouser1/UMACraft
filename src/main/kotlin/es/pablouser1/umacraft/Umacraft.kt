package es.pablouser1.umacraft

import es.pablouser1.umacraft.commands.CentrosCommand
import es.pablouser1.umacraft.commands.LoginCommand
import es.pablouser1.umacraft.commands.RegisterCommand
import es.pablouser1.umacraft.commands.VerifyCommand
import es.pablouser1.umacraft.enums.MailEncryptionEnum
import es.pablouser1.umacraft.helpers.Auth
import es.pablouser1.umacraft.helpers.Mail
import es.pablouser1.umacraft.listeners.AuthListener
import es.pablouser1.umacraft.listeners.CentrosInventoryListener
import es.pablouser1.umacraft.models.EmailConfig
import es.pablouser1.umacraft.models.Invitations
import es.pablouser1.umacraft.models.Users
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class Umacraft : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()

        setupDb()

        val mail = Mail(EmailConfig(
            host = config.getString("mail.host", "localhost")!!,
            port = config.getInt("mail.port", 1025),
            username = config.getString("mail.username", "")!!,
            password = config.getString("mail.password", "")!!,
            encryption = MailEncryptionEnum.valueOf(config.getString("mail.encryption", "NONE")!!),
        ))

        val auth = Auth()

        server.pluginManager.registerEvents(AuthListener(auth), this)
        server.pluginManager.registerEvents(CentrosInventoryListener(), this)

        setupCommands(auth, mail)

        logger.info("Umacraft Plugin Enabled")
    }

    override fun onDisable() {
        logger.info("Umacraft Plugin Disabled")
    }

    private fun setupDb() {
        val dbUri = config.getString("db.uri") ?: "jdbc:sqlite:file:test?mode=memory&cache=shared"
        val dbDriver = config.getString("db.driver") ?: "org.sqlite.JDBC"
        Database.connect(url = dbUri, driver = dbDriver)
        transaction {
            SchemaUtils.create(Invitations, Users)
        }
    }

    private fun setupCommands(auth: Auth, mail: Mail) {
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS, {commands ->
            val registrar = commands.registrar()
            registrar.register(RegisterCommand(auth, mail, logger).createCommand())
            registrar.register(VerifyCommand(auth, logger).createCommand())
            registrar.register(LoginCommand(auth, logger).createCommand())
            registrar.register(CentrosCommand().createCommand())
        })
    }
}
