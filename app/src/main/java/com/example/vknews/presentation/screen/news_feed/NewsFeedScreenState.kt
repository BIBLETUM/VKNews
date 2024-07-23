package com.example.vknews.presentation.screen.news_feed

import com.example.vknews.domain.FeedPost

sealed class NewsFeedScreenState {
    object Initial : NewsFeedScreenState()
    data class Posts(
        val postsList: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ) : NewsFeedScreenState()
}