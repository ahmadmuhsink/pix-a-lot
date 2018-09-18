package com.amk.pixalot.domain.network.pexel

import com.google.gson.annotations.SerializedName

data class PexelItem(
        @SerializedName("src") val urls: PexelUrls = PexelUrls()
) {
    val previewUrl: String?
        get() = urls.previewUrl

    val fullscreenUrl: String?
        get() = urls.fullscreenUrl
}

data class PexelUrls(
        @SerializedName("original") val fullscreenUrl: String? = null,
        @SerializedName("small") val previewUrl: String? = null
)

data class PexelImageResponse(
        @SerializedName("photos") val images: List<PexelItem> = listOf()
)