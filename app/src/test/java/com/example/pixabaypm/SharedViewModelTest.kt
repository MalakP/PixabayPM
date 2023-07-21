package com.example.pixabaypm

import androidx.lifecycle.SavedStateHandle
import com.example.pixabaypm.ui.SharedViewModel
import org.junit.Before

//Test class for SharedViewModel
class SharedViewModelTest {

    private lateinit var viewModel: SharedViewModel

    @Before
    fun setup() {
        val savedState = SavedStateHandle(mapOf("query" to testId))
        viewModel = SharedViewModel(savedStateHandle = savedState)
    }

    //test method onQueryChanged() of SharedViewModel
    fun onQueryChanged_Test() {


    }

    companion object {
        const val testId = "testId"
    }
}