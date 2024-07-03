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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vknews.presentation.navigation.AppNavGraph
import com.example.vknews.presentation.navigation.NavigationItem
import com.example.vknews.presentation.navigation.rememberNavigationState

@Composable
fun MainScreen(viewModel: MainViewModel) {
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
            val currentRoute = backStackEntry?.destination?.route
            NavigationBar(modifier = Modifier.shadow(10.dp)) {
                listNavItems.forEach { navigationItem ->
                    NavigationBarItem(
                        selected = navigationItem.screen.route == currentRoute,
                        icon = {
                            Icon(
                                imageVector =
                                if (navigationItem.screen.route == currentRoute) navigationItem.imageSelected
                                else navigationItem.imageUnSelected,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            navigationState.navigateTo(navigationItem.screen.route)
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
            homeScreenContent = {
                HomeScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues
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