package com.example.vknews.data

import android.app.Application
import com.example.vknews.data.mapper.NewsFeedMapper
import com.example.vknews.data.network.ApiFactory
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.StatisticItem
import com.example.vknews.domain.StatisticType
import com.example.vknews.presentation.TokenManager

class NewsFeedRepository(application: Application) {

    private val tokenManager = TokenManager(application)
    private val token = tokenManager.getToken()

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    suspend fun loadRecommendations(): List<FeedPost> {
        val startFrom = nextFrom

        if (startFrom == null && _feedPosts.isNotEmpty()) return feedPosts

        val response = if (startFrom == null) {
            apiService.loadFeedPosts(getAccessToken())
        } else {
            apiService.loadFeedPosts(getAccessToken(), startFrom)
        }
        nextFrom = response.newsFeedContent.nextFrom
        val posts = mapper.mapResponseToPost(response)
        _feedPosts.addAll(posts)
        return feedPosts
    }

    suspend fun ignoreFeedPost(feedPost: FeedPost) {
        apiService.ignoreItem(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id,
        )
        _feedPosts.remove(feedPost)
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
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
    }

    private fun getAccessToken(): String {
        return token ?: throw IllegalStateException("Token is null")
    }

}