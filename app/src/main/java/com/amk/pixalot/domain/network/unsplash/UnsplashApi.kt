package com.amk.pixalot.domain.network.unsplash

import com.amk.pixalot.common.ApiConfig.PER_PAGE_CONFIG
import com.amk.pixalot.common.ApiConfig.SEARCH_QUERY
import com.amk.pixalot.domain.network.unsplash.UnsplashConfig.UNSPLASH_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

object UnsplashConfig {
    const val UNSPLASH_URL = "https://api.unsplash.com/"
    const val UNSPLASH_KEY = "ef1dc9cf6c08a2442b0e92b58dd5074af6a629becfa34b30a4af4cd5989f5f63"
}

interface UnsplashApi {
    @GET("search/photos")
    fun getImages(
            @Query("client_id") key: String = UNSPLASH_KEY,
            @Query("query") query: String = SEARCH_QUERY,
            @Query("per_page") perPage: Int = PER_PAGE_CONFIG,
            @Query("page") page: Int = 1
    ): Observable<UnsplashImageResponse>
}
