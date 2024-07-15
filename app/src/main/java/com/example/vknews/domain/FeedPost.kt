package com.example.vknews.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.example.vknews.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
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
) : Parcelable {
    companion object {
        val NavType: NavType<FeedPost> = object : NavType<FeedPost>(false) {
            override fun get(bundle: Bundle, key: String): FeedPost? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson(value, FeedPost::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key, value)
            }

        }
    }
}