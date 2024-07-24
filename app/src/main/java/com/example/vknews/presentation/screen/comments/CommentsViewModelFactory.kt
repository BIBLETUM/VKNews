package com.example.vknews.presentation.screen.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vknews.domain.FeedPost

class CommentsViewModelFactory(
    private val feedPost: FeedPost,
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost, application) as T
    }
}