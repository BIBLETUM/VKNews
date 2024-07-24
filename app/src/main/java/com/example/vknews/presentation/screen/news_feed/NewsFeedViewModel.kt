package com.example.vknews.presentation.screen.news_feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.use_case.news_feed.ChangeLikeStatusUseCase
import com.example.vknews.domain.use_case.news_feed.GetRecommendationsFlowUseCase
import com.example.vknews.domain.use_case.news_feed.IgnoreFeedPostUseCase
import com.example.vknews.domain.use_case.news_feed.LoadNextDataUseCase
import com.example.vknews.extentions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getRecommendationsFlowUseCase: GetRecommendationsFlowUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val ignoreFeedPostUseCase: IgnoreFeedPostUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("NewsFeedViewModel", "Exception caught")
    }


    private val recommendationsFlow = getRecommendationsFlowUseCase()

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
            loadNextDataUseCase()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }

    fun deleteItem(post: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            ignoreFeedPostUseCase(feedPost = post)
        }
    }

}