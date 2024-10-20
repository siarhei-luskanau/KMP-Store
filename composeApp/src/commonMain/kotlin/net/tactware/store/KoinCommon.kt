package net.tactware.store

import kotlinx.serialization.json.Json
import net.tactware.store.login.LoginUserUseCase
import net.tactware.store.login.LoginViewModel
import org.koin.dsl.module

val commonAppModule = module {
    factory { LoginUserUseCase(get()) }

    factory { LoginViewModel(get(), get()) }
    single {
        Json {
            serializersModule = serializer
        }
    }
}