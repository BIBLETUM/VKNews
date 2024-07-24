package com.example.vknews.presentation.screen.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.vknews.data.AuthRepository
import com.vk.id.VKID
import kotlinx.coroutines.flow.asStateFlow

class LoginScreenViewModel(
    vkid: VKID,
    application: Application
) : AndroidViewModel(application) {

    private val repository = AuthRepository(vkid, application)

    private val _authState = repository.authFlow
    private val authState = _authState.asStateFlow()

    fun getAuthState() = authState

    init {
        login()
    }

    fun login() {
        repository.login()
    }
}