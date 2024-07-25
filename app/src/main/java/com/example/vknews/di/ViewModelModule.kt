package com.example.vknews.di

import androidx.lifecycle.ViewModel
import com.example.vknews.presentation.screen.login.LoginScreenViewModel
import com.example.vknews.presentation.screen.news_feed.NewsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(LoginScreenViewModel::class)
    @Binds
    fun bindAuthViewModel(viewModel: LoginScreenViewModel): ViewModel

}