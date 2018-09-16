package com.amk.pixalot.di

import com.amk.pixalot.domain.network.pixabay.PixabayApi
import com.amk.pixalot.domain.network.pixabay.PixabayConfig.PIXABAY_URL
import com.amk.pixalot.domain.network.unsplash.UnsplashApi
import com.amk.pixalot.domain.network.unsplash.UnsplashConfig.UNSPLASH_URL
import com.amk.pixalot.domain.repo.PixabayRepository
import com.amk.pixalot.domain.repo.UnsplashRepository
import com.amk.pixalot.domain.store.PhotoListStore
import com.amk.pixalot.domain.workflow.PhotoListWorkflow
import com.amk.pixalot.screen.home.photolist.PhotoListContract
import com.amk.pixalot.screen.home.photolist.PhotoListPresenter
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createOkHttpClient() }
    single { createWebService<PixabayApi>(get(), PIXABAY_URL) }
    single { createWebService<UnsplashApi>(get(), UNSPLASH_URL) }
}

val sourceModule = module {
    single { PixabayRepository(get()) }
    single { UnsplashRepository(get()) }
}

val photoListModule = module {
    factory { PhotoListStore() }
    factory { PhotoListWorkflow(get(), get(), get()) }
    factory<PhotoListContract.Presenter> { PhotoListPresenter(get()) }
}

private fun createOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(5L, TimeUnit.SECONDS)
        .readTimeout(5L, TimeUnit.SECONDS)
        .build()

private inline fun <reified T> createWebService(
        okHttpClient: OkHttpClient,
        url: String
): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}
