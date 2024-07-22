package com.example.vknews.data.network

import com.example.vknews.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.get?v=5.199&filters=post")
    suspend fun loadFeedPosts(
        @Query("access_token") token: String,
    ): NewsFeedResponseDto

}