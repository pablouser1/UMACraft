package es.pablouser1.umacraft.listeners

import es.pablouser1.umacraft.constants.Centros
import es.pablouser1.umacraft.inventories.CentrosInventory
import es.pablouser1.umacraft.models.Users
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class CentrosInventoryListener: Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        // We're getting the clicked inventory to avoid situations where the player
        // already has an item in their inventory and clicks that one.
        val inventory = event.clickedInventory
        // Add a null check in case the player clicked outside the window.
        if (inventory == null || inventory.getHolder(false) !is CentrosInventory) {
            return
        }

        event.isCancelled = true

        val player = event.whoClicked
        val rawSlot = event.rawSlot
        val centro = Centros.getByIndex(rawSlot) ?: return

        transaction {
            Users.update({ Users.username eq player.name }) {
                it[this.centro] = rawSlot
            }
        }

        player.sendPlainMessage("Has elegido ${centro.name}")
        val sound = Sound.sound(
            Key.key("minecraft", "block.note_block.pling"),
            Sound.Source.UI, 1f, 1f
        )
        player.playSound(sound)
        player.closeInventory()
    }
}