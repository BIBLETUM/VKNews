package com.example.vknews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.PostComment

class CommentsViewModel: ViewModel() {

    private val _commentsScreenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val commentsScreenState: LiveData<CommentsScreenState> = _commentsScreenState

    init {
        loadComments(FeedPost(id = 0))
    }

    fun loadComments(feedPost: FeedPost) {
        val commentsList = mutableListOf<PostComment>().apply {
            repeat(10) {
                add(
                    PostComment(id = it)
                )
            }
        }

        _commentsScreenState.value = CommentsScreenState.Comments(feedPost, commentsList)
    }
}