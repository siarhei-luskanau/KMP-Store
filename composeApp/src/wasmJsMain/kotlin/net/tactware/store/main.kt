package net.tactware.store

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
//import org.koin.compose.KoinContext
//import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
//    startKoin {
//        modules(
//            commonModule,
//        )
//    }
    ComposeViewport(document.body!!) {
//        KoinContext(){
            App()

//        }
    }
}