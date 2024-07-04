package com.example.vknews.presentation

import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.PostComment

sealed class CommentsScreenState {
    object Initial: CommentsScreenState()
    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}