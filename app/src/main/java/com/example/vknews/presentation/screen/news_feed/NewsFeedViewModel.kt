package com.example.vknews.presentation.screen.news_feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.StatisticItem

class NewsFeedViewModel : ViewModel() {

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(500) {
            add(
                FeedPost(
                    id = it,
                    communityName = "Community name: $it",
                    date = "${(0..24).random()}:${(0..60).random()}",
                    postText = "Text: $it"
                )
            )
        }
    }

    private val initialState = NewsFeedScreenState.Posts(initialList.toList())

    private val _newsFeedScreenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val newsFeedScreenState: LiveData<NewsFeedScreenState> = _newsFeedScreenState

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