package es.pablouser1.umacraft.listeners;

import es.pablouser1.umacraft.Umacraft;
import es.pablouser1.umacraft.constants.Messages;
import es.pablouser1.umacraft.helpers.Auth;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AuthListener implements Listener {
    final private Auth auth;

    public AuthListener(Umacraft plugin, Auth auth) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.auth = auth;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)  {
        Player player = e.getPlayer();
        player.sendMessage(Messages.LOGIN);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.auth.delete(e.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (this.auth.exists(e.getPlayer().getName())) {
            return;
        }

        if (e.hasChangedBlock() || e.hasChangedOrientation() || e.hasChangedPosition()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (this.auth.exists(e.getPlayer().getName())) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (this.auth.exists(e.getPlayer().getName())) {
            return;
        }

        e.setCancelled(true);
    }
}
