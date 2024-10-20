package net.tactware.store.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import net.tactware.qrg.appwide.bl.coroutines.IDispatcherProvider
import net.tactware.store.login.bl.LoginResult

class LoginViewModel(private val loginUserUseCase: LoginUserUseCase, private val dispatcher: IDispatcherProvider) :
    ViewModel() {

    internal var username by mutableStateOf("")
        private set

    internal var password by mutableStateOf("")
        private set

    internal val snackbarHostState = SnackbarHostState()

    fun onInteraction(interaction: LoginInteraction) {
        when (interaction) {
            LoginInteraction.AttemptToLogin -> {
                viewModelScope.launch(dispatcher.default()) {
                    when (val result = loginUserUseCase.invoke(username, password)) {
                        is LoginResult.Success -> {
                            snackbarHostState.showSnackbar("Login successful")
                        }

                        is LoginResult.Failure -> {
                            snackbarHostState.showSnackbar(result.message)
                        }
                    }
                }

            }

            is LoginInteraction.EntersPassword -> {
                password = interaction.password
            }

            is LoginInteraction.EntersUsername -> {
                username = interaction.username
            }
        }
    }
}