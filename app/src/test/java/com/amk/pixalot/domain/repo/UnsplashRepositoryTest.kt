package com.amk.pixalot.domain.repo

import com.amk.pixalot.common.RxImmediateSchedulerRule
import com.amk.pixalot.domain.network.unsplash.UnsplashApi
import com.amk.pixalot.domain.network.unsplash.UnsplashImageResponse
import com.amk.pixalot.domain.network.unsplash.UnsplashItem
import com.amk.pixalot.domain.network.unsplash.UnsplashUrls
import com.amk.pixalot.domain.viewmodel.ImageItem
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UnsplashRepositoryTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var api: UnsplashApi

    private lateinit var repo: UnsplashRepository

    @Before
    fun setup() {
        repo = UnsplashRepository(api)
    }

    @Test
    fun `should return list of images in page 1`() {
        val expectedApiResponse = UnsplashImageResponse(
                (1..5).map { index ->
                    UnsplashItem(
                            UnsplashUrls(
                                    "#$index fullscreen url",
                                    "#$index preview url"
                            )
                    )
                }
        )

        val expectedList = expectedApiResponse.images.map {
            ImageItem(it.previewUrl!!, it.fullscreenUrl!!)
        }

        `when`(api.getImages(page = 1)).thenReturn(Observable.just(expectedApiResponse))

        repo.fetchImages(page = 1)
                .test()
                .assertValueCount(1)
                .assertResult(expectedList)
    }

    @Test
    fun `should return list of images with valid url`() {
        val expectedApiResponse = UnsplashImageResponse(
                (1..5).map { index ->
                    val preview = if (index == 1) null else "#$index preview url"
                    val full = if (index == 3) null else "#$index fullscreen url"
                    UnsplashItem(
                            UnsplashUrls(
                                    full,
                                    preview
                            )
                    )
                }
        )

        val expectedList = expectedApiResponse.images
                .asSequence()
                .filterNot { item ->
                    item.previewUrl.isNullOrBlank() || item.fullscreenUrl.isNullOrBlank()
                }.map {
                    ImageItem(it.previewUrl!!, it.fullscreenUrl!!)
                }
                .toList()

        `when`(api.getImages(page = 1)).thenReturn(Observable.just(expectedApiResponse))

        repo.fetchImages(page = 1)
                .test()
                .assertValueCount(1)
                .assertValue { it.size == 3 }
                .assertResult(expectedList)
    }

    @Test
    fun `should return next page of images`() {
        val expectedApiResponse = UnsplashImageResponse(
                (1..5).map { index ->
                    UnsplashItem(
                            UnsplashUrls(
                                    "#$index fullscreen url",
                                    "#$index preview url"
                            )
                    )
                }
        )

        val expectedList = expectedApiResponse.images.map {
            ImageItem(it.previewUrl!!, it.fullscreenUrl!!)
        }

        `when`(api.getImages(page = 2)).thenReturn(Observable.just(expectedApiResponse))
        repo.loadMore()
                .test()
                .assertValueCount(1)
                .assertResult(expectedList)

        `when`(api.getImages(page = 3)).thenReturn(Observable.just(expectedApiResponse))
        repo.loadMore()
                .test()
                .assertValueCount(1)
                .assertResult(expectedList)
    }
}