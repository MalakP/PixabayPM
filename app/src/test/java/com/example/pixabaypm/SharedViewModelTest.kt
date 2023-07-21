package com.example.pixabaypm

import androidx.lifecycle.SavedStateHandle
import com.example.pixabaypm.domain.usecase.GetPicturesUseCase
import com.example.pixabaypm.ui.SharedViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

//Test class for SharedViewModel
class SharedViewModelTest {

    private lateinit var viewModel: SharedViewModel

    private val mockGetPicturesUseCase = mock(GetPicturesUseCase::class.java)

    @Before
    fun setup() {
        val savedState = SavedStateHandle(mapOf("query" to testQuery))
        viewModel = SharedViewModel(savedStateHandle = savedState, mockGetPicturesUseCase)
    }

    @Test
    fun `check if query is changed in state flow`()  = runTest{

    }

    companion object {
        const val testQuery = "dogs"
    }
}