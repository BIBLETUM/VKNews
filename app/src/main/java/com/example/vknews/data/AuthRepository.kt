package com.example.vknews.data

import android.app.Application
import android.util.Log
import com.example.vknews.data.model.AuthState
import com.example.vknews.presentation.TokenManager
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthRepository(
    private val vkid: VKID,
    private val application: Application
) {

    val authFlow = MutableStateFlow<AuthState>(AuthState.Initial)

    private val scope = CoroutineScope(Dispatchers.Default)

    private val vkAuthCallback = object : VKIDAuthCallback {
        override fun onAuth(accessToken: AccessToken) {
            Log.d("TOKEN", accessToken.token)

            val manager = TokenManager(application)
            manager.saveToken(accessToken.token)

            authFlow.value = AuthState.Authorized(accessToken)
        }

        override fun onFail(fail: VKIDAuthFail) {
            authFlow.value = AuthState.Fail(fail)
        }
    }

    fun login() {
        scope.launch {
            vkid.authorize(callback = vkAuthCallback, params = initializer.build())
        }
    }

    private val initializer = VKIDAuthParams.Builder().apply {
        scopes = setOf("email", "wall", "groups", "photos", "friends")
        build()
    }
}