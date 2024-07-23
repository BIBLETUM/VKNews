package com.example.vknews.data.model.likes

import com.google.gson.annotations.SerializedName

data class LikesResponseDto(
    @SerializedName("response") val likes: LikesCountDto,
)