package com.example.vknews.data.model.comments

import com.google.gson.annotations.SerializedName

data class CommentsContentDto(
    @SerializedName("items") val comment: List<CommentDto>,
    @SerializedName("profiles") val profiles: List<ProfileDto>,
)
