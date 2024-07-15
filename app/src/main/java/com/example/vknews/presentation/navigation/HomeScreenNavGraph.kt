package com.example.vknews.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.vknews.domain.FeedPost
import com.example.vknews.presentation.navigation.Screen.Companion.KEY_FEED_POST_ID
import com.google.gson.Gson

fun NavGraphBuilder.HomeScreenNavGraph(
    feedPostScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (feedPost: FeedPost) -> Unit,
) {
    navigation(startDestination = Screen.NewsFeed.route, route = Screen.Home.route) {
        composable(Screen.NewsFeed.route) {
            feedPostScreenContent()
        }
        composable(route = Screen.Comments.route,
            arguments = listOf(
                navArgument(name = KEY_FEED_POST_ID) {
                    type = FeedPost.NavType
                }
            )
        ) {
            val feedPost = it.arguments?.getParcelable<FeedPost>(KEY_FEED_POST_ID) ?: throw Exception("Null arguments for feedPost")
            commentsScreenContent(feedPost)
        }
    }
}
