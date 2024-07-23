package com.example.vknews.presentation.screen.news_feed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknews.domain.FeedPost
import com.example.vknews.presentation.components.PostCard
import com.example.vknews.presentation.theme.Blue

@Composable
fun NewsFeedScreen(paddingValues: PaddingValues, onCommentsClick: (FeedPost) -> Unit) {
    val viewModel: NewsFeedViewModel = viewModel()
    val newsFeedScreenState =
        viewModel.newsFeedScreenState.observeAsState(NewsFeedScreenState.Initial)

    when (val currentState = newsFeedScreenState.value) {
        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                viewModel = viewModel,
                paddingValues = paddingValues,
                posts = currentState.postsList,
                nextDataIsLoading = currentState.nextDataIsLoading,
                onCommentClick = onCommentsClick
            )
        }

        is NewsFeedScreenState.Initial -> {}

        is NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Blue)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    nextDataIsLoading: Boolean,
    onCommentClick: (FeedPost) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = posts, key = { it.id }) { post ->
            val dismissState = rememberSwipeToDismissBoxState()
            if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                viewModel.deleteItem(post)
            }
            SwipeToDismissBox(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                backgroundContent = {
                },
                content = {
                    PostCard(
                        feedPost = post,
                        onCommentItemClickListener = {
                            onCommentClick(post)
                        },
                        onLikeItemClickListener = { _ ->
                            viewModel.changeLikeStatus(feedPost = post)
                        },
                    )
                },
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = false
            )
        }
        item {
            if (nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Blue)
                }
            } else {
                SideEffect {
                    viewModel.loadNextNewsFeed()
                }
            }
        }
    }
}