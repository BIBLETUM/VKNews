package com.example.vknews.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknews.presentation.screen.login.AuthState
import com.example.vknews.presentation.screen.login.LoginScreen
import com.example.vknews.presentation.screen.login.LoginScreenViewModel
import com.example.vknews.presentation.screen.login.LoginViewModelFactory
import com.example.vknews.presentation.theme.VKNewsTheme
import com.vk.id.VKID

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VKID.init(this)
        setContent {
            VKNewsTheme {
                val viewModel: LoginScreenViewModel = viewModel(
                    factory = LoginViewModelFactory(VKID.instance)
                )
                val authState = viewModel.authState.collectAsState()

                when (val currentState = authState.value) {
                    is AuthState.Authorized -> {
                        val manager = TokenManager(LocalContext.current.applicationContext)
                        val token = currentState.accessToken
                        manager.saveToken(token.token)

                        MainScreen()
                    }

                    AuthState.UnAuthorized -> {
                        LoginScreen {
                            viewModel.login(VKID.instance)
                        }
                    }

                    is AuthState.Fail -> {
                        Log.d("AuthState", currentState.fail.description)
                    }

                    AuthState.Initial -> {

                    }
                }
            }
        }
    }
}