package com.example.vknews.presentation.screen.comments

import androidx.lifecycle.ViewModel
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.use_case.comments.GetCommentsFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentsViewModel @Inject constructor (
    private val feedPost: FeedPost,
    private val getCommentsFlowUseCase: GetCommentsFlowUseCase
) : ViewModel() {

    private val _screenState = getCommentsFlowUseCase(feedPost)
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