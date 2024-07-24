package com.example.vknews.domain.repository

import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.PostComment
import kotlinx.coroutines.flow.StateFlow

interface CommentsRepository {

    fun commentsFlow(feedPost: FeedPost): StateFlow<List<PostComment>>

}