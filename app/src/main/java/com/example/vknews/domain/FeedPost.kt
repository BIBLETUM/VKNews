package com.example.vknews.domain

import com.example.vknews.R

data class FeedPost (
    val id: Int,
    val communityName: String = "/dev/null",
    val date: String = "14:00",
    val postText: String = "ОООООООООООоооооооооооооооооооооооооооооооооочень длинный текст",
    val communityImageResId: Int = R.drawable.post_comunity_thumbnail,
    val postImageResIs: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 996),
        StatisticItem(StatisticType.SHARES, 7),
        StatisticItem(StatisticType.COMMENTS, 8),
        StatisticItem(StatisticType.LIKES, 27)
    ),
)