package com.example.vknews.data

import android.app.Application
import com.example.vknews.data.mapper.NewsFeedMapper
import com.example.vknews.data.network.ApiFactory
import com.example.vknews.data.network.ApiService
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.StatisticItem
import com.example.vknews.domain.StatisticType
import com.example.vknews.domain.repository.NewsFeedRepository
import com.example.vknews.extentions.mergeWith
import com.example.vknews.presentation.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val apiService: ApiService,
    private val mapper: NewsFeedMapper
) : NewsFeedRepository {

    private val scope = CoroutineScope(Dispatchers.Default)
    private val nextDataNeeded = MutableSharedFlow<Unit>(replay = 1)

    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()
    private val loadedListFlow = flow {
        nextDataNeeded.emit(Unit)
        nextDataNeeded.collect {
            val startFrom = nextFrom

            if (startFrom == null && _feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.loadFeedPosts(getAccessToken())
            } else {
                apiService.loadFeedPosts(getAccessToken(), startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPost(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_TIMEOUT)
        true
    }

    private val token = tokenManager.getToken()

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val recommendationsFlow: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override fun getRecommendationsFlow() = recommendationsFlow

    override suspend fun loadNextData() {
        nextDataNeeded.emit(Unit)
    }

    override suspend fun ignoreFeedPost(feedPost: FeedPost) {
        apiService.ignoreItem(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id,
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id,
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id,
            )
        }
        val likesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, count = likesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val index = _feedPosts.indexOf(feedPost)
        _feedPosts[index] = newPost
        refreshedListFlow.emit(feedPosts)
    }

    private fun getAccessToken(): String {
        return token ?: throw IllegalStateException("Token is null")
    }

    companion object {
        private const val RETRY_TIMEOUT = 3000L
    }
}