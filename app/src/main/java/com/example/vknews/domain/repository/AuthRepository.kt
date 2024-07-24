package com.example.vknews.domain.repository

import com.example.vknews.data.model.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    fun getAuthFlow(): StateFlow<AuthState>

    suspend fun login()

}