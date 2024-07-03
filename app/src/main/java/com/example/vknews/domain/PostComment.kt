package com.example.vknews.domain

import com.example.vknews.R

data class PostComment (
    val id: Int,
    val authorName: String = "Aboba",
    val authorAvatarResId: Int = R.drawable.comment_author_avatar,
    val commentText: String = "Long comment for post",
    val publicationDate: String = "21.11.2001",
)