package com.example.vknews.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vknews.domain.FeedPost
import com.example.vknews.presentation.navigation.AppNavGraph
import com.example.vknews.presentation.navigation.NavigationItem
import com.example.vknews.presentation.navigation.rememberNavigationState

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    val listNavItems = listOf(
        NavigationItem.Home,
        NavigationItem.Favourite,
        NavigationItem.Profile
    )
    Scaffold(
        modifier = Modifier.padding(8.dp),
        bottomBar = {
            val backStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
            NavigationBar(modifier = Modifier.shadow(10.dp)) {
                listNavItems.forEach { navigationItem ->

                    val selected = backStackEntry?.destination?.hierarchy?.any {
                        it.route == navigationItem.screen.route
                    } ?: false

                    NavigationBarItem(
                        selected = selected,
                        icon = {
                            Icon(
                                imageVector =
                                if (selected) navigationItem.imageSelected
                                else navigationItem.imageUnSelected,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(navigationItem.screen.route)
                            }
                        },
                        label = { Text(text = stringResource(id = navigationItem.nameResId)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            feedPostScreenContent = {
                HomeScreen(
                    paddingValues = paddingValues,
                ) { feedPost ->
                    navigationState.navigateToComments(feedPost)
                }
            },
            commentsScreenContent = {
                CommentsScreen(
                    paddingValues = paddingValues,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    feedPost = it
                )
            },
            favouriteScreenContent = { ScreenText(text = "Favourite") },
            profileScreenContent = { ScreenText(text = "Profile") })
    }
}

@Composable
fun ScreenText(text: String) {
    var count by rememberSaveable {
        mutableIntStateOf(0)
    }
    Text(text = "$text Count $count", color = Color.Black, modifier = Modifier.clickable {
        count++
    })
}