package es.pablouser1.umacraft.helpers

class Auth {
    private val logged: ArrayList<String> = ArrayList<String>()

    fun add(username: String) {
        if (!this.logged.contains(username)) {
            this.logged.add(username)
        }
    }

    fun delete(username: String) {
        this.logged.remove(username)
    }

    fun exists(username: String): Boolean {
        return this.logged.contains(username)
    }

    fun clear() {
        this.logged.clear()
    }
}