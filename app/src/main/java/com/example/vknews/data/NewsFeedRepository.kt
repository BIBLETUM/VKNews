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
    val feedPost: List<FeedPost>
        get() = _feedPosts.toList()

    suspend fun loadRecommendations(): List<FeedPost> {
        val response = apiService.loadFeedPosts(getAccessToken())
        val feedPosts = mapper.mapResponseToPost(response)
        _feedPosts.addAll(feedPosts)
        return feedPosts
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

    suspend fun deleteLike(feedPost: FeedPost) {
        val response = apiService.deleteLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id,
        )
        val likesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, count = likesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = false)
        val index = _feedPosts.indexOf(feedPost)
        _feedPosts[index] = newPost
    }

    private fun getAccessToken(): String {
        return token ?: throw IllegalStateException("Token is null")
    }

}