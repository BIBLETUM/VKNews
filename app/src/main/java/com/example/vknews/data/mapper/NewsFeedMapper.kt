package com.example.vknews.data.mapper

import com.example.vknews.data.model.feed_dto.NewsFeedResponseDto
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.StatisticItem
import com.example.vknews.domain.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToPost(responseDto: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        val posts = responseDto.newsFeedContent.posts
        val groups = responseDto.newsFeedContent.groups

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue
            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                communityName = group.name,
                date = mapTimeStampToDate(post.date * 1000),
                postText = post.text,
                communityImageUrl = group.photoUrl,
                postImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url
                    ?: "",
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, count = post.likes.count),
                    StatisticItem(type = StatisticType.VIEWS, count = post.views.count),
                    StatisticItem(type = StatisticType.COMMENTS, count = post.comments.count),
                    StatisticItem(type = StatisticType.SHARES, count = post.reposts.count),
                ),
                isLiked = post.likes.userLikes > 0,
            )
            result.add(feedPost)
        }

        return result.toList()
    }

    private fun mapTimeStampToDate(timeStamp: Long): String {
        val date = Date(timeStamp)
        return SimpleDateFormat("dd MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }

}