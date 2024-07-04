package com.example.vknews.presentation

import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.PostComment

sealed class NewsFeedScreenState {
    object Initial: NewsFeedScreenState()
    data class Posts(val postsList: List<FeedPost>) : NewsFeedScreenState()
}