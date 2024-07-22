package com.example.vknews.presentation.screen.news_feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vknews.data.mapper.NewsFeedMapper
import com.example.vknews.data.network.ApiFactory
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.StatisticItem
import com.example.vknews.presentation.TokenManager
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val initialState = NewsFeedScreenState.Initial

    private val _newsFeedScreenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val newsFeedScreenState: LiveData<NewsFeedScreenState> = _newsFeedScreenState

    private val mapper = NewsFeedMapper()

    init {
        loadNewsFeed()
    }

    private fun loadNewsFeed() {
        viewModelScope.launch {
            val token = TokenManager(getApplication()).getToken() ?: return@launch
            val response = ApiFactory.apiService.loadFeedPosts(token)
            val feedPost = mapper.mapResponseToPost(response)
            _newsFeedScreenState.value = NewsFeedScreenState.Posts(feedPost)
        }
    }

    fun updatePost(post: FeedPost, newItem: StatisticItem) {
        val currentState = _newsFeedScreenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val newList = currentState.postsList.toMutableList()
        newList.replaceAll {
            if (it == post) {
                updateCount(it, newItem)
            } else {
                it
            }
        }
        _newsFeedScreenState.value = NewsFeedScreenState.Posts(newList)
    }

    private fun updateCount(post: FeedPost, newItem: StatisticItem): FeedPost {
        val oldStatistics = post.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == newItem.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        return post.copy(statistics = newStatistics)
    }

    fun deleteItem(post: FeedPost) {
        val currentState = _newsFeedScreenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val list = currentState.postsList.toMutableList()
        list.remove(post)
        _newsFeedScreenState.value = NewsFeedScreenState.Posts(list)
    }

}