package com.example.vknews.data

import android.app.Application
import com.example.vknews.data.mapper.NewsFeedMapper
import com.example.vknews.data.network.ApiService
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.PostComment
import com.example.vknews.domain.repository.CommentsRepository
import com.example.vknews.presentation.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val mapper: NewsFeedMapper,
    private val apiService: ApiService
) : CommentsRepository {
    private val token = tokenManager.getToken()

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun commentsFlow(feedPost: FeedPost): StateFlow<List<PostComment>> = flow {
        val comments = apiService.getComments(
            getAccessToken(),
            feedPost.communityId,
            feedPost.id
        )
        emit(mapper.mapResponseToComments(comments))
    }.retry {
        delay(RETRY_TIMEOUT)
        true
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    private fun getAccessToken(): String {
        return token ?: throw IllegalStateException("Token is null")
    }

    companion object {
        private const val RETRY_TIMEOUT = 3000L
    }
}