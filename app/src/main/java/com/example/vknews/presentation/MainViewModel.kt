package com.example.vknews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknews.domain.PostData
import com.example.vknews.domain.StatisticItem

class MainViewModel : ViewModel() {

    private val initialList = mutableListOf<PostData>().apply {
        repeat(500) {
            add(
                PostData(
                    id = it,
                    communityName = "Community name: $it",
                    date = "${(0..24).random()}:${(0..60).random()}",
                    postText = "Text: $it"
                )
            )
        }
    }

    private val _posts = MutableLiveData(initialList.toList())
    val posts: LiveData<List<PostData>> = _posts

    fun updatePost(post: PostData, newItem: StatisticItem) {
        val list = _posts.value?.toMutableList() ?: mutableListOf()
        list.replaceAll {
            if (it == post) {
                updateCount(it, newItem)
            } else {
                it
            }
        }
        _posts.value = list
    }

    private fun updateCount(post: PostData, newItem: StatisticItem): PostData {
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

    fun deleteItem(post: PostData){
        val list = _posts.value?.toMutableList() ?: mutableListOf()
        list.remove(post)
        _posts.value = list
    }

}