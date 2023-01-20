package es.pablouser1.umacraft.listeners;

import es.pablouser1.umacraft.Umacraft;
import es.pablouser1.umacraft.constants.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.UUID;

public class AuthListener implements Listener {
    private final ArrayList<UUID> logged;
    public AuthListener(Umacraft plugin) {
        this.logged = new ArrayList<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)  {
        Player player = e.getPlayer();
        player.sendMessage(Messages.LOGIN);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (this.logged.contains((e.getPlayer().getUniqueId()))) {
            return;
        }

        if (e.hasChangedBlock() || e.hasChangedOrientation() || e.hasChangedPosition()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (this.logged.contains((e.getPlayer().getUniqueId()))) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (this.logged.contains((e.getPlayer().getUniqueId()))) {
            return;
        }

        e.setCancelled(true);
    }
}
