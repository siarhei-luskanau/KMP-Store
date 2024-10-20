package net.tactware.store

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Example store",
    ) {
        startKoin {
            modules(commonAppModule, sharedCommonModule)
        }
        App()
    }
}