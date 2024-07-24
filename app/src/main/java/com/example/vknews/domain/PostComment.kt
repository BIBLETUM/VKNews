package com.example.vknews.domain

import com.example.vknews.R

data class PostComment (
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String,
    val commentText: String,
    val publicationTime: String,
)