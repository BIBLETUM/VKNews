package com.example.vknews.domain.use_case.comments

import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.PostComment
import com.example.vknews.domain.repository.CommentsRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCommentsFlowUseCase @Inject constructor (private val repository: CommentsRepository) {

    operator fun invoke(feedPost: FeedPost): StateFlow<List<PostComment>> {
        return repository.commentsFlow(feedPost)
    }

}