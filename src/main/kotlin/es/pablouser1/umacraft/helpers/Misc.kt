package es.pablouser1.umacraft.helpers

import java.security.SecureRandom

object Misc {
    /**
     * @link https://stackoverflow.com/a/157202
     */
    fun randomString(len: Int): String {
        val ab = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val rnd = SecureRandom()

        val sb = StringBuilder(len)
        for (i in 0..<len) {
            sb.append(ab[rnd.nextInt(ab.length)])
        }
        return sb.toString()
    }
}