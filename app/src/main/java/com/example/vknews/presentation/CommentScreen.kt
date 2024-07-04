package com.example.vknews.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.PostComment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    onBackClick: () -> Unit,
) {
    val viewModel: CommentsViewModel = viewModel()
    val screenState =
        viewModel.commentsScreenState.observeAsState(initial = CommentsScreenState.Initial)
    val currentState = screenState.value
    if (currentState is CommentsScreenState.Comments) {
        Scaffold(topBar = {
            TopAppBar(modifier = Modifier.shadow(8.dp), title = {
                Text(text = "Comments for FeedPost id: ${currentState.feedPost.id}")
            }, navigationIcon = {
                IconButton(onClick = { onBackClick() }, content = {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                })
            })
        }) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 72.dp
                )
            ) {
                items(items = currentState.comments, key = { it.id }) { comment ->
                    Comment(comment)
                }
            }
        }
    }

}

@Composable
private fun Comment(comment: PostComment = PostComment(id = 0)) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(30.dp),
            painter = painterResource(id = comment.authorAvatarResId),
            contentDescription = null,

            )
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = comment.authorName + comment.id.toString(),
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