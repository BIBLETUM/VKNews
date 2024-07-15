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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknews.domain.FeedPost

@Composable
fun HomeScreen(paddingValues: PaddingValues, onCommentsClick: (FeedPost) -> Unit) {
    val viewModel: NewsFeedViewModel = viewModel()
    val newsFeedScreenState =
        viewModel.newsFeedScreenState.observeAsState(NewsFeedScreenState.Initial)

    when (val currentState = newsFeedScreenState.value) {
        is NewsFeedScreenState.Posts -> {
            FeedPosts(viewModel, paddingValues, currentState.postsList, onCommentsClick)
        }

        is NewsFeedScreenState.Initial -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    onCommentClick: (FeedPost) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = posts, key = { it.id }) { post ->
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
                        feedPost = post,
                        onShareItemClickListener = { newItem ->
                            viewModel.updatePost(post, newItem)
                        },
                        onViewItemClickListener = { newItem ->
                            viewModel.updatePost(post, newItem)
                        },
                        onCommentItemClickListener = {
                            onCommentClick(post)
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