package com.example.vknews.domain

import androidx.compose.runtime.Immutable

@Immutable
data class PostComment(
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String,
    val commentText: String,
    val publicationTime: String,
)