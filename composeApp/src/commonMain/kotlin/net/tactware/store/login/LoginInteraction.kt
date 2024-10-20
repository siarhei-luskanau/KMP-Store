package net.tactware.store.login

sealed class LoginInteraction {

    data class EntersUsername(val username: String) : LoginInteraction()

    data class EntersPassword(val password: String) : LoginInteraction()

    data object AttemptToLogin : LoginInteraction()
}