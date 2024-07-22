package com.example.vknews.presentation.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import com.vk.id.auth.VKIDAuthUiParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    vkid: VKID
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState = _authState.asStateFlow()

    private val vkAuthCallback = object : VKIDAuthCallback {
        override fun onAuth(accessToken: AccessToken) {
            Log.d("TOKEN", accessToken.token)
            _authState.value = AuthState.Authorized(accessToken)
        }

        override fun onFail(fail: VKIDAuthFail) {
            _authState.value = AuthState.Fail(fail)
        }
    }

    private val initializer = VKIDAuthParams.Builder().apply {
        scopes = setOf("email", "wall", "groups", "photos", "friends")
        build()
    }

    init {
        viewModelScope.launch {
            vkid.authorize(callback = vkAuthCallback, params = initializer.build())
        }
    }

    fun login(vkid: VKID) {
        viewModelScope.launch {
            vkid.authorize(callback = vkAuthCallback, params = initializer.build())
        }
    }
}