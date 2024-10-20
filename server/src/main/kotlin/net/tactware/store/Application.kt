package net.tactware.store

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import kotlinx.rpc.krpc.ktor.server.RPC
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import net.tactware.store.bl.UserAuthService
import net.tactware.store.login.dal.AuthService
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.getKoin
import org.koin.ktor.plugin.Koin

fun main() {
    embeddedServer(Netty, port = 8080, host = "192.168.1.22", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(Koin) {
        modules(module, sharedCommonModule)
    }

    install(RPC)

    val koin = getKoin()
    routing {
        rpc("/auth") {
            rpcConfig {
                serialization {
                    json(koin.get())
                }
            }

            registerService<AuthService> { ctx ->
                koin.get { parametersOf(ctx) }
            }
        }
    }
}