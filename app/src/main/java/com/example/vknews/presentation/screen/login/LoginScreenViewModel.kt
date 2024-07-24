package com.example.vknews.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.domain.use_case.auth.GetAuthStateUseCaseFlow
import com.example.vknews.domain.use_case.auth.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginScreenViewModel @Inject constructor (
    private val loginUseCase: LoginUseCase,
    private val getAuthStateUseCaseFlow: GetAuthStateUseCaseFlow,
) : ViewModel() {

    private val _authState = getAuthStateUseCaseFlow()
    private val authState = _authState

    fun getAuthState() = authState

    init {
        login()
    }

    fun login() {
        viewModelScope.launch {
            loginUseCase()
        }
    }
}