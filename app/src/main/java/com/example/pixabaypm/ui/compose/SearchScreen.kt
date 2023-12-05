package com.example.pixabaypm.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pixabaypm.R
import com.example.pixabaypm.domain.model.PictureModel
import com.example.pixabaypm.ui.SharedViewModel
import com.example.pixabaypm.ui.UIEvent
import com.example.pixabaypm.ui.theme.PixabayPMTheme

@Composable
fun SearchScreen(
    searchViewModel: SharedViewModel,
    onRowClick: (id: Long) -> Unit
) {
    val uiState by searchViewModel.stateFlow.collectAsStateWithLifecycle()

    PixabayPMTheme {
        Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {

            Loading(isVisible = uiState.isLoading)

            SearchBar(uiState.query,
                onQueryChanged = { query -> searchViewModel.onEvent(UIEvent.QueryChanged(query)) },
                onSearchImages = { query -> searchViewModel.onEvent(UIEvent.SearchImages(query)) })

            PixList(uiState.imagesFetched, onRowClick)

            if (uiState.error != "") {
                ErrorMessage(text = uiState.error)
            }
        }
    }
}

@Composable
fun ErrorMessage(text: String) {
    var showDialog by rememberSaveable { mutableStateOf(true) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.error)) },
            text = { Text(stringResource(R.string.error_details) + text) },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(android.R.string.ok).uppercase())
                }
            }
        )
    }
}

@Composable
fun Loading(isVisible: Boolean) {
    if (isVisible) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)

        ) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit, onSearchImages: (String) -> Unit) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = query,
        onValueChange = { onQueryChanged(it) },
        label = { Text(stringResource(R.string.search_tooltip)) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearchImages(query)
            focusManager.clearFocus()
        }),
        singleLine = true
    )
}

@Composable
fun PixList(messages: List<PictureModel>, onRowClick: (id: Long) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(messages) { message ->
            PixRow(message, onRowClick)
            Divider(color = Color.LightGray)
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Preview
@Composable
fun PreviewMessageList() {
    PixList(messages = listOf(
        PictureModel(
            id = 123,
            userName = "Henryk Muszka",
            tags = "nature, landscape",
            likesNr = 10,
            downloadsNr = 5,
            commentsNr = 3,
            imageUrl = "https://pixabay.com/get/ed6a9364a9fd0a76647.jpg",
            largeImageUrl = "https://pixabay.com/get/ed6a99fd0a76647_1280.jpg"
        ),
        PictureModel(
            id = 124,
            userName = "Wanda Loria",
            tags = "nature, landscape",
            likesNr = 10,
            downloadsNr = 5,
            commentsNr = 3,
            imageUrl = "https://pixabay.com/get/ed6a9364a9fd0a76647.jpg",
            largeImageUrl = "https://pixabay.com/get/ed6a99fd0a76647_1280.jpg"
        )
    ), {})
}

@Preview
@Composable
fun PreviewLoading() {
    PixabayPMTheme {
        Loading(true)
    }
}
