package com.example.vknews.domain.use_case.news_feed

import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetRecommendationsFlowUseCase @Inject constructor (private val repository: NewsFeedRepository) {

    operator fun invoke(): StateFlow<List<FeedPost>> {
        return repository.getRecommendationsFlow()
    }

}