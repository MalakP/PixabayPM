package com.example.pixabaypm.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pixabaypm.R
import com.example.pixabaypm.domain.model.PictureModel
import com.example.pixabaypm.ui.SharedViewModel
import com.example.pixabaypm.ui.theme.PixabayPMTheme

@Composable
fun SearchScreen(
    searchViewModel: SharedViewModel,
    onRowClick: (id: Long) -> Unit
) {
    val uiState by searchViewModel.stateFlow.collectAsStateWithLifecycle()
    if (!uiState.initialized) {
        searchViewModel.onInit()
    }

    PixabayPMTheme {
        Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {

            Loading(isVisible = uiState.isLoading)

            SearchBar(uiState.query, searchViewModel)

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
fun SearchBar(query: String, viewModel: SharedViewModel) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = query,
        onValueChange = { viewModel.onQueryChanged(it) },
        label = { Text(stringResource(R.string.search_tooltip)) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            viewModel.searchImages(query)
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

@Composable
fun PixRow(img: PictureModel, onShowDetails: (id: Long) -> Unit) {
    var itemClicked by rememberSaveable {
        mutableStateOf(false)
    }
    if (itemClicked) {
        AlertDialog(
            onDismissRequest = { itemClicked = false },
            title = { Text(stringResource(R.string.image_click_popup_title)) },
            text = { Text(stringResource(R.string.image_click_popup_message)) },
            confirmButton = {
                TextButton(onClick = {
                    onShowDetails(img.id)
                    itemClicked = false
                }) {
                    Text(stringResource(R.string.yes).uppercase())
                }
            },
            dismissButton = {
                TextButton(onClick = { itemClicked = false }) {
                    Text(text = stringResource(R.string.no).uppercase())
                }
            }
        )

    }
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .clickable { itemClicked = true }
        .fillMaxWidth()) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(img.imageUrl)
                .crossfade(true).build(),
            contentDescription = img.userName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .border(1.5.dp, MaterialTheme.colorScheme.inversePrimary)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column() {
            Text(
                text = img.userName,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = img.tags,
                modifier = Modifier.padding(all = 2.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )

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
