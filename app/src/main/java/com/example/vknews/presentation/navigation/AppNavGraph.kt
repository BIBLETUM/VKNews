package com.example.vknews.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vknews.domain.FeedPost

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    feedPostScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (feedPost: FeedPost) -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        HomeScreenNavGraph(
            feedPostScreenContent = feedPostScreenContent,
            commentsScreenContent = commentsScreenContent
        )
        composable(Screen.Favourite.route) {
            favouriteScreenContent()
        }
        composable(Screen.Profile.route) {
            profileScreenContent()
        }
    }
}