package com.example.vknews.data.model

import com.google.gson.annotations.SerializedName

data class AttachmentDto(
    @SerializedName("photos") val photo: PhotoDto
)
