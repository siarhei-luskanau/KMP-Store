package net.tactware.store.bl

import at.favre.lib.crypto.bcrypt.BCrypt
import net.tactware.store.dal.UserRepository
import net.tactware.store.login.bl.LoginResult
import net.tactware.store.login.dal.AuthService
import kotlin.coroutines.CoroutineContext

class UserAuthService(override val coroutineContext: CoroutineContext, private val userRepository: UserRepository) :
    AuthService {

    private fun verifyPassword(password: String, passwordHash: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), passwordHash)
        return result.verified
    }

    override suspend fun login(username: String, password: String): LoginResult {
        val user = userRepository.findByUsername(username)
        return if (user != null && verifyPassword(password, user.passwordHash)) {
            LoginResult.Success("TestToken")
        } else {
            LoginResult.Failure("Invalid username or password")
        }
    }
}