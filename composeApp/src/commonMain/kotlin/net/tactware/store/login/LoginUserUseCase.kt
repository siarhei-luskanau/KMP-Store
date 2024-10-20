package net.tactware.store.login

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.rpc.krpc.ktor.client.installRPC
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.krpc.streamScoped
import kotlinx.rpc.withService
import kotlinx.serialization.json.Json
import net.tactware.store.login.bl.LoginResult
import net.tactware.store.login.dal.AuthService

class LoginUserUseCase(private val json: Json) {

    suspend operator fun invoke(username: String, password: String): LoginResult {
        val rpcClient = HttpClient { installRPC() }.rpc {
            url("ws://192.168.1.22:8080/auth")

            rpcConfig {
                serialization {
                    json(json)
                }
            }
        }

        return streamScoped {
            rpcClient.withService<AuthService>().login(username, password)
        }
    }
}