package net.tactware.store

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.tactware.store.login.LoginInteraction
import net.tactware.store.login.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.getKoin

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            val koin = getKoin()

            val vm = remember { koin.get<LoginViewModel>() }

            Scaffold(snackbarHost = {
                SnackbarHost(vm.snackbarHostState) {
                    Snackbar(it)
                }
            }) { padding ->
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(padding).fillMaxSize()) {
                    Column(Modifier.fillMaxWidth(.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                        val groupModifier = Modifier.fillMaxWidth()
                        OutlinedTextField(
                            modifier = groupModifier,
                            value = vm.username,
                            onValueChange = { vm.onInteraction(LoginInteraction.EntersUsername(it)) },
                            label = {
                                Text("Username")
                            }
                        )

                        OutlinedTextField(
                            modifier = groupModifier,
                            value = vm.password,
                            onValueChange = { vm.onInteraction(LoginInteraction.EntersPassword(it)) },
                            label = {
                                Text("Password")
                            },
                        )

                        Button(
                            modifier = groupModifier,
                            onClick = { vm.onInteraction(LoginInteraction.AttemptToLogin) }
                        ) {
                            Text("Login")
                        }

                    }
                }
            }
        }
    }
}