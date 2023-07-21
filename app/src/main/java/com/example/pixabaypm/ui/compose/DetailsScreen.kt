package com.example.pixabaypm.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
fun DetailsScreen(viewModel: SharedViewModel, imgId: Long, onBackClick: () -> Unit) {

    val stateFlow = viewModel.stateFlow.collectAsStateWithLifecycle()

    if (stateFlow.value.imagesFetched.isEmpty()) {
        viewModel.onInit()
    }

    Content(stateFlow.value.imagesFetched.firstOrNull { it.id == imgId }, onBackClick)

}

@Composable
fun Content(img: PictureModel?, onBackClick: () -> Unit) {
    img?.let {
        Surface(modifier = Modifier.fillMaxSize()) {

            val configuration = LocalConfiguration.current
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(img.largeImageUrl)
                    .crossfade(true).build(),
                contentDescription = img.userName,
                contentScale = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    ContentScale.FillHeight
                } else {
                    ContentScale.FillWidth
                },

                placeholder = painterResource(id = R.drawable.image_512),
                error = painterResource(id = R.drawable.baseline_broken_image_512)
            )

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.teal_bg)),
                onClick = { onBackClick() }, modifier = Modifier
                    .padding(all = 8.dp)
                    .wrapContentWidth(align = Alignment.Start)
                    .wrapContentHeight(align = Alignment.Top)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    tint = Color.Black,
                    contentDescription = "Back icon"
                )
            }

            Column(
                modifier = Modifier
                    .wrapContentWidth(align = Alignment.End)
                    .wrapContentHeight(align = Alignment.Bottom)
                    .padding(all = 8.dp)
                    .background(
                        colorResource(id = R.color.teal_bg),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(all = 10.dp)
            ) {
                Row(verticalAlignment = CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(R.string.author_icon_content_description)
                    )
                    Text(
                        text = img.userName,
                        modifier = Modifier.padding(all = 2.dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Tag,
                        contentDescription = stringResource(R.string.tags_icon_content_description)
                    )
                    Text(
                        text = img.tags,
                        modifier = Modifier.padding(all = 2.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = CenterVertically) {

                    Row(
                        modifier = Modifier.padding(all = 4.dp),
                        verticalAlignment = CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(
                                R.string.likes_icon_content_description
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = img.likesNr.toString(),

                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(verticalAlignment = CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = stringResource(R.string.downloads_icon_content_description)
                        )
                        Text(
                            text = img.downloadsNr.toString(),
                            modifier = Modifier.padding(all = 4.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Row(verticalAlignment = CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Comment,
                            contentDescription = stringResource(
                                R.string.comments_icon_content_description
                            )
                        )
                        Text(
                            text = img.downloadsNr.toString(),
                            modifier = Modifier.padding(all = 2.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode",
    heightDp = 360, widthDp = 800
)
@Preview
@Composable
fun PreviewDetails() {
    PixabayPMTheme {
        Content(
            img = PictureModel(
                id = 123,
                userName = "Henryk Muszka",
                tags = "nature, landscape",
                likesNr = 10,
                downloadsNr = 5,
                commentsNr = 3,
                imageUrl = "https://i2.wp.com/www.danapointtimes.com/wp-content/uploads/2015/06/Pilgrim_Gibby-Zone57.jpg",
                largeImageUrl = "https://i2.wp.com/www.danapointtimes.com/wp-content/uploads/2015/06/Pilgrim_Gibby-Zone57.jpg"
            ), {}
        )
    }
}
