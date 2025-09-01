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

class LoginCommand(
    private val auth: Auth,
    private val logger: Logger
) {
    fun createCommand(): LiteralCommandNode<CommandSourceStack> {
        return Commands.literal("login")
            .then(Commands.argument("password", StringArgumentType.word())
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

        val password = StringArgumentType.getString(c, "password")

        val users = transaction {
            Users.selectAll().where { Users.username eq sender.player!!.name }.limit(1).toList()
        }

        if (users.isEmpty()) {
            sender.sendRichMessage(Messages.LOGIN_FAILED)
            return Command.SINGLE_SUCCESS
        }

        val hashing = Hashing()
        val user = users.first()
        val hashedPassword = user[Users.password]

        if (!hashing.authenticate(password.toCharArray(), hashedPassword)) {
            sender.sendRichMessage(Messages.LOGIN_FAILED)
            return Command.SINGLE_SUCCESS
        }

        auth.add(sender.player!!.name)
        sender.sendPlainMessage(Messages.LOGIN_OK)

        return Command.SINGLE_SUCCESS
    }
}