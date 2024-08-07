package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.nibalk.tasky.core.presentation.R
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.TaskyGray
import com.nibalk.tasky.core.presentation.themes.TaskyGreen
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing

@Composable
fun TaskyPhotoCarousel(
    photos: List<ByteArray?> = emptyList(),
    maxPhotoCount: Int,
    isEditable: Boolean,
    onPhotoClicked: (ByteArray?) -> Unit,
    onPhotoAddIconClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            itemsIndexed(
                items = photos,
            ) { index, photo ->
                TaskyPhotoThumbnail(
                    photo = photo,
                    onClick = {
                        onPhotoClicked(photo)
                    },
                    footerText = (index + 1).toString(),
                    modifier = Modifier
                        .then(
                            if (index == 0) {
                                Modifier.padding(start = MaterialTheme.spacing.spaceMedium)
                            } else if (index == photos.lastIndex && index == maxPhotoCount - 1) {
                                Modifier.padding(end = MaterialTheme.spacing.spaceMedium)
                            } else {
                                Modifier
                            },
                        ),
                )
            }
            if (isEditable && photos.size <= maxPhotoCount) {
                item {
                    TaskyPhotoAddThumbnail(
                        onClick = onPhotoAddIconClicked,
                        modifier = Modifier
                            .padding(end = MaterialTheme.spacing.spaceMedium),
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskyPhotoThumbnail(
    modifier: Modifier = Modifier,
    photo: ByteArray?,
    footerText: String,
    onClick: () -> Unit,
) {
    TaskyPhotoPlaceholder(
        modifier = modifier,
        onClick = onClick,
        footerText = footerText,
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = photo,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            error = {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null
                )
            },
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = TaskyGreen
                )
            }
        )
    }
}

@Composable
private fun TaskyPhotoAddThumbnail(
    modifier: Modifier = Modifier,
    iconColor: Color = TaskyGray,
    footerText: String = "",
    onClick: () -> Unit,
) {
    TaskyPhotoPlaceholder(
        onClick = onClick,
        modifier = modifier,
        footerText = footerText
    ) {
        Icon(
            imageVector = Icons.Sharp.Add,
            contentDescription = stringResource(id = R.string.photos_section_title_add),
            tint = iconColor,
        )
    }
}

@Composable
private fun TaskyPhotoPlaceholder(
    modifier: Modifier = Modifier,
    borderColor: Color = TaskyGray,
    borderWidth: Dp = 2.dp,
    thumbnailCorner: Dp = 5.dp,
    thumbnailSize: Dp = 60.dp,
    onClick: () -> Unit,
    footerText: String,
    content: @Composable () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .size(thumbnailSize + borderWidth)
                .clip(RoundedCornerShape(thumbnailCorner))
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(thumbnailCorner),
                )
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
        Text(
            color = TaskyDarkGray,
            text = footerText,
            style = MaterialTheme.typography.displaySmall,
        )
    }
}

// Previews

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyPhotoCarouselPreview() {
    TaskyTheme {
        TaskyPhotoCarousel(
            photos = listOf(null, null, null, null, null),
            maxPhotoCount = 5,
            isEditable = true,
            onPhotoClicked = {},
            onPhotoAddIconClicked = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyPhotoThumbnailPreview() {
    TaskyTheme {
        TaskyPhotoThumbnail(
            photo = null,
            footerText = "1",
            onClick = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyPhotoAddThumbnailPreview() {
    TaskyTheme {
        TaskyPhotoAddThumbnail(
            onClick = {},
            footerText = ""
        )
    }
}
