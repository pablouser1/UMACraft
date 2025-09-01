package es.pablouser1.umacraft.listeners

import es.pablouser1.umacraft.constants.Messages
import es.pablouser1.umacraft.helpers.Auth
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent

class AuthListener(private val auth: Auth): Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        e.player.sendMessage(Messages.LOGIN)
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        this.auth.delete(e.player.name);
    }

    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        if (this.auth.exists(e.player.name)) {
            return;
        }

        if (e.hasChangedBlock() || e.hasChangedOrientation() || e.hasChangedPosition()) {
            e.isCancelled = true;
        }
    }

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        if (this.auth.exists(e.player.name)) {
            return;
        }

        e.isCancelled = true;
    }

    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        if (this.auth.exists(e.player.name)) {
            return;
        }

        e.isCancelled = true;
    }
}