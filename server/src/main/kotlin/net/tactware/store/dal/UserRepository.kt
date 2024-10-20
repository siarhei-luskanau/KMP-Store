package net.tactware.store.dal

import at.favre.lib.crypto.bcrypt.BCrypt
import net.tactware.store.bl.entities.User

class UserRepository {
    private val users = listOf(
        User("admin", hashPassword("testPassword")),
        User("user", hashPassword("userPassword"))
    )

    fun findByUsername(username: String): User? {
        return users.find { it.username == username }
    }


    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }
}