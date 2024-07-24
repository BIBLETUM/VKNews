package com.example.vknews.di

import android.content.Context
import com.example.vknews.data.AuthRepositoryImpl
import com.example.vknews.data.CommentsRepositoryImpl
import com.example.vknews.data.NewsFeedRepositoryImpl
import com.example.vknews.data.network.ApiFactory
import com.example.vknews.data.network.ApiService
import com.example.vknews.domain.repository.CommentsRepository
import com.example.vknews.domain.repository.NewsFeedRepository
import com.example.vknews.presentation.TokenManager
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindNewsFeedRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    @ApplicationScope
    @Binds
    fun bindCommentsRepository(impl: CommentsRepositoryImpl): CommentsRepository

    @ApplicationScope
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepositoryImpl

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @ApplicationScope
        @Provides
        fun provideTokenManager(
            context: Context
        ): TokenManager {
            return TokenManager(context)
        }
    }

}