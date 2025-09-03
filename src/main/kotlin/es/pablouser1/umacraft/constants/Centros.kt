package es.pablouser1.umacraft.constants

import es.pablouser1.umacraft.models.Centro
import org.bukkit.Material

class Centros {
    companion object {
        // TODO: Replace Material.STONE for better items
        private val LIST = listOf(
            Centro(
                "E.T.S. DE ARQUITECTURA",
                Material.BRICK
            ),
            Centro(
                "E.T.S. DE INGENIERÍA INFORMÁTICA",
                Material.REDSTONE_BLOCK
            ),
            Centro(
                "ESCUELA DE INGENIERÍAS INDUSTRIALES",
                Material.PISTON
            ),
            Centro(
            "E.T.S. DE INGENIERÍA DE TELECOMUNICACIÓN",
                Material.STONE
            ),
            Centro(
            "FACULTAD DE BELLAS ARTES",
                Material.GRASS_BLOCK
            ),
            Centro(
            "FACULTAD DE CIENCIAS",
                Material.STONE
            ),
            Centro(
            "FACULTAD DE CIENCIAS DE LA COMUNICACIÓN",
                Material.STONE
            ),
            Centro(
            "FACULTAD DE CIENCIAS DE LA EDUCACIÓN",
                Material.BOOK
            ),
            Centro(
            "FACULTAD DE CIENCIAS DE LA SALUD",
                Material.STONE
            ),
            Centro(
            "FACULTAD DE CIENCIAS ECONÓMICAS Y EMPRESARIALES",
                Material.SAND
            ),
            Centro(
            "FACULTAD DE DERECHO",
                Material.GRAVEL
            ),
            Centro(
            "FACULTAD DE ESTUDIOS SOCIALES Y DEL TRABAJO",
                Material.SPONGE
            ),
            Centro(
            "FACULTAD DE FILOSOFÍA Y LETRAS",
                Material.GRASS_BLOCK
            ),
            Centro(
            "FACULTAD DE MARKETING Y GESTIÓN",
                Material.GOLD_BLOCK
            ),
            Centro(
            "FACULTAD DE MEDICINA",
                Material.GOLDEN_APPLE
            ),
            Centro(
            "FACULTAD DE PSICOLOGÍA Y LOGOPEDIA",
                Material.STONE
            ),
            Centro(
            "FACULTAD DE TURISMO",
                Material.ELYTRA
            )
        )

        fun getAll(): List<Centro> {
            return LIST
        }

        fun getByIndex(index: Int): Centro? {
            return if (index in LIST.indices) LIST[index] else null
        }

        fun getSize(): Int {
            return LIST.size
        }

        fun roundSizeForInventory(): Int {
            val clamped = getSize().coerceIn(9, 54)
            val nearest = ((clamped + 4) / 9) * 9

            return nearest.coerceAtMost(54)
        }
    }
}