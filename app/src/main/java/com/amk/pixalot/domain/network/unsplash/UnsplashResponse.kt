package com.amk.pixalot.domain.network.unsplash

import com.google.gson.annotations.SerializedName

data class UnsplashItem(
        @SerializedName("urls") val urls: UnsplashUrls = UnsplashUrls()
) {
    val previewUrl: String?
        get() = urls.previewUrl

    val fullscreenUrl: String?
        get() = urls.fullscreenUrl
}

data class UnsplashUrls(
        @SerializedName("full") val fullscreenUrl: String? = null,
        @SerializedName("thumb") val previewUrl: String? = null
)

data class UnsplashImageResponse(
        @SerializedName("results") val images: List<UnsplashItem> = listOf()
)
