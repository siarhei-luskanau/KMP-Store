package net.tactware.store

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import net.tactware.qrg.appwide.bl.coroutines.IDispatcherProvider
import net.tactware.store.appwide.bl.DispatcherProvider
import net.tactware.store.login.bl.LoginResult
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedCommonModule = module {

    single { DispatcherProvider() } bind IDispatcherProvider::class

}

val serializer = SerializersModule {

    polymorphic(LoginResult::class) {
        subclass(LoginResult.Success::class, LoginResult.Success.serializer())
        subclass(LoginResult.Failure::class, LoginResult.Failure.serializer())

    }
}