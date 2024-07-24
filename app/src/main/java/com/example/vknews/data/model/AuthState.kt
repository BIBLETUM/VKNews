package com.example.vknews.data.model

import com.vk.id.AccessToken
import com.vk.id.VKIDAuthFail

sealed class AuthState {

    data class Authorized(val accessToken: AccessToken): AuthState()

    object UnAuthorized: AuthState()

    data class Fail (val fail: VKIDAuthFail): AuthState()

    object Initial: AuthState()

}