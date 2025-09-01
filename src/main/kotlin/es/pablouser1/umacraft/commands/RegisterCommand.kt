package es.pablouser1.umacraft.commands

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import es.pablouser1.umacraft.constants.Messages
import es.pablouser1.umacraft.helpers.Auth
import es.pablouser1.umacraft.helpers.Mail
import es.pablouser1.umacraft.helpers.Misc
import es.pablouser1.umacraft.models.Invitations
import es.pablouser1.umacraft.models.Users
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.entity.Player
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.logging.Logger

class RegisterCommand(
    private val auth: Auth,
    private val mail: Mail,
    private val logger: Logger
) {
    fun createCommand(): LiteralCommandNode<CommandSourceStack> {
        return Commands.literal("register")
            .then(
                Commands.argument("niu", StringArgumentType.word())
                    .executes { ctx -> runCommand(ctx) }
            )
            .build()
    }

    private fun runCommand(c: CommandContext<CommandSourceStack>): Int {
        val sender = c.source.sender

        if (sender !is Player) {
            sender.sendRichMessage(Messages.COMMON_MUST_BE_PLAYER)
            return Command.SINGLE_SUCCESS
        }

        if (auth.exists(sender.player!!.name)) {
            sender.sendRichMessage(Messages.COMMON_ALREADY_LOGGED_IN)
            return Command.SINGLE_SUCCESS
        }

        val niu = StringArgumentType.getString(c, "niu")
        val code = Misc.randomString(12)

        // Check if invite already exists
        val invitationExists = transaction {
            Invitations.selectAll().where { Invitations.niu eq niu }.limit(1).count() > 0
        }
        if (invitationExists) {
            sender.sendRichMessage(Messages.REGISTRATION_INVITATION_EXISTS)
            return Command.SINGLE_SUCCESS
        }

        // Check if user already exists
        val userExists = transaction {
            Users.selectAll().where { Users.niu eq niu }.limit(1).count() > 0
        }
        if (userExists) {
            sender.sendRichMessage(Messages.REGISTRATION_USER_EXISTS)
            return Command.SINGLE_SUCCESS
        }

        // Do actual invite
        transaction {
            val id = Invitations.insert {
                it[this.niu] = niu
                it[this.code] = code
            } get Invitations.id
            
            mail.sendCode(niu, code)
            logger.info("User invited with NIU $niu, ID $id")
            sender.sendPlainMessage(Messages.REGISTRATION_OK)
        }

        return Command.SINGLE_SUCCESS
    }
}