package com.amk.pixalot.domain.network.pixabay

import com.google.gson.annotations.SerializedName

data class PixabayItem(
        @SerializedName("largeImageURL") val fullscreenUrl: String? = null,
        @SerializedName("previewURL") val previewUrl: String? = null
)

data class PixabayImageResponse(
        @SerializedName("hits") val images: List<PixabayItem> = listOf()
)