package net.tactware.store.login.dal

import kotlinx.rpc.RPC
import net.tactware.store.login.bl.LoginResult

interface AuthService : RPC {

    suspend fun login(username: String, password: String): LoginResult

}