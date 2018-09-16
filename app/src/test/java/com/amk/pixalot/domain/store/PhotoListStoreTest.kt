package com.amk.pixalot.domain.store

import com.amk.pixalot.domain.viewmodel.ImageItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhotoListStoreTest {

    private lateinit var store: PhotoListStore

    @Before
    fun setUp() {
        store = PhotoListStore()
    }

    @Test
    fun `should return empty list on init`() {
        assertTrue(store.currentImages.isEmpty())
    }

    @Test
    fun `should return current list of image item`() {
        val images = listOf(
                ImageItem("preview url", "fullscreen url")
        )
        store.update(images)
        val actual = store.currentImages

        assertEquals(images.size, actual.size)
        assertEquals(images.first().previewUrl, actual.first().previewUrl)
        assertEquals(images.first().fullscreenUrl, actual.first().fullscreenUrl)
    }

    @Test
    fun `should return current view model with list of images`() {
        val images = listOf(
                ImageItem("preview url", "fullscreen url")
        )
        val actual = store.update(images)

        assertEquals(images.size, actual.images.size)
        assertEquals(images.first().previewUrl, actual.images.first().previewUrl)
        assertEquals(images.first().fullscreenUrl, actual.images.first().fullscreenUrl)
    }

    @Test
    fun `should return new view model with new images`() {
        val images = listOf(
                ImageItem("preview url", "fullscreen url")
        )

        val before = store.update(images)
        assertEquals(images.size, before.images.size)
        assertEquals(images.first().previewUrl, before.images.first().previewUrl)
        assertEquals(images.first().fullscreenUrl, before.images.first().fullscreenUrl)

        val nextImages = listOf(
                ImageItem("preview url 2", "fullscreen url 2")
        )

        val expected = images.plus(nextImages)
        val actual = store.add(nextImages)

        assertEquals(expected.size, actual.images.size)
        assertEquals(expected[0].previewUrl, actual.images[0].previewUrl)
        assertEquals(expected[0].fullscreenUrl, actual.images[0].fullscreenUrl)
        assertEquals(expected[1].previewUrl, actual.images[1].previewUrl)
        assertEquals(expected[1].fullscreenUrl, actual.images[1].fullscreenUrl)
    }
}