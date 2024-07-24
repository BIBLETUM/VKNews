package com.example.vknews.domain.use_case.auth

import com.example.vknews.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor (private val repository: AuthRepository) {

    suspend operator fun invoke() {
        repository.login()
    }

}