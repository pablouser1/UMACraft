package es.pablouser1.umacraft.constants

class Centros {
    companion object {
        private val LIST = listOf(
            "E.T.S. DE ARQUITECTURA",
            "E.T.S. DE INGENIERÍA INFORMÁTICA",
            "ESCUELA DE INGENIERÍAS INDUSTRIALES",
            "E.T.S. DE INGENIERÍA DE TELECOMUNICACIÓN",
            "FACULTAD DE BELLAS ARTES",
            "FACULTAD DE CIENCIAS",
            "FACULTAD DE CIENCIAS DE LA COMUNICACIÓN",
            "FACULTAD DE CIENCIAS DE LA EDUCACIÓN",
            "FACULTAD DE CIENCIAS DE LA SALUD",
            "FACULTAD DE CIENCIAS ECONÓMICAS Y EMPRESARIALES",
            "FACULTAD DE DERECHO",
            "FACULTAD DE ESTUDIOS SOCIALES Y DEL TRABAJO",
            "FACULTAD DE FILOSOFÍA Y LETRAS",
            "FACULTAD DE MARKETING Y GESTIÓN",
            "FACULTAD DE MEDICINA",
            "FACULTAD DE PSICOLOGÍA Y LOGOPEDIA",
            "FACULTAD DE TURISMO",
        )

        fun getAll(): List<String> {
            return LIST
        }

        fun getByIndex(index: Int): String? {
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