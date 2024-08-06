package com.example.vknews.data

import android.util.Log
import com.example.vknews.data.model.AuthState
import com.example.vknews.domain.repository.AuthRepository
import com.example.vknews.presentation.TokenManager
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
) : AuthRepository {

    private val vkid = VKID.instance

    val authFlow = MutableStateFlow<AuthState>(AuthState.Initial)

    override fun getAuthFlow() = authFlow.asStateFlow()

    private val vkAuthCallback = object : VKIDAuthCallback {
        override fun onAuth(accessToken: AccessToken) {
            Log.d("TOKEN", accessToken.token)

            tokenManager.saveToken(accessToken.token)

            authFlow.value = AuthState.Authorized(accessToken)
        }

        override fun onFail(fail: VKIDAuthFail) {
            authFlow.value = AuthState.Fail(fail)
        }
    }

    override suspend fun login() {
        vkid.authorize(callback = vkAuthCallback, params = initializer.build())
    }

    private val initializer = VKIDAuthParams.Builder().apply {
        scopes = setOf("email", "wall", "groups", "photos", "friends")
        build()
    }
}