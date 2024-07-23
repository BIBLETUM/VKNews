package com.example.vknews.presentation.screen.news_feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.NewsFeedRepository
import com.example.vknews.domain.FeedPost
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val initialState = NewsFeedScreenState.Initial

    private val _newsFeedScreenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val newsFeedScreenState: LiveData<NewsFeedScreenState> = _newsFeedScreenState

    private val repository = NewsFeedRepository(application)

    init {
        _newsFeedScreenState.value = NewsFeedScreenState.Loading
        loadNewsFeed()
    }

    private fun loadNewsFeed() {
        viewModelScope.launch {
            val feedPost = repository.loadRecommendations()
            _newsFeedScreenState.value = NewsFeedScreenState.Posts(feedPost)
        }
    }

    fun loadNextNewsFeed() {
        _newsFeedScreenState.value = NewsFeedScreenState.Posts(
            repository.feedPosts,
            true
        )
        loadNewsFeed()
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
            _newsFeedScreenState.value = NewsFeedScreenState.Posts(repository.feedPosts)
        }
    }

    fun deleteItem(post: FeedPost) {
        val currentState = _newsFeedScreenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        viewModelScope.launch {
            repository.ignoreFeedPost(feedPost = post)
            _newsFeedScreenState.value = NewsFeedScreenState.Posts(repository.feedPosts)
        }
    }

}