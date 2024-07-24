package com.example.vknews.presentation.screen.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.vknews.data.NewsFeedRepository
import com.example.vknews.domain.FeedPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)

    private val _screenState = repository.commentsFlow(feedPost)
        .filter { it.isNotEmpty() }
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            ) as CommentsScreenState
        }

    private val screenState: Flow<CommentsScreenState> = _screenState
    fun getScreenState() = screenState
}