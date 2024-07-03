package com.example.vknews.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: MainViewModel, paddingValues: PaddingValues) {
    val postsState = viewModel.posts.observeAsState(listOf())
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = postsState.value, key = { it.id }) { post ->
            val dismissState = rememberDismissState(DismissValue.Default)
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.deleteItem(post)
            }
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                background = {
                },
                dismissContent = {
                    PostCard(
                        postData = post,
                        onShareItemClickListener = { newItem ->
                            viewModel.updatePost(post, newItem)
                        },
                        onViewItemClickListener = { newItem ->
                            viewModel.updatePost(post, newItem)
                        },
                        onCommentItemClickListener = { newItem ->
                            viewModel.updatePost(post, newItem)
                        },
                        onLikeItemClickListener = { newItem ->
                            viewModel.updatePost(post, newItem)
                        },
                    )
                },
                directions = setOf(DismissDirection.EndToStart)
            )
        }
    }
}