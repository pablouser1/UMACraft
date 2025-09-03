package es.pablouser1.umacraft.inventories

import es.pablouser1.umacraft.constants.Centros
import net.kyori.adventure.text.Component
import org.bukkit.Server
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack


class CentrosInventory(server: Server): InventoryHolder {
    private val inventory = server.createInventory(this, Centros.roundSizeForInventory())

    fun populate() {
        Centros.getAll().forEachIndexed { index, centro ->
            val item = ItemStack.of(centro.material, 1)
            val meta = item.itemMeta!!
            meta.displayName(Component.text(centro.name))
            item.itemMeta = meta

            inventory.setItem(index, item)
        }
    }

    override fun getInventory(): Inventory {
        return inventory
    }
}