package com.example.pixabaypm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabaypm.domain.usecase.GetPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val getPicturesUseCase: GetPicturesUseCase) :
    ViewModel() {

    private val _stateFlow = MutableStateFlow(SearchScreenState())
    val stateFlow: StateFlow<SearchScreenState> = _stateFlow.asStateFlow()


    fun onInit() {
        onQueryChanged("fruits")
        searchImages(stateFlow.value.query)
    }

    fun onQueryChanged(query: String) {
        _stateFlow.update { it.copy(query = query) }
    }

    fun searchImages(query: String) {
        viewModelScope.launch {
            getPicturesUseCase
                .execute(query)
                .onStart { _stateFlow.update { it.copy(isLoading = true) } }
                .onCompletion {
                    _stateFlow.update {
                        it.copy(
                            isLoading = false,
                            initialized = true
                        )
                    }
                }
                .catch { ex ->
                    _stateFlow.update {
                        it.copy(
                            error = ex.message ?: " unknown error"
                        )
                    }
                }
                .collect { images ->
                    _stateFlow.update { it.copy(imagesFetched = images, error = "") }
                }

        }
    }
}