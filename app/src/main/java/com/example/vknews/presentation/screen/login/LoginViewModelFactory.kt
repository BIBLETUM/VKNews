package com.example.vknews.presentation.screen.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vk.id.VKID

class LoginViewModelFactory(
    private val vkid: VKID,
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginScreenViewModel(vkid, application) as T
    }
}