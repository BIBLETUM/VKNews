package com.example.vknews.data.model.feed_dto

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("sizes") val photoUrls: List<PhotoUrlDto>
)
