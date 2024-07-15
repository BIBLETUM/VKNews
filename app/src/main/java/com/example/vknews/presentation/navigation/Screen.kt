package com.example.vknews.presentation.navigation

import android.net.Uri
import com.example.vknews.domain.FeedPost
import com.google.gson.Gson

sealed class Screen(
    val route: String,
) {
    object Home: Screen(ROUTE_HOME)
    object NewsFeed : Screen(ROUTE_NEWS_FEED)
    object Comments : Screen(ROUTE_COMMENTS) {

        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: FeedPost): String {
            val feedPostJson = Gson().toJson(feedPost)
            return ("$ROUTE_FOR_ARGS/${feedPostJson.encode()}")
        }
    }
    object Favourite : Screen(ROUTE_FAVOURITE)
    object Profile : Screen(ROUTE_PROFILE)

    companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_NEWS_FEED = "news_feed"

        const val KEY_FEED_POST_ID = "feed_post_id"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST_ID}"

        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
    }
}
fun String.encode(): String{
    return Uri.encode(this)
}
