package com.example.pixabaypm

import androidx.lifecycle.SavedStateHandle
import com.example.pixabaypm.domain.model.PictureModel
import com.example.pixabaypm.domain.usecase.GetPicturesUseCase
import com.example.pixabaypm.ui.SharedViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

//Test class for SharedViewModel
class SharedViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private lateinit var viewModel: SharedViewModel
    private lateinit var savedState: SavedStateHandle

    private val mockGetPicturesUseCase = mock(GetPicturesUseCase::class.java)

    @Before
    fun setup() {
        savedState = SavedStateHandle(mapOf("query" to initQuery))
        viewModel = SharedViewModel(savedStateHandle = savedState, mockGetPicturesUseCase)
    }


    @Test
    fun `check if query is changed in state flow`() = runTest() {
        viewModel.onQueryChanged(testQuery)
        assert(viewModel.stateFlow.value.query == testQuery)
    }

    @Test
    fun `check if onInit set state flow query to saved state query`() = runTest {
        assert(viewModel.stateFlow.value.query == initQuery)
    }

    @Test
    fun `check if searchImages returns state flow imagesFetched with sample data`() = runTest {
        Mockito.`when`(mockGetPicturesUseCase(Mockito.anyString()))
            .thenReturn(flowOf(samplePictures))
        viewModel.searchImages(Mockito.anyString())
        assert(viewModel.stateFlow.value.imagesFetched == samplePictures)
    }

    companion object {
        const val testQuery = "dogs"
        const val initQuery = "dogs"
        val samplePictures = listOf(
            PictureModel(
                id = 1,
                userName = "Bard",
                tags = "nature, landscape, mountains",
                likesNr = 100,
                downloadsNr = 50,
                commentsNr = 20,
                imageUrl = "https://example.com/image1.jpg",
                largeImageUrl = "https://example.com/image1_large.jpg",
            ),
            PictureModel(
                id = 2,
                userName = "Alice",
                tags = "cat, dog, pet",
                likesNr = 200,
                downloadsNr = 100,
                commentsNr = 50,
                imageUrl = "https://example.com/image2.jpg",
                largeImageUrl = "https://example.com/image2_large.jpg",
            )
        )
    }
}