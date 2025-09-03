package es.pablouser1.umacraft.constants

class Messages {
    companion object {
        // Login inicial
        const val LOGIN = "¡Bienvenido! Inicia sesión usando /login. También puedes registrate usando /register."

        // Common
        const val COMMON_MUST_BE_LOGGED_IN = "<red>Tienes que iniciar sesión."
        const val COMMON_ALREADY_LOGGED_IN = "<red>Ya has iniciado sesión."
        const val COMMON_MUST_BE_PLAYER = "<red>Tienes que ser un jugador."

        // Registration
        const val REGISTRATION_OK = "Se ha enviado un correo electrónico a tu correo institucional de la UMA con más instrucciones."
        const val REGISTRATION_INVITATION_EXISTS = "<red>La invitación ya existe."
        const val REGISTRATION_USER_EXISTS = "<red>El usuario ya existe."

        // Verify
        const val VERIFY_OK = "¡Registro terminado, bienvenid@! Puedes iniciar sesión con /login"
        const val VERIFY_INVALID_CODE = "<red>Código inválido."
        const val VERIFY_CENTRO_REMINDER = "Recuerda que puedes elegir tu facultad / escuela una vez inicias sesión con /centros"

        // Login
        const val LOGIN_OK = "Sesión iniciada con éxito."
        const val LOGIN_FAILED = "<red>Contraseña incorrecta o no estás registrado."
    }
}