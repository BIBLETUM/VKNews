package com.example.vknews.presentation.screen.comments

import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.PostComment

sealed class CommentsScreenState {
    object Initial: CommentsScreenState()
    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()
}