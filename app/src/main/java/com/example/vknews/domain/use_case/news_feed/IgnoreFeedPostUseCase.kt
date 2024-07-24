package com.example.vknews.domain.use_case.news_feed

import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.repository.NewsFeedRepository
import javax.inject.Inject

class IgnoreFeedPostUseCase @Inject constructor(private val repository: NewsFeedRepository) {

    suspend operator fun invoke(feedPost: FeedPost){
        repository.ignoreFeedPost(feedPost)
    }

}