package com.amk.pixalot.domain.network.pixabay

import com.amk.pixalot.common.ApiConfig.PER_PAGE_CONFIG
import com.amk.pixalot.common.ApiConfig.SEARCH_QUERY
import com.amk.pixalot.domain.network.pixabay.PixabayConfig.PIXABAY_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

object PixabayConfig {
    const val PIXABAY_URL = "https://pixabay.com/api/"
    const val PIXABAY_KEY = "10112626-3f8ae434913e13d9f9ec26516"
}

interface PixabayApi {

    @GET(".")
    fun getImages(
            @Query("key") key: String = PIXABAY_KEY,
            @Query("q") query: String = SEARCH_QUERY,
            @Query("per_page") perPage: Int = PER_PAGE_CONFIG,
            @Query("page") page: Int = 1
    ): Observable<PixabayImageResponse>
}
