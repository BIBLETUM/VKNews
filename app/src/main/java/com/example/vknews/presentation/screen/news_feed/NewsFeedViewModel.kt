package com.example.vknews.presentation.screen.news_feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.NewsFeedRepository
import com.example.vknews.domain.FeedPost
import com.example.vknews.extentions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)

    private val exceptionHandler = CoroutineExceptionHandler{ _, _ ->
        Log.d("NewsFeedViewModel", "Exception caught")
    }

    private val recommendationsFlow = repository.recommendationsFlow

    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState>()

    val newsFeedScreenState = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(postsList = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun loadNextNewsFeed() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsFeedScreenState.Posts(
                    postsList = recommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
            repository.loadNextData()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            repository.changeLikeStatus(feedPost)
        }
    }

    fun deleteItem(post: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            repository.ignoreFeedPost(feedPost = post)
        }
    }

}