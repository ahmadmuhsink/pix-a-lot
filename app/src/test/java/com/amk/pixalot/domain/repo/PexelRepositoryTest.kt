package com.amk.pixalot.domain.repo

import com.amk.pixalot.common.RxImmediateSchedulerRule
import com.amk.pixalot.domain.network.pexel.PexelApi
import com.amk.pixalot.domain.network.pexel.PexelImageResponse
import com.amk.pixalot.domain.network.pexel.PexelItem
import com.amk.pixalot.domain.network.pexel.PexelUrls
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
class PexelRepositoryTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var api: PexelApi

    private lateinit var repo: PexelRepository

    @Before
    fun setup() {
        repo = PexelRepository(api)
    }

    @Test
    fun `should return list of images in page 1`() {
        val expectedApiResponse = PexelImageResponse(
                (1..5).map { index ->
                    PexelItem(
                            PexelUrls(
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
        val expectedApiResponse = PexelImageResponse(
                (1..5).map { index ->
                    val preview = if (index == 1) null else "#$index preview url"
                    val full = if (index == 3) null else "#$index fullscreen url"
                    PexelItem(
                            PexelUrls(
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
        val expectedApiResponse = PexelImageResponse(
                (1..5).map { index ->
                    PexelItem(
                            PexelUrls(
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