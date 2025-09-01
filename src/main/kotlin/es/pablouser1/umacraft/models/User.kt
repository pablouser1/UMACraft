package es.pablouser1.umacraft.models

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Users: IntIdTable("users") {
    val username = varchar("username", 32)
    val niu = varchar("niu", 16)
    val password = varchar("password", 72)
}