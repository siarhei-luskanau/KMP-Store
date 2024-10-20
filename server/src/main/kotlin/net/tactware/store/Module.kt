package net.tactware.store

import kotlinx.serialization.json.Json
import net.tactware.store.bl.UserAuthService
import net.tactware.store.dal.UserRepository
import net.tactware.store.login.dal.AuthService
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.math.sin

val module = module {
    single { UserRepository() }
    single { UserAuthService(it[0], get()) } bind AuthService::class

    single { Json {
        serializersModule = serializer
    } }
}