package es.pablouser1.umacraft.commands

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import es.pablouser1.umacraft.constants.Messages
import es.pablouser1.umacraft.helpers.Auth
import es.pablouser1.umacraft.inventories.CentrosInventory
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.entity.Player

class CentrosCommand(private val auth: Auth) {
    fun createCommand(): LiteralCommandNode<CommandSourceStack> {
        return Commands.literal("centros")
            .executes { ctx -> runCommand(ctx) }
            .build()
    }

    private fun runCommand(c: CommandContext<CommandSourceStack>): Int {
        val sender = c.source.sender
        if (sender !is Player) {
            sender.sendRichMessage(Messages.COMMON_MUST_BE_PLAYER)
            return Command.SINGLE_SUCCESS
        }

        val username = sender.player!!.name

        if (!auth.exists(username)) {
            sender.sendRichMessage(Messages.COMMON_MUST_BE_LOGGED_IN)
            return Command.SINGLE_SUCCESS
        }

        val inv = CentrosInventory(sender.server)
        inv.populate()
        sender.openInventory(inv.inventory)

        return Command.SINGLE_SUCCESS
    }
}