package com.example.vknews.domain.repository

import com.example.vknews.domain.FeedPost
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getRecommendationsFlow(): StateFlow<List<FeedPost>>

    suspend fun loadNextData()

    suspend fun ignoreFeedPost(feedPost: FeedPost)

    suspend fun changeLikeStatus(feedPost: FeedPost)

}