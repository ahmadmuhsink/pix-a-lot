package com.amk.pixalot.domain.workflow

import com.amk.pixalot.common.RxImmediateSchedulerRule
import com.amk.pixalot.domain.repo.PixabayRepository
import com.amk.pixalot.domain.repo.UnsplashRepository
import com.amk.pixalot.domain.store.PhotoListStore
import com.amk.pixalot.domain.viewmodel.ImageItem
import com.amk.pixalot.screen.home.photolist.PhotoListViewModel
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhotoListWorkflowTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var unsplashRepo: UnsplashRepository

    @Mock
    private lateinit var pixabayRepo: PixabayRepository

    private lateinit var workflow: PhotoListWorkflow

    @Before
    fun setup() {
        workflow = PhotoListWorkflow(pixabayRepo, unsplashRepo, PhotoListStore())
    }

    @Test
    fun `should fetch new images and return view model`() {
        val expectedPixa = (1..5).map { index ->
            ImageItem(
                    "#$index preview url",
                    "#$index full url"
            )
        }

        val expectedUnsplash = (6..10).map { index ->
            ImageItem(
                    "#$index preview url",
                    "#$index full url"
            )
        }

        `when`(pixabayRepo.fetchImages(1)).thenReturn(Observable.just(expectedPixa))
        `when`(unsplashRepo.fetchImages(1)).thenReturn(Observable.just(expectedUnsplash))

        val expected = PhotoListViewModel(expectedPixa + expectedUnsplash)

        workflow.load()
                .test()
                .assertValueCount(1)
                .assertValue { it.images.size == 10 }
                .assertResult(expected)
    }

    @Test
    fun `should fetch next page of images and return view model`() {
        val expectedPixa = (1..5).map { index ->
            ImageItem(
                    "#$index preview url",
                    "#$index full url"
            )
        }

        val expectedUnsplash = (6..10).map { index ->
            ImageItem(
                    "#$index preview url",
                    "#$index full url"
            )
        }

        `when`(pixabayRepo.loadMore()).thenReturn(Observable.just(expectedPixa))
        `when`(unsplashRepo.loadMore()).thenReturn(Observable.just(expectedUnsplash))

        val expected = PhotoListViewModel(expectedPixa + expectedUnsplash)

        workflow.loadMore()
                .test()
                .assertValueCount(1)
                .assertValue { it.images.size == 10 }
                .assertResult(expected)
    }
}