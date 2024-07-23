package com.example.vknews.data.model.feed_dto

import com.google.gson.annotations.SerializedName

data class GroupDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("photo_200") val photoUrl: String
)