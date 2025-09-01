package es.pablouser1.umacraft.models

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object Invitations: IntIdTable("invitations") {
    val niu = varchar("niu", 16) // TODO: NIU is shorter, I think!
    val code = varchar("code", 12)
}
