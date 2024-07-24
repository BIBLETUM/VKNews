package com.example.vknews.data.model.comments

import com.google.gson.annotations.SerializedName

data class CommentsResponseDto(
    @SerializedName("response") val content: CommentsContentDto
)