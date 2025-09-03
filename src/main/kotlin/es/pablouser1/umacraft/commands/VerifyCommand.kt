package es.pablouser1.umacraft.commands

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import es.pablouser1.umacraft.constants.Messages
import es.pablouser1.umacraft.helpers.Auth
import es.pablouser1.umacraft.helpers.Hashing
import es.pablouser1.umacraft.models.Invitations
import es.pablouser1.umacraft.models.Users
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.entity.Player
import org.jetbrains.exposed.v1.core.statements.UpsertSqlExpressionBuilder.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.logging.Logger

class VerifyCommand(
    private val auth: Auth,
    private val logger: Logger
) {
    fun createCommand(): LiteralCommandNode<CommandSourceStack> {
        return Commands.literal("verify")
            .then(Commands.argument("code", StringArgumentType.word())
                .then(Commands.argument("password", StringArgumentType.word())
                        .executes { ctx -> runCommand(ctx) }
                    )
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

        val code = StringArgumentType.getString(c, "code")
        val password = StringArgumentType.getString(c, "password")

        val invitations = transaction {
            Invitations.selectAll().where { Invitations.code eq code }.toList()
        }

        if (invitations.isEmpty()) {
            sender.sendRichMessage(Messages.VERIFY_INVALID_CODE)
            return Command.SINGLE_SUCCESS
        }

        val invitation = invitations.first()
        val invitationId = invitation[Invitations.id]
        val userNiu = invitation[Invitations.niu]

        val hashing = Hashing()
        val username = sender.name
        val hashedPassword = hashing.hash(password.toCharArray())

        transaction {
            // Add user to db
            Users.insert {
                it[this.niu] = userNiu
                it[this.username] = username
                it[this.password] = hashedPassword
            }

            // Remove invitation after user has been inserted
            Invitations.deleteWhere { Invitations.id eq invitationId }

            logger.info("User created, NIU $userNiu | Username: $username | Id: $invitationId")
            sender.sendPlainMessage(Messages.VERIFY_OK)
        }

        return Command.SINGLE_SUCCESS
    }
}