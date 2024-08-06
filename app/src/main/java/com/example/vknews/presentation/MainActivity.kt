package com.example.vknews.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknews.data.model.AuthState
import com.example.vknews.presentation.screen.login.LoginScreen
import com.example.vknews.presentation.screen.login.LoginScreenViewModel
import com.example.vknews.presentation.theme.VKNewsTheme
import com.vk.id.VKID
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VKID.init(this)
        setContent {
            val component = getApplicationComponent()
            val viewModel: LoginScreenViewModel = viewModel(
                factory = component.getViewModelFactory()
            )
            val authState = viewModel.getAuthState().collectAsState()

            VKNewsTheme {

                when (val currentState = authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen()
                    }

                    AuthState.UnAuthorized -> {
                        LoginScreen {
                            viewModel.login()
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