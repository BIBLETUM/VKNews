package com.example.vknews.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vk.id.VKID

class LoginViewModelFactory(
    private val vkid: VKID,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginScreenViewModel(vkid) as T
    }
}