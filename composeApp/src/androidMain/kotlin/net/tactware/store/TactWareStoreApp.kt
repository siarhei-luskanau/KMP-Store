package net.tactware.store

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TactWareStoreApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TactWareStoreApp)
            modules(listOf(commonAppModule, sharedCommonModule))
        }
    }
}