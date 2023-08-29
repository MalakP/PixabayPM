package com.example.pixabaypm.ui.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pixabaypm.R
import com.example.pixabaypm.domain.model.PictureModel

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