package com.example.vknews.presentation.screen.comments

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.PostComment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    paddingValues: PaddingValues,
    onBackPressed: () -> Unit,
    feedPost: FeedPost,
) {
    val viewModel: CommentsViewModel = viewModel(
        factory = CommentsViewModelFactory(
            feedPost,
            LocalContext.current.applicationContext as Application
        )
    )
    val screenState = viewModel.screenState.observeAsState(CommentsScreenState.Initial)
    val currentState = screenState.value

    if (currentState is CommentsScreenState.Comments) {
        Scaffold(
            modifier = Modifier.padding(paddingValues),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Comments for FeedPost Id: ${currentState.feedPost.id}")
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { innerPaddingValues ->
            LazyColumn(
                modifier = Modifier.padding(innerPaddingValues),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                )
            ) {
                items(
                    items = currentState.comments,
                    key = { it.id }
                ) { comment ->
                    CommentItem(comment = comment)
                }
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: PostComment
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier.clip(RoundedCornerShape(50)),
            model = comment.authorAvatarUrl,
            contentDescription = null
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = comment.authorName,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp
            )
            Text(
                text = comment.commentText,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp
            )
            Text(
                text = comment.publicationTime,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp
            )
        }
    }
}