package com.example.vknews.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.HomeScreenNavGraph(
    feedPostScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable () -> Unit,
) {
    navigation(startDestination = Screen.NewsFeed.route, route = Screen.Home.route) {
        composable(Screen.NewsFeed.route) {
            feedPostScreenContent()
        }
        composable(Screen.Comments.route) {
            commentsScreenContent()
        }
    }
}
