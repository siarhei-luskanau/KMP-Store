package net.tactware.store.login.bl

import kotlinx.serialization.Serializable

@Serializable
sealed class LoginResult {

    @Serializable
    data class Success(val token: String): LoginResult()

    @Serializable
    data class Failure(val message: String): LoginResult()
}