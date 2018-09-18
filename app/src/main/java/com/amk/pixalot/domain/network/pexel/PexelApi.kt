package com.amk.pixalot.domain.network.pexel

import com.amk.pixalot.common.ApiConfig.PER_PAGE_CONFIG
import com.amk.pixalot.common.ApiConfig.SEARCH_QUERY
import com.amk.pixalot.domain.network.pexel.PexelConfig.PEXEL_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

object PexelConfig {
    const val PEXEL_URL = "https://api.pexels.com/v1/"
    const val PEXEL_KEY = "563492ad6f917000010000019cca4ee08df2465a9509eb6c308b569d"
}

interface PexelApi {

    @Headers("Authorization: $PEXEL_KEY")
    @GET("search/")
    fun getImages(
            @Query("query") query: String = SEARCH_QUERY,
            @Query("per_page") perPage: Int = PER_PAGE_CONFIG,
            @Query("page") page: Int = 1
    ): Observable<PexelImageResponse>
}
