package com.example.vknews.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vknews.R
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.StatisticItem
import com.example.vknews.domain.StatisticType

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onViewItemClickListener: (StatisticItem) -> Unit,
    onLikeItemClickListener: (StatisticItem) -> Unit,
    onShareItemClickListener: (StatisticItem) -> Unit,
    onCommentItemClickListener: (StatisticItem) -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        TopBar(feedPost.communityName, feedPost.date, feedPost.communityImageUrl)
        TextAndImage(feedPost.postText, feedPost.postImageUrl)
        BottomInformation(
            feedPost.statistics,
            onLikeItemClickListener = onLikeItemClickListener,
            onShareItemClickListener = onShareItemClickListener,
            onViewItemClickListener = onViewItemClickListener,
            onCommentItemClickListener = onCommentItemClickListener
        )
    }

}

@Composable
fun TopBar(communityName: String, date: String, communityImageUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = communityImageUrl,
            contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)

        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Row {
                Text(text = communityName, color = MaterialTheme.colorScheme.onPrimary)
            }
            Row {
                Text(text = date, color = MaterialTheme.colorScheme.onSecondary)
            }
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSecondary
        )

    }
}

@Composable
fun TextAndImage(text: String, postImageUrl: String?) {
    Text(
        text = text, modifier = Modifier
            .padding(8.dp)
    )
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        contentScale = ContentScale.FillWidth,
        model = postImageUrl,
        contentDescription = ""
    )
}

@Composable
fun BottomInformation(
    statistics: List<StatisticItem>,
    onViewItemClickListener: (StatisticItem) -> Unit,
    onLikeItemClickListener: (StatisticItem) -> Unit,
    onShareItemClickListener: (StatisticItem) -> Unit,
    onCommentItemClickListener: (StatisticItem) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                R.drawable.ic_views_count, viewsItem.count.toString()
            ) { onViewItemClickListener(viewsItem) }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val repostsItem = statistics.getItemByType(StatisticType.SHARES)
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                R.drawable.ic_share,
                repostsItem.count.toString()
            ) { onShareItemClickListener(repostsItem) }
            IconWithText(
                R.drawable.ic_comment,
                commentsItem.count.toString()
            ) { onCommentItemClickListener(commentsItem) }
            IconWithText(R.drawable.ic_like, likesItem.count.toString()) {
                onLikeItemClickListener(
                    likesItem
                )
            }
        }
    }
    Spacer(modifier = Modifier.padding(4.dp))
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException()
}

@Composable
fun IconWithText(
    iconResId: Int,
    text: String,
    onStatisticItemClickListener: () -> Unit,
) {
    Row(
        modifier = Modifier.clickable {
            onStatisticItemClickListener()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 10.dp),
            tint = MaterialTheme.colorScheme.onSecondary,
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = text, color = MaterialTheme.colorScheme.onSecondary)
    }

}