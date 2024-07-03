package com.example.vknews.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vknews.R

sealed class NavigationItem(
    val screen: Screen,
    val nameResId: Int,
    val imageSelected: ImageVector,
    val imageUnSelected: ImageVector
) {
    object Home : NavigationItem(
        screen = Screen.NewsFeed,
        nameResId = R.string.navigation_item_name_main,
        imageSelected = Icons.Filled.Home,
        imageUnSelected = Icons.Outlined.Home
    )

    object Favourite : NavigationItem(
        screen = Screen.Favourite,
        nameResId = R.string.navigation_item_name_favourite,
        imageSelected = Icons.Filled.Favorite,
        imageUnSelected = Icons.Outlined.Favorite
    )

    object Profile : NavigationItem(
        screen = Screen.Profile,
        nameResId = R.string.navigation_item_name_profile,
        imageSelected = Icons.Filled.Person,
        imageUnSelected = Icons.Outlined.Person
    )
}