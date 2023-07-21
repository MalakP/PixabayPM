package com.example.pixabaypm.ui

import androidx.lifecycle.SavedStateHandle
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
class SharedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPicturesUseCase: GetPicturesUseCase
) :
    ViewModel() {

    private val _stateFlow = MutableStateFlow(SearchScreenState())
    val stateFlow: StateFlow<SearchScreenState> = _stateFlow.asStateFlow()

    init {
        onInit()
    }

    fun onInit() {
        val query = savedStateHandle.get<String>(QUERY_KEY) ?: INITIAL_QUERY
        _stateFlow.update { it.copy(query = query) }
        searchImages(stateFlow.value.query)
    }

    fun onQueryChanged(query: String) {
        _stateFlow.update { it.copy(query = query) }
        savedStateHandle[QUERY_KEY] = query
    }

    fun searchImages(query: String) {
        viewModelScope.launch {
            getPicturesUseCase(query)
                .onStart { _stateFlow.update { it.copy(isLoading = true) } }
                .onCompletion {
                    _stateFlow.update {
                        it.copy(
                            isLoading = false
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

    companion object {
        private const val QUERY_KEY = "query"
        private const val INITIAL_QUERY = "fruits"
    }
}