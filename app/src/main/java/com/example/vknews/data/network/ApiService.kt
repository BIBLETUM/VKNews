package com.example.vknews.data.network

import com.example.vknews.data.model.comments.CommentsResponseDto
import com.example.vknews.data.model.feed_dto.NewsFeedResponseDto
import com.example.vknews.data.model.likes.LikesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.199&filters=post")
    suspend fun loadFeedPosts(
        @Query("access_token") token: String,
    ): NewsFeedResponseDto

    @GET("newsfeed.getRecommended?v=5.199&filters=post")
    suspend fun loadFeedPosts(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String,
    ): NewsFeedResponseDto

    @GET("newsfeed.ignoreItem?v=5.199&type=wall")
    suspend fun ignoreItem(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long,
    )

    @GET("likes.add?v=5.199&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long,
    ): LikesResponseDto

    @GET("likes.delete?v=5.199&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long,
    ): LikesResponseDto

    @GET("wall.getComments?v=5.131&extended=1&fields=photo_100")
    suspend fun getComments(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("post_id") postId: Long,
    ): CommentsResponseDto

}