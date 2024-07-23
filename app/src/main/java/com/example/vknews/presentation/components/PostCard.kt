package com.example.vknews.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vknews.R
import com.example.vknews.domain.FeedPost
import com.example.vknews.domain.StatisticItem
import com.example.vknews.domain.StatisticType
import com.example.vknews.presentation.theme.DarkRed
import java.util.Locale

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onLikeItemClickListener: (StatisticItem) -> Unit,
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
            statistics = feedPost.statistics,
            isFavourite = feedPost.isLiked,
            onLikeItemClickListener = onLikeItemClickListener,
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
    isFavourite: Boolean,
    onLikeItemClickListener: (StatisticItem) -> Unit,
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
                iconResId = R.drawable.ic_views_count,
                text = formatStatisticCountToString(viewsItem.count)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val repostsItem = statistics.getItemByType(StatisticType.SHARES)
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = R.drawable.ic_share,
                text = formatStatisticCountToString(repostsItem.count)
            )
            IconWithText(
                iconResId = R.drawable.ic_comment,
                text = formatStatisticCountToString(commentsItem.count)
            ) { onCommentItemClickListener(commentsItem) }
            IconWithText(
                iconResId = if (isFavourite) R.drawable.ic_like_set else R.drawable.ic_like,
                text = formatStatisticCountToString(likesItem.count),
                iconTint = if (isFavourite) DarkRed else MaterialTheme.colorScheme.onSecondary,
            ) { onLikeItemClickListener(likesItem) }
        }
    }
    Spacer(modifier = Modifier.padding(4.dp))
}

private fun formatStatisticCountToString(count: Int): String {
    return when (count) {
        in 1000..99_999 -> {
            String.format(Locale.getDefault(), "%.1fK", (count / 1000f))
        }

        in 100_000..Int.MAX_VALUE -> {
            String.format("sK", (count / 1000))
        }

        else -> {
            count.toString()
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException()
}

@Composable
fun IconWithText(
    iconResId: Int,
    text: String,
    iconTint: Color = MaterialTheme.colorScheme.onSecondary,
    onStatisticItemClickListener: (() -> Unit)? = null,
) {
    val modifier = if (onStatisticItemClickListener != null) {
        Modifier.clickable {
            onStatisticItemClickListener()
        }
    } else {
        Modifier
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 10.dp)
                .size(20.dp),
            tint = iconTint,
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = text, color = MaterialTheme.colorScheme.onSecondary)
    }

}