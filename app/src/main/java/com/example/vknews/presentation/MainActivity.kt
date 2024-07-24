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

    private val component by lazy {
        (application as NewsFeedApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        VKID.init(this)
        setContent {
            VKNewsTheme {
                val viewModel: LoginScreenViewModel = viewModel(
                    factory = viewModelFactory
                )
                val authState = viewModel.getAuthState().collectAsState()

                when (val currentState = authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen(viewModelFactory)
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