package com.example.vknews.domain.use_case.auth

import com.example.vknews.data.model.AuthState
import com.example.vknews.domain.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthStateUseCaseFlow @Inject constructor (private val repository: AuthRepository) {

    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthFlow()
    }

}